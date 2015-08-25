package utilities;

import java.awt.*;

/**
 * @author danecek
 */
public class GridBagConstraintsBuilder {

    GridBagConstraints gbc = new GridBagConstraints();

    public GridBagConstraintsBuilder(GridBagConstraints gbc) {
        this.gbc = (GridBagConstraints) gbc.clone();
    }

    public GridBagConstraintsBuilder() {
        setInsets(5);
        setAnchor(Anchor.FIRST_LINE_START);
    }

    public GridBagConstraintsBuilder setXRel() {
        return setX(GridBagConstraints.RELATIVE);
    }

    public GridBagConstraintsBuilder setX(int gridx) {
        gbc.gridx = gridx;
        return this;
    }

    public GridBagConstraintsBuilder setY(int gridy) {
        gbc.gridy = gridy;
        return this;
    }

    public GridBagConstraintsBuilder setWidth(int gridwidth) {
        gbc.gridwidth = gridwidth;
        return this;
    }

    public GridBagConstraintsBuilder setHeight(int gridheight) {
        gbc.gridheight = gridheight;
        return this;
    }

    public GridBagConstraintsBuilder setWeightx(double weightx) {
        gbc.weightx = weightx;
        return this;
    }

    public GridBagConstraintsBuilder setWeighty(double weighty) {
        gbc.weighty = weighty;
        return this;
    }

    public final GridBagConstraintsBuilder setAnchor(Anchor anchor) {
        switch (anchor) {
            case FIRST_LINE_START:
                gbc.anchor = GridBagConstraints.FIRST_LINE_START;
                return this;
            case PAGE_START:
                gbc.anchor = GridBagConstraints.PAGE_START;
                return this;
            case FIRST_LINE_END:
                gbc.anchor = GridBagConstraints.FIRST_LINE_END;
                return this;
            case LINE_START:
                gbc.anchor = GridBagConstraints.LINE_START;
                return this;
            case CENTER:
                gbc.anchor = GridBagConstraints.CENTER;
                return this;
            case LINE_END:
                gbc.anchor = GridBagConstraints.LINE_END;
                return this;
            case LAST_LINE_START:
                gbc.anchor = GridBagConstraints.LAST_LINE_START;
                return this;
            case PAGE_END:
                gbc.anchor = GridBagConstraints.PAGE_END;
                return this;
            case LAST_LINE_END:
                gbc.anchor = GridBagConstraints.LAST_LINE_END;
                return this;
        }
        return this;
    }

    public GridBagConstraintsBuilder setFill(Fill fill) {
        switch (fill) {
            case NONE:
                gbc.fill = GridBagConstraints.NONE;
                return this;
            case HORIZONTAL:
                gbc.fill = GridBagConstraints.HORIZONTAL;
                return this;
            case VERTICAL:
                gbc.fill = GridBagConstraints.VERTICAL;
                return this;
            case BOTH:
                gbc.fill = GridBagConstraints.BOTH;
                return this;
        }
        return this;
    }

    public final GridBagConstraintsBuilder setInsets(int insets) {
        gbc.insets = new Insets(insets, insets, insets, insets);
        return this;
    }

    public GridBagConstraintsBuilder setInsets(Insets insets) {
        gbc.insets = insets;
        return this;
    }

    public GridBagConstraintsBuilder setIpadx(int ipadx) {
        gbc.ipadx = ipadx;
        return this;
    }

    public GridBagConstraintsBuilder setIpady(int ipady) {
        gbc.ipady = ipady;
        return this;
    }

    public GridBagConstraints build() {
        return gbc;
    }

    public static enum Fill {

        NONE,//the default), 
        HORIZONTAL,// (make the component wide enough to fill its display area horizontally, but do not change its height), 
        VERTICAL,//(make the component tall enough to fill its display area vertically, but do not change its width),
        BOTH;
    }

    public enum Anchor {

        FIRST_LINE_START,
        PAGE_START,
        FIRST_LINE_END,
        LINE_START,
        CENTER,
        LINE_END,
        LAST_LINE_START,
        PAGE_END,
        LAST_LINE_END,
    }
}