package be.elgem.gameoflife.render;

import java.util.Objects;

public class Index {
    private int xIndex;
    private int yIndex;

    public Index() {
        this(0, 0);
    }

    public Index(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    public int getXIndex() {
        return xIndex;
    }

    public void setXIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    public int getYIndex() {
        return yIndex;
    }

    public void setYIndex(int yIndex) {
        this.yIndex = yIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return xIndex == index.xIndex && yIndex == index.yIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xIndex, yIndex);
    }
}
