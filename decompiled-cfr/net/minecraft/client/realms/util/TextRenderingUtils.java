/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.realms.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TextRenderingUtils {
    @VisibleForTesting
    protected static List<String> lineBreak(String text) {
        return Arrays.asList(text.split("\\n"));
    }

    public static List<Line> decompose(String text, LineSegment ... links) {
        return TextRenderingUtils.decompose(text, Arrays.asList(links));
    }

    private static List<Line> decompose(String text, List<LineSegment> links) {
        List<String> list = TextRenderingUtils.lineBreak(text);
        return TextRenderingUtils.insertLinks(list, links);
    }

    private static List<Line> insertLinks(List<String> lines, List<LineSegment> links) {
        int n = 0;
        ArrayList _snowman2 = Lists.newArrayList();
        for (String string : lines) {
            ArrayList arrayList = Lists.newArrayList();
            List<String> _snowman3 = TextRenderingUtils.split(string, "%link");
            for (String string2 : _snowman3) {
                if ("%link".equals(string2)) {
                    arrayList.add(links.get(n++));
                    continue;
                }
                arrayList.add(LineSegment.text(string2));
            }
            _snowman2.add(new Line(arrayList));
        }
        return _snowman2;
    }

    public static List<String> split(String line, String delimiter) {
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException("Delimiter cannot be the empty string");
        }
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = 0;
        while ((_snowman = line.indexOf(delimiter, _snowman2)) != -1) {
            if (_snowman > _snowman2) {
                arrayList.add(line.substring(_snowman2, _snowman));
            }
            arrayList.add(delimiter);
            _snowman2 = _snowman + delimiter.length();
        }
        if (_snowman2 < line.length()) {
            arrayList.add(line.substring(_snowman2));
        }
        return arrayList;
    }

    public static class LineSegment {
        private final String fullText;
        private final String linkTitle;
        private final String linkUrl;

        private LineSegment(String fullText) {
            this.fullText = fullText;
            this.linkTitle = null;
            this.linkUrl = null;
        }

        private LineSegment(String fullText, String linkTitle, String linkUrl) {
            this.fullText = fullText;
            this.linkTitle = linkTitle;
            this.linkUrl = linkUrl;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            LineSegment lineSegment = (LineSegment)o;
            return Objects.equals(this.fullText, lineSegment.fullText) && Objects.equals(this.linkTitle, lineSegment.linkTitle) && Objects.equals(this.linkUrl, lineSegment.linkUrl);
        }

        public int hashCode() {
            return Objects.hash(this.fullText, this.linkTitle, this.linkUrl);
        }

        public String toString() {
            return "Segment{fullText='" + this.fullText + '\'' + ", linkTitle='" + this.linkTitle + '\'' + ", linkUrl='" + this.linkUrl + '\'' + '}';
        }

        public String renderedText() {
            return this.isLink() ? this.linkTitle : this.fullText;
        }

        public boolean isLink() {
            return this.linkTitle != null;
        }

        public String getLinkUrl() {
            if (!this.isLink()) {
                throw new IllegalStateException("Not a link: " + this);
            }
            return this.linkUrl;
        }

        public static LineSegment link(String linkTitle, String linkUrl) {
            return new LineSegment(null, linkTitle, linkUrl);
        }

        @VisibleForTesting
        protected static LineSegment text(String fullText) {
            return new LineSegment(fullText);
        }
    }

    public static class Line {
        public final List<LineSegment> segments;

        Line(List<LineSegment> segments) {
            this.segments = segments;
        }

        public String toString() {
            return "Line{segments=" + this.segments + '}';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Line line = (Line)o;
            return Objects.equals(this.segments, line.segments);
        }

        public int hashCode() {
            return Objects.hash(this.segments);
        }
    }
}

