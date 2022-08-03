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

	private CapNode getNodeById(Integer id) {
		return nodes.get(id);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		Graph result = new CapGraph();
		if (nodes.containsKey(center)) {
			CapNode centerNode = nodes.get(center);
			result.addVertex(center);
			Set<CapNode> centerNodeNeighbors = centerNode.getNeighbors();
			for (CapNode node : centerNodeNeighbors) {
				result.addVertex(node.getId());
				result.addEdge(centerNode.getId(), node.getId());
				for (CapNode secondNeighbor : node.getNeighbors()) {
					if (!secondNeighbor.equals(centerNode) && centerNodeNeighbors.contains(secondNeighbor)) {
						result.addVertex(secondNeighbor.getId());
						result.addEdge(node.getId(), secondNeighbor.getId());
					}
				}
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		//First step - get finished stack of nodes after DFS
		Deque<CapNode> originalGraphCapNodes = new ArrayDeque<>(this.getNodes());
		Set<CapNode> visitedOriginalGraphCapNodes = new HashSet<>();
		Deque<CapNode> firstDfsFinishedNodes = new ArrayDeque<>();
		while (!originalGraphCapNodes.isEmpty()) {
			performDfsCycle(originalGraphCapNodes, visitedOriginalGraphCapNodes, firstDfsFinishedNodes);
		}

		//Second step - transpose original graph
		CapGraph transposedGraph = transpose(this);

		//Third step - perform DFS with transposed graph and form SCCs
		Deque<CapNode> transposedGraphCapNodes = new ArrayDeque<>();
		while (!firstDfsFinishedNodes.isEmpty()) {
			transposedGraphCapNodes.addLast(transposedGraph.getNodeById(firstDfsFinishedNodes.pop().getId()));
		}

		Set<CapNode> visitedTransposedGraphCapNodes = new HashSet<>();
		List<Graph> sccs = new ArrayList<>();

		while (!transposedGraphCapNodes.isEmpty()) {
			Deque<CapNode> secondDfsFinishedNodes  = new ArrayDeque<>();
			performDfsCycle(transposedGraphCapNodes, visitedTransposedGraphCapNodes, secondDfsFinishedNodes);
			if (!secondDfsFinishedNodes .isEmpty()) {
				CapGraph graph = new CapGraph();
				secondDfsFinishedNodes .forEach(n -> graph.addVertex(n.getId()));
				secondDfsFinishedNodes .forEach(n -> {
					n.getEdges().forEach(edge -> graph.addEdge(edge.getEnd().getId(), edge.getStart().getId()));
				});
				sccs.add(graph);
			}
		}
		return sccs;
	}

	private void performDfsCycle(Deque<CapNode> capNodes, Set<CapNode> visited, Deque<CapNode> finished) {
		CapNode currentNode = capNodes.pop();
		if (!visited.contains(currentNode)){
			dfsVisit(currentNode, visited, finished);
		}
	}

	private void dfsVisit(CapNode node, Set<CapNode> visited, Deque<CapNode> finished) {
		visited.add(node);
		for (CapNode neighbor : node.getNeighbors()) {
			if (!visited.contains(neighbor)){
				dfsVisit(neighbor, visited, finished);
			}
		}
		finished.push(node);
	}

	private CapGraph transpose(CapGraph graph) {
		CapGraph transposedGraph = new CapGraph();
		graph.getNodes().forEach(n -> transposedGraph.addVertex(n.getId()));
		graph.getEdges().forEach(edge -> transposedGraph.addEdge(edge.getEnd().getId(), edge.getStart().getId()));
		return transposedGraph;
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
	private Collection<CapNode> getNodes()
	{
		return nodes.values();
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
