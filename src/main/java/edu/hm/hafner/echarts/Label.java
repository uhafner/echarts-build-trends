package edu.hm.hafner.echarts;

/**
 * Label style for an item without children.
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Florian Orendi
 */
public class Label {

    private final boolean show;
    private final String color;

    /**
     * Creates a new {@link  Label} with the given values.
     *
     * @param show
     *         whether the label should be shown or not
     * @param color
     *         the text color of the label
     */
    public Label(final boolean show, final String color) {
        this.show = show;
        this.color = color;
    }

    public boolean getShow() {
        return show;
    }

    public String getColor() {
        return color;
    }
}
