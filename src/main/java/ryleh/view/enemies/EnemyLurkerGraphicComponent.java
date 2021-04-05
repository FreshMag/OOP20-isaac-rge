package ryleh.view.enemies;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ryleh.common.P2d;
import ryleh.view.GraphicComponent;
import ryleh.view.Textures;

public class EnemyLurkerGraphicComponent implements GraphicComponent{

	private Rectangle rectangle;
	P2d playerDirection;
	
	public EnemyLurkerGraphicComponent(P2d point) {
		rectangle = new Rectangle(100, 100);
		rectangle.setFill(Textures.ENEMY_LURKER.getImagePattern());
		this.playerDirection=point;
	}

	private void updateImage() {

	}

	@Override
	public void render(final Point2D position, final int deltaTime) {
		rectangle.setX(position.getX());
		rectangle.setY(position.getY());
		
		this.updateImage();
	}

	@Override
	public void onAdded(final Scene scene) {
		Parent root = scene.getRoot();
        ((AnchorPane) root).getChildren().add(rectangle);
	}

}
