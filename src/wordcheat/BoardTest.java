package wordcheat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class BoardTest {

	public static void main(String[] args) throws IOException {

		WordBoard board = new WordBoard("src/board1.txt");
		
		Tree dict = (new DictBuilder("src/words.txt")).buildDict();
		
		ArrayList<WordAnalysisResult> list = WordBoard.analyze(dict, board);
		
		Collections.sort(list, WordAnalysisResult.Order.ByLength);
		
		for(int i = 0; i < 8; i++){
			System.out.println(list.get(i).getWord());
			System.out.println(list.get(i).getHeight());
			System.out.println(list.get(i).getWidth());
		}
		System.out.println();
		
		Collections.sort(list, WordAnalysisResult.Order.ByHeight);
		
		for(int i = 0; i < 8; i++){
			System.out.println(list.get(i).getWord());
			System.out.println(list.get(i).getHeight());
			System.out.println(list.get(i).getWidth());
		}
		System.out.println();
		Collections.sort(list, WordAnalysisResult.Order.ByWidth);
		
		for(int i = 0; i < 8; i++){
			System.out.println(list.get(i).getWord());
			System.out.println(list.get(i).getHeight());
			System.out.println(list.get(i).getWidth());
		}
	}

}
