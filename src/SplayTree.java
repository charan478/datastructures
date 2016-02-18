public class SplayTree<Type extends Comparable<Type>> {
    private SplayTreeNode<Type> root;

    public SplayTree() {
        this.root = null;
    }

    public boolean search(Type data) {
        if (get_node(data) != null) {
            splay(get_node(data));
            return true;
        }
        return false;
    }

    private SplayTreeNode<Type> get_node(Type data) {
        SplayTreeNode<Type> node = root;
        while (node != null)
        {
            if (node.data.compareTo(data) == 0)
                return node;
            if (node.data.compareTo(data) > 0)
                node = node.left;
            else node = node.right;
        }
        return null;
    }

    public void insert(Type data) {
        if (get_node(data) != null) {
            splay(get_node(data));
            System.out.println("Element already exists");
            return;
        }
        SplayTreeNode<Type> node = root;
        SplayTreeNode<Type> parent = null;

        while (node != null) {
            parent = node;
            if (data.compareTo(parent.data) < 0)
                node = node.left;
            else
                node = node.right;
        }
        node = new SplayTreeNode<Type>(data);
        node.parent = parent;
        if (parent == null)
            root = node;
        else if (data.compareTo(parent.data) < 0)
            parent.left = node;
        else
            parent.right = node;
        splay(node);
    }

    public void delete(Type data) {
        SplayTreeNode<Type> node = get_node(data);
        if (node == null)
            return;
        splay(node);

        if (node.left != null && node.right != null){
            SplayTreeNode<Type> predecessor = node.left;
            while(predecessor.right != null)
                predecessor = predecessor.right;
            SplayTreeNode<Type> rtree = node.right;
            root = node.left;
            root.parent = null;
            splay(predecessor);

            predecessor.right = rtree;
            rtree.parent = predecessor;

        } else if (node.right != null) {
            node.right.parent = null;
            root = node.right;
        } else if (node.left != null) {
            node.left.parent = null;
            root = node.left;
        } else {
            root = null;
        }
        node.parent = node.left = node.right = null;
    }

    private void splay(SplayTreeNode<Type> node){

        if (node.parent == null) {
            root = node;
            return;
        }
        if (node == root) {
            return;
        }
        if (node.parent == root){
            // ZIG Step
            if (node.parent.left == node) {
                root = node;
                node.parent.left = node.right;
                node.right = node.parent;
                node.parent.parent = node;
                node.parent = null;
                if (node.right.left != null)
                    node.right.left.parent = node.right;
            } else if (node.parent.right == node) {
                root = node;
                node.parent.right = node.left;
                node.left = node.parent;
                node.parent.parent = node;
                node.parent = null;
                if (node.left.right != null)
                    node.left.right.parent = node.left;
            }
        } else if (node.parent.parent.left != null && node.parent.parent.left.left == node) {
            SplayTreeNode<Type> parent = node.parent;
            SplayTreeNode<Type> grand_parent = node.parent.parent;
            SplayTreeNode<Type> b = node.right;
            SplayTreeNode<Type> c = parent.right;

            node.parent = grand_parent.parent;
            if (grand_parent.parent != null) {
                if (grand_parent.parent.left != null && grand_parent.parent.left == grand_parent)
                    grand_parent.parent.left = node;
                else
                    grand_parent.parent.right = node;
            }
            node.right = parent;
            parent.parent = node;
            parent.left = b;
            if (b!=null) b.parent = parent;
            parent.right = grand_parent;
            grand_parent.parent = parent;
            grand_parent.left = c;
            if (c!=null) c.parent = grand_parent;
            splay(node);
        } else if (node.parent.parent.right != null && node.parent.parent.right.right == node) {
            SplayTreeNode<Type> parent = node.parent;
            SplayTreeNode<Type> grand_parent = node.parent.parent;
            SplayTreeNode<Type> b = node.left;
            SplayTreeNode<Type> c = parent.left;

            node.parent = grand_parent.parent;
            if (grand_parent.parent != null) {
                if (grand_parent.parent.left != null && grand_parent.parent.left == grand_parent)
                    grand_parent.parent.left = node;
                else
                    grand_parent.parent.right = node;
            }

            node.left = parent;
            parent.parent = node;
            parent.right = b;
            if (b!=null) b.parent = parent;
            parent.left = grand_parent;
            grand_parent.parent = parent;
            grand_parent.right = c;
            if (c!=null) c.parent = grand_parent;
            splay(node);
        } else if (node.parent.right != null && node.parent.right == node) {
            SplayTreeNode<Type> parent = node.parent;
            SplayTreeNode<Type> grand_parent = node.parent.parent;
            SplayTreeNode<Type> b = node.left;
            SplayTreeNode<Type> c = node.right;

            node.parent = grand_parent.parent;
            if (grand_parent.parent != null) {
                if (grand_parent.parent.left != null && grand_parent.parent.left == grand_parent)
                    grand_parent.parent.left = node;
                else
                    grand_parent.parent.right = node;
            }
            node.left = parent;
            parent.parent = node;
            node.right = grand_parent;
            grand_parent.parent = node;
            parent.right = b;
            if (b != null) b.parent = parent;
            grand_parent.left = c;
            if (c != null) c.parent = grand_parent;
            splay(node);
        } else if (node.parent.left != null && node.parent.left == node) {
            SplayTreeNode<Type> parent = node.parent;
            SplayTreeNode<Type> grand_parent = node.parent.parent;
            SplayTreeNode<Type> b = node.right;
            SplayTreeNode<Type> c = node.left;

            node.parent = grand_parent.parent;
            if (grand_parent.parent != null) {
                if (grand_parent.parent.left != null && grand_parent.parent.left == grand_parent)
                    grand_parent.parent.left = node;
                else
                    grand_parent.parent.right = node;
            }
            node.right = parent;
            parent.parent = node;
            node.left = grand_parent;
            grand_parent.parent = node;
            parent.left = b;
            if (b != null) b.parent = parent;
            grand_parent.right = c;
            if (c != null) c.parent = grand_parent;
            splay(node);
        }
    }

    public void print()
    {
        print(root, 0);
    }

    private void print(SplayTreeNode<Type> node, int h) {
        if(node.left != null)
            print(node.left, h + 1);
        System.out.printf("%s\n", node.data);
        if(node.right != null)
            print(node.right,h+1);
    }

    public void printHeight() {
        if (root == null)
            System.out.println(0);
        else
            System.out.println(height(1, root));
    }

    private int height(int d, SplayTreeNode<Type> node) {
        int value = d;
        if (node.left != null)
            value = Math.max(value, height(d+1, node.left));
        if (node.right != null)
            value = Math.max(value, height(d+1, node.right));
        return value;
    }

    public int getCount() {
        if (root == null)
            return 0;
        else
            return getCount(root);
    }

    private int getCount(SplayTreeNode<Type> node) {
        int value = 0;
        if (node.left != null)
            value += getCount(node.left);
        value += 1;
        if (node.right != null)
            value += getCount(node.right);
        return value;
    }
}


class SplayTreeNode<Type extends Comparable<Type>> implements Comparable<SplayTreeNode<Type>> {

    Type data;
    SplayTreeNode<Type> left, right, parent;

    public SplayTreeNode(Type data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    @Override
    public int compareTo(SplayTreeNode<Type> o) {
        return data.compareTo(o.data);
    }
}
