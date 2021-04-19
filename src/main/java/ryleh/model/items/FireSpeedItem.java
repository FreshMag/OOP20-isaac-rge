package ryleh.model.items;

import ryleh.controller.core.GameState;
import ryleh.model.components.ShootingComponent;
/**
 * This type of item augments player's attack speed by a factor.
 */
public class FireSpeedItem implements Item {

    private static final double FACTOR = 0.25;
    /**
     * {@inheritDoc}
     * Increases the attack speed
     */
    @Override
    public void apply(final GameState state) {
        ((ShootingComponent) state.getPlayer().getGameObject().getComponent(ShootingComponent.class).get()).increaseAtkSpeed(FACTOR);
        System.out.println("spara");
    }

}
