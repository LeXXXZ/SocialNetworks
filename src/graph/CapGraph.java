/**
 * 
 */
package graph;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A class which represents a graph implementation
 * Nodes in the graph are users of social network
 *
 */
public class CapGraph implements Graph {

	private final HashMap<Integer, CapNode> nodes = new HashMap<>();
	private final HashSet<CapEdge> edges = new HashSet<>();

	private static final Logger logger = Logger.getLogger(CapGraph.class.getName());
	static {
		logger.setLevel(Level.WARNING);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		if (nodes.putIfAbsent(num, new CapNode(num)) != null) {
			logger.warning("Node with id: " + num + " already exists");
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		CapNode start = nodes.get(from);
		CapNode end = nodes.get(to);
		if (start != null && end != null){
			CapEdge edge = new CapEdge(start, end);
			if (edges.contains(edge)) {
				logger.warning("Edge with start (" + from + ") and end (" + to + ") already exists");
			} else {
				start.addEdge(edge);
				edges.add(edge);
			}
		} else {
			logger.warning("Start (" + from + ") or end (" + to + ") nodes don't exist");
		}

	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		HashMap<Integer, HashSet<Integer>> graph = new HashMap<>();
		nodes.forEach((id, node) -> graph.put(id, node.getNeighbors().stream().map(CapNode::getId).collect(Collectors.toCollection(HashSet::new))));
		return graph;
	}


	@Override
	public int getNumNodes()
	{
		return nodes.values().size();
	}

	/**
	 * Return the user ids, which are the nodes in this graph.
	 * @return The nodes in this graph as user ids
	 */
	private Set<Integer> getNodes()
	{
		return nodes.keySet();
	}

	@Override
	public int getNumEdges()
	{
		return edges.size();
	}

	private HashSet<CapEdge> getEdges() {
		return edges;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", CapGraph.class.getSimpleName() + "[", "]")
				.add("NumNodes=" + getNumNodes())
				.add("NumEdged=" + getNumEdges())
				.add("nodes=" + nodes)
				.toString();
	}
}
