package edu.hm.hafner.echarts;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.hm.hafner.util.Generated;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Node for constructing a tree structure for a sunburst or treemap ECharts diagram.
 *
 * @author Andreas Pabst
 */
public class TreeNode {
    private String name;
    private double value;

    @JsonIgnore
    private Map<String, TreeNode> childrenMap = new HashMap<>();

    /**
     * Create a new {@link TreeNode} with value 0.0.
     *
     * @param name
     *         the name of the node
     */
    public TreeNode(final String name) {
        this(name, 0.0);
    }

    /**
     * Create a new {@link TreeNode}.
     *
     * @param name
     *         the name of the node
     * @param value
     *         the value of the node
     */
    public TreeNode(final String name, final double value) {
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * Add to the current value of this node.
     *
     * @param amount
     *         the amount to add
     */
    private void addValue(final double amount) {
        this.value += amount;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @JsonIgnore
    public Map<String, TreeNode> getChildrenMap() {
        return childrenMap;
    }

    public List<TreeNode> getChildren() {
        return new ArrayList<>(childrenMap.values());
    }

    /**
     * Collapse the package names. If a node only has one child, its name is appended to the current node and its
     * children are now the children of the current node. This is repeated as long as there are nodes with only one
     * child (package nodes at the top of the hierarchy).
     */
    public void collapsePackage() {
        while (getChildren().size() == 1) {
            var singleChild = getChildrenMap().values().iterator().next();
            if (name.isEmpty()) {
                setName(singleChild.getName());
            }
            else {
                setName(name + "." + singleChild.getName());
            }
            childrenMap = singleChild.getChildrenMap();
        }
    }

    /**
     * Insert a node in the tree.
     *
     * @param node
     *         the node to insert
     */
    public void insertNode(final TreeNode node) {
        Deque<String> packageLevels = new ArrayDeque<>(Arrays.asList(node.getName().split("\\.")));
        insertNode(node, packageLevels);
    }

    private void insertNode(final TreeNode node, final Deque<String> levels) {
        var nextLevelName = levels.pop();

        addValue(node.getValue());
        if (levels.isEmpty()) {
            node.setName(nextLevelName);
            childrenMap.put(nextLevelName, node);
        }
        else {
            updateChildren(node, levels, nextLevelName);
        }
    }

    @SuppressWarnings("NullAway")
    private void updateChildren(final TreeNode node, final Deque<String> levels, final String nextLevelName) {
        childrenMap.putIfAbsent(nextLevelName, new TreeNode(nextLevelName));
        childrenMap.get(nextLevelName).insertNode(node, levels);
    }

    @Override
    public String toString() {
        return "TreeNode '%s' (%s)".formatted(name, value);
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var treeNode = (TreeNode) o;
        return Double.compare(value, treeNode.value) == 0
                && Objects.equals(name, treeNode.name)
                && Objects.equals(childrenMap, treeNode.childrenMap);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(name, value, childrenMap);
    }
}
