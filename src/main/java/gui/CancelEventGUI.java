package gui;

import java.text.DateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Movement;
import domain.RegularUser;
import exceptions.EventFinished;
import exceptions.MovementAlreadyExistsException;
import exceptions.Negative;
import exceptions.QuestionAlreadyExist;

public class CancelEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	BLFacade facade = MainGUI.getBusinessLogic();

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CancelEvent"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	public CancelEventGUI(Vector<domain.Event> v) {
		try {
			setTitle(ResourceBundle.getBundle("Etiquetas").getString("CancelEventGUI"));
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCancel.setBounds(new Rectangle(100, 275, 165, 30));
		jButtonCancel.setEnabled(false);

		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCancel_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(349, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCancel, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		final JLabel lblEbentuaBukatuDa = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventFinished")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEbentuaBukatuDa.setForeground(Color.RED);
		lblEbentuaBukatuDa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEbentuaBukatuDa.setBounds(100, 211, 130, 30);
		getContentPane().add(lblEbentuaBukatuDa);
		lblEbentuaBukatuDa.setVisible(false);

		// Code for JCalendar
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
					if (new Date().compareTo(firstDay)>0) {
						lblEbentuaBukatuDa.setVisible(true);
						jComboBoxEvents.removeAllItems();}
						
					else {
						lblEbentuaBukatuDa.setVisible(false);
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
							jButtonCancel.setEnabled(false);
						else
							jButtonCancel.setEnabled(true);
				}
				paintDaysWithEvents(jCalendar);
			}
		}});
	}

	
	public static void paintDaysWithEvents(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		Vector<Date> dates=facade.getEventsMonth(jCalendar.getDate());
			
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		//int today=calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;
		
		
	 	for (Date d:dates){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
	 		calendar.set(Calendar.DAY_OF_MONTH, 1);
	 		calendar.set(Calendar.MONTH, month);
	 	
	}
	
	
	private void jButtonCancel_actionPerformed(ActionEvent e) {
		try {
			domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
			facade.cancelEvent(event);
			JOptionPane.showMessageDialog(getContentPane(), ResourceBundle.getBundle("Etiquetas").getString("GertaeraBorratua"), ResourceBundle.getBundle("Etiquetas").getString("Oharra"),
					JOptionPane.INFORMATION_MESSAGE);
			modelEvents.removeAllElements();
		}catch(EventFinished el) {
			JOptionPane.showMessageDialog(getContentPane(), ResourceBundle.getBundle("Etiquetas").getString("EventFinished"), "Error",
					JOptionPane.ERROR_MESSAGE);
		}catch(Negative el) {
			JOptionPane.showMessageDialog(getContentPane(), ResourceBundle.getBundle("Etiquetas").getString("MoneyPositive"), "Error",
					JOptionPane.ERROR_MESSAGE);
		}catch(MovementAlreadyExistsException el) {
			JOptionPane.showMessageDialog(getContentPane(), el.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}finally {
			jButtonCancel.setEnabled(false);
		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}