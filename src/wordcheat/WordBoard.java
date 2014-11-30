package wordcheat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class WordBoard {
	
	private char[][] board;
	public static final int NUM_ROWS = 13;
	
	public WordBoard(String inputFile) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(
				new FileInputStream(inputFile)));
		
		String line = input.readLine();
		if(line != null){
			board = new char[NUM_ROWS][line.length()];
		}
		int j = 0;
		do{
			for(int i = 0; i < line.length(); i++){
				char currChar = line.charAt(i);
				board[j][i] = currChar;
			}
			j++;
		} while((line = input.readLine()) != null);
		
		input.close();
	}
	
	public WordBoard(char[][] data) {
		this.board = data.clone();
	}
	
	public static ArrayList<WordAnalysisResult> analyze(Tree dict, WordBoard board){
		ArrayList<WordAnalysisResult> toReturn = new ArrayList<WordAnalysisResult>();
		char[][] charArray = board.board;
		for(int i = 0; i < charArray.length; i++){
			for(int j = 0; j < charArray[0].length; j++){
				if(Character.isUpperCase(charArray[i][j])){
					char[][] copyBoard = deepCopy(charArray);
					
					copyBoard[i][j] = '.';
					WordBoard newBoard = new WordBoard(copyBoard);
					
					ArrayList<WordAnalysisResult> returned = analyzeFrom(dict.contains(Character.toLowerCase(charArray[i][j])), i, j, newBoard);
					
					toReturn.addAll(returned);
				}
			}
		}
		
		return toReturn;
	}
	
	private static ArrayList<WordAnalysisResult> analyzeFrom(Tree dict, int row, int col, WordBoard wordboard){
		Tree currTree = dict;
		ArrayList<WordAnalysisResult> toReturn = new ArrayList<WordAnalysisResult>();
		char[][] board = wordboard.board;		
		
		for(int i = row-1; i <= row+1; i++){
			if(i < 0 || i > NUM_ROWS - 1){
				continue;
			}
			for(int j = col-1; j <= col+1; j++){
				if(j < 0 || j > board[0].length - 1){
					continue;
				}
				
				char currChar = Character.toLowerCase(board[i][j]);
				if((currTree = dict.contains(currChar)) != null){
					if(currTree.isTerminal){
						String s = "" + currTree.getChar();
						Tree parentTree = currTree.getParent();
						
						while(parentTree.getChar() != '\0'){
							s = parentTree.getChar() + s;
							parentTree = parentTree.getParent();
						}
						WordAnalysisResult toAdd = new WordAnalysisResult(s, wordboard);
						toReturn.add(toAdd);
					}
					char[][] copyBoard = deepCopy(board);
					
					copyBoard[i][j] = '.';
					WordBoard newBoard = new WordBoard(copyBoard);

					ArrayList<WordAnalysisResult> returned = analyzeFrom(currTree, i, j, newBoard);
					toReturn.addAll(returned);
				}
			}
		}
		
		return toReturn;
	}
	
	public char[][] getBoard(){
		return deepCopy(board);
	}
	
	private static char[][] deepCopy(char[][] original) {
	    if (original == null) {
	        return null;
	    }

	    final char[][] result = new char[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return result;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				s += board[i][j];
			}
			s += "\n";
		}
		return s;
	}
}
