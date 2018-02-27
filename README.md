# Genetic Algorithm - TSP

This project heavily uses Java 8 features like Streams, First Class(ish) functions, map, reduce, bind(flatmap), etc.

The Travelling Salesman problem can be stated as:
```
Given locations (A(x,y), B(x,y), ... ) with distinct locations on a directed acyclic graph,
find the least costly path (in this case distance) to traverse from starting point A to end point. 
```

my (loose) interpretation of a genetic algorithm eventually converges upon an optimal solution by:

`-> Begin with a randomly generated seed Population of size N`

`-> For M generations, perform random mutations on the traversal order, keeping only the most efficient routes (less than averagte)`

`-> After M generations, we will have converged upon a near optimal solution in much less time than exhaustive search`