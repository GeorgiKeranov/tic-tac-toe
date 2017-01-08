// Bug Reports
// 1. bug when X or O player is first and lose game its the same person's first move.

package georgi.com.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.String;
import java.lang.Integer;



/*
checkWinner() of MainLogic.java :
return 0 : no winner and have a blank
return 1 : Android win
return 2 : Player win
return 3 : Game is Draw
*/



public class game_PvA extends AppCompatActivity implements View.OnClickListener {

    TextView info_text, player_score, android_score, draw_score;

    int wins = 0 , defeats = 0 , draws = 0;

    boolean Game_Over = false;
    boolean PlayerTurn = true;
    boolean FirstGameTurn = true;
    MainLogic PvA_game;

    Button new_game;
    Button[] aButton = new Button[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pva);


        info_text = (TextView) findViewById(R.id.info_text);
        player_score = (TextView) findViewById(R.id.player1_text);
        android_score = (TextView) findViewById(R.id.player2_text);
        draw_score = (TextView) findViewById(R.id.draws_text);

        aButton[0] = (Button) findViewById(R.id.but_0);
        aButton[1] = (Button) findViewById(R.id.but_1);
        aButton[2] = (Button) findViewById(R.id.but_2);
        aButton[3] = (Button) findViewById(R.id.but_3);
        aButton[4] = (Button) findViewById(R.id.but_4);
        aButton[5] = (Button) findViewById(R.id.but_5);
        aButton[6] = (Button) findViewById(R.id.but_6);
        aButton[7] = (Button) findViewById(R.id.but_7);
        aButton[8] = (Button) findViewById(R.id.but_8);

        new_game = (Button) findViewById(R.id.new_but);
        new_game.setOnClickListener(this);


        PvA_game = new MainLogic();
        StartGame();

    }


    public void StartGame()
    {
        for(int i = 0 ; i < 9 ; i++)
        {
            aButton[i].setBackgroundColor(Color.parseColor("#ffffbb33"));
            aButton[i].setText("");
            aButton[i].setEnabled(true);
        }

        PvA_game.clearBoard();
        new_game.setVisibility(View.INVISIBLE);
        info_text.setText(R.string.Info);

        for (int i = 0; i < 9; i++) {
            aButton[i].setOnClickListener(new ButtonClicked(i));
        }


        if(!PlayerTurn)
        {
            int and_move = PvA_game.getAndro_move();
            set_move(and_move , PvA_game.PLAYER1);
            PlayerTurn = true;
        }

    }

    private void set_move(int move , char player)
    {
        aButton[move].setText(String.valueOf(player));
        aButton[move].setEnabled(false);
        PvA_game.set_move(move , player);

        if(PlayerTurn) aButton[move].setBackgroundColor(Color.RED);
        else aButton[move].setBackgroundColor(Color.BLUE);
    }


    // That onClick is for the new button
    @Override
    public void onClick(View v) {
        Game_Over = false;
        StartGame();
    }


    private class ButtonClicked implements View.OnClickListener {

        private int location;

        private ButtonClicked(int i) {
            location = i;
        }

        public void onClick(View view){

            if(!Game_Over)
            {
                // Setting player's move.
                set_move(location , PvA_game.PLAYER2);
                int winner = PvA_game.checkWinner();
                PlayerTurn = !PlayerTurn;

                // If we don't have winner , we set android's move.
                if (winner == 0)
                {
                    int get_move = PvA_game.getAndro_move();
                    set_move(get_move , PvA_game.PLAYER1);
                    // winner checks for winner again.
                    winner = PvA_game.checkWinner();
                    PlayerTurn = !PlayerTurn;

                }

                // Next game PlayerTurn will be turn of the player
                // who was second turn last game.
                if (winner > 0 )
                {
                    FirstGameTurn = !FirstGameTurn;
                    PlayerTurn = FirstGameTurn;
                }

                if (winner == 1)
                {
                    defeats++;
                    info_text.setText(R.string.android_win);
                    android_score.setText("Android : " + Integer.toString(defeats));
                    Game_Over = true;
                }

                if (winner == 2)
                {
                    wins++;
                    info_text.setText(R.string.player_win);
                    player_score.setText("Player : " + Integer.toString(wins));
                    Game_Over = true;
                }

                if (winner == 3)
                {
                    draws++;
                    info_text.setText(R.string.draw_text);
                    draw_score.setText("Draws : " + Integer.toString(draws));
                    Game_Over = true;
                }
            }

            // If game is over we set our new_game button to visible.
            if(Game_Over) new_game.setVisibility(View.VISIBLE);

        }

    }
}
