/*  Name: Ange Christa Dushime
*  PennKey: dushime
*  Recitation: 202
*  Execution: n/a
*
*  A class that represents every tile in the game 2048.
*  It can change its number and draw itself.
*/

public class Tile {
    // data fields
    
    private int number; // the number it is containing
    private double xPos; // its x position on the grid
    private double yPos; // its y position on the grid
    
    // constructor
    
    /* Initialize the x and y positions
    * for an empty tile, which means a tile
    * that contains the number 0.
    */
    public Tile(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    /* Initialize the x and y positions
    * and the number held by a tile.
    */
    public Tile(int number, double xPos, double yPos) {
        this.number = number;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    // methods
    
    /**
     * Inputs: None
     * Outputs: returns nothing
     * Description: draws a square with a color according to the number that it 
     * contains and draws also the number on it. It calls two other functions 
    * to set the color and the number's color.
    */
    public void draw() {
        setTileColor();
        PennDraw.filledSquare(this.xPos, this.yPos, 0.1);
        PennDraw.setFontSize(60);
        setNumColor();
        String text = "" + number;
        if (this.number == 0) {
            text = ""; // for the tile to look empty
        }
        PennDraw.text(this.xPos, this.yPos, text);
    }
    
    /**
     * Inputs: None
     * Outputs: returns no value
     * Description: It sets the black color for 0,2,4 and white for 
     * other possible numbers.
    */
    private void setNumColor() {
        if (this.number == 0 || this.number == 2 || this.number == 4) {
            PennDraw.setPenColor(PennDraw.BLACK);
        }
        else {
            PennDraw.setPenColor(PennDraw.WHITE);
        }
    }

    /**
     * Inputs: None 
     * Outputs: returns no value
     * Description: It is a helper method that sets a specific color for every 
     * tile according to the number it contains. 
    */
    private void setTileColor() {
        if (this.number == 0) {
            PennDraw.setPenColor(211, 186, 166);
        }
        else if (this.number == 2) {
            PennDraw.setPenColor(239, 227, 218);
        }
        else if (this.number == 4) {
            PennDraw.setPenColor(239, 227, 208);
        }
        else if (this.number == 8) {
            PennDraw.setPenColor(242, 168, 104);
        }
        else if (this.number == 16) {
            PennDraw.setPenColor(237, 137, 75);
        }
        else if (this.number == 32) {
            PennDraw.setPenColor(244, 94, 61);
        }
        else if (this.number == 64) {
            PennDraw.setPenColor(255, 75, 10);
        }
        else if (this.number == 128) {
            PennDraw.setPenColor(247, 222, 84);
        }
        else if (this.number == 256) {
            PennDraw.setPenColor(247, 216, 44);
        }
        else if (this.number == 512) {
            PennDraw.setPenColor(252, 239, 55);
        }
        else if (this.number == 1024) {
            PennDraw.setPenColor(252, 236, 7);
        }
        else if (this.number == 2048) {
            PennDraw.setPenColor(255, 255, 7);
        }
        else {
            return;
        }
    }
    
    /**
     * Inputs: None
     * Outputs: returns an int
     * Description: getter method, that returns the number that the tile contains.
    */
    public int getNumber() {
        return this.number;
    }
    
    /**
     * Inputs: an integer 
     * Outputs: returns no value
     * Description: setter method, that sets a new value (the input) to 
     * the number held by the tile
    */
    public void setNumber(int newNumber) {
        this.number = newNumber;
    }
    
    /**
     * Inputs: takes in a tile
     * Outputs: returns a boolean value
     * Description: checks if two tiles can merge/ crash by checking if they
     *              contain the same number.
    */
    public boolean canCrash(Tile other) {
        return this.number == other.number;
    }
    
    /**
     * Inputs: takes in another tile 
     * Outputs: returns no value
     * Description: combines two tiles by the 
    */
    public void combine(Tile other) {
        this.number += other.number;
    }
    
    /**
     * Inputs: None
     * Outputs: returns a boolean
     * Description: checks if a tile is empty.(A tile is empty when it contains 0)
    */
    public boolean isEmpty() {
        return this.number == 0;
    }
    
}
