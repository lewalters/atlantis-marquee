package data;

import javafx.scene.paint.Color;

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
    private Color color;
    private int intensity;

    public Dot(Color color, int intensity)
    {
        this.color = color;
        this.intensity = intensity;
    }

    public Color getColor()
    {
        return color;
    }

    public int getIntensity()
    {
        return intensity;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setIntensity(int intensity)
    {
        this.intensity = intensity;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Dot)
        {
            Dot dot = (Dot) obj;
            return this.color.equals(dot.color) && this.intensity == dot.intensity;
        }

        return false;
    }
}