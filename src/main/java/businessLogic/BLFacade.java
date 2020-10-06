package businessLogic;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

//import domain.Booking;
import domain.*;
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  { 
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
		
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod public void createNewUser(RegularUser user) throws UserAlreadyExistsException;
	
	@WebMethod public User getUserByUsername(String userName) throws UserDoesNotExistException;
	
	@WebMethod public User login(String userName, String password) throws UserDoesNotExistException,IncorrectPassException;
	
	@WebMethod public Kuota createKuota(Question question, String result, float kuota) throws EventFinished, KuotaAlreadyExist;
	
	@WebMethod public  Event createEvent(Date data, String event) throws EventAlreadyExist,EventFinished;
	
	@WebMethod public void updateQueResult(int zein, String result);
	
	@WebMethod public void addBet(RegularUser user,Bet bet) throws BetAlreadyExist,Negative,MovementAlreadyExistsException, NoMoneyException;
	
	@WebMethod public void restMoney(float kop,RegularUser user) throws Negative, NoMoneyException;
	
	@WebMethod public double putMoney(float kop,RegularUser user) throws Negative;
	
	@WebMethod public float howMuchMoney(RegularUser user);
	
	//@WebMethod public Collection<Bet> getBetsByUserAndQuota(RegularUser user,ArrayList<Kuota> kuota); 
	
	@WebMethod public Collection<Bet> getBetsByUser(RegularUser user);
	
	@WebMethod public void removeBet(Bet bet) throws Negative, MovementAlreadyExistsException, EventFinished;
	
	@WebMethod public void createMovement(Movement movement) throws MovementAlreadyExistsException;
	
	@WebMethod public Collection<Movement> getMovementsByUser(RegularUser user);

	@WebMethod public Vector<Kuota> getKuotas(int Qzein);
	
	@WebMethod public void calculateProfits(Bet bet) throws Negative, MovementAlreadyExistsException, ResultNotEqual, EventFinished;

	@WebMethod public Question getQuestion(int qZein);
	
	@WebMethod public void cancelEvent(Event event) throws Negative, MovementAlreadyExistsException, EventFinished;
	
	@WebMethod public ArrayList<RegularUser>getUsers();
	
	@WebMethod public boolean betExists(RegularUser user , Bet bet) ;
}
