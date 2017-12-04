package util;

public enum BorderEffect
{
    NONE("None"), BLINK("Blinking"), CLOCKWISE("Clockwise"), COUNTERCLOCKWISE("Counterclockwise");

    private String description;

    BorderEffect(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return description;
    }
}
