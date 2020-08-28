package database.utils;

import java.util.List;

public interface DAOCrud<E, I> {

    void save(E model);

    void update(E model);

    void delete(E model);

    E selectById(I id);

    List<E> selectAll();

    List<E> selectAllByKeyword(I key);
}
