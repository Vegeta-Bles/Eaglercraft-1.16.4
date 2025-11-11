import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

public class eno implements enm {
   private final eno.a a;
   private enm b;
   private final BufferedInputStream c;

   public eno(eno.a var1, InputStream var2) throws IOException {
      this.a = _snowman;
      this.c = new BufferedInputStream(_snowman);
      this.c.mark(Integer.MAX_VALUE);
      this.b = _snowman.create(new eno.b(this.c));
   }

   @Override
   public AudioFormat a() {
      return this.b.a();
   }

   @Override
   public ByteBuffer a(int var1) throws IOException {
      ByteBuffer _snowman = this.b.a(_snowman);
      if (!_snowman.hasRemaining()) {
         this.b.close();
         this.c.reset();
         this.b = this.a.create(new eno.b(this.c));
         _snowman = this.b.a(_snowman);
      }

      return _snowman;
   }

   @Override
   public void close() throws IOException {
      this.b.close();
      this.c.close();
   }

   @FunctionalInterface
   public interface a {
      enm create(InputStream var1) throws IOException;
   }

   static class b extends FilterInputStream {
      private b(InputStream var1) {
         super(_snowman);
      }

      @Override
      public void close() {
      }
   }
}
