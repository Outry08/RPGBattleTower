package rpgbattletower;

/*******************************************************************************
 * Project: RPGBattleTower
 * @author Ryan McKinnon
 * Class: Enemy
 * Description: Holds fields and methods that are specific to what enemies do,
   such as randomly determine their move and deal damage to the user. Inherits
   from GameCharacter class.
*******************************************************************************/

import java.util.*;

public class Enemy extends GameCharacter {
    
    //Global Scanner
    public Scanner scanN = new Scanner(System.in);
    
    //Fields
    protected boolean skeleton;
    protected int maxDodgeNum;
    
    //Argument Constructor
    Enemy(String n, int mH, int bS, int bD, int l, boolean s, int mDN) {
        /*
        The enemy's stats are set to their base stats, with 13 added on for
        every level for health, 11 for strength, and 9 for the defence.
        */
        super(n, mH + (13 * (l-1)), bS + (11 * (l-1)), bD + (9 * (l-1)), l);
        skeleton = s;
        maxDodgeNum = mDN;
    }

    //skeleton Getter and Setter
    public boolean isSkeleton() {
        return skeleton;
    }
    public void setSkeleton(boolean skeleton) {
        this.skeleton = skeleton;
    }
    
    //maxDodgeNum Getter and Setter
    public int getMaxDodgeNum() {
        return maxDodgeNum;
    }
    public void setMaxDodgeNum(int maxDodgeNum) {
        this.maxDodgeNum = maxDodgeNum;
    }
    
    /**
     * Method: chooseAction
     * Description: Randomly chooses a 1, 2, 3, or 4, each corresponding to an
       enemy's different move.
     * @return - The number randomly chosen to determine which move the enemy
       will do.
    **/
    public int chooseAction() {
        
        //Variables
        int moveChoice, moveNumber;
        
        /*
        If the enemy is defending, set their defending boolean to false and
        their defence back to their baseDefence.
        */
        if (defending) {
            defending = false;
            defence = baseDefence;
        }
        
        //A random number between 1 and 100, to use percent chances
        moveChoice = (int)(1 + Math.random() * (100 + 1 - 1));
        
        //50% chance of move 1
        if (moveChoice <= 50) 
            moveNumber = 1;
        //20% chance of move 2
        else if (moveChoice > 50 && moveChoice <= 70) 
            moveNumber = 2;
        //15% chance of move 3
        else if (moveChoice > 70 && moveChoice <= 85) 
            moveNumber = 3;
        //15% chance of move 4
        else
            moveNumber = 4;
        
        return moveNumber;
        
    }
    
    /**
     * Method: attack
     * Description: Takes the user's input to potentially dodge the enemy's
       attack. If the user doesn't dodge it, damage the user.
     * @param user - The Player object that the user is playing the game through.
     * @throws InterruptedException - Allows for the user of program pauses.
    **/
    public void attack(Player user) throws InterruptedException {
        
        //Variables
        int damage;
        int max;  
        int userDodgeNum, programDodgeNum;
        
        /*
        Subtracting the user's defence from the damage variable, which is
        received from the superclass' attack.
        */
        damage = super.attack() - user.getDefence();
        //If the damage is negative, set it to 0
        if (damage < 0)
            damage = 0;
        
        /*
        The maximum number of the range of number to guess to try and dodge is
        equal to the maxDodgeNum minus the user's dodgeOdds.
        */
        max = (int)(maxDodgeNum - user.getDodgeOdds());
        
        /*
        If the max variable is less than or equal to 1, set it to 2 so that it
        could never be a 100% chance of dodging all the time. The best odds the
        user can have is 50%.
        */
        if (max <=1 )
            max = 2;
        
        //Output that the enemy is attacking
        System.out.println("The " + name + " is attacking!");
        /*
        User input for a number between 1 and the max variable to try and dodge
        the enemy's attack.
        */
        System.out.print("Choose a number between 1-" + max
                + " to try and dodge: ");
        //Error handling
        do {
            userDodgeNum = scanN.nextInt();
            if (userDodgeNum < 1 || userDodgeNum > max)
                System.out.print("Please choose a number between 1 & " + max
                        + ": ");
        } while (userDodgeNum < 1 || userDodgeNum > max);
        
        //Randomly determine a number of the same range
        programDodgeNum = (int)(1 + Math.random() * (max + 1 - 1));
        
        Thread.sleep(1500);
        
        /*
        If the user and the program chose the same number, output that the user
        dodge the attack, and skip the enemy damaging them.
        */
        if (userDodgeNum == programDodgeNum)
            System.out.println(user.getName()
                    + " successfully dodged the attack!");
        //If the user and the program did not choose the same number...
        else {
            //Output that th user did not dodge the attack
            System.out.println(user.getName() + " did not dodge the attack.");
            
            Thread.sleep(1000);
            
            //Output how much damage the enemy dealth to the user
            System.out.println(name + " dealt " + damage + " damage to "
                    + user.getName());
            
            //Subtract the damage variable from the user' health
            user.setHealth(user.getHealth() - damage);
            
            //If the user's health is less than or equal to zero...
            if (user.getHealth() <= 0) {
                //Set the user's alive boolean to false
                user.setAlive(false);
                //State that the user was defeated
                System.out.println(user.getName() + " was defeated by the "
                        + name);
            }
        }
        
    }
    
    //Methods overridden for all subclasses
    public void executeAction(ArrayList<Enemy> enemies, Player user)
            throws InterruptedException {
        System.out.println("Error. Enemy class should not call upon"
                + " executeAction.");
    }
    
    //Overridden Skeleton methods
    public boolean isDown() {
        return false;
    }
    public void setDown(boolean down) {
        System.out.println("Error. Enemy class should not call upon setDown.");
    }
    public int getDownTurns() {
        System.out.println("Error. Enemy class should not call upon"
                + " getDownTurns.");
        return -1;
    }
    public void setDownTurns(int downTurns) {
        System.out.println("Error. Enemy class should not call upon"
                + " setDownTurns.");
    }
    public int getCloneCount() {
        System.out.println("Error. Enemy class should not call upon"
                + " getCloneCount.");
        return -1;
    }
    public void setCloneCount(int cloneCount) {
        System.out.println("Error. Enemy class should not call upon"
                + " setCloneCount.");
    }
    public void revive() {
        System.out.println("Error. Enemy class should not call upon revive.");
    }
    public void clone(ArrayList<Enemy> enemies, Player user) {
        System.out.println("Error. Enemy class should not call upon clone.");
    }
    
    //Overridden Bear methods
    public boolean isCharged() {
        return false;
    }
    public void setCharged(boolean charged) {
        System.out.println("Error. Enemy class should not call upon setCharged.");
    }
    public void charge() {
        System.out.println("Error. Enemy class should not call upon charge.");
    }
    public void rest() {
        System.out.println("Error. Enemy class should not call upon rest.");
    }
    
    //Overridden Ghost methods
    public boolean isHidden() {
        return false;
    }
    public void setHidden(boolean hidden) {
        System.out.println("Error. Enemy class should not call upon setHidden.");
    }
    public int getHiddenTurns() {
        System.out.println("Error. Enemy class should not call upon"
                + " getHiddenTurns.");
        return -1;
    }
    public void setHiddenTurns(int hiddenTurns) {
        System.out.println("Error. Enemy class should not call upon"
                + " setHiddenTurns.");
    }
    public void hide() {
        System.out.println("Error. Enemy class should not call upon hide.");
    }
    public void scare(Player user) {
        System.out.println("Error. Enemy class should not call upon scare.");
    }
    
}
