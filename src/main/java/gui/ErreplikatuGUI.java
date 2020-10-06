package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bet;
import domain.Kuota;
import domain.Movement;
import domain.Question;
import domain.RegularUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import domain.User;
import javax.swing.table.DefaultTableModel;
import exceptions.*
;
public class ErreplikatuGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelUsers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	
	private JPanel contentPane;

	private JScrollPane scrollPaneUsers = new JScrollPane();
	
	private JLabel jLabelMsg = new JLabel();
	private JTable tableUsers = new JTable();

	private DefaultTableModel tableModelUsers;

	private BLFacade facade=MainGUI.getBusinessLogic();;
	
	private String[] columnNamesUsers = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("UserNameN"), 
			ResourceBundle.getBundle("Etiquetas").getString("UserN")

	};

	private final JButton btnErreplikatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Erreplikatu"));; 
	
	private RegularUser u;
	
	public ErreplikatuGUI()
	{
		try
		{
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErreplikatuGUI"));
			jFind();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jFind() throws Exception
	{

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));

		jLabelUsers.setBounds(40, 181, 268, 14);

		this.getContentPane().add(jLabelUsers);
		
		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.blue);
		this.getContentPane().add(jLabelMsg, null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		jButtonClose.setBounds(new Rectangle(371, 294, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		btnErreplikatu.setEnabled(false);
		
		scrollPaneUsers.setBounds(new Rectangle(161, 62, 378, 200));
		tableModelUsers = new DefaultTableModel(null, columnNamesUsers);

		scrollPaneUsers.setViewportView(tableUsers);
		tableModelUsers = new DefaultTableModel(null, columnNamesUsers);

		tableUsers.setModel(tableModelUsers);
		tableUsers.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableUsers.getColumnModel().getColumn(1).setPreferredWidth(268);
		this.getContentPane().add(scrollPaneUsers, null);
		
		ArrayList<RegularUser> users = facade.getUsers();
		for (domain.RegularUser u:users){
			Vector<Object> row = new Vector<Object>();
			if(!LoginGUI.getUser().getUserName().equals(u.getUserName())) {
				row.add(u.getUserName());
				row.add(u.getFirstName());
				row.add(u);
				tableModelUsers.addRow(row);	
			}
		}
		
		tableUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnErreplikatu.setEnabled(true);
			}
		});
				 
		btnErreplikatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					float dirua = 0;
					int i=tableUsers.getSelectedRow();
					u = (RegularUser)facade.getUserByUsername(String.valueOf(tableModelUsers.getValueAt(i,0)));
					Collection<Bet> bets = facade.getBetsByUser(u);
					if(bets.isEmpty())
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("ApusturikEz"));
					RegularUser u2 = (RegularUser)LoginGUI.getUser();
					Date date = new Date();
					Vector<Bet> bets2 = new Vector<Bet>();
					for (Bet b: bets) {
						
						
						if (b.getFirstDate().after(date) && !facade.betExists(u2, b))
							
						{
								dirua = dirua + b.getBet();
								bets2.add(b);
						}
					}
					if(bets2.isEmpty())
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("ApustuEzberdin"));
					if(facade.howMuchMoney(u2)<dirua)
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("NoMoneyReplicate"));
					for(Bet b: bets2) {
						Bet apustua = new Bet(u2,b.getBet(),b.getKuota());
						facade.addBet(u2, apustua);
					}
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("ApustuaErreplikatuta"), "Info",
						JOptionPane.INFORMATION_MESSAGE);
				}catch(UserDoesNotExistException e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch(BetAlreadyExist e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch(Negative e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch(NoMoneyException e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch(MovementAlreadyExistsException e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}finally { 
					btnErreplikatu.setEnabled(false);
				}
			}
		});	
		btnErreplikatu.setBounds(186, 291, 128, 36);
		getContentPane().add(btnErreplikatu);
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}