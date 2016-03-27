package com.main.network.Network;

import com.main.network.CommonInterfaces.IBackpropagationable;
import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.ILayer;
import com.sun.deploy.util.ArrayUtil;
import jdk.nashorn.internal.ir.LiteralNode;
import sun.plugin.javascript.navig4.Layer;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by EmilSebastian on 17-03-2016.
 */
public class Network {
//    private ExcecutorService
    protected ArrayList<ILayer> layers = new ArrayList<>();
    protected Network(){

    }

    public void unsupervizedDBTraining(double[][] traningset) throws InvalidNumberOfInput{
        ILayer first = layers.get(0);
        for (int l = 1; l< this.layers.size(); l++) {
            ILayer second = layers.get(l);
            for (int i = 0 ; i < NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations-1; i++) {
                for (int x = 0; x < traningset.length; x++) {
                    unsupervizedDeepBelieveIteration(first, second, traningset[x]);
                }
            }
            for (int x = 0; x < traningset.length; x++) {
               traningset[x] =  unsupervizedDeepBelieveIteration(first, second, traningset[x]);
            }
            first = second;
        }
    }

    private double[] unsupervizedDeepBelieveIteration(ILayer lay0, ILayer lay1, double[] datasource) throws InvalidNumberOfInput{
        lay0.addInputToLayer(datasource); // TODO: implementes in feed backwards so it does not send data to next layer.!

        lay0.feedForward();
        lay0.resetLayer();

        lay1.feedForward(); // TODO: implementes in feed backwards so it does not send data to next layer.!
        lay1.feedBackward();
        lay1.resetLayer();

        lay0.feedForward();
        lay1.feedForward(); // TODO: implementes in feed backwards so it does not send data to next layer.!

        lay1.backpropagateRBM();

        double result[] = lay1.getOutput(); // TODO: implementes in feed backwards so it does not send data to next layer.!
        lay0.resetLayer();
        lay1.resetLayer();// TODO: implementes in feed backwards so it does not send data to next layer.!
        return result;
    }

    public void supervisedTraining(double[][] trainingset, double[][] labels) throws InvalidNumberOfInput{
        // TODO: add stop criteria to the training algorithm so it will stop when no more can be learnt!
        ArrayList<double[]> train = new ArrayList(Arrays.asList(trainingset));
        for (int i = 0 ; i < NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations; i++){
//            Collections.shuffle(train);
            for (int x = 0 ; x < trainingset.length; x++){
                feedInputThroughNetwork(trainingset[x]);
                backpropagateNetwork(labels[x]);
            }
        }
    }

    public void backpropagateNetwork(double[] expectedValues) throws InvalidNumberOfInput {
        ILayer lay = this.layers.get(layers.size() - 1);

        if(!(lay instanceof IBackpropagationable)){
            return;
        }

        ((IBackpropagationable)lay).startBackpropagate(expectedValues);
        for(int i = layers.size()-1; i > 0; i--){
            lay = layers.get(i);
            if(!(lay instanceof IBackpropagationable)) return;
//            System.out.println("BACK  " + i);
            ((IBackpropagationable) lay).backpropagate();
        }
        resetNetwork();
    }

    public double[] feedInputThroughNetwork(double[] input) throws InvalidNumberOfInput{
        addInputToVisibleLayer(input);
        feedForward();
        return getOutputFromOutputLayer();
    }

    public double[] feedInputThroughNetwork(ArrayList<Double> input) throws InvalidNumberOfInput{
        addInputToVisibleLayer(input);
        feedForward();
        return getOutputFromOutputLayer();
    }


    public void addInputToVisibleLayer(ArrayList<Double> input) throws InvalidNumberOfInput {
        layers.get(0).addInputToLayer(input.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public void addInputToVisibleLayer(double[] input) throws InvalidNumberOfInput {
        layers.get(0).addInputToLayer(input);

    }

    public double[] getOutputFromOutputLayer(){
        return layers.get(layers.size()-1).getOutput();
    }

    public void feedForward(){
        for (ILayer layer: layers){
            layer.feedForward();
        }
    }

    public void resetNetwork(){
        for(ILayer layer: this.layers){
            layer.resetLayer();
        }
    }

    public void addLayer(ILayer layer){
        layers.add(layer);
    }

    public ILayer getLayer(int index){
        return this.layers.get(index);
    }

    public int getNumberOfLayers (){
        return this.layers.size();
    }


    private class UnsupervizedRestrictedBolzmanMachineWave implements Runnable{
        private double[] datasource;

        private UnsupervizedRestrictedBolzmanMachineWave(double[] datasource) throws InvalidNumberOfInput{
            if(layers.get(0).getNeurons().size() == datasource.length)
                throw new InvalidNumberOfInput();
            this.datasource = datasource;
        }

        @Override
        public void run() {
            ILayer first = layers.get(0);
            for (int i = 1 ; i < layers.size(); i++){
                ILayer second = layers.get(i);
                this.unsupervizedDeepBelieveIteration(first,second);
                first = second;
            }
        }

        private void unsupervizedDeepBelieveIteration(ILayer lay0, ILayer lay1){
            lay0.feedForward();
            lay0.resetLayer();

            lay1.feedBackward();
            lay1.resetLayer();

            lay0.feedForward();

            lay1.backpropagateRBM();
            lay0.resetLayer();
        }
    }
}
