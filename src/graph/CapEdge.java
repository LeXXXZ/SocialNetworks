package graph;

import java.util.Objects;

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
}
