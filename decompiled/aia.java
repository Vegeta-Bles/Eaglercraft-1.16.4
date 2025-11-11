import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;

public class aia extends DataFix {
   public aia(Schema var1) {
      super(_snowman, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      return this.fixTypeEverywhereTyped("EntityProjectileOwner", _snowman.getType(akn.p), this::a);
   }

   private Typed<?> a(Typed<?> var1) {
      _snowman = this.a(_snowman, "minecraft:egg", this::d);
      _snowman = this.a(_snowman, "minecraft:ender_pearl", this::d);
      _snowman = this.a(_snowman, "minecraft:experience_bottle", this::d);
      _snowman = this.a(_snowman, "minecraft:snowball", this::d);
      _snowman = this.a(_snowman, "minecraft:potion", this::d);
      _snowman = this.a(_snowman, "minecraft:potion", this::c);
      _snowman = this.a(_snowman, "minecraft:llama_spit", this::b);
      _snowman = this.a(_snowman, "minecraft:arrow", this::a);
      _snowman = this.a(_snowman, "minecraft:spectral_arrow", this::a);
      return this.a(_snowman, "minecraft:trident", this::a);
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      long _snowman = _snowman.get("OwnerUUIDMost").asLong(0L);
      long _snowmanx = _snowman.get("OwnerUUIDLeast").asLong(0L);
      return this.a(_snowman, _snowman, _snowmanx).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
   }

   private Dynamic<?> b(Dynamic<?> var1) {
      OptionalDynamic<?> _snowman = _snowman.get("Owner");
      long _snowmanx = _snowman.get("OwnerUUIDMost").asLong(0L);
      long _snowmanxx = _snowman.get("OwnerUUIDLeast").asLong(0L);
      return this.a(_snowman, _snowmanx, _snowmanxx).remove("Owner");
   }

   private Dynamic<?> c(Dynamic<?> var1) {
      OptionalDynamic<?> _snowman = _snowman.get("Potion");
      return _snowman.set("Item", _snowman.orElseEmptyMap()).remove("Potion");
   }

   private Dynamic<?> d(Dynamic<?> var1) {
      String _snowman = "owner";
      OptionalDynamic<?> _snowmanx = _snowman.get("owner");
      long _snowmanxx = _snowmanx.get("M").asLong(0L);
      long _snowmanxxx = _snowmanx.get("L").asLong(0L);
      return this.a(_snowman, _snowmanxx, _snowmanxxx).remove("owner");
   }

   private Dynamic<?> a(Dynamic<?> var1, long var2, long var4) {
      String _snowman = "OwnerUUID";
      return _snowman != 0L && _snowman != 0L ? _snowman.set("OwnerUUID", _snowman.createIntList(Arrays.stream(a(_snowman, _snowman)))) : _snowman;
   }

   private static int[] a(long var0, long var2) {
      return new int[]{(int)(_snowman >> 32), (int)_snowman, (int)(_snowman >> 32), (int)_snowman};
   }

   private Typed<?> a(Typed<?> var1, String var2, Function<Dynamic<?>, Dynamic<?>> var3) {
      Type<?> _snowman = this.getInputSchema().getChoiceType(akn.p, _snowman);
      Type<?> _snowmanx = this.getOutputSchema().getChoiceType(akn.p, _snowman);
      return _snowman.updateTyped(DSL.namedChoice(_snowman, _snowman), _snowmanx, var1x -> var1x.update(DSL.remainderFinder(), _snowman));
   }
}
