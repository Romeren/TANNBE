package com.main.network.CommonInterfaces;

import com.main.network.Exceptions.InvalidNumberOfInput;

/**
 * Created by EmilSebastian on 22-03-2016.
 */
public interface IBackpropagationable {

    void backpropagate();

    void startBackpropagate(double[] expectedValues) throws InvalidNumberOfInput;

}
