package Client;

import DAO.NhanVienDAO;
import Entity.ChuyenDe;
import Entity.NhanVien;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrmNhanVien extends javax.swing.JInternalFrame {

    String[] file;
    double hocPhi;
    boolean kTra, truongP, vTro;
    int ind = 0, thoiLuong;
    DefaultTableModel model;
    String mk, vaiTroS = "", maNV;
    NhanVien nv = new NhanVien();
    NhanVienDAO daoNV = new NhanVienDAO();
    List<NhanVien> dsNV = new ArrayList<>();

    public IfrmNhanVien(String maNVIn, boolean vTroIn) {
        initComponents();
        vTro = vTroIn;
        maNV = maNVIn;
        designJatbel();
        loadDataToTable();
    }

    //phương thức tạo mới model
    void newModel() {
        String[] title = {"Mã NV", "Mật khẩu", "Họ và tên", "Vai trò"};
        model = new DefaultTableModel(title, 0);
        tblList.setModel(model);
    }

    //Phương thức loadDataToTable
    void loadDataToTable() {
        newModel();
        while (dsNV.size() > 0) {
            dsNV.remove(0);
        }
        dsNV = daoNV.loadDataFull();
        for (NhanVien x : dsNV) {
            Vector v = new Vector();
            v.add(x.getMaNV());
            v.add(x.getMatKhau());
            v.add(x.getHoTen());
            vaiTroS = x.isVaiTro() == true ? "Trưởng phòng" : "Nhân viên";
            v.add(vaiTroS);
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
    void showTable() {
        txtMaNV.setText(dsNV.get(ind).getMaNV());
        txtMK.setText(dsNV.get(ind).getMatKhau());
        txtHoTen.setText(dsNV.get(ind).getHoTen());
        txtXNMK.setText(dsNV.get(ind).getMatKhau());
        if (dsNV.get(ind).isVaiTro()) {
            rdoTP.setSelected(true);
        } else {
            rdoNV.setSelected(true);
        }
    }

    //Phương thức tìm CD
    void findCD() {
        for (int i = 0; i < dsNV.size(); i++) {
            if (dsNV.get(i).getMaNV().equalsIgnoreCase(txtMaNV.getText().trim())) {
                ind = i;
            }
        }
    }

    //Phương thức kiểm tra
    void checkDL() {
        kTra = false;
        mk = new String(txtMK.getPassword()).trim();
        if (mk.matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            txtMK.setText("");
            txtMK.requestFocus();
            return;
        }
        if (mk.matches("[!@#\\$%\\^\\&*\\)\\(+=._-]")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được chứa ký tự đặc biệt");
            txtMK.setText("");
            txtMK.requestFocus();
            return;
        }
        if (new String(txtXNMK.getPassword()).trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng xác nhận mật khẩu");
            txtXNMK.setText("");
            txtXNMK.requestFocus();
            return;
        }
        if (new String(txtXNMK.getPassword()).trim().matches("[!@#\\$%\\^\\&*\\)\\(+=._-]")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được chứa ký tự đặc biệt");
            txtXNMK.setText("");
            txtXNMK.requestFocus();
            return;
        }
        if (!txtXNMK.getText().trim().equals(mk)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không khớp");
            txtXNMK.setText("");
            txtXNMK.requestFocus();
            return;
        }
        if (txtHoTen.getText().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ và tên");
            txtHoTen.setText("");
            txtHoTen.requestFocus();
            return;
        }
        if (txtHoTen.getText().trim().matches("[!@#\\$%\\^\\&*\\)\\(+=._-]")) {
            JOptionPane.showMessageDialog(this, "Họ tên không được chứa ký tự đặc biệt");
            txtHoTen.setText("");
            txtHoTen.requestFocus();
            return;
        }
        kTra = true;
    }

    //Phương thức thêm CD
    void isnertCD() {
        if (txtMaNV.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã nhân viên");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (txtMaNV.getText().trim().matches("[!@#\\$%\\^\\&*\\)\\(+=._-]")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được chứa ký tự đặc biệt");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (daoNV.loadDataKey(txtMaNV.getText().trim()).size() != 0) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        checkDL();
        if (!kTra) {
            return;
        }
        truongP = rdoTP.isSelected() == true ? true : false;
        nv = new NhanVien(txtMaNV.getText().trim(), mk, txtHoTen.getText().trim(), kTra);
        daoNV.insertCSDL(nv);
        loadDataToTable();
        findCD();
        showTable();
        JOptionPane.showMessageDialog(this, "Thêm thành công");
    }

    //Phương thức cập  nhật CD
    void updateCD() {
        if (txtMaNV.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã nhân viên");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (txtMaNV.getText().trim().matches("[!@#\\$%\\^\\&*\\)\\(+=._-]")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được chứa ký tự đặc biệt");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (daoNV.loadDataKey(txtMaNV.getText().trim()).size() == 0) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        checkDL();
        if (!kTra) {
            return;
        }
        truongP = rdoTP.isSelected() == true ? true : false;
        nv = new NhanVien(txtMaNV.getText().trim(), mk, txtHoTen.getText().trim(), kTra);
        daoNV.updateCSDL(nv);
        findCD();
        showTable();
        loadDataToTable();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
    }

    //Phương thức làm mới form 
    void resetFrm() {
        txtMaNV.setText("");
        txtMK.setText("");
        txtXNMK.setText("");
        txtHoTen.setText("");
        rdoNV.setSelected(false);
    }

    //Phương thức xóa
    void deleteNV() {
        if (txtMaNV.getText().trim().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã nhân viên");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (txtMaNV.getText().trim().matches("[!@#\\$%\\^\\&*\\)\\(+=._-]")) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được chứa ký tự đặc biệt");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (daoNV.loadDataKey(txtMaNV.getText().trim()).size() == 0) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại");
            txtMaNV.setText("");
            txtMaNV.requestFocus();
            return;
        }
        if (!vTro) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhân viên");
            return;
        }
        if (txtMaNV.getText().trim().equalsIgnoreCase(maNV)) {
            JOptionPane.showMessageDialog(this, "Không được xóa chính mình");
            return;
        }
        daoNV.deleteCSDL(txtMaNV.getText().trim());
        loadDataToTable();
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        resetFrm();
    }

    //Phương thức next
    void nextNH() {
        if (ind < dsNV.size() - 1) {
            ind++;
            showTable();
        } else {
            ind = dsNV.size() - 1;
            JOptionPane.showMessageDialog(this, "Next!");
        }
    }

    //Phương thức Prev
    void prevNH() {
        if (ind > 0) {
            ind--;
            showTable();
        } else {
            ind = 0;
            JOptionPane.showMessageDialog(this, "Prev!");
        }
    }

    //Phương thức Last
    void lastNH() {
        ind = dsNV.size() - 1;
        showTable();
    }

    //Phương thức First
    void firstNH() {
        ind = 0;
        showTable();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tbpMain = new javax.swing.JTabbedPane();
        pnlCN = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        rdoTP = new javax.swing.JRadioButton();
        rdoNV = new javax.swing.JRadioButton();
        txtMK = new javax.swing.JPasswordField();
        txtXNMK = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Vai trò");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Xác nhận mật khẩu");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mật khẩu");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Mã nhân viên");

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        buttonGroup1.add(rdoTP);
        rdoTP.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoTP.setText("Trưởng phòng");

        buttonGroup1.add(rdoNV);
        rdoNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNV.setSelected(true);
        rdoNV.setText("Nhân viên");

        txtMK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtXNMK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rdoTP)
                        .addGap(46, 46, 46)
                        .addComponent(rdoNV))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtXNMK, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(15, 15, 15)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(txtXNMK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTP)
                    .addComponent(rdoNV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCN.add(jPanel2);
        jPanel2.setBounds(20, 20, 850, 430);

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

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

        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(133, 133, 133))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlCN.add(jPanel3);
        jPanel3.setBounds(880, 20, 340, 350);

        btnFirst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pnlCN.add(jPanel4);
        jPanel4.setBounds(880, 380, 330, 70);

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
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

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
                        .addGap(0, 996, Short.MAX_VALUE)))
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
        showTable();
        tbpMain.setSelectedComponent(pnlCN);
    }//GEN-LAST:event_tblListMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        isnertCD();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateCD();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        resetFrm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteNV();
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
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlCN;
    private javax.swing.JPanel pnlDS;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JRadioButton rdoTP;
    private javax.swing.JTable tblList;
    private javax.swing.JTabbedPane tbpMain;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JPasswordField txtMK;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtXNMK;
    // End of variables declaration//GEN-END:variables
}
