package viper.ui.main;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class FileTableModelForAccessEntries extends AbstractTableModel
{
	private String[] columnNames = {"User","Access Time", "Access Date","Destination"};
	private Object[][] data = {{"User","Access Time", "Access Date", "Accessing"}};
	private TableColumn column = null;
	
	public FileTableModelForAccessEntries()
	{
		super();
	}
	
	public FileTableModelForAccessEntries(Object[][] data)
	{
		super();
		this.data = data;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}
	
	public String getColumnName(int col)
	{
        return columnNames[col].toString();
    }

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return data[arg0][arg1];
	}
	
}
