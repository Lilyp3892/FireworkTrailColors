package me.spiral_cat.fireworktrailcolors;

public class TrailColor //sets up format and setters and getters this file was made by Visparu, so I don't really mess with it
{
    private int red;
    private int green;
    private int blue;
    private boolean on;

    public TrailColor()
    {

    }

    public TrailColor(int r, int g, int b, boolean o)
    {
        this.red   = r;
        this.green = g;
        this.blue  = b;
        this.on    = o;
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }

    public boolean getOn()
    {
        return on;
    }

    public void setRed(int red)
    {
        this.red = red;
    }

    public void setGreen(int green)
    {
        this.green = green;
    }

    public void setBlue(int blue)
    {
        this.blue = blue;
    }

    public void setOn(boolean on)
    {
        this.on = on;
    }

    @Override
    public String toString()
    {
        return String.format("%d/%d/%d/%b", this.red, this.green, this.blue, this.on);
    }
}