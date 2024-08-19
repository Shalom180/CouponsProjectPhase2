package couponsProjectPhase2.services;

import beans.ClientType;
import couponsProjectPhase2.exceptions.*;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class LoginManager {
    private static LoginManager instance;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    public ClientFacade login(String email, String password, ClientType clientType) throws SQLException,
            EmptyValueException, NonPositiveValueException, EmailFormatException, NegativeValueException,
            PasswordFormatException, DateException, NameException, WrongEmailOrPasswordException {
        if (clientType.equals(ClientType.Administrator)) {
            AdminService facade = new AdminService();
            if (facade.login(email, password))
                return facade;
            else throw new WrongEmailOrPasswordException();
        } else if (clientType.equals(ClientType.Company)) {
            CompanyService facade = new CompanyService();
            if (facade.login(email, password))
                return facade;
            else throw new WrongEmailOrPasswordException();
        } else if (clientType.equals(ClientType.Customer)) {
            CustomerService facade = new CustomerService();
            if (facade.login(email, password))
                return facade;
            else throw new WrongEmailOrPasswordException();

        }
        return null;
    }
}
