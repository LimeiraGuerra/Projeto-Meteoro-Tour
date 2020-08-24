package database.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

public class DatabaseBuilder {
    public static void main(String[] args) {
        clear();
        build();
    }
    
    private static void clear() {
        System.out.println("Cleaning up...");
        try {
            Files.deleteIfExists(Paths.get("database.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void build(){
        try (Statement stmt = ConnectionFactory.createStatement();
             Scanner scanner = new Scanner(new File("database.sql"))) {
            scanner.useDelimiter(";");
            while (scanner.hasNext())
                stmt.addBatch(scanner.next());
            stmt.executeBatch();
            stmt.close();
            System.out.println("Database has been created ...");
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("tá entrando aqui");
        }
    }

    /*private static void build() {
        try (Statement stmt = ConnectionFactory.createStatement()) {
            stmt.addBatch("CREATE TABLE Declaracao (\n" +
                    "\tid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tvalor_pago REAL,\n" +
                    "\ttipo INTEGER NOT NULL,\n" +
                    "\trenda REAL);");
            stmt.addBatch("CREATE TABLE Deducao (\n" +
                    "\tid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\tdescricao TEXT,\n" +
                    "\tcnpj TEXT,\n" +
                    "\tinstituicao TEXT,\n" +
                    "\tconselho TEXT,\n" +
                    "\tvalor REAL);");
            stmt.executeBatch();
            stmt.close();
            System.out.println("Database has been created ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
}
