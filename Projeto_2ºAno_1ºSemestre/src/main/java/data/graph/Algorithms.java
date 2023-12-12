package data.graph;

import data.graph.map.MapGraph;
import data.graph.matrix.MatrixGraph;
import domain.model.Firm;
import domain.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * @author DEI-ISEP
 */
public class Algorithms {

    /**
     * Performs breadth-first search of a Graph starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex that will be the source of the search
     * @return a LinkedList with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert))
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        visited[g.key(vert)] = true;
        LinkedList<V> qbfs = new LinkedList<>();
        qbfs.add(vert);
        LinkedList<V> qaux = new LinkedList<>();
        qaux.add(vert);

        while (!qaux.isEmpty()) {
            System.out.println(qbfs);
            vert = qaux.remove();
            for (V adjVert : g.adjVertices(vert)) {
                if (!visited[g.key(adjVert)]) {
                    qbfs.add(adjVert);
                    qaux.add(adjVert);
                    visited[g.key(adjVert)] = true;
                }
            }
        }
        return qbfs;
    }

    public static <V, E> LinkedList<V> BFSFindNearestNeighboursByType(Graph<V, E> g, V vert, int limit, Class<?> clazz) {
        if (!g.validVertex(vert))
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        visited[g.key(vert)] = true;
        LinkedList<V> qbfs = new LinkedList<>();
        qbfs.add(vert);
        LinkedList<V> qaux = new LinkedList<>();
        qaux.add(vert);
        ArrayList<V> q = new ArrayList<>();
        int counter = 0;
        boolean found;

        while (!qaux.isEmpty()) {
            found = false;
            vert = qaux.remove();
            for (V adjVert : g.adjVertices(vert)) {
                if (!visited[g.key(adjVert)]) {
                    q.add(adjVert);
                    visited[g.key(adjVert)] = true;
                }
            }
            for (V adjVert : q) {
                if (adjVert.getClass().equals(clazz)) {
                    found = true;
                    counter++;
                }
            }
            if (!found && counter >= limit)
                return qbfs;
            qbfs.addAll(q);
            qaux.addAll(q);
            q.clear();
        }
        return qbfs;
    }

    public static <V, E> LinkedList<V> BFSFindNearestHUB(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert))
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        visited[g.key(vert)] = true;
        LinkedList<V> qbfs = new LinkedList<>();
        qbfs.add(vert);
        LinkedList<V> qaux = new LinkedList<>();
        qaux.add(vert);
        ArrayList<V> q = new ArrayList<>();
        boolean found;
        boolean foundHUB = false;

        while (!qaux.isEmpty()) {
            found = false;
            vert = qaux.remove();
            for (V adjVert : g.adjVertices(vert)) {
                if (!visited[g.key(adjVert)]) {
                    q.add(adjVert);
                    visited[g.key(adjVert)] = true;
                }
            }
            for (V adjVert : q) {
                if (adjVert instanceof Firm && ((Firm) adjVert).isHUB()) {
                    found = true;
                    foundHUB = true;
                    break;
                }
            }
            if (!found && foundHUB)
                return qbfs;
            qbfs.addAll(q);
            qaux.addAll(q);
            q.clear();
        }
        return qbfs;
    }


    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g       Graph instance
     * @param vOrig   vertex of graph g that will be the source of the search
     * @param visited set of previously visited vertices
     * @param qdfs    return LinkedList with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        if (visited[g.key(vOrig)])
            return;

        qdfs.add(vOrig);
        visited[g.key(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig))
            DepthFirstSearch(g, vAdj, visited, qdfs);
    }

    /**
     * Performs depth-first search starting in a vertex
     *
     * @param g    Graph instance
     * @param vert vertex of graph g that will be the source of the search
     * @return a LinkedList with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert))
            return null;

        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> qdfs = new LinkedList<>();

        DepthFirstSearch(g, vert, visited, qdfs);
        return qdfs;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in
     *                reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {

    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with non-negative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of previously visited vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    private static <V, E> void shortestPathDijkstra(Graph<V, E> g, V vOrig,
                                                    Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                                    boolean[] visited, V[] pathKeys, E[] dist) {

        int vKey = g.key(vOrig);
        dist[vKey] = zero;
        pathKeys[vKey] = vOrig;
        int vKeyAdj;
        E minDist;

        while (vOrig != null) {
            vKey = g.key(vOrig);
            visited[vKey] = true;
            for (Edge<V, E> edge : g.outgoingEdges(vOrig)) {
                vKeyAdj = g.key(edge.getVDest());
                if (!visited[vKeyAdj]) {
                    E s = sum.apply(dist[vKey], edge.getWeight());
                    if (dist[vKeyAdj] == null || ce.compare(dist[vKeyAdj], s) > 0) {
                        dist[vKeyAdj] = s;
                        pathKeys[vKeyAdj] = vOrig;
                    }
                }
            }
            minDist = null;
            vOrig = null;
            for (V vert : g.vertices()) {
                if (!visited[g.key(vert)] && dist[g.key(vert)] != null
                        && (minDist == null || ce.compare(dist[g.key(vert)], minDist) < 0)) {
                    minDist = dist[g.key(vert)];
                    vOrig = vert;
                }
            }
        }
    }

    /**
     * Shortest-path between two vertices
     *
     * @param g         graph
     * @param vOrig     origin vertex
     * @param vDest     destination vertex
     * @param ce        comparator between elements of type E
     * @param sum       sum two elements of type E
     * @param zero      neutral element of the sum in elements of type E
     * @param shortPath returns the vertices which make the shortest path
     * @return if vertices exist in the graph and are connected, true, false
     * otherwise
     */
    public static <V, E> E shortestPath(Graph<V, E> g, V vOrig, V vDest,
                                        Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                        LinkedList<V> shortPath) {
        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return null;

        shortPath.clear();
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts]; // inicializado a falso
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];
        for (int i = 0; i < numVerts; i++) {
            dist[i] = null;
            pathKeys[i] = null;
        }
        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        E lengthPath = dist[g.key(vDest)];
        if (lengthPath == null)
            return null;

        getPath(g, vOrig, vDest, pathKeys, shortPath);

        return lengthPath;
    }

    /**
     * Shortest-path between a vertex and all other vertices
     *
     * @param g     graph
     * @param vOrig start vertex
     * @param ce    comparator between elements of type E
     * @param sum   sum two elements of type E
     * @param zero  neutral element of the sum in elements of type E
     * @param paths returns all the minimum paths
     * @param dists returns the corresponding minimum distances
     * @return if vOrig exists in the graph true, false otherwise
     */
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig,
                                               Comparator<E> ce, BinaryOperator<E> sum, E zero,
                                               ArrayList<LinkedList<V>> paths, ArrayList<E> dists) {
        if (!g.validVertex(vOrig))
            return false;
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        dists.clear();
        paths.clear();
        for (int i = 0; i < numVerts; i++) {
            paths.add(null);
            dists.add(null);
        }

        for (V vDst : g.vertices()) {
            int i = g.key(vDst);
            if (dist[i] != null) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDst, pathKeys, shortPath);
                paths.set(i, shortPath);
                dists.set(i, dist[i]);
            }
        }
        return true;
    }

    public static <V, E> boolean shortestPathsFindNearestNeighboursByType(Graph<V, E> g, V vOrig,
                                                                          Comparator<E> ce, BinaryOperator<E> sum,
                                                                          E zero, ArrayList<LinkedList<V>> paths,
                                                                          ArrayList<E> dists, Class<?> clazz) {
        if (!g.validVertex(vOrig))
            return false;
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        dists.clear();
        paths.clear();

        for (V vDst : g.vertices()) {
            int i = g.key(vDst);
            if (dist[i] != null && vDst.getClass().equals(clazz)) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDst, pathKeys, shortPath);
                paths.add(shortPath);
                dists.add(dist[i]);
            }
        }
        return true;
    }

    public static <V, E> boolean shortestPathsFindNearestHUB(Graph<V, E> g, V vOrig,
                                                             Comparator<E> ce, BinaryOperator<E> sum,
                                                             E zero, ArrayList<LinkedList<V>> paths,
                                                             ArrayList<E> dists) {
        if (!g.validVertex(vOrig))
            return false;
        int numVerts = g.numVertices();
        boolean[] visited = new boolean[numVerts];
        @SuppressWarnings("unchecked")
        V[] pathKeys = (V[]) new Object[numVerts];
        @SuppressWarnings("unchecked")
        E[] dist = (E[]) new Object[numVerts];

        shortestPathDijkstra(g, vOrig, ce, sum, zero, visited, pathKeys, dist);
        dists.clear();
        paths.clear();

        for (V vDst : g.vertices()) {
            int i = g.key(vDst);
            if (dist[i] != null && vDst instanceof Firm && ((Firm) vDst).isHUB()) {
                LinkedList<V> shortPath = new LinkedList<>();
                getPath(g, vOrig, vDst, pathKeys, shortPath);
                paths.add(shortPath);
                dists.add(dist[i]);
            }
        }
        return true;
    }

    /**
     * Extracts from pathKeys the minimum path between voInf and vdInf
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest,
                                       V[] pathKeys, LinkedList<V> path) {

        if (vOrig.equals(vDest)) {
            path.push(vOrig);
        } else {
            path.push(vDest);
            int vKey = g.key(vDest);
            vDest = pathKeys[vKey];
            getPath(g, vOrig, vDest, pathKeys, path);
        }
    }

    /**
     * Calculates the minimum distance graph using Floyd-Warshall
     *
     * @param g   initial graph
     * @param ce  comparator between elements of type E
     * @param sum sum two elements of type E
     * @return the minimum distance graph
     */
    public static <V, E> MatrixGraph<V, E> minDistGraph(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum) {
        if (g.numVertices() == 0)
            return null;

        @SuppressWarnings("unchecked")
        E[][] mat = (E[][]) new Object[g.numVertices()][g.numVertices()];

        for (int i = 0; i < g.numVertices(); i++) {
            for (int j = 0; j < g.numVertices(); j++) {
                Edge<V, E> edge = g.edge(i, j);
                if (edge != null) {
                    mat[i][j] = edge.getWeight();
                }
            }
        }
        for (int k = 0; k < g.numVertices(); k++) {
            for (int j = 0; j < g.numVertices(); j++) {
                if (j != k && mat[j][k] != null) {
                    for (int i = 0; i < g.numVertices(); i++) {
                        if (i != k && i != j && mat[k][i] != null) {
                            E sum2 = sum.apply(mat[j][k], mat[k][i]);
                            if (mat[j][i] == null || ce.compare(sum2, mat[j][i]) < 0)
                                mat[j][i] = sum2;
                        }
                    }
                }
            }
        }
        return new MatrixGraph<>(g.isDirected(), g.vertices(), mat);
    }

    /**
     * Calculates if the graph is connected using BFS
     *
     * @param <V> vertex type
     * @param <E> edge type
     * @param g   initial graph
     * @return if the graph is connected
     */
    public static <V, E> boolean isGraphConnected(Graph<V, E> g) {
        if (g.numVertices() == 0)
            return false;
        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> qdfs = new LinkedList<>();
        DepthFirstSearch(g, g.vertices().iterator().next(), visited, qdfs);
        for (int i = 0; i < g.numVertices(); i++) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    /**
     * Caculates the minimum path between two vertices using Djikstra
     *
     * @param <V>     vertex type
     * @param <E>     edge type
     * @param g       initial graph
     * @param vOrigin origin vertex
     * @param vDest   destination vertex
     * @param ce      comparator between elements of type E
     * @param sum     sum two elements of type E
     * @param zero    neutral element of the sum in elements of type E
     * @return the size of the minimum path
     */

    public static <V, E> int CountMinPath(Graph<V, E> g, V vOrigin, V vDest, Comparator<E> ce, BinaryOperator<E> sum,
                                          E zero) {
        LinkedList<V> paths = new LinkedList<>();
        shortestPath(g, vOrigin, vDest, ce, sum, zero, paths);
        return paths.size() - 1;
    }

    public static <V, E> Graph<V, E> kruskal(Graph<V, E> g) {
        Graph<V, E> mst;
        if (g instanceof MapGraph<V, E>) {
            mst = new MapGraph<>((!g.isDirected() && isGraphConnected(g)));
        } else if (g instanceof MatrixGraph<V, E>) {
            mst = new MatrixGraph<>((!g.isDirected() && isGraphConnected(g)));
        } else
            return null;

        LinkedList<V> connectedVerts = new LinkedList<>();
        for (int i = 0; i < g.numVertices(); i++) {
            mst.addVertex(g.vertex(i));
        }
        List<Edge<V, E>> lst = new ArrayList<>(g.edges());
        lst.subList(0, lst.size()).sort(new Comparator<Edge<V, E>>() {
            @Override
            public int compare(Edge<V, E> o1, Edge<V, E> o2) {
                return ((Comparable<E>) o1.getWeight()).compareTo(o2.getWeight());
            }
        });
        for (Edge<V, E> e : lst) {

            connectedVerts = DepthFirstSearch(mst, e.getVOrig());
            if (!connectedVerts.contains(e.getVDest()))
                mst.addEdge(e.getVOrig(), e.getVDest(), e.getWeight());

        }
        // System.out.println(mst);
        return mst;
    }

}