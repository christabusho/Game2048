/*  Name: Ange Christa Dushime
*  PennKey: dushime
*  Recitation: 202
*  Execution: java Game 
*
*  A class that represents the game 2048 and initializes the 
* game board, then updates it according to the key pressed,
* then runs the game until the player wins or loses.
*/
public class Game {
    public static void main(String[] args) {
        // creating and drawing the board
        Board gameBoard = new Board();
        gameBoard.draw();
        // enable animation
        PennDraw.enableAnimation(30);
        while (!gameBoard.isGameOver()) {
            if (PennDraw.hasNextKeyTyped()) {
                char keyPressed = PennDraw.nextKeyTyped();
                gameBoard.update(keyPressed);
                // draw the updated version of the board
                gameBoard.draw();
                PennDraw.advance();
            }
        }
        gameBoard.drawFinalMessage();
    }
}

