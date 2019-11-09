import java.util.Arrays; //importing java class: Arrays
import java.util.Scanner; //importing java class: Scanner
import java.util.Random; //importing java class: Random

public class TicTacToe { // this is a program to simulate a game of TicTacToe
  
  public static void main (String[] args) {
    play(); //calling the play method to simulate a game of TicTacToe
  }
  
  public static char[][] createBoard(int dimensions) { //this method takes an integer as input that represents the dimensions of the board
    char[][] boardDimensions = new char[dimensions][dimensions]; //creates the dimensions of our array that represents the board
    for (int i = 0; i<dimensions; i++) { 
      for (int j = 0; j<dimensions; j++) { //these two loops let us access each element of the array so we can populate each element
        boardDimensions[i][j] = ' '; //populating the elements with a space to represent an empty cell
      }
    }
    return boardDimensions;  //this method returns our array with the board dimensions
  }
  // this method displays/prints our game board
  public static void displayBoard(char[][] board) {
    for (int j=0; j<=2*board.length; j++) { //these loops are used to make a new array that has dimensions twice as large as the input array
      for (int i=0; i<=2*board.length; i++){ //this is to make a new array that makes the board itself and the 'x'/'o' input spots as elements
        if (j%2==0) { //on the even rows (row 0, 2, 4 etc...) the elements of the array make up the board and not the x's or o's
          if (i==2*board.length) {
            System.out.println('+'); //the last column of the new array has to be a '+' on the even rows
          }
          else if (i%2==0) {
            System.out.print('+'); // at the even column and even row values, we always have a '+'
          }
          else {
            System.out.print('-'); //on the even row, if we don't have a '+', it must be a '-'
          }
        }
        else { //this is for the odd rows (row 1, 3, 5 etc...)
          if (i==2*board.length) { 
            System.out.println('|'); //the last column of the new array has to be a '|' on odd rows
          }
          else if (i%2==0) {
            System.out.print('|'); //at the even column and odd row values, we always have a '|'
          }
          else { 
            System.out.print(board[(j-1)/2][(i-1)/2]); //on the odd row, if we don't have a '+', we have the 'x'/'o' input
//on an array that has dimensions twice as large, to populate the correct elements (ie not where the board itself is, we must do: (coordinates-1)/2
// this way, the printed array still appears to be the same size as the input array
          }
        } 
      }
    }
  }
  //this method puts a character on the board
  public static void writeOnBoard(char[][] board, char input, int x, int y) { 
    if (x>board.length-1 || y>board.length-1) { //if the input coordinates are outside of the board dimensions
      throw new IllegalArgumentException ("Coordinates of chosen cell lie outside of the board dimensions."); //informs user of mistake
    }
    else if (board[x][y]!=' ') { //if chosen cell coordinates have alreadu been played on
      throw new IllegalArgumentException ("Chosen cell have already been used."); //informs user of mistake
    }
    else { //otherwise...
      board[x][y] = input;// ...we execute the move at the given coordinates with the given character 
    }
  }
  
  //this method gets a users input
  public static void getUserMove(char[][] board) {
    int xCoordinate; //declaring x coordinate int
    int yCoordinate; //declaring y coordinate int
    Scanner read = new Scanner(System.in); //setting up our scanner
    boolean isNotValid = true; //initialize a boolean representing whether or not a move is valid. We assume it is not.
    while (isNotValid) { //while the move is not valid
      System.out.println("Please enter your move: "); //asking user for their move
      xCoordinate = read.nextInt(); // the x coordinate will be the next integer inputted by the user
      yCoordinate = read.nextInt(); // the y coordinate will be the next integer inputted by the user
      if (xCoordinate>board.length-1 || yCoordinate>board.length-1) { //next line prints only if either x or y coordinate lie outside the board dimensions
        System.out.println("Coordinates of chosen cell lie outside of the board dimensions. Please select different coordinates."); //informing user
      }
      else if ((board[xCoordinate][yCoordinate]) != ' ') { //next line prints only if the cell chosen by user has already been played on
        System.out.println("Chosen cell have already been used. Please select different coordinates"); //informing user
      }
      else { //here the move is valid, so after we execute the user move, we update our boolean so we don't enter the while loop again
        writeOnBoard(board, 'x', xCoordinate, yCoordinate); //execute the user move
        isNotValid = false; //update the boolean so we leave the loop after executing the user's move
      }
    }
  }
  
