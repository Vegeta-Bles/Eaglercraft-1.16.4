import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;

public final class gf {
   public static final Codec<gf> a = RecordCodecBuilder.create(
      var0 -> var0.group(brx.f.fieldOf("dimension").forGetter(gf::a), fx.a.fieldOf("pos").forGetter(gf::b)).apply(var0, gf::a)
   );
   private final vj<brx> b;
   private final fx c;

   private gf(vj<brx> var1, fx var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public static gf a(vj<brx> var0, fx var1) {
      return new gf(_snowman, _snowman);
   }

   public vj<brx> a() {
      return this.b;
   }

   public fx b() {
      return this.c;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         gf _snowman = (gf)_snowman;
         return Objects.equals(this.b, _snowman.b) && Objects.equals(this.c, _snowman.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.b, this.c);
   }

   @Override
   public String toString() {
      return this.b.toString() + " " + this.c;
   }
}
