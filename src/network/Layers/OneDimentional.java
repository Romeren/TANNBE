package network.Layers;

import network.neurons.INeuron;

import java.util.ArrayList;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class OneDimentional implements ILayer{
    private ArrayList<INeuron> neurons = new ArrayList<>();

    public OneDimentional(){

    }

    @Override
    public void feedForward() {
        for(INeuron n : this.neurons){
            n.feedForward();
        }
    }

    @Override
    public ArrayList<Double> getOutput() {
        ArrayList<Double> r = new ArrayList<>();
        for (INeuron n : neurons){
            r.add(n.getOutput());
        }
        return r;
    }

    @Override
    public void resetLayer() {
        for (INeuron n : neurons){
            n.resetNeuron();
        }
    }

    @Override
    public void addInputToLayer(ArrayList<Double> input) {
        for (int i = 0 ; 0 < neurons.size(); i++){
            neurons.get(i).addInput(input.get(i));
        }
    }

    @Override
    public void backpropagate() {
        for (INeuron n : neurons){
            n.backpropagate();
        }
    }
}
