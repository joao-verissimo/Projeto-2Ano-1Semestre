package domain.store;

import data.graph.Algorithms;
import data.graph.Graph;
import domain.model.Client;
import domain.model.Firm;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.BinaryOperator;


public class closestHubToClient {


    public static <V, E> void getclosesthubtoclients(Graph<V, E> g, Comparator<E> ce, BinaryOperator<E> sum, E zero) {
        ArrayList<V> qdfs;
        qdfs = g.vertices();
        for (V client : qdfs) {
            if (client instanceof Client || client instanceof Firm) {
                getclosesthubtoclient(g, client, ce, sum, zero);
                System.out.println("Client: " + client + " Closest Hub: " + getclosesthubtoclient(g, client, ce, sum, zero));
            }
        }
    }


    public static <V, E> V getclosesthubtoclient(Graph<V, E> g, V client, Comparator<E> ce, BinaryOperator<E> sum, E zero) {
        LinkedList<V> qdfs;
        LinkedList<V> shortPath = new LinkedList<>();
        V closesthub = null;
        double min = Double.MAX_VALUE;
        qdfs = DepthFirstSearchForHubs(g, client);
        for (V u : qdfs) {
            Integer dist = (Integer) Algorithms.shortestPath(g, client, u, ce, sum, zero, shortPath);
                if (dist < min) {
                    min = dist;
                    closesthub =  u;
                }

        }
        return closesthub;
    }

    private static <V, E> void DepthFirstSearchForHubs(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        if (visited[g.key(vOrig)]) return;

        if ((vOrig instanceof Firm && ((Firm) vOrig).isHUB())){
            qdfs.add(vOrig);
            return;
        }


        visited[g.key(vOrig)] = true;
        for (V vAdj : g.adjVertices(vOrig))
            DepthFirstSearchForHubs(g, vAdj, visited, qdfs);
    }

    public static <V, E> LinkedList<V> DepthFirstSearchForHubs(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) return null;

        boolean[] visited = new boolean[g.numVertices()];
        LinkedList<V> qdfs = new LinkedList<>();

        DepthFirstSearchForHubs(g, vert, visited, qdfs);
        return qdfs;
    }
}



