package viper.ui.main;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import viper.ui.behavior.DwellTimePanel;
import viper.ui.behavior.FaceRecFailurePanel;
import viper.ui.behavior.IPAddressPanel;
import viper.ui.behavior.PasswordFailurePanel;
import viper.ui.behavior.TypingSpeedPanel;
import viper.ui.program.SessionPanel;
import viper.ui.program.RegistrationPanel;
import viper.ui.user.SuspendedUserPanel;

public class MainFrame extends JFrame {

	private static JPanel contentPane = null;
	private static MainFrame frame;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		
		menuBar = new JMenuBar();
		
		// Overview
		menu = new JMenu("Overview");
		menu.getAccessibleContext().setAccessibleDescription(
		        "Overview");
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new LogAnalyser(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menuBar.add(menu);
		
		// Program stats
		menu = new JMenu("Program stats");
		menu.getAccessibleContext().setAccessibleDescription(
		        "Program stats");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Registration");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new RegistrationPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Session");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Session");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new SessionPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);

		// Behavior stats
		menu = new JMenu("Behavior stats");
		menu.getAccessibleContext().setAccessibleDescription(
		        "Behavior stats");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Password Failure");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Password Failure");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new PasswordFailurePanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Face Recognition Failure");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Face Recognition Failure");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new FaceRecFailurePanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);
		
		menuItem = new JMenuItem("IP Addresses");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"IP Addresses");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new IPAddressPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Dwell Time");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Dwell Time");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new DwellTimePanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Typing Speed");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Typing Speed");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new TypingSpeedPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);
		
		// Monitor users
		menu = new JMenu("Monitor users");
		menu.getAccessibleContext().setAccessibleDescription(
		        "Monitor users");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Suspended Users");
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Suspended Users");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = new SuspendedUserPanel(frame);
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel);
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
			}
        });
		menu.add(menuItem);

		this.setJMenuBar(menuBar);
		
		/*try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.getLookAndFeelDefaults()
			        .put("defaultFont", new Font("Trebuchet MS", Font.PLAIN, 16));
					break;
				}
			}
		} catch (Exception e) {

		}*/
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getContentPane());
		this.setBounds(10, 10, 1000, 700);
		this.setResizable(false);
		this.setTitle("VIPER ADMIN");
		this.setVisible(false);
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getContentPane() {
		if (contentPane == null) {
			contentPane = new JPanel();
			contentPane.setLayout(null);
		}
		return contentPane;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		frame = new MainFrame();
		JPanel panel = new LogAnalyser(frame);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

}
