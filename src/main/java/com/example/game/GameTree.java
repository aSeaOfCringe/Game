package com.example.game;

import java.util.LinkedList;
import java.util.List;

public class GameTree {
    private StateOfGame node;
    private List<GameTree> branches;
    private int countOfBranches = 0;

    public GameTree(StateOfGame node){
        this.node = node;
        this.branches = new LinkedList<>();
    }

    public StateOfGame getNode(){
        return this.node;
    }

    public List<GameTree> getBranches(){
        return this.branches;
    }

    public GameTree getBranch(int i){
        return this.branches.get(i);
    }

    public void addBranch(GameTree branch){
        this.branches.add(branch);
        this.countOfBranches++;
    }

    public int getCountOfBranches(){
        return this.countOfBranches;
    }
}
