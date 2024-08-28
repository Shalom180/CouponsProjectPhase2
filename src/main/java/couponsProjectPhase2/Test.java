package couponsProjectPhase2;

import couponsProjectPhase2.beans.ClientType;
import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.beans.Coupon;
import couponsProjectPhase2.beans.Customer;
import couponsProjectPhase2.exceptions.*;
import couponsProjectPhase2.services.AdminService;
import couponsProjectPhase2.services.CompanyService;
import couponsProjectPhase2.services.CustomerService;
import couponsProjectPhase2.services.LoginManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Component
@Order(1)
public class Test implements CommandLineRunner {
    private LoginManager loginManager;
    private CouponExpirationDailyJob job;

    public Test(LoginManager loginManager, CouponExpirationDailyJob job) {
        this.loginManager = loginManager;
        this.job = job;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            testAdminService();
        } catch (WrongEmailOrPasswordException e) {
            throw new RuntimeException(e);
        }
    }

//    public void testAll() {
//        try {
//            Thread thread = new Thread(job);
//            thread.start();
//
//
//            //ADMIN FACADE TESTS
//            testAdminService();
//            System.out.println("TEST ADMIN FACADE DONE");
//
//            //COMPANY FACADE TESTS
//            Coupon coupon = testCompanyFacade();
//            System.out.println("TEST COMPANY FACADE DONE");
//
//
//            //CUSTOMER FACADE TESTS
//            testCustomerFacade(coupon);
//            System.out.println("TEST CUSTOMER FACADE DONE");
//
//
//            //STOP THE DAILY JOB
//            job.stop();
//
//
//        } catch (SQLException | NonPositiveValueException | EmailFormatException | NegativeValueException |
//                 PasswordFormatException | NameException | DateException | EmptyValueException |
//                 AlreadyExistingValueException | UnallowedUpdateException | CompanyIdException |
//                 NonexistantObjectException | UnavailableCouponException | AlreadyPurchasedException |
//                 WrongEmailOrPasswordException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public void testTask() {
        Thread thread = new Thread(job);
        thread.start();
        //STOP THE DAILY JOB
        job.stop();
    }

    private void testAdminService() throws WrongEmailOrPasswordException, EmptyValueException,
            EmailFormatException, PasswordFormatException, AlreadyExistingValueException, UnallowedUpdateException,
            NonexistantObjectException, NameException {

        AdminService adminService = (AdminService) loginManager.login(
                "admin@admin.com", "admin", ClientType.Administrator);

        Company johnnysCompany = new Company("JohnnyAndCo.", "johnny@johnnyandco.com", "Adhdjh1(45",
                null);
        adminService.addCompany(johnnysCompany);

        Company company2 = adminService.getAllCompanies().get(0);
        company2.setPassword("ThisNew#Password1");
        adminService.updateCompany(company2);

        adminService.deleteCompany(adminService.getAllCompanies().get(1).getId());

        System.out.println(adminService.getAllCompanies());

        System.out.println(adminService.getOneCompany(adminService.getAllCompanies().get(2).getId()));

        Customer customerYali = new Customer(
                "Yali", "Taw", "yali.taw@gmail.com", "@YalisPassword1", null);
        adminService.addCustomer(customerYali);

        Customer customer2 = adminService.getOneCustomer(adminService.getAllCustomers().get(1).getId());
        System.out.println(customer2);
        customer2.setEmail("customertwonewemail@gmail.com");
        adminService.updateCustomer(customer2);
        System.out.println(customer2);

        adminService.deleteCustomer(adminService.getAllCustomers().get(1).getId());


    }

//    public Coupon testCompanyFacade() throws NonPositiveValueException, EmailFormatException,
//            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException,
//            EmptyValueException, CompanyIdException, AlreadyExistingValueException, UnallowedUpdateException,
//            NonexistantObjectException, WrongEmailOrPasswordException {
//        CompanyService companyService = (CompanyService) LoginManager.login(
//                "johnny@johnnyandco.com", "Adhdjh1(45", ClientType.Company);
//
//        Coupon johnnysCoupon1 = new Coupon(
//                companyService.getCompanyDetails(), companyService.getCategories().get(0), "Johnny's Food Coupon",
//                "This is a coupon for food.", Date.valueOf("2024-09-12"), Date.valueOf("2024-09-22"),
//                10, 19.9, null, null);
//        companyService.addCoupon(johnnysCoupon1);
//
//        Coupon johnnysCoupon2 = new Coupon(
//                companyService.getCompanyDetails().getId(), Category.Fashion, "Johnny's Fashion Coupon",
//                "This is a coupon for fashion.", Date.valueOf("2024-09-12"), Date.valueOf("2024-09-22"),
//                10, 19.9, null);
//        companyService.addCoupon(johnnysCoupon2);
//
//        johnnysCoupon1 = companyService.getCompanyCoupons().get(0); // we retrieve the coupon's id in order to update it
//        johnnysCoupon1.setDescription("This is a coupon for delicious food.");
//        companyService.updateCoupon(johnnysCoupon1);
//
//        List<Coupon> allCoupons = companyService.getCompanyCoupons();
//        companyService.deleteCoupon(allCoupons.get(0).getId());
//
//        List<Coupon> foodCoupons = companyService.getCompanyCoupons(Category.Food);
//
//        List<Coupon> cheaperThan50 = companyService.getCompanyCoupons(50);
//
//        companyService.getCompanyDetails();
//        return allCoupons.get(1);
//    }
//
//    public void testCustomerFacade(Coupon coupon) throws NonPositiveValueException, EmailFormatException,
//            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException,
//            EmptyValueException, NonexistantObjectException, UnavailableCouponException, AlreadyPurchasedException,
//            WrongEmailOrPasswordException {
//        CustomerService customerService = (CustomerService) loginManager.login(
//                "yali.taw@gmail.com", "@YalisPassword1", ClientType.Customer);
//
//
//        customerService.purchaseCoupon(coupon);
//
//        List<Coupon> yalisCoupons = customerService.getCustomerCoupons();
//
//        List<Coupon> yalisFashionCoupons = customerService.getCustomerCoupons(Category.Fashion);
//
//        List<Coupon> yalisCouponsBellow50 = customerService.getCustomerCoupons(50);
//
//        Customer customerYali2 = customerService.getCustomerDetails();
//    }
//
//
}
