package couponsProjectPhase2.factories;

import beans.Category;
import beans.Coupon;
import exceptions.DateException;
import exceptions.EmptyValueException;
import exceptions.NegativeValueException;
import exceptions.NonPositiveValueException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

public class CouponsFactory extends Factory {
    public static Coupon createCoupon(int companyID, int couponIndex) throws NonPositiveValueException, NegativeValueException, DateException, EmptyValueException {
        Random random = new Random();

        int couponID = (companyID - 1) * NUM_OF_COUPONS_FOR_EACH_COMPANY + couponIndex;
        Category category = Category.values()[random.nextInt(Category.values().length)];
        String title = "Company" + companyID + "'s Coupon " + couponIndex;
        Date startDate = Date.valueOf(LocalDate.of(random.nextInt(2023, 2025),
                random.nextInt(12) + 1 , random.nextInt(1, 29)));
        LocalDate localEndDate = startDate.toLocalDate().plusDays(random.nextInt(1, 200));
        Date endDate = Date.valueOf(localEndDate);
//        Date startDate = Date.valueOf("2023-05-18");
//        Date endDate = Date.valueOf("2024-05-18");
        int amount = random.nextInt(1, 100);
        double price = random.nextInt(10000) / 100.0;
        String description = title + " is a coupon that starts on " + startDate + " and ends on " + endDate +
                ". It costs " + price + " and has an amount of " + amount + ".";
        String image = null;
        return new Coupon(couponID, companyID, category, title, description, startDate, endDate, amount, price, image);
    }
}



//    public static ArrayList<Coupon> createCouponArrayList(int companyID, int size) throws NonPositiveValueException, NegativeValueException, DateException, EmptyValueException {
//        ArrayList<Coupon> arrayList = new ArrayList<>();
//        for (int i = 1; i <= size; i++) {
//            arrayList.add(createCoupon(companyID, i));
//        }
//        return arrayList;
//    }
//}
