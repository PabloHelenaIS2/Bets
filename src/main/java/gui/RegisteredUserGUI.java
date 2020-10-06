package gui;

import javax.swing.*;

import domain.Admin;
import domain.Event;
import domain.RegularUser;
import domain.User;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class RegisteredUserGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQuestions = null;

    private BLFacade facade;
	
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private static User user;
	private JButton jButtonMakeBets;
	private JButton jButtonRemoveABet;
	private JButton jButtonMoney;
	private JButton jButtonActivity;
	private JButton jButtonBackToMain;
	private JButton jButtonQueryQuotas;
	private JButton jButtonTopBets;
	
	private Locale en = new Locale("en");
	private Locale es = new Locale("es");
	private Locale eus = new Locale("eus");
	private JButton btnNewButton;
	private JButton btnErreplikatu;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisteredUserGUI frame = new RegisteredUserGUI();
					frame.setVisible(true);
					frame.setBussinessLogic(new BLFacadeImplementation());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public BLFacade getBusinessLogic(){
		return this.facade;
	}
	
	public void setBussinessLogic(BLFacade fa){
		this.facade = fa;
	}
	
	/**
	 * This is the default constructor
	 */
	public RegisteredUserGUI() {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(700, 400);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisteredUser"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getJButtonActivity());
			jContentPane.add(getBoton3());
			jContentPane.add(getJButtonQueryQuotas());
			jContentPane.add(getJButtonMakeBets());
			jContentPane.add(getJButtonRemoveABet());
			jContentPane.add(getJButtonMoney());
			jContentPane.add(getBtnErreplikatu());
			jContentPane.add(getJButtonBackToMain());
			jContentPane.add(getPanel());
			if(Locale.getDefault().equals(en))
				rdbtnNewRadioButton.setSelected(true);
			if(Locale.getDefault().equals(eus))
				rdbtnNewRadioButton_1.setSelected(true);
			if(Locale.getDefault().equals(es))
				rdbtnNewRadioButton_2.setSelected(true);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQuestions == null) {
			jButtonQueryQuestions = new JButton();
			jButtonQueryQuestions.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQuestions.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQuestions;
	}
	
	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQuestions.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonQueryQuotas.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQuotas"));
		jButtonActivity.setText(ResourceBundle.getBundle("Etiquetas").getString("Activity"));
		jButtonMakeBets.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
		jButtonRemoveABet.setText(ResourceBundle.getBundle("Etiquetas").getString("RemoveBet"));
		jButtonMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("Money"));
		jButtonBackToMain.setText(ResourceBundle.getBundle("Etiquetas").getString("BackToMenu"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("RegisteredUser"));
		btnErreplikatu.setText(ResourceBundle.getBundle("Etiquetas").getString("Erreplikatu"));
	}
	
	public static void setUser(User loggedUser) {
		user = loggedUser;
	}
	
	private JButton getJButtonMakeBets() {
		if (jButtonMakeBets == null) {
			jButtonMakeBets = new JButton();
			jButtonMakeBets.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));//$NON-NLS-1$ //$NON-NLS-2$
			jButtonMakeBets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new MakeBetGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonMakeBets;
	}
	
	private JButton getJButtonRemoveABet() {
		if (jButtonRemoveABet == null) {
			jButtonRemoveABet = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonRemoveABet.setText(ResourceBundle.getBundle("Etiquetas").getString("RemoveBet"));
			jButtonRemoveABet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new RemoveBetGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonRemoveABet;
	}
	
	private JButton getJButtonMoney() {
		if (jButtonMoney == null) {
			jButtonMoney = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("Money"));
			jButtonMoney.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new PutExtractMoneyGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonMoney;
	}
	
	private JButton getJButtonActivity() {
		if (jButtonActivity == null) {
			jButtonActivity = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonActivity.setText(ResourceBundle.getBundle("Etiquetas").getString("Activity"));
			jButtonActivity.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame jf = new ActivityGUI();
					jf.setAlwaysOnTop(true);
					jf.setVisible(true);
				}
			});
		}
		return jButtonActivity;
	}
	
	private JButton getJButtonBackToMain() {
		if (jButtonBackToMain == null) {
			jButtonBackToMain = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonBackToMain.setText(ResourceBundle.getBundle("Etiquetas").getString("BackToMenu"));
			jButtonBackToMain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoginGUI l = new LoginGUI();
					l.setBusinessLogic(MainGUI.getBusinessLogic());
					l.setVisible(true);
					dispose();
				}
			});
		}
		return jButtonBackToMain;
	}
	
	private JButton getJButtonQueryQuotas() {
		if (jButtonQueryQuotas == null) {
			jButtonQueryQuotas = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonQueryQuotas.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQuotas"));
			jButtonQueryQuotas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new FindKuotaGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQuotas;
	}
	
	private JButton getBtnErreplikatu() {
		if (btnErreplikatu == null) {
			btnErreplikatu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Erreplikatu")); //$NON-NLS-1$ //$NON-NLS-2$
			btnErreplikatu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new ErreplikatuGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return btnErreplikatu;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

