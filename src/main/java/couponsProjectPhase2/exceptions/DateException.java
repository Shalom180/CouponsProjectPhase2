package couponsProjectPhase2.exceptions;

public class DateException extends Exception {
    public DateException() {
        super("Input Date incompatible. Start date must be before end date and vice versa.");
    }
}
