package com.main.network.Network;

import com.main.network.Layers.ILayer;
import com.main.network.Layers.LayerFactory;
import com.main.network.Layers.LayerTypes;
import com.main.network.RandomUtilz;
import com.main.network.connections.BasicConnection;
import com.main.network.connections.IConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.util.Random;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NetworkFactory {

    public Network addLayer(Network network, LayerTypes lType, NeuronTypes nType, int numberOfNEurons){
        network.addLayer(LayerFactory.createLayer(lType, numberOfNEurons, nType));
        if (network.layers.size() > 1 && NetworkConfiguration.isFullyConnected){
            // then connect the new layer with the previouse one
            ILayer previous = network.layers.get(network.layers.size()-2);
            ILayer newLayer = network.layers.get(network.layers.size()-1);
            fullyConnectToLayers(previous, newLayer);
        }
        return network;
    }

    public Network createLayer(){
        return new Network();
    }

    public void fullyConnectToLayers(ILayer first, ILayer secound){
        Random random = new Random();
        NeuronTypes nt1 = first.getNeuronType();
        NeuronTypes nt2 = secound.getNeuronType();
        // if Self-Organizing-Map (SOMS) Or Convolutional neural network CNN we need to do some other thing:
        if((nt1 == NeuronTypes.PERCEPTRON || nt1 == NeuronTypes.LINEAR || nt1 == NeuronTypes.SIGMA || nt1 == NeuronTypes.RBM) &&
                (nt2 == NeuronTypes.PERCEPTRON || nt2 == NeuronTypes.LINEAR || nt2 == NeuronTypes.SIGMA || nt2 == NeuronTypes.RBM)){
            for (INeuron n1 : first.getNeurons()){
                for (INeuron n2 : secound.getNeurons()){
                    connectTwoNeurons(n1, n2,
                            new BasicConnection(RandomUtilz.getDoubleInRange(NetworkConfiguration.minimumInitializedConnectionWeight,
                                    NetworkConfiguration.maximumInitializedConnectionWeight),
                            n1, n2));
                }
            }
        }
    }

    private void connectTwoNeurons(INeuron first, INeuron second, IConnection con){
        first.getForwardConnections().add(con);
        second.getBackwardsConnections().add(con);
    }
}
