package data.graph.map;

import data.graph.Algorithms;
import data.graph.Edge;
import data.graph.Graph;
import domain.store.closestHubToClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


import java.util.*;
class MapAlgorithmsTest {

    final Graph<String, Integer> completeMap = new MapGraph<>(false);
    Graph<String, Integer> incompleteMap = new MapGraph<>(false);
    Graph<String, Integer> oneVertexMap = new MapGraph<>(false);
    Graph<String, Integer> oneEdgeMap = new MapGraph<>(false);

    Graph<String, Integer> emptyMap = new MapGraph<>(false);

    public MapAlgorithmsTest() {
    }

    @BeforeEach
    public void setUp() {

        completeMap.addVertex("Porto");
        completeMap.addVertex("Braga");
        completeMap.addVertex("Vila Real");
        completeMap.addVertex("Aveiro");
        completeMap.addVertex("Coimbra");
        completeMap.addVertex("Leiria");

        completeMap.addVertex("Viseu");
        completeMap.addVertex("Guarda");
        completeMap.addVertex("Castelo Branco");
        completeMap.addVertex("Lisboa");
        completeMap.addVertex("Faro");

        completeMap.addEdge("Porto", "Aveiro", 75);
        completeMap.addEdge("Porto", "Braga", 60);
        completeMap.addEdge("Porto", "Vila Real", 100);
        completeMap.addEdge("Viseu", "Guarda", 75);
        completeMap.addEdge("Guarda", "Castelo Branco", 100);
        completeMap.addEdge("Aveiro", "Coimbra", 60);
        completeMap.addEdge("Coimbra", "Lisboa", 200);
        completeMap.addEdge("Coimbra", "Leiria", 80);
        completeMap.addEdge("Aveiro", "Leiria", 120);
        completeMap.addEdge("Leiria", "Lisboa", 150);

        incompleteMap = completeMap.clone();

        completeMap.addEdge("Aveiro", "Viseu", 85);
        completeMap.addEdge("Leiria", "Castelo Branco", 170);
        completeMap.addEdge("Lisboa", "Faro", 280);

    }

    private void checkContentEquals(List<String> l1, List<String> l2, String msg) {
        Collections.sort(l1);
        Collections.sort(l2);
        assertEquals(l1, l2, msg);
    }

    /**
     * Test of BreadthFirstSearch method, of class Algorithms.
     */
    @Test
    public void testBreadthFirstSearch() {
        System.out.println("Test BreadthFirstSearch");

        Assertions.assertNull(Algorithms.BreadthFirstSearch(completeMap, "LX"), "Should be null if vertex does not exist");

        LinkedList<String> path = Algorithms.BreadthFirstSearch(incompleteMap, "Faro");

        assertEquals(1, path.size(), "Should be just one");

        assertEquals("Faro", path.peekFirst());

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Porto");
        assertEquals(7, path.size(), "Should give seven vertices");

        assertEquals("Porto", path.removeFirst(), "BreathFirst Porto");

        LinkedList<String> expected = new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real"));
        checkContentEquals(expected, path.subList(0, 3), "BreathFirst Porto");

        expected = new LinkedList<>(Arrays.asList("Coimbra", "Leiria"));
        checkContentEquals(expected, path.subList(3, 5), "BreathFirst Porto");

        expected = new LinkedList<>(Arrays.asList("Lisboa"));
        checkContentEquals(expected, path.subList(5, 6), "BreathFirst Porto");

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Viseu");
        expected = new LinkedList<>(Arrays.asList("Viseu", "Guarda", "Castelo Branco"));
        assertEquals(expected, path, "BreathFirst Viseu");
    }

    /**
     * Test of DepthFirstSearch method, of class Algorithms.
     */
    @Test
    public void testDepthFirstSearch() {
        System.out.println("Test of DepthFirstSearch");

        assertNull(Algorithms.DepthFirstSearch(completeMap, "LX"), "Should be null if vertex does not exist");

        LinkedList<String> path = Algorithms.DepthFirstSearch(incompleteMap, "Faro");
        assertEquals(1, path.size(), "Should be just one");

        assertEquals("Faro", path.peekFirst());

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Porto");
        assertEquals(7, path.size(), "Should give seven vertices");

        assertEquals("Porto", path.removeFirst(), "DepthFirst Porto");
        assertTrue(new LinkedList<>(Arrays.asList("Aveiro", "Braga", "Vila Real")).contains(path.removeFirst()), "DepthFirst Porto");

        path = Algorithms.BreadthFirstSearch(incompleteMap, "Viseu");
        List<String> expected = new LinkedList<>(Arrays.asList("Viseu", "Guarda", "Castelo Branco"));
        assertEquals(expected, path, "DepthFirst Viseu");
    }

