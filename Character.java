package hangman;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.util.Scanner;
//import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;


public class Character {
	private String charactername;
	private String incorrect = "";
	private String correct = "";
	private String status = "Status: ";
	public StringBuilder name = new StringBuilder();
	private int length = -1;
	private ArrayList<String> words;
	
	public Character(String address, String regex) throws FileNotFoundException{
		File charFile = new File(address);
        Scanner sc = new Scanner(charFile);
		ArrayList<String> words = new ArrayList<String>();
		while(sc.hasNextLine()) {
			String tempWord = sc.nextLine();
			if (!Pattern.matches(regex, tempWord)) {
				words.add(tempWord);
			}
		}
		this.words = words;
		sc.close();
	}
	
	public ArrayList<String> getWords() {
		return this.words;
	}
	
	/**
	 * Randomly select a word from the word list.
	 * @param words
	 * @return a random word
	 */
	public String pickWord(List<String> words) {
		Random rand = new Random();
		int len = words.size();
		String word = words.get(rand.nextInt(len));
		this.charactername = word;
		if (this.length == -1) {
			this.length = this.charactername.length();
		}
		return word;
	}
	
	/**
	 * Pick the word list that all words have the same length as the first word.
	 * @param words
	 * @param len 
	 * @return A filtered word list
	 */
	public ArrayList<String> newWords(List<String> words, int len) {
		ArrayList<String> filteredWords = new ArrayList<String>();
		for (String word: words) {
			if (word.length() == len) {
				filteredWords.add(word);
			}
		}
		return filteredWords;
	}
	
	
	/**
	 * Partitioning the words based on a specified character
	 * @return the word family with the most elements
	 */
	public ArrayList<String> wordFamilies(ArrayList<String> words, char c) {
		HashMap<ArrayList<Integer>, ArrayList<String>> wordsPartitioner = 
				new HashMap<ArrayList<Integer>, ArrayList<String>>();
		
		for (String word: words) {
			ArrayList<Integer> indices = new ArrayList<Integer>();
			int idx = word.indexOf(c);
			indices.add(idx);
			// get all rest indices
			while(idx >= 0) {
			    idx = word.indexOf(c, idx+1);
			    indices.add(idx);
			}
			
			ArrayList<String> wordList = wordsPartitioner.get(indices);
		    // if list does not exist create it
		    if(wordList == null) {
		    	wordList = new ArrayList<String>();
		    	wordList.add(word);
		        wordsPartitioner.put(indices, wordList);
		    } else {
		        // add if item is not already in list
		        if(!wordList.contains(word)) wordList.add(word);
		    }
		}
		
		int longest = 0;
		ArrayList<String> mostWords = new ArrayList<String>();
		for (ArrayList<Integer> idx: wordsPartitioner.keySet()) {
			if (wordsPartitioner.get(idx).size() > longest) {
				longest = wordsPartitioner.get(idx).size();
				mostWords = wordsPartitioner.get(idx);
			}
		}
		return mostWords;
	}
	
	
	/**
	 * Retrieve the correct word
	 * @return the correct word
	 */
	public String displayName(){
		return this.charactername;
	}
	
	public int getLength() {
		return this.length;
	}
	
	/**
	 * This function create a spacer to indicate the length of a word
	 */
	public void create() {
		this.name.append("-".repeat(this.length));
	}
	
	public void display(char character, int round) {
		for (int i = 0 ; i < this.charactername.length(); i++) {
			 char c = this.charactername.charAt(i);
	         if(c == character) {
	             this.name.setCharAt(i, c);
	             this.length--;
	         } 
		} 
		if (this.length == 0) {
	        return;
		} else {
			System.out.println("Round: " + round);
			System.out.println(this.status + this.name);
	        System.out.println("Guessed letters are: " + this.incorrect);
		}
	}
	
	/**
	 * Check is the hidden word contains the input char
	 * @param character to be checked
	 * @return a boolean value
	 */
	public boolean check(char character) {
		boolean check = this.charactername.indexOf(character) >= 0;
		if (check) {
			if (this.correct.indexOf(character) < 0) {
	        	this.correct += character;
	    	} else {
	    		System.out.println("You have already guessed this letter");
	    	}
		}
        return check;
    }
	
	/**
	 * If the guess character is wrong, record the wrong chars
	 * @param character
	 */
    public int wrong(char character, int round){
    	if (this.incorrect.indexOf(character) < 0) {
        	this.incorrect += character;
        	round++;
    	} else {
    		System.out.println("You have already guessed this letter");
    	}
    	System.out.println("Round: " + round);
        System.out.println(this.status + this.name);
        System.out.println("Guessed characters are: " + this.incorrect);
        return round;
    }
}
