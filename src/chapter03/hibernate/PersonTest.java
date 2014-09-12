package chapter03.hibernate;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PersonTest {

	SessionFactory factory;
	
	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void setup(){
		
		Configuration configuration = new Configuration();
		configuration.configure();
		@SuppressWarnings("deprecation")
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry); 
		
	}
	
	@AfterMethod
	public void shutdown(){
		
		factory.close();
	}
	
	@Test
	public void testSavePerson(){
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Person person = new Person();
		person.setName("J.C. Smell");
		
		session.save (person);
		tx.commit();
		session.close();
	}
	
	@Test
	public void testSaveRanking(){
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Person subject = savePerson(session, "J. C. Smell");
		Person observer = savePerson(session, "Drew Lombardo");
		
		Skill skill = saveSkill (session, "Java");
		
		Ranking ranking = new Ranking();
		ranking.setSubject(subject);
		ranking.setObserver(observer);
		ranking.setSkill(skill);
		session.save(ranking);
		
		tx.commit();
		session.close();
	}
	
	@Test
	public void testRankings (){
		
	}
	
	private void populateRankingData(){
		
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		session.beginTransaction();
		
		
		
	}
	
	private Person findPerson (Session session, String name){
			
			Query query = session.createQuery("from Person p where p.name=:name");
			query.setParameter("name", name);
			Person person = (Person) query.uniqueResult();
			return person;
		}
		
		private Person savePerson(Session session, String name){
			
			Person person  = findPerson(session, name);
			
			if(person==null){
				
				person = new Person();
				person.setName(name);
				session.save(person);
			}
			return person;
		}
		
		private Skill saveSkill(Session session, String skillName){
			
			Skill skill  = findSkill(session, skillName);
			
			if(skill==null){
				
				skill = new Skill();
				skill.setName(skillName);
				session.save(skill);
			}
			return skill;
		}
		
		private Skill findSkill(Session session, String skillName){
			
			Query query = session.createQuery("from Skill s where s.name=:skillName");
			query.setParameter("skillName", skillName);
			Skill skill = (Skill) query.uniqueResult();
			return skill;
		}
	
}