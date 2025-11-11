import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class aim extends agd {
   private static final Set<String> c = Sets.newHashSet();
   private static final Set<String> d = Sets.newHashSet();
   private static final Set<String> e = Sets.newHashSet();
   private static final Set<String> f = Sets.newHashSet();
   private static final Set<String> g = Sets.newHashSet();
   private static final Set<String> h = Sets.newHashSet();

   public aim(Schema var1) {
      super(_snowman, akn.p);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.b), var1 -> {
         var1 = var1.update(DSL.remainderFinder(), aim::c);

         for (String _snowman : c) {
            var1 = this.a(var1, _snowman, aim::l);
         }

         for (String _snowman : d) {
            var1 = this.a(var1, _snowman, aim::l);
         }

         for (String _snowman : e) {
            var1 = this.a(var1, _snowman, aim::m);
         }

         for (String _snowman : f) {
            var1 = this.a(var1, _snowman, aim::n);
         }

         for (String _snowman : g) {
            var1 = this.a(var1, _snowman, aim::b);
         }

         for (String _snowman : h) {
            var1 = this.a(var1, _snowman, aim::o);
         }

         var1 = this.a(var1, "minecraft:bee", aim::k);
         var1 = this.a(var1, "minecraft:zombified_piglin", aim::k);
         var1 = this.a(var1, "minecraft:fox", aim::j);
         var1 = this.a(var1, "minecraft:item", aim::i);
         var1 = this.a(var1, "minecraft:shulker_bullet", aim::h);
         var1 = this.a(var1, "minecraft:area_effect_cloud", aim::g);
         var1 = this.a(var1, "minecraft:zombie_villager", aim::f);
         var1 = this.a(var1, "minecraft:evoker_fangs", aim::e);
         return this.a(var1, "minecraft:piglin", aim::d);
      });
   }

   private static Dynamic<?> d(Dynamic<?> var0) {
      return _snowman.update(
         "Brain", var0x -> var0x.update("memories", var0xx -> var0xx.update("minecraft:angry_at", var0xxx -> a(var0xxx, "value", "value").orElseGet(() -> {
                     a.warn("angry_at has no value.");
                     return var0xxx;
                  })))
      );
   }

   private static Dynamic<?> e(Dynamic<?> var0) {
      return c(_snowman, "OwnerUUID", "Owner").orElse(_snowman);
   }

   private static Dynamic<?> f(Dynamic<?> var0) {
      return c(_snowman, "ConversionPlayer", "ConversionPlayer").orElse(_snowman);
   }

   private static Dynamic<?> g(Dynamic<?> var0) {
      return c(_snowman, "OwnerUUID", "Owner").orElse(_snowman);
   }

   private static Dynamic<?> h(Dynamic<?> var0) {
      _snowman = b(_snowman, "Owner", "Owner").orElse(_snowman);
      return b(_snowman, "Target", "Target").orElse(_snowman);
   }

   private static Dynamic<?> i(Dynamic<?> var0) {
      _snowman = b(_snowman, "Owner", "Owner").orElse(_snowman);
      return b(_snowman, "Thrower", "Thrower").orElse(_snowman);
   }

   private static Dynamic<?> j(Dynamic<?> var0) {
      Optional<Dynamic<?>> _snowman = _snowman.get("TrustedUUIDs")
         .result()
         .map(var1x -> _snowman.createList(var1x.asStream().map(var0x -> (Dynamic)a((Dynamic<?>)var0x).orElseGet(() -> {
                  a.warn("Trusted contained invalid data.");
                  return (Dynamic<?>)var0x;
               }))));
      return (Dynamic<?>)DataFixUtils.orElse(_snowman.map(var1x -> _snowman.remove("TrustedUUIDs").set("Trusted", var1x)), _snowman);
   }

   private static Dynamic<?> k(Dynamic<?> var0) {
      return a(_snowman, "HurtBy", "HurtBy").orElse(_snowman);
   }

   private static Dynamic<?> l(Dynamic<?> var0) {
      Dynamic<?> _snowman = m(_snowman);
      return a(_snowman, "OwnerUUID", "Owner").orElse(_snowman);
   }

   private static Dynamic<?> m(Dynamic<?> var0) {
      Dynamic<?> _snowman = n(_snowman);
      return c(_snowman, "LoveCause", "LoveCause").orElse(_snowman);
   }

   private static Dynamic<?> n(Dynamic<?> var0) {
      return b(_snowman).update("Leash", var0x -> c(var0x, "UUID", "UUID").orElse(var0x));
   }

   public static Dynamic<?> b(Dynamic<?> var0) {
      return _snowman.update(
         "Attributes",
         var1 -> _snowman.createList(
               var1.asStream()
                  .map(
                     var0x -> var0x.update(
                           "Modifiers",
                           var1x -> var0x.createList(var1x.asStream().map(var0xx -> (Dynamic)c((Dynamic<?>)var0xx, "UUID", "UUID").orElse((Dynamic<?>)var0xx)))
                        )
                  )
            )
      );
   }

   private static Dynamic<?> o(Dynamic<?> var0) {
      return (Dynamic<?>)DataFixUtils.orElse(_snowman.get("OwnerUUID").result().map(var1 -> _snowman.remove("OwnerUUID").set("Owner", var1)), _snowman);
   }

   public static Dynamic<?> c(Dynamic<?> var0) {
      return c(_snowman, "UUID", "UUID").orElse(_snowman);
   }

   static {
      c.add("minecraft:donkey");
      c.add("minecraft:horse");
      c.add("minecraft:llama");
      c.add("minecraft:mule");
      c.add("minecraft:skeleton_horse");
      c.add("minecraft:trader_llama");
      c.add("minecraft:zombie_horse");
      d.add("minecraft:cat");
      d.add("minecraft:parrot");
      d.add("minecraft:wolf");
      e.add("minecraft:bee");
      e.add("minecraft:chicken");
      e.add("minecraft:cow");
      e.add("minecraft:fox");
      e.add("minecraft:mooshroom");
      e.add("minecraft:ocelot");
      e.add("minecraft:panda");
      e.add("minecraft:pig");
      e.add("minecraft:polar_bear");
      e.add("minecraft:rabbit");
      e.add("minecraft:sheep");
      e.add("minecraft:turtle");
      e.add("minecraft:hoglin");
      f.add("minecraft:bat");
      f.add("minecraft:blaze");
      f.add("minecraft:cave_spider");
      f.add("minecraft:cod");
      f.add("minecraft:creeper");
      f.add("minecraft:dolphin");
      f.add("minecraft:drowned");
      f.add("minecraft:elder_guardian");
      f.add("minecraft:ender_dragon");
      f.add("minecraft:enderman");
      f.add("minecraft:endermite");
      f.add("minecraft:evoker");
      f.add("minecraft:ghast");
      f.add("minecraft:giant");
      f.add("minecraft:guardian");
      f.add("minecraft:husk");
      f.add("minecraft:illusioner");
      f.add("minecraft:magma_cube");
      f.add("minecraft:pufferfish");
      f.add("minecraft:zombified_piglin");
      f.add("minecraft:salmon");
      f.add("minecraft:shulker");
      f.add("minecraft:silverfish");
      f.add("minecraft:skeleton");
      f.add("minecraft:slime");
      f.add("minecraft:snow_golem");
      f.add("minecraft:spider");
      f.add("minecraft:squid");
      f.add("minecraft:stray");
      f.add("minecraft:tropical_fish");
      f.add("minecraft:vex");
      f.add("minecraft:villager");
      f.add("minecraft:iron_golem");
      f.add("minecraft:vindicator");
      f.add("minecraft:pillager");
      f.add("minecraft:wandering_trader");
      f.add("minecraft:witch");
      f.add("minecraft:wither");
      f.add("minecraft:wither_skeleton");
      f.add("minecraft:zombie");
      f.add("minecraft:zombie_villager");
      f.add("minecraft:phantom");
      f.add("minecraft:ravager");
      f.add("minecraft:piglin");
      g.add("minecraft:armor_stand");
      h.add("minecraft:arrow");
      h.add("minecraft:dragon_fireball");
      h.add("minecraft:firework_rocket");
      h.add("minecraft:fireball");
      h.add("minecraft:llama_spit");
      h.add("minecraft:small_fireball");
      h.add("minecraft:snowball");
      h.add("minecraft:spectral_arrow");
      h.add("minecraft:egg");
      h.add("minecraft:ender_pearl");
      h.add("minecraft:experience_bottle");
      h.add("minecraft:potion");
      h.add("minecraft:trident");
      h.add("minecraft:wither_skull");
   }
}
