package lot.hack.aislop.mlp;

import java.util.concurrent.ThreadLocalRandom;

import lot.hack.aislop.ActivationFunction;

public class MLPNeuron {
    final double[] weights;
    double bias;

    final ActivationFunction activation;

    double lastInputSum;
    double lastOutput;

    MLPNeuron(int prevLayerSize, ActivationFunction activation) {
        this.activation = activation;
        weights = new double[prevLayerSize];

        double bound = 1 / Math.sqrt(Math.max(prevLayerSize, 1));

        for (int i = 0; i < prevLayerSize; i++) {
            weights[i] = ThreadLocalRandom.current().nextDouble(-bound, bound);
        }

        bias = ThreadLocalRandom.current().nextDouble(-bound, bound);
    }

    double forward(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        sum += bias;

        lastInputSum = sum;
        lastOutput = activation.function(sum);
        return lastOutput;
    }
}
