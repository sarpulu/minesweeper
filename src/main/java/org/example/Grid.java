package org.example;

import java.util.Random;

public class Grid {

    private int mode = 0;
    private int rows = 0;
    private int cols = 0;
    private int mineCount =0;

    private int revealedTiles=0;

    private boolean gameLoss = false;
    private boolean gameWin = false;

    private boolean resume = false;
    char[][] chart;
    Tile[][] arr;



    public void setMode(int x){
        mode = x;
    }

    public void updateMode(){
        if (mode==1){
            this.rows = 8;
            this.cols = 8;
            this.mineCount = 10;
            System.out.println("You have selected Beginner");
        }
        if (mode==2){
            this.rows = 16;
            this.cols = 16;
            this.mineCount = 40;
            System.out.println("You have selected Intermediate");
        }
        if (mode==3){
            this.rows = 16;
            this.cols = 30;
            this.mineCount = 99;
            System.out.println("You have selected Expert");
        }
        this.chart = new char[rows][cols];
    }

    public void setVisGrid() {
        int cellNumber;
        char cellP;
        for(int row=0;row<rows;row++) {
            for (int col = 0; col < cols; col++) {
                cellNumber = this.arr[row][col].neighbourNo;
                cellP = (char) (cellNumber+'0');
                if(arr[row][col].isRevealed==false) {
                    if(arr[row][col].isFlagged){
                        this.chart[row][col] = 'F';
                    }
                    else {
                        this.chart[row][col] = '?';
                    }
                }
                if(arr[row][col].isRevealed==true){
                    if(arr[row][col].isMine){
                        this.chart[row][col]='*';
                    }
                    else if (arr[row][col].isFlagged==true) {
                        this.chart[row][col]='F';
                    }
                    else if(arr[row][col].neighbourNo == 0){
                        this.chart[row][col]=' ';
                    }
                    else if(arr[row][col].neighbourNo>0){
                        this.chart[row][col]= cellP;
                    }
                }
            }
        }
    }


    public void printGrid() {

        for(int row=0;row<rows;row++) {
            System.out.println();
            for (int col = 0; col < cols; col++) {
                System.out.print(chart[row][col]+" ");
            }
        }
    }

    public boolean checkInput(String input) {
        if (input==null){
            return false;
        }
        String[] check = input.split("-");
        int part1 = Integer.parseInt(check[0]);
        int part2 = Integer.parseInt(check[1]);
        String part3 = check[2];
        if (part1 < 0 || part1 > cols-1) {
            return false;
        }
        if (part2 < 0 || part2 > rows-1) {
            return false;
        }
        if (!(part3.equals("F") || part3.equals("R"))) {
            return false;
        }
        if (arr[part2][part1].isFlagged && part3.equals("R")){
            System.out.println("Cannot reveal flagged tile.");
            return false;
        }
        if(arr[part2][part1].isRevealed){
            System.out.println("Cannot reveal a revealed tile");
            return false;
        }

        else {
            return true;
        }
    }

    public void setArr(){
        this.arr = new Tile[rows][cols];

        for(int row=0;row<rows;row++) {
            for (int col = 0; col < cols; col++) {
                arr[row][col]= new Tile();
                arr[row][col].isRevealed=false;
                arr[row][col].isMine=false;
                arr[row][col].isFlagged=false;
            }
        }
    }


    public void setMines(){
        int count = mineCount;
        Random r = new Random();

        while(count!=0){
            int ranRow = r.nextInt(rows-1);
            int ranCol = r.nextInt(cols-1);
            if(arr[ranRow][ranCol].isMine==false){
                arr[ranRow][ranCol].isMine=true;
                count--;
            }
        }

    }

