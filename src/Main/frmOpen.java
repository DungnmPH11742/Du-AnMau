package Main;

import JDBCHelper.ConnectHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.Timer;

public class frmOpen extends javax.swing.JFrame {

    Timer t;
    int i = 0;

    public frmOpen() {
        initComponents();
        setLocationRelativeTo(null);
        openFrmLogin();
        t.start();
    }
    
    //Phương thức mở form Login
    public void openFrmLogin() {
        t = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i = prbRun.getValue();
                if (i < 100) {
                    i++;
                    prbRun.setValue(i);
                } else {
                    frmLogin login = new frmLogin();
                    login.setVisible(true);
                    hideFrm();
                    t.stop();
                }
            }
        });
    }

    public void hideFrm() {
        this.dispose();
    }

   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        prbRun = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1161, 722));
        setMinimumSize(new java.awt.Dimension(1161, 722));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1161, 722));
        getContentPane().setLayout(null);

        prbRun.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        prbRun.setStringPainted(true);
        getContentPane().add(prbRun);
        prbRun.setBounds(-1, 705, 1161, 20);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Library.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1160, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                if ("".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmOpen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmOpen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmOpen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmOpen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmOpen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar prbRun;
    // End of variables declaration//GEN-END:variables
}
