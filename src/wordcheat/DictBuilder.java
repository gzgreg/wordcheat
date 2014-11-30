package wordcheat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DictBuilder {
	
	private BufferedReader input;
	private String nextWord;
	
	public DictBuilder(String filename) throws IOException{
		this.input = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename)));
		
		this.nextWord = this.input.readLine();
	}
	
	public Tree buildDict() throws IOException{
		Tree tree = new Tree('\0');
		
		do{
			Tree currTree = tree;
			for(int i = 0; i < nextWord.length(); i++){
				char currChar = nextWord.charAt(i);
				if(currChar == '\'') break;
				currChar = Character.toLowerCase(currChar);
				Tree testTree;
				if((testTree = currTree.contains(currChar)) != null){
					currTree = testTree;
				} else{
					Tree newCurrTree = new Tree(currChar);
					currTree.addChild(newCurrTree);
					currTree = newCurrTree;
				}
			}
			currTree.setTerminal();
		}
		while((nextWord = this.input.readLine()) != null);
		
		return tree;
	}
}