  //helper method for checkForObviousMove to see if a row is one play away from winning for either player. Fills row to win/block accordingly if true.
  public static boolean checkRowForWin(char[][] board, char xOrO) {
    int emptyRowSpace = 0; //initializing x coordinate of the empty space
    int emptyColSpace = 0; //initializing y coordinate of the empty space
    char[] boardRow = new char[board.length]; //making a 1dimensional array to represent the 2dimensional array's rows
    for (int i=0; i<board.length; i++) { //a loop to iterate through the rows
      int numberOfChars = 0; //initializing the numberOfChars int to 0
      for(int j=0; j<board.length; j++) { //a loop to iterate through the columns
        boardRow[i] = board[i][j]; //populating each element of the row array with each value of a row from the 2dimensional array
        if (boardRow[i]!=xOrO) { //if the element is not populated with the inputted char
          emptyRowSpace = i; //we find the x coordinate
          emptyColSpace = j; //we find the y coordinate
          continue; //continue testing the row
        }
        else { //if the element is populated with the inputted character
          numberOfChars+=1; //we add one to the numberOfChars int
        }
      }
      if (numberOfChars==boardRow.length-1) { //if all but one element is populated with the inputted character,
        if (board[emptyRowSpace][emptyColSpace]==' ') { //checking to see if the element not populated with the inputted character is a ' '
          board[emptyRowSpace][emptyColSpace] = 'o'; //if it is a ' ', we can populate it with the computer's character: 'o'
          return true; //returns true if there is an obvious row move
        }
      }
    }
    return false; //returns false if there is no obvious row move
  }
  
  //helper method for checkForObviousMove to see if a column is one play away from winning for either player. Fills column to win/block accordingly if true.
  public static boolean checkColForWin(char[][] board, char xOrO) {
    int emptyRowSpace = 0;//initializing x coordinate of the empty space
    int emptyColSpace = 0;//initializing y coordinate of the empty space
    char[] boardCol = new char[board.length]; //making a 1dimensional array to represent the 2dimensional array's columns
    for (int i=0; i<board.length; i++) { //a loop to iterate through the rows
      int numberOfChars = 0; //initializing the numberOfChars int to 0
      for(int j=0; j<board.length; j++) { //a loop to iterate through the columns
        boardCol[j] = board[j][i]; //populating each element of the column array with each value of a column from the 2dimensional array
        if (boardCol[j]!=xOrO) { //if the element is not populated with the inputted char
          emptyRowSpace = j; //we find the x coordinate
          emptyColSpace = i; //we find the y coordinate
          continue; //continue testing the column
        }
        else { //if the element is populated with the inputted character
          numberOfChars+=1;  //we add one to the numberOfChars int
        }
      }
      if (numberOfChars==boardCol.length-1) { //if all but one element is populated with the inputted character,
        if (board[emptyRowSpace][emptyColSpace]==' ') { //checking to see if the element not populated with the inputted character is a ' '
          board[emptyRowSpace][emptyColSpace] = 'o';  //if it is a ' ', we can populate it with the computer's character: 'o'
          return true;  //returns true if there is an obvious column move
        }
      }
    }
    return false; //returns false if there is no obvious column move
  }
  
  //helper method for checkForObviousMove to see if the declining diagonal is one play away from winning. Fills space to win.
  public static boolean checkDecreasingDiagonal(char[][] board, char xOrO) {
    int numberOfChars = 0; //initializing the numberOfChars int to 0
    int emptyRowSpace = 0; //initializing x coordinate of the empty space
    int emptyColSpace = 0; //initializing y coordinate of the empty space
    for (int i=0; i<board.length; i++) { //a loop to go through the diagonal elements of the array
      if (board[i][i]!=xOrO) { //if a decreasing diagonal element is not populated with the input character
        emptyRowSpace = i; //we find the x coordinate
        emptyColSpace = i; //we find the y coordinate
        continue; //continue testing the diagonal 
      }
      else { //if the element is populated with the inputted character
        numberOfChars+=1; //we add one to the numberOfChars int
      }
    }
    if (numberOfChars==board.length-1) { //if all but one element on the diagonal is populated with the inputted character,
      if (board[emptyRowSpace][emptyColSpace]==' ') { //checking to see if the element not populated with the inputted character is a ' '
        board[emptyRowSpace][emptyColSpace] = 'o'; //if it is a ' ', we can populate it with the computer's character: 'o'
        return true;  //returns true if there is an obvious decreasing diagonal move
      }
    }
    return false; //returns false if there is no obvious decreasing diagonal move
  }
  
