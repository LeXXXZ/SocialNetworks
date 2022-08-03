package graph;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * A directed edge in a graph from Node start to Node end
 * representing friendship between users
 */
public class CapEdge {

    private CapNode start;
    private CapNode end;

    public CapEdge(CapNode start, CapNode end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Given one of the nodes involved in this edge, get the other one
     * @param node The node on one side of this edge
     * @return the other node involved in this edge
     */
    CapNode getOtherNode(CapNode node)
    {
        if (node.equals(start))
            return end;
        else if (node.equals(end))
            return start;
        throw new IllegalArgumentException("Looking for a node that is not in the edge");
    }
    public CapNode getStart() {
        return start;
    }

    public CapNode getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapEdge capEdge = (CapEdge) o;
        return Objects.equals(start, capEdge.start) && Objects.equals(end, capEdge.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CapEdge.class.getSimpleName() + "{", "}")
                .add("from=" + start.getId())
                .add("to=" + end.getId())
                .toString();
    }
}
