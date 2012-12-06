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
import library.webservice.BookWebService;
import library.webservice.Department;


/**
 *
 * @author Emptak
 */
public class EditBookDialog extends javax.swing.JDialog {

    private BookDTO bookDTO;
    private ConnectionHolder holder;
    private BookTableModel btm;
    
    public void setReq(BookDTO bookDTO,ConnectionHolder holder,BookTableModel btm)
    {
        this.bookDTO = bookDTO;
        this.holder = holder;
        this.btm = btm;
        
        myInit();
    }
   
    private void myInit()
    {
        jTextFieldBookAuthor.setText(bookDTO.getAuthor());
        jTextFieldBookDepatment.setText(bookDTO.getDepartment().toString());
        jTextFieldBookID.setText(bookDTO.getBookID().toString());
        jTextFieldBookStatus.setText(bookDTO.getBookStatus().toString());
        jTextFieldBookTitle.setText(bookDTO.getTitle());        
    }
    
    private void valuesToObject()
    {
        try
        {
            bookDTO.setAuthor(jTextFieldBookAuthor.getText());
            bookDTO.setBookID(new Long(jTextFieldBookID.getText()));
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
     * Creates new form EditBookDialog
     */
    public EditBookDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle("Edit book");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelBookEditTitle = new javax.swing.JLabel();
        jLabelBookID = new javax.swing.JLabel();
        jLabelBookTitle = new javax.swing.JLabel();
        jLabelBookAuthor = new javax.swing.JLabel();
        jLabelDepartment = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jTextFieldBookID = new javax.swing.JTextField();
        jTextFieldBookTitle = new javax.swing.JTextField();
        jTextFieldBookAuthor = new javax.swing.JTextField();
        jTextFieldBookDepatment = new javax.swing.JTextField();
        jTextFieldBookStatus = new javax.swing.JTextField();
        jButtonReset = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelBookEditTitle.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelBookEditTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBookEditTitle.setText("Edit book");

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Messages"); // NOI18N
        jLabelBookID.setText(bundle.getString("gui.book.bookid")); // NOI18N

        jLabelBookTitle.setText(bundle.getString("gui.book.title")); // NOI18N

        jLabelBookAuthor.setText(bundle.getString("gui.book.author")); // NOI18N

        jLabelDepartment.setText(bundle.getString("gui.book.department")); // NOI18N

        jLabelStatus.setText(bundle.getString("gui.book.availability")); // NOI18N

        jTextFieldBookID.setText("ERROR: VALUE NOT LOADED");
        jTextFieldBookID.setEnabled(false);

        jTextFieldBookTitle.setText("ERROR: VALUE NOT LOADED");

        jTextFieldBookAuthor.setText("ERROR: VALUE NOT LOADED");
        jTextFieldBookAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBookAuthorActionPerformed(evt);
            }
        });

        jTextFieldBookDepatment.setText("ERROR: VALUE NOT LOADED");

        jTextFieldBookStatus.setText("ERROR: VALUE NOT LOADED");

        jButtonReset.setText(bundle.getString("gui.button.reset")); // NOI18N

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
                                .addComponent(jTextFieldBookDepatment, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelStatus)
                                .addGap(16, 16, 16)
                                .addComponent(jTextFieldBookStatus))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelBookID)
                                    .addComponent(jLabelBookTitle)
                                    .addComponent(jLabelBookAuthor))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldBookAuthor)
                                    .addComponent(jTextFieldBookTitle)
                                    .addComponent(jTextFieldBookID))))
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
                .addContainerGap()
                .addComponent(jLabelBookEditTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBookID)
                    .addComponent(jTextFieldBookID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldBookAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBookAuthorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBookAuthorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
             
        valuesToObject();
        
        try
        {
            holder.getBws().updateBook(bookDTO);
            updateModel();
            dispose();
            
        }
        catch(ConnectException | IllegalArgumentException | NullPointerException e)
        {
            System.err.println(e.getMessage());
        }  
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(EditBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditBookDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditBookDialog dialog = new EditBookDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelBookID;
    private javax.swing.JLabel jLabelBookTitle;
    private javax.swing.JLabel jLabelDepartment;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JTextField jTextFieldBookAuthor;
    private javax.swing.JTextField jTextFieldBookDepatment;
    private javax.swing.JTextField jTextFieldBookID;
    private javax.swing.JTextField jTextFieldBookStatus;
    private javax.swing.JTextField jTextFieldBookTitle;
    // End of variables declaration//GEN-END:variables
}
