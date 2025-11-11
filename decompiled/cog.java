import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class cog extends coi {
   public static final Codec<cog> a = RecordCodecBuilder.create(
      var0 -> var0.group(coi.e.listOf().fieldOf("elements").forGetter(var0x -> var0x.b), d()).apply(var0, cog::new)
   );
   private final List<coi> b;

   public cog(List<coi> var1, cok.a var2) {
      super(_snowman);
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("Elements are empty");
      } else {
         this.b = _snowman;
         this.b(_snowman);
      }
   }

   @Override
   public List<ctb.c> a(csw var1, fx var2, bzm var3, Random var4) {
      return this.b.get(0).a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public cra a(csw var1, fx var2, bzm var3) {
      cra _snowman = cra.a();

      for (coi _snowmanx : this.b) {
         cra _snowmanxx = _snowmanx.a(_snowman, _snowman, _snowman);
         _snowman.c(_snowmanxx);
      }

      return _snowman;
   }

   @Override
   public boolean a(csw var1, bsr var2, bsn var3, cfy var4, fx var5, fx var6, bzm var7, cra var8, Random var9, boolean var10) {
      for (coi _snowman : this.b) {
         if (!_snowman.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public coj<?> a() {
      return coj.b;
   }

   @Override
   public coi a(cok.a var1) {
      super.a(_snowman);
      this.b(_snowman);
      return this;
   }

   @Override
   public String toString() {
      return "List[" + this.b.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
   }

   private void b(cok.a var1) {
      this.b.forEach(var1x -> var1x.a(_snowman));
   }
}
