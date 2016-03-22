package com.main.network.connections;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.INeuron;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class BasicConnection extends AConnection {

    public BasicConnection(double weight, INeuron start, INeuron end) {
        super(weight, start, end);
    }

    @Override
    public void backpropagate() {
        this.updatedWeight = this.weight + ((NetworkConfiguration.learningRate * this.getEnd().getError()) *this.getStart().getOutput());
        this.getStart().addBackpropagationError(this.getEnd().getError() * this.getWeight());
    }
}
