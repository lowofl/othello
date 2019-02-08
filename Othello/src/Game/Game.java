package Game;

public class Game {
	private int[][] board = new int[10][10];
	private int pointsBlack = 2;
	private int pointsWhite = 2;
	private static final int WHITE = 1, BLACK = -1, EMPTY = 0;

	public Game() {
		// start a new board
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				board[i][j] = EMPTY;
		}

		// place pebbles
		board[4][4] = WHITE;
		board[5][4] = BLACK;
		board[4][5] = BLACK;
		board[5][5] = WHITE;
	}

	//makes a move, calls score() to turn the pebbles, and updates score.
	public void makeMove(int x, int y, int player) {
	    int points = 0;
		for (int j = -1; j <= 1; j++) {
			for (int i = -1; i <= 1; i++) {
                points += score(x, y, j, i, player); //flips pebbles and gives score
            }
		}
		//sets the chosen pebble and scores.
        board[x][y] = player;
        if(player == BLACK){
            pointsBlack+=points+1;
            pointsWhite-=points;
        }else{
            pointsWhite+=points+1;
            pointsBlack-=points;
        }
	}

	//returns how many points a certain move would yield.
	public int scoreFromMove(int x,int y, int player){
	    int points = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++){
                points += countScore(x,y,i,j,player);
            }
        }
        return points+1;
    }

    public int getScoreBlack(){
	    return pointsBlack;
    }
    public int getScoreWhite(){
	    return pointsWhite;
    }

    //turns pebbles in a direction
    int score(int startx, int starty, int vertical, int horizontal, int player) {
        int opponent = BLACK;
        if (player == BLACK)
            opponent = WHITE;
        int i = 1;
        int indexX = startx + horizontal;
        int indexY = starty + vertical;
        while (opponent == board[indexX][indexY]) {
            i++;
            indexX = startx + i * horizontal;
            indexY = starty + i * vertical;
        }
        if (board[indexX][indexY] == player){ //this direction gave score, so turn the pebbles.
            for(int a = 1; a<i;a++){
                indexX = startx + a * horizontal;
                indexY = starty + a * vertical;
                board[indexX][indexY] = player;
            }
        }else{
            return 0;
        }

        return i-1; //-1 because it's not supposed to count the placed pebble - that is done in makeMove.

    }

    // calculates the points in one direction
    int countScore(int startx, int starty, int vertical, int horizontal, int player) {
        int opponent = BLACK;
        if (player == BLACK)
            opponent = WHITE;
        int i = 1;
        int indexX = startx + horizontal;
        int indexY = starty + vertical;
        while (opponent == board[indexX][indexY]) {
            i++;
            indexX = startx + i * horizontal;
            indexY = starty + i * vertical;
        }
        if (board[indexX][indexY] == EMPTY){
            return 0;
        }

        return i-1; //-1 because it's not supposed to count the placed pebble - that is done in score

    }

	// checks the legality of a move
	public boolean checkMoveLegality(int x, int y, int player) {
		if(board[x][y] != EMPTY || x < 1 || x > 8 || y < 1 || y > 8)
			return false;
		for (int j = -1; j <= 1; j++) {
			for (int i = -1; i <= 1; i++)
				if (checkLegalityDirection(x, y, j, i, player))
					return true;
		}
		return false;
	}
	public boolean checkLegalityDirection(int startx, int starty, int vertical, int horizontal, int player) {
		int opponent = BLACK;
		if (player == BLACK)
			opponent = WHITE;
		int i = 1;
		int indexX = startx + horizontal;
		int indexY = starty + vertical;
		if (board[indexX][indexY] == EMPTY || board[indexX][indexY]  == player)
			return false;

        while (board[indexX][indexY] == opponent) {
            i++;
            indexX = startx + horizontal*i;
            indexY = starty + vertical*i;
        }
        if (board[indexX][indexY] == player)
            return true;
		return false;
	}
	
	// prints the game current board
	public void printBoard(int player) {
	    System.out.println("   A B C D E F G H");
		for (int i = 1; i < 9; i++) {
			System.out.print(i + " |");
			for (int j = 1; j < 9; j++) {
				if (board[i][j] == BLACK)
					System.out.print("B");
				if (board[i][j] == WHITE)
					System.out.print("W");
				if (board[i][j] == EMPTY){
				    if(checkMoveLegality(i,j,player)){
				        System.out.print(scoreFromMove(i,j,player));
                    }else{
                        System.out.print(" ");
                    }
                }
				System.out.print("|");
			}
			System.out.println("");
		}
	}

}
