package JDBCHelper;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectHelper {

    static Connection cn;
    static String userName = "sa", pass = "123";
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=polyPro";

    public static Connection Connected() {
        if (cn == null) {
            openConnection();
        }
        return cn;
    }

    //Phương thức kết nối
    public static void openConnection() {
        try {
            cn = DriverManager.getConnection(url, userName, pass);
            System.out.println("Thành công");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
