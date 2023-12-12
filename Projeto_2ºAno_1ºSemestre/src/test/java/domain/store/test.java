package domain.store;

import data.graph.Edge;
import data.graph.Graph;
import domain.model.Producer;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    @Test
    public void testGetShortestPath() {
        // Create a set of producers
        Set<Producer> producers = new HashSet<>();
        Producer p1 = new Producer("CT17", "P1", 40.6667, -7.9167);
        Producer p2 = new Producer("CT6", "P2", 40.2111, -8.4291);
        Producer p3 = new Producer("CT10", "P3", 39.7444, -8.8072);
        producers.add(p1);
        producers.add(p2);
        producers.add(p3);

        // Test for a graph with three producers and three edges
        Graph<Producer, Integer> graph1 = new Graph<>() {
            @Override
            public boolean isDirected() {
                return false;
            }

            @Override
            public int numVertices() {
                return 0;
            }

            @Override
            public ArrayList<Producer> vertices() {
                return null;
            }

            @Override
            public boolean validVertex(Producer vert) {
                return false;
            }

            @Override
            public int key(Producer vert) {
                return 0;
            }

            @Override
            public Producer vertex(int key) {
                return null;
            }

            @Override
            public Producer vertex(Predicate<Producer> p) {
                return null;
            }

            @Override
            public Collection<Producer> adjVertices(Producer vert) {
                return null;
            }

            @Override
            public int numEdges() {
                return 0;
            }

            @Override
            public Collection<Edge<Producer, Integer>> edges() {
                return null;
            }

            @Override
            public Edge<Producer, Integer> edge(Producer vOrig, Producer vDest) {
                return null;
            }

            @Override
            public Edge<Producer, Integer> edge(int vOrigKey, int vDestKey) {
                return null;
            }

            @Override
            public int outDegree(Producer vert) {
                return 0;
            }

            @Override
            public int inDegree(Producer vert) {
                return 0;
            }

            @Override
            public Collection<Edge<Producer, Integer>> outgoingEdges(Producer vert) {
                return null;
            }

            @Override
            public Collection<Edge<Producer, Integer>> incomingEdges(Producer vert) {
                return null;
            }

            @Override
            public boolean addVertex(Producer vert) {
                return false;
            }

            @Override
            public boolean addEdge(Producer vOrig, Producer vDest, Integer weight) {
                return false;
            }

            @Override
            public boolean removeVertex(Producer vert) {
                return false;
            }

            @Override
            public boolean removeEdge(Producer vOrig, Producer vDest) {
                return false;
            }

            @Override
            public Graph<Producer, Integer> clone() {
                return null;
            }
        };
        graph1.addVertex(p1);
        graph1.addVertex(p2);
        graph1.addVertex(p3);
        graph1.addEdge(p1, p2, 10);
        graph1.addEdge(p2, p3, 20);
        graph1.addEdge(p3, p1, 15);
        ArrayList<LinkedList<Producer>> expectedPaths1 = new ArrayList<>();
        expectedPaths1.add(new LinkedList<>(Arrays.asList(p1)));
        expectedPaths1.add(new LinkedList<>(Arrays.asList(p1, p2)));
        expectedPaths1.add(new LinkedList<>(Arrays.asList(p1, p2, p3)));
        //ArrayList<LinkedList<Producer>> actualPaths1 = DispatchList.getShortestPath(producers, graph1, Comparator.naturalOrder(), Integer::sum, 0, p1);
        //assertEquals(expectedPaths1, actualPaths1);
    }
}
