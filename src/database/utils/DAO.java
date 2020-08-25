package database.utils;

import java.sql.SQLException;
import java.util.List;

public interface DAO<E, I> {
    /*Lançar "NotImplementedException" caso não use algum método*/

    void save(E model) throws SQLException;

    void update(E model);

    void delete(E model);

    E selectById(I id);

    List<E> selectAll();

    List<E> selectAllByArg(I arg);

    List<E> selectByArgs(I... args); /*Usar somente quando necessário*/
}
