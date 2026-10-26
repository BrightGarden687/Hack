package lot.hack.aislop.mlp;

import java.util.List;

public class MLPTrainer {
    private final MLP mlp;
    private final int epochs;
    private final double learningRate;

    public MLPTrainer(MLP mlp, int epochs, double learningRate) {
        this.mlp = mlp;
        this.epochs = epochs;
        this.learningRate = learningRate;
    }

    public void train(double[][] inputs, double[][] targets) throws IllegalArgumentException {
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

    public double[] trainStep(double[] inputs, double[] targets) {
        double[] outputs = mlp.forward(inputs);
        updateWeights(inputs, calculateDeltas(targets));
        return outputs;
    }

    private double[][] calculateDeltas(double[] needOutputs) {
        List<MLPLayer> layers = mlp.getLayers();
        double[][] deltas = new double[layers.size()][];

        List<MLPNeuron> outputNeurons = layers.getLast().getNeurons();

        double[] outputDeltas = new double[outputNeurons.size()];
        for (int i = 0; i < outputNeurons.size(); i++) {
            MLPNeuron neuron = outputNeurons.get(i);

            double error = needOutputs[i] - neuron.lastOutput;
            outputDeltas[i] = error * neuron.activation.derivative(neuron.lastInputSum, neuron.lastOutput);
        }
        deltas[deltas.length - 1] = outputDeltas;

        for (int i = layers.size() - 2; i >= 0; i--) {
            List<MLPNeuron> neurons = layers.get(i).getNeurons();
            List<MLPNeuron> nextNeurons = layers.get(i + 1).getNeurons();

            double[] layerDeltas = new double[neurons.size()];
            for (int j = 0; j < neurons.size(); j++) {
                double error = 0.0;

                for (int k = 0; k < nextNeurons.size(); k++) {
                    error += deltas[i + 1][k] * nextNeurons.get(k).weights[j];
                }

                MLPNeuron neuron = neurons.get(j);
                layerDeltas[j] = error * neuron.activation.derivative(neuron.lastInputSum, neuron.lastOutput);
            }
            deltas[i] = layerDeltas;
        }

        return deltas;
    }

    private void updateWeights(double[] inputs, double[][] deltas) {
        List<MLPLayer> layers = mlp.getLayers();

        double[] currentInputs = inputs;

        for (int i = 0; i < layers.size(); i++) {
            List<MLPNeuron> neurons = layers.get(i).getNeurons();
            double[] layerDeltas = deltas[i];

            for (int n = 0; n < neurons.size(); n++) {
                MLPNeuron neuron = neurons.get(n);
                double delta = layerDeltas[n];

                for (int w = 0; w < neuron.weights.length; w++) {
                    neuron.weights[w] += learningRate * delta * currentInputs[w];
                }

                neuron.bias += learningRate * delta;
            }

            currentInputs = layers.get(i).getLastOutputs();
        }
    }
}
