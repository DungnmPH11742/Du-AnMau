package Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ThongKeJFrame extends javax.swing.JInternalFrame {

    Connection cn;
    boolean VaiTro;
    String mKhau = "";
    DefaultTableModel modelNH, modelBD, modelTHD, modelDT;
    DefaultComboBoxModel modelKH = new DefaultComboBoxModel();
    int ind = 0, s = 0, tg = 0, tam = 0;
    String panel;

    public ThongKeJFrame(Connection con, boolean Vt, String pn) {
        initComponents();
        cn = con;
        VaiTro = Vt;
        newModelBD();
        newModelDT();
        newModelNH();
        dataLoadToTableNH();
        newModelTHD();
        dataLoadToTableTHD();
        dataLoadToCboKH();
        addcboNam();
        loc();
        loc2();
        panel = pn;
        openPanel();
    }

    //Phương thức mở Panel
    public void openPanel() {
        if (panel.equalsIgnoreCase("nh")) {
            tabs.setSelectedComponent(pnlNH);
        }
        if (panel.equalsIgnoreCase("dt")) {
            tabs.setSelectedComponent(pnlDT);
        }
        if (panel.equalsIgnoreCase("bd")) {
            tabs.setSelectedComponent(pnlBD);
        }
        if (panel.equalsIgnoreCase("ntt")) {
            tabs.setSelectedComponent(pnlTHD);
        }
    }

    //Tạo mới model NH
    public void newModelNH() {
        String[] titleNH = {"Năm", "Số người học", "Đầu tiên", "Sau cùng"};
        modelNH = new DefaultTableModel(titleNH, 0);
        tblNH.setModel(modelNH);
    }

    //Tạo mới model NH
    public void newModelBD() {
        String[] titleBD = {"Mã NH", "Họ và tên", "Điểm", "Xếp loại"};
        modelBD = new DefaultTableModel(titleBD, 0);
        tblBD.setModel(modelBD);
    }

    //Tạo mới model NH
    public void newModelTHD() {
        String[] titleNH = {"Chuyên đề", "Tổng số học viên", "Cao nhất", "Thấp nhất", "Điểm TB"};
        modelTHD = new DefaultTableModel(titleNH, 0);
        tblTHD.setModel(modelTHD);
    }

    //Tạo mới model NH
    public void newModelDT() {
        String[] titleNH = {"Chuyên đề", "Số khóa", "Số HV", "Doanh thu", "HP cao nhất", "HP thấp nhất", "HP trung bình"};
        modelDT = new DefaultTableModel(titleNH, 0);
        tblDT.setModel(modelDT);
    }

    //DataLoad
    public void dataLoadToTableNH() {
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("exec sp_ThongKeNguoiHoc");
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                modelNH.addRow(v);
            }
            stm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dataLoad");
        }
    }

    //DataLoad
    public void dataLoadToTableBD(String maKH) {
        while (s > 0) {
            modelBD.removeRow(0);
            s--;
        }
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("exec sp_BangDiem @MaKH =" + maKH);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add("Chưa nhập");
                modelBD.addRow(v);
                s++;
            }
            stm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dataLoad");
        }
    }

    //DataLoad
    public void dataLoadToTableTHD() {
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("exec sp_ThongKeDiem");
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getFloat(3));
                v.add(rs.getFloat(4));
                v.add(rs.getFloat(5));
                modelTHD.addRow(v);
            }
            stm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dataLoad");
        }
    }

    //DataLoad
    int tong = 0;

    public void dataLoadToTableDT(String nam) {
        while (tong > 0) {
            modelDT.removeRow(0);
            tong--;
        }
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("exec sp_ThongKeDoanhThu @Year =" + nam);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
                v.add(rs.getString(7));
                modelDT.addRow(v);
                tong++;
            }
            stm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dataLoad");
        }
    }

    //Data Load Cbo
    public void dataLoadToCboKH() {
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("Select * from khoahoc");
            while (rs.next()) {
                Lop.KhoaHoc kh = new Lop.KhoaHoc();
                kh.setMaCD(rs.getString(2));
                kh.setNgayKG(rs.getString(5));
                modelKH.addElement(kh);
            }
            cboKH.setModel(modelKH);
            stm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    //
    public void loc() {
        String maKh = "";
        String cbo = cboKH.getSelectedItem().toString();
        String[] mangtam = cbo.split("\\(");
        String[] mangtam2 = mangtam[1].split("\\)");
        try {
            PreparedStatement pstm = cn.prepareStatement("Select maKH from khoahoc where maCD = ? and ngayKG = ?");
            pstm.setString(1, mangtam[0]);
            pstm.setString(2, mangtam2[0]);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                maKh = String.valueOf(rs.getInt(1));
            }
            pstm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm");
        }
        dataLoadToTableBD(maKh);
    }

    //
    public void addcboNam() {
        try {
            Statement stm = cn.createStatement();
            ResultSet rs = stm.executeQuery("Select DISTINCT year(NgayKG)  from KhoaHoc");
            while (rs.next()) {
                cboNam.addItem(rs.getString(1));
            }
            stm.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi đổ dữ liệu lên Cbo Năm");
        }
    }

    public void loc2() {
        String nam = cboNam.getSelectedItem().toString();
        dataLoadToTableDT(nam);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlNH = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNH = new javax.swing.JTable();
        pnlBD = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBD = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        cboKH = new javax.swing.JComboBox<>();
        pnlTHD = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblTHD = new javax.swing.JTable();
        pnlDT = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDT = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();

        setMinimumSize(new java.awt.Dimension(1280, 625));
        setPreferredSize(new java.awt.Dimension(1280, 625));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("TỔNG HỢP THỐNG KÊ");

        tabs.setBackground(new java.awt.Color(72, 88, 139));

        pnlNH.setBackground(new java.awt.Color(72, 88, 139));

        tblNH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblNH);

        javax.swing.GroupLayout pnlNHLayout = new javax.swing.GroupLayout(pnlNH);
        pnlNH.setLayout(pnlNHLayout);
        pnlNHLayout.setHorizontalGroup(
            pnlNHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE)
        );
        pnlNHLayout.setVerticalGroup(
            pnlNHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNHLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 103, Short.MAX_VALUE))
        );

        tabs.addTab("Người học", pnlNH);

        tblBD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblBD);

        jLabel2.setText("Khoa học");

        cboKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboKHItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlBDLayout = new javax.swing.GroupLayout(pnlBD);
        pnlBD.setLayout(pnlBDLayout);
        pnlBDLayout.setHorizontalGroup(
            pnlBDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE)
            .addGroup(pnlBDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cboKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBDLayout.setVerticalGroup(
            pnlBDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBDLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlBDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        tabs.addTab("Bảng điểm", pnlBD);

        tblTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblTHD);

        javax.swing.GroupLayout pnlTHDLayout = new javax.swing.GroupLayout(pnlTHD);
        pnlTHD.setLayout(pnlTHDLayout);
        pnlTHDLayout.setHorizontalGroup(
            pnlTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1259, Short.MAX_VALUE)
        );
        pnlTHDLayout.setVerticalGroup(
            pnlTHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTHDLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 103, Short.MAX_VALUE))
        );

        tabs.addTab("Tổng hợp điểm", pnlTHD);

        tblDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblDT);

        jLabel3.setText("Năm:");

        cboNam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboNamItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlDTLayout = new javax.swing.GroupLayout(pnlDT);
        pnlDT.setLayout(pnlDTLayout);
        pnlDTLayout.setHorizontalGroup(
            pnlDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
            .addGroup(pnlDTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 1196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlDTLayout.setVerticalGroup(
            pnlDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        tabs.addTab("Doanh thu", pnlDT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboKHItemStateChanged
        loc();
    }//GEN-LAST:event_cboKHItemStateChanged

    private void cboNamItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboNamItemStateChanged
        loc2();
    }//GEN-LAST:event_cboNamItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboKH;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel pnlBD;
    private javax.swing.JPanel pnlDT;
    private javax.swing.JPanel pnlNH;
    private javax.swing.JPanel pnlTHD;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblBD;
    private javax.swing.JTable tblDT;
    private javax.swing.JTable tblNH;
    private javax.swing.JTable tblTHD;
    // End of variables declaration//GEN-END:variables
}
