package com.main.network.connections;


import com.main.network.neurons.INeuron;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public interface IConnection<T,Y> {
    void resetValues();
    void feedForward(double input);
    void feedBackwards(double input);
    T getStart();
    Y getEnd();
    void backpropagate();
    void unsupervizedRBM();
    double getWeight();
    void setWeight(double weight);
}
