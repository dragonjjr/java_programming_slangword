import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
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
import java.util.jar.JarEntry;

public class Main extends JPanel implements ActionListener {

	JPanel panelGridBagLayout, pn1, pn2, pn3, pn4, pn5;
	JLabel lb1, lb2, lb3, lb4, lb5, lb6;
	JTextField tfSlang, tfDefinition;
	JButton jbtnSlangSearch, jbtnDefinitionSearch;
	JTable jtbSlangwords;
	String[] colMedHdr = { "Slang word", "Definition" };

	ArrayList<SlangWord> lstSlangWord;

	public Main() {
		super(new BorderLayout());

		lstSlangWord = new ArrayList<SlangWord>();

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

		// Panel Slang text field
		lb2 = new JLabel("Mã học sinh:");
		lb2.setFont(new Font("Arial", Font.PLAIN, 16));
		tfSlang = new JFormattedTextField(new DecimalFormat("#"));
		tfSlang.setPreferredSize(new Dimension(180, 24));
		jbtnSlangSearch = new JButton("Slang word search");
		pn1.setLayout(panelGridLayout);
		pn1.add(lb2);
		pn1.add(tfSlang);
		pn1.add(jbtnSlangSearch);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 10, 5, 10);

		panelGridBagLayout.add(pn1, gridBagConstraints);

		// Panel Definition text field
		lb3 = new JLabel("Tên học sinh: ");
		lb3.setFont(new Font("Arial", Font.PLAIN, 16));
		tfDefinition = new JTextField();
		tfDefinition.setPreferredSize(new Dimension(180, 24));
		jbtnDefinitionSearch = new JButton("Definition search");
		pn2.setLayout(panelGridLayout);
		pn2.add(lb3);
		pn2.add(tfDefinition);
		pn2.add(jbtnDefinitionSearch);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
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
				setForm(slangWord);
			}
		});
		jtbSlangwords.setDefaultEditor(Object.class, null);

		// Box
		JPanel pn7 = new JPanel();
		pn7.setPreferredSize(new Dimension(30, jtbSlangwords.getHeight()));

		JPanel pn8 = new JPanel();
		pn8.setPreferredSize(new Dimension(30, jtbSlangwords.getHeight()));

		// Add Main panel
		add(panelGridBagLayout, BorderLayout.PAGE_START);
		add(pn7, BorderLayout.WEST);
		add(pn8, BorderLayout.EAST);
		add(new JScrollPane(jtbSlangwords), BorderLayout.CENTER);

	}

	private void setForm(SlangWord slangWord) {
		tfSlang.setText(String.valueOf(slangWord.getSlang()));
		tfDefinition.setText(String.valueOf(slangWord.getDefinition()));
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
