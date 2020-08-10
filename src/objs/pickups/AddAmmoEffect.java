package objs.pickups;

import objs.characters.PlayerCharacter;
import objs.pickups.Effect;
import objs.weapons.Weapon;

public class AddAmmoEffect extends Effect {

    @Override
    public <T extends PlayerCharacter> void apply(T character) {
        for (Weapon weapon : character.weapons) {
            weapon.ammo += weapon.clipSize;
        }
    }

    @Override
    public <T extends PlayerCharacter> void cease(T character) {
        /*
         * No implementation needed.
         */
    }

}