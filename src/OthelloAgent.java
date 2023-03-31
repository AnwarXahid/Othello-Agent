import java.util.ArrayList;
public class OthelloAgent {
    private boolean IAmBlack;
    public static final int ROW_NUMBER = 8;
    public static final int COLUMN_NUMBER = 8;
    private final int[][] priorityBoard = new int[8][8];
    private final AgentType evaluationFunctionType;
    private final int depth;

    public OthelloAgent() {
        this.evaluationFunctionType = AgentType.PRIORITY;
        this.depth = -1;
    }

    public OthelloAgent(AgentType evaluationFunctionType) {
        this.evaluationFunctionType = evaluationFunctionType;
        depth = -1;
    }

    public OthelloAgent(AgentType evaluationFunctionType, int depth) {
        this.evaluationFunctionType = evaluationFunctionType;
        this.depth = depth;
    }

    public OthelloMove chooseMove(OthelloGameState state) {
        long startTime = System.currentTimeMillis();

        this.fillThePriorityBoard();

        ArrayList<EvaluationMove> listOfMoves = new ArrayList<EvaluationMove>();

        int theDepth;

        int validMoves = 0;

        for (int row = 0; row < ROW_NUMBER; row++) {
            for (int col = 0; col < COLUMN_NUMBER; col++) {
                if (state.isValidMove(row, col)) {
                    validMoves++;
                }
            }
        }


        if (depth == -1) {
            if (validMoves >= 1 && validMoves < 5) {
                theDepth = 6;
            } else if (validMoves >= 5 && validMoves < 7) {
                theDepth = 5;
            } else {
                theDepth = 4;
            }
        } else {
            theDepth = depth;
        }


        IAmBlack = state.isBlackTurn();

        for (int row = 0; row < ROW_NUMBER; row++) {
            for (int col = 0; col < COLUMN_NUMBER; col++) {
                if (state.isValidMove(row, col)) {
                    OthelloMove currentMove = new OthelloMove(row, col);
                    OthelloGameState clone = state.clone();
                    clone.makeMove(row, col);

                    if ((IAmBlack && clone.isBlackTurn())
                            || (!IAmBlack && !clone.isBlackTurn())) {
                        return new OthelloMove(row, col);
                    }

                    else if ((row == 0 && col == 0) || (row == 0 && col == 7)
                            || (row == 7 && col == 0) || (row == 7 && col == 7)) {
                        return new OthelloMove(row, col);
                    }
                    listOfMoves.add(new EvaluationMove(alphaBeta(clone,
                            theDepth, Integer.MIN_VALUE, Integer.MAX_VALUE),
                            currentMove));
                }
            }
        }

        EvaluationMove max = listOfMoves.get(0);
        for (int i = 1; i < listOfMoves.size(); i++) {
            if (listOfMoves.get(i).evaluation() > max.evaluation()) {
                max = listOfMoves.get(i);
            }
        }
        long endTime = System.currentTimeMillis();
        double total = (endTime - startTime) * 0.001;
        System.out.println("\n" + total +" seconds to find the move in "+ theDepth + " depth!\n");
        return max.move();
    }


    public int alphaBeta(OthelloGameState othelloGameState, int depth, int alpha, int beta) {
        if (depth == 0 || othelloGameState.gameIsOver()) {

            if (evaluationFunctionType == AgentType.PRIORITY) {
                return getPriorityEvaluation(othelloGameState);
            } else {
                return getNaiveEvaluation(othelloGameState);
            }
        }

        else {

            if ((IAmBlack && othelloGameState.isBlackTurn()) || (!IAmBlack && !othelloGameState.isBlackTurn())) {
                for (int row = 0; row < ROW_NUMBER; row++) {
                    for (int col = 0; col < COLUMN_NUMBER; col++) {
                        if (othelloGameState.isValidMove(row, col)) {
                            OthelloGameState temp = othelloGameState.clone();
                            temp.makeMove(row, col);
                            int runningMax = alphaBeta(temp, depth - 1, alpha, beta);
                            if (runningMax > alpha) {
                                alpha = runningMax;
                            }
                            if (alpha >= beta) {
                                return alpha;
                            }
                        }
                    }
                }
                return alpha;
            } else {
                for (int row = 0; row < ROW_NUMBER; row++) {
                    for (int col = 0; col < COLUMN_NUMBER; col++) {
                        if (othelloGameState.isValidMove(row, col)) {
                            OthelloGameState temp = othelloGameState.clone();
                            temp.makeMove(row, col);
                            int runningMin = alphaBeta(temp, depth - 1, alpha, beta);
                            if (runningMin < beta) {
                                beta = runningMin;
                            }
                            if (alpha >= beta) {
                                return beta;
                            }
                        }
                    }
                }
                return beta;
            }
        }
    }

