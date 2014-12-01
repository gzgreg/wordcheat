package wordcheat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class WordCheatMain {
	
	private static WordBoard board = null;
	private static Tree dict = null;
	private static ArrayList<WordAnalysisResult> analyzed = null;
	private static ArrayList<String> wordList = null;

	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		
		out.println("WordCheat started...");
		out.flush();
		while(true){
			try{
				String input = in.readLine();
				if(input.startsWith("import ")){ //Import a wordboard
					String fileName = input.substring(7);
					
					board = new WordBoard(fileName);
					out.println("Imported board" + fileName);
					
					out.println(board);
				} 
				else if(input.startsWith("buildDict")){ //create dictionary tree
					String fileName = input.substring(9).trim();
					if(fileName.length() == 0){
						fileName = "words.txt"; //default dictionary
					}
					
					dict = (new DictBuilder(fileName)).buildDict();
					out.println("Build complete.");
				}
				else if(input.equals("analyze")){ //analyze the current board
					if(dict == null || board == null){
						out.println("Error: must import a board and dictionary");
					} else {
						analyzed = WordBoard.analyze(dict, board);
						
						Collections.sort(analyzed, WordAnalysisResult.Order.ByLength);
						
						buildWordList();
						
						out.println("Words found: " + wordList.size());
						out.println("Longest words: ");

						for(int i = 0; i < 8; i++){
							out.println(wordList.get(i));
						}
					}
				}
				else if(input.startsWith("how ")){ //display how to do certain words
					if(analyzed == null){
						out.print("Need to analyze the board first!");
					}
					else{
						String argument = input.substring(4);
						boolean found = false;
						for(int i = 0; i < analyzed.size(); i++){
							if(analyzed.get(i).getWord().equals(argument)){
								if(!found){
									out.print(analyzed.get(i));
									out.flush();
									found = true;
								}
								else {
									out.println("There are more options: do you want to see them? (Y/N)");
									out.flush();
									String wantToSee;
									do{
										wantToSee = in.readLine();
										if(wantToSee.equalsIgnoreCase("y")){
											out.print(analyzed.get(i));
											out.flush();
										} else if(wantToSee.equalsIgnoreCase("n")){
											break;
										}
									} while(!wantToSee.equalsIgnoreCase("y") && !wantToSee.equalsIgnoreCase("n"));
									if(wantToSee.equalsIgnoreCase("n")) break;
								}
							}
						}
						if(!found){
							out.println("Not found!");
						}
					}
				}
				else if(input.startsWith("sort ")){ //sort words
					if(analyzed == null){
						out.println("Need to analyze the board first!");
						out.flush();
					}
					else{
						String argument = input.substring(5);
						switch(argument){
						case "length":
							Collections.sort(analyzed, WordAnalysisResult.Order.ByLength);
							out.println("Sorting by length");
							break;
						case "height":
							Collections.sort(analyzed, WordAnalysisResult.Order.ByHeight);
							out.println("Sorting by height");
							break;
						case "width":
							Collections.sort(analyzed, WordAnalysisResult.Order.ByWidth);
							out.println("Sorting by width");
							break;
						case "top":
							Collections.sort(analyzed, WordAnalysisResult.Order.ByTop);
							out.println("Sorting by top");
							break;
						case "bottom":
							Collections.sort(analyzed, WordAnalysisResult.Order.ByBottom);
							out.println("Sorting by bottom");
							break;
						default:
							out.println("Invalid argument: no changes");
						}
						buildWordList();
						
						for(int i = 0; i < 8; i++){
							out.println(wordList.get(i));
						}
					}
				}
				else if(input.startsWith("get ")){
					if(analyzed == null){
						out.print("Need to analyze the board first!");
					}
					else {
						String[] arguments = input.substring(4).split(" ");
						int start = 0, end = 0;
						boolean success = true;
						try{
							start = Integer.parseInt(arguments[0]);
							end = Integer.parseInt(arguments[1]);
						} catch(NumberFormatException e){
							out.println("Incorrect formatting.");
							success = false;
						}
						finally{
							if(start < 0 || end >= wordList.size()){
								success = false;
								out.println("Out of range.");
							}
							if(success){
								for(int i = start; i < end; i++){
									out.print(wordList.get(i) + " ");
									out.println();
								}
							}
						}
					}
				}
				else if(input.equals("win")){
					if(analyzed == null){
						out.print("Need to analyze the board first!");
					}
					else{
						int winRow = WordBoard.NUM_ROWS - 1;
						char[][] originalBoard = board.getBoard();
						for(int i = 0; i < originalBoard[0].length; i++){
							if(!Character.isUpperCase(originalBoard[0][i])){ //checks if top row is all uppercase
								winRow = 0;
							}
						}
						
						ArrayList<String> winningWords = new ArrayList<String>();
						ArrayList<WordAnalysisResult> resortedWordList = (ArrayList<WordAnalysisResult>) analyzed.clone();
						Collections.sort(resortedWordList, winRow == 0 ? WordAnalysisResult.Order.ByTop : WordAnalysisResult.Order.ByBottom); //sorts depending on winning row
						
						for(int i = 0; i < resortedWordList.size(); i++){
							WordAnalysisResult result = resortedWordList.get(i);
							if(winRow == 0 ? result.getTop() == 0 : result.getBottom() == WordBoard.NUM_ROWS - 1){
								if(!winningWords.contains(result.getWord())) winningWords.add(result.getWord());
							}
							else break; //note that sorting allows us to do this
						}
						
						out.println("Winning words: ");
						for(int i = 0; i < winningWords.size(); i++){
							out.print(winningWords.get(i) + " ");
						}
					}
				}
				else if(input.equals("quit")){ //Quit
					out.println("Closing.");
					out.close();
					in.close();
					break;
				}
				else{
					out.println("Invalid input");
				}
				
				out.flush();
			} catch(Exception e){
				out.println("Error!");
				out.println(e.getMessage());
				out.flush();
			}
		}

	}
	
	private static void buildWordList(){
		wordList = new ArrayList<String>();
		for(int i = 0; i < analyzed.size(); i++){
			if(!(wordList.contains(analyzed.get(i).getWord()))){
				wordList.add(analyzed.get(i).getWord());
			}
		}
	}

}