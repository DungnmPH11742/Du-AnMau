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
        jdbc.pstmHelper("Insert into NHANVIEN values (?, ?, ?, ?)", entity.getMaNV(), entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro());
    }

    @Override
    public void updateCSDL(NhanVien entity) {
        jdbc.pstmHelper("Update NhanVien set matKhau = ?, hoTen = ?, vaiTro = ? where maNV = ?", entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void deleteCSDL(String key) {
        jdbc.pstmHelper("EXEC deleteNV ?", key);
    }

    @Override
    public List<NhanVien> loadDataFull() {
        dsNV = new ArrayList<>();
        try {
            ResultSet rs = jdbc.selectAll("Select * from NhanVien");
            while (rs.next()) {
                dsNV.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsNV;
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
        try {
            dsNV = new ArrayList<>();
            ResultSet rs = jdbc.selectByID("select * from NHANVIEN where MANV = ?", key);
            while (rs.next()) {
                dsNV.add(new NhanVien(rs.getString(1), rs.getString(2), "", rs.getBoolean(4)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dsNV;
    }

}
