/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.create;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.security.NoSuchAlgorithmException;
import library.gui.ConnectionHolder;
import library.gui.tools.Tools;
import library.gui.models.UserRoleBoxModel;
import library.gui.models.UserTableModel;
import library.webservice.IllegalArgumentException_Exception;
import library.webservice.UserDTO;

/**
 *
 * @author Andrej,Emptak
 */
public class NewUserDialog extends javax.swing.JDialog {
    
    private UserDTO userDTO = new UserDTO();
    private ConnectionHolder holder;
    private UserTableModel utm;
    
    public void setReq(ConnectionHolder holder,UserTableModel utm)
    {
        this.holder = holder;
        this.utm = utm;
    }

    /**
     * Creates new form NewUserDialog
     */
    public NewUserDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelBookTitle = new javax.swing.JLabel();
        jLabelBookAuthor = new javax.swing.JLabel();
        jLabelDepartment = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextFieldUsername = new javax.swing.JTextField();
        jButtonReset = new javax.swing.JButton();
        jLabelBookEditTitle = new javax.swing.JLabel();
        jTextFieldRealName = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPasswordField1 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Messages"); // NOI18N
        setTitle(bundle.getString("gui.frame.user.button.user.create")); // NOI18N

        jLabelBookTitle.setText(bundle.getString("gui.user.username")); // NOI18N

        jLabelBookAuthor.setText(bundle.getString("gui.user.realname")); // NOI18N

        jLabelDepartment.setText(bundle.getString("gui.user.password")); // NOI18N

        jButton1.setText(bundle.getString("gui.button.create")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextFieldUsername.setText(bundle.getString("gui.field.error")); // NOI18N

        jButtonReset.setText(bundle.getString("gui.button.reset")); // NOI18N
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jLabelBookEditTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelBookEditTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBookEditTitle.setText(bundle.getString("gui.frame.user.button.user.create")); // NOI18N

        jTextFieldRealName.setText(bundle.getString("gui.field.error")); // NOI18N

        jLabelStatus.setText(bundle.getString("gui.user.systemrole")); // NOI18N

        jComboBox1.setModel(new UserRoleBoxModel());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelBookEditTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelBookTitle)
                                    .addComponent(jLabelDepartment)
                                    .addComponent(jLabelStatus))
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelBookAuthor)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldRealName)
                            .addComponent(jTextFieldUsername)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPasswordField1))
                        .addGap(19, 19, 19)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(131, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(27, 27, 27)
                .addComponent(jButtonReset)
                .addGap(116, 116, 116))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabelBookEditTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBookTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldRealName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBookAuthor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDepartment)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStatus)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReset)
                    .addComponent(jButton1))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        valuesToObject();
        
        try
        {
            holder.getUws().createUser(userDTO);
            updateModel();
            System.out.print("==Following user has been saved:");
            Tools.printEntity(this.userDTO);
            dispose();   
        }
        catch(ConnectException | IllegalArgumentException_Exception | IllegalArgumentException | NullPointerException e)
        {
            System.err.println(e.getMessage());
            Tools.createErrorDialog(e.getMessage());
        }  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        jTextFieldUsername.setText("");
        jTextFieldRealName.setText("");
        jComboBox1.setSelectedIndex(0);
        jPasswordField1.setText("");
    }//GEN-LAST:event_jButtonResetActionPerformed

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
            java.util.logging.Logger.getLogger(NewUserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewUserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewUserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewUserDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                NewUserDialog dialog = new NewUserDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabelBookAuthor;
    private javax.swing.JLabel jLabelBookEditTitle;
    private javax.swing.JLabel jLabelBookTitle;
    private javax.swing.JLabel jLabelDepartment;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextFieldRealName;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables

    private String passwordFromField()
    {
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<jPasswordField1.getPassword().length;i++)
        {
            sb.append(jPasswordField1.getPassword()[i]);
        }
        
        return sb.toString();
    }
    
    
    
    private void valuesToObject()
    {
        userDTO.setUsername(jTextFieldUsername.getText());
        userDTO.setRealName(jTextFieldRealName.getText());
        userDTO.setSystemRole(jComboBox1.getSelectedItem().toString());
        try
        {
            
            userDTO.setPassword(Tools.SHA1(passwordFromField()));            
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        System.out.print("==from form obtained following book:");
        Tools.printEntity(userDTO);
    }
    
    private void updateModel()
    {
        utm.clear();
        try
        {
            utm.addUsers(holder.getUws().getUsers());            
        }
        catch(ConnectException ce)
        {
            System.err.println(ce.getMessage());
        }        
    }
}
