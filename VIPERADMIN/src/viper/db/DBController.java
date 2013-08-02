package viper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBController {
	private static Connection con;
	// private static String userid = "root", password = "root";
	private static String userid = "root", password = "MariahFarah";
	// private static String url = "jdbc:mysql://localhost:3306/ISFCS-db";
	private static String url = "jdbc:mysql://192.168.180.128:3306/isfcs-db";

	private static PreparedStatement pstmt;

	public static Connection getConnection() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url, userid, password);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}

	public static ArrayList<Object[]> retrieveRegistrations() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT YEAR(userCreated) AS year, MONTH(userCreated) AS month, COUNT(*) AS count FROM user GROUP BY YEAR(userCreated), MONTH(userCreated) ASC LIMIT 10";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[3];
				     for (int i = 0; i < data.length; i++) {
				         data[i] = rs.getInt(i + 1);
				     }
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}

	public static ArrayList<Object[]> retrieveSession() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT YEAR(Date_Of_Attempt) AS year, MONTH(Date_Of_Attempt) AS month, COUNT(*) AS count FROM entrancerecord WHERE Outcome='1' GROUP BY YEAR(Date_Of_Attempt), MONTH(Date_Of_Attempt) ASC LIMIT 10;";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[3];
				     for (int i = 0; i < data.length; i++) {
				         data[i] = rs.getInt(i + 1);
				     }
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public static Object[][] to3dArray(ArrayList<Object[]> arr) {
		Object[][] threeDArr = null;
		
		if (arr.size() > 0) {
			int row = arr.size();
			int column = arr.get(0).length;
			threeDArr = new Object[row][column];
			
			for (int i=0; i<row; i++) {
				for (int j=0; j<column; j++) {
					threeDArr[i][j] = arr.get(i)[j];
				}
			}
			return threeDArr;
		}
		else {
			threeDArr = new Object[0][0];
			return threeDArr;
		}
	}
	
	public static ArrayList<Object[]> retrievePasswordFailure() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT Time_Of_Attempt AS time, Date_Of_Attempt AS date, username, userId, userSuspended FROM invalidattempts INNER JOIN user ON invalidattempts.UsernameUsed = user.username;";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[4];
				     data[0] = rs.getString("date") + " " + rs.getString("time");
				     data[1] = rs.getString("username");
				     data[2] = rs.getString("userId");
				     data[3] = rs.getBoolean("userSuspended");
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public static void updateSuspension(String userId, boolean userSuspended, String reason) {
		String sql;

		try {
			con = DBController.getConnection();

			try {
				sql = "UPDATE user SET " +
						"`userSuspended`=? WHERE `userId`=?;";

				pstmt = con.prepareStatement(sql);

				pstmt.setBoolean(1, userSuspended);
				pstmt.setString(2, userId);

				pstmt.executeUpdate();
				
				if (userSuspended == true) {
					sql = "INSERT INTO suspendeduser(`suspendedUserId`, `suspendedDate`, `suspendedReason`) " +
						"VALUES (?, ?, ?);";

					pstmt = con.prepareStatement(sql);

					pstmt.setString(1, userId);
					pstmt.setString(2, new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					pstmt.setString(3, reason);

					pstmt.executeUpdate();
				}
				else {
					sql = "DELETE FROM suspendeduser WHERE `suspendedUserId`=?;";

					pstmt = con.prepareStatement(sql);

					pstmt.setString(1, userId);

					pstmt.executeUpdate();
				}
				pstmt.close();
				con.close();
			} catch (SQLException s) {
				s.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Object[]> retrieveFaceRecFailure() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT Time_Of_Attempt AS time, Date_Of_Attempt AS date, username, userId, userSuspended FROM entrancerecord INNER JOIN user ON entrancerecord.userWhoLoggedIn = user.userId WHERE Outcome='0';";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[4];
				     data[0] = rs.getString("date") + " " + rs.getString("time");
				     data[1] = rs.getString("username");
				     data[2] = rs.getString("userId");
				     data[3] = rs.getBoolean("userSuspended");
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public static ArrayList<Object[]> retrieveIPAddress() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT Time_Of_Attempt AS time, Date_Of_Attempt AS date, username, userId, userSuspended, Description, COUNT(*) AS count FROM entrancerecord INNER JOIN user ON entrancerecord.userWhoLoggedIn = user.userId WHERE Outcome='1' GROUP BY Description ORDER BY Date_Of_Attempt DESC;";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[6];
				     data[0] = rs.getString("date") + " " + rs.getString("time");
				     data[1] = rs.getString("username");
				     data[2] = rs.getString("userId");
				     data[3] = rs.getString("Description").split("::")[1];
				     data[4] = rs.getInt("count");
				     data[5] = rs.getBoolean("userSuspended");
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public static ArrayList<Object[]> retrieveDwellTime() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT username, userId, userSuspended, Destination, STDDEV_POP(duration) AS deviation FROM accessingrecord INNER JOIN user ON accessingrecord.userWhoAccessed = user.userId GROUP BY userId, Destination ORDER BY userId, Destination, Date_Of_Accessing DESC;";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[5];
				     data[0] = rs.getString("username");
				     data[1] = rs.getString("userId");
				     data[2] = rs.getString("Destination");
				     data[3] = rs.getString("deviation");
				     data[4] = rs.getBoolean("userSuspended");
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	
	public static ArrayList<Object[]> retrieveTypingSpeed() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT username, userId, userSuspended, SUBSTRING_INDEX(Description,'::', 1) AS category, STDDEV_POP(SUBSTRING_INDEX(Description,'::', -1)) AS deviation FROM logrecord INNER JOIN user ON logrecord.userWhoPerformAction = user.userId WHERE Event='Typing Speed' GROUP BY userId, SUBSTRING_INDEX(Description,'::', 1) ORDER BY userId;";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[5];
				     data[0] = rs.getString("username");
				     data[1] = rs.getString("userId");
				     data[2] = rs.getString("category");
				     data[3] = rs.getString("deviation");
				     data[4] = rs.getBoolean("userSuspended");
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
	public static ArrayList<Object[]> retrieveSuspendedUser() {
		ResultSet rs = null;
		String sql;
		ArrayList<Object[]> arr = new ArrayList<Object[]>();

		try {
			con = DBController.getConnection();

			try {
				sql = "SELECT userId, username, suspendedDate, suspendedReason, suspendedResponse FROM user INNER JOIN suspendeduser ON user.userId = suspendeduser.suspendedUserId WHERE userSuspended='1';";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				     Object[] data = new Object[5];
				     data[0] = rs.getString("username");
				     data[1] = rs.getString("userId");
				     data[2] = rs.getString("suspendedDate");
				     data[3] = rs.getString("suspendedReason");
				     data[4] = rs.getString("suspendedResponse");
				     arr.add(data);
				}

				pstmt.close();
				con.close();
			} catch (SQLException s) {
				System.out.println("SQL statement is not executed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
}
