package com.test.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.LinearNeuron;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class LinearNeuronTest {

    @Test
    public void feedToActivationFunctionTest(){
        NetworkConfiguration.minimumInitializedLinearBias = 0.5;
        NetworkConfiguration.maximumInitializedLinearBias = 0.5;
        LinearNeuron n = new LinearNeuron();

        Assert.assertEquals(n.getOutput() == 0, true);
        n.addInput(0.5);
        Assert.assertEquals(n.getOutput() == 0, true);

        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() == 1, true);

        n.resetNeuron();
        Assert.assertEquals(n.getOutput() == 0, true);

        n.addInput(0.7);
        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() == 1.2, true);

        n.resetNeuron();
        n.addInput(3);
        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() == 3.5, true);
        n.addInput(0.5);
        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() == 4, true);
    }
}
