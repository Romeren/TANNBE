package com.main.network.GeneticAlgorithm;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.RandomUtilz;
import com.main.network.connections.IConnection;
import com.thoughtworks.xstream.XStream;

/**
 * Created by EmilSebastian on 30-03-2016.
 */
public class Individual {
    private Network net;

    static int defaultGeneLength = 64;
    private IConnection[] genes = new IConnection[defaultGeneLength];
    // Cache
    private double fitness = 0;

    public Individual(Network net){
        XStream stream = new XStream();
        this.net = (Network)stream.fromXML(stream.toXML(net));
    }

    public Network getNetwork(){
        return net;
    }

    // Create a random individual
    public void generateIndividual() {
        int counter = 0;
        for (int i = 0 ; i < net.getNumberOfLayers(); i++){
            for (int n = 0 ; n < net.getLayer(i).getNeurons().size(); n++){
                for(int con = 0 ; con < net.getLayer(i).getNeurons().get(n).getBackwardsConnections().size(); con++) {
                    genes[counter] = net.getLayer(i).getNeurons().get(n).getBackwardsConnections().get(con);
                    genes[counter].setWeight(RandomUtilz.getDoubleInRange(NetworkConfiguration.gaMinimumConWeight, NetworkConfiguration.gaMaximumConWeight));
                    counter++;
                }
            }
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    public double getGene(int index) {
        return genes[index].getWeight();
    }

    public void setGene(int index, double value) {
        genes[index].setWeight(value);
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public double getFitness() throws InvalidNumberOfInput {
        if (fitness == 0) {
            fitness = FitnessCalculator.getFitness(this);
        }
        return fitness;
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
