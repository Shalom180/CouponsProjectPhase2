package couponsProjectPhase2.repositories;

import couponsProjectPhase2.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponsRepository extends JpaRepository<Coupon, Integer> {
    @Query(value = "DELETE FROM customers_coupons WHERE coupon_id=?1", nativeQuery = true)
    void deleteCouponsPurchaseHistory(int couponId);
    @Query(value = "DELETE FROM coupons WHERE company_id=?1", nativeQuery = true)
    void deleteAllCouponsByCompanyId(int companyId);
}
