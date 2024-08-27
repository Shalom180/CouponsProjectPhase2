package couponsProjectPhase2.repositories;

import couponsProjectPhase2.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompaniesRepository extends JpaRepository<Company, Integer> {
    boolean existsById(int id);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    @Query(value = "DELETE cc FROM customers_coupons cc JOIN coupons c ON cc.coupon_id = c.id WHERE c.company_id =?1",
    nativeQuery = true)
    void deleteCouponsPurchaseHistory(int companyID);
    Optional<Company> findByEmailAndPassword(String email, String password);
}
