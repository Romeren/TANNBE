package com.test.network.neurons.network;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.connections.BasicConnection;
import com.main.network.connections.IConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.LinearNeuron;
import com.main.network.neurons.Perceptron;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class TrainingBackPropagationTest {

    @Test
    public void perceptronTrainingTest(){
        NetworkConfiguration.learningRate = 0.1;
        INeuron n1 = new Perceptron();
        INeuron n2 = new Perceptron();
        INeuron n3 = new Perceptron();

        IConnection con1 = new BasicConnection(0.01,n1,n3);
        IConnection con2 = new BasicConnection(0.01,n2,n3);
        n1.getForwardConnections().add(con1);
        n2.getForwardConnections().add(con2);
        n3.getBackwardsConnections().add(con1);
        n3.getBackwardsConnections().add(con2);

        for (int i = 0; i < 100; i++){
            n1.addInput(1);
            n2.addInput(0);

            n1.feedForward();
            n2.feedForward();
            n3.feedForward();
            //System.out.println("Weight 1: " + n3.getBackwardsConnections().get(0).getWeight() + "  Weight 2: " + n3.getBackwardsConnections().get(1).getWeight());
            //System.out.println(n3.getOutput());
            n3.startBackpropagate(1);
            n3.backpropagate();

            n1.resetNeuron();
            n2.resetNeuron();
            n3.resetNeuron();

            n1.addInput(0);
            n2.addInput(1);

            n1.feedForward();
            n2.feedForward();
            n3.feedForward();

            n3.startBackpropagate(0);
            n3.backpropagate();

            n1.resetNeuron();
            n2.resetNeuron();
            n3.resetNeuron();
        }

        n1.addInput(1);
        n2.addInput(0);

        n1.feedForward();
        n2.feedForward();
        n3.feedForward();

        Assert.assertEquals(n3.getOutput() >=0.9, true);
    }

    @Test
    public void linearClassificationTrainingTest(){
        // Setup:
        INeuron n1 = new LinearNeuron();
        INeuron n2 = new LinearNeuron();
        INeuron n3 = new Perceptron(); // since this is classification we use a perceptron as output neuron!

        IConnection con1 = new BasicConnection(0.01,n1,n3);
        IConnection con2 = new BasicConnection(0.01,n2,n3);
        n1.getForwardConnections().add(con1);
        n2.getForwardConnections().add(con2);
        n3.getBackwardsConnections().add(con1);
        n3.getBackwardsConnections().add(con2);

        double[][] dist1 = {{1,1},{0.9,0.9},{0.8,0.9},{0.8,0.8},{0.7,0.7},{0.6,0.7}};
        double[][] dist2 = {{1,0},{0.8,0},{0.8,0.1},{0.7,0.2},{0.7,0.1},{0.7,0.0}};
        // setup done

        //training network:
        for(int i = 0; i < 100; i++){
            for(int x = 0 ; x < 6 ; x++){
                for(int y = 0; y < 2; y ++){
                    n1.addInput(y == 0 ? dist1[x][0] : dist2[x][0]);
                    n2.addInput(y == 0 ? dist1[x][1] : dist2[x][1]);

                    n1.feedForward();
                    n2.feedForward();
                    n3.feedForward();

                    //System.out.println(n3.getOutput() + " Expected: " + (y == 0 ? 0 : 1));
                    n3.startBackpropagate(y == 0 ? 0 : 1);
                    n3.backpropagate();

                    n1.resetNeuron();
                    n2.resetNeuron();
                    n3.resetNeuron();
                }
            }
        }

        for(int x = 0 ; x < 6 ; x++){
            for(int y = 0; y < 2; y ++){
                n1.addInput(y == 0 ? dist1[x][0] : dist2[x][0]);
                n2.addInput(y == 0 ? dist1[x][1] : dist2[x][1]);

                n1.feedForward();
                n2.feedForward();
                n3.feedForward();

                Assert.assertEquals(n3.getOutput() == (y == 0 ? 0: 1), true);

                n1.resetNeuron();
                n2.resetNeuron();
                n3.resetNeuron();
            }
        }
    }
}
