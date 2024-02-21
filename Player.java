package RPGBattleTower;

/*******************************************************************************
 * Project: RPGBattleTower
 * @author Ryan McKinnon
 * Class: Player
 * Description: Holds fields and methods that are specific to what the user can
   do with their player character. Such as the user's battle menu, special
   attack, their inventory, and upgrades. Inherits from GameCharacter class
*******************************************************************************/

import java.util.*;

public class Player extends GameCharacter {

    // Global Scanner
    public Scanner scanN = new Scanner(System.in);

    // Fields
    private int specialPoints;
    private int maxSpecialPoints;
    private int dodgeOdds;
    private boolean alive;
    private ArrayList<String> inventory;
    private int maxInventory;
    private int sBoostTurns;
    private int dBoostTurns;
    private boolean scared;
    private int scaredTurns;

    // New player Constructor
    Player(String n) {
        super(n, 50, 25, 13, 1);
        maxSpecialPoints = 20;
        specialPoints = maxSpecialPoints;
        dodgeOdds = 0;
        alive = true;
        inventory = new ArrayList<>(Arrays.asList("Health Potion",
                "Health Potion", "Health Potion"));
        maxInventory = 5;
        sBoostTurns = 0;
        dBoostTurns = 0;
        scared = false;
        scaredTurns = 0;
    }

    // Returning player Constructor
    Player(String n, int h, int mH, int bS, int bD, int l, int sP, int mSP,
            int dO, int mI, ArrayList<String> i) {
        super(n, h, mH, bS, bD, l);
        specialPoints = sP;
        maxSpecialPoints = mSP;
        dodgeOdds = dO;
        alive = true;
        maxInventory = mI;
        inventory = i;
        sBoostTurns = 0;
        dBoostTurns = 0;
        scared = false;
        scaredTurns = 0;
    }

    // specialPoints Getter and Setter
    public int getSpecialPoints() {
        return specialPoints;
    }

    public void setSpecialPoints(int specialPoints) {
        this.specialPoints = specialPoints;
    }

    // maxSpecialPoints Getter and Setter
    public int getMaxSpecialPoints() {
        return maxSpecialPoints;
    }

    public void setMaxSpecialPoints(int maxSpecialPoints) {
        this.maxSpecialPoints = maxSpecialPoints;
    }

    // dodgeOdds Getter and Setter
    public int getDodgeOdds() {
        return dodgeOdds;
    }

    public void setDodgeOdds(int dodgeOdds) {
        this.dodgeOdds = dodgeOdds;
    }

    // alive Getter and Setter
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // inventory Getter and Setter
    public ArrayList<String> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    // maxInventory Getter and Setter
    public int getMaxInventory() {
        return maxInventory;
    }

    public void setMaxInventory(int maxInventory) {
        this.maxInventory = maxInventory;
    }

    // sBoostTurns Getter and Setter
    public int getsBoostTurns() {
        return sBoostTurns;
    }

    public void setsBoostTurns(int sBoostTurns) {
        this.sBoostTurns = sBoostTurns;
    }

    // dBoostTurns Getter and Setter
    public int getdBoostTurns() {
        return dBoostTurns;
    }

    public void setdBoostTurns(int dBoostTurns) {
        this.dBoostTurns = dBoostTurns;
    }

    // scared Getter and Setter
    public boolean isScared() {
        return scared;
    }

    public void setScared(boolean scared) {
        this.scared = scared;
    }

    // scaredTurns Getter and Setter
    public int getScaredTurns() {
        return scaredTurns;
    }

    public void setScaredTurns(int scaredTurns) {
        this.scaredTurns = scaredTurns;
    }

