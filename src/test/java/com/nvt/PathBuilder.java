package com.nvt;

import static java.lang.String.format;

public class PathBuilder {

    private final StringBuilder sb = new StringBuilder("data/");
    private boolean ordered;
    private boolean reverseOrdered;

    public static PathBuilder builder() {
        return new PathBuilder();
    }

    public PathBuilder withOriginal() {
        sb.append("original/");
        return this;
    }

    public PathBuilder withOrdering() {
        ordered = true;
        sb.append("ordered/");
        return this;
    }

    public PathBuilder reverse() {
        reverseOrdered = true;
        return this;
    }

    public PathBuilder withAllData() {
        sb.append("all/");
        return this;
    }

    public PathBuilder withRange() {
        sb.append("range/");
        return this;
    }

    public PathBuilder incomingDataFile() {
        return addFileName("incoming_data");
    }

    public PathBuilder validDataFile() {
        return addFileName("valid_data");
    }

    public PathBuilder invalidDataFile() {
        return addFileName("invalid_data");
    }

    public String build() {
        return sb.toString();
    }

    private PathBuilder addFileName(String fileName) {
        if (ordered && reverseOrdered) {
            sb.append(format("%s_reverse_ordered.json", fileName));
            return this;
        }

        if (ordered) {
            sb.append(format("%s_ordered.json", fileName));
            return this;
        }

        sb.append(format("%s.json", fileName));
        return this;
    }

}
