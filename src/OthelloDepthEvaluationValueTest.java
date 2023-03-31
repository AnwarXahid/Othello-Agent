import java.util.Scanner;

public class OthelloDepthEvaluationValueTest {

    private static AgentType agentOneEvaluationFunction = AgentType.SIMPLE;
//    private static AgentType agentOneEvaluationFunction = AgentType.PRIORITY;
    private static int agentOneDepth = -1;

    private static AgentType agentTwoEvaluationFunction = AgentType.SIMPLE;
    //    private static AgentType agentTwoEvaluationFunction = AgentType.PRIORITY;
    private static int agentTwoDepth = -1;

    public static void main(String[] args) {
//        OthelloAgent agentOne = new OthelloAgent(agentOneEvaluationFunction);
//        OthelloAgent agentTwo = new OthelloAgent(agentTwoEvaluationFunction);

        OthelloAgent agentOne = new OthelloAgent(agentOneEvaluationFunction, agentOneDepth);
        OthelloAgent agentTwo = new OthelloAgent(agentTwoEvaluationFunction, agentTwoDepth);

        OthelloGameState state = new OthelloGameState();
        boolean agentOneMove = true;

        System.out.println("Initial Othello Board: ");
        state.showBoard();
        System.out.println("\n\n");

        OthelloMove move;

        while (!state.gameIsOver()) {

            if (agentOneMove) {
                move = agentOne.chooseMove(state);
                state.makeMove(move.getRow(), move.getColumn());
                System.out.println("Othello Board after agent-One's move: " + move.getRow() + " " + move.getColumn());
                state.showBoard();
                System.out.println("\n\n");
            } else {
                move = agentTwo.chooseMove(state);
                state.makeMove(move.getRow(), move.getColumn());
                System.out.println("Othello Board after agent-Two's move: " + move.getRow() + " " + move.getColumn());
                state.showBoard();
                System.out.println("\n\n");
            }
            agentOneMove = !agentOneMove;
        }

        System.out.println("Game Over!");
        System.out.println("Agent-One's point: " + state.getBlackScore());
        System.out.println("Agent-Two's point: " + state.getWhiteScore());

        if (state.getBlackScore() > state.getWhiteScore()) {
            System.out.println("Agent-One wins the game!");
        } else if (state.getBlackScore() < state.getWhiteScore()) {
            System.out.println("Agent-Two wins the game!");
        } else {
            System.out.println("Draw!");
        }
    }

}