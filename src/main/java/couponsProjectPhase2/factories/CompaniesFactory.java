package couponsProjectPhase2.factories;

import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.repositories.CategoriesRepository;
import couponsProjectPhase2.repositories.CompaniesRepository;
import couponsProjectPhase2.repositories.CouponsRepository;
import couponsProjectPhase2.repositories.CustomersRepository;
import org.springframework.stereotype.Component;

@Component
public class CompaniesFactory extends Factory {

    //ctor
    public CompaniesFactory(CompaniesRepository companiesRepository, CouponsRepository couponsRepository, CustomersRepository customersRepository, CategoriesRepository categoriesRepository) {
        super(companiesRepository, couponsRepository, customersRepository, categoriesRepository);
    }

    public Company createCompany() {
        String name = switch (random.nextInt(2) + 1) {
            case 1 -> FirstName.values()[random.nextInt(FirstName.values().length)].toString();
            case 2 -> LastName.values()[random.nextInt(LastName.values().length)].toString();
            default -> "";
        };
        name += CompanyNameSuffix.values()[random.nextInt(CompanyNameSuffix.values().length)].toString();

        String email = name.toLowerCase() + "@" + name.toLowerCase() + ".com";
        String password = Password.array[random.nextInt(Password.array.length)];
        return new Company(name, email, password, null);
    }

//    public static Company createCompany(int companyID) throws EmailFormatException, PasswordFormatException, EmptyValueException, NonPositiveValueException, NegativeValueException, DateException {
//        Company company = createCompany();
//        return new Company(companyID, company.getName(), company.getEmail(), company.getPassword(), null);
//    }
}
