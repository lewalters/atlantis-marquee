package util;

import javafx.beans.property.BooleanProperty;

import java.util.Deque;

public interface ObservableDeque<E> extends Deque<E>
{
    BooleanProperty emptyProperty();
}
