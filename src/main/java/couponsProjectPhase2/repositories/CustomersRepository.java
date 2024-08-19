package couponsProjectPhase2.repositories;

import couponsProjectPhase2.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomersRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
    @Query(value = "DELETE FROM customers_coupons WHERE customer_id=?1", nativeQuery = true)
    void deleteCouponsPurchaseHistory(int customerId);
}
