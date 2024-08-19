package couponsProjectPhase2.services;

import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.beans.Customer;
import couponsProjectPhase2.exceptions.*;
import couponsProjectPhase2.repositories.CompaniesRepository;
import couponsProjectPhase2.repositories.CouponsRepository;
import couponsProjectPhase2.repositories.CustomersRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService extends ClientService {

    //ctor
    public AdminService(CompaniesRepository companiesRepository, CouponsRepository couponsRepository, CustomersRepository customersRepository) {
        super(companiesRepository, couponsRepository, customersRepository);
    }

    //methods
    @Override
    public boolean login(String email, String password) throws EmptyValueException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty())
            throw new EmptyValueException();
        return email.equalsIgnoreCase("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company company) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, SQLException, DateException, EmptyValueException,
            AlreadyExistingValueException {
        //we'll check whether we've got an empty value which isn't allowed to be empty
        if (company == null || company.getName() == null || company.getName().isEmpty() || company.getPassword() == null
                || company.getPassword().isEmpty() || company.getEmail() == null || company.getEmail().isEmpty())
            throw new EmptyValueException();

        //we'll check whether the input email and password match our format properly
        if (!Validations.isValidEmail(company.getEmail()))
            throw new EmailFormatException();

        if (!Validations.isValidPassword(company.getPassword()))
            throw new PasswordFormatException();

        //we'll check whether there's already a company in the db with the same id, name or email
        if (companiesRepository.existsById(company.getId()) || companiesRepository.existsByEmail(company.getEmail()) ||
                companiesRepository.existsByName(company.getName()))
            throw new AlreadyExistingValueException();

        companiesRepository.save(company);
    }

    public void updateCompany(Company company) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, SQLException, DateException, EmptyValueException,
            UnallowedUpdateException, NonexistantObjectException, AlreadyExistingValueException {
        if (company == null)
            throw new EmptyValueException();

        //we cannot update a nonexistant company - we'll check if this company actually exists
        if (!companiesRepository.existsById(company.getId()))
            throw new NonexistantObjectException();

        //we'll check whether we've got an empty value which isn't allowed to be empty
        if (company.getName() == null || company.getName().isEmpty() || company.getPassword() == null
                || company.getPassword().isEmpty() || company.getEmail() == null || company.getEmail().isEmpty())
            throw new EmptyValueException();

        //we cannot update to an email that already exists in the db -
        // we'll check first if the user is actually trying to change the company's email and then well look
        // for matching values
        if (!company.getEmail().equals(companiesRepository.findById(company.getId()).get().getEmail()) &&
                companiesRepository.existsByEmail(company.getEmail()))
            throw new AlreadyExistingValueException();
        //we are not allowed to update name
        if (!companiesRepository.findById(company.getId()).get().getName().equals(company.getName()))
            throw new UnallowedUpdateException();

        companiesRepository.save(company);

    }

    public void deleteCompany(int companyId) throws NonexistantObjectException {
        //we cannot delete a nonexistant company
        if (!companiesRepository.existsById(companyId))
            throw new NonexistantObjectException();
        //we have to delete also the coupons issued by the company and its customer purchase history
        companiesRepository.deleteCouponsPurchaseHistory(companyId);
        couponsRepository.deleteAllCouponsByCompanyId(companyId);
        companiesRepository.deleteById(companyId);


    }

    public List<Company> getAllCompanies() {
        return companiesRepository.findAll();
    }

    public Company getOneCompany(int companyId) {
        return companiesRepository.findById(companyId).orElseThrow();
    }

    public void addCustomer(Customer customer) throws NonPositiveValueException, EmailFormatException,
            NegativeValueException, PasswordFormatException, NameException, SQLException, DateException,
            EmptyValueException, AlreadyExistingValueException {
        //we'll check for disallowed empty values
        if (customer == null || customer.getEmail() == null || customer.getEmail().isEmpty() ||
                customer.getPassword() == null || customer.getPassword().isEmpty() || customer.getFirstName() == null ||
                customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty())
            throw new EmptyValueException();

        //we can't add a new customer that already exist in the db by id
        if (customersRepository.existsById(customer.getId()))
            throw new AlreadyExistingValueException();

        //we'll verify the password and email format
        if (!Validations.isValidPassword(customer.getPassword()))
            throw new PasswordFormatException();
        if (!Validations.isValidEmail(customer.getEmail()))
            throw new EmailFormatException();

        //we'll check whether the customer's name is valid
        if (!Validations.onlyAlphabets(customer.getFirstName()) || !Validations.onlyAlphabets(customer.getLastName()))
            throw new NameException();


        //we mustn't add a new customer with an existing email address
        if (customersRepository.existsByEmail(customer.getEmail()))
            throw new AlreadyExistingValueException();

        customersRepository.save(customer);
    }

    public void updateCustomer(Customer customer) throws EmptyValueException, NonexistantObjectException, AlreadyExistingValueException {
        //we cannot accept a null expression
        if (customer == null)
            throw new EmptyValueException();
        //we cant update a nonexistant customer
        if (!customersRepository.existsById(customer.getId()))
            throw new NonexistantObjectException();

        //we'll check for disallowed empty values
        if (customer.getEmail() == null || customer.getEmail().isEmpty() ||
                customer.getPassword() == null || customer.getPassword().isEmpty() || customer.getFirstName() == null ||
                customer.getFirstName().isEmpty() || customer.getLastName() == null || customer.getLastName().isEmpty())
            throw new EmptyValueException();

        //we cannot update a customer email to an already existing email -
        // we'll check first if the user is actually trying to change the customer's email and then well look
        // for matching values
        if (!customer.getEmail().equals(customersRepository.findById(customer.getId()).get().getEmail()) &&
                customersRepository.existsByEmail(customer.getEmail()))
            throw new AlreadyExistingValueException();
        //we cannot update customer id
        customersRepository.save(customer);
    }

    public void deleteCustomer(int customerId) throws NonexistantObjectException {
        //we cannot delete a nonexistant customer
        if (!customersRepository.existsById(customerId))
            throw new NonexistantObjectException();

        //we have to delete his coupons purchase history as well
        customersRepository.deleteCouponsPurchaseHistory(customerId);
        customersRepository.deleteById(customerId);
    }

    public List<Customer> getAllCustomers(){
        return customersRepository.findAll();
    }

    public Customer getOneCustomer(int customerId) {
        return customersRepository.findById(customerId).orElseThrow();
    }

}
