package textproc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class BookReaderController {
	JTextField textField; // attributdeklarering för att vara synliga i hela klassen
	SortedListModel<Entry<String, Integer>> listModel;
	JList<Entry<String, Integer>> list;
	JFrame frame;
	

	public BookReaderController(GeneralWordCounter counter) {
		SwingUtilities.invokeLater(() -> createWindow(counter, "BookReader", 100, 300));
	}

	private void createWindow(GeneralWordCounter counter, String title, int width, int height) {

		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

		// skapar en JList och knyter an ett objekt av SortedListMODEL, som i sin tur
		// tar in en list från vår generalWordcounter.
		// Dessutom knyts JList till scrollList, som läggs i vår ruta (pane).
		listModel = new SortedListModel<>(counter.getWordList());
		list = new JList<>(listModel);
		JScrollPane scrollList = new JScrollPane(list);
		pane.add(scrollList);

		JPanel panel = new JPanel();
		JRadioButton buttonABC = new JRadioButton("Alphabetic");
		JRadioButton buttonFrec = new JRadioButton("Frequency");
		// lambda innuti lambda
		buttonABC.addActionListener(event -> listModel.sort((o1, o2) -> o1.getKey().compareTo(o2.getKey())));
		buttonFrec.addActionListener(event -> listModel.sort((o1, o2) -> o2.getValue() - o1.getValue()));

		panel.add(buttonABC);
		panel.add(buttonFrec);
		pane.add(panel, BorderLayout.SOUTH);

		JPanel searchPanel = new JPanel();
		JButton buttonSearch = new JButton("Search for word");
		frame.getRootPane().setDefaultButton(buttonSearch);			//Connects the enter key
		buttonSearch.addActionListener(new SearchEngine()); // Tränar här på att implementera en inre klass istället för
															// att köra lambdautryck
		textField = new JTextField("", 25);
		searchPanel.add(textField);
		searchPanel.add(buttonSearch);
		pane.add(searchPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

	private class SearchEngine implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Hell yeah, we are searching");
			boolean found = false;		//lowers time complexity
			String text = textField.getText().replaceAll("(\\s|,|\\.|:|;|!|\\?|'|\\\")+", "");
			for (int i = 0; i < listModel.getSize() && found == false; i++) {				//for loop with several conditions, should be ok according to stackoverflow.
				if (text.equalsIgnoreCase(listModel.getElementAt(i).getKey())) {
					list.setSelectedIndex(i);
					list.ensureIndexIsVisible(i);
					found = true;
				}
			}
			if (!found) {
				JOptionPane.showMessageDialog(frame, "The word does not exist");
			}

		}

	}

}
