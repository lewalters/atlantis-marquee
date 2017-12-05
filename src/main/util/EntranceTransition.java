package util;

public enum EntranceTransition implements EntranceEffect
{
    NONE("None"), RANDOM_ON("Random On"), FADE_IN("Fade In");

    private String description;

    EntranceTransition(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return description;
    }
}
