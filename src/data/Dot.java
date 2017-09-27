package data;

/**
 * (Insert a brief comment that describes
 * the purpose of this class definition.)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public class Dot
{
    private String color;
    private int intensity;

    public Dot(String color, int intensity)
    {
        this.color = color;
        this.intensity = intensity;
    }

    public String getColor()
    {
        return color;
    }

    public double getIntensity()
    {
        return intensity / 100.0;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public void setIntensity(int intensity)
    {
        this.intensity = intensity;
    }
}