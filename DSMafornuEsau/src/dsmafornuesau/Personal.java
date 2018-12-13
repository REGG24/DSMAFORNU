
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
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

public class Personal extends javax.swing.JFrame {
    
    DateFormat df=DateFormat.getDateInstance();
    static String TPS="O-",P,S,ColCombobox;
    int vts=0; //Esta es la variable la cual obtiene el valor del combo box de tipo de sangre      
    Connection Con;
    Statement St;
    ResultSet Rs;
    ResultSet resultset;
    Statement statement;
    TextAutoCompleter autocompleta;  //autocompleter del codigo postal 
    TextAutoCompleter autoComCampoBusqueda1;
    TextAutoCompleter autoComCampoBusqueda2;  
    String ConsultaNombre="";
    MuestraMotivo m=new MuestraMotivo();
    boolean punto=false;
    Aviso_Contrato avc=new Aviso_Contrato();
    Aviso_Licencia2 avl=new Aviso_Licencia2();
    
    //metodo constructor, donde se inicializan los componentes
    public Personal() {
        initComponents();//inicia el ciclo de vida de los componentes
        setLocationRelativeTo(null);//se define que el frame debe aparacer en el centro de la pantalla
        //se cambia el color del frame a blanco
        getContentPane().setBackground(new java.awt.Color(255,255,255));
        setResizable(false);//se define el jframe como no reajustable
        this.setTitle("Personal - Usuario: "+Globales.User);//se le pone un encabezado al frame
        llenarpuesto();//se llena el JcomboBox de puestos con los puestos existentes.
        llenarsucursal();//se llena el JcomboBox de sucursales con las sucursales existentes.
        nom.requestFocus();//se pone el cursor en el campo del nombre            
        autoComCampoBusqueda1=new TextAutoCompleter(busqueda1);//definicion del autocompleter del campo busqueda1                     
        
        //se define que al elegir un personal en el campo de busqueda2
        //se llamara al metodo consulta enviandole el mismo nombre o parametro
        autoComCampoBusqueda2 = new TextAutoCompleter(busqueda2, new AutoCompleterCallback() {
            @Override
            public void callback(Object selectedItem) {
                consulta((String)selectedItem);
            }
           });
        
        //se define que al elegir un codigo postal en el campo de cp
        //se llamara al metodo Ayuda_de_cp enviandole el mismo codigo
        //postal como parametro
        autocompleta = new TextAutoCompleter(cp, new AutoCompleterCallback() {
            @Override
            public void callback(Object selectedItem) {             
                Ayuda_de_cp((String)selectedItem);
            }
           });
        
       //Se agrega una propiedad al jDateChooser de la fecha de nacimiento
       //para que al presionar el dia en el calendario se calcule la edad y no tenga que 
       //ingresarla el usuario
       FechaNa.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                          
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            //si no esta vacio el campo del calendario
            if(FechaNa.getDate() == null){              
                cp.requestFocus();
               }else{
                //como no esta vacio se procede a calcular la edad                              
                int años=CalculaEdad();//metodo que retorna los años de edad
                edad.setText(""+años);//se ponen los años en el campo de la edad
                cp.requestFocus();//se redirige el cursor al campo del codigo postal              
               } 
        }
       });
       
       //Se agrega una propiedad al jDateChooser de la fecha del contrato
       //para que al elegir una fecha se active el campo para la fecha de vencimiento de contrato
       //y a su ves defina la fecha permitida para el vencimiento de contrato
       FechaContra.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                          
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            if(FechaContra.getDate() == null){              
              //si el campo del calendario es vacio   
            }else{
              //si el campo del calendario no es vacio 
              FechaVenContra.setEnabled(true);//se activa la fecha de vencimiento de contrato
              
              //se define el formato correcto de la fecha y se obtiene la fecha del contrato
              //y se guarda como tipo date en la variable: contrato_date
              DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");
              String contra=GetFormatoFecha("FechaContra");
              LocalDate contrato = LocalDate.parse(contra, fmt);            
              Date contrato_date = Date.from(contrato.atStartOfDay(ZoneId.systemDefault()).toInstant());                         
               
              //se define la fecha valida para el vencimiento de contrato a partir de la ya ingresada fecha de contrato
              Calendar cal=Calendar.getInstance();              
              cal.setTime(contrato_date);                     	
              cal.add(Calendar.DAY_OF_YEAR,1);
              FechaVenContra.setMinSelectableDate(cal.getTime());
            }
        }
       });
       
       //Se agrega una propiedad al jDateChooser de la fecha del vencimiento de contrato
       //para solo se pueda elegir una fecha superior a la fecha de contrato
       FechaVenContra.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {                                
        @Override
           public void propertyChange(PropertyChangeEvent e) {
            if(FechaVenContra.getDate() != null||FechaVenContra.getCalendar()!=null){              
               DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");//se establece el formato para la fecha                            
                String contra=GetFormatoFecha("FechaContra");//se obtiene en la variable contra la fecha de contrato que ya se ingreso               
                LocalDate contrato = LocalDate.parse(contra, fmt);//fecha del contrato en formato Date
                String vencontrato=GetFormatoFecha("FechaVenContra");//se obtiene en la variable vencontrato la fecha del vencimiento de contrato que se acaba de ingresar                    
                LocalDate fecha_ven_contrato = LocalDate.parse(vencontrato,fmt);//fecha del vencimiento del contrato en formato Date
                int correcto=fecha_ven_contrato.compareTo(contrato);//se almacena -1 si el vencimiento de contrato es menor al contrato,se almacena cero si ambas fechas son iguales
                 
                if(correcto<1){
                    JOptionPane.showMessageDialog(null,"La fecha de vencimiento de contrato debe ser mayor a la del contrato");                                                
                    ((JTextField)FechaVenContra.getDateEditor().getUiComponent()).setText("");
                }   
            }             
                
            }     
       }); 
       
       //metodo que valida que la fecha de nacimiento no sea en el futuro, y que el empleado tenga mas de 15 años y menos de 80
        ValidaNacimiento();
        
        //se define a estos calendarios que el rango para elegir fecha 
        //no se permite si es un dia anterior al actual.
        FechaIngre.setMinSelectableDate(new Date());
        FechaContra.setMinSelectableDate(new Date());
        FechaVenContra.setMinSelectableDate(new Date());
        
        //lo siguiente es para bloquear los campos de texto de las fechas
        //se crean un objetos del tipo DateEditor y se ponen en false para no poder editar la fecha seleccionada desde el teclado
        JTextFieldDateEditor editorFechaNa,editorFechaContra,editorFechaVenContra,editorFechaVenLM,editorFechaIngre;//se crea el objeto tipo DateEditor
        
        //se declaran los objetos de tipo editor para los calendarios
        editorFechaNa=(JTextFieldDateEditor) FechaNa.getDateEditor();
        editorFechaContra=(JTextFieldDateEditor) FechaContra.getDateEditor();
        editorFechaVenContra=(JTextFieldDateEditor) FechaVenContra.getDateEditor();
        editorFechaVenLM=(JTextFieldDateEditor) FechaVenLM.getDateEditor();
        editorFechaIngre=(JTextFieldDateEditor) FechaIngre.getDateEditor();
        
        //se desactivan las ediciones a los objetos.
        editorFechaNa.setEditable(false);
        editorFechaContra.setEditable(false);
        editorFechaVenContra.setEditable(false);
        editorFechaVenLM.setEditable(false);
        editorFechaIngre.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        agregar = new javax.swing.JButton();
        regresar = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        modificar = new javax.swing.JButton();
        baja = new javax.swing.JButton();
        consultar = new javax.swing.JButton();
        LP = new javax.swing.JButton();
        labelnombre = new javax.swing.JLabel();
        labelap = new javax.swing.JLabel();
        labelam = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelestado = new javax.swing.JLabel();
        labelcelular = new javax.swing.JLabel();
        labelfijo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        puesto = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        tiposangre = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        sucursal = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        ap = new javax.swing.JTextField();
        calle = new javax.swing.JTextField();
        am = new javax.swing.JTextField();
        numero = new javax.swing.JTextField();
        cp = new javax.swing.JTextField();
        ciudad = new javax.swing.JTextField();
        estado = new javax.swing.JTextField();
        celular = new javax.swing.JTextField();
        fijo = new javax.swing.JTextField();
        curp = new javax.swing.JTextField();
        rfc = new javax.swing.JTextField();
        nss = new javax.swing.JTextField();
        edad = new javax.swing.JTextField();
        id = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        busqueda1 = new javax.swing.JTextField();
        ComboBusqueda2 = new javax.swing.JComboBox();
        busqueda2 = new javax.swing.JTextField();
        ComboBusqueda1 = new javax.swing.JComboBox();
        activos = new javax.swing.JCheckBox();
        inactivos = new javax.swing.JCheckBox();
        nom = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        FechaNa = new com.toedter.calendar.JDateChooser();
        FechaIngre = new com.toedter.calendar.JDateChooser();
        FechaContra = new com.toedter.calendar.JDateChooser();
        FechaVenContra = new com.toedter.calendar.JDateChooser();
        FechaVenLM = new com.toedter.calendar.JDateChooser();
        condicion = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        colonia = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1350, 690));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(180, 180, 180));

        agregar.setText("Agregar");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });

        regresar.setText("Regresar");
        regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regresarActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Gestionar Personal");

        modificar.setText("Modificar");
        modificar.setEnabled(false);
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        baja.setText("Dar de baja");
        baja.setEnabled(false);
        baja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bajaActionPerformed(evt);
            }
        });

        consultar.setText("Consultar");
        consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarActionPerformed(evt);
            }
        });

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
                    .addComponent(regresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(baja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(agregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(consultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(LP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(consultar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(baja, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                .addComponent(regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 690));

        labelnombre.setText("Nombre:");
        getContentPane().add(labelnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, -1, -1));

        labelap.setText("Apellido Paterno:");
        getContentPane().add(labelap, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, -1, -1));

        labelam.setText("Apellido Materno:");
        getContentPane().add(labelam, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        jLabel4.setText("Calle:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 330, -1, -1));

        jLabel5.setText("Número:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, -1, -1));

        jLabel6.setText("Colonia:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, 55, -1));

        jLabel7.setText("Ciudad:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 250, 70, -1));

        jLabel8.setText("Código Postal:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));

        labelestado.setText("Estado:");
        getContentPane().add(labelestado, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, 50, -1));

        labelcelular.setText("Teléfono celular:");
        getContentPane().add(labelcelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 290, -1, -1));

        labelfijo.setText("Teléfono fijo:");
        getContentPane().add(labelfijo, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 330, -1, -1));

        jLabel12.setText("CURP:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 250, -1, -1));

        jLabel13.setText("RFC:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, -1, -1));

        jLabel14.setText("Seguro Social:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 170, -1, 20));

        jLabel15.setText("Puesto:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 170, -1, -1));

        puesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puestoActionPerformed(evt);
            }
        });
        getContentPane().add(puesto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 168, 130, -1));

        jLabel16.setText("Fecha de contrato:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 290, -1, -1));

        jLabel17.setText("Tipo de sangre:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, -1, -1));

        jLabel18.setText("Edad:");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, -1, -1));

        tiposangre.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-" }));
        tiposangre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiposangreActionPerformed(evt);
            }
        });
        getContentPane().add(tiposangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 130, -1));

        jLabel19.setText("Fecha de Nacimiento:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, -1));

        jLabel20.setText("Sucursal:");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 210, -1, -1));

        sucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sucursalActionPerformed(evt);
            }
        });
        getContentPane().add(sucursal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 210, 130, -1));

        jLabel21.setText("Fecha de ingreso:");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 250, -1, -1));

        jLabel22.setText("Vencimiento de contrato:");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 330, -1, -1));

        jLabel23.setText("Vencimiento de Licencia  manejo:");
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 370, -1, -1));

        ap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apActionPerformed(evt);
            }
        });
        ap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                apKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                apKeyTyped(evt);
            }
        });
        getContentPane().add(ap, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 130, -1));

        calle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                calleKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                calleKeyTyped(evt);
            }
        });
        getContentPane().add(calle, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 330, 120, -1));

        am.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                amKeyTyped(evt);
            }
        });
        getContentPane().add(am, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 130, -1));

        numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numeroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                numeroKeyTyped(evt);
            }
        });
        getContentPane().add(numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, 120, -1));

        cp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cpActionPerformed(evt);
            }
        });
        cp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cpKeyTyped(evt);
            }
        });
        getContentPane().add(cp, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 120, -1));

        ciudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ciudadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ciudadKeyTyped(evt);
            }
        });
        getContentPane().add(ciudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 120, -1));
        ciudad.setEditable(false);

        estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoActionPerformed(evt);
            }
        });
        estado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                estadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                estadoKeyTyped(evt);
            }
        });
        getContentPane().add(estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 210, 120, -1));
        estado.setEditable(false);

        celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                celularKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                celularKeyTyped(evt);
            }
        });
        getContentPane().add(celular, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 290, 150, -1));

        fijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijoActionPerformed(evt);
            }
        });
        fijo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fijoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fijoKeyTyped(evt);
            }
        });
        getContentPane().add(fijo, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 330, 150, -1));

        curp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                curpKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                curpKeyTyped(evt);
            }
        });
        getContentPane().add(curp, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 250, 150, -1));

        rfc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rfcKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rfcKeyTyped(evt);
            }
        });
        getContentPane().add(rfc, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 210, 150, -1));

        nss.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nssKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nssKeyTyped(evt);
            }
        });
        getContentPane().add(nss, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 170, 150, -1));

        edad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edadKeyTyped(evt);
            }
        });
        getContentPane().add(edad, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 330, 130, -1));
        edad.setEditable(false);

        id.setEnabled(false);
        getContentPane().add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 130, 130, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Edad", "Tipo sangre", "Ciudad", "Estado", "Puesto", "Sucursal", "Ingreso", "Contratacion", "Ven_Contra", "Ven_Lic", "Condición"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
            jTable1.getColumnModel().getColumn(10).setResizable(false);
            jTable1.getColumnModel().getColumn(11).setResizable(false);
            jTable1.getColumnModel().getColumn(12).setResizable(false);
            jTable1.getColumnModel().getColumn(13).setResizable(false);
            jTable1.getColumnModel().getColumn(14).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 1140, 270));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Búsqueda Especializada");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        busqueda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busqueda1ActionPerformed(evt);
            }
        });
        busqueda1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busqueda1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busqueda1KeyTyped(evt);
            }
        });
        getContentPane().add(busqueda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 190, -1));

        ComboBusqueda2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre", "Apellido", "ID" }));
        ComboBusqueda2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBusqueda2ActionPerformed(evt);
            }
        });
        getContentPane().add(ComboBusqueda2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 80, 20));

        busqueda2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                busqueda2MouseClicked(evt);
            }
        });
        busqueda2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busqueda2KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busqueda2KeyTyped(evt);
            }
        });
        getContentPane().add(busqueda2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 190, -1));

        ComboBusqueda1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Puesto", "Sucursal", "Estado" }));
        ComboBusqueda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBusqueda1ActionPerformed(evt);
            }
        });
        getContentPane().add(ComboBusqueda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 80, 20));

        activos.setSelected(true);
        activos.setText("Activos");
        activos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activosMouseClicked(evt);
            }
        });
        activos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activosActionPerformed(evt);
            }
        });
        getContentPane().add(activos, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, -1, -1));

        inactivos.setText("Inactivos");
        inactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inactivosMouseClicked(evt);
            }
        });
        getContentPane().add(inactivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        nom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nomKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nomKeyTyped(evt);
            }
        });
        getContentPane().add(nom, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, 130, -1));

        jLabel27.setText("ID:");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, -1, -1));

        jLabel28.setText("Condición:");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 370, -1, -1));

        FechaNa.setDateFormatString("yyyy-MM-dd");
        FechaNa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FechaNaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FechaNaMousePressed(evt);
            }
        });
        FechaNa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FechaNaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FechaNaKeyTyped(evt);
            }
        });
        getContentPane().add(FechaNa, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 130, -1));

        FechaIngre.setDateFormatString("yyyy-MM-dd");
        FechaIngre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                FechaIngreKeyTyped(evt);
            }
        });
        getContentPane().add(FechaIngre, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 250, 130, -1));

        FechaContra.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(FechaContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 290, 130, -1));

        FechaVenContra.setDateFormatString("yyyy-MM-dd");
        FechaVenContra.setEnabled(false);
        getContentPane().add(FechaVenContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 330, 130, -1));

        FechaVenLM.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(FechaVenLM, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 370, 130, -1));

        condicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        condicion.setEnabled(false);
        condicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                condicionActionPerformed(evt);
            }
        });
        getContentPane().add(condicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 370, 150, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("Historial de bajas");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 10, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID Baja", "Motivo", "Fecha De Baja"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 40, 610, 110));

        colonia.setEditable(true);
        colonia.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                coloniaPopupMenuWillBecomeVisible(evt);
            }
        });
        colonia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coloniaActionPerformed(evt);
            }
        });
        getContentPane().add(colonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 120, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //metodo que valida el calendario de fecha de nacimiento
    public void ValidaNacimiento(){
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, -15);//15 años antes de la fecha actual
      Date min = cal.getTime();
      cal.add(Calendar.YEAR,-65);//65+15=80 años antes de la fecha actual
      Date max = cal.getTime();            
      FechaNa.setSelectableDateRange(max,min);//ahora solo se puede seleccionar entre 80 y 15 años antes
    }
    
    //metodo que valida el calendario de la fecha de contrato
    public void ValidaContrato(){      
      LocalDate ahora = LocalDate.now();//la fecha del dia actual
      //el rango minimo de seleccion es el dia actual 
      Date min = Date.from(ahora.atStartOfDay(ZoneId.systemDefault()).toInstant());
      Date max=min;                         
      FechaContra.setSelectableDateRange(max,min);//ahora solo se puede elegir apartir del dia actual
    }
    
    //metodo que calcula los años de edad de acuerdo a la fecha que este
    //registrada o se seleccione
    public int CalculaEdad(){
                //se obtiene la fecha y se guarda en la cadena nacimiento
                String nacimiento=GetFormatoFecha("FechaNa");               
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");//se hace un objeto fmt para el formato deseado a manejar
                LocalDate fechaNac = LocalDate.parse(nacimiento,fmt);//fechaNac tiene la fecha de naciiento 
                LocalDate ahora = LocalDate.now();//la fecha de hoy se guarda en la variable ahora
                Period periodo = Period.between(fechaNac,ahora);//se calcula el tiempo que ha pasado entre la fecha de nacimiento y hoy              
                return periodo.getYears();//se retornan los años calculados
    }
    
    private void puestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puestoActionPerformed
     P=(String)puesto.getSelectedItem();  
    }//GEN-LAST:event_puestoActionPerformed

    private void estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estadoActionPerformed

    private void cpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cpActionPerformed

    private void apActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apActionPerformed
        
    }//GEN-LAST:event_apActionPerformed

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        //Se declaran las variables y banderas  que se van a usar en el método
        int auxedad=0,auxcp=0,idpue=0,idsuc=0;
        String nacimiento,ingreso,contra,ven_contra,ven_lm;
        ColCombobox=(String)colonia.getSelectedItem();
        
        //Se activan las banderas que nos diran si los campos de texto para id están vacíos o no
        if(edad.getText().isEmpty())auxedad=1;
        else {int EDAD=Integer.parseInt(edad.getText());}
        if(cp.getText().isEmpty())auxcp=1;
        else {int CP=Integer.parseInt(cp.getText());}    
       
       //Se valida que no haya campos vacios       
       if(nom.getText().isEmpty()||ap.getText().isEmpty()||am.getText().isEmpty()||FechaNa.getDate()==null||calle.getText().isEmpty()||numero.getText().isEmpty()||ciudad.getText().isEmpty()||estado.getText().isEmpty()||nss.getText().isEmpty()||rfc.getText().isEmpty()||curp.getText().isEmpty()||celular.getText().isEmpty()||fijo.getText().isEmpty()||FechaIngre.getDate()==null||FechaContra.getDate()==null||FechaVenContra.getDate()==null||FechaVenLM.getDate()==null||auxedad==1||auxcp==1||ColCombobox.isEmpty()){      
        JOptionPane.showMessageDialog(null,"Campos vacios");
       }//if (validar vampos vacios)
       else{
           //validamos que no tenga espacios de mas en los campos necesarios para la busqueda
           //o que los numeros de telefonos si tengan su longitud minima
           boolean correcto=true,minimo=true,minimoF=true;
            
           if(String.valueOf(nom.getText().charAt(0)).equals(" ")||String.valueOf(nom.getText().charAt(nom.getText().length() - 1)).equals(" "))                 
           {
             labelnombre.setForeground(Color.red);correcto=false;
           }
           if(String.valueOf(ap.getText().charAt(0)).equals(" ")||String.valueOf(ap.getText().charAt(ap.getText().length() - 1)).equals(" "))                 
           {
             labelap.setForeground(Color.red);correcto=false;
           }
           if(String.valueOf(am.getText().charAt(0)).equals(" ")||String.valueOf(am.getText().charAt(am.getText().length() - 1)).equals(" "))                 
           {
             labelam.setForeground(Color.red);correcto=false;
           }  
           if(String.valueOf(estado.getText().charAt(0)).equals(" ")||String.valueOf(estado.getText().charAt(estado.getText().length() - 1)).equals(" "))                 
           {
             labelestado.setForeground(Color.red);correcto=false;
           }
           if(celular.getText().length()<10)                 
           {
             labelcelular.setForeground(Color.red);             
             minimo=false;
           }
           if(fijo.getText().length()<7)                 
           {
             labelfijo.setForeground(Color.red);             
             minimoF=false;
           } 
           //ahora si se checan las variables booleanas para determinar si hubo espacios de mas
           //o si la longitud de el numero de telefono o celular es invalido a la especificacion
           if(correcto==false||minimo==false||minimoF==false){
             if(correcto==false)JOptionPane.showMessageDialog(null,"Hay espacios en blanco de mas");
             if(minimo==false)JOptionPane.showMessageDialog(null,"La longitud de un numero de celular debe ser minima de 10 y maxima de 13");
             if(minimoF==false)JOptionPane.showMessageDialog(null,"La longitud de un numero de telefono fijo debe ser minima de 7 y maxima de 10");
           }         
           else{//al entrar aqui quiere decir que almenos las entradas de los datos son correctos
                //Se le pregunta al usuario si esta seguro de agregar al registro
                Object [] opciones ={"Aceptar","Cancelar"};
                int eleccion = JOptionPane.showOptionDialog(rootPane,"¿Estas seguro de agregar un nuevo registro?","Mensaje de Confirmacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
            
                if (eleccion == JOptionPane.YES_OPTION)//si la eleccion fue si
                {
                    try{
                        //Se obiene el nombre completo que se acaba de ingresar
                        String nombre=(nom.getText()+" "+ap.getText()+" "+am.getText());                     
                        Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
                        St=Con.createStatement();
                        
                        //se checa si este nombre ya existe en la base de datos
                        Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Repito FROM personal WHERE CONCAT(nom_p,' ',ap,' ',am) = '"+nombre+"';");
                        if(Rs.last()){
                            //como si existe se le avisa al usuario y no se agrega
                            JOptionPane.showMessageDialog(null,"Ya existe un registro con ese nombre");               
                        }
                        else{
                            //al entrar aqui quiere decir que el nombre no existe y se puede agregar
                            
                            //necesitamos encontrar el id de puesto y sucursal con el nombre elegido
                            Rs=St.executeQuery("select ID_Pue from puesto where nom_p = '"+P+"';");
                            //Rs=St.executeQuery("CALL SIDP('"+P+"');");
                            while(Rs.next())idpue=Integer.parseInt(Rs.getString("ID_Pue"));          
                            Rs=St.executeQuery("select ID_Suc from sucursal where nom_suc = '"+S+"';");
                            //Rs=St.executeQuery("CALL SIDS('"+S+"');");
                            while(Rs.next())idsuc=Integer.parseInt(Rs.getString("ID_Suc"));                    
            
                            //obtenemos los formatos correctos de las fechas
                            nacimiento=GetFormatoFecha("FechaNa");
                            ingreso=GetFormatoFecha("FechaIngre");
                            contra=GetFormatoFecha("FechaContra");//de la tabla contratos
                            ven_contra=GetFormatoFecha("FechaVenContra");//de la tabla contratos
                            ven_lm=GetFormatoFecha("FechaVenLM");
            
                            //ahora si a insertar en la tabla personal
                            String query="insert into Personal(ID_Pue,ID_Suc,nom_p,ap,am,Fecha_Nacimiento,edad,tipo_s,calle,numero,colonia,cp,ciudad, estado,nss,rfc,curp,cel,fijo,Fecha_Ingreso,Fecha_Contrato,Vencimiento_Contrato,Vencimiento_Licencia,condicion) values ("+idpue+","+idsuc+",'"+nom.getText()+"','"+ap.getText()+"','"+am.getText()+"','"+nacimiento+"','"+edad.getText()+"','"+TPS+"','"+calle.getText()+"','"+numero.getText()+"','"+ColCombobox+"','"+cp.getText()+"','"+ciudad.getText()+"','"+estado.getText()+"','"+nss.getText()+"','"+rfc.getText()+"','"+curp.getText()+"','"+celular.getText()+"','"+fijo.getText()+"','"+ingreso+"','"+contra+"','"+ven_contra+"','"+ven_lm+"','Activo');";                   
                            //String query="CALL INSERTA_PERSONAL("+idpue+","+idsuc+",'"+nom.getText()+"','"+ap.getText()+"','"+am.getText()+"','"+nacimiento+"','"+edad.getText()+"','"+TPS+"','"+calle.getText()+"','"+numero.getText()+"','"+ColCombobox+"','"+cp.getText()+"','"+ciudad.getText()+"','"+estado.getText()+"','"+nss.getText()+"','"+rfc.getText()+"','"+curp.getText()+"','"+celular.getText()+"','"+fijo.getText()+"','"+ingreso+"','"+contra+"','"+ven_contra+"','"+ven_lm+"','Activo');";                                             
                            St.executeUpdate(query);//insercion del personal
                            
                            
                            cargardatosTabla("select * from Personal");
                            //cargardatosTabla("CALL CDTP");//se actualizan los datos con el nuevo personal ingresado
                            JOptionPane.showMessageDialog(null,"Registro agregado exitosamente");
                            limpiacampos();//se limpian los campos
                            colonia.removeAllItems();//se limpia el autocompleter del campo colonia
                            //se desactivan los botones modificar y dar de baja
                            modificar.setEnabled(false);
                            baja.setEnabled(false);
                           }//else de si exite ya el registro o no                  
                        
                        //se cierra la conexion de la base de datos
                        Con.close();
                        Rs.close();
                        St.close();
                        
                        //se actualiza el autocompleter de la busqueda para consulta
                        SeleccionCombo2();
                } catch (Exception e){
                    //si hay un error aqui se muestra
                    JOptionPane.showMessageDialog(null,e);
                }
         }//if de comfirmacion
        }//else de espacios en blanco de mas
       }//else de campos vacios  
    }//GEN-LAST:event_agregarActionPerformed

    //este metodo retorna el formato correcto de fecha de algun calendario
    public String GetFormatoFecha(String nombDateChooser){
       //nombDateChooser es un parametro que sirve para saber que calendario
       //se debe tomar para sacar el formato
        //se declaran las variables que se van a usar en el metodo
        String formato="";
        String dia,mes,año;
        //si se requiere la fecha del formato de nacimiento
        if(nombDateChooser.equals("FechaNa")){
             dia = Integer.toString(FechaNa.getCalendar().get(Calendar.DAY_OF_MONTH));
             mes = Integer.toString(FechaNa.getCalendar().get(Calendar.MONTH) + 1);
             año = Integer.toString(FechaNa.getCalendar().get(Calendar.YEAR));
             formato = (año + "-" + mes+ "-" + dia);
        }
        //si se requiere la fecha de ingreso
        else if(nombDateChooser.equals("FechaIngre")){
             dia = Integer.toString(FechaIngre.getCalendar().get(Calendar.DAY_OF_MONTH));
             mes = Integer.toString(FechaIngre.getCalendar().get(Calendar.MONTH) + 1);
             año = Integer.toString(FechaIngre.getCalendar().get(Calendar.YEAR));
             formato = (año + "-" + mes+ "-" + dia);
        }
        //si se requiere la fecha de contrato
        else if(nombDateChooser.equals("FechaContra")){
             dia = Integer.toString(FechaContra.getCalendar().get(Calendar.DAY_OF_MONTH));
             mes = Integer.toString(FechaContra.getCalendar().get(Calendar.MONTH) + 1);
             año = Integer.toString(FechaContra.getCalendar().get(Calendar.YEAR));
             formato = (año + "-" + mes+ "-" + dia);
        }
        //si se requiere la fecha de vencimiento de contrato
        else if(nombDateChooser.equals("FechaVenContra")){
             dia = Integer.toString(FechaVenContra.getCalendar().get(Calendar.DAY_OF_MONTH));
             mes = Integer.toString(FechaVenContra.getCalendar().get(Calendar.MONTH) + 1);
             año = Integer.toString(FechaVenContra.getCalendar().get(Calendar.YEAR));
             formato = (año + "-" + mes+ "-" + dia);
        }
        //si se requiere la fecha de vencimiento de licencia
        else if(nombDateChooser.equals("FechaVenLM")){
             dia = Integer.toString(FechaVenLM.getCalendar().get(Calendar.DAY_OF_MONTH));
             mes = Integer.toString(FechaVenLM.getCalendar().get(Calendar.MONTH) + 1);
             año = Integer.toString(FechaVenLM.getCalendar().get(Calendar.YEAR));
             formato = (año + "-" + mes+ "-" + dia);
        }    
        //se retorna el correcto formato de  del calendario requerido
        return formato;
    }
    
    private void fijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fijoActionPerformed

    private void busqueda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busqueda1ActionPerformed
            
    }//GEN-LAST:event_busqueda1ActionPerformed
    
    private void ComboBusqueda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBusqueda1ActionPerformed
        //se limpia el campo de busqueda1 y se manda a llamar al metodo SeleccionCombo1
        busqueda1.setText("");
        SeleccionCombo1();      
    }//GEN-LAST:event_ComboBusqueda1ActionPerformed
    
    //este metodo decide que parametro enviar al metodo autoComCampoBusqueda1
    //se invoca a este metodo cuando se quiere actualizar la busqueda predictiva
    //del campo de busqueda1
    public void SeleccionCombo1(){
        if(ComboBusqueda1.getSelectedItem()=="Puesto")autoComCampoBusqueda1("Puesto");
        else if(ComboBusqueda1.getSelectedItem()=="Sucursal")autoComCampoBusqueda1("Sucursal");
        else if(ComboBusqueda1.getSelectedItem()=="Estado")autoComCampoBusqueda1("Estado");       
    }
    
    //este metodo rellena al campo de busqueda1 con los registros de los puestos o sucursales o estados registrados
    public void autoComCampoBusqueda1(String parametro){
        //primero se limpia para evitar repeticiones
        autoComCampoBusqueda1.removeAllItems();
       try{   
           //se hace conexion a la base de datos.
            Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
            St=Con.createStatement();
            
            //se decide la consulta que se la hara la base de datos de acuerdo al parametro recibido
            if(parametro.equals("Puesto")){Rs = St.executeQuery("select  nom_p as Todos from puesto;");}
            //if(parametro.equals("Puesto")){Rs = St.executeQuery("CALL `AS-TODOS-PUE`();");}
            else if(parametro.equals("Sucursal")){ Rs = St.executeQuery("select  nom_suc as Todos from sucursal;");}
            //else if(parametro.equals("Sucursal")){ Rs = St.executeQuery("CALL `AS-TODOS-SUC`();");}
            else if(parametro.equals("Estado")){Rs = St.executeQuery("select distinct estado as Todos from personal;");}
            //else if(parametro.equals("Estado")){Rs = St.executeQuery("CALL `AS-TODOS-EST`();");}
            
            //se rellena la lista de la busqueda predictiva con el resultado de la base de datos
            while(Rs.next())autoComCampoBusqueda1.addItem(Rs.getString("Todos"));
            
            //se cierran conexiones
            Rs.close();
            St.close();
            Con.close();
          } catch (Exception e){
              //si hay algun error aqui se muestra
                    JOptionPane.showMessageDialog(null,e);
          }    
    }
    
    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        int auxedad=0,auxcp=0;
        ColCombobox=(String)colonia.getSelectedItem();
        
        if(edad.getText().isEmpty())auxedad=1;
        else {int EDAD=Integer.parseInt(edad.getText());}
        if(cp.getText().isEmpty())auxcp=1;
        else {int CP=Integer.parseInt(cp.getText());}    
            
       if(nom.getText().isEmpty()||ap.getText().isEmpty()||am.getText().isEmpty()||df.format(FechaNa.getDate()).isEmpty()||calle.getText().isEmpty()||numero.getText().isEmpty()||ciudad.getText().isEmpty()||estado.getText().isEmpty()||nss.getText().isEmpty()||rfc.getText().isEmpty()||curp.getText().isEmpty()||celular.getText().isEmpty()||fijo.getText().isEmpty()||df.format(FechaIngre.getDate()).isEmpty()||df.format(FechaContra.getDate()).isEmpty()||df.format(FechaVenContra.getDate()).isEmpty()||df.format(FechaVenLM.getDate()).isEmpty()||auxedad==1||auxcp==1||ColCombobox.isEmpty()){
        //falta tipo de sangre, condicion,puesto,sucursal,checar cp y edad,se ingresan numeros pero a la mera no es necesario hacer conversion(26/03/2018)
        JOptionPane.showMessageDialog(null,"Campos vacios");
       }//if (validar vampos vacios)
       else{
           //validamos que no tenga espacios de mas en los campos necesarios para la busqueda
           boolean correcto=true;
           if(String.valueOf(nom.getText().charAt(0)).equals(" ")||String.valueOf(nom.getText().charAt(nom.getText().length() - 1)).equals(" "))                 
           {
             labelnombre.setForeground(Color.red);correcto=false;
           }
           if(String.valueOf(ap.getText().charAt(0)).equals(" ")||String.valueOf(ap.getText().charAt(ap.getText().length() - 1)).equals(" "))                 
           {
             labelap.setForeground(Color.red);correcto=false;
           }
           if(String.valueOf(am.getText().charAt(0)).equals(" ")||String.valueOf(am.getText().charAt(am.getText().length() - 1)).equals(" "))                 
           {
             labelam.setForeground(Color.red);correcto=false;
           }  
           if(String.valueOf(estado.getText().charAt(0)).equals(" ")||String.valueOf(estado.getText().charAt(estado.getText().length() - 1)).equals(" "))                 
           {
             labelestado.setForeground(Color.red);correcto=false;
           }   
           if(correcto==false){
             JOptionPane.showMessageDialog(null,"Hay espacios en blanco de mas");
           }
           else{
            Object [] opciones ={"Aceptar","Cancelar"};
            int eleccion = JOptionPane.showOptionDialog(rootPane,"¿Estas seguro de modificar este registro?","Mensaje de Confirmacion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
            
            if (eleccion == JOptionPane.YES_OPTION)
            {
                try{                       
                    Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
                    St=Con.createStatement();
                 
                    //sacamos el actual nombre del id que se muestra
                    Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Repito FROM personal WHERE ID_Per = "+Integer.parseInt(id.getText())+";");
                    //Rs = St.executeQuery("CALL BNPI('"+Integer.parseInt(id.getText())+"');");
                    while(Rs.next()){ConsultaNombre=Rs.getString("Repito");}
                                
                    //ahora sacamos el nuevo nombre que modificara
                    String nombre=(nom.getText()+" "+ap.getText()+" "+am.getText());              
                    
                    //ahora podemos ver que al modificar el registro no le ponga nombre y apellidos iguales a otro existente en la base de datos
                    if(nombre.equals(ConsultaNombre)){
                     //JOptionPane.showMessageDialog(null,"Son iguales");
                     AuxiliarModificar();//el registro es el que originalmente se consulto, asi que si esta en la base de datos y omitimos validar su existencia
                     modificar.setEnabled(false);
                     baja.setEnabled(false);                    
                    }
                    else 
                    {
                        //JOptionPane.showMessageDialog(null,"No son iguales");
                        Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Repito FROM personal WHERE CONCAT(nom_p,' ',ap,' ',am) = '"+nombre+"';");
                        if(Rs.last()){
                            JOptionPane.showMessageDialog(null,"Ya existe un registro con ese nombre");                     
                        }
                        else{          
                            AuxiliarModificar();
                            modificar.setEnabled(false);
                            baja.setEnabled(false);
                        }//else de si exite ya el registro o no en la base de datos
                    }//else de si el registro es el mismo que originalmente se consulto
                   
                   Con.close();
                   Rs.close();
                   St.close();                                  
                   SeleccionCombo2();//actualiza la busqueda especializada
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
         }//if de comfirmacion
        }//else de espacios de mas
       }//else de campos vacios         
    }//GEN-LAST:event_modificarActionPerformed
                  
    public void AuxiliarModificar(){
        String nacimiento,ingreso,contra,ven_contra,ven_lm;
        int idpue=0,idsuc=0;
        try {
                     //necesitamos encontrar el id de puesto y sucursal con el nombre elegido
                     Rs=St.executeQuery("select ID_Pue from puesto where nom_p = '"+P+"';");
                     //Rs=St.executeQuery("CALL OBTEN_ID_PUESTO('"+P+"');");
                     while(Rs.next())idpue=Integer.parseInt(Rs.getString("ID_Pue"));          
                     Rs=St.executeQuery("select ID_Suc from sucursal where nom_suc = '"+S+"';");
                     //Rs=St.executeQuery("CALL OBTEN_ID_SUC('"+S+"');");
                     while(Rs.next())idsuc=Integer.parseInt(Rs.getString("ID_Suc"));                    
            
                     //obtenemos los formatos correctos de las fechas
                     nacimiento=GetFormatoFecha("FechaNa");
                     ingreso=GetFormatoFecha("FechaIngre");
                     contra=GetFormatoFecha("FechaContra");//de la tabla contratos
                     ven_contra=GetFormatoFecha("FechaVenContra");//de la tabla contratos
                     ven_lm=GetFormatoFecha("FechaVenLM");
            
                     //obtenemos la condicion
                     String condi=(String)condicion.getSelectedItem();               
                     
                    //ahora si a modificar en la tabla personal
                     String query="update Personal set ID_Pue = "+idpue+",ID_Suc = "+idsuc+",nom_p='"+nom.getText()+"',ap='"+ap.getText()+"',am='"+am.getText()+"',Fecha_Nacimiento='"+nacimiento+"',edad='"+edad.getText()+"',tipo_s='"+TPS+"',calle='"+calle.getText()+"',numero='"+numero.getText()+"',colonia='"+ColCombobox+"',cp='"+cp.getText()+"',ciudad='"+ciudad.getText()+"',estado='"+estado.getText()+"',nss='"+nss.getText()+"',rfc='"+rfc.getText()+"',curp='"+curp.getText()+"',cel='"+celular.getText()+"',fijo='"+fijo.getText()+"',Fecha_Ingreso='"+ingreso+"',Fecha_Contrato='"+contra+"',Vencimiento_Contrato='"+ven_contra+"',Vencimiento_Licencia='"+ven_lm+"',condicion= '"+condi+"' where ID_Per = "+Integer.parseInt(id.getText())+";";                   
                     //String query="CALL MODIFICA_PERSONAL("+idpue+","+idsuc+",'"+nom.getText()+"','"+ap.getText()+"','"+am.getText()+"','"+nacimiento+"','"+edad.getText()+"','"+TPS+"','"+calle.getText()+"','"+numero.getText()+"','"+ColCombobox+"','"+cp.getText()+"','"+ciudad.getText()+"','"+estado.getText()+"','"+nss.getText()+"','"+rfc.getText()+"','"+curp.getText()+"','"+celular.getText()+"','"+fijo.getText()+"','"+ingreso+"','"+contra+"','"+ven_contra+"','"+ven_lm+"','"+condi+"',"+Integer.parseInt(id.getText())+");";                   
                     St.executeUpdate(query);//modificacion del personal                            
            
                     cargardatosTabla("select * from Personal");
                     //cargardatosTabla("CALL CDTP");
                     JOptionPane.showMessageDialog(null,"Registro modificado exitosamente");
                     limpiacampos();
                     colonia.removeAllItems();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null,e);
        }
    }
       
    private void activosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activosActionPerformed
        
    }//GEN-LAST:event_activosActionPerformed

    private void bajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bajaActionPerformed
        new DarBaja().setVisible(true);
        this.dispose();
        DarBaja.idb.setText(id.getText());
        DarBaja.nombaja.setText(nom.getText());
        DarBaja.apbaja.setText(ap.getText());
        DarBaja.ambaja.setText(am.getText());
    }//GEN-LAST:event_bajaActionPerformed

    //Para llenar la tabla de bajas con los datos del personal
     public void llenaTablaBajas(){
     try{
         //se define el modelo de la tabla y se hace conexion a la base de datos
          DefaultTableModel tablab=new DefaultTableModel();
          Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
          St=Con.createStatement();
          //se consulta si el personal tiene bajas registradas
          Rs=St.executeQuery("select * from Bajas where ID_Per ="+Integer.parseInt(id.getText())+";");
          //Rs=St.executeQuery("CALL CTDB('"+Integer.parseInt(id.getText())+"');");
          tablab.setColumnIdentifiers(new Object[]{"ID_B","Motivo","Fecha De Baja"});
          //se rellena la tabla con las bajas
          while(Rs.next())
           {
            tablab.addRow(new Object[]{Rs.getString(1),Rs.getString(3),Rs.getString(4)});
           }
          jTable2.setModel(tablab);
          //se cierra la conexion con la base de datos
          St.close();
          Rs.close();
          Con.close();
         }catch(Exception e){}
    }
    
    private void regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regresarActionPerformed
        avc.dispose();//se cierra la alerta contrato
        avl.dispose();//se cierra la alerta licencia
        this.dispose();//se cierra esta ventana
        new ControlPanel().setVisible(true);//se abre el panel de control     
    }//GEN-LAST:event_regresarActionPerformed

    private void tiposangreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiposangreActionPerformed
     TPS=(String)tiposangre.getSelectedItem(); 
    }//GEN-LAST:event_tiposangreActionPerformed

    private void sucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sucursalActionPerformed
      S=(String)sucursal.getSelectedItem();
    }//GEN-LAST:event_sucursalActionPerformed

    private void consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarActionPerformed
        //se obtiene el nombre o id del personal que se quiere consultar
        String nombre=busqueda2.getText();
        //se llama al metodo que consulta
        consulta(nombre);
    }//GEN-LAST:event_consultarActionPerformed
     
    public void consulta(String NOMBRE){
       int edadreal=0,edadregistrada=0;//Variables necesarias para actualizar la edad de ser necesarui 
       //primero se verifica que el campo de busqueda no este vacio
       if(NOMBRE.isEmpty())JOptionPane.showMessageDialog(null,"Campo de busqueda vacio");
        else{
             boolean existe;
             try{
                  
                 //Se realiza conexion a la base de datos
                 Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
                  St=Con.createStatement();
                  
                    //se selecciona la consulta adecuada para ver si el registro existe                
                    if(ComboBusqueda2.getSelectedItem()=="Nombre")
                    Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Repito FROM personal WHERE CONCAT(nom_p,' ',ap,' ',am) = '"+NOMBRE+"';");                 
                    //Rs=St.executeQuery("CALL CNC('"+NOMBRE+"');");
                    else if(ComboBusqueda2.getSelectedItem()=="Apellido")
                    Rs = St.executeQuery("SELECT  CONCAT(ap,' ',am,' ',nom_p) as Repito FROM personal WHERE CONCAT(ap,' ',am,' ',nom_p) = '"+NOMBRE+"';");                 
                    //Rs = St.executeQuery("CALL SNCA('"+NOMBRE+"');");
                    else if(ComboBusqueda2.getSelectedItem()=="ID"){
                    //se quita el espacio que se agrego para consultar sin errores
                    NOMBRE=quitaEspacios(NOMBRE);
                    Rs = St.executeQuery("SELECT  ID_Per as Repito FROM personal WHERE ID_Per = "+Integer.parseInt(NOMBRE)+";");                 
                    //Rs = St.executeQuery("CALL SPID('"+NOMBRE+"');"); 
                    }
                    
                  //a continuacion nos damos cuenta si existe o no
                  if(Rs.last()){existe=true;}
                  else{                
                    JOptionPane.showMessageDialog(null,"No existe el registro");
                    existe=false;
                  }
                                   
                  if(existe==true){//en este punto si existe el registro
                      //ahora se selecciona la consulta adecuada para buscar en la base de datos
                      if(ComboBusqueda2.getSelectedItem()=="Nombre")
                      Rs = St.executeQuery("SELECT  * FROM personal WHERE CONCAT(nom_p,' ',ap,' ',am) = '"+NOMBRE+"';");                                    
                      //Rs=St.executeQuery("CALL CNCNR('"+NOMBRE+"');");
                      else if(ComboBusqueda2.getSelectedItem()=="Apellido")
                      Rs = St.executeQuery("SELECT  * FROM personal WHERE CONCAT(ap,' ',am,' ',nom_p) = '"+NOMBRE+"';");                       
                      //Rs=St.executeQuery("CALL 	SNCASR('"+NOMBRE+"');");
                      else if(ComboBusqueda2.getSelectedItem()=="ID")
                      Rs = St.executeQuery("SELECT  *  FROM personal WHERE ID_Per = "+Integer.parseInt(NOMBRE)+";");
                      //Rs = St.executeQuery("CALL SPIDSR('"+NOMBRE+"');");
                      
                      //se preparan las variables necesarias para sacar los id de
                      //puestos y sucursales
                      String IDPUESTO="",IDSUCURSAL="";
                      String PUESTO="",SUCURSAL="",TP="",CONDICION="";
                      
                      //ahora si, se obienen los id de puestos y sucursales
                      while(Rs.next()){
                       IDPUESTO=Rs.getString("ID_Pue");
                       IDSUCURSAL=Rs.getString("ID_Suc");
                       TP=Rs.getString("tipo_s");
                       CONDICION=Rs.getString("condicion");
                      }
                      cargardatos(TP);//se cargan los datos en los campos de texto
                      
                      //se revisa si la edad coincide con la fecha de nacimiento
                      edadreal=CalculaEdad();
                      edadregistrada=Integer.parseInt(edad.getText());                    
                      //si la edad no coincide con la fecha de nacimiento entonces se actualiza
                      if(edadreal!=edadregistrada&&(edadreal!=0||edadregistrada!=0)){              
                        St.executeUpdate("update Personal set edad = "+edadreal+" where ID_Per = "+Integer.parseInt(id.getText())+";");                         
                        edad.setText(""+edadreal);//se actualiza la edad en el campo de texto                     
                      }
                      
                      //es necesario cerrar estas variables de conexion y utilizar otras
                      St.close();
                      Rs.close(); 
                      
                      
                      
                      //ahora con los id de puesto y sucursal obtenemos el nombre de estos
                      statement=Con.createStatement(); 
                      resultset=statement.executeQuery("select nom_p from puesto where ID_Pue = "+IDPUESTO+";");
                      //resultset=statement.executeQuery("CALL PUE_TABLA("+IDPUESTO+");");
                      while(resultset.next())PUESTO=resultset.getString("nom_p");
                      //se consultan los demas datos del trabajador en la base de datos
                      resultset=statement.executeQuery("select nom_suc from sucursal where ID_Suc = "+IDSUCURSAL+";");
                      //resultset=statement.executeQuery("CALL SUC_TABLA("+IDSUCURSAL+");");
                      while(resultset.next())SUCURSAL=resultset.getString("nom_suc");
                      //se cierran conexiones                    
                      resultset.close();
                      statement.close();
                      //se seleccionan los puestos y sucursales encontrados en los jcombobox
                      puesto.setSelectedItem(PUESTO);
                      sucursal.setSelectedItem(SUCURSAL);
                      
                      //se detecta si la condicion del trabajador es activa o inactiva
                      //para activar o desactivar el boton de dar de baja y mostrarle
                      //la condicion del trabajado al empleado
                      if(CONDICION.equals("Activo")){         
                          condicion.setSelectedItem("Activo");
                          condicion.setEnabled(false);
                          baja.setEnabled(true);
                          llenaTablaBajas();        
                      }
                      else if(CONDICION.equals("Inactivo")){
                          condicion.setSelectedItem("Inactivo");
                          condicion.setEnabled(true);                 
                          baja.setEnabled(false);
                          //se llena la tabla del historial de bajas con todas las bajas que se
                          //le hayan hecho al trabajador
                          llenaTablaBajas();          
                      }
                      //se activa el boton de modificar porque ya se consulto correctamente
                      modificar.setEnabled(true);                   
                      busqueda2.setText("");          
                  }
                  //se cierran conexiones
                  St.close();
                  Rs.close();
                  Con.close();
                  if(edadreal!=edadregistrada&&(edadreal!=0||edadregistrada!=0))cargardatosTabla("CALL CDTP;");//se actualiza la tabla
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
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
    
    //este metodo se ejecuta al abrir el frame
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      ConstruyeTabla();//especifica tamaños y propiedas de la tabla para el personal
      cargardatosTabla("select * from Personal");
      //cargardatosTabla("CALL CDTP;");//llena la tabla del personal
      autocompletaDatosCP();//carga los codigos postales a la lista del campo de codigos postales
      //ajusta la busqueda especializada por defecto
      autoComCampoBusqueda1("Puesto");
      autoComCampoBusqueda2("Nombre");
      //alertas de contrato y licencia
      DFV("select ID_Per,nom_p,ap,am,Vencimiento_Contrato,datediff(Vencimiento_Contrato,curdate()) as dias from personal where datediff(Vencimiento_Contrato,curdate())<8 and condicion='activo'");
      //DFV("CALL ALERTA_CONTRATO;");
      DFL("select ID_Per,nom_p,ap,am,Vencimiento_Licencia,datediff(Vencimiento_Licencia,curdate()) as diasl from personal where datediff(Vencimiento_Licencia,curdate())<8 and condicion='activo'");
      //DFL("CALL ALERTA_LICENCIA;");
    }//GEN-LAST:event_formWindowOpened

    //alerta contrato
    public void DFV(String consulta)
    {      
        int DiasF=0;
      try {
             //se hace conexion a la base de datos
             Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
             St=Con.createStatement();
             Rs=St.executeQuery(consulta);
             //si cierra la ventana si esta ya esta abierta
             if(Rs.last()){
                if (avc!=null) {//si existe una venta, la cierra.
                  avc.dispose();
                }
                 avc.setVisible(true);              
             }
             //se cierra la conexion de la base de datos
             St.close();
             Rs.close();
             Con.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e);
        }    
    }
    //alerta licencia
    public void DFL(String consulta)
    {
        int DiasF=0;
      try {
             //se hace conecion a laba base de datos
             Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
             St=Con.createStatement();
             Rs=St.executeQuery(consulta);
             //si cierra la ventana si esta ya esta abierta
             if(Rs.last()){
                if (avl!=null) {//si existe una venta, la cierra.
                  avl.dispose();
                }
                 avl.setVisible(true);
               //new Aviso_Licencia2().setVisible(true);
             }
             //se cierra la conexion a la base de datos
             St.close();
             Rs.close();
             Con.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e);
        }    
    }
    
    private void LPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LPActionPerformed
      limpiacampos();
    }//GEN-LAST:event_LPActionPerformed

    public void limpiacampos(){
                    id.setText("");
                    nom.setText("");
                    ap.setText("");
                    am.setText("");
                    edad.setText("");
                    calle.setText("");
                    numero.setText("");
                    colonia.removeAllItems();
                    cp.setText("");
                    ciudad.setText("");
                    estado.setText("");
                    nss.setText("");
                    rfc.setText("");
                    curp.setText("");
                    celular.setText("");
                    fijo.setText("");                
                    modificar.setEnabled(false);
                    baja.setEnabled(false);
                    cargardatosTabla("select*from Personal");
                    //cargardatosTabla("CALL CDTP");
                    nom.requestFocus();
                    busqueda1.setText("");
                    busqueda2.setText("");
                    FechaNa.setCalendar(null);
                    FechaIngre.setCalendar(null);
                    FechaContra.setCalendar(null);
                    
                    FechaVenContra.setCalendar(null);
                    FechaVenContra.setEnabled(false); //se desactiva la fecha de vencimiento de contrato                   
                    
                    FechaVenLM.setCalendar(null);
                    condicion.setSelectedItem("Activo");
                    condicion.setEnabled(false);
                    limpiartabla2();                                     
                    activos.setSelected(true);
                    inactivos.setSelected(true);
                    SeleccionCombo2();//vuelve a rellenar el autocompleter de la busqueda 2 con lo que este seleccionado en el combobox 2, ya sean activos o inactivos
                    labelnombre.setForeground(Color.black);
                    labelap.setForeground(Color.black);
                    labelam.setForeground(Color.black);
                    labelestado.setForeground(Color.black);
                    labelcelular.setForeground(Color.black);
                    labelfijo.setForeground(Color.black);
                    
    }
    
    public void limpiartabla2(){
        DefaultTableModel tb = (DefaultTableModel) jTable2.getModel();    
        for (int x = 0; x < jTable2.getRowCount(); x++) {
            tb.removeRow(x);
            x-=1;
        }   
    }
    
    private void nomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            int tamaño=nom.getText().length();
            if(nom.getText().isEmpty()){
                ap.requestFocus();
                labelnombre.setForeground(Color.black);
            }             
            else if(String.valueOf(nom.getText().charAt(0)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su primera letra no debe ser un espacio en blanco");
                 labelnombre.setForeground(Color.red);
            }
            else if(String.valueOf(nom.getText().charAt(nom.getText().length() - 1)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su ultima letra no debe ser un espacio en blanco");
                 labelnombre.setForeground(Color.red);
            }
            else {
                ap.requestFocus();
                labelnombre.setForeground(Color.black);
            }
        }
    }//GEN-LAST:event_nomKeyPressed

    
    
    private void apKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER ){
          int tamaño=ap.getText().length();
            if(ap.getText().isEmpty()){
                am.requestFocus();
                labelap.setForeground(Color.black);
            }             
            else if(String.valueOf(ap.getText().charAt(0)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su primera letra no debe ser un espacio en blanco");
                 labelap.setForeground(Color.red);
            }
            else if(String.valueOf(ap.getText().charAt(ap.getText().length() - 1)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su ultima letra no debe ser un espacio en blanco");
                 labelap.setForeground(Color.red);
            }
            else {
                am.requestFocus();
                labelap.setForeground(Color.black);
            }
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          nom.requestFocus();
        }
    }//GEN-LAST:event_apKeyPressed

    private void amKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          int tamaño=am.getText().length();
            if(am.getText().isEmpty()){
                edad.requestFocus();
                labelam.setForeground(Color.black);
            }             
            else if(String.valueOf(am.getText().charAt(0)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su primera letra no debe ser un espacio en blanco");
                 labelam.setForeground(Color.red);
            }
            else if(String.valueOf(am.getText().charAt(am.getText().length() - 1)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su ultima letra no debe ser un espacio en blanco");
                 labelam.setForeground(Color.red);
            }
            else {
                edad.requestFocus();
                labelam.setForeground(Color.black);
            }
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          ap.requestFocus();
        }
    }//GEN-LAST:event_amKeyPressed

    private void edadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edadKeyPressed
      if(evt.getKeyCode()==KeyEvent.VK_ENTER){                                                 
          cp.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          am.requestFocus();
        }
    }//GEN-LAST:event_edadKeyPressed

    private void calleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calleKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          numero.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          ciudad.requestFocus();
        }
    }//GEN-LAST:event_calleKeyPressed

    private void numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          nss.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          calle.requestFocus();
        }
    }//GEN-LAST:event_numeroKeyPressed

    private void cpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){        
          estado.requestFocus();         
          if(cp.getText().isEmpty()){}
          else Ayuda_de_cp(cp.getText());          
        }     
    }//GEN-LAST:event_cpKeyPressed

    public void Ayuda_de_cp(String codigo){       
        colonia.removeAllItems(); //se elimina todo lo que tenga el jcombobox a modo de reinicio
        try {
            Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
            St=Con.createStatement();
            Rs=St.executeQuery("select Estado,Municipio from codigospostales where CodigoPostal = "+codigo+";");
            //Rs=St.executeQuery("CALL EST_MUN("+codigo+");");
            while(Rs.next()){               
                   estado.setText(Rs.getString("Estado"));
                   ciudad.setText(Rs.getString("Municipio"));                
            }
            String col="";
            Rs=St.executeQuery("select Colonia from codigospostales where CodigoPostal = "+codigo+";");
            //Rs=St.executeQuery("CALL COL_CODIGO("+codigo+");");
            while(Rs.next()){                             
                   col=(Rs.getString("Colonia"));
            }       
            char[]caracteres;
            caracteres=col.toCharArray();
            col="";           
            for (int x = 0; x < caracteres.length; x++) {
               if(caracteres[x]==';'){               
                colonia.addItem(col);
                col="";
               }else{                             
                col+=Character.toString(caracteres[x]);     
                if(x==caracteres.length-1)colonia.addItem(col);
               }
            }            
            St.close();
            Rs.close();
            Con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    private void ciudadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ciudadKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          calle.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          estado.requestFocus();
        }
    }//GEN-LAST:event_ciudadKeyPressed

    private void estadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_estadoKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          int tamaño=estado.getText().length();
            if(estado.getText().isEmpty()){
                ciudad.requestFocus();
                labelestado.setForeground(Color.black);
            }             
            else if(String.valueOf(estado.getText().charAt(0)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su primera letra no debe ser un espacio en blanco");
                 labelestado.setForeground(Color.red);
            }
            else if(String.valueOf(estado.getText().charAt(estado.getText().length() - 1)).equals(" ")){
                 JOptionPane.showMessageDialog(null,"Su ultima letra no debe ser un espacio en blanco");
                 labelestado.setForeground(Color.red);
            }
            else {
                ciudad.requestFocus();
                labelestado.setForeground(Color.black);
            }
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          edad.requestFocus();
        }
    }//GEN-LAST:event_estadoKeyPressed

    private void nssKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nssKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          rfc.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
         numero.requestFocus();
        }
    }//GEN-LAST:event_nssKeyPressed

    private void rfcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rfcKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          curp.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          nss.requestFocus();
        }
    }//GEN-LAST:event_rfcKeyPressed

    private void curpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_curpKeyPressed
      if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          celular.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          rfc.requestFocus();
        }
    }//GEN-LAST:event_curpKeyPressed

    private void celularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celularKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          fijo.requestFocus();
        }
        else if(evt.getKeyCode()==KeyEvent.VK_UP){
          curp.requestFocus();
        }
    }//GEN-LAST:event_celularKeyPressed

    private void fijoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fijoKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_UP){
          celular.requestFocus();
        }
    }//GEN-LAST:event_fijoKeyPressed

    
    private void ComboBusqueda2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBusqueda2ActionPerformed
        //se limpia el segundo campo de busqueda y se manda a llamar al metodo SeleccionCombo2
        busqueda2.setText("");
        SeleccionCombo2();       
    }//GEN-LAST:event_ComboBusqueda2ActionPerformed

    //Este metodo decide que parametro enviarle al metodo autoComCampoBusqueda2
    //de acuerdo a lo que este seleccionado en el jcombobox ComboBusqueda2
    public void SeleccionCombo2(){      
        if(ComboBusqueda2.getSelectedItem()=="Nombre")autoComCampoBusqueda2("Nombre");
        else if(ComboBusqueda2.getSelectedItem()=="Apellido")autoComCampoBusqueda2("Apellido");
        else if(ComboBusqueda2.getSelectedItem()=="ID")autoComCampoBusqueda2("ID");
    }
    
     public void autoComCampoBusqueda2(String parametro){
       //primero se limpia para evitar repeticiones
         autoComCampoBusqueda2.removeAllItems();
       try{  
           //se hace conexion a la base de datos
            Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
            St=Con.createStatement();
          
            //checa si el campo de busqueda1 esta vacio para ver que tipo de busqueda se debe hacer
            if(busqueda1.getText().isEmpty()){//si esta vacio el campo de busqueda 1
                
                //checa las casillas de activos e inactivos para ver que tipo de busqueda se debe hacer
                boolean act=false,inact=false;
                 if(activos.isSelected()){act=true;}
                 if(inactivos.isSelected()){inact=true;}
                 
                 //ya que se checaron los parametros de busqueda que se solicitaron
                 //ahora es necesario decidir la busqueda que se hara con estos
                 //parametros
                 
                 //si estan seleccionadas ambas casillas, activos e inactivos, o bien, ninguna esta seleccionada
                 //despues checa si la busqueda1 es por nombre,apellido o Id
                 if(act==true&&inact==true||act==false&&inact==false){//ignora si es activo o inactivo para el autocompleter
                   if(parametro.equals("Nombre")){Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Todos FROM personal;");}
                   //if(parametro.equals("Nombre")){Rs = St.executeQuery("CALL ALL_PER_NAME;");}                  
                   else if(parametro.equals("Apellido")){ Rs = St.executeQuery("SELECT  CONCAT(ap,' ',am,' ',nom_p) as Todos FROM personal;");}
                   //else if(parametro.equals("Apellido")){ Rs = St.executeQuery("CALL ALL_PER_AP;");}
                    else if(parametro.equals("ID")){Rs = St.executeQuery("SELECT ID_Per as Todos FROM personal;");}
                   //else if(parametro.equals("ID")){Rs = St.executeQuery("CALL ALL_PER_ID;");}
                 }
                 //si esta seleccionada la casilla activos pero no la de inactivos
                 //despues checa si la busqueda1 es por nombre,apellido o Id
                 else if(act==true&&inact==false){//solo los activos se utilizaran en la busqueda del autocompleter
                   if(parametro.equals("Nombre")){Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Todos FROM personal where condicion='Activo';");}
                   //if(parametro.equals("Nombre")){Rs = St.executeQuery("CALL ALL_PER_NAME_ACT;");}
                    else if(parametro.equals("Apellido")){ Rs = St.executeQuery("SELECT  CONCAT(ap,' ',am,' ',nom_p) as Todos FROM personal where condicion='Activo';");}
                   //else if(parametro.equals("Apellido")){ Rs = St.executeQuery("CALL ALL_PER_AP_ACT;");} 
                   else if(parametro.equals("ID")){Rs = St.executeQuery("SELECT ID_Per as Todos FROM personal where condicion='Activo';");}
                   //else if(parametro.equals("ID")){Rs = St.executeQuery("CALL ALL_PER_ID_ACT;");}
                 }
                 //si esta seleccionada la casilla inactivos pero no la de activos
                 //despues checa si la busqueda1 es por nombre,apellido o Id
                 else if(act==false&&inact==true){//solo los activos se utilizaran en la busqueda del autocompleter
                   if(parametro.equals("Nombre")){Rs = St.executeQuery("SELECT  CONCAT(nom_p,' ',ap,' ',am) as Todos FROM personal where condicion='Inactivo';");}
                   //if(parametro.equals("Nombre")){Rs = St.executeQuery("CALL ALL_PER_NAME_INACT;");}
                    else if(parametro.equals("Apellido")){ Rs = St.executeQuery("SELECT  CONCAT(ap,' ',am,' ',nom_p) as Todos FROM personal where condicion='Inactivo';");}
                   //else if(parametro.equals("Apellido")){ Rs = St.executeQuery("CALL ALL_PER_AP_INACT");}
                    else if(parametro.equals("ID")){Rs = St.executeQuery("SELECT ID_Per as Todos FROM personal where condicion='Inactivo';");}
                   //else if(parametro.equals("ID")){Rs = St.executeQuery("CALL ALL_PER_ID_INACT;");}
                 }                  
            }
            //si ya de plano se quiere combinar a todo lo anterior el parametro de busqueda1
            //el cual puede ser un puesto,sucursal o estado entonces hay que hacer una busqueda supercombinada
            else{
               BusquedaCombinada();      
            } //ahora si se realiza la busqueda y se rellena el autocompleter   
            while(Rs.next()){
                //si se selecciono la busqueda por ID es necesario agregar un espacio para evitar errores    
                if(ComboBusqueda2.getSelectedItem()=="ID")autoComCampoBusqueda2.addItem(Rs.getString("Todos")+" ");        
                else autoComCampoBusqueda2.addItem(Rs.getString("Todos"));
            }
            //se cierran las conexiones a la base de datos
            Rs.close();
            St.close();
            Con.close();
          } catch (Exception e){
                    JOptionPane.showMessageDialog(null,e);
          }    
    }
    
    //el siguiente metodo se invoca cuando se requiere una busqueda que incluya el parametro de del cambo busqueda1 y busqueda2
    //el cual puede ser un puesto,sucursal o estado 
    public void BusquedaCombinada(){
       //este metodo fue hecho para hacer una busqueda personlizada con los dos combobox de busqueda
       //se utiliza en autoComCampoBusqueda2
        try {
                 //checa las casillas de activos e inactivos para ver que tipo de busqueda se debe hacer
                 boolean act=false,inact=false;
                 if(activos.isSelected()){act=true;}
                 if(inactivos.isSelected()){inact=true;}
                 
                 //Si estan seleccionadas ambas casillas, activos e inactivos, o bien, ninguna esta seleccionada           
                 if(act==true&&inact==true||act==false&&inact==false){
                     //busqueda combinada para puesto
                     //Este caso es para cuando esta seleccionado el parametro puesto para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(personal.nom_p,' ',personal.ap,' ',personal.am) as Todos FROM personal,puesto where puesto.nom_p = '"+busqueda1.getText()+"' and personal.ID_Pue=puesto.ID_Pue;");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_PUE`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(personal.ap,' ',personal.am,' ',personal.nom_p) as Todos FROM personal,puesto where puesto.nom_p = '"+busqueda1.getText()+"' and personal.ID_Pue=puesto.ID_Pue;");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_PUE`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT personal.ID_Per as Todos FROM personal,puesto  where puesto.nom_p = '"+busqueda1.getText()+"' and personal.ID_Pue=puesto.ID_Pue;");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_PUE`('"+busqueda1.getText()+"');");
                         
                     //busqueda combinada para sucursal
                     //Este caso es para cuando esta seleccionado el parametro sucursal para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(personal.nom_p,' ',personal.ap,' ',personal.am) as Todos FROM personal,sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.ID_Suc=sucursal.ID_Suc;");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_SUC`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(personal.ap,' ',personal.am,' ',personal.nom_p) as Todos FROM personal,sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.ID_Suc=sucursal.ID_Suc;");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_SUC`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT personal.ID_Per as Todos FROM personal,sucursal  where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.ID_Suc=sucursal.ID_Suc;");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_SUC`('"+busqueda1.getText()+"');");
                         
                     //busqueda combinada para estado
                     //Este caso es para cuando esta seleccionado el parametro estado para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(nom_p,' ',ap,' ',am) as Todos FROM personal where estado = '"+busqueda1.getText()+"';");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_EST`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(ap,' ',am,' ',nom_p) as Todos FROM personal where estado = '"+busqueda1.getText()+"';");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_EST`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT ID_Per as Todos FROM personal where estado = '"+busqueda1.getText()+"';");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_EST`('"+busqueda1.getText()+"');");
                 }
                 //si esta seleccionada la casilla activos pero no la de inactivos
                 else if(act==true&&inact==false){
                     //busqueda combinada para puesto
                     //Este caso es para cuando esta seleccionado el parametro puesto para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(personal.nom_p,' ',personal.ap,' ',personal.am) as Todos FROM personal,puesto where puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Pue=puesto.ID_Pue;");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_PUE_ACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(personal.ap,' ',personal.am,' ',personal.nom_p) as Todos FROM personal,puesto where puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Pue=puesto.ID_Pue;");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_PUE_ACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT personal.ID_Per as Todos FROM personal,puesto  where puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Pue=puesto.ID_Pue;");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_PUE_ACT`('"+busqueda1.getText()+"');");
                         
                     //busqueda combinada para sucursal
                     //Este caso es para cuando esta seleccionado el parametro sucursal para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(personal.nom_p,' ',personal.ap,' ',personal.am) as Todos FROM personal,sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Suc=sucursal.ID_Suc;");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_SUC_ACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(personal.ap,' ',personal.am,' ',personal.nom_p) as Todos FROM personal,sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Suc=sucursal.ID_Suc;");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_SUC_ACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT personal.ID_Per as Todos FROM personal,sucursal  where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Suc=sucursal.ID_Suc;");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_SUC_ACT`('"+busqueda1.getText()+"');");
                         
                     //busqueda combinada para estado
                     //Este caso es para cuando esta seleccionado el parametro estado para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(nom_p,' ',ap,' ',am) as Todos FROM personal where estado = '"+busqueda1.getText()+"' and condicion='Activo';");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_EST_ACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(ap,' ',am,' ',nom_p) as Todos FROM personal where estado = '"+busqueda1.getText()+"' and  condicion='Activo';");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_EST_ACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT ID_Per as Todos FROM personal where estado = '"+busqueda1.getText()+"' and condicion='Activo';");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_EST_ACT`('"+busqueda1.getText()+"');");
                  }
                 //si esta seleccionada la casilla inactivos pero no la de activos
                  else if(act==false&&inact==true){
                     //busqueda combinada para puesto
                     //Este caso es para cuando esta seleccionado el parametro puesto para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(personal.nom_p,' ',personal.ap,' ',personal.am) as Todos FROM personal,puesto where puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Pue=puesto.ID_Pue;");                           
                      //Rs = St.executeQuery("CALL `ALL_PER_NAME_PUE_INACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(personal.ap,' ',personal.am,' ',personal.nom_p) as Todos FROM personal,puesto where puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Pue=puesto.ID_Pue;");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_PUE_INACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Puesto"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT personal.ID_Per as Todos FROM personal,puesto  where puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Pue=puesto.ID_Pue;");                    
                      //Rs = St.executeQuery("CALL `ALL_PER_ID_PUE_INACT`('"+busqueda1.getText()+"');");
                         
                     //busqueda combinada para sucursal
                     //Este caso es para cuando esta seleccionado el parametro sucursal para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(personal.nom_p,' ',personal.ap,' ',personal.am) as Todos FROM personal,sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Suc=sucursal.ID_Suc;");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_SUC_INACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(personal.ap,' ',personal.am,' ',personal.nom_p) as Todos FROM personal,sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Suc=sucursal.ID_Suc;");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_SUC_INACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Sucursal"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT personal.ID_Per as Todos FROM personal,sucursal  where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Suc=sucursal.ID_Suc;");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_SUC_INACT`('"+busqueda1.getText()+"');");
                         
                     //busqueda combinada para estado
                     //Este caso es para cuando esta seleccionado el parametro estado para el campo de busqueda1
                     //Finalmente checa si la busqueda2 es por nombre,apellido o Id
                     if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="Nombre")
                     Rs = St.executeQuery("SELECT CONCAT(nom_p,' ',ap,' ',am) as Todos FROM personal where estado = '"+busqueda1.getText()+"' and condicion='Inactivo';");                           
                     //Rs = St.executeQuery("CALL `ALL_PER_NAME_EST_INACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="Apellido")
                     Rs = St.executeQuery("SELECT CONCAT(ap,' ',am,' ',nom_p) as Todos FROM personal where estado = '"+busqueda1.getText()+"' and  condicion='Inactivo';");
                     //Rs = St.executeQuery("CALL `ALL_PER_AP_EST_INACT`('"+busqueda1.getText()+"');");
                     else if(ComboBusqueda1.getSelectedItem()=="Estado"&&ComboBusqueda2.getSelectedItem()=="ID")
                     Rs = St.executeQuery("SELECT ID_Per as Todos FROM personal where estado = '"+busqueda1.getText()+"' and condicion='Inactivo';");                    
                     //Rs = St.executeQuery("CALL `ALL_PER_ID_EST_INACT`('"+busqueda1.getText()+"');");
                  }
        } catch (Exception e) {
            //si hay algun error aqui se muestra
            JOptionPane.showMessageDialog(null,e);
        }
    }
     
    private void condicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_condicionActionPerformed
  
    }//GEN-LAST:event_condicionActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int filaseleccionada;
        try{
                //se obtiene el numero de renglon seleccionado
                filaseleccionada= jTable2.getSelectedRow();         
                DefaultTableModel modelotabla=(DefaultTableModel) jTable2.getModel(); 
                //se obtiene el motivo de baja de la tabla
                String motivotb=(String)modelotabla.getValueAt(filaseleccionada, 1);                          
                
                if (m!= null) {//si existe una venta, la cierra.
                  m.dispose();
                }
                 m.setVisible(true);
                 //se manda a llamar una ventana que muestra el motivo de baja
                 MuestraMotivo.motivoF.setText(motivotb);
                                
        }catch (HeadlessException ex){
            JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTable2MouseClicked
  
    //cada ves que se presiona una tecla en el campo de texto del nombre
    //se manda a llamar a este metodo automaticamente para validar que sea
    //un caracter correcto
    private void nomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nomKeyTyped
        //primero se guarda en la cadena nombre lo que hay en el campo
        //de texto antes del nuevo caracter ingresado
        String nombre=nom.getText();
          //se obtiene solo el caracter ingresado
          char k=evt.getKeyChar();
          
         //solo si el caracter ingresado es una letra, un punto o un espacio se
         //dejara ingresar y se analizara el orden correcto para la validacion
         if(Character.isLetter(k)||k=='.'||k==' '){
          //si el campo de texto no esta vacio
          if(nombre.length()>0){          
            //si el ultimo caracter en el campo de texto es un punto
            //y el caracter ingresado es diferente de un espacio
            //entonces no se deja ingresar este caracter
           if(nombre.charAt(nombre.length()-1)=='.'&&k!=' '){
            getToolkit().beep();
            evt.consume();
           }
           //si el ultimo caracter en el campo de texto es un espacio
           //y el caracter ingresado es un espacio o un punto
           ////entonces no se deja ingresar este caracter
           else if(nombre.charAt(nombre.length()-1)==' '&&(k==' '||k=='.')){
            getToolkit().beep();
            evt.consume();
           }
           //no se deja ingresar un caracter si en el campo de texto ya hay 25 caracteres
            else if(nombre.length()==25){
              getToolkit().beep();
              evt.consume();
            }  
          }
          //si el campo de texto si esta vacio no se pueden
          //ingresar espacios o puntos
          else if(nombre.isEmpty()){
            if(k==' '||k=='.'){
              getToolkit().beep();
              evt.consume();  
            }
          }                 
         }
         //si el caracter es algo diferente de una letra, punto o espacio
         //no se deja ingresar el caracter
         else{
              evt.consume();  
         }
         //si el campo de texto ya contiene un punto ya no deja ingresar otro         
         if(nombre.contains(".")&&k=='.'){
              getToolkit().beep();
              evt.consume();
          }
    }//GEN-LAST:event_nomKeyTyped

    private void apKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apKeyTyped
        //primero se guarda en la cadena:nombre lo que hay en el campo
        //de texto antes del nuevo caracter ingresado
         String nombre=ap.getText();
         //se obtiene el nuevo caracter ingresado
          char k=evt.getKeyChar();
         //si el caracter ingresado es letra,punto o espacio este es analizado
         if(Character.isLetter(k)||k=='.'||k==' '){
          if(nombre.length()>0){//si el campo de texto no esta vacio       
           //si el ultimo caracter en el campo de texto es un punto
            //y el caracter ingresado es diferente de un espacio
            //entonces no se deja ingresar este caracter
           if(nombre.charAt(nombre.length()-1)=='.'&&k!=' '){
            getToolkit().beep();
            evt.consume();
           }
           //si el ultimo caracter en el campo de texto es un espacio
           //y el caracter ingresado es un espacio o un punto
           ////entonces no se deja ingresar este caracter
           else if(nombre.charAt(nombre.length()-1)==' '&&(k==' '||k=='.')){
            getToolkit().beep();
            evt.consume();
           }
           //no se deja ingresar un caracter si en el campo de texto ya hay 25 caracteres
            else if(nombre.length()==25){
            getToolkit().beep();
              evt.consume();
            } 
          }
          //si el campo de texto si esta vacio no se pueden
          //ingresar espacios o puntos
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
         //si el campo de texto ya contiene un punto ya no deja ingresar otro 
         if(nombre.contains(".")&&k=='.'){
              getToolkit().beep();
              evt.consume();
          }
    }//GEN-LAST:event_apKeyTyped

    private void amKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amKeyTyped
         //primero se guarda en la cadena:nombre lo que hay en el campo
        //de texto antes del nuevo caracter ingresado
         String nombre=am.getText();
         //se obtiene el nuevo caracter ingresado
          char k=evt.getKeyChar();
         //si el caracter ingresado es letra,punto o espacio este es analizado
         if(Character.isLetter(k)||k=='.'||k==' '){
          if(nombre.length()>0){//si el campo de texto no esta vacio       
           //si el ultimo caracter en el campo de texto es un punto
            //y el caracter ingresado es diferente de un espacio
            //entonces no se deja ingresar este caracter
           if(nombre.charAt(nombre.length()-1)=='.'&&k!=' '){
            getToolkit().beep();
            evt.consume();
           }
           //si el ultimo caracter en el campo de texto es un espacio
           //y el caracter ingresado es un espacio o un punto
           ////entonces no se deja ingresar este caracter
           else if(nombre.charAt(nombre.length()-1)==' '&&(k==' '||k=='.')){
            getToolkit().beep();
            evt.consume();
           }
           //no se deja ingresar un caracter si en el campo de texto ya hay 25 caracteres
            else if(nombre.length()==25){
            getToolkit().beep();
              evt.consume();
            } 
          }
          //si el campo de texto si esta vacio no se pueden
          //ingresar espacios o puntos
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
         //si el campo de texto ya contiene un punto ya no deja ingresar otro 
         if(nombre.contains(".")&&k=='.'){
              getToolkit().beep();
              evt.consume();
          }
    }//GEN-LAST:event_amKeyTyped

    private void estadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_estadoKeyTyped
        String nombre=estado.getText();
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
            else if(nombre.length()==20){
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
    }//GEN-LAST:event_estadoKeyTyped

    private void ciudadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ciudadKeyTyped
       String nombre=ciudad.getText();
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
            else if(nombre.length()==30){
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
    }//GEN-LAST:event_ciudadKeyTyped

    private void edadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edadKeyTyped
        char k=evt.getKeyChar();
        String EDAD=edad.getText();
        if(Character.isDigit(k)){
           if(EDAD.isEmpty()&&(k=='0'||k=='9')){
              getToolkit().beep();
              evt.consume();
           } 
           else if(EDAD.length()>0){          
             if(EDAD.length()==2){
              //getToolkit().beep();
              evt.consume();
             }
             else if(EDAD.charAt(EDAD.length()-1)=='1'&&(k=='0'||k=='1'||k=='2'||k=='3'||k=='4')){
              getToolkit().beep();
              evt.consume();
             }
             else if(EDAD.charAt(EDAD.length()-1)=='8'&&k!='0'){
               getToolkit().beep();
               evt.consume();
             }  
            }       
        }//if de si es digito
        else{
          evt.consume();
        }                  
    }//GEN-LAST:event_edadKeyTyped

    private void cpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpKeyTyped
       char k=evt.getKeyChar();
        String CP=cp.getText();        
        if(Character.isDigit(k)){
           if(CP.length()>0){          
             if(CP.length()==5){            
              evt.consume();
             }
            }
         }               
        else{
          //getToolkit().beep();
          evt.consume();
        }//else    
    }//GEN-LAST:event_cpKeyTyped

    private void numeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numeroKeyTyped
       //se obtiene el caracter ingresado
        char k=evt.getKeyChar();
       //se obtienen los caracteres que ya estan el campo de texto
        String NUMERO=numero.getText();
       //solo se analizaran digitos y letras
       if(Character.isDigit(k)||Character.isLetter(k)){
        //no se aceptan espacios
         if(k==' '){
          getToolkit().beep();
          evt.consume();      
        }
        ////no se acepta un cero como primer caracter
        else if(NUMERO.isEmpty()&&(k=='0'||Character.isLetter(k))){
            getToolkit().beep();
            evt.consume();
        }
        else if(NUMERO.length()>0){
           //no se aceptan mas de 6 numeros 
           if(NUMERO.length()==6){
            getToolkit().beep();
            evt.consume();
           }
           //solo se acepta una letra despues de un numero
           else if(Character.isLetter(NUMERO.charAt(NUMERO.length()-1))){
            getToolkit().beep();
            evt.consume();
           }          
        }
       }else{          
            evt.consume();
       }
    }//GEN-LAST:event_numeroKeyTyped

    private void celularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_celularKeyTyped
        //se obtienen el caracter ingresado
        //y los que estan en el campo de texto
        char k=evt.getKeyChar();
        String NUMERO=celular.getText();
        //solo se aceptan numeros
       if(Character.isDigit(k)){
        //no acepta espacios
           if(k==' '){
          getToolkit().beep();
          evt.consume();      
        }        
        else if(NUMERO.length()>0){          
           //solo se aceptan 13 numeros
            if(NUMERO.length()==13){
            getToolkit().beep();
            evt.consume();
           }                  
        }
       }
       //no se acepta nada que no sea numero
       else{          
            evt.consume();
       }
    }//GEN-LAST:event_celularKeyTyped

    private void fijoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fijoKeyTyped
        //se obtienen el caracter ingresado
        //y los que estan en el campo de texto
        char k=evt.getKeyChar();
        String NUMERO=fijo.getText();
         //solo se aceptan numeros
       if(Character.isDigit(k)){
        if(k==' '){
          getToolkit().beep();
          evt.consume();      
        }
         //no acepta espacios        
        else if(NUMERO.length()>0){
            //solo se aceptan 10 numeros
           if(NUMERO.length()==10){
            getToolkit().beep();
            evt.consume();
           }                  
        }
       }
       //no se acepta nada que no sea numero
       else{          
            evt.consume();
       }
    }//GEN-LAST:event_fijoKeyTyped

    private void nssKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nssKeyTyped
        //se obtiene el caracter ingresado y los que ya estan
        //el campo de texto
        char k=evt.getKeyChar();
        String NSS=nss.getText();
        //solo se aceptan digitos      
        if(Character.isDigit(k)){
            //si el campo de texto no esta vacio
            //solo se permiten 11 caracteres
            if(NSS.length()>0){          
             if(NSS.length()==11){
               getToolkit().beep();
               evt.consume();
             }         
           }        
        }
        //no deja entrar caracteres que no sean digitos
        else{         
          evt.consume();
        }        
           
    }//GEN-LAST:event_nssKeyTyped

    private void FechaNaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FechaNaKeyPressed
        
    }//GEN-LAST:event_FechaNaKeyPressed

    private void FechaNaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FechaNaMouseClicked
        
    }//GEN-LAST:event_FechaNaMouseClicked

    private void FechaNaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FechaNaMousePressed
        
    }//GEN-LAST:event_FechaNaMousePressed

    private void busqueda1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busqueda1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
          busqueda2.requestFocus();
          SeleccionCombo2();//esto para que actualice el autocompleter del texfield busqueda2
          //y a continuacion llenamos la tabla dependiendo de lo que se haya seleccionado en el combobox1 y si es activo o inactivo
          busqueda_especial_tabla();
        }   
    }//GEN-LAST:event_busqueda1KeyPressed

    private void busqueda2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busqueda2MouseClicked
         SeleccionCombo2();//esto para que actualice el autocompleter del texfield busqueda2
         //y a continuacion llenamos la tabla dependiendo de lo que se haya seleccionado en el combobox1 y si es activo o inactivo
         busqueda_especial_tabla();
    }//GEN-LAST:event_busqueda2MouseClicked

    private void activosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activosMouseClicked
       //llenamos la tabla dependiendo de lo que se haya seleccionado en el combobox1 y si es activo o inactivo
        busqueda_especial_tabla();
    }//GEN-LAST:event_activosMouseClicked

    private void inactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inactivosMouseClicked
        //llenamos la tabla dependiendo de lo que se haya seleccionado en el combobox1 y si es activo o inactivo
        busqueda_especial_tabla();
    }//GEN-LAST:event_inactivosMouseClicked

    private void busqueda2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busqueda2KeyPressed
          
    }//GEN-LAST:event_busqueda2KeyPressed

    private void calleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calleKeyTyped
        //primero se guarda en la cadena nombre lo que hay en el campo
        //de texto antes del nuevo caracter ingresado
        String nombre=calle.getText();
        //se obtiene solo el caracter ingresado  
        char k=evt.getKeyChar();
        //solo si el caracter ingresado es una letra,un digito, un punto o un espacio se
         //dejara ingresar y se analizara el orden correcto para la validacion
         if(Character.isLetter(k)||k=='.'||k==' '||Character.isDigit(k)){
           //si el campo de texto no esta vacio
           if(nombre.length()>0){
             //si el ultimo caracter en el campo de texto es un punto
            //y el caracter ingresado es diferente de un espacio
            //entonces no se deja ingresar este caracter  
            if(nombre.charAt(nombre.length()-1)=='.'&&k!=' '){
            getToolkit().beep();
            evt.consume();
           }
            //si el ultimo caracter en el campo de texto es un espacio
           //y el caracter ingresado es un espacio o un punto
           ////entonces no se deja ingresar este caracter
           else if(nombre.charAt(nombre.length()-1)==' '&&(k==' '||k=='.')){
            getToolkit().beep();
            evt.consume();
           }
           //no se deja ingresar un caracter si en el campo de texto ya hay 50 caracteres
            else if(nombre.length()==50){
              getToolkit().beep();
              evt.consume();
            }  
          }
           //si el campo de texto si esta vacio no se pueden
          //ingresar espacios o puntos
          else if(nombre.isEmpty()){
            if(k==' '||k=='.'){
              getToolkit().beep();
              evt.consume();  
            }
          }          
         }
         //si el caracter es algo diferente de una letra, punto o espacio
         //no se deja ingresar el caracter
         else{
              //getToolkit().beep();
              evt.consume();  
         } 
         //si el campo de texto ya contiene un punto ya no deja ingresar otro 
         if(nombre.contains(".")&&k=='.'){
              getToolkit().beep();
              evt.consume();
          }
    }//GEN-LAST:event_calleKeyTyped

    private void busqueda2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busqueda2KeyTyped
      if(ComboBusqueda2.getSelectedItem()=="Nombre"||ComboBusqueda2.getSelectedItem()=="Apellido"){
         String nombre=busqueda2.getText();
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
      else if(ComboBusqueda2.getSelectedItem()=="ID"){
        char k=evt.getKeyChar();
        String ID=busqueda2.getText();        
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
      }//else if, este es para el ID
    }//GEN-LAST:event_busqueda2KeyTyped

    private void busqueda1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busqueda1KeyTyped
       char k=evt.getKeyChar();
        String CODIGO=busqueda1.getText();        
        if(Character.isLetter(k)){
          if(CODIGO.length()>0){          
           if(CODIGO.length()==14){
            getToolkit().beep();
            evt.consume();
           }         
          }   
        }else{
          evt.consume(); 
        }                 
    }//GEN-LAST:event_busqueda1KeyTyped

    private void curpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_curpKeyTyped
        //se obienen el caracter ingresado y los que ya estan
        //en el campo de texto
        char k=evt.getKeyChar();
        String CURP=curp.getText();
       //solo se analizaran digitos y letras
       if(Character.isDigit(k)||Character.isLetter(k)){
        //no se aceptan espacios
        if(k==' '){
          getToolkit().beep();
          evt.consume();      
        } 
        //si el campo esta vacio no acepta digitos 
        else if(CURP.isEmpty()&&(Character.isDigit(k))){
            getToolkit().beep();
            evt.consume();
        }
        //si el campo no esta vacio
        else if(CURP.length()>0){
           //solo se aceptan 18 caracteres maximo
           if(CURP.length()==18){
            getToolkit().beep();
            evt.consume();
           }
           //no se aceptan digitos en los primeros 4 caracteres
           else if(CURP.length()<=3&&Character.isDigit(k)){
            getToolkit().beep();
            evt.consume();
           }
           //no se aceptan letras entre los caracteres 5 y 10
           else if(CURP.length()>=4&&CURP.length()<=9&&Character.isLetter(k)){
            getToolkit().beep();
            evt.consume();
           }
           //no se aceptan digitos entre los caracteres 12 y 16
           else if(CURP.length()>=10&&CURP.length()<=15&&Character.isDigit(k)){
            getToolkit().beep();
            evt.consume();
           }
           //no se aceptan letras en los ultimos 2 caracteres
           else if(CURP.length()>=16&&CURP.length()<=17&&Character.isLetter(k)){
            getToolkit().beep();
            evt.consume();
           }
        }
       }
       //si no es digito o letra no se permite el caracter
       else{          
            evt.consume();
       }
    }//GEN-LAST:event_curpKeyTyped

    private void rfcKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rfcKeyTyped
        //se obtiene el caracter ingresado
        //y los caracteres que ya estan ingresados
        char k=evt.getKeyChar();
        String RFC=rfc.getText();
        //si el caracter ingresado es letra o numero se acepta
       if(Character.isDigit(k)||Character.isLetter(k)){
        //no se aceptan espacios
        if(k==' '){
          getToolkit().beep();
          evt.consume();      
        } 
        //no se puede meter un numero como primer caracter
        else if(RFC.isEmpty()&&(Character.isDigit(k))){
            getToolkit().beep();
            evt.consume();
        }
        //se checa si el campo no esta vacio
        else if(RFC.length()>0){
           //acepta 13 caracteres maximo
           if(RFC.length()==13){
            getToolkit().beep();
            evt.consume();
           }
           //no se aceptan digitos en los primeros 4 caracteres
           else if(RFC.length()<=3&&Character.isDigit(k)){
            getToolkit().beep();
            evt.consume();
           }
           //no se aceptan letras entre los caracteres 5 y 10
           else if(RFC.length()>=4&&RFC.length()<=9&&Character.isLetter(k)){
            getToolkit().beep();
            evt.consume();
           }          
        }
       }else{          
            evt.consume();
       }
    }//GEN-LAST:event_rfcKeyTyped

    private void FechaNaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FechaNaKeyTyped
        
    }//GEN-LAST:event_FechaNaKeyTyped

    private void FechaIngreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FechaIngreKeyTyped
           
    }//GEN-LAST:event_FechaIngreKeyTyped

    private void coloniaPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_coloniaPopupMenuWillBecomeVisible
        

    }//GEN-LAST:event_coloniaPopupMenuWillBecomeVisible

    private void coloniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coloniaActionPerformed
        ColCombobox=(String)colonia.getSelectedItem();
    }//GEN-LAST:event_coloniaActionPerformed

    public void busqueda_especial_tabla(){
         boolean act=false,inact=false;
         if(activos.isSelected()){act=true;}
         if(inactivos.isSelected()){inact=true;}
         
         if(busqueda1.getText().isEmpty()){//si esta vacio el campo de busqueda 1 entonces que ignore si es activo o inactivo
             if(act==true&&inact==true||act==false&&inact==false)cargardatosTabla("select * from Personal;");
             //cargardatosTabla("CALL CDTP;");
             else if(act==true&&inact==false)cargardatosTabla("select * from Personal where condicion='Activo';");
             //cargardatosTabla("CALL `TODO_ACTIVO`();");
             else if(act==false&&inact==true)cargardatosTabla("select * from Personal where condicion='Inactivo';"); 
             //cargardatosTabla("CALL `TODO_INACTIVO`();");
          }
         else{
            if(act==true&&inact==true||act==false&&inact==false){//ignora si es activo o inactivo
                if(ComboBusqueda1.getSelectedItem()=="Puesto")cargardatosTabla("select * from Personal,puesto where Puesto.nom_p = '"+busqueda1.getText()+"' and personal.ID_Pue=puesto.ID_Pue;");
                //cargardatosTabla("CALL TODO_PUE('"+busqueda1.getText()+"');");
                else if(ComboBusqueda1.getSelectedItem()=="Sucursal")cargardatosTabla("select * from Personal,Sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.ID_Suc=sucursal.ID_Suc;");
                //cargardatosTabla("CALL TODO_SUC('"+busqueda1.getText()+"');");
                else if(ComboBusqueda1.getSelectedItem()=="Estado")cargardatosTabla("select * from Personal where estado = '"+busqueda1.getText()+"';"); 
                //cargardatosTabla("CALL TODO_ESTADO('"+busqueda1.getText()+"');");
            }
            else if(act==true&&inact==false){
                if(ComboBusqueda1.getSelectedItem()=="Puesto")cargardatosTabla("select * from Personal,puesto where Puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Pue=puesto.ID_Pue;");
                //cargardatosTabla("CALL TODO_PUE_ACT('"+busqueda1.getText()+"');");
                else if(ComboBusqueda1.getSelectedItem()=="Sucursal")cargardatosTabla("select * from Personal,Sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Activo' and personal.ID_Suc=sucursal.ID_Suc;");
                //cargardatosTabla("CALL TODO_SUC_ACT('"+busqueda1.getText()+"');");
                else if(ComboBusqueda1.getSelectedItem()=="Estado")cargardatosTabla("select * from Personal where estado = '"+busqueda1.getText()+"' and condicion='Activo';");        
                //cargardatosTabla("CALL TODO_ESTADO_ACT('"+busqueda1.getText()+"');");
            }
            else if(act==false&&inact==true){
                if(ComboBusqueda1.getSelectedItem()=="Puesto")cargardatosTabla("select * from Personal,puesto where Puesto.nom_p = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Pue=puesto.ID_Pue;");
                //cargardatosTabla("CALL TODO_PUE_INACT('"+busqueda1.getText()+"');");
                else if(ComboBusqueda1.getSelectedItem()=="Sucursal")cargardatosTabla("select * from Personal,Sucursal where sucursal.nom_suc = '"+busqueda1.getText()+"' and personal.condicion='Inactivo' and personal.ID_Suc=sucursal.ID_Suc;");
                //cargardatosTabla("CALL TODO_SUC_INACT('"+busqueda1.getText()+"');");
                else if(ComboBusqueda1.getSelectedItem()=="Estado")cargardatosTabla("select * from Personal where estado = '"+busqueda1.getText()+"' and condicion='Inactivo';");
                //cargardatosTabla("CALL TODO_ESTADO_INACT('"+busqueda1.getText()+"');");
            }//else if
        }//else
    }
    
    public void cargardatos(String TP)
    {
     try{
         Rs.beforeFirst();//se reinicia el resulset
         colonia.removeAllItems();//se limpia la lista de colonias
         while(Rs.next()) 
         {
          //se cargan los datos encontrados en los campos
          //de texto correspondientes y se ajustan los calendarios        
          id.setText(Rs.getString(1));
          nom.setText(Rs.getString(4));
          ap.setText(Rs.getString(5));
          am.setText(Rs.getString(6));                
          Date date = new SimpleDateFormat("yyyy-MM-dd").parse(Rs.getString(7));FechaNa.setDate(date);      
          edad.setText(Rs.getString(8));
          tiposangre.setSelectedItem(TP);//STRING 9
          calle.setText(Rs.getString(10));
          numero.setText(Rs.getString(11));
          colonia.addItem(Rs.getString(12));
          cp.setText(Rs.getString(13));
          ciudad.setText(Rs.getString(14));
          estado.setText(Rs.getString(15));
          nss.setText(Rs.getString(16));
          rfc.setText(Rs.getString(17));
          curp.setText(Rs.getString(18));
          celular.setText(Rs.getString(19));
          fijo.setText(Rs.getString(20));
          //ajuste de calendarios
          date = new SimpleDateFormat("yyyy-MM-dd").parse(Rs.getString(21));FechaIngre.setDate(date);       
          date = new SimpleDateFormat("yyyy-MM-dd").parse(Rs.getString(22));FechaContra.setDate(date);
          date = new SimpleDateFormat("yyyy-MM-dd").parse(Rs.getString(23));FechaVenContra.setDate(date);
          date = new SimpleDateFormat("yyyy-MM-dd").parse(Rs.getString(24));FechaVenLM.setDate(date);     
         }    
        }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
        }
    }
    
    public void ConstruyeTabla(){
          jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
          TableColumn columnas = jTable1.getColumnModel().getColumn(0);
          columnas.setPreferredWidth(1);                       
    }
    
    public void cargardatosTabla(String consulta)
    {
        String PUESTO="",SUCURSAL="";
        int ID_PUE=0,ID_SUC=0;
        ResultSet resultset;
        Statement statement;
        DefaultTableModel tabla=new DefaultTableModel();
      try{                            
          Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
          St=Con.createStatement();
          statement=Con.createStatement();
          Rs=St.executeQuery(consulta);                 
          tabla.setColumnIdentifiers(new Object[]{"ID_Per","Nombre","Apellido Paterno","Apellido Materno","Edad","Tipo de Sangre","Ciudad","Estado","Puesto","Sucursal","Fecha Ingreso","Contratacion","Ven_Contra","Ven_Lic","Condicion"});
          while(Rs.next())
           {
            ID_PUE=Integer.parseInt(Rs.getString("ID_Pue"));
            ID_SUC=Integer.parseInt(Rs.getString("ID_Suc"));          
            
            resultset=statement.executeQuery("select nom_p from puesto where ID_Pue = "+ID_PUE+";");
            //resultset=statement.executeQuery("CALL PUE_TABLA("+ID_PUE+");");
            while(resultset.next())PUESTO=resultset.getString("nom_p");
           
            resultset=statement.executeQuery("select nom_suc from sucursal where ID_Suc = "+ID_SUC+";");           
            //resultset=statement.executeQuery("CALL `SUC_TABLA`("+ID_SUC+");");
            while(resultset.next())SUCURSAL=resultset.getString("nom_suc");          
            
            resultset.close();                  
            tabla.addRow(new Object[]{Rs.getString(1),Rs.getString(4),Rs.getString(5),Rs.getString(6),Rs.getString(8),Rs.getString(9),Rs.getString(14),Rs.getString(15),PUESTO,SUCURSAL,Rs.getString(21),Rs.getString(22),Rs.getString(23),Rs.getString(24),Rs.getString(25)});         
           }
          jTable1.setModel(tabla);
          
          statement.close();
          St.close();
          Rs.close();
          Con.close();
         }catch(Exception e){}
    }
    
    
    public void llenarpuesto(){
      puesto.removeAllItems();    
      String consulta="select*from puesto"; 
      //String consulta="CALL `TODOPue`();";  
        try {
             Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
             St=Con.createStatement();
             Rs=St.executeQuery(consulta);
             while(Rs.next()){
               puesto.addItem(Rs.getString("nom_p"));            
             } 
             St.close();
             Rs.close();
             Con.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e);
        }    
    }
    
    public void llenarsucursal(){
      sucursal.removeAllItems();    
      String consulta="select*from sucursal;";
      //String consulta="CALL `TODOSuc`();";
      
        try {
             Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
             St=Con.createStatement();
             Rs=St.executeQuery(consulta);
             while(Rs.next()){
               sucursal.addItem(Rs.getString("nom_suc"));            
             } 
             St.close();
             Rs.close();
             Con.close();
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e);
        }    
    }
    
    public void autocompletaDatosCP(){
        //autocompleta= new TextAutoCompleter(cp);                 
        try {
            Con=DriverManager.getConnection(Globales.Url,Globales.User,Globales.Pass);
            St=Con.createStatement();
            Rs=St.executeQuery("SELECT CodigoPostal from codigospostales;");
            //Rs=St.executeQuery("CALL `ALL_CODIGOS_POS`();");
            while(Rs.next()){               
                   autocompleta.addItem(Rs.getString("CodigoPostal"));               
            }
            St.close();
            Rs.close();
            Con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Personal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboBusqueda1;
    private javax.swing.JComboBox ComboBusqueda2;
    private com.toedter.calendar.JDateChooser FechaContra;
    private com.toedter.calendar.JDateChooser FechaIngre;
    private com.toedter.calendar.JDateChooser FechaNa;
    private com.toedter.calendar.JDateChooser FechaVenContra;
    private com.toedter.calendar.JDateChooser FechaVenLM;
    private javax.swing.JButton LP;
    private javax.swing.JCheckBox activos;
    private javax.swing.JButton agregar;
    private javax.swing.JTextField am;
    private javax.swing.JTextField ap;
    private javax.swing.JButton baja;
    private javax.swing.JTextField busqueda1;
    private javax.swing.JTextField busqueda2;
    private javax.swing.JTextField calle;
    private javax.swing.JTextField celular;
    private javax.swing.JTextField ciudad;
    private javax.swing.JComboBox<String> colonia;
    private javax.swing.JComboBox<String> condicion;
    private javax.swing.JButton consultar;
    private javax.swing.JTextField cp;
    private javax.swing.JTextField curp;
    private javax.swing.JTextField edad;
    private javax.swing.JTextField estado;
    private javax.swing.JTextField fijo;
    private javax.swing.JTextField id;
    private javax.swing.JCheckBox inactivos;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel labelam;
    private javax.swing.JLabel labelap;
    private javax.swing.JLabel labelcelular;
    private javax.swing.JLabel labelestado;
    private javax.swing.JLabel labelfijo;
    private javax.swing.JLabel labelnombre;
    private javax.swing.JButton modificar;
    private javax.swing.JTextField nom;
    private javax.swing.JTextField nss;
    private javax.swing.JTextField numero;
    private javax.swing.JComboBox puesto;
    private javax.swing.JButton regresar;
    private javax.swing.JTextField rfc;
    private javax.swing.JComboBox sucursal;
    private javax.swing.JComboBox tiposangre;
    // End of variables declaration//GEN-END:variables
}
