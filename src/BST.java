/**
 * Class BST:
 *  Binary search tree
 */
public class BST<Type extends Comparable<Type>> {

    private BSTNode<Type> root;

    public BST() {
        this.root = null;
    }

    public boolean search(Type value) {
        return this.root != null && search(this.root, value);
    }

    private boolean search(BSTNode<Type> node, Type value) {
        if (value.compareTo(node.getData()) == 0)
            return true;
        if (value.compareTo(node.getData()) > 0)
            return node.getRight() != null && search(node.getRight(), value);
        return value.compareTo(node.getData()) < 0 && node.getLeft() != null && search(node.getLeft(), value);
    }

    public void insert(Type value) {
        this.root = insert(root, value);
    }

    private BSTNode<Type> insert(BSTNode<Type> node, Type value) {
        if (node == null)
            return new BSTNode<Type>(value);
        if (value.compareTo(node.getData()) == 0)
            return node;
        if (value.compareTo(node.getData()) < 0)
            node.left = insert(node.left, value);
        if (value.compareTo(node.getData()) > 0)
            node.right = insert(node.right, value);
        return node;
    }

    public void delete(Type value) {
        if (!search(value)){
            return;
        }
        this.root = delete(this.root, value);
    }

    private BSTNode<Type> delete(BSTNode<Type> node, Type value) {
        if (node.getData().compareTo(value) < 0) {
            node.setRight(delete(node.getRight(), value));
            return node;
        }
        if (node.getData().compareTo(value) > 0) {
            node.setLeft(delete(node.getLeft(), value));
            return node;
        }
        BSTNode<Type> left_subtree, right_subtree;
        left_subtree = node.getLeft();
        right_subtree = node.getRight();
        if (left_subtree == null && right_subtree == null)
            return null;
        if (left_subtree == null)
            return right_subtree;
        if (right_subtree == null)
            return left_subtree;
        BSTNode<Type> rightmost = left_subtree;
        while (rightmost.getRight() != null)
            rightmost = rightmost.getRight();
        node.setData(rightmost.getData());
        node.setLeft(delete(node.getLeft(), node.getData()));
        return node;
    }

    public void print() {
        if (this.root == null)
            return;
        print(this.root);
    }

    private void print(BSTNode<Type> node) {
        if(node.getLeft() != null)
            print(node.getLeft());
        System.out.println(node.data);
        if(node.getRight() != null)
            print(node.getRight());
    }

    public void printHeight() {
        if (root == null)
            System.out.println("0");
        else
            System.out.println(height(1, root));
    }

    private int height(int d, BSTNode<Type> node) {
        int value = d;
        if (node.getLeft() != null)
            value = Math.max(value, height(d+1, node.getLeft()));
        if (node.getRight() != null)
            value = Math.max(value, height(d+1, node.getRight()));
        return value;
    }

    public int getCount() {
        if (root == null)
            return 0;
        else
            return getCount(root);
    }

    private int getCount(BSTNode<Type> node) {
        int value = 0;
        if (node.getLeft() != null)
            value += getCount(node.getLeft());
        value += 1;
        if (node.getRight() != null)
            value += getCount(node.getRight());
        return value;
    }
}

class BSTNode<Type extends Comparable<Type>> implements Comparable<BSTNode<Type>> {
    protected BSTNode<Type> left, right;
    protected Type data;

    public BSTNode(Type value)
    {
        this.left = null;
        this.right = null;
        this.data = value;
    }

    public BSTNode<Type> getLeft() {
        return left;
    }

    public void setLeft(BSTNode<Type> left) {
        this.left = left;
    }

    public BSTNode<Type> getRight() {
        return right;
    }

    public void setRight(BSTNode<Type> right) {
        this.right = right;
    }

    public Type getData() {
        return data;
    }

    public void setData(Type data) {
        this.data = data;
    }

    @Override
    public int compareTo(BSTNode<Type> o) {
        return this.data.compareTo(o.getData());
    }
}