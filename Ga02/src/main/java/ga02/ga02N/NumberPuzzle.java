package ga02.ga02N;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class NumberPuzzle {

    private static Stack<int[][]> stack = new Stack<>();
    private static Queue<int[][]> queue = new LinkedList<>();
    private static HashMap<int[][],int[][]> map = new HashMap<>();

    private final static int[][] LSG = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    public  Queue<int[][]> dfs_limited(int[][] puzzle, int depth){
        map.put(puzzle,puzzle);
        stack.push(puzzle);
        int[][] current_Puzzle;
        int[][] current_Neighbor;
        boolean found = comparePuzzle(puzzle,LSG);
        while (!found || !stack.empty()){
            current_Puzzle = stack.peek();
            printPuzzle(current_Puzzle);
            current_Neighbor = nextNeighbor(current_Puzzle);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(current_Neighbor != null){
                System.out.println("Ever here1?");
                if(comparePuzzle(current_Neighbor,LSG)){
                    System.out.println("Ever here2?");
                    queue.add(current_Neighbor);
                    while (!stack.empty()){
                        queue.add(stack.pop());
                    }
                    found = true;
                }else if(!findPuzzleInStack(current_Neighbor) && stack.size() < 20){
                    System.out.println("Put in Stack:" + stack.size());
                    stack.add(current_Neighbor);
                }
            }else {
                System.out.println("Null");
                stack.pop();
                map.remove(current_Puzzle);
            }
        }
        if(queue.isEmpty()){System.out.println("Keine Lösung gefunden");}

        return queue;
    }

     public static int[][] nextNeighbor(int[][] oripuzzle){
        int[][] copy = copyPuzzle(oripuzzle);
        int index = findZero(copy);
        if(index == -1) return null;
        int x = index/3;
        int y = index%3;

         if(x+1 < 3){
             System.out.println("Fall 1");
             switchNum(copy, x,y,x+1,y);
             if(!findPuzzleInStack(copy)){
                 System.out.println("Nicht in stack");
                 return copy;
             }
             switchNum(copy,x+1,y,x,y);
         }
         if(y+1 < 3){
             System.out.println("Fall 2");
             switchNum(copy, x,y,x,y+1);
             if(!findPuzzleInStack(copy)) return copy;
             switchNum(copy,x,y+1,x,y);
         }
         if(x-1 >= 0){
             System.out.println("Fall 3");
             switchNum(copy, x,y,x-1,y);
             if(!findPuzzleInStack(copy)) return copy;
             switchNum(copy, x-1,y,x,y);
         }
         if(y-1 >= 0){
             System.out.println("Fall 4");
             switchNum(copy, x,y,x,y-1);
             if(!findPuzzleInStack(copy)) return copy;
             switchNum(copy, x,y-1,x,y);
         }
        return null;
     }

    private static int[][] copyPuzzle(int[][] oripuzzle){
        int[][] out = new int[3][3];
        for (int i = 0; i < 3;++i){
            System.arraycopy(oripuzzle[i], 0, out[i], 0, 3);
        }
        return out;
    }

    public static int[][] rondomPuzzle() {
        int[][] out = new int[3][3];
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tmp.add(i);
        }
        Collections.shuffle(tmp);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                out[i][j] = tmp.get(i * 3 + j);
            }
        }
        return out;
    }

    private static int findZero(int[][] puzzle){
        for (int i = 0; i < 3; ++i){
            for (int j = 0; j < 3;++j){
                if (puzzle[i][j] == 0) {
                    return  3*i + j;
                }
            }
        }
        return -1;
    }

    private static void switchNum(int[][] puzzle, int x1, int y1, int x2, int y2){
        int tmp = puzzle[x1][y1];
        puzzle[x1][y1] = puzzle[x2][y2];
        puzzle[x2][y2] = tmp;
    }

    public static void printPuzzle(int[][] puzzle) {

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print("|" + puzzle[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("------------------");
    }

    private static boolean findPuzzleInStack(int[][] puzzle) {
        for (int[][] p : stack) {
            if (comparePuzzle(p, puzzle)) {
                return true;
            }
        }
        return false;
    }
    private static boolean comparePuzzle(int[][] p1, int[][] p2) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (p1[i][j] != p2[i][j]) return false;
            }
        }
        return true;
    }

}

