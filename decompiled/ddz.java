import java.nio.ByteBuffer;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.openal.AL10;

public class ddz {
   @Nullable
   private ByteBuffer a;
   private final AudioFormat b;
   private boolean c;
   private int d;

   public ddz(ByteBuffer var1, AudioFormat var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   OptionalInt a() {
      if (!this.c) {
         if (this.a == null) {
            return OptionalInt.empty();
         }

         int _snowman = ddy.a(this.b);
         int[] _snowmanx = new int[1];
         AL10.alGenBuffers(_snowmanx);
         if (ddy.a("Creating buffer")) {
            return OptionalInt.empty();
         }

         AL10.alBufferData(_snowmanx[0], _snowman, this.a, (int)this.b.getSampleRate());
         if (ddy.a("Assigning buffer data")) {
            return OptionalInt.empty();
         }

         this.d = _snowmanx[0];
         this.c = true;
         this.a = null;
      }

      return OptionalInt.of(this.d);
   }

   public void b() {
      if (this.c) {
         AL10.alDeleteBuffers(new int[]{this.d});
         if (ddy.a("Deleting stream buffers")) {
            return;
         }
      }

      this.c = false;
   }

   public OptionalInt c() {
      OptionalInt _snowman = this.a();
      this.c = false;
      return _snowman;
   }
}
