package sk.westland.core.hibernate;

import java.util.Optional;

public interface AsyncCallBackObject<T> {

    void done(Optional<T> result);
}
