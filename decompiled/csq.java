import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class csq {
   public static final Codec<csq> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               csu.c.fieldOf("input_predicate").forGetter(var0x -> var0x.b),
               csu.c.fieldOf("location_predicate").forGetter(var0x -> var0x.c),
               cso.c.optionalFieldOf("position_predicate", csn.b).forGetter(var0x -> var0x.d),
               ceh.b.fieldOf("output_state").forGetter(var0x -> var0x.e),
               md.a.optionalFieldOf("output_nbt").forGetter(var0x -> Optional.ofNullable(var0x.f))
            )
            .apply(var0, csq::new)
   );
   private final csu b;
   private final csu c;
   private final cso d;
   private final ceh e;
   @Nullable
   private final md f;

   public csq(csu var1, csu var2, ceh var3) {
      this(_snowman, _snowman, csn.b, _snowman, Optional.empty());
   }

   public csq(csu var1, csu var2, cso var3, ceh var4) {
      this(_snowman, _snowman, _snowman, _snowman, Optional.empty());
   }

   public csq(csu var1, csu var2, cso var3, ceh var4, Optional<md> var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman.orElse(null);
   }

   public boolean a(ceh var1, ceh var2, fx var3, fx var4, fx var5, Random var6) {
      return this.b.a(_snowman, _snowman) && this.c.a(_snowman, _snowman) && this.d.a(_snowman, _snowman, _snowman, _snowman);
   }

   public ceh a() {
      return this.e;
   }

   @Nullable
   public md b() {
      return this.f;
   }
}
