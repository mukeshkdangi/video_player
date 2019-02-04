# video_player
> Programming Part:
This assignment will help you gain a practical understanding of issues that relate to video resampling, spatial and temporal aliasing effects, image aspect ratios and pixel aspect ratios. In this assignment you will be given a videofileas input and produce a spatially & temporally resampled output.

> How to run 

``` 
javac *.java
java videoPlayer <path_to_video_file> <width_scaling_factor> <heigh_scaling_factor> <frame_per_sec> <analysis_i/p>
```

`
Where video should be raw rrr... ggg... bbbb... format and 
-analysis input =0 if normal scaling up and down 
-analysis input =1 if anti-aliasing scaling up and down 
`


![Results](https://github.com/mukeshkdangi/video_player/blob/master/src/pics1.png)

![Results](https://github.com/mukeshkdangi/video_player/blob/master/src/pics2.png)

![Results](https://github.com/mukeshkdangi/video_player/blob/master/src/pics4.png)

![Results](https://github.com/mukeshkdangi/video_player/blob/master/src/pics5.png)
