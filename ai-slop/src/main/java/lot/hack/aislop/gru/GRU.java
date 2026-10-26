package lot.hack.aislop.gru;

import java.util.ArrayList;
import java.util.List;

public class GRU {
    private final List<GRULayer> layers = new ArrayList<>();

    public GRU(int inputsCount, GRULayerConfig... layerConfigs) {
        int prevLayerSize = inputsCount;

        for (GRULayerConfig config : layerConfigs) {
            layers.add(new GRULayer(config.neuronsCount(), prevLayerSize));
            prevLayerSize = config.neuronsCount();
        }
    }

    public double[] forward(double[][] sequence) {
        resetState();

        for (double[] inputs : sequence) {
            forwardStep(inputs);
        }

        return getLastHidden();
    }

    public double[] forwardStep(double[] inputs) {
        double[] current = inputs;

        for (GRULayer layer : layers) {
            current = layer.forward(current);
        }

        return current;
    }

    public double[] getLastHidden() {
        return layers.getLast().getLastHidden();
    }

    public List<GRULayer> getLayers() {
        return layers;
    }

    public void resetState() {
        for (GRULayer layer : layers) {
            layer.reset();
        }
    }

    public GRUTrainer trainer(int epochs, double learningRate) {
        return new GRUTrainer(this, epochs, learningRate);
    }
}
