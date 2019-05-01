/**
 * Is a 3x3 Puzzle with depth-search for finding the solution
 */
package ga02;

import java.util.*;

public class Puzzle implements Comparable<Puzzle>{

    // The Solution
    private final static int[][] LSGARRAY = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private final static Puzzle LSG = new Puzzle(LSGARRAY);

    //The init state. Random or given via constructor
    private int[][] current_puzzle;
    private final int DIM  = 3;
    //Store the visited Puzzle
    private static Stack<Puzzle> stack = new Stack<>();
    //Store the visited Puzzle-Hash for quick compare
    private static HashSet<Integer> set = new HashSet<>();

    /**
     * Constructs a new Puzzle with random numbers
     */
    public Puzzle() {
        this.current_puzzle = randomPuzzle().current_puzzle;
    }

    /**
     * Constructs a new Puzzle with given array
     * @param puzzle_array
     */
    public Puzzle(int [][] puzzle_array) {
        if(puzzle_array.length == DIM && puzzle_array[0].length == DIM){
            this.current_puzzle = puzzle_array;
        }
    }

    /**
     * Go thorugh all neighbor and neighbor neighbors till Stack is empty and no solution is found
     * Or the current Puzzle is equal to LSG
     * @param depth The depth of the search
     * @return True if solution is found. Else false
     */
    public boolean search(int depth){
        System.out.println("Given:");
        printPuzzle();

        //Put init Puzzle in Stack
        stack.push(this);

        while (!stack.empty()){
            //Get the top puzzle
            Puzzle current = stack.peek();
            //add to Set
            set.add(current.hashCode());
            Puzzle neighbor = current.nextNeighbor();
            if(neighbor != null){
                //Has a neighbor
                if(comparePuzzle(neighbor,LSG)){
                    System.out.println("Found Solution");
                    neighbor.printPuzzle();
                    return true;
                }
                if(!seen(neighbor) && stack.search(neighbor) < depth){
                    // Neighbor not seen yet so put it on stack
                    stack.push(neighbor);
                }
            }else {
                //No mor new Neighbors so pop the top Puzzle
                stack.pop();
            }
        }

        System.out.println("Found no Solution");
        return false;
    }

    /**
     * Gets a Neighbor of the node its called of
     * @return A NEW Neighbor or null
     */
    private Puzzle nextNeighbor() {
        Puzzle copy = copyPuzzle();
        //Find the 0
        int index = copy.findZero();
        if (index == -1) return null;
        int pos1 = index / 3;
        int pos2 = index % 3;

        //Check witch switches are allowed
        if(pos1+1 < 3){
            copy = switchNum(copy,pos1,pos2,pos1+1,pos2);
            if(!seen(copy)){
                //If not seen return
                return copy;
            }
            //Seen already. Undo switch.
            copy = switchNum(copy,pos1+1,pos2,pos1,pos2);
        }
        if(pos2+1 < 3){
            copy = switchNum(copy,pos1,pos2,pos1,pos2+1);
            if(!seen(copy)){
                return copy;
            }
            copy = switchNum(copy,pos1,pos2+1,pos1,pos2);
        }
        if(pos1-1 >= 0){
            copy = switchNum(copy,pos1,pos2,pos1-1,pos2);
            if(!seen(copy)){
                return copy;
            }
            copy = switchNum(copy,pos1-1,pos2,pos1,pos2);
        }
        if(pos2-1 >= 0){
            copy = switchNum(copy,pos1,pos2,pos1,pos2-1);
            if(!seen(copy)){
                return copy;
            }
            copy = switchNum(copy,pos1,pos2-1,pos1,pos2);
        }

        //All Neighbors already seen
        return null;
    }

    /**
     * Checks if seen alrady
     * @param o The Puzzle to check
     * @return true if already seen. Else false
     */
    private boolean seen(Puzzle o){
        return set.contains(o.hashCode());
    }

