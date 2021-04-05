package ryleh.controller;

import javafx.scene.Scene;
import ryleh.model.components.PhysicsComponent;
import ryleh.model.physics.Direction;
import ryleh.view.AnimatedPlayerGraphicComponent;

public class InputController {
	private boolean isMoveUp;
	private boolean isMoveDown;
	private boolean isMoveLeft;
	private boolean isMoveRight;
	private final AnimatedPlayerGraphicComponent graphic;
	private final PhysicsComponent physics; 
	private final Scene scene;
	private final Entity player;
	
	public InputController(final Scene scene, final Entity player) {
		this.scene = scene;
		this.player = player;
		this.graphic = (AnimatedPlayerGraphicComponent) player.getView();
		this.physics = (PhysicsComponent) player.getGameObject()
		        .getComponent(PhysicsComponent.class).get();
	}
	
	public void initInput(){
		this.scene.setOnKeyPressed(key -> {
			switch (key.getCode()) {
			//TODO change to V2d indication (adesso c'è il metodo cape)
			case A: {
				isMoveLeft = true;
			}
				break;
			case D: {
				isMoveRight = true;
			}
				break;
			case W: {
				isMoveUp = true;
			}
				break;
			case S: {
				isMoveDown = true;
			}
				break;
			default:
				break;
			}
		});
		scene.setOnKeyReleased(key -> {
			switch (key.getCode()) {
			case A: {
				isMoveLeft = false;
			}
				break;
			case D: {
				isMoveRight = false;
			}
				break;
			case W: {
				isMoveUp = false;
			}
				break;
			case S: {
				isMoveDown = false;
			}
//			case A:  physics.setVelocityX(0);
//					physics.setDirection(Direction.IDLE);
//					graphic.setDirection(Direction.IDLE);
//				break;
//			case D:  physics.setVelocityX(0);
//					physics.setDirection(Direction.IDLE);
//					graphic.setDirection(Direction.IDLE);
//				break;
//			case W:  physics.setVelocityY(0);
//					physics.setDirection(Direction.IDLE);
//					graphic.setDirection(Direction.IDLE);
//				break;
//			case S: physics.setVelocityY(0);
//					physics.setDirection(Direction.IDLE);
//					graphic.setDirection(Direction.IDLE);
//				break;
			default:
				break;
			}
		});
	}
	public void updateInput() {
		if (isMoveUp) {
			physics.setVelocity(Direction.UP.getPoint());
			physics.setDirection(Direction.UP);
			graphic.setDirection(Direction.UP);
		} else if (isMoveDown) {
			physics.setVelocity(Direction.DOWN.getPoint());
			physics.setDirection(Direction.DOWN);
			graphic.setDirection(Direction.DOWN);
		} else if (isMoveLeft) {
			physics.setVelocity(Direction.LEFT.getPoint());
			physics.setDirection(Direction.LEFT);
			graphic.setDirection(Direction.LEFT);
		} else if (isMoveRight) {
			physics.setVelocity(Direction.RIGHT.getPoint());
			physics.setDirection(Direction.RIGHT);
			graphic.setDirection(Direction.RIGHT);	
		} else {
			physics.setVelocity(Direction.IDLE.getPoint());
			physics.setDirection(Direction.IDLE);
			graphic.setDirection(Direction.IDLE);
			
		}
	}
}
