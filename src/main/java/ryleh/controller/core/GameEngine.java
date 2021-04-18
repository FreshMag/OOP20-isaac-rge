package ryleh.controller.core;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import ryleh.view.menu.RylehGameOverMenu;
import ryleh.view.menu.RylehPauseMenu;

public final class GameEngine {
    private GameState rylehState;
    private static Timeline loop;
    private static final double PERIOD = 1000 / 60;
    private RylehPauseMenu pauseMenu;
    private Stage primaryStage;
    /**
     * If True, the game runs in developer mode, otherwise it will run is release
     * mode. While release mode is the normal way to play, developer exists only for
     * debugging purposes. Note: in developer mode player HP doesn't decrease, level
     * changing can be done by pressing "L" key, current level can be set to maximum
     * by pressing "O" key plus other features.
     */
    private static boolean isDeveloper;
    private static boolean debugOn;

    /**
     * Initializes engine's game state and the frequency (period) of game loop. Key
     * code "P" is set to pause engine.
     * 
     * @param stage that will contain game scene.
     */
    public void initGame(final Stage stage) {
        this.primaryStage = stage;
        this.rylehState = new GameStateImpl(stage);
        this.pauseMenu = new RylehPauseMenu(stage);
        this.primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode().equals(KeyCode.P) || key.getCode().equals(KeyCode.ESCAPE) && !rylehState.isGameOver()) {
                GameEngine.pauseEngine();
                pauseMenu.renderPauseMenu();
            }
        });
        this.rylehState.generateNewLevel();
    }

    /**
     * Uses a JavaFx class, called "Timeline", to handle one "Keyframe" (that
     * corresponds to one gameloop's update). This operation is done once every
     * "period"
     */
    public void mainLoop() {
        final Duration oneFrameAmt = Duration.millis(PERIOD);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt, new EventHandler<>() {
            @Override
            public void handle(final ActionEvent event) {
                if (rylehState.isGameOver()) {
                    renderGameOver(rylehState.isVictory());
                }
                rylehState.updateState(PERIOD);
            }
        }); // oneFrame
        // sets the game world's game loop (Timeline)
        loop = new Timeline(oneFrame);
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();;
    }

    /**
     * Call by factories. An Entity builder is used to create both GameObject and
     * View of each entity.
     * 
     * @return an entity builder
     */
    public static EntityBuilder entityBuilder() {
        return new EntityBuilder();
    }

    /**
     * Pauses GameEngine.
     */
    public static void pauseEngine() {
        loop.pause();
    }

    /**
     * Resumes engine.
     */
    public static void resumeEngine() {
        loop.playFromStart();
    }

    /**
     * This method is called when the game is over. Stops game loop and render the
     * game over scene.
     */
    private void renderGameOver(final boolean victory) {
        loop.stop();
        new RylehGameOverMenu(this.primaryStage, victory).show();
    }

    /**
     * Current mode the game is playing.
     * 
     * @return True if you are currently in Developer mode. False if you are in
     *         Release mode.
     */
    public static boolean isDeveloper() {
        return isDeveloper;
    }

    /**
     * Sets current game mode.
     * 
     * @param isDeveloper True if you want to set Developer mode. Use False for
     *                    Release mode.
     */
    public static void setDeveloper(final boolean isDeveloper) {
        GameEngine.isDeveloper = isDeveloper;
    }

    public static void runDebugger(final Runnable action) {
        if (GameEngine.debugOn) {
            action.run();
        }
    }
}

