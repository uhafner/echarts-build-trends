package edu.hm.hafner.echarts;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

import static edu.hm.hafner.echarts.assertions.Assertions.*;

/**
 * Tests the class {@link LabeledTreeMapNode}.
 */
class LabeledTreeMapNodeTest {
    private static final String ID = "id";

    @Test
    void shouldCreateNode() {
        var root = createRoot();

        assertThat(root).hasName("Root")
                .hasNoChildren()
                .hasValue(StringUtils.EMPTY);
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
        var root = createRoot();
        var child = new LabeledTreeMapNode(ID, "Child");
        root.insertNode(child);

        assertThat(root).hasChildren(child);
    }

    @Test
    void shouldCollapseChildNodes() {
        var root = new LabeledTreeMapNode(ID, "Root");

        root.collapseEmptyPackages();

        assertThat(root.getChildren()).isEmpty();
        assertThat(root).hasName("Root");

        var one = new LabeledTreeMapNode(ID, "1");
        root.insertNode(one);
        root.collapseEmptyPackages();
        assertThat(root).hasName("Root.1");

        var two = new LabeledTreeMapNode(ID, "2");
        two.insertNode(new LabeledTreeMapNode(ID, "3"));
        root.insertNode(two);
        root.collapseEmptyPackages();
        assertThat(root).hasName("Root.1.2.3");

        var four = new LabeledTreeMapNode(ID, "4");
        four.insertNode(new LabeledTreeMapNode(ID, "5"));
        four.collapseEmptyPackages();
        root.insertNode(four);
        root.collapseEmptyPackages();
        assertThat(root).hasName("Root.1.2.3.4.5");
    }

    @Test
    void shouldHaveToString() {
        var root = new LabeledTreeMapNode(ID, "Root");
        assertThat(root.toString()).isEqualTo("'Root' ([])");
    }

    @Test
    void shouldHaveEquals() {
        EqualsVerifier.simple()
                .forClass(LabeledTreeMapNode.class)
                .withPrefabValues(LabeledTreeMapNode.class, new LabeledTreeMapNode(ID, "1"), new LabeledTreeMapNode(ID, "2"))
                .verify();
    }

    private LabeledTreeMapNode createRoot() {
        return new LabeledTreeMapNode(ID, "Root");
    }
}
