package couponsProjectPhase2.factories;

import beans.Company;
import exceptions.*;

import java.util.Random;

public class CompaniesFactory extends Factory{
    public static Company createCompany() throws EmailFormatException, PasswordFormatException, EmptyValueException,
            NonPositiveValueException, NegativeValueException, DateException {
        Random random = new Random();
        String name = switch (random.nextInt(2) + 1) {
            case 1 -> FirstName.values()[random.nextInt(FirstName.values().length)].toString() +
                    CompanyNameSuffix.values()[random.nextInt(CompanyNameSuffix.values().length)].toString();
            case 2 -> LastName.values()[random.nextInt(LastName.values().length)].toString() +
                    CompanyNameSuffix.values()[random.nextInt(CompanyNameSuffix.values().length)].toString();
            default -> "";
        };
        String email = name.toLowerCase() + "@" + name.toLowerCase() + ".com";
        String password = Password.array[random.nextInt(Password.array.length)];
        return new Company(name, email, password, null);
    }

    public static Company createCompany(int companyID) throws EmailFormatException, PasswordFormatException, EmptyValueException, NonPositiveValueException, NegativeValueException, DateException {
        Company company = createCompany();
        return new Company(companyID, company.getName(), company.getEmail(), company.getPassword(), null);
    }
}
