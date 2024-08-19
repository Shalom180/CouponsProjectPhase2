package couponsProjectPhase2.exceptions;

public class AlreadyPurchasedException extends Exception {
    public AlreadyPurchasedException() {
        super("A customer cannot purchase one coupon more that once.");
    }
}
