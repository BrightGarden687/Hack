package lot.hack.aislop.gru;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GRUTrainer {
    private final GRU gru;
    private final int epochs;
    private final double learningRate;

    public GRUTrainer(GRU gru, int epochs, double learningRate) {
        this.gru = gru;
        this.epochs = epochs;
        this.learningRate = learningRate;
    }

    public void train(double[][][] inputs, double[][] targets) throws IllegalArgumentException {
        if (inputs.length != targets.length) {
            throw new IllegalArgumentException("inputs and target must have same lenght");
        }

        for (int epoch = 0; epoch < epochs; epoch++) {
            double totalLoss = 0;

            for (int i = 0; i < inputs.length; i++) {
                double[] outputs = trainStep(inputs[i], targets[i]);

                for (int j = 0; j < targets[i].length; j++) {
                    totalLoss += Math.pow(targets[i][j] - outputs[j], 2);
                }
            }

            int currentEpoch = epoch + 1;

            if (currentEpoch % 500 == 0) {
                System.out.printf(
                    "[%d/%d] epoch finished. Loss: %.5f%n",
                    currentEpoch,
                    epochs,
                    totalLoss / inputs.length
                );
            }
        }
    }

    public double[] trainStep(double[][] inputs, double[] target) {
        gru.resetState();

        List<StepCache> caches = forward(inputs);
        List<GRULayer> layers = gru.getLayers();

        List<LayerGradients> gradients = new ArrayList<>();
        for (int l = 0; l < layers.size(); l++) {
            StepCache cache = caches.get(l);
            gradients.add(new LayerGradients(cache.update.getFirst().length, cache.inputs.getFirst().length));
        }

        backward(caches, gradients, target);
        updateWeights(gradients);

        return gru.getLastHidden();
    }

    private List<StepCache> forward(double[][] inputs) {
        List<GRULayer> layers = gru.getLayers();
        List<StepCache> caches = new ArrayList<>();

        for (int l = 0; l < layers.size(); l++) {
            caches.add(new StepCache(l));
        }

        for (int t = 0; t < inputs.length; t++) {
            for (int l = 0; l < layers.size(); l++) {
                caches.get(l).prevHidden.add(layers.get(l).getLastHidden());
            }

            gru.forwardStep(inputs[t]);

            for (int l = 0; l < layers.size(); l++) {
                GRULayer layer = layers.get(l);
                List<GRUNeuron> neurons = layer.getNeurons();

                double[] layerInputs = (l == 0) ? inputs[t] : layers.get(l - 1).getLastHidden();

                double[] update = new double[neurons.size()];
                double[] reset = new double[neurons.size()];
                double[] candidate = new double[neurons.size()];

                for (int j = 0; j < neurons.size(); j++) {
                    GRUNeuron neuron = neurons.get(j);
                    update[j] = neuron.update;
                    reset[j] = neuron.reset;
                    candidate[j] = neuron.candidate;
                }

                double[] rh = new double[neurons.size()];
                for (int k = 0; k < neurons.size(); k++) {
                    rh[k] = reset[k] * caches.get(l).prevHidden.get(t)[k];
                }

                caches.get(l).inputs.add(layerInputs);
                caches.get(l).update.add(update);
                caches.get(l).reset.add(reset);
                caches.get(l).candidate.add(candidate);
                caches.get(l).rh.add(rh);
            }
        }

        return caches;
    }

    private void backward(List<StepCache> caches, List<LayerGradients> gradients, double[] target) {
        List<GRULayer> layers = gru.getLayers();
        int layerCount = layers.size();
        int steps = caches.getFirst().update.size();

        // hiddenError[l] — error of layer l's hidden state for the current time step,
        // carried back through time after each backward step
        double[][] hiddenError = new double[layerCount][];
        double[][] carryError = new double[layerCount][];

        for (int l = 0; l < layerCount; l++) {
            int hiddenSize = layers.get(l).getNeurons().size();
            hiddenError[l] = new double[hiddenSize];
            carryError[l] = new double[hiddenSize];
        }

        double[] outputs = layers.getLast().getLastHidden();
        for (int j = 0; j < outputs.length; j++) {
            hiddenError[layerCount - 1][j] += target[j] - outputs[j];
        }

        // inputError[l] — error of layer l's hidden state at the current time step,
        // filled by layer l + 1 while unrolling backwards
        double[][] inputError = new double[layerCount - 1][];
        for (int l = 0; l < layerCount - 1; l++) {
            inputError[l] = new double[layers.get(l).getNeurons().size()];
        }

        for (int t = steps - 1; t >= 0; t--) {
            for (int l = 0; l < inputError.length; l++) {
                Arrays.fill(inputError[l], 0.0);
            }

            for (int l = layerCount - 1; l >= 0; l--) {
                double[] layerError = hiddenError[l];

                if (l < layerCount - 1) {
                    for (int j = 0; j < layerError.length; j++) {
                        layerError[j] += inputError[l][j];
                    }
                }

                double[] belowError = (l > 0) ? inputError[l - 1] : null;

                backwardStep(t, caches.get(l), gradients.get(l), layerError, carryError[l], belowError);

                double[] swap = hiddenError[l];
                hiddenError[l] = carryError[l];
                carryError[l] = swap;
            }
        }
    }

    private void backwardStep(
        int t,
        StepCache cache,
        LayerGradients gradients,
        double[] layerError,
        double[] carryError,
        double[] inputError
    ) {
        List<GRUNeuron> neurons = gru.getLayers().get(cache.layerIndex).getNeurons();
        int hiddenSize = neurons.size();

        double[] inputs = cache.inputs.get(t);
        double[] prevHidden = cache.prevHidden.get(t);
        double[] rh = cache.rh.get(t);
        double[] update = cache.update.get(t);
        double[] reset = cache.reset.get(t);
        double[] candidate = cache.candidate.get(t);

        Arrays.fill(carryError, 0.0);
        double[] resetError = new double[hiddenSize];

        for (int j = 0; j < hiddenSize; j++) {
            GRUNeuron neuron = neurons.get(j);
            double error = layerError[j];

            double eUpdate = error * (candidate[j] - prevHidden[j]) * update[j] * (1 - update[j]);
            double eCandidate = error * update[j] * (1 - candidate[j] * candidate[j]);

            double[] gUpdateW = gradients.gUpdateW[j];
            double[] gCandW = gradients.gCandW[j];
            double[] gUpdateU = gradients.gUpdateU[j];
            double[] gCandU = gradients.gCandU[j];

            for (int i = 0; i < inputs.length; i++) {
                gUpdateW[i] += eUpdate * inputs[i];
                gCandW[i] += eCandidate * inputs[i];

                if (inputError != null) {
                    inputError[i] += eUpdate * neuron.updateWeights[i];
                    inputError[i] += eCandidate * neuron.candidateWeights[i];
                }
            }

            gradients.gUpdateB[j] += eUpdate;
            gradients.gCandB[j] += eCandidate;

            for (int k = 0; k < hiddenSize; k++) {
                gUpdateU[k] += eUpdate * prevHidden[k];
                gCandU[k] += eCandidate * rh[k];

                carryError[k] += eUpdate * neuron.updateRecurrent[k];
                carryError[k] += eCandidate * neuron.candidateRecurrent[k] * reset[k];

                resetError[k] += eCandidate * neuron.candidateRecurrent[k] * prevHidden[k];
            }
        }

        for (int k = 0; k < hiddenSize; k++) {
            GRUNeuron neuron = neurons.get(k);
            double eReset = resetError[k] * reset[k] * (1 - reset[k]);

            double[] gResetW = gradients.gResetW[k];
            double[] gResetU = gradients.gResetU[k];

            for (int i = 0; i < inputs.length; i++) {
                gResetW[i] += eReset * inputs[i];

                if (inputError != null) {
                    inputError[i] += eReset * neuron.resetWeights[i];
                }
            }

            gradients.gResetB[k] += eReset;

            for (int m = 0; m < hiddenSize; m++) {
                gResetU[m] += eReset * prevHidden[m];
                carryError[m] += eReset * neuron.resetRecurrent[m];
            }
        }

        for (int j = 0; j < hiddenSize; j++) {
            carryError[j] += (1 - update[j]) * layerError[j];
        }
    }

    private void updateWeights(List<LayerGradients> gradients) {
        List<GRULayer> layers = gru.getLayers();

        for (int l = 0; l < layers.size(); l++) {
            LayerGradients gradientsLayer = gradients.get(l);
            List<GRUNeuron> neurons = layers.get(l).getNeurons();

            for (int j = 0; j < neurons.size(); j++) {
                GRUNeuron neuron = neurons.get(j);

                for (int i = 0; i < neuron.updateWeights.length; i++) {
                    neuron.updateWeights[i] += learningRate * gradientsLayer.gUpdateW[j][i];
                    neuron.resetWeights[i] += learningRate * gradientsLayer.gResetW[j][i];
                    neuron.candidateWeights[i] += learningRate * gradientsLayer.gCandW[j][i];
                }

                for (int k = 0; k < neuron.updateRecurrent.length; k++) {
                    neuron.updateRecurrent[k] += learningRate * gradientsLayer.gUpdateU[j][k];
                    neuron.resetRecurrent[k] += learningRate * gradientsLayer.gResetU[j][k];
                    neuron.candidateRecurrent[k] += learningRate * gradientsLayer.gCandU[j][k];
                }

                neuron.updateBias += learningRate * gradientsLayer.gUpdateB[j];
                neuron.resetBias += learningRate * gradientsLayer.gResetB[j];
                neuron.candidateBias += learningRate * gradientsLayer.gCandB[j];
            }
        }
    }

    private static final class StepCache {
        final List<double[]> prevHidden = new ArrayList<>();
        final List<double[]> inputs = new ArrayList<>();
        final List<double[]> rh = new ArrayList<>();
        final List<double[]> update = new ArrayList<>();
        final List<double[]> reset = new ArrayList<>();
        final List<double[]> candidate = new ArrayList<>();

        StepCache(int layerIndex) {
            this.layerIndex = layerIndex;
        }

        final int layerIndex;
    }

    private static final class LayerGradients {
        final double[][] gUpdateW;
        final double[][] gResetW;
        final double[][] gCandW;

        final double[][] gUpdateU;
        final double[][] gResetU;
        final double[][] gCandU;

        final double[] gUpdateB;
        final double[] gResetB;
        final double[] gCandB;

        LayerGradients(int hiddenSize, int inputSize) {
            gUpdateW = new double[hiddenSize][inputSize];
            gResetW = new double[hiddenSize][inputSize];
            gCandW = new double[hiddenSize][inputSize];

            gUpdateU = new double[hiddenSize][hiddenSize];
            gResetU = new double[hiddenSize][hiddenSize];
            gCandU = new double[hiddenSize][hiddenSize];

            gUpdateB = new double[hiddenSize];
            gResetB = new double[hiddenSize];
            gCandB = new double[hiddenSize];
        }
    }
}
