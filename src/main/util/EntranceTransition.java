package util;

/**
 * Describes the possible entrance special effects that aren't related to scrolling
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
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
