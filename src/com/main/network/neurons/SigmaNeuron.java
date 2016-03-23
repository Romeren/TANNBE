package com.main.network.neurons;

import com.main.network.connections.IConnection;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class SigmaNeuron extends ANeuron {

    public double sigmaFunction(double input){
        return 1/((Math.exp(input * (-1))  +1));
    }

    @Override
    public void feedToActivationFunction() {
        this.output = sigmaFunction(this.getInput());
    }

    @Override
    public void backpropagate() {

        this.error = this.output * (1-this.output) * this.error;

        for (IConnection con : this.backwardsConnections){
            con.backpropagate();
        }
    }
}
