// 4.a2.1 class used in StrangeCardGame2.java

class ListItem {
    int card;
    ListItem next;
}

public class MyLinkedList {

    ListItem front, rear; // null by default

    public void add (int c)
    {
	if (front == null) {
	    front = new ListItem ();
	    front.card = c;
	    rear = front;
	}
	else {
	    ListItem tempPtr = new ListItem ();
	    tempPtr.card = c;
	    rear.next = tempPtr;
	    rear = tempPtr;
	}
    }


    public int removeFirst ()
    {
	if (front == null) {
	    System.out.println ("ERROR in MyLinkedList.removeFirst(): list empty");
	    return -1;
	}

	ListItem removedItem = front;
	front = front.next;
	return removedItem.card;

    }


    public boolean isEmpty ()
    {
	return front == null;
    }


    public void printList ()
    {
	if (front == null) {
	    System.out.println (" []");
	    return;
	}

	String s = " [";

	ListItem listPtr = front;
	while (listPtr != rear) {
	    s += (listPtr.card + ", ");
	    listPtr = listPtr.next;    
	}
	s += (rear.card + "]");
	
	System.out.println (s);
    }

}
