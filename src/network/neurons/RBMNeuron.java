package network.neurons;

import network.Network.NetworkConfiguration;
import network.RandomUtilz;
import network.connections.IConnection;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class RBMNeuron extends SigmaNeuron implements IBackwardsFeed {
    private double bias = RandomUtilz.getDoubleInRange(NetworkConfiguration.minimumRBMBias, NetworkConfiguration.maximumRBMBias);
    private int lastRoundOutput;

    @Override
    public double getLastRoundsOutput() {
        return this.lastRoundOutput;
    }

    @Override
    public void feedBackwards() {
        this.feedToActivationFunction();
        for(IConnection con : this.backwardsConnections){
            // get a random thredshold and check if the output is above or below thredshold:
            double randomThredshold = RandomUtilz.getDoubleInRange(
                    NetworkConfiguration.minimumThredsholdRBMBackpropagate, NetworkConfiguration.maximumThredsholdRBMBackpropagate);
            if(this.getOutput() > randomThredshold){
                con.feedBackwards(1);
            }else {
                con.feedBackwards(0);
            }
        }
        this.lastRoundOutput = this.getOutput() <= 0.5 ? 0 : 1;
    }

    @Override
    public void feedToActivationFunction() {
        this.addInput(this.bias); // add bias to input
        super.feedToActivationFunction();
    }

    @Override
    public void unsupervizedRBM() {
        // Update bias:
        this.bias += NetworkConfiguration.learningRate * (this.lastRoundOutput - this.getOutput());
        for(IConnection con : this.backwardsConnections){
            con.unsupervizedRBM();
        }
    }
}