    /**
     * Method: menu
     * Description: Prints the user's main menu that will be shown for each of
     * the user's turns. Takes the user input for which action they will do for
     * that turn, and calls upon the respective action's methods.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @throws InterruptedException - Allows for the user of program pauses.
     **/
    public void menu(ArrayList<Enemy> enemies) throws InterruptedException {

        // Variables
        int takeScaredTurn;
        int menu1Choice, menu2Choice = 0, backNumber = -1;
        boolean lackingPoints;

        /*
         * If the user is defending, set the defending boolean to false and divide
         * the defence by 2.
         */
        if (defending) {
            defending = false;
            defence /= 2;
        }

        // Subtract one from sBoostTurns, because one turn has passed
        sBoostTurns--;
        // If sBoostTurns = 0, reset the user's strength back to their base stength
        if (sBoostTurns == 0) {
            strength = baseStrength;
            System.out.println("The effects of the strength potion have worn"
                    + " off.");
        }

        // Subtract one from dBoostTurns, because one turn has passed
        dBoostTurns--;
        /*
         * If dBoostTurns = 0, divide the user's defence by 2, reverting it back
         * to before it was boosted by a defence potion.
         */
        if (dBoostTurns == 0) {
            defence /= 2;
            System.out.println("The effects of the defence potion have worn"
                    + " off.");
        }

        // Subtract one from scaredTurns, because one turn has passed
        scaredTurns--;
        // If scaredTurns = 0, set the scared boolean to false
        if (scaredTurns == 0) {
            scared = false;
            System.out.println("\n" + name + " is no longer scared.");
        }

        // Outputting the user's health and special points
        System.out.println("\nHere are your current stats:");
        System.out.println("HP: " + health + " / " + maxHealth
                + "\nSP: " + specialPoints + " / " + maxSpecialPoints);

        /*
         * If the user's sBoostTurns is greater than 0, print how many turns of
         * strength boost they have left.
         */
        if (sBoostTurns > 0)
            System.out.println("Strength Boost Turns Remaining: " + sBoostTurns);
        /*
         * If the user's dBoostTurns is greater than 0, print how many turns of
         * defence boost they have left.
         */
        if (dBoostTurns > 0)
            System.out.println("Defence Boost Turns Remaining: " + dBoostTurns);
        /*
         * If the user's scaredTurns is greater than 0, print how many turns of
         * being scared they have left.
         */
        if (scaredTurns > 0)
            System.out.println("Scared Turns Remaining: " + scaredTurns);

        Thread.sleep(1000);

        // If the user is scared...
        if (scared) {
            /*
             * Get a random number between 1 & 2 to determine if the user will
             * take their turn or not because they are scared.
             */
            takeScaredTurn = (int) (1 + Math.random() * (2 + 1 - 1));

            // If the random number is 1, allow the user to take their turn
            if (takeScaredTurn == 1)
                System.out.println("\n" + name + " fought through their fear and"
                        + " will take their turn!");
            /*
             * If the random number is 2, don't allow the user to take their turn
             * by using an empty return statement to exit the method early.
             */
            else {
                System.out.println("\n" + name + " is too scared to move and"
                        + " will not take their turn.");
                Thread.sleep(1000);
                return;
            }
            Thread.sleep(1000);
        }

        // Printing a list of the enemies that the user is facing
        System.out.println("\nHere are the enemies you are facing: ");
        printEnemyList(enemies);

        Thread.sleep(2000);

        /*
         * Loops while the user selects 'back' in any menu where the option is
         * available.
         */
        do {

            // User input for what action they will do
            System.out.print("\nWhat will you do?"
                    + "\n1. Attack"
                    + "\n2. Defend"
                    + "\n3. Use Item"
                    + "\nChoice: ");
            // Error handling
            do {
                menu1Choice = scanN.nextInt();
                if (menu1Choice < 1 || menu1Choice > 3)
                    menuErrorMessage();
            } while (menu1Choice < 1 || menu1Choice > 3);

            // Executing the correct action based on the user's input
            switch (menu1Choice) {
                // If the user chose to attack...
                case 1:
                    // Initially set the lackingPoints boolean to false
                    lackingPoints = false;
                    // User input for what type of attack thy will do
                    System.out.print("\nWhat attack will you do?"
                            + "\n1. Basic Attack"
                            + "\n2. Super Attack (5 SP)"
                            + "\n3. Group Attack (5 SP)"
                            + "\n4. Back"
                            + "\nChoice: ");
                    /*
                     * If the user's special points are less than 5, set
                     * lackingPoints to true.
                     */
                    if (specialPoints < 5)
                        lackingPoints = true;
                    // Error handling
                    do {
                        menu2Choice = scanN.nextInt();

                        if (menu2Choice < 1 || menu2Choice > 4)
                            menuErrorMessage();

                        else if (lackingPoints && (menu2Choice == 2
                                || menu2Choice == 3)) {
                            System.out.print("You do not have enough special"
                                    + " points to do that attack."
                                    + "\nChoose again: ");
                        }

                    } while (menu2Choice < 1 || menu2Choice > 4 ||
                            (lackingPoints && (menu2Choice == 2
                                    || menu2Choice == 3)));

                    // Setting the backNumber variable to 4
                    backNumber = 4;

                    // Executing the correct attack type based on the user's input
                    switch (menu2Choice) {
                        // If the user chose basic attack go to the attack method
                        case 1:
                            attack(enemies);
                            break;
                        /*
                         * If the user chose super attack, take away 5 special
                         * points, then go to the superAttack method.
                         */
                        case 2:
                            specialPoints -= 5;
                            superAttack(enemies);
                            break;
                        /*
                         * If the user chose group attack, take away 5 special
                         * points, then go to the groupAttack method.
                         */
                        case 3:
                            specialPoints -= 5;
                            groupAttack(enemies);
                            break;
                    }
                    break;

                // If the user chose to defend...
                case 2:
                    // Go to the defend method
                    defend();
                    /*
                     * set menu2Choice to 0 to ensure that the menu will never
                     * accidentally loop.
                     */
                    menu2Choice = 0;
                    break;

                // If the user chose to use an item...
                case 3:
                    /*
                     * Setting the backNumber to the size of the user's inventory
                     * plus 1.
                     */
                    backNumber = inventory.size() + 1;

                    /*
                     * If the user's inventory is empty, print that the user
                     * doesn't have any items, then set the menu2Choice variable
                     * to the backNumber so that the menu loops.
                     */
                    if (inventory.isEmpty()) {
                        System.out.println("\nYou don't have any items.\n");
                        menu2Choice = backNumber;
                    }
                    // If the user's inventory is not empty...
                    else {
                        // Take the user input for which item they will use
                        System.out.println("\nWhich item would you like to use?");
                        printItemList();
                        System.out.println(backNumber + ". Back");
                        // Error handling
                        do {
                            menu2Choice = scanN.nextInt();

                            /*
                             * Break early if the user's input is the backNumber
                             * so that the user's input is not called upon in the
                             * else if below, because it will be out of bounds.
                             */
                            if (menu2Choice == backNumber)
                                break;
                            if (menu2Choice < 1
                                    || menu2Choice > backNumber)
                                menuErrorMessage();
                            else if (inventory.get(menu2Choice - 1).equals("Key"))
                                System.out.print("You cannot use a key in battle."
                                        + "\nChoose again: ");

                        } while (menu2Choice < 1
                                || menu2Choice > backNumber
                                || inventory.get(menu2Choice - 1).equals("Key"));

                        // If the user did not select back...
                        if (menu2Choice != backNumber) {
                            System.out.println();
                            /*
                             * Access the appropriate method corresponding to which
                             * type of item the user chose.
                             */
                            switch (inventory.get(menu2Choice - 1)) {
                                case ("Health Potion"):
                                    useHealthPotion();
                                    break;
                                case ("Strength Potion"):
                                    useStrengthPotion();
                                    break;
                                case ("Defence Potion"):
                                    useDefencePotion();
                                    break;
                                case ("Special Point Potion"):
                                    useSpecialPointsPotion();
                                    break;
                                case ("Bomb"):
                                    useBomb(enemies);
                                    break;
                            }
                            // Remove whichever item the user chose to use.
                            inventory.remove(menu2Choice - 1);
                        }
                    }
                    break;

            }

        } while (menu2Choice == backNumber);

        Thread.sleep(2000);

    }

