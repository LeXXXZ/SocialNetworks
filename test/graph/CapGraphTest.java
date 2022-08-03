package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.GraphLoader;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class CapGraphTest {

    public static final int NOT_EXISTING_NODE_ID = 0;
    public static final int EXISTING_NODE_ID_1 = 1;
    public static final int EXISTING_NODE_ID_4 = 4;
    private static final Logger logger = Logger.getLogger(CapGraphTest.class.getName());
    static {
        logger.setLevel(Level.INFO);
    }
    private final Graph graph = new CapGraph();

    @BeforeEach
    void setup() throws Exception {
        GraphLoader.loadGraph(graph, "data/small_test_graph.txt");
        logger.info(graph.toString());
    }

    @Test
    void addVertex() {
        int initNumNodes = graph.getNumNodes();
        graph.addVertex(EXISTING_NODE_ID_1);
        assertEquals(initNumNodes, graph.getNumNodes());
        graph.addVertex(NOT_EXISTING_NODE_ID);
        assertEquals(initNumNodes+1, graph.getNumNodes());
    }

    @Test
    void addEdge() {
        int initNumEdges = graph.getNumEdges();
        graph.addEdge(NOT_EXISTING_NODE_ID, EXISTING_NODE_ID_1);
        assertEquals(initNumEdges, graph.getNumEdges());
        graph.addEdge(EXISTING_NODE_ID_1, EXISTING_NODE_ID_4);
        assertEquals(initNumEdges+1, graph.getNumEdges());
        graph.addEdge(EXISTING_NODE_ID_1, EXISTING_NODE_ID_4);
        assertEquals(initNumEdges+1, graph.getNumEdges());
    }
}