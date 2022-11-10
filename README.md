# Collision Detection Algorithm Demo
 A visual demostration of K/D tree spatial partitioning used for collision detection.
 
 Collision detection with a large number of objects/particles very quickly get very expensive to compute since every object has to be checked against every other object. This is an implementation and a visual demonstartion of an algorithm that splits the space into subareas based on object's median positions, the result of this is that instead of every object requiring to bruteforce check every other object, they only end up checking only a couple nearby objects.


Physics tickrate, framerate and toggling of the partitioning algorithm(as oposed to bruteforce collision check) is available in order to observe the effects of the algorithm on performace.


 
![image](https://user-images.githubusercontent.com/20630248/147418948-806cc019-d2b9-48e1-b5bc-1209edd7a21b.png)
