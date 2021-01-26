package DAO;

import Entity.NhanVien;
import JDBCHelper.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    JDBCHelper jdbc = new JDBCHelper();
    List<NhanVien> dsNV;

    @Override
    public void insertCSDL(NhanVien entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateCSDL(NhanVien entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCSDL(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NhanVien> loadDataFull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<NhanVien> loadDataID(NhanVien entity) {
        try {
            dsNV = new ArrayList<>();
            ResultSet rs = jdbc.selectByID("select * from NHANVIEN where MANV = ? and MATKHAU = ?", entity.getMaNV(), entity.getMatKhau());
            while (rs.next()) {
                dsNV.add(new NhanVien(rs.getString(1), rs.getString(2), "", rs.getBoolean(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsNV;
    }

    @Override
    public List<NhanVien> loadDataKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
