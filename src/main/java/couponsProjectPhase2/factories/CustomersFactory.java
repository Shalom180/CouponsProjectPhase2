package couponsProjectPhase2.factories;

import beans.Company;
import beans.Customer;
import exceptions.EmailFormatException;
import exceptions.NameException;
import exceptions.PasswordFormatException;

import java.util.ArrayList;
import java.util.Random;

public class CustomersFactory extends Factory{
    public static Customer createCustomer(int customerID, ArrayList<Company> companies) throws EmailFormatException, PasswordFormatException, NameException {
        Random random = new Random();
        String firstName = FirstName.values()[random.nextInt(FirstName.values().length)].toString();
        String lastName = LastName.values()[random.nextInt(LastName.values().length)].toString();
        String email = firstName + "." + lastName + "@" +
                EmailProvider.values()[random.nextInt(EmailProvider.values().length)] + ".com";
        String password = Password.array[random.nextInt(Password.array.length)];

        return new Customer(customerID, firstName, lastName, email, password, null);
    }
}
