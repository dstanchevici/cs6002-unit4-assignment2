
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;


class AntEaterPanel extends JPanel implements MouseListener, Runnable {

    // We'll put all the ant-clicks into a queue, from which the anteater 
    // will pull out the next target.
    MyQueueClosest antQueue = new MyQueueClosest ();

    Coords antEater;    // The current position of the anteater.
    Coords nextAnt;     // The position of the current ant being chased.

    public void initialize ()
    {
        // This is where we'll start the anteater.
        antEater = new Coords ();
	antEater.x = 0;
	antEater.y = 0;
        
        // Listen to mouseclicks.
	addMouseListener (this);
        
        // The anteater will run in a separate thread.
        Thread t = new Thread (this); 
        t.start ();
    }

    public void mouseClicked (MouseEvent m) 
    {
        // Add a new ant to the queue.
	Coords c = new Coords ();
	c.x = m.getX();
	c.y = m.getY();
        antQueue.add (c);
        draw ();
    }

    public void mouseEntered (MouseEvent m) {}   // Strange? An empty method!
    public void mouseExited (MouseEvent m) {}
    public void mousePressed (MouseEvent m) {}
    public void mouseReleased (MouseEvent m) {}

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        // Clear drawing area.
        g.setColor (Color.white);
        Dimension D = this.getSize();
        g.fillRect (0,0, D.width, D.height);

        // Draw the ants.
        g.setColor (Color.gray);
        if (nextAnt != null) {
            g.fillOval ((int)nextAnt.x-2,(int)nextAnt.y-2, 4, 4);
        }
        for (int i=0; i<antQueue.size(); i++) {
	    Coords c = antQueue.get (i);
            g.fillOval ((int)c.x-2,(int)c.y-2, 4, 4);
        }

        // AntEater.
        g.setColor (Color.red);
        g.fillOval ((int)antEater.x-10, (int)antEater.y-10, 20, 20);
    }
    
    
    void draw ()
    {
        // Place a call to paintComponent(). 
        this.repaint ();
    }


    public void run ()
    {
        while (true) {

            // Pause 100 milliseconds for animation.
	    animationPause ();

            if (nextAnt == null) {
                if (! antQueue.isEmpty() ) {
                    // See if there's an ant to chase.                    
                    nextAnt = antQueue.removeClosest(antEater);
                }
            }
            else {
		// nextAnt does have an ant to chase.
                if ( distance(nextAnt, antEater) < 10 ) {
                    // Eat the ant if the anteater is close enough.
                    nextAnt = null;
                }
                else {
                    // Otherwise, step towards ant. This is the "trig math"
		    // to find the direction and step in that direction.
                    double theta = Math.atan2 ((nextAnt.y - antEater.y), (nextAnt.x - antEater.x));
                    double stepsize = 10.0;
                    antEater.x = (int) (antEater.x + stepsize*Math.cos(theta));
                    antEater.y = (int) (antEater.y + stepsize*Math.sin(theta));
                    draw ();
                }
            }
        }
    }


    void animationPause ()
    {
	try {
	    Thread.sleep (100);
	}
	catch (InterruptedException e) {
	}
    }

    double distance (Coords p, Coords q)
    {
        double distSq = (p.x-q.x)*(p.x-q.x) + (p.y-q.y)*(p.y-q.y);
        return Math.sqrt (distSq);
    }

    public void makeFrameAndDisplay ()
    {
	JFrame f = new JFrame ();
        f.setTitle ("AntEater");
        f.setSize (300, 300);

        // The frame only consists of the panel.
        f.getContentPane().add (this);

        f.setVisible (true);
    }

} //end-panel



public class AntEaterClosest {

    public static void main (String[] argv)
    {
        AntEaterPanel p = new AntEaterPanel ();
	p.initialize ();
	p.makeFrameAndDisplay ();
    }

}
