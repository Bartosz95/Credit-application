package pl.inteca.customer.api;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T,I> {

    T save(T item);

    List<T> findAll();
}
