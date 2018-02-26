package com.beeceej;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tour {

  final private List<Location> tour;

  public Tour(final Location... locations) {
    this.tour = Stream.of(locations).collect(Collectors.toList());
  }

  public Tour(final List<Location> locations) {
    this.tour = locations;
  }

  public List<Location> getTour() {
    return tour;
  }

  public static final Function<Tour, Tour>
          mutate =
          t -> {
            final Random rng = new Random(System.nanoTime());
            final Location l1 = t.getTour().get(rng.nextInt(t.getTour().size() - 1));
            final Location l2 = t.getTour().get(rng.nextInt(t.getTour().size() - 1));
            final List<Location> mutatedTour = new ArrayList<>();
            t.getTour().forEach(location -> {
              if (location.equals(l1)) {
                mutatedTour.add(l2);
              } else if (location.equals(l2)) {
                mutatedTour.add(l1);
              } else {
                mutatedTour.add(location);
              }
            });
            return new Tour(mutatedTour);
          };

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tour tour1 = (Tour) o;
    return Objects.equals(tour, tour1.tour);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tour);
  }

  @Override
  public String toString() {
    return Arrays.toString(tour.toArray());
  }
}
