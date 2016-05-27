package com.main.network.GeneticAlgorithm;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Layers.LayerTypes;
import com.main.network.Network.Network;
import com.main.network.Network.NetworkConfiguration;
import com.main.network.Network.NetworkFactory;
import com.main.network.neurons.INeuron;
import com.main.network.neurons.NeuronTypes;

/**
 * Created by EmilSebastian on 30-03-2016.
 */
public class GeneticAlgorithm {
    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.05;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    /* Public methods */
    public  Individual bestMatch;

    // Evolve a population
    private Population evolvePopulation(Population pop) throws InvalidNumberOfInput {
        Population newPopulation = new Population(pop.size());

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual(indiv1.getNetwork());
        newSol.generateIndividual();
        // Loop through genes
        for (int i = 0; i < indiv1.size(); i++) {
            // Crossover
            if (Math.random() <= uniformRate) {
                newSol.setGene(i, indiv1.getGene(i));
            } else {
                newSol.setGene(i, indiv2.getGene(i));
            }
        }
        return newSol;
    }

    // Mutate an individual
    private void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                byte gene = (byte) Math.round(Math.random());
                indiv.setGene(i, gene);
            }
        }
    }

    // Select individuals for crossover
    private Individual tournamentSelection(Population pop) throws InvalidNumberOfInput {
        // Create a tournament population
        Population tournament = new Population(tournamentSize);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }

    public GeneticAlgorithm(int populationSize, Network net, double[][] traininset, double[][] trainingLabels, int maxIterations) throws InvalidNumberOfInput {
        int counter = 0;
        for(int i = 1; i < net.getNumberOfLayers(); i++){
            for(INeuron n : net.getLayer(i).getNeurons()){
                counter += n.getBackwardsConnections().size();
            }
        }
        // Configure gene length
        Individual.setDefaultGeneLength(counter);

        // Create an initial population
        Population myPop = new Population(populationSize, net);

        int generationCount = 0;
        FitnessCalculator.setSolutions(trainingLabels);
        FitnessCalculator.setSamples(traininset);
        while (myPop.getFittest().getFitness() != 0 && generationCount < maxIterations ) {
            // Set a candidate solution
            generationCount++;

            System.out.println("------------------------------------------");
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = evolvePopulation(myPop);
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        this.bestMatch = myPop.getFittest();
    }


    public static void main(String[] args){
        NetworkConfiguration.maximumNumberOfSupervizedTrainingIterations =2000;
        NetworkConfiguration.learningRate= 0.01;
        NetworkConfiguration.minimumInitializedLinearBias = -5;
        NetworkConfiguration.maximumInitializedLinearBias = 5;
        NetworkConfiguration.minimumInitializedConnectionWeight = 0.6;
        NetworkConfiguration.maximumInitializedConnectionWeight =1;

        Network net = new NetworkFactory().addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 1)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 4)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.SIGMA, 5)
                .addLayer(LayerTypes.ONE_DIMENTIONAL, NeuronTypes.LINEAR, 1).build();

        double[][] trainingset = new double[300][1];
        double[][] testset = new double[300][1];
        int counter = 0;
        for(double i = -1; counter < trainingset.length; i+=0.01){
            trainingset[counter][0] = i;
            testset[counter][0] =  Math.sin(2 * Math.PI * i) + Math.sin(5 * Math.PI * i) ;
            counter++;
        }

        // Evolve our population until we reach an optimum solution
        try {
            GeneticAlgorithm genetic = new GeneticAlgorithm(50,net, trainingset, testset, 100);
           //net = genetic.bestMatch.getNetwork();
        } catch (InvalidNumberOfInput invalidNumberOfInput) {
            invalidNumberOfInput.printStackTrace();
        }
    }
}
