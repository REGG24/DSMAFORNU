
package dsmafornuesau;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class GestionarPrestamos extends javax.swing.JFrame {
    Connection Con;
    Statement St;
    ResultSet Rs;
    DateFormat df=DateFormat.getDateInstance();
    static int x=0;
    TextAutoCompleter lista;
    public GestionarPrestamos() {
        initComponents();
        setLocationRelativeTo(null);
        getContentPane().setBackground(new java.awt.Color(255,255,255));
        setResizable(false);
        //autocompleta= new TextAutoCompleter(busqueda);
        lista = new TextAutoCompleter(busqueda, new AutoCompleterCallback() {
            @Override
            public void callback(Object selectedItem) {
                consultar((String)selectedItem);
                String sentenciaTabla="SELECT prestamos.ID_Pres, personal.ID_Per, personal.nom_p,personal.ap,personal.am,prestamos.Fecha_Prestamo,prestamos.cantidad FROM personal INNER JOIN prestamos ON personal.ID_Per=prestamos.ID_Per and concat(personal.nom_p,' ',personal.ap,' ',personal.am) = '"+(String)selectedItem+"';";
                tabla(sentenciaTabla);
            }
           });  
        ValidaFecha();
    }
    public void ValidaFecha()
    {
     Calendar cal= Calendar.getInstance();
     LocalDate ahora= LocalDate.now();
     Date date= Date.from(ahora.atStartOfDay(ZoneId.systemDefault()).toInstant());
     cal.add(Calendar.DAY_OF_YEAR,-0);
     Date max =cal.getTime();
     fecha.setSelectableDateRange(max,date);
    }
    


  
    @SuppressWarnings("unchecked")
    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        id = new javax.swing.JTextField();
        idp = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        abono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        busqueda = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        fecha = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(214, 213));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Gestionar");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Préstamos");

        jButton3.setText("Nuevo Préstamo");
        jButton3.setMaximumSize(new java.awt.Dimension(143, 23));
        jButton3.setMinimumSize(new java.awt.Dimension(143, 23));
        jButton3.setPreferredSize(new java.awt.Dimension(143, 23));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Consultar Préstamo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Regresar");
        jButton6.setMaximumSize(new java.awt.Dimension(143, 23));
        jButton6.setMinimumSize(new java.awt.Dimension(143, 23));
        jButton6.setPreferredSize(new java.awt.Dimension(143, 23));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(29, 29, 29)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Identificador del Préstamo", "Identificador del Personal", "Nombre del Personal", "Apellido Paterno", "Apellido Materno", "Fecha Préstamo", "Cantidad  de abono"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        id.setEnabled(false);
        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        idp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idpKeyTyped(evt);
            }
        });

        jLabel3.setText("Fecha del préstamo:");

        jLabel4.setText("ID del personal:");

        jLabel5.setText("ID del préstamo:");

        abono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                abonoKeyTyped(evt);
            }
        });

        jLabel6.setText("Cantidad de abono");

        jLabel7.setText("Buscar:");

        busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedaActionPerformed(evt);
            }
        });
        busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busquedaKeyTyped(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre del personal", "ID del prestamo" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        fecha.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 849, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(busqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(id)
                            .addComponent(idp, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(abono, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                    .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(fecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(abono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(3, 3, 3)))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        
    }//GEN-LAST:event_idActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      String fechap;
      String condicion="";
     if(fecha.getDate()==null|| abono.getText().isEmpty()||idp.getText().isEmpty())
      {
          JOptionPane.showMessageDialog(null,"Campos Vacios");
      }
      else{
      Object [] opciones ={"Aceptar","Cancelar"};
      int eleccion = JOptionPane.showOptionDialog(rootPane,"Estas seguro de agregar un nuevo registro","Mensaje de Confirmacion",
      JOptionPane.YES_NO_OPTION,
      JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
      if(eleccion == JOptionPane.YES_OPTION)
     {
     try{
        String busid=idp.getText();
        int abonop=Integer.parseInt(abono.getText());
        fechap=GetFormatoFecha("fecha");
         
        Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
        St=Con.createStatement();       
       
        //se checa que el personal a recibir prestamo este activo
        Rs = St.executeQuery("select condicion as Condi from personal where ID_Per="+busid+";");
        while(Rs.next()){condicion=Rs.getString("Condi");}
                
        if(condicion.equals("Activo")&&abonop>0){//si es activo
            //String query="insert into Prestamos(ID_Per,Fecha_Prestamo,cantidad) values("+busid+",'"+fechap+"','"+abonop+"');";
            String query="CALL INSERTA_PRESTAMO("+busid+",'"+fechap+"','"+abonop+"');";
            St.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Registro agregado exitosamente");
        
             cargardatosTabla("SELECT prestamos.ID_Pres, personal.ID_Per, personal.nom_p,personal.ap,personal.am,prestamos.Fecha_Prestamo,prestamos.cantidad FROM personal INNER JOIN prestamos ON personal.ID_Per=prestamos.ID_Per");
             idp.setText("");
             id.setText("");
             fecha.setCalendar(null);
             abono.setText("");
             busqueda.setText("");
         }else{//no es activo
           if(condicion.equals("Inactivo"))JOptionPane.showMessageDialog(null,"No se puede registrar un prestamo a un trabajador dado de baja");
           else if(abonop<=0)JOptionPane.showMessageDialog(null,"La cantidad de abono debe ser mayor a cero");
           else JOptionPane.showMessageDialog(null,"El ID de personal no existe");
         }                        
         St.close();
         Rs.close();
         Con.close();
         autocompletaDatos();
        } catch (Exception e){JOptionPane.showMessageDialog(null,e);}
      }//if 
     }//if   
        
    }//GEN-LAST:event_jButton3ActionPerformed

    //metodo que obtiene el formato correcto de la fecha
     public String GetFormatoFecha(String nombDateChooser){
        //recibe un parametro que indica de que calendario obtendra la fecha
        String formato="";
        String dia,mes,año;
        if(nombDateChooser.equals("fecha")){
             //se obtiene individualmente el dia,mes y año del calendario
             dia = Integer.toString(fecha.getCalendar().get(Calendar.DAY_OF_MONTH));
             mes = Integer.toString(fecha.getCalendar().get(Calendar.MONTH) + 1);
             año = Integer.toString(fecha.getCalendar().get(Calendar.YEAR));
             //se define el formato de fecha
             formato = (año + "-" + mes+ "-" + dia);
        }   
        //se regresa el formato correcto de la fecha para su uso en otro lugar
        return formato;
    }
    
            public void cargardatosTabla(String consulta){
         try {
             DefaultTableModel tabla=new DefaultTableModel();
             tabla.setColumnIdentifiers(new Object[]{"Identificador de Préstamo","Identificador del Personal","Nombre","Apellido Paterno","Apellido Materno","Fecha Préstamo","Cantidad de abono"});
             Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
             St=Con.createStatement();
             Rs=St.executeQuery(consulta);
             int i=0;
             while(Rs.next()){
             tabla.addRow(new Object[]{Rs.getString(1),Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getString(5),Rs.getString(6),Rs.getString(7)});
                             } 
             jTable1.setModel(tabla);
             Con.close();
             St.close();
         }
             catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }   
    }
    
    public void tabla(String consulta){
      if(jComboBox1.getSelectedItem()=="Nombre del personal")cargardatosTabla(consulta);
    } 
    
    public void autocompletaDatos(){                        
       lista.removeAllItems();
        try {
            Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
            St=Con.createStatement();
            
            if(jComboBox1.getSelectedItem()=="ID del prestamo")Rs=St.executeQuery("select ID_Pres as parametro from prestamos;"); 
            else if(jComboBox1.getSelectedItem()=="Nombre del personal")Rs=St.executeQuery("SELECT distinct concat(personal.nom_p,' ',personal.ap,' ',personal.am) as parametro FROM personal INNER JOIN prestamos ON personal.ID_Per=prestamos.ID_Per");
            
            while(Rs.next()){
                if(jComboBox1.getSelectedItem()=="ID del prestamo"){
                   lista.addItem(Rs.getString("parametro")+" "); 
                } 
                else if(jComboBox1.getSelectedItem()=="Nombre del personal"){
                   lista.addItem(Rs.getString("parametro"));        
                }                       
                            
            }
            St.close();
            Rs.close();
            Con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }      
            
    
    public void cargardatos(){
         try {
             while(Rs.next()){
            id.setText(Rs.getString(1));
            idp.setText(Rs.getString(2));
            Rs.getString(3);
            Rs.getString(4);
            Rs.getString(5);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(Rs.getString(6));fecha.setDate(date);
            abono.setText(Rs.getString(7));}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error"+e);
        }       
    }
            
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new ControlPanel().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        cargardatosTabla("SELECT prestamos.ID_Pres, personal.ID_Per, personal.nom_p,personal.ap,personal.am,prestamos.Fecha_Prestamo,prestamos.cantidad FROM personal INNER JOIN prestamos WHERE personal.ID_Per = prestamos.ID_Per");
        autocompletaDatos();
    }//GEN-LAST:event_formWindowOpened

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       String parametro=busqueda.getText();
       consultar(parametro);
    }//GEN-LAST:event_jButton4ActionPerformed

    public void consultar(String parametro){
      if(parametro.isEmpty())
        {JOptionPane.showMessageDialog(null,"Campo de busqueda vacio");}
        else{
         try{
            int idb=0;
            Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
            St=Con.createStatement();
            String query;
            

            boolean existe;          
            if(jComboBox1.getSelectedItem()=="Nombre del personal"){    
             Rs = St.executeQuery("SELECT DISTINCT concat(personal.nom_p,' ',personal.ap,' ',personal.am)  FROM personal INNER JOIN prestamos WHERE personal.ID_Per = prestamos.ID_Per and concat(personal.nom_p,' ',personal.ap,' ',personal.am)= '"+parametro+"';");
            }                   
            else if(jComboBox1.getSelectedItem()=="ID del prestamo"){
                parametro=quitaEspacios(parametro);
                Rs = St.executeQuery("SELECT prestamos.ID_Pres FROM personal INNER JOIN prestamos WHERE personal.ID_Per = prestamos.ID_Per and prestamos.ID_Pres="+Integer.parseInt(parametro)+";");          
            }                         
                 //a continuacion nos damos cuenta si existe o no
                  if(Rs.last()){existe=true;}
                  else{                
                    JOptionPane.showMessageDialog(null,"No existe el registro");
                    existe=false;
                  }
                                   
                if(existe==true){
                
                  if(jComboBox1.getSelectedItem()=="ID del prestamo"){
                     query="SELECT prestamos.ID_Pres, personal.ID_Per, personal.nom_p,personal.ap,personal.am,prestamos.Fecha_Prestamo,prestamos.cantidad FROM personal INNER JOIN prestamos WHERE personal.ID_Per = prestamos.ID_Per and prestamos.ID_Pres="+Integer.parseInt(parametro)+";";
                     Rs=St.executeQuery(query);
                  }
                  else if(jComboBox1.getSelectedItem()=="Nombre del personal"){              
                    query="SELECT prestamos.ID_Pres, personal.ID_Per, personal.nom_p,personal.ap,personal.am,prestamos.Fecha_Prestamo,prestamos.cantidad FROM personal INNER JOIN prestamos WHERE personal.ID_Per = prestamos.ID_Per and concat(personal.nom_p,' ',personal.ap,' ',personal.am)= '"+parametro+"';";
                    Rs=St.executeQuery(query);
                  }   
                    cargardatos();
                    busqueda.setText("");
                }           
            St.close();
            Rs.close();
            Con.close();          
          } catch (Exception e){JOptionPane.showMessageDialog(null,e);}   
        }
    }
    
    public String quitaEspacios(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(" ").append(tokens.nextToken());
        }
        return buff.toString().trim();
    }
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        x=jComboBox1.getSelectedIndex();
        autocompletaDatos();
        busqueda.setText("");
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void busquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaActionPerformed

    private void busquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaKeyTyped
       if(jComboBox1.getSelectedItem()=="Nombre del personal"){
         String nombre=busqueda.getText();
          char k=evt.getKeyChar();
         if(Character.isLetter(k)||k=='.'||k==' '){
          if(nombre.length()>0){          
            if(nombre.charAt(nombre.length()-1)=='.'&&k!=' '){
            getToolkit().beep();
            evt.consume();
           }
           else if(nombre.charAt(nombre.length()-1)==' '&&(k==' '||k=='.')){
            getToolkit().beep();
            evt.consume();
           }
            else if(nombre.length()==25){
              getToolkit().beep();
              evt.consume();
            }  
          }
          else if(nombre.isEmpty()){
            if(k==' '||k=='.'){
              getToolkit().beep();
              evt.consume();  
            }
          }                 
         }else{
              //getToolkit().beep();
              evt.consume();  
         } 
         
         if(nombre.contains(".")&&k=='.'){
              getToolkit().beep();
              evt.consume();
          }
      }
      else if(jComboBox1.getSelectedItem()=="ID del prestamo"){
        char k=evt.getKeyChar();
        String ID=busqueda.getText();        
        if(Character.isDigit(k)){
           if(ID.length()>0){          
             if(ID.length()==10){
              //getToolkit().beep();
              evt.consume();
             }
            }
         }               
        else{
          evt.consume();
        }//else
      }
    }//GEN-LAST:event_busquedaKeyTyped

    private void idpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idpKeyTyped
        char k=evt.getKeyChar();
        String ID=idp.getText();        
        if(Character.isDigit(k)){
           if(ID.length()>0){          
             if(ID.length()==10){
              //getToolkit().beep();
              evt.consume();
             }
            }
         }               
        else{
          evt.consume();
        }//else     
    }//GEN-LAST:event_idpKeyTyped

    private void abonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_abonoKeyTyped
        char k=evt.getKeyChar();
        String ABONO=abono.getText();        
        if(Character.isDigit(k)){
         if(ABONO.length()>0){          
           if(ABONO.length()==5){
            //getToolkit().beep();
            evt.consume();
           }         
         }  
        }
        else{
          evt.consume();
        }
           
    }//GEN-LAST:event_abonoKeyTyped

   
    public static void main(String args[]) {       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionarPrestamos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField abono;
    private javax.swing.JTextField busqueda;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JTextField id;
    private javax.swing.JTextField idp;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
