package montecarlo;

import java.util.ArrayList;
import java.util.Scanner;

public class DisPlayChessDetail {
    public static void printFirstWinRate() {
        int count = 1000;
        int count1 = 0;
        for (int i = 0; i < count; i++) {
            boolean changePlayer = true;
            ArrayList<String> pathChoice = new ArrayList<>();
            ArrayList<String> spareChoice = Board.getBoardPlaces();
            while (!(Board.isTerminal(pathChoice))) {
                // 展示下一位棋手的下棋信息
                if (changePlayer) {
                    Node bestNode = alterMessage2(pathChoice, spareChoice, true);
                    pathChoice = bestNode.getBoard().getPathChoice();
                    spareChoice = getSpareChoice(pathChoice);
                    Board.printBoard(pathChoice);
                    changePlayer = false;
                }
                // 展示下一位棋手的下棋信息
                else {
                    alterMessage1(pathChoice, spareChoice);
                    changePlayer = true;
                }
                if (Board.isPlayerOneWin(pathChoice))
                    count1++;
            }
        }
        System.out.println("先手胜率： " + count1);
    }

    public static void printSecondWinRate() {
        int count = 1000;
        int count1 = 0;
        for (int i = 0; i < count; i++) {
            boolean changePlayer = true;
            ArrayList<String> pathChoice = new ArrayList<>();
            ArrayList<String> spareChoice = Board.getBoardPlaces();
            while (!(Board.isTerminal(pathChoice))) {
                // 展示下一位棋手的下棋信息
                if (changePlayer) {
                    alterMessage1(pathChoice, spareChoice);
                    changePlayer = false;
                }
                // 展示下一位棋手的下棋信息
                else {
                    Node bestNode = alterMessage2(pathChoice, spareChoice, false);
                    pathChoice = bestNode.getBoard().getPathChoice();
                    spareChoice = getSpareChoice(pathChoice);
                    Board.printBoard(pathChoice);
                    changePlayer = true;
                }
                if (Board.isPlayerTwoWin(pathChoice))
                    count1++;
            }
        }
        System.out.println("后手胜率： " + count1);
    }

    public static void printHumanVsMachine() {
        ArrayList<String> pathChoice = new ArrayList<>();
        ArrayList<String> spareChoice = Board.getBoardPlaces();
        Scanner in = new Scanner(System.in);
        while (!(Board.isTerminal(pathChoice))) {
            Node bestNode = alterMessage2(pathChoice, spareChoice, true);
            pathChoice = bestNode.getBoard().getPathChoice();
            spareChoice = getSpareChoice(pathChoice);
            Board.printBoard(pathChoice);
            if (Board.isTerminal(pathChoice)) {
                break;
            }
            String s = in.nextLine();
            pathChoice.add(s);
            spareChoice.remove(s);
            Board.printBoard(pathChoice);
        }
    }

    private static Node alterMessage2(ArrayList<String> pathChoice, ArrayList<String> spareChoice, boolean bool) {
        Board board = new Board(pathChoice, spareChoice, bool);
        Node node = new Node(null, new ArrayList<>(), 0.0, 0.0, board);
        Mcts root = new Mcts();
        return root.mcts(node);
    }

    private static void alterMessage1(ArrayList<String> pathChoice, ArrayList<String> spareChoice) {
        String s = spareChoice.get((int) (Math.random() * spareChoice.size()));
        pathChoice.add(s);
        spareChoice.remove(s);
        Board.printBoard(pathChoice);
    }

    private static ArrayList<String> getSpareChoice(ArrayList<String> pathChoice) {
        ArrayList<String> spare = Board.getBoardPlaces();
        for (String s : pathChoice) {
            spare.remove(s);
        }
        return spare;
    }
}
