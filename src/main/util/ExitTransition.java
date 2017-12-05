package util;

public enum ExitTransition implements ExitEffect
{
    NONE("None"), RANDOM_OFF("Random Off"), FADE_OUT("Fade Out");

    private String description;

    ExitTransition(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return description;
    }
}
