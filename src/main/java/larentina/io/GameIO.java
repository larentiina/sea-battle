package larentina.io;

import larentina.model.Cell;
import larentina.model.Field;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.Scanner;


@RequiredArgsConstructor
public class GameIO {

    private final Scanner scanner;

    public String readLine(String input) {
        String line;
        while (true) {
            try{
                System.out.println(input);
                line = scanner.nextLine();
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Ошибка ввода. Попробуйте снова.");
            }
        }
        return line;
    }
    public int readInt(String input) {
        int value;
        while (true) {
            try {
                System.out.println(input);
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: необходимо ввести число. Попробуйте снова.");
            }
        }
        return value;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printField(Field field, boolean gameMode) {
        System.out.print(" \t");
        for (int i = 1; i <= Field.X_SIZE; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();
        System.out.print(" \t");
        for (int i = 1; i <= Field.X_SIZE; i++) {
            System.out.print("_\t");
        }
        System.out.println();
        for (int i = 0; i < Field.X_SIZE; i++) {
            System.out.print(i + 1 + "|\t");
            for (int j = 0; j < Field.Y_SIZE; j++) {
                System.out.print(imageCell(field.getField().get(i).get(j), gameMode) + "\t");
            }
            System.out.println();
        }
    }
    private char imageCell(Cell cell, boolean gameMode) {
        return switch (cell.getCellType()) {
            case FULL -> gameMode ? 'K' : '*';
            case EMPTY, FORBIDDEN -> gameMode && cell.getCellType() == Cell.CellType.FORBIDDEN ? '-' : '*';
            case KILLED_EMPTY -> 'X';
            case KILLED_SHIP -> 'K';
            default -> ' ';
        };
    }
    public void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

}
