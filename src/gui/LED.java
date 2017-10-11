package gui;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static util.Global.OFF_COLOR;

public class LED extends Circle
{
    private boolean on;
    private Paint paint;

    public LED(int radius)
    {
        super(radius, OFF_COLOR);
        on = false;
    }

    public boolean isOn()
    {
        return on;
    }

    public void turnOn(String color, int intensity)
    {
        paint = Color.web(color, intensity / 100.0);
        this.setFill(paint);
        on = true;
    }

    public void turnOn(Paint paint)
    {
        this.paint = paint;
        this.setFill(paint);
        on = true;
    }

    public void turnOn()
    {
        this.setFill(paint);
        on = true;
    }

    public void turnOff()
    {
        this.setFill(OFF_COLOR);
        on = false;
    }
}
