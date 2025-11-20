package net.minecraft.client.realms.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TextRenderingUtils {
   @VisibleForTesting
   protected static List<String> lineBreak(String text) {
      return Arrays.asList(text.split("\\n"));
   }

   public static List<TextRenderingUtils.Line> decompose(String text, TextRenderingUtils.LineSegment... links) {
      return decompose(text, Arrays.asList(links));
   }

   private static List<TextRenderingUtils.Line> decompose(String text, List<TextRenderingUtils.LineSegment> links) {
      List<String> list2 = lineBreak(text);
      return insertLinks(list2, links);
   }

   private static List<TextRenderingUtils.Line> insertLinks(List<String> lines, List<TextRenderingUtils.LineSegment> links) {
      int i = 0;
      List<TextRenderingUtils.Line> list3 = Lists.newArrayList();

      for (String string : lines) {
         List<TextRenderingUtils.LineSegment> list4 = Lists.newArrayList();

         for (String string2 : split(string, "%link")) {
            if ("%link".equals(string2)) {
               list4.add(links.get(i++));
            } else {
               list4.add(TextRenderingUtils.LineSegment.text(string2));
            }
         }

         list3.add(new TextRenderingUtils.Line(list4));
      }

      return list3;
   }

   public static List<String> split(String line, String delimiter) {
      if (delimiter.isEmpty()) {
         throw new IllegalArgumentException("Delimiter cannot be the empty string");
      } else {
         List<String> list = Lists.newArrayList();
         int i = 0;

         int j;
         while ((j = line.indexOf(delimiter, i)) != -1) {
            if (j > i) {
               list.add(line.substring(i, j));
            }

            list.add(delimiter);
            i = j + delimiter.length();
         }

         if (i < line.length()) {
            list.add(line.substring(i));
         }

         return list;
      }
   }

   @Environment(EnvType.CLIENT)
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
            TextRenderingUtils.Line lv = (TextRenderingUtils.Line)o;
            return Objects.equals(this.segments, lv.segments);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.segments);
      }
   }

   @Environment(EnvType.CLIENT)
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
            TextRenderingUtils.LineSegment lv = (TextRenderingUtils.LineSegment)o;
            return Objects.equals(this.fullText, lv.fullText) && Objects.equals(this.linkTitle, lv.linkTitle) && Objects.equals(this.linkUrl, lv.linkUrl);
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
