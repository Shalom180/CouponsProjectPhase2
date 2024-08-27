package couponsProjectPhase2.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class DBFactory extends Factory {
    public static void dbBuilder() throws SQLException, NonPositiveValueException, EmailFormatException, PasswordFormatException, NegativeValueException, DateException, EmptyValueException, NameException {

        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        CustomersDBDAO customersDBDAO = new CustomersDBDAO();
        Random random = new Random();

        ArrayList<Company> companies = new ArrayList<>();
        for (int i = 1; i < NUM_OF_COMPANIES; i++) {
            companies.add(CompaniesFactory.createCompany(i));
        }

        for (Company company : companies) {
            companiesDBDAO.addCompany(company);

        }

        ArrayList<Coupon> coupons = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_COMPANIES * NUM_OF_COUPONS_FOR_EACH_COMPANY; i++) {
            ArrayList<Company> allCompanies = companiesDBDAO.getAllCompanies();
            int companyID = allCompanies.get(random.nextInt(allCompanies.size())).getId();
            coupons.add(CouponsFactory.createCoupon(companyID, i));
        }

        for (Coupon coupon : coupons) {
            couponsDBDAO.addCoupon(coupon);
        }

        ArrayList<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_CUSTOMERS; i++) {
            customers.add(CustomersFactory.createCustomer(i, companies));
        }

        for (Customer customer : customers) {
            customersDBDAO.addCustomer(customer);
        }

        for (Customer customer : customersDBDAO.getAllCustomers()) {
            for (int i = 0; i <= random.nextInt(NUM_OF_COUPONS_FOR_EACH_CUSTOMER); i++) {
                Coupon randomCoupon = couponsDBDAO.getAllCoupons().get(random.nextInt(couponsDBDAO.getAllCoupons().size()));
                couponsDBDAO.addCouponPurchase(customer.getId(), randomCoupon.getId());
            }
        }
    }

    public static void formatDB() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection con = pool.getConnection();
        try {
            PreparedStatement statement1 = con.prepareStatement("DELETE FROM customers_vs_coupons;");
            PreparedStatement statement2 = con.prepareStatement("DELETE FROM customers;");
            PreparedStatement statement3 = con.prepareStatement("DELETE FROM coupons;");
            PreparedStatement statement4 = con.prepareStatement("DELETE FROM companies;");
            statement1.execute();
            statement2.execute();
            statement3.execute();
            statement4.execute();
        } finally {
            pool.restoreConnection(con);
        }
    }

    public static void formatAndBuildDB() throws NonPositiveValueException, EmailFormatException,
            PasswordFormatException, NegativeValueException, NameException, SQLException, DateException,
            EmptyValueException {
        formatDB();
        dbBuilder();
    }
}
