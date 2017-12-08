package util;

/**
 * Describes the possible special effects while the Segment is
 * fully displayed on the screen (between entrance and exit)
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public enum MiddleEffect
{
    NONE("None"), BLINK("Blinking"), INVERT("Inverted Blinking"), RANDOM_COLOR("Random Colors");

    private String description;

    MiddleEffect(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return description;
    }
}
