package hibernate;

import org.hibernate.Session;

public class DatabaseManager {
	 private Session session = null;
	 public DatabaseManager(Session session) {
	  if(session == null)
	    throw new 
	      RuntimeException("Invalid session object.");
	  this.session = session;
	 }
	 public void saveUser(HibernateClass user){
	  session.save(user);
	 }
	 public void updateUser(HibernateClass user){
	  session.update(user);
	 }
	 public void deleteUser(HibernateClass user) {
	  session.delete(user);
	 }

}
