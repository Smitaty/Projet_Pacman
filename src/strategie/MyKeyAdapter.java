package strategie;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter{
	
	private boolean top = false;
	private boolean bottom = false;
	private boolean left = false;
	private boolean right = false;

	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'z') {
			top = true;
		}
		if(e.getKeyChar() == 's') {
			bottom = true;
		}
		if(e.getKeyChar() == 'q') {
			left = true;
		}
		if(e.getKeyChar() == 'd') {
			right = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean bottom) {
		this.bottom = bottom;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
}
