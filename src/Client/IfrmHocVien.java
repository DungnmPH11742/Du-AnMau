package Client;

import DAO.ChuyenDeDAO;
import DAO.DAONguoiHoc;
import DAO.HocVienDAO;
import DAO.KhoaHocDAO;
import Entity.ChuyenDe;
import Entity.HocVien;
import Entity.KhoaHoc;
import Entity.NguoiHoc;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrmHocVien extends javax.swing.JInternalFrame {

    int ind, chon = -1, chon2 = -1;
    double diem = 0;
    boolean kTr;
    ChuyenDe cd;
    LocalDate day;
    String key, maNV, gtinh = "", key2;
    KhoaHoc kh = new KhoaHoc();
    NguoiHoc nh = new NguoiHoc();
    KhoaHocDAO daoKH = new KhoaHocDAO();
    HocVienDAO daoHV = new HocVienDAO();
    DAONguoiHoc daoNH = new DAONguoiHoc();
    ChuyenDeDAO daoCD = new ChuyenDeDAO();
    List<KhoaHoc> dsKH = new ArrayList<>();
    List<NguoiHoc> dsNH = new ArrayList<>();
    List<HocVien> dsHV = new ArrayList<>();
    List<ChuyenDe> dsCD = new ArrayList<>();
    DefaultTableModel modelTable, modelNH;
    DefaultComboBoxModel modelCD = new DefaultComboBoxModel();
    DefaultComboBoxModel modelKH = new DefaultComboBoxModel();

    public IfrmHocVien() {
        initComponents();
        loadDataToCboCD();
        loadDataToCboKH();
    }

    //newModel
    void newModelHV() {
        String[] title = {"TT", "Mã HV", "Mã NH", "Họ tên", "Điểm"};
        modelTable = new DefaultTableModel(title, 0);
        tblHV.setModel(modelTable);
    }

    void newModelNH() {
        String[] title = {"Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email"};
        modelNH = new DefaultTableModel(title, 0);
        tblList.setModel(modelNH);
    }

    //loadDataoTable
    void loadDataTable() {
        int stt = 0;
        newModelHV();
        while (dsHV.size() > 0) {
            dsHV.remove(0);
        }
        dsHV = daoHV.loadDataKey(key2);
        for (HocVien x : dsHV) {
            Vector v = new Vector();
            nh = daoNH.loadDataID(new NguoiHoc(x.getMaNH(), title, maNV, isIcon, title, maNV, title, maNV, key)).get(0);
            v.add(stt);
            v.add(x.getMaHV());
            v.add(x.getMaNH());
            v.add(nh.getHoTen());
            v.add(x.getDiem());
            modelTable.addRow(v);
        }
    }

    void loadDataTableNH() {
        newModelNH();
        while (dsNH.size() > 0) {
            dsNH.remove(0);
        }
        dsNH = daoNH.loadDataNotHV("", key2);
        for (NguoiHoc x : dsNH) {
            Vector v = new Vector();
            v.add(x.getMaNH());
            v.add(x.getHoTen());
            if (x.isGioiTinh()) {
                gtinh = "Nam";
            } else {
                gtinh = "Nữ";
            }
            v.add(gtinh);
            v.add(x.getNgaySinh());
            v.add(x.getDienThoai());
            v.add(x.getEmail());
            modelNH.addRow(v);
        }
    }

    //loadDataToCBo
    void loadDataToCboCD() {
        dsCD = daoCD.loadDataFull();
        cboCD.setModel(modelCD);
        for (ChuyenDe x : dsCD) {
            modelCD.addElement(x);
        }
    }

    //loadDataToCBo
    void loadDataToCboKH() {
        dsKH = daoKH.loadDataKey(key);
        CBOkh.setModel(modelKH);
        for (KhoaHoc x : dsKH) {
            modelKH.addElement(x);
        }
    }

    void addHV() {
        HocVien hv = new HocVien();
        if (chon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người học cần thêm");
        } else {
            hv.setMaNH((String) tblList.getValueAt(chon, 0));
            hv.setMaKH(key2);
            hv.setDiem(-1);
            daoHV.insertCSDL(hv);
            loadDataTable();
            loadDataTableNH();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        }
    }

    void updateHV() {
        HocVien hv = new HocVien();
        if (chon2 == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người học cần cập nhật");
        } else {
            diem = Double.parseDouble((tblHV.getValueAt(chon2, 4).toString()));
            hv.setMaNH(tblHV.getValueAt(chon2, 2).toString());
            hv.setMaKH(key2);
            hv.setDiem(diem);
            daoHV.updateCSDL(hv);
            loadDataTable();
            loadDataTableNH();
            JOptionPane.showMessageDialog(this, hv.getMaKH() + hv.getMaNH() + hv.getDiem());
        }
    }

    void deleteHV() {
        HocVien hv = new HocVien();
        if (chon2 == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người học cần Xóa");
        } else {
            hv.setMaNH(tblHV.getValueAt(chon2, 2).toString());
            hv.setMaKH(key2);
            daoHV.deleteCSDL(daoHV.loadDataIDHV(hv).get(0).getMaHV());
            loadDataTable();
            loadDataTableNH();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbpMain = new javax.swing.JTabbedPane();
        pnlCN = new javax.swing.JPanel();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHV = new javax.swing.JTable();
        pnlDS = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        CBOkh = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        cboCD = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(241, 244, 246));
        setMaximumSize(new java.awt.Dimension(1280, 630));
        setMinimumSize(new java.awt.Dimension(1280, 630));
        setPreferredSize(new java.awt.Dimension(1280, 630));

        pnlCN.setBackground(new java.awt.Color(255, 255, 255));
        pnlCN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        tblHV.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHVMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHV);

        javax.swing.GroupLayout pnlCNLayout = new javax.swing.GroupLayout(pnlCN);
        pnlCN.setLayout(pnlCNLayout);
        pnlCNLayout.setHorizontalGroup(
            pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCNLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1161, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCNLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlCNLayout.setVerticalGroup(
            pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCNLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        tbpMain.addTab("Học viên", pnlCN);

        pnlDS.setBackground(new java.awt.Color(255, 255, 255));
        pnlDS.setLayout(null);

        tblList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Mã NV", "Ngày thêm"
            }
        ));
        tblList.setGridColor(new java.awt.Color(241, 244, 246));
        tblList.setRowHeight(20);
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblList);

        pnlDS.add(jScrollPane2);
        jScrollPane2.setBounds(15, 17, 1150, 330);

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlDS.add(btnThem);
        btnThem.setBounds(1020, 370, 67, 29);

        tbpMain.addTab("Người học", pnlDS);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("CHUYÊN ĐỀ");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        CBOkh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        CBOkh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CBOkhItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(CBOkh, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(CBOkh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cboCD.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cboCD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCDItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(cboCD, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(cboCD, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("KHÓA HỌC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbpMain, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(tbpMain))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        addHV();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateHV();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteHV();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        chon = tblList.getSelectedRow();
    }//GEN-LAST:event_tblListMouseClicked

    private void cboCDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCDItemStateChanged
        cd = (ChuyenDe) cboCD.getSelectedItem();
        key = cd.getMaCD();
        loadDataToCboKH();
    }//GEN-LAST:event_cboCDItemStateChanged

    private void CBOkhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CBOkhItemStateChanged
        kh = (KhoaHoc) CBOkh.getSelectedItem();
        key2 = kh.getMaKH();
        loadDataTable();
        loadDataTableNH();
    }//GEN-LAST:event_CBOkhItemStateChanged

    private void tblHVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHVMouseClicked
        chon2 = tblHV.getSelectedRow();
    }//GEN-LAST:event_tblHVMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBOkh;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboCD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel pnlCN;
    private javax.swing.JPanel pnlDS;
    private javax.swing.JTable tblHV;
    private javax.swing.JTable tblList;
    private javax.swing.JTabbedPane tbpMain;
    // End of variables declaration//GEN-END:variables
}
