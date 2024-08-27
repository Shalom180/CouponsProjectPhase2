package couponsProjectPhase2.services;

import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.beans.Coupon;
import couponsProjectPhase2.beans.Customer;
import couponsProjectPhase2.exceptions.*;
import couponsProjectPhase2.repositories.CategoriesRepository;
import couponsProjectPhase2.repositories.CompaniesRepository;
import couponsProjectPhase2.repositories.CouponsRepository;
import couponsProjectPhase2.repositories.CustomersRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CustomerService extends ClientService {
    private int customerID;

    //ctor
    public CustomerService(CompaniesRepository companiesRepository, CouponsRepository couponsRepository, CustomersRepository customersRepository, CategoriesRepository categoriesRepository, int customerID) {
        super(companiesRepository, couponsRepository, customersRepository, categoriesRepository);
    }

    //methods
    @Override
    public boolean login(String email, String password) {
        Customer customer = customersRepository.findByEmailAndPassword(email, password).orElse(null);
        if (customer == null)
            return false;
        else {
            customerID = customer.getId();
            return true;
        }
    }

    public void purchaseCoupon(Coupon coupon) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException,
            EmptyValueException, AlreadyPurchasedException, UnavailableCouponException, NonexistantObjectException {
        //method does not accept null values
        if (coupon == null)
            throw new EmptyValueException();

        //we cannot purchase a nonexistant coupon
        if (!couponsRepository.existsById(coupon.getId()))
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
