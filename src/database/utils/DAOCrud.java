package database.utils;

import java.sql.SQLException;
import java.util.List;

public interface DAOCrud<E, I> {
    /**Lançar "NotImplementedException" caso não use algum método*/


    void save(E model) throws SQLException;

    void update(E model);

    void delete(E model);

    E selectById(I id);

    List<E> selectAll();

    List<E> selectAllByKeyword(I key);
}