    /**
     * Method: printEnemyList
     * Description: Outputs a list of the enemies the user is facing, and any
     * states that the enemies are in.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     **/
    public void printEnemyList(ArrayList<Enemy> enemies) {

        // Loops for the size of the enemies ArrayList
        for (int i = 0; i < enemies.size(); i++) {
            // Print out the enemy's name and health
            System.out.println((i + 1) + ". " + enemies.get(i).getName() + " HP: "
                    + enemies.get(i).getHealth() + " / "
                    + enemies.get(i).getMaxHealth());

            System.out.print("\t");

            // If the enemy is charged, print that out
            if (enemies.get(i).isCharged())
                System.out.print(" [Charged]");

            // If the enemy is defending, print that out
            if (enemies.get(i).isDefending())
                System.out.print(" [Defending]");

            // If the enemy is hidden, print that out
            if (enemies.get(i).isHidden())
                System.out.print(" [Hidden]");

            // If the enemy is down, print that out
            if (enemies.get(i).isDown())
                System.out.print(" [Down]");

            System.out.println();
        }

    }

    /**
     * Method: printItemList
     * Description: Prints the user's inventory as a numbered list, sorted
     * alphabetically.
     **/
    public void printItemList() {
        // Sort the inventory alphabetically
        sortInventory();
        // Loops for the inventory's size, and prints each item in the inventory
        for (int i = 0; i < inventory.size(); i++)
            System.out.println((i + 1) + ". " + inventory.get(i));
    }

