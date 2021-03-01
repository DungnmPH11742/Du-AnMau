package Client;

import DAO.DAONguoiHoc;
import Entity.NguoiHoc;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrmNguoiHoc extends javax.swing.JInternalFrame {

    //Khai báo biến
    int ind = 0;
    LocalDate day;
    String gioiTinh, maNV;
    DefaultTableModel model;
    boolean gTinh, tg = false;
    NguoiHoc nh = new NguoiHoc();
    DAONguoiHoc daoNH = new DAONguoiHoc();
    List<NguoiHoc> dsNH = new ArrayList<>();

    public IfrmNguoiHoc(String maNVien) throws ParseException {
        initComponents();
        maNV = maNVien;
        designJatbel();
        loadDataToTable();
        dthNgaySinh.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(day.now().toString()));
    }

    //Phương thức design truyện
    void designJatbel() {
        tblList.getTableHeader().setFont(new Font("Segoe UI", 1, 14));
        tblList.getTableHeader().setOpaque(false);
        tblList.getTableHeader().setBackground(Color.decode("#a0a0a0"));
        tblList.getTableHeader().setForeground(Color.white);
        tblList.setRowHeight(24);
    }

    //Phương thức tạo mới model
    void newModel() {
        String[] title = {"Mã NH", "Họ tên", "Ngày sinh", "Giới tính", "Điện thoại", "Email", "Mã NV", "Ngày DK"};
        model = new DefaultTableModel(title, 0);
        tblList.setModel(model);
    }

    //Phương thức loadDataToTable
    void loadDataToTable() {
        newModel();
        while (dsNH.size() > 0) {
            dsNH.remove(0);
        }
        dsNH = daoNH.loadDataFull();
        for (NguoiHoc x : dsNH) {
            Vector v = new Vector();
            v.add(x.getMaNH());
            v.add(x.getHoTen());
            v.add(x.getNgaySinh());
            gioiTinh = x.isGioiTinh() == true ? "Nam" : "Nữ";
            v.add(gioiTinh);
            v.add(x.getDienThoai());
            v.add(x.getEmail());
            v.add(x.getMaNV());
            v.add(x.getNgayDK());
            model.addRow(v);
        }
    }

    //Phương thức check Trùng 
    boolean checkID() {
        nh.setMaNH(txtMaNH.getText().trim());
        return daoNH.loadDataID(nh).size() != 0 ? true : false;
    }

    //Phương thức kiểm tra trống
    void checkEmpty() {
        if (txtHoTen.getText().trim().length() == 0) {
            txtHoTen.setText("");
            txtHoTen.requestFocus();
            return;
        }
        //Biểu thức chính quy Ktr Email
        String regexEmail = "\\w+\\@\\w{3,6}\\.\\w{2,5}\\.\\w{2,5}";
        //Biểu thức chính quy Ktr SDT
        String regexSDT = "[0]{1}\\d{9}";
        //Kiểm tra SDT
        if (txtDienThoai.getText().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập SDT");
            txtDienThoai.setText("");
            txtDienThoai.requestFocus();
            return;
        } else {
            if (!txtDienThoai.getText().matches(regexSDT)) {
                JOptionPane.showMessageDialog(this, "SDT không đúng định dạng\nĐịnh dạng SĐT: 0{9}");
                txtDienThoai.setText("");
                txtDienThoai.requestFocus();
                return;
            }
        }
        //Kiểm tra Email
        if (txtEmail.getText().matches("\\s*")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Email");
            txtEmail.requestFocus();
            return;
        } else {
            if (!txtEmail.getText().matches(regexEmail)) {
                JOptionPane.showMessageDialog(this, "Email không đúng định dạng\nĐịnh dạng Email: X@X.X.X");
                txtEmail.requestFocus();
                return;
            }
        }
        tg = true;
    }

    //Phương thức thêm Người học
    void insertNH() {
        if (txtMaNH.getText().trim().length() == 0) {
            txtMaNH.setText("");
            txtMaNH.requestFocus();
            return;
        } else {
            if (checkID()) {
                JOptionPane.showMessageDialog(this, "Mã người học đã tồn tại");
                txtMaNH.setText("");
                txtMaNH.requestFocus();
                return;
            }
        }
        checkEmpty();
        if (!tg) {
            return;
        }
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(txtMaNH.getText().trim());
        nh.setHoTen(txtHoTen.getText().trim());
        nh.setNgaySinh((new SimpleDateFormat("yyyy-MM-dd")).format(dthNgaySinh.getDate()));
        gTinh = rdoNam.isSelected() == true ? true : false;
        nh.setGioiTinh(gTinh);
        nh.setDienThoai(txtDienThoai.getText().trim());
        nh.setEmail(txtEmail.getText().trim());
        nh.setGhiChu(txtGhiChu.getText().trim());
        nh.setMaNV(maNV);
        nh.setNgayDK(day.now().toString());
        daoNH.insertCSDL(nh);
        JOptionPane.showMessageDialog(this, "Thêm thành công");
        loadDataToTable();
    }

    //Phương thức cập nhật Người Học
    void updateNH() {
        if (txtMaNH.getText().trim().length() == 0) {
            txtMaNH.setText("");
            txtMaNH.requestFocus();
            return;
        } else {
            if (!checkID()) {
                JOptionPane.showMessageDialog(this, "Mã người học không tồn tại");
                txtMaNH.setText("");
                txtMaNH.requestFocus();
                return;
            }
        }
        checkEmpty();
        if (!tg) {
            return;
        }
        NguoiHoc nh = new NguoiHoc();
        nh.setHoTen(txtHoTen.getText().trim());
        nh.setNgaySinh((new SimpleDateFormat("yyyy-MM-dd")).format(dthNgaySinh.getDate()));
        gTinh = rdoNam.isSelected() == true ? true : false;
        nh.setGioiTinh(gTinh);
        nh.setDienThoai(txtDienThoai.getText().trim());
        nh.setEmail(txtEmail.getText().trim());
        nh.setGhiChu(txtGhiChu.getText().trim());
        nh.setMaNV(maNV);
        nh.setMaNH(txtMaNH.getText().trim());
        daoNH.updateCSDL(nh);
        JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        loadDataToTable();
    }

    //Phương thức xóa
    void deleteNH() {
        if (txtMaNH.getText().trim().length() == 0) {
            txtMaNH.setText("");
            txtMaNH.requestFocus();
            return;
        } else {
            if (!checkID()) {
                JOptionPane.showMessageDialog(this, "Mã người học không tồn tại");
                txtMaNH.setText("");
                txtMaNH.requestFocus();
                return;
            }
        }
        daoNH.deleteCSDL(txtMaNH.getText());
        JOptionPane.showMessageDialog(this, "Xóa thành công");
        loadDataToTable();
    }

    //Phương thức new
    void resertFrm() {
        try {
            txtMaNH.setText("");
            txtHoTen.setText("");
            txtDienThoai.setText("");
            txtEmail.setText("");
            rdoNam.setSelected(true);
            txtGhiChu.setText("");
            dthNgaySinh.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(day.now().toString()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Phương thức fillTable
    void fillTable() {
        try {
            txtMaNH.setText(tblList.getValueAt(ind, 0).toString());
            txtHoTen.setText(tblList.getValueAt(ind, 1).toString());
            if (tblList.getValueAt(ind, 3).toString().equalsIgnoreCase("Nam")) {
                rdoNam.setSelected(true);
            } else {
                rdoNu.setSelected(true);
            }
            dthNgaySinh.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(tblList.getValueAt(ind, 2).toString()));
            txtDienThoai.setText(tblList.getValueAt(ind, 4).toString());
            txtEmail.setText(tblList.getValueAt(ind, 5).toString());
            nh.setMaNH(tblList.getValueAt(ind, 0).toString());
            txtGhiChu.setText(daoNH.loadDataID(nh).get(0).getGhiChu());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Phương thức tìm kiếm
    void searchNH() {
        while (dsNH.size() > 0) {
            dsNH.remove(0);
        }
        if(txtTK.getText().matches("\\s*")){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên người học cần tìm");
        }
        dsNH = daoNH.loadDataKey(txtTK.getText());
        newModel();
        for (NguoiHoc x : dsNH) {
            Vector v = new Vector();
            v.add(x.getMaNH());
            v.add(x.getHoTen());
            v.add(x.getNgaySinh());
            gioiTinh = x.isGioiTinh() == true ? "Nam" : "Nữ";
            v.add(gioiTinh);
            v.add(x.getDienThoai());
            v.add(x.getEmail());
            v.add(x.getMaNV());
            v.add(x.getNgayDK());
            model.addRow(v);
        }
    }

    //Phương thức next
    void nextNH() {
        if (ind < dsNH.size() - 1) {
            ind++;
            fillTable();
        } else {
            ind = dsNH.size() - 1;
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
        ind = dsNH.size() - 1;
        fillTable();
    }

    //Phương thức First
    void firstNH() {
        ind = 0;
        fillTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        tbpMain = new javax.swing.JTabbedPane();
        pnlCN = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtDienThoai = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        dthNgaySinh = new com.toedter.calendar.JDateChooser();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        pnlDS = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTK = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Họ và tên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Địa chỉ email");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Ngày sinh");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Điện thoại");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Ghi chú");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Giới tính");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Mã người học");

        txtMaNH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        dthNgaySinh.setDateFormatString("yyyy-MM-dd");

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

        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCNLayout = new javax.swing.GroupLayout(pnlCN);
        pnlCN.setLayout(pnlCNLayout);
        pnlCNLayout.setHorizontalGroup(
            pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCNLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCNLayout.createSequentialGroup()
                        .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6)
                    .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlCNLayout.createSequentialGroup()
                        .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(150, 150, 150)
                        .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4))
                            .addComponent(jLabel3)
                            .addGroup(pnlCNLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(dthNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))))
                    .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnlCNLayout.createSequentialGroup()
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        pnlCNLayout.setVerticalGroup(
            pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCNLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addGap(10, 10, 10)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dthNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(pnlCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        tbpMain.addTab("Cập nhật", pnlCN);

        pnlDS.setBackground(new java.awt.Color(255, 255, 255));
        pnlDS.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(241, 244, 246));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12))); // NOI18N

        txtTK.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnTim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pnlDS.add(jPanel4);
        jPanel4.setBounds(35, 11, 1130, 80);

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
        jScrollPane2.setBounds(35, 97, 1130, 380);

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
        jLabel1.setText("Quản lý người học");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbpMain, javax.swing.GroupLayout.Alignment.LEADING))
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insertNH();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateNH();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        resertFrm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        ind = tblList.getSelectedRow();
        fillTable();
        tbpMain.setSelectedComponent(pnlCN);
    }//GEN-LAST:event_tblListMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteNH();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextNH();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevNH();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstNH();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastNH();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        searchNH();
    }//GEN-LAST:event_btnTimActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dthNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlCN;
    private javax.swing.JPanel pnlDS;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblList;
    private javax.swing.JTabbedPane tbpMain;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtTK;
    // End of variables declaration//GEN-END:variables
}
