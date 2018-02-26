package com.beeceej;

import java.util.Objects;

public class Location {

  private final Double x;
  private final Double y;
  private final String name;

  public Location(final Double x, final Double y, final String name) {
    this.x = x;
    this.y = y;
    this.name = name;
  }

  public Double getX() {
    return x;
  }

  public Double getY() {
    return y;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(name, location.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(y, x, name);
  }

  @Override
  public String toString() {
    return name + " ";
  }
}
