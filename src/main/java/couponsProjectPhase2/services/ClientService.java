package couponsProjectPhase2.services;

import couponsProjectPhase2.exceptions.EmptyValueException;
import couponsProjectPhase2.repositories.CategoriesRepository;
import couponsProjectPhase2.repositories.CompaniesRepository;
import couponsProjectPhase2.repositories.CouponsRepository;
import couponsProjectPhase2.repositories.CustomersRepository;
import couponsProjectPhase2.exceptions.*;

import java.sql.SQLException;

public abstract class ClientService {
    protected CompaniesRepository companiesRepository;
    protected CouponsRepository couponsRepository;
    protected CustomersRepository customersRepository;
    protected CategoriesRepository categoriesRepository;

    //ctor
    public ClientService(CompaniesRepository companiesRepository, CouponsRepository couponsRepository, CustomersRepository customersRepository, CategoriesRepository categoriesRepository) {
        this.companiesRepository = companiesRepository;
        this.couponsRepository = couponsRepository;
        this.customersRepository = customersRepository;
        this.categoriesRepository = categoriesRepository;
    }

    //methods
    public abstract boolean login(String email, String password) throws NonPositiveValueException, EmailFormatException, NegativeValueException, PasswordFormatException, SQLException, DateException, EmptyValueException, NameException, EmptyValueException;

}
