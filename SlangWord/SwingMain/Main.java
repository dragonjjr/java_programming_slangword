import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.desktop.AboutHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.jar.JarEntry;

public class Main extends JPanel implements ActionListener {

	JPanel panelGridBagLayout, pn1, pn2, pn3, pn4, pn5;
	JLabel lb1, lb2, lb3, lb4, lb5, lb6;
	JTextField tfId, tfSlang, tfDefinition;
	JButton jbtnSlangSearch, jbtnDefinitionSearch, jbtnAdd, jbtnUpdate, jbtnDelete, jbtnResetData, jbtnRandomSlang;
	JTable jtbSlangwords;
	String[] colMedHdr = { "Slang word", "Definition" };

	SlangWord slangWordSelected = null;
	ArrayList<SlangWord> lstSlangWord;

	public Main() {
		super(new BorderLayout());

		this.lstSlangWord = new ArrayList<SlangWord>();
		try {
			importDataFromFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

		GridLayout panelGridLayout = new GridLayout(1, 3);
		panelGridBagLayout = new JPanel();
		pn1 = new JPanel();
		pn2 = new JPanel();
		pn3 = new JPanel();
		pn4 = new JPanel();
		pn5 = new JPanel();

		// Set up layout for panel
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		panelGridBagLayout.setLayout(gridBagLayout);

		// Label
		lb1 = new JLabel("Slang word");
		lb1.setFont(new Font("Arial", Font.BOLD, 32));

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		// gridBagConstraints.gridheight = 0;
		gridBagConstraints.insets = new Insets(0, 50, 20, 50);
		panelGridBagLayout.add(lb1, gridBagConstraints);
		gridBagConstraints.gridwidth = 1;

		// Panel Id field
//		lb4 = new JLabel("ID:");
//		lb4.setFont(new Font("Arial", Font.PLAIN, 16));
//		tfId = new JTextField();
//		tfId.setPreferredSize(new Dimension(180, 24));
//		
//		pn3.setLayout(new GridLayout(1,2));
//		pn3.add(lb4);
//		pn3.add(tfId);
//		
//		gridBagConstraints.gridx = 0;
//		gridBagConstraints.gridy = 1;
//		gridBagConstraints.insets = new Insets(5, 10, 5, 10);
//		panelGridBagLayout.add(pn3, gridBagConstraints);

		// Panel Slang text field
		lb2 = new JLabel("Slang word:");
		lb2.setFont(new Font("Arial", Font.PLAIN, 16));
		tfSlang = new JTextField();
		tfSlang.setPreferredSize(new Dimension(180, 24));

		jbtnSlangSearch = new JButton("Slang word search");
		jbtnSlangSearch.addActionListener(this);
		jbtnSlangSearch.setActionCommand("btnSlangwordSearch");

		pn1.setLayout(panelGridLayout);
		pn1.add(lb2);
		pn1.add(tfSlang);
		pn1.add(jbtnSlangSearch);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 10, 5, 10);

		panelGridBagLayout.add(pn1, gridBagConstraints);

		// Panel Definition text field
		lb3 = new JLabel("Definition: ");
		lb3.setFont(new Font("Arial", Font.PLAIN, 16));
		tfDefinition = new JTextField();
		tfDefinition.setPreferredSize(new Dimension(180, 24));

		jbtnDefinitionSearch = new JButton("Definition search");
		jbtnDefinitionSearch.addActionListener(this);
		jbtnDefinitionSearch.setActionCommand("btnDefinitionSearch");

		pn2.setLayout(panelGridLayout);
		pn2.add(lb3);
		pn2.add(tfDefinition);
		pn2.add(jbtnDefinitionSearch);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(5, 10, 5, 10);
		panelGridBagLayout.add(pn2, gridBagConstraints);

		// Panel table slang words
		DefaultTableModel table_model = new DefaultTableModel(colMedHdr, 0);
		jtbSlangwords = new JTable(table_model);
		jtbSlangwords.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				SlangWord slangWord = new SlangWord();
				slangWord.setSlang(jtbSlangwords.getValueAt(jtbSlangwords.getSelectedRow(), 0).toString());
				slangWord.setDefinition(jtbSlangwords.getValueAt(jtbSlangwords.getSelectedRow(), 1).toString());

				slangWordSelected = slangWord;

				setForm(slangWord);
			}
		});
		jtbSlangwords.setDefaultEditor(Object.class, null);
		fillTable(this.lstSlangWord);

		// Box
		JPanel pn7 = new JPanel();
		pn7.setPreferredSize(new Dimension(30, jtbSlangwords.getHeight()));

		JPanel pn8 = new JPanel();
		pn8.setPreferredSize(new Dimension(30, jtbSlangwords.getHeight()));

		JPanel pn9 = new JPanel();
		pn9.setPreferredSize(new Dimension(jtbSlangwords.getWidth(), 30));

		// Panel button (add, update, delete)
		jbtnAdd = new JButton("Add");
		jbtnAdd.addActionListener(this);
		jbtnAdd.setActionCommand("btnAdd");
		jbtnUpdate = new JButton("Update");
		jbtnUpdate.addActionListener(this);
		jbtnUpdate.setActionCommand("btnUpdate");
		jbtnDelete = new JButton("Delete");
		jbtnDelete.addActionListener(this);
		jbtnDelete.setActionCommand("btnDelete");
		jbtnResetData = new JButton("Reset data");
		jbtnResetData.addActionListener(this);
		jbtnResetData.setActionCommand("btnResetData");
		jbtnRandomSlang = new JButton("Random slang word");
		jbtnRandomSlang.addActionListener(this);
		jbtnRandomSlang.setActionCommand("btnRandomSlang");

		JPanel pnControlData = new JPanel(new FlowLayout());
		pnControlData.add(jbtnAdd);
		pnControlData.add(jbtnUpdate);
		pnControlData.add(jbtnDelete);
		pnControlData.add(jbtnResetData);
		pnControlData.add(jbtnRandomSlang);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(10, 70, 10, 10);
		gridBagConstraints.gridwidth = 2;
		panelGridBagLayout.add(pnControlData, gridBagConstraints);

		// Add Main panel
		add(panelGridBagLayout, BorderLayout.PAGE_START);
		add(pn7, BorderLayout.WEST);
		add(pn8, BorderLayout.EAST);
		add(pn9, BorderLayout.SOUTH);
		add(new JScrollPane(jtbSlangwords), BorderLayout.CENTER);

	}

	private void importDataFromFile() throws FileNotFoundException, IOException {

		try (BufferedReader br = new BufferedReader(new FileReader("slang.txt"))) {

			String row;

			// read header.
			row = br.readLine();

			// read content file.
			while (true) {
				row = br.readLine();

				if (row == null)
					break;

				String[] data = row.split("`");

				SlangWord slangWord = new SlangWord();

				slangWord.setSlang(data[0]);
				slangWord.setDefinition(data[1]);

				this.lstSlangWord.add(slangWord);

			}

			br.close();

		} catch (NumberFormatException e) {
			e.printStackTrace();

		}
	}

	private void resetForm() {
		this.slangWordSelected = null;
		tfSlang.setText("");
		tfDefinition.setText("");
	}

	private void setForm(SlangWord slangWord) {
		tfSlang.setText(String.valueOf(slangWord.getSlang()));
		tfDefinition.setText(String.valueOf(slangWord.getDefinition()));
	}

	private void fillTable(ArrayList<SlangWord> lstSlangWords) {
		DefaultTableModel model = (DefaultTableModel) jtbSlangwords.getModel();
		model.setRowCount(0);
		for (SlangWord slangWord : lstSlangWords) {
			Object[] rowdata = new Object[2];
			rowdata[0] = slangWord.getSlang();
			rowdata[1] = slangWord.getDefinition();

			model.addRow(rowdata);
		}

	}

	public static void main(String args[]) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Create and set up the window.
		JFrame frame = new JFrame("Slang word");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new Main();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.setPreferredSize(new Dimension(900, 700));
		frame.setMinimumSize(new Dimension(850, 500));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void searchBySlangword(String slangWordText) {
		if (!slangWordText.isEmpty()) {
			ArrayList<SlangWord> lstSlangSearch = new ArrayList<SlangWord>();

			for (SlangWord slangWord : this.lstSlangWord) {
				if (slangWordText.equals(slangWord.getSlang())) {
					lstSlangSearch.add(slangWord);
				}
			}

			this.slangWordSelected = null;
			fillTable(lstSlangSearch);
		} else {
			fillTable(this.lstSlangWord);
		}
	}

	private void searchByDefinition(String definitionText) {
		if (!definitionText.isEmpty()) {
			ArrayList<SlangWord> lstSlangSearch = new ArrayList<SlangWord>();

			for (SlangWord slangWord : this.lstSlangWord) {
				if (slangWord.getDefinition().contains(definitionText)) {
					lstSlangSearch.add(slangWord);
				}
			}

			this.slangWordSelected = null;
			fillTable(lstSlangSearch);
		} else {
			fillTable(this.lstSlangWord);
		}
	}

	private void resetData() throws FileNotFoundException, IOException {
		resetForm();
		this.lstSlangWord = new ArrayList<SlangWord>();
		this.slangWordSelected = null;
		importDataFromFile();
		fillTable(lstSlangWord);
	}

	private void addSlangword(SlangWord slangWord) {
		try {
			if (String.valueOf(slangWord.getSlang()).isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please input slang word !!!");
				return;
			} else if (String.valueOf(slangWord.getDefinition()).isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please input definition !!!");
				return;
			} else {
				if (findSlangword(slangWord) != -1) {
					JOptionPane.showMessageDialog(this, "Slangword and definition are exists !");
					return;
				}
				this.lstSlangWord.add(slangWord);
				JOptionPane.showMessageDialog(this, "Add success !");
				fillTable(this.lstSlangWord);
				resetForm();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Something wrong !!!");
			return;
		}
	}

	private void updateSlangword(SlangWord slangWord, String slangWordText, String definition) {

		try {
			if (slangWord == null) {
				JOptionPane.showMessageDialog(this, "Not slang word is selected !!!");
				return;
			} else {
				SlangWord slangWordEdit = this.lstSlangWord.get(findSlangword(slangWord));
				slangWordEdit.setSlang(slangWordText);
				slangWordEdit.setDefinition(definition);

				JOptionPane.showMessageDialog(this, "Update success !");
				fillTable(this.lstSlangWord);
				resetForm();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Something wrong !!!");
			return;
		}

	}

	private void deleteSlangWord(SlangWord slangWord) {
		try {
			if (slangWord == null) {
				JOptionPane.showMessageDialog(this, "Not slang word is selected !!!");
				return;
			} else {
				int res = JOptionPane.showOptionDialog(new JFrame(), "Do you want to delete this slang word?", "Delete",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Yes", "No" },
						JOptionPane.YES_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					this.lstSlangWord.remove(findSlangword(slangWord));
					JOptionPane.showMessageDialog(this, "Delete success !");
					fillTable(this.lstSlangWord);
					resetForm();
				}
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Something wrong !!!");
			ex.printStackTrace();
			return;
		}
	}

	private int findSlangword(SlangWord slangWord) {
		for (int i = 0; i < this.lstSlangWord.size(); i++) {
			SlangWord itemSlangWord = this.lstSlangWord.get(i);
			if (itemSlangWord.getSlang().equals(slangWord.getSlang())
					& itemSlangWord.getDefinition().equals(slangWord.getDefinition())) {
				return i;
			}
		}

		return -1;
	}

	private void randomSlangword() {
		Random rand = new Random();

		int indexRandom = rand.nextInt(this.lstSlangWord.size());

		SlangWord slangwordRandom = this.lstSlangWord.get(indexRandom);

		JOptionPane.showMessageDialog(this, slangwordRandom.getSlang() + ": " + slangwordRandom.getDefinition(),"On this day slang word",JOptionPane.INFORMATION_MESSAGE);

		setForm(slangwordRandom);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String str = e.getActionCommand();
		if (str.equals("btnSlangwordSearch")) {
			searchBySlangword(tfSlang.getText().trim());
		} else if (str.equals("btnDefinitionSearch")) {
			searchByDefinition(tfDefinition.getText().trim());
		} else if (str.equals("btnResetData")) {
			try {
				resetData();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (str.equals("btnAdd")) {
			SlangWord slangWord = new SlangWord(tfSlang.getText().trim(), tfDefinition.getText().trim());
			addSlangword(slangWord);
		} else if (str.equals("btnUpdate")) {
			updateSlangword(this.slangWordSelected, tfSlang.getText().trim(), tfDefinition.getText().trim());
		} else if (str.equals("btnDelete")) {
			deleteSlangWord(this.slangWordSelected);
		} else if (str.equals("btnRandomSlang")) {
			randomSlangword();
		}

	}
}
