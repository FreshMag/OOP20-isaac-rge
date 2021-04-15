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

/**
 * A class that provides the GraphicComponent of the view related to the Item Entity.
 */
public class ItemGraphicComponent implements GraphicComponent{

	private Rectangle rectangle;
	private boolean animPlayed;
	private static final int ANIM_DURATION = 200;
	private AnimationLoop animItem;
	
	
	/**
	 * Creates a new Instance of ItemGraphicComponent.
	 */
	public ItemGraphicComponent() {
		this.rectangle = new Rectangle(Textures.ITEM1.getWidth(), Textures.ITEM1.getHeight());
		this.rectangle.setFill(Textures.ITEM1.getImagePattern());
		this.animPlayed = false;
	}
	
	/**
	 * Creates a new Instance of ItemGraphicComponent with the given initial position.
	 * @param position The position at which the ItemGraphicCOmponent needs to be initialized.
	 */
	public ItemGraphicComponent(final Point2D position) {
		this.rectangle = new Rectangle(Textures.ITEM1.getWidth(), Textures.ITEM1.getHeight());
		this.rectangle.setX(position.getX() - rectangle.getWidth() / 2);
		this.rectangle.setY(position.getY() - rectangle.getHeight() / 2);
		this.rectangle.setFill(Textures.ITEM1.getImagePattern());
		this.animPlayed = false;
		this.animItem = new AnimationLoop(List.of(Textures.ITEM1.getImagePattern(), 
				   								  Textures.ITEM2.getImagePattern(), 
				   								  Textures.ITEM3.getImagePattern()), 
										  		  ANIM_DURATION, rectangle);
	}

	/**
	 * A method to set the animation as played.
	 */
	public void setAnimPlayed() {
		animPlayed = true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(final Point2D position, final double deltaTime) {
		rectangle.setX(position.getX() - rectangle.getWidth() / 2);
		rectangle.setY(position.getY() - rectangle.getHeight() / 2);
		if (animPlayed) {
			if (isAnimFinished()) {
				animItem.stop();
				rectangle.setVisible(false);
			} else {
				animItem.play();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAdded(final Scene scene) {
		Parent root = scene.getRoot();
        ((AnchorPane) root).getChildren().add(rectangle);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isAnimFinished() {
		return animItem.isCycleFinished();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rectangle getNode() {
		return rectangle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onRemoved(final EventHandler<ActionEvent> event) {
	}	
}
