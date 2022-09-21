package edu.hm.hafner.echarts;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static edu.hm.hafner.echarts.assertions.Assertions.*;

/**
 * Tests the class {@link TreeMapNode}.
 */
class TreeMapNodeTest {
    @Test
    void shouldCreateNode() {
        TreeMapNode root = createRoot();

        assertThat(root).hasName("Root")
                .hasNoChildren()
                .hasValue(0.0);
        assertThat(root.getItemStyle()).satisfies(
                itemStyle -> assertThat(itemStyle.getColor()).isEqualTo("-")
        );
        assertThat(root.getLabel()).satisfies(
                label -> assertThat(label.getShow()).isFalse(),
                label -> assertThat(label.getColor()).isEqualTo("#ffffff")
        );
        assertThat(root.getUpperLabel()).satisfies(
                upperLabel -> assertThat(upperLabel.getShow()).isFalse(),
                upperLabel -> assertThat(upperLabel.getColor()).isEqualTo("#ffffff")
        );
    }

    @Test
    void shouldInsertNode() {
        TreeMapNode root = createRoot();
        TreeMapNode child = new TreeMapNode("Child");
        root.insertNode(child);

        assertThat(root).hasChildren(child);
    }

    @Test
    void shouldCollapseChildNodes() {
        TreeMapNode root = new TreeMapNode("Root");

        root.collapseEmptyPackages();

        assertThat(root.getChildren()).isEmpty();
        assertThat(root).hasName("Root");

        TreeMapNode one = new TreeMapNode("1");
        root.insertNode(one);
        root.collapseEmptyPackages();
        assertThat(root).hasName("Root.1");

        TreeMapNode two = new TreeMapNode("2");
        two.insertNode(new TreeMapNode("3"));
        root.insertNode(two);
        root.collapseEmptyPackages();
        assertThat(root).hasName("Root.1.2.3");

        TreeMapNode four = new TreeMapNode("4");
        four.insertNode(new TreeMapNode("5"));
        four.collapseEmptyPackages();
        root.insertNode(four);
        root.collapseEmptyPackages();
        assertThat(root).hasName("Root.1.2.3.4.5");
    }

    @Test
    void shouldHaveToString() {
        TreeMapNode root = new TreeMapNode("Root");
        assertThat(root.toString()).isEqualTo("'Root' ([0.0])");
    }

    @Test
    void shouldHaveEquals() {
        EqualsVerifier.simple()
                .forClass(TreeMapNode.class)
                .withPrefabValues(TreeMapNode.class, new TreeMapNode("1"), new TreeMapNode("2"))
                .verify();
    }

    private TreeMapNode createRoot() {
        return new TreeMapNode("Root");
    }
}
