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
            final int index1 = rng.nextInt(t.getTour().size() - 1);
            final int index2 = rng.nextInt(t.getTour().size() - 1);
            final Location l1 = t.getTour().get(index1);
            final Location l2 = t.getTour().get(index2);
            List<Location> mutatedTour = new ArrayList<>();
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

//  public static final BiFunction<Tour, Tour, Tour>
//          crossover =
//          (t1, t2) -> {
//            final List<Location> locations1 = t1.getTour().subList(0, t1.getTour().size() / 2);
//            final List<Location> locations2 = t2.getTour().subList(t2.getTour().size() / 2, t2.getTour().size());
//            final List<Location> newLocations = new ArrayList<>();
//            newLocations.addAll(locations2);
//            newLocations.addAll(locations1);
//            System.out.println(newLocations);
//            Map<Location, Integer> occurences = new HashMap<>();
//            locations1.forEach(loc -> occurences.put(loc, 0));
//            locations2.forEach(loc -> occurences.put(loc, 0));
//            System.out.println(locations1 .size()+ locations2.size());
//           // occurences.forEach((k,v) -> System.out.println(k + " " + v));
//            newLocations.forEach(loc -> {
//              Integer cnt = occurences.get(loc);
////              System.out.println("putting " + loc + occurences.get(loc));
//              occurences.put(loc,cnt+1);
//            });
////            System.out.println(occurences);
//            return new Tour(new ArrayList<>(newLocations));
//          };

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
