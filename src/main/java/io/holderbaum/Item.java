package io.holderbaum;

import java.time.LocalDate;

public class Item {

  public String name;
  public int price;
  public LocalDate releaseDate;

  public Item(String name, int price, LocalDate releaseDate) {
    this.name = name;
    this.price = price;
    this.releaseDate = releaseDate;
  }
}
