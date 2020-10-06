package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;


import dataAccess.DataAccess;
import domain.Admin;
import domain.RegularUser;
import domain.User;
import exceptions.IncorrectPassException;
import exceptions.UserDoesNotExistException;
import javax.swing.JPasswordField;


public class LoginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField textPass;
	private BLFacade facade;
	private static User user;
	
	private JLabel lblErabiltzailea = new JLabel("User:");
	private JLabel lblPasahitza = new JLabel("Password:");
	
	private JButton login = new JButton("LOGIN");
	private JButton btnErregistratu = new JButton("SIGN");
	private JButton btnSartu = new JButton("Login without sign");
	
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton rdbtnEuskara = new JRadioButton("Euskara");
	private JRadioButton rdbtnCastellano = new JRadioButton("Castellano");
	private JRadioButton rdbtnEnglish = new JRadioButton("English");
	
	private Locale en = new Locale("en");
	private Locale es = new Locale("es");
	private Locale eus = new Locale("eus");
	
	/**
	 * Launch the application.
	 */
	//k
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setBusinessLogic(new BLFacadeImplementation());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
			 	}
			}
		});
	}
	
	public void setBusinessLogic(BLFacade facade) {
		this.facade = facade;
	}
	
	public BLFacade getBusinessLogic() {
		return this.facade;
	}
	
	public void setUser(User user) {
		LoginGUI.user=user;
	}
	
	public static User getUser() {
		return user;
	}
	
	/** 
	 * Create the frame.
	 */
	public LoginGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login"));
		if(Locale.getDefault().equals(en))
			rdbtnEnglish.setSelected(true);
		if(Locale.getDefault().equals(eus))
			rdbtnEuskara.setSelected(true);
		if(Locale.getDefault().equals(es))
			rdbtnCastellano.setSelected(true);
		redibujar();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblErabiltzailea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblErabiltzailea.setBounds(39, 47, 100, 14);
		contentPane.add(lblErabiltzailea);
		
		lblPasahitza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasahitza.setBounds(63, 87, 76, 14);
		contentPane.add(lblPasahitza);
		
		textUser = new JTextField();
		textUser.setBounds(149, 45, 131, 20);
		contentPane.add(textUser);
		textUser.setColumns(10);
		
		textPass = new JPasswordField();
		textPass.setBounds(149, 85, 131, 20);
		contentPane.add(textPass);
		textPass.setColumns(10);
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String pass = String.valueOf(textPass.getPassword());
					String userName = textUser.getText();
					if(pass.equals("") || userName.equals(""))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("EremuHutsak"));
					User user = facade.login(userName, pass);
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("SuccessfulLog") + userName, ResourceBundle.getBundle("Etiquetas").getString("Login..."),
							JOptionPane.INFORMATION_MESSAGE);
					if(user instanceof RegularUser) {
						RegisteredUserGUI re = new RegisteredUserGUI();
						setUser(user);
						re.setBussinessLogic(MainGUI.getBusinessLogic());
						re.setAlwaysOnTop(true);
						re.setVisible(true);
						dispose();
					}else {
						MainGUI que = new MainGUI();
						que.setAlwaysOnTop(true);
						que.setVisible(true);
						dispose();
					}
				} catch (UserDoesNotExistException e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (IncorrectPassException e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					textPass.setText("");
					textUser.setText("");
				}
			}			
		});
		login.setBounds(97, 134, 89, 23);
		contentPane.add(login);
		
		btnErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ErregistratuGUI erreg = new ErregistratuGUI();
				erreg.setBusinessLogic(MainGUI.getBusinessLogic());
				erreg.setAlwaysOnTop(true);
				erreg.setVisible(true);
				dispose();
			}
		});
		btnErregistratu.setBounds(220, 134, 135, 23);
		contentPane.add(btnErregistratu);
		
		rdbtnEuskara.setBounds(76, 218, 100, 23);
		bg.add(rdbtnEuskara);
		contentPane.add(rdbtnEuskara);
		
		rdbtnEuskara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				redibujar();				}
		});
		bg.add(rdbtnEuskara);
		
		rdbtnCastellano.setBounds(177, 218, 115, 23);
		bg.add(rdbtnCastellano);
		contentPane.add(rdbtnCastellano);
		
		rdbtnCastellano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				redibujar();
			}
		});
		bg.add(rdbtnCastellano);
		
		rdbtnEnglish.setBounds(292, 218, 100, 23);
		bg.add(rdbtnEnglish);
		contentPane.add(rdbtnEnglish);
		
		rdbtnEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				redibujar();				}
		});
		bg.add(rdbtnEnglish);
		
		btnSartu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleUserGUI su = new SimpleUserGUI();
				su.setBusinessLogic(MainGUI.getBusinessLogic());
				su.setAlwaysOnTop(true);
				su.setVisible(true);
				dispose();
			}
		});
		btnSartu.setBounds(107, 177, 205, 23);
		contentPane.add(btnSartu);
	}
	
	private void redibujar() {
		lblErabiltzailea.setText(ResourceBundle.getBundle("Etiquetas").getString("User"));
		lblPasahitza.setText(ResourceBundle.getBundle("Etiquetas").getString("Password"));
		login.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginBtn"));
		btnErregistratu.setText(ResourceBundle.getBundle("Etiquetas").getString("SignBtn"));
		btnSartu.setText(ResourceBundle.getBundle("Etiquetas").getString("NoSignBtn"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Login"));
	}
	
}
