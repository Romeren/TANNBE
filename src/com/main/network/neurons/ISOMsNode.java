package com.main.network.neurons;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public interface ISOMsNode {

    double calculateDistance(double[] inputVector);
    void adjustWeights(double[] inputVector, double learningRate, double influence);
}
