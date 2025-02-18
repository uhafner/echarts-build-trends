package edu.hm.hafner.echarts;

import java.util.Locale;

/**
 * Label style for an item without children.
 *
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Florian Orendi
 */
public class Label {
    /** Available font thick weights. */
    public enum FontWeight {
        LIGHTER, NORMAL, BOLD, BOLDER
    }

    private final boolean show;
    private final String color;
    private final String fontWeight;

    /**
     * Creates a new {@link  Label} with the given values and a font weight of {@link FontWeight#BOLD}.
     *
     * @param show
     *         whether the label should be shown or not
     * @param color
     *         the text color of the label
     */
    public Label(final boolean show, final String color) {
        this(show, color, FontWeight.BOLD);
    }

    /**
     * Creates a new {@link  Label} with the given values.
     *
     * @param show
     *         whether the label should be shown or not
     * @param color
     *         the text color of the label
     * @param fontWeight
     *         the font weight to use for the text
     */
    public Label(final boolean show, final String color, final FontWeight fontWeight) {
        this.show = show;
        this.color = color;
        this.fontWeight = fontWeight.name().toLowerCase(Locale.ENGLISH);
    }

    @SuppressWarnings("PMD.BooleanGetMethodName")
    public boolean getShow() {
        return show;
    }

    public String getColor() {
        return color;
    }

    public String getFontWeight() {
        return fontWeight;
    }
}
