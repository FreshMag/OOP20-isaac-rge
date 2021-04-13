package ryleh.view.other;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import ryleh.view.AnimationLoop;
import ryleh.view.GraphicComponent;
import ryleh.view.Textures;

public class DoorGraphicComponent implements GraphicComponent{

	private Rectangle rectangle;
	private boolean animPlayed;
	private AnimationLoop animDoor = new AnimationLoop(List.of(Textures.DOOR1.getImagePattern(), 
															   Textures.DOOR2.getImagePattern(), 
															   Textures.DOOR3.getImagePattern(), 
															   Textures.DOOR4.getImagePattern(), 
															   Textures.DOOR5.getImagePattern()), 
														8);
	//COSTRUTTORE DA RIFARE
	public DoorGraphicComponent() {
		this.rectangle = new Rectangle(Textures.DOOR1.getWidth(), Textures.DOOR1.getHeight());
		this.rectangle.setFill(Textures.DOOR1.getImagePattern());
		this.animPlayed = false;
	}
	
	public void setAnimPlayed() {
		animPlayed = true;
	}

	public void playAnimation() {
		rectangle = animDoor.setFrame(rectangle);
		animDoor.incTimer();
	}
	
	public boolean isAnimFinished() {
		return animDoor.isCycleFinished();
	}

	@Override
	public void render(final Point2D position, final double deltaTime) {
		rectangle.setX(position.getX() - rectangle.getWidth() / 2);
		rectangle.setY(position.getY() - rectangle.getHeight() / 2);
		if (animPlayed) {
			this.playAnimation();
		}
	}

	@Override
	public void onAdded(final Scene scene) {
		Parent root = scene.getRoot();
        ((AnchorPane) root).getChildren().add(rectangle);
	}
	@Override
	public Object getNode() {
		return rectangle;
	}

	@Override
	public void onRemoved(final EventHandler<ActionEvent> event) {
		// TODO Auto-generated method stub
	}
}
