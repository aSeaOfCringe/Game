package com.example.game;


public class Bot {

    public GameTree tree;

    public Bot(GameTree tree){
        this.tree = tree;
    }

    public String[][] copyGameBoard(String[][] gameBoard){ // Supportive function manual copy of array
        String[][] array = new String[3][3];
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                array[i][j] = gameBoard[i][j];
            }
        }
        return array;
    }

    public void printBoard(String[][] array){ //Helper function for logging information (not required for algorithm functionality)
        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                System.out.print(array[i][j]);
                if (array[i][j].equals(" ")) System.out.print("_");
            }
            System.out.println("");
        }
    }

    public void generatePartialTree(int i1, int i2, int j1, int j2, String[][] currentGameBoard,
                                    String figureMax, String figureMin, GameTree node, int branchNr){

        if (node == null) {
            String[][] array;
            array = copyGameBoard(currentGameBoard);
            this.tree = new GameTree(new StateOfGame(i1,i2,j1,j2,0,true,array));
            node = this.tree;
        }
        if (node.getNode().getLevel()<4){ //Here You can set, how many levels of game tree will be generated
            String figure;
            if (node.getNode().getIsMaximizer()) figure=figureMax;
            else figure = figureMin;
            for (int i=0; i<3; i++){
                for (int j=0; j<3; j++){
                    if (currentGameBoard[i][j].equals(figure)){
                        if (i-1>=0 && currentGameBoard[i-1][j].equals(" ")) {
                            String[][] array = copyGameBoard(currentGameBoard);
                            addNewBranch(i, i-1, j, j, node, figureMax, figureMin, array);
                        }
                        if (i-1>=0 && j-1>=0 && currentGameBoard[i-1][j-1].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i-1, j, j-1, node, figureMax, figureMin, array);
                        }
                        if (i-2>=0 && currentGameBoard[i-2][j].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i-2, j, j,node, figureMax, figureMin, array);
                        }
                        if (i+1<3 && currentGameBoard[i+1][j].equals(" ")) {
                            String[][] array = copyGameBoard(currentGameBoard);
                            addNewBranch(i, i+1, j, j, node, figureMax, figureMin, array);
                        }
                        if (i+1<3 && j+1<3 && currentGameBoard[i+1][j+1].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i+1, j, j+1, node, figureMax, figureMin, array);
                        }
                        if (i+2<3 && currentGameBoard[i+2][j].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i+2, j, j, node, figureMax, figureMin, array);
                        }
                        if (j-1>=0 && currentGameBoard[i][j-1].equals(" ")) {
                            String[][] array = copyGameBoard(currentGameBoard);
                            addNewBranch(i, i, j, j-1, node, figureMax, figureMin, array);
                        }
                        if (j-2>=0 && currentGameBoard[i][j-2].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i, j, j-2, node, figureMax, figureMin, array);
                        }
                        if (j+1<3 && currentGameBoard[i][j+1].equals(" ")) {
                            String[][] array = copyGameBoard(currentGameBoard);
                            addNewBranch(i, i, j, j+1, node,figureMax, figureMin, array);
                        }
                        if (j+2<3 && currentGameBoard[i][j+2].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i, j, j+2, node, figureMax, figureMin, array);
                        }
                        if (i-1>=0 && j+1<3 && currentGameBoard[i-1][j+1].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i-1, j, j+1, node, figureMax, figureMin, array);
                        }
                        if (i+1<3 && j-1>=0 && currentGameBoard[i+1][j-1].equals(" ")){
                            String[][] array = copyGameBoard(currentGameBoard);
                            array[i][j] = new String(" ");
                            addNewBranch(i, i+1, j, j-1, node, figureMax, figureMin, array);
                        }
                    }
                }
            }
            int k=0;
            while (node.getCountOfBranches()>k){
                generatePartialTree(i1, i2, j1, j2,node.getBranch(k).getNode().getGameBoard(),
                    figureMax, figureMin, node.getBranches().get(k), k);
                k++;
            }
        }
    }

    public void addNewBranch(int i1, int i2, int j1, int j2, GameTree node,
                             String figureMax, String figureMin, String[][] array){ //Adds new branch to partial game tree
        String figure;
        if (node.getNode().getIsMaximizer()) figure = new String(figureMax);
        else figure = new String(figureMin);
        makeMove(array, i2, j2, figure);
        node.addBranch(new GameTree(new StateOfGame(i1, i2, j1, j2, node.getNode().getLevel()+1,
                 !node.getNode().getIsMaximizer(), array)));
    }

    public void makeMove(String[][]array, int i, int j, String figure){ //Supportive function - changes opponents figures into players figures
        array[i][j] = new String(figure);
        if (i-1 >= 0 && !array[i-1][j].equals(figure) && !array[i-1][j].equals(" ")) array[i-1][j] = new String(figure);
        if (j-1 >= 0 && !array[i][j-1].equals(figure) && !array[i][j-1].equals(" ")) array[i][j-1] = new String(figure);
        if (j+1 <= 2 && !array[i][j+1].equals(figure) && !array[i][j+1].equals(" ")) array[i][j+1] = new String(figure);
        if (i+1 <= 2 && !array[i+1][j].equals(figure) && !array[i+1][j].equals(" ")) array[i+1][j] = new String(figure);

    }

    public void chooseTheMove(GameTree tree, String figureMax, String figureMin){ //Here the Minimax algorithm is used
        int k=0;
        if (tree.getCountOfBranches()==0) {
            tree.getNode().calculateHeuristic(figureMax, figureMin);
            printBoard(tree.getNode().getGameBoard());
            System.out.println("Heuristic value is " + tree.getNode().getHeuristicResult());
            return;
        }
        while (tree.getCountOfBranches()>k){

            chooseTheMove(tree.getBranch(k), figureMax, figureMin);
            if (tree.getNode().getIsMaximizer()){
                if (k==0 || tree.getNode().getHeuristicResult() < tree.getBranch(k).getNode().getHeuristicResult()){
                    tree.getNode().setHeuristicResult(tree.getBranch(k).getNode().getHeuristicResult());
                    tree.getNode().setI1(tree.getBranch(k).getNode().getI1());
                    tree.getNode().setI2(tree.getBranch(k).getNode().getI2());
                    tree.getNode().setJ1(tree.getBranch(k).getNode().getJ1());
                    tree.getNode().setJ2(tree.getBranch(k).getNode().getJ2());
                }
            }
            else {
                if (k==0 || tree.getNode().getHeuristicResult() > tree.getBranch(k).getNode().getHeuristicResult())
                    tree.getNode().setHeuristicResult(tree.getBranch(k).getNode().getHeuristicResult());
            }
            k++;
        }
    }

}
