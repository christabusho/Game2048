/*  Name: Ange Christa Dushime
*  PennKey: dushime
*  Recitation: 202
*  Execution: n/a
*
* A class that represents the board where 2048 takes place.
* It keeps track of the moves of all the tiles and receives the 
* player's input to control the moves.
*/

public class Board {
    
    //***data fields***

    // a 2D array of the tiles representing all the tiles drawn on the board
    private Tile[][] tiles; 
    // a variable that keeps of the number of moves made throughout the game
    private int moves;
    
    //***constructor***
    
    /*
    * Initializes the number of moves to 0 and the array to a 4*4 2D array and
    * assigns x and y positions to the tiles in the array. 
    */
    public Board() {
        this.moves = 0;
        double yPos = 0.86;
        this.tiles = new Tile[4][4];
        // Assign every tile in the array with a specific position on the board
        for (int row = 0; row < 4; row++) {
            double xPos = 0.14;
            for (int col = 0; col < 4; col++) {
                this.tiles[row][col] = new Tile(xPos, yPos);
                xPos += 0.24;
            }
            yPos -= 0.24;
        }
        // generating 2 random tiles to start with on random positions
        for (int j = 0; j < 2; j++) {
            int randRow = getRandomPosition();
            int randCol = getRandomPosition();
            
            if (tiles[randRow][randCol].isEmpty()) {
                tiles[randRow][randCol].setNumber(getRandomNumber());
            } // just in case it generates the same random position twice
            else {
                // Avoiding going out of the array
                if (randRow < 3 || randCol < 3) {
                    randRow += 1;
                    randCol += 1;
                } 
                else {
                    randRow -= 1;
                    randCol -= 1;
                }
                tiles[randRow][randCol].setNumber(getRandomNumber());
            }
        }
    }

