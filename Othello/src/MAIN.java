import Game.Game;

import java.util.Scanner;

public class MAIN {
	public static void main(String[] args) {
	    int player = 0, opp = 0;
		Game game = new Game();
        Scanner sc = new Scanner(System.in);
        System.out.println("Play as white or black? [w/b]");
        String playC = sc.next();
        if(playC.equals("w")){
            player = 1;
            opp = -1;
        }else{
            player = -1;
            opp = 1;
        }
        int currentPlayer = player; //change to white?

        while(true){//change to game not over
            System.out.println("Score:\nBlack "+game.getScoreBlack() + " - " + game.getScoreWhite()+" White");
            System.out.println("Current board: ");
            game.printBoard(currentPlayer);
            if(currentPlayer == -1){ //change to as in Game?
                System.out.println("Black to place. ");
            } else{
                System.out.println("White to place. ");
            }
            System.out.println("Place where? "); //(format: e3)
            String choice = sc.next();
            choice = choice.toLowerCase();
            int y = choice.charAt(0)-96; //lazy ascii conversion
            int x = choice.charAt(1)-48;
            if(game.checkMoveLegality(x,y, currentPlayer)){ //crashes on bad input, catch?
                game.makeMove(x,y,currentPlayer);
                if(currentPlayer == player){ //switching to the opponents turn
                    currentPlayer = opp;
                }else{
                    currentPlayer = player;
                }
            }else{
                System.out.println("Invalid move, try again. (format: e3)");
            }


        }

	}
}
