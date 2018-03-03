package georgi.com.tictactoe;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.String;
import java.lang.Integer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class PlayerVersusAndroidActivity extends AppCompatActivity {

    private GameBoard gameBoard = new GameBoard();

    private TextView infoText, playerScore, androidScore, drawScore;
    private int wins = 0, defeats = 0, draws = 0;

    private boolean gameOver = false, playerTurn = true, firstTurnInGame = true;

    private Button newGameButton, boardButton[] = new Button[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pva);

        infoText = (TextView) findViewById(R.id.info_text);
        playerScore = (TextView) findViewById(R.id.player1_text);
        androidScore = (TextView) findViewById(R.id.player2_text);
        drawScore = (TextView) findViewById(R.id.draws_text);

        boardButton[0] = (Button) findViewById(R.id.but_0);
        boardButton[1] = (Button) findViewById(R.id.but_1);
        boardButton[2] = (Button) findViewById(R.id.but_2);
        boardButton[3] = (Button) findViewById(R.id.but_3);
        boardButton[4] = (Button) findViewById(R.id.but_4);
        boardButton[5] = (Button) findViewById(R.id.but_5);
        boardButton[6] = (Button) findViewById(R.id.but_6);
        boardButton[7] = (Button) findViewById(R.id.but_7);
        boardButton[8] = (Button) findViewById(R.id.but_8);

        newGameButton = (Button) findViewById(R.id.new_but);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartGame();
            }
        });

        StartGame();
    }

    private void setAndroidMove(int move) {
        boardButton[move].setText("" + GameBoard.PLAYER1);
        boardButton[move].setEnabled(false);
        boardButton[move].setBackgroundColor(Color.BLUE);
    }

    private void setPlayerMove(int move) {
        gameBoard.setMove(move, GameBoard.PLAYER2);

        boardButton[move].setText("" + GameBoard.PLAYER2);
        boardButton[move].setEnabled(false);
        boardButton[move].setBackgroundColor(Color.RED);
    }

    private void StartGame() {

        // Clearing buttons and enabling them.
        for(int i = 0 ; i < 9 ; i++) {
            boardButton[i].setBackgroundColor(Color.parseColor("#ffffbb33"));
            boardButton[i].setText("");
            boardButton[i].setEnabled(true);
        }

        // Clearing the GameBoard.
        gameBoard.clearBoard();

        // New game button invisible.
        newGameButton.setVisibility(View.INVISIBLE);
        // Default text to the info.
        infoText.setText(R.string.Info);

        for (int i = 0; i < 9; i++) {
            boardButton[i].setOnClickListener(new ButtonClicked(i));
        }

        // If android player is first.
        if(!playerTurn) {
            int androidMove = gameBoard.getAndroidMove();
            setAndroidMove(androidMove);

            playerTurn = !playerTurn;
        }

        gameOver = false;
    }

    private class ButtonClicked implements View.OnClickListener {

        private int location;

        private ButtonClicked(int i) {
            location = i;
        }

        public void onClick(View view){

            if(!gameOver) {
                // Setting player's move.
                setPlayerMove(location);
                int winner = gameBoard.checkWinner();
                playerTurn = !playerTurn;

                // If don't have winner, set android's move.
                if (winner == 0) {
                    int androidMove = gameBoard.getAndroidMove();
                    setAndroidMove(androidMove);

                    // Check for winner again.
                    winner = gameBoard.checkWinner();
                    playerTurn = !playerTurn;
                }

                // Next game playerTurn will be turn of the player
                // who was second turn last game.
                if (winner > 0 ) {
                    firstTurnInGame = !firstTurnInGame;
                    playerTurn = firstTurnInGame;
                }

                if (winner == 1) {
                    defeats++;
                    infoText.setText(R.string.android_win);
                    androidScore.setText("Android : " + defeats);
                    gameOver = true;
                }

                if (winner == 2) {
                    wins++;
                    infoText.setText(R.string.player_win);
                    playerScore.setText("Player : " + wins);
                    gameOver = true;
                }

                if (winner == 3) {
                    draws++;
                    infoText.setText(R.string.draw_text);
                    drawScore.setText("Draws : " + draws);
                    gameOver = true;
                }
            }

            // If game is over we set our newGameButton button to visible.
            if(gameOver)
                newGameButton.setVisibility(View.VISIBLE);
        }

    }

}
