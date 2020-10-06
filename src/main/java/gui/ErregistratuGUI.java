package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import domain.Admin;
import domain.RegularUser;
import domain.User;
import exceptions.UserAlreadyExistsException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class ErregistratuGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textIzena;
	private JTextField textAbizenak;
	private JTextField textNan;
	private JTextField textHelbidea;
	private JTextField textZenb;
	private JTextField textEmail;
	private JTextField textBank;
	private JTextField textUser;
	private JPasswordField textPass;
	private BLFacade facade;
	private SpinnerNumberModel egunak;
	private JSpinner eguna;
	private JComboBox<String> hilabetea;
	private JSpinner urtea;
	private DefaultComboBoxModel<String> hilaIzena = new DefaultComboBoxModel<String>();
	private JCheckBox chckbxKondizioakOnartzenDitut;
	private JPasswordField textPassBai;
	
	private JLabel lblIzena = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Name"));
	private JLabel lblAbizenak = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Surname"));
	private JLabel lblNana = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ID"));
	private JLabel lblJaiotzeData = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Birthdate"));
	private JLabel lblHelbidea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Address"));
	private JLabel lblTelZenbakia = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NumberTel"));
	private JLabel lblEmaila = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Mail"));
	private JLabel lblKontuKorrontea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bank"));
	private JLabel lblErabiltzailea = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("User*"));
	private JLabel lblPasahitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Password*"));
	private JLabel lblPasahitzaBaieztatu = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PasswordYes"));
	
	private JButton jButtonBackToMain = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BackToMenu"));
	private JButton jButtonErregistratu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SignBtn"));
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErregistratuGUI frame = new ErregistratuGUI();
					frame.setVisible(true);
					frame.setBusinessLogic(new BLFacadeImplementation());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Date newDate(int year,int month,int day) {
	     Calendar calendar = Calendar.getInstance();
	     calendar.set(year, month, day,0,0,0);
	     calendar.set(Calendar.MILLISECOND, 0);

	     return calendar.getTime();
	}
	
	public void setBusinessLogic(BLFacade facade) {
		this.facade = facade;
	}
	
	private void initializeMonthComponents() {
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Urtarrila"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Otsaila"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Martxoa"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Apirila"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Maiatza"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Ekaina"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Uztaila"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Abuztua"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Iraila"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Urria"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Azaroa"));
		hilaIzena.addElement(ResourceBundle.getBundle("Etiquetas").getString("Abendua"));
	}

	/**
	 * Create the frame.
	 */
	public ErregistratuGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("ErregistratuGUI"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblIzena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIzena.setBounds(69, 33, 85, 14);
		contentPane.add(lblIzena);
		
		lblAbizenak.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAbizenak.setBounds(68, 58, 86, 14);
		contentPane.add(lblAbizenak);
		
		lblJaiotzeData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblJaiotzeData.setBounds(10, 108, 144, 14);
		contentPane.add(lblJaiotzeData);
		
		lblHelbidea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHelbidea.setBounds(68, 133, 86, 14);
		contentPane.add(lblHelbidea);
		
		lblTelZenbakia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelZenbakia.setBounds(10, 158, 144, 14);
		contentPane.add(lblTelZenbakia);
		
		lblEmaila.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmaila.setBounds(68, 183, 86, 14);
		contentPane.add(lblEmaila);
		
		lblKontuKorrontea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKontuKorrontea.setBounds(32, 208, 122, 14);
		contentPane.add(lblKontuKorrontea);
		
		lblErabiltzailea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblErabiltzailea.setBounds(54, 233, 100, 14);
		contentPane.add(lblErabiltzailea);
		
		lblPasahitza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasahitza.setBounds(68, 258, 86, 14);
		contentPane.add(lblPasahitza);
		
		lblPasahitzaBaieztatu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasahitzaBaieztatu.setBounds(10, 283, 144, 14);
		contentPane.add(lblPasahitzaBaieztatu);
		
		chckbxKondizioakOnartzenDitut = new JCheckBox(ResourceBundle.getBundle("Etiquetas").getString("Kondizioak"));
		chckbxKondizioakOnartzenDitut.setBounds(69, 303, 342, 23);
		contentPane.add(chckbxKondizioakOnartzenDitut);
		
		jButtonErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					if (checkBlankFields())
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("*fields"));
					if (!chckbxKondizioakOnartzenDitut.isSelected())
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("kondizioakOnartu"));
					if (!String.valueOf(textPass.getPassword()).equals(String.valueOf(textPassBai.getPassword())))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("passBaiOkerra"));
					java.util.Date date = newDate((Integer)urtea.getValue(),hilabetea.getSelectedIndex()+1,(Integer)eguna.getValue());
					RegularUser newUser = new RegularUser(textUser.getText(),
							String.valueOf(textPass.getPassword()),
							textIzena.getText(),
							textAbizenak.getText(),
							textNan.getText(),
							date,
							textEmail.getText(),
							textBank.getText(),
							Integer.parseInt(textZenb.getText()),
							textHelbidea.getText());
					facade.createNewUser(newUser);
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("NewUser"), ResourceBundle.getBundle("Etiquetas").getString("Oharra"),
					        JOptionPane.INFORMATION_MESSAGE);
					LoginGUI l = new LoginGUI();
					l.setBusinessLogic(MainGUI.getBusinessLogic());
					l.setVisible(true);
					dispose();
				} catch (UserAlreadyExistsException uaee) {
					JOptionPane.showMessageDialog(contentPane, uaee.getMessage(), "Error",
					        JOptionPane.ERROR_MESSAGE);
					allFieldsBlank();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		jButtonErregistratu.setBounds(44, 333, 135, 23);
		contentPane.add(jButtonErregistratu);
		
		textIzena = new JTextField();
		textIzena.setBounds(160, 31, 86, 20);
		contentPane.add(textIzena);
		textIzena.setColumns(10);
		
		textAbizenak = new JTextField();
		textAbizenak.setBounds(160, 56, 139, 20);
		contentPane.add(textAbizenak);
		textAbizenak.setColumns(10);
		
		textNan = new JTextField();
		textNan.setBounds(160, 81, 74, 20);
		contentPane.add(textNan);
		textNan.setColumns(10);
		
		urtea = new JSpinner();
		urtea.setModel(new SpinnerNumberModel(2000, 1900, null, 1));
		urtea.setBounds(160, 106, 86, 20);
		contentPane.add(urtea);
		
		textHelbidea = new JTextField();
		textHelbidea.setBounds(160, 130, 139, 20);
		contentPane.add(textHelbidea);
		textHelbidea.setColumns(10);
		
		textZenb = new JTextField();
		textZenb.setBounds(160, 155, 86, 20);
		contentPane.add(textZenb);
		textZenb.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(160, 180, 115, 20);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		textBank = new JTextField();
		textBank.setBounds(160, 205, 86, 20);
		contentPane.add(textBank);
		textBank.setColumns(10);
		
		textUser = new JTextField();
		textUser.setBounds(160, 230, 86, 20);
		contentPane.add(textUser);
		textUser.setColumns(10);
		
		textPass = new JPasswordField();
		textPass.setBounds(160, 255, 82, 20);
		contentPane.add(textPass);
		
		textPassBai = new JPasswordField();
		textPassBai.setBounds(160, 279, 86, 20);
		contentPane.add(textPassBai);
		textPassBai.setColumns(10);
		
		lblNana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNana.setBounds(68, 83, 86, 14);
		contentPane.add(lblNana);
		
		hilabetea = new JComboBox<String>();
		hilabetea.setBounds(251, 106, 100, 20);
		contentPane.add(hilabetea);
		this.initializeMonthComponents();
		hilabetea.setModel(hilaIzena);
		hilabetea.setSelectedIndex(0);
		
		egunak = new SpinnerNumberModel(1, 1, 31, 1);
		eguna = new JSpinner();
		eguna.setModel(egunak);;
		eguna.setBounds(356, 106, 55, 20);
		contentPane.add(eguna);
		
		jButtonBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginGUI l = new LoginGUI();
				l.setBusinessLogic(MainGUI.getBusinessLogic());
				l.setVisible(true);
				dispose();
			}
		});
		jButtonBackToMain.setBounds(204, 333, 207, 23);
		contentPane.add(jButtonBackToMain);	
	}
	
	protected boolean checkBlankFields() {
		// Hutsuneak uzten ditu, RegExp batekin konpon daiteke
		if (textIzena.getText().equals(""))						return true;
		if (textAbizenak.getText().equals("")) 					return true;
		if (textUser.getText().equals("")) 						return true;
		if (String.valueOf(textPass.getPassword()).equals("")) 	return true;
		if (String.valueOf(textPassBai.getPassword()).equals("")) 	return true;
		if (textZenb.getText().equals(""))						return true;
		if (textNan.getText().equals(""))						return true;
		if (textBank.getText().equals(""))						return true;
		return false;
	}
	
	protected void allFieldsBlank() {
		textIzena.setText("");
		textAbizenak.setText("");
		textUser.setText("");
		textPass.setText("");
		textNan.setText("");
		textEmail.setText("");
		textBank.setText("");
		textHelbidea.setText("");
		textZenb.setText("");
		textPassBai.setText("");
	}
}
