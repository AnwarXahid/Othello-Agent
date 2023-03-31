import java.util.Scanner;

public class OthelloGameTest {
    public static void main(String[] args) {
        OthelloAgent agent = new OthelloAgent();

        OthelloGameState state = new OthelloGameState();

        System.out.println("Initial Othello Board: ");
        state.showBoard();
        System.out.println("\n\n");

        String input;
        int input_row, input_column;
        boolean usersMove = true;
        OthelloMove move;

        while (!state.gameIsOver()) {

            if (usersMove) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Your valid moves are: " + state.getValidMoves().toString());
                System.out.print("Enter your move [row column]: ");
                input = scan.nextLine();
                input_row = Integer.parseInt(input.split(" ")[0]);
                input_column = Integer.parseInt(input.split(" ")[1]);

                if (state.isValidMove(input_row, input_column)) {
                    state.makeMove(input_row, input_column);
                    System.out.println("Othello Board after user's move: ");
                    state.showBoard();
                    System.out.println("\n\n");
                    usersMove = false;
                } else {
                    System.out.println("Not a valid move. Try another! ");
                }
            } else {

                move = agent.chooseMove(state);
                state.makeMove(move.getRow(), move.getColumn());
                System.out.println("Othello Board after agent's move: " + move.getRow() + " " + move.getColumn());
                state.showBoard();
                System.out.println("\n\n");
                usersMove = true;
            }
        }

        System.out.println("Game Over!");
        System.out.println("User's point: " + state.getBlackScore());
        System.out.println("Agent's point: " + state.getWhiteScore());

        if (state.getBlackScore() > state.getWhiteScore()) {
            System.out.println("User wins the game!");
        } else if (state.getBlackScore() < state.getWhiteScore()) {
            System.out.println("Agent wins the game!");
        } else {
            System.out.println("Draw!");
        }
    }
}