    /**
     * Test of shortestPath method, of class Algorithms.
     */
    @Test
    public void testShortestPath() {
        System.out.println("Test of shortest path");

        LinkedList<String> shortPath = new LinkedList<>();

        Integer lenPath = Algorithms.shortestPath(completeMap, "Porto", "LX", Integer::compare, Integer::sum, 0, shortPath);
        assertNull(lenPath, "Length path should be null if vertex does not exist");
        assertEquals(0, shortPath.size(), "Shortest Path does not exist");

        lenPath = Algorithms.shortestPath(incompleteMap, "Porto", "Faro", Integer::compare, Integer::sum, 0, shortPath);
        assertNull(lenPath, "Length path should be null if vertex does not exist");
        assertEquals(0, shortPath.size(), "Shortest Path does not exist");

        lenPath = Algorithms.shortestPath(completeMap, "Porto", "Porto", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(0, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto"), shortPath, "Shortest Path only contains Porto");

        lenPath = Algorithms.shortestPath(incompleteMap, "Porto", "Lisboa", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(335, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), shortPath, "Shortest Path Porto - Lisboa");

        lenPath = Algorithms.shortestPath(incompleteMap, "Braga", "Leiria", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(255, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), shortPath, "Shortest Path Braga - Leiria");

        lenPath = Algorithms.shortestPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(335, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), shortPath, "Shortest Path Porto - Castelo Branco");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco

        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.addEdge("Leiria", "Castelo Branco", 170);

        lenPath = Algorithms.shortestPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0, shortPath);
        assertEquals(365, lenPath, "Length path should be 0 if vertices are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), shortPath, "Shortest Path Porto - Castelo Branco");
    }

    /**
     * Test of shortestPaths method, of class Algorithms.
     */
    @Test
    public void testShortestPaths() {
        System.out.println("Test of shortest path");

        ArrayList<LinkedList<String>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();

        Algorithms.shortestPaths(completeMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);

        assertEquals(paths.size(), dists.size(), "There should be as many paths as sizes");
        assertEquals(completeMap.numVertices(), paths.size(), "There should be a path to every vertex");
        assertEquals(Arrays.asList("Porto"), paths.get(completeMap.key("Porto")), "Number of nodes should be 1 if source and vertex are the same");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.key("Lisboa")), "Path to Lisbon");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Viseu", "Guarda", "Castelo Branco"), paths.get(completeMap.key("Castelo Branco")), "Path to Castelo Branco");
        assertEquals(335, dists.get(completeMap.key("Castelo Branco")), "Path between Porto and Castelo Branco should be 335 Km");

        //Changing Edge: Aveiro-Viseu with Edge: Leiria-C.Branco
        //should change shortest path between Porto and Castelo Branco
        completeMap.removeEdge("Aveiro", "Viseu");
        completeMap.addEdge("Leiria", "Castelo Branco", 170);
        Algorithms.shortestPaths(completeMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);
        assertEquals(365, dists.get(completeMap.key("Castelo Branco")), "Path between Porto and Castelo Branco should now be 365 Km");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Leiria", "Castelo Branco"), paths.get(completeMap.key("Castelo Branco")), "Path to Castelo Branco");

        Algorithms.shortestPaths(incompleteMap, "Porto", Integer::compare, Integer::sum, 0, paths, dists);
        assertNull(dists.get(completeMap.key("Faro")), "Length path should be null if there is no path");
        assertEquals(335, dists.get(completeMap.key("Lisboa")), "Path between Porto and Lisboa should be 335 Km");
        assertEquals(Arrays.asList("Porto", "Aveiro", "Coimbra", "Lisboa"), paths.get(completeMap.key("Lisboa")), "Path to Lisboa");

