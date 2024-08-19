package couponsProjectPhase2.exceptions;

public class CompanyIdException extends Exception {
    public CompanyIdException() {
        super("A coupons companyID must match the company adding it.");
    }
}
