package rpgbattletower;

/*******************************************************************************
 * Project: RPGBattleTower
 * @author Ryan McKinnon
 * Class: Ghost
 * Description: Holds fields and methods that are specific to the ghost
   enemy type. It has the two special moves of hiding and scaring. Inherits
   from Enemy class.
*******************************************************************************/

import java.util.*;

public class Ghost extends Enemy {
    
    //Fields
    private boolean hidden;
    private int hiddenTurns;
    
    //Ghost Constructor
    Ghost(int l) {
        super("Ghost", 23, 20, 5, l, false, 5);
        hidden = false;
        hiddenTurns = 0;
    }
    
    //Ghost Boss Constructor
    Ghost(String n, int l) {
        super("Ghost Boss", 90, 25, 12, l, false, 7);
        hidden = false;
        hiddenTurns = 0;
    }

    //hidden Getter and Setter
    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    //hiddenTurns Getter and Setter
    public int getHiddenTurns() {
        return hiddenTurns;
    }
    public void setHiddenTurns(int hiddenTurns) {
        this.hiddenTurns = hiddenTurns;
    }
    
    /**
     * Method: executeAction
     * Description: Takes the number received from the superclass' chooseAction
       method and calls upon the correct move based on the number and the
       current state of the Ghost.
     * @param enemies - The ArrayList of Enemy objects that the user is
       currently fighting.
     * @param user - The Player object that the user is playing the game through.
     * @throws InterruptedException - Allows for the user of program pauses.
    **/
    public void executeAction(ArrayList<Enemy> enemies, Player user)
            throws InterruptedException {
        
        //Variable
        int action;
        
        //Subtracting 1 from the hiddenTurns field
        hiddenTurns--;
        //If hiddenTurns is equal to zero and the ghost is hidden...
        if (hiddenTurns == 0 && hidden) {
            //Set the hidden boolean to false
            hidden = false;
            //Output that the ghost is no longer hidden
            System.out.println("The " + name + " is no longer hidden.");
        }
        
        //Receive the action number from the chooseAction method
        action = chooseAction();
        
        /*
        If the ghost is hidden, don't let the hide again or needlessly defend
        when they're already invulnerable.
        */
        if (hidden) {
            //Looking at the action number to determine the move
            switch (action) {
                
                //If 1 or 2, attack the user
                case 1:
                case 2:
                    attack(user);
                    break;
                    
                //If 3 or 4, scare the user
                case 3:
                case 4:
                    scare(user);
                    break;
                    
            }
        }
        
        //If the ghost is not hidden, let them do any of their moves
        else {
            //Looking at the action number to determine the move
            switch(action) {
                
                //If 1, attack the user
                case 1:
                    attack(user);
                    break;
                    
                //If 2, defend
                case 2:
                    defend();
                    break;
                    
                //If 3, hide
                case 3:
                    hide();
                    break;
                    
                //If 4, scare the user
                case 4:
                    scare(user);
                    break;
                    
            }
        }
        
    }
    
    /**
     * Method: hide
     * Description: Makes the ghost hide, and sets its hidden turns to 2
    **/
    public void hide() {
        //Print that the ghost hid
        System.out.println("The " + name + " has turned transparent.");
        
        //Set the hidden boolean to true
        hidden = true;
        
        //Set the hidden turns field to 2
        hiddenTurns = 2;
    }
    
    /**
     * Method: scare
     * Description: scares the user, potentially causing them to miss their turns.
     * @param user - The Player object that the user is playing the game through.
    **/
    public void scare(Player user) {
        //Output that the ghost scared the user
        System.out.println("The " + name + " scared " + user.getName() + ".");
        
        //If the user is already scared, add 1 to their scared turns
        if (user.isScared())
            user.setScaredTurns(user.getScaredTurns() + 1);
        //If the user is not already scared, set their scared turns to 2
        else
            user.setScaredTurns(2);
        
        //Set the user's scared boolean to true
        user.setScared(true);
        
    }
    
}
