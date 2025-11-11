import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;

public class azp {
   private final fx a;
   private final azr b;
   private int c;
   private final Runnable d;

   public static Codec<azp> a(Runnable var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(
                  fx.a.fieldOf("pos").forGetter(var0x -> var0x.a),
                  gm.aj.fieldOf("type").forGetter(var0x -> var0x.b),
                  Codec.INT.fieldOf("free_tickets").orElse(0).forGetter(var0x -> var0x.c),
                  RecordCodecBuilder.point(_snowman)
               )
               .apply(var1, azp::new)
      );
   }

   private azp(fx var1, azr var2, int var3, Runnable var4) {
      this.a = _snowman.h();
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public azp(fx var1, azr var2, Runnable var3) {
      this(_snowman, _snowman, _snowman.b(), _snowman);
   }

   protected boolean b() {
      if (this.c <= 0) {
         return false;
      } else {
         this.c--;
         this.d.run();
         return true;
      }
   }

   protected boolean c() {
      if (this.c >= this.b.b()) {
         return false;
      } else {
         this.c++;
         this.d.run();
         return true;
      }
   }

   public boolean d() {
      return this.c > 0;
   }

   public boolean e() {
      return this.c != this.b.b();
   }

   public fx f() {
      return this.a;
   }

   public azr g() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else {
         return _snowman != null && this.getClass() == _snowman.getClass() ? Objects.equals(this.a, ((azp)_snowman).a) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }
}
