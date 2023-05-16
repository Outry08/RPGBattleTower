package rpgbattletower;

/*******************************************************************************
 * Project: RPGBattleTower
 * @author Ryan McKinnon
 * Class: Skeleton
 * Description: Holds fields and methods that are specific to the skeleton
   enemy type. It has the two special moves of reviving and cloning. Inherits
   from Enemy class.
*******************************************************************************/

import java.util.*;

public class Skeleton extends Enemy {
    
    //Fields
    private boolean down;
    private int downTurns;
    private int cloneCount;
    
    //Skeleton Constructor
    Skeleton(int l) {
        super("Skeleton", 22, 20, 7, l, true, 5);
        down = false;
        downTurns = 0;
        cloneCount = 0;
    }
    
    //Constructed Skeleton Constructor
    Skeleton(int l, int cC) {
        super("Skeleton", 22, 20, 7, l, true, 5);
        down = false;
        downTurns = 0;
        cloneCount = cC;
    }
    
    //Skeleton Boss Constructor
    Skeleton(String n, int l) {
        super("Skeleton Boss", 90, 24, 12, l, true, 7);
        down = false;
        downTurns = 0;
        cloneCount = 0;
    }

    //down Getter and Setter
    public boolean isDown() {
        return down;
    }
    public void setDown(boolean down) {
        this.down = down;
    }
    
    //downTurns Getter and Setter
    public int getDownTurns() {
        return downTurns;
    }
    public void setDownTurns(int downTurns) {
        this.downTurns = downTurns;
    }

    //cloneCount Getter and Setter
    public int getCloneCount() {
        return cloneCount;
    }
    public void setCloneCount(int cloneCount) {
        this.cloneCount = cloneCount;
    }
    
    /**
     * Method: executeAction
     * Description: Takes the number received from the superclass' chooseAction
       method and calls upon the correct move based on the number and the
       current state of the skeleton.
     * @param enemies - The ArrayList of Enemy objects that the user is
       currently fighting.
     * @param user - The Player object that the user is playing the game through.
     * @throws InterruptedException - Allows for the user of program pauses.
    **/
    public void executeAction(ArrayList<Enemy> enemies, Player user)
            throws InterruptedException {
        
        //Variable
        int action;
        
        //Subtract one from the skeleton's downTurns
        downTurns--;
        
        //If the downTurns equal zero, revive the skeleton
        if (downTurns == 0 && down == true)
            revive();
        //If the skeleton is not down...
        else if (!down) {
            //Receive the action number from the chooseAction method
            action = chooseAction();
            
            /*
            If the cloneCount is greater than or equal to 3, don't let the
            skeleton build another skeleton.
            */
            if (cloneCount >= 3) {
                //Looking at the action number to determine the move
                switch (action) {
                    
                    //If 1 or 2, attack the user
                    case 1:
                    case 2:
                        attack(user);
                        break;
                        
                    //If 3 or 4, defend
                    case 3:
                    case 4:
                        defend();
                        break;
                        
                }
            }
            
            /*
            If the cloneCount is not greater than 3, let them do any of their
            moves.
            */
            else {
                //Looking at the action number to determine the move
                switch (action) {
                    
                    //If 1, attack the user
                    case 1:
                        attack(user);
                        break;
                        
                    //If 2 or 3, defend
                    case 2:
                    case 3:
                        defend();
                        break;
                        
                    //If 4, build a new skeleton
                    case 4:
                        clone(enemies, user);
                        break;
                        
                }
            }
        
        }
        
    }
    
    /**
     * Method: revive
     * Description: Revives the skeleton by replenishing their health to half
       of their max health.
    **/
    public void revive() {
        //Output that the skeleton revived
        System.out.println("The " + name + " has revived.");
        
        //Set the skeleton's health to half of its maxHealth
        health = maxHealth / 2;
        
        //Set the skeleton's down boolean to false
        down = false;
    }
    
    /**
     * Method: clone
     * Description: Adds a new skeleton to the enemies ArrayList.
     * @param enemies - The ArrayList of Enemy objects that the user is
       currently fighting.
     * @param user - The Player object that the user is playing the game through.
    **/
    public void clone(ArrayList<Enemy> enemies, Player user) {
        //Output that the skeleton built another one
        System.out.println("The " + name + " has built another skeleton.");
        
        //Add a new Skeleton object to the enemies ArrayList
        enemies.add(new Skeleton(user.getLevel(), cloneCount));
        
        //Increase the cloneCount field by 1
        cloneCount++;
        
        //Loops through the entire enemies ArrayList
        for (int i = 0; i < enemies.size(); i++) 
            /*
            If the current enemy is a skeleton, set their clone count to this
            skeleton's clone count, ensuring that a maximum of 3 skeletons are
            built per floor.
            */
            if (enemies.get(i).isSkeleton()) 
                enemies.get(i).setCloneCount(cloneCount);
    }
    
}
