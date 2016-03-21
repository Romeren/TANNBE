package network.neurons;

import network.connections.IConnection;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class SigmaNeuron extends ANeuron {

    public double sigmaFunction(double input){
        return 1/(Math.exp(getInput() * (-1))+1);
    }

    @Override
    public void feedToActivationFunction() {
        this.output = sigmaFunction(this.getInput());
    }

    @Override
    public void backpropagate() {
        this.error = this.getOutput() * (1-this.getOutput()) * this.error;
        for (IConnection con : this.backwardsConnections){
            con.backpropagate();
        }
    }
}
