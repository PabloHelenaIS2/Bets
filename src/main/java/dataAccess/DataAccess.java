package dataAccess;


import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.IntegerAdapter;
import domain.Kuota;
import domain.Movement;
import domain.MovementId;

import java.util.ArrayList;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.*;
import gui.LoginGUI;
//import javafx.scene.chart.PieChart.Data;
import gui.MainGUI;
import gui.RegisteredUserGUI;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c;

	public DataAccess(boolean initializeMode)  {
		
		c=ConfigXML.getInstance();
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode)
			fileName=fileName+";drop";
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
	}

	public DataAccess()  {	
		 new DataAccess(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {

			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}  
	    
			Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
			

			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month,28));
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
					
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				
			}
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);
	
	        
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);			
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question, betMinimum);
			q.setEvent(event);
			//db.persist(q);
			db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return q;
		
	}
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}
	
	public int getEventCount() {
		TypedQuery<Event> qu = db.createQuery("SELECT ev FROM Event ev", Event.class);
		return qu.getResultList().size();
	}
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	
	public void createNewUser(RegularUser user) throws UserAlreadyExistsException {
		User exists = db.find(User.class, user.getUserName());
		if (exists != null)
			throw new UserAlreadyExistsException(user.getUserName() + ResourceBundle.getBundle("Etiquetas").getString("AlreadyExist"));
		db.getTransaction().begin();
		db.persist(user);
		db.persist(user.getWallet());
		db.getTransaction().commit();
	}
	
	public User getUserByUsername(String userName) throws UserDoesNotExistException {
		User target = db.find(User.class, userName);
		if (target == null)
			throw new UserDoesNotExistException(ResourceBundle.getBundle("Etiquetas").getString("UserNotFound"));
		return target;
	}
	
	public User login(String userName, String password) throws UserDoesNotExistException,IncorrectPassException {
		User target = db.find(User.class, userName);
		if (target == null)
			throw new UserDoesNotExistException(ResourceBundle.getBundle("Etiquetas").getString("UserNotFound"));
		//"User not found in the database."
		if (!password.equals(target.getUserPass()))
			throw new IncorrectPassException(ResourceBundle.getBundle("Etiquetas").getString("IncorrectPass"));
		//"Incorrect password"
		return target;
	}
	
	
	
	public Kuota createKuota(Question question, String result, float kuota) throws  KuotaAlreadyExist {
		
		System.out.println(">> DataAccess: createKuota=> event= " + question.getEvent() + " question= " + question.getQuestion() +" betMinimum=" + question.getBetMinimum() + " result= " + result + " kuota= " + kuota);
		System.out.println("BAG");
			
		
		//galderaEguneratu(question);
			Question qu = db.find(Question.class, question.getQuestionNumber());
			
		
		
			if (qu.DoesKuotaExists(result)) throw new KuotaAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorKuotaAlreadyExist"));
			
			db.getTransaction().begin();
			Kuota k = new Kuota(result,kuota,qu);
			qu.add(k);
			//Kuota k = qu.addKuota(result, kuota);
			//k.setQuestion(qu);
			//db.persist(q);
			//db.persist(qu); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
			db.persist(k);				// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.persist(qu);
			db.getTransaction().commit();
			
			return k;
		
	}

	public Event createEvent(Date data, String event) throws EventAlreadyExist{
		System.out.println(">> DataAccess: createEvent=> event= " + event);
		boolean found = false;	
		Vector<Event> events = getEvents(data);
		for(Event e:events) {
			if(e.getDescription().equals(event))
				found = true;
		}
		if(found) 
			throw new EventAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventAlreadyExist"));
		db.getTransaction().begin();
		Event e= new Event(event,data);
		db.persist(e);
		db.getTransaction().commit();
		System.out.println(ResourceBundle.getBundle("Etiquetas").getString("GertaeraGordeta"));
		return e;		
	}
	
	public void updateQueResult(int zein, String result) {
		Question q = db.find(Question.class, zein);
		db.getTransaction().begin();
		q.setResult(result);
		db.getTransaction().commit();
		System.out.println(q + ResourceBundle.getBundle("Etiquetas").getString("ResultUpdate"));
	}
	
	
	public void addBet(RegularUser user, Bet bet) throws BetAlreadyExist,Negative,MovementAlreadyExistsException, NoMoneyException {
		RegularUser user2 = db.find(RegularUser.class, user.getUserName());
		
		System.out.println(bet.getKuota());

		ArrayList<Kuota> ku = new ArrayList<Kuota>();
		for(Kuota k: bet.getKuota())
			ku.add(db.find(Kuota.class, k.getKuotaNumber()));
		restMoney(bet.getBet(), user);
		createMovement(new Movement(user, new Date(), bet.getBet(), ResourceBundle.getBundle("Etiquetas").getString("BetMade")));
		db.getTransaction().begin(); 
		bet.setKuota(ku);
		System.out.println(bet);
		Bet apus=user2.addBet(bet);
		db.persist(apus);
		db.getTransaction().commit();		
	}
	
	public void restMoney(float kop, RegularUser user) throws Negative, NoMoneyException {
		if(howMuchMoney(user)<kop)
			throw new NoMoneyException(ResourceBundle.getBundle("Etiquetas").getString("NoMoney"));
		if(kop>=0) {
			RegularUser user2 = db.find(RegularUser.class, user.getUserName());
			db.getTransaction().begin();
			user2.setWallet(user2.getWallet()-kop);
			db.getTransaction().commit();
		}
		else {
			throw new Negative();
		}
	}
	
	public double putMoney(float kop, RegularUser user)  throws Negative {
		if(kop>=0) {
			RegularUser user2 = db.find(RegularUser.class, user.getUserName());
			db.getTransaction().begin();
			user2.setWallet(user2.getWallet() + kop);
			db.getTransaction().commit();
			}
			else {
				throw new Negative();
			}
		return kop;
		
	}
	
	public float howMuchMoney(RegularUser user) {
		RegularUser user2 = db.find(RegularUser.class, user.getUserName());
		db.getTransaction().begin();
		Float money = user2.getWallet();
		db.getTransaction().commit();
		return money;
	}
	
	public Collection<Bet> getBetsByUser(RegularUser user,ArrayList<Kuota> kuota) {
		TypedQuery<Bet> query = db.createQuery("SELECT ap FROM Bet ap WHERE ap.getKuota().toString()="+kuota.toString()+" AND ap.getUser().getUserName()='"+user.getUserName()+"'",Bet.class);   
		Collection<Bet> bets = (Collection<Bet>)query.getResultList();
	 	return bets;
	}
	
	public Collection<Bet> getBetsByUser(RegularUser user) {
		TypedQuery<Bet> query = db.createQuery("SELECT ap FROM Bet ap WHERE ap.getUser()=?1", Bet.class);   
		query.setParameter(1, user);
	 	return (Collection<Bet>)query.getResultList();
	}
	
	public boolean betExists(RegularUser user , Bet bet) {
		TypedQuery<Bet> query = db.createQuery("SELECT ap FROM Bet ap WHERE ap.getUser().getUserName()='"+user.getUserName()+"'",Bet.class);   
		Collection<Bet> bets = (Collection<Bet>)query.getResultList();
		if (bets.size()==0) {
			return false;
		}
		for(Bet beta: bets) {
			if (beta.equals(bet)) 
			 return true;
		}
		return false;		
	}
	
	public void removeBet(Bet bet) throws Negative, MovementAlreadyExistsException,EventFinished {
		TypedQuery<Bet> query1 = db.createQuery("SELECT ap FROM Bet ap WHERE ap.getBetNumber() = ?1",Bet.class);
		query1.setParameter(1, bet.getBetNumber());
		Bet bet1 = query1.getSingleResult();
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Bet b WHERE b.getBetNumber()="+bet.getBetNumber());   
		int bets = query.executeUpdate();
		System.out.println("Deleted bet " + bets);
		db.getTransaction().commit();
	}
	 
	public void createMovement(Movement movement) throws MovementAlreadyExistsException {
		Movement exists = db.find(Movement.class, new MovementId(movement.getUser(), movement.getDate()));
		
		if (exists != null)
			throw new MovementAlreadyExistsException(String.format("%s %s already exists.", movement.getUser().getUserName(), movement.getDate()));
		
		db.getTransaction().begin();
		db.merge(movement);
		db.getTransaction().commit();
		
	}
	
	public Collection<Movement> getMovementsByUser(RegularUser user) {
		TypedQuery<Movement> query = db.createQuery("SELECT mo FROM Movement mo WHERE mo.getUser() = ?1", Movement.class);
		query.setParameter(1, user);
		return (Collection<Movement>) query.getResultList();
	}

	public Vector<Kuota> getKuotas(int qZein) {
		System.out.println(">> DataAccess: getKuotas");
		Vector<Kuota> res = new Vector<Kuota>();	
		Question question =db.find(Question.class,qZein);
		TypedQuery<Kuota> query = db.createQuery("SELECT k FROM Kuota k WHERE k.question=?1",Kuota.class);   
		query.setParameter(1, question);
		List<Kuota> kuotas = query.getResultList();
	 	for (Kuota k:kuotas)	 
		   res.add(k);
	 	return res;
	}
	
	public void calculateProfits(Bet bet) throws Negative, MovementAlreadyExistsException, ResultNotEqual, EventFinished{
		Bet bet1 = db.find(Bet.class, bet);
		float moneyWon = 1;
		for(Kuota k: bet1.getKuota()) {
			moneyWon = moneyWon * k.getKuota(); 
		}
		bet1.getUser().setMoneyWon(putMoney((moneyWon * bet1.getBet()),bet1.getUser()));
		createMovement(new Movement(bet1.getUser(), new Date(), (moneyWon * bet1.getBet()), ResourceBundle.getBundle("Etiquetas").getString("MoneyWonBet")));
	}
	
	public Question getQuestion(int qZein) {
		return db.find(Question.class, qZein);
	}
	
	public void cancelEvent(Event event) throws Negative,MovementAlreadyExistsException, EventFinished {
		Event event1 = db.find(Event.class, event);
		TypedQuery<Bet> query1 = db.createQuery("SELECT ap FROM Bet ap",Bet.class);
		query1.setParameter(1, event.getEventNumber());
		List<Bet> bets = query1.getResultList();
		boolean find = false;
		for(Bet bet : bets) {
			find = false;
			for(Kuota k: bet.getKuota()) {
				if(k.getQuestion().getEvent().getEventNumber() == event1.getEventNumber()) {
					find = true;
				}
			}		
			if(find) {
				this.putMoney(bet.getBet(), bet.getUser());
				createMovement(new Movement(bet.getUser(), new Date(), bet.getBet(), ResourceBundle.getBundle("Etiquetas").getString("EventRemoved")));
				removeBet(bet);
			}
		}
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Event e WHERE e.getEventNumber() = ?1");
		query.setParameter(1, event.getEventNumber());   
		int events = query.executeUpdate();
		System.out.println("Deleted event " + events);
		db.getTransaction().commit();
	}
	
	public ArrayList<RegularUser> getUsers(){
		ArrayList<RegularUser>users=new ArrayList<RegularUser>();
		TypedQuery<RegularUser>query1=db.createQuery("SELECT u FROM RegularUser u", RegularUser.class);
		List<RegularUser>user=query1.getResultList();
		for (RegularUser u: user) {
			users.add(u);
		}
		return users;
	}
}
