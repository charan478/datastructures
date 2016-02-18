import java.util.Random;

public class SkipList<Type extends Comparable<Type>> {

    private SkipListNode<Type> top_left;
    private Random random;

    public SkipList() {
        this.top_left = new SkipListNode<Type>(null);
        this.top_left.minf = true;
        this.random = new Random();
    }

    public boolean search(Type value) {
        SkipListNode<Type> node = top_left;
        while (node != null)
        {
            if (!node.minf && node.data.compareTo(value) == 0)
                return true;

            if (node.right == null)
            {
                if (node.down == null)
                    return false;
                node = node.down;
                continue;
            }

            if (node.right.data.compareTo(value) <= 0) {
                node = node.right;
            } else {
                node = node.down;
            }
        }
        return false;
    }

    private int getLevel() {
        int ret_val = 1;
        while (true) {
            if (random.nextBoolean())
                break;
            ret_val += 1;
        }
        return ret_val;
    }

    public void insert(Type data) {
        if (search(data)) {
            System.out.println("Data already in the list");
            return;
        }
        int insert_upto_level = getLevel();
        SkipListNode<Type> below = null, left_end = top_left;
        while (left_end.down != null)
            left_end = left_end.down;

        SkipListNode<Type> current_node = left_end;
        while (true) {
            if (insert_upto_level == 0) break;
            if (current_node.right == null) {
                SkipListNode<Type> new_node = new SkipListNode<Type>(data);
                new_node.down = below;
                if(below != null) below.up = new_node;
                current_node.right = new_node;
                new_node.left = current_node;
                if (left_end.up == null && insert_upto_level != 1) {
                    left_end.up = new SkipListNode<Type>(null);
                    left_end.up.down = left_end;
                    left_end.minf = true;
                    top_left = left_end;
                }
                insert_upto_level -= 1;
                left_end = left_end.up;
                below = new_node;
                current_node = left_end;
            } else if (current_node.right.data.compareTo(data) < 0){
                current_node = current_node.right;
            } else {
                SkipListNode<Type> new_node = new SkipListNode<Type>(data);
                new_node.down = below;
                if (below != null) below.up = new_node;
                new_node.left = current_node;
                new_node.right = current_node.right;
                current_node.right = new_node;
                new_node.right.left = new_node;

                if (left_end.up == null && insert_upto_level != 1) {
                    left_end.up = new SkipListNode<Type>(null);
                    left_end.up.down = left_end;
                    left_end.minf = true;
                    top_left = left_end;
                }

                insert_upto_level -= 1;
                left_end = left_end.up;
                below = new_node;
                current_node = left_end;
            }
        }
    }

    public void delete(Type data) {
        if (!search(data)) {
            return;
        }

        SkipListNode<Type> node = top_left;

        while (true)
        {
            if (!node.minf && node.data.compareTo(data) == 0)
                break;
            else if (node.right != null && node.right.data.compareTo(data) <= 0)
                node = node.right;
            else
                node = node.down;
        }

        while (true)
        {
            if (node == null) break;
            node.left.right = node.right;
            if (node.right != null) node.right.left = node.left;
            if (node.left.minf && node.right == null) {
                top_left = top_left.down;
                top_left.up = null;
            }
            node = node.down;
        }
    }

    public void print() {
        SkipListNode<Type> lm = top_left, current;

        if (lm == null)
            return;

        while (lm.down != null) lm = lm.down;
        current = lm.right;
        while (current != null) {
            System.out.println(current.data);
            current = current.right;
        }
    }

    public int getCount() {
        SkipListNode<Type> lm = top_left, current;

        if (lm == null)
            return 1;

        int count = 0;

        while (lm != null) {
            current = lm;
            while (current != null) {
                count += 1;
                current = current.right;
            }
            lm = lm.down;
        }
        return count;
    }
}

class SkipListNode<Type extends Comparable<Type>> implements Comparable<SkipListNode<Type>> {

    SkipListNode<Type> up, left, right, down;
    Type data;
    boolean minf; // Negative infinity

    public SkipListNode(Type data) {
        this.up = null;
        this.left = null;
        this.right = null;
        this.down = null;
        this.data = data;
        this.minf = false;
    }

    @Override
    public int compareTo(SkipListNode<Type> o) {
        return data.compareTo(o.data);
    }
}
