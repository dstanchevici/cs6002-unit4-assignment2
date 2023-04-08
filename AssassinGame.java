
class ListItem2 { // NOTE to grader: I changed "ListItem" to "ListItem2" so that it does not interefere with the class "ListItem" in StrangeCardGame2 and StrangeCardGameAnalysis
    String data;
    ListItem2 next;
}


class AssassinList {

    // The usual list variables.
    ListItem2 front = null;
    ListItem2 rear = null;

    // To keep track of the size.
    int numItems = 0;


    public void add (String s)
    {
	if (front == null) {
	    front = rear = new ListItem2 ();
	    front.data = s;
	}
	else {
	    ListItem2 nextOne = new ListItem2 ();
	    nextOne.data = s;
	    rear.next = nextOne;
	    rear = nextOne;
	    rear.next = front; // circle back to front
	}

	numItems ++;
    }


    public int size ()
    {
	return numItems;
    }


    public String toString ()
    {
	String s = "";
	
	ListItem2 ptr = front;
	while (ptr != rear) {
	    s += (ptr.data + ", ");
	    ptr = ptr.next;
	}
	s += rear.data;

	return s;	
    }


    
    public String fire (String assassin)
    {
	
	if (numItems == 0) {
	    System.out.println ("ERROR in AssassinList.fire(): list is empty");
	    return "@";
	}

	if (numItems == 1) {
	    System.out.println (assassin + " is the only assassin on the list.");
	    return "@";
	}

	if ( front.data.equals(assassin) ) {
	    String victim = front.next.data;
	    front.next = front.next.next;
	    numItems --;
	    return victim;
	}
	
	if ( rear.data.equals(assassin) ) {
	    String victim = front.data;
	    rear.next = front = front.next;
	    numItems --;
	    return victim;
	}	

	boolean assassinOnList = false;
	ListItem2 ptr = front.next;
	while (ptr != rear) {
	    if ( ptr.data.equals(assassin) ) {		
		assassinOnList = true;
		break;
	    }
	    ptr = ptr.next;
	}

	if ( ! assassinOnList ) {
	    return "Error: No Such Assassin";
	}

	String victim = ptr.next.data;

	ptr.next = ptr.next.next;

	numItems --;
	return victim;

    } // end-fire

}


public class AssassinGame {

    public static void main (String[] argv)
    {
        AssassinList assassins = new AssassinList ();
        assassins.add ("Jackal");
        assassins.add ("Mata Hari");
        assassins.add ("John Wilkes Booth");
        assassins.add ("Lee Harvey Oswald");
        assassins.add ("Gavrilo Princip");
        assassins.add ("James Earl Ray");
        assassins.add ("Jack Ruby");

        System.out.println (assassins);
	
        String victim = assassins.fire ("Gavrilo Princip");
        System.out.println ("\nGavrilo's victim: " + victim + "\n  Remaining: " + assassins);
        // Gavrilo's victim: James Earl Ray
	
        victim = assassins.fire ("Jack Ruby");
        System.out.println ("\nJack's victim: " + victim + "\n  Remaining: " + assassins);
        // Jack's victim: Jackal

        victim = assassins.fire ("Mata Hari");
        System.out.println ("\nMata's victim: " + victim + "\n  Remaining: " + assassins);
        // Mata's victim: John Wilkes Booth

        victim = assassins.fire ("Jackal");
        System.out.println ("\nJackal's victim: " + victim + "\n  Remaining: " + assassins);
        // Victim: Error: no such assassin.

        victim = assassins.fire ("Jack Ruby");
        System.out.println ("\nJack's victim: " + victim + "\n  Remaining: " + assassins);
        // Jack's second victim: Mata Hari

    }

}
