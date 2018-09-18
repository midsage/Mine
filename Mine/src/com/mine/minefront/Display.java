package com.mine.minefront;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import com.mine.minefront.entity.mob.Player;
import com.mine.minefront.graphics.Screen;
import com.mine.minefront.gui.Launcher;

import com.mine.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width = 800;
	public static int height = 600;

	// public static final int WIDTH = 800;
	// public static final int HEIGHT = 600;
	public static final String TITLE = "Minefront Pre-Alpha 0.02";

	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;
	private int fps;
	public static int selection = 0;
	public static int MouseSpeed;

	static Launcher launcher;

	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(getGameWidth(), getGameHeight());
		input = new InputHandler();
		game = new Game(input);
		img = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

	}

	public static Launcher getLauncherInstance() {
		if (launcher == null) {
			launcher = new Launcher(0);
		}
		return launcher;

	}

	public static int getGameWidth() {

		return width;

	}

	public static int getGameHeight() {

		return height;

	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "game");
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void run() {
		long previousTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		requestFocus();
		while (running) {
			long currentTime = System.nanoTime();
			delta += (currentTime - previousTime) / ns;
			previousTime = currentTime;

			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}

			render();
			frames++;

			while (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " UPS,  " + frames + " FPS");
				fps = frames;
				frames = 0;
				updates = 0;
			}
		}

	}

	private void tick() {
		input.tick();
		game.tick();

		newX = InputHandler.MouseX;
		if (newX > oldX) {
			// System.out.println("Right...");
			Player.turnRight = true;
		}
		if (newX < oldX) {
			// System.out.println("Left....");
			Player.turnLeft = true;

		}
		if (newX == oldX) {
			// System.out.println("Still...");
			Player.turnLeft = false;
			Player.turnRight = false;

		}
		MouseSpeed = Math.abs(newX - oldX);
		oldX = newX;

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(game);

		for (int i = 0; i < getGameWidth() * getGameHeight(); i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getGameWidth() + 10, getGameHeight() + 10, null);
		g.setFont(new Font("Verdana", 2, 30));
		g.setColor(Color.YELLOW);
		g.drawString("FPS: " + fps, 20, 50);
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		getLauncherInstance();
		// new Launcher(0);

	}
}
