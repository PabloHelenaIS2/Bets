package businessLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.RegularUser;
import domain.User;
import domain.Bet;
import domain.Event;
import domain.IntegerAdapter;
import domain.Kuota;
import domain.Movement;
import exceptions.*;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
			}
		
	}
	

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
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
	    DataAccess dBManager=new DataAccess();
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dBManager.createQuestion(event,question,betMinimum);		

		dBManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		DataAccess dbManager=new DataAccess();
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		DataAccess dbManager=new DataAccess();
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
		DataAccess dBManager=new DataAccess();
		dBManager.initializeDB();
		dBManager.close();
	}

    @WebMethod
	public void createNewUser(RegularUser user) throws UserAlreadyExistsException {
		DataAccess dbManager = new DataAccess();
		dbManager.createNewUser(user);
		dbManager.close();
	}

    @WebMethod
	public User getUserByUsername(String userName) throws UserDoesNotExistException {
		DataAccess dbManager = new DataAccess();
		User user = dbManager.getUserByUsername(userName);
		dbManager.close();
		return user;
	}
    
    @WebMethod 
    public User login(String userName, String password) throws UserDoesNotExistException,IncorrectPassException{
    	DataAccess dbManager = new DataAccess();
    	User login = dbManager.login(userName, password);
    	dbManager.close();
    	return login;
    }
    
    @WebMethod
    public Kuota createKuota(Question question, String result, float kuota) throws EventFinished, KuotaAlreadyExist {
 	   
 	    //The minimum bed must be greater than 0
 	    DataAccess dBManager=new DataAccess();
 		Kuota qry=null;
 		
 	    
 		//if(new Date().compareTo(question.getEvent().getEventDate())>0)
 		//throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
 				//Koutak sortu GUI-an inplementatua
 		
 		 qry=dBManager.createKuota(question, result, kuota);		

 		dBManager.close();
 		
 		return qry;
    }

    @WebMethod
	public Event createEvent(Date data, String event) throws EventAlreadyExist, EventFinished {
		DataAccess dBManager=new DataAccess();
		if(new Date().compareTo(data)>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		Event e = dBManager.createEvent(data,event);		
		dBManager.close();	
		return e; 
	}
	
	@WebMethod public void updateQueResult(int zein, String result) {
		DataAccess dBManager = new DataAccess();
		dBManager.updateQueResult(zein, result);
		dBManager.close();
	}
	
	@WebMethod
	public void addBet(RegularUser user,Bet bet) throws BetAlreadyExist,Negative,MovementAlreadyExistsException, NoMoneyException{
		DataAccess dBManager = new DataAccess();
		System.out.println("DAD");

		dBManager.addBet(user, bet);
		dBManager.close();
	}
	
	@WebMethod
	public void restMoney(float kop,RegularUser user) throws Negative, NoMoneyException {
		DataAccess dBManager = new DataAccess();
		dBManager.restMoney( kop,  user);
		dBManager.close();
	}
	
	@WebMethod
	public double putMoney(float kop,RegularUser user) throws Negative {
		DataAccess dBManager = new DataAccess();
		dBManager.putMoney( kop,  user);
		dBManager.close();
		return kop;
	}
	
	@WebMethod
	public float howMuchMoney(RegularUser user) {
		DataAccess dBManager = new DataAccess();
		Float money=dBManager.howMuchMoney(user);
		dBManager.close();
		return money;
	}
	
	/*
	@WebMethod
	public Collection<Bet> getBetsByUser(RegularUser user,ArrayList<Kuota> kuota) {
		DataAccess dBManager = new DataAccess();
		Collection<Bet> bets =dBManager.getBetsByUser( user,kuota);
		dBManager.close();
		return bets;
	}
	*/
	
	@WebMethod
	public Collection<Bet> getBetsByUser(RegularUser user) {
		DataAccess dBManager = new DataAccess();
		Collection<Bet> bets =dBManager.getBetsByUser(user);
		dBManager.close();
		return bets;
	}
	 
	@WebMethod
	public void removeBet(Bet bet) throws Negative, MovementAlreadyExistsException,EventFinished {
		DataAccess dBManager = new DataAccess();
		dBManager.removeBet(bet);
		dBManager.close();
	}

	@WebMethod public void createMovement(Movement movement) throws MovementAlreadyExistsException {
		DataAccess dbManager = new DataAccess();
		dbManager.createMovement(movement);
		dbManager.close();
	}

	@WebMethod public Collection<Movement> getMovementsByUser(RegularUser user) {
		DataAccess dbManager = new DataAccess();
		Collection<Movement> movements = dbManager.getMovementsByUser(user);
		dbManager.close();
		return movements;
	}

	@WebMethod 
	public Vector<Kuota> getKuotas(int qZein) {
		DataAccess dbManager = new DataAccess();
		Vector<Kuota> kuotas = dbManager.getKuotas(qZein);
		dbManager.close();
		return kuotas;
	}
	
	@WebMethod 
	public void calculateProfits(Bet bet) throws Negative, MovementAlreadyExistsException, ResultNotEqual,EventFinished{
		DataAccess dbManager = new DataAccess();
		dbManager.calculateProfits(bet);
		dbManager.close();
	}
	
	@WebMethod 
	public Question getQuestion(int qZein) {
		DataAccess dbManager = new DataAccess();
		Question question = dbManager.getQuestion(qZein);
		dbManager.close();
		return question;
	}
	
	@WebMethod 
	public void cancelEvent(Event event) throws Negative, MovementAlreadyExistsException, EventFinished {
		DataAccess dBManager = new DataAccess();
		dBManager.cancelEvent(event);
		dBManager.close();
	}
	
	@WebMethod 
	public ArrayList<RegularUser>getUsers(){
		DataAccess dBManager = new DataAccess();
		ArrayList<RegularUser>users=dBManager.getUsers();
		return users;
	}
	@WebMethod 
	public boolean betExists(RegularUser user , Bet bet) {
		DataAccess dBManager = new DataAccess();
		boolean exist= dBManager.betExists(user, bet);
		dBManager.close();
		return exist;
		
	}
	
}

