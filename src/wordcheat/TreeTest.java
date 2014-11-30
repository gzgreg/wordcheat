package wordcheat;

import java.io.IOException;

public class TreeTest {

	public static void main(String[] args) throws IOException {
		DictBuilder builder = new DictBuilder("src/words.txt");
		
		Tree tree = builder.buildDict();

		printTree(tree);
		System.out.println();
		
		Tree currTree = tree.getChildren().get(6);
		
		for(int i = 0; i < 7; i++){
			System.out.print(currTree.getChar());
			currTree = currTree.getChildren().get(currTree.getChildren().size() - 1);	
		}
		System.out.println();
		
		for(int i = 0; i < tree.children.size(); i++){
			System.out.println(tree.children.get(i).getChar());
		}
		
		System.out.println(tree.getChar());

	}
	
	public static void printTree(Tree tree){
		for(Tree subTree : tree.getChildren()){
			System.out.print(subTree.getChar());
			System.out.print(subTree.isTerminal ? subTree.isTerminal + "!": "");
			printTree(subTree);
		}
	}

}
