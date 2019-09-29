package pl.inteca.product.api;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T,I> {

    T save(T item) throws ClassNotFoundException;

    List<T> findAll();
}
