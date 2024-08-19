package couponsProjectPhase2.exceptions;

public class NonexistantObjectException extends Exception {
    public NonexistantObjectException() {
        super("A nonexistant object is not allowed to be referred to.");
    }
}
