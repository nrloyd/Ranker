package com.example.ranker;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private String data;
    private BinaryTree left;
    private BinaryTree right;
    private boolean isRoot;

    public BinaryTree(String data) {
        this.data = data;
        this.isRoot = true;
    }

    public BinaryTree left() { return left; }

    public void setLeft(BinaryTree left) {
        if(left == null) {
            this.left = null;
        } else {
            this.left = left;
            left.notRoot();
        }
    }

    public boolean hasLeft() { return left != null; }

    public BinaryTree right() { return right; }

    public void setRight(BinaryTree right) {
        this.right = right;
        right.notRoot();
    }

    public boolean hasRight() { return right != null; }

    public String getData() { return data; }

    public boolean isRoot() { return isRoot; }

    public void notRoot() { this.isRoot = false; }

    public int size() {
        int size = 1;
        if(this.hasLeft()) size += this.left.size();
        if(this.hasRight()) size += this.right.size();
        return size;
    }

    //continue traversing rightward down the tree until reaching the rightmost node
    public BinaryTree rightMost() {
        BinaryTree rightMost = this;
        while(rightMost.hasRight()){
            rightMost = rightMost.right();
        }
        return rightMost;
    }

    public String toString() {
        String toReturn = data;
        BinaryTree right = this;
        while(right.hasRight()){
            right = right.right();
            toReturn = toReturn + " - " + right.getData();
        }
        if(this.hasLeft()){
            toReturn = toReturn + "\n | \n" + this.left().toString();
        }
        return toReturn;
    }

    public String[][] toStringArray() {
        String[][] toReturn = new String[2][this.size()];
        int currentRank = 1;
        int index = 0;
        BinaryTree currentRoot = this;
        while (currentRoot != null) {
            toReturn[0][index] = Integer.toString(currentRank);
            toReturn[1][index] = currentRoot.getData();
            index++;

            BinaryTree currentRight = currentRoot;
            while (currentRight.hasRight()) {
                currentRight = currentRight.right();
                toReturn[0][index] = Integer.toString(currentRank);
                toReturn[1][index] = currentRight.getData();
                index++;
            }

            currentRank = index + 1;
            currentRoot = currentRoot.left();
        }

        return toReturn;
    }

    public static List<BinaryTree> generateTreeList(String[] dataList) {
        List<BinaryTree> treeList = new ArrayList<BinaryTree>();
        for(String s : dataList) {
            treeList.add(new BinaryTree(s));
        }
        return treeList;
    }


}
