package viper.ui.main;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class LogAnalyser extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JTable LogViewer = null;
	private JButton jButtonFilter = null;
	private JLabel jLabel1 = null;
	private JComboBox SortBy = null;
	private JFrame mainFrame = null;
	private TableColumn column = null;  //  @jve:decl-index=0:
	private LogAnalyserController controller = null;
	private JComboBox logs = null;
	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	private JTextField Sorted = null;
	private JLabel jLabel3 = null;
	private JTextArea Description = null;
	
	/**
	 * This is the default constructor
	 */
	public LogAnalyser() {
		super();
		initialize();
	}
	
	public LogAnalyser(JFrame frame) {
		super();
		mainFrame = frame;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		jLabel3 = new JLabel();
		jLabel3.setFont(new Font("Dialog", Font.BOLD, 16));
		jLabel3.setLocation(new Point(30, 500));
		jLabel3.setSize(new Dimension(100, 25));
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel3.setText("Description: ");
		jLabel3.setForeground(Color.CYAN);
		jLabel2 = new JLabel();
		jLabel2.setText("Sorted By: ");
		jLabel2.setForeground(Color.CYAN);
		jLabel2.setSize(new Dimension(90, 25));
		jLabel2.setFont(new Font("Dialog", Font.BOLD, 16));
		jLabel2.setLocation(new Point(30, 400));
		jLabel = new JLabel();
		jLabel.setText("View logs: ");
		jLabel.setLocation(new Point(30, 300));
		jLabel.setForeground(Color.CYAN);
		jLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel.setSize(new Dimension(85, 25));
		controller = new LogAnalyserController();
		jLabel1 = new JLabel();
		jLabel1.setText("Sort By: ");
		jLabel1.setSize(new Dimension(75, 30));
		jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel1.setFont(new Font("Dialog", Font.BOLD, 16));
		jLabel1.setLocation(new Point(30, 450));
		jLabel1.setForeground(Color.CYAN);
		this.setSize(1000,700);
		this.setBackground(Color.black);
		this.setLayout(null);
		this.add(jLabel1, null);
		this.add(getLogs(), null);
		this.add(getJScrollPane(), null);
		this.add(getJButtonFilter(), null);
		this.add(getSortBy(), null);
		this.add(jLabel, null);
		this.add(jLabel2, null);
		this.add(getSorted(), null);
		this.add(jLabel3, null);
		this.add(getDescription(), null);
		loadApplicableEntranceLogs(0);
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setLocation(new Point(25, 25));
			jScrollPane.setSize(new Dimension(950, 250));
			jScrollPane.setViewportView(getLogViewer());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes LogViewer	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getLogViewer()
	{
		if (LogViewer == null) 
		{
			LogViewer = new JTable(controller.getDefaultForLogEntries());
		}
		
		return LogViewer;
	}
	
	private JTable getLogViewer(TableModel model)
	{
		LogViewer = new JTable(model);
		LogViewer.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int index = logs.getSelectedIndex();
				int row;
				
				if(index == 0 || index == 3)
				{
					row = LogViewer.getSelectedRow();
					
					Description.setText(controller.getDescription(row));
				}
			}
		});
		
		return LogViewer;
	}

	/**
	 * This method initializes jButtonFilter	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFilter() {
		if (jButtonFilter == null) {
			jButtonFilter = new JButton();
			jButtonFilter.setLocation(new Point(30, 350));
			jButtonFilter.setText("Filter");
			jButtonFilter.setSize(new Dimension(200, 25));
			jButtonFilter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TODO Auto-generated Event stub actionPerformed()\
					
					int chose = logs.getSelectedIndex();
					int sort = SortBy.getSelectedIndex();
					
					Sorted.setText("No Sorting");
					
					switch(chose)
					{
					case 0:
						loadApplicableEntranceLogs(sort);
						
						break;
						
					case 1:
						loadNonApplicableEntranceLogs(sort);
						
						break;
						
					case 2:
						loadAccessLogs(sort);
						
						break;
						
					case 3:
						loadLogs(sort);
						
						break;
					}
					
					Description.setText("");
					Sorted.setText((String)SortBy.getSelectedItem());
				}
			});
		}
		return jButtonFilter;
	}

	/**
	 * This method initializes SortBy	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getSortBy() {
		if (SortBy == null) {
			SortBy = new JComboBox();
			SortBy.setLocation(new Point(120, 450));
			SortBy.setSize(new Dimension(200, 30));
			
			SortBy.addItem("No Sorting");
			SortBy.addItem("Username");
			SortBy.addItem("Successful");
			SortBy.addItem("Login Time");
			SortBy.addItem("Login Date");
			SortBy.addItem("Logout Time");
			SortBy.addItem("Logout Date");
		}
		return SortBy;
	}

	/**
	 * This method initializes logs	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getLogs() {
		if (logs == null) {
			logs = new JComboBox();
			logs.setLocation(new Point(125, 300));
			logs.setPreferredSize(new Dimension(31, 20));
			logs.setSize(new Dimension(400, 30));
			logs.addItem("Login with existing usernames");
			logs.addItem("Login with non-existing usernames");
			logs.addItem("Where the user has visited");
			logs.addItem("What the user has done");
			
			logs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TODO Auto-generated Event stub actionPerformed()
					
					int chose = logs.getSelectedIndex();
					
					SortBy.removeAllItems();
					SortBy.addItem("No Sorting");
					
					switch(chose)
					{
					
					case 0:
						loadApplicableEntranceLogs(0);
						
						SortBy.addItem("Username");
						SortBy.addItem("Successful");
						SortBy.addItem("Login Time");
						SortBy.addItem("Login Date");
						SortBy.addItem("Logout Time");
						SortBy.addItem("Logout Date");
						
						break;
						
					case 1:
						loadNonApplicableEntranceLogs(0);
						
						SortBy.addItem("Username Used");
						SortBy.addItem("Time of Attempt");
						SortBy.addItem("Date of Attempt");
						
						break;
						
					case 2:
						loadAccessLogs(0);
						
						SortBy.addItem("User");
						SortBy.addItem("Access Time");
						SortBy.addItem("Access Date");
						SortBy.addItem("Destination");
						
						break;
						
					case 3:
						loadLogs(0);
						
						SortBy.addItem("User");
						SortBy.addItem("Event Time");
						SortBy.addItem("Event Date");
						SortBy.addItem("Event Category");
						SortBy.addItem("Event");
						SortBy.addItem("Successful");
						
						break;
					}
					
					Sorted.setText((String)SortBy.getSelectedItem());
				}
			});
		}
		return logs;
	}
	
	private void loadApplicableEntranceLogs(int sort)
	{
		try
		{
		controller.loadApplicableEntranceEntries(sort);
		getJScrollPane().setViewportView(getLogViewer(controller.getCustomForEntranceEntries()));
	
		column = LogViewer.getColumnModel().getColumn(0);
		column.setPreferredWidth(100);
	
		column = LogViewer.getColumnModel().getColumn(1);
		column.setPreferredWidth(140);
		
		column = LogViewer.getColumnModel().getColumn(2);
		column.setPreferredWidth(140);
		
		column = LogViewer.getColumnModel().getColumn(3);
		column.setPreferredWidth(140);
		
		column = LogViewer.getColumnModel().getColumn(4);
		column.setPreferredWidth(140);
		
		column = LogViewer.getColumnModel().getColumn(5);
		column.setPreferredWidth(140);
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadNonApplicableEntranceLogs(int sort)
	{
		try
		{
		controller.loadNonApplicableEntranceEntries(sort);
		getJScrollPane().setViewportView(getLogViewer(controller.getCustomForInvalidAttempts()));
		
		column = LogViewer.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
	
		column = LogViewer.getColumnModel().getColumn(1);
		column.setPreferredWidth(325);
		
		column = LogViewer.getColumnModel().getColumn(2);
		column.setPreferredWidth(325);
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadAccessLogs(int sort)
	{
		try
		{
		controller.loadAccessEntries(sort);
		getJScrollPane().setViewportView(getLogViewer(controller.getCustomForAccessEntries()));
		
		column = LogViewer.getColumnModel().getColumn(0);
		column.setPreferredWidth(100);
	
		column = LogViewer.getColumnModel().getColumn(1);
		column.setPreferredWidth(50);
		
		column = LogViewer.getColumnModel().getColumn(2);
		column.setPreferredWidth(50);
		
		column = LogViewer.getColumnModel().getColumn(3);
		column.setPreferredWidth(300);
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void loadLogs(int sort)
	{
		try
		{
		controller.loadLogEntries(sort);
		getJScrollPane().setViewportView(getLogViewer(controller.getCustomForLogEntries()));
		
		column = LogViewer.getColumnModel().getColumn(0);
		column.setPreferredWidth(100);
	
		column = LogViewer.getColumnModel().getColumn(1);
		column.setPreferredWidth(100);
		
		column = LogViewer.getColumnModel().getColumn(2);
		column.setPreferredWidth(100);
		
		column = LogViewer.getColumnModel().getColumn(3);
		column.setPreferredWidth(150);
		
		column = LogViewer.getColumnModel().getColumn(4);
		column.setPreferredWidth(300);
		
		column = LogViewer.getColumnModel().getColumn(5);
		column.setPreferredWidth(100);
		}
		
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes Sorted	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSorted() {
		if (Sorted == null) {
			Sorted = new JTextField();
			Sorted.setLocation(new Point(130, 400));
			Sorted.setEditable(false);
			Sorted.setText("No Sorting");
			Sorted.setHorizontalAlignment(JTextField.CENTER);
			Sorted.setSize(new Dimension(250, 25));
		}
		return Sorted;
	}

	/**
	 * This method initializes Description	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getDescription() {
		if (Description == null) {
			Description = new JTextArea();
			Description.setLocation(new Point(140, 500));
			Description.setLineWrap(true);
			Description.setEditable(false);
			Description.setSize(new Dimension(500, 150));
		}
		return Description;
	}
}
