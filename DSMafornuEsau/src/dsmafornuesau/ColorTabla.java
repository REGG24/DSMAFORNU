
package dsmafornuesau;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ColorTabla extends DefaultTableCellRenderer {
private int columna ;

public ColorTabla(int Colpatron)
{
    this.columna = Colpatron;
}

@Override
public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
{        
    setBackground(Color.white);
    table.setForeground(Color.black);
    super.getTableCellRendererComponent(table, value, selected, focused, row, column);
    
    
    if(table.getValueAt(row,columna)!=null){
     if(Integer.parseInt((String)table.getValueAt(row,columna))<0)
     {
       this.setBackground(Color.RED);
     }
     else if(Integer.parseInt((String)table.getValueAt(row,columna))==0)
     {
       this.setBackground(Color.YELLOW);
     }
    }   
    return this;
  }
  }
