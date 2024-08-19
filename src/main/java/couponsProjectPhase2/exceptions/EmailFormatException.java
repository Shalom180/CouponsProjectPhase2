package couponsProjectPhase2.exceptions;

public class EmailFormatException extends Exception {
    public EmailFormatException() {
        super("Input String does not match properly to the email format.");
    }

}
