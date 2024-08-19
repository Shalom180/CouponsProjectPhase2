package couponsProjectPhase2.exceptions;

public class UnavailableCouponException extends Exception {
    public UnavailableCouponException() {
        super("A customer cannot purchase a coupon whose quantity is below one or end date has already passed.");
    }
}
