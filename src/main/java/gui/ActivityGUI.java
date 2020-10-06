package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import businessLogic.BLFacade;
import domain.Movement;
import domain.RegularUser;

import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ActivityGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable movementsTable;
	private TableModel movementsTableModel;
	private JLabel lUser;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActivityGUI frame = new ActivityGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	class TableModel extends AbstractTableModel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<Movement> movementsList;
		private String[] columns = new String[] {ResourceBundle.getBundle("Etiquetas").getString("Date"), ResourceBundle.getBundle("Etiquetas").getString("Amount"), ResourceBundle.getBundle("Etiquetas").getString("Description")};
		private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		public TableModel(Collection<Movement> movementsCollection) {
			this.movementsList = new ArrayList<Movement>(movementsCollection.size());
			for (Movement m : movementsCollection)
				this.movementsList.add(m);
			this.movementsList.sort(new Comparator<Movement>() {
				public int compare(Movement m1, Movement m2) {
					return m1.getDate().compareTo(m2.getDate());
				}
			});
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columns[columnIndex];
		}

		public int getColumnCount() {
			return columns.length;
		}

		public int getRowCount() {
			return movementsList.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Movement m = movementsList.get(rowIndex);
			switch (columnIndex) {
				case 0 : return df.format(m.getDate());
				case 1 : return Float.toString(m.getMoneyMoved());
				case 2 : return m.getDescription();
			}
			return "";
		}
	}

	/**
	 * Create the frame.
	 */
	public ActivityGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ActivityGUI"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		BLFacade facade = MainGUI.getBusinessLogic();
		RegularUser user = (RegularUser) LoginGUI.getUser();
		
		movementsTableModel = new TableModel(facade.getMovementsByUser(user));
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnClose, BorderLayout.SOUTH);
		
		lUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User") + " " + user.getUserName());
		contentPane.add(lUser, BorderLayout.NORTH);
		
		movementsTable = new JTable();
		movementsTable.setModel(movementsTableModel);
		adjustCells();

		scrollPane = new JScrollPane(movementsTable);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	
	private void adjustCells() {
		this.movementsTable.getColumnModel().getColumn(0).setMinWidth(130);
		this.movementsTable.getColumnModel().getColumn(0).setMaxWidth(150);
		this.movementsTable.getColumnModel().getColumn(1).setMinWidth(75);
		this.movementsTable.getColumnModel().getColumn(1).setMaxWidth(130);
	}

}
