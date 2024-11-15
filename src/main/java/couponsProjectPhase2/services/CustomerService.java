package couponsProjectPhase2.services;

import couponsProjectPhase2.beans.Category;
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
import java.util.List;

@Service
public class CustomerService extends ClientService {
    private int customerID;

    //ctor
    public CustomerService(CompaniesRepository companiesRepository, CouponsRepository couponsRepository,
                           CustomersRepository customersRepository, CategoriesRepository categoriesRepository) {
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

    public void purchaseCoupon(Coupon coupon) throws EmptyValueException, AlreadyPurchasedException, UnavailableCouponException, NonexistantObjectException {
        //method does not accept null values
        if (coupon == null)
            throw new EmptyValueException();

        //we cannot purchase a nonexistant coupon
        if (!couponsRepository.existsById(coupon.getId()))
            throw new NonexistantObjectException();

        //one customer cannot purchase a coupon more that once
        if (couponsRepository.findAllByCustomerId(customerID).contains(coupon))
            throw new AlreadyPurchasedException();

        //they cannot purchase a coupon whose quantity is zero
        if (coupon.getAmount() < 1)
            throw new UnavailableCouponException();

        //they cannot purchase the coupon if its end date has already passed
        if (coupon.getEndDate().before(new Date(System.currentTimeMillis())))
            throw new UnavailableCouponException();

        //after purchasing the coupons quantity goes down by one
        couponsRepository.addCouponPurchase(customerID, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        couponsRepository.save(coupon);
    }

    public List<Coupon> getCustomerCoupons() {
        return couponsRepository.findAllByCustomerId(customerID);
    }

    public List<Coupon> getCustomerCoupons(Category category) {
        return couponsRepository.findAllByCustomerIdAndCategoryId(customerID, category.getId());
    }

    public List<Coupon> getCustomerCoupons(double maxPrice) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException, EmptyValueException {
        return couponsRepository.findAllByCustomerIdAndPriceBelow(customerID, maxPrice);

    }

    public Customer getCustomerDetails() throws NonPositiveValueException, EmailFormatException, NegativeValueException,
            PasswordFormatException, NameException, SQLException, DateException, EmptyValueException {
        return customersRepository.findById(customerID).get();
    }

    public List<Category> getCategories() {
        return categoriesRepository.findAll();
    }

}
