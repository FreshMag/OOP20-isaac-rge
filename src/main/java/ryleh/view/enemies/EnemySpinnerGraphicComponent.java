package ryleh.view.enemies;



import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import ryleh.common.Config;
import ryleh.common.GameMath;
import ryleh.view.GraphicComponent;
import ryleh.view.Textures;

public class EnemySpinnerGraphicComponent implements GraphicComponent{
	private Rotate rotation = new Rotate();
	private Rectangle rectangle;
	private int width;
	private int height;
	
	public EnemySpinnerGraphicComponent() {
		this.height = Textures.ENEMY_SPINNER.getHeight();
		this.width = Textures.ENEMY_SPINNER.getWidth();
		this.rectangle = new Rectangle(width, height);
		rectangle.setFill(Textures.ENEMY_SPINNER.getImagePattern());
		rotation.setAngle(GameMath.toDegrees(Math.PI / 60));
	}

	private void updateImage() {

	}
	
	@Override
	public void render(final Point2D position, final double deltaTime) {
		rectangle.setX(position.getX() - rectangle.getWidth() / 2);
		rectangle.setY(position.getY() - rectangle.getHeight() / 2);
		rotation.setPivotX(position.getX());
		rotation.setPivotY(position.getY());
		rectangle.getTransforms().add(rotation);
		this.updateImage();
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
}
