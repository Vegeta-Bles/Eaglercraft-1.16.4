package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

public class LongArrayTag extends AbstractListTag<LongTag> {
   public static final TagReader<LongArrayTag> READER = new TagReader<LongArrayTag>() {
      public LongArrayTag read(DataInput _snowman, int _snowman, PositionTracker _snowman) throws IOException {
         _snowman.add(192L);
         int _snowmanxxx = _snowman.readInt();
         _snowman.add(64L * (long)_snowmanxxx);
         long[] _snowmanxxxx = new long[_snowmanxxx];

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
            _snowmanxxxx[_snowmanxxxxx] = _snowman.readLong();
         }

         return new LongArrayTag(_snowmanxxxx);
      }

      @Override
      public String getCrashReportName() {
         return "LONG[]";
      }

      @Override
      public String getCommandFeedbackName() {
         return "TAG_Long_Array";
      }
   };
   private long[] value;

   public LongArrayTag(long[] value) {
      this.value = value;
   }

   public LongArrayTag(LongSet value) {
      this.value = value.toLongArray();
   }

   public LongArrayTag(List<Long> value) {
      this(toArray(value));
   }

   private static long[] toArray(List<Long> list) {
      long[] _snowman = new long[list.size()];

      for (int _snowmanx = 0; _snowmanx < list.size(); _snowmanx++) {
         Long _snowmanxx = list.get(_snowmanx);
         _snowman[_snowmanx] = _snowmanxx == null ? 0L : _snowmanxx;
      }

      return _snowman;
   }

   @Override
   public void write(DataOutput output) throws IOException {
      output.writeInt(this.value.length);

      for (long _snowman : this.value) {
         output.writeLong(_snowman);
      }
   }

   @Override
   public byte getType() {
      return 12;
   }

   @Override
   public TagReader<LongArrayTag> getReader() {
      return READER;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[L;");

      for (int _snowmanx = 0; _snowmanx < this.value.length; _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.value[_snowmanx]).append('L');
      }

      return _snowman.append(']').toString();
   }

   public LongArrayTag copy() {
      long[] _snowman = new long[this.value.length];
      System.arraycopy(this.value, 0, _snowman, 0, this.value.length);
      return new LongArrayTag(_snowman);
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o instanceof LongArrayTag && Arrays.equals(this.value, ((LongArrayTag)o).value);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.value);
   }

   @Override
   public Text toText(String indent, int depth) {
      Text _snowman = new LiteralText("L").formatted(RED);
      MutableText _snowmanx = new LiteralText("[").append(_snowman).append(";");

      for (int _snowmanxx = 0; _snowmanxx < this.value.length; _snowmanxx++) {
         MutableText _snowmanxxx = new LiteralText(String.valueOf(this.value[_snowmanxx])).formatted(GOLD);
         _snowmanx.append(" ").append(_snowmanxxx).append(_snowman);
         if (_snowmanxx != this.value.length - 1) {
            _snowmanx.append(",");
         }
      }

      _snowmanx.append("]");
      return _snowmanx;
   }

   public long[] getLongArray() {
      return this.value;
   }

   @Override
   public int size() {
      return this.value.length;
   }

   public LongTag get(int _snowman) {
      return LongTag.of(this.value[_snowman]);
   }

   public LongTag method_10606(int _snowman, LongTag _snowman) {
      long _snowmanxx = this.value[_snowman];
      this.value[_snowman] = _snowman.getLong();
      return LongTag.of(_snowmanxx);
   }

   public void add(int _snowman, LongTag _snowman) {
      this.value = ArrayUtils.add(this.value, _snowman, _snowman.getLong());
   }

   @Override
   public boolean setTag(int index, Tag tag) {
      if (tag instanceof AbstractNumberTag) {
         this.value[index] = ((AbstractNumberTag)tag).getLong();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int index, Tag tag) {
      if (tag instanceof AbstractNumberTag) {
         this.value = ArrayUtils.add(this.value, index, ((AbstractNumberTag)tag).getLong());
         return true;
      } else {
         return false;
      }
   }

   public LongTag remove(int _snowman) {
      long _snowmanx = this.value[_snowman];
      this.value = ArrayUtils.remove(this.value, _snowman);
      return LongTag.of(_snowmanx);
   }

   @Override
   public byte getElementType() {
      return 4;
   }

   @Override
   public void clear() {
      this.value = new long[0];
   }
}
