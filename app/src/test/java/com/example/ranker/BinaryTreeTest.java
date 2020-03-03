package com.example.ranker;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    private static BinaryTree tree;
    private static BinaryTree level5;
    private static BinaryTree level3;

    @BeforeClass
    public static void classSetup() {
        tree = new BinaryTree("six");
        tree.setRight(new BinaryTree("six"));
        BinaryTree level2 = new BinaryTree("five");
        level3 = new BinaryTree("four");
        BinaryTree level4 = new BinaryTree("four");
        level5 = new BinaryTree("four");
        BinaryTree level6 = new BinaryTree("three");
        tree.setLeft(level2);
        level2.setLeft(level3);
        level3.setRight(level4);
        level4.setRight(level5);
        level3.setLeft(level6);
        level6.setRight(new BinaryTree("three"));
    }


    @Test
    public void rightMost() {
        assertSame(level5, level3.rightMost());
    }

    @Test
    public void testToString() {
        assertEquals(tree.toString(), "six - six\n | \nfive\n | \nfour - four - four\n | \nthree - three");
    }

    @Test
    public void testSize() {
        assertEquals(tree.size(), 8);
        assertEquals(level3.size(),5);
    }

    @Test
    public void testToStringArray() {
        String[] ranks = new String[]{"1","1","3","4","4","4","7","7"};
        String[] strings = new String[]{"six","six","five","four","four","four","three","three"};
        String[][] treeList = new String[][]{ranks,strings};
        assertEquals(tree.toStringArray(), treeList);
    }
}