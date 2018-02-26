package com.beeceej.util;

import com.beeceej.Location;

import java.util.function.BiFunction;

public class Distance {
  public static final BiFunction<Location, Location, Double>
          compute = (a, b) -> Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
}
