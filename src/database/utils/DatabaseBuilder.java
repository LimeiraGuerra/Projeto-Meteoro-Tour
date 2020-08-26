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
        }
    }
}
