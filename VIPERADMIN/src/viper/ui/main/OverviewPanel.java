package viper.ui.main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OverviewPanel extends JPanel {

	private static JFrame frame = null;
	private JLabel jLabelPlaceHolder;
	
	/**
	 * Create the panel.
	 */
	public OverviewPanel() {
		super();
		initialize();
	}

	public OverviewPanel(JFrame f) {
		super();
		frame = f;
		initialize();
	}

	private void initialize() {
		
		jLabelPlaceHolder = new JLabel();
		jLabelPlaceHolder.setText("Overview Panel");
		jLabelPlaceHolder.setForeground(Color.gray);
		jLabelPlaceHolder.setBounds(400, 300, 200, 30);
		
		this.setSize(1000, 700);
		this.setLayout(null);
		this.setBackground(Color.black);

		this.add(jLabelPlaceHolder);

	}
}