package edu.hm.hafner.echarts;

/**
 * Item style for a chart.
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Ullrich Hafner
 */
public class ItemStyle {
    private final String color;
    private final String borderColor;
    private final int borderWidth;

    /**
     * Creates a new {@link ItemStyle} instance with the specified color.
     *
     * @param color
     *         the color to use
     */
    public ItemStyle(final String color) {
        this(color, "#ffffff", 0);
    }

    /**
     * Creates a new {@link ItemStyle} instance with the specified color and border.
     *
     * @param color
     *         the color to use
     * @param borderColor
     *         the border color to use
     * @param borderWidth
     *         the border width
     */
    public ItemStyle(final String color, final String borderColor, final int borderWidth) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public String getColor() {
        return color;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }
}
