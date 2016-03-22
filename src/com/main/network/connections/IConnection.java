package com.main.network.connections;


/**
 * Created by EmilSebastian on 17-03-2016.
 */
public interface IConnection<I, O> {
    void resetValues();
    void feedForward(I input);
    void feedBackwards(O input);
    void backpropagate();
    void unsupervizedRBM();
    double getWeight();
}
