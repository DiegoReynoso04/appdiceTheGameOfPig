package com.example.appdice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button hold, newGame, roll;
    private final Dice dice = new Dice();
    private TextView winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winner = findViewById(R.id.player_winner);
        winner.setVisibility(View.INVISIBLE);

        roll = findViewById(R.id.roll_button);
        newGame = findViewById(R.id.new_game_button);
        hold = findViewById(R.id.hold_button);

        setDiceRoll();
        resetGame(newGame);
        setGameHold();
    }

    private void setGameHold() {
        hold.setOnClickListener(view -> {
            String currentPlayer = dice.invertedHold() ? "p1" : "p2";
            dice.addDice(currentPlayer, dice.getScoreRound());

            updateScoreUI();
            changeCurrentPlayer();

            dice.setScoreRound(0);
            TextView scoreView = findViewById(R.id.turn_score);
            scoreView.setText(String.valueOf(dice.getScoreRound()));

            winnerCheck();
            dice.setHold(dice.invertedHold());

            // Cambiar de turno manualmente al presionar el botón Hold
            changeTurn();
        });
    }

    private void changeTurn() {
        // Cambia el estado de hold y actualiza el texto del jugador actual
        dice.setHold(!dice.invertedHold());
        changeCurrentPlayer();
    }

    private void updateScoreUI() {
        TextView viewPlayer1 = findViewById(R.id.p1_score);
        TextView viewPlayer2 = findViewById(R.id.p2_score);
        viewPlayer1.setText(String.valueOf(dice.getPoints("p1")));
        viewPlayer2.setText(String.valueOf(dice.getPoints("p2")));
    }

    private void changeCurrentPlayer() {
        TextView actualPlayer = findViewById(R.id.actualPlayer);
        // Actualizar el texto del jugador actual basado en el hold
        if (dice.invertedHold()) {
            actualPlayer.setText(R.string.player1); // Si es el turno de Player 1
        } else {
            actualPlayer.setText(R.string.player2); // Si es el turno de Player 2
        }
    }

    private void resetGame(Button newGame) {
        newGame.setOnClickListener(view -> {
            dice.deleteGame();
            updateScoreUI();
            winner.setVisibility(View.INVISIBLE);
            // Resetear el jugador actual al inicio de un nuevo juego
            TextView actualPlayer = findViewById(R.id.actualPlayer);
            actualPlayer.setText(R.string.player1);
            dice.setHold(false); // Asegurarse de que el primer jugador sea Player 1
        });
    }

    private void setDiceRoll() {
        roll.setOnClickListener(view -> {
            int rolledValue = dice.play();
            TextView scoreView = findViewById(R.id.turn_score);
            scoreView.setText(String.valueOf(dice.getScoreRound()));

            ImageView diceImage = findViewById(R.id.dice_image);
            switch (rolledValue) {
                case 1:
                    diceImage.setImageResource(R.drawable.dice_one);
                    // Cambiar de turno si se saca un 1
                    changeTurn(); // Cambia de turno automáticamente
                    break;
                case 2:
                    diceImage.setImageResource(R.drawable.dice_two);
                    break;
                case 3:
                    diceImage.setImageResource(R.drawable.dice_three);
                    break;
                case 4:
                    diceImage.setImageResource(R.drawable.dice_four);
                    break;
                case 5:
                    diceImage.setImageResource(R.drawable.dice_five);
                    break;
                case 6:
                    diceImage.setImageResource(R.drawable.dice_six);
                    break;
            }

            winnerCheck();
        });
    }

    private void winnerCheck() {
        String winnerName = dice.winner();
        if (winnerName.equalsIgnoreCase("P1")) {
            winner.setText(R.string.player1_wins);
            winner.setVisibility(View.VISIBLE);
            disableButtons();
        } else if (winnerName.equalsIgnoreCase("P2")) {
            winner.setText(R.string.player2_wins);
            winner.setVisibility(View.VISIBLE);
            disableButtons();
        }
    }

    private void disableButtons() {
        roll.setEnabled(false);
        hold.setEnabled(false);
        newGame.setEnabled(true);
    }
}
