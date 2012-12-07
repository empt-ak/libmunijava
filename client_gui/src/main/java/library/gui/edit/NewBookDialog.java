/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.edit;

import java.net.ConnectException;
import library.gui.ConnectionHolder;
import library.models.BookTableModel;
import library.webservice.BookDTO;
import library.webservice.BookStatus;
import library.webservice.Department;

/**
 *
 * @author Andrej
 */
public class NewBookDialog extends javax.swing.JDialog {
    
    private BookDTO bookDTO = new BookDTO();
    private ConnectionHolder holder;
    private BookTableModel btm;
    
    public void setReq(ConnectionHolder holder,BookTableModel btm)
    {
        this.holder = holder;
        this.btm = btm; 
    }
    
    private void valuesToObject()
    {
        try
        {
            bookDTO.setAuthor(jTextFieldBookAuthor.getText());
            bookDTO.setBookStatus(BookStatus.valueOf(jTextFieldBookStatus.getText()));
            bookDTO.setDepartment(Department.valueOf(jTextFieldBookDepatment.getText()));
            bookDTO.setTitle(jTextFieldBookTitle.getText());            
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        System.out.println("==from form obtained following book:"+bookDTO);
    }
    
    private void updateModel()
    {
        btm.clear();
        try
        {
            btm.addBooks(holder.getBws().getAllBooks());            
        }
        catch(ConnectException ce)
        {
            System.err.println(ce.getMessage());
        }
        
    }

    /**
     * Creates new form NewBookDialog
     */
    public NewBookDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle(java.util.ResourceBundle.getBundle("Messages").getString("gui.frame.books.button.create"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jTextFieldBookDepatment = new javax.swing.JTextField();
        jTextFieldBookStatus = new javax.swing.JTextField();
        jLabelBookEditTitle = new javax.swing.JLabel();
        jTextFieldBookAuthor = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        jLabelDepartment = new javax.swing.JLabel();
        jLabelBookAuthor = new javax.swing.JLabel();
        jLabelBookTitle = new javax.swing.JLabel();
        jTextFieldBookTitle = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Messages"); // NOI18N
        jButton1.setText(bundle.getString("gui.button.create")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonReset.setText(bundle.getString("gui.button.reset")); // NOI18N
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jTextFieldBookDepatment.setText(bundle.getString("gui.field.error")); // NOI18N

        jTextFieldBookStatus.setText(bundle.getString("gui.field.error")); // NOI18N

        jLabelBookEditTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelBookEditTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBookEditTitle.setText(bundle.getString("gui.frame.books.button.create")); // NOI18N

        jTextFieldBookAuthor.setText(bundle.getString("gui.field.error")); // NOI18N

        jLabelStatus.setText(bundle.getString("gui.book.availability")); // NOI18N

        jLabelDepartment.setText(bundle.getString("gui.book.department")); // NOI18N

        jLabelBookAuthor.setText(bundle.getString("gui.book.author")); // NOI18N

        jLabelBookTitle.setText(bundle.getString("gui.book.title")); // NOI18N

        jTextFieldBookTitle.setText(bundle.getString("gui.field.error")); // NOI18N

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
                                .addComponent(jLabelDepartment)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldBookDepatment, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelStatus)
                                .addGap(16, 16, 16)
                                .addComponent(jTextFieldBookStatus))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelBookTitle)
                                    .addComponent(jLabelBookAuthor))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldBookAuthor)
                                    .addComponent(jTextFieldBookTitle))))
                        .addGap(19, 19, 19)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jLabelBookTitle)
                    .addComponent(jTextFieldBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBookAuthor)
                    .addComponent(jTextFieldBookAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDepartment)
                    .addComponent(jTextFieldBookDepatment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStatus)
                    .addComponent(jTextFieldBookStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReset)
                    .addComponent(jButton1))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        valuesToObject();
        
        try
        {
            holder.getBws().createBook(bookDTO);
            updateModel();
            dispose();   
        }
        catch(ConnectException | IllegalArgumentException | NullPointerException e)
        {
            System.err.println(e.getMessage());
        }  
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        this.dispose();
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
            java.util.logging.Logger.getLogger(NewBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewBookDialog dialog = new NewBookDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelBookAuthor;
    private javax.swing.JLabel jLabelBookEditTitle;
    private javax.swing.JLabel jLabelBookTitle;
    private javax.swing.JLabel jLabelDepartment;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JTextField jTextFieldBookAuthor;
    private javax.swing.JTextField jTextFieldBookDepatment;
    private javax.swing.JTextField jTextFieldBookStatus;
    private javax.swing.JTextField jTextFieldBookTitle;
    // End of variables declaration//GEN-END:variables
}
