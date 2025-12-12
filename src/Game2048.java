import java.util.Random;

public class Game2048 {

    private int score = 0;
    private boolean won = false;

    public int getScore() { return score; }
    public boolean hasWon() { return won; }

    private int[][] grid = new int[4][4];
    private Random r = new Random();

    public Game2048() {
        restart();
    }

    public int[][] getGrid() {
        return grid;
    }

    public void restart() {
        grid = new int[4][4];
        score = 0;
        won = false;
        spawn();
        spawn();
    }

    private boolean hasEmptyCell() {
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                if(grid[i][j] == 0) return true;
        return false;
    }

    public void spawn() {
        if(!hasEmptyCell()) return;

        int row, col;
        do {
            row = r.nextInt(4);
            col = r.nextInt(4);
        } while (grid[row][col] != 0);

        grid[row][col] = (r.nextInt(10) < 9) ? 2 : 4;
    }

    public boolean isGameOver() {
        if(hasEmptyCell()) return false;

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j] == grid[i][j + 1]) return false;

        for (int j = 0; j < 4; j++)
            for (int i = 0; i < 3; i++)
                if (grid[i][j] == grid[i + 1][j]) return false;

        return true;
    }

    private int[][] copyGrid() {
        int[][] copy = new int[4][4];
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                copy[i][j] = grid[i][j];
        return copy;
    }

    private boolean gridsAreSame(int[][] oldGrid) {
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                if(oldGrid[i][j] != grid[i][j]) return false;
        return true;
    }

    public void move(String direction) {
        if(isGameOver()) return;

        int[][] before = copyGrid();

        switch (direction) {
            case "w": up_action(); break;
            case "a": left_action(); break;
            case "d": right_action(); break;
            case "s": down_action(); break;
            default: return;
        }

        if(!gridsAreSame(before)) spawn();
    }

    //--------------------------------------------------------//

    private void left_action() {
        for (int i = 0; i < 4; i++) {
            int[] temp = new int[4];
            int index = 0;

            for (int j = 0; j < 4; j++)
                if (grid[i][j] != 0)
                    temp[index++] = grid[i][j];

            for (int j = 0; j < 3; j++)
                if (temp[j] != 0 && temp[j] == temp[j + 1]) {
                    temp[j] *= 2;
                    score += temp[j];
                    if(temp[j] == 2048) won = true;
                    temp[j + 1] = 0;
                    j++;
                }

            int[] newRow = new int[4];
            index = 0;

            for (int j = 0; j < 4; j++)
                if (temp[j] != 0)
                    newRow[index++] = temp[j];

            grid[i] = newRow;
        }
    }

    private void right_action() {
        for (int i = 0; i < 4; i++) {
            int[] temp = new int[4];
            int index = 3;

            for (int j = 3; j >= 0; j--)
                if (grid[i][j] != 0)
                    temp[index--] = grid[i][j];

            for (int j = 3; j > 0; j--)
                if (temp[j] != 0 && temp[j] == temp[j - 1]) {
                    temp[j] *= 2;
                    score += temp[j];
                    if(temp[j] == 2048) won = true;
                    temp[j - 1] = 0;
                    j--;
                }

            int[] newRow = new int[4];
            index = 3;

            for (int j = 3; j >= 0; j--)
                if (temp[j] != 0)
                    newRow[index--] = temp[j];

            grid[i] = newRow;
        }
    }

    private void up_action() {
        for (int col = 0; col < 4; col++) {
            int[] temp = new int[4];
            int index = 0;

            for (int row = 0; row < 4; row++)
                if (grid[row][col] != 0)
                    temp[index++] = grid[row][col];

            for (int row = 0; row < 3; row++)
                if (temp[row] != 0 && temp[row] == temp[row + 1]) {
                    temp[row] *= 2;
                    score += temp[row];
                    if(temp[row] == 2048) won = true;
                    temp[row + 1] = 0;
                    row++;
                }

            int[] newCol = new int[4];
            index = 0;

            for (int row = 0; row < 4; row++)
                if (temp[row] != 0)
                    newCol[index++] = temp[row];

            for (int row = 0; row < 4; row++)
                grid[row][col] = newCol[row];
        }
    }

    private void down_action() {
        for (int col = 0; col < 4; col++) {
            int[] temp = new int[4];
            int index = 3;

            for (int row = 3; row >= 0; row--)
                if (grid[row][col] != 0)
                    temp[index--] = grid[row][col];

            for (int row = 3; row > 0; row--)
                if (temp[row] != 0 && temp[row] == temp[row - 1]) {
                    temp[row] *= 2;
                    score += temp[row];
                    if(temp[row] == 2048) won = true;
                    temp[row - 1] = 0;
                    row--;
                }

            int[] newCol = new int[4];
            index = 3;

            for (int row = 3; row >= 0; row--)
                if (temp[row] != 0)
                    newCol[index--] = temp[row];

            for (int row = 0; row < 4; row++)
                grid[row][col] = newCol[row];
        }
    }
}
