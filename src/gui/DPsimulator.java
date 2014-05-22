package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class DPsimulator extends JFrame {

	private JPanel contentPane;
	private JTable registers;
	private JTable memory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DPsimulator frame = new DPsimulator();
					frame.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DPsimulator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mips Datapath Simulator");
		setBounds(100, 100, 640, 568); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JScrollPane codesp = new JScrollPane();
		GridBagConstraints gbc_codesp = new GridBagConstraints();
		gbc_codesp.gridheight = 3;
		gbc_codesp.gridwidth = 11;
		gbc_codesp.insets = new Insets(0, 0, 5, 5);
		gbc_codesp.fill = GridBagConstraints.BOTH;
		gbc_codesp.gridx = 0;
		gbc_codesp.gridy = 0;
		contentPane.add(codesp, gbc_codesp);
		
		JTextArea codetxt = new JTextArea();
		codetxt.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		codetxt.setLineWrap(true);
		codesp.setViewportView(codetxt);
		
		// data memory
		// fill table with data
		memory = new JTable();
		memory.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		memory.setEnabled(false);
		
		JScrollPane memsp = new JScrollPane(memory);
		GridBagConstraints gbc_memsp = new GridBagConstraints();
		gbc_memsp.gridwidth = 2;
		gbc_memsp.gridheight = 4;
		gbc_memsp.insets = new Insets(0, 0, 5, 5);
		gbc_memsp.fill = GridBagConstraints.BOTH;
		gbc_memsp.gridx = 11;
		gbc_memsp.gridy = 0;
		contentPane.add(memsp, gbc_memsp);

		// register file
		// fill table with data
		registers = new JTable();
		registers.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		registers.setEnabled(false);
		
		JScrollPane regsp = new JScrollPane(registers);
		regsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_regsp = new GridBagConstraints();
		gbc_regsp.gridheight = 16;
		gbc_regsp.fill = GridBagConstraints.BOTH;
		gbc_regsp.gridx = 13;
		gbc_regsp.gridy = 0;
		contentPane.add(regsp, gbc_regsp);
		
		JButton btnSimulate = new JButton("simulate");
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		JButton btnClear = new JButton("clear");
		btnClear.setFont(new Font("Dialog", Font.BOLD, 10));
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.insets = new Insets(0, 0, 5, 5);
		gbc_btnClear.gridx = 9;
		gbc_btnClear.gridy = 3;
		contentPane.add(btnClear, gbc_btnClear);
		btnSimulate.setFont(new Font("Dialog", Font.BOLD, 10));
		GridBagConstraints gbc_btnSimulate = new GridBagConstraints();
		gbc_btnSimulate.insets = new Insets(0, 0, 5, 5);
		gbc_btnSimulate.gridx = 10;
		gbc_btnSimulate.gridy = 3;
		contentPane.add(btnSimulate, gbc_btnSimulate);
		
		JScrollPane pipeline = new JScrollPane();
		GridBagConstraints gbc_pipeline = new GridBagConstraints();
		gbc_pipeline.gridheight = 12;
		gbc_pipeline.gridwidth = 13;
		gbc_pipeline.insets = new Insets(0, 0, 0, 5);
		gbc_pipeline.fill = GridBagConstraints.BOTH;
		gbc_pipeline.gridx = 0;
		gbc_pipeline.gridy = 4;
		contentPane.add(pipeline, gbc_pipeline);
	}
	
	// run frame
	public void run(){
		this.setVisible(true);
	}
}
