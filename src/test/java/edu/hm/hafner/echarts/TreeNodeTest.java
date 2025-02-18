package edu.hm.hafner.echarts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static edu.hm.hafner.echarts.assertions.Assertions.*;

/**
 * Test for the class {@link TreeNode}.
 *
 * @author Andreas Pabst
 */
public class TreeNodeTest {
    /**
     * Test if packages with two identical package levels are inserted correctly.
     */
    @Test
    public void shouldInsertTwoLevelPackage() {
        var root = new TreeNode("");

        var myClass = new TreeNode("com.example.MyClass");
        var otherClass = new TreeNode("com.example.other.OtherClass");

        root.insertNode(myClass);
        root.insertNode(otherClass);

        assertThat(root.getChildren()).hasSize(1);
        var child = root.getChildren().get(0);
        assertThat(child).hasName("com");

        Assertions.assertThat(child.getChildren()).hasSize(1);
        child = child.getChildren().get(0);
        assertThat(child).hasName("example");

        Assertions.assertThat(child.getChildren()).hasSize(2);
        assertThat(child.getChildren().get(0)).hasName("MyClass");

        child = child.getChildren().get(1);
        assertThat(child).hasName("other");
        Assertions.assertThat(child.getChildren()).hasSize(1);
        assertThat(child.getChildren().get(0)).hasName("OtherClass");
    }

    /**
     * Test if the value of the metric is kept correctly.
     */
    @Test
    public void shouldGetSpecificMetricValue() {
        final double metricValue = 42;

        var node = new TreeNode("node", metricValue);
        Assertions.assertThat(node.getValue()).isEqualTo(42);
    }

    /**
     * Test if all children values are summed up correctly.
     */
    @Test
    public void shouldSumUpChildrenValues() {
        final double metricValue1 = 42;
        final double metricValue2 = 47;
        final double metricValue3 = 11;

        var root = new TreeNode("");
        root.insertNode(new TreeNode("test.node1", metricValue1));
        root.insertNode(new TreeNode("test.node2", metricValue2));
        root.insertNode(new TreeNode("node3", metricValue3));

        Assertions.assertThat(root.getValue()).isEqualTo(metricValue1 + metricValue2 + metricValue3);
    }

    /**
     * Test if the package is collapsed correctly.
     */
    @Test
    public void shouldCollapsePackage() {
        var rootNode = threeLevelTree();
        rootNode.collapsePackage();

        Assertions.assertThat(rootNode.getName()).isEqualTo("levelOneNode.levelTwoNode");
        Assertions.assertThat(rootNode.getChildren()).hasSize(2);
    }

    private TreeNode threeLevelTree() {
        var leafNode2 = new TreeNode("leafNode1");
        var leafNode1 = new TreeNode("leafNode2");
        var levelTwoNode = new TreeNode("levelTwoNode");
        levelTwoNode.insertNode(leafNode1);
        levelTwoNode.insertNode(leafNode2);

        var levelOneNode = new TreeNode("levelOneNode");
        levelOneNode.insertNode(levelTwoNode);

        var rootNode = new TreeNode("");
        rootNode.insertNode(levelOneNode);

        return rootNode;
    }

    /**
     * Test the equals and hash functions.
     */
    @Test
    public void shouldBeEqualAndHash() {
        final var name = "name";
        final double one = 1.0;
        var node = new TreeNode(name);

        assertThat(node).isNotEqualTo(new TreeNode(name, one));
        assertThat(node).isEqualTo(node);
        assertThat(node).isEqualTo(new TreeNode(name));

        node.insertNode(new TreeNode("name1"));
        assertThat(node).isNotEqualTo(new TreeNode(name));

        assertThat(node).isNotEqualTo("test");

        Assertions.assertThat(node.hashCode()).isEqualTo(node.hashCode());

        Assertions.assertThat(node.hashCode()).isNotEqualTo(new TreeNode(name).hashCode());
    }

    /**
     * Test if the JSON serialisation is correct.
     */
    @Test
    public void shouldContainRelevantInformationInJson() {
        var facade = new JacksonFacade();
        var root = new TreeNode("");
        root.insertNode(new TreeNode("com.example.Bar", 5.0));
        root.insertNode(new TreeNode("com.example.package.Foo", 2.0));
        root.collapsePackage();

        assertThat(facade.toJson(root)).isEqualTo("{\"name\":\"com.example\",\"value\":7.0,\"children\":["
                + "{\"name\":\"Bar\",\"value\":5.0,\"children\":[]},"
                + "{\"name\":\"package\",\"value\":2.0,\"children\":["
                + "{\"name\":\"Foo\",\"value\":2.0,\"children\":[]}"
                + "]}]}");
    }
}
