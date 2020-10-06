package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Kuota;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class CreateKuotaGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	private BLFacade facade = MainGUI.getBusinessLogic();

	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
	};

	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")
	};

	private JTextField textEmaitza;
	private JTextField textField;
	private JButton btnKuotaSortu;

	private Vector<domain.Event> events;

	public CreateKuotaGUI() {
		try {
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateKuotaGUI"));
			jbInit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(92, 211, 452, 16);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		final JLabel lblEbentuaAmaituDa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventFinished")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEbentuaAmaituDa.setForeground(Color.RED);
		lblEbentuaAmaituDa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEbentuaAmaituDa.setBounds(67, 199, 140, 14);
		getContentPane().add(lblEbentuaAmaituDa);
		lblEbentuaAmaituDa.setVisible(false);

		jButtonClose.setBounds(new Rectangle(382, 420, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());

				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jCalendar1.setCalendar(calendarMio);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));
					lblEbentuaAmaituDa.setVisible(false);
					if (new Date().compareTo(firstDay)>0) {
						lblEbentuaAmaituDa.setVisible(true);
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelQueries.setDataVector(null, columnNamesQueries);
						jLabelQueries.setText("");
						btnKuotaSortu.setEnabled(false);
					} else {
						btnKuotaSortu.setEnabled(true);
						lblEbentuaAmaituDa.setVisible(false);
						try {
							tableModelEvents.setDataVector(null, columnNamesEvents);
							tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

							BLFacade facade=MainGUI.getBusinessLogic();

							events=facade.getEvents(firstDay);

							if (events.isEmpty()) {
								jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarMio.getTime()));
								tableModelQueries.setDataVector(null, columnNamesQueries);
								jLabelQueries.setText("");
								btnKuotaSortu.setEnabled(false);
							} else
								jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarMio.getTime()));

							for (domain.Event ev:events) {
								Vector<Object> row = new Vector<Object>();

								System.out.println("Events "+ev);

								row.add(ev.getEventNumber());
								row.add(ev.getDescription());
								row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
								tableModelEvents.addRow(row);		
							}
							tableEvents.getColumnModel().getColumn(0).setPreferredWidth(100);
							tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
							tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable

						} catch (Exception e1) {

							jLabelQueries.setText(e1.getMessage());
						}

					}
				}
				CreateQuestionGUI.paintDaysWithEvents(jCalendar1);
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		btnKuotaSortu = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuota")); //$NON-NLS-1$ //$NON-NLS-2$
		btnKuotaSortu.setEnabled(false);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(92, 238, 406, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				///
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(3);

				btnKuotaSortu.setEnabled(false);
				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());
				for (domain.Question q:queries) {
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q);
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2)); // not shown in JTable
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnKuotaSortu.setEnabled(true);
			}
		});

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);

		btnKuotaSortu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get the question
				int i = tableQueries.getSelectedRow();
				domain.Question qu = (domain.Question) tableModelQueries.getValueAt(i, 2);
				// Get the quota
				try {
					if (textEmaitza.getText().equals("") || textField.getText().equals(""))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("EremuHutsak"));
					for(Kuota k: qu.getKuotas()) {
						if(k.getResult().equals(textEmaitza.getText()))
							throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("KuotaExistitu"));
					}
					float kuota = Float.parseFloat(textField.getText());
					String result = textEmaitza.getText();
					facade.createKuota(qu, result, kuota);
					JOptionPane.showMessageDialog(getContentPane(), ResourceBundle.getBundle("Etiquetas").getString("NewFee"), ResourceBundle.getBundle("Etiquetas").getString("Oharra"),
							JOptionPane.INFORMATION_MESSAGE);
					tableModelQueries.setDataVector(null, columnNamesQueries);
					tableModelQueries.fireTableDataChanged();
					tableModelEvents.setDataVector(null, columnNamesEvents);
					tableModelEvents.fireTableDataChanged();
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
					jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events"));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}finally {
					textField.setText("");
					textEmaitza.setText("");
				}
			}
		});
		btnKuotaSortu.setBounds(203, 420, 123, 30);
		getContentPane().add(btnKuotaSortu);

		JLabel lblEmaitza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Emaitza"));
		lblEmaitza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmaitza.setBounds(122, 375, 93, 25);
		getContentPane().add(lblEmaitza);

		textEmaitza = new JTextField();
		textEmaitza.setBounds(225, 377, 101, 20);
		getContentPane().add(textEmaitza);
		textEmaitza.setColumns(10);

		JLabel lblKuota = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Kuota"));
		lblKuota.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKuota.setBounds(356, 375, 93, 25);
		getContentPane().add(lblKuota);

		textField = new JTextField();
		textField.setBounds(459, 377, 101, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
