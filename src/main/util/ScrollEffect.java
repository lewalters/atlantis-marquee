package util;

/**
 * Describes the possible types of special effect scrolling a Segment can be displayed with
 * <p>
 * <p/> Bugs: None known
 *
 * @author Team Atlantis
 */
public enum ScrollEffect implements EntranceEffect, ExitEffect
{
    SPLIT_SCROLL_HORIZONTAL("Split Scroll Horizontal"), SPLIT_SCROLL_VERTICAL("Split Scroll Vertical"),
    HALF_SCROLL_TOP_LEFT("Half Scroll Top Left"), HALF_SCROLL_TOP_RIGHT("Half Scroll Top Right"),
    HALF_SCROLL_LEFT_UP("Half Scroll Left Up"), HALF_SCROLL_LEFT_DOWN("Half Scroll Left Down");

    private String description;

    ScrollEffect(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return description;
    }
}
