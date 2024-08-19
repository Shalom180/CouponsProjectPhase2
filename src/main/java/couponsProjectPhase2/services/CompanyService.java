package couponsProjectPhase2.services;

import couponsProjectPhase2.beans.Category;
import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.beans.Coupon;
import couponsProjectPhase2.exceptions.*;
import couponsProjectPhase2.repositories.CompaniesRepository;
import couponsProjectPhase2.repositories.CouponsRepository;
import couponsProjectPhase2.repositories.CustomersRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class CompanyService extends ClientService {
    private int companyID;

    //ctor
    public CompanyService(CompaniesRepository companiesRepository, CouponsRepository couponsRepository, CustomersRepository customersRepository, int companyID) {
        super(companiesRepository, couponsRepository, customersRepository);
    }

    //methods
    @Override
    public boolean login(String email, String password) {
        Company company = companiesRepository.findByEmailAndPassword(email, password).orElse(null);
        if (company == null)
            return false;
        else {
         companyID = company.getId();
         return true;
        }
    }

    public void addCoupon(Coupon coupon) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, SQLException, DateException, EmptyValueException,
            AlreadyExistingValueException, CompanyIdException {
        //checking for empty values
        if (coupon == null)
            throw new EmptyValueException();

        if (coupon.getTitle() == null || coupon.getTitle().isEmpty() || coupon.getCompany() == null ||
                coupon.getCategory() == null)
            throw new EmptyValueException();

        if (!cate)

        if (coupon.getAmount() < 0)
            throw new NegativeValueException();

        if (coupon.getStartDate().after(coupon.getEndDate()))
            throw new DateException();

        //we cannot add a coupon with a name matching an existing coupon from the same company
        for (Coupon existing : companiesDAO.getOneCompany(companyID).getCoupons()) {
            if (coupon.getTitle().equals(existing.getTitle()))
                throw new AlreadyExistingValueException();
        }
        //we cannot add a coupon with a company id not matching the company adding it
        if (coupon.getCompanyId() != companyID)
            throw new CompanyIdException();

        couponsDAO.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws UnallowedUpdateException, SQLException, EmptyValueException,
            NonPositiveValueException, NegativeValueException, DateException, AlreadyExistingValueException,
            NonexistantObjectException {
        if (coupon == null)
            throw new EmptyValueException();
        //we cannot update coupon id or company id
        if (coupon.getCompanyId() != companyID)
            throw new UnallowedUpdateException();
        //we cannot delete a nonexistant coupon
        if (couponsDAO.getOneCoupon(coupon.getId()) == null)
            throw new NonexistantObjectException();
        //we cannot update a coupon to a title that already exists among this company's coupons
        // we'll check first if the user is actually trying to change the coupon's title and then well look
        // for matching values
        if (!coupon.getTitle().equals(couponsDAO.getOneCoupon(coupon.getId()).getTitle())) {
            for (Coupon inDB : couponsDAO.getAllCouponsByCompany(companyID)) {
                if (inDB.getTitle().equals(coupon.getTitle()))
                    throw new AlreadyExistingValueException();
            }
        }
        couponsDAO.updateCoupon(coupon);
    }

    public void deleteCoupon(int couponID) throws SQLException, CompanyIdException, NonPositiveValueException,
            NegativeValueException, DateException, EmptyValueException, NonexistantObjectException {
        //we cannot delete a nonexistent coupon
        if (couponsDAO.getOneCoupon(couponID) == null)
            throw new NonexistantObjectException();

        //we cannot delete a coupon not belonging to the company
        if (couponsDAO.getOneCoupon(couponID).getCompanyId() != companyID)
            throw new CompanyIdException();

        //we also have to delete the coupons purchase history
        couponsDAO.deleteCouponsPurchaseHistory(couponID);
        couponsDAO.deleteCoupon(couponID);
    }

    //returns all the company's coupons
    public ArrayList<Coupon> getCompanyCoupons() throws NonPositiveValueException, NegativeValueException, SQLException,
            DateException, EmptyValueException {
        return couponsDAO.getAllCouponsByCompany(companyID);
    }

    //returns all the company's coupons in a certain category
    public ArrayList<Coupon> getCompanyCoupons(Category category) throws NonPositiveValueException, NegativeValueException,
            SQLException, DateException, EmptyValueException {
        //i can also implement it using a dao method that sends a specific query to the db
        // a list that contains only the coupons in a certain category - what we eventually return
        ArrayList<Coupon> inCategoryList = new ArrayList<>();
        for (Coupon c : couponsDAO.getAllCouponsByCompany(companyID)) {
            if (c.getCategory().equals(category))
                inCategoryList.add(c);
        }
        return inCategoryList;
    }

    //returns all the company's coupons below a certain price
    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws NonPositiveValueException, NegativeValueException,
            SQLException, DateException, EmptyValueException {
        //i can also implement it using a dao method that sends a specific query to the db
        ArrayList<Coupon> belowMaxList = new ArrayList<>();
        for (Coupon c : couponsDAO.getAllCouponsByCompany(companyID)) {
            if (c.getPrice() <= maxPrice)
                belowMaxList.add(c);
        }
        return belowMaxList;
    }

    public Company getCompanyDetails() throws NonPositiveValueException, EmailFormatException, NegativeValueException,
            PasswordFormatException, SQLException, DateException, EmptyValueException {
        return companiesDAO.getOneCompany(companyID);
    }

}
