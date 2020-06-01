package io.holderbaum;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Cart {

  public List<Item> items = new LinkedList<>();
  public LocalDateTime checkoutTime;

}
