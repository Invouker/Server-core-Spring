package sk.westland.core.hibernate;

import java.util.List;

public interface AsyncCallBackObjectList<T> {

    void done(List<T> result);
}
