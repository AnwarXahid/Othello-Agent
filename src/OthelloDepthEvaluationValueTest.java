public class OthelloDepthEvaluationValueTest {

    private static final AgentType AGENT_ONE_EVALUATION_FUNCTION = AgentType.SIMPLE;
//    private static AgentType AGENT_ONE_EVALUATION_FUNCTION = AgentType.PRIORITY;
    private static final int AGENT_ONE_DEPTH = 4;
//    private static final int AGENT_ONE_DEPTH = -1; // default value

    private static final AgentType AGENT_TWO_EVALUATION_FUNCTION = AgentType.SIMPLE;
    //    private static AgentType AGENT_TWO_EVALUATION_FUNCTION = AgentType.PRIORITY;
    private static final int AGENT_TWO_DEPTH = 8;
//    private static final int AGENT_TWO_DEPTH = -1; // default value

    public static void main(String[] args) {
//        OthelloAgent agentOne = new OthelloAgent(agentOneEvaluationFunction);
//        OthelloAgent agentTwo = new OthelloAgent(agentTwoEvaluationFunction);

        OthelloAgent agentOne = new OthelloAgent(AGENT_ONE_EVALUATION_FUNCTION, AGENT_ONE_DEPTH);
        OthelloAgent agentTwo = new OthelloAgent(AGENT_TWO_EVALUATION_FUNCTION, AGENT_TWO_DEPTH);

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