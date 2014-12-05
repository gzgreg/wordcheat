package wordcheat;

import java.util.Comparator;

public class WordAnalysisResult {

	private String data;
	private WordBoard board;
	private int height;
	private int width;
	private int top;
	private int bottom;
	private int newLetters;
	
	public WordAnalysisResult(String data, WordBoard board, int newLetters){
		this.data = data;
		this.board = board;
		this.newLetters = newLetters;
		
		char[][] boardData = board.getBoard();
		int top = WordBoard.NUM_ROWS+1, bottom = 0;
		int left = boardData[0].length, right = 0;
		for(int i = 0; i < WordBoard.NUM_ROWS; i++){
			for(int j = 0; j < boardData[0].length; j++){
				char currChar = boardData[i][j];
				if(currChar == '.'){
					if(top == WordBoard.NUM_ROWS + 1){
						top = i;
					} else{
						bottom = i;
					}
					if(j < left){
						left = j;
					}
					if(j > right){
						right = j;
					}
				}
			}
		}
		
		this.height = bottom - top + 1;
		this.width = right-left + 1;
		this.top = top;
		this.bottom = bottom;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public String getWord(){
		return data;
	}
	
	public int getTop(){
		return top;
	}
	
	public int getBottom(){
		return bottom;
	}
	
	public int getNewLetters(){
		return newLetters;
	}
	
	public static enum Order implements Comparator<WordAnalysisResult>{
	    
	    ByLength(){
	    	public int compare(WordAnalysisResult o1, WordAnalysisResult o2) {
	    	
				if (o1.getWord().length() < o2.getWord().length()) {
					return 1;
				} else if (o1.getWord().length() > o2.getWord().length()) {
					return -1;
				}
				
				if(o1.getHeight() > o2.getHeight()){
					return 1;
				}
				if(o1.getHeight() == o2.getHeight()){
					return 0;
				}
				
				return -1;
			}
	    },
	    ByHeight(){
	    	public int compare(WordAnalysisResult o1, WordAnalysisResult o2) {
		    	
	    		if(o1.getHeight() < o2.getHeight()){
					return 1;
				}
				if(o1.getHeight() > o2.getHeight()){
					return -1;
				}
				
				if (o1.getWord().length() < o2.getWord().length()) {
					return 1;
				} else if (o1.getWord().length() == o2.getWord().length()) {
					return 0;
				}
				
				return -1;
			}
	    },
	    ByWidth(){
	    	public int compare(WordAnalysisResult o1, WordAnalysisResult o2) {
		    	
	    		if(o1.getWidth() < o2.getWidth()){
					return 1;
				}
				if(o1.getWidth() > o2.getWidth()){
					return -1;
				}
				
				if (o1.getWord().length() < o2.getWord().length()) {
					return 1;
				} else if (o1.getWord().length() == o2.getWord().length()) {
					return 0;
				}
				
				return -1;
			}
	    },
	    
	    ByTop(){
	    	public int compare(WordAnalysisResult o1, WordAnalysisResult o2) {
		    	
	    		if(o1.getTop() > o2.getTop()){
					return 1;
				}
				if(o1.getTop() < o2.getTop()){
					return -1;
				}
				
				if (o1.getWord().length() < o2.getWord().length()) {
					return 1;
				} else if (o1.getWord().length() == o2.getWord().length()) {
					return 0;
				}
				
				return -1;
			}
	    },
	    
	    ByBottom(){
	    	public int compare(WordAnalysisResult o1, WordAnalysisResult o2) {
		    	
	    		if(o1.getBottom() < o2.getBottom()){
					return 1;
				}
				if(o1.getBottom() > o2.getBottom()){
					return -1;
				}
				
				if (o1.getWord().length() < o2.getWord().length()) {
					return 1;
				} else if (o1.getWord().length() == o2.getWord().length()) {
					return 0;
				}
				
				return -1;
			}
	    },
	    
	    ByNewLetters(){
	    	public int compare(WordAnalysisResult o1, WordAnalysisResult o2) {
		    	
	    		if(o1.getNewLetters() < o2.getNewLetters()){
					return 1;
				}
				if(o1.getNewLetters() > o2.getNewLetters()){
					return -1;
				}
				
				if (o1.getWord().length() < o2.getWord().length()) {
					return 1;
				} else if (o1.getWord().length() == o2.getWord().length()) {
					return 0;
				}
				
				return -1;
			}
	    };
	    
	    public abstract int compare(WordAnalysisResult o1, WordAnalysisResult o2);
	}
	
	public boolean equals(Object o){
		if(!(o instanceof WordAnalysisResult)) return false;
		
		WordAnalysisResult o2 = (WordAnalysisResult) o;
		  
		if (this.getWord().length() != o2.getWord().length()) {
		   return false;
		}
		
		if(this.getHeight() != o2.getHeight()){
			return false;
		}
		
		return true;		
	}
	
	public int hashCode(){
		return this.getWord().hashCode() + this.height;
	}
	
	public String toString(){
		return data + "\n" + board.toString();
	}

}
