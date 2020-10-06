package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Movement;
import domain.RegularUser;
import exceptions.Negative;
import exceptions.NoMoneyException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Date;
import java.util.ResourceBundle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PutExtractMoneyGUI extends JFrame {

	private JPanel contentPane;
	
	private JTextField put;
	private BLFacade facade; 
	private JTextField extract;
	private JTextField textField;
	public PutExtractMoneyGUI()
	{
		try{
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("PutExtractMoneyGUI"));
			PutExtractMoneyGUI();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	

	/**
	 * Create the frame.
	 */
	public void PutExtractMoneyGUI() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		put = new JTextField();
		put.setBounds(57, 67, 179, 20);
		contentPane.add(put);
		put.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PutMoney"));
		lblNewLabel.setBounds(57, 42, 179, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnPut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Put"));
		btnPut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (put.getText().equals(""))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("EremuHutsa"));

					RegularUser user=(RegularUser) LoginGUI.getUser();
					Float amount = Float.parseFloat(put.getText());

					facade=MainGUI.getBusinessLogic();
					facade.putMoney(amount, user);
					facade.createMovement(new Movement(user, new Date(), amount, ResourceBundle.getBundle("Etiquetas").getString("WalletPut")));

					Float money=facade.howMuchMoney(user);
					textField.setText(money.toString());
					
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("MoneyToWallet"), "Info",
							JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Negative n){
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("MoneyPositive"), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				put.setText("");
			}
		});
		
		btnPut.setBounds(284, 66, 89, 23);
		contentPane.add(btnPut);
		
		extract = new JTextField();
		extract.setBounds(57, 150, 179, 20);
		contentPane.add(extract);
		extract.setColumns(10);
		
		JLabel lblExtractMoney = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ExtractMoney"));
		lblExtractMoney.setBounds(57, 125, 179, 14);
		contentPane.add(lblExtractMoney);
		
		JButton btnExtract = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Extract"));
		btnExtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (extract.getText().equals(""))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("EremuHutsa"));
					RegularUser user=(RegularUser) LoginGUI.getUser();
					Float amount = Float.parseFloat(extract.getText());

					facade=MainGUI.getBusinessLogic();
					facade.restMoney(amount, user);
					facade.createMovement(new Movement(user, new Date(), amount, ResourceBundle.getBundle("Etiquetas").getString("WalletExtract")));

					Float money=facade.howMuchMoney(user);
					textField.setText(money.toString());
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("DiruaBankura"), "Info",
							JOptionPane.INFORMATION_MESSAGE);
				}catch(NoMoneyException el) {
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("NoMoney"), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch(Negative n){
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("MoneyPositive"), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(getContentPane(), e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				extract.setText("");
			}
		});
		btnExtract.setBounds(284, 149, 89, 23);
		contentPane.add(btnExtract);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jButton2_actionPerformed(arg0);			
			}
		});
		this.getContentPane().add(btnClose, null);
		btnClose.setBounds(178, 208, 89, 31);
		
		JLabel lblWallet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Wallet"));
		lblWallet.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWallet.setBounds(228, 11, 89, 14);
		contentPane.add(lblWallet);
		
		textField = new JTextField();
		RegularUser user=(RegularUser) LoginGUI.getUser();
		facade=MainGUI.getBusinessLogic();
		Float money=facade.howMuchMoney(user);
		textField.setText(money.toString());		
		
		
		
		textField.setBounds(327, 8, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		
		
	}
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
