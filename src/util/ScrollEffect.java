package util;

public enum ScrollEffect implements EntranceEffect, ExitEffect
{
    SPLIT_SCROLL_HORIZONTAL("Horizontal Split Scroll"), SPLIT_SCROLL_VERTICAL("Vertical Split Scroll"),
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