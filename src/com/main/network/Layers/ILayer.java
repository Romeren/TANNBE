package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public interface ILayer {
    NeuronTypes getNeuronType();
    void setNeuronType(NeuronTypes type);
    ArrayList<INeuron> getNeurons();
    void feedForward();
    double[] getOutput();
    void resetLayer();
    void addInputToLayer(double[] input)  throws InvalidNumberOfInput;
    void backpropagate();
    void startBackpropagate(double[] expectedValues) throws InvalidNumberOfInput;
    void addNeuron(INeuron neuron);
}
