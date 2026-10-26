package lot.hack.aislop.mlp;

import lot.hack.aislop.ActivationFunction;

public record MLPLayerConfig(int neuronsCount, ActivationFunction activation) { }
