package com.main.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.RandomUtilz;
import com.main.network.connections.IConnection;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class CorrelationSomsNode extends ANeuron implements ISOMsNode {
    private double[] weights;
    private double[] buffer;
    private double distance = Double.MIN_VALUE;

    public CorrelationSomsNode(){
        this.weights = new double[NetworkConfiguration.dimentionalityOfInput];
        this.buffer = new double[weights.length];
        for (int i = 0; i < NetworkConfiguration.dimentionalityOfInput; i++){
            this.weights[i] = RandomUtilz.getDoubleInRange(
                    NetworkConfiguration.minimumInitializedSOMWeight,
                    NetworkConfiguration.maximumInitializedSOMWeight);
            this.buffer[i] = Double.MIN_VALUE;
        }
    }

    @Override
    public void resetNeuron() {
        super.resetNeuron();
        for (int i = 0; i < this.buffer.length; i++){
            this.buffer[i] = Double.MIN_VALUE;
        }
        this.distance = Double.MIN_VALUE;
    }

    @Override
    public void addInput(double input){
        int i = 0;
        while (i < this.buffer.length && this.buffer[i] != Double.MIN_VALUE){
            i++;
        }
        if(i > this.buffer.length){
            try {
                throw new Exception("Too many inputs");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.buffer[i] = input;
    }
    @Override
    public double getDistance() {
        if(this.distance == Double.MIN_VALUE) {
            /*
            double coeficientWeight = 0;
            double coeficientBuffer = 0;
            for (int i = 0; i < this.weights.length; i++) {
                coeficientWeight -= this.weights[i];
                coeficientBuffer -= this.buffer[i];
            }
            coeficientBuffer = coeficientBuffer * (1 / this.buffer.length);
            coeficientWeight = coeficientWeight * (1 / this.weights.length);

            double summationOfCoeficient = 0;
            double squardBuffer = 0;
            double squardWeight = 0;
            for (int i = 0; i < this.weights.length; i++) {
                double tmpBuffer = (this.buffer[i] + coeficientBuffer);
                double tmpWeight = (this.weights[i] + coeficientWeight);

                summationOfCoeficient += (tmpBuffer * tmpWeight);
                squardBuffer += Math.abs(tmpBuffer * tmpBuffer);
                squardWeight += Math.abs(tmpWeight * tmpWeight);
            }
            this.distance = 1-(summationOfCoeficient/(Math.sqrt(squardBuffer)*Math.sqrt(squardWeight)));
            */
            double product = 0;
            double x = 0;
            double y = 0;
            double x2 = 0;
            double y2 = 0;
            for(int i = 0; i < this.weights.length; i++){
                product += this.weights[i] * this.buffer[i];
                x += this.weights[i];
                y += this.buffer[i];
                x2 +=this.weights[i] *this.weights[i];
                y2 += this.buffer[i] * this.buffer[i];
            }
            double top = this.weights.length * (product - x*y);
            double buttom = Math.sqrt(this.weights.length  * x2 - (x*x)) * Math.sqrt(this.weights.length * y2 - (y*y));
            this.distance = top/buttom;
            this.distance = 1 -( this.distance * this.distance);
        }
        return distance;
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
    public void feedToActivationFunction() {
        // todo: soms gets activated if it has the shortest distance.... and output is the weights!
        this.output = this.getInput();
    }



    @Override
    public void backpropagate() {}

}
