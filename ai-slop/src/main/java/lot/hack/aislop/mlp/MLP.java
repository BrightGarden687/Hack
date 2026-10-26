package lot.hack.aislop.mlp;

import java.util.ArrayList;
import java.util.List;

public class MLP {
    private final List<MLPLayer> layers = new ArrayList<>();

    public MLP(int inputsCount, MLPLayerConfig... layerConfigs) {
        int prevLayerSize = inputsCount;

        for (MLPLayerConfig config : layerConfigs) {
            layers.add(new MLPLayer(config.neuronsCount(), prevLayerSize, config.activation()));
            prevLayerSize = config.neuronsCount();
        }
    }

    public double[] forward(double[] inputs) {
        double[] outputs = inputs;

        for (MLPLayer layer : layers) {
            outputs = layer.forward(outputs);
        }

        return outputs;
    }

    public List<MLPLayer> getLayers() {
        return layers;
    }

    public MLPTrainer trainer(int epochs, double learningRate) {
        return new MLPTrainer(this, epochs, learningRate);
    }
}
