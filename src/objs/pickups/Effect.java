package objs.pickups;

import java.io.Serializable;

import objs.characters.PlayerCharacter;

/**
 * Class to represent the effect of a pickup.
 * Effects can be temporary or permanent.
 *
 */
public abstract class Effect implements Serializable {

    public int lifespan;

    /**
     * Apply this effect to the character.
     * @param character
     */
    public abstract <T extends PlayerCharacter> void apply(T character);

    /**
     * Stop applying this effect to the character.
     * If the effect is permanent, this function is not called.
     * @param character
     */
    public abstract <T extends PlayerCharacter> void cease(T character);


}