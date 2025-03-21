package org.mps.tree;

import java.util.Comparator;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public BinarySearchTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.value = null;
        this.left = null;
        this.right = null;
    }

    @Override
    public void insert(T value) {
        if (this.value == null) {
            this.value = value;
        } else {
            int cmp = comparator.compare(value, this.value);
            if (cmp < 0) {
                if (left == null) left = new BinarySearchTree<>(comparator);
                left.insert(value);
            } else if (cmp > 0) {
                if (right == null) right = new BinarySearchTree<>(comparator);
                right.insert(value);
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
}