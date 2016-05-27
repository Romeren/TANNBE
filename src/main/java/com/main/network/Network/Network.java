package com.main.network.Network;

import com.main.network.CommonInterfaces.IBackpropagationable;
import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.ILayer;
import com.main.network.RandomUtilz;

import java.util.*;

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
        shuffleDataSet(trainingset, labels);
        int numberOfOutputsInEpochs = this.getLayer(this.layers.size()-1).getNeurons().size() * (trainingset.length);
        int errorCalcInterval = 1000;
        for (int i = 0 ; i < NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations; i++){
            double error = 0;
            for (int x = 0 ; x < trainingset.length; x++){
                double[] output = feedInputThroughNetwork(trainingset[x]);
                if(i % errorCalcInterval == 0)
                    for(int g = 0; g < output.length; g++){
                        error += (labels[x][g] - output[g]) * (labels[x][g] - output[g]) ;
                    }
                backpropagateNetwork(labels[x]);
            }
            if(i % errorCalcInterval == 0) {
                error = (error / numberOfOutputsInEpochs);
                System.out.println("Epoch: " + i + "\tSquared Error is: " + error);
            }
        }
    }

    //TODO: REMOVE FUNCTION!!!!!
    public double[][] meanSquaredWithTime(double[][] trainingset, double[][] labels) throws InvalidNumberOfInput{
        List<Double> meanerror = new ArrayList<>();
        List<Long> comptime = new ArrayList<>();
        int numberOfOutputsInEpochs = this.getLayer(this.layers.size()-1).getNeurons().size() * (trainingset.length);

        shuffleDataSet(trainingset, labels);
        long startTime = System.currentTimeMillis();
        for (int i = 0 ; i <= NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations; i++){
            double error = 0;
            for (int x = 0 ; x < trainingset.length; x++){
                double[] output = feedInputThroughNetwork(trainingset[x]);
                if(i == 1000 || i == 10000 ||i == 25000)
                    for(int g = 0; g < output.length; g++){
                        error += (labels[x][g] - output[g]) * (labels[x][g] - output[g]) ;
                    }
                backpropagateNetwork(labels[x]);
            }
            if(i == 1000 || i == 10000 ||i == 25000){
                error = (error / numberOfOutputsInEpochs);
                meanerror.add(error);
                comptime.add(System.currentTimeMillis() - startTime);
                System.out.println("Epoch: " + i + "\tSquared Error is: " + error);
            }
        }

        double[][] result = new double[meanerror.size()][2];
        for(int i = 0 ; i < result.length; i++){
            result[i][0] = meanerror.get(i);
            result[i][1] = comptime.get(i);
        }
        return result;
    }

    static void shuffleDataSet(double[][] train, double[][] labels ){
        Random rnd = new Random();
        for(int i = 0; i < train.length; i++){
            int index = rnd.nextInt(train.length);
            double[] tmpTrain = train[index];
            double[] tmpLabel = labels[index];
            train[index] = train[i];
            train[i] = tmpTrain;
            labels[index] = labels[i];
            labels[i] = tmpLabel;
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
