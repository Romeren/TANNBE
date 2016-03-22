package com.main.network.Network;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class NetworkConfiguration {
    public static boolean isFullyConnected = true;

    public static double learningRate = 0.05;

    public static int maximumNumberOfSupervizedTrainingIterations = 1000;

    //Connection variables:
    public static double minimumInitializedConnectionWeight = -1;
    public static double maximumInitializedConnectionWeight = 1;

    //Linear variables:
    public static double minimumInitializedLinearBias = -2;
    public static double maximumInitializedLinearBias = 2;

    //Perceptron variables:
    public static double minimumInitializedPerceptronThredshold = 0;
    public static double maximumInitializedPerceptronThredshold = 1;

    //RBM variables:
    public static double minimumThredsholdRBMBackpropagate = 0;
    public static double maximumThredsholdRBMBackpropagate = 1;
    public static double minimumRBMBias = -0.2;
    public static double maximumRBMBias = 0.2;

    // Self-Organized map SOMs Variables:
    public static double minimumInitializedSOMWeight = 0;
    public static double maximumInitializedSOMWeight = 1;
    public static double SOMsLearningRate = 0.1;
}
