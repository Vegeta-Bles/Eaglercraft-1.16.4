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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

public class NbtIo {
   public static CompoundTag readCompressed(File file) throws IOException {
      CompoundTag var3;
      try (InputStream inputStream = new FileInputStream(file)) {
         var3 = readCompressed(inputStream);
      }

      return var3;
   }

   public static CompoundTag readCompressed(InputStream stream) throws IOException {
      CompoundTag var3;
      try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream)))) {
         var3 = read(dataInputStream, PositionTracker.DEFAULT);
      }

      return var3;
   }

   public static void writeCompressed(CompoundTag tag, File file) throws IOException {
      try (OutputStream outputStream = new FileOutputStream(file)) {
         writeCompressed(tag, outputStream);
      }
   }

   public static void writeCompressed(CompoundTag tag, OutputStream stream) throws IOException {
      try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(stream)))) {
         write(tag, dataOutputStream);
      }
   }

   @Environment(EnvType.CLIENT)
   public static void write(CompoundTag tag, File file) throws IOException {
      try (
         FileOutputStream fileOutputStream = new FileOutputStream(file);
         DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
      ) {
         write(tag, dataOutputStream);
      }
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static CompoundTag read(File file) throws IOException {
      if (!file.exists()) {
         return null;
      } else {
         CompoundTag var5;
         try (
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
         ) {
            var5 = read(dataInputStream, PositionTracker.DEFAULT);
         }

         return var5;
      }
   }

   public static CompoundTag read(DataInput input) throws IOException {
      return read(input, PositionTracker.DEFAULT);
   }

   public static CompoundTag read(DataInput input, PositionTracker tracker) throws IOException {
      Tag lv = read(input, 0, tracker);
      if (lv instanceof CompoundTag) {
         return (CompoundTag)lv;
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
      byte b = input.readByte();
      if (b == 0) {
         return EndTag.INSTANCE;
      } else {
         input.readUTF();

         try {
            return TagReaders.of(b).read(input, depth, tracker);
         } catch (IOException var7) {
            CrashReport lv = CrashReport.create(var7, "Loading NBT data");
            CrashReportSection lv2 = lv.addElement("NBT Tag");
            lv2.add("Tag type", b);
            throw new CrashException(lv);
         }
      }
   }
}
