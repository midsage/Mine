package com.mine.minefront;

import com.mine.minefront.entity.mob.Player;
import com.mine.minefront.input.InputHandler;
import com.mine.minefront.level.Level;

public class Game {

	public int time;
	public Player player;
	public Player[] players;
	public Level level;
	
	
	public Game(InputHandler input) {
		player = new Player(input);
		level = new Level(100, 100);
		level.addEntity(player);
		
	}
	
	public void tick() {
		time++;
		level.tick();
	}
}
