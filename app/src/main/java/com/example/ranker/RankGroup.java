package com.example.ranker;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RankGroup implements Parcelable {
    private String mTitle;
    private String[] mStrings;
    private int mSorted; //0 if unsorted, 1 if sorted
    private int mInitialized; //0 if uninitialized, 1 if initialized
    private BinaryTree[] itemsToCompare; // only two items to be compared
    private Stack<BinaryTree[]> mStack;
    private BinaryTree[] mItems; //all items
    private BinaryTree activeNode; //the nearest node that is above both items in itemsToCompare
    private static final String TAG = "RankGroup";

    public RankGroup(String title, String[] strings){
        mTitle = title;
        mStrings = strings;

        if(strings.length > 1) {
            mSorted = 0;
            mInitialized = 0;
            mStack = new Stack<BinaryTree[]>();
            itemsToCompare = new BinaryTree[2];
        } else {
            mSorted = 1;
            mInitialized = 1;
            mItems = new BinaryTree[1];
            mItems[0] = new BinaryTree(strings[0]);
        }
    }

    private RankGroup(Parcel source) {
        mTitle = source.readString();
        mStrings = source.createStringArray();
        mSorted = source.readInt();
        mInitialized = source.readInt();
    }

    public String[] getStrings() {
        return mStrings;
    }

    public void setStrings(String[] strings) {
        mStrings = strings;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String toString(){
        return mTitle;
    }

    public int isSorted() { return mSorted; }

    public int isInitialized() { return mInitialized; }

    public String[] presentTwoItems() {
        if(mSorted == 1) return null;
        if(mInitialized == 0){ this.reset(); }

        String[] toReturn = new String[2];
        toReturn[0] = itemsToCompare[0].getData();
        toReturn[1] = itemsToCompare[1].getData();

        return toReturn;
    }

    public void reset() {
        mSorted = 0;
        mItems = new BinaryTree[mStrings.length];
        for(int i = 0; i < mStrings.length; i++) {
            mItems[i] = new BinaryTree(mStrings[i]);
        }
        this.fillStack();
        itemsToCompare = mStack.pop();
        mInitialized = 1;
    }

    private void fillStack() {
        int base2index = 2;
        while(mItems.length > base2index) {
            base2index *= 2;
        }
        //should be the smallest base 2 number larger than the length of mItems
        //Log.d(TAG, "RankGroup.fillStack() base2index = " + base2index);

        if(mItems.length == base2index) {
            //Log.d(TAG, "RankGroup.fillStack() length = base2, pushing all items");
            for(int i = mItems.length-1; i > 0; i -= 2) {
                //Log.d(TAG, "RankGroup.fillStack() pushing " + mItems[i-1].getData()
                       // + "," + mItems[i].getData());
                mStack.push(new BinaryTree[]{mItems[i-1], mItems[i]});
            }
        } else {
            int numMatchups = mItems.length - (base2index / 2);

            int numPartitions = 1;
            while(numPartitions < numMatchups) {
                numPartitions *= 2;
            }

            ArrayList<Integer> partitionList = new ArrayList<Integer>();
            partitionList.add(mItems.length);
            while(partitionList.size() < numPartitions) {
                ArrayList<Integer> newPartitionList = new ArrayList<Integer>();
                for(Integer i : partitionList) {
                    if (i % 2 == 0) {
                        newPartitionList.add(i/2);
                    } else {
                        newPartitionList.add((i/2)+1);
                    }
                    newPartitionList.add(i/2);
                }
                partitionList = newPartitionList;
            }

            int stackIndex = 0;
            ArrayList<BinaryTree[]> toPush = new ArrayList<BinaryTree[]>();
            for(Integer i : partitionList) {
                if(i >= 2){
                    toPush.add(new BinaryTree[]{mItems[stackIndex], mItems[stackIndex+1]});
                }
                stackIndex += i;
            }

            for(int i = toPush.size() - 1; i >= 0; i--) {
                mStack.push(toPush.get(i));
            }

        }

    }

    //param winInt: 0 = itemsToCompare[0]; 1 = itemsToCompare[1]; other = tie;
    public void selectWinner(int winInt) {
        if(winInt == 0 || winInt == 1) {//if a winner is selected
            //set winner and loser
            BinaryTree winner;
            BinaryTree loser;
            if(winInt == 0) {
                winner = itemsToCompare[0];
                loser = itemsToCompare[1];
            } else {
                winner = itemsToCompare[1];
                loser = itemsToCompare[0];
            }

            if(activeNode != null) {
                activeNode.setLeft(winner);
            }
            activeNode = winner;

            if(winner.hasLeft()) {
                if(winInt == 0) {
                    mStack.push(new BinaryTree[]{winner.left(), loser});
                } else {
                    mStack.push(new BinaryTree[]{loser, winner.left()});
                }

            } else {
                winner.setLeft(loser);
                activeNode = null;
            }

        } else {//if a tie is selected
            BinaryTree first = itemsToCompare[0];
            BinaryTree second = itemsToCompare[1];
            if(activeNode != null) {
                activeNode.setLeft(first);
            }
            activeNode = first;

            first.rightMost().setRight(second);

            if(first.hasLeft() && second.hasLeft()) {
                mStack.push(new BinaryTree[]{first.left(), second.left()});
                second.setLeft(null);
            } else if(second.hasLeft()) {
                first.setLeft(second.left());
                second.setLeft(null);
            } else {
                activeNode = null;
            }

        }

        this.setNewItemsToCompare();

    }

    private void setNewItemsToCompare() {
        itemsToCompare = null;
        if(mStack.empty()) {
            //reset mItems
            List<BinaryTree> stillRootItems = new ArrayList<BinaryTree>();
            for(BinaryTree b : mItems) {
                if(b.isRoot()) stillRootItems.add(b);
            }
            BinaryTree[] newItems = new BinaryTree[stillRootItems.size()];
            newItems = stillRootItems.toArray(newItems);
            mItems = newItems;

            if(mItems.length == 1) {
                mSorted = 1;
                return;
            } else {
                this.fillStack();
            }
        }

        itemsToCompare = mStack.pop();
    }

    public String[][] presentRanking() {
        if(this.isSorted() != 1) return null;
        else return mItems[0].toStringArray();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeStringArray(mStrings);
        dest.writeInt(mSorted);
        dest.writeInt(mInitialized);
    }

    public static final Parcelable.Creator<RankGroup> CREATOR
            = new Parcelable.Creator<RankGroup>() {
        @Override
        public RankGroup createFromParcel(Parcel source) {
            return new RankGroup(source);
        }

        @Override
        public RankGroup[] newArray(int size) {
            return new RankGroup[size];
        }
    };
}