        Algorithms.shortestPaths(incompleteMap, "Braga", Integer::compare, Integer::sum, 0, paths, dists);
        assertEquals(255, dists.get(completeMap.key("Leiria")), "Path between Braga and Leiria should be 255 Km");
        assertEquals(Arrays.asList("Braga", "Porto", "Aveiro", "Leiria"), paths.get(completeMap.key("Leiria")), "Path to Leiria");
    }

    /**
     * Test minimum distance graph using Floyd-Warshall.
     */
    //@Test

    /**
     * Test of isGraphConnected
     */
    @Test
    public void testIsGraphConnected() {
        System.out.println("Test of isGraphConnected");
        assertTrue(Algorithms.isGraphConnected(completeMap), "Complete Map should be connected");
        completeMap.removeEdge("Porto", "Vila Real");
        assertFalse(Algorithms.isGraphConnected(completeMap), "Complete Map should not be connected");
        completeMap.addEdge("Porto", "Vila Real", 100);
        assertFalse(Algorithms.isGraphConnected(incompleteMap), "Incomplete Map should not be connected");
        incompleteMap.addEdge("Aveiro", "Viseu", 85);
        incompleteMap.addEdge("Leiria", "Castelo Branco", 170);
        incompleteMap.addEdge("Lisboa", "Faro", 280);
        assertTrue(Algorithms.isGraphConnected(incompleteMap), "Incomplete Map should be connected");
        incompleteMap.removeEdge("Aveiro", "Viseu");
        incompleteMap.removeEdge("Leiria", "Castelo Branco");
        incompleteMap.removeEdge("Lisboa", "Faro");
    }

    /**
     * Test of  minConnections
     */
    @Test
    public void testMinConnections() {
        System.out.println("Test of minConnections");
        assertEquals(0, Algorithms.CountMinPath(completeMap, "Porto", "Porto", Integer::compare, Integer::sum, 0), "Porto should have 0 connections");
        assertEquals(1, Algorithms.CountMinPath(completeMap, "Porto", "Aveiro", Integer::compare, Integer::sum, 0), "Porto should have 1 connection");
        assertEquals(2, Algorithms.CountMinPath(completeMap, "Porto", "Coimbra", Integer::compare, Integer::sum, 0), "Porto should have 2 connections");
        assertEquals(3, Algorithms.CountMinPath(completeMap, "Porto", "Lisboa", Integer::compare, Integer::sum, 0), "Porto should have 3 connections");
        assertEquals(4, Algorithms.CountMinPath(completeMap, "Porto", "Faro", Integer::compare, Integer::sum, 0), "Porto should have 4 connections");
        assertEquals(4, Algorithms.CountMinPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0), "Porto should have 4 connections");
        assertNotEquals(5, Algorithms.CountMinPath(completeMap, "Porto", "Faro", Integer::compare, Integer::sum, 0), "Porto should not have 5 connections");
        assertNotEquals(5, Algorithms.CountMinPath(completeMap, "Porto", "Castelo Branco", Integer::compare, Integer::sum, 0), "Porto should not have 5 connections");
        completeMap.removeEdge("Porto", "Vila Real");
        assertEquals(-1, Algorithms.CountMinPath(completeMap, "Porto", "Vila Real", Integer::compare, Integer::sum, 0), "Porto and Vila Real should not be connected");
    }

    @Test
    public void testkruskal() {

        // test with complete map
        Graph<String, Integer> kruskalComplete = Algorithms.kruskal(completeMap);
        assertEquals(completeMap.numVertices(), kruskalComplete.numVertices());
        assertEquals(20, kruskalComplete.numEdges());


        // test with incomplete map
        Graph<String, Integer> kruskalIncomplete = Algorithms.kruskal(incompleteMap);
        if (Algorithms.isGraphConnected(incompleteMap) == true) {
            assertEquals(incompleteMap.numVertices(), kruskalIncomplete.numVertices());
            assertEquals(16, kruskalIncomplete.numEdges());
        }


        // test with empty map
        Graph<String, Integer> kruskalEmpty = Algorithms.kruskal(emptyMap);
        assertEquals(emptyMap.numVertices(), kruskalEmpty.numVertices());
        assertEquals(0, kruskalEmpty.numEdges());

        // test with null map
        Graph<String, Integer> nullMap = null;
        Graph<String, Integer> kruskalNull = Algorithms.kruskal(nullMap);
        assertNull(kruskalNull);


        // test with map with only one vertex
        oneVertexMap.addVertex("Porto");
        Graph<String, Integer> kruskalOneVertex = Algorithms.kruskal(oneVertexMap);
        assertEquals(oneVertexMap.numVertices(), kruskalOneVertex.numVertices());
        assertEquals(0, kruskalOneVertex.numEdges());

        // test with map with only one edge
        oneEdgeMap.addEdge("Porto", "Aveiro", 100);
        Graph<String, Integer> kruskalOneEdge = Algorithms.kruskal(oneEdgeMap);
        assertEquals(oneEdgeMap.numVertices(), kruskalOneEdge.numVertices());
        assertEquals(2, kruskalOneEdge.numEdges());

    }

}
