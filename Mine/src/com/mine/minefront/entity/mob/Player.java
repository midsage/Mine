package com.mine.minefront.entity.mob;

import com.mine.minefront.input.InputHandler;

public class Player extends Mob {

	// X Left-Right, Y UP-DOWN, Z In-Out ~Depth

	public double y, rotation, xa, za, rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runWalk = false;
	private InputHandler input;
	
	public Player(InputHandler input) {
		this.input = input;
	}
	
	public void tick() {
		double rotationSpeed = 0.02; //* Display.MouseSpeed;
		double jumpHeight = 0.5;
		double crouchHeight = 0.3;
		double walkSpeed = 0.5;
		double xMove = 0;
		double zMove = 0;
		int xa = 0;
		int za = 0;

		if (input.forward) {
			za++;
			walk = true;
		}

		if (input.back) {
			za--;
			walk = true;
		}

		if (input.left) {
			xa--;
			walk = true;
		}

		if (input.right) {
			xa++;
			walk = true;
		}

		if (input.rleft) {
			rotationa -= rotationSpeed;
		}
		if (input.rright) {
			rotationa += rotationSpeed;
		}
		
		rotation += rotationa;
		rotationa *= 0.5;

		if (xa != 0 || za != 0) move(xa, za, rotation);

		if (input.jump) {
			y += jumpHeight;
			input.run = false;
		}
		if (input.crouch) {
			y -= crouchHeight;
			input.run = false;
			crouchWalk = true;
			walkSpeed = 0.2;
		}
		if (input.run) {
			walkSpeed = 1;
			walk = true;
			runWalk = true;
		}
		if (!input.forward && !input.back && !input.left && !input.right) {
			walk = false;
		}
		if (!input.crouch) {
			crouchWalk = false;
		}

		if (!input.run) {
			runWalk = false;

		}

		
		y *= 0.9; //Leave. It's needed
		
	}

}
