package com.main.network.connections;


/**
 * Created by EmilSebastian on 17-03-2016.
 */
public interface IConnection {
    void resetValues();
    void feedForward(double input);
    void feedBackwards(double input);
    void backpropagate();
    void unsupervizedRBM();
    double getWeight();
}
