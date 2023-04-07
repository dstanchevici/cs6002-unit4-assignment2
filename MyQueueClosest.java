// Unit 4, assignment 2, problem 3
// Use by AntEaterClosest.java

class QueueItem {
    Coords coords;      // Each node in the list stores this as data.
    QueueItem next;     // The usual "next" pointer.
    QueueItem prev;     // The usual "prev" pointer.
}

public class MyQueueClosest {

    QueueItem front, rear;
    int numItems = 0;

    public void add (Coords c)
    {
	if (front == null) {
	    front = rear = new QueueItem ();
	    front.coords = c;
	}
	else {
	    QueueItem nextOne = new QueueItem ();
	    nextOne.coords = c;
	    nextOne.prev = rear;
	    rear.next = nextOne;
	    rear = nextOne;
	}

	numItems ++;
    }

    public Coords removeClosest (Coords antEater)
    {
	// INSERT YOUR CODE HERE to find the node that has
	// the coords closest to that of the antEater. Then
	// remove the node from the queue, and return that node's coords.

        if (front == null) {
	    System.out.println ("ERROR in MyQueue.removeClosest(): queue is empty");
	    return null;
	}

	// Find closest node
	double minDist = distance (antEater, front.coords);
	QueueItem closestNode = front;	
	QueueItem ptr = front.next;
	while ( ptr != null) {
	    if ( distance(antEater, ptr.coords) < minDist ) {
		minDist = distance(antEater, ptr.coords);
		closestNode = ptr;
	    }
	    ptr = ptr.next;
	}

	// Remove closest node
	if (closestNode == front) {
	    front = front.next;	    
	}
	else if (closestNode == rear) {
	    rear = rear.prev;
	    rear.next = null;
	}
	else {
	    closestNode.prev.next = closestNode.next;
	    closestNode.next.prev = closestNode.prev;
	}

	// Decrease numItems by one
	numItems--;

	// Return closestNode's coords
	return closestNode.coords;
	
    } // end-removeClosest


    public boolean isEmpty ()
    {
	return numItems <= 0;
    }

    public int size ()
    {
        return numItems;
    }

    public Coords get (int k)
    {
	if (k >= numItems) {
	    System.out.println ("ERROR in MyQueue.get(): " + k + " is not a valid index for a queue of size " + numItems);
	    return null;
	}
		
	if (k==numItems-1) return rear.coords;
	
	QueueItem ptr = front;
	int counter = 0;
	while (counter < k) {
	    ptr = ptr.next;
	    counter++;
	}

	return ptr.coords;
    } // end-get

    double distance (Coords p, Coords q)
    {
        double distSq = (p.x-q.x)*(p.x-q.x) + (p.y-q.y)*(p.y-q.y);
        return Math.sqrt (distSq);
    }

}
