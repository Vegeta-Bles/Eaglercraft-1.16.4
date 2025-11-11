import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class coc extends coi {
   public static final Codec<coc> a = RecordCodecBuilder.create(
      var0 -> var0.group(civ.b.fieldOf("feature").forGetter(var0x -> var0x.b), d()).apply(var0, coc::new)
   );
   private final Supplier<civ<?, ?>> b;
   private final md c;

   protected coc(Supplier<civ<?, ?>> var1, cok.a var2) {
      super(_snowman);
      this.b = _snowman;
      this.c = this.b();
   }

   private md b() {
      md _snowman = new md();
      _snowman.a("name", "minecraft:bottom");
      _snowman.a("final_state", "minecraft:air");
      _snowman.a("pool", "minecraft:empty");
      _snowman.a("target", "minecraft:empty");
      _snowman.a("joint", ccz.a.a.a());
      return _snowman;
   }

   public fx a(csw var1, bzm var2) {
      return fx.b;
   }

   @Override
   public List<ctb.c> a(csw var1, fx var2, bzm var3, Random var4) {
      List<ctb.c> _snowman = Lists.newArrayList();
      _snowman.add(new ctb.c(_snowman, bup.mZ.n().a(bxr.a, ge.a(gc.a, gc.d)), this.c));
      return _snowman;
   }

   @Override
   public cra a(csw var1, fx var2, bzm var3) {
      fx _snowman = this.a(_snowman, _snowman);
      return new cra(_snowman.u(), _snowman.v(), _snowman.w(), _snowman.u() + _snowman.u(), _snowman.v() + _snowman.v(), _snowman.w() + _snowman.w());
   }

   @Override
   public boolean a(csw var1, bsr var2, bsn var3, cfy var4, fx var5, fx var6, bzm var7, cra var8, Random var9, boolean var10) {
      return this.b.get().a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public coj<?> a() {
      return coj.c;
   }

   @Override
   public String toString() {
      return "Feature[" + gm.aE.b(this.b.get().b()) + "]";
   }
}
