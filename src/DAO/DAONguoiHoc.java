package DAO;

import Entity.NguoiHoc;
import JDBCHelper.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAONguoiHoc extends EduSysDAO<NguoiHoc, String> {

    List<NguoiHoc> dsNH;
    JDBCHelper jdbc = new JDBCHelper();

    @Override
    public void insertCSDL(NguoiHoc entity) {
        jdbc.pstmHelper("insert into NguoiHoc values(?, ?, ? ,? ,? ,? ,? ,? ,?)",
                entity.getMaNH(), entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(), entity.getDienThoai(),
                entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void updateCSDL(NguoiHoc entity) {
        jdbc.pstmHelper("Update NguoiHoc set hoTen = ?, NgaySinh = ?, gioiTinh = ?, dienThoai = ?, email = ?, ghiChu = ? where maNH = ?",
                entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(), entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNH());
    }

    @Override
    public void deleteCSDL(String key) {
        jdbc.pstmHelper("exec deleteNH ?", key);
    }

    @Override
    public List<NguoiHoc> loadDataFull() {
        ResultSet rs = null;
        dsNH = new ArrayList<>();
        try {
            rs = jdbc.selectAll("select * from NguoiHoc");
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setNgaySinh(rs.getString(3));
                nh.setGioiTinh(rs.getBoolean(4));
                nh.setDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNV(rs.getString(8));
                nh.setNgayDK(rs.getString(9));
                dsNH.add(nh);
            }
        } catch (Exception e) {
        }
        return dsNH;
    }

    @Override
    public List<NguoiHoc> loadDataID(NguoiHoc entity) {
        ResultSet rs = null;
        dsNH = new ArrayList<>();
        try {
            rs = jdbc.selectByID("Select * from NguoiHoc where maNh = ?", entity.getMaNH());
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setNgaySinh(rs.getString(3));
                nh.setGioiTinh(rs.getBoolean(4));
                nh.setDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNV(rs.getString(8));
                nh.setNgayDK(rs.getString(9));
                dsNH.add(nh);
            }
        } catch (Exception e) {
        }
        return dsNH;
    }

    @Override
    public List<NguoiHoc> loadDataKey(String key) {
        ResultSet rs = null;
        dsNH = new ArrayList<>();
        try {
            rs = jdbc.selectByID("Select * from NguoiHoc where hoTen like ?", "%" + key + "%");
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setNgaySinh(rs.getString(3));
                nh.setGioiTinh(rs.getBoolean(4));
                nh.setDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNV(rs.getString(8));
                nh.setNgayDK(rs.getString(9));
                dsNH.add(nh);
            }
        } catch (Exception e) {
        }
        return dsNH;
    }

}
