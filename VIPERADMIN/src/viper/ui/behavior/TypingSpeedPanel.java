package viper.ui.behavior;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import viper.db.DBController;

public class TypingSpeedPanel extends JPanel {

	private static JFrame frame = null;
	private JScrollPane scrollPane;
	private JTable table;
	private Object[][] data = DBController.to3dArray(DBController.retrieveTypingSpeed());
	
	/**
	 * Create the panel.
	 */
	public TypingSpeedPanel() {
		super();
		initialize();
	}

	public TypingSpeedPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {
		table = new JTable(new MyTableModel());
        //table.setPreferredScrollableViewportSize(new Dimension(900, 600));
        table.setFillsViewportHeight(true);
        //table.setSize(800, 500);
        //table.getSelectionModel().addListSelectionListener(new RowListener());
        //table.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }

                
                int rowindex = table.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = new JPopupMenu();
                	JMenuItem menuItem;
                	menuItem = new JMenuItem("Suspend User");
            		menuItem.addActionListener(new ActionListener() {
            			@Override
            			public void actionPerformed(ActionEvent arg0) {
            				// suspend user
            				DBController.updateSuspension(data[table.getSelectedRow()][1].toString(), true, "Typing Speed");
            				JPanel panel = new TypingSpeedPanel(frame);
            				frame.getContentPane().removeAll();
            				frame.getContentPane().add(panel);
            				frame.getContentPane().validate();
            				frame.getContentPane().repaint();
            			}
                    });
                    popup.add(menuItem);
                	menuItem = new JMenuItem("Unsuspend User");
            		menuItem.addActionListener(new ActionListener() {
            			@Override
            			public void actionPerformed(ActionEvent arg0) {
            				// suspend user
            				DBController.updateSuspension(data[table.getSelectedRow()][1].toString(), false, null);
            				JPanel panel = new TypingSpeedPanel(frame);
            				frame.getContentPane().removeAll();
            				frame.getContentPane().add(panel);
            				frame.getContentPane().validate();
            				frame.getContentPane().repaint();
            			}
                    });
                    popup.add(menuItem);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 15, 970, 500);
        
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

        this.add(scrollPane);

	}
	
	class MyTableModel extends AbstractTableModel {
       private String[] columnNames = {"Username",
                                        "UserId",
                                        "Cateory",
                                        "Typing Speed Deviation",
                                        "Suspended"};
       
       Object[][] data = DBController.to3dArray(DBController.retrieveTypingSpeed());
       
        /*private Object[][] data = {
	    {"Kathy", "Smith",
	     "Snowboarding", new Integer(5), new Boolean(false)},
	    {"John", "Doe",
	     "Rowing", new Integer(3), new Boolean(true)},
	    {"Sue", "Black",
	     "Knitting", new Integer(2), new Boolean(false)},
	    {"Jane", "White",
	     "Speed reading", new Integer(20), new Boolean(true)},
	    {"Joe", "Brown",
	     "Pool", new Integer(10), new Boolean(false)}
        };*/

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        /*public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }*/

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }
}