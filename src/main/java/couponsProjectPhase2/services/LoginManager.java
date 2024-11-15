package couponsProjectPhase2.services;

import couponsProjectPhase2.beans.ClientType;
import couponsProjectPhase2.exceptions.*;
import org.springframework.stereotype.Component;

@Component
public class LoginManager {
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    public LoginManager(AdminService adminService, CompanyService companyService, CustomerService customerService) {
        this.adminService = adminService;
        this.companyService = companyService;
        this.customerService = customerService;
    }




    public ClientService login(String email, String password, ClientType clientType) throws WrongEmailOrPasswordException, EmptyValueException {
        if (clientType.equals(ClientType.Administrator) && adminService.login(email, password))
            return adminService;

        else if (clientType.equals(ClientType.Company) && companyService.login(email, password))
            return companyService;

        else if (clientType.equals(ClientType.Customer) && customerService.login(email, password))
            return customerService;

        throw new WrongEmailOrPasswordException();
    }
}
