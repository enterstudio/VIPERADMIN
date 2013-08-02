package viper.ui.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import viper.db.SecureDatabaseConnector;

public class LogAnalyserController
{
	private FileTableModelForEntranceEntries ftmfee = null;
	private FileTableModelForAccessEntries ftmfae = null;
	private FileTableModelForLogEntries ftmfle = null;
	private FileTableModelForInvalidAttempts ftmfia = null;
	private SecureDatabaseConnector sdc = null;
	private ResultSet rs = null;
	private int rowCount;
	private Object data[][];
	private String description;
	
	public LogAnalyserController()
	{
		super();
		
		ftmfee = new FileTableModelForEntranceEntries();
		ftmfae = new FileTableModelForAccessEntries();
		ftmfle = new FileTableModelForLogEntries();
		ftmfia = new FileTableModelForInvalidAttempts();
		
		sdc = new SecureDatabaseConnector("jdbc:mysql://192.168.180.128:3306/project");
	}
	
	public String getDescription(int index)
	{
		return (String)data[index][data[0].length - 1];
	}
	
	public FileTableModelForEntranceEntries getDefaultForEntranceEntries()
	{
		return new FileTableModelForEntranceEntries();
	}
	
	public FileTableModelForEntranceEntries getCustomForEntranceEntries()
	{
		if(ftmfee == null)
		{
			return getDefaultForEntranceEntries();
		}
		
		return ftmfee;
	}
	
	public FileTableModelForAccessEntries getDefaultForAccessEntries()
	{
		return new FileTableModelForAccessEntries();
	}
	
	public FileTableModelForAccessEntries getCustomForAccessEntries()
	{
		if(ftmfae == null)
		{
			return getDefaultForAccessEntries();
		}
		
		return ftmfae;
	}
	
	public FileTableModelForLogEntries getDefaultForLogEntries()
	{
		return new FileTableModelForLogEntries();
	}
	
	public FileTableModelForLogEntries getCustomForLogEntries()
	{
		if(ftmfle == null)
		{
			return getDefaultForLogEntries();
		}
		
		return ftmfle;
	}
	
	public FileTableModelForInvalidAttempts getDefaultForInvalidAttempts()
	{
		return new FileTableModelForInvalidAttempts();
	}
	
	public FileTableModelForInvalidAttempts getCustomForInvalidAttempts()
	{
		if(ftmfia == null)
		{
			return getDefaultForInvalidAttempts();
		}
		
		return ftmfia;
	}
	
	public void load(String sqlCommand, String[] values) throws SQLException
	{	
		sdc.setPreparedStatement(sqlCommand, values);
		sdc.performReadAction();
		
		rs = sdc.getResultSet();
		
		rs.last();
		
		rowCount = rs.getRow();
		
		rs.first();
	}
	
	public void loadApplicableEntranceEntries(int sort) throws SQLException
	{
		String column = sortBy(1, sort);
		String description = "User Username viewed valid login attempts sorted in " + column;
		boolean outcome = true;
		
		System.out.println(column);
		
		try
		{
			sdc.connectToDatabase();
			
			String sqlCommand = "SELECT ER.*, U.Username " +
								"FROM EntranceRecord ER " +
								"JOIN User U " +
								"ON ER.userWhoLoggedIN = U.userID " +
								"ORDER BY ER." + column + " DESC";
			
			load(sqlCommand, null);
			
			data = new Object[rowCount][8];
			
			for(int i = 0; i < rowCount; i++)
			{
					
					String placeholder = "Failed";
					
					
					
					data[i][0] = rs.getString("Username");
					
					if(rs.getInt("Outcome") == 1)
					{
						placeholder = "Successful";
					}
					
					data[i][1] = placeholder;
					data[i][2] = rs.getString("Time_Of_Attempt");
					data[i][3] = rs.getString("Date_Of_Exit");
					data[i][4] = rs.getString("Time_Of_Exit");
					data[i][5] = rs.getString("Date_Of_Exit");
					data[i][6] = rs.getString("Entrance_ID");
					data[i][7] = rs.getString("Description");
				
					
				rs.next();
			}
			
			ftmfee = new FileTableModelForEntranceEntries(data);
		}
		
		catch(SQLException e)
		{
			throw e;
		}
		
		finally
		{	
			sdc.closeConnection();
		}
	}
	
	public void loadNonApplicableEntranceEntries(int sort) throws SQLException
	{
		try
		{
			sdc.connectToDatabase();
			
			String sqlCommand = "SELECT * " +
								"FROM InvalidAttempts " +
								"ORDER BY " + sortBy(2, sort) + " DESC";
			
			load(sqlCommand, null);
			
			data = new Object[rowCount][4];
			
			for(int i = 0; i < rowCount; i++)
			{		
					
					data[i][0] = rs.getString("UsernameUsed");
					data[i][1] = rs.getString("Time_Of_Attempt");
					data[i][2] = rs.getString("Time_Of_Exit");
					data[i][3] = rs.getString("InvalidAttemptsID");
				
				rs.next();
			}
			
			ftmfia = new FileTableModelForInvalidAttempts(data);
		}
		
		catch(SQLException e)
		{
			throw e;
		}
		
		finally
		{
			sdc.closeConnection();
		}
	}
	
