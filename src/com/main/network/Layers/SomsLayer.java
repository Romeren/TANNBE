package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public class SomsLayer extends ALayer {
    private ArrayList<INeuron> neurons = new ArrayList<>();
    private int layerIndex;

    public SomsLayer(int layerIndex){
        super();
        this.layerIndex=layerIndex;
    }

    @Override
    public void addInputToLayer(double[] input) throws InvalidNumberOfInput {
        // you cannot add input to this layer it does not make sense!
        throw new InvalidNumberOfInput();
    }
}
