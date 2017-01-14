package sample;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by nur8sultan on 21.12.16.
 */


class SelectApp {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite: way to database";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void selectAll(){
        String sql = "SELECT * FROM Words";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getString("Word1") +  "\t" +
                        rs.getString("Word2"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SelectApp app = new SelectApp();
        app.selectAll();
    }

}