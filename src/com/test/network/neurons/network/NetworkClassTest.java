package com.test.network.neurons.network;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.*;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NetworkClassTest {

    @Test
    public void networkStructureTest(){
        Class[] classes = {Perceptron.class, LinearNeuron.class, SigmaNeuron.class, RBMNeuron.class};
        Enum[] types = {NeuronTypes.PERCEPTRON, NeuronTypes.LINEAR, NeuronTypes.SIGMA, NeuronTypes.RBM};
        for(int i = 0; i < 4 ; i++) {

            Network net = new NetworkFactory()
                    .addLayer(LayerTypes.ONE_DIMENTIONAL, (NeuronTypes) types[i], 2)
                    .addLayer(LayerTypes.ONE_DIMENTIONAL, (NeuronTypes) types[i], 1)
                    .build();

            // check that the correct number of layers and neurons where added:
            Assert.assertEquals(net.getLayer(0).getNeurons().size() == 2, true);
            Assert.assertEquals(net.getLayer(1).getNeurons().size() == 1, true);

            //check that the type of neurons added where correct:
            Assert.assertEquals(net.getLayer(0).getNeurons().get(0).getClass() == classes[i], true);

            // check that the created connection are correct:
            Assert.assertEquals(net.getLayer(0).getNeurons().get(0).getForwardConnections().size() == 1, true);

            Assert.assertEquals(net.getLayer(0).getNeurons().get(0).getForwardConnections().get(0).getEnd() != null, true);
            Assert.assertEquals(net.getLayer(0).getNeurons().get(1).getForwardConnections().get(0).getEnd() != null, true);

            Assert.assertEquals(
                    net.getLayer(0).getNeurons().get(0).getForwardConnections().get(0).getEnd() ==
                    net.getLayer(0).getNeurons().get(1).getForwardConnections().get(0).getEnd(), true);

            // check backwards connections:
            Assert.assertEquals(net.getLayer(1).getNeurons().get(0).getBackwardsConnections().size() == 2, true);

            Assert.assertEquals(net.getLayer(1).getNeurons().get(0).getBackwardsConnections().get(0).getStart() != null, true);
            Assert.assertEquals(net.getLayer(1).getNeurons().get(0).getBackwardsConnections().get(1).getStart() != null, true);
            Assert.assertEquals(
                    net.getLayer(1).getNeurons().get(0).getBackwardsConnections().get(0).getStart() !=
                    net.getLayer(1).getNeurons().get(0).getBackwardsConnections().get(1).getStart(), true);
        }
    }

    @Test
    public void perceptronTrainingTest(){
        NetworkConfiguration.learningRate = 0.1;
        Network net = new NetworkFactory()
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.PERCEPTRON, 2)
                .addLayer(LayerTypes.ONE_DIMENTIONAL,NeuronTypes.PERCEPTRON,1)
                .build();
        try {
            for (int i = 0 ; i < 100 ; i++) {
                net.feedInputThroughNetwork(new double[]{0, 1});
                net.backpropagateNetwork(new double[]{1});

                net.feedInputThroughNetwork(new double[]{1, 0});
                net.backpropagateNetwork(new double[]{0});
            }

            Assert.assertEquals(net.feedInputThroughNetwork(new double[]{0,1})[0] == 1, true);
            net.resetNetwork();
            Assert.assertEquals(net.feedInputThroughNetwork(new double[]{1,0})[0] == 0, true);
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void linearClassificationTrainingTest(){
        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL,NeuronTypes.LINEAR,2)
                .addLayer(LayerTypes.ONE_DIMENTIONAL,NeuronTypes.PERCEPTRON,1)
                .build();
        double[][] dist1 = {{1,1},{0.9,0.9},{0.8,0.9},{0.8,0.8},{0.7,0.7},{0.6,0.7}};
        double[][] dist2 = {{1,0},{0.8,0},{0.8,0.1},{0.7,0.2},{0.7,0.1},{0.7,0.0}};
        try {
            for(int i = 0 ; i < 100 ; i++) {
                for (int x = 0; x < 6; x++) {
                    for (int y = 0; y < 2; y++) {
                        net.feedInputThroughNetwork(y == 0 ? dist1[x] : dist2[x]);
                        net.backpropagateNetwork(new double[]{y});
                    }
                }
            }
            for (int x = 0; x < 6; x++) {
                for (int y = 0; y < 2; y++) {
                    Assert.assertEquals(net.feedInputThroughNetwork(y == 0 ? dist1[x] : dist2[x])[0] == y, true);
                    net.resetNetwork();
                }
            }
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
            Assert.fail();
        }

    }
}
