package com.main.network.GeneticAlgorithm;

import com.main.network.Exceptions.InvalidNumberOfInput;
import com.main.network.Network.Network;

import java.nio.ByteBuffer;

/**
 * Created by EmilSebastian on 30-03-2016.
 */
public class FitnessCalculator {
    static double[][] solutions;
    static double[][] samples;

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolutions(double[][] newSolution) {
        solutions = newSolution;
    }
    public static void setSamples(double[][] newSample){samples = newSample;}

    // To make it easier we can use this method to set our candidate solution
    // with string of 0s and 1s
    /*
    static void setSolution(double[] newSolution) {
        solution = new double[newSolution.length * 8];
        // Loop through each character of our string and save it in our byte
        // array
        for (int i = 0; i < newSolution.length; i++) {
            ByteBuffer.wrap(solution).putDouble(i*8,newSolution[i]);
            /*String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }
*/

    // Calculate inidividuals fittness by comparing it to our candidate solution
    static double getFitness(Individual individual) throws InvalidNumberOfInput {
        double fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates
        for(int x = 0 ; x < solutions.length; x++){
            double[] solution = solutions[x];
            double[] sample = samples[x];
            double[] output = individual.getNetwork().feedInputThroughNetwork(sample);
            individual.getNetwork().resetNetwork();
            for(int i = 0 ; i < output.length && i < solution.length; i++){
                fitness += (output[i] - solution[i]) * (output[i] - solution[i]);
            }
        }
//        System.out.println("Output: " +output[0]);


        /*
        for (int i = 0; i < solution.length; i++) {
            byte[] bytes = new byte[8];
            ByteBuffer.wrap(bytes).putDouble(output[i/8]);
            for (byte b : bytes){
                if (b == solution[i]) {
                    fitness++;
                }
                i++;
            }
        }*/
        fitness = fitness/solutions.length;
        return fitness;
    }

    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = 0;
        return maxFitness;
    }

}
