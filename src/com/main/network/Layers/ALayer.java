package com.main.network.Layers;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.neurons.INeuron;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public abstract class ALayer implements ILayer {
    protected ArrayList<INeuron> neurons = new ArrayList<>();

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
    public void addInputToLayer(double[] input) throws InvalidNumberOfInput {
        if(input.length != this.neurons.size()){
            throw new InvalidNumberOfInput();
        }
        for (int i = 0 ; i < neurons.size(); i++){
            neurons.get(i).addInput(input[i]);
        }
    }

    @Override
    public void addNeuron(INeuron neuron) {
        this.neurons.add(neuron);
    }
}
