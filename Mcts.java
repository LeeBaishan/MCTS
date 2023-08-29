package montecarlo;

import java.util.ArrayList;

public class Mcts {

    public Mcts() {

    }

    public Node mcts(Node node) {
        int counts = 1000;
        int reward;
        for (int i = 0; i < counts; i++) {
            node = treePolicy(node);//返回叶节点
            reward = defaultPolicy(node);
            node = backup(node, reward);
        }
        return bestChild(node, false);
    }

    private Node treePolicy(Node node) {
        while (!(Board.isTerminal(node.getBoard().getPathChoice()))) {
            if (node.isAllExpand()) {
                node = bestChild(node, true);
            } else {
                return expand(node);
            }
        }
        return node;
    }

    private Node expand(Node node) {
        Node childNode = new Node(node, new ArrayList<>(), 0.0, 0.0, node.getBoard().nextBoard());
        node.setChildList(childNode);
        return childNode;
    }

    private int defaultPolicy(Node node) {
        ArrayList<String> pathChoice = (ArrayList<String>) node.getBoard().getPathChoice().clone();
        ArrayList<String> spareChoice = (ArrayList<String>) node.getBoard().getSpareChoice().clone();
        while (!(Board.isTerminal(pathChoice))) {
            String str = spareChoice.get((int) (Math.random() * spareChoice.size()));
            pathChoice.add(str);
            spareChoice.remove(str);
        }
        if (node.getBoard().isFirstPlayer()) {
            if (Board.isPlayerOneWin(pathChoice)) {
                return 1;
            }
        }
        if (!(node.getBoard().isFirstPlayer())) {
            if (Board.isPlayerTwoWin(pathChoice)) {
                return 1;
            }
        }
        return 0;
    }

    private Node backup(Node node, int value) {
        while (node.getParent() != null) {
            if (value == 1)
                node.setQualityValue();
            node.setVisitTimes();
            node = node.getParent();
        }
        if (value == 1)
            node.setQualityValue();
        node.setVisitTimes();
        return node;
    }

    private static Node bestChild(Node node, boolean isExploration) {
        Node bestnode = node.getChildList().get(0);
        double bestScore = Double.MIN_VALUE;
        double c;
        for (int i = 0; i < node.getChildList().size(); i++) {
            if (isExploration) {
                c = 2;
            } else {
                c = 0.0;
            }
            double left = node.getChildList().get(i).getQualityValue() / node.getChildList().get(i).getVisitTimes();
            double right = 2.0 * (Math.log(node.getVisitTimes())) / node.getChildList().get(i).getVisitTimes();
            double score = left + c * (Math.sqrt(right));
            if (score > bestScore) {
                bestScore = score;
                bestnode = node.getChildList().get(i);
            }
        }
        return bestnode;
    }

}
