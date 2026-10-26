package lot.hack.aislop.mlp;

import java.util.ArrayList;
import java.util.List;

import lot.hack.aislop.ActivationFunction;

public class MLPLayer {
    private final List<MLPNeuron> neurons;

    MLPLayer(int neuronsCount, int prevLayerSize, ActivationFunction activation) {
        neurons = new ArrayList<>();

        for (int i = 0; i < neuronsCount; i++) {
            neurons.add(new MLPNeuron(prevLayerSize, activation));
        }
    }

    public List<MLPNeuron> getNeurons() {
        return neurons;
    }

    double[] forward(double[] inputs) {
        double[] outputs = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).forward(inputs);
        }

        return outputs;
    }

    public double[] getLastOutputs() {
        double[] outputs = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).lastOutput;
        }

        return outputs;
    }
}
