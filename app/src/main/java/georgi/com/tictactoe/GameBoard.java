package georgi.com.tictactoe;

import java.util.Random;

public class GameBoard {

    // Here we are storing player moves.
    private char board[];
    private Random randomGenerator;

    private static final int BOARD_SIZE = 9;

    static final char PLAYER1 = 'O';
    static final char PLAYER2 = 'X';
    static final char BLANK = ' ';

    static final int NO_WINNER = 0;
    static final int PLAYER1_WIN = 1;
    static final int PLAYER2_WIN = 2;
    static final int DRAW = 3;

    GameBoard(){
        // Initializing the board.
        board = new char[BOARD_SIZE];

        // Initializing the randomGenerator.
        randomGenerator = new Random();
    }

    void clearBoard() {
        for(int i = 0; i < BOARD_SIZE; i++)
            board[i] = BLANK;
    }

    /**
     * @return :
     *  0 -> no winner and have a blank
     *  1 -> PLAYER1 win
     *  2 -> PLAYER2 win
     *  3 -> Game is Draw
    */
    int checkWinner(){

        /**
         * Check for horizontal winners :
         *
         * Starts from 0 to 6 to check horizontals
         * 0 1 2
         * 3 4 5
         * 6 7 8
         */
        for(int i = 0 ; i <= 6 ; i += 3){

                if (board[i] == PLAYER1 &&
                        board[i+1] == PLAYER1 &&
                        board[i+2] == PLAYER1) return PLAYER1_WIN;

                if (board[i] == PLAYER2 &&
                        board[i+1] == PLAYER2 &&
                        board[i+2] == PLAYER2) return PLAYER2_WIN;
        }

        /**
         * Check for vertical winners :
         *
         * i, i+3 and i+6 to check one vertical line.
         * 0 1 2
         * 3 4 5
         * 6 7 8
         */
        for(int i = 0 ; i <= 2 ; i++) {
            if (board[i] == PLAYER1 &&
                board[i+3] == PLAYER1 &&
                board[i+6] == PLAYER1) return PLAYER1_WIN;

            if (board[i] == PLAYER2 &&
                board[i+3] == PLAYER2 &&
                board[i+6] == PLAYER2) return PLAYER2_WIN;

        }

        /**
         * Check for diagonal winners.
         * They are 2 options so without a cycle.
         * 0 , 4 , 8 -> first diagonal.
         * 2 , 4 , 6 -> second diagonal.
        */

        if(board[0] == PLAYER1 &&
           board[4] == PLAYER1 &&
           board[8] == PLAYER1) return PLAYER1_WIN;

        if(board[2] == PLAYER1 &&
           board[4] == PLAYER1 &&
           board[6] == PLAYER1) return PLAYER1_WIN;

        if(board[0] == PLAYER2 &&
           board[4] == PLAYER2 &&
           board[8] == PLAYER2) return PLAYER2_WIN;

        if(board[2] == PLAYER2 &&
           board[4] == PLAYER2 &&
           board[6] == PLAYER2) return PLAYER2_WIN;

        // If board have blank space and no winner return 0.
        for(int i = 0 ; i < BOARD_SIZE; i++) {
            if (board[i] == BLANK) return NO_WINNER;
        }

        // If we got to that line means game is draw.
        // return 3 is for DRAW game.
        return DRAW;
    }

    void setMove(int move , char player){
        board[move] = player;
    }

    int getAndroidMove() {

        // First check if android can win with 1 move.
        for (int i = 0; i < BOARD_SIZE; i++) {

            // Check if the space is blank.
            if (board[i] == BLANK) {

                // Set android move to the board.
                setMove(i , PLAYER1);

                // If android win with that move return it.
                if (checkWinner() == PLAYER1_WIN)
                    return i;
                // If android isn't winning with that move remove it from board.
                else
                    setMove(i , BLANK);
            }
        }

        // Second check if player can win with 1 move to
        // set the android move there so player can't win.
        for (int i = 0; i < BOARD_SIZE; i++) {

            if (board[i] == BLANK) {

                // Set PLAYER2 move to the board.
                setMove(i , PLAYER2);

                // If PLAYER2 wins set the android move here and return it.
                if (checkWinner() == PLAYER2_WIN) {
                    setMove(i, PLAYER1);
                    return i;
                }

                // If PLAYER2 isn't winning with that move remove it from board.
                else
                    setMove(i , BLANK);
            }
        }

        // Here we set random move because we don't have a logic way.
        int randomMove;

        // Check with do while if we are not on already filled place.
        do {
            randomMove = randomGenerator.nextInt(BOARD_SIZE);
        } while(board[randomMove] == PLAYER1 || board[randomMove] == PLAYER2);

        setMove(randomMove , PLAYER1);
        return randomMove;
    }

}
