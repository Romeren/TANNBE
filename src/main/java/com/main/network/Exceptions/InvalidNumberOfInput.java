package com.main.network.Exceptions;

/**
 * Created by EmilSebastian on 21-03-2016.
 */
public class InvalidNumberOfInput extends Exception {

    public InvalidNumberOfInput(){
        super("The number of inputs does not match the number of neurons!");
    }
}