    /**
     * Method: attack
     * Description: First receives the damage from the superclass' attack
     * method, the calls upon choose enemy.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void attack(ArrayList<Enemy> enemies) throws InterruptedException {
        int damage = super.attack();
        chooseEnemy(enemies, damage);
    }

    /**
     * Method: superAttack
     * Description: Calls upon the attack method with the user's strength
     * multiplied by 2.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void superAttack(ArrayList<Enemy> enemies)
            throws InterruptedException {

        // Output that the user is using a super attack
        System.out.println("\n" + name + " used a super attack!");

        // Multiply the user's strength by 2
        strength *= 2;

        // Call upon the attack method withstrength doubled
        attack(enemies);

        // Divide the user's strength by 2 again
        strength /= 2;

    }

    /**
     * Method: groupAttack
     * Description: Prints that the user used a group attack and calls upon the
     * damageAllEnemies method.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void groupAttack(ArrayList<Enemy> enemies)
            throws InterruptedException {
        System.out.println("\n" + name + " used a group attack!");
        damageAllEnemies(enemies);
    }

    /**
     * Method: chooseEnemy
     * Description: Prints a menu of enemies and takes the user input for which
     * one they will attack.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @param damage  - The amount of damage that the user will deal to their
     *                chosen enemy.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void chooseEnemy(ArrayList<Enemy> enemies, int damage)
            throws InterruptedException {

        // Variable
        int enemyChoice;

        // User input for which enemy they will attack
        System.out.println("Which enemy will you attack?");
        printEnemyList(enemies); // Printing the menu of enemies
        System.out.print("Choice: ");
        // Error handling
        do {
            enemyChoice = scanN.nextInt();

            if (enemyChoice < 1 || enemyChoice > enemies.size())
                menuErrorMessage();

            else if (enemies.get(enemyChoice - 1).isDown())
                System.out.print("That enemy is already down and cannot be"
                        + " attacked.\nChoose again: ");

        } while (enemyChoice < 1 || enemyChoice > enemies.size()
                || enemies.get(enemyChoice - 1).isDown());

        // Calling upon the damageEnemy method
        damageEnemy(enemies, enemyChoice - 1, damage);

    }

    /**
     * Method: damageEnemy
     * Description: Deals damage to the chosen enemy and checks if they have
     * been defeated. Also checks if the user can run away to the next floor if
     * the ArrayList of enemies is filled with nothing but downed skeletons.
     * 
     * @param enemies    - The ArrayList of Enemy objects that the user is
     *                   currently fighting.
     * @param enemyIndex - The index of the user's chosen enemy.
     * @param damage     - The amount of damage to be dealt.
     * @throws InterruptedException - Allows for the user of program pauses.
     **/
    public void damageEnemy(ArrayList<Enemy> enemies, int enemyIndex, int damage)
            throws InterruptedException {

        // Variables
        String item;
        boolean allDown = true;

        // Taking the damage variable and subtracting the enemy's defence from it
        damage = damage - enemies.get(enemyIndex).getDefence();
        // If the damage is a negative number, just make it 0
        if (damage < 0)
            damage = 0;

        /*
         * If the chosen enemy is hidden, print that the attack missed, and don't
         * damage the enemy.
         */
        if (enemies.get(enemyIndex).isHidden())
            System.out.println("\nThe attack missed the "
                    + enemies.get(enemyIndex).getName() + "!");
        // If the chosen enemy is not hidden...
        else {
            // Subtract the damage variable from the chosen enemy's health.
            enemies.get(enemyIndex).setHealth(enemies.get(enemyIndex).getHealth()
                    - damage);
            /*
             * Output that the user attack the chosen enemy, and output the damage
             * dealt.
             */
            System.out.println("\nYou attacked the "
                    + enemies.get(enemyIndex).getName() + " and dealt "
                    + damage + " damage!");
        }

        Thread.sleep(500);

        // If the enemy's health is less than or equal to zero...
        if (enemies.get(enemyIndex).getHealth() <= 0) {
            // If the enemy is not a skeleton...
            if (!enemies.get(enemyIndex).isSkeleton()) {
                // Print that the enemy was defeated
                System.out.println(enemies.get(enemyIndex).getName()
                        + " was defeated!");

                Thread.sleep(1000);

                /*
                 * Determine if the enemy will drop an item, and what item that
                 * will be.
                 */
                enemyItemDrop(enemies.get(enemyIndex).getName());

                // Remove the enemy from the enemies ArrayList
                enemies.remove(enemyIndex);
            }
            // If the enemy is a skeleton and is not down
            else if (enemies.get(enemyIndex).isSkeleton()
                    && !enemies.get(enemyIndex).isDown()) {
                // Print that the skeleton was knowcked down
                System.out.println(enemies.get(enemyIndex).getName() + " was"
                        + " knocked down.");

                /*
                 * If the enemy's health is less than 0, set it equal to zero since
                 * the health is still outputted even though the enemy is down.
                 */
                if (enemies.get(enemyIndex).getHealth() < 0)
                    enemies.get(enemyIndex).setHealth(0);

                // Set the enemy's down boolean to true, an downTurns variable to 4.
                enemies.get(enemyIndex).setDown(true);
                enemies.get(enemyIndex).setDownTurns(4);

                /*
                 * If the enemy was defending when it was knocked down, set the
                 * defending boolean to false so that it is not needlessly
                 * outputted.
                 */
                if (enemies.get(enemyIndex).isDefending())
                    enemies.get(enemyIndex).setDefending(false);
            }

        }
        // Loops for the size of the enemies ArrayList
        for (int i = 0; i < enemies.size(); i++) {
            /*
             * If the enemy is not down, set the allDown boolean to false and
             * break out of the loop.
             */
            if (!enemies.get(i).isDown()) {
                allDown = false;
                break;
            }
        }

        /*
         * If the allDown boolean remains true after every enemy has been checked,
         * and the enemies ArrayList isn't already empty...
         */
        if (allDown && !enemies.isEmpty()) {
            // Loops while the enemies ArrayList is not empty
            while (!enemies.isEmpty()) {

                Thread.sleep(1000);

                /*
                 * Check if the enemy will drop an item, and determining which
                 * item that will be.
                 */
                enemyItemDrop(enemies.get(0).getName());

                // Remove the enemy from the ArrayList
                enemies.remove(0);
            }

            Thread.sleep(500);

            /*
             * Output that the user ran away before the skeletons could get back
             * up, revealing how to beat a group of skeletons after the user did
             * it once. This is because skeletons can never be permanently
             * defeated, only temporarily knocked over. So you need to knock them
             * all over before you can advance to the next floor.
             */
            System.out.println("\n" + name + " left the room before the skeletons"
                    + " could get back up.");
        }

    }

