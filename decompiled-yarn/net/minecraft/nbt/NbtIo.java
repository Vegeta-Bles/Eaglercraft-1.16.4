package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

public class NbtIo {
   public static CompoundTag readCompressed(File file) throws IOException {
      CompoundTag var3;
      try (InputStream _snowman = new FileInputStream(file)) {
         var3 = readCompressed(_snowman);
      }

      return var3;
   }

   public static CompoundTag readCompressed(InputStream stream) throws IOException {
      CompoundTag var3;
      try (DataInputStream _snowman = new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream)))) {
         var3 = read(_snowman, PositionTracker.DEFAULT);
      }

      return var3;
   }

   public static void writeCompressed(CompoundTag tag, File file) throws IOException {
      try (OutputStream _snowman = new FileOutputStream(file)) {
         writeCompressed(tag, _snowman);
      }
   }

   public static void writeCompressed(CompoundTag tag, OutputStream stream) throws IOException {
      try (DataOutputStream _snowman = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(stream)))) {
         write(tag, _snowman);
      }
   }

   public static void write(CompoundTag tag, File file) throws IOException {
      try (
         FileOutputStream _snowman = new FileOutputStream(file);
         DataOutputStream _snowmanx = new DataOutputStream(_snowman);
      ) {
         write(tag, _snowmanx);
      }
   }

   @Nullable
   public static CompoundTag read(File file) throws IOException {
      if (!file.exists()) {
         return null;
      } else {
         CompoundTag var5;
         try (
            FileInputStream _snowman = new FileInputStream(file);
            DataInputStream _snowmanx = new DataInputStream(_snowman);
         ) {
            var5 = read(_snowmanx, PositionTracker.DEFAULT);
         }

         return var5;
      }
   }

   public static CompoundTag read(DataInput input) throws IOException {
      return read(input, PositionTracker.DEFAULT);
   }

   public static CompoundTag read(DataInput input, PositionTracker tracker) throws IOException {
      Tag _snowman = read(input, 0, tracker);
      if (_snowman instanceof CompoundTag) {
         return (CompoundTag)_snowman;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(CompoundTag tag, DataOutput output) throws IOException {
      write((Tag)tag, output);
   }

   private static void write(Tag tag, DataOutput output) throws IOException {
      output.writeByte(tag.getType());
      if (tag.getType() != 0) {
         output.writeUTF("");
         tag.write(output);
      }
   }

   private static Tag read(DataInput input, int depth, PositionTracker tracker) throws IOException {
      byte _snowman = input.readByte();
      if (_snowman == 0) {
         return EndTag.INSTANCE;
      } else {
         input.readUTF();

         try {
            return TagReaders.of(_snowman).read(input, depth, tracker);
         } catch (IOException var7) {
            CrashReport _snowmanx = CrashReport.create(var7, "Loading NBT data");
            CrashReportSection _snowmanxx = _snowmanx.addElement("NBT Tag");
            _snowmanxx.add("Tag type", _snowman);
            throw new CrashException(_snowmanx);
         }
      }
   }
}
