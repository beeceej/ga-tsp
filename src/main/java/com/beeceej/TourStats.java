package com.beeceej;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TourStats {
  private final TourResult longest;
  private final TourResult shortest;
  private final List<TourResult> tours;
  private final double average;

  public TourStats(TourResult longest, TourResult shortest, List<TourResult> tours) {
    this.longest = longest;
    this.shortest = shortest;
    this.average = tours.stream()
            .mapToDouble(TourResult::getDistance)
            .average()
            .orElse(0.0);
    this.tours = tours;
  }

  public TourStats() {
    this.longest = new TourResult(0.0, new Tour());
    this.shortest = new TourResult(Integer.MAX_VALUE, new Tour());
    this.average = 0.0;
    this.tours = Collections.emptyList();
  }

  public TourResult getLongest() {
    return longest;
  }

  public TourResult getShortest() {
    return shortest;
  }

  public double getAverage() {
    return average;
  }

  public List<TourResult> getTours() {
    return tours;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TourStats tourStats = (TourStats) o;
    return Double.compare(tourStats.average, average) == 0 &&
            Objects.equals(longest, tourStats.longest) &&
            Objects.equals(shortest, tourStats.shortest) &&
            Objects.equals(tours, tourStats.tours);
  }

  @Override
  public int hashCode() {
    return Objects.hash(longest, shortest, tours, average);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    tours.forEach(t -> sb.append(t.toString()).append("\n"));
    return "TourStats{" +
            "longest=" + longest +
            ", shortest=" + shortest +
            ", tours=" + sb.toString() +
            ", average=" + average +
            '}';
  }
}