    public int getPriorityEvaluation(OthelloGameState othelloGameState) {
        int evaluatedValue = 0;
        int blackScore = 0;
        int whiteScore = 0;

        for (int row = 0; row < ROW_NUMBER; row++) {
            for (int col = 0; col < COLUMN_NUMBER; col++) {
                if (othelloGameState.getCell(row, col).equals(OthelloCell.BLACK)) {
                    blackScore += priorityBoard[row][col];
                } else if (othelloGameState.getCell(row, col).equals(OthelloCell.WHITE)) {
                    whiteScore += priorityBoard[row][col];
                }

                if (((IAmBlack && othelloGameState.isBlackTurn()) || (!IAmBlack && !othelloGameState
                        .isBlackTurn())) && othelloGameState.isValidMove(row, col)) {
                    evaluatedValue += 500;
                } else if (((IAmBlack && !othelloGameState.isBlackTurn()) || (!IAmBlack && othelloGameState
                        .isBlackTurn())) && othelloGameState.isValidMove(row, col)) {
                    evaluatedValue -= 500;
                }
            }
        }

        if (IAmBlack) {
            evaluatedValue += (blackScore - whiteScore);
        } else {
            evaluatedValue += (whiteScore - blackScore);
        }

        return evaluatedValue;
    }

    public int getNaiveEvaluation(OthelloGameState inState) {
        return inState.getWhiteScore() - inState.getBlackScore();
    }

    public void fillThePriorityBoard() {
        priorityBoard[0][0] = 5000;
        priorityBoard[0][1] = -1500;
        priorityBoard[0][2] = 500;
        priorityBoard[0][3] = 400;
        priorityBoard[0][4] = 400;
        priorityBoard[0][5] = 500;
        priorityBoard[0][6] = -1500;
        priorityBoard[0][7] = 5000;
        priorityBoard[1][0] = -1500;
        priorityBoard[1][1] = -2500;
        priorityBoard[1][2] = -225;
        priorityBoard[1][3] = -250;
        priorityBoard[1][4] = -250;
        priorityBoard[1][5] = -225;
        priorityBoard[1][6] = -2500;
        priorityBoard[1][7] = -1500;
        priorityBoard[2][0] = 500;
        priorityBoard[2][1] = -225;
        priorityBoard[2][2] = 15;
        priorityBoard[2][3] = 5;
        priorityBoard[2][4] = 5;
        priorityBoard[2][5] = 15;
        priorityBoard[2][6] = -225;
        priorityBoard[2][7] = 500;
        priorityBoard[3][0] = 400;
        priorityBoard[3][1] = -250;
        priorityBoard[3][2] = 5;
        priorityBoard[3][3] = 25;
        priorityBoard[3][4] = 25;
        priorityBoard[3][5] = 5;
        priorityBoard[3][6] = -250;
        priorityBoard[3][7] = 400;
        priorityBoard[4][0] = 400;
        priorityBoard[4][1] = -250;
        priorityBoard[4][2] = 5;
        priorityBoard[4][3] = 25;
        priorityBoard[4][4] = 25;
        priorityBoard[4][5] = 5;
        priorityBoard[4][6] = -250;
        priorityBoard[4][7] = 400;
        priorityBoard[5][0] = 500;
        priorityBoard[5][1] = -225;
        priorityBoard[5][2] = 15;
        priorityBoard[5][3] = 5;
        priorityBoard[5][4] = 5;
        priorityBoard[5][5] = 15;
        priorityBoard[5][6] = -225;
        priorityBoard[5][7] = 500;
        priorityBoard[6][0] = -1500;
        priorityBoard[6][1] = -2500;
        priorityBoard[6][2] = -225;
        priorityBoard[6][3] = -250;
        priorityBoard[6][4] = -250;
        priorityBoard[6][5] = -225;
        priorityBoard[6][6] = -2500;
        priorityBoard[6][7] = -1500;
        priorityBoard[7][0] = 5000;
        priorityBoard[7][1] = -1500;
        priorityBoard[7][2] = 500;
        priorityBoard[7][3] = 400;
        priorityBoard[7][4] = 400;
        priorityBoard[7][5] = 500;
        priorityBoard[7][6] = -1500;
        priorityBoard[7][7] = 5000;
    }
}