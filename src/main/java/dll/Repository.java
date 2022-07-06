package dll;

import java.util.List;

public interface Repository<T> {
    List<T> getAll() ;

    void save(T t);

    void remove(Long id);

    void update(T t);
}
