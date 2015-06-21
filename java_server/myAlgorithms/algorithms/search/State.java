package algorithms.search;
/**
* The State class describe us state from problem
* the state need to represented by the string
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
* 
* 
*/
public class State implements Comparable<State>{
    private String state;    // the state represented by a string
    private double cost=0;     // cost to reach this state
    private State cameFrom;  // the state we came from to this state
    /**
     * This constructor creates us state from string
     * @param state The state represented by the string
     */
    public State(String state){    // CTOR    
        this.state = state;
    }

    @Override
    public boolean equals(Object obj){ // we override Object's equals method
        return state.equals(((State)obj).state);
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int compareTo(State o) {
		if (this.getCost() < o.getCost())
        {
            return -1;
        }
        if (this.getCost() > o.getCost())
        {
            return 1;
        }
        return 0;
	} 
   
}
