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

    public SomsNode(int numberOfDimensions){
        this.weights = new double[numberOfDimensions];
        for (int i = 0; i < numberOfDimensions; i++){
            this.weights[i] = RandomUtilz.getDoubleInRange(
                    NetworkConfiguration.minimumInitializedSOMWeight,
                    NetworkConfiguration.maximumInitializedSOMWeight);
        }
    }

    @Override
    public double calculateDistance(double[] inputVector) {
        double distance = 0;
        for (int i = 0 ; i < this.weights.length; i++){
            distance += (inputVector[i] - this.weights[i]) * (inputVector[i] - this.weights[i]);
        }
        return Math.sqrt(distance);
    }

    @Override
    public void adjustWeights(double[] inputVector, double learningRate, double influence) {
        double awg = 0;
        for(int i = 0 ; i < this.weights.length; i++){
            double deltaWeight = (this.weights[i] - inputVector[i]) * NetworkConfiguration.learningRate *influence;
            this.weights[i] -= deltaWeight;
            awg += this.weights[i];
        }
        this.addInput(awg/this.weights.length);
    }

    @Override
    public void feedToActivationFunction() { this.output = this.getInput();}



    @Override
    public void backpropagate() {}

}
