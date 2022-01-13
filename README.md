# Collision Detection Algorithm Demo
 A visual demostration of KD trees spatial partitioning used for collision detection.

 This implementation does not actually generate a tree as i found it not nececarry for the purposes of this demo and instead only the lowest elements of the tree are saved and the iterrated upon. However making it generate a tree should be easily done with minor modifications 

Physics tickrate, framerate and toggling of the partitioning algorithm(as oposed to bruteforce collision check) is available in order to observe the effects of KD partitioning on performace.

My machine can handle about:

300-400 Enitites without KD partitioning

and 2000+ with KD partitioning
 
![image](https://user-images.githubusercontent.com/20630248/147418948-806cc019-d2b9-48e1-b5bc-1209edd7a21b.png)
