package ga02.ga02N;
import java.util.ArrayList;
import java.util.List;


/**
 * Class to represent a game field for a sliding puzzle problem
 * The field is stored in a 2d-Array
 * 
 * 
 * @author thart
 *
 */
public class Spielfeld implements Comparable{

	//matrix for the field
	private int[][] matrix;
	
	
	/**
	 * Constructor to initialize the 3x3 field
	 * 
	 */
	public Spielfeld(){
		matrix = new int [3][3];
	}
	
	/**
	 * Method, which returns the field
	 * 
	 * @return field as 2d-array
	 */
	public int[][] getSpielfeld(){
		return matrix;
	}
	
	
	/**
	 * Method to set the field
	 * 
	 * @param matrixNew
	 */
	public void setSpielfeld(int[][]matrixNew){
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0; j<matrix[i].length;j++){
				matrix[i][j] = matrixNew[i][j];
			}
		}
		
	}
	
	/**
	 * Method to set a specific number on the field
	 * The first parameter is the line the second the column
	 * The third parameter is the value
	 * 
	 * @param i
	 * @param j
	 * @param set
	 */
	public void setField(int i, int j, int set){
		this.matrix[i][j] = set;
	}
	
	
	/**
	 * Method to return all the neighbors of a certain field
	 * 
	 * @return List with all the neighbors
	 */
	public List<Spielfeld> getNeighbors(){
		List<Spielfeld> l = new ArrayList();
		//search the 0, which represents the empty place
		int[] emptyField = searchEmptyField(this.getSpielfeld());
		
		//field for the right neighbor
		Spielfeld s1 = new Spielfeld();
		//field for the left neighbor
		Spielfeld s2 = new Spielfeld();
		//field for the down neighbor
		Spielfeld s3 = new Spielfeld();
		//field for the up neighbor
		Spielfeld s4 = new Spielfeld();
		
		
		//right
		if(emptyField[1] < 2){
			s1.setSpielfeld(this.getSpielfeld());
			s1.setField(emptyField[0], emptyField[1], this.getSpielfeld()[emptyField[0]][emptyField[1]+1]);
			s1.setField(emptyField[0], emptyField[1]+1, 0);
			l.add(s1);
		}	
		//left
		if(emptyField[1] > 0){
			s2.setSpielfeld(this.getSpielfeld());
			s2.setField(emptyField[0], emptyField[1], this.getSpielfeld()[emptyField[0]][emptyField[1]-1]);
			s2.setField(emptyField[0],emptyField[1]-1,0);
			l.add(s2);
		}
		//down
		if(emptyField[0] < 2){
			s3.setSpielfeld(this.getSpielfeld());
			s3.setField(emptyField[0], emptyField[1], this.getSpielfeld()[emptyField[0]+1][emptyField[1]]);
			s3.setField(emptyField[0]+1,emptyField[1],0);
			l.add(s3);
		}
		//up
		if(emptyField[0] > 0){
			s4.setSpielfeld(this.getSpielfeld());
			s4.setField(emptyField[0], emptyField[1], this.getSpielfeld()[emptyField[0]-1][emptyField[1]]);
			s4.setField(emptyField[0]-1,emptyField[1],0);
			l.add(s4);
			
		}	
		return l;	
	}
	
	/**
	 * Method to look for the empty field in the given matrix
	 * 
	 * @param neighborMatrix
	 * @return tuple, first part for the line, second for the column
	 */
	private int[] searchEmptyField(int[][] neighborMatrix){
		int[] found = new int[2];
		
		//search the 0
		for(int i=0; i<neighborMatrix.length;i++){
			for(int j=0; j<neighborMatrix[i].length;j++){
				if(neighborMatrix[i][j] == 0){
					found[0] = i;
					found[1] = j;
					return found;
				}
				
			}
		}
		//case that the 0 was not found
		return null;
	}
	
	/**
	 * Equals-Method, to compare two fields
	 * 
	 * @param s1
	 * @return boolean, true if equals
	 */
	public boolean equals(Spielfeld s1){
		boolean flag = true;
		
		for(int i=0;i<s1.getSpielfeld().length;i++){
			for(int j=0; j<s1.getSpielfeld()[i].length;j++){
				if(s1.getSpielfeld()[i][j] != this.getSpielfeld()[i][j]){
					flag = false;
				}
					
			}
		}	
			
		return flag;
	}
	
	/**
	 * Method to print a field
	 * 
	 */
	public void printSpielfeld(){
		for(int i=0;i<this.getSpielfeld().length;i++){
			for(int j=0; j<this.getSpielfeld()[i].length;j++){
				System.out.print(this.getSpielfeld()[i][j] + " ");
			}
			System.out.println();
		}	
	}


	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