    /**
     * Method: useHealthPotion
     * Description: Heals the user by half of their max health.
     **/
    public void useHealthPotion() {

        // Variable
        int amount = maxHealth / 2;

        // Add the amount to heal to the health field
        health += (amount);

        // Check if the user has more health than their max health
        checkMaxHPSurpassed();

        // Output that the user used a health potion and how much they healed
        System.out.println(name + " used a health potion, replenishing "
                + amount + " health.");

    }

    /**
     * Method: useStrengthPotion
     * Description: Multiplies the user's strength by 2 for 3 turns.
     **/
    public void useStrengthPotion() {

        // Sets strength to baseStrength times 2
        strength = baseStrength * 2;

        // Sets the sBoostTurns variable to 3
        sBoostTurns = 3;

        // Output that the user used a strength potion
        System.out.println(name + " used a strength potion, doubling their"
                + " strength for 3 turns.");
    }

    /**
     * Method: useDefencePotion
     * Description: Multiplies the user's defence by 2 for 3 turns.
     **/
    public void useDefencePotion() {

        // Sets defence to baseDefence times 2
        defence = baseDefence * 2;

        // Sets the dBoostTurns variable to 3
        dBoostTurns = 3;

        // Output that the user used a defence potion
        System.out.println(name + " used a defence potion, doubling their"
                + " defence for 3 turns.");
    }

    /**
     * Method: useSpecialPointPotion
     * Description: Increases the user's special points by 10.
     **/
    public void useSpecialPointsPotion() {

        // Add 10 to special points
        specialPoints += 10;

        // Checks if the user's special points surpassed the max special points
        checkMaxSPSurpassed();

        // Output that the user used a special point potion.
        System.out.println(name + " used a special points potion, replenishing"
                + " 10 special points.");

    }

