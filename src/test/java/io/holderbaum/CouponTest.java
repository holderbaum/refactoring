package io.holderbaum;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class CouponTest {

  private Cart cart;

  @Before
  public void setup() {
    cart = new Cart();
  }

  @Test
  public void bulkShouldBeApplicableForMoreThanTwoItems() {
    Coupon coupon = createCoupon(CouponType.BULK);

    addToCart(new Item("Roleplaying Game", 1000, LocalDate.of(2010, 1, 20)));
    addToCart(new Item("Point and Click Adventure", 1000, LocalDate.of(2010, 1, 20)));
    assertFalse(coupon.isApplicableOn(cart));

    addToCart(new Item("Shooter", 1000, LocalDate.of(2010, 1, 20)));
    assertTrue(coupon.isApplicableOn(cart));
  }

  @Test
  public void bulkShouldBeApplicableForMoreThan50Eur() {
    Coupon coupon = createCoupon(CouponType.BULK);

    addToCart(new Item("Roleplaying Game", 4900, LocalDate.of(2010, 1, 20)));
    assertFalse(coupon.isApplicableOn(cart));

    addToCart(new Item("Shooter", 101, LocalDate.of(2010, 1, 20)));
    assertTrue(coupon.isApplicableOn(cart));
  }

  @Test
  public void shoppingSpreeShouldBeApplicableForMoreThanThreeItems() {
    Coupon coupon = createCoupon(CouponType.SHOPPING_SPREE);

    addToCart(new Item("Roleplaying Game", 1000, LocalDate.of(2010, 1, 20)));
    addToCart(new Item("Point and Click Adventure", 1000, LocalDate.of(2010, 1, 20)));
    addToCart(new Item("RTS", 1000, LocalDate.of(2010, 1, 20)));
    assertFalse(coupon.isApplicableOn(cart));

    addToCart(new Item("Shooter", 1000, LocalDate.of(2010, 1, 20)));
    assertTrue(coupon.isApplicableOn(cart));
  }

  @Test
  public void shoppingSpreeShouldNotCountFreePromoGames() {
    Coupon coupon = createCoupon(CouponType.SHOPPING_SPREE);

    addToCart(new Item("Roleplaying Game", 1000, LocalDate.of(2010, 1, 20)));
    addToCart(new Item("Point and Click Adventure", 1000, LocalDate.of(2010, 1, 20)));
    addToCart(new Item("RTS", 1000, LocalDate.of(2010, 1, 20)));
    assertFalse(coupon.isApplicableOn(cart));

    addToCart(new Item("Promo Shooter", 0, LocalDate.of(2010, 1, 20)));
    assertFalse(coupon.isApplicableOn(cart));

    addToCart(new Item("Regular Shooter", 1, LocalDate.of(2010, 1, 20)));
    assertTrue(coupon.isApplicableOn(cart));
  }

  @Test
  public void shoppingSpreeShouldBeApplicableForMoreThan100Eur() {
    Coupon coupon = createCoupon(CouponType.SHOPPING_SPREE);

    addToCart(new Item("Roleplaying Game", 5000, LocalDate.of(2010, 1, 20)));
    addToCart(new Item("Point and Click Adventure", 5000, LocalDate.of(2010, 1, 20)));
    assertFalse(coupon.isApplicableOn(cart));

    addToCart(new Item("Shooter", 1, LocalDate.of(2010, 1, 20)));
    assertTrue(coupon.isApplicableOn(cart));
  }

  @Test
  public void classicsShouldBeApplicableIfAllGamesOlderThan2000() {
    Coupon coupon = createCoupon(CouponType.CLASSICS);

    addToCart(new Item("Roleplaying Game", 5000, LocalDate.of(1999, 1, 20)));
    addToCart(new Item("Point and Click Adventure", 5000, LocalDate.of(1999, 12, 20)));
    assertTrue(coupon.isApplicableOn(cart));

    addToCart(new Item("Shooter", 1, LocalDate.of(2000, 1, 1)));
    assertFalse(coupon.isApplicableOn(cart));
  }

  @Test
  public void holidayShouldNotBeApplicableIfCartHasNoCheckoutDate() {
    Coupon coupon = createCoupon(CouponType.HOLIDAY);
    assertFalse(coupon.isApplicableOn(cart));
  }

  @Test
  public void holidayShouldBeApplicableOnSummerBreak() {
    Coupon coupon = createCoupon(CouponType.HOLIDAY);

    cart.checkoutTime = LocalDateTime.of(2020, 5, 31, 23, 59);
    assertFalse(coupon.isApplicableOn(cart));

    cart.checkoutTime = LocalDateTime.of(2020, 6, 1, 0, 0);
    assertTrue(coupon.isApplicableOn(cart));

    cart.checkoutTime = LocalDateTime.of(2020, 6, 15, 0, 0);
    assertTrue(coupon.isApplicableOn(cart));

    cart.checkoutTime = LocalDateTime.of(2020, 7, 1, 0, 0);
    assertFalse(coupon.isApplicableOn(cart));
  }

  public static Coupon createCoupon(CouponType type) {
    return new Coupon(type);
  }

  private void addToCart(Item item) {
    cart.items.add(item);
  }
}
