package couponsProjectPhase2.exceptions;

public class NonPositiveValueException extends Exception {
    public NonPositiveValueException(){
        super("Set value must be positive");
    }
}
