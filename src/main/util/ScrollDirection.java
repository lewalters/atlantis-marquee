package util;

public enum ScrollDirection implements EntranceEffect, ExitEffect
{
    UP("Scroll Up"), DOWN("Scroll Down"), LEFT("Scroll Left"), RIGHT("Scroll Right"), STATIC("Static");

    private String direction;

    ScrollDirection(String direction)
    {
        this.direction = direction;
    }

    @Override public String toString()
    {
        return direction;
    }
}
