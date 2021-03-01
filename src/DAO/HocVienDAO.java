package DAO;

import Entity.HocVien;
import JDBCHelper.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HocVienDAO extends EduSysDAO<HocVien, String> {

    ResultSet rs;
    List<HocVien> dsHV;
    JDBCHelper jdbc = new JDBCHelper();

    @Override
    public void insertCSDL(HocVien entity) {
        jdbc.pstmHelper("insert into HOCVIEN(maKH, maNH, diem) values(?,?,?)", entity.getMaKH(), entity.getMaNH(), entity.getDiem());
    }

    @Override
    public void updateCSDL(HocVien entity) {
        jdbc.pstmHelper("Update hocVien set Diem = ? where maKH = ? and maNH = ?", entity.getDiem(), entity.getMaKH(), entity.getMaNH());
    }

    @Override
    public void deleteCSDL(String key) {
        jdbc.pstmHelper("Delete hocVien where maHV = ?", key);
    }

    @Override
    public List<HocVien> loadDataFull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HocVien> loadDataID(HocVien entity) {
        dsHV = new ArrayList<>();
        try {
            rs = jdbc.selectByID("Select * from HocVien where maKH = ?", entity.getMaKH());
            while (rs.next()) {
                dsHV.add(new HocVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4)));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHV;
    }

    @Override
    public List<HocVien> loadDataKey(String key) {
        dsHV = new ArrayList<>();
        try {
            rs = jdbc.selectByID("Select * from HocVien where maKH = '" + key + "'");
            while (rs.next()) {
                dsHV.add(new HocVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4)));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHV;
    }

    public List<HocVien> loadDataIDHV(HocVien entity) {
        dsHV = new ArrayList<>();
        try {
            rs = jdbc.selectByID("Select * from HocVien where maKH = ? and maNH = ?", entity.getMaKH(), entity.getMaNH());
            while (rs.next()) {
                dsHV.add(new HocVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getFloat(4)));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsHV;
    }

}
