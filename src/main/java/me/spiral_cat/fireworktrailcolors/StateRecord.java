package me.spiral_cat.fireworktrailcolors;

import java.util.Map;
import java.util.UUID;

public class StateRecord {//sets up StateRecord help format data into json file structures
    private Map<UUID, TrailColor> colorMap;

    public Map<UUID, TrailColor> getColorMap()
    {
        return colorMap;
    }

    public void setColorMap(Map<UUID, TrailColor> colorMap)
    {
        this.colorMap = colorMap;
    }

}
