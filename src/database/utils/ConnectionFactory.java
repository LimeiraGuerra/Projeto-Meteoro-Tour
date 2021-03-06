package database.utils;

import java.sql.*;

public class ConnectionFactory implements AutoCloseable{

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;

    public  static Connection createConnection() {
        try {
            if(connection == null)
                connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            connection.setAutoCommit(true);
        }
        catch (SQLException e) { e.printStackTrace();}
        return connection;
    }

    public static PreparedStatement createPreparedStatement(String sql) {
        try {preparedStatement = createConnection().prepareStatement(sql);}
        catch (SQLException e) { e.printStackTrace();}
        return preparedStatement;
    }

    public  static Statement createStatement() {
        try {statement = createConnection().createStatement();}
        catch (SQLException e) { e.printStackTrace();}
        return statement;
    }

    public static void closeStatements(Statement... stmts){
        for (Statement stmt : stmts) {
            try {stmt.close(); }
            catch (SQLException throwables) { throwables.printStackTrace(); }
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void executeRollBack(){
        if(connection != null){
            try { connection.rollback();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void close() throws Exception {
        if(connection != null){
            connection.close();
            if(preparedStatement != null)
                preparedStatement.close();
            if(statement != null)
                statement.close();
        }
    }
}