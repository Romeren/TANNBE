package com.test.network.neurons;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.neurons.Perceptron;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class PerceptronTest {


    @Test
    public void feedToActivationFunctionTest(){

        NetworkConfiguration.minimumInitializedPerceptronThredshold = 0.5;
        NetworkConfiguration.maximumInitializedPerceptronThredshold = 0.6;
        Perceptron p = new Perceptron();

        Assert.assertEquals(p.getOutput() == 0, true);
        p.addInput(1);
        Assert.assertEquals(p.getOutput() == 0, true);
        p.feedToActivationFunction();
        Assert.assertEquals(p.getOutput() == 1, true);

        p.resetNeuron();
        Assert.assertEquals(p.getOutput() == 0, true);

        p.addInput(0.7);
        p.feedToActivationFunction();
        Assert.assertEquals(p.getOutput() == 1, true);

        p.resetNeuron();
        p.addInput(3);
        p.feedToActivationFunction();
        Assert.assertEquals(p.getOutput() == 3, true);
    }

}
