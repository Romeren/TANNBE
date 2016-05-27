package com.test.network.neurons.network;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.connections.BasicConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.LinearNeuron;
import com.main.network.neurons.Perceptron;
import com.main.network.neurons.SigmaNeuron;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NetworkConceptTest {

    @Test
    public void simpleTest(){
        NetworkConfiguration.maximumInitializedLinearBias =0.2;
        NetworkConfiguration.minimumInitializedLinearBias = 0.1;

        INeuron n1 = null;
        INeuron n2 = null;
        for(int i = 0; i < 3 ; i++) {
            if(i == 0) {
         //       System.out.println("Perceptron");
                n1 = new Perceptron();
                n2 = new Perceptron();
            }else if(i ==1){
           //     System.out.println("Linear neuron");
                n1 = new LinearNeuron();
                n2 = new LinearNeuron();
            }
            else if(i ==2){
             //   System.out.println("Sigma Neuron");
                n1 = new SigmaNeuron();
                n2 = new SigmaNeuron();
            }

            BasicConnection con = new BasicConnection(1, n1, n2);
            n1.getForwardConnections().add(con);
            n2.getBackwardsConnections().add(con);

            Assert.assertEquals(n1.getOutput() == 0, true);
            Assert.assertEquals(n2.getOutput() == 0, true);

            n1.addInput(1);
            n1.feedForward();
            n2.feedForward();
            Assert.assertEquals(n1.getOutput() >= 0.7, true);
            Assert.assertEquals(n2.getOutput() >= 0.6, true);

            n1.resetNeuron();
            n2.resetNeuron();
            n1.addInput(0.1);
            n1.feedForward();
            n2.feedForward();
            Assert.assertEquals(n1.getOutput() >= 0.7, false);
            Assert.assertEquals(n2.getOutput() >= 0.7, false);
        }
    }

    @Test
    public void twoToOneNeuronTest(){
        NetworkConfiguration.maximumInitializedLinearBias =0.2;
        NetworkConfiguration.minimumInitializedLinearBias = 0.1;

        INeuron n1 = null;
        INeuron n2 = null;
        INeuron n3 = null;
        for(int i = 0; i < 3 ; i++) {
            if(i == 0) {
                //System.out.println("Perceptron");
                n1 = new Perceptron();
                n2 = new Perceptron();
                n3 = new Perceptron();
            }else if(i ==1){
                //System.out.println("Linear neuron");
                n1 = new LinearNeuron();
                n2 = new LinearNeuron();
                n3 = new LinearNeuron();
            }
            else if(i ==2){
                //System.out.println("Sigma Neuron");
                n1 = new SigmaNeuron();
                n2 = new SigmaNeuron();
                n3 = new SigmaNeuron();
            }

            BasicConnection con1 = new BasicConnection(1, n1, n3);
            BasicConnection con2 = new BasicConnection(1, n2, n3);
            n1.getForwardConnections().add(con1);
            n2.getForwardConnections().add(con2);

            n3.getBackwardsConnections().add(con1);
            n3.getBackwardsConnections().add(con2);

            Assert.assertEquals(n1.getOutput() == 0, true);
            Assert.assertEquals(n2.getOutput() == 0, true);
            Assert.assertEquals(n3.getOutput() == 0, true);

            n1.addInput(1);
            n1.feedForward();
            n2.feedForward();
            n3.feedForward();
            Assert.assertEquals(n1.getOutput() >= 0.7, true);
            Assert.assertEquals(n3.getOutput() >= 0.6, true);
            Assert.assertEquals(n2.getOutput() <= 0.5, true);

            n1.resetNeuron();
            n2.resetNeuron();
            n3.resetNeuron();

            n1.addInput(0.1);
            n1.feedForward();
            n2.feedForward();
            n3.resetNeuron();
            Assert.assertEquals(n1.getOutput() >= 0.7, false);
            Assert.assertEquals(n3.getOutput() >= 0.7, false);
        }
    }

    @Test
    public void oneToTwoNeuronTest(){
        NetworkConfiguration.maximumInitializedLinearBias =0.2;
        NetworkConfiguration.minimumInitializedLinearBias = 0.1;

        INeuron n1 = null;
        INeuron n2 = null;
        INeuron n3 = null;
        for(int i = 0; i < 3 ; i++) {
            if(i == 0) {
               // System.out.println("Perceptron");
                n1 = new Perceptron();
                n2 = new Perceptron();
                n3 = new Perceptron();
            }else if(i ==1){
               // System.out.println("Linear neuron");
                n1 = new LinearNeuron();
                n2 = new LinearNeuron();
                n3 = new LinearNeuron();
            }
            else if(i ==2){
               //W System.out.println("Sigma Neuron");
                n1 = new SigmaNeuron();
                n2 = new SigmaNeuron();
                n3 = new SigmaNeuron();
            }

            BasicConnection con1 = new BasicConnection(1, n1, n2);
            BasicConnection con2 = new BasicConnection(1, n1, n3);
            n1.getForwardConnections().add(con1);
            n1.getForwardConnections().add(con2);

            n2.getBackwardsConnections().add(con1);
            n3.getBackwardsConnections().add(con2);

            Assert.assertEquals(n1.getOutput() == 0, true);
            Assert.assertEquals(n2.getOutput() == 0, true);
            Assert.assertEquals(n3.getOutput() == 0, true);

            n1.addInput(1);
            n1.feedForward();
            n2.feedForward();
            n3.feedForward();
            Assert.assertEquals(n1.getOutput() >= 0.7, true);
            Assert.assertEquals(n2.getOutput() >= 0.6, true);
            Assert.assertEquals(n3.getOutput() >= 0.6, true);

        }
    }
}
