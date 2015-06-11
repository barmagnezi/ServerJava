package hibernate;

import hibernate.HibernateClass;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HiberTest {

	public static void main(String[] args) {
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(HibernateClass.class);
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true, true);
		
		/*AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(HibernateClass.class);
		config.configure();
		
		//new SchemaExport(config).create(true, true);
		
		SessionFactory factory = config.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		/*
		session.beginTransaction();
		HibernateClass HB1 = new HibernateClass();
		HB1.setName("senia2Maze");
		HB1.setMaze("10100110001010011");
		HB1.setSolution("42");
		session.save(HB1);
		
		session.getTransaction().commit();
		
		Session session2 = factory.openSession();
		
		//List<HibernateClass> result = (List<HibernateClass>) session2.createQuery("from HibernateClass").list();
		ArrayList<HibernateClass> qu =  (ArrayList) session2.createQuery("from HibernateClass").list();
		
		Iterator<HibernateClass> it = qu.iterator();
		HibernateClass HC;
		while(it.hasNext()){
			HC=it.next();
			System.out.println("NEW LINE: ");
			System.out.println(HC.getId());
			System.out.println(HC.getName());
			System.out.println(HC.getMaze());
			System.out.println(HC.getSolution());
		}
		System.out.println("SENIA: "+it);
		//System.out.println(result);
		
		///org.hibernate.Query q = session2.createQuery("from HibernateClass");
		///System.out.println("HERE "+q.toString());
		session2.close();
		*/
	}

}
