/**
 * Class RBTREE:
 *  Red Black Tree
 */
public class RBTREE<Type extends Comparable<Type>> {

    RBTreeNode<Type> root;

    public RBTREE() {
        root = null;
    }

    public boolean search(Type value) {
        return this.root != null && search(this.root, value);
    }

    private boolean search(RBTreeNode<Type> node, Type value) {
    	boolean found=false;
    	while(node!=null)
    	{
    		if (node.data.compareTo(value) == 0) {
    			found=true;
    			break;
            }
    		else {
                if (node.data.compareTo(value) > 0) {
                    node=node.left;
                } else {
                	node=node.right;
                }
    		}
    	}
    	return found;
    }

    public void insert(Type data) {
        if (root == null)
            root = new RBTreeNode<Type>(data);

        RBTreeNode<Type> n = root;

        while (true) {
            if (data.compareTo(n.data) == 0) {
                n.data = data;
                return;
            }
            if (data.compareTo(n.data) < 0) {
                if (n.left == null) {
                    n.left = new RBTreeNode<Type>(data);
                    postInsertion(n.left);
                    break;
                }
                n = n.left;
            } else {
                if (n.right == null) {
                    n.right = new RBTreeNode<Type>(data);
                    postInsertion(n.right);
                    break;
                }
                n = n.right;
            }
        }
    }

    public void delete(Type data) {
        RBTreeNode<Type> node = root;
        System.out.println(search(data));
        if (!search(data)) {
            System.out.println("Data not present for removal");
            return;
        }
        while (true) {
            if (data.compareTo(node.data) == 0)
                break;
            if (data.compareTo(node.data) > 0)
                node = node.right;
            else
                node = node.left;
        }
        // Get the rightmost node in the left subtree if required
        RBTreeNode<Type> parent_old = parentOf(node);
        if (node.left != null && node.right != null){
            RBTreeNode<Type> next = node.left;
            while (next.right != null){
                next = next.right;
            }
            parent_old = parentOf(next);
            node.data = next.data;
            node = next;
        }

        // Only one child or none
        RBTreeNode<Type> child = node.left;
        if(child == null)
            child = node.right;

        if (child != null) {
            RBTreeNode<Type> parent = parentOf(child);
            if (node == root) {
                if (node.left == child)
                    node.left = null;
                else if (node.right == child)
                    node.right = null;
                root = child;
            } else if (parent == null) {
                System.out.println("Dev: Unexpected parent null pointer in RBTREE");
                return;
            } else if (parent.right != null && parent.right == node) {
                parent.right = child;
            } else if (parent.left != null && parent.left == node) {
                parent.left = child;
            }

            if (node.color == Color.BLACK)
            {
                postRemoval(child);
            }
            
        } else if (node == root) {
            root = null;
        } else {
            if (node.color == Color.BLACK) {
            	
            	{
            	postRemoval(node);
            	}
            }
            if (parent_old != null) {
                if (parent_old.right == node)
                    parent_old.right = null;
                else
                    parent_old.left = null;
            }
        }
    }

    private RBTreeNode<Type> parentOf(RBTreeNode<Type> node) {
        RBTreeNode<Type> parent = root;
        while (true) {
            if (parent == null || parent.data.compareTo(node.data) == 0)
            {
            	return null;
            	
            }
                

            if (parent.left!=null && parent.left.data.compareTo(node.data) == 0)
                return parent;
            if (parent.right!=null && parent.right.data.compareTo(node.data) == 0)
                return parent;

            if (parent.data.compareTo(node.data) > 0)
                parent = parent.left;
            else
                parent = parent.right;
        }
    }

    private RBTreeNode<Type> grandParentOf(RBTreeNode<Type> node) {
        if (node == null)
            return null;
        if (parentOf(node) == null)
            return null;
        return parentOf(parentOf(node));
    }

    private RBTreeNode<Type> siblingOf(RBTreeNode<Type> node) {
        if (node == null || parentOf(node) == null)
            return null;
        RBTreeNode<Type> parent = parentOf(node);
        if (parent == null)
            return null;
        if (parent.right == node)
            return parent.left;
        else
            return parent.right;
    }

    private void postInsertion(RBTreeNode<Type> node) {
        if (node != null) node.color = Color.RED;

        RBTreeNode<Type> parent = parentOf(node);

        if (node != null && node != root && parent != null && parent.color == Color.RED) {

            RBTreeNode<Type> grandParent = grandParentOf(node);
            RBTreeNode<Type> sibling = siblingOf(parent);

            if(grandParent != null && sibling != null && sibling.color == Color.RED){
                parent.color = sibling.color = Color.BLACK;
                if (grandParentOf(node) != null) {
                    grandParent.color = Color.RED;
                    //System.out.println("parent is "+parent+"\t\tgrandparent right is "+grandParent.right);
                    postInsertion(grandParent);
                }
            }

            else if (grandParent != null && parent == grandParent.left) {
                if (node == parent.right) {
                    node = parent;
                    rotate_left(node);
                }
                parentOf(node).color = Color.BLACK;
                if (grandParentOf(node) != null) {
                    grandParentOf(node).color = Color.RED;
                    rotate_right(grandParentOf(node));
                }
            }

            else if (grandParent != null && parent == grandParent.right)
            {
                if (node == parent.left) {
                    node = parent;
                    rotate_right(node);
                }
                parentOf(node).color = Color.BLACK;
                if (grandParentOf(node) != null) {
                    grandParentOf(node).color = Color.RED;
                    rotate_left(grandParentOf(node));
                }
            }
        }

        root.color = Color.BLACK;

    }

