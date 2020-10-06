package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.RegularUser;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class TopBetGUI extends JFrame {

	private JPanel contentPane;
	private JTextField top1;
	private JTextField money1;
	private JTextField top2;
	private JTextField money2;
	private JTextField top3;
	private JTextField money3;
	private ArrayList<RegularUser> u=new ArrayList<RegularUser>();
	/**
	 * Launch the application.
	 */
	public TopBetGUI()
	{
		try{
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("TopUsersGUI"));
			TopBetGUI();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	

	/**
	 * Create the frame.
	 */
	public void TopBetGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsers = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Users"));
		lblUsers.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsers.setBounds(20, 23, 129, 14);
		contentPane.add(lblUsers);
		
		JLabel label = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MoneyWon"));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(187, 23, 129, 14);
		contentPane.add(label);
	
		BLFacade facade = MainGUI.getBusinessLogic();
		u=facade.getUsers();
		double m1=0;
		String user1="";
		for(int i=0;i<u.size();i++) {
			if(m1<u.get(i).getMoneyWon()) {
				m1=u.get(i).getMoneyWon();
				user1=u.get(i).getUserName();
			}
		}
		
		
		top1 = new JTextField();
		top1.setBounds(78, 70, 113, 20);
		contentPane.add(top1);
		top1.setColumns(10);
		top1.setEditable(false);
		top1.setText(user1);
		
		
		JLabel label_1 = new JLabel("1");
		label_1.setBounds(62, 73, 15, 14);
		contentPane.add(label_1);
		
		money1 = new JTextField();
		money1.setBounds(247, 70, 86, 20);
		contentPane.add(money1);
		money1.setColumns(10);
		money1.setEditable(false);
		money1.setText(Double.toString(m1));
		
		JLabel label_2 = new JLabel("2");
		label_2.setBounds(62, 119, 15, 14);
		contentPane.add(label_2);
		
		double m2=0;
		String user2="";
		for(int i=0;i<u.size();i++) {
			if(!u.get(i).getUserName().equals(user1) && m2<u.get(i).getMoneyWon()) {
				m2=u.get(i).getMoneyWon();
				user2=u.get(i).getUserName();
			}
		}
		
		top2 = new JTextField();
		top2.setBounds(78, 116, 113, 20);
		contentPane.add(top2);
		top2.setColumns(10);
		top2.setEditable(false);
		top2.setText(user2);
		
		money2 = new JTextField();
		money2.setBounds(247, 116, 86, 20);
		contentPane.add(money2);
		money2.setColumns(10);
		money2.setEditable(false);
		money2.setText(Double.toString(m2));
		
		JLabel label_3 = new JLabel("3");
		label_3.setBounds(62, 168, 15, 14);
		contentPane.add(label_3);
		
		double m3=0;
		String user3="";
		for(int i=0;i<u.size();i++) {
			if(!u.get(i).getUserName().equals(user1) && !u.get(i).getUserName().equals(user2) && m3<u.get(i).getMoneyWon()) {
				m3=u.get(i).getMoneyWon();
				user3=u.get(i).getUserName();
			}
		}
		
		top3 = new JTextField();
		top3.setBounds(78, 165, 113, 20);
		contentPane.add(top3);
		top3.setColumns(10);
		top3.setEditable(false);
		top3.setText(user3);
		
		money3 = new JTextField();
		money3.setBounds(247, 165, 86, 20);
		contentPane.add(money3);
		money3.setColumns(10);
		money3.setEditable(false);
		money3.setText(Double.toString(m3));
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButton2_actionPerformed(arg0);	
			}
		});
		btnClose.setBounds(167, 220, 94, 30);
		contentPane.add(btnClose);
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
