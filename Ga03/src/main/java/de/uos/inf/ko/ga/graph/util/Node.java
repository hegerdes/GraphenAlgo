package de.uos.inf.ko.ga.graph.util;

public class Node implements Comparable<Node> {

    private double weight;
    private int node2;
    private int node1;

    /**
     * Construct new Node with data
     * @param weight
     */
    public Node(double weight, int node1, int node2){
        this.node1 = node1;
        this.weight = weight;
        this.node2 = node2;
    }

    public double getWeight() {
        return weight;
    }

    public int getEnd() {
        return node2;
    }

    public int getStart() {
        return node1;
    }

    @Override
    public int compareTo(Node o) {
        if(this.weight - o.getWeight() == 0) return 0;
        if(this.weight - o.getWeight() > 0) return 1;
        else return -1;
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("Weight: " + weight + "node1: " + node1 + " node2: "  +node2);
        return st.toString();
    }
}
