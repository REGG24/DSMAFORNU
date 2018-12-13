/*
 Esta es la clase principal del sistema, se ejecuta siempre primero.
 Checa si la base de datos esta creada, de lo contrario la crea. 
 Despues ejecuta la clase Login para comenzar a funcionar para el usuario.
 */
package dsmafornuesau;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CreaBDD {
  static Wait w=new Wait();
  public static void main(String[]args) throws SQLException{
        String Url="jdbc:mysql://localhost";
        String User="root";
        String Pass="";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");//cargar el controlador
            java.sql.Connection con=DriverManager.getConnection(Url,User,Pass);
            Statement St=con.createStatement();                
                
            //lo primero es checar si la base de datos existe
            ResultSet Rs = St.executeQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'dsmafornu';");
            if(Rs.last()){//si existe
               new Login().setVisible(true);
            }else{//no existe              
                w.setVisible(true);
                St.executeUpdate("CREATE DATABASE dsmafornu;");//se crea la base de datos para poder meterle un respaldo         
                //se mete un respadaldo con solo los registros de los codigos postales                                              
                //Process  p = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysql --user=root --password= dsmafornu");           
                Process  p = Runtime.getRuntime().exec("C:\\mysql\\bin\\mysql --user=root --password= dsmafornu");
                OutputStream  os = p.getOutputStream();
                //Hacemos una restauracion de un .sql que se 
                FileInputStream  fis = new FileInputStream("C:\\DeimosSoft\\DSMAFORNU\\RespaldoSinRegistros.sql");     
                byte[] buffer = new byte[1000];

                int  leido = fis.read(buffer);
                while (leido > 0) {
                    os.write(buffer, 0, leido);
                    leido = fis.read(buffer);
                }

                os.flush();
                os.close();
                fis.close();
                
                Rs.close();
                St.close();
                con.close();
                w.dispose();
                new Login().setVisible(true);
            }//else de que ya existe la base de datos
                 
              Rs.close();
              St.close();
              con.close();//nos aseguramos de cerrar la conexion, no es muy necesario pero es recomendable
              
            }//fin del try
            catch (Exception ex) {
              JOptionPane.showMessageDialog(null,""+ex);
              System.exit(0);//cualquier error que ocurra el programa debe cerrarse
            }                                      
  }
}
