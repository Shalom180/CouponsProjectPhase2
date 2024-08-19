package couponsProjectPhase2.exceptions;

public class NegativeValueException extends Exception{
    public NegativeValueException() {
        super("Input value must not be negative.");
    }
}
