package com.example.ranker;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RankGroupTest {

    static DataManager dataManager;

    @BeforeClass
    public static void classSetup() {
        dataManager = DataManager.getInstance();
    }

    @Test
    public void rankerTest() {
        RankGroup channels = dataManager.getGroups().get(3);

        //at first, the group is unsorted and the internals are uninitialized
        assertEquals(channels.isSorted(), 0);
        assertEquals(channels.isInitialized(), 0);

        //after presenting the first two items and selecting a winner, the internals are initialized but the group is still unsorted
        String[] strings = channels.presentTwoItems();
        String[] toCompare = new String[]{"ABC","NBC"};
        assertEquals(channels.isInitialized(),1);
        assertEquals(strings,toCompare);
        channels.selectWinner(1);
        assertEquals(channels.isSorted(),0);

        //since the list is not sorted, presentRanking() should return null
        assertEquals(channels.presentRanking(), null);

        strings = channels.presentTwoItems();
        toCompare[0] = "FOX";
        toCompare[1] = "CBS";
        assertEquals(strings,toCompare);
        channels.selectWinner(0);

        strings = channels.presentTwoItems();
        toCompare[0] = "NBC";
        toCompare[1] = "PBS";
        assertEquals(strings,toCompare);
        channels.selectWinner(0);

        strings = channels.presentTwoItems();
        toCompare[0] = "ABC";
        //toCompare[1] = "PBS";
        assertEquals(strings,toCompare);
        channels.selectWinner(-1);

        strings = channels.presentTwoItems();
        toCompare[0] = "FOX";
        toCompare[1] = "Univision";
        assertEquals(strings,toCompare);
        channels.selectWinner(1);

        strings = channels.presentTwoItems();
        toCompare[0] = "NBC";
        //toCompare[1] = "Univision";
        assertEquals(strings,toCompare);
        channels.selectWinner(0);

        strings = channels.presentTwoItems();
        toCompare[0] = "ABC";
        //toCompare[1] = "Univision";
        assertEquals(strings,toCompare);
        channels.selectWinner(1);

        strings = channels.presentTwoItems();
        //toCompare[0] = "ABC";
        toCompare[1] = "FOX";
        assertEquals(strings,toCompare);
        channels.selectWinner(-1);

        //at this point, the list should be both initialized and sorted
        assertEquals(channels.isInitialized(),1);
        assertEquals(channels.isSorted(),1);

        //since list is sorted, presentTwoItems() should return null
        assertEquals(channels.presentTwoItems(),null);

        //since list is sorted, presentRanking() should return a correct array
        String[][] stringArrays = channels.presentRanking();
        toCompare = new String[]{"1","2","3","3","3","6"};
        assertEquals(toCompare, stringArrays[0]);
        toCompare = new String[]{"NBC","Univision","ABC","PBS","FOX","CBS"};
        assertEquals(toCompare, stringArrays[1]);

        channels.reset();
        assertEquals(0, channels.isSorted());
        assertEquals(1, channels.isInitialized());




    }

}