package net.minecraft.server.rcon;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataStreamHelper {
   private final ByteArrayOutputStream byteArrayOutputStream;
   private final DataOutputStream dataOutputStream;

   public DataStreamHelper(int _snowman) {
      this.byteArrayOutputStream = new ByteArrayOutputStream(_snowman);
      this.dataOutputStream = new DataOutputStream(this.byteArrayOutputStream);
   }

   public void write(byte[] _snowman) throws IOException {
      this.dataOutputStream.write(_snowman, 0, _snowman.length);
   }

   public void writeBytes(String _snowman) throws IOException {
      this.dataOutputStream.writeBytes(_snowman);
      this.dataOutputStream.write(0);
   }

   public void write(int _snowman) throws IOException {
      this.dataOutputStream.write(_snowman);
   }

   public void writeShort(short _snowman) throws IOException {
      this.dataOutputStream.writeShort(Short.reverseBytes(_snowman));
   }

   public byte[] bytes() {
      return this.byteArrayOutputStream.toByteArray();
   }

   public void reset() {
      this.byteArrayOutputStream.reset();
   }
}
