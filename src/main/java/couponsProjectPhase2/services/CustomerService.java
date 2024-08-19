package couponsProjectPhase2.services;

import beans.Category;
import beans.Coupon;
import beans.Customer;
import couponsProjectPhase2.exceptions.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CustomerService extends ClientFacade {
    private int customerID;

    //ctor
    public CustomerService() throws SQLException {
    }

    //methods
    @Override
    public boolean login(String email, String password) throws EmptyValueException, NonPositiveValueException,
            EmailFormatException, NegativeValueException, PasswordFormatException, NameException, SQLException,
            DateException {

        if (customersDAO.isCustomerExists(email, password)) {
            for (Customer existing : customersDAO.getAllCustomers()) {
                if (existing.getEmail().equals(email) && existing.getPassword().equals(password)) {
                    this.customerID = existing.getId();
                    return true;
                }
            }
        }
        return false;
    }

    public void purchaseCoupon(Coupon coupon) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException,
            EmptyValueException, AlreadyPurchasedException, UnavailableCouponException, NonexistantObjectException {
        //method does not accept null values
        if (coupon == null)
            throw new EmptyValueException();

        //we cannot purchase a nonexistant coupon
        boolean existingAlready = false;
        for (Coupon existing : couponsDAO.getAllCoupons()) {
            if (existing.getId() == coupon.getId()) {
                existingAlready = true;
                break;
            }
        }
        if (!existingAlready)
            throw new NonexistantObjectException();

        //one customer cannot purchase a coupon more that once
        for (Coupon purchased : customersDAO.getOneCustomer(customerID).getCoupons()) {
            if (purchased.getId() == coupon.getId())
                throw new AlreadyPurchasedException();
        }
        //they cannot purchase a coupon whose quantity is zero
        if (coupon.getAmount() < 1)
            throw new UnavailableCouponException();
        //they cannot purchase the coupon if its end date has already passed
        if (coupon.getEndDate().before(new Date(System.currentTimeMillis())))
            throw new UnavailableCouponException();
        //after purchasing the coupons quantity goes down by one
        couponsDAO.addCouponPurchase(customerID, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        couponsDAO.updateCoupon(coupon);
    }

    public ArrayList<Coupon> getCustomerCoupons() throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException, EmptyValueException {
        return customersDAO.getOneCustomer(customerID).getCoupons();
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) throws NonPositiveValueException,
            EmailFormatException, NegativeValueException, PasswordFormatException, NameException, SQLException,
            DateException, EmptyValueException {
        ArrayList<Coupon> inCategory = new ArrayList<>();
        for (Coupon c : getCustomerCoupons()) {
            if (c.getCategory().equals(category))
                inCategory.add(c);
        }
        return inCategory;
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException, EmptyValueException {
        ArrayList<Coupon> bellowMax = new ArrayList<>();
        for (Coupon c : getCustomerCoupons()) {
            if (c.getPrice() <= maxPrice)
                bellowMax.add(c);
        }
        return bellowMax;
    }

    public Customer getCustomerDetails() throws NonPositiveValueException, EmailFormatException, NegativeValueException,
            PasswordFormatException, NameException, SQLException, DateException, EmptyValueException {
        return customersDAO.getOneCustomer(customerID);
    }

}
