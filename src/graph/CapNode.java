package graph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Class representing a user (or node) in CapGraph
 */
public class CapNode {

    private int id;

    /** The list of edges out of this node */
    private HashSet<CapEdge> edges;

    public CapNode(int id) {
        this.id = id;
        edges = new HashSet<CapEdge>();
    }

    public int getId() {
        return id;
    }

    public HashSet<CapEdge> getEdges() {
        return edges;
    }

    /**
     * Add an edge that is outgoing from this node in the graph
     * @param edge The edge to be added
     */
    void addEdge(CapEdge edge)
    {
        edges.add(edge);
    }

    /**
     * Return the neighbors of this CapNode
     * @return a set containing all the neighbors of this node
     */
    Set<CapNode> getNeighbors()
    {
        Set<CapNode> neighbors = new HashSet<CapNode>();
        for (CapEdge edge : edges) {
            neighbors.add(edge.getOtherNode(this));
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapNode capNode = (CapNode) o;
        return id == capNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CapNode.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("edges=" + edges)
                .toString();
    }
}
