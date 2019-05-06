package de.uos.inf.ko.ga.graph.util;

public class Node implements Comparable<Node> {

    private double weight;
    private int pred;

    /**
     * Construct new Node with data
     * @param weight
     * @param pred
     */
    public Node(double weight, int pred){
        this.pred = pred;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getPred() {
        return pred;
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
        st.append("Weight: " + weight);
        return st.toString();
    }
}
