package montecarlo;

import java.util.ArrayList;

public class Node {
    private Node parent;
    private ArrayList<Node> childList;
    private double visitTimes;
    private double qualityValue;
    private Board board;

    public Node() {
        this(null, new ArrayList<>(), 0.0, 0.0, new Board());
    }

    public Node(Node parent, ArrayList<Node> childList, double visitTimes, double qualityValue, Board board) {
        this.parent = parent;
        this.childList = childList;
        this.visitTimes = visitTimes;
        this.qualityValue = qualityValue;
        this.board = board;
    }

    Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildList() {
        return childList;
    }

    public void setChildList(Node child) {
        this.childList.add(child);
    }

    public double getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes() {
        this.visitTimes = visitTimes + 1;
    }

    public double getQualityValue() {
        return qualityValue;
    }

    public void setQualityValue() {
        this.qualityValue = qualityValue + 1;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isAllExpand() {
        return this.board.getSpareChoice().isEmpty();
    }
}
