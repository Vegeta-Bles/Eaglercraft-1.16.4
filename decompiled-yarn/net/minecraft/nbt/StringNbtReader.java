package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.text.TranslatableText;

public class StringNbtReader {
   public static final SimpleCommandExceptionType TRAILING = new SimpleCommandExceptionType(new TranslatableText("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType EXPECTED_KEY = new SimpleCommandExceptionType(new TranslatableText("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType EXPECTED_VALUE = new SimpleCommandExceptionType(new TranslatableText("argument.nbt.expected.value"));
   public static final Dynamic2CommandExceptionType LIST_MIXED = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("argument.nbt.list.mixed", _snowman, _snowmanx)
   );
   public static final Dynamic2CommandExceptionType ARRAY_MIXED = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("argument.nbt.array.mixed", _snowman, _snowmanx)
   );
   public static final DynamicCommandExceptionType ARRAY_INVALID = new DynamicCommandExceptionType(_snowman -> new TranslatableText("argument.nbt.array.invalid", _snowman));
   private static final Pattern DOUBLE_PATTERN_IMPLICIT = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern DOUBLE_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern BYTE_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern LONG_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern SHORT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern INT_PATTERN = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader reader;

   public static CompoundTag parse(String string) throws CommandSyntaxException {
      return new StringNbtReader(new StringReader(string)).readCompoundTag();
   }

   @VisibleForTesting
   CompoundTag readCompoundTag() throws CommandSyntaxException {
      CompoundTag _snowman = this.parseCompoundTag();
      this.reader.skipWhitespace();
      if (this.reader.canRead()) {
         throw TRAILING.createWithContext(this.reader);
      } else {
         return _snowman;
      }
   }

   public StringNbtReader(StringReader reader) {
      this.reader = reader;
   }

