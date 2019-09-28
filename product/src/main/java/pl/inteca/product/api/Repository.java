package pl.inteca.product.api;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T,I> {

    T save(T item) throws ClassNotFoundException, SQLException;

    List<T> findAll() throws ClassNotFoundException, SQLException;

    T findAllById(I id);

    void deleteById(I id);

    void delete(T item);

    void deleteAll();
}