    private void postRemoval(RBTreeNode<Type> node) {
    	/*System.out.println("post removal"+node.data);
    	System.out.println(node.color);
    	System.out.println(root.data+"--root");
    	System.out.println(node.data+"--node");
        */while ( node != null && node.data != root.data && node.color == Color.BLACK) {
        	//System.out.println("in");
            RBTreeNode<Type> parent = parentOf(node);
            if (parent != null && node == parent.left) {
                RBTreeNode<Type> sibling = parent.right;

                if (sibling != null && sibling.color == Color.RED) {
                    parent.color = Color.RED;
                    sibling.color = Color.BLACK;
                    rotate_left(parent);
                    sibling = parent.right;
                }

                if (((sibling == null) || (sibling.left == null) || (sibling.left.color == Color.BLACK)) &&
                        ((sibling == null) || (sibling.right == null) || (sibling.right.color == Color.BLACK))) {
                    if (sibling != null)
                        sibling.color = Color.RED;
                    node = parent;
                } else {
                    if (sibling.right == null || sibling.right.color == Color.BLACK ) {
                        sibling.left.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rotate_right(sibling);
                        sibling = parent.right;
                    }
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                    //System.out.println(sibling.right);
                    if (sibling.right != null)
                    {
                    sibling.right.color = Color.BLACK;
                    }
                    rotate_left(parent);
                    node = root;
                }
            } else if (parent != null ){
                RBTreeNode<Type> sibling = parent.left;

                if (sibling != null && sibling.color == Color.RED) {
                    parent.color = Color.RED;
                    sibling.color = Color.BLACK;
                    rotate_right(parent);
                    sibling = parent.left;
                }

                if (((sibling == null) ||(sibling.left == null) || (sibling.left.color == Color.BLACK)) &&
                        ((sibling == null) || (sibling.right == null) || (sibling.right.color == Color.BLACK))) {
                    if (sibling != null)
                        sibling.color = Color.RED;
                    node = parent;
                } else {
                    if (sibling.left == null || sibling.left.color == Color.BLACK) {
                        sibling.right.color = Color.BLACK;
                        sibling.color = Color.RED;
                        rotate_left(sibling);
                        sibling = parent.left;
                    }
                    sibling.color = parent.color;
                    parent.color = Color.BLACK;
                    sibling.left.color = Color.BLACK;
                    rotate_right(parent);
                    node = root;
                }
            }
        }
        if (node != null)
            node.color = Color.BLACK;
    }

    private void rotate_left(RBTreeNode<Type> node) {
        if (node.right == null)
            return;

        RBTreeNode<Type> rightNode = node.right;
        node.right = rightNode.left;

        if (parentOf(node) == null)
            root = rightNode;
        else if (parentOf(node).left == node)
            parentOf(node).left = rightNode;
        else
            parentOf(node).right = rightNode;

        rightNode.left = node;
    }

    private void rotate_right(RBTreeNode<Type> node) {
        if (node.left == null)
            return;

        RBTreeNode<Type> leftNode = node.left;
        node.left = leftNode.right;

        if (parentOf(node) == null)
            root = leftNode;
        else if (parentOf(node).left == node)
            parentOf(node).left = leftNode;
        else
            parentOf(node).right = leftNode;

        leftNode.right = node;
    }

    public void print() {
        if (this.root == null)
            return;
        print(this.root, 0);
    }

    private void print(RBTreeNode<Type> node, int h) {
        if(node.left != null)
            print(node.left, h+1);
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

    private int height(int d, RBTreeNode<Type> node) {
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

    private int getCount(RBTreeNode<Type> node) {
        int value = 0;
        if (node.left != null)
            value += getCount(node.left);
        value += 1;
        if (node.right != null)
            value += getCount(node.right);
        return value;
    }
}

enum Color {RED, BLACK}

class RBTreeNode<Type extends Comparable<Type>> implements Comparable<RBTreeNode<Type>> {

    RBTreeNode<Type> left, right;
    Type data;
    Color color;

    public RBTreeNode(Type data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.color = Color.BLACK;
    }

    @Override
    public int compareTo(RBTreeNode<Type> o) {
        return data.compareTo(o.data);
    }
}