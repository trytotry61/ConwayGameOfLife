package com.conway.backend;

public class BackendTest {

    public static void main(String[] args) {
        GameEngine game = new GameEngine(5, 5);
        game.getBoard().setCell(2, 1, true);
        game.getBoard().setCell(2, 2, true);
        game.getBoard().setCell(2, 3, true);
        System.out.println("Начальное состояние:");
        printBoard(game);
        game.nextGeneration();
        System.out.println("\nСледующее поколение:");
        printBoard(game);
        System.out.println("\nПоколение: " + game.getGeneration());
        System.out.println(
                "Живых клеток: " + game.getAliveCellsCount()
        );
    }
    private static void printBoard(GameEngine game) {
        GameBoard board = game.getBoard();
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.isAlive(row, col)) {
                    System.out.print("■ ");
                } else {
                    System.out.print("□ ");
                }
            }
            System.out.println();
        }
    }
}