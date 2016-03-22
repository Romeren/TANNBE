package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public interface ILayer {
    ArrayList<INeuron> getNeurons();
    void feedForward();
    double[] getOutput();
    void resetLayer();
    void addInputToLayer(double[] input)  throws InvalidNumberOfInput;
    void addNeuron(INeuron neuron);
}
