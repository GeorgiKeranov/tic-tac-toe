package georgi.com.tictactoe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);


        // One Player - Button.
        ((Button) findViewById(R.id.one_game)).setOnClickListener(new View.OnClickListener(){
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext() , game_PvA.class);
            startActivity(intent);
        }
    });

        // Two Player - Button.
        ((Button) findViewById(R.id.two_game)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext() , game_PvP.class);
                startActivity(intent);
            }
        });

        // Exit Game - Button.
        ((Button) findViewById(R.id.exit_game)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Menu.this.finish();
            }
        });

    }
}
