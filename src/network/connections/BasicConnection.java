package network.connections;

import network.Network.Network;
import network.Network.NetworkConfiguration;
import network.neurons.INeuron;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class BasicConnection extends AConnection {

    public BasicConnection(double weight, INeuron start, INeuron end) {
        super(weight, start, end);
    }

    @Override
    public void backpropagate() {
        this.updatedWeight = this.weight + (NetworkConfiguration.learningRate * this.getStart().getOutput() * this.getEnd().getError());
    }
}
