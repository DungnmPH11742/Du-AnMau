package JDBCHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCHelper {

    Connection cn = new ConnectHelper().Connected();

    //
    public void pstmHelper(String sql, Object... arg) {
        PreparedStatement pstm = null;
        try {
            if (sql.trim().split("\\s")[0].equalsIgnoreCase("exec")) {
                pstm = cn.prepareCall(sql);
            } else {
                pstm = cn.prepareStatement(sql);
            }
            for (int i = 0; i < arg.length; i++) {
                pstm.setObject(i + 1, arg[i]);
            }
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //lấy tất cả dữ liệu
    public ResultSet selectAll(String sql) {
        ResultSet rs = null;
        try {
            Statement stm = cn.createStatement();
            rs = stm.executeQuery(sql);
        } catch (Exception e) {
        }
        return rs;
    }

    //Lấy dữ liệu theo điều kiện
    public ResultSet selectByID(String sql, Object... arg) {
        ResultSet rs = null;
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            int i = 0;
            while (i < arg.length) {
                pstm.setObject(i + 1, arg[i]);
                i++;
            }
            rs = pstm.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rs;
    }

}
