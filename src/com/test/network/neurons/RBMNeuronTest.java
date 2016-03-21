package com.test.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.RBMNeuron;
import com.main.network.neurons.SigmaNeuron;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class RBMNeuronTest {

    @Test
    public void feedToActivationFunctionTest(){
        RBMNeuron n = new RBMNeuron();

        Assert.assertEquals(n.getOutput() == 0, true);
        n.addInput(0.5);
        Assert.assertEquals(n.getOutput() == 0, true);

        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() != 1, true);

        n.resetNeuron();
        Assert.assertEquals(n.getOutput() == 0, true);

        n.addInput(0.7);
        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() >= 0, true);

        n.resetNeuron();
        n.addInput(3);
        n.feedToActivationFunction();
        Assert.assertEquals(n.getOutput() >= 0.5, true);
    }

    @Test
    public void feedBackwards(){
        RBMNeuron n = new RBMNeuron();

        Assert.assertEquals(n.getLastRoundsOutput() == 0, true);
        n.addInput(2);
        Assert.assertEquals(n.getLastRoundsOutput() == 0, true);
        n.feedBackwards();
        Assert.assertEquals(n.getLastRoundsOutput() != 0, true);
    }

    @Test
    public void biasChangeTest(){
        RBMNeuron n = new RBMNeuron();
        n.addInput(0);
        n.feedBackwards();
        n.addInput(5);
        n.feedToActivationFunction();

        double before = n.getBias();
        n.unsupervizedRBM();
        Assert.assertEquals(before != n.getBias(), true);

    }
}
