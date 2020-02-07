package hangman;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EvilHangman {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		boolean playAgain = true;
        Scanner userInputs = new Scanner(System.in);
		while (playAgain) {
			// Pick the mode randomly
			String mode = "";
			Random rand = new Random();
			int modeNum = rand.nextInt(2); // mode = 0 : normal; mode = 1: evil
			if (modeNum == 0) {
				mode = "Normal";
			} else {
				mode = "Evil";
			}
			System.out.println("\n------------- Welcome to Hangman !---------------");
			// Specify the address of the words file
			String address = "C:/Users/sugar/eclipse-workspace/"
					+ "EvilHangman/src/hangman/words.txt";
			// Specify the regular expression
			String regex = ".*[A-Z0-9.,'-/s]+.*";
			// Create a character object to filter the words.
			Character newGame = new Character(address, regex);
			// Store the new word list
			ArrayList<String> words = newGame.getWords();
			// Pick a random word
			String word = newGame.pickWord(words);
			// Filter the word list based on the first word length
			words = newGame.newWords(words, word.length());
			boolean notWin = true;
			int round = 1;
			newGame.create();
			System.out.println("Guess a " + newGame.getLength() + "-letter word.");
			System.out.println("Round: " + round);
			System.out.println("Status: " + newGame.name);
			while (notWin) {
				char userGuess = userInputs.next().charAt(0);
				while (java.lang.Character.isDigit(userGuess)) {
					System.out.println("-------- Invalid Input! ---------");
					System.out.println("-- Please pick a letter instead --");
					userGuess = userInputs.next().charAt(0);
				}
				
		        if (modeNum == 1) {
		        	words = newGame.wordFamilies(words, userGuess);
			        word = newGame.pickWord(words);
		        }
		        if (newGame.check(userGuess)) {
		        	newGame.display(userGuess, round);
		       	    if (newGame.getLength() == 0) {
		       	    	System.out.println("Congrats, You win!");
		       	    	System.out.println("You played " + mode + " Hangman!");
		       	    	System.out.println("\n------------------- Gameover --------------------");
		       		    notWin = false;
		       	    }
		        } else {
		        	// If the guessed is not correct, update the round
		       	    round = newGame.wrong(userGuess, round);
	        	}
		        // Stop the play is #wrongRounds >= 10
		        if (round >= 10) {
		        	notWin = false;
		           	System.out.println("You loss this game!");
		           	System.out.println("You played " + mode + " Hangman!");
		           	System.out.println("The correct words is " + newGame.displayName());
		           	System.out.println("\n------------------- Gameover --------------------");
		        } 	
			}
		    // The following part will ask if the player wants to play again.
		    boolean input = true;
	        while(input) {
	        	System.out.println("Do you want to play again (y/n)?");
	        	char again = userInputs.next().charAt(0);
	        	if(again == 'Y' || again == 'y') {
	            	playAgain = true;
	        		input = false;
	            } else if(again == 'N' || again == 'n') {
	            	playAgain = false;
	            	input = false;
	            	userInputs.close();
	            	System.out.println("-------- Good Bye! ---------");
	            } else {
	           		System.out.println("-------- Invalid Input! ---------");
	            }
	        }
		}
	}	
}
