package database.utils;

import java.util.List;

public interface DAOSelects<E, T> {

    List<String> selectStringForAutoComplete();

    List<E> selectByParent(T parent);

    List<E> selectByInterval(T ini, T end);
}
