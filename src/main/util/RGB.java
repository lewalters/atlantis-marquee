package util;

public class RGB
{
    private double r, g, b, o;
    private int area;

    public RGB(int height, int width)
    {
        r = 0;
        g = 0;
        b = 0;
        o = 0;
        area = height * width;
    }

    public void addRed(double r)
    {
        this.r += r;
    }

    public void addGreen(double g)
    {
        this.g += g;
    }

    public void addBlue(double b)
    {
        this.b += b;
    }

    public void addOpacity(double o)
    {
        this.o += o;
    }

    public double getRed()
    {
        return r / area;
    }

    public double getGreen()
    {
        return g / area;
    }

    public double getBlue()
    {
        return b / area;
    }

    public double getOpacity()
    {
        return o / area;
    }
}
