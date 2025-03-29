package org.mps.tree;

import java.util.Comparator;
import java.util.List;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public String render() {
        String render = "";

        if (value != null) {
            render += value.toString();
        }

        if (left != null || right != null) {
            render += "(";
            if (left != null) {
                render += left.render();
            }
            render += ",";
            if (right != null) {
                render += right.render();
            }
            render += ")";
        }

        return render;
    }

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.value = null;
        this.left = null;
        this.right = null;
    }
    
    @Override
    public void insert(T value) throws BinarySearchTreeException {
        if (this.value == null) {
            this.value = value;
        } else {
            int comparison = this.comparator.compare(value, this.value);

            if (comparison < 0) { 
                if (this.left == null) { 
                    this.left = new BinarySearchTree<>(this.comparator);
                    this.left.value = value;
                } else {
                    this.left.insert(value);
                }
            } else if (comparison == 0) {
                throw new BinarySearchTreeException("Ese número ya está insertado");
            } else { // Insertar en la derecha
                if (this.right == null) { // Crear el subárbol derecho si es null
                    this.right = new BinarySearchTree<>(this.comparator);
                    this.right.value = value;
                } else {
                    this.right.insert(value);
                }
            }
        }
    }

    @Override
    public boolean isLeaf() {
        if (this.value == null) throw new BinarySearchTreeException("Tree is empty");
        return left == null && right == null;
    }

    @Override
    public boolean contains(T value) {
        if (this.value == null) return false;
        int cmp = comparator.compare(value, this.value);
        if (cmp == 0) return true;
        else if (cmp < 0) return left != null && left.contains(value);
        else return right != null && right.contains(value);
    }

    @Override
    public T minimum() {
        if (this.value == null) throw new BinarySearchTreeException("Tree is empty");
        return (left == null) ? this.value : left.minimum();
    }

    @Override
    public T maximum() {
        if (this.value == null) throw new BinarySearchTreeException("Tree is empty");
        return (right == null) ? this.value : right.maximum();
    }

    @Override
    public void removeBranch(T value) {
        if (this.value == null) throw new BinarySearchTreeException("Tree is empty");
        removeNode(value, null, false);
    }

    private void removeNode(T value, BinarySearchTree<T> parent, boolean isLeftChild) {
        int cmp = comparator.compare(value, this.value);
        if (cmp < 0 && left != null) {
            left.removeNode(value, this, true);
        } else if (cmp > 0 && right != null) {
            right.removeNode(value, this, false);
        } else if (cmp == 0) {
            if (left != null && right != null) {
                this.value = right.minimum();
                right.removeNode(this.value, this, false);
            } else if (parent != null) {
                if (isLeftChild) {
                    parent.left = (left != null) ? left : right;
                } else {
                    parent.right = (left != null) ? left : right;
                }
            } else {
                if (left != null) {
                    this.value = left.value;
                    this.right = left.right;
                    this.left = left.left;
                } else if (right != null) {
                    this.value = right.value;
                    this.left = right.left;
                    this.right = right.right;
                } else {
                    this.value = null;
                }
            }
        }
    }
    

    @Override
    public int size() {
        if (this.value == null) return 0;
        int leftSize = (left == null) ? 0 : left.size();
        int rightSize = (right == null) ? 0 : right.size();
        return 1 + leftSize + rightSize;
    }

    @Override
    public int depth() {
        if (this.value == null) return 0;
        int leftDepth = (left == null) ? 0 : left.depth();
        int rightDepth = (right == null) ? 0 : right.depth();
        return 1 + Math.max(leftDepth, rightDepth);
    }

    // Complex operations

    /**
     * Removes the first occurrence of the specified element from this binary search tree, if it is present.
     *
     * @param value to be removed from this binary tree, if present
     * @throws BinaryTreeException if the element is not present in the binary tree
     */
    void removeValue(T value){
        if (!contains(value)) throw new BinarySearchTreeException("Element not found");
        removeBranch(value);
    }

    /**
     * Returns a List of all the values of the tree in order.
     *
     * @return a List of all the values of the tree in order
     */
    List<T> inOrder(){
        List<T> list = new ArrayList<>();
        inOrderTraversal(this, list);
        return list;
    }

    private void inOrderTraversal(BinarySearchTree<T> node, List<T> list){
        if (node == null && node.value == null) return;
            inOrderTraversal(node.left, list);
            list.add(node.value);
            inOrderTraversal(node.right, list);
    }

    /**
     * Balance the binary search tree. Making the depth of the
     * left and right subtrees of every node differ by at most one.
     */
    void balance(){
        List<T> list = inOrder();
        clearTree();
        balanceTree(list, 0, list.size() - 1);
    }

    void clearTree(){
        this.value = null;
        this.left = null;
        this.right = null;
    }

    void balanceTree(List<T> list, int start, int end){
        if (start > end) return;
        int mid = (start + end) / 2;
        insert(list.get(mid));
        balanceTree(list, start, mid - 1);
        balanceTree(list, mid + 1, end);
    }
}