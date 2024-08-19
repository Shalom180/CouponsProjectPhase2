package couponsProjectPhase2.beans;

import couponsProjectPhase2.exceptions.EmailFormatException;
import couponsProjectPhase2.exceptions.NameException;
import couponsProjectPhase2.exceptions.PasswordFormatException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String firstName, lastName, password;
    @Column(nullable = false, unique = true)
    private String email;
    @ManyToMany
    private Set<Coupon> coupons;

    //ctors
    public Customer() {
    }

    //for insert
    public Customer(String firstName, String lastName, String email, String password, Set<Coupon> coupons)
            throws NameException, EmailFormatException, PasswordFormatException {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        this.coupons = coupons;
    }

    //getters & setters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }

    //methods
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
