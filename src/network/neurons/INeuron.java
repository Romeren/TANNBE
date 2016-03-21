package network.neurons;

import network.connections.IConnection;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public interface INeuron {

    void resetNeuron();
    void addInput(double i);
    void feedForward();
    void feedToActivationFunction();
    void backpropagate();
    void startBackpropagate(double targetValue);
    void addBackpropagationError(double propagatedError);
    double getError();
    double getOutput();
    ArrayList<IConnection> getForwardConnections();
    ArrayList<IConnection> getBackwardsConnections();
}
