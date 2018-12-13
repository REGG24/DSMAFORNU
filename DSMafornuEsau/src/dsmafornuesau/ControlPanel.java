
package dsmafornuesau;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ruben
 */
public class ControlPanel extends javax.swing.JFrame {

    /**
     * Creates new form ControlPanel
     */
    public ControlPanel() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(205,92,92));
        setLocationRelativeTo(null);
        setResizable(false);
        this.setTitle("Menu principal - Usuario: "+Globales.User);
        
        ((JPanel)getContentPane()).setOpaque(false);
        ImageIcon uno=new ImageIcon(this.getClass().getResource("/Imagenes/claro.jpg"));
        JLabel fondo= new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());        
        if(Globales.User.equals("Administrador")||Globales.User.equals("root")){
          nuevousuario.setEnabled(true);
        }
        else nuevousuario.setEnabled(false);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        nuevousuario1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        gestionprestamos = new javax.swing.JButton();
        nuevousuario = new javax.swing.JButton();
        respaldobasedatos = new javax.swing.JButton();
        restauracion = new javax.swing.JButton();
        gestionpuesto = new javax.swing.JButton();
        gestionsucursal = new javax.swing.JButton();
        LogOutButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenuItem1.setText("jMenuItem1");

        nuevousuario1.setBackground(new java.awt.Color(255, 255, 255));
        nuevousuario1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        nuevousuario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario2.png"))); // NOI18N
        nuevousuario1.setToolTipText("");
        nuevousuario1.setBorderPainted(false);
        nuevousuario1.setContentAreaFilled(false);
        nuevousuario1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nuevousuario1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nuevousuario1.setIconTextGap(-3);
        nuevousuario1.setLabel("Registrar nuevo usuario");
        nuevousuario1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Uusario3.png"))); // NOI18N
        nuevousuario1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario1.png"))); // NOI18N
        nuevousuario1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        nuevousuario1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nuevousuario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevousuario1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Personal2.png"))); // NOI18N
        jButton5.setText("Gestionar Personal");
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setIconTextGap(-3);
        jButton5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Personal3.png"))); // NOI18N
        jButton5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Personal1.png"))); // NOI18N
        jButton5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        gestionprestamos.setBackground(new java.awt.Color(255, 255, 255));
        gestionprestamos.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        gestionprestamos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Prestamo2.png"))); // NOI18N
        gestionprestamos.setText("Gestionar Préstamos");
        gestionprestamos.setBorderPainted(false);
        gestionprestamos.setContentAreaFilled(false);
        gestionprestamos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gestionprestamos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gestionprestamos.setIconTextGap(-3);
        gestionprestamos.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Prestamo3.png"))); // NOI18N
        gestionprestamos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Prestamo1.png"))); // NOI18N
        gestionprestamos.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gestionprestamos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gestionprestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionprestamosActionPerformed(evt);
            }
        });

        nuevousuario.setBackground(new java.awt.Color(255, 255, 255));
        nuevousuario.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        nuevousuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario2.png"))); // NOI18N
        nuevousuario.setToolTipText("");
        nuevousuario.setBorderPainted(false);
        nuevousuario.setContentAreaFilled(false);
        nuevousuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nuevousuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nuevousuario.setIconTextGap(-3);
        nuevousuario.setLabel("Registrar nuevo usuario");
        nuevousuario.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Uusario3.png"))); // NOI18N
        nuevousuario.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Usuario1.png"))); // NOI18N
        nuevousuario.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        nuevousuario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nuevousuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevousuarioActionPerformed(evt);
            }
        });

        respaldobasedatos.setBackground(new java.awt.Color(255, 255, 255));
        respaldobasedatos.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        respaldobasedatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Respaldo2.png"))); // NOI18N
        respaldobasedatos.setText("Respaldar base de datos");
        respaldobasedatos.setToolTipText("");
        respaldobasedatos.setBorderPainted(false);
        respaldobasedatos.setContentAreaFilled(false);
        respaldobasedatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        respaldobasedatos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        respaldobasedatos.setIconTextGap(-3);
        respaldobasedatos.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Respaldo3.png"))); // NOI18N
        respaldobasedatos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Respaldo1.png"))); // NOI18N
        respaldobasedatos.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        respaldobasedatos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        respaldobasedatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                respaldobasedatosActionPerformed(evt);
            }
        });

        restauracion.setBackground(new java.awt.Color(255, 255, 255));
        restauracion.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        restauracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Restaurar2.png"))); // NOI18N
        restauracion.setText("Restaurar base de datos");
        restauracion.setToolTipText("");
        restauracion.setBorderPainted(false);
        restauracion.setContentAreaFilled(false);
        restauracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        restauracion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        restauracion.setIconTextGap(-3);
        restauracion.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Restaurar3.png"))); // NOI18N
        restauracion.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Restaurar1.png"))); // NOI18N
        restauracion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        restauracion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        restauracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restauracionActionPerformed(evt);
            }
        });

        gestionpuesto.setBackground(new java.awt.Color(255, 255, 255));
        gestionpuesto.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        gestionpuesto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/puesto2.png"))); // NOI18N
        gestionpuesto.setText("Gestionar Puestos");
        gestionpuesto.setBorderPainted(false);
        gestionpuesto.setContentAreaFilled(false);
        gestionpuesto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gestionpuesto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gestionpuesto.setIconTextGap(-3);
        gestionpuesto.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/puesto3.png"))); // NOI18N
        gestionpuesto.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/puesto1.png"))); // NOI18N
        gestionpuesto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gestionpuesto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gestionpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionpuestoActionPerformed(evt);
            }
        });

        gestionsucursal.setBackground(new java.awt.Color(255, 255, 255));
        gestionsucursal.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        gestionsucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sucursal2.png"))); // NOI18N
        gestionsucursal.setText("Gestionar Sucursales");
        gestionsucursal.setBorderPainted(false);
        gestionsucursal.setContentAreaFilled(false);
        gestionsucursal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gestionsucursal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gestionsucursal.setIconTextGap(-3);
        gestionsucursal.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sucursal3.png"))); // NOI18N
        gestionsucursal.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sucursal1.png"))); // NOI18N
        gestionsucursal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gestionsucursal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gestionsucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gestionsucursalActionPerformed(evt);
            }
        });

        LogOutButton.setBackground(new java.awt.Color(255, 255, 255));
        LogOutButton.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        LogOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout2.png"))); // NOI18N
        LogOutButton.setText("  Cerrar sesión");
        LogOutButton.setToolTipText("");
        LogOutButton.setBorderPainted(false);
        LogOutButton.setContentAreaFilled(false);
        LogOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LogOutButton.setIconTextGap(-3);
        LogOutButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout3.png"))); // NOI18N
        LogOutButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout1.png"))); // NOI18N
        LogOutButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        LogOutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        LogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButtonActionPerformed(evt);
            }
        });
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton5)
                .addGap(26, 26, 26)
                .addComponent(gestionprestamos)
                .addGap(27, 27, 27)
                .addComponent(gestionpuesto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gestionsucursal)
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(nuevousuario)
                        .addGap(39, 39, 39)
                        .addComponent(respaldobasedatos)
                        .addGap(42, 42, 42)
                        .addComponent(restauracion))
                    .addComponent(LogOutButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gestionprestamos, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gestionpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gestionsucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(respaldobasedatos, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restauracion, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nuevousuario, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        new Personal().setVisible(true);        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void gestionprestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionprestamosActionPerformed
        new GestionarPrestamos().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gestionprestamosActionPerformed

    private void respaldobasedatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respaldobasedatosActionPerformed
     
        JFrame respaldo=new JFrame();//creo un jframe
       JFileChooser fileChooser = new JFileChooser();//un objeto del tipo jfilechooser
       fileChooser.setDialogTitle("Elige la ubicacion para tu respaldo"); //con esto le pongo el titulo al frame         
       
       //creo una variable int para obtener si le da al boton guardar
       int userSelection = fileChooser.showSaveDialog(respaldo);//despues le digo que mi objeto filechooser pertenece al tipo guardar y esta en el frame respaldo    
       File fileToSave = fileChooser.getSelectedFile();
       String ruta=fileToSave.getAbsolutePath();//obtengo la ruta de la carpeta
       
        try {
            Runtime runtime=Runtime.getRuntime();           
            FileWriter fw=new FileWriter(ruta);
             //se ejecuta el comando para realizar el respaldo
            //para xamp
            //Process p =runtime.exec("C:\\xampp\\mysql\\bin\\mysqldump --opt --password= --user=root --database= dsmafornu -R");
            //para mysql essential
           Process p =runtime.exec("C:\\mysql\\bin\\mysqldump --opt --password= --user=root --database= dsmafornu -R");
            
            //se preparan variables de lectura y escritura
            InputStreamReader is=new InputStreamReader(p.getInputStream());
            BufferedReader br=new BufferedReader(is);
            
            //se hace el respaldo a base de escritura
            String line;
            while((line=br.readLine())!=null){
             fw.write(line + "\n");
            }
            
            //se cierran las variables de escritura
            fw.close();
            is.close();
            br.close();
            JOptionPane.showMessageDialog(null,"Respaldo creado exitosamente");
        } catch (Exception e) {
            //si hay algun error aqui se muestra
            JOptionPane.showMessageDialog(null,e);
        }           
    }//GEN-LAST:event_respaldobasedatosActionPerformed
    
    
   
    private void restauracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restauracionActionPerformed
       //se invoca a la ventana restauracion
        new Restauracion().setVisible(true);
    }//GEN-LAST:event_restauracionActionPerformed

    private void nuevousuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevousuarioActionPerformed
     
     this.dispose();
      new RegistrarUsuario().setVisible(true);
    }//GEN-LAST:event_nuevousuarioActionPerformed

    private void gestionpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionpuestoActionPerformed
       new PuestoD().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gestionpuestoActionPerformed

    private void gestionsucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gestionsucursalActionPerformed
        new Sucursales().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gestionsucursalActionPerformed

    private void nuevousuario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevousuario1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevousuario1ActionPerformed

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_LogOutButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ControlPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LogOutButton;
    private javax.swing.JButton gestionprestamos;
    private javax.swing.JButton gestionpuesto;
    private javax.swing.JButton gestionsucursal;
    private javax.swing.JButton jButton5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JButton nuevousuario;
    private javax.swing.JButton nuevousuario1;
    private javax.swing.JButton respaldobasedatos;
    private javax.swing.JButton restauracion;
    // End of variables declaration//GEN-END:variables
}
