package couponsProjectPhase2.beans;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Component
public enum ClientType {
    Administrator, Company, Customer
}
