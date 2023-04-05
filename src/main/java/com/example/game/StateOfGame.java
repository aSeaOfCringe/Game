package com.example.game;

public class StateOfGame {
    private int i1, i2, j1, j2;
    private final int level;
    private double heuristicResult;
    private final boolean isMaximizer;
    private String[][] gameBoard;

    public StateOfGame(int i1, int i2, int j1, int j2, int level, boolean isMaximizer, String[][] gameBoard){
        this.i1 = i1;
        this.i2 = i2;
        this.j1 = j1;
        this.j2 = j2;
        this.level = level;
        this.isMaximizer = isMaximizer;
        this.gameBoard = gameBoard;
    }

    public int getI1(){
        return i1;
    }

    public int getI2(){
        return i2;
    }

    public int getJ1(){
        return j1;
    }

    public int getJ2(){
        return j2;
    }
    public void setI1(int i1){
        this.i1 = i1;
    }
    public void setI2(int i2){
        this.i2 = i2;
    }
    public void setJ1(int j1){
        this.j1 = j1;
    }
    public void setJ2(int j2){
        this.j2 = j2;
    }
    public int getLevel(){
        return this.level;
    }

    public double getHeuristicResult(){
        return this.heuristicResult;
    }

    public void setHeuristicResult(double heuristicResult){
        this.heuristicResult = heuristicResult;
    }

    public boolean getIsMaximizer(){
        return this.isMaximizer;
    }

    public String[][] getGameBoard(){
        return this.gameBoard;
    }

    public void calculateHeuristic(String max, String min){ // Calculates heuristic value to current state
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if ((i-1 < 0 && j-1 < 0) || (i+1>2 && j-1<0) || (i+1>2 && j+1>2) || (i-1<0 && j+1>2)){
                    if (this.gameBoard[i][j].equals(max)) this.heuristicResult +=1.1;
                    if (this.gameBoard[i][j].equals(min)) this.heuristicResult -=1.1;
                }
                else if (i-1<0 || j-1<0 || i+1>2 || j+1>2){
                    if (this.gameBoard[i][j].equals(max)) this.heuristicResult +=1;
                    if (this.gameBoard[i][j].equals(min)) this.heuristicResult -=1;
                }
                else {
                    if (this.gameBoard[i][j].equals(max)) this.heuristicResult +=0.9;
                    if (this.gameBoard[i][j].equals(min)) this.heuristicResult -=0.9;
                }
            }
        }
    }
}