    /**
     * Method useBomb
     * Description: Uses a bomb item to damage all enemies.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void useBomb(ArrayList<Enemy> enemies) throws InterruptedException {

        // Output that the user used a bomb
        System.out.println(name + " used a bomb.");

        // Call upon the damage all enemies method
        damageAllEnemies(enemies);

    }

    /**
     * Method: checkKey
     * Description: Binary searches the user inventory to see if they have a key.
     * 
     * @return - Returns the index of the key in the user's inventory if it was
     *         found, otherwise returns -1;
     **/
    public int checkKey() {

        // Variables
        int first = 0;
        int last = inventory.size() - 1;
        int middle = -1;
        boolean found = false;

        // Sorts the user's inventory alphabetically
        sortInventory();

        // Binary searching the user's inventory for a key
        while (!found && first <= last) {

            middle = (first + last) / 2;

            if (inventory.get(middle).equals("Key"))
                found = true;
            else if (inventory.get(middle).compareTo("Key") > 0)
                last = middle - 1;
            else if (inventory.get(middle).compareTo("Key") < 0)
                first = middle + 1;

        }

        // If a key was not found, set middle to -1
        if (!found)
            middle = -1;

        /*
         * Return the middle variable. Either the index where the key was found,
         * or -1
         */
        return middle;

    }

    /**
     * Method: upgrade
     * Description: Outputs three randomly selected upgrades for the user to
     * choose from.
     */
    public void upgrade() {

        // Variables
        int upgradeChoice;
        int lastNonItemUp = 5;
        String itemName = "None";
        // Array of random numbers
        int[] randIndexes = new int[3];
        // Array of potential ugrades
        String[] upgrades = { "Max HP +10", "Strength +10", "Defence +5",
                "Max SP +10", "Dodge Odds ↑", "Inventory Size +5",
                "Receive Health Potion x2", "Receive Strength Potion x2",
                "Receive Defence Potion x2", "Receive Special Points Potion x2",
                "Receive Bomb x2" };

        // Choosing the first random upgrade
        randIndexes[0] = (int) (0 + Math.random() * (upgrades.length));

        /*
         * Choosing the second random upgrade, ensuring that it is not the same as
         * the first.
         */
        do {
            randIndexes[1] = (int) (0 + Math.random() * (upgrades.length));
        } while (randIndexes[1] == randIndexes[0]);

        /*
         * Choosing the third random upgrade, ensuring that it is not the same as
         * the first.
         */
        do {
            randIndexes[2] = (int) (0 + Math.random() * (upgrades.length));
        } while (randIndexes[2] == randIndexes[1]
                || randIndexes[2] == randIndexes[0]);

        // User input for which upgrade they would like
        System.out.println("\nChoose your upgrade:");
        for (int i = 0; i < randIndexes.length; i++)
            System.out.println((i + 1) + ". " + upgrades[randIndexes[i]]);

        /*
         * If the user's inventory is at max capacity and there is an option for
         * receiving an item, output to the user that their inventory is full so
         * that their decision is more informed.
         */
        if (inventory.size() == maxInventory && (randIndexes[0] > lastNonItemUp
                || randIndexes[1] > lastNonItemUp
                || randIndexes[2] > lastNonItemUp))
            System.out.println("(Your inventory is full)");

        /*
         * If the user's inventory is not at max capacity and there is an option for
         * receiving an item, output to the user that their inventory is full so
         * that their decision is more informed.
         */
        else if (inventory.size() < maxInventory &&
                (randIndexes[0] > lastNonItemUp
                        || randIndexes[1] > lastNonItemUp
                        || randIndexes[2] > lastNonItemUp))
            System.out.println("(Your inventory is not full)");

        System.out.print("\nChoice: ");
        // Error handling
        do {
            upgradeChoice = scanN.nextInt();
            if (upgradeChoice < 1 || upgradeChoice > randIndexes.length)
                menuErrorMessage();
        } while (upgradeChoice < 1 || upgradeChoice > randIndexes.length);

        /*
         * Look at the randomIndex at the user's choice minus 1 to determine which
         * upgrade they chose.
         */
        switch (randIndexes[upgradeChoice - 1]) {

            /*
             * If the index is 0, increase the user's maxHealth by 10 and
             * increase their health by that much as well.
             */
            case 0:
                maxHealth += 10;
                health += 10;
                System.out.println("Max health increased.");
                break;

            /*
             * If the index is 1, increase the user's baseStrength by 10 and
             * increase their strength by that much as well.
             */
            case 1:
                baseStrength += 10;
                strength = baseStrength;
                System.out.println("Strength increased.");
                break;

            /*
             * If the index is 2, increase the user's baseDefence by 10 and
             * increase their defence by that much as well.
             */
            case 2:
                baseDefence += 5;
                defence = baseDefence;
                System.out.println("Defence increased.");
                break;

            /*
             * If the index is 3, increase the user's maxSpecialPoints by 10 and
             * increase their specialPoints by that much as well.
             */
            case 3:
                maxSpecialPoints += 10;
                specialPoints += 10;
                System.out.println("Max special points increased.");
                break;

            // If the index is 4, increase the user's dodgeOdds by 1
            case 4:
                dodgeOdds++;
                System.out.println("Odds of dodging slightly increased.");
                break;

            // If the index is 5, increase the user's inventory size by 5
            case 5:
                maxInventory += 5;
                System.out.println("Inventory size increased.");
                break;

            // If the index is 6, set the itemName to Health Potion
            case 6:
                itemName = "Health Potion";
                break;

            // If the index is 7, set the itemName to Strength Potion
            case 7:
                itemName = "Strength Potion";
                break;

            // If the index is 8, set the itemName to Defence Potion
            case 8:
                itemName = "Defence Potion";
                break;

            // If the index is 9, set the itemName to Special Points Potion
            case 9:
                itemName = "Special Points Potion";
                break;

            // If the index is 10, set the itemName to Bomb
            case 10:
                itemName = "Bomb";
                break;

        }

        /*
         * If the upgrade the the user chose has to do with getting an item, add
         * two of the itemName to the user's inventory, and check if the user's
         * inventory surpassed its maximum.
         */
        if (randIndexes[upgradeChoice - 1] > lastNonItemUp) {
            for (int i = 0; i < 2; i++) {
                inventory.add(itemName);
                System.out.println(itemName + " added to inventory.");
                checkFullInventory();
            }
        }

    }

