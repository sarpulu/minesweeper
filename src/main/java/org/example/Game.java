package org.example;

import java.util.Scanner;

public class Game {

    public Scanner scanner;
    int mode;
    Grid grid = new Grid();


    public void startGame(){
        scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Please select a difficulty:");
        System.out.println("Press 1 for Beginner, Press 2 for Intermediate, Press 3 for Expert");

        try {
            mode = scanner.nextInt();
        }
        catch (Exception e){
            startGame();
        }
        if (mode==1||mode==2||mode==3){
            if(mode==1){
                System.out.println("You have selected mode: Beginner");
                grid.setMode(mode);
                this.grid=grid;
                playerFirstTurn();

            }
            if(mode==2){
                System.out.println("You have selected mode: Intermediate");
                grid.setMode(mode);
                playerFirstTurn();
            }
            if(mode==3){
                System.out.println("You have selected mode: Expert");
                grid.setMode(mode);
                playerFirstTurn();
            }
        }
        else{
            startGame();
        }

    }

    public void playerFirstTurn(){
        this.grid=grid;
        grid.updateMode();
        grid.setArr();
        grid.setMines();
        grid.setNeighbourNo();
        grid.setVisGrid();
        grid.printGrid();


        playerTurn();

    }

    public void playerTurn() {
        System.out.println();
        System.out.println();
        System.out.println("Select a tile with its co-ordinates");
        System.out.println("To flag tile use F to reveal tile use R");
        System.out.println("e.g. to uncover 00 type 0-0-R or flag 00 type 0-0-F");


        String input="";
        try {
            input = scanner.next();

            if (input.length() < 5) {
                System.out.println("Please use correct input");
                playerTurn();
            }
            if (!grid.checkInput(input)) {
                System.out.println("Please use correct input");
                playerTurn();
            }
            if (grid.checkInput(input)) {
                String[] check = input.split("-");
                int col = Integer.parseInt(check[0]);
                int row = Integer.parseInt(check[1]);
                String cmd = check[2];
                if (cmd.equals("F") || cmd.equals("f")) {
                    grid.setFlag(row, col);
                    grid.setVisGrid();
                    grid.printGrid();
                    playerTurn();
                }
                if (cmd.equals("R") || cmd.equals("r")) {
                    grid.revealTile(col, row);
                    if (grid.getGameLoss()) {
                        System.out.println("You lose");
                        grid.revealAll();
                        grid.setVisGrid();
                        grid.printGrid();
                        startGame();
                    }
                    if (grid.getGameWin()) {
                        System.out.println("You win");
                        grid.revealAll();
                        grid.setVisGrid();
                        grid.printGrid();
                        startGame();

                    }
                    grid.setVisGrid();
                    grid.printGrid();
                    playerTurn();

                }


            }

        }
        catch (Exception e){
            System.out.println("Invalid input, please try again");
            playerTurn();
        }
    }
}