    /**
     * Inputs: None
     * Outputs: returns no value.
     * Description: draws the board with all the tiles it contains.
    */
    public void draw() {
        // clear with the background color
        PennDraw.clear(200, 175, 153);
        // drawing the 16 tiles
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                tiles[row][col].draw();
            }
        }
    }
    
    /**
     * Inputs: None
     * Outputs: a boolean
     * Description: checks if there is an empty space on the board, which means 
     * if there is any tile containing the number 0. It returns true if it finds 
     * it and false otherwise.
    */
    private boolean emptySpace() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getNumber() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Inputs: None 
     * Outputs: returns an integer 
     * Description: It generates/ returns a random integer from 0 to 4.
    */
    private int getRandomPosition() {
        return (int) (4 * Math.random());
    }
    
    /**
     * Inputs: None 
     * Outputs: returns an integer : 2 or 4
     * Description: It returns a random integer between 2 and 4. 2 has more odds of 
     * being generated than 4.
    */
    private int getRandomNumber() {
        if (Math.random() < 0.9) {
            return 2;
        } 
        else {
            return 4;
        }
    }
    
    /**
     * Inputs: None 
     * Outputs: returns no value 
     * Description: If there is an empty space, replace the empty tile with a 
     * randomly generated tile containing 2 or 4. 
    */
    private void addRandom() {
        // check if there is an empty tile
        if (this.emptySpace()) {
            // a variable that tracks if an empty tile was found 
            boolean hasfound = false;
            while (!hasfound) {
                // get a random position of a tile
                int randRow = getRandomPosition();
                int randCol = getRandomPosition();
                // check if that tile is empty
                if (tiles[randRow][randCol].isEmpty()) {
                    //then, add a new tile
                    hasfound = true;
                    tiles[randRow][randCol].setNumber(getRandomNumber());
                }
                //if not, then generate another position
            }
        }
    }
    
    /**
     * Inputs: a char, the key pressed 
     * Outputs: returns the value 
     * Description: updates the board by the calling its helper methods according
     * to the input key. (More details in the helper methods's headers)
    */
    public void update(char key) {
        if (key == 'W' || key == 'w') {
            moveUp();
            
        }
        else if (key == 'S' || key == 's') {
            moveDown();
            
        }
        else if (key == 'A' || key == 'a') {
            moveleft();
            
        }
        else if (key == 'D' || key == 'd') {
            moveRight();
        }
        else {
            return;
        }
    }
    
    /**
     * Inputs: None
     * Outputs: returns no value 
     * Description: It first looks for the farthest empty tile above each tile on 
     * the board, then makes it move to that tile by changing the numbers they hold.
     * It also handles merging two tiles with the same number and moving the ones 
     * below the merged ones. It increases the moves when there was a move.
    */
    private void moveUp() {
        // create a variable that keeps track if there was a move
        boolean moved = false;
        for (int col = 0; col < tiles[0].length; col++) {
            // create a variable that tracks the index of the farthest unoccupied
            // tile
            int empty = 0;
            for (int row = 0; row < tiles.length; row++) {
                if (tiles[row][col].getNumber() != 0) { // if a tile is not empty
                    // then set the number of the empty tile to the one moving 
                    tiles[empty][col].setNumber(tiles[row][col].getNumber());
                    if (row != empty) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                    }
                    empty++;
                }
            }
        }
        
        // handling crashing
        boolean merged = false;
        for (int col = 0; col < tiles[0].length; col++) {
            for (int row = 1; row < tiles.length; row++) {
                if (tiles[row][col].canCrash(tiles[row - 1][col])) {
                    tiles[row - 1][col].combine(tiles[row][col]);
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                        merged = true;
                    } 
                }
            }
        }
        
        //moving other tiles up that can move after the crashing
        if (merged) {
            for (int col = 0; col < tiles[0].length; col++) {
            // create a variable that tracks the index of the farthest unoccupied
            // tile
            int empty = 0;
                for (int row = 0; row < tiles.length; row++) {
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[empty][col].setNumber(tiles[row][col].getNumber());
                        if (row != empty) {
                            tiles[row][col].setNumber(0);
                        }
                        empty++;
                    }
                }
            }
        }
        
        //if there is a move, then add a random tile
        if (moved) {
            moves++;
            addRandom();
        }
    }
    
    /**
     * Inputs: None
     * Outputs: returns no value 
     * Description: It first looks for the farthest empty tile below each tile on 
     * the board, then makes it move to that tile by changing the numbers they hold.
     * It handles merging two tiles with the same number and moving the ones 
     * above the merged ones. It also increases the moves when there was a move.
    */
    private void moveDown() {
        // create a variable that keeps track if there was a move or not
        boolean moved = false;
        for (int col = 0; col < tiles[0].length; col++) {
            // create a variable that keeps track of the index of the farthest
            // unoccupied tile
            int empty = 3;
            for (int row = tiles.length - 1; row >= 0; row--) {
                if (tiles[row][col].getNumber() != 0) {
                    tiles[empty][col].setNumber(tiles[row][col].getNumber());
                    if (row != empty) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                    }
                    empty--;
                }
            }
        }
        
        //handling crashing
        boolean merged = false;
        for (int col = 0; col < tiles[0].length; col++) {
            for (int row = tiles.length - 2; row >= 0; row--) {
                if (tiles[row][col].canCrash(tiles[row + 1][col])) {
                    tiles[row + 1][col].combine(tiles[row][col]);
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                        merged = true;
                    }
                }
            }
        }
        
        //moving down other tiles that can after the crashing
        if (merged) {
            for (int col = 0; col < tiles[0].length; col++) {
            // create a variable that keeps track of the index of the farthest
            // unoccupied tile
                int empty = 3;
                for (int row = tiles.length - 1; row >= 0; row--) {
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[empty][col].setNumber(tiles[row][col].getNumber());
                        if (row != empty) {
                            tiles[row][col].setNumber(0);
                        }
                        empty--;
                    }
                }
            }
        }
        
        // if there was a move, then add a random tile
        if (moved) {
            moves++;
            addRandom();
        }
    }
    
    /**
     * Inputs: None
     * Outputs: returns no value 
     * Description: It first looks for the farthest empty tile on the left each   
     * tile on the board, then makes it move to that tile by changing the  
     * numbers they hold. It handles merging two tiles with the same number and  
     * moving the ones on the right the merged ones. It also increases the 
     * moves when there was a move.
    */
    private void moveleft() {
        // create a variable that keeps track if there was a move
        boolean moved = false;
        // moving a column to the left
        for (int row = 0; row < tiles.length; row++) {
            // create a variable that keeps track of the index of the farthest
            // unoccupied tile
            int empty = 0;
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col].getNumber() != 0) {
                    tiles[row][empty].setNumber(tiles[row][col].getNumber());
                    if (col != empty) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                    }
                    empty++;
                }
            }
        }
        
        // handling crashing
        boolean merged = false;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 1; col < tiles[row].length; col++) {
                if (tiles[row][col].canCrash(tiles[row][col - 1])) {
                    tiles[row][col - 1].combine(tiles[row][col]);
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                        merged = true;
                    }
                }
            }
        }
        
        //moving left other tiles that can after crashing
        if (merged) {
            for (int row = 0; row < tiles.length; row++) {
            // create a variable that keeps track of the index of the farthest
            // unoccupied tile
                int empty = 0;
                for (int col = 0; col < tiles[row].length; col++) {
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[row][empty].setNumber(tiles[row][col].getNumber());
                        if (col != empty) {
                        tiles[row][col].setNumber(0);
                        }
                        empty++;
                    }
                }
            }
        }
        
        if (moved) {
            moves++;
            addRandom();
        }
    }
    
    /**
     * Inputs: None
     * Outputs: returns no value 
     * Description: It first looks for the farthest empty tile on the right each   
     * tile on the board, then makes it move to that tile by changing the  
     * numbers they hold. It handles merging two tiles with the same number and  
     * moving the ones on the left the merged ones. It also increases the 
     * moves when there was a move.
    */
    private void moveRight() {
        // create a variable that keeps track if there was a move or not
        boolean moved = false;
        for (int row = 0; row < tiles.length; row++) {
            // create a variable that keeps track of the index of the farthest
            // unoccupied tile
            int empty = 3;
            for (int col = tiles[row].length - 1; col >= 0; col--) {
                if (tiles[row][col].getNumber() != 0) {
                    tiles[row][empty].setNumber(tiles[row][col].getNumber());
                    if (col != empty) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                    }
                    empty--;
                }
            }
        }
        
        //handling crashing
        boolean merged = false;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = tiles[row].length - 2; col >= 0; col--) {
                if (tiles[row][col].canCrash(tiles[row][col + 1])) {
                    tiles[row][col + 1].combine(tiles[row][col]);
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[row][col].setNumber(0);
                        moved = true;
                        merged = true;
                    }
                }
            }
        }
        
        // moving right other tiles that can after the crashing
        if (merged) {
            for (int row = 0; row < tiles.length; row++) {
            // create a variable that keeps track of the index of the farthest
            // unoccupied tile
                int empty = 3;
                for (int col = tiles[row].length - 1; col >= 0; col--) {
                    if (tiles[row][col].getNumber() != 0) {
                        tiles[row][empty].setNumber(tiles[row][col].getNumber());
                        if (col != empty) {
                            tiles[row][col].setNumber(0);
                        }
                        empty--;
                    }
                }
            }
        }
        
        // if there was a move, then add a random tile
        if (moved) {
            moves++;
            addRandom();
        }
    }
    
    /**
     * Inputs: None
     * Outputs: returns a boolean
     * Description: checks if there is any tile containing the number 2048. If so, 
     * returns true and false otherwise.
    */
    private boolean didPlayerWin() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col].getNumber() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Inputs: None 
     * Outputs: returns a boolean
     * Description: checks if the board is full and if there is a possible move.
    */
    private boolean didPlayerLose() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col].isEmpty()) {
                    return false;
                }
            }
        }
        // then it is full, check if there is a move possible
        return !canMove();
    }
    
    /**
     * Inputs: None
     * Outputs: returns a boolean
     * Description: checks for the tiles in the middle (not on the edges), if they 
     * can crash. If so, returns true and false otherwise.
    */
    private boolean canMove() {
        for (int row = 1; row < 3; row++) {
            for (int col = 1; col < 3; col++) {
                if (tiles[row][col].canCrash(tiles[row][col + 1])) {
                    return true;
                }
                if (tiles[row][col].canCrash(tiles[row][col - 1])) {
                    return true;
                }
                if (tiles[row][col].canCrash(tiles[row + 1][col])) {
                    return true;
                }
                if (tiles[row][col].canCrash(tiles[row - 1][col])) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Inputs: None
     * Outputs: returns a boolean 
     * Description: returns true if the player won or lost; returns false otherwise.
    */
    public boolean isGameOver() {
        return didPlayerWin() || didPlayerLose();
    }
    
    /**
     * Inputs: None
     * Outputs: returns no value 
     * Description: draws the final message stating if the player won or lost after 
     * x moves.
    */
    public void drawFinalMessage() {
        // setting the size and the color for the text
        PennDraw.setFontBold();
        PennDraw.setFontSize(30);
        PennDraw.setPenColor(211, 119, 6);
        if (didPlayerWin()) {
            PennDraw.text(0.5, 0.5, "YOU WON AFTER " + moves + " MOVES!");
        }
        else if (didPlayerLose()) {
            PennDraw.text(0.5, 0.5, "YOU LOST AFTER " + moves + " MOVES!");
        }
        PennDraw.advance();
    }
}
