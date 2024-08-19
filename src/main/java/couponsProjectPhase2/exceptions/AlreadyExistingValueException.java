package couponsProjectPhase2.exceptions;

public class AlreadyExistingValueException extends Exception {
    public AlreadyExistingValueException() {
        super("Input data already exists in the DB.");
    }
}