    public void setNeighbourNo(){
        for(int row=0;row<rows;row++) {
            for (int col = 0; col < cols; col++) {
                System.out.println();
                if(arr[row][col].isMine){
                    arr[row][col].neighbourNo=9;
                }
                if (!arr[row][col].isMine) {
                    if (col - 1 < 0 && row - 1 < 0) {
                        if (arr[row][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                    else if (col - 1 < 0 && row + 1 >= rows) {
                        if (arr[row - 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }

                    }
                    else if (col + 1 >= cols && row - 1 < 0) {
                        if (arr[row][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                    else if (col + 1 >= cols && row + 1 >= rows) {
                        if (arr[row - 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                    else if (row - 1 < 0) {
                        if (arr[row][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                    else if (row + 1 >= rows) {
                        if (arr[row][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                    else if (col + 1 >= cols) {
                        if (arr[row - 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }

                    }
                    else if (col - 1 < 0) {
                        if (arr[row - 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                    else {
                        if (arr[row - 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row - 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col - 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                        if (arr[row + 1][col + 1].isMine) {
                            arr[row][col].neighbourNo += 1;
                        }
                    }
                }

            }


        }

    }

    public void gameLoss(){
        gameLoss=true;
    }
    public boolean getGameLoss(){
        return gameLoss;
    }

    public void resume(){
        resume=true;
    }

    public void gameWin(){
        if(mode==1){
            if (revealedTiles==54){
                gameWin=true;
            }
        }
        if(mode==2){
            if(revealedTiles==216){
                gameWin=true;
            }
        }
        if(mode==3){
            if (revealedTiles==381){
                gameWin=true;
            }
        }

    }
    public void revealTile(int col, int row) {
        arr[row][col].isRevealed=true;
        setVisGrid();
        revealedTiles +=1;

        if(arr[row][col].isMine){
            gameLoss();
        }


        if (arr[row][col].neighbourNo>0&&arr[row][col].neighbourNo<9){

        }
        if (arr[row][col].neighbourNo==0){
            if (col - 1 < 0 && row - 1 < 0) {
                if(!arr[row][col + 1].isRevealed && !arr[row][col + 1].isFlagged&&!arr[row][col + 1].isMine){
                    revealTile(col + 1, row);
                }
                if(!arr[row+1][col].isRevealed&&!arr[row+1][col].isFlagged&&!arr[row+1][col].isMine){
                    revealTile(col, row+1);
                }
                if(!arr[row+1][col+1].isRevealed&&!arr[row+1][col+1].isFlagged&&!arr[row+1][col+1].isMine){
                    revealTile(col + 1, row+1);
                }
            }
            else if (col - 1 < 0 && row + 1 >= rows) {

                if(!arr[row-1][col].isRevealed&&!arr[row-1][col].isFlagged&&!arr[row-1][col].isMine){
                    revealTile(col,row-1);
                }
                if(!arr[row-1][col+1].isRevealed&&!arr[row-1][col+1].isFlagged&&!arr[row-1][col+1].isMine){
                    revealTile(col+1, row-1);
                }
                if(!arr[row][col+1].isRevealed&&!arr[row][col+1].isFlagged&&!arr[row][col+1].isMine){
                    revealTile(col + 1, row);
                }
            }
            else if (col + 1 >= cols && row - 1 < 0) {

                if(!arr[row][col-1].isRevealed&&!arr[row][col-1].isFlagged&&!arr[row][col-1].isMine){
                    revealTile(col - 1, row);
                }
                if(!arr[row+1][col-1].isRevealed&&!arr[row+1][col-1].isFlagged&&!arr[row+1][col-1].isMine){
                    revealTile(col-1, row+1);
                }
                if(!arr[row+1][col].isRevealed&&!arr[row+1][col].isFlagged&&!arr[row+1][col].isMine){
                    revealTile(col, row+1);
                }

            }
            else if (col + 1 >= cols && row + 1 >= rows) {

                if(!arr[row-1][col-1].isRevealed&&!arr[row-1][col-1].isFlagged&&!arr[row-1][col-1].isMine){
                    revealTile(col - 1, row-1);
                }
                if(!arr[row-1][col].isRevealed&&!arr[row-1][col].isFlagged&&!arr[row-1][col].isMine){
                    revealTile(col, row-1);
                }
                if(!arr[row][col-1].isRevealed&&!arr[row][col-1].isFlagged&&!arr[row][col-1].isMine){
                    revealTile(col - 1, row);
                }

            }
            else if (row - 1 < 0) {
                if(!arr[row][col-1].isRevealed&&!arr[row][col-1].isFlagged&&!arr[row][col-1].isMine){
                    revealTile(col - 1, row);
                }
                if(!arr[row][col+1].isRevealed&&!arr[row][col+1].isFlagged&&!arr[row][col+1].isMine){
                    revealTile(col+1, row);
                }
                if(!arr[row+1][col-1].isRevealed&&!arr[row+1][col-1].isFlagged&&!arr[row+1][col-1].isMine){
                    revealTile(col - 1, row+1);
                }
                if(!arr[row+1][col].isRevealed&&!arr[row+1][col].isFlagged&&!arr[row+1][col].isMine){
                    revealTile(col , row+1);
                }
                if(!arr[row+1][col+1].isRevealed&&!arr[row+1][col+1].isFlagged&&!arr[row+1][col+1].isMine){
                    revealTile(col + 1, row+1);
                }

            }
            else if (row + 1 >= rows) {
                if(!arr[row][col-1].isRevealed&&!arr[row][col-1].isFlagged&&!arr[row][col-1].isMine){
                    revealTile(col - 1, row);
                }
                if(!arr[row][col+1].isRevealed&&!arr[row][col+1].isFlagged&&!arr[row][col+1].isMine){
                    revealTile(col + 1, row);
                }
                if(!arr[row-1][col-1].isRevealed&&!arr[row-1][col-1].isFlagged&&!arr[row-1][col-1].isMine){
                    revealTile(col - 1, row-1);
                }
                if(!arr[row-1][col].isRevealed&&!arr[row-1][col].isFlagged&&!arr[row-1][col].isMine){
                    revealTile(col , row-1);
                }
                if(!arr[row-1][col+1].isRevealed&&!arr[row-1][col+1].isFlagged&&!arr[row-1][col+1].isMine){
                    revealTile(col + 1, row-1);
                }

            }
            else if (col + 1 >= cols) {

                if(!arr[row-1][col].isRevealed&&!arr[row-1][col].isFlagged&&!arr[row-1][col].isMine){
                    revealTile(col, row-1);
                }
                if(!arr[row-1][col-1].isRevealed&&!arr[row-1][col-1].isFlagged&&!arr[row-1][col-1].isMine){
                    revealTile(col - 1, row-1);
                }
                if(!arr[row][col-1].isRevealed&&!arr[row][col-1].isFlagged&&!arr[row][col-1].isMine){
                    revealTile(col - 1, row);
                }
                if(!arr[row+1][col-1].isRevealed&&!arr[row+1][col-1].isFlagged&&!arr[row+1][col-1].isMine){
                    revealTile(col - 1, row+1);
                }
                if(!arr[row+1][col].isRevealed&&!arr[row+1][col].isFlagged&&!arr[row+1][col].isMine){
                    revealTile(col, row+1);
                }

            }
            else if (col - 1 < 0) {

                if(!arr[row-1][col].isRevealed&&!arr[row-1][col].isFlagged&&!arr[row-1][col].isMine){
                    revealTile(col, row-1);
                }
                if(!arr[row-1][col+1].isRevealed&&!arr[row-1][col+1].isFlagged&&!arr[row-1][col+1].isMine){
                    revealTile(col + 1, row-1);
                }
                if(!arr[row][col+1].isRevealed&&!arr[row][col+1].isFlagged&&!arr[row][col+1].isMine){
                    revealTile(col + 1, row);
                }
                if(!arr[row+1][col].isRevealed&&!arr[row+1][col].isFlagged&&!arr[row+1][col].isMine){
                    revealTile(col , row+1);
                }
                if(!arr[row+1][col+1].isRevealed&&!arr[row+1][col+1].isFlagged&&!arr[row+1][col+1].isMine){
                    revealTile(col + 1, row+1);
                }

            }
            else {
                if(!arr[row-1][col-1].isRevealed&&!arr[row-1][col-1].isFlagged&&!arr[row-1][col-1].isMine){
                    revealTile(col - 1, row-1);
                }
                if(!arr[row-1][col].isRevealed&&!arr[row-1][col].isFlagged&&!arr[row-1][col].isMine){
                    revealTile(col, row-1);
                }
                if(!arr[row-1][col+1].isRevealed&&!arr[row-1][col+1].isFlagged&&!arr[row-1][col+1].isMine){
                    revealTile(col + 1, row-1);
                }
                if(!arr[row][col-1].isRevealed&&!arr[row][col-1].isFlagged&&!arr[row][col-1].isMine){
                    revealTile(col - 1, row);
                }
                if(!arr[row][col+1].isRevealed&&!arr[row][col+1].isFlagged&&!arr[row][col+1].isMine){
                    revealTile(col + 1, row);
                }
                if(!arr[row+1][col-1].isRevealed&&!arr[row+1][col-1].isFlagged&&!arr[row+1][col-1].isMine){
                    revealTile(col - 1, row+1);
                }
                if(!arr[row+1][col].isRevealed&&!arr[row+1][col].isFlagged&&!arr[row+1][col].isMine){
                    revealTile(col , row+1);
                }
                if(!arr[row+1][col+1].isRevealed&&!arr[row+1][col+1].isFlagged&&!arr[row+1][col+1].isMine){
                    revealTile(col+1 , row+1);
                }

            }

        }


    }

    public void setFlag(int row, int col) {
        arr[row][col].isFlagged=!(arr[row][col].isFlagged);
    }

    public boolean getGameWin() {
        return gameWin;
    }

    public void revealAll() {
        for(int row=0;row<rows;row++) {
            for (int col = 0; col < cols; col++) {
                arr[row][col].isRevealed=true;
            }
        }
    }
}
