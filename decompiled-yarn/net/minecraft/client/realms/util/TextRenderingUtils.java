package net.minecraft.client.realms.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TextRenderingUtils {
   @VisibleForTesting
   protected static List<String> lineBreak(String text) {
      return Arrays.asList(text.split("\\n"));
   }

   public static List<TextRenderingUtils.Line> decompose(String text, TextRenderingUtils.LineSegment... links) {
      return decompose(text, Arrays.asList(links));
   }

   private static List<TextRenderingUtils.Line> decompose(String text, List<TextRenderingUtils.LineSegment> links) {
      List<String> _snowman = lineBreak(text);
      return insertLinks(_snowman, links);
   }

   private static List<TextRenderingUtils.Line> insertLinks(List<String> lines, List<TextRenderingUtils.LineSegment> links) {
      int _snowman = 0;
      List<TextRenderingUtils.Line> _snowmanx = Lists.newArrayList();

      for (String _snowmanxx : lines) {
         List<TextRenderingUtils.LineSegment> _snowmanxxx = Lists.newArrayList();

         for (String _snowmanxxxx : split(_snowmanxx, "%link")) {
            if ("%link".equals(_snowmanxxxx)) {
               _snowmanxxx.add(links.get(_snowman++));
            } else {
               _snowmanxxx.add(TextRenderingUtils.LineSegment.text(_snowmanxxxx));
            }
         }

         _snowmanx.add(new TextRenderingUtils.Line(_snowmanxxx));
      }

      return _snowmanx;
   }

   public static List<String> split(String line, String delimiter) {
      if (delimiter.isEmpty()) {
         throw new IllegalArgumentException("Delimiter cannot be the empty string");
      } else {
         List<String> _snowman = Lists.newArrayList();
         int _snowmanx = 0;

         int _snowmanxx;
         while ((_snowmanxx = line.indexOf(delimiter, _snowmanx)) != -1) {
            if (_snowmanxx > _snowmanx) {
               _snowman.add(line.substring(_snowmanx, _snowmanxx));
            }

            _snowman.add(delimiter);
            _snowmanx = _snowmanxx + delimiter.length();
         }

         if (_snowmanx < line.length()) {
            _snowman.add(line.substring(_snowmanx));
         }

         return _snowman;
      }
   }

   public static class Line {
      public final List<TextRenderingUtils.LineSegment> segments;

      Line(List<TextRenderingUtils.LineSegment> segments) {
         this.segments = segments;
      }

      @Override
      public String toString() {
         return "Line{segments=" + this.segments + '}';
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            TextRenderingUtils.Line _snowman = (TextRenderingUtils.Line)o;
            return Objects.equals(this.segments, _snowman.segments);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.segments);
      }
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

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            TextRenderingUtils.LineSegment _snowman = (TextRenderingUtils.LineSegment)o;
            return Objects.equals(this.fullText, _snowman.fullText) && Objects.equals(this.linkTitle, _snowman.linkTitle) && Objects.equals(this.linkUrl, _snowman.linkUrl);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.fullText, this.linkTitle, this.linkUrl);
      }

      @Override
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
         } else {
            return this.linkUrl;
         }
      }

      public static TextRenderingUtils.LineSegment link(String linkTitle, String linkUrl) {
         return new TextRenderingUtils.LineSegment(null, linkTitle, linkUrl);
      }

      @VisibleForTesting
      protected static TextRenderingUtils.LineSegment text(String fullText) {
         return new TextRenderingUtils.LineSegment(fullText);
      }
   }
}
