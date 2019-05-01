package ga02.ga02N;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Verschiebepuzzel {

	//stack
	static Stack<Spielfeld> s = new Stack();
	//list to store all the visited fields
	static List<Spielfeld> visited = new ArrayList();
	
	/**
	 * Start the deep-search with a random field
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Spielfeld start = new Spielfeld();
		
		//start the search
		System.out.println(dfs_beschraenkt(getRandomStart(),10000));
		

	}
	
	/**
	 * Deep-search method
	 * 
	 * @param start
	 * @param q, the constraint for the search
	 * @return whether the solution could be found
	 */
	private static boolean dfs_beschraenkt(Spielfeld start,int q){
		
		//create the solution
		int[][] solutionMatrix = {{1,2,3},{4,5,6},{7,8,0}};
		Spielfeld solution= new Spielfeld();
		solution.setSpielfeld(solutionMatrix);
		
		//check if the start field equals the solution
		if(start.equals(solution)){
			return true;
		}
		s.push(start);
		visited.add(start);
		
		
		while(!s.isEmpty()){
			Spielfeld game = s.peek();
			//add the visited field to the list
			if(!visited.contains(game)) visited.add(game);
			
			game.printSpielfeld();
			System.out.println();
			
			//get a neighbor
			Spielfeld neighbor = getNeighbor(game);
			
			//look if a neighbor could be found
			if(neighbor != null && s.size() < q){
				//check if the neighbor is the solution
				if(neighbor.equals(solution)){
					return true;
				}
				// push the neighbor on the stack if the limit is not reached
				// and the neighbor is not visited
				if(!visited.contains(neighbor) && s.size() < q){
					s.push(neighbor);
				}
			}
			else{
				s.pop();
			}
		}
		return false;
		
		
	}
	
	
	/**
	 * Method, which return one unvisited neighbor of a field, by
	 * using the getNeighbors method of the Spielfeld class
	 * 
	 * @param s1
	 * @return one neighbor of the field parameter
	 */
	private static Spielfeld getNeighbor(Spielfeld s1){
		List<Spielfeld> l = s1.getNeighbors();
		boolean[] notFoundNeighbors = new boolean[l.size()];
		for(int k=0;k<notFoundNeighbors.length;k++){
			notFoundNeighbors[k] = true;
		}
			
		for(int i=0; i<l.size();i++){
			for(int j=0;j<visited.size();j++){
				if(l.get(i).equals(visited.get(j))) notFoundNeighbors[i] = false;
			}
		}
		
		for(int v=0;v<notFoundNeighbors.length;v++){
			if(notFoundNeighbors[v] != false) return l.get(v);
		}
		
		//case no unvisited neighbor could be found
		return null;
		
		
	}
	
	/**
	 * Method to create a random start-field
	 * 
	 * @return random field
	 */
	private static Spielfeld getRandomStart(){
		
		Spielfeld s = new Spielfeld();
		int[][] matrix = new int[3][3];
		//List to store, which numbers are already in the field
		List l = new ArrayList();
		Random r = new Random();
		//set the 0 and store the line and column of it
		int emptyX = r.nextInt(3);
		int emptyY = r.nextInt(3);
		matrix[emptyX][emptyY] = 0;
		l.add(0);
		
		//set a random number to every remaining position on the field
		for(int i=0;i<matrix.length;i++){
			for(int j=0; j<matrix[i].length;j++){
				//do not override the 0
				if(!(i == emptyX && j == emptyY)){
					boolean flag = false;
					while(flag == false){
						int next = r.nextInt(9);
						if(!l.contains(next) && next != 0){
							matrix[i][j] = next;
							l.add(next);
							flag = true;
						}	
					}
					
				}
				
			}
		}
		//set the field with the matrix
		s.setSpielfeld(matrix);
		
		return s;
	}
	

}
