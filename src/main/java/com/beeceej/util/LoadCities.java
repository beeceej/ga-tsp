package com.beeceej.util;

import com.beeceej.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LoadCities {

  private static final Function<String, Optional<File>>
          openFile = fname -> Optional.of(new File(fname));

  private static final Function<Optional<File>, Optional<Boolean>>
          canRead = maybeFile -> maybeFile.flatMap(file -> Optional.of(file.exists() && file.canRead()));

  private static final Function<String, Location>
          parseLocation =
          line -> {
            try {
              final String[] split = line.split(",");
              return new Location(Double.parseDouble(split[0]), Double.parseDouble(split[1]), split[2]);
            } catch (Exception e) {
              throw new RuntimeException(e.getMessage() + "invalid input format");
            }
          };

  private static final Function<Optional<File>, List<Location>>
          readLocations =
          maybeFile -> maybeFile.flatMap(f -> {
            try {
              return Optional.of(new BufferedReader(new FileReader(f))
                      .lines()
                      .map(parseLocation)
                      .collect(Collectors.toList()));
            } catch (FileNotFoundException e) {
              throw new RuntimeException(e.getMessage());
            }
          }).orElse(Collections.emptyList());

  public static final Function<String, List<Location>>
          loadCities = fname ->
          openFile.apply(fname)
                  .flatMap(file -> canRead.apply(Optional.of(file))
                          .flatMap(readable -> {
                            if (readable) {
                              return Optional.ofNullable(readLocations.apply(Optional.of(file)));
                            } else {
                              return Optional.empty();
                            }
                          })
                  ).orElse(Collections.emptyList());
}
