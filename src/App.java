import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
    private static final int SIZE = 5;
    private static final int WINNING_COUNT = 5;
    private Button[][] buttons = new Button[SIZE][SIZE];
    private char currentPlayer = 'X';
    private boolean gameEnded = false;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        initializeButtons(gridPane);

        Scene scene = new Scene(gridPane, 300, 300);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeButtons(GridPane gridPane) {
        for (int i = 0; i < SIZE; i++) {
            final int row = i;
            for (int j = 0; j < SIZE; j++) {
                final int col = j; 
                Button button = new Button();
                button.setMinSize(60, 60);
                button.setOnAction(e -> {
                    if (!gameEnded && buttons[row][col].getText().isEmpty()) {
                        buttons[row][col].setText(Character.toString(currentPlayer));
                        if (checkForWin(row, col)) {
                            showAlert("Player " + currentPlayer + " wins!");
                            gameEnded = true;
                        } else if (checkForDraw()) {
                            showAlert("It's a draw!");
                            gameEnded = true;
                        } else {
                            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                        }
                    }
                });
                buttons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }
    }

    private boolean checkForWin(int row, int col) {

        int count = 0;
        for (int j = 0; j < SIZE; j++) {
            if (buttons[row][j].getText().equals(Character.toString(currentPlayer))) {
                count++;
                if (count == WINNING_COUNT) return true;
            } else {
                count = 0;
            }
        }

        count = 0;
        for (int i = 0; i < SIZE; i++) {
            if (buttons[i][col].getText().equals(Character.toString(currentPlayer))) {
                count++;
                if (count == WINNING_COUNT) return true;
            } else {
                count = 0;
            }
        }

        count = 0;
        for (int i = 0, j = 0; i < SIZE && j < SIZE; i++, j++) {
            if (buttons[i][j].getText().equals(Character.toString(currentPlayer))) {
                count++;
                if (count == WINNING_COUNT) return true;
            } else {
                count = 0;
            }
        }

        count = 0;
        for (int i = 0, j = SIZE - 1; i < SIZE && j >= 0; i++, j--) {
            if (buttons[i][j].getText().equals(Character.toString(currentPlayer))) {
                count++;
                if (count == WINNING_COUNT) return true;
            } else {
                count = 0;
            }
        }

        return false;
    }

    private boolean checkForDraw() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                if (button.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
