package com.main.network.GeneticAlgorithm;

import com.main.network.Network.NetworkConfiguration;
import com.main.network.RandomUtilz;

/**
 * Created by EmilSebastian on 30-03-2016.
 */
public class Individual {

    static int defaultGeneLength = 64;
    private byte[] genes = new byte[defaultGeneLength];
    // Cache
    private int fitness = 0;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) RandomUtilz.getDoubleInRange(NetworkConfiguration.maximumInitializedConnectionWeight,NetworkConfiguration.maximumInitializedConnectionWeight);
            genes[i] = gene;
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }

    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }

    public int getFitness() {
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
