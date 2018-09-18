package com.mine.minefront.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.mine.minefront.Configuration;
import com.mine.minefront.RunGame;
import com.mine.minefront.input.InputHandler;

public class Launcher extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private Rectangle rplay, roptions, rhelp, rquit;
	Configuration config = new Configuration();

	private int width = 800;
	private int height = 400;
	protected int button_width = 80;
	protected int button_height = 40;
	boolean running = false;
	Thread thread;
	JFrame frame = new JFrame();

	public Launcher(int id) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// SwingUtilities.updateComponentTreeUI(window);
		} catch (Exception e) {

			e.printStackTrace();
		}
		frame.setUndecorated(true); // set to decorate launcher
		frame.setTitle("MineFront Launcher");
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// getContentPane().add(window);
		frame.add(this);
		// frame.add(display);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		window.setLayout(null);
		if (id == 0) {
			drawButtons();
		}
		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		startMenu();
		//display.start();
		frame.repaint();

	}

	public void updateFrame() {
		if (InputHandler.dragged) {
			Point p = frame.getLocation();
			int x = getX();
			int y = getY();
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX,
					p.y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}

	public void startMenu() {
		running = true;
		thread = new Thread(this, "menu");
		thread.start();

	}

	public void stopMenu() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		requestFocus();
		while (running) {
			System.out.println("Running.......");
			try {
				renderMenu();
			} catch(IllegalStateException e) {
				System.out.println("Run Error Handled");
				e.printStackTrace();
				System.exit(-1);
			}
			updateFrame();

		}
	}

	private void renderMenu() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		
		try {
			g.drawImage(ImageIO.read(Launcher.class.getResource("/menu_image.jpg")), 0, 0, 800, 400, null);
			if (InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 130
					&& InputHandler.MouseY < 130 + 30) {

				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/play_on.png")), 690, 130, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 690 + 80, 134, 22, 20, null);
				if(InputHandler.MouseButton == 1) {
					config.loadConfiguration("res/settings/config.xml");
					frame.dispose();
					new RunGame();
				}
				
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/play_off.png")), 690, 130, 80, 30, null);
			}
			if (InputHandler.MouseX > 641 && InputHandler.MouseX < 641 + 130 && InputHandler.MouseY > 170
					&& InputHandler.MouseY < 170 + 30) {

				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/options_on.png")), 641, 170, 130, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 690 + 80, 174, 22, 20, null);
				if(InputHandler.MouseButton == 1) {
					new Options();
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/options_off.png")), 641, 170, 130, 30, null);
			}
			if (InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 210
					&& InputHandler.MouseY < 210 + 30) {

				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/help_on.png")), 690, 210, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 690 + 80, 214, 22, 20, null);
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/help_off.png")), 690, 210, 80, 30, null);
			}
			if (InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 250
					&& InputHandler.MouseY < 250 + 30) {

				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/quit_on.png")), 690, 250, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 690 + 80, 254, 22, 20, null);
				if(InputHandler.MouseButton == 1) {
					System.exit(0);
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/quit_off.png")), 690, 250, 80, 30, null);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		g.dispose();
		bs.show();

	}

	private void drawButtons() {
		play = new JButton("Play!");
		rplay = new Rectangle((width / 2) - (button_width / 2), 90, button_width, button_height);
		play.setBounds(rplay);
		window.add(play);

		options = new JButton("Options");
		roptions = new Rectangle((width / 2) - (button_width / 2), 140, button_width, button_height);
		options.setBounds(roptions);
		window.add(options);

		help = new JButton("Help");
		rhelp = new Rectangle((width / 2) - (button_width / 2), 190, button_width, button_height);
		help.setBounds(rhelp);
		window.add(help);

		quit = new JButton("Quit");
		rquit = new Rectangle((width / 2) - (button_width / 2), 240, button_width, button_height);
		quit.setBounds(rquit);
		window.add(quit);

		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.loadConfiguration("res/settings/config.xml");
				frame.dispose();
				new RunGame();

			}
		});
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
				new Options();

			}
		});
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("help...");

			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

	}

}
