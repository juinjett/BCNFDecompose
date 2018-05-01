# BCNF decompostion algorithm
This is a project implementing BCNF decompostion algorithm in Java.
Given a relation R and functional dependency FD, the program should 
return the decomposed RF pair that has no BCNF violation.

The example input has the format:

A B C D E
A B -> C
C -> D
D -> B E

The output should has the same format.

I have my own BCNFTestDrive.class so you can directly run the .jar that I provide.

Usage:
In your terminal, run the following command:

java -jar BCNFDecomposition.jar <input dir> <output dir>

Here I provide four input test files in testFiles/ and also put four output files in testFiles/. 
You can create your own test files in testFiles.
