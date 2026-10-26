package lot.hack.aislop;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum ActivationFunction {
    LINEAR(
        x -> x, 
        (x, activated) -> 1.0
    ),

    SIGMOID(
        x -> 1.0 / (1.0 + Math.exp(-x)), 
        (x, activated) -> activated * (1.0 - activated)
    ),

    TANH(
        x -> Math.tanh(x),
        (x, activated) -> 1 - activated * activated
    ),

    RELU(
        x -> x > 0 ? x : 0.0, 
        (x, activated) -> x > 0 ? 1.0 : 0.0
    ),

    GELU(
        x -> 0.5 * x * (1.0 + Math.tanh(Math.sqrt(2.0 / Math.PI) * (x + 0.044715 * Math.pow(x, 3)))),
        (x, activated) -> {
            double c = Math.sqrt(2.0 / Math.PI);
            double x3 = 0.044715 * Math.pow(x, 3);
            double inner = c * (x + x3);
            double tanhInner = Math.tanh(inner);
            double sech2 = 1.0 - Math.pow(tanhInner, 2);
            return 0.5 * (1.0 + tanhInner) + 0.5 * x * sech2 * c * (1.0 + 3 * 0.044715 * Math.pow(x, 2));
        }
    );

    private final Function<Double, Double> function;
    private final BiFunction<Double, Double, Double> derivative; 

    ActivationFunction(Function<Double, Double> function, BiFunction<Double, Double, Double> derivative) {
        this.function = function;
        this.derivative = derivative;
    }

    public double function(double x) {
        return function.apply(x);
    }

    public double derivative(double inputSum, double activated) {
        return derivative.apply(inputSum, activated);
    }
}
