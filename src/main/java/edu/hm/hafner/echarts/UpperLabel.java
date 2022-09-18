package edu.hm.hafner.echarts;

/**
 * Label style for an item with children.
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Florian Orendi
 */
public class UpperLabel {

    private final boolean show;
    private final String color;

    /**
     * Creates a new {@link  UpperLabel} with the given values.
     *
     * @param show
     *         whether the label should be shown or not
     * @param color
     *         the text color of the label
     */
    public UpperLabel(final boolean show, final String color) {
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