    /**
     * Switch two numbers in Puzzle
     * @param p The Puzzle to switch
     * @param pos1_x Old PosX
     * @param pos1_y Pld PosY
     * @param pos2_x New PosX
     * @param pos2_y New PosY
     * @return The switched Puzzle
     */
    private static Puzzle switchNum(Puzzle p, int pos1_x, int pos1_y, int pos2_x, int pos2_y){
        int[][] puzzle = p.getCurrent_puzzle();
        int tmp = puzzle[pos1_x][pos1_y];
        puzzle[pos1_x][pos1_y] = puzzle[pos2_x][pos2_y];
        puzzle[pos2_x][pos2_y] = tmp;
        return new Puzzle(puzzle);
    }

    /**
     * Compares two Puzzles
     * @param p1 The first Puzzle
     * @param p2 The second Puzzle
     * @return True if the same else false
     */
    private boolean comparePuzzle(Puzzle p1, Puzzle p2) {
        for (int i = 0; i < DIM; ++i) {
            for (int j = 0; j < DIM; ++j) {
                if (p1.getCurrent_puzzle()[i][j] != p2.getCurrent_puzzle()[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * Copy's the Puzzle
     * @return A new independent Puzzle
     */
    private Puzzle copyPuzzle(){
        int[][] out = new int[DIM][DIM];
        for (int i = 0; i < DIM;++i){
            System.arraycopy(current_puzzle[i], 0, out[i], 0, DIM);
        }
        return new Puzzle(out);
    }

    /**
     * Compares this Puzzle with a other one
     * @param o The other Puzzle
     * @return True if the same else false
     */
    @Override
    public int compareTo(Puzzle o) {
        for (int i = 0; i < DIM; ++i) {
            for (int j = 0; j < DIM; ++j) {
                if (current_puzzle[i][j] > o.getCurrent_puzzle()[i][j]) return 1;
                if (current_puzzle[i][j] < o.getCurrent_puzzle()[i][j]) return -1;
            }
        }
        return 0;
    }

    /**
     * Makes a new Puzzle with random Numbers
     * @return A new random Puzzle
     */
    private Puzzle randomPuzzle() {
        int[][] out = new int[DIM][DIM];
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < DIM*DIM; i++) {
            tmp.add(i);
        }
        Collections.shuffle(tmp);
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                out[i][j] = tmp.get(i * DIM + j);
            }
        }
        return new Puzzle(out);
    }

    /**
     * Finds the 0 in the puzzle
     * @return The absolute index in one int.
     */
    private int findZero(){
        for (int i = 0; i < DIM; ++i){
            for (int j = 0; j < DIM;++j){
                if (current_puzzle[i][j] == 0) {
                    return  DIM*i + j;
                }
            }
        }
        return -1;
    }

    /**
     * Prints the Puzzle ot std-out
     */
    public void printPuzzle() {
        for (int i = 0; i < DIM; ++i) {
            for (int j = 0; j < DIM; ++j) {
                System.out.print("|" + current_puzzle[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("------------------");
    }

    /**
     * getter for the int-array
     * @return the current_puzzle
     */
    public int[][] getCurrent_puzzle() {
        return current_puzzle;
    }

    /**
     * Overrides the Hashcode for a easy compare
     * Hash Code the all 9 numbers written one after the other. Stat with index 0,0
     * @return The HashCode
     */
    @Override
    public int hashCode(){
        int hash = 0;
        for (int i = 0; i < DIM; ++i) {
            for (int j = 0; j < DIM; ++j) {
                hash += current_puzzle[i][j] * (int)Math.pow(10,(i*DIM+j)+1);
            }
        }
        return hash;
    }

    /**
     * Old method that compared of the Puzzle o is alrady in the Stack
     * @param o The Puzzle to check if it is in Stack
     * @return True if it is in stack else false
     */
    private boolean isInStack(Puzzle o){
        for(Puzzle p: stack){
            if(comparePuzzle(p,o)) return true;
        }
        return false;
    }
}


