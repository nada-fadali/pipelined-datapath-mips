package gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

import simulator.Program;

public class DPsimulator extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTable registers;
	private JTable memory;
	private JTable if_id;
	private JTable id_ex;
	private JTable ex_mem;
	private JTable mem_wb;
	private JTextArea codetxt;
	private JTextField dataAddresstxt;
	private JTextField dataMemtxt;
	private JTextField pcstarttxt;

	// engine
	private Program sim;
	private ArrayList<String> code;
	private ArrayList<String> data;
	private int pcstart;
	

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
		// gui
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Mips Datapath Simulator");
		setBounds(100, 100, 640, 568);
		this.initContentPane();
		this.initCodePanel();
		this.initInputDataPanel();
		this.initDataMemory();
		this.initRegisterFile();
		this.initButtons();
		this.initLblClock();
		this.initIFID();
		this.initIDEX();
		this.initEXMEM();
		this.initMEMWB();
		this.initLblPCsrc();

		// engine
		code = new ArrayList<String>();
		data = new ArrayList<String>();

	}

	private void initContentPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
	}

	private void initCodePanel() {
		JScrollPane codesp = new JScrollPane();
		GridBagConstraints gbc_codesp = new GridBagConstraints();
		gbc_codesp.gridheight = 9;
		gbc_codesp.gridwidth = 7;
		gbc_codesp.insets = new Insets(0, 0, 5, 5);
		gbc_codesp.fill = GridBagConstraints.BOTH;
		gbc_codesp.gridx = 0;
		gbc_codesp.gridy = 0;
		contentPane.add(codesp, gbc_codesp);

		codetxt = new JTextArea();
		codetxt.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		codetxt.setLineWrap(true);
		codesp.setViewportView(codetxt);
	}

	private void initInputDataPanel() {
		JLabel lblMemoryAddress = new JLabel("Memory Address");
		GridBagConstraints gbc_lblMemoryAddress = new GridBagConstraints();
		gbc_lblMemoryAddress.anchor = GridBagConstraints.EAST;
		gbc_lblMemoryAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblMemoryAddress.gridx = 0;
		gbc_lblMemoryAddress.gridy = 9;
		contentPane.add(lblMemoryAddress, gbc_lblMemoryAddress);

		dataAddresstxt = new JTextField();
		GridBagConstraints gbc_dataAddresstxt = new GridBagConstraints();
		gbc_dataAddresstxt.insets = new Insets(0, 0, 5, 5);
		gbc_dataAddresstxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataAddresstxt.gridx = 1;
		gbc_dataAddresstxt.gridy = 9;
		contentPane.add(dataAddresstxt, gbc_dataAddresstxt);
		dataAddresstxt.setColumns(10);

		JLabel label_1 = new JLabel("Data");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 9;
		contentPane.add(label_1, gbc_label_1);

		dataMemtxt = new JTextField();
		GridBagConstraints gbc_dataMemtxt = new GridBagConstraints();
		gbc_dataMemtxt.gridwidth = 2;
		gbc_dataMemtxt.insets = new Insets(0, 0, 5, 5);
		gbc_dataMemtxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataMemtxt.gridx = 3;
		gbc_dataMemtxt.gridy = 9;
		contentPane.add(dataMemtxt, gbc_dataMemtxt);
		dataMemtxt.setColumns(10);

		JButton btnAdd = new JButton("add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				data.add(dataAddresstxt.getText() + "/" + dataMemtxt.getText());
			}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 2;
		gbc_btnAdd.gridy = 10;
		contentPane.add(btnAdd, gbc_btnAdd);
		
		JLabel lblStartingAddress = new JLabel("Starting Address");
		GridBagConstraints gbc_lblStartingAddress = new GridBagConstraints();
		gbc_lblStartingAddress.anchor = GridBagConstraints.EAST;
		gbc_lblStartingAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartingAddress.gridx = 0;
		gbc_lblStartingAddress.gridy = 11;
		contentPane.add(lblStartingAddress, gbc_lblStartingAddress);
		
		pcstarttxt = new JTextField();
		GridBagConstraints gbc_pcstarttxt = new GridBagConstraints();
		gbc_pcstarttxt.insets = new Insets(0, 0, 5, 5);
		gbc_pcstarttxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_pcstarttxt.gridx = 1;
		gbc_pcstarttxt.gridy = 11;
		contentPane.add(pcstarttxt, gbc_pcstarttxt);
		pcstarttxt.setColumns(10);

	}

	private void initDataMemory() {
		// data memory
		// fill table with data
		memory = new JTable();
		memory.setShowHorizontalLines(true);
		memory.setShowVerticalLines(true);
		memory.setBorder(new LineBorder(new Color(0, 0, 0)));
		memory.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		memory.setEnabled(false);

		JScrollPane memsp = new JScrollPane(memory);
		memsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_memsp = new GridBagConstraints();
		gbc_memsp.gridwidth = 6;
		gbc_memsp.gridheight = 14;
		gbc_memsp.insets = new Insets(0, 0, 5, 5);
		gbc_memsp.fill = GridBagConstraints.BOTH;
		gbc_memsp.gridx = 7;
		gbc_memsp.gridy = 0;
		contentPane.add(memsp, gbc_memsp);
	}

	private void initRegisterFile() {
		// register file
		// fill table with data
		registers = new JTable();
		registers.setBorder(new LineBorder(new Color(0, 0, 0)));
		registers.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		registers.setEnabled(false);

		JScrollPane regsp = new JScrollPane(registers);
		regsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_regsp = new GridBagConstraints();
		gbc_regsp.gridheight = 36;
		gbc_regsp.fill = GridBagConstraints.BOTH;
		gbc_regsp.gridx = 13;
		gbc_regsp.gridy = 0;
		contentPane.add(regsp, gbc_regsp);
	}

	private void initButtons() {
		JButton btnRun = new JButton("run");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] tmp = codetxt.getText().split("\n");
				for(int i = 0; i < tmp.length; i++)
					code.add(i, tmp[0]);
				pcstart = Integer.parseInt(pcstarttxt.getText());
				
				// start simulator
				sim = new Program(code, data, pcstart);
				sim.run();
				
			}
		});
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.anchor = GridBagConstraints.EAST;
		gbc_btnRun.insets = new Insets(0, 0, 5, 5);
		gbc_btnRun.gridx = 0;
		gbc_btnRun.gridy = 12;
		contentPane.add(btnRun, gbc_btnRun);

		JButton btnNext = new JButton("next");
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 1;
		gbc_btnNext.gridy = 12;
		contentPane.add(btnNext, gbc_btnNext);

		JButton btnFinish = new JButton("finish");
		GridBagConstraints gbc_btnFinish = new GridBagConstraints();
		gbc_btnFinish.insets = new Insets(0, 0, 5, 5);
		gbc_btnFinish.gridx = 2;
		gbc_btnFinish.gridy = 12;
		contentPane.add(btnFinish, gbc_btnFinish);

		JButton btnStop = new JButton("stop");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStop.gridx = 3;
		gbc_btnStop.gridy = 12;
		contentPane.add(btnStop, gbc_btnStop);
	}

	private void initLblClock() {
		JLabel lblClock = new JLabel("Clock #");
		GridBagConstraints gbc_lblClock = new GridBagConstraints();
		gbc_lblClock.insets = new Insets(0, 0, 5, 5);
		gbc_lblClock.gridx = 2;
		gbc_lblClock.gridy = 13;
		contentPane.add(lblClock, gbc_lblClock);

		JLabel label = new JLabel("0");
		label.setForeground(Color.RED);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 3;
		gbc_label.gridy = 13;
		contentPane.add(label, gbc_label);
	}

	private void initIFID() {

		JLabel lblIfidRegister = new JLabel("IF/ID REGISTER");
		GridBagConstraints gbc_lblIfidRegister = new GridBagConstraints();
		gbc_lblIfidRegister.anchor = GridBagConstraints.WEST;
		gbc_lblIfidRegister.insets = new Insets(0, 0, 5, 5);
		gbc_lblIfidRegister.gridx = 0;
		gbc_lblIfidRegister.gridy = 14;
		contentPane.add(lblIfidRegister, gbc_lblIfidRegister);

		if_id = new JTable();
		if_id.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		if_id.setBorder(new LineBorder(new Color(0, 0, 0)));
		if_id.setEnabled(false);

		JScrollPane if_idsp = new JScrollPane(if_id);
		if_idsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_if_idsp = new GridBagConstraints();
		gbc_if_idsp.gridwidth = 13;
		gbc_if_idsp.insets = new Insets(0, 0, 5, 5);
		gbc_if_idsp.fill = GridBagConstraints.BOTH;
		gbc_if_idsp.gridx = 0;
		gbc_if_idsp.gridy = 17;
		contentPane.add(if_idsp, gbc_if_idsp);

	}

	private void initIDEX() {
		JLabel lblIdexRegister = new JLabel("ID/EX REGISTER");
		GridBagConstraints gbc_lblIdexRegister = new GridBagConstraints();
		gbc_lblIdexRegister.anchor = GridBagConstraints.WEST;
		gbc_lblIdexRegister.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdexRegister.gridx = 0;
		gbc_lblIdexRegister.gridy = 18;
		contentPane.add(lblIdexRegister, gbc_lblIdexRegister);

		id_ex = new JTable();
		id_ex.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		id_ex.setBorder(new LineBorder(new Color(0, 0, 0)));
		id_ex.setEnabled(false);

		JScrollPane id_exsp = new JScrollPane(id_ex);
		id_exsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_id_exsp = new GridBagConstraints();
		gbc_id_exsp.gridwidth = 13;
		gbc_id_exsp.insets = new Insets(0, 0, 5, 5);
		gbc_id_exsp.fill = GridBagConstraints.BOTH;
		gbc_id_exsp.gridx = 0;
		gbc_id_exsp.gridy = 19;
		contentPane.add(id_exsp, gbc_id_exsp);

	}

	private void initEXMEM() {
		JLabel lblExmemRegister = new JLabel("EX/MEM REGISTER");
		GridBagConstraints gbc_lblExmemRegister = new GridBagConstraints();
		gbc_lblExmemRegister.anchor = GridBagConstraints.WEST;
		gbc_lblExmemRegister.insets = new Insets(0, 0, 5, 5);
		gbc_lblExmemRegister.gridx = 0;
		gbc_lblExmemRegister.gridy = 20;
		contentPane.add(lblExmemRegister, gbc_lblExmemRegister);

		ex_mem = new JTable();
		ex_mem.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		ex_mem.setBorder(new LineBorder(new Color(0, 0, 0)));
		ex_mem.setEnabled(false);

		JScrollPane ex_memsp = new JScrollPane(ex_mem);
		ex_memsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_ex_memsp = new GridBagConstraints();
		gbc_ex_memsp.gridwidth = 13;
		gbc_ex_memsp.insets = new Insets(0, 0, 5, 5);
		gbc_ex_memsp.fill = GridBagConstraints.BOTH;
		gbc_ex_memsp.gridx = 0;
		gbc_ex_memsp.gridy = 21;
		contentPane.add(ex_memsp, gbc_ex_memsp);

	}

	private void initMEMWB() {
		JLabel lblMemwbRegister_1 = new JLabel("MEM/WB REGISTER");
		GridBagConstraints gbc_lblMemwbRegister_1 = new GridBagConstraints();
		gbc_lblMemwbRegister_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMemwbRegister_1.gridx = 0;
		gbc_lblMemwbRegister_1.gridy = 22;
		contentPane.add(lblMemwbRegister_1, gbc_lblMemwbRegister_1);

		mem_wb = new JTable();
		mem_wb.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		mem_wb.setBorder(new LineBorder(new Color(0, 0, 0)));
		mem_wb.setEnabled(false);

		JScrollPane mem_wbsp = new JScrollPane(mem_wb);
		mem_wbsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_mem_wbsp = new GridBagConstraints();
		gbc_mem_wbsp.gridheight = 7;
		gbc_mem_wbsp.gridwidth = 13;
		gbc_mem_wbsp.insets = new Insets(0, 0, 5, 5);
		gbc_mem_wbsp.fill = GridBagConstraints.BOTH;
		gbc_mem_wbsp.gridx = 0;
		gbc_mem_wbsp.gridy = 23;
		contentPane.add(mem_wbsp, gbc_mem_wbsp);

	}

	private void initLblPCsrc() {

		JLabel lblPcsrcSignal = new JLabel("PCSrc SIGNAL");
		GridBagConstraints gbc_lblPcsrcSignal = new GridBagConstraints();
		gbc_lblPcsrcSignal.anchor = GridBagConstraints.WEST;
		gbc_lblPcsrcSignal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPcsrcSignal.gridx = 0;
		gbc_lblPcsrcSignal.gridy = 30;
		contentPane.add(lblPcsrcSignal, gbc_lblPcsrcSignal);

		JLabel label_1 = new JLabel(" 0 ");
		label_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 31;
		contentPane.add(label_1, gbc_label_1);
	}

	// run frame
	public void run() {
		this.setVisible(true);
	}
}
