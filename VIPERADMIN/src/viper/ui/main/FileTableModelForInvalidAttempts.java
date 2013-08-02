package viper.ui.main;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class FileTableModelForInvalidAttempts extends AbstractTableModel {
	private String[] columnNames = {"Username Used", "Time Of Attempt","Date Of Attempt"};
	private Object[][] data = {{"Username Used", "Time Of Attempt","Date Of Attempt"}};
	
	private TableColumn column = null;
	
	public FileTableModelForInvalidAttempts()
	{
		super();
	}
	
	public FileTableModelForInvalidAttempts(Object[][] data)
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