	public void loadAccessEntries(int sort) throws SQLException
	{
		try
		{
			String column = sortBy(3, sort);
			sdc.connectToDatabase();
			
			String sqlCommand = "SELECT a.*, u.Username " +
								"FROM AccessingRecord a " + 
								"INNER JOIN EntranceRecord e ON a.Entrance_ID = e.Entrance_ID " +
								"INNER JOIN User u ON a.userWhoAccessed = u.userID " +
								"ORDER BY " + column + " DESC";
			
			System.out.println(column);
			
			load(sqlCommand, null);
			
			data = new Object[rowCount][5];
			
			for(int i = 0; i < rowCount; i++)
			{
				
				data[i][0] = rs.getString("Username");
				data[i][1] = rs.getString("Time_Of_Accessing");
				data[i][2] = rs.getString("Date_Of_Accessing");
				data[i][3] = rs.getString("Destination");
				data[i][4] = rs.getString("Accessing_ID");
				
				rs.next();
			}
			
			ftmfae = new FileTableModelForAccessEntries(data);
		}
		
		catch(SQLException e)
		{
			throw e;
		}
		
		finally
		{
			sdc.closeConnection();
		}
	}
	
	public void loadLogEntries(int sort) throws SQLException
	{	
		try
		{
			String column = sortBy(4, sort);
			sdc.connectToDatabase();
			
			String sqlCommand = "SELECT l.*, u.username " +
			"FROM logrecord l " + 
			"INNER JOIN AccessingRecord a ON l.Accessing_ID = a.Accessing_ID " +
			"INNER JOIN user u ON l.UserWhoPerformAction = u.UserID " +
			"ORDER BY " + column + " DESC";
			
			System.out.println(column);
			
			load(sqlCommand, null);
			
			data = new Object[rowCount][8];
			
			for(int i = 0; i < rowCount; i++)
			{
				String successful = "Failed";
				
				
				data[i][0] = rs.getString("Username");
				data[i][1] = rs.getString("Time_Of_Event");
				data[i][2] = rs.getString("Date_Of_Event");;
				data[i][3] = rs.getString("Event_Category");
				data[i][4] = rs.getString("Event");
				
				if(rs.getInt("Outcome") == 1)
				{
					successful = "Successful";
				}
				
				data[i][5] = successful;
				
				data[i][6] = rs.getString("Log_ID");
				
				data[i][7] = rs.getString("Description");
				
				rs.next();
			}
			
			ftmfle = new FileTableModelForLogEntries(data);
		}
		
		catch(SQLException e)
		{
			throw e;
		}
		
		finally
		{
			sdc.closeConnection();
		}
	}
	
	private String sortBy(int logs, int chose)
	{
		String column = null;
		
		switch(logs)
		{
		case 1:
			
			switch(chose)
			{
			case 0:
				column = "Entrance_ID";
				
				break;
				
			case 1:
				column = "Username";
				
				break;
				
			case 2:
				column = "Outcome";
				
				break;
				
			case 3:
				column = "Time_Of_Attempt";
				
				break;
				
			case 4:
				column = "Date_Of_Attempt";
				
				break;
				
			case 5:
				column = "Time_Of_Exit";
				
				break;
				
			case 6:
				column = "Date_Of_Exit";
				
				break;
				
			default:
				column = "Entrance_ID";	
				
				break;
			}
			
			break;
			
		case 2:
			
			switch(logs)
			{
			case 1:
				
				switch(chose)
				{
				case 0:
					column = "InvalidAttemptsID";
					
					break;
					
				case 1:
					column = "Time_Of_Attempt";
					
					break;
					
				case 2:
					column = "Date_Of_Attempt";
					
				default:
					column = "InvalidAttemptsID";	
					
					break;
				}
			
			break;
			
		case 3:
			
			switch(chose)
			{
			case 0:
				column = "Accessing_ID";
				
				break;
			
			case 1:
				column = "Username";
				
				break;
				
			case 2:
				column = "Time_of_Accessing";
				
				break;
				
			case 3:
				column = "Date_of_Accessing";
				
				break;
				
			case 4:
				column = "Destination";
				
				break;
				
			default:
				column = "Accessing_ID";	
				
				break;
			}
			
			break;
			
		case 4:
			
			switch(chose)
			{
			case 0:
				column = "Log_ID";
				
				break;
			
			case 1:
				column = "Username";
				
				break;
				
			case 2:
				column = "Time_of_Event";
				
				break;
				
			case 3:
				column = "Date_Of_Event";
				
				break;
				
			case 4:
				column = "Category";
				break;
				
			case 5:
				column = "Event";
				break;
				
			case 6:
				column = "Outcome";
				break;
				
			default:
				column = "Log_ID";	
				
				break;
			}
			
			break;
		}
		}
		return column;
	}
}
