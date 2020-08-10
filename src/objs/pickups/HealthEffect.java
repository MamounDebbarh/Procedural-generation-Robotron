package objs.pickups;

import objs.characters.PlayerCharacter;
import objs.pickups.Effect;

public class HealthEffect extends Effect {

    @Override
    public <T extends PlayerCharacter> void apply(T character) {
        character.health += 15;

    }

    @Override
    public <T extends PlayerCharacter> void cease(T character) {
        /*
         * No implementation required.
         */
    }

}