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

public class ByteArrayTag extends AbstractListTag<ByteTag> {
   public static final TagReader<ByteArrayTag> READER = new TagReader<ByteArrayTag>() {
      public ByteArrayTag read(DataInput _snowman, int _snowman, PositionTracker _snowman) throws IOException {
         _snowman.add(192L);
         int _snowmanxxx = _snowman.readInt();
         _snowman.add(8L * (long)_snowmanxxx);
         byte[] _snowmanxxxx = new byte[_snowmanxxx];
         _snowman.readFully(_snowmanxxxx);
         return new ByteArrayTag(_snowmanxxxx);
      }

      @Override
      public String getCrashReportName() {
         return "BYTE[]";
      }

      @Override
      public String getCommandFeedbackName() {
         return "TAG_Byte_Array";
      }
   };
   private byte[] value;

   public ByteArrayTag(byte[] value) {
      this.value = value;
   }

   public ByteArrayTag(List<Byte> value) {
      this(toArray(value));
   }

   private static byte[] toArray(List<Byte> list) {
      byte[] _snowman = new byte[list.size()];

      for (int _snowmanx = 0; _snowmanx < list.size(); _snowmanx++) {
         Byte _snowmanxx = list.get(_snowmanx);
         _snowman[_snowmanx] = _snowmanxx == null ? 0 : _snowmanxx;
      }

      return _snowman;
   }

   @Override
   public void write(DataOutput output) throws IOException {
      output.writeInt(this.value.length);
      output.write(this.value);
   }

   @Override
   public byte getType() {
      return 7;
   }

   @Override
   public TagReader<ByteArrayTag> getReader() {
      return READER;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[B;");

      for (int _snowmanx = 0; _snowmanx < this.value.length; _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.value[_snowmanx]).append('B');
      }

      return _snowman.append(']').toString();
   }

   @Override
   public Tag copy() {
      byte[] _snowman = new byte[this.value.length];
      System.arraycopy(this.value, 0, _snowman, 0, this.value.length);
      return new ByteArrayTag(_snowman);
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o instanceof ByteArrayTag && Arrays.equals(this.value, ((ByteArrayTag)o).value);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.value);
   }

   @Override
   public Text toText(String indent, int depth) {
      Text _snowman = new LiteralText("B").formatted(RED);
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

   public byte[] getByteArray() {
      return this.value;
   }

   @Override
   public int size() {
      return this.value.length;
   }

   public ByteTag get(int _snowman) {
      return ByteTag.of(this.value[_snowman]);
   }

   public ByteTag set(int _snowman, ByteTag _snowman) {
      byte _snowmanxx = this.value[_snowman];
      this.value[_snowman] = _snowman.getByte();
      return ByteTag.of(_snowmanxx);
   }

   public void method_10531(int _snowman, ByteTag _snowman) {
      this.value = ArrayUtils.add(this.value, _snowman, _snowman.getByte());
   }

   @Override
   public boolean setTag(int index, Tag tag) {
      if (tag instanceof AbstractNumberTag) {
         this.value[index] = ((AbstractNumberTag)tag).getByte();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int index, Tag tag) {
      if (tag instanceof AbstractNumberTag) {
         this.value = ArrayUtils.add(this.value, index, ((AbstractNumberTag)tag).getByte());
         return true;
      } else {
         return false;
      }
   }

   public ByteTag method_10536(int _snowman) {
      byte _snowmanx = this.value[_snowman];
      this.value = ArrayUtils.remove(this.value, _snowman);
      return ByteTag.of(_snowmanx);
   }

   @Override
   public byte getElementType() {
      return 1;
   }

   @Override
   public void clear() {
      this.value = new byte[0];
   }
}
