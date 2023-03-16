package com.mygdx.pathfinding;

import com.mygdx.map.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarMap {

    public static final boolean DEBUG_ASTAR = false;

    /* La première liste, appelée liste ouverte, va contenir tous les noeuds étudiés. Dès que l'algorithme va se pencher sur
    un noeud du graphe, il passera dans la liste ouverte (sauf s'il y est déjà).
    */
    private List<Node> openList;

    /*  La seconde liste, appelée liste fermée, contiendra tous les noeuds qui, à un moment où à un autre, ont été considérés
    comme faisant partie du chemin solution. Avant de passer dans la liste fermée, un noeud doit d'abord passer dans la
    liste ouverte, en effet, il doit d'abord être étudié avant d'être jugé comme bon.
    */
    private List<Node> close;

    private Map map;

    public AStarMap(Map m) {
        this.map = m;
    }

    // start: coordonnées de la tuile de départ, goal: coordonnées de la tuile de départ
    public List<Vector2int> findPath(Vector2int start, Vector2int goal) {
        return findPath(new Node(start, null), new Node(goal, null));
    }

    NodeComparator nodeComparator = new NodeComparator();

    public List<Vector2int> findPath(Node pointDepart, Node pointArrivee) {
        int steps = 0; // pour limiter la recherche
        close = new ArrayList<>();
        openList = new ArrayList<>();
        openList.add(pointDepart);

        while (!openList.isEmpty()) {

            Collections.sort(openList, nodeComparator);
            Node u = openList.get(0);
            openList.remove(0);

            if (u.point.equals(pointArrivee.point) || steps++ > 2000) {

//                System.out.println("findPath FOUND __________" + pointDepart.toString() + " => " + pointArrivee.toString() + "__________ steps=" + steps);

                return restitutePath(u);
            }

            for (Node voisin : getNeighbours(u)) {
                if (!(voisin.existIn(close) || voisin.existWithInferiorCost(openList))) {
                    voisin.cost = u.cost + 1;
                    voisin.heuristic = voisin.cost + voisin.point.getDistanceManhattan(pointArrivee.point);

                    openList.add(voisin); // mets un voisin pour le prochain examen
                }
            }

            close.add(u);
        }

        System.out.println("findPath KO __________" + pointDepart.toString() + " => " + pointArrivee.toString() + " NULL ");

        return null;
    }

    private List<Vector2int> restitutePath(Node end) {

        List<Vector2int> path = new ArrayList<>();

        do {
            path.add(map.mapTileToPixels(end.point));
            end = end.parent;
        }
        while (end != null);

        return path;
    }

    private List<Node> getNeighbours(Node point) {

        Node nodeGauche, nodeDroite, nodeHaut, nodeBas;
        nodeGauche = makeNeighbour(point, -1, 0);
        nodeDroite = makeNeighbour(point, +1, 0);
        nodeHaut = makeNeighbour(point, 0, +1);
        nodeBas = makeNeighbour(point, 0, -1);

        List<Node> Neighbours = new ArrayList<>();

        if (isValidNode(nodeGauche)) Neighbours.add(nodeGauche);

        if (isValidNode(nodeDroite)) Neighbours.add(nodeDroite);

        if (isValidNode(nodeHaut)) Neighbours.add(nodeHaut);

        if (isValidNode(nodeBas)) Neighbours.add(nodeBas);

//        System.out.println("getNeighbours::::::::::::::::: " + nodeGauche + " * " + nodeDroite + " * " + nodeHaut + " * " + nodeBas + " = " + Neighbours.size());

        return Neighbours;
    }

    private Node makeNeighbour(Node parent, int xDiff, int yDiff) {
        return new Node(parent.point.x + xDiff, parent.point.y + yDiff, parent);
    }

    private boolean isValidNode(Node node) {

        boolean isXValid = node.point.x < map.mapWidthInTiles && node.point.x >= 0;

        boolean isYValid = node.point.y < map.mapHeightInTiles && node.point.y >= 0;

        if (!isXValid || !isYValid) {
            return false;
        }

        return (!map.isTileObstacle(node.point.x, node.point.y));
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}

