package com.main.network.connections;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.ANeuron;
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

        this.updatedWeight = this.weight + (NetworkConfiguration.learningRate * this.end.getError() * this.start.getOutput());
//        System.out.println("WEIGHT: " + this.weight + "   UPDATETED: " + this.updatedWeight);
        this.start.addBackpropagationError(this.end.getError() * this.getWeight());
    }
}
