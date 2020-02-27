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
        this.left = left;
        left.notRoot();
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

    public static List<BinaryTree> generateTreeList(String[] dataList) {
        List<BinaryTree> treeList = new ArrayList<BinaryTree>();
        for(String s : dataList) {
            treeList.add(new BinaryTree(s));
        }
        return treeList;
    }


}
