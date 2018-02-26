package com.beeceej;

import java.util.Arrays;
import java.util.Objects;

public class TourResult {
  private final double distance;
  private final Tour tour;

  public TourResult(double distance, Tour tour) {
    this.distance = distance;
    this.tour = tour;
  }

  public double getDistance() {
    return distance;
  }

  public Tour getTour() {
    return tour;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TourResult that = (TourResult) o;
    return Double.compare(that.distance, distance) == 0 &&
            Objects.equals(tour, that.tour);
  }

  @Override
  public int hashCode() {
    return Objects.hash(distance, tour);
  }

  @Override
  public String toString() {
    return "TourResult{" +
            "distance=" + distance +
            ", tour=" + Arrays.toString(tour.getTour().toArray()) +
            '}';
  }
}
