package network.neurons;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public interface INeuron {

    void resetNeuron();
    void addInput(double i);
    void feedForward();
    void feedToActivationFunction();
    void backpropagate();
    double getOutput();
}
