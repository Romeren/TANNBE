package com.main.network.Network;

import com.main.network.Layers.ILayer;
import com.main.network.Layers.LayerFactory;
import com.main.network.Layers.LayerTypes;
import com.main.network.Layers.SomsLayer;
import com.main.network.RandomUtilz;
import com.main.network.connections.BasicConnection;
import com.main.network.connections.IConnection;
import com.main.network.connections.SomsConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.security.PublicKey;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NetworkFactory {
    private Network network = new Network();
    private ExecutorService pool;

    public NetworkFactory(){}
    public NetworkFactory(int numberOfCPUs){
        pool = Executors.newFixedThreadPool(numberOfCPUs);
    }


    public Network build(){
        return this.network;
    }

    public NetworkFactory addLayer(LayerTypes lType, NeuronTypes nType, int numberOfNEurons){
        if(pool!= null){
            network.addLayer(LayerFactory.createLayerMultithreaded(lType, numberOfNEurons, nType, pool));
        }else {
            network.addLayer(LayerFactory.createLayer(lType, numberOfNEurons, nType));
        }
        if (network.layers.size() > 1 && NetworkConfiguration.isFullyConnected){
            // then connect the new layer with the previouse one
            ILayer previous = network.layers.get(network.layers.size()-2);
            ILayer newLayer = network.layers.get(network.layers.size()-1);
            fullyConnectToLayers(previous, newLayer);
        }
        return this;
    }

    public void fullyConnectToLayers(ILayer first, ILayer secound) {
        if(secound instanceof SomsLayer){
            int i = 0;
            for (INeuron n1 : first.getNeurons()){
                SomsConnection con = new SomsConnection(n1, secound.getNeurons(), i);
                i++;
                for (INeuron n2 : secound.getNeurons()){
                    connectTwoNeurons(n1, n2, con);
                }
            }
        }else {
            for (INeuron n1 : first.getNeurons()) {
                for (INeuron n2 : secound.getNeurons()) {
                    connectTwoNeurons(n1, n2,
                            new BasicConnection(RandomUtilz.getDoubleInRange(NetworkConfiguration.minimumInitializedConnectionWeight,
                                    NetworkConfiguration.maximumInitializedConnectionWeight),
                                    n1, n2));
                }
            }
        }
    }


    private void connectTwoNeurons(INeuron first, INeuron second, IConnection con){
        if(!first.getForwardConnections().contains(con))
            first.getForwardConnections().add(con);
        second.getBackwardsConnections().add(con);
    }
}
