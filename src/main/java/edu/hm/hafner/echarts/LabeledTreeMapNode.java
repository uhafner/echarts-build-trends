package edu.hm.hafner.echarts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

/**
 * Node for constructing a tree structure of {@code double} values for a sunburst or treemap ECharts diagram.
 *
 * @author Andreas Pabst
 * @author Ullrich Hafner
 */
// FIXME: we should use a builder pattern here
// FIXME: values should still be doubles and one other element should be the label
public class LabeledTreeMapNode {
    private final ItemStyle itemStyle;
    private final Label label;
    private final Label upperLabel;
    private final String id;
    private String name;
    private final List<String> values = new ArrayList<>();

    private final List<LabeledTreeMapNode> children = new ArrayList<>();

    /**
     * Creates a new {@link LabeledTreeMapNode} with an empty String as a value.
     *
     * @param id
     *         the id of the node
     * @param name
     *         the name of the node
     */
    public LabeledTreeMapNode(final String id, final String name) {
        this(id, name, StringUtils.EMPTY);
    }

    /**
     * Creates a new {@link LabeledTreeMapNode} with the given value.
     *
     * @param id
     *         the id of the node
     * @param name
     *         the name of the node
     * @param value
     *         the value of the node
     */
    public LabeledTreeMapNode(final String id, final String name, final String value) {
        this(id, name, "-", value);
    }

    /**
     * Creates a new {@link LabeledTreeMapNode} with the given values.
     *
     * @param id
     *         the id of the node
     * @param name
     *         the name of the node
     * @param color
     *         the color of the node
     * @param value
     *         the value of the node
     * @param additionalValues
     *         additional values of the node
     */
    public LabeledTreeMapNode(final String id, final String name, final String color,
            final String value, final String... additionalValues) {
        this(id, name, new ItemStyle(color), new Label(false, "#ffffff"),
                new Label(false, "#ffffff"), value, additionalValues);
    }

    /**
     * Creates a new {@link LabeledTreeMapNode} with the given values.
     *
     * @param id
     *         the id of the node
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
    public LabeledTreeMapNode(final String id, final String name, final ItemStyle itemStyle,
            final Label label, final Label upperLabel,
            final String value, final String... additionalValues) {
        this.id = id;
        this.name = name;
        this.itemStyle = itemStyle;
        this.label = label;
        this.upperLabel = upperLabel;
        this.values.add(value);
        values.addAll(Arrays.asList(additionalValues));
    }

    public List<String> getValue() {
        return List.copyOf(values);
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

    public List<LabeledTreeMapNode> getChildren() {
        return List.copyOf(children);
    }

    /**
     * Inserts the specified node in the tree.
     *
     * @param node
     *         the node to insert
     */
    public void insertNode(final LabeledTreeMapNode node) {
        children.add(node);
    }

    /**
     * Collapse the package names. If a node only has one child, its name is appended to the current node and its
     * children are now the children of the current node. This is repeated as long as there are nodes with only one
     * child (package nodes at the top of the hierarchy).
     */
    public void collapseEmptyPackages() {
        while (children.size() == 1) {
            LabeledTreeMapNode singleChild = children.iterator().next();
            name = String.join(".", name, singleChild.getName());

            children.clear();
            children.addAll(singleChild.getChildren());
        }
    }

    @Override
    public String toString() {
        return String.format("'%s' (%s)", name, values);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabeledTreeMapNode that = (LabeledTreeMapNode) o;
        return Objects.equals(itemStyle, that.itemStyle) && Objects.equals(label, that.label)
                && Objects.equals(upperLabel, that.upperLabel) && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(values, that.values)
                && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemStyle, label, upperLabel, id, name, values, children);
    }
}
