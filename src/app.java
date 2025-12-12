import java.util.Random;
import java.util.Scanner;

public class app{

      int[][] grid = new int [4][4];

      private int[][] copyGrid() {
    int[][] copy = new int[4][4];
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
            copy[i][j] = grid[i][j];
        }
    }
    return copy;
}

private boolean gridsAreSame(int[][] oldGrid) {
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
            if(oldGrid[i][j] != grid[i][j]) return false;
        }
    }
    return true;
}


      private void left_action() {

    for (int i = 0; i < 4; i++) {

        int[] temp = new int[4];
        int index = 0;

        // Step 1: compress (remove zeroes)
        for (int j = 0; j < 4; j++) {
            if (grid[i][j] != 0) {
                temp[index++] = grid[i][j];
            }
        }

        // Step 2: merge with skip rule
        for (int j = 0; j < 3; j++) {
            if (temp[j] != 0 && temp[j] == temp[j + 1]) {
                temp[j] *= 2;
                temp[j + 1] = 0;
                j++; // skip next tile after merge
            }
        }

        // Step 3: compress again
        int[] newRow = new int[4];
        index = 0;
        for (int j = 0; j < 4; j++) {
            if (temp[j] != 0) {
                newRow[index++] = temp[j];
            }
        }

        grid[i] = newRow;
    }
}


     private void right_action() {

    for (int i = 0; i < 4; i++) {

        int[] temp = new int[4];
        int index = 3;

        // Step 1: compress (right to left)
        for (int j = 3; j >= 0; j--) {
            if (grid[i][j] != 0) {
                temp[index--] = grid[i][j];
            }
        }

        // Step 2: merge with skip rule
        for (int j = 3; j > 0; j--) {
            if (temp[j] != 0 && temp[j] == temp[j - 1]) {
                temp[j] *= 2;
                temp[j - 1] = 0;
                j--; // skip merged tile
            }
        }

        // Step 3: compress again
        int[] newRow = new int[4];
        index = 3;
        for (int j = 3; j >= 0; j--) {
            if (temp[j] != 0) {
                newRow[index--] = temp[j];
            }
        }

        grid[i] = newRow;
    }
}



      private void up_action() {
    for (int col = 0; col < 4; col++) {

        int[] temp = new int[4];
        int index = 0;

        // compress
        for (int row = 0; row < 4; row++) {
            if (grid[row][col] != 0) {
                temp[index++] = grid[row][col];
            }
        }

        // merge with skip rule
        for (int row = 0; row < 3; row++) {
            if (temp[row] != 0 && temp[row] == temp[row + 1]) {
                temp[row] *= 2;
                temp[row + 1] = 0;
                row++; // ← Skip next tile after merge
            }
        }

        // compress again
        int[] newCol = new int[4];
        index = 0;
        for (int row = 0; row < 4; row++) {
            if (temp[row] != 0) {
                newCol[index++] = temp[row];
            }
        }

        // copy back
        for (int row = 0; row < 4; row++) {
            grid[row][col] = newCol[row];
        }
    }
}

     
     private void down_action() {

    for (int col = 0; col < 4; col++) {

        int[] temp = new int[4];
        int index = 3;

        // Step 1: compress (bottom → top)
        for (int row = 3; row >= 0; row--) {
            if (grid[row][col] != 0) {
                temp[index--] = grid[row][col];
            }
        }

        // Step 2: merge with skip rule
        for (int row = 3; row > 0; row--) {
            if (temp[row] != 0 && temp[row] == temp[row - 1]) {
                temp[row] *= 2;
                temp[row - 1] = 0;
                row--; // skip next after merge
            }
        }

        // Step 3: compress again
        int[] newCol = new int[4];
        index = 3;
        for (int row = 3; row >= 0; row--) {
            if (temp[row] != 0) {
                newCol[index--] = temp[row];
            }
        }

        // Step 4: write back
        for (int row = 0; row < 4; row++) {
            grid[row][col] = newCol[row];
        }
    }
}

private boolean isGameOver() {

    // 1️⃣ Check for empty spaces (still playable)
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            if (grid[i][j] == 0) return false;
        }
    }

    // 2️⃣ Check horizontal merges possible
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 3; j++) { 
            if (grid[i][j] == grid[i][j+1]) return false;
        }
    }

    // 3️⃣ Check vertical merges possible
    for (int j = 0; j < 4; j++) {
        for (int i = 0; i < 3; i++) {
            if (grid[i][j] == grid[i+1][j]) return false;
        }
    }

    return true; // No moves left → Game Over
}



private void printhelper() {
    System.out.println("\n-------------------------");
    for (int i = 0; i < 4; i++) {
        System.out.print("| ");
        for (int j = 0; j < 4; j++) {
            if(grid[i][j] == 0)
                System.out.print(".\t");   // show empty as .
            else
                System.out.print(grid[i][j] + "\t");
        }
        System.out.println("|");
    }
    System.out.println("-------------------------");
}


      private static Random r = new Random();

      public void spawn() {
    if(!hasEmptyCell()) return; // safety check

    int row, column;
    do {
        row = r.nextInt(4);
        column = r.nextInt(4);
    } while (grid[row][column] != 0);

    grid[row][column] = (r.nextInt(10) < 9) ? 2 : 4;
}

private boolean hasEmptyCell() {
    for(int i = 0; i < 4; i++){
        for(int j = 0; j < 4; j++){
            if(grid[i][j] == 0) return true;
        }
    }
    return false;
}


      public void init(){
            spawn();
            spawn();
            
      }
      public static void main(String a[]){
            
            

            app game = new app();
            System.out.println("Welcome to 2048");
            Scanner input = new Scanner(System.in);
            System.out.println("\nUser Manual \n 1. w=up \n 2. a=left \n 3. d=right \n 4. s=down \n 5. 0=exit \n");
            

            game.init();
            game.printhelper();
            
            while(true){
                  System.out.println("\nEnter a key \n 1. w=up \n 2. a=left \n 3. d=right \n 4. s=down \n 5. 0=exit \n");
                  String i = input.nextLine().trim().toLowerCase();

                  if(i.equals("0")){
                        System.out.println("\nGame ended\n");
                        break;
                  }

                  int[][] beforemove = game.copyGrid();
                  
                  switch (i) {
                        case "w": game.up_action(); break;
                        case "a": game.left_action(); break;
                        case "d": game.right_action(); break;
                        case "s": game.down_action(); break;

                  
                        default:
                              System.out.println("Please enter a valid input");                  
                              continue;
                  }

                  if(!game.gridsAreSame(beforemove)){
                        game.spawn();

                  }
                  else{
                        System.out.println("No movement happened");
                  }

                  game.printhelper();

                  if (game.isGameOver()) {
    System.out.println("\n🚨 GAME OVER! No more moves possible. 🚨");
    break;
}



            }

            

            

      }
}