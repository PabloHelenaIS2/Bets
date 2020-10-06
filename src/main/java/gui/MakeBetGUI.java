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
public class MakeBetGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	// Class elements
	private double kop = 0;
	private Float min = 0f;
	private BLFacade facade;
	private Kuota k;
	private final ArrayList<Kuota> kuotak = new ArrayList<Kuota>(); 

	// Swing components
	private JPanel contentPane;

	// Labels
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel jLabelQuestions = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelQuotas = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quotas"));
	private final JLabel jLabelBets = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Apustuak")); //$NON-NLS-1$ //$NON-NLS-2$

	private final JLabel lblApustatzekoSarreraMinimoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApustatzekoSarreraMinimoa")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblApustuMin = new JLabel(); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblApustuaApustuMinimoa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ApustuaApustuMinimoa")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private final JLabel jLabelEbentuaAmaituDa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventFinished")); //$NON-NLS-1$ //$NON-NLS-2$

	// Buttons
	private final JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private final JButton jButtonApustuEgin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));; 
	private final JButton jButtonKuotaGehitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("KuotaGehitu")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton btnGarbitu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Garbitu")); //$NON-NLS-1$ //$NON-NLS-2$

	// Text fields
	private final JTextField txtAmount = new JTextField();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;

	// Main components and tables
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQuestions = new JScrollPane();
	private JScrollPane scrollPaneQuotas = new JScrollPane();
	private JScrollPane scrollPaneBets = new JScrollPane();
	
	private JTable tableEvents= new JTable();
	private JTable tableQuestions = new JTable();
	private JTable tableQuotas = new JTable();
	private JTable tableBets = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQuestions;
	private DefaultTableModel tableModelQuotas;
	private DefaultTableModel tableModelBets;

	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
	};

	private String[] columnNamesQuestions = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")
	};

	private String[] columnNamesQuotas = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("KuotakN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Kuotak"), 
	};
	
	private String[] columnNamesBets = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("ApustuN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Apustuak"), 
	};

	// Constructor
	public MakeBetGUI() {
		try {
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("MakeBetGUI"));
			jFind();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// Main method
	private void jFind() throws Exception {

		// Set the content pane properties 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setSize(new Dimension(793, 682));
		setContentPane(contentPane);

		// Set the label properties and add them to the content pane
		jLabelEventDate.setBounds(new Rectangle(40, 0, 140, 25));
		jLabelQuestions.setBounds(40, 181, 378, 14);
		jLabelEvents.setBounds(305, 4, 259, 16);
		jLabelQuotas.setBounds(447, 180, 204, 16);
		jLabelBets.setBounds(40, 333, 69, 14);

		getContentPane().add(jLabelEventDate, null);
		getContentPane().add(jLabelQuestions);
		getContentPane().add(jLabelEvents);
		getContentPane().add(jLabelQuotas);
		getContentPane().add(jLabelBets);
		
		lblApustatzekoSarreraMinimoa.setBounds(10, 567, 202, 14);
		lblApustuMin.setBounds(205, 567, 46, 14);
		lblApustuaApustuMinimoa.setBounds(40, 592, 378, 20);
		lblApustuaApustuMinimoa.setForeground(Color.RED);
		lblApustuaApustuMinimoa.setVisible(false);
		
		jLabelEbentuaAmaituDa.setBounds(296, 333, 122, 14);
		jLabelEbentuaAmaituDa.setForeground(Color.RED);
		jLabelEbentuaAmaituDa.setVisible(false);

		getContentPane().add(lblApustatzekoSarreraMinimoa);
		getContentPane().add(lblApustuMin);
		getContentPane().add(lblApustuaApustuMinimoa);
		getContentPane().add(jLabelEbentuaAmaituDa);

		// Set button properties and add them to the content pane
		jButtonClose.setBounds(new Rectangle(590, 559, 130, 30));
		jButtonApustuEgin.setBounds(413, 556, 128, 36);
		jButtonApustuEgin.setEnabled(false);
		jButtonKuotaGehitu.setBounds(499, 407, 140, 30);
		btnGarbitu.setBounds(499, 357, 140, 30);

		getContentPane().add(jButtonClose, null);
		getContentPane().add(jButtonApustuEgin);
		getContentPane().add(jButtonKuotaGehitu);
		getContentPane().add(btnGarbitu);
		
		// Set text field properties and add them to the content pane
		txtAmount.setBounds(302, 564, 86, 20);
		txtAmount.setColumns(10);
		//txtAmount.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBetGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$

		getContentPane().add(txtAmount);

		// Code for JCalendar
		jCalendar1.setBounds(new Rectangle(40, 20, 225, 150));

		getContentPane().add(jCalendar1, null);
		
		// Set main components and tables' properties
		// Scroll panels
		scrollPaneEvents.setBounds(new Rectangle(305, 20, 346, 150));
		scrollPaneQuestions.setBounds(new Rectangle(40, 206, 378, 116));
		scrollPaneQuotas.setBounds(new Rectangle(447, 206, 204, 116));
		scrollPaneBets.setBounds(new Rectangle(40, 358, 298, 116));

		getContentPane().add(scrollPaneEvents, null);
		getContentPane().add(scrollPaneQuestions, null);
		getContentPane().add(scrollPaneQuotas, null);
		getContentPane().add(scrollPaneBets, null);

		// Table models
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		tableModelQuestions = new DefaultTableModel(null, columnNamesQuestions);
		tableModelQuotas = new DefaultTableModel(null, columnNamesQuotas);
		tableModelBets = new DefaultTableModel(null, columnNamesBets);

		tableEvents.setModel(tableModelEvents);
		tableQuestions.setModel(tableModelQuestions);
		tableQuotas.setModel(tableModelQuotas);
		tableBets.setModel(tableModelBets);
		
		// Tables
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableQuestions.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQuestions.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableQuotas.getColumnModel().getColumn(0).setPreferredWidth(268);
		tableQuotas.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableBets.getColumnModel().getColumn(0).setPreferredWidth(268);
		tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		scrollPaneEvents.setViewportView(tableEvents);
		scrollPaneQuestions.setViewportView(tableQuestions);
		scrollPaneQuotas.setViewportView(tableQuotas);
		scrollPaneBets.setViewportView(tableBets);
		
		/* Action listeners */

		jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale"))
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jCalendar1.setCalendar(calendarMio);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));
					
					if (new Date().compareTo(firstDay)>0) {
						jLabelEbentuaAmaituDa.setVisible(true);
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelQuestions.setDataVector(null, columnNamesQuestions);
						tableModelQuotas.setDataVector(null, columnNamesQuotas);
					} else {
						jLabelEbentuaAmaituDa.setVisible(false);
						try {
							tableModelEvents.setDataVector(null, columnNamesEvents);
							tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

							facade=MainGUI.getBusinessLogic();

							Vector<domain.Event> events=facade.getEvents(firstDay);

							if (events.isEmpty())
								jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarMio.getTime()));
							else
								jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarMio.getTime()));
							for (domain.Event ev:events) {
								Vector<Object> row = new Vector<Object>();

								System.out.println("Events "+ev);

								row.add(ev.getEventNumber());
								row.add(ev.getDescription());
								row.add(ev);
								tableModelEvents.addRow(row);		
							}
							tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
							tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
							tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
						} catch (Exception e1) {
							jLabelQuestions.setText(e1.getMessage());
						}
					}
				}
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1);
				jButtonKuotaGehitu.setEnabled(false);
			} 
		});
		
		jButtonApustuEgin.setEnabled(false);
		jButtonKuotaGehitu.setEnabled(false);

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQuestions.setDataVector(null, columnNamesQuestions);
				tableModelQuestions.setColumnCount(3);
				if (queries.isEmpty())
					jLabelQuestions.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQuestions.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQuestions.addRow(row);	
				}
				tableQuestions.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQuestions.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQuestions.getColumnModel().removeColumn(tableQuestions.getColumnModel().getColumn(2));
				jButtonKuotaGehitu.setEnabled(false);
			}
		});
		
		tableQuestions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableQuestions.getSelectedRow();
				domain.Question qu=(domain.Question) tableModelQuestions.getValueAt(i,2); // obtain ev object
				
				System.out.println(qu.toString());
				Collection<Kuota> kuotak=qu.getKuotas();

				tableModelQuotas.setDataVector(null, columnNamesQuotas);
				tableModelQuotas.setColumnCount(3);
				
				for (domain.Kuota k:kuotak){
					Vector<Object> row = new Vector<Object>();

					row.add(k.getResult());
					row.add(k.getKuota());
					row.add(k);
					tableModelQuotas.addRow(row);	
				}
				tableQuotas.getColumnModel().getColumn(0).setPreferredWidth(268);
				tableQuotas.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQuotas.getColumnModel().removeColumn(tableQuotas.getColumnModel().getColumn(2));
				jButtonKuotaGehitu.setEnabled(false);
			}
		});
		
		tableQuotas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQuotas.getSelectedRow();
				k = (Kuota) tableModelQuotas.getValueAt(i,2);
				System.out.println(k.toString());
				jButtonKuotaGehitu.setEnabled(true);
			}
		});
		
		jButtonApustuEgin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (txtAmount.getText().equals(""))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("EremuHutsa"));
					Float kop= Float.parseFloat(txtAmount.getText());
					System.out.println(kop);
					if (kop < min) 
						throw new Exception(lblApustuaApustuMinimoa.getText());
					if (kuotak.isEmpty())
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("KuotakEzAukeratu"));
					RegularUser user = (RegularUser) LoginGUI.getUser();
					//////////////////////////
					Bet apustua = new Bet(user, kop, kuotak);
					System.out.println(apustua);
					facade.addBet(user,apustua);

					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("ApustuaSortuta"), "Info",
						JOptionPane.INFORMATION_MESSAGE);
					tableModelQuestions.setDataVector(null, columnNamesQuestions);
					tableModelQuestions.fireTableDataChanged();
					tableModelEvents.setDataVector(null, columnNamesEvents);
					tableModelEvents.fireTableDataChanged();
					tableModelQuotas.setDataVector(null, columnNamesQuotas);
					tableModelQuotas.fireTableDataChanged();
					jLabelQuestions.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
					jLabelEventDate.setText(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
				} catch (NoMoneyException el) {
					System.out.println("1");
				JOptionPane.showMessageDialog(getContentPane(), el.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (BetAlreadyExist el) {
					System.out.println("2");
				JOptionPane.showMessageDialog(getContentPane(), el.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (MovementAlreadyExistsException el) {
					System.out.println("3");
					JOptionPane.showMessageDialog(getContentPane(), el.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Negative el) {
					System.out.println("4");
					JOptionPane.showMessageDialog(getContentPane(), el.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					System.out.println("5");
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					txtAmount.setText("");
					lblApustuMin.setText("");
					tableModelBets.setDataVector(null, columnNamesBets);
					jButtonApustuEgin.setEnabled(false);
				}
			}
		});
		
		btnGarbitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				min = 0f;
				kop = 0f;
				kuotak.clear();
				tableModelBets.setDataVector(null, columnNamesBets);
				lblApustuMin.setText("");
				jButtonApustuEgin.setEnabled(false);
			}
		});
		
		jButtonKuotaGehitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonApustuEgin.setEnabled(true);
				jButtonKuotaGehitu.setEnabled(false);
				 Vector<Object> apustuaktabla = new Vector<Object>();
				//
				int i = tableQuotas.getSelectedRow();
			 	
				k = (Kuota) tableModelQuotas.getValueAt(i,2);
				if (kuotak.contains(k)) 
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("IadaGehitua"), "Info", JOptionPane.INFORMATION_MESSAGE);
				else {
					lblApustuMin.setText(Float.toString(min + k.getQuestion().getBetMinimum()));
					// obtain ev object
					if (kuotak.isEmpty())
						kop = k.getKuota();

					min += k.getQuestion().getBetMinimum();
					// TODO hau ahal bada optimizatu
					for (Kuota ku : kuotak)
						if (ku.getQuestion().equals(k.getQuestion())) {
							min -= k.getQuestion().getBetMinimum();
							for (int j = 0; j < tableModelBets.getRowCount(); ++j)
								if (tableModelBets.getValueAt(j, 2).equals(ku)) {
									lblApustuMin.setText(Float.toString(Float.parseFloat(lblApustuMin.getText()) - k.getQuestion().getBetMinimum()));
									tableModelBets.removeRow(j);
									break;
								}
							kuotak.remove(ku);
							break;
						}

					kuotak.add(k);
					System.out.println(">>>");
					for (Kuota k : kuotak)
						System.out.println(k);
					System.out.println("<<<");

					tableModelBets.setColumnCount(3);

					apustuaktabla.add(k.getResult());
					apustuaktabla.add(k.getKuota());
					apustuaktabla.add(k);

					tableModelBets.addRow(apustuaktabla);

					tableBets.getColumnModel().getColumn(0).setPreferredWidth(268);
					tableBets.getColumnModel().getColumn(1).setPreferredWidth(268);
					tableBets.getColumnModel().removeColumn(tableBets.getColumnModel().getColumn(2));
					tableBets.setEnabled(false);
					
				}
			}
		});

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}