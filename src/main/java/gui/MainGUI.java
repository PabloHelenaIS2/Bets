package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
import domain.Event;
import domain.RegularUser;
import domain.User;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonCreateEvent;
	private JButton jButtonCreateQuota;
	private JButton jButtonBackToMain;
	private JButton btnQueryQuotas;
	private JButton btnPutQuestionResult;
	private JButton btnCalculatePro;
	private JButton btnCancelEvent;
	private JButton jButtonTopBets;
	
	private Locale en = new Locale("en");
	private Locale es = new Locale("es");
	private Locale eus = new Locale("eus");
	
	//private static User user;
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin"));
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
			jContentPane.add(getBoton3());
			jContentPane.add(getBtnQueryQuotas());
			jContentPane.add(getJButtonCreateEvent());
			jContentPane.add(getBoton2());
			jContentPane.add(getJButtonCreateQuota());
			jContentPane.add(getBtnCancelEvent());
			jContentPane.add(getBtnPutQuestionResult());
			jContentPane.add(getBtnCalculatePro());
			jContentPane.add(getJButtonTopBets());
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
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuery;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
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
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		btnQueryQuotas.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQuotas"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		jButtonCreateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		jButtonCreateQuota.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuota"));
		jButtonBackToMain.setText(ResourceBundle.getBundle("Etiquetas").getString("BackToMenu"));
		btnCalculatePro.setText(ResourceBundle.getBundle("Etiquetas").getString("CalculateProfits"));
		btnPutQuestionResult.setText(ResourceBundle.getBundle("Etiquetas").getString("PutResult"));
		btnCancelEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CancelEvent"));
		jButtonTopBets.setText(ResourceBundle.getBundle("Etiquetas").getString("TopUsers"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Admin"));
	}
	
	private JButton getJButtonCreateEvent() {
		if (jButtonCreateEvent == null) {
			jButtonCreateEvent = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCreateEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
			jButtonCreateEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateEventGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateEvent;
	}
	
	private JButton getJButtonCreateQuota() {
		if (jButtonCreateQuota == null) {
			jButtonCreateQuota = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCreateQuota.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuota"));
			jButtonCreateQuota.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new CreateKuotaGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuota;
	}
	
	private JButton getJButtonBackToMain() {
		if (jButtonBackToMain == null) {
			jButtonBackToMain = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonBackToMain.setText(ResourceBundle.getBundle("Etiquetas").getString("BackToMenu"));
			jButtonBackToMain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoginGUI l = new LoginGUI();
					l.setBusinessLogic(getBusinessLogic());
					l.setVisible(true);
					dispose();
				}
			});
		}
		return jButtonBackToMain;
	}
	
	private JButton getBtnQueryQuotas() {
		if (btnQueryQuotas == null) {
			btnQueryQuotas = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			btnQueryQuotas.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQuotas"));
			btnQueryQuotas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new FindKuotaGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return btnQueryQuotas;
	}
	
	private JButton getBtnPutQuestionResult() {
		if (btnPutQuestionResult == null) {
			btnPutQuestionResult = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			btnPutQuestionResult.setText(ResourceBundle.getBundle("Etiquetas").getString("PutResult"));
			btnPutQuestionResult.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new PutResultGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return btnPutQuestionResult;
	}
	
	private JButton getBtnCalculatePro() {
		if (btnCalculatePro == null) {
			btnCalculatePro = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			btnCalculatePro.setText(ResourceBundle.getBundle("Etiquetas").getString("CalculateProfits"));
			btnCalculatePro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CalculateProfitsGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return btnCalculatePro;
	}
	private JButton getBtnCancelEvent() {
		if (btnCancelEvent == null) {
			btnCancelEvent = new JButton(); //$NON-NLS-1$ //$NON-NLS-2$
			btnCancelEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("CancelEvent"));
			btnCancelEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CancelEventGUI(new Vector<Event>());
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return btnCancelEvent;
	}
	private JButton getJButtonTopBets() {
		if (jButtonTopBets == null) {
			jButtonTopBets = new JButton(); 
			jButtonTopBets.setText(ResourceBundle.getBundle("Etiquetas").getString("TopUsers"));
			jButtonTopBets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new TopBetGUI();
					a.setAlwaysOnTop(true);
					a.setVisible(true);
				}
			});
		}
		return jButtonTopBets;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

