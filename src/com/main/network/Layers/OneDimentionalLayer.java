package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class OneDimentionalLayer implements ILayer{
    private ArrayList<INeuron> neurons = new ArrayList<>();
    private NeuronTypes neuronTypes;

    public OneDimentionalLayer(){

    }

    @Override
    public NeuronTypes getNeuronType() {
        return this.neuronTypes;
    }

    @Override
    public void setNeuronType(NeuronTypes type) {
        this.neuronTypes = type;
    }

    @Override
    public ArrayList<INeuron> getNeurons() {
        return this.neurons;
    }

    @Override
    public void feedForward() {
        for(INeuron n : this.neurons){
            n.feedForward();
        }
    }

    @Override
    public double[] getOutput() {
        double[] output = new double[this.neurons.size()];
        for (int i = 0; i < neurons.size(); i++){
            output[i] = neurons.get(i).getOutput();
        }
        return output;
    }

    @Override
    public void resetLayer() {
        for (INeuron n : neurons){
            n.resetNeuron();
        }
    }

    @Override
    public void addInputToLayer(double[] input) throws InvalidNumberOfInput{
        if(input.length != this.neurons.size()){
            throw new InvalidNumberOfInput();
        }
        for (int i = 0 ; i < neurons.size(); i++){
            neurons.get(i).addInput(input[i]);
        }
    }

    @Override
    public void backpropagate() {
        for (INeuron n : neurons){
            n.backpropagate();
        }
    }

    @Override
    public void startBackpropagate(double[] expectedValues) throws InvalidNumberOfInput {
        if(expectedValues.length != neurons.size()){
            throw new InvalidNumberOfInput();
        }
        for (int i = 0; i < neurons.size(); i++){
            neurons.get(i).startBackpropagate(expectedValues[i]);
        }
    }

    @Override
    public void addNeuron(INeuron neuron) {
        this.neurons.add(neuron);
    }
}
