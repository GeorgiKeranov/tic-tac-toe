package georgi.com.tictactoe;

import java.util.Random;

public class MainLogic {

    // Random Generator
    private Random rand_gen;

    // board array is for the all the board.
    private int BOARD_SIZE = 9;
    char PLAYER1 = 'O';
    char PLAYER2 = 'X';
    private char BLANK = ' ';
    private int [] board;


    MainLogic(){
        board = new int[BOARD_SIZE];
        clearBoard();
        rand_gen = new Random();
    }

    void clearBoard() {
        for(int i = 0 ; i < BOARD_SIZE ; i++)
            board[i] = BLANK;
    }

    /*
    return 0 : no winner and have a blank
    return 1 : PLAYER1 win
    return 2 : PLAYER2 win
    return 3 : Game is Draw
    */
    short checkWinner(){
        // Check for horizontal winners :
        //  0 , 1 , 2.
        //  3 , 4 , 5.
        //  6 , 7 , 8.
        // Starts from 0 to 6 to check horizontals
        for(int i = 0 ; i<=6 ; i+=3){

            //Player1 win = 1.
                if (board[i] == PLAYER1 &&
                    board[i+1] == PLAYER1 &&
                    board[i+2] == PLAYER1) return 1;
            //Player2 win = 2.
                if (board[i] == PLAYER2 &&
                    board[i+1] == PLAYER2 &&
                    board[i+2] == PLAYER2) return 2;
        }

        // Check for Vertical winners :
        // 0 , 3 , 6.
        // 1 , 4 , 7.
        // 2 , 5 , 8.
        // I'm writing i , i+3 and i+6 to check one vertical line.
        for(int i = 0 ; i<=2 ; i++) {
            if (board[i] == PLAYER1 &&
                    board[i + 3] == PLAYER1 &&
                    board[i + 6] == PLAYER1) return 1;

            if (board[i] == PLAYER2 &&
                    board[i + 3] == PLAYER2 &&
                    board[i + 6] == PLAYER2) return 2;

        }

        // Check for Diagonal winners:
        // 0 , 4 , 8.
        // 2 , 4 , 6.
        // They are 2 options so without a cycle.
        if(board[0] == PLAYER1 &&
           board[4] == PLAYER1 &&
           board[8] == PLAYER1) return 1;

        if(board[2] == PLAYER1 &&
           board[4] == PLAYER1 &&
           board[6] == PLAYER1) return 1;

        if(board[0] == PLAYER2 &&
           board[4] == PLAYER2 &&
           board[8] == PLAYER2) return 2;

        if(board[2] == PLAYER2 &&
           board[4] == PLAYER2 &&
           board[6] == PLAYER2) return 2;

        // If our board have blank space and we don't have winner return 0.
        for(int i = 0 ; i < BOARD_SIZE; i++) {
            if (board[i] == BLANK) return 0;
        }

        // We checked if someone win and if none win
        // but have blank space so return 3 is for DRAW game.
        return 3;
    }

    void set_move(int move , char player){
        board[move] = player;
    }


    int getAndro_move() {

        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if(board[i] != PLAYER2 && board[i] != PLAYER1){
                //Here im setting if the blank space can
                //be used to win the game.
                set_move(i , PLAYER1);
                //checkWinner == 1 (Device win)
                if (checkWinner() == 1) return i;
                else set_move(i , BLANK);
            }
        }

        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if(board[i] != PLAYER2 && board[i] != PLAYER1){
                //Here im setting if the blank space can
                //be used to draw the game.
                set_move(i , PLAYER2);
                //checkWinner == 2 (Player win)
                if (checkWinner() == 2) return i;
                else set_move(i , BLANK);
            }
        }

        // Here we set random because we don't have a logic way.

        // Check with while if we are not on already filled place.

        int rand;

        do
        {
            rand = rand_gen.nextInt(BOARD_SIZE);
        }while(board[rand] == PLAYER1 || board[rand] == PLAYER2);

        set_move(rand , PLAYER1);
        return rand;

    }

}
