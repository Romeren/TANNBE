package network.neurons;

import network.Network.NetworkConfiguration;
import network.RandomUtilz;
import network.connections.IConnection;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class RBMNeuron extends SigmaNeuron implements IBackwardsFeed {
    private double bias = RandomUtilz.getDoubleInRange(NetworkConfiguration.minimumRBMBias, NetworkConfiguration.maximumRBMBias);

    @Override
    public void feedBackwards() {
        this.feedToActivationFunction();
        for(IConnection con : this.backwardsConnections){
            double randomThredshold = RandomUtilz.getDoubleInRange(
                    NetworkConfiguration.minimumThredsholdRBMBackpropagate, NetworkConfiguration.maximumThredsholdRBMBackpropagate);
            if(this.getOutput() > randomThredshold){
                con.feedBackwards(1);
            }else {
                con.feedBackwards(0);
            }
        }
    }

    @Override
    public void backpropagate() {
        this.addInput(this.bias);
        super.backpropagate();
    }

    @Override
    public void unsupervizedRBM() {

    }
}
