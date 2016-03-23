package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.connections.IConnection;
import com.main.network.connections.SomsConnection;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.ISOMsNode;
import com.main.network.neurons.NeuronTypes;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class SomsLayer extends ALayer {
    public SomsLayer(){
        super();
    }

    @Override
    public void feedForward() {
        if(this.pool != null) {
            For(neurons.get(0).getBackwardsConnections(),pParameter -> ((SomsConnection)pParameter).adjustWeights());
        }else{
            for (IConnection con : this.getNeurons().get(0).getBackwardsConnections()) {
                ((SomsConnection) con).adjustWeights();
            }
        }
        super.feedForward();
    }

    @Override
    public void addInputToLayer(double[] input) throws InvalidNumberOfInput {
        // you cannot add input to this layer it does not make sense!
        throw new InvalidNumberOfInput();
    }
}
