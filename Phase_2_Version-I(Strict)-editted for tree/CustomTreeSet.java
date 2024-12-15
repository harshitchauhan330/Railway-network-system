import java.util.ArrayList;
import java.util.List;

public class CustomTreeSet<T extends Comparable<T>> {
    private class Node {
        T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    public void add(T value) {
        root = insert(root, value);
    }

    public boolean contains(T value) {
        return search(root, value) != null;
    }

    public boolean removeIf(java.util.function.Predicate<T> condition) {
        List<T> toRemove = new ArrayList<>();
        inOrderTraversal(root, toRemove::add);
        boolean removed = false;
        for (T value : toRemove) {
            if (condition.test(value)) {
                remove(value); // Here remove(value) is now valid
                removed = true;
            }
        }
        return removed;
    }

    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<>();
        inOrderTraversal(root, result::add);
        return result;
    }

    // AVL Helper Methods
    private Node insert(Node node, T value) {
        if (node == null)
            return new Node(value);
        int cmp = value.compareTo(node.value);
        if (cmp < 0)
            node.left = insert(node.left, value);
        else if (cmp > 0)
            node.right = insert(node.right, value);
        else
            return node; // Duplicate
        updateHeight(node);
        return balance(node);
    }

    // Updated remove method to use root and value
    public void remove(T value) {
        root = remove(root, value); // Root node passed here
    }

    private Node remove(Node node, T value) {
        if (node == null)
            return null;
        int cmp = value.compareTo(node.value);
        if (cmp < 0)
            node.left = remove(node.left, value);
        else if (cmp > 0)
            node.right = remove(node.right, value);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node maxNode = findMax(node.left);
                node.value = maxNode.value;
                node.left = remove(node.left, maxNode.value);
            }
        }
        if (node != null) {
            updateHeight(node);
            node = balance(node);
        }
        return node;
    }

    private Node search(Node node, T value) {
        if (node == null)
            return null;
        int cmp = value.compareTo(node.value);
        if (cmp == 0)
            return node;
        return cmp < 0 ? search(node.left, value) : search(node.right, value);
    }

    private Node findMax(Node node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    private void inOrderTraversal(Node node, java.util.function.Consumer<T> action) {
        if (node == null)
            return;
        inOrderTraversal(node.left, action);
        action.accept(node.value);
        inOrderTraversal(node.right, action);
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node balance(Node node) {
        int balance = getBalanceFactor(node);
        if (balance > 1) {
            if (getBalanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

}
