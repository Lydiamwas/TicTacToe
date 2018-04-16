package com.example.android.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int i, j, k = 0;

    TextView textView;


    private Button[][] buttons = new Button[3][3];

    // Variable A_turn identifies the players turn
    private boolean A_turn = true;

    //Variable that updates the number of turns
    private int roundCnt;


    // PlayerAPoints display cumulative points for playerA
    // playerBPoints display cumulative points for playerB
    private int playerAPoints;
    private int playerBPoints;

    private TextView textViewPlayerA;
    private TextView textViewPlayerB;

    //Games status informs us who has won
    //private TextView gameStatus;

    //Private TextView textViewComputer_Player;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewPlayerA = findViewById(R.id.text_view_pA);
        textViewPlayerB = findViewById(R.id.text_view_pB);

        //@param  buttons[i][j] all the 9 buttons are setOnclick and Implemented on Apptcompat
        //OnClick method set for the buttons in the TTTBoard
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                // Sets all buttons onclick and clears the board after condition enable is met
                buttons[i][j].setOnClickListener(this);
                if (!buttons[i][j].isEnabled()) {
                    buttons[i][j].setText(" ");
                    buttons[i][j].setEnabled(true);
                }
            }
            //Captures the Reset button from the R.layout
            //Register the onClick listener with the implementation on Apptcompat Activity
            Button buttonbReset = (Button) findViewById(R.id.bReset);
            buttonbReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetGame();
                }

            });

        }

    }

    //The v represents the Buttons. The method checks if the buttonspace is empty.
    //If not empty then it takes action of returning value
    //Checks if buton is an empty string. if its not an empty screen we just return value
    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        // check if playerA turn is true perform X if not then playerB
        if (A_turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundCnt++;                                         //Updates the rounds to the last round

        if (checkForWin()) {
            if (A_turn) {
                playerAWins();                 //PlayerAWins method created after checkwin is called
            } else {
                playerBWins();                 //PlayerBWins method is called here
            }
        } else if (roundCnt == 9) {
            draw();                        // draw method will be called
        } else {                           //If there is no winner and no draw then turns change
            A_turn = !A_turn;               // that means playerA turn is is equal to opposite
        }
    }

    // Check three values to see if they are the same.
    private boolean checkForWin() {

        String[][] grid = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = buttons[i][j].getText().toString();
            }
        }
        // Loop through the rows
        //Checks if buttons for tableRow1,tableRow2 and tableRow3 are equal
        //last && ensures that the first button of each row is not empty
        for (int i = 0; i < 3; i++) {
            if (grid[i][0].equals(grid[i][1]) && grid[i][0].equals(grid[i][2])
                    && !grid[i][0].equals("")) {
                return true;
            }
        }
        //Loop through the columns
        //Checks if buttons for the three columns are equal
        //last && checks if the first button of each column is not empty
        for (int i = 0; i < 3; i++) {
            if (grid[0][i].equals(grid[1][i]) && grid[0][i].equals(grid[2][i])
                    && !grid[0][i].equals("")) {
                return true;
            }
        }
        // Check diagonals,
        //first diagonal top left to bottom right
        if (grid[0][0].equals(grid[1][1]) && grid[0][0].equals(grid[2][2])
                && !grid[0][0].equals("")) {
            return true;
        }
        //second diagonal top right to bottom left
        if (grid[0][2].equals(grid[1][1]) && grid[0][2].equals(grid[2][0])) {
            return true;
        }
        return false;

    }

    private void playerAWins() {
        playerAPoints++;
        Toast.makeText(this, "Player A Wins!!", Toast.LENGTH_LONG).show();
        modifyPointsText();            //This will update the playerA points beside the textView
        resetTTTBoard();
    }

    private void playerBWins() {
        playerBPoints++;
        Toast.makeText(this,"Player B Wins!!", Toast.LENGTH_LONG).show();
        modifyPointsText();            //This will update the playerB points beside the textView
        resetTTTBoard();                           //Reset the game after win or draw
    }

    private void draw() {
        Toast.makeText(this, "No Win! draw!", Toast.LENGTH_LONG).show();
        resetTTTBoard();                           //Reset the game board after win or draw
    }

    private void modifyPointsText() {
        textViewPlayerA.setText("Player A:" + playerAPoints);
        textViewPlayerB.setText("Player B:" + playerBPoints);

    }

    //Reset method resets all our buttons to emptystring
    //Resets roundcnt to zero and reset A_turn to true
    private void resetTTTBoard() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCnt = 0;
        A_turn = true;
    }
    private void resetGame(){
        playerAPoints= 0;
        playerBPoints= 0;
        modifyPointsText();
        resetTTTBoard();
    }

}


