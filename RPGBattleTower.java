package rpgbattletower;

/*******************************************************************************
 * Project: RPGBattleTower
 * @author Ryan McKinnon
 * Date: June 8th-17th
 * Description: Plays a game where the user battle enemies in a turn based
   format. The user has stats that will change over the course of the game.
   The game runs until the user loses.
*******************************************************************************/

import java.util.*;
import java.io.*;

public class RPGBattleTower {
    
    //Public Scanners
    public static Scanner scanN = new Scanner(System.in);
    public static Scanner scanS = new Scanner(System.in);
    //Public Variables
    public static int userIndex;
    public static int floorNum = 0;
    
    public static void main(String[] args) throws IOException,
            InterruptedException {
        
        //ArrayLists
        ArrayList<String[]> usersStats = new ArrayList<>();
        ArrayList<String[]> usersItems = new ArrayList<>();
        //Variables
        int startChoice;
        int saveChoice;
        boolean ambushed = false;
        boolean save = false;
        //Player object
        Player user;
        
        //Reading the txt file for past users who saved their game.
        readSaveFile(usersStats, usersItems);
        
        //Welcome message
        System.out.println("Welcome to the Battle Tower RPG game!");
        //User input for stating a new game or loading an existing game
        System.out.print("Select one of the following:"
                + "\n1. New Game"
                + "\n2. Load Game"
                + "\nChoice: ");
        //Error handling
        do {
            startChoice = scanN.nextInt();
            if (startChoice < 1 || startChoice > 2)
                System.out.print("That is not an option.\nTry again: ");
        } while (startChoice < 1 || startChoice > 2);
        
        //Going the the correct method based on the user's choice
        if (startChoice == 1)
            user = newGame(usersStats, usersItems);
        else
            user = loadGame(usersStats, usersItems);
        
        /*
        Playing the game until the user dies or they choose to save and quit.
        saveChoice is used to indicate which one happened to break out of the
        game.
        */
        saveChoice = playGame(user);
        
        //If the user chose to save and quit...
        if (saveChoice == 2) {
            //Add all of the user's stats and fields to the usersStats ArrayList
            usersStats.add(new String[]{user.getName(),
                String.valueOf(user.getHealth()),
                String.valueOf(user.getMaxHealth()),
                String.valueOf(user.getStrength()),
                String.valueOf(user.getDefence()),
                String.valueOf(user.getLevel()),
                String.valueOf(user.getSpecialPoints()),
                String.valueOf(user.getMaxSpecialPoints()),
                String.valueOf(user.getDodgeOdds()),
                String.valueOf(user.getMaxInventory()),
                String.valueOf(floorNum)});
            
            //Add the user's inventory to the usersItems ArrayList
            usersItems.add(new String[user.getInventory().size()]);
            for (int i = 0; i < user.getInventory().size(); i++)
                usersItems.get(usersItems.size() - 1)[i]
                        = user.getInventory().get(i);
            
            //Output to simulate the act of saving the game
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                Thread.sleep(500);
            }
            System.out.println("\nGame saved.");
            
        }
        //If the user did not choose to save and quit, meaning they died...
        else {
            //Game over output
            System.out.println("\n\n\t\tGAME OVER");
            Thread.sleep(1000);
            //Write the user's name and floor number to the high scores txt file
            writeHighScores(user.getName());
        }
        
