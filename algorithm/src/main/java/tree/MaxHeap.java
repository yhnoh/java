package tree;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<V> {






    private List<V> items = new ArrayList<>();
    private int level = 0;
    public MaxHeap() {
        items.add(null);
    }

    public int size(){
        return items.size() - 1;
    }

    private void setLevel(){
        int nodeCount = items.size();
        int level = 0;
        while (true){
            nodeCount = nodeCount / 2;
            if(nodeCount == 0){
                break;
            }
            level++;
        }
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void insert(V value){
        items.add(value);
        int curIndex = items.size() - 1;

        while (curIndex != 1){
            int parentIndex = curIndex / 2;

            if(Comparable.class.cast(items.get(curIndex)).compareTo(items.get(parentIndex)) > 0){
                V temp = items.get(curIndex);
                items.set(curIndex, items.get(parentIndex));
                items.set(parentIndex, temp);
                curIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    public void delete(){


    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        int size = items.size();
        for (int i = 1; i < size; i++) {
            sb.append(items.get(i));
            if(i != size - 1){
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
