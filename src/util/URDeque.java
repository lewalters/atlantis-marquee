package util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayDeque;

public class URDeque<E> extends ArrayDeque<E> implements ObservableDeque<E>
{
    private BooleanProperty empty = new SimpleBooleanProperty();

    public URDeque()
    {
        empty.set(isEmpty());
    }

    @Override
    public void push(E e)
    {
        super.push(e);
        empty.set(isEmpty());
    }

    @Override
    public E pop()
    {
        E e = super.pop();
        empty.set(isEmpty());
        return e;
    }

    @Override
    public BooleanProperty emptyProperty()
    {
        return empty;
    }
}