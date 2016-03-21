package network.Layers;

import network.neurons.INeuron;
import network.neurons.NeuronTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public interface ILayer {
    NeuronTypes getNeuronType();
    void setNeuronType(NeuronTypes type);
    ArrayList<INeuron> getNeurons();
    void feedForward();
    ArrayList<Double> getOutput();
    void resetLayer();
    void addInputToLayer(ArrayList<Double> input);
    void backpropagate();
    void addNeuron(INeuron neuron);
}
