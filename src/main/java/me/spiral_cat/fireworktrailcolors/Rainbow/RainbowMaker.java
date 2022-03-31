package me.spiral_cat.fireworktrailcolors.Rainbow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RainbowMaker {
    public static java.util.List<Color> InitRainbow() { //is called in FireworkTrailColors
        List<Color> colors = new ArrayList<>(); //makes rainbow rgb list

        for (int b = 0; b < 100; b++) colors.add(new Color(255, 0, b * 255 / 100));  //starts red goes to red-blue     [1]
        for (int r = 100; r > 0; r--) colors.add(new Color(r * 255 / 100, 0, 255));  //starts red-blue goes to blue    [2]
        for (int g = 0; g < 100; g++) colors.add(new Color(0, g * 255 / 100, 255));  //starts blue goes to green-blue  [3]
        for (int b = 100; b > 0; b--) colors.add(new Color(0, 255, b * 255 / 100));  //starts green-blue goes to green [4]
        for (int r = 0; r < 100; r++) colors.add(new Color(r * 255 / 100, 255, 0));  //starts green goes to red-green  [5]
        for (int g = 100; g > 0; g--) colors.add(new Color(255, g * 255 / 100, 0));  //starts red-green goes to red    [6]
        colors.add(new Color(255, 0, 0));  //adds red to the end just cuz
        return(colors); //returns the list
    }
}
