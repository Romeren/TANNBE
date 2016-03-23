package com.main.network.connections;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.IBackwardsFeed;
import com.main.network.neurons.INeuron;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public abstract class AConnection implements  IConnection {
    private static final double UNCHANGEDUPDATEDWEIGHT = Double.MIN_VALUE;
    protected double weight;
    protected double updatedWeight = UNCHANGEDUPDATEDWEIGHT;
    protected INeuron start;
    protected INeuron end;

    public AConnection(double weight, INeuron start, INeuron end){
        this.weight = weight;
        this.start = start;
        this.end = end;
    }

    @Override
    public void resetValues() {
        if(updatedWeight != UNCHANGEDUPDATEDWEIGHT){
            this.weight = updatedWeight;
            this.updatedWeight = UNCHANGEDUPDATEDWEIGHT;
        }
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void feedForward(double input) {
        this.end.addInput(input * this.weight);
    }

    @Override
    public void feedBackwards(double input) {
        this.start.addInput(input * this.weight);
    }

    @Override
    public void unsupervizedRBM() {
        if(!(start instanceof IBackwardsFeed) && !(end instanceof IBackwardsFeed)){
            return;
        }
        IBackwardsFeed start = (IBackwardsFeed) this.start;
        IBackwardsFeed end = (IBackwardsFeed) this.end;
        this.updatedWeight = this.weight + (NetworkConfiguration.learningRate *
                (start.getLastRoundsOutput() * end.getLastRoundsOutput()) - (this.start.getOutput() * this.end.getOutput()));
    }
}
