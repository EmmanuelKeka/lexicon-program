public class TreeNode {
    private TreeNode leftChild;
    private TreeNode rightChild;
    private TreeNode midelChild;
    private Pos label;

    public TreeNode(Pos label) {
        this.label = label;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }

    public TreeNode getMidelChild() { return midelChild; }

    public void setMidelChild(TreeNode midelChild) { this.midelChild = midelChild; }

    public Pos getLabel() {
        return label;
    }
}
