package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bet;
import domain.Kuota;
import domain.Movement;
import domain.Question;
import domain.RegularUser;
import exceptions.EventFinished;
import exceptions.MovementAlreadyExistsException;
import exceptions.Negative;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

/* Kode asko komentaturik dago behar izan ezkero, bestela borratu behar da */
public class RemoveBetGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Bet b;
	
	private BLFacade facade = MainGUI.getBusinessLogic();
	private Collection<Bet> bets = facade.getBetsByUser((RegularUser) LoginGUI.getUser());
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Apustuak")); 

	private final JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private final JButton btnApustuaEzabatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RemoveBet")); //$NON-NLS-1$ //$NON-NLS-2$

	private JScrollPane scrollPaneBets = new JScrollPane();
	private JScrollPane scrollPaneQuotas = new JScrollPane();
	private JPanel contentPane;

	private JTable tableBets = new JTable();
	private JTable tableQuotas = new JTable();

	private DefaultTableModel tableModelBets;
	private DefaultTableModel tableModelQuotas;

	private String[] columnNamesKuotak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("KuotakN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Kuotak"), 
	};

	private String[] columnNamesApustuak = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("ApustuakN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Apustuak"), 
	};
	

	public RemoveBetGUI() {
		try {
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("RemoveBetGUI"));
			jFind();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jFind() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		jLabelQueries.setBounds(10, 27, 406, 14);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.getContentPane().add(jLabelQueries);

		jButtonClose.setBounds(new Rectangle(286, 307, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		this.getContentPane().add(jButtonClose, null);

		btnApustuaEzabatu.setEnabled(false);
		
		scrollPaneBets.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneBets.setBounds(10, 52, 305, 116);
	
		tableModelBets = new DefaultTableModel(null, columnNamesApustuak);
		tableModelBets.setColumnCount(3);
		Vector<Object> row;
		for (Bet b : bets) {
			row = new Vector<Object>();
			row.add(b.getBetNumber());
			row.add(b.getBet());
			row.add(b);
			tableModelBets.addRow(row);
		}
		scrollPaneBets.setViewportView(tableBets);
		
		tableBets.setModel(tableModelBets);
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(268);
		tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableBets.getColumnModel().removeColumn(tableBets.getColumnModel().getColumn(2));
		
		this.getContentPane().add(scrollPaneBets, null);
		
		JLabel label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Kuotak"));
		label.setBounds(337, 26, 204, 16);
		getContentPane().add(label);
		
		tableBets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableBets.getSelectedRow();
				
				tableModelQuotas.setDataVector(null, columnNamesKuotak);
				b=(domain.Bet) tableModelBets.getValueAt(i,2); // obtain ev object
				
				System.out.println(b.toString());
				
				Collection<Kuota> kuotak = b.getKuota();
				System.out.println(kuotak);
				
				tableModelQuotas.setColumnCount(3);
				Vector<Object> row;
				for (domain.Kuota ku : kuotak) {
					row = new Vector<Object>();

					row.add(ku.getQuestion().getQuestion());
					row.add(ku.getResult());
					row.add(ku);
					tableModelQuotas.addRow(row);	
				}
				tableQuotas.getColumnModel().getColumn(0).setPreferredWidth(268);
				tableQuotas.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQuotas.getColumnModel().removeColumn(tableQuotas.getColumnModel().getColumn(2));
				btnApustuaEzabatu.setEnabled(true);
			}
		});
		
		btnApustuaEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {
					Date data = new Date();
					for(Kuota k: b.getKuota())
						if(k.getQuestion().getEvent().getEventDate().compareTo(data)<0)
							throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ApustuaAmaituta"));
					facade.removeBet(b);
					facade.putMoney(b.getBet(), b.getUser());
					facade.createMovement(new Movement(b.getUser(), new Date(), b.getBet(), ResourceBundle.getBundle("Etiquetas").getString("BetRemoved")));
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("ApustuaEzabatuta"), "Info", JOptionPane.INFORMATION_MESSAGE);
					tableModelBets.removeRow(tableBets.getSelectedRow());
					tableModelBets.fireTableDataChanged();
					tableModelQuotas.setDataVector(null, columnNamesKuotak);
					tableModelQuotas.fireTableDataChanged();
				} catch(EventFinished el) {
					JOptionPane.showMessageDialog(contentPane, el.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch (Negative n) {
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("MoneyPositive"), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (MovementAlreadyExistsException maee) {
					JOptionPane.showMessageDialog(contentPane, maee.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					btnApustuaEzabatu.setEnabled(false);
				}	
			}
		});
		btnApustuaEzabatu.setBounds(73, 307, 130, 30);
		getContentPane().add(btnApustuaEzabatu);
		
		scrollPaneQuotas.setBounds(337, 52, 337, 203);
		getContentPane().add(scrollPaneQuotas);
		tableQuotas.setRowSelectionAllowed(false);
		scrollPaneQuotas.setViewportView(tableQuotas);
		tableModelQuotas = new DefaultTableModel(null, columnNamesKuotak);
		tableQuotas.setModel(tableModelQuotas);
	}
}
