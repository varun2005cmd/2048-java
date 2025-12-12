import java.util.Scanner;

public class ConsoleUI {

    public static void main(String[] args) {

        Game2048 game = new Game2048();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to 2048!");

        while(true) {
            System.out.println("\nScore: " + game.getScore());
            printBoard(game.getGrid());

            if(game.hasWon())
                System.out.println("\n🎉 YOU WIN! You made 2048! Type R to restart or continue playing.");

            if(game.isGameOver()) {
                System.out.println("\n💀 GAME OVER — No moves left! Type R to restart or 0 to quit.");
            }

            System.out.println("\nMove: (w/a/s/d), R = restart, 0 = exit");
            String in = sc.nextLine().trim().toLowerCase();

            if(in.equals("0")) break;
            if(in.equals("r")) { game.restart(); continue; }

            game.move(in);
        }

        sc.close();
    }

    private static void printBoard(int[][] grid) {
        System.out.println("\n-------------------------");
        for(int[] row : grid) {
            System.out.print("| ");
            for(int cell : row)
                System.out.print((cell == 0 ? "." : cell) + "\t");
            System.out.println("|");
        }
        System.out.println("-------------------------");
    }
}
