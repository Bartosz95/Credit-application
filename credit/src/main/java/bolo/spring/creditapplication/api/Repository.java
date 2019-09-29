package bolo.spring.creditapplication.api;

import java.util.List;

public interface Repository<T,I> {

    T save(T item);

    List<T> findAll();
}
