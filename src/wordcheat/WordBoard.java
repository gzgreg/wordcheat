package wordcheat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class WordBoard {
	
	private char[][] board;
	private int winRow;
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
		
		winRow = NUM_ROWS - 1;
		for(int i = 0; i < this.board[0].length; i++){
			if(!Character.isUpperCase(this.board[0][i])){ //checks if top row is all uppercase
				winRow = 0;
			}
		}
	}
	
	public WordBoard(char[][] data) {
		this.board = data.clone();
		
		winRow = NUM_ROWS - 1;
		
		for(int i = 0; i < this.board[0].length; i++){
			if(!Character.isUpperCase(this.board[0][i])){ //checks if top row is all uppercase
				winRow = 0;
			}
		}
	}
	
	public void addWord(char[][] board){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				if(board[i][j] == '.'){
					this.board[i][j] = Character.toUpperCase(this.board[i][j]);
				}
			}
		}
	}
	
	public static ArrayList<WordAnalysisResult> analyze(Tree dict, WordBoard board){
		ArrayList<WordAnalysisResult> toReturn = new ArrayList<WordAnalysisResult>();
		char[][] charArray = board.getBoard();
		for(int i = 0; i < charArray.length; i++){
			for(int j = 0; j < charArray[0].length; j++){
				if(Character.isUpperCase(charArray[i][j])){
					char[][] copyBoard = deepCopy(charArray);
					
					copyBoard[i][j] = '.';
					WordBoard newBoard = new WordBoard(copyBoard);
					
					ArrayList<WordAnalysisResult> returned = analyzeFrom(dict.contains(Character.toLowerCase(charArray[i][j])), i, j, newBoard, 0);
					
					toReturn.addAll(returned);
				}
			}
		}
		
		return toReturn;
	}
	
	private static ArrayList<WordAnalysisResult> analyzeFrom(Tree dict, int row, int col, WordBoard wordboard, int newLetters){
		Tree currTree = dict;
		ArrayList<WordAnalysisResult> toReturn = new ArrayList<WordAnalysisResult>();
		char[][] board = wordboard.getBoard();		
		
		for(int i = row-1; i <= row+1; i++){
			if(i < 0 || i >= NUM_ROWS){
				continue;
			}
			for(int j = col-1; j <= col+1; j++){
				if(j < 0 || j > board[0].length - 1){
					continue;
				}
				
				char currChar = Character.toLowerCase(board[i][j]);
				if((currTree = dict.contains(currChar)) != null){
					int newnewLetters;
					if(Character.isLowerCase(board[i][j])) newnewLetters = newLetters + 1;
					else newnewLetters = newLetters;
					char[][] copyBoard = deepCopy(board);
					
					copyBoard[i][j] = '.';
					
					if(currTree.isTerminal){
						String s = "" + currTree.getChar();
						Tree parentTree = currTree.getParent();
						
						while(parentTree.getChar() != '\0'){
							s = parentTree.getChar() + s;
							parentTree = parentTree.getParent();
						}
						
						WordAnalysisResult toAdd = new WordAnalysisResult(s, new WordBoard(copyBoard), newnewLetters);
						toReturn.add(toAdd);
					}
					
					WordBoard newBoard = new WordBoard(copyBoard);
					
					ArrayList<WordAnalysisResult> returned = analyzeFrom(currTree, i, j, newBoard, newnewLetters);
					toReturn.addAll(returned);
				}
			}
		}
		
		return toReturn;
	}
	
	public static WordBoard analyzeWin(Tree dict, WordBoard board, int winRow){
		char[][] returnBoard = board.getBoard();
		char[][] originalBoard = board.getBoard();
		for(int i = 0; i < NUM_ROWS; i++){
			for(int j = 0; j < originalBoard[0].length; j++){
				boolean isWinning = analyzeWinFrom(dict.contains(Character.toLowerCase(originalBoard[i][j])), i, j, winRow, board);
				if(isWinning) returnBoard[i][j] = '.';
			}
		}
		
		return new WordBoard(returnBoard);
	}
	
	private static boolean analyzeWinFrom(Tree dict, int row, int col, int winRow, WordBoard wordboard){
		Tree currTree = dict;
		char[][] board = wordboard.getBoard();		
		
		for(int i = row-1; i <= row+1; i++){
			if(i < 0 || i >= NUM_ROWS){
				continue;
			}
			for(int j = col-1; j <= col+1; j++){
				if(j < 0 || j > board[0].length - 1){
					continue;
				}
				
				char currChar = Character.toLowerCase(board[i][j]);
				if((currTree = dict.contains(currChar)) != null){
					char[][] copyBoard = deepCopy(board);
					
					copyBoard[i][j] = '.';
					
					if(currTree.isTerminal){
						if(i == winRow){
							return true;
						}
					}
					
					WordBoard newBoard = new WordBoard(copyBoard);
					
					boolean returned = analyzeWinFrom(currTree, i, j, winRow, newBoard);
					if(returned) return true;
				}
			}
		}
		
		return false;
	}
	
	public char[][] getBoard(){
		return deepCopy(board);
	}
	
	public int getWinRow(){
		return winRow;
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
