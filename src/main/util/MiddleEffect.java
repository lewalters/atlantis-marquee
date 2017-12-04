package util;

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