        /*
        Writing the entire contents of the usersStats and usersItems
        ArrayLists to the save states txt file. The file is not appended,
        so the stats of all past users must be re-written. Do this regardless
        of if the user died or chose to save and quit. This is because if the
        user died, the save states txt file has to be updated to no longer
        include them.
        */
        writeSaveStates(usersStats, usersItems);
        
    }
    
    /**
     * Method: readSaveFile
     * Description: Tokenizes and reads the save states txt file, containing
       the stats and inventories of past players who saved their game and
       haven't lost yet. Stores each user's stats in an Array of Strings, which
       is stored in an ArrayList with all the other sets of user's stats. Each
       users inventory is stored in the same way, parallel to the stats
       ArrayList.
     * @param usersStats - An ArrayList of String arrays, each to be filled with
       a user's saved stats.
     * @param usersItems - An ArrayList of String arrays, each to be filled with
       a user's saved inventory contents.
     * @throws IOException - Allows for the user of txt file objects.
    **/
    public static void readSaveFile(ArrayList<String[]> usersStats,
            ArrayList<String[]> usersItems) throws IOException {
        
        //File reader objects
        File myFile = new File("RPGSaveStates.txt");
        Scanner readSaveStates = new Scanner(myFile);
        //Arrays for tokenizing
        String[] statTokens;
        String[] itemTokens;
        
        //Loops while there is more lines of the txt file to be read
        while (readSaveStates.hasNext()) {
            //Tokenizes a line of the txt file, receiving a user's stats
            statTokens = readSaveStates.nextLine().split(", ");
            //Storing this Array of user stats in the usersStats ArrayList
            usersStats.add(statTokens);
            
            /*
            Tokenizes the subsequent line of the txt file, corresponding to
            the above user's inventory.
            */
            itemTokens = readSaveStates.nextLine().split("; ");
            /*
            Storing this Array of user items in the usersItems ArrayList,
            parallel to the usersStats ArrayList.
            */
            usersItems.add(itemTokens);
        }
        
    }
    
    /**
     * Method: newGame
     * Description: Runs code for a user starting a new game.
     * @param usersStats - An ArrayList of past user's saved stats.
     * @param usersItems- An ArrayList of past user's saved items.
     * @return - Returns a new Player object initialized with pre-determined
       stat values.
    **/
    public static Player newGame(ArrayList<String[]> usersStats,
            ArrayList<String[]> usersItems) throws InterruptedException {
        
        //Variables
        String name, ruleChoice;
        
        /*
        Loops while a save file with the entered name exists and the user
        chooses to enter a new name.
        */
        do {
            //User input for their username
            System.out.print("Please input your name: ");
            name = scanS.nextLine();
        
            /*
            Sorting the usersStats ArrayList to be in alphabetical order of
            the usernames. Sorting the usersItems ArrayList to remain parallel
            to usersStats.
            */
            sortUsers(usersStats, usersItems);
            
            /*
            searchUsers is searching for a user with the entered name within
            the usersStats ArrayList. 
            If a name was found AND the userIndex variable is equal to the size
            of the usersStats ArrayList's size, this means that the user chose
            to enter a ne name to start a new game with.
            */
        } while (searchUsers(usersStats, name) && userIndex == usersStats.size());
        
        /*
        If the userIndex variable is not equal to the usersStats ArrayList's
        size, meaning userIndex corresponds to an already existing index in
        usersStats, meaning that the user chose to continue from that save file,
        jump to the loadUser method instead of starting a new game, and instead
        of taking them to loadGame, since they already chose their save file.
        */
        if (userIndex != usersStats.size())
            return loadUser(usersStats, usersItems);
        
        //User input for whether they would like to view the rules of the game
        System.out.print("Would you like to view the rules?"
                + "\n(yes/no): ");
        //Error handling
        do {
            ruleChoice = scanS.nextLine();
            if (!(ruleChoice.equalsIgnoreCase("yes")
                    || ruleChoice.equalsIgnoreCase("no")))
                System.out.print("Please type yes or no: ");
        } while (!(ruleChoice.equalsIgnoreCase("yes")
                || ruleChoice.equalsIgnoreCase("no")));
        
        //If the user chose to view the rules, show them the rules.
        if (ruleChoice.equalsIgnoreCase("yes")) {
            System.out.println("\nThis is an endless, turn-based RPG game where"
                    + "\nyou play as a knight and will climb a tower filled with"
                    + "\nenemies. Each floor will be a random battle, and you"
                    + "\nwill have to strategically choose between attacking,"
                    + "\ndefending, and using items to try to get as high in the"
                    + "\ntower as possible. You will climb a floor of the tower"
                    + "\nafter every battle. Every 5 floors you will level up,"
                    + "\nreceiving stat boosts and an extra upgrade of your"
                    + "\nchoice. The enemies will also get stronger as you"
                    + "\nprogress. You can save and quit after every floor."
                    + "\nEvery 5 floors you will encounter a boss battle,"
                    + "\nwhich is one enemy with larger stats, meaning that"
                    + "\nit will be more difficult to defeat. Each enemy"
                    + "\ntype has their own unique moves that you can learn"
                    + "\nabout throughout your game.");
            Thread.sleep(10000);
        }
        
        //Output to wish the user luck
        System.out.println("\nGood luck, have fun, and climb as high as you can!");
        
        /*
        Return a Player object with the name being the only field relying on
        the user's input.
        */
        return new Player(name);
        
    }
    
    /**
     * Method: loadGame
     * Description: Uses the data from the save states txt file to output the
       save files for the user to choose from, and creates a Player object
       based on the stats saved in the user's chosen save file.
     * @param usersStats - An ArrayList of past user's saved stats.
     * @param usersItems- An ArrayList of past user's saved items.
     * @return - A Player object initialized with the data from the user's
       chosen save file. Data received from the save stats txt file.
    **/
    public static Player loadGame(ArrayList<String[]> usersStats,
            ArrayList<String[]> usersItems) throws InterruptedException {
        
        //Variables
        int userChoice;
        String name;
        int health, maxHealth, strength, defence, level, specialPoints,
                maxSpecialPoints, dodgeOdds, maxInventory;
        ArrayList<String> inventory = new ArrayList<>();
        
        /*
        If there are no perviously saved games, redirect the user to start a
        new game because there are no games to load. Return the Player object
        created in that method instead.
        */
        if (usersStats.isEmpty()) {
            System.out.println("There are no save files to load. You will start"
                    + " a new game.");
            return newGame(usersStats, usersItems);
        }
        
        /*
        Sorting the usersStats ArrayList to be in alphabetical order of the
        usernames. Sorting the usersItems ArrayList to remain parallel to
        usersStats.
        */
        sortUsers(usersStats, usersItems);
        
        //User input for which save file they would like to load.
        System.out.println("Please select your name:");
        //Printing a menu of all usernames received from the txt file
        for (int i = 0; i < usersStats.size(); i++)
            System.out.println((i+1) + ". " + usersStats.get(i)[0]);
            
        System.out.print("Choice: ");
        //Error handling
        do {
            userChoice = scanN.nextInt();
            if (userChoice < 1 || userChoice > usersStats.size())
                System.out.print("That is not an option.\nTry again: ");
        } while (userChoice < 1 || userChoice > usersStats.size());
        
        /*
        Ther user's stats index in the usersStats and usersItems ArrayLists is
        their choice minus 1.
        */
        userIndex = userChoice - 1;
        
        /*
        Takes the info from the user's chosen save file and initialzes a Player
        object using this info.
        */
        return loadUser(usersStats, usersItems);
        
    }
    
    /**
     * Method: loadUser
     * Description: Takes the information from the usersStats and usersItems
       ArrayLists at the user's chosen index and creates a Player object using
       this information.
     * @param usersStats - An ArrayList of past user's saved stats.
     * @param usersItems- An ArrayList of past user's saved items.
     * @return - A Player object initialized with the data from the user's
       chosen save file. Data received from the save stats txt file.
    **/
    public static Player loadUser(ArrayList<String[]> usersStats,
            ArrayList<String[]> usersItems) {
        
        //ArrayList for inventory
        ArrayList<String> inventory = new ArrayList<>();
        
        /*
        Variables for each field of the Player object to be initialized. Using
        the infromation from the usersStats and usersItems ArrayLists and the
        user's chosen index to receive the correct data.
        */
        String name = usersStats.get(userIndex)[0];
        int health = Integer.parseInt(usersStats.get(userIndex)[1]);
        int maxHealth = Integer.parseInt(usersStats.get(userIndex)[2]);
        int strength = Integer.parseInt(usersStats.get(userIndex)[3]);
        int defence = Integer.parseInt(usersStats.get(userIndex)[4]);
        int level = Integer.parseInt(usersStats.get(userIndex)[5]);
        int specialPoints = Integer.parseInt(usersStats.get(userIndex)[6]);
        int maxSpecialPoints = Integer.parseInt(usersStats.get(userIndex)[7]);
        int dodgeOdds = Integer.parseInt(usersStats.get(userIndex)[8]);
        int maxInventory = Integer.parseInt(usersStats.get(userIndex)[9]);
        /*
        Determining the floor number from the txt file's information as well,
        so that the user starts on the correct floor.
        */
        floorNum = Integer.parseInt(usersStats.get(userIndex)[10]);
        /*
        Filling the inventory ArrayList with the contents of the usersItems
        ArrayList at the user's chosen index.
        */
        for (int i = 0; i < usersItems.get(userIndex).length; i++)
            inventory.add(usersItems.get(userIndex)[i]);
        
        /*
        Remove the user's loaded save file from the txt file. This is because
        the user's stats are updated when playing, updated, and re-added to the
        txt file if the user chose to save and quit again. If the user died,
        then the user's stats are already removed.
        */
        usersStats.remove(userIndex);
        usersItems.remove(userIndex);
        
        //Message welcoming the user back
        System.out.println("Welcome back " + name + "!");
        System.out.println("Good luck have fun.");
        
        /*
        Returning a Player object initialized with all the info from the
        usersStats and usersItems ArrayLists at the user's chosen index.
        */
        return new Player(name, health, maxHealth, strength, defence, level,
                specialPoints, maxSpecialPoints, dodgeOdds, maxInventory,
                inventory);
        
    }
    
    /**
     * Method: sortUsers
     * Description: Sorts the usersStats ArrayList alphabetically based on the
       usernames. Also sorts the usersItems whenever the usersStats is sorted
       to keep them parallel. Uses bubble sort.
     * @param usersStats - An ArrayList of past user's saved stats.
     * @param usersItems- An ArrayList of past user's saved items.
    **/
    public static void sortUsers(ArrayList<String[]> usersStats,
            ArrayList<String[]> usersItems) {
        
        //Variables
        boolean isMixed;
        String[] dummy;
        
        /*
        Bubble sorting, comparing the usersStats ArrayList's String Array at
        i's first element, which is the name. Moving the entire String Array's
        position in the ArrayList if out of alphabetical order.
        */
        do {
            isMixed = false;
            for (int i = 0; i < usersStats.size() - 1; i++) {
                if (usersStats.get(i)[0].compareToIgnoreCase
                        (usersStats.get(i+1)[0]) > 0) {
                    
                    dummy = usersStats.get(i);
                    usersStats.set(i, usersStats.get(i+1));
                    usersStats.set(i+1, dummy);
                    
                    dummy = usersItems.get(i);
                    usersItems.set(i, usersItems.get(i+1));
                    usersItems.set(i+1, dummy);
                    
                    isMixed = true;
                    
                }
            }
        } while (isMixed);
        
    }
    
    /**
     * Method: searchUsers
     * Description: Searches the usersStats ArrayList of user's saves for a
       specific name. Uses binary search.
     * @param usersStats - An ArrayList of past user's saved stats.
     * @param name - The name that is being searched for.
     * @return - Whether or not the name was found in the usersStats ArrayList.
    **/
    public static boolean searchUsers(ArrayList<String[]> usersStats,
            String name) {
        
        //Variables
        int choice;
        int first = 0;
        int last = usersStats.size() - 1;
        int middle = -1;
        boolean found = false;
        
        //Binary searching the usersStats ArrayList for the name in the parameters
        while (!found && first <= last) {
            
            middle = (first + last) / 2;
            
            if (usersStats.get(middle)[0].equals(name))
                found = true;
            else if (usersStats.get(middle)[0].compareToIgnoreCase(name) > 0)
                last = middle - 1;
            else if (usersStats.get(middle)[0].compareToIgnoreCase(name) < 0)
                first = middle + 1;
            
        }
        
        //If the name was found...
        if (found) {
            /*
            Let the user know that the name they entered was found in the save
            stats txt file, and ask what they would like to do as an
            alternative option.
            */
            System.out.print("\nThis name is in the records already as a save"
                    + " file."
                    + "\nWould you like to:"
                    + "\n1. Start from this save file?"
                    + "\n2. Type a new name?"
                    + "\nChoice: ");
            //Error handling
            do {
                choice = scanN.nextInt();
                if (choice < 1 || choice > 2)
                    System.out.print("That is not an option.\nTry again: ");
            } while (choice < 1 || choice > 2);
            
            /*
            If the user chose to start from the save that was found, set the
            userIndex to middle. Middle being the index where the name was
            found.
            */
            if (choice == 1)
                userIndex = middle;
            /*
            If the user chose to type a new name, set the user Index to the
            size of the usersStats ArrayList, meaning that, when it is added
            to the ArrayList later, that will be it's index.*/
            else
                userIndex = usersStats.size();
        }
        /*
        If the name was not found, set the user Index to the size of the
        usersStats ArrayList, meaning that, when it is added to the ArrayList
        later, that will be it's index.
        */
        else
            userIndex = usersStats.size();
        
        //Return whether or not the name was found
        return found;
        
    }
    
    /**
     * Method: playGame
     * Description: Loops the main gameplay of the game while the user lives
       and doesn't choose to save and quit. Contains the user's turn and the
       enemies' turns. Also contains the spawning of enemies and bosses, and
       leveling up the user. Also contains the random appearances of locked
       doors.
     * @param user - The Player object that the user operates through to play
       the game.
     * @return - The user's choice for if they chose to save and quit or not.
     * @throws InterruptedException - Allows for the use of program pauses.
    **/
    public static int playGame(Player user) throws InterruptedException {
        
        //Variables
        boolean ambushed = false;
        int saveQuit = 0;
        int numEnemies;
        int rndDoor, keyIndex;
        int rndDoorPrize;
        String unlock;
        String item;
        //ArrayList of Enemy objects
        ArrayList<Enemy> enemies = new ArrayList<>();
        
        /*
        Loops while the user is alive and the user did not choose to save and
        quit.
        */
        do {
            /*
            If the user was not ambushed, increase the floor number and print
            it out.
            */
            if (!ambushed) {
                floorNum++;
                System.out.println("\nFloor #" + floorNum);
            }

            /*
            If the floor number is not a multiple of 5 or if the user was
            ambushed, spawn in a random number of random enemies.
            */
            if (floorNum % 5 != 0 || ambushed) {
                /*
                Random number between 1 & 3 to determine how many enemies will
                be on this floor.
                */
                numEnemies = (int)(1 + Math.random() * (3 + 1 - 1));

                //Loop for the number of enemies there will be
                for (int i = 0; i < numEnemies; i++) {
                    /*
                    Getting a random number between 1 & 3 to determine which
                    type of enemy will be spawned.
                    */
                    switch ((int)(1 + Math.random() * (3 + 1 - 1))) {
                        //If the random number is 1, spawn a Skeleton Enemy
                        case 1:
                            enemies.add(new Skeleton(user.getLevel()));
                            break;
                        //If the random number is 2, spawn a Bear Enemy
                        case 2:
                            enemies.add(new Bear(user.getLevel()));
                            break;
                        //If the random number is 3, spawn a Ghost Enemy
                        case 3:
                            enemies.add(new Ghost(user.getLevel()));
                            break;
                    }
                }
            }
            /*
            If the floor number is a multiple of 5 and the user was not
            ambushed...
            */
            else {
                /*
                Generate a random number to determine what kind of boss will
                be spawned. Only spawn one boss.
                */
                switch ((int)(1 + Math.random() * (3 + 1 - 1))) {
                    //If the random number is 1, spawn a Skeleton Boss
                    case 1:
                        enemies.add(new Skeleton("Boss", user.getLevel()));
                        break;
                    //If the random number is 2, spawn a Bear Boss
                    case 2:
                        enemies.add(new Bear("Boss", user.getLevel()));
                        break;
                    //If the random number is 3, spawn a Ghost Boss
                    case 3:
                        enemies.add(new Ghost("Boss", user.getLevel()));
                        break;
                }
            }

            //Loops while the user is alive and the enemies ArrayList is not empty.
            do {

                //Run the user's turn
                user.menu(enemies);

                /*
                Record the size of the enemies ArrayList before entering the
                loop. This is because, if a new skeleton is built, the size of
                the ArrayList will be increased, but this new skeleton should
                not take it's first turn until the next turn, so that the player
                can plan for its turn.
                */
                numEnemies = enemies.size();

                /*
                Loops for the size of the enemies ArrayList, to run each
                enemy's turn.*/
                for (int i = 0; i < numEnemies; i++) {
                    System.out.println();
                    enemies.get(i).executeAction(enemies, user);
                    /*
                    If the user dies after one of the enemy's turns, don't
                    allow the rest of the enemies to take their turn. Just
                    exit the loop early.
                    */
                    if (!user.isAlive())
                        break;
                    /*
                    Only pause the program after an enemy's turn if the enemy
                    is not down. This is because enemies that are down do not
                    take their turn.
                    */
                    if (!enemies.get(i).isDown())
                        Thread.sleep(2000);
                }
                
            } while (user.isAlive() && !enemies.isEmpty());

            //If the user is alive...
            if (user.isAlive()) {
                /*
                If the user was not ambushed, congratulate the user on clearing
                the floor.
                */
                if (!ambushed)
                    System.out.println("\n\nCongratulations! You cleared floor #"
                            + floorNum + "!");
                /*
                If the user was ambushed, congratulate the user on surviving
                the ambush, and increase their stats by half a level, as a
                reward for survivng the ambush after not expecting it. 1
                represents a half a level's worth of stat increases.
                */
                else {
                    System.out.println("\n\nCongratulations on surviving the"
                            + " ambush!");
                    Thread.sleep(1000);
                    user.increaseStats(1);
                }
                
                /*
                If the floor number if a multiple of 5 and the user was not
                ambushed...
                */
                if (floorNum % 5 == 0 && !ambushed) {
                    /*
                    Congratulate the user on defeating the boss, and state that
                    they and the enemies have levelled up.
                    */
                    System.out.println("\nYou defeated the boss and levelled up!");
                    System.out.println("The enemies going forward will be tougher"
                            + " than before, but so will you!");
                    Thread.sleep(1000);
                    /*
                    Increase the user's stats by a full level. 2 represents a
                    full level's worth of stat increases.
                    */
                    user.increaseStats(2);
                }

                /*
                Remove any and all stat boosts that the user may have had after
                they beat the floor.
                */
                user.setStrength(user.getBaseStrength());
                user.setsBoostTurns(0);
                user.setDefence(user.getBaseDefence());
                user.setdBoostTurns(0);
                user.setDefending(false);
                user.setScared(false);
                user.setScaredTurns(0);

                //If the user was not ambushed...
                if (!ambushed) {
                    /*
                    Generate a random number between 1 & 20 to determine if a
                    locked door will appear for the user to unlock.
                    */
                    rndDoor = (int)(1 + Math.random() * (10 + 1 - 1));
                    //If the random number is 1...
                    if (rndDoor == 1) {
                        /*
                        Output that a locked door was generated, and ask if the
                        user will unlock the door.
                        */
                        System.out.print("\nAs " + user.getName() + " is heading"
                                + " to the next floor,\nthey notice a locked door."
                                + "\nUnlock the door? (yes/no): ");
                        //Error handling
                        do {
                            unlock = scanS.nextLine();
                            if (!(unlock.equalsIgnoreCase("yes")
                                    || unlock.equalsIgnoreCase("no")))
                                System.out.print("Please type yes or no: ");
                        } while (!(unlock.equalsIgnoreCase("yes")
                                || unlock.equalsIgnoreCase("no")));
                        
                        //If the user chose to unlock the door...
                        if (unlock.equalsIgnoreCase("yes")) {
                            /*
                            Binary search the user's inventory to see if they
                            have a key and receive its index in the user's 
                            inventory.
                            */
                            keyIndex = user.checkKey();
                            /*
                            If the keyIndex is -1, the user does not have a key,
                            so don't allow them to unlock the door.
                            */
                            if (keyIndex == -1)
                                System.out.println("\nUnfortunately, you do not"
                                        + " have a key to unlock the door.");
                            //If the user has a key...
                            else {
                                //Remove the key from the user's inventory
                                user.getInventory().remove(keyIndex);
                                System.out.print("\nIn the locked door, ");
                                //Get a random number between 1 & 5
                                switch((int)(1 + Math.random() * (5 + 1 - 1))) {
                                    /*
                                    If the random number is 1 or 2, provide the
                                    user with an upgrade choice.
                                    */
                                    case 1:
                                    case 2:
                                        System.out.println("you received a bonus"
                                                + " upgrade!");
                                        user.upgrade();
                                        break;
                                    /*
                                    If the random number is 3 or 4, make the
                                    user receive four random items, and check
                                    if the user's inventory is full after each
                                    item.
                                    */
                                    case 3:
                                    case 4:
                                        System.out.println("you received 4"
                                                + " items:");
                                        for (int i = 0; i < 4; i++) {
                                            item = user.determineItem();
                                            System.out.println((i+1) + ") "
                                                    + item);
                                            user.getInventory().add(item);
                                            user.checkFullInventory();
                                        }
                                        break;
                                    /*
                                    If the random number is 5, make the user be
                                    ambushed by enemies, and set the ambushed 
                                    boolean to true.
                                    */
                                    case 5:
                                        System.out.println("you were ambushed by"
                                                + " enemies!");
                                        ambushed = true;
                                }
                                /*
                                If the user was ambushed, continue back to the
                                top of the loop that is looping the main game.
                                This uses the same code as a regular floor, but
                                skips certain things using the ambushed boolean
                                like increasing the floor number.
                                */
                                if (ambushed)
                                    continue;
                            }
                        }

                    }
                }
                /*
                If the user was already ambushed, set the ambushed boolean back
                to false.
                */
                else
                    ambushed = false;
                
                Thread.sleep(1000);
                
                /*
                User input for if they would like to save and quit or continue
                playing.
                */
                System.out.print("\n\nWhat will you do:"
                        + "\n1. Onward to floor #" + (floorNum+1) + "!"
                        + "\n2. Save and quit"
                        + "\nChoice: ");
                //Error handling
                do {
                    saveQuit = scanN.nextInt();
                    if (saveQuit < 1 || saveQuit > 2)
                        System.out.print("That is not an option.\nTry again: ");
                } while (saveQuit < 1 || saveQuit > 2);
                
            }
            
        } while (user.isAlive() && saveQuit != 2);
        
        //Return whether or not the user chose to save and quit
        return saveQuit;
        
    }
    
    /**
     * Method: writeSaveStates
     * Description: Writes every users' stats and items to the save states
       txt file.
     * @param usersStats - An ArrayList of past user's saved stats.
     * @param usersItems- An ArrayList of past user's saved items.
     * @throws IOException - Allows for the use of txt file objects.
    **/
    public static void writeSaveStates(ArrayList<String[]> usersStats,
            ArrayList<String[]> usersItems) throws IOException {
        
        //File writer objects
        FileWriter fWrite = new FileWriter("RPGSaveStates.txt", false);
        PrintWriter outFile = new PrintWriter(fWrite);
        
        /*
        Loops for the size of the usersStats ArrayList's size, to output every
        user's stats and items.
        */
        for (int i = 0; i < usersStats.size(); i++) {
            /*
            Loops for the length of the String array of stats inside the
            userStats ArrayList's current index. Prints each element of the
            String array of stats with a comma and a space after so that it is
            properly formatted to be tokenized later.
            */ 
            for (int j = 0; j < usersStats.get(i).length; j++)
                outFile.print(usersStats.get(i)[j] + ", ");
            
            outFile.println();
            
            /*
            If there are no items in this index of the usersItems ArrayList,
            still output one semi-colon and a space to prevent errors when
            tokenizing.
            */
            if (usersItems.get(i).length == 0)
                outFile.print("; ");
            
            /*
            Loops for the length of the String array of items inside the
            userItems ArrayList's current index. Prints each element of the
            String array of items with a semi-colon and a space after so that it
            is properly formatted to be tokenized later.
            */ 
            for (int j = 0; j < usersItems.get(i).length; j++)
                outFile.print(usersItems.get(i)[j] + "; ");
            
            outFile.println();
        }
        
        /*
        Closing the Printwriter so that the info is actually written to the txt
        file.
        */
        outFile.close();
        
    }
    
    /**
     * Method: writeHighScores
     * Description: Reads the high scores txt file, adds the user's score to it,
       prints out the top 5 scorers, and re-writes all the user's scores to the
       high scores txt file.
     * @param userName - The user's name.
     * @throws IOException - Allows for the use of txt file objects.
    **/
    public static void writeHighScores(String userName) throws IOException {
        
        //File reader objets
        File myFile = new File("RPGHighScores.txt");
        Scanner readHighScoresFile = new Scanner(myFile);
        //File writer objects
        FileWriter fWrite;
        PrintWriter outFile;
        //ArrayLists to hold the high scores txt file information
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> floors = new ArrayList<>();
        //Tokens Array
        String[] tokens;
        //Variable
        int numUsers;
        
        /*
        Loops while the high scores txt file has more lines to read. Reads the
        high scores txt file using tokenizing.
        */
        while (readHighScoresFile.hasNext()) {
            tokens = readHighScoresFile.nextLine().split(", ");
            names.add(tokens[0]);
            floors.add(Integer.parseInt(tokens[1]));
        }
        
        //Add the current user's name and floor number to the respective ArrayLists
        names.add(userName);
        floors.add(floorNum);
        
        //Sort the high socres ArrayLists by floor number, from greatest to least.
        sortHighScores(names, floors);
        
        /*
        If there are less than five users in the high scores txt file, set the
        numUsers variable to the size of the names ArrayList.
        */
        if (names.size() < 5) 
            numUsers = names.size();
        /*
        If there are at least 5 users in the high scores txt file, set the
        numUsers variable to five.
        */
        else
            numUsers = 5;
        
        /*
        Output the top 5 users, or the amount of users that are in the txt
        file if there aren't 5.
        */
        System.out.println("\nHighest Scores:");
        for (int i = 0; i < numUsers; i++)
            System.out.println((i+1) + ") " + names.get(i) + ": Floor #"
                    + floors.get(i));
        
        /*
        Setting up the file writer objects after the file has been read so that
        the contents aren't deleted before they are read.
        */
        fWrite = new FileWriter("RPGHighScores.txt", false);
        outFile = new PrintWriter(fWrite);
        
        /*
        Writing, to the high scores txt file, each user's name and floor
        separated by a comma and a space, properly set up to be tokenized later.
        */
        for (int i = 0; i < names.size(); i++) 
            outFile.println(names.get(i) + ", " + floors.get(i));
        
        /*
        Closing the Printwriter so that the info is actually written to the txt
        file.
        */
        outFile.close();
        
    }
    
    /**
     * Method: sortHighScores
     * Description: Sorts the floors ArrayList, its info received from the high
       scores txt file, from greatest to least. Also shifting the names
       ArrayList whenever the floors ArrayList is switch to keep them parallel.
     * @param names - An ArrayList of all the names of users who have died and
       had their high scores recorded.
     * @param floors - An ArrayList of all the floors(High scores) of the users
       who have died and had their high scores recorded.
    **/
    public static void sortHighScores(ArrayList<String> names,
            ArrayList<Integer> floors) {
        
        //Variables
        boolean isMixed;
        int dummyN;
        String dummyS;
        
        /*
        Sorting the floors ArrayList from greatest to least, and keeping the
        names ArrayList parallel, using bubble sort.
        */
        do {
            isMixed = false;
            for (int i = 0; i < floors.size() - 1; i++) {
                if (floors.get(i) < floors.get(i+1)) {
                    
                    dummyN = floors.get(i);
                    floors.set(i, floors.get(i+1));
                    floors.set(i+1, dummyN);
                    
                    dummyS = names.get(i);
                    names.set(i, names.get(i+1));
                    names.set(i+1, dummyS);
                    
                    isMixed = true;
                    
                }
            }
        } while (isMixed);
        
    }
    
}