  //helper method for checkForObviousMove to see if the increasing diagonal is one play away from winning. Fills space to win.
  public static boolean checkIncreasingDiagonal(char[][] board, char xOrO) {
    int numberOfChars = 0; //initializing the numberOfChars int to 0
    int emptyRowSpace = 0; //initializing x coordinate of the empty space
    int emptyColSpace = 0; //initializing y coordinate of the empty space
    for (int i=0; i<board.length; i++) { //a loop to go through the diagonal elements of the array
      if (board[i][board.length-i-1]!=xOrO) { //if an increasing diagonal element is not populated with the input character
        emptyRowSpace = i; //we find the x coordinate
        emptyColSpace = board.length-i-1; //we find the y coordinate
        continue; //continue testing the diagonal 
      }
      else { //if the element is populated with the inputted character
        numberOfChars+=1; //we add one to the numberOfChars int
      }
    }
    if (numberOfChars==board.length-1) {  //if all but one element on the diagonal is populated with the inputted character,
      if (board[emptyRowSpace][emptyColSpace]==' ') { //checking to see if the element not populated with the inputted character is a ' '
        board[emptyRowSpace][emptyColSpace] = 'o'; //if it is a ' ', we can populate it with the computer's character: 'o'
        return true;  //returns true if there is an obvious increasing diagonal move
      }
    }
    return false; //returns false if there is no obvious increasing diagonal move
  }
  
  //a method to check if there is an obvious win/lose situation. Executes said win move or lose-blocking move if true.
  public static boolean checkForObviousMove(char[][] board) {
    if (checkRowForWin(board, 'o')) {
      return true;  //we check all possibilities for an obvious win for the AI first. if they return true, so does this method.
    } //this is because an obvious win takes priority over an obvious lose. Thats why we call the checkFor methods on 'o' before 'x'
    else if (checkColForWin(board, 'o')) {
      return true;
    }
    else if (checkDecreasingDiagonal(board, 'o')) {
      return true;
    }
    else if (checkIncreasingDiagonal(board, 'o')) {
      return true;
    }
    else if (checkRowForWin(board, 'x')) {
      return true;//we then check all possibilities for an obvious lose for the AI if the obvious win method calls all return false. if they return true, so does this method.
    } //this is why we are now calling the checkFor methods on 'x' (the user's character)
    else if (checkColForWin(board, 'x')) {
      return true;
    }
    else if (checkDecreasingDiagonal(board, 'x')) {
      return true;
    }
    else if (checkIncreasingDiagonal(board, 'x')) {
      return true;
    }
    else {  //if there are no obvious moves for the AI to win or to block a win for the user, the method returns false.
      return false;
    }
  }
  
  //a method to simulate the AI's move
  public static void getAIMove(char[][] board) {
    if (checkForObviousMove(board)==true) { //this method call in the if statement executes only when it is true
    } //if its true, AI does the obvious move
    else { //otherwise...
      Random randomPosition = new Random(); //initializing our random int generator
      boolean availableCell = false; //initializing a boolean to see if the randomly selected cell is available. Initialize as false
      while (availableCell == false) { //a loop that executes so long as the randomly selected cell is not available
        int x = randomPosition.nextInt(board.length); //randomly generates an int value for the x coordinate within the board dimensions
        int y = randomPosition.nextInt(board.length); //randomly generates an int value for the y coordinate within the board dimensions
        if (board[x][y] == ' ') { //checks to see if the randomly generated coordinates refer to a free cell (one populated with ' ')
          availableCell = true; //if they do, we update the boolean as true and we do not enter the while loop again
          writeOnBoard(board, 'o', x, y); // if the cell is free, the AI places it's 'o' there
        }
      }
    }
  }
  
  //helper method for checkForWinner to see if row is a winner or loser
  public static boolean checkRowForWinOrLose(char[][] board, char xOrO) {
    char[] boardRow = new char[board.length]; //create a 1dimensional array to represent a row of our 2dimensional input array
    for (int i=0; i<board.length; i++) { //a loop to iterate through the rows
      int numberOfChars = 0; //initializing the number of times the input character is seen as zero
      for(int j=0; j<board.length; j++) { //a loop to iterate through the columns
        boardRow[i] = board[i][j]; //populating the elements of our row array with the elements of a row of the 2dimensional array
        if (boardRow[i]!=xOrO) { //if the element of the row is not populated by the input character
          continue; //continue to the next element
        }
        else { //if this executes, we know that the element is populated by the input character
          numberOfChars+=1; //add one to the number of characters int
        }
      }
      if (numberOfChars==boardRow.length) { //if all elements of a row are populated by the input character
        return true; //a row is a winner and we return true
      }
    }
    return false; //if we reach this code, the row is not a winner so we return false
  }
  
