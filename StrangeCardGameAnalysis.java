// 4.a2.1 StrangeCardGame with MyLinkedList (saved in MyLinkedList.java)

import java.util.*;

public class StrangeCardGameAnalysis {

    static Random rand = new Random ();
    static int maxRounds = 10000;

    // Analysis variables
    static int totalGames = 100_000;
    static int totalRounds, hundredRounds, drawCount, player1Wins, player2Wins;

    public static void main (String[] argv)
    {
	for (int i=0; i<totalGames; i++) {
	    // Use cards numbered 0,..,9 and deal 5 cards to each player.
	    playGame (5, 10);
	}

	// Report analysis:
	System.out.println ("In " + totalGames + " games:");
	System.out.println (" Average rounds per game: " + (totalRounds/totalGames) );
	System.out.println (" Games requring at least 100 rounds: " + hundredRounds);
	System.out.println (" Draws: " + drawCount);
	System.out.println (" Player 1's victories: " + player1Wins);
	System.out.println (" Player 2's victories: " + player2Wins);
	
    }


    static void playGame (int dealSize, int numCards)
    {
	// The three queues:

        // Cards dealt out to player 1:
	MyLinkedList player1 = new MyLinkedList ();
        // Cards dealt out to player 2:
	MyLinkedList player2 = new MyLinkedList ();
        // The pile between the two players:
	MyLinkedList pile = new MyLinkedList ();


        // Make the cards and shuffle them randomly.
	int[] cards = new int [numCards];
	for (int i=0; i<cards.length; i++) {
	    cards[i] = i;
	}
	shuffle (cards);

	// Deal cards to each player.
	for (int i=0; i<cards.length; i++) {
	    if (i%2 == 0) {
		player1.add (cards[i]);
	    }
	    else
		player2.add (cards[i]);
	}
	

	// Now play.
	int round = 0;

	/*
	printList ("Player 1:", player1);
	printList ("Player 2:", player2);
	printList ("Pile    :", pile);
	*/

	while (round<=maxRounds) {

	    // Each player plays their first card by removing
	    // the first card in their list. These are added to the
	    // pile in turn.
	    int player1first = player1.removeFirst ();
	    pile.add (player1first);
	    int player2first = player2.removeFirst ();
	    pile.add (player2first);

	    //System.out.println ("Round " + round +": player1's card=" + player1first + "  player2's card=" + player2first);

	    //  LOGIC of the game:
	    if (player1first - player2first >= 3) {
		addListToList (pile, player1);
	    }
	    else if (player2first - player1first >= 3) {
		addListToList (pile, player2);
	    }

	    /*
	    // Print results of this round:
	    printList ("Player 1:", player1);
	    printList ("Player 2:", player2);
	    printList ("Pile    :", pile);
	    */

	    if ( (player1.isEmpty()) && (player2.isEmpty()) ) {
		// Draw
		//System.out.println ("It's a draw.");
		drawCount++;
		return;
	    }
	    else if (player1.isEmpty()) {
		//System.out.println ("Player 2 wins!");
		player2Wins++;
		return;
	    }
	    else if (player2.isEmpty()) {
		//System.out.println ("Player 1 wins!");
		player1Wins++;
		return;
	    }

	    round++;

	    // Analysis
	    totalRounds++;
	    if (round == 100) hundredRounds++;

	} //end-while

    }


    static void shuffle (int[] A)
    {
	for (int i=0; i<A.length; i++) {
	    int randInd = rand.nextInt (A.length);
	    int temp = A[randInd];
	    A[randInd] = A[i];
	    A[i] = temp;
	}
    }


    static void addListToList (MyLinkedList list1, MyLinkedList list2)
    {
        // This method should extract every item in list1 and 
	// add them to list2 in order.
	while ( ! list1.isEmpty() ) {
	    list2.add ( list1.removeFirst () );
	}

    }

    static void printList (String message, MyLinkedList list)
    {
	// This should print the message and the entire list on one line.
	System.out.print (message);
	list.printList ();

    }

}
