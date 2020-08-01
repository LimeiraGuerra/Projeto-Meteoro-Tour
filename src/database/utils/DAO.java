package database.utils;

import java.util.List;

public interface DAO<E, I> {

    void save(E model);

    void update(E model);

    void delete(E model);

    List<E> selectById(I id);

    List<E> selectByArgs();/* todo, tem que fazer pra receber parametros diferenciados, tipo strings para o trecho */
}