  //helper method for checkForWinner to see if column is a winner or loser
  public static boolean checkColForWinOrLose(char[][] board, char xOrO) {
    char[] boardCol = new char[board.length];  //create a 1dimensional array to represent a column of our 2dimensional input array
    for (int i=0; i<board.length; i++) { //a loop to iterate through the rows
      int numberOfChars = 0; //initializing the number of times the input character is seen as zero
      for(int j=0; j<board.length; j++) {  //a loop to iterate through the columns
        boardCol[j] = board[j][i];  //populating the elements of our column array with the elements of a row of the 2dimensional array
        if (boardCol[j]!=xOrO) { //if the element of the column is not populated by the input character
          continue; //continue to the next element
        }
        else { //if this executes, we know that the element is populated by the input character
          numberOfChars+=1;  //add one to the number of characters int
        }
      }
      if (numberOfChars==boardCol.length) { //if all elements of a column are populated by the input character
        return true; //a column is a winner and we return true
      }
    }
    return false;  //if we reach this code, the column is not a winner so we return false
  }
  
  //helper method to see if decreasing diagonal is a winner or loser
  public static boolean checkDecreasingDiagonalForWinOrLose(char[][] board, char xOrO) {
    int numberOfChars = 0; //initializing the number of times the input character is seen as zero
    for (int i=0; i<board.length; i++) { //a loop to iterate along the diagonal
      if (board[i][i]!=xOrO) { //if the element of the decreasing diagonal is not populated by the input character
        continue; //continue to the next element
      }
      else { //if this executes, we know that the element is populated by the input character
        numberOfChars+=1; //add one to the number of characters int
      }
    }
    if (numberOfChars==board.length) { //if all elements of the decreasing diagonal are populated by the input character
      return true; //the decreasing diagonal is a winner and we return true
    }
    return false; //if we reach this code, the decreasing diagonal is not a winner so we return false
  }
  
  //helper method to see if increasing diagonal is a winner or loser
  public static boolean checkIncreasingDiagonalForWinOrLose(char[][] board, char xOrO) {
    int numberOfChars = 0; //initializing the number of times the input character is seen as zero
    for (int i=0; i<board.length; i++) { //a loop to iterate along the diagonal
      if (board[i][board.length-i-1]!=xOrO) {  //if the element of the increasing diagonal is not populated by the input character
        continue; //continue to the next element
      }
      else {  //if this executes, we know that the element is populated by the input character
        numberOfChars+=1;  //add one to the number of characters int
      }
    }
    if (numberOfChars==board.length) { //if all elements of the increasing diagonal are populated by the input character
      return true; //the increasing diagonal is a winner and we return true
    }
    return false; //if we reach this code, the increasing diagonal is not a winner so we return false
  }
  
  public static char checkForWinner(char[][] board) {
    if (checkRowForWinOrLose(board, 'x')==true || checkColForWinOrLose(board, 'x')==true){ //if the user wins
      return 'x'; //returning 'x' if the user wins
    }
    else if (checkDecreasingDiagonalForWinOrLose(board, 'x')==true || checkIncreasingDiagonalForWinOrLose(board, 'x')==true) { //if the user wins
      return 'x'; //returning 'x' if the user wins
    }
    else if (checkRowForWinOrLose(board, 'o')==true || checkColForWinOrLose(board, 'o')==true){ //if the AI wins
      return 'o'; //returning 'o' if the AI wins
    }
    else if (checkDecreasingDiagonalForWinOrLose(board, 'o')==true || checkIncreasingDiagonalForWinOrLose(board, 'o')==true) { //if the AI wins
      return 'o'; //returning 'o' if the AI wins
    }
    else { //if no one wins
      int counterOfSpaces = 0; //counts spaces. initializing as zero
      for (int i = 0; i<board.length; i++) { //loop to iterate through the rows
        for (int j = 0; j<board.length; j++) { //loop to iterate through the columns
          if (board[i][j] == ' ') { //if any elements of the 2dimensional array are spaces...
            counterOfSpaces+=1; // ...we add 1 to the space counter int
          }
        }
      }
      if  (counterOfSpaces == 0) { //if there are exactly zero elements left populated as a space and no one has won...
        System.out.println("Stalemate.\nEnding game."); // ...we know it is a tie.
        System.exit(0);//this ends the entire program after informing the user it is a stalemate.
      }
      return ' '; //if no one wins and the game is not a stalemate, we keep playing so the method returns a space character
    }
  }
  
