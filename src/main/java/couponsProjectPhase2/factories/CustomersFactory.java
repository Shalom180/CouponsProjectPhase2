package couponsProjectPhase2.factories;

import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.beans.Customer;
import couponsProjectPhase2.exceptions.EmailFormatException;
import couponsProjectPhase2.exceptions.NameException;
import couponsProjectPhase2.exceptions.PasswordFormatException;
import couponsProjectPhase2.repositories.CategoriesRepository;
import couponsProjectPhase2.repositories.CompaniesRepository;
import couponsProjectPhase2.repositories.CouponsRepository;
import couponsProjectPhase2.repositories.CustomersRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CustomersFactory extends Factory{

    //ctor

    public CustomersFactory(CompaniesRepository companiesRepository, CouponsRepository couponsRepository, CustomersRepository customersRepository, CategoriesRepository categoriesRepository) {
        super(companiesRepository, couponsRepository, customersRepository, categoriesRepository);
    }

    public Customer createCustomer(List<Company> companies) throws EmailFormatException, PasswordFormatException,
            NameException {
        Random random = new Random();
        String firstName = FirstName.values()[random.nextInt(FirstName.values().length)].toString();
        String lastName = LastName.values()[random.nextInt(LastName.values().length)].toString();
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" +
                EmailProvider.values()[random.nextInt(EmailProvider.values().length)] + ".com";
        String password = Password.array[random.nextInt(Password.array.length)];

        return new Customer(firstName, lastName, email, password, null);
    }
}
