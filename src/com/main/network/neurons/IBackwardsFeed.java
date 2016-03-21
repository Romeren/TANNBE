package com.main.network.neurons;

/**
 * Created by EmilSebastian on 18-03-2016.
 */
public interface IBackwardsFeed {

    double getLastRoundsOutput();

    void feedBackwards();

    void unsupervizedRBM();
}
