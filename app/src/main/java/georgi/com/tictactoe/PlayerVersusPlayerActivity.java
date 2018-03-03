package georgi.com.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class PlayerVersusPlayerActivity extends AppCompatActivity {

    private GameBoard gameBoard = new GameBoard();

    private TextView infoText, player1Score, player2Score, drawScore;
    private int pl1wins = 0, pl2wins = 0, draws = 0;

    private boolean player1Turn = true, firstTurnInTheGame = true, gameOver = false;

    private Button newGameButton, buttons[] = new Button[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pvp);

        infoText = (TextView) findViewById(R.id.info_text);
        player1Score = (TextView) findViewById(R.id.player1_text);
        player2Score = (TextView) findViewById(R.id.player2_text);
        drawScore = (TextView) findViewById(R.id.draws_text);

        newGameButton = (Button) findViewById(R.id.new_but);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartGame();
            }
        });

        buttons[0] = (Button) findViewById(R.id.but_0);
        buttons[1] = (Button) findViewById(R.id.but_1);
        buttons[2] = (Button) findViewById(R.id.but_2);
        buttons[3] = (Button) findViewById(R.id.but_3);
        buttons[4] = (Button) findViewById(R.id.but_4);
        buttons[5] = (Button) findViewById(R.id.but_5);
        buttons[6] = (Button) findViewById(R.id.but_6);
        buttons[7] = (Button) findViewById(R.id.but_7);
        buttons[8] = (Button) findViewById(R.id.but_8);

        StartGame();
    }

    private void StartGame() {

        // Clearing buttons and enabling them.
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(true);
            buttons[i].setBackgroundColor(Color.parseColor("#ffffbb33"));
            buttons[i].setText(" ");
        }

        // Clearing the GameBoard.
        gameBoard.clearBoard();

        // New game button invisible.
        newGameButton.setVisibility(View.INVISIBLE);
        // Default text to the info.
        infoText.setText(R.string.Info);

        for (int i = 0; i < 9; i++) {
            buttons[i].setOnClickListener(new ButClicked(i));
        }

        gameOver = false;
    }


    private class ButClicked implements View.OnClickListener {

        int location;

        ButClicked(int location) {
            this.location = location;
        }

        public void onClick(View v) {

            if(!gameOver) {
                if (player1Turn)
                    setPlayerMove(location, GameBoard.PLAYER1);
                else
                    setPlayerMove(location, GameBoard.PLAYER2);

                player1Turn = !player1Turn;

                int winner = gameBoard.checkWinner();
                // Check if we have winner.
                if (winner > 0) {
                    // Next game player1Turn will be turn of the player
                    // who was second turn last game.
                    firstTurnInTheGame = !firstTurnInTheGame;
                    player1Turn = firstTurnInTheGame;

                    // Hiding new game button.
                    newGameButton.setVisibility(View.VISIBLE);

                    // Setting stats with the winner.
                    setStats(winner);

                    gameOver = true;
                 }
             }
        }

    }

    public void setStats(int winner)
    {
        if(winner == GameBoard.PLAYER1_WIN) {
            pl1wins++;
            infoText.setText(R.string.player1_win);
            player1Score.setText("Player 1 : " + pl1wins);

        }

        if(winner == GameBoard.PLAYER2_WIN) {
            pl2wins++;
            infoText.setText(R.string.player2_win);
            player2Score.setText("Player 2 : " + pl2wins);
        }

        if(winner == GameBoard.DRAW) {
            draws++;
            infoText.setText(R.string.draw_text);
            drawScore.setText("Draws : " + draws);
        }
    }

    // Sets move on the layout and in the gameBoard.
    public void setPlayerMove(int location, char player) {

        gameBoard.setMove(location , player);
        buttons[location].setEnabled(false);

        if (player1Turn) {
            buttons[location].setBackgroundColor(Color.RED);
            buttons[location].setText("O");
        } else {
            buttons[location].setBackgroundColor(Color.BLUE);
            buttons[location].setText("X");
        }
    }

}


