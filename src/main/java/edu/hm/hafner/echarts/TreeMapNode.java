package edu.hm.hafner.echarts;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node for constructing a tree structure of {@code double} values for a sunburst or treemap
 * ECharts diagram.
 *
 * @author Andreas Pabst
 * @author Ullrich Hafner
 */
@SuppressWarnings("InconsistentOverloads")
// TODO: in order to locate a selected element we need to provide an additional reference for each node
public class TreeMapNode {
    private final ItemStyle itemStyle;
    private final Label label;
    private final Label upperLabel;
    private String name;
    private final List<Double> values = new ArrayList<>();

    private final List<TreeMapNode> children = new ArrayList<>();

    /**
     * Creates a new {@link TreeMapNode} with the value 0.0.
     *
     * @param name
     *         the name of the node
     */
    public TreeMapNode(final String name) {
        this(name, 0.0);
    }

    /**
     * Creates a new {@link TreeMapNode} with the given value.
     *
     * @param name
     *         the name of the node
     * @param value
     *         the value of the node
     */
    public TreeMapNode(final String name, final double value) {
        this(name, "-", value);
    }

    /**
     * Creates a new {@link TreeMapNode} with the given values.
     *
     * @param name
     *         the name of the node
     * @param color
     *         the color of the node
     * @param value
     *         the value of the node
     * @param additionalValues
     *         additional values of the node
     */
    public TreeMapNode(final String name, final String color, final double value, final double... additionalValues) {
        this(name, new ItemStyle(color), new Label(false, "#ffffff"),
                new Label(false, "#ffffff"), value, additionalValues);
    }

    /**
     * Creates a new {@link TreeMapNode} with the given values.
     *
     * @param name
     *         the name of the node
     * @param itemStyle
     *         the style of the node
     * @param label
     *         the label style of the node
     * @param upperLabel
     *         the label style of the node if it contains children
     * @param value
     *         the value of the node
     * @param additionalValues
     *         additional values of the node
     */
    public TreeMapNode(final String name, final ItemStyle itemStyle, final Label label, final Label upperLabel,
                       final double value, final double... additionalValues) {
        this.itemStyle = itemStyle;
        this.label = label;
        this.upperLabel = upperLabel;
        this.name = name;
        this.values.add(value);
        Collections.addAll(values, ArrayUtils.toObject(additionalValues));
    }

    public List<Double> getValue() {
        return Collections.unmodifiableList(values);
    }

    public String getName() {
        return name;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public Label getLabel() {
        return label;
    }

    public Label getUpperLabel() {
        return upperLabel;
    }

    public List<TreeMapNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Inserts the specified node in the tree.
     *
     * @param node
     *         the node to insert
     */
    public void insertNode(final TreeMapNode node) {
        children.add(node);
    }

    /**
     * Collapse the package names. If a node only has one child, its name is appended to the current node and its
     * children are now the children of the current node. This is repeated as long as there are nodes with only one
     * child (package nodes at the top of the hierarchy).
     */
    public void collapseEmptyPackages() {
        while (children.size() == 1) {
            var singleChild = children.iterator().next();
            name = String.join(".", name, singleChild.getName());

            children.clear();
            children.addAll(singleChild.getChildren());
        }
    }

    @Override
    public String toString() {
        return "'%s' (%s)".formatted(name, values);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var that = (TreeMapNode) o;

        if (!itemStyle.equals(that.itemStyle)) {
            return false;
        }
        if (!label.equals(that.label)) {
            return false;
        }
        if (!upperLabel.equals(that.upperLabel)) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        if (!values.equals(that.values)) {
            return false;
        }
        return children.equals(that.children);
    }

    @Override
    public int hashCode() {
        int result = itemStyle.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + upperLabel.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + values.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }
}
