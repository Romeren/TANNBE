package com.main.network.neurons;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public interface ISOMsNode extends INeuron {

    double getDistance();
    void adjustWeights(double inputVector, double learningRate, double influence, int weightNumber);
    double[] getWeights();
}
