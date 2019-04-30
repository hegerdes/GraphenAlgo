package de.uos.inf.ko.ga.graph.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.converter.GraphConverter;
import de.uos.inf.ko.ga.graph.impl.DirectedGraphMatrix;
import de.uos.inf.ko.ga.graph.impl.UndirectedGraphMatrix;

public class GraphReader {

	/**
	 * Reads a directed graph from a file.
	 * @param f File to be read
	 * @return Directed graph
	 */
	public static Graph readDirectedGraph(File f) {
		//Create the graph
		DirectedGraphMatrix graph = new DirectedGraphMatrix();
		
		try { 
			//Create Reader to read the file, given in the parameters
            BufferedReader in = new BufferedReader(new FileReader(f.getPath())); 
            //Skip the first line because it is a comment
            in.readLine();
            //read the number of vertices into a string-array
            String[] line = in.readLine().trim().split("\\s+");  
            //read the number behind the n and create the applicable number of vertices
            graph.addVertices(Integer.parseInt(line[1]));
            //Skip the one line because it is a comment
            in.readLine();
            //String to store the given matrix
            String mat = null;
            //counter to store which line is looked at
            int counter=0;
            //Read matrix
            while((mat = in.readLine()) != null){
            	//Divide the read line on every space
            	line = mat.trim().split("\\s+");
            	//skip the first to symbols
            	for(int i=2;i<line.length;i++){
            		//Create an edge if the specific symbol is not an "x"
            		if(!line[i].equals("x")){
            			graph.addEdge(counter, i-2, Double.parseDouble(line[i]));
            		}
            	}
            	counter++;
            	
            }
            
        } catch (IOException e) { 
            e.printStackTrace(); 
        }   
		return graph;
	}

	/**
	 * Reads an undirected graph from a file.
	 * @param f File to be read
	 * @return Undirected graph
	 */
	public static Graph readUndirectedGraph(File f) {
		//Create the graph
		UndirectedGraphMatrix graph = new UndirectedGraphMatrix();
		
		try { 
			//Create Reader to read the file, given in the parameters
            BufferedReader in = new BufferedReader(new FileReader(f.getPath())); 
            //Skip the first line because it is a comment
            in.readLine();
            //read the number of vertices into a string-array
            String[] line = in.readLine().trim().split("\\s+"); 
            //read the number behind the n and create the applicable number of vertices
            graph.addVertices(Integer.parseInt(line[1]));
            //Skip the one line because it is a comment
            in.readLine();
            //String to store the given matrix
            String mat = null;
            //counter to store which line is looked at
            int counter=0;
            //Read matrix
            while((mat = in.readLine()) != null){
            	//Divide the read line on every space
            	line = mat.trim().split("\\s+");
            	//skip the first to symbols
            	for(int i=2;i<line.length;i++){
            		//Create an edge if the specific symbol is not an "x"
            		if(!line[i].equals("x")){
            			graph.addEdge(counter, i-2, Double.parseDouble(line[i]));
            		}
            	}
            	counter++;
            	
            }
            
        } catch (IOException e) { 
            e.printStackTrace(); 
        }   
		return graph;
	}

}
