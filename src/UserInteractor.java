/**
 * Created by gonzalonunez on 3/21/17.
 */
import java.awt.*;
import java.util.Scanner;

public class UserInteractor implements GameActionPerformer {
    Scanner scanner;

    public UserInteractor() {
        scanner = new Scanner(System.in);
    }

    public void close() {
        scanner.close();
    }

    public Point tileAction(Tile tile, Board board) {
        //TODO: Display the tile and the current board to the user. Maybe we want to show valid spots with numbers on them?

        System.out.println("Where would you like to place your tile?");

        int x = getXCoordinate();
        int y = getYCoordinate();

        return new Point(x, y);
    }

    private int getXCoordinate() {
        return getIntegerInputForPrompt("X: ");
    }

    private int getYCoordinate() {
        return getIntegerInputForPrompt("Y: ");
    }

    private int getIntegerInputForPrompt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                return input;
            } catch (Exception e) {
                System.out.println("Please provide an integer.");
                scanner.nextLine();
            }
        }
    }

    public BuildAction buildAction(Board board) {
        //TODO: Build action(s)...
        return new CreateSettlementAction();
    }
}