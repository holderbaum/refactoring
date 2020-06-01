package io.holderbaum;

import java.time.LocalDate;

public class Coupon {

  private CouponType type;

  public Coupon(CouponType type) {
    this.type = type;
  }

  public boolean isApplicableOn(Cart cart) {
    switch (type) {
      case SHOPPING_SPREE:
        int tp = 0;
        for (Item i : cart.items) {
          tp += i.price;
        }
        return cart.items
            .stream()
            .filter(i -> i.price > 0)
            .count() >= 4
            || tp > 10000;
      case BULK:
        int bulk = 0;
        for (Item i : cart.items) {
          bulk += i.price;
        }
        return cart.items.size() >= 3
            || bulk > 5000;
      case CLASSICS:
        return cart.items
            .stream()
            .allMatch(n -> n
                .releaseDate
                .isBefore(
                    LocalDate.of(2000, 1, 1)
                )
            );
      case HOLIDAY:
        return cart.checkoutTime != null
            && cart.checkoutTime.getMonthValue() == 6;
    }
    return false;
  }
}
