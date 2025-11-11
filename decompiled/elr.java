import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;

public class elr {
   private final vk a;
   private final vk b;
   @Nullable
   private eao c;

   public elr(vk var1, vk var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public vk a() {
      return this.a;
   }

   public vk b() {
      return this.b;
   }

   public ekc c() {
      return djz.C().a(this.a()).apply(this.b());
   }

   public eao a(Function<vk, eao> var1) {
      if (this.c == null) {
         this.c = _snowman.apply(this.a);
      }

      return this.c;
   }

   public dfq a(eag var1, Function<vk, eao> var2) {
      return this.c().a(_snowman.getBuffer(this.a(_snowman)));
   }

   public dfq a(eag var1, Function<vk, eao> var2, boolean var3) {
      return this.c().a(efo.c(_snowman, this.a(_snowman), true, _snowman));
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         elr _snowman = (elr)_snowman;
         return this.a.equals(_snowman.a) && this.b.equals(_snowman.b);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b);
   }

   @Override
   public String toString() {
      return "Material{atlasLocation=" + this.a + ", texture=" + this.b + '}';
   }
}