    /**
     * Method: increaseStats
     * Description: Increases the user's stats by a fixed amount multiplied by
     * a multiplier in the parameters. Does other functions specifically if the
     * multiplier is 2.
     * 
     * @param multiplier - The number that the stat increases are multiplied by.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void increaseStats(int multiplier) throws InterruptedException {

        // Variables
        int healthInc = 10 * multiplier;
        int strengthInc = 5 * multiplier;
        int defenceInc = 4 * multiplier;
        int specialPointsInc = 5 * multiplier;

        // Outputting how much each stat has been increased by
        System.out.println("Max Health +" + healthInc);
        Thread.sleep(500);
        System.out.println("Strength +" + strengthInc);
        Thread.sleep(500);
        System.out.println("Defence +" + defenceInc);
        Thread.sleep(500);
        System.out.println("Max Special Points +" + specialPointsInc);
        Thread.sleep(500);

        // Increasing each stat by their respective increase amounts
        maxHealth += healthInc;
        baseStrength += strengthInc;
        baseDefence += defenceInc;
        maxSpecialPoints += specialPointsInc;

        // If teh multiplier is 2, meaning that the user levelled up...
        if (multiplier == 2) {

            // Increase the level by 1
            level++;

            // Print that health and special points have been fully replenished.
            System.out.println("Health Fully Replenished");
            Thread.sleep(500);
            System.out.println("Special Points Fully Replenished");
            Thread.sleep(5000);

            // Fully replenish health and special points
            health = maxHealth;
            specialPoints = maxSpecialPoints;

            /*
             * Go to the upgrade method to allow the user to choose an upgrade as
             * part of levelling up.
             */
            upgrade();
        }

    }

    /**
     * Method: enemyItemDrop
     * Description: Randomly determines if an enemy will drop an item.
     * 
     * @param enemyName - The name of the enemy that may potentially drop an item.
     **/
    public void enemyItemDrop(String enemyName) {

        // Variable
        String item;

        // If the random number between 1 and 3 is 1, the enemy drops an item
        if ((int) (1 + Math.random() * (3 + 1 - 1)) == 1) {
            // The item is equal to the item chosen in the determineItem method
            item = determineItem();

            // Print that the enemy dropped the item
            System.out.println("The " + enemyName
                    + " dropped a " + item + "!");

            // Add the item to the user's inventory
            inventory.add(item);
            // Check if the user's inventory is full
            checkFullInventory();
        }

    }

    /**
     * Method: determineItem
     * Description: Randomly determines an item to be dropped.
     * 
     * @return - The randomly selected item.
     **/
    public String determineItem() {

        // A random number between 1 and 100 to use percent chances
        int itemNumber = (int) (1 + Math.random() * (100 - 1 + 1));
        String itemName;

        // 40% chance of Health Potion being chosen
        if (itemNumber <= 40)
            itemName = "Health Potion";
        // 25% chance of Special Point Potion being chosen
        else if (itemNumber > 40 && itemNumber <= 65)
            itemName = "Special Point Potion";
        // 10% chance of Strength Potion being chosen
        else if (itemNumber > 65 && itemNumber <= 75)
            itemName = "Strength Potion";
        // 10% chance of Defence Potion being chosen
        else if (itemNumber > 75 && itemNumber <= 85)
            itemName = "Defence Potion";
        // 10% chance of Bomb being chosen
        else if (itemNumber > 85 && itemNumber <= 95)
            itemName = "Bomb";
        // 5% chance of Key being chosen
        else
            itemName = "Key";

        // Return the chosen item
        return itemName;

    }

    /**
     * Method: damageAllEnemies
     * Description: Call upon damageEnemy for every enemy in the enemies ArrayList.
     * 
     * @param enemies - The ArrayList of Enemy objects that the user is
     *                currently fighting.
     * @throws InterruptedException - Allows for the use of program pauses.
     **/
    public void damageAllEnemies(ArrayList<Enemy> enemies)
            throws InterruptedException {

        // Variables
        int damage;
        int sizeBefore = enemies.size();

        // Loops for the size of the enemies ArrayList
        for (int i = 0; i < enemies.size(); i++) {
            // If the current enemy is not down...
            if (!enemies.get(i).isDown()) {

                // Receive the damage from the superclass' attack method
                damage = super.attack();
                // Deal the damage to the current enemy
                damageEnemy(enemies, i, damage);

                /*
                 * If the enemies ArrayList's size has been altered
                 * (Meaning an enemy died)...
                 */
                if (enemies.size() != sizeBefore) {
                    /*
                     * Reduce i by one so that the same index is repeated, since
                     * this same index will now correspond to a new enemy.
                     */
                    i--;

                    /*
                     * set the sizeBefore variabe to the NEW enemies ArrayList
                     * size so that it can be checked again.
                     */
                    sizeBefore = enemies.size();
                }
            }
        }

    }

    /**
     * Method: sortInventory
     * Description: Bubble sorts the user's inventory to be in alphabetical order.
     **/
    public void sortInventory() {

        // Variables
        boolean isMixed;
        String dummy;

        // Sorting the user's inventory alphabetically using bubble sort
        do {
            isMixed = false;
            for (int i = 0; i < inventory.size() - 1; i++) {
                if (inventory.get(i).compareTo(inventory.get(i + 1)) > 0) {
                    dummy = inventory.get(i);
                    inventory.set(i, inventory.get(i + 1));
                    inventory.set(i + 1, dummy);
                    isMixed = true;
                }
            }
        } while (isMixed);

    }

    /**
     * Method: checkFullInventory
     * Description: Checks if the user's inventory size has surpassed the
     * user's maximum inventory capacity. If it did, Take the user input to
     * discard an item.
     **/
    public void checkFullInventory() {

        // Variable
        int discardChoice;

        /*
         * If the user's inventory size has surpassed the user's maximum inventory
         * capacity...
         */
        if (inventory.size() > maxInventory) {

            // Print this out to the user, and user input for an item to discard
            System.out.println("\nYour inventory it too full to carry this item."
                    + "\nPlease select an item to discard:");
            // Print the list of all the user's items
            printItemList();
            System.out.print("Choice: ");
            // Error handling
            do {
                discardChoice = scanN.nextInt();
                if (discardChoice < 1 || discardChoice > inventory.size())
                    menuErrorMessage();
            } while (discardChoice < 1 || discardChoice > inventory.size());

            // Outputting which item the user chose to discard
            System.out.println("You have discarded "
                    + inventory.get(discardChoice - 1));
            // Removing the user's chosen item
            inventory.remove(discardChoice - 1);

        }

    }

    /**
     * Method: checkMaxHPSurpassed
     * Description: Checks if the user's health surpassed their max health.
     **/
    public void checkMaxHPSurpassed() {
        /*
         * If the user's health is greater than their maxHealth, set the user's
         * health to maxHealth.
         */
        if (health > maxHealth)
            health = maxHealth;
    }

    /**
     * Method: checkMaxHPSurpassed
     * Description: Checks if the user's special points surpassed their max
     * special points.
     **/
    public void checkMaxSPSurpassed() {
        /*
         * If the user's specialPoints is greater than maxSpecialPoints, set the
         * user's specialPoints maxSpecialPoints.
         */
        if (specialPoints > maxSpecialPoints)
            specialPoints = maxSpecialPoints;
    }

    /**
     * Method: menuErrorMessage
     * Description: A line of code that was written for almost every error
     * handle message. Method created for efficiency.
     **/
    public void menuErrorMessage() {
        System.out.print("That is not an option.\nTry again: ");
    }

}
