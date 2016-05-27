package com.test.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.SigmaNeuron;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class SigmaNeuronTest {

    @Test
    public void feedToActivationFunctionTest(){
        SigmaNeuron n = new SigmaNeuron();

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
    public void sigmafunctionTest(){
        SigmaNeuron sn = new SigmaNeuron();

        for(double i = -10 ; i < 10 ; i += 1) {
            System.out.println(sn.sigmaFunction(i));
        }
    }
}
