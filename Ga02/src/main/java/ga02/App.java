package ga02;

/**
 * Test Call for Puzzle
 *
 */
public class App 
{
    /**
     * NOTE Please run multiple times
     * @param args
     */
    public static void main( String[] args ) {


        // New random Puzzle
        Puzzle puzzle = new Puzzle();
        //Search
        puzzle.search(300);

    }
}
