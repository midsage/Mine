package com.mine.minefront.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	
	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX; // D = drag....
	public static int MouseDY;
	public static int MousePX; // P = Pressed
	public static int MousePY;
	public static int MouseButton;
	public static boolean dragged = false;
	
	public boolean forward, back, left, right, rleft, rright, jump, crouch, run;
	
	
	public void tick() {
		forward = key[KeyEvent.VK_W] || key[KeyEvent.VK_UP];
		back = key[KeyEvent.VK_S] || key[KeyEvent.VK_DOWN];
		left = key[KeyEvent.VK_A] || key[KeyEvent.VK_LEFT];
		right = key[KeyEvent.VK_D] || key[KeyEvent.VK_RIGHT];
		rleft = key[KeyEvent.VK_Z];
		rright = key[KeyEvent.VK_X];
		jump = key[KeyEvent.VK_SPACE];
		crouch = key[KeyEvent.VK_CONTROL];
		run = key[KeyEvent.VK_SHIFT];
		
	}
	
	
	public void mouseDragged(MouseEvent e) {
		MouseDX = e.getX();
		MouseDY = e.getY();
		dragged = true;
		
	}

	
	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
		
		
		
	}


	public void mouseClicked(MouseEvent e) {
		
	}


	public void mousePressed(MouseEvent e) {
		MouseButton = e.getButton();
		MousePX = e.getX();
		MousePY = e.getY();
	
		
		
	}

	
	public void mouseReleased(MouseEvent e) {
		dragged = false;
		MouseButton = 0;
	}

	
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		for(int i = 0; i < key.length; i++) {
			key[i] = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
			
		}
	}

}
