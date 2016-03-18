package network.neurons;

import network.connections.IConnection;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class RBMNeuron extends SigmaNeuron implements IBackwardsFeed {

    @Override
    public void feedBackwards() {
        this.feedToActivationFunction();
        for(IConnection con : this.backwardsConnections){
            con.feedBackwards(this.getOutput());
        }
    }
}
