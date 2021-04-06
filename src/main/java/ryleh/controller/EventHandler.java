package ryleh.controller;

import java.util.ArrayList;
import java.util.List;

import ryleh.core.GameState;

public class EventHandler implements EventListener{
	
	private GameState gameState;
	private List<Event> eventQueue;

	public EventHandler(final GameState gameState) {
		this.gameState = gameState;
		this.eventQueue = new ArrayList<>();
	}
	
	public void checkEvents() {
		eventQueue.forEach(e -> {
			if (e instanceof EnemyCollisionEvent) {
				EnemyCollisionEvent enEvent = (EnemyCollisionEvent) e;
				enEvent.handle();
			} else if (e instanceof ItemPickUpEvent) {
				ItemPickUpEvent pickEvent = (ItemPickUpEvent) e;
				pickEvent.handle();
			} else if (e instanceof BulletSpawnEvent) {
				BulletSpawnEvent bulletSpawn = (BulletSpawnEvent) e;
			}
		});
	}
	
	@Override
	public void notifyEvent(final Event e) {
		this.eventQueue.add(e);
	}
	
}
