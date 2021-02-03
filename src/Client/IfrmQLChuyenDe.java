package Client;

import DAO.ChuyenDeDAO;
import Entity.ChuyenDe;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrmQLChuyenDe extends javax.swing.JInternalFrame {

    String[] file;
    boolean kTra;
    double hocPhi;
    String path = "", tgFile = "";
    int ind = 0, thoiLuong;
    DefaultTableModel model;
    ChuyenDe cd = new ChuyenDe();
    ChuyenDeDAO daoCD = new ChuyenDeDAO();
    List<ChuyenDe> dsCD = new ArrayList<>();

    public IfrmQLChuyenDe() {
        initComponents();
        designJatbel();
        loadDataToTable();
    }

    //phương thức tạo mới model
    void newModel() {
        String[] title = {"Mã CD", "Tên CD", "Học phí", "Thời lượng", "Hình"};
        model = new DefaultTableModel(title, 0);
        tblList.setModel(model);
    }

    //Phương thức loadDataToTable
    void loadDataToTable() {
        newModel();
        while (dsCD.size() > 0) {
            dsCD.remove(0);
        }
        dsCD = daoCD.loadDataFull();
        for (ChuyenDe x : dsCD) {
            Vector v = new Vector();
            v.add(x.getMaCD());
            v.add(x.getTenCD());
            v.add(x.getHocPhi());
            v.add(x.getThoiLuong());
            v.add(x.getHinh());
            model.addRow(v);
        }
    }

    //Phương thức design truyện
    void designJatbel() {
        tblList.getTableHeader().setFont(new Font("Segoe UI", 1, 14));
        tblList.getTableHeader().setOpaque(false);
        tblList.getTableHeader().setBackground(Color.decode("#a0a0a0"));
        tblList.getTableHeader().setForeground(Color.white);
        tblList.setRowHeight(24);
    }

    //phương thức fillTable
    void fillTable() {
        txtMaCD.setText(tblList.getValueAt(ind, 0).toString());
        txtTenCD.setText(tblList.getValueAt(ind, 1).toString());
        txtHocPhi.setText(tblList.getValueAt(ind, 2).toString());
        txtThoiLuong.setText(tblList.getValueAt(ind, 3).toString());
        txtMoTa.setText(dsCD.get(ind).getMoTa());
        Image image = new ImageIcon("..\\DA Mau\\src\\Img\\" + tblList.getValueAt(ind, 4).toString()).getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_AREA_AVERAGING);
        lblLogo.setIcon(new ImageIcon(image));
    }

    //Phương thức tìm CD
    void findCD() {
        for (int i = 0; i < dsCD.size(); i++) {
            if (dsCD.get(i).getMaCD().equalsIgnoreCase(txtMaCD.getText().trim())) {
                ind = i;
            }
        }
    }

    //Phương thức kiểm tra
    void checkDL() {
        kTra = false;
        if (txtTenCD.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên chuyên đề");
            txtTenCD.setText("");
            txtTenCD.requestFocus();
            return;
        }
        //Kiểm tra thời lượng
        try {
            if (txtThoiLuong.getText().trim().matches("\\s*")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập thời lượng");
                txtThoiLuong.setText("");
                txtThoiLuong.requestFocus();
                return;
            }
            thoiLuong = Integer.parseInt(txtThoiLuong.getText());
            if (thoiLuong < 0) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0");
                txtThoiLuong.setText("");
                txtThoiLuong.requestFocus();
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số");
            txtThoiLuong.setText("");
            txtThoiLuong.requestFocus();
            return;

        }

        //Kiểm tra học phí
        try {
            if (txtHocPhi.getText().trim().matches("\\s*")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập học phí");
                txtHocPhi.setText("");
                txtHocPhi.requestFocus();
                return;
            }
            hocPhi = Double.parseDouble(txtHocPhi.getText());
            if (hocPhi < 0) {
                JOptionPane.showMessageDialog(this, "Học phí phải lớn hơn 0");
                txtHocPhi.setText("");
                txtHocPhi.requestFocus();
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Học phí phải là số");
            txtHocPhi.setText("");
            txtHocPhi.requestFocus();
            return;

        }
        if (tgFile.matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh");
            return;
        }
        kTra = true;
    }

    //Phương thức thêm CD
    void isnertCD() {
        if (txtMaCD.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã chuyên đề");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        cd.setMaCD(txtMaCD.getText());
        if (daoCD.loadDataID(cd).size() != 0) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề đã tồn tại");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        checkDL();
        if (!kTra) {
            return;
        }
        cd.setMaCD(txtMaCD.getText());
        cd.setTenCD(txtTenCD.getText());
        cd.setThoiLuong(thoiLuong);
        cd.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        cd.setMoTa(txtMoTa.getText());
        cd.setHinh(path);
        daoCD.insertCSDL(cd);
        try {
            copyAPaste();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadDataToTable();
        findCD();
        fillTable();
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }

    //Phương thức cập  nhật CD
    void updateCD() {
        if (txtMaCD.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã chuyên đề");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        if (txtMaCD.getText().trim().length() > 5) {
            JOptionPane.showMessageDialog(this, "Ma CD <=5 ky tu");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        cd.setMaCD(txtMaCD.getText().trim());
        if (daoCD.loadDataID(cd).size() == 0) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề không tồn tại");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        checkDL();
        if (!kTra) {
            return;
        }
        cd.setMaCD(txtMaCD.getText());
        cd.setTenCD(txtTenCD.getText());
        cd.setThoiLuong(thoiLuong);
        cd.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        cd.setMoTa(txtMoTa.getText());
        cd.setHinh(path);
        daoCD.updateCSDL(cd);
        try {
            copyAPaste();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadDataToTable();
        findCD();
        fillTable();
        loadDataToTable();
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }

    //Phương thức làm mới form 
    void resetFrm() {
        txtMaCD.setText("");
        txtTenCD.setText("");
        txtThoiLuong.setText("");
        txtHocPhi.setText("");
        txtMoTa.setText("");
        lblLogo.setIcon(null);
    }

    //Phương thức chọn ảnh
    public void avata() {
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    tgFile = fc.getSelectedFile().getPath();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
            file = tgFile.split("\\\\");
            path = file[file.length - 1];
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh");
        }
    }

    //Phương thức copy ảnh
    void copyAPaste() throws FileNotFoundException, IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(tgFile));
            File file2 = new File("");
            String currentDirectory = file2.getAbsolutePath();
            os = new FileOutputStream(new File(currentDirectory + "\\src\\Img\\" + path));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        tgFile = "";
    }

    //Phuong thuc xoa CD
    void deleteCD() {
        if (txtMaCD.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã chuyên đề");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        cd.setMaCD(txtMaCD.getText());
        if (daoCD.loadDataID(cd).size() == 0) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề không tồn tại");
            txtMaCD.setText("");
            txtMaCD.requestFocus();
            return;
        }
        daoCD.deleteCSDL(txtMaCD.getText().trim());
        JOptionPane.showMessageDialog(this, "Delete Successfully!");
        loadDataToTable();
    }

    //Phương thức next
    void nextNH() {
        if (ind < dsCD.size() - 1) {
            ind++;
            fillTable();
        } else {
            ind = dsCD.size() - 1;
            JOptionPane.showMessageDialog(this, "Next!");
        }
    }

    //Phương thức Prev
    void prevNH() {
        if (ind > 0) {
            ind--;
            fillTable();
        } else {
            ind = 0;
            JOptionPane.showMessageDialog(this, "Prev!");
        }
    }

    //Phương thức Last
    void lastNH() {
        ind = dsCD.size() - 1;
        fillTable();
    }

    //Phương thức First
    void firstNH() {
        ind = 0;
        fillTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tbpMain = new javax.swing.JTabbedPane();
        pnlCN = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        txtTenCD = new javax.swing.JTextField();
        txtThoiLuong = new javax.swing.JTextField();
        txtHocPhi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        pnlDS = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(241, 244, 246));
        setMaximumSize(new java.awt.Dimension(1280, 630));
        setMinimumSize(new java.awt.Dimension(1280, 630));
        setPreferredSize(new java.awt.Dimension(1280, 630));

        pnlCN.setBackground(new java.awt.Color(255, 255, 255));
        pnlCN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCN.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên chuyên đề");
        pnlCN.add(jLabel2);
        jLabel2.setBounds(350, 100, 89, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Học phí");
        pnlCN.add(jLabel3);
        jLabel3.setBounds(350, 250, 47, 20);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Thời lượng");
        pnlCN.add(jLabel4);
        jLabel4.setBounds(350, 180, 65, 20);

        lblLogo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
        });
        pnlCN.add(lblLogo);
        lblLogo.setBounds(68, 45, 200, 270);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Mô tả chuyên đề");
        pnlCN.add(jLabel6);
        jLabel6.setBounds(70, 320, 104, 20);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Hình logo");
        pnlCN.add(jLabel7);
        jLabel7.setBounds(70, 15, 60, 20);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Mã chuyên đề");
        pnlCN.add(jLabel8);
        jLabel8.setBounds(350, 15, 87, 20);

        txtMaCD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCN.add(txtMaCD);
        txtMaCD.setBounds(350, 50, 820, 35);

        txtTenCD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCN.add(txtTenCD);
        txtTenCD.setBounds(350, 130, 820, 35);

        txtThoiLuong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCN.add(txtThoiLuong);
        txtThoiLuong.setBounds(350, 210, 820, 35);

        txtHocPhi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlCN.add(txtHocPhi);
        txtHocPhi.setBounds(350, 280, 820, 35);

        txtMoTa.setColumns(20);
        txtMoTa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMoTa.setLineWrap(true);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        pnlCN.add(jScrollPane1);
        jScrollPane1.setBounds(70, 351, 1100, 75);

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlCN.add(btnThem);
        btnThem.setBounds(77, 456, 90, 35);

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        pnlCN.add(btnSua);
        btnSua.setBounds(185, 456, 90, 35);

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        pnlCN.add(btnXoa);
        btnXoa.setBounds(293, 456, 90, 35);

        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        pnlCN.add(btnMoi);
        btnMoi.setBounds(401, 456, 90, 35);

        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlCN.add(btnLast);
        btnLast.setBounds(1122, 456, 55, 35);

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlCN.add(btnNext);
        btnNext.setBounds(1049, 456, 55, 35);

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlCN.add(btnPrev);
        btnPrev.setBounds(976, 456, 55, 35);

        btnFirst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlCN.add(btnFirst);
        btnFirst.setBounds(903, 456, 55, 35);

        tbpMain.addTab("Cập nhật", pnlCN);

        pnlDS.setBackground(new java.awt.Color(255, 255, 255));
        pnlDS.setLayout(null);

        tblList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã CD", "Tên CD", "Hoc phí", "Thời lượng", "Hình"
            }
        ));
        tblList.setRowHeight(20);
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblList);

        pnlDS.add(jScrollPane2);
        jScrollPane2.setBounds(35, 27, 1170, 450);

        tbpMain.addTab("Danh sách", pnlDS);

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
        jLabel1.setText("Quản lý chuyên đề");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbpMain)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 1032, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbpMain, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        ind = tblList.getSelectedRow();
        fillTable();
        tbpMain.setSelectedComponent(pnlCN);
    }//GEN-LAST:event_tblListMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        isnertCD();
    }//GEN-LAST:event_btnThemActionPerformed

    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseClicked
        avata();
    }//GEN-LAST:event_lblLogoMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateCD();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        resetFrm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteCD();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstNH();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevNH();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextNH();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastNH();
    }//GEN-LAST:event_btnLastActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlCN;
    private javax.swing.JPanel pnlDS;
    private javax.swing.JTable tblList;
    private javax.swing.JTabbedPane tbpMain;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenCD;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}
