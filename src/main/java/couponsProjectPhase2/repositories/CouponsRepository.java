package couponsProjectPhase2.repositories;

import couponsProjectPhase2.beans.Category;
import couponsProjectPhase2.beans.Company;
import couponsProjectPhase2.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface CouponsRepository extends JpaRepository<Coupon, Integer> {
    boolean existsByTitle(String title);

    @Query(value = "select * from coupons where end_date<?1", nativeQuery = true)
    List<Coupon> findAllCouponsExpireBefore(Date date);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `customers_coupons` (`customers_id`, `coupons_id`) VALUES (?1, ?2)", nativeQuery = true)
    void addCouponPurchase(int customerId, int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customers_coupons WHERE coupons_id=?1", nativeQuery = true)
    void deleteCouponsPurchaseHistory(int couponId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM coupons WHERE company_id=?1", nativeQuery = true)
    void deleteAllCouponsByCompanyId(int companyId);

    List<Coupon> findAllByCompanyId(int companyId);

    List<Coupon> findAllByCompanyAndCategory(Company company, Category category);


    @Query(value = "SELECT * FROM coupons WHERE company_id=?1 AND price<?2", nativeQuery = true)
    List<Coupon> findAllByCompanyAndPriceBelow(int company_id, double maxPrice);

    @Query(value = "SELECT * FROM coupons c RIGHT JOIN customers_coupons cc ON c.id=cc.coupons_id WHERE cc.customers_id=?1",
            nativeQuery = true)
    List<Coupon> findAllByCustomerId(int customerID);

    @Query(value = "SELECT * FROM coupons c RIGHT JOIN customers_coupons cc ON c.id=cc.coupons_id WHERE cc.customers_id=?1 " +
            "AND c.category_id=?2", nativeQuery = true)
    List<Coupon> findAllByCustomerIdAndCategoryId(int customerID, int categoryId);

    @Query(value = "SELECT * FROM coupons c RIGHT JOIN customers_coupons cc ON c.id=cc.coupons_id WHERE cc.customers_id=?1 " +
            "AND c.price<?2", nativeQuery = true)
    List<Coupon> findAllByCustomerIdAndPriceBelow(int customerID, double maxPrice);


}