   protected String readString() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw EXPECTED_KEY.createWithContext(this.reader);
      } else {
         return this.reader.readString();
      }
   }

   protected Tag parseTagPrimitive() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      int _snowman = this.reader.getCursor();
      if (StringReader.isQuotedStringStart(this.reader.peek())) {
         return StringTag.of(this.reader.readQuotedString());
      } else {
         String _snowmanx = this.reader.readUnquotedString();
         if (_snowmanx.isEmpty()) {
            this.reader.setCursor(_snowman);
            throw EXPECTED_VALUE.createWithContext(this.reader);
         } else {
            return this.parsePrimitive(_snowmanx);
         }
      }
   }

   private Tag parsePrimitive(String input) {
      try {
         if (FLOAT_PATTERN.matcher(input).matches()) {
            return FloatTag.of(Float.parseFloat(input.substring(0, input.length() - 1)));
         }

         if (BYTE_PATTERN.matcher(input).matches()) {
            return ByteTag.of(Byte.parseByte(input.substring(0, input.length() - 1)));
         }

         if (LONG_PATTERN.matcher(input).matches()) {
            return LongTag.of(Long.parseLong(input.substring(0, input.length() - 1)));
         }

         if (SHORT_PATTERN.matcher(input).matches()) {
            return ShortTag.of(Short.parseShort(input.substring(0, input.length() - 1)));
         }

         if (INT_PATTERN.matcher(input).matches()) {
            return IntTag.of(Integer.parseInt(input));
         }

         if (DOUBLE_PATTERN.matcher(input).matches()) {
            return DoubleTag.of(Double.parseDouble(input.substring(0, input.length() - 1)));
         }

         if (DOUBLE_PATTERN_IMPLICIT.matcher(input).matches()) {
            return DoubleTag.of(Double.parseDouble(input));
         }

         if ("true".equalsIgnoreCase(input)) {
            return ByteTag.ONE;
         }

         if ("false".equalsIgnoreCase(input)) {
            return ByteTag.ZERO;
         }
      } catch (NumberFormatException var3) {
      }

      return StringTag.of(input);
   }

   public Tag parseTag() throws CommandSyntaxException {
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw EXPECTED_VALUE.createWithContext(this.reader);
      } else {
         char _snowman = this.reader.peek();
         if (_snowman == '{') {
            return this.parseCompoundTag();
         } else {
            return _snowman == '[' ? this.parseTagArray() : this.parseTagPrimitive();
         }
      }
   }

   protected Tag parseTagArray() throws CommandSyntaxException {
      return this.reader.canRead(3) && !StringReader.isQuotedStringStart(this.reader.peek(1)) && this.reader.peek(2) == ';'
         ? this.parseTagPrimitiveArray()
         : this.parseListTag();
   }

   public CompoundTag parseCompoundTag() throws CommandSyntaxException {
      this.expect('{');
      CompoundTag _snowman = new CompoundTag();
      this.reader.skipWhitespace();

      while (this.reader.canRead() && this.reader.peek() != '}') {
         int _snowmanx = this.reader.getCursor();
         String _snowmanxx = this.readString();
         if (_snowmanxx.isEmpty()) {
            this.reader.setCursor(_snowmanx);
            throw EXPECTED_KEY.createWithContext(this.reader);
         }

         this.expect(':');
         _snowman.put(_snowmanxx, this.parseTag());
         if (!this.readComma()) {
            break;
         }

         if (!this.reader.canRead()) {
            throw EXPECTED_KEY.createWithContext(this.reader);
         }
      }

      this.expect('}');
      return _snowman;
   }

   private Tag parseListTag() throws CommandSyntaxException {
      this.expect('[');
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw EXPECTED_VALUE.createWithContext(this.reader);
      } else {
         ListTag _snowman = new ListTag();
         TagReader<?> _snowmanx = null;

         while (this.reader.peek() != ']') {
            int _snowmanxx = this.reader.getCursor();
            Tag _snowmanxxx = this.parseTag();
            TagReader<?> _snowmanxxxx = _snowmanxxx.getReader();
            if (_snowmanx == null) {
               _snowmanx = _snowmanxxxx;
            } else if (_snowmanxxxx != _snowmanx) {
               this.reader.setCursor(_snowmanxx);
               throw LIST_MIXED.createWithContext(this.reader, _snowmanxxxx.getCommandFeedbackName(), _snowmanx.getCommandFeedbackName());
            }

            _snowman.add(_snowmanxxx);
            if (!this.readComma()) {
               break;
            }

            if (!this.reader.canRead()) {
               throw EXPECTED_VALUE.createWithContext(this.reader);
            }
         }

         this.expect(']');
         return _snowman;
      }
   }

   private Tag parseTagPrimitiveArray() throws CommandSyntaxException {
      this.expect('[');
      int _snowman = this.reader.getCursor();
      char _snowmanx = this.reader.read();
      this.reader.read();
      this.reader.skipWhitespace();
      if (!this.reader.canRead()) {
         throw EXPECTED_VALUE.createWithContext(this.reader);
      } else if (_snowmanx == 'B') {
         return new ByteArrayTag(this.readArray(ByteArrayTag.READER, ByteTag.READER));
      } else if (_snowmanx == 'L') {
         return new LongArrayTag(this.readArray(LongArrayTag.READER, LongTag.READER));
      } else if (_snowmanx == 'I') {
         return new IntArrayTag(this.readArray(IntArrayTag.READER, IntTag.READER));
      } else {
         this.reader.setCursor(_snowman);
         throw ARRAY_INVALID.createWithContext(this.reader, String.valueOf(_snowmanx));
      }
   }

   private <T extends Number> List<T> readArray(TagReader<?> arrayTypeReader, TagReader<?> typeReader) throws CommandSyntaxException {
      List<T> _snowman = Lists.newArrayList();

      while (this.reader.peek() != ']') {
         int _snowmanx = this.reader.getCursor();
         Tag _snowmanxx = this.parseTag();
         TagReader<?> _snowmanxxx = _snowmanxx.getReader();
         if (_snowmanxxx != typeReader) {
            this.reader.setCursor(_snowmanx);
            throw ARRAY_MIXED.createWithContext(this.reader, _snowmanxxx.getCommandFeedbackName(), arrayTypeReader.getCommandFeedbackName());
         }

         if (typeReader == ByteTag.READER) {
            _snowman.add((T)((AbstractNumberTag)_snowmanxx).getByte());
         } else if (typeReader == LongTag.READER) {
            _snowman.add((T)((AbstractNumberTag)_snowmanxx).getLong());
         } else {
            _snowman.add((T)((AbstractNumberTag)_snowmanxx).getInt());
         }

         if (!this.readComma()) {
            break;
         }

         if (!this.reader.canRead()) {
            throw EXPECTED_VALUE.createWithContext(this.reader);
         }
      }

      this.expect(']');
      return _snowman;
   }

   private boolean readComma() {
      this.reader.skipWhitespace();
      if (this.reader.canRead() && this.reader.peek() == ',') {
         this.reader.skip();
         this.reader.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void expect(char _snowman) throws CommandSyntaxException {
      this.reader.skipWhitespace();
      this.reader.expect(_snowman);
   }
}
