package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompoundTag implements Tag {
   public static final Codec<CompoundTag> CODEC = Codec.PASSTHROUGH.comapFlatMap(_snowman -> {
      Tag _snowmanx = (Tag)_snowman.convert(NbtOps.INSTANCE).getValue();
      return _snowmanx instanceof CompoundTag ? DataResult.success((CompoundTag)_snowmanx) : DataResult.error("Not a compound tag: " + _snowmanx);
   }, _snowman -> new Dynamic(NbtOps.INSTANCE, _snowman));
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9._+-]+");
   public static final TagReader<CompoundTag> READER = new TagReader<CompoundTag>() {
      public CompoundTag read(DataInput _snowman, int _snowman, PositionTracker _snowman) throws IOException {
         _snowman.add(384L);
         if (_snowman > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            Map<String, Tag> _snowmanxxx = Maps.newHashMap();

            byte _snowmanxxxx;
            while ((_snowmanxxxx = CompoundTag.readByte(_snowman, _snowman)) != 0) {
               String _snowmanxxxxx = CompoundTag.readString(_snowman, _snowman);
               _snowman.add((long)(224 + 16 * _snowmanxxxxx.length()));
               Tag _snowmanxxxxxx = CompoundTag.read(TagReaders.of(_snowmanxxxx), _snowmanxxxxx, _snowman, _snowman + 1, _snowman);
               if (_snowmanxxx.put(_snowmanxxxxx, _snowmanxxxxxx) != null) {
                  _snowman.add(288L);
               }
            }

            return new CompoundTag(_snowmanxxx);
         }
      }

      @Override
      public String getCrashReportName() {
         return "COMPOUND";
      }

      @Override
      public String getCommandFeedbackName() {
         return "TAG_Compound";
      }
   };
   private final Map<String, Tag> tags;

   protected CompoundTag(Map<String, Tag> tags) {
      this.tags = tags;
   }

   public CompoundTag() {
      this(Maps.newHashMap());
   }

   @Override
   public void write(DataOutput output) throws IOException {
      for (String _snowman : this.tags.keySet()) {
         Tag _snowmanx = this.tags.get(_snowman);
         write(_snowman, _snowmanx, output);
      }

      output.writeByte(0);
   }

   public Set<String> getKeys() {
      return this.tags.keySet();
   }

   @Override
   public byte getType() {
      return 10;
   }

   @Override
   public TagReader<CompoundTag> getReader() {
      return READER;
   }

   public int getSize() {
      return this.tags.size();
   }

   @Nullable
   public Tag put(String key, Tag tag) {
      return this.tags.put(key, tag);
   }

   public void putByte(String key, byte value) {
      this.tags.put(key, ByteTag.of(value));
   }

   public void putShort(String key, short value) {
      this.tags.put(key, ShortTag.of(value));
   }

   public void putInt(String key, int value) {
      this.tags.put(key, IntTag.of(value));
   }

   public void putLong(String key, long value) {
      this.tags.put(key, LongTag.of(value));
   }

   public void putUuid(String key, UUID value) {
      this.tags.put(key, NbtHelper.fromUuid(value));
   }

   public UUID getUuid(String key) {
      return NbtHelper.toUuid(this.get(key));
   }

   public boolean containsUuid(String key) {
      Tag _snowman = this.get(key);
      return _snowman != null && _snowman.getReader() == IntArrayTag.READER && ((IntArrayTag)_snowman).getIntArray().length == 4;
   }

   public void putFloat(String key, float value) {
      this.tags.put(key, FloatTag.of(value));
   }

   public void putDouble(String key, double value) {
      this.tags.put(key, DoubleTag.of(value));
   }

   public void putString(String key, String value) {
      this.tags.put(key, StringTag.of(value));
   }

   public void putByteArray(String key, byte[] value) {
      this.tags.put(key, new ByteArrayTag(value));
   }

   public void putIntArray(String key, int[] value) {
      this.tags.put(key, new IntArrayTag(value));
   }

   public void putIntArray(String key, List<Integer> value) {
      this.tags.put(key, new IntArrayTag(value));
   }

   public void putLongArray(String key, long[] value) {
      this.tags.put(key, new LongArrayTag(value));
   }

   public void putLongArray(String key, List<Long> value) {
      this.tags.put(key, new LongArrayTag(value));
   }

   public void putBoolean(String key, boolean value) {
      this.tags.put(key, ByteTag.of(value));
   }

   @Nullable
   public Tag get(String key) {
      return this.tags.get(key);
   }

   public byte getType(String key) {
      Tag _snowman = this.tags.get(key);
      return _snowman == null ? 0 : _snowman.getType();
   }

   public boolean contains(String key) {
      return this.tags.containsKey(key);
   }

   public boolean contains(String key, int type) {
      int _snowman = this.getType(key);
      if (_snowman == type) {
         return true;
      } else {
         return type != 99 ? false : _snowman == 1 || _snowman == 2 || _snowman == 3 || _snowman == 4 || _snowman == 5 || _snowman == 6;
      }
   }

   public byte getByte(String key) {
      try {
         if (this.contains(key, 99)) {
            return ((AbstractNumberTag)this.tags.get(key)).getByte();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public short getShort(String key) {
      try {
         if (this.contains(key, 99)) {
            return ((AbstractNumberTag)this.tags.get(key)).getShort();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public int getInt(String key) {
      try {
         if (this.contains(key, 99)) {
            return ((AbstractNumberTag)this.tags.get(key)).getInt();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public long getLong(String key) {
      try {
         if (this.contains(key, 99)) {
            return ((AbstractNumberTag)this.tags.get(key)).getLong();
         }
      } catch (ClassCastException var3) {
      }

      return 0L;
   }

   public float getFloat(String key) {
      try {
         if (this.contains(key, 99)) {
            return ((AbstractNumberTag)this.tags.get(key)).getFloat();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0F;
   }

   public double getDouble(String key) {
      try {
         if (this.contains(key, 99)) {
            return ((AbstractNumberTag)this.tags.get(key)).getDouble();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0;
   }

   public String getString(String key) {
      try {
         if (this.contains(key, 8)) {
            return this.tags.get(key).asString();
         }
      } catch (ClassCastException var3) {
      }

      return "";
   }

   public byte[] getByteArray(String key) {
      try {
         if (this.contains(key, 7)) {
            return ((ByteArrayTag)this.tags.get(key)).getByteArray();
         }
      } catch (ClassCastException var3) {
         throw new CrashException(this.createCrashReport(key, ByteArrayTag.READER, var3));
      }

      return new byte[0];
   }

   public int[] getIntArray(String key) {
      try {
         if (this.contains(key, 11)) {
            return ((IntArrayTag)this.tags.get(key)).getIntArray();
         }
      } catch (ClassCastException var3) {
         throw new CrashException(this.createCrashReport(key, IntArrayTag.READER, var3));
      }

      return new int[0];
   }

   public long[] getLongArray(String key) {
      try {
         if (this.contains(key, 12)) {
            return ((LongArrayTag)this.tags.get(key)).getLongArray();
         }
      } catch (ClassCastException var3) {
         throw new CrashException(this.createCrashReport(key, LongArrayTag.READER, var3));
      }

      return new long[0];
   }

   public CompoundTag getCompound(String key) {
      try {
         if (this.contains(key, 10)) {
            return (CompoundTag)this.tags.get(key);
         }
      } catch (ClassCastException var3) {
         throw new CrashException(this.createCrashReport(key, READER, var3));
      }

      return new CompoundTag();
   }

   public ListTag getList(String key, int type) {
      try {
         if (this.getType(key) == 9) {
            ListTag _snowman = (ListTag)this.tags.get(key);
            if (!_snowman.isEmpty() && _snowman.getElementType() != type) {
               return new ListTag();
            }

            return _snowman;
         }
      } catch (ClassCastException var4) {
         throw new CrashException(this.createCrashReport(key, ListTag.READER, var4));
      }

      return new ListTag();
   }

   public boolean getBoolean(String key) {
      return this.getByte(key) != 0;
   }

   public void remove(String key) {
      this.tags.remove(key);
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("{");
      Collection<String> _snowmanx = this.tags.keySet();
      if (LOGGER.isDebugEnabled()) {
         List<String> _snowmanxx = Lists.newArrayList(this.tags.keySet());
         Collections.sort(_snowmanxx);
         _snowmanx = _snowmanxx;
      }

      for (String _snowmanxx : _snowmanx) {
         if (_snowman.length() != 1) {
            _snowman.append(',');
         }

         _snowman.append(escapeTagKey(_snowmanxx)).append(':').append(this.tags.get(_snowmanxx));
      }

      return _snowman.append('}').toString();
   }

   public boolean isEmpty() {
      return this.tags.isEmpty();
   }

   private CrashReport createCrashReport(String key, TagReader<?> _snowman, ClassCastException _snowman) {
      CrashReport _snowmanxx = CrashReport.create(_snowman, "Reading NBT data");
      CrashReportSection _snowmanxxx = _snowmanxx.addElement("Corrupt NBT tag", 1);
      _snowmanxxx.add("Tag type found", () -> this.tags.get(key).getReader().getCrashReportName());
      _snowmanxxx.add("Tag type expected", _snowman::getCrashReportName);
      _snowmanxxx.add("Tag name", key);
      return _snowmanxx;
   }

   public CompoundTag copy() {
      Map<String, Tag> _snowman = Maps.newHashMap(Maps.transformValues(this.tags, Tag::copy));
      return new CompoundTag(_snowman);
   }

   @Override
   public boolean equals(Object o) {
      return this == o ? true : o instanceof CompoundTag && Objects.equals(this.tags, ((CompoundTag)o).tags);
   }

   @Override
   public int hashCode() {
      return this.tags.hashCode();
   }

   private static void write(String key, Tag tag, DataOutput output) throws IOException {
      output.writeByte(tag.getType());
      if (tag.getType() != 0) {
         output.writeUTF(key);
         tag.write(output);
      }
   }

   private static byte readByte(DataInput input, PositionTracker tracker) throws IOException {
      return input.readByte();
   }

   private static String readString(DataInput input, PositionTracker tracker) throws IOException {
      return input.readUTF();
   }

   private static Tag read(TagReader<?> reader, String key, DataInput input, int depth, PositionTracker tracker) {
      try {
         return reader.read(input, depth, tracker);
      } catch (IOException var8) {
         CrashReport _snowman = CrashReport.create(var8, "Loading NBT data");
         CrashReportSection _snowmanx = _snowman.addElement("NBT Tag");
         _snowmanx.add("Tag name", key);
         _snowmanx.add("Tag type", reader.getCrashReportName());
         throw new CrashException(_snowman);
      }
   }

   public CompoundTag copyFrom(CompoundTag source) {
      for (String _snowman : source.tags.keySet()) {
         Tag _snowmanx = source.tags.get(_snowman);
         if (_snowmanx.getType() == 10) {
            if (this.contains(_snowman, 10)) {
               CompoundTag _snowmanxx = this.getCompound(_snowman);
               _snowmanxx.copyFrom((CompoundTag)_snowmanx);
            } else {
               this.put(_snowman, _snowmanx.copy());
            }
         } else {
            this.put(_snowman, _snowmanx.copy());
         }
      }

      return this;
   }

   protected static String escapeTagKey(String key) {
      return PATTERN.matcher(key).matches() ? key : StringTag.escape(key);
   }

   protected static Text prettyPrintTagKey(String key) {
      if (PATTERN.matcher(key).matches()) {
         return new LiteralText(key).formatted(AQUA);
      } else {
         String _snowman = StringTag.escape(key);
         String _snowmanx = _snowman.substring(0, 1);
         Text _snowmanxx = new LiteralText(_snowman.substring(1, _snowman.length() - 1)).formatted(AQUA);
         return new LiteralText(_snowmanx).append(_snowmanxx).append(_snowmanx);
      }
   }

   @Override
   public Text toText(String indent, int depth) {
      if (this.tags.isEmpty()) {
         return new LiteralText("{}");
      } else {
         MutableText _snowman = new LiteralText("{");
         Collection<String> _snowmanx = this.tags.keySet();
         if (LOGGER.isDebugEnabled()) {
            List<String> _snowmanxx = Lists.newArrayList(this.tags.keySet());
            Collections.sort(_snowmanxx);
            _snowmanx = _snowmanxx;
         }

         if (!indent.isEmpty()) {
            _snowman.append("\n");
         }

         Iterator<String> _snowmanxx = _snowmanx.iterator();

         while (_snowmanxx.hasNext()) {
            String _snowmanxxx = _snowmanxx.next();
            MutableText _snowmanxxxx = new LiteralText(Strings.repeat(indent, depth + 1))
               .append(prettyPrintTagKey(_snowmanxxx))
               .append(String.valueOf(':'))
               .append(" ")
               .append(this.tags.get(_snowmanxxx).toText(indent, depth + 1));
            if (_snowmanxx.hasNext()) {
               _snowmanxxxx.append(String.valueOf(',')).append(indent.isEmpty() ? " " : "\n");
            }

            _snowman.append(_snowmanxxxx);
         }

         if (!indent.isEmpty()) {
            _snowman.append("\n").append(Strings.repeat(indent, depth));
         }

         _snowman.append("}");
         return _snowman;
      }
   }

   protected Map<String, Tag> toMap() {
      return Collections.unmodifiableMap(this.tags);
   }
}
