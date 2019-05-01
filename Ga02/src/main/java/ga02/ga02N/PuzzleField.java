package ga02.ga02N;

import java.util.ArrayList;

public class PuzzleField {

    private int m_field[][];

    public PuzzleField(int[][] field){
        int leng = field.length;
        for(int i = 0; i < leng; i++){
            if(field[i].length != leng){
                throw new ExceptionInInitializerError("Kein Quatratisches Array");
            }
        }
        m_field = field;
        m_field[2][2] = 0;
    }

    public boolean isSolved(){
        for(int i = 0; i < m_field.length; i++){
            for(int j = 0; j<m_field[i].length; j++){
                if(i == m_field.length-1 && j == m_field.length-1 && m_field[i][j] == 0) return true;
                if(m_field[i][j] != (i*m_field.length + j) + 1){
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<PuzzleField> getPossible(){
        ArrayList<PuzzleField> possible = new ArrayList<>();
        for(int i = 0; i < m_field.length; i++){
            for(int j = 0; j<m_field[i].length; j++){
                if(m_field[i][j] == 0){
                    if(i + 1 < m_field.length && i >= 0) possible.add(swap(i,j,i+1,j));
                    if(i - 1 < m_field.length && i >= 0) possible.add(swap(i,j,i-1,j));
                    if(j + 1 < m_field.length && j >= 0) possible.add(swap(i,j,i,j+1));
                    if(j - 1 < m_field.length && j >= 0) possible.add(swap(i,j,i,j-1));
                }
            }
        }
        return possible;
    }

    public PuzzleField swap(int a, int b, int x ,int y){

        return new PuzzleField(new int[2][3]);
    }

    public void pirnt(){
        for(int i = 0; i < m_field.length; i++){
            System.out.print("|");
            for(int j = 0; j < m_field.length; j++){
                System.out.print(m_field[i][j] + " |");
            }
            System.out.println();
        }
    }
}
