# Assumptions

## Input 

- Triangles will be composed of nodes with only `Int` values to simplify the problem.
- Triangles will contain only one minimum path as stated in the exercise.

## Resources

- The amount of memory allocated to the process will be enough to store the list of `Path`s. 
This list will grow as much as: 
  - N=2^(H−1) `Path`s, where H is the triangle's height.
  - Each `Path` will in turn include every `Int` node within the path (H elements)




