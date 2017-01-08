// Bug Reports
// 1. bug when X or O player is first and lose game its the same person's first move.



package georgi.com.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;


public class game_PvP extends AppCompatActivity implements View.OnClickListener {

    TextView info_text, player1_score, player2_score, draw_score;

    Button new_game;
    Button[] buttons = new Button[9];
    MainLogic PvP_game;
    // Pl1 = True , Pl2 = False.
    boolean Player1Turn = true; // 'O' First
    //WhoisFirst - to change Player1 and Player2 turns
    boolean WhoIsFirst = true;
    boolean GameOver = false;

    int pl1wins = 0, pl2wins = 0, draws = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pvp);

        info_text = (TextView) findViewById(R.id.info_text);
        player1_score = (TextView) findViewById(R.id.player1_text);
        player2_score = (TextView) findViewById(R.id.player2_text);
        draw_score = (TextView) findViewById(R.id.draws_text);

        new_game = (Button) findViewById(R.id.new_but);
        new_game.setOnClickListener(this);

        buttons[0] = (Button) findViewById(R.id.but_0);
        buttons[1] = (Button) findViewById(R.id.but_1);
        buttons[2] = (Button) findViewById(R.id.but_2);
        buttons[3] = (Button) findViewById(R.id.but_3);
        buttons[4] = (Button) findViewById(R.id.but_4);
        buttons[5] = (Button) findViewById(R.id.but_5);
        buttons[6] = (Button) findViewById(R.id.but_6);
        buttons[7] = (Button) findViewById(R.id.but_7);
        buttons[8] = (Button) findViewById(R.id.but_8);


        PvP_game = new MainLogic();
        StartGame();


    }

    private void StartGame() {

        info_text.setText(R.string.Info);

        // Setting buttons , board , GameOver and visibility for a new game.
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(true);
            buttons[i].setBackgroundColor(Color.parseColor("#ffffbb33"));
            buttons[i].setText(" ");
        }

        PvP_game.clearBoard();
        new_game.setVisibility(View.INVISIBLE);
        GameOver = false;

        for (int i = 0; i < 9; i++) {
            buttons[i].setOnClickListener(new ButClicked(i));
        }

    }


    private class ButClicked implements View.OnClickListener {

        int location;

        ButClicked(int location) {
            this.location = location;
        }


        public void onClick(View v) {

         if(!GameOver) {
             int winner = PvP_game.checkWinner();

             if (winner == 0) {
                 if (Player1Turn) {
                     set_move(location, PvP_game.PLAYER1);
                     Player1Turn = false;
                 } else if (!Player1Turn) {
                     set_move(location, PvP_game.PLAYER2);
                     Player1Turn = true;
                 }
             }

             // Checking again if we got winner.
             winner = PvP_game.checkWinner();
             if (winner > 0) {
                 // WhoIsFirst is fixing Player1Turn.
                 // Next game Player1Turn will be turn of the player
                 // who was second turn last game.
                 WhoIsFirst = !WhoIsFirst;
                 Player1Turn = WhoIsFirst;
                 new_game.setVisibility(View.VISIBLE);
                 set_stats(winner);
                 GameOver = true;
             }
         }
        }
    }

    public void set_stats(int a)
    {
        if(a == 1)
        {
            pl1wins++;
            info_text.setText(R.string.player1_win);
            player1_score.setText("Player 1 : " + Integer.toString(pl1wins));

        }

        if(a == 2)
        {
            pl2wins++;
            info_text.setText(R.string.player2_win);
            player2_score.setText("Player 2 : " + Integer.toString(pl2wins));
        }

        if(a == 3)
        {
            draws++;
            info_text.setText(R.string.draw_text);
            draw_score.setText("Draws : " + Integer.toString(draws));
        }
    }


    //set_move sets move on the layout and in the PvP_game.
    public void set_move(int location, char player) {
        PvP_game.set_move(location , player);
        buttons[location].setEnabled(false);
        if (Player1Turn) {
            buttons[location].setBackgroundColor(Color.RED);
            buttons[location].setText("O");
        } else {
            buttons[location].setBackgroundColor(Color.BLUE);
            buttons[location].setText("X");
        }
    }

        // That onClick is for the New Game button.
        @Override
        public void onClick (View v){
            StartGame();
        }
    }


