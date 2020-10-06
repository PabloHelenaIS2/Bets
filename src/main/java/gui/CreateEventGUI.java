package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import dataAccess.DataAccess;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;

public class CreateEventGUI extends JFrame {

	private JPanel contentPane;
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private DataAccess facade;
	private JTextField textField;
	private JLabel jLabelMsg = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEventGUI frame = new CreateEventGUI();
					frame.setBusinessLogic(new DataAccess());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setBusinessLogic(DataAccess dataAccess) {facade = dataAccess;}

	/**
	 * Create the frame.
	 */
	public CreateEventGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEventGUI"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		jCalendar.setBounds(new Rectangle(80, 30, 250, 150));
		this.getContentPane().add(jCalendar, null);

		JLabel lblEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Event"));
		lblEvent.setBounds(160, 11, 70, 14);
		contentPane.add(lblEvent);

		textField = new JTextField();
		textField.setBounds(80, 191, 250, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		jLabelMsg.setBounds(80, 183, 250, 14);
		contentPane.add(jLabelMsg);
		this.getContentPane().add(jLabelMsg, null);

		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
				//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					jCalendar.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					//try {


					//}
					/*
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarMio.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}
					 */
				}
				//paintDaysWithEvents(jCalendar);

			}
		});
		JButton btnSortuGertaera = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SortuGertaera"));
		btnSortuGertaera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (textField.getText().equals(""))
						throw new Exception(ResourceBundle.getBundle("Etiquetas").getString("EremuHutsa"));
					BLFacade facade = MainGUI.getBusinessLogic();
					facade.createEvent(UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime())),textField.getText());
					JOptionPane.showMessageDialog(getContentPane(), ResourceBundle.getBundle("Etiquetas").getString("NewEvent"), ResourceBundle.getBundle("Etiquetas").getString("Oharra"),
							JOptionPane.INFORMATION_MESSAGE);
				}catch(EventFinished el) {
					JOptionPane.showMessageDialog(contentPane, ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"), "Error",
							JOptionPane.ERROR_MESSAGE);
				}catch (EventAlreadyExist e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}finally {
					textField.setText("");
				}
			}
		});
		btnSortuGertaera.setBounds(80, 222, 150, 28);
		contentPane.add(btnSortuGertaera);

		JButton btnItxi = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnItxi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnItxi.setBounds(249, 222, 81, 26);
		contentPane.add(btnItxi);


	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
