package lot.hack.aislop.gru;

import java.util.ArrayList;
import java.util.List;

public class GRULayer {
    private final List<GRUNeuron> neurons;
    private double[] lastHidden;

    GRULayer(int neuronsCount, int inputSize) {
        neurons = new ArrayList<>();

        for (int i = 0; i < neuronsCount; i++) {
            neurons.add(new GRUNeuron(inputSize, neuronsCount));
        }

        lastHidden = new double[neuronsCount];
    }

    public List<GRUNeuron> getNeurons() {
        return neurons;
    }

    public double[] getLastHidden() {
        return lastHidden;
    }

    double[] forward(double[] inputs) {
        int size = neurons.size();
        double[] prevHidden = lastHidden;

        for (GRUNeuron neuron : neurons) {
            neuron.forwardGates(inputs, prevHidden);
        }

        double[] rh = new double[size];
        for (int k = 0; k < size; k++) {
            rh[k] = neurons.get(k).reset * prevHidden[k];
        }

        double[] hidden = new double[size];
        for (int k = 0; k < size; k++) {
            hidden[k] = neurons.get(k).forwardHidden(inputs, rh, prevHidden[k]);
        }

        lastHidden = hidden;
        return hidden;
    }

    void reset() {
        lastHidden = new double[lastHidden.length];
    }
}
