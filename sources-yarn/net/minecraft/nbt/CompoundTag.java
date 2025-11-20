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
   public static final Codec<CompoundTag> CODEC = Codec.PASSTHROUGH.comapFlatMap(dynamic -> {
      Tag lv = (Tag)dynamic.convert(NbtOps.INSTANCE).getValue();
      return lv instanceof CompoundTag ? DataResult.success((CompoundTag)lv) : DataResult.error("Not a compound tag: " + lv);
   }, arg -> new Dynamic(NbtOps.INSTANCE, arg));
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern PATTERN = Pattern.compile("[A-Za-z0-9._+-]+");
   public static final TagReader<CompoundTag> READER = new TagReader<CompoundTag>() {
      public CompoundTag read(DataInput dataInput, int i, PositionTracker arg) throws IOException {
         arg.add(384L);
         if (i > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            Map<String, Tag> map = Maps.newHashMap();

            byte b;
            while ((b = CompoundTag.readByte(dataInput, arg)) != 0) {
               String string = CompoundTag.readString(dataInput, arg);
               arg.add((long)(224 + 16 * string.length()));
               Tag lv = CompoundTag.read(TagReaders.of(b), string, dataInput, i + 1, arg);
               if (map.put(string, lv) != null) {
                  arg.add(288L);
               }
            }

            return new CompoundTag(map);
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
      for (String string : this.tags.keySet()) {
         Tag lv = this.tags.get(string);
         write(string, lv, output);
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
      Tag lv = this.get(key);
      return lv != null && lv.getReader() == IntArrayTag.READER && ((IntArrayTag)lv).getIntArray().length == 4;
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
      Tag lv = this.tags.get(key);
      return lv == null ? 0 : lv.getType();
   }

   public boolean contains(String key) {
      return this.tags.containsKey(key);
   }

   public boolean contains(String key, int type) {
      int j = this.getType(key);
      if (j == type) {
         return true;
      } else {
         return type != 99 ? false : j == 1 || j == 2 || j == 3 || j == 4 || j == 5 || j == 6;
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
            ListTag lv = (ListTag)this.tags.get(key);
            if (!lv.isEmpty() && lv.getElementType() != type) {
               return new ListTag();
            }

            return lv;
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
      StringBuilder stringBuilder = new StringBuilder("{");
      Collection<String> collection = this.tags.keySet();
      if (LOGGER.isDebugEnabled()) {
         List<String> list = Lists.newArrayList(this.tags.keySet());
         Collections.sort(list);
         collection = list;
      }

      for (String string : collection) {
         if (stringBuilder.length() != 1) {
            stringBuilder.append(',');
         }

         stringBuilder.append(escapeTagKey(string)).append(':').append(this.tags.get(string));
      }

      return stringBuilder.append('}').toString();
   }

   public boolean isEmpty() {
      return this.tags.isEmpty();
   }

   private CrashReport createCrashReport(String key, TagReader<?> arg, ClassCastException classCastException) {
      CrashReport lv = CrashReport.create(classCastException, "Reading NBT data");
      CrashReportSection lv2 = lv.addElement("Corrupt NBT tag", 1);
      lv2.add("Tag type found", () -> this.tags.get(key).getReader().getCrashReportName());
      lv2.add("Tag type expected", arg::getCrashReportName);
      lv2.add("Tag name", key);
      return lv;
   }

   public CompoundTag copy() {
      Map<String, Tag> map = Maps.newHashMap(Maps.transformValues(this.tags, Tag::copy));
      return new CompoundTag(map);
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
         CrashReport lv = CrashReport.create(var8, "Loading NBT data");
         CrashReportSection lv2 = lv.addElement("NBT Tag");
         lv2.add("Tag name", key);
         lv2.add("Tag type", reader.getCrashReportName());
         throw new CrashException(lv);
      }
   }

   public CompoundTag copyFrom(CompoundTag source) {
      for (String string : source.tags.keySet()) {
         Tag lv = source.tags.get(string);
         if (lv.getType() == 10) {
            if (this.contains(string, 10)) {
               CompoundTag lv2 = this.getCompound(string);
               lv2.copyFrom((CompoundTag)lv);
            } else {
               this.put(string, lv.copy());
            }
         } else {
            this.put(string, lv.copy());
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
         String string2 = StringTag.escape(key);
         String string3 = string2.substring(0, 1);
         Text lv = new LiteralText(string2.substring(1, string2.length() - 1)).formatted(AQUA);
         return new LiteralText(string3).append(lv).append(string3);
      }
   }

   @Override
   public Text toText(String indent, int depth) {
      if (this.tags.isEmpty()) {
         return new LiteralText("{}");
      } else {
         MutableText lv = new LiteralText("{");
         Collection<String> collection = this.tags.keySet();
         if (LOGGER.isDebugEnabled()) {
            List<String> list = Lists.newArrayList(this.tags.keySet());
            Collections.sort(list);
            collection = list;
         }

         if (!indent.isEmpty()) {
            lv.append("\n");
         }

         Iterator<String> iterator = collection.iterator();

         while (iterator.hasNext()) {
            String string2 = iterator.next();
            MutableText lv2 = new LiteralText(Strings.repeat(indent, depth + 1))
               .append(prettyPrintTagKey(string2))
               .append(String.valueOf(':'))
               .append(" ")
               .append(this.tags.get(string2).toText(indent, depth + 1));
            if (iterator.hasNext()) {
               lv2.append(String.valueOf(',')).append(indent.isEmpty() ? " " : "\n");
            }

            lv.append(lv2);
         }

         if (!indent.isEmpty()) {
            lv.append("\n").append(Strings.repeat(indent, depth));
         }

         lv.append("}");
         return lv;
      }
   }

   protected Map<String, Tag> toMap() {
      return Collections.unmodifiableMap(this.tags);
   }
}
