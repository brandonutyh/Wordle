import java.util.*;
import java.io.*;

public class Wordle
{
   // add instance and/or static variables
  private String secretWord = "";
  private String guess = "";
  private ArrayList<String> groupGuess = new ArrayList<String>();
  private ArrayList<String> groupHighlight = new ArrayList<String>();
  private int numGuesses = 1;
  private Set<String> allWords;
  private List<String> possibleAnswers;
  private boolean testMode; // if true, test mode on, else play mode
  private Scanner input = new Scanner(System.in);
  private ArrayList<String> availLetters = new ArrayList<String>();
  

  public Wordle(Set<String> a, List<String> p, boolean testM)
  {
    allWords = a;
    possibleAnswers = p;
    testMode = testM; 
    availLetters.add("a");
    availLetters.add("b");
    availLetters.add("c");
    availLetters.add("d");
    availLetters.add("e");
    availLetters.add("f");
    availLetters.add("g");
    availLetters.add("h");
    availLetters.add("i");
    availLetters.add("j");
    availLetters.add("k");
    availLetters.add("l");
    availLetters.add("m");
    availLetters.add("n");
    availLetters.add("o");
    availLetters.add("p");
    availLetters.add("q");
    availLetters.add("r");
    availLetters.add("s");
    availLetters.add("t");
    availLetters.add("u");
    availLetters.add("v");
    availLetters.add("w");
    availLetters.add("x");
    availLetters.add("y");
    availLetters.add("z");
    // add code below

  }

  //Overall play method that calls on either testMode or regular Wordle.
  public void play(){
    greeting();
    if(testMode){
      playT();
    }
    else{
      playWordle();
    }
    System.out.println("Do you want to play again? Type y for yes, type anything to exit.");
    
    replay();
  } 

  public void greeting(){
    System.out.println("Hi, welcome to Wordle, brought to you by Nick Truong and Brandon Huang.");   
  }

  public void playT(){
    System.out.println("You are in test mode. \n Enter your secret word: (5 letter string)");
    secretWord = validateInput(input.next());
    printTriesT();
    if(checkWin(guess)){
    System.out.println("CONGRADULATIONS ON BEATING WORDLE! YOU WON IN " + numGuesses + "!");
   }
   else{
    System.out.println("Sorry, you didn't win.");
   }
   for(int i = 1; i <= groupHighlight.size(); i++){
    System.out.print(groupHighlight.get(i-1) + " ");
    if(i%5 ==0){
      System.out.println();
      System.out.println();
    }
   }
    

  }


  public void playWordle(){
    secretWord = possibleAnswers.get((int)(Math.random()*possibleAnswers.size()));
    printTriesT();
   if(checkWin(guess)){
    System.out.println("CONGRADULATIONS ON BEATING WORDLE! YOU WON IN " + numGuesses + "!");
   }
   else{
    System.out.println("Sorry, you didn't win.");
    System.out.println("The secret word was: " + secretWord);
   }
   for(int i = 1; i <= groupHighlight.size(); i++){
    System.out.print(groupHighlight.get(i-1) + " ");
    if(i%5 ==0){
      System.out.println();
      System.out.println();
    }
   }
  }

  //Prints the results and the number of tries and stuff; also takes up the guess;
  public void printTriesT(){
    while(numGuesses<=6 && !checkWin(guess)){
    System.out.println("ATTEMPT: " + numGuesses);
    System.out.println(availLetters);
    System.out.println("Enter your guess: ");
    guess = validateInput(input.next());
    checkGuess();
    numGuesses++;
    System.out.println("***RESULTS***");
    for(int i = 0; i < groupGuess.size(); i++){
      System.out.println(groupGuess.get(i));
    }
    System.out.println("");

    }
    
  }
  
  
  
  //checks the user's guess for secret word. Removes/changes colors of letters in. availLetters. Highlights letters in the results.
  public void checkGuess(){
    ArrayList<String> highlightIndexes = new ArrayList<String>();     
    for(int i = 0; i < 5; i++){
      String letterGuess = guess.substring(i,i+1);
      int indexGuess = secretWord.indexOf(letterGuess);    
      if(indexGuess == -1){
        if(availLetters.contains(letterGuess)){
          availLetters.remove(letterGuess);
        }
      }    
      else if(i != indexGuess){
         if(availLetters.indexOf(letterGuess) != -1){
           availLetters.set(availLetters.indexOf(letterGuess), "\033[0;33m" +  letterGuess + "\u001B[0m");
         }
        highlightIndexes.add(i + "y");
      }
      else if(i == indexGuess){
        if(availLetters.indexOf(letterGuess) != -1){
          availLetters.set(availLetters.indexOf(letterGuess), "\033[0;32m" +  letterGuess + "\u001B[0m"); 
        }
        highlightIndexes.add(i + "g");
      }
    }
    highlight(highlightIndexes);
  }
  
  //highlights the words in the list of guesses
  public void highlight(ArrayList<String> list){
    String yellow = "\u001B[43m";
    String green = "\u001B[42m";
    String white = "\u001B[47m";
    String stop = "\u001B[0m";
    String[] arr = guess.split("");
    for(int i = 0; i < list.size(); i++){
      String info = list.get(i);
      if(info.substring(1,2).equals("y")){
        arr[Integer.parseInt(info.substring(0,1))] = yellow + arr[Integer.parseInt(info.substring(0,1))] + stop;
      }
      else{
        arr[Integer.parseInt(info.substring(0,1))] = green + arr[Integer.parseInt(info.substring(0,1))] + stop;
      }
    }
    groupGuess.add(arr[0] + arr[1] + arr[2] + arr[3] + arr[4]);
    for(int j = 0; j < arr.length; j++){
      if(arr[j].contains(yellow)){
        groupHighlight.add(yellow + " " + stop);
      }
      else if(arr[j].contains(green)){
        groupHighlight.add(green + " " + stop);
      }
      else{
        groupHighlight.add(white + " " + stop);
      }
    }
  }
  
  // add methods
  // validates input until it becomes a valid string and then it prints it
  public String validateInput(String str){
    boolean doLoop = true;
    if(testMode){
      while(doLoop){
        if(str.length() != 5){
          System.out.println("The word is not 5 letters. Please enter another word with 5 letters.");
          str = input.next();
        }
        else if(!allWords.contains(str)){
          System.out.println("Please enter a valid 5 letter word.");
          str = input.next();
        }
        else{
          doLoop=false;
        }
      }
      return str;
    }
    else if(!testMode){
      while(doLoop){
        if(str.length()!=5){
          System.out.println("The guess is not 5 letters. Please enter another guess with 5 letters.");
          str = input.next();
        }
        else if(!possibleAnswers.contains(str)){
          System.out.println("Please enter a valid 5 letter guess.");
          str = input.next();
        }
        else{
          doLoop=false;
        } 
      } 
      return str;
    }
    return "";
  }
  
  public boolean checkWin(String str){
    if(str.equals(secretWord)){
      return true;
    }
    else if(numGuesses == 6){
      return false;
    }
    else{
      return false;
    }
  }
  public void replay(){
    if(input.next().equals("y")){
      guess = "";
      secretWord = "";
      numGuesses = 1;
      availLetters.clear();
      for (int i = 97; i <=122; i++){
        char letter = (char)i;
        String let = letter + "";
        availLetters.add(let);
      }
      groupGuess.clear();
      groupHighlight.clear();
      play();  
    }
    else{
      System.out.println("Thanks for playing!");
      return;
    }
  }


}