package inheritamon.model.pokemon.moves;

import java.util.HashMap;

import inheritamon.model.pokemon.types.Pokemon;

public class NormalAbility extends Ability {

    public int executeMove(Pokemon enemy, Pokemon user) {

        // Check if the user has enough MP to use the move
        int mp = user.getNumericalStat("MP");
        int mpCost = numericalStats.get("Cost");

        if (mp < mpCost) {
            System.out.println("Not enough MP!");

            // -1 is used to indicate that the move was not used
            return -1;
        }

        // Reduce the user's MP by the cost of the move
        user.loseMP(mpCost);

        // Obtain the accuracy of the move
        int accuracy = user.getNumericalStat("Acc");

        String type = stringStats.get("Type");

        // Calculate the damage based on the user's attack and modifier depending on the type
        int modifier = numericalStats.get("Modifier");
        int atk = user.getNumericalStat("Atk");
        int mAtk = user.getNumericalStat("M.Atk");

        // Take a split of the attack and magic attack by default, used in the case of healing
        int damage = (atk + (mAtk * 2)) / 2 * modifier;

        if (type.equals("Physical")) {
            damage = atk * modifier;
            System.out.println("Physical raw damage: " + damage);
        } else if (type.equals("Magical")) {
            damage = mAtk * modifier;
            System.out.println("Magical raw damage: " + damage);
        } else if (type.equals("Healing")) {
            user.gainHP(damage);
            return -damage;
        } else {
            System.out.println("Unknown type!");
        }
        

        Integer calculatedDamage = enemy.takeDamage(damage, accuracy);

        return calculatedDamage;

    }

    public NormalAbility(HashMap<String, String> moveData) {
        super(moveData);
    }
    
}
