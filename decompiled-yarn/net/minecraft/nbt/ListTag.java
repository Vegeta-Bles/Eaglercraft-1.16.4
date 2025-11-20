package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class ListTag extends AbstractListTag<Tag> {
   public static final TagReader<ListTag> READER = new TagReader<ListTag>() {
      public ListTag read(DataInput _snowman, int _snowman, PositionTracker _snowman) throws IOException {
         _snowman.add(296L);
         if (_snowman > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            byte _snowmanxxx = _snowman.readByte();
            int _snowmanxxxx = _snowman.readInt();
            if (_snowmanxxx == 0 && _snowmanxxxx > 0) {
               throw new RuntimeException("Missing type on ListTag");
            } else {
               _snowman.add(32L * (long)_snowmanxxxx);
               TagReader<?> _snowmanxxxxx = TagReaders.of(_snowmanxxx);
               List<Tag> _snowmanxxxxxx = Lists.newArrayListWithCapacity(_snowmanxxxx);

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
                  _snowmanxxxxxx.add(_snowmanxxxxx.read(_snowman, _snowman + 1, _snowman));
               }

               return new ListTag(_snowmanxxxxxx, _snowmanxxx);
            }
         }
      }

      @Override
      public String getCrashReportName() {
         return "LIST";
      }

      @Override
      public String getCommandFeedbackName() {
         return "TAG_List";
      }
   };
   private static final ByteSet NBT_NUMBER_TYPES = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
   private final List<Tag> value;
   private byte type;

   private ListTag(List<Tag> list, byte type) {
      this.value = list;
      this.type = type;
   }

   public ListTag() {
      this(Lists.newArrayList(), (byte)0);
   }

   @Override
   public void write(DataOutput output) throws IOException {
      if (this.value.isEmpty()) {
         this.type = 0;
      } else {
         this.type = this.value.get(0).getType();
      }

      output.writeByte(this.type);
      output.writeInt(this.value.size());

      for (Tag _snowman : this.value) {
         _snowman.write(output);
      }
   }

   @Override
   public byte getType() {
      return 9;
   }

   @Override
   public TagReader<ListTag> getReader() {
      return READER;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("[");

      for (int _snowmanx = 0; _snowmanx < this.value.size(); _snowmanx++) {
         if (_snowmanx != 0) {
            _snowman.append(',');
         }

         _snowman.append(this.value.get(_snowmanx));
      }

      return _snowman.append(']').toString();
   }

   private void forgetTypeIfEmpty() {
      if (this.value.isEmpty()) {
         this.type = 0;
      }
   }

   @Override
   public Tag remove(int _snowman) {
      Tag _snowmanx = this.value.remove(_snowman);
      this.forgetTypeIfEmpty();
      return _snowmanx;
   }

   @Override
   public boolean isEmpty() {
      return this.value.isEmpty();
   }

   public CompoundTag getCompound(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         if (_snowman.getType() == 10) {
            return (CompoundTag)_snowman;
         }
      }

      return new CompoundTag();
   }

   public ListTag getList(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         if (_snowman.getType() == 9) {
            return (ListTag)_snowman;
         }
      }

      return new ListTag();
   }

   public short getShort(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         if (_snowman.getType() == 2) {
            return ((ShortTag)_snowman).getShort();
         }
      }

      return 0;
   }

   public int getInt(int _snowman) {
      if (_snowman >= 0 && _snowman < this.value.size()) {
         Tag _snowmanx = this.value.get(_snowman);
         if (_snowmanx.getType() == 3) {
            return ((IntTag)_snowmanx).getInt();
         }
      }

      return 0;
   }

   public int[] getIntArray(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         if (_snowman.getType() == 11) {
            return ((IntArrayTag)_snowman).getIntArray();
         }
      }

      return new int[0];
   }

   public double getDouble(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         if (_snowman.getType() == 6) {
            return ((DoubleTag)_snowman).getDouble();
         }
      }

      return 0.0;
   }

   public float getFloat(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         if (_snowman.getType() == 5) {
            return ((FloatTag)_snowman).getFloat();
         }
      }

      return 0.0F;
   }

   public String getString(int index) {
      if (index >= 0 && index < this.value.size()) {
         Tag _snowman = this.value.get(index);
         return _snowman.getType() == 8 ? _snowman.asString() : _snowman.toString();
      } else {
         return "";
      }
   }

   @Override
   public int size() {
      return this.value.size();
   }

   public Tag get(int _snowman) {
      return this.value.get(_snowman);
   }

   @Override
   public Tag set(int _snowman, Tag _snowman) {
      Tag _snowmanxx = this.get(_snowman);
      if (!this.setTag(_snowman, _snowman)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", _snowman.getType(), this.type));
      } else {
         return _snowmanxx;
      }
   }

   @Override
   public void add(int _snowman, Tag _snowman) {
      if (!this.addTag(_snowman, _snowman)) {
         throw new UnsupportedOperationException(String.format("Trying to add tag of type %d to list of %d", _snowman.getType(), this.type));
      }
   }

   @Override
   public boolean setTag(int index, Tag tag) {
      if (this.canAdd(tag)) {
         this.value.set(index, tag);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean addTag(int index, Tag tag) {
      if (this.canAdd(tag)) {
         this.value.add(index, tag);
         return true;
      } else {
         return false;
      }
   }

   private boolean canAdd(Tag tag) {
      if (tag.getType() == 0) {
         return false;
      } else if (this.type == 0) {
         this.type = tag.getType();
         return true;
      } else {
         return this.type == tag.getType();
      }
   }

   public ListTag copy() {
      Iterable<Tag> _snowman = (Iterable<Tag>)(TagReaders.of(this.type).isImmutable() ? this.value : Iterables.transform(this.value, Tag::copy));
      List<Tag> _snowmanx = Lists.newArrayList(_snowman);
      return new ListTag(_snowmanx, this.type);
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o instanceof ListTag && Objects.equals(this.value, ((ListTag)o).value);
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   @Override
   public Text toText(String indent, int depth) {
      if (this.isEmpty()) {
         return new LiteralText("[]");
      } else if (NBT_NUMBER_TYPES.contains(this.type) && this.size() <= 8) {
         String _snowman = ", ";
         MutableText _snowmanx = new LiteralText("[");

         for (int _snowmanxx = 0; _snowmanxx < this.value.size(); _snowmanxx++) {
            if (_snowmanxx != 0) {
               _snowmanx.append(", ");
            }

            _snowmanx.append(this.value.get(_snowmanxx).toText());
         }

         _snowmanx.append("]");
         return _snowmanx;
      } else {
         MutableText _snowman = new LiteralText("[");
         if (!indent.isEmpty()) {
            _snowman.append("\n");
         }

         String _snowmanx = String.valueOf(',');

         for (int _snowmanxx = 0; _snowmanxx < this.value.size(); _snowmanxx++) {
            MutableText _snowmanxxx = new LiteralText(Strings.repeat(indent, depth + 1));
            _snowmanxxx.append(this.value.get(_snowmanxx).toText(indent, depth + 1));
            if (_snowmanxx != this.value.size() - 1) {
               _snowmanxxx.append(_snowmanx).append(indent.isEmpty() ? " " : "\n");
            }

            _snowman.append(_snowmanxxx);
         }

         if (!indent.isEmpty()) {
            _snowman.append("\n").append(Strings.repeat(indent, depth));
         }

         _snowman.append("]");
         return _snowman;
      }
   }

   @Override
   public byte getElementType() {
      return this.type;
   }

   @Override
   public void clear() {
      this.value.clear();
      this.type = 0;
   }
}
