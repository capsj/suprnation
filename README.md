# Suprnation Coding Exercise

This program will find minimum/maximum paths for triangles and print them out to standard output. 

## How to run

There are a couple of ways in which you can choose to run this coding exercise.
1. Executing the jar file included in the top-level directory providing the input:
```
cat << EOF | java -jar MinTrianglePath.jar
```

2.  Executing the jar file with no input will trigger you to provide a triangle:
```
java -jar MinTrianglePath.jar
```

3. As an `sbt` main class:
```
sbt "runMain suprnation.TriangleApp"
```

4. If you wish to generate your own version of the jar you can do so by running:
```
sbt "assembly"
java -jar target/scala-2.13/MinTrianglePath.jar
```

Just enter a blank line whenever you wish to stop providing input. 

## Tests

In order to run the tests:
```
sbt test
```

## Notes

I had initially solved it by traversing the entire input once it was fully known.
After reading from the file I had the idea to stream the lines and compute the `Path`s as new lines were processed. 
I got to implement the solution processing one line at a time, but did not get around to use any streaming tools.
