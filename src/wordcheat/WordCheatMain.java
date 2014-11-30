package wordcheat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class WordCheatMain {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		WordBoard board = null;
		Tree dict = null;
		ArrayList<WordAnalysisResult> analyzed = null;
		
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
					out.flush();
				} 
				else if(input.startsWith("buildDict")){ //create dictionary tree
					String fileName = input.substring(9).trim();
					if(fileName.length() == 0){
						fileName = "words.txt"; //default dictionary
					}
					dict = (new DictBuilder(fileName)).buildDict();
					out.println("Build complete.");
					out.flush();
				}
				else if(input.equals("analyze")){ //analyze the current board
					if(dict == null || board == null){
						out.println("Error: must import a board and dictionary");
					} else {
						analyzed = WordBoard.analyze(dict, board);
						Collections.sort(analyzed, WordAnalysisResult.Order.ByLength);
						out.println("Words found: " + analyzed.size());
						out.println("Longest words: ");
						
						ArrayList<String> words = new ArrayList<String>();
						int ii = 0;
						
						while(words.size() <= 8){
							String currWord = analyzed.get(ii).getWord();
							if(!words.contains(currWord)) words.add(currWord);
							ii++;
						}
						
						for(int i = 0; i < 8; i++){
							out.println(words.get(i));
						}
						out.flush();
					}
				}
				else if(input.startsWith("how ")){
					if(analyzed == null){
						out.print("Need to analyze the board first!");
						out.flush();
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
								}
							}
						}
						if(!found){
							out.println("Not found!");
							out.flush();
						}
					}
				}
				else if(input.startsWith("sort ")){
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
						default:
							out.println("Invalid argument: no changes");
						}
						
						ArrayList<String> words = new ArrayList<String>();
						int ii = 0;
						while(words.size() <= 8){
							String currWord = analyzed.get(ii).getWord();
							if(!words.contains(currWord)) words.add(currWord);
							ii++;
						}
						
						for(int i = 0; i < 8; i++){
							out.println(words.get(i));
						}
						out.flush();
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
					out.flush();
				}
			} catch(Exception e){
				out.println("Error!");
				out.println(e.getMessage());
				out.flush();
			}
		}

	}

}