
 
package dsmafornuesau;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author MikeD
 */
public class DarBaja extends javax.swing.JFrame {

    /**
     * Creates new form DarBaja
     */
    int vcbi=0,vcbfi=0,vcbee=0,vcbil=0,vcbfc=0,vcbci=0,vcbr=0;
    String Mot="";
    Connection Con;
    Statement St;
    ResultSet Rs;
    public DarBaja() {
        initComponents();
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(255,255,255));
        setResizable(false);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        idb = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        nombaja = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        apbaja = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ambaja = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        imp = new javax.swing.JCheckBox();
        fali = new javax.swing.JCheckBox();
        ebri = new javax.swing.JCheckBox();
        inla = new javax.swing.JCheckBox();
        fincontra = new javax.swing.JCheckBox();
        malacon = new javax.swing.JCheckBox();
        renuncia = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        darbaja = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        LP = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        motivo = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1350, 650));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("ID del personal que se dara de baja:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 60, -1, -1));
        getContentPane().add(idb, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 132, -1));
        idb.setEditable(false);

        jLabel2.setText("Nombre:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, -1, -1));
        getContentPane().add(nombaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 99, -1));
        nombaja.setEditable(false);

        jLabel3.setText("Apellido Paterno:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 60, -1, -1));
        getContentPane().add(apbaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 60, 96, -1));
        apbaja.setEditable(false);

        jLabel4.setText("Apellido Materno:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 60, -1, -1));

        ambaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ambajaActionPerformed(evt);
            }
        });
        getContentPane().add(ambaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 60, 95, -1));
        ambaja.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Motivo(s) por lo(s) que se dio de baja al personal.");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, -1));

        imp.setText("Impuntualidad.");
        imp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                impActionPerformed(evt);
            }
        });
        getContentPane().add(imp, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, -1));

        fali.setText("Faltas injustificadas.");
        fali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faliActionPerformed(evt);
            }
        });
        getContentPane().add(fali, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, -1, -1));

        ebri.setText("Se presentò en estado de ebriedad.");
        ebri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ebriActionPerformed(evt);
            }
        });
        getContentPane().add(ebri, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, -1, -1));

        inla.setText("Incumplimiento de labores.");
        inla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inlaActionPerformed(evt);
            }
        });
        getContentPane().add(inla, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, -1, -1));

        fincontra.setText("Fin de contrato.");
        fincontra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fincontraActionPerformed(evt);
            }
        });
        getContentPane().add(fincontra, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, -1, -1));

        malacon.setText("Conducta inadecuada.");
        malacon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                malaconActionPerformed(evt);
            }
        });
        getContentPane().add(malacon, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 530, -1, -1));

        renuncia.setText("Renuncia.");
        renuncia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renunciaActionPerformed(evt);
            }
        });
        getContentPane().add(renuncia, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 580, -1, -1));

        jPanel1.setBackground(java.awt.SystemColor.activeCaptionBorder);

        darbaja.setText("Dar de baja");
        darbaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                darbajaActionPerformed(evt);
            }
        });

        jButton3.setText("Regresar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Dar de baja personal");

        LP.setText("Limpiar Campos");
        LP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(darbaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel6)
                .addGap(58, 58, 58)
                .addComponent(darbaja, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        motivo.setColumns(20);
        motivo.setRows(5);
        jScrollPane1.setViewportView(motivo);
        motivo.setLineWrap(true);

        motivo.setWrapStyleWord(true);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 270, 450, 220));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Otros motivos.");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 220, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ambajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ambajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ambajaActionPerformed

    private void impActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_impActionPerformed
          if (imp.isSelected()) {
              vcbi=1; 
             } 
          else {vcbi=0;}
        
    }//GEN-LAST:event_impActionPerformed

    private void inlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inlaActionPerformed
        // TODO add your handling code here:
        if (inla.isSelected()) {
              vcbil=1;
             }
        else{
              vcbil=0;
            }
        
    }//GEN-LAST:event_inlaActionPerformed

    private void renunciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renunciaActionPerformed
        // TODO add your handling code here:
        if (renuncia.isSelected()) {
              vcbr=1;
             }
        else{vcbr=0;}
    }//GEN-LAST:event_renunciaActionPerformed

    private void faliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faliActionPerformed
        // TODO add your handling code here:
        if (fali.isSelected()) {
            vcbfi=1;     
        }
        else{vcbfi=0;}
        
    }//GEN-LAST:event_faliActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      this.dispose();
      new Personal().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ebriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ebriActionPerformed

        if (ebri.isSelected()) {
              vcbee=1;
             }
        else{vcbee=0;}
    }//GEN-LAST:event_ebriActionPerformed

    private void darbajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_darbajaActionPerformed
     
        if(motivo.getText().isEmpty() && (vcbee==0&&vcbi==0&&vcbfi==0&&vcbil==0&&vcbfc==0&&vcbci==0&&vcbr==0))JOptionPane.showMessageDialog(null,"Campos vacios");
        else{
        try {
             Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
             St=Con.createStatement();
             String Hoy="";
             Rs=St.executeQuery("select CURDATE() as Fecha_Baja;");
             while(Rs.next())Hoy=(Rs.getString("Fecha_Baja"));
             if(imp.isSelected()){Mot=Mot+" \\nImpuntualidad ";}
             if(fali.isSelected()){Mot=Mot+" \\nFaltas Injustificadas ";}
             if(ebri.isSelected()){Mot=Mot+" \\nSe presento en estado de ebriedad ";}
             if(inla.isSelected()){Mot=Mot+" \\nIncumplimiento de labores ";}
             if(fincontra.isSelected()){Mot=Mot+" \\nFin de contrato ";}
             if(malacon.isSelected()){Mot=Mot+" \\nMala conducta ";}
             if(renuncia.isSelected()){Mot=Mot+" \\nRenuncia ";}
             String query="insert into Bajas(ID_Per,motivo,Fecha_Baja) values('"+idb.getText()+"','"+motivo.getText()+Mot+"','"+Hoy+"');";           
                    St.executeUpdate(query);
             query="update Personal set condicion= 'Inactivo' where ID_Per = "+Integer.parseInt(idb.getText())+";";           
                    St.executeUpdate(query);
                    idb.setText("");
                    nombaja.setText("");
                    apbaja.setText("");
                    ambaja.setText("");
                    motivo.setText("");
                    imp.setSelected(false);
                    fali.setSelected(false);
                    ebri.setSelected(false);
                    inla.setSelected(false);
                    fincontra.setSelected(false);
                    malacon.setSelected(false);
                    renuncia.setSelected(false);
                    JOptionPane.showMessageDialog(null,"Registo dado de baja exitosamente");
                    this.dispose();
                    new Personal().setVisible(true);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,ex);
             Mot="";
        }
       }//else campos vacios     
        
    }//GEN-LAST:event_darbajaActionPerformed

    private void malaconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_malaconActionPerformed
        // TODO add your handling code here:
        if (malacon.isSelected()) {
              vcbci=1;
             }
        else{vcbci=0;}
    }//GEN-LAST:event_malaconActionPerformed

    private void fincontraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fincontraActionPerformed
        // TODO add your handling code here:
        if (fincontra.isSelected()) {
              //Mot=Mot+" Fin de contrato ";
              vcbfc=1;
             }
        else{vcbfc=0;}
    }//GEN-LAST:event_fincontraActionPerformed

    private void LPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LPActionPerformed
        // TODO add your handling code here:
        motivo.setText("");
        Mot="";
        imp.setSelected(false);
        fali.setSelected(false);
        ebri.setSelected(false);
        inla.setSelected(false);
        fincontra.setSelected(false);
        malacon.setSelected(false);
        renuncia.setSelected(false);
        
    }//GEN-LAST:event_LPActionPerformed

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
            java.util.logging.Logger.getLogger(DarBaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DarBaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DarBaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DarBaja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DarBaja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LP;
    public static javax.swing.JTextField ambaja;
    public static javax.swing.JTextField apbaja;
    private javax.swing.JButton darbaja;
    private javax.swing.JCheckBox ebri;
    private javax.swing.JCheckBox fali;
    private javax.swing.JCheckBox fincontra;
    public static javax.swing.JTextField idb;
    private javax.swing.JCheckBox imp;
    private javax.swing.JCheckBox inla;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox malacon;
    private javax.swing.JTextArea motivo;
    public static javax.swing.JTextField nombaja;
    private javax.swing.JCheckBox renuncia;
    // End of variables declaration//GEN-END:variables
}
