package ryleh.controller.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import ryleh.common.Point2d;
import ryleh.controller.Entity;
import ryleh.controller.EntityImpl;
import ryleh.controller.InputController;
import ryleh.controller.InputControllerImpl;
import ryleh.controller.core.factories.BasicFactory;
import ryleh.controller.events.EventHandler;
import ryleh.controller.levels.LevelHandler;
import ryleh.model.Type;
import ryleh.model.World;
import ryleh.model.WorldImpl;
import ryleh.model.components.PhysicsComponent;
import ryleh.view.ViewHandler;
import ryleh.view.ViewHandlerImpl;

public class GameStateImpl implements GameState {
    private final ViewHandlerImpl view;
    private final World world;
    private final List<Entity> entities;
    private boolean isGameOver;
    private boolean isVictory;
    private final EventHandler eventHandler;
    private final InputController input;
    private final LevelHandler levelHandler;
    private final Entity player;

    public GameStateImpl(final Stage mainStage) {
        view = new ViewHandlerImpl(mainStage);
        this.eventHandler = new EventHandler(this);
        world = new WorldImpl(eventHandler);
        entities = new ArrayList<>();
        this.levelHandler = new LevelHandler(this);
        this.player = BasicFactory.getInstance().createPlayer(this, levelHandler.getPosition(levelHandler.getPlayerSpawn()));
        input = new InputControllerImpl(this);
        //AudioPlayer.playBackGround();
        this.generateNewLevel();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Entity getPlayer() {
	return this.player;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(final Entity entity) {
    	this.entities.add(entity);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateNewLevel() {
	world.getGameObjects().clear();
	entities.clear();
	view.getGraphicComponents().clear();
	view.displayLevelScene();
	levelHandler.generateNewLevel();
	view.addGraphicComponent(player.getView());
	world.addGameObject(player.getGameObject());
	entities.add(player);

	((PhysicsComponent) player.getGameObject().getComponent(PhysicsComponent.class).get())
	    .setPosition(levelHandler.getPosition(levelHandler.getPlayerSpawn()));

	entities.addAll(levelHandler.getEntities());
	Collections.sort(entities, new Comparator<Entity>() {
	    @Override
		public int compare(final Entity o1, final Entity o2) {
			return o1.getGameObject().getzIndex() - o2.getGameObject().getzIndex();
		}
	});
	input.initInput();
	GameEngine.runDebugger(() -> levelHandler.printSpawnPoints());

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    	view.removeGraphicComponent(entity.getView());
    	world.removeGameObject(entity.getGameObject());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateState(double dt) {
        input.updateInput();
        for (final Entity object : this.entities) {
            object.getGameObject().onUpdate(dt);
            object.getView().render(toPoint2D(new Point2d(
                    object.getGameObject().getPosition().getX(), object.getGameObject().getPosition().getY())), dt);
        }
        eventHandler.checkEvents();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return isGameOver;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void callGameOver(final boolean victory) {
        this.isGameOver = true;
        this.isVictory = victory;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ViewHandlerImpl getView() {
	return view;
    }
    private Point2D toPoint2D(final Point2d point) {
        return new Point2D(point.getX() * ViewHandlerImpl.SCALE_MODIFIER, point.getY() * ViewHandlerImpl.SCALE_MODIFIER);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public LevelHandler getLevelHandler() {
        return this.levelHandler;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Entity> getEntityByType(final Type type) {
	return entities.stream().filter(i -> i.getGameObject().getType().equals(type)).findAny();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public World getWorld() {
        return this.world;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> getEntities() {
	return this.entities;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVictory() {
        return this.isVictory;
    }
}