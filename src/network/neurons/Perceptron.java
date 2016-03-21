package network.neurons;

import network.Network.NetworkConfiguration;
import network.RandomUtilz;
import network.connections.IConnection;

import java.util.Random;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class Perceptron extends ANeuron {
    private final double THREDSHOLD = RandomUtilz.getDoubleInRange(NetworkConfiguration.minimumInitializedPerceptronThredshold, NetworkConfiguration.maximumInitializedPerceptronThredshold);

    public Perceptron(){
        super();
    }

    @Override
    public void feedToActivationFunction() {
        double tmp = this.getInput();
        this.output = 0;
        while (tmp > this.THREDSHOLD){
            this.output++;
            tmp--;
        }
    }

    @Override
    public void backpropagate() {
        for (IConnection con : this.backwardsConnections){
            con.backpropagate();
        }
    }
}
