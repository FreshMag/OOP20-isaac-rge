
package ryleh.controller.levels;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import ryleh.common.P2d;
import ryleh.common.Pair;
import ryleh.common.Rectangle2d;
import ryleh.controller.Entity;
import ryleh.core.GameState;
import ryleh.core.factories.BasicFactory;
import ryleh.core.factories.EnemyFactory;
import ryleh.model.Type;
import ryleh.model.World;
import ryleh.view.ViewHandler;

//to determine where to spawn the entities generated by Level Designer
public class LevelHandler {

    /*PROBLEMI PRINCIPALI
     * - come spawnare dentro il gamestate?
     * - come gestire la view?
     * - come gestire l'input?
     * - aggiungere item e porte alla factory
     */
        public static final double DRUNK_SPAWN_DISTANCE=2;
        public static final double LURKER_SPAWN_DISTANCE=3;
        public static final double SHOOTER_SPAWN_DISTANCE=4;
        public static final double SPINNER_SPAWN_DISTANCE=3;

        //map entitty values
        public static final double ROCK_SPAWN_DISTANCE=2;
        public static final double FIRE_SPAWN_DISTANCE=1;
        public static final double ITEM_SPAWN_DISTANCE=1;
	
	private int entityCounter;
	//the number of spawn points is determined
	private Map<Pair<Integer, Integer>, Entity> spawnPoints;
	private static int COLUMNS = 9;
	private static int ROWS = 5;
	private int nEnemies;
	private boolean hasItem;
	private LevelDesigner designer;
	private World world;
	private ViewHandler view;
	private GameState gameState;
	private int nRooms;
	private P2d boundsCoord;
	private double boundsWidth;
	private double boundsHeight;
	
	public Pair<Integer, Integer> playerSpawn;
	
	public LevelHandler(final GameState gameState) {
	    this.gameState = gameState;
	    this.world = gameState.getWorld();
	    this.view = gameState.getView();
	    this.designer = new LevelDesigner();
	    spawnPoints = new HashMap<>();
	    boundsCoord = ((Rectangle2d) world.getBounds()).upperLeft;
	    boundsWidth = world.getWidthBound();
	    boundsHeight = world.getHeightBound();
	    playerSpawn = new Pair<>((COLUMNS + 1) / 2, ROWS - 2);
	    nEnemies = 0;
	    hasItem = false;
	    entityCounter = 0;
	    nRooms = 0;
	}
	

	public void generateNewLevel() {
		spawnPoints.clear();
		nEnemies = 1;
		designer.clearLevel();
		hasItem = false;
		nRooms++;
		entityCounter = 0;
		final List<Type> entityList = designer.generateLevelEntities();
//		if (entityList.contains(Type.ENEMY_DRUNKSPINNER)) {
//			spawnPoints.put(new Pair<>(4, 0), null);
//		}
		for (final Type elem : entityList) {
			Pair<Integer, Integer> temp;
			Entity entity;
			switch (elem) {
			case ENEMY_DRUNK:
				nEnemies++;
				temp = getRandomSpawnPoint(DRUNK_SPAWN_DISTANCE);
				entity = EnemyFactory.getInstance().createEnemyDrunk(this.gameState, getPosition(temp));
				break;
			case ENEMY_LURKER:
				nEnemies++;
				temp = getRandomSpawnPoint(LURKER_SPAWN_DISTANCE);
				entity = EnemyFactory.getInstance().createEnemyLurker(this.gameState, getPosition(temp));
				break;
			case ENEMY_SHOOTER:
				nEnemies++;
				temp = getRandomSpawnPoint(SHOOTER_SPAWN_DISTANCE);
				entity = EnemyFactory.getInstance().createEnemyShooter(this.gameState, getPosition(temp));
				break;
			case ENEMY_SPINNER:
				nEnemies++;
				temp = getRandomSpawnPoint(SPINNER_SPAWN_DISTANCE);
				entity = EnemyFactory.getInstance().createEnemySpinner(this.gameState, getPosition(temp));
				break;
			case ENEMY_DRUNKSPINNER:
				nEnemies++;
				temp = getRandomSpawnPoint(DRUNK_SPAWN_DISTANCE);
				entity = EnemyFactory.getInstance().createEnemyDrunkSpinner(this.gameState, getPosition(temp));
				break;
			case ROCK:
				temp = getRandomSpawnPoint(ROCK_SPAWN_DISTANCE);
				entity = BasicFactory.getInstance().createRock(this.gameState, getPosition(temp));
				break;	
			case FIRE:
				temp = getRandomSpawnPoint(FIRE_SPAWN_DISTANCE);
				entity = BasicFactory.getInstance().createFire(this.gameState, getPosition(temp));
				break;	
			case ITEM: //Spawn mechanics when all enemies are defeated 
				temp = getRandomSpawnPoint(ITEM_SPAWN_DISTANCE);
				entity = BasicFactory.getInstance().createItem(this.gameState, getPosition(temp));
				hasItem = true;
				break;
			default:
				temp = getRandomSpawnPoint(ROCK_SPAWN_DISTANCE);
				entity = BasicFactory.getInstance().createRock(this.gameState, getPosition(temp));
				break;
			}
			addEntity(temp,entity);
		}
		decreaseEnemies();
	}
	
