package Main;

import DAO.NhanVienDAO;
import Entity.NhanVien;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class frmLogin extends javax.swing.JFrame {

    int x, y;
    Timer t, t2, t3, t4, t5, t6;
    NhanVienDAO daoNV = new NhanVienDAO();
    String[] runText = {"Dự án mẫu(UDPM)", "Phần mềm PolyPro", "Nguyễn Mạnh Dũng", "MaSV: PH11742 "};

    public frmLogin() {
        initComponents();
        setLocationRelativeTo(null);
        pnlDK.setVisible(true);
        pnlDN.setVisible(true);
        pnlAvata.setVisible(true);
        x = pnlDK.getX();
        y = pnlDN.getX();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    //Phương thức mở phần đăng nhập
    public void openDN() {

        x = pnlDK.getX();
        y = pnlDN.getX();
        t2 = new Timer(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x < 470) {
                    x++;
                    pnlDK.setLocation(x, 80);
                }
                if (y > 70) {
                    y--;
                    pnlDN.setLocation(y, 80);
                    p1.setLocation((y + 50), 128);
                    p2.setLocation((y + 50), 200);
                    lblUser.setLocation((y + 64), 128);
                    lblPass.setLocation((y + 64), 200);
                    lblUserA.setLocation((y + 300), 128);
                    lblPassA.setLocation((y + 300), 200);
                    txtPass.setLocation((y + 64), 200);
                    txtUser.setLocation((y + 64), 128);
                    btnLogin.setLocation((y + 50), 336);
                    lblTextDK.setLocation((y + 100), 400);
                } else {
                    t2.stop();
                }
            }
        });
    }

    //Phương thức mở phần Quên mật khẩu
    public void openDK() {
        t = new Timer(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > 70) {
                    x--;
                    pnlDK.setLocation(x, 80);
                }
                if (y < 470) {
                    y++;
                    pnlDN.setLocation(y, 80);
                    p1.setLocation((y + 50), 128);
                    p2.setLocation((y + 50), 200);
                    lblUser.setLocation((y + 64), 128);
                    lblPass.setLocation((y + 64), 200);
                    lblUserA.setLocation((y + 300), 128);
                    lblPassA.setLocation((y + 300), 200);
                    txtPass.setLocation((y + 64), 200);
                    txtUser.setLocation((y + 64), 128);
                    btnLogin.setLocation((y + 50), 336);
                    lblTextDK.setLocation((y + 100), 400);
                } else {
                    pnlDN.setVisible(true);
                    t.stop();
                }
            }
        });
    }

    //Phương thức chuyển màu Nút
    int R = 184, G = 69, B = 146;
    int R2 = 184, G2 = 69, B2 = 146;

    public void fillColor() {
        t3 = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (R > 29) {
                    R--;
                    btnLogin.setBackground(new Color(R, G, B));
                } else {
                    t3.stop();
                }
                if (G < 161) {
                    G++;
                    btnLogin.setBackground(new Color(R, G, B));
                }
                if (B > 242) {
                    B++;
                    btnLogin.setBackground(new Color(R, G, B));
                }
            }
        });
    }

    //Phương thức chuyển màu Nút 2
    public void fillColor3() {
        t5 = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (R < 184) {
                    R++;
                    btnLogin.setBackground(new Color(R, G, B));
                } else {
                    t5.stop();
                }
                if (G > 69) {
                    G--;
                    btnLogin.setBackground(new Color(R, G, B));
                }
                if (B > 146) {
                    B--;
                    btnLogin.setBackground(new Color(R, G, B));
                }
            }
        });
    }

    //Phương thức đăng nhập
    public void chkDN() {
        boolean Vaitro = false;
        try {
            NhanVien nv = new NhanVien(txtUser.getText(), txtPass.getText(), "", true);
            if (daoNV.loadDataID(nv).size() != 0) {
                Vaitro = daoNV.loadDataID(nv).get(0).isVaiTro();
                frmMain fC = new frmMain(txtUser.getText(), Vaitro);
                fC.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không thể đăng nhập!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p1 = new javax.swing.JPanel();
        pnlAvata = new javax.swing.JPanel();
        BackgroundLogUp = new javax.swing.JLabel();
        pnlDK = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblTextDK = new javax.swing.JLabel();
        lblPassA = new javax.swing.JLabel();
        lblUserA = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        txtUser = new javax.swing.JTextField();
        p2 = new javax.swing.JPanel();
        pnlDN = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(960, 599));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        p1.setBackground(new java.awt.Color(0, 198, 223));

        javax.swing.GroupLayout p1Layout = new javax.swing.GroupLayout(p1);
        p1.setLayout(p1Layout);
        p1Layout.setHorizontalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        p1Layout.setVerticalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(p1);
        p1.setBounds(120, 128, 5, 38);

        pnlAvata.setLayout(null);

        BackgroundLogUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BackgroundLogUp.jpg"))); // NOI18N
        pnlAvata.add(BackgroundLogUp);
        BackgroundLogUp.setBounds(0, 0, 410, 390);

        getContentPane().add(pnlAvata);
        pnlAvata.setBounds(470, 80, 410, 390);

        pnlDK.setBackground(new java.awt.Color(255, 255, 255));
        pnlDK.setForeground(new java.awt.Color(51, 51, 51));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Đăng nhập?");

        javax.swing.GroupLayout pnlDKLayout = new javax.swing.GroupLayout(pnlDK);
        pnlDK.setLayout(pnlDKLayout);
        pnlDKLayout.setHorizontalGroup(
            pnlDKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDKLayout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        pnlDKLayout.setVerticalGroup(
            pnlDKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDKLayout.createSequentialGroup()
                .addContainerGap(317, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(56, 56, 56))
        );

        getContentPane().add(pnlDK);
        pnlDK.setBounds(470, 80, 410, 390);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Login");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(390, 10, 114, 44);

        lblPass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPass.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblPass);
        lblPass.setBounds(134, 200, 90, 36);

        lblUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lblUser);
        lblUser.setBounds(134, 128, 120, 38);

        lblTextDK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTextDK.setForeground(new java.awt.Color(255, 255, 255));
        lblTextDK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTextDK.setText("Quên mật khẩu?");
        lblTextDK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTextDKMouseClicked(evt);
            }
        });
        getContentPane().add(lblTextDK);
        lblTextDK.setBounds(170, 400, 200, 17);

        lblPassA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPassA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/1 Img/login.png"))); // NOI18N
        getContentPane().add(lblPassA);
        lblPassA.setBounds(370, 200, 34, 36);

        lblUserA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/1 Img/profile.png"))); // NOI18N
        getContentPane().add(lblUserA);
        lblUserA.setBounds(370, 130, 34, 36);

        btnLogin.setBackground(new java.awt.Color(184, 69, 146));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setAlignmentY(0.0F);
        btnLogin.setBorder(null);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginMouseExited(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin);
        btnLogin.setBounds(120, 336, 292, 44);

        txtPass.setBackground(new Color(0,0,0,0));
        txtPass.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPass.setForeground(new java.awt.Color(255, 255, 255));
        txtPass.setText("123456");
        txtPass.setBorder(null);
        txtPass.setOpaque(false);
        txtPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPassMouseClicked(evt);
            }
        });
        getContentPane().add(txtPass);
        txtPass.setBounds(134, 200, 270, 36);

        txtUser.setBackground(new Color(0,0,0,0));
        txtUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtUser.setForeground(new java.awt.Color(255, 255, 255));
        txtUser.setText("TeoNV");
        txtUser.setBorder(null);
        txtUser.setOpaque(false);
        txtUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserMouseClicked(evt);
            }
        });
        getContentPane().add(txtUser);
        txtUser.setBounds(134, 128, 270, 38);

        p2.setBackground(new java.awt.Color(0, 198, 223));

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(p2);
        p2.setBounds(120, 200, 5, 36);

        pnlDN.setBackground(new Color(0,0,0,150));
        pnlDN.setForeground(new java.awt.Color(51, 51, 51));
        pnlDN.setLayout(null);
        getContentPane().add(pnlDN);
        pnlDN.setBounds(70, 80, 410, 390);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/BackgroundLogin.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 960, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        openDN();
        t2.start();
        chkDN();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        fillColor();
        t3.start();
    }//GEN-LAST:event_btnLoginMouseEntered

    private void btnLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseExited
        fillColor3();
        t5.start();
    }//GEN-LAST:event_btnLoginMouseExited

    private void lblTextDKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTextDKMouseClicked
        openDK();
        t.start();
    }//GEN-LAST:event_lblTextDKMouseClicked

    private void txtUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserMouseClicked
        if (txtUser.getText().matches("\\s*")) {
            lblUser.setText("");
        }
        if (txtPass.getText().matches("\\s*")) {
            lblPass.setText("Mật khẩu");
        } else {
            lblPass.setText("");
        }
    }//GEN-LAST:event_txtUserMouseClicked

    private void txtPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPassMouseClicked
        if (txtPass.getText().matches("\\s*")) {
            lblPass.setText("");
        }
        if (txtUser.getText().matches("\\s*")) {
            lblUser.setText("Tên đăng nhập");
        } else {
            lblUser.setText("");
        }
    }//GEN-LAST:event_txtPassMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frmLogin().setVisible(true);
//            }
//        });
        //</editor-fold>
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new frmLogin().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundLogUp;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPassA;
    private javax.swing.JLabel lblTextDK;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserA;
    private javax.swing.JPanel p1;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel pnlAvata;
    private javax.swing.JPanel pnlDK;
    private javax.swing.JPanel pnlDN;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

    private void setIconImage(ImageIcon imageIcon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
