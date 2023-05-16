package rpgbattletower;

/*******************************************************************************
 * Project: RPGBattleTower
 * @author Ryan McKinnon
 * Class: Bear
 * Description: Holds fields and methods that are specific to the bear
   enemy type. It has the two special moves of charging their attack and resting
   to heal their HP. Inherits from Enemy class.
*******************************************************************************/

import java.util.*;

public class Bear extends Enemy {
    
    //Field
    private boolean charged;
    
    //Bear Constructor
    Bear(int l) {
        super("Bear", 25, 22, 8, l, false, 5);
        charged = false;
    }
    
    //Bear Boss Constructor
    Bear(String n, int l) {
        super("Bear Boss", 100, 26, 13, l, false, 7);
        charged = false;
    }

    //charged Getter and Setter
    public boolean isCharged() {
        return charged;
    }
    public void setCharged(boolean charged) {
        this.charged = charged;
    }
    
    /**
     * Method: executeAction
     * Description: Takes the number received from the superclass' chooseAction
       method and calls upon the correct move based on the number and the
       current state of the bear.
     * @param enemies - The ArrayList of Enemy objects that the user is
       currently fighting.
     * @param user - The Player object that the user is playing the game through.
     * @throws InterruptedException - Allows for the user of program pauses.
    **/
    public void executeAction(ArrayList<Enemy> enemies, Player user)
            throws InterruptedException {
        
        //Variable
        int action;
        
        //Receive the action number from the chooseAction method
        action = chooseAction();
        
        /*
        If the bear is charged, don't let the bear waste it's charge by
        defending or resting.
        */
        if (charged) {
            //Looking at the action number to determine the move
            switch(action) {
                
                //If 1, 2, or 3, attack the user
                case 1:
                case 2:
                case 3:
                    attack(user);
                    break;
                    
                //If 4, charge their attack again
                case 4:
                    charge();
                    break;
                    
            }
        }
        
        //If the bear's health is full, don't let them needlessly rest.
        else if (health == maxHealth) {
            //Looking at the action number to determine the move
            switch (action) {
                
                //If 1 or 2, attack the user
                case 1:
                case 2:
                    attack(user);
                    break;
                    
                //If 3, defend
                case 3:
                    defend();
                    break;
                    
                //If 4, charge their attack
                case 4:
                    charge();
                    break;
                    
            }
        }
        
        /*
        If the bear is not charged and its health is not full, let them do any
        of their moves.
        */
        else {
            //Looking at the action number to determine the move
            switch (action) {
                
                //if 1, attack the user
                case 1:
                    attack(user);
                    break;
                    
                //If 2, defend
                case 2:
                    defend();
                    break;
                    
                //If 3, charge their attack
                case 3:
                    charge();
                    break;
                    
                //If 4, rest to heal health
                case 4: 
                    rest();
                    break;
                    
            }
        }
        
    }
    
    /**
     * Method: attack
     * Description: Attacks using the superclass' attack method, and then
       checks if the bear is charged, and sets their state back to normal if
       they are.
     * @param user - The Player object that the user is playing the game through.
     * @throws InterruptedException - Allows for program pauses.
    **/
    public void attack(Player user) throws InterruptedException {
        //Attack like normal using the superclass' attack method
        super.attack(user);
        
        /*
        If the bear is charged, set their stength back to their baseStrength,
        and set their charged boolean back to false.
        */
        if (charged) {
            strength = baseStrength;
            charged = false;
        }
            
    }
    
    /**
     * Method: charge
     * Description: Doubles the bear's strength.
    **/
    public void charge() {
        //Print that the bear charged its attack
        System.out.println("The " + name + " charged its attack.");
        
        /*
        Increase the bear's strength by 3/4 of the bear's base strength each
        time the bear charges.
        */
        strength += (int)(baseStrength * 0.75);
        
        //Set the charged boolean to true
        charged = true;
    }
    
    /**
     * Method: rest
     * Description: Replenishes the bear's health by half of its maxHealth.
    **/
    public void rest() {
        
        //Variable
        int amount;
        
        //If the bear is a boss, regenerate a quarter of its max health
        if (name.equals("Bear Boss"))
            amount = maxHealth / 4;
        //If the bear is not a boss, regenerate half of its max health
        else
            amount = maxHealth / 2;
        
        //Print that the bear replenished its health
        System.out.println("The " + name + " rested, replenishing "
                + amount + " health.");
        
        //Add the amount  to the bear's health
        health += amount;
        
        /*
        If the bear's helath is greater than the bear's maxHealth, set it
        equal to its maxHealth.
        */
        if (health > maxHealth)
            health = maxHealth;
    }
    
}
