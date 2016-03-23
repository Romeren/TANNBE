package com.main.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.RandomUtilz;
import com.main.network.connections.IConnection;

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
//        System.out.println("--------BACK------------");
//        System.out.println(this.getBackwardsConnections().size());
//        System.out.println("Output: " + this.getOutput() + "   ERROR: " + this.error );
        for (IConnection con : this.backwardsConnections){
            con.backpropagate();
        }
    }
}
