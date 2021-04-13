package ryleh.view.other;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import ryleh.view.GraphicComponent;
import ryleh.view.Textures;

public class RockGraphicComponent implements GraphicComponent{

	private Rectangle rectangle;
	
	public RockGraphicComponent() {
		this.rectangle = new Rectangle(Textures.ROCK.getWidth(), Textures.ROCK.getHeight());
		this.rectangle.setFill(Textures.ROCK2.getImagePattern());
	}

	public RockGraphicComponent(final Point2D position) {
		this.rectangle = new Rectangle(Textures.ROCK.getWidth(), Textures.ROCK.getHeight());
		this.rectangle.setX(position.getX() - rectangle.getWidth() / 2);
		this.rectangle.setY(position.getY() - rectangle.getHeight() / 2);
		this.rectangle.setFill(Textures.ROCK2.getImagePattern());
	}

	private void updateImage() {

	}

	@Override
	public void render(final Point2D position, final double deltaTime) {
		rectangle.setX(position.getX() - rectangle.getWidth() / 2);
		rectangle.setY(position.getY() - rectangle.getHeight() / 2);
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
	public void onRemoved(EventHandler<ActionEvent> event) {
		event.handle(null);
	}
}
