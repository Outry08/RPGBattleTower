package RPGBattleTower;

/*******************************************************************************
 * Project: RPGBattleTower
 * 
 * @author Ryan McKinnon
 *         Class: GameCharacter
 *         Description: Holds fields and methods that are shared by both enemies
 *         and the
 *         user. These methods are attack and defend.
 *******************************************************************************/

public class GameCharacter {

    // Fields
    protected String name;
    protected int health;
    protected int strength;
    protected int defence;
    protected int maxHealth;
    protected int baseStrength;
    protected int baseDefence;
    protected boolean defending;
    protected int level;

    // New player Constructor
    GameCharacter(String n, int mH, int bS, int bD, int l) {
        name = n;
        maxHealth = mH;
        baseStrength = bS;
        baseDefence = bD;
        health = maxHealth;
        strength = baseStrength;
        defence = baseDefence;
        defending = false;
        level = l;
    }

    // Returning player Constructor
    GameCharacter(String n, int h, int mH, int bS, int bD, int l) {
        name = n;
        health = h;
        maxHealth = mH;
        baseStrength = bS;
        strength = baseStrength;
        baseDefence = bD;
        defence = baseDefence;
        defending = false;
        level = l;
    }

    // name Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // health Getter and Setter
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    // strength Getter and Setter
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    // defence Getter and Setter
    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    // maxHealth Getter and Setter
    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // baseStrength Getter and Setter
    public int getBaseStrength() {
        return baseStrength;
    }

    public void setBaseStrength(int baseStrength) {
        this.baseStrength = baseStrength;
    }

    // baseDefence Getter and Setter
    public int getBaseDefence() {
        return baseDefence;
    }

    public void setBaseDefence(int baseDefence) {
        this.baseDefence = baseDefence;
    }

    // defending Getter and Setter
    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    // level Getter and Setter
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Method: attack
     * Description: Uses the strength field to determine the damage of an attack.
     * 
     * @return - The damage of an attack;
     **/
    public int attack() {
        /*
         * Getting a random number between 5 less than the strength field and 5
         * more than the strength field and returning it as damage.
         */
        return (int) ((strength - 5) + Math.random()
                * ((strength + 5) + 1 - (strength - 5)));
    }

    /**
     * Method: defend
     * Description: Setting the defence field to 2x itself temporarily, and
     * setting the defending boolean to true.
     **/
    public void defend() {
        System.out.println(name + " chose to defend.");
        defence *= 2;
        defending = true;
    }

}
