import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OthelloGameState {
    private OthelloCell[][] board = new OthelloCell[8][8];
    private boolean blackTurn;
    private boolean gameOver;
    private ArrayList<OthelloBoardChangeListener> boardChangeListeners;

    public OthelloGameState() {
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                this.board[i][j] = OthelloCell.NONE;
            }
        }

        this.board[3][3] = OthelloCell.WHITE;
        this.board[3][4] = OthelloCell.BLACK;
        this.board[4][3] = OthelloCell.BLACK;
        this.board[4][4] = OthelloCell.WHITE;
        this.blackTurn = true;
        this.gameOver = false;
        this.boardChangeListeners = new ArrayList<>();
    }

    public OthelloGameState(OthelloGameState othelloGameState) {
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                this.board[i][j] = othelloGameState.board[i][j];
            }
        }

        this.blackTurn = othelloGameState.blackTurn;
        this.gameOver = othelloGameState.gameOver;
        this.boardChangeListeners = new ArrayList<>();
    }

    public OthelloGameState clone() {
        return new OthelloGameState(this);
    }

    public void showBoard() {
        for (int i = 0; i < 8; ++i) {
            System.out.print("|");
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == OthelloCell.WHITE) {
                    System.out.print("0|");
                } else if (board[i][j] == OthelloCell.BLACK) {
                    System.out.print("X|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println("");
        }
    }

    private void fireBoardChanged(OthelloGameState othelloGameState) {
        Iterator iterator = this.boardChangeListeners.iterator();

        while(iterator.hasNext()) {
            OthelloBoardChangeListener var3 = (OthelloBoardChangeListener)iterator.next();
            var3.boardChanged(othelloGameState);
        }

    }

    private void fireNewTurn(OthelloGameState othelloGameState) {
        Iterator iterator = this.boardChangeListeners.iterator();

        while(iterator.hasNext()) {
            OthelloBoardChangeListener var3 = (OthelloBoardChangeListener)iterator.next();
            var3.newTurn(othelloGameState);
        }

    }

    public OthelloCell getCell(int xCoordinate, int yCoordinate) {
        return this.board[xCoordinate][yCoordinate];
    }

    public boolean isBlackTurn() {
        return this.blackTurn;
    }

    public boolean gameIsOver() {
        return this.gameOver;
    }

    public boolean isValidMove(int xCoordinate, int yCoordinate) {
        if (this.board[xCoordinate][yCoordinate] != OthelloCell.NONE) {
            return false;
        } else {
            return this.checkValid(xCoordinate, yCoordinate, 0, -1) || this.checkValid(xCoordinate, yCoordinate, 0, 1) || this.checkValid(xCoordinate, yCoordinate, -1, -1) || this.checkValid(xCoordinate, yCoordinate, -1, 0) || this.checkValid(xCoordinate, yCoordinate, -1, 1) || this.checkValid(xCoordinate, yCoordinate, 1, -1) || this.checkValid(xCoordinate, yCoordinate, 1, 0) || this.checkValid(xCoordinate, yCoordinate, 1, 1);
        }
    }

    public void makeMove(int xCoordinate, int yCoordinate) {
        if (!this.isValidMove(xCoordinate, yCoordinate)) {
            throw new RuntimeException("Invalid Move Exception!");
        } else {
            this.board[xCoordinate][yCoordinate] = this.isBlackTurn() ? OthelloCell.BLACK : OthelloCell.WHITE;
            this.fireBoardChanged(this.clone());
            this.flipTiles(xCoordinate, yCoordinate, 0, -1);
            this.flipTiles(xCoordinate, yCoordinate, 0, 1);
            this.flipTiles(xCoordinate, yCoordinate, -1, -1);
            this.flipTiles(xCoordinate, yCoordinate, -1, 0);
            this.flipTiles(xCoordinate, yCoordinate, -1, 1);
            this.flipTiles(xCoordinate, yCoordinate, 1, -1);
            this.flipTiles(xCoordinate, yCoordinate, 1, 0);
            this.flipTiles(xCoordinate, yCoordinate, 1, 1);
            this.blackTurn = !this.blackTurn;
            if (!this.validMoveExists()) {
                this.blackTurn = !this.blackTurn;
                if (!this.validMoveExists()) {
                    this.gameOver = true;
                }
            }

            this.fireNewTurn(this);
        }
    }

    private boolean validMoveExists() {
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                if (this.isValidMove(i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<String> getValidMoves() {
        List<String> validMoves = new ArrayList<>();
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                if (this.isValidMove(i, j)) {
                    validMoves.add(i + " " + j);
                }
            }
        }

        return validMoves;
    }

    private boolean checkValid(int var1, int var2, int var3, int var4) {
        return this.getDistance(var1, var2, var3, var4) != 0;
    }

    private void flipTiles(int i1, int j1, int i2, int j2) {
        for(int var5 = this.getDistance(i1, j1, i2, j2) - 1; var5 > 0; --var5) {
            int var6 = i1 + var5 * i2;
            int var7 = j1 + var5 * j2;
            this.flipTile(var6, var7);
        }

    }

    private int getDistance(int i1, int j1, int i2, int j2) {
        OthelloCell var5 = this.isBlackTurn() ? OthelloCell.BLACK : OthelloCell.WHITE;
        OthelloCell var6 = this.isBlackTurn() ? OthelloCell.WHITE : OthelloCell.BLACK;
        int var7 = i1;
        int var8 = j1;
        int var9 = 0;
        boolean var10 = false;

        while(true) {
            var7 += i2;
            var8 += j2;
            ++var9;
            if (var7 < 0 || var7 >= 8 || var8 < 0 || var8 >= 8) {
                return 0;
            }

            if (this.board[var7][var8] == OthelloCell.NONE) {
                return 0;
            }

            if (this.board[var7][var8] == var6) {
                var10 = true;
            } else if (this.board[var7][var8] == var5) {
                if (var10) {
                    return var9;
                }

                return 0;
            }
        }
    }

    private void flipTile(int row, int column) {
        if (this.board[row][column] == OthelloCell.WHITE) {
            this.board[row][column] = OthelloCell.BLACK;
            this.fireBoardChanged(this.clone());
        } else if (this.board[row][column] == OthelloCell.BLACK) {
            this.board[row][column] = OthelloCell.WHITE;
            this.fireBoardChanged(this.clone());
        }

    }

    public int getBlackScore() {
        return this.countScore(OthelloCell.BLACK);
    }

    public int getWhiteScore() {
        return this.countScore(OthelloCell.WHITE);
    }

    private int countScore(OthelloCell cell) {
        int score = 0;
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                if (this.board[i][j] == cell) {
                    score++;
                }
            }
        }
        return score;
    }
}
