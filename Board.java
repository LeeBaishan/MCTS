package montecarlo;

import java.util.ArrayList;

public class Board {
    private ArrayList<String> pathChoice;
    private ArrayList<String> spareChoice;
    private boolean firstPlayer;

    public Board() {
        this(new ArrayList<>(), Board.getBoardPlaces(), true);
    }

    public Board(ArrayList<String> pathChoice, ArrayList<String> spareChoice, boolean firstPlayer) {
        this.pathChoice = pathChoice;
        this.spareChoice = spareChoice;
        this.firstPlayer = firstPlayer;
    }

    public ArrayList<String> getPathChoice() {
        return pathChoice;
    }

    public ArrayList<String> getSpareChoice() {
        return spareChoice;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public static ArrayList<String> getBoardPlaces() {
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String index = i + "" + j;
                temp.add(index);
            }
        }
        return temp;
    }

    public Board nextBoard() {
        ArrayList<String> path = (ArrayList<String>) pathChoice.clone();
        ArrayList<String> spare = Board.getBoardPlaces();
        String str = spareChoice.get((int) (Math.random() * spareChoice.size()));
        path.add(str);
        spareChoice.remove(str);
        for (String e : path) {
            spare.remove(e);
        }
        return new Board(path, spare, isFirstPlayer());
    }

    public static boolean isTerminal(ArrayList<String> pathChoice) {
        if (Board.isPlayerOneWin(pathChoice)) {
            return true;
        } else if (Board.isPlayerTwoWin(pathChoice)) {
            return true;
        } else return pathChoice.size() == 9;
    }

    public static boolean isPlayerOneWin(ArrayList<String> pathChoice) {
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < pathChoice.size(); i++) {
            if (i % 2 == 0) {
                list1.add(pathChoice.get(i));
            }
        }
        return isVictory(list1);
    }

    public static boolean isPlayerTwoWin(ArrayList<String> pathChoice) {
        ArrayList<String> list2 = new ArrayList<>();
        for (int i = 0; i < pathChoice.size(); i++) {
            if (i % 2 != 0) {
                list2.add(pathChoice.get(i));
            }
        }
        return isVictory(list2);
    }

    public static void printBoard(ArrayList<String> pathChoice) {
        String[][] checker = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                checker[i][j] = " ";
            }
        }
        for (int i = 0; i < pathChoice.size(); i++) {
            String string = pathChoice.get(i);
            int a = (int) string.charAt(0) - (int) '0';
            int b = (int) string.charAt(1) - (int) '0';
            if (i % 2 == 0) {
                checker[a][b] = "x";
            } else {
                checker[a][b] = "o";
            }
        }
        System.out.print("  ");
        for (int i = 0; i < 3; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.print(i);
            for (int j = 0; j < 3; j++)
                System.out.print("|" + checker[i][j]);
            System.out.println("|");
        }
        System.out.println();
    }

    public static boolean isVictory(ArrayList<String> player) {
        if (player.contains("00") && player.contains("01") && player.contains("02")) {
            return true;
        } else if (player.contains("10") && player.contains("11") && player.contains("12")) {
            return true;
        } else if (player.contains("20") && player.contains("21") && player.contains("22")) {
            return true;                                                                       //横着
        } else if (player.contains("00") && player.contains("10") && player.contains("20")) {
            return true;
        } else if (player.contains("01") && player.contains("11") && player.contains("21")) {
            return true;
        } else if (player.contains("02") && player.contains("12") && player.contains("22")) {
            return true;                                                                       //竖着
        } else if (player.contains("00") && player.contains("11") && player.contains("22")) {
                return true;
        } else {
            return player.contains("02") && player.contains("11") && player.contains("20");//斜着
        }
    }
}