  //a method to simulate a game of TicTacToe between a user and the computer
  public static void play() {
    String name; //declaring a String to represent the user's name
    int dimensions; //declaring an integer to represent the board dimensions
    boolean isInt = false; //initializing a boolean that tests whether or not the input of dimensions is an int or not
    char[][] gameBoard; //declaring a 2dimensional characeter array to be our official game board
    Scanner read = new Scanner(System.in); //setting up scanner to take user inputs
    System.out.println("Please enter your name here: "); //asking the user to enter their name
    name = read.nextLine(); //the user writes their name, and we take that input as the String: name
    System.out.println("Welcome, " + name + "! Are you ready to play? Please choose the dimensions of your board: "); //welcoming user and asking for the dimensions input as an int
    while (!isInt) { //executes while user has not yet inputted an integer
      if (!read.hasNextInt()) {// if the next input is NOT an integer
        System.out.println("Please enter an integer to represent the dimensions of your board: ");// we ask the user to input an integer
        read.nextLine(); //clearing the buffer
      }
      else { //this executes once the next line is an integer that was inputted
        isInt = true; //user has inputted an integer therefore this is the last iteration of the while loop
      }
    }
    dimensions = read.nextInt(); //the dimensions are the inputted integer
    gameBoard = createBoard(dimensions); //we can now create the game board because we know it's size
    displayBoard(gameBoard); //display our starting game board
    Random coinFlip = new Random(); //setting up random to simulate a coinflip
    int coinToss = coinFlip.nextInt(2); //the result will be a 0 or 1 as it is an int, below and excluding 2 and above but including 0
    if (coinToss==1) { //if the result is 1, the AI wins
      System.out.println("The result of the coin toss is: 1\nThe AI has the first move."); //informing user of the result of the coin toss                   
    }
    else { //if we reach here, it means our coiflip resulted in 0 (the user wins)
      System.out.println("The result of the coin toss is: 0\n" + name +  " has the first move."); //informing user of the result of the coin toss
    }
    for (int i=0; i<dimensions*dimensions; i++) { //loop we enter to simulate the game which maxes out once all elements are used
      if (coinToss==0) { //if user wins coin toss
        getUserMove(gameBoard); //have the user execute their move
        displayBoard(gameBoard); //display the game board
        if (checkForWinner(gameBoard) == 'x' || checkForWinner(gameBoard) == 'o') { //check to see if either player has won
          break; //if someone has won, exit loop
        }
        else { //if no one has won, the AI may play
          System.out.println("The AI has made its move: "); //informs the user of the AI's move
          getAIMove(gameBoard); //check for winner or stalemate
          displayBoard(gameBoard); //display the game board
          checkForWinner(gameBoard); //check for winner or stalemate
        }
        if (checkForWinner(gameBoard) == 'x' || checkForWinner(gameBoard) == 'o') { //check to see if either player has won
          break; //if someone has won, exit loop
        }
      }
      else { //if AI wins cointoss
        System.out.println("The AI has made its move: "); //informs the user of the AI's move
        getAIMove(gameBoard); //check for winner or stalemate
        displayBoard(gameBoard); //display the game board
        if (checkForWinner(gameBoard) == 'x' || checkForWinner(gameBoard) == 'o') { //check to see if either player has won
          break; //if someone has won, exit loop
        }
        else { //if no one has won, the user may play
          getUserMove(gameBoard); //have the user execute their move
          displayBoard(gameBoard); //display the game board
          checkForWinner(gameBoard); //check for winner or stalemate
        }
        if (checkForWinner(gameBoard) == 'x' || checkForWinner(gameBoard) == 'o') { //check to see if either player has won
          break; //if someone has won, exit loop
        }
      }
    }
    System.out.println("GAME OVER!"); //once the loop is left, the game is over. This informs the user
    if (checkForWinner(gameBoard) == 'x') { //if the check for winner returns 'x', the user won
      System.out.println("Congratulations. You won."); //informs user of win
    }
    else if (checkForWinner(gameBoard) == 'o') { //if the check for winner returns 'o', the AI won
      System.out.println("Too bad. You lost."); //informs user of lose
    }
  }
}


