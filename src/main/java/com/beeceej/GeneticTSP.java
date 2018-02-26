package com.beeceej;

import com.beeceej.util.Distance;
import com.beeceej.util.LoadCities;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneticTSP {

  private static final List<Location>
          locations = LoadCities.loadCities.apply("/home/bjones/genetic-tsp/locations.txt");
  private static final Map<String, Map<String, Double>>
          distanceMatrix = createDistanceMatrix(locations);

  private static Map<String, Map<String, Double>> createDistanceMatrix(List<Location> locationSet) {
    final Map<String, Map<String, Double>> distanceMatrix = new HashMap<>();
    locationSet.forEach(l -> distanceMatrix.put(l.getName(), new HashMap<>()));
    locationSet.forEach(l1 -> locationSet.stream()
            .filter(l2 -> !l2.equals(l1))
            .forEach(l2 -> distanceMatrix.get(l1.getName())
                    .put(l2.getName(), Distance.compute.apply(l1, l2))
            ));
    return distanceMatrix;
  }

  private static final Function<List<Location>, Tour>
          randomTour =
          locations -> {
            Random rng = new Random(System.nanoTime());
            final int N = locations.size();
            final Location[] route = new Location[N];
            Set<Integer> allocated = new HashSet<>();
            int current = 0;
            while (allocated.size() < N) {
              int random = rng.nextInt(N);
              if (!Objects.isNull(locations.get(random)) && !allocated.contains(random)) {
                route[current] = locations.get(random);
                current++;
                allocated.add(random);
              }
            }
            return new Tour(route);
          };
  private static final BiFunction<Location, Location, Double>
          getDistance = (l1, l2) -> distanceMatrix.get(l1.getName()).get(l2.getName());
  private static final Function<Integer, List<Tour>>
          seed = poolSize -> {
    final List<Tour> tours = IntStream.range(0, poolSize)
            .mapToObj(i -> randomTour.apply(locations))
            .collect(Collectors.toList());

    return tours;
  };

  private static final Function<Tour, TourResult>
          doTour = tour -> new TourResult(IntStream.range(0, tour.getTour().size() - 1)
          .mapToDouble(i -> getDistance.apply(tour.getTour().get(i), tour.getTour().get(i + 1)))
          .sum(), tour);


  public static void main(String[] args) {
    final int genCount = 1000;
    final int seedPopulationCount = locations.size() * 1000;
    final List<Tour> population = seed.apply(seedPopulationCount);

    List<TourStats> stats = IntStream.range(0, genCount).mapToObj(i -> population.stream()
            .map(Tour.mutate)
            .reduce(new TourStats(), (tourStats, tour) -> {
              final TourResult result = doTour.apply(tour);
              boolean isShortest = result.getDistance() <= tourStats.getShortest().getDistance();
              boolean isLongest = result.getDistance() >= tourStats.getLongest().getDistance();
              final List<TourResult> tours = new ArrayList<>();
              tours.add(result);
              tours.addAll(tourStats.getTours());
              System.out.println(tours.size());
              return new TourStats(
                      isLongest ? result : tourStats.getLongest(),
                      isShortest ? result : tourStats.getShortest(),
                      tours);
            }, (tS, tS2) -> tS.getShortest().getDistance() < tS2.getShortest().getDistance() ? tS : tS2))
            .map(s -> new TourStats(
                    s.getLongest(),
                    s.getShortest(),
                    s.getTours().stream()
                            .filter(ts -> ts.getDistance() <= s.getAverage())
                            .collect(Collectors.toList())
            )).collect(Collectors.toList());

    System.out.println("Shortest | " +
            stats.stream().min(Comparator.comparingDouble(a -> a.getShortest().getDistance()))
                    .map(TourStats::getShortest).get());
    System.out.println("Longest | " +
            stats.stream().max(Comparator.comparingDouble(a -> a.getLongest().getDistance()))
                    .map(TourStats::getLongest).get());
    System.out.println("Average | " +
            stats.stream().mapToDouble(TourStats::getAverage).average().getAsDouble());
  }
}
