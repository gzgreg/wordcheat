package wordcheat;

import java.util.ArrayList;

public class Tree {
	public ArrayList<Tree> children;
	public char data;
	public Tree parent;
	public boolean isTerminal;
	
	public Tree(char data) {
		this.data = data;
		this.children = new ArrayList<Tree>();
		this.isTerminal = false;
		this.parent = null;
	}
	
	public void addChild(Tree tree){
		children.add(tree);
		tree.addParent(this);
	}
	
	public void addParent(Tree parent){
		this.parent = parent;
	}
	
	public void setTerminal(){
		this.isTerminal = true;
	}
	
	public char getChar(){
		return data;
	}
	
	public Tree getParent(){
		return parent;
	}
	
	public ArrayList<Tree> getChildren(){
		return children;
	}
	
	public Tree contains(char character){
		for(Tree tree : children){
			if(tree.getChar() == character) return tree;
		}
		
		return null;
	}
}
