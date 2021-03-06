
package dsmafornuesau;

//importaciones de librerias necesarias
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class RegistrarUsuario extends javax.swing.JFrame {
    Connection Con; //Conexion
    Statement St;   //Ejecutar comandos
    ResultSet Rs;   //Resultado de la consulta
    /**
     * Creates new form UserControl
     */
    //declaracion de variables globales
    static int s,i,u,d;
    //metodo constructor de la clase
    public RegistrarUsuario() {
        //se ajusta el encabezado de la ventana
        this.setTitle("Registrar usuario - Usuario: "+Globales.User);
        //se inicializan los componentes
        initComponents();
        setLocationRelativeTo(null);//para que aparesca centrado
        s=0;i=0;u=0;d=0;
        //no se permite reajustar la ventana
        setResizable(false);
        //el cursor se pone por defecto en el campo de texto del nombre
        nombre.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        Cancelar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        contra = new javax.swing.JPasswordField();
        contra2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 0, 153));

        jPanel1.setBackground(java.awt.SystemColor.activeCaptionBorder);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Registrar Usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel1)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel2.setText("Repite la Contraseña:");

        jLabel3.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Corbel", 0, 18)); // NOI18N
        jLabel4.setText("Contraseña:");

        nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreActionPerformed(evt);
            }
        });
        nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreKeyTyped(evt);
            }
        });

        Cancelar.setBackground(new java.awt.Color(204, 204, 204));
        Cancelar.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        Cancelar.setForeground(new java.awt.Color(255, 255, 255));
        Cancelar.setText("Cancelar");
        Cancelar.setFocusPainted(false);
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarBotonAgregar(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Aceptar");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3BotonAgregar(evt);
            }
        });

        contra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contraKeyTyped(evt);
            }
        });

        contra2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                contra2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contra2KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(Cancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contra)
                    .addComponent(contra2))
                .addGap(33, 33, 33))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(293, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(Cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(339, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void CancelarBotonAgregar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarBotonAgregar
       //se cierra esta ventana y se abre la del menu principal
        this.dispose();
        new ControlPanel().setVisible(true);   
    }//GEN-LAST:event_CancelarBotonAgregar

    private void jButton3BotonAgregar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3BotonAgregar
        //se valida que no haya campos vacios
        if(nombre.getText().isEmpty() || contra.getText().isEmpty() || contra2.getText().isEmpty()){JOptionPane.showMessageDialog(null,"Campos vacios");}
        else{
           //se valida que ambas contraseñas sean iguales
          if(contra.getText().equals(contra2.getText())){
            
            //se le pregunta al usuario si esta seguro de sus acciones
            Object [] opciones ={"Aceptar","Cancelar"};
            int eleccion = JOptionPane.showOptionDialog(rootPane,"¿Estas seguro de agregar un nuevo registro?","Mensaje de Confirmacion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
            
            //si el usuario selecciono que aceptar
            if (eleccion == JOptionPane.YES_OPTION)
            {          
                 try {
                     //se raliza conexion con la base de datos
                    Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
                    St=Con.createStatement();
                    //se ejecutan los comandos necesarios para crear al nuevo usuario     
                    St.executeUpdate("CREATE USER'"+nombre.getText()+"'@localhost IDENTIFIED BY'"+contra.getText()+"';");
                    St.executeUpdate("GRANT ALL PRIVILEGES ON dsmafornu.* TO '"+nombre.getText()+"'@'localhost';");        
                    //St.executeUpdate("GRANT ALL PRIVILEGES ON *.* TO '"+nombre.getText()+"'@localhost REQUIRE NONE WITH GRANT OPTION;");
                    JOptionPane.showMessageDialog(null,"Registro agregado exitosamente");
                    //se limpian los campos de texto
                    nombre.setText("");
                    contra.setText("");
                    contra2.setText("");
                }   catch (Exception e) {
                    //si hay algun error aqui se muestra
                    JOptionPane.showMessageDialog(null,e);
                    }
                 
            }//if de confirmacion
          }
          else{
              //se llega a esta condicion si las contraseñas no son iguales
            JOptionPane.showMessageDialog(null,"La contraseña debe coincidir");
          }
        }            
    }//GEN-LAST:event_jButton3BotonAgregar

    private void nombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyPressed
        //si el cursor esta en el campo de texto del nombre
        //y se presiona enter el cursor se mueve al campo de la contraseña
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          contra.requestFocus();
        }
    }//GEN-LAST:event_nombreKeyPressed

    private void contraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contraKeyPressed
        //si el cursor esta en el campo de texto contra        
        //y presiona enter el cursor se mueve al campo de contra2
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          contra2.requestFocus();
        }
        //si lo contrario, si se presiona la flecha arriba el cursor se mueve al campo nombre
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          nombre.requestFocus();
        }
    }//GEN-LAST:event_contraKeyPressed

    private void contra2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contra2KeyPressed
        //si el cursor esta en el campo de texto contra       
        //y se presiona el boton arriba el cursor se dirige al campo contra
        if(evt.getKeyCode()==KeyEvent.VK_UP){
          contra.requestFocus();
        }
    }//GEN-LAST:event_contra2KeyPressed

    private void nombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyTyped
        //se obtienen el caracter ingresado y los caracteres que 
        //que ya estan en el campo de texto
        String NOMBRE=nombre.getText();
          char k=evt.getKeyChar();
         //solo se analizan letras y digitos
         if(Character.isLetter(k)||Character.isDigit(k)){
           //si el campo de texto no esta vacio
             if(NOMBRE.length()>0){
             //solo se permiten 15 caracteres
             if(NOMBRE.length()==15){
              getToolkit().beep();
              evt.consume();
            }  
          }
          //si el campo de texto esta vacio
          //no se permiten espacios o puntos
          else if(NOMBRE.isEmpty()){
            if(k==' '||k=='.'){
              getToolkit().beep();
              evt.consume();  
            }
          }                 
         }
         //si no es letra o digito no se acepta el caracter
         else{
              evt.consume();  
         }                 
    }//GEN-LAST:event_nombreKeyTyped

    private void contraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contraKeyTyped
        //se obtienen el caracter ingresado y los caracteres que 
        //que ya estan en el campo de texto
        char k=evt.getKeyChar();
        String NUMERO=contra.getText();
        //solo se analizan letras y digitos
       if(Character.isDigit(k)||Character.isLetter(k)){
          //no se aceptan espacios
          if(k==' '){
          getToolkit().beep();
          evt.consume();      
        }       
        else if(NUMERO.length()>0){ 
           //solo se aceptan 15 caracteres
           if(NUMERO.length()==15){
            getToolkit().beep();
            evt.consume();
           }                    
        }
       }
       //no se acepta el caracter si no es letra o digito
       else{          
            evt.consume();
       }
    }//GEN-LAST:event_contraKeyTyped

    private void contra2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contra2KeyTyped
         //se obtienen el caracter ingresado y los caracteres que 
        //que ya estan en el campo de texto
        char k=evt.getKeyChar();
        String NUMERO=contra2.getText();
        //solo se analizan letras y digitos
       if(Character.isDigit(k)||Character.isLetter(k)){
          //no se aceptan espacios
          if(k==' '){
          getToolkit().beep();
          evt.consume();      
        }       
        else if(NUMERO.length()>0){ 
           //solo se aceptan 15 caracteres
           if(NUMERO.length()==15){
            getToolkit().beep();
            evt.consume();
           }                    
        }
       }
       //no se acepta el caracter si no es letra o digito
       else{          
            evt.consume();
       }
    }//GEN-LAST:event_contra2KeyTyped

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
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancelar;
    private javax.swing.JPasswordField contra;
    private javax.swing.JPasswordField contra2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField nombre;
    // End of variables declaration//GEN-END:variables
}