	//determine ? type in the map
	public P2d getPosition(final Pair<Integer, Integer> position) {
		return new P2d(((boundsWidth / COLUMNS)) * position.getX() + boundsCoord.x + (boundsWidth / (COLUMNS*2)) ,
				((boundsHeight / ROWS)) * position.getY() + boundsCoord.y + (boundsHeight/ (ROWS*2)));
	}
	/*
	 * @returns a random position inside the map of SpawnPoints
	 */
	public Pair<Integer, Integer> getRandomSpawnPoint() {
		//TODO 
		final Random generator = new Random();
		Pair<Integer, Integer> random;
		do {
			 random = new Pair<>(generator.nextInt(COLUMNS), generator.nextInt(ROWS));
		} while (spawnPoints.containsKey(random) && entityCounter < ROWS * COLUMNS && !random.equals(playerSpawn));
		entityCounter++;
		return random;
	}
	public Pair<Integer, Integer> getRandomSpawnPoint(final double minDistance) {
		//TODO 
		final Random generator = new Random();
		boolean flag = false;
		Pair<Integer, Integer> random;
		do {
				random = new Pair<>(generator.nextInt(COLUMNS), generator.nextInt(ROWS));
		} while ((spawnPoints.containsKey(random) && entityCounter < ROWS * COLUMNS) || random.equals(playerSpawn)
				 || getDistanceFromSpawn(random) < minDistance);
		entityCounter++;
		System.out.println(random);
		System.out.println(spawnPoints);
		return random;
	}
	private double getDistanceFromSpawn(final Pair<Integer, Integer> point) {
		return Math.sqrt(Math.pow(point.getX() - playerSpawn.getX(), 2) 
		        + Math.pow(point.getY() - playerSpawn.getY(), 2));
	}
	
	private void addEntity(final Pair<Integer,Integer> temp, final Entity entity) {
		spawnPoints.put(temp, entity);
		entity.getGameObject().setPosition(getPosition(temp));
	}

	public Pair<Integer, Integer> getPlayerSpawn() {
		return playerSpawn;
	}
	public void spawnItem() {
		if (hasItem) {
		    //BasicFactory.getInstance().createItem(this.getPosition(this.playerSpawn));
		}
	}
	public void spawnDoor() {
		//BasicFactory.getInstance().createDoor(this.getPosition(new Pair<>(0,2)));
	}
	public void decreaseEnemies() {
		nEnemies--;
		System.out.println("morto un nemico. nemici rimasti: " + nEnemies);
		if (noEnemies()) {
		    spawnItem();
		    spawnDoor();
		}
	}
	public boolean noEnemies() {
		return nEnemies == 0;
	}
	
	public Collection<Entity> getEntities(){
		return spawnPoints.values();
	}
	public void debug() {
		spawnPoints.forEach((k,v) -> System.out.println("chiave" + k + "\t valore" + v));
	}
}
