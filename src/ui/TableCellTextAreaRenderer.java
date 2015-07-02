package ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer { 
	private static final long serialVersionUID = 1L;

	public TableCellTextAreaRenderer() { 
		setLineWrap(true); 
		setOpaque(false);
	} 

	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		int maxPreferredHeight = table.getRowHeight(); 
		for (int i = 0; i < table.getColumnCount(); i++) { 
           setSize(table.getColumnModel().getColumn(column).getWidth(), 0); 
           maxPreferredHeight = Math.max(maxPreferredHeight, this.getPreferredSize().height); 
		} 

		if (table.getRowHeight(row) != maxPreferredHeight)
			table.setRowHeight(row, maxPreferredHeight);
       
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		}
		else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
       
		setText(value == null ? "" : value.toString()); 
		return this; 
	}
}