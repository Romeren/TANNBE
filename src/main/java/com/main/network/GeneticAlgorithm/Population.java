package com.main.network.GeneticAlgorithm;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Network.Network;

/**
 * Created by EmilSebastian on 30-03-2016.
 */
public class Population {
    Individual[] individuals;

    /*
 * Constructors
 */
    // Create a population
    public Population(int populationSize, Network net) {
        individuals = new Individual[populationSize];
        // Initialise population
        // Loop and create individuals
        for (int i = 0; i < size(); i++) {
            Individual newIndividual = new Individual(net);
            newIndividual.generateIndividual();
            saveIndividual(i, newIndividual);
        }
    }
    public Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() throws InvalidNumberOfInput {
        Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() >= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        //System.out.println(fittest.getFitness());
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
