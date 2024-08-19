package couponsProjectPhase2.exceptions;

public class NameException extends Exception{
    public NameException() {
        super("Name must be not empty or null and contain only alphabets.");
    }
}
