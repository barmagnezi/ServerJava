package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


/**
* The HibernateClass Used for saving our data in our "mazesinfo" table using Hibernate.
* @author  Bar Magnezi and Senia Kalma
* @version 1.0
* @since 17.5.2015
*/
@Entity
@Table(name="mazesinfo")
public class HibernateClass {

	int id;
	String name;
	String maze;
	String solution;
	
	@Id
	@TableGenerator(name="mazeid", table="mazetb", pkColumnName="mazekey", pkColumnValue="mazeval", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="mazeid")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=31800)
	public String getMaze() {
		return maze;
	}
	public void setMaze(String maze) {
		this.maze = maze;
	}
	@Column(length=31800)
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
}
