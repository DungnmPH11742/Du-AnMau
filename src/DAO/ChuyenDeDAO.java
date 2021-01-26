package DAO;

import Entity.ChuyenDe;
import JDBCHelper.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    List<ChuyenDe> dsCD;
    JDBCHelper jdbc = new JDBCHelper();

    @Override
    public void insertCSDL(ChuyenDe entity) {
        jdbc.pstmHelper("Insert into chuyenDe values(?, ?, ?, ?, ?, ?)",
                entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa());
    }

    @Override
    public void updateCSDL(ChuyenDe entity) {
        jdbc.pstmHelper("update CHUYENDE\n"
                + "	set TENCD = ?, HOCPHI = ?, THOILUONG = ?, HINH = ?, MOTA = ? where MACD = ?", entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getHinh(), entity.getMoTa(), entity.getMaCD());
    }

    @Override
    public void deleteCSDL(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ChuyenDe> loadDataFull() {
        dsCD = new ArrayList<>();
        try {
            ResultSet rs = jdbc.selectAll("Select * from chuyenDe");
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString(1));
                cd.setTenCD(rs.getString(2));
                cd.setHocPhi(rs.getFloat(3));
                cd.setThoiLuong(rs.getInt(4));
                cd.setHinh(rs.getString(5));
                cd.setMoTa(rs.getString(6));
                dsCD.add(cd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsCD;
    }

    @Override
    public List<ChuyenDe> loadDataID(ChuyenDe entity) {
        dsCD = new ArrayList<>();
        try {
            ResultSet rs = jdbc.selectByID("Select * from chuyenDe where maCD = ?", entity.getMaCD());
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString(1));
                cd.setTenCD(rs.getString(2));
                cd.setHocPhi(rs.getFloat(3));
                cd.setThoiLuong(rs.getInt(4));
                cd.setHinh(rs.getString(5));
                cd.setMoTa(rs.getString(6));
                dsCD.add(cd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsCD;
    }

    @Override
    public List<ChuyenDe> loadDataKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
