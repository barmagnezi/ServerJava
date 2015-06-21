package algorithms.search;
/**
* interface of problem that searcher
*
* @author  Bar Magnezi
* @version 1.0
* @since 14.4.2015
*/
public interface Searcher{
    // the search method
    public Solution search(Searchable s);
    // get how many nodes were evaluated by the algorithm
    public int getNumberOfNodesEvaluated();
}
