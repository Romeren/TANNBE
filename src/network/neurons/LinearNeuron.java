package network.neurons;

import network.Network.NetworkConfiguration;
import network.RandomUtilz;
import network.connections.IConnection;

import java.util.Random;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class LinearNeuron extends ANeuron {
    private  final double bias = RandomUtilz.getDoubleInRange(NetworkConfiguration.minimumInitializedLinearBias, NetworkConfiguration.maximumInitializedLinearBias);

    public LinearNeuron() {
        super();
    }

    @Override
    public void feedToActivationFunction() {
        this.output = getInput() + bias;
    }

    @Override
    public void backpropagate() {
        for (IConnection con : this.backwardsConnections){
            con.backpropagate();
        }
    }
}
