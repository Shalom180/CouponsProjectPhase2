package couponsProjectPhase2.exceptions;

public class UnallowedUpdateException extends Exception {
    public UnallowedUpdateException() {
        super("An update of this sort is not allowed.");
    }
}
