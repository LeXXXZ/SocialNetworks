package graph;

import java.util.Objects;

/**
 * Class representing a user (or node) in CapGraph
 */
public class CapNode {

    private int id;

    public CapNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
}
