package lot.hack.aislop.gru;

import java.util.concurrent.ThreadLocalRandom;

import lot.hack.aislop.ActivationFunction;

public class GRUNeuron {
    final double[] updateWeights;
    final double[] resetWeights;
    final double[] candidateWeights;

    final double[] updateRecurrent;
    final double[] resetRecurrent;
    final double[] candidateRecurrent;

    double updateBias;
    double resetBias;
    double candidateBias;

    double update;
    double reset;
    double candidate;

    double lastHidden;

    GRUNeuron(int inputSize, int hiddenSize) {
        updateWeights = new double[inputSize];
        resetWeights = new double[inputSize];
        candidateWeights = new double[inputSize];

        updateRecurrent = new double[hiddenSize];
        resetRecurrent = new double[hiddenSize];
        candidateRecurrent = new double[hiddenSize];

        double inputBound = 1 / Math.sqrt(Math.max(inputSize, 1));
        double hiddenBound = 1 / Math.sqrt(Math.max(hiddenSize, 1));

        for (int i = 0; i < inputSize; i++) {
            updateWeights[i] = ThreadLocalRandom.current().nextDouble(-inputBound, inputBound);
            resetWeights[i] = ThreadLocalRandom.current().nextDouble(-inputBound, inputBound);
            candidateWeights[i] = ThreadLocalRandom.current().nextDouble(-inputBound, inputBound);
        }

        for (int k = 0; k < hiddenSize; k++) {
            updateRecurrent[k] = ThreadLocalRandom.current().nextDouble(-hiddenBound, hiddenBound);
            resetRecurrent[k] = ThreadLocalRandom.current().nextDouble(-hiddenBound, hiddenBound);
            candidateRecurrent[k] = ThreadLocalRandom.current().nextDouble(-hiddenBound, hiddenBound);
        }

        updateBias = ThreadLocalRandom.current().nextDouble(-inputBound, inputBound);
        resetBias = ThreadLocalRandom.current().nextDouble(-inputBound, inputBound);
        candidateBias = ThreadLocalRandom.current().nextDouble(-inputBound, inputBound);
    }

    void forwardGates(double[] inputs, double[] prevHidden) {
        double updateSum = updateBias;
        double resetSum = resetBias;

        for (int i = 0; i < inputs.length; i++) {
            updateSum += inputs[i] * updateWeights[i];
            resetSum += inputs[i] * resetWeights[i];
        }

        for (int k = 0; k < prevHidden.length; k++) {
            updateSum += prevHidden[k] * updateRecurrent[k];
            resetSum += prevHidden[k] * resetRecurrent[k];
        }

        update = ActivationFunction.SIGMOID.function(updateSum);
        reset = ActivationFunction.SIGMOID.function(resetSum);
    }

    double forwardHidden(double[] inputs, double[] rh, double prevSelf) {
        double candidateSum = candidateBias;

        for (int i = 0; i < inputs.length; i++) {
            candidateSum += inputs[i] * candidateWeights[i];
        }

        for (int k = 0; k < rh.length; k++) {
            candidateSum += rh[k] * candidateRecurrent[k];
        }

        candidate = ActivationFunction.TANH.function(candidateSum);
        lastHidden = (1 - update) * prevSelf + update * candidate;

        return lastHidden;
    }
}
