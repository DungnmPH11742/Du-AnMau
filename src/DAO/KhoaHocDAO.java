package DAO;

import Entity.KhoaHoc;
import JDBCHelper.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhoaHocDAO extends EduSysDAO<KhoaHoc, String> {

    JDBCHelper jdbc = new JDBCHelper();
    List<KhoaHoc> dsKH;

    @Override
    public void insertCSDL(KhoaHoc entity) {
        jdbc.pstmHelper("Insert into KHOAHOC values(?, ?, ?, ?, ?, ?, ?)",
                entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao());
    }

    @Override
    public void updateCSDL(KhoaHoc entity) {
        jdbc.pstmHelper("update KHOAHOC\n"
                + "	set NGAYKG = ?, GHICHU = ?\n"
                + "		where MACD = ? and MANV = ?", entity.getNgayKG(), entity.getGhiChu(), entity.getMaCD(), entity.getMaNV());
    }

    @Override
    public void deleteCSDL(String key) {
        jdbc.pstmHelper("exec deleteKH ?", key);
    }

    @Override
    public List<KhoaHoc> loadDataFull() {
        dsKH = new ArrayList<>();
        try {
            ResultSet rs = jdbc.selectAll("select * from KHOAHOC");
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getString(1));
                kh.setMaCD(rs.getString(2));
                kh.setHocPhi(rs.getFloat(3));
                kh.setThoiLuong(rs.getInt(4));
                kh.setNgayKG(rs.getString(5));
                kh.setGhiChu(rs.getString(6));
                kh.setMaNV(rs.getString(7));
                kh.setNgayTao(rs.getString(8));
                dsKH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKH;
    }

    @Override
    public List<KhoaHoc> loadDataID(KhoaHoc entity) {
        dsKH = new ArrayList<>();
        try {
            ResultSet rs = jdbc.selectByID("select * from KHOAHOC where maCD = ? and maNV = ?", entity.getMaCD(), entity.getMaNV());
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getString(1));
                kh.setMaCD(rs.getString(2));
                kh.setHocPhi(rs.getFloat(3));
                kh.setThoiLuong(rs.getInt(4));
                kh.setNgayKG(rs.getString(5));
                kh.setGhiChu(rs.getString(6));
                kh.setMaNV(rs.getString(7));
                kh.setNgayTao(rs.getString(8));
                dsKH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKH;
    }

    @Override
    public List<KhoaHoc> loadDataKey(String key) {
        dsKH = new ArrayList<>();
        try {
            ResultSet rs = jdbc.selectAll("select * from KHOAHOC where maCD = '" + key + "'");
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getString(1));
                kh.setMaCD(rs.getString(2));
                kh.setHocPhi(rs.getFloat(3));
                kh.setThoiLuong(rs.getInt(4));
                kh.setNgayKG(rs.getString(5));
                kh.setGhiChu(rs.getString(6));
                kh.setMaNV(rs.getString(7));
                kh.setNgayTao(rs.getString(8));
                dsKH.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsKH;
    }

}
