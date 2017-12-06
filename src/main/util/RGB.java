package util;

public class RGB
{
    private double r, g, b, o, area;

    public RGB(double height, double width)
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
        return r / area > 1 ? 1 : r / area;
    }

    public double getGreen()
    {
        return g / area > 1 ? 1 : g / area;
    }

    public double getBlue()
    {
        return b / area > 1 ? 1 : b / area;
    }

    public double getOpacity()
    {
        return o / area > 1 ? 1 : o / area;
    }
}
