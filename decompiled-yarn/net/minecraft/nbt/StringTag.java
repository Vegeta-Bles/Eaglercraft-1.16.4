package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class StringTag implements Tag {
   public static final TagReader<StringTag> READER = new TagReader<StringTag>() {
      public StringTag read(DataInput _snowman, int _snowman, PositionTracker _snowman) throws IOException {
         _snowman.add(288L);
         String _snowmanxxx = _snowman.readUTF();
         _snowman.add((long)(16 * _snowmanxxx.length()));
         return StringTag.of(_snowmanxxx);
      }

      @Override
      public String getCrashReportName() {
         return "STRING";
      }

      @Override
      public String getCommandFeedbackName() {
         return "TAG_String";
      }

      @Override
      public boolean isImmutable() {
         return true;
      }
   };
   private static final StringTag EMPTY = new StringTag("");
   private final String value;

   private StringTag(String value) {
      Objects.requireNonNull(value, "Null string not allowed");
      this.value = value;
   }

   public static StringTag of(String value) {
      return value.isEmpty() ? EMPTY : new StringTag(value);
   }

   @Override
   public void write(DataOutput output) throws IOException {
      output.writeUTF(this.value);
   }

   @Override
   public byte getType() {
      return 8;
   }

   @Override
   public TagReader<StringTag> getReader() {
      return READER;
   }

   @Override
   public String toString() {
      return escape(this.value);
   }

   public StringTag copy() {
      return this;
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o instanceof StringTag && Objects.equals(this.value, ((StringTag)o).value);
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   @Override
   public String asString() {
      return this.value;
   }

   @Override
   public Text toText(String indent, int depth) {
      String _snowman = escape(this.value);
      String _snowmanx = _snowman.substring(0, 1);
      Text _snowmanxx = new LiteralText(_snowman.substring(1, _snowman.length() - 1)).formatted(GREEN);
      return new LiteralText(_snowmanx).append(_snowmanxx).append(_snowmanx);
   }

   public static String escape(String value) {
      StringBuilder _snowman = new StringBuilder(" ");
      char _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < value.length(); _snowmanxx++) {
         char _snowmanxxx = value.charAt(_snowmanxx);
         if (_snowmanxxx == '\\') {
            _snowman.append('\\');
         } else if (_snowmanxxx == '"' || _snowmanxxx == '\'') {
            if (_snowmanx == 0) {
               _snowmanx = (char)(_snowmanxxx == '"' ? 39 : 34);
            }

            if (_snowmanx == _snowmanxxx) {
               _snowman.append('\\');
            }
         }

         _snowman.append(_snowmanxxx);
      }

      if (_snowmanx == 0) {
         _snowmanx = '"';
      }

      _snowman.setCharAt(0, _snowmanx);
      _snowman.append(_snowmanx);
      return _snowman.toString();
   }
}
