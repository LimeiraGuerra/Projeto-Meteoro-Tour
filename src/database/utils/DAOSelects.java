package database.utils;

import java.util.List;

public interface DAOSelects<E, T> {
    /*Lançar "NotImplementedException" caso não use algum método*/

    List<E> selectByParent(T parent);

    List<E> selectByInterval(T ini, T end);
}
