package com.main.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.RandomUtilz;
import com.main.network.connections.IConnection;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class SomsNode extends ANeuron implements ISOMsNode {
    private double[] weights;
    private double[] buffer;

    public SomsNode(){
        this.weights = new double[NetworkConfiguration.dimentionalityOfInput];
        this.buffer = new double[weights.length];
        for (int i = 0; i < NetworkConfiguration.dimentionalityOfInput; i++){
            this.weights[i] = RandomUtilz.getDoubleInRange(
                    NetworkConfiguration.minimumInitializedSOMWeight,
                    NetworkConfiguration.maximumInitializedSOMWeight);
        }
    }

    @Override
    public double getDistance() {
        return Math.sqrt(this.input);
    }

    @Override
    public void adjustWeights(double inputVector, double learningRate, double influence, int weightNumber) {
        double deltaWeight = (this.weights[weightNumber] - inputVector) * NetworkConfiguration.learningRate * influence;
        this.weights[weightNumber] -= deltaWeight;
    }

    @Override
    public double[] getWeights() {
        return this.weights;
    }

    @Override
    public void feedToActivationFunction() { this.output = this.getInput();}



    @Override
    public void backpropagate() {}

}
