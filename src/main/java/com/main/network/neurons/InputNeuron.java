package com.main.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.RandomUtilz;
import com.main.network.connections.IConnection;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public class InputNeuron extends ANeuron {
    public InputNeuron() {
        super();
    }

    @Override
    public void feedToActivationFunction() {
        this.output = getInput();
    }

    @Override
    public void backpropagate() {
    }
}
