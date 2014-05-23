package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

import simulator.Program;

import java.awt.SystemColor;


public class DPsimulator extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable registers;
	private JTable memory;
	private JTable if_id;
	private JTable id_ex;
	private JTable ex_mem;
	private JTable mem_wb;
	private JTextPane codetxt;
	private JTextField dataAddresstxt;
	private JTextField dataMemtxt;
	private JTextField pcstarttxt;
	private JLabel lblpcsrc;
	private JLabel lblclk;

	// ==================== engine ======================
	private Program sim;
	private ArrayList<String> code;
	private ArrayList<String> data;
	private int pcstart;

	private int clock;
	public static boolean reset = false;

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
		this.setTitle("Mips Datapath Simulator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1140, 628);
		this.setResizable(false);
		this.initContentPane();
		
		init();
	}

	public void init() {
		initCodePanel();
		initInputDataPanel();
		initDataMemory();
		initRegisterFile();
		initButtons();
		initLblClock();
		initIFID();
		initIDEX();
		initEXMEM();
		initMEMWB();
		initLblPCsrc();

		// engine
		code = new ArrayList<String>();
		data = new ArrayList<String>();
		clock = 0;
	}
	private void initContentPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 1.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0,
				1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
	}

	// =================================================
	// helper method for coloring keywords in text pane
	final StyleContext cont = StyleContext.getDefaultStyleContext();
	final AttributeSet attr = cont.addAttribute(cont.getEmptySet(),
			StyleConstants.Foreground, Color.RED);
	final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(),
			StyleConstants.Foreground, Color.BLACK);
	private int findLastNonWordChar(String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
		}
		return index;
	}
	private int findFirstNonWordChar(String text, int index) {
		while (index < text.length()) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
			index++;
		}
		return index;
	}
	DefaultStyledDocument doc = new DefaultStyledDocument() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {
			super.insertString(offset, str, a);

			String text = getText(0, getLength());
			int before = findLastNonWordChar(text, offset);
			if (before < 0)
				before = 0;
			int after = findFirstNonWordChar(text, offset + str.length());
			int wordL = before;
			int wordR = before;

			while (wordR <= after) {
				if (wordR == after
						|| String.valueOf(text.charAt(wordR)).matches("\\W")) {
					if (text.substring(wordL, wordR).matches(
							"(\\W)*(add|addi|sub|lw|sw|sll|srl|and|andi|or|ori|nor|beq|bne|j|jal|jr|slt|sltu)"))
						setCharacterAttributes(wordL, wordR - wordL, attr,
								false);
					else
						setCharacterAttributes(wordL, wordR - wordL, attrBlack,
								false);
					wordL = wordR;
				}
				wordR++;
			}
		}

		public void remove(int offs, int len) throws BadLocationException {
			super.remove(offs, len);

			String text = getText(0, getLength());
			int before = findLastNonWordChar(text, offs);
			if (before < 0)
				before = 0;
			int after = findFirstNonWordChar(text, offs);

			if (text.substring(before, after).matches(
					"(\\W)*(add|addi|sub|lw|sw|sll|srl|and|andi|or|ori|nor|beq|bne|j|jal|jr|slt|sltu)")) {
				setCharacterAttributes(before, after - before, attr, false);
			} else {
				setCharacterAttributes(before, after - before, attrBlack, false);
			}
		}
	};
	// =================================================

	private void initCodePanel() {
		JScrollPane codesp = new JScrollPane();
		GridBagConstraints gbc_codesp = new GridBagConstraints();
		gbc_codesp.anchor = GridBagConstraints.EAST;
		gbc_codesp.gridheight = 14;
		gbc_codesp.gridwidth = 8;
		gbc_codesp.insets = new Insets(0, 0, 5, 5);
		gbc_codesp.fill = GridBagConstraints.BOTH;
		gbc_codesp.gridx = 0;
		gbc_codesp.gridy = 0;
		contentPane.add(codesp, gbc_codesp);

		codetxt = new JTextPane(doc);
		codetxt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
					codetxt.setText(codetxt.getText().substring(0,
							codetxt.getText().length()));
			}
		});
		codetxt.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		codesp.setViewportView(codetxt);
	}

	private void initInputDataPanel() {

		JLabel lblStartingAddress = new JLabel("Starting Address");
		GridBagConstraints gbc_lblStartingAddress = new GridBagConstraints();
		gbc_lblStartingAddress.gridwidth = 5;
		gbc_lblStartingAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartingAddress.gridx = 8;
		gbc_lblStartingAddress.gridy = 5;
		contentPane.add(lblStartingAddress, gbc_lblStartingAddress);

		pcstarttxt = new JTextField();
		GridBagConstraints gbc_pcstarttxt = new GridBagConstraints();
		gbc_pcstarttxt.gridwidth = 5;
		gbc_pcstarttxt.insets = new Insets(0, 0, 5, 5);
		gbc_pcstarttxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_pcstarttxt.gridx = 8;
		gbc_pcstarttxt.gridy = 6;
		contentPane.add(pcstarttxt, gbc_pcstarttxt);
		pcstarttxt.setColumns(10);
		JLabel lblMemoryAddress = new JLabel("Memory Address");
		GridBagConstraints gbc_lblMemoryAddress = new GridBagConstraints();
		gbc_lblMemoryAddress.gridwidth = 5;
		gbc_lblMemoryAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblMemoryAddress.gridx = 8;
		gbc_lblMemoryAddress.gridy = 7;
		contentPane.add(lblMemoryAddress, gbc_lblMemoryAddress);

		dataAddresstxt = new JTextField();
		GridBagConstraints gbc_dataAddresstxt = new GridBagConstraints();
		gbc_dataAddresstxt.gridwidth = 5;
		gbc_dataAddresstxt.insets = new Insets(0, 0, 5, 5);
		gbc_dataAddresstxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataAddresstxt.gridx = 8;
		gbc_dataAddresstxt.gridy = 8;
		contentPane.add(dataAddresstxt, gbc_dataAddresstxt);
		dataAddresstxt.setColumns(10);

		JLabel label_1 = new JLabel("Data");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.gridwidth = 5;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 8;
		gbc_label_1.gridy = 9;
		contentPane.add(label_1, gbc_label_1);

		dataMemtxt = new JTextField();
		GridBagConstraints gbc_dataMemtxt = new GridBagConstraints();
		gbc_dataMemtxt.gridwidth = 5;
		gbc_dataMemtxt.insets = new Insets(0, 0, 5, 5);
		gbc_dataMemtxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataMemtxt.gridx = 8;
		gbc_dataMemtxt.gridy = 10;
		contentPane.add(dataMemtxt, gbc_dataMemtxt);
		dataMemtxt.setColumns(10);

		JButton btnAdd = new JButton("add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!dataAddresstxt.getText().equals("") && !dataMemtxt.getText().equals("")){
					data.add(dataAddresstxt.getText() + "/" + dataMemtxt.getText());
					dataAddresstxt.setText("");
					dataMemtxt.setText("");
				}
				else
					JOptionPane.showMessageDialog(contentPane, "Please type in the text box the data you need to store in the memory");
			}
		});
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.gridwidth = 5;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 8;
		gbc_btnAdd.gridy = 11;
		contentPane.add(btnAdd, gbc_btnAdd);

	}

	private void initDataMemory() {
		// fill table with data
		String[] col = { "Addr", "Dec", "Hex" };
		String[][] row = { { "0", "0", "0" } };
		DefaultTableModel mdl = new DefaultTableModel(row, col);
		memory = new JTable(mdl);
		memory.setShowHorizontalLines(true);
		memory.setShowVerticalLines(true);
		memory.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 2));
		memory.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		memory.setEnabled(false);

		JScrollPane memsp = new JScrollPane(memory);
		memsp.setBorder(null);
		GridBagConstraints gbc_memsp = new GridBagConstraints();
		gbc_memsp.gridwidth = 6;
		gbc_memsp.gridheight = 16;
		gbc_memsp.insets = new Insets(0, 0, 5, 5);
		gbc_memsp.fill = GridBagConstraints.BOTH;
		gbc_memsp.gridx = 13;
		gbc_memsp.gridy = 0;
		contentPane.add(memsp, gbc_memsp);
	}

	private void initRegisterFile() {
		// fill table with data
		String[] col = { "Reg", "Dec", "Hex" };
		String[][] row = new String[34][3];
		for (int i = 0; i < 32; i++) {
			row[i][0] = "" + i;
			row[i][1] = "0";
			row[i][2] = "0";
		}
		row[32][0] = "Ra";
		row[32][1] = "0";
		row[32][2] = "0";
		row[33][0] = "PC";
		row[33][1] = "0";
		row[33][2] = "0";
		
		DefaultTableModel mdl = new DefaultTableModel(row, col);
		registers = new JTable(mdl);
		registers.setEnabled(false);
		registers.setBorder(new LineBorder(SystemColor.activeCaptionBorder, 2));
		registers.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		JScrollPane regsp = new JScrollPane(registers);
		regsp.setBorder(null);
		GridBagConstraints gbc_regsp = new GridBagConstraints();
		gbc_regsp.gridheight = 40;
		gbc_regsp.fill = GridBagConstraints.BOTH;
		gbc_regsp.gridx = 19;
		gbc_regsp.gridy = 0;
		contentPane.add(regsp, gbc_regsp);
	}

	private void initButtons() {
		JButton btnRun = new JButton("run");
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!codetxt.getText().equals("")){
					String[] tmp = codetxt.getText().split("\n");
					//System.out.println(codetxt.getText());
					for (int i = 0; i < tmp.length; i++)
						code.add(i, tmp[0]);
					pcstart = (pcstarttxt.getText().equals("")) ? 0 : Integer
							.parseInt(pcstarttxt.getText());

					// start simulator
					sim = new Program(code, data, pcstart);
					sim.run();

					lblclk.setText(" 1 ");
					String[] content = sim.getCyle(clock); // repaint data
					
					//	register file
					tmp = content[0].split(" ");
					String[][] row = new String[34][3];
					int i = 0;
					for(int j = 0; j < 32; j++){
						row[j][0] = "" + j; row[j][1] = tmp[i]; row[j][2] = tmp[i+1]; i+=2;
					}
					row[32][0] = "Ra"; row[32][1] = tmp[i]; row[32][2] = tmp[i+1]; i+=2;
					row[33][0] = "PC"; row[33][1] = tmp[i]; row[33][2] = tmp[i+1];
					String[] col = { "Reg", "Dec", "Hex" };
					
					DefaultTableModel mdl = new DefaultTableModel(row, col);
					registers.setModel(mdl);
					
					//	data memory
					if(!content[1].equals("")){
						tmp = content[1].split(" ");
						String[] colm = { "Addr", "Dec", "Hex" };
						String[][] rowm = new String[tmp.length/3][3];
						i = 0;
						for(int k = 0; k < tmp.length; k+=3){
							rowm[i][0] = tmp[k]; rowm[i][1] = tmp[k+1]; rowm[i][2] = tmp[k+2]; i++;
						}
						DefaultTableModel mdlm = new DefaultTableModel(rowm, colm);
						memory.setModel(mdlm);
					}
					
					//	pcsrc
					lblpcsrc.setText(" " + content[2] + " ");
					
					//	ifid
					//System.out.println(content[3]);
					tmp = content[3].split(" ");
					String s = "";
					for(int k = 1; k < tmp.length; k++){
						s+= tmp[k] + " ";
					}
					String[] colpr1 = { "nextPC", "Instruction" };
					String[][] rowpr1 = { {tmp[0], s}};
					//System.out.println(s);
					DefaultTableModel mdlpr1 = new DefaultTableModel(rowpr1, colpr1);
					if_id.setModel(mdlpr1);
					
					//	idex
					tmp = content[4].split(" ");
					String[] colpr2 = { "nextPC", "data1", "data2", "extend", "rt", "rd",
							"WB", "M", "EX" };
					String[][] rowpr2 = {tmp};
					DefaultTableModel mdlpr2 = new DefaultTableModel(rowpr2, colpr2);
					id_ex.setModel(mdlpr2);
					
					//	exmem
					tmp = content[5].split(" ");
					String[] colpr3 = { "newPC", "Zero", "Alu Result", "rt/rd", "WB", "M" };
					String[][] rowpr3 = {tmp};
					DefaultTableModel mdlpr3 = new DefaultTableModel(rowpr3, colpr3);
					ex_mem.setModel(mdlpr3);
					
					//	memwb
					tmp = content[6].split(" ");
					String[] colpr4 = { "read data", "Alu Result", "rt/rd", "WB" };
					String[][] rowpr4 = {tmp};
					DefaultTableModel mdlpr4 = new DefaultTableModel(rowpr4, colpr4);
					mem_wb.setModel(mdlpr4);
					
					clock++;
				}
				else
					JOptionPane.showMessageDialog(contentPane, "Please type instructions to run.");
			}
		});
		GridBagConstraints gbc_btnRun = new GridBagConstraints();
		gbc_btnRun.insets = new Insets(0, 0, 5, -190);
		gbc_btnRun.gridx = 0;
		gbc_btnRun.gridy = 14;
		contentPane.add(btnRun, gbc_btnRun);

		JButton btnNext = new JButton("step");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblclk.setText(" "+ (clock+1) +" ");
				String[] content = sim.getCyle(clock);
				if(content == null) return;
				
				//	register file
				String[] tmp = content[0].split(" ");
				String[][] row = new String[34][3];
				int i = 0;
				for(int j = 0; j < 32; j++){
					row[j][0] = "" + j; row[j][1] = tmp[i]; row[j][2] = tmp[i+1]; i+=2;
				}
				row[32][0] = "Ra"; row[32][1] = tmp[i]; row[32][2] = tmp[i+1]; i+=2;
				row[33][0] = "PC"; row[33][1] = tmp[i]; row[33][2] = tmp[i+1];
				String[] col = { "Reg", "Dec", "Hex" };
				
				DefaultTableModel mdl = new DefaultTableModel(row, col);
				registers.setModel(mdl);
				
				//	data memory
				if(!content[1].equals("")){
					tmp = content[1].split(" ");
					String[] colm = { "Addr", "Dec", "Hex" };
					String[][] rowm = new String[tmp.length/3][3];
					i = 0;
					for(int k = 0; k < tmp.length; k+=3){
						rowm[i][0] = tmp[k]; rowm[i][1] = tmp[k+1]; rowm[i][2] = tmp[k+2]; i++;
					}
					DefaultTableModel mdlm = new DefaultTableModel(rowm, colm);
					memory.setModel(mdlm);
				}
				
				//	pcsrc
				lblpcsrc.setText(" " + content[2] + " ");
				
				//	ifid
				//System.out.println(content[3]);
				tmp = content[3].split(" ");
				String s = "";
				for(int k = 1; k < tmp.length; k++){
					s+= tmp[k] + " ";
				}
				String[] colpr1 = { "nextPC", "Instruction" };
				String[][] rowpr1 = { {tmp[0], s}};
				System.out.println(s);
				DefaultTableModel mdlpr1 = new DefaultTableModel(rowpr1, colpr1);
				if_id.setModel(mdlpr1);
				
				//	idex
				tmp = content[4].split(" ");
				String[] colpr2 = { "nextPC", "data1", "data2", "extend", "rt", "rd",
						"WB", "M", "EX" };
				String[][] rowpr2 = {tmp};
				DefaultTableModel mdlpr2 = new DefaultTableModel(rowpr2, colpr2);
				id_ex.setModel(mdlpr2);
				
				//	exmem
				tmp = content[5].split(" ");
				String[] colpr3 = { "newPC", "Zero", "Alu Result", "rt/rd", "WB", "M" };
				String[][] rowpr3 = {tmp};
				DefaultTableModel mdlpr3 = new DefaultTableModel(rowpr3, colpr3);
				ex_mem.setModel(mdlpr3);
				
				//	memwb
				tmp = content[6].split(" ");
				String[] colpr4 = { "read data", "Alu Result", "rt/rd", "WB" };
				String[][] rowpr4 = {tmp};
				DefaultTableModel mdlpr4 = new DefaultTableModel(rowpr4, colpr4);
				mem_wb.setModel(mdlpr4);
				
				clock++;
			}
		});
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridwidth = 4;
		gbc_btnNext.insets = new Insets(0, 0, 5, 117);
		gbc_btnNext.gridx = 1;
		gbc_btnNext.gridy = 14;
		contentPane.add(btnNext, gbc_btnNext);

		JButton btnFinish = new JButton("finish");
		GridBagConstraints gbc_btnFinish = new GridBagConstraints();
		gbc_btnFinish.insets = new Insets(0, 0, 5, 0);
		gbc_btnFinish.gridx = 2;
		gbc_btnFinish.gridy = 14;
		contentPane.add(btnFinish, gbc_btnFinish);

		JButton btnStop = new JButton("reset");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
			}
		});
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.insets = new Insets(0, 10, 5, 5);
		gbc_btnStop.gridx = 3;
		gbc_btnStop.gridy = 14;
		contentPane.add(btnStop, gbc_btnStop);

	}

	private void initLblClock() {

		JLabel lblClock = new JLabel("Clock #");
		GridBagConstraints gbc_lblClock = new GridBagConstraints();
		gbc_lblClock.insets = new Insets(0, 0, 5, 5);
		gbc_lblClock.gridx = 4;
		gbc_lblClock.gridy = 15;

		contentPane.add(lblClock, gbc_lblClock);

		lblclk = new JLabel("0");
		lblclk.setForeground(Color.RED);
		GridBagConstraints gbc_lblclk = new GridBagConstraints();
		gbc_lblclk.anchor = GridBagConstraints.WEST;
		gbc_lblclk.insets = new Insets(0, 0, 5, 5);
		gbc_lblclk.gridx = 8;
		gbc_lblclk.gridy = 15;
		contentPane.add(lblclk, gbc_lblclk);
	}

	private void initIFID() {
		JLabel lblIfidRegister = new JLabel("IF/ID REGISTER");
		GridBagConstraints gbc_lblIfidRegister = new GridBagConstraints();
		gbc_lblIfidRegister.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIfidRegister.gridwidth = 5;
		gbc_lblIfidRegister.insets = new Insets(0, 0, 5, 5);
		gbc_lblIfidRegister.gridx = 0;
		gbc_lblIfidRegister.gridy = 15;
		contentPane.add(lblIfidRegister, gbc_lblIfidRegister);

		String[] col = { "nextPC", "Instruction" };
		String[][] row = { { "0", "0" } };
		if_id = new JTable(row, col);
		if_id.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		if_id.setBorder(new LineBorder(new Color(0, 0, 0)));
		if_id.setEnabled(false);

		JScrollPane if_idsp = new JScrollPane(if_id);
		// if_idsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_if_idsp = new GridBagConstraints();
		gbc_if_idsp.gridheight = 2;
		gbc_if_idsp.gridwidth = 19;
		gbc_if_idsp.insets = new Insets(0, 0, 5, 5);
		gbc_if_idsp.fill = GridBagConstraints.BOTH;
		gbc_if_idsp.gridx = 0;
		gbc_if_idsp.gridy = 16;
		contentPane.add(if_idsp, gbc_if_idsp);

	}

	private void initIDEX() {
		JLabel lblIdexRegister = new JLabel("ID/EX REGISTER");
		GridBagConstraints gbc_lblIdexRegister = new GridBagConstraints();
		gbc_lblIdexRegister.gridwidth = 2;
		gbc_lblIdexRegister.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblIdexRegister.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdexRegister.gridx = 0;
		gbc_lblIdexRegister.gridy = 20;
		contentPane.add(lblIdexRegister, gbc_lblIdexRegister);

		String[] col = { "nextPC", "data1", "data2", "extend", "rt", "rd",
				"WB", "M", "EX" };
		String[][] row = { { "0", "0", "0", "0", "0", "0", "0", "0", "0" } };
		id_ex = new JTable(row, col);
		id_ex.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		id_ex.setBorder(new LineBorder(new Color(0, 0, 0)));
		id_ex.setEnabled(false);

		JScrollPane id_exsp = new JScrollPane(id_ex);
		// id_exsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_id_exsp = new GridBagConstraints();
		gbc_id_exsp.gridheight = 6;
		gbc_id_exsp.gridwidth = 19;
		gbc_id_exsp.insets = new Insets(0, 0, 76, 5);
		gbc_id_exsp.fill = GridBagConstraints.BOTH;
		gbc_id_exsp.gridx = 0;
		gbc_id_exsp.gridy = 21;
		contentPane.add(id_exsp, gbc_id_exsp);

	}

	private void initEXMEM() {
		JLabel lblExmemRegister = new JLabel("EX/MEM REGISTER");
		GridBagConstraints gbc_lblExmemRegister = new GridBagConstraints();
		gbc_lblExmemRegister.anchor = GridBagConstraints.WEST;
		gbc_lblExmemRegister.insets = new Insets(55, 0, -5, 5);
		gbc_lblExmemRegister.gridx = 0;
		gbc_lblExmemRegister.gridy = 23;
		contentPane.add(lblExmemRegister, gbc_lblExmemRegister);

		String[] col = { "newPC", "Zero", "Alu Result", "rt/rd", "WB", "M" };
		String[][] row = { { "0", "0", "0", "0", "0", "0" } };
		ex_mem = new JTable(row, col);
		ex_mem.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		ex_mem.setBorder(new LineBorder(new Color(0, 0, 0)));
		ex_mem.setEnabled(false);

		JScrollPane ex_memsp = new JScrollPane(ex_mem);
		// ex_memsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_ex_memsp = new GridBagConstraints();
		gbc_ex_memsp.gridheight = 4;
		gbc_ex_memsp.gridwidth = 19;
		gbc_ex_memsp.insets = new Insets(15, 0, 65, 5);
		gbc_ex_memsp.fill = GridBagConstraints.BOTH;
		gbc_ex_memsp.gridx = 0;
		gbc_ex_memsp.gridy = 24;
		contentPane.add(ex_memsp, gbc_ex_memsp);
	}

	private void initMEMWB() {
		JLabel lblMemwbRegister_1 = new JLabel("MEM/WB REGISTER");
		GridBagConstraints gbc_lblMemwbRegister_1 = new GridBagConstraints();
		gbc_lblMemwbRegister_1.insets = new Insets(-170, -115, 5, 5);
		gbc_lblMemwbRegister_1.gridx = 0;
		gbc_lblMemwbRegister_1.gridy = 32;
		contentPane.add(lblMemwbRegister_1, gbc_lblMemwbRegister_1);

		String[] col = { "read data", "Alu Result", "rt/rd", "WB" };
		String[][] row = { { "0", "0", "0", "0" } };

		mem_wb = new JTable(row, col);
		mem_wb.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		mem_wb.setBorder(new LineBorder(new Color(0, 0, 0)));
		mem_wb.setEnabled(false);

		JScrollPane mem_wbsp = new JScrollPane(mem_wb);
		// mem_wbsp.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_mem_wbsp = new GridBagConstraints();
		gbc_mem_wbsp.gridheight = 2;
		gbc_mem_wbsp.gridwidth = 19;
		gbc_mem_wbsp.insets = new Insets(-80, 0, 30, 5);
		gbc_mem_wbsp.fill = GridBagConstraints.BOTH;
		gbc_mem_wbsp.gridx = 0;
		gbc_mem_wbsp.gridy = 33;
		contentPane.add(mem_wbsp, gbc_mem_wbsp);

	}

	private void initLblPCsrc() {

		JLabel lblPcsrcSignal = new JLabel("PCSrc SIGNAL");
		GridBagConstraints gbc_lblPcsrcSignal = new GridBagConstraints();
		gbc_lblPcsrcSignal.anchor = GridBagConstraints.WEST;
		gbc_lblPcsrcSignal.insets = new Insets(-20, 0, 5, 5);
		gbc_lblPcsrcSignal.gridx = 0;
		gbc_lblPcsrcSignal.gridy = 35;
		contentPane.add(lblPcsrcSignal, gbc_lblPcsrcSignal);

		lblpcsrc = new JLabel(" 0 ");
		lblpcsrc.setBackground(Color.WHITE);
		lblpcsrc.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_lblpcsrc = new GridBagConstraints();
		gbc_lblpcsrc.insets = new Insets(0, 0, 5, 5);
		gbc_lblpcsrc.gridx = 0;
		gbc_lblpcsrc.gridy = 36;
		contentPane.add(lblpcsrc, gbc_lblpcsrc);
	}

	// run frame
	public void run() {
		// this.pack();
		this.setVisible(true);
	}
}
