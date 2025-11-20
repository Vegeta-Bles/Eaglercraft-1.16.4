package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

public class IntArrayTag extends AbstractListTag<IntTag> {
   public static final TagReader<IntArrayTag> READER = new TagReader<IntArrayTag>() {
      public IntArrayTag read(DataInput _snowman, int _snowman, PositionTracker _snowman) throws IOException {
         _snowman.add(192L);
         int _snowmanxxx = _snowman.readInt();
         _snowman.add(32L * (long)_snowmanxxx);
         int[] _snowmanxxxx = new int[_snowmanxxx];

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
            _snowmanxxxx[_snowmanxxxxx] = _snowman.readInt();
         }

         return new IntArrayTag(_snowmanxxxx);
      }

      @Override
      public String getCrashReportName() {
         return "INT[]";
      }

      @Override
      public String getCommandFeedbackName() {
         return "TAG_Int_Array";
      }
   };
   private int[] value;

   public IntArrayTag(int[] value) {
      this.value = value;
   }

   public IntArrayTag(List<Integer> value) {
      this(toArray(value));
   }

   private static int[] toArray(List<Integer> list) {
      int[] _snowman = new int[list.size()];

      for (int _snowmanx = 0; _snowmanx < list.size(); _snowmanx++) {
         Integer _snowmanxx = list.get(_snowmanx);
         _snowman[_snowmanx] = _snowmanxx == null ? 0 : _snowmanxx;
      }

      return _snowman;
   }

   @Override
   public void write(DataOutput output) throws IOException {
      output.writeInt(this.value.length);

      for (int _snowman : this.value) {
         output.writeInt(_snowman);
      }
   }

   @Override
   public byte getType() {
      return 11;
   }

   @Override
   public TagReader<IntArrayTag> getReader() {
      return READER;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[I;");

      for (int _snowmanx = 0; _snowmanx < this.value.length; _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.value[_snowmanx]);
      }

      return _snowman.append(']').toString();
   }

   public IntArrayTag copy() {
      int[] _snowman = new int[this.value.length];
      System.arraycopy(this.value, 0, _snowman, 0, this.value.length);
      return new IntArrayTag(_snowman);
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o instanceof IntArrayTag && Arrays.equals(this.value, ((IntArrayTag)o).value);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.value);
   }

   public int[] getIntArray() {
      return this.value;
   }

   @Override
   public Text toText(String indent, int depth) {
      Text _snowman = new LiteralText("I").formatted(RED);
      MutableText _snowmanx = new LiteralText("[").append(_snowman).append(";");

      for (int _snowmanxx = 0; _snowmanxx < this.value.length; _snowmanxx++) {
         _snowmanx.append(" ").append(new LiteralText(String.valueOf(this.value[_snowmanxx])).formatted(GOLD));
         if (_snowmanxx != this.value.length - 1) {
            _snowmanx.append(",");
         }
      }

      _snowmanx.append("]");
      return _snowmanx;
   }

   @Override
   public int size() {
      return this.value.length;
   }

   public IntTag get(int index) {
      return IntTag.of(this.value[index]);
   }

   public IntTag set(int index, IntTag tag) {
      int previous = this.value[index];
      this.value[index] = tag.getInt();
      return IntTag.of(previous);
   }

   public void add(int index, IntTag tag) {
      this.value = ArrayUtils.add(this.value, index, tag.getInt());
   }

   @Override
   public boolean setTag(int index, Tag tag) {
      if (tag instanceof AbstractNumberTag) {
         this.value[index] = ((AbstractNumberTag)tag).getInt();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int index, Tag tag) {
      if (tag instanceof AbstractNumberTag) {
         this.value = ArrayUtils.add(this.value, index, ((AbstractNumberTag)tag).getInt());
         return true;
      } else {
         return false;
      }
   }

   public IntTag remove(int index) {
      int valueAtIndex = this.value[index];
      this.value = ArrayUtils.remove(this.value, index);
      return IntTag.of(valueAtIndex);
   }

   @Override
   public byte getElementType() {
      return 3;
   }

   @Override
   public void clear() {
      this.value = new int[0];
   }
}
