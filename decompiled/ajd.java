import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class ajd extends DataFix {
   private static final String[] a = (String[])DataFixUtils.make(new String[256], var0 -> {
      var0[1] = "Item";
      var0[2] = "XPOrb";
      var0[7] = "ThrownEgg";
      var0[8] = "LeashKnot";
      var0[9] = "Painting";
      var0[10] = "Arrow";
      var0[11] = "Snowball";
      var0[12] = "Fireball";
      var0[13] = "SmallFireball";
      var0[14] = "ThrownEnderpearl";
      var0[15] = "EyeOfEnderSignal";
      var0[16] = "ThrownPotion";
      var0[17] = "ThrownExpBottle";
      var0[18] = "ItemFrame";
      var0[19] = "WitherSkull";
      var0[20] = "PrimedTnt";
      var0[21] = "FallingSand";
      var0[22] = "FireworksRocketEntity";
      var0[23] = "TippedArrow";
      var0[24] = "SpectralArrow";
      var0[25] = "ShulkerBullet";
      var0[26] = "DragonFireball";
      var0[30] = "ArmorStand";
      var0[41] = "Boat";
      var0[42] = "MinecartRideable";
      var0[43] = "MinecartChest";
      var0[44] = "MinecartFurnace";
      var0[45] = "MinecartTNT";
      var0[46] = "MinecartHopper";
      var0[47] = "MinecartSpawner";
      var0[40] = "MinecartCommandBlock";
      var0[48] = "Mob";
      var0[49] = "Monster";
      var0[50] = "Creeper";
      var0[51] = "Skeleton";
      var0[52] = "Spider";
      var0[53] = "Giant";
      var0[54] = "Zombie";
      var0[55] = "Slime";
      var0[56] = "Ghast";
      var0[57] = "PigZombie";
      var0[58] = "Enderman";
      var0[59] = "CaveSpider";
      var0[60] = "Silverfish";
      var0[61] = "Blaze";
      var0[62] = "LavaSlime";
      var0[63] = "EnderDragon";
      var0[64] = "WitherBoss";
      var0[65] = "Bat";
      var0[66] = "Witch";
      var0[67] = "Endermite";
      var0[68] = "Guardian";
      var0[69] = "Shulker";
      var0[90] = "Pig";
      var0[91] = "Sheep";
      var0[92] = "Cow";
      var0[93] = "Chicken";
      var0[94] = "Squid";
      var0[95] = "Wolf";
      var0[96] = "MushroomCow";
      var0[97] = "SnowMan";
      var0[98] = "Ozelot";
      var0[99] = "VillagerGolem";
      var0[100] = "EntityHorse";
      var0[101] = "Rabbit";
      var0[120] = "Villager";
      var0[200] = "EnderCrystal";
   });

   public ajd(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      Type<?> _snowmanx = _snowman.getType(akn.l);
      OpticFinder<Pair<String, String>> _snowmanxx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      OpticFinder<String> _snowmanxxx = DSL.fieldFinder("id", DSL.string());
      OpticFinder<?> _snowmanxxxx = _snowmanx.findField("tag");
      OpticFinder<?> _snowmanxxxxx = _snowmanxxxx.type().findField("EntityTag");
      OpticFinder<?> _snowmanxxxxxx = DSL.typeFinder(_snowman.getTypeRaw(akn.p));
      Type<?> _snowmanxxxxxxx = this.getOutputSchema().getTypeRaw(akn.p);
      return this.fixTypeEverywhereTyped(
         "ItemSpawnEggFix",
         _snowmanx,
         var6x -> {
            Optional<Pair<String, String>> _snowmanxxxxxxxx = var6x.getOptional(_snowman);
            if (_snowmanxxxxxxxx.isPresent() && Objects.equals(_snowmanxxxxxxxx.get().getSecond(), "minecraft:spawn_egg")) {
               Dynamic<?> _snowmanx = (Dynamic<?>)var6x.get(DSL.remainderFinder());
               short _snowmanxx = _snowmanx.get("Damage").asShort((short)0);
               Optional<? extends Typed<?>> _snowmanxxx = var6x.getOptionalTyped(_snowman);
               Optional<? extends Typed<?>> _snowmanxxxx = _snowmanxxx.flatMap(var1x -> var1x.getOptionalTyped(_snowman));
               Optional<? extends Typed<?>> _snowmanxxxxx = _snowmanxxxx.flatMap(var1x -> var1x.getOptionalTyped(_snowman));
               Optional<String> _snowmanxxxxxx = _snowmanxxxxx.flatMap(var1x -> var1x.getOptional(_snowman));
               Typed<?> _snowmanxxxxxxx = var6x;
               String _snowmanxxxxxxxx = a[_snowmanxx & 255];
               if (_snowmanxxxxxxxx != null && (!_snowmanxxxxxx.isPresent() || !Objects.equals(_snowmanxxxxxx.get(), _snowmanxxxxxxxx))) {
                  Typed<?> _snowmanxxxxxxxxx = var6x.getOrCreateTyped(_snowman);
                  Typed<?> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getOrCreateTyped(_snowman);
                  Typed<?> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getOrCreateTyped(_snowman);
                  Typed<?> _snowmanxxxxxxxxxxxx = (Typed<?>)((Pair)_snowmanxxxxxxxxxxx.write()
                        .flatMap(var3x -> _snowman.readTyped(var3x.set("id", _snowman.createString(_snowman))))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not parse new entity")))
                     .getFirst();
                  _snowmanxxxxxxx = var6x.set(_snowman, _snowmanxxxxxxxxx.set(_snowman, _snowmanxxxxxxxxxx.set(_snowman, _snowmanxxxxxxxxxxxx)));
               }

               if (_snowmanxx != 0) {
                  _snowmanx = _snowmanx.set("Damage", _snowmanx.createShort((short)0));
                  _snowmanxxxxxxx = _snowmanxxxxxxx.set(DSL.remainderFinder(), _snowmanx);
               }

               return _snowmanxxxxxxx;
            } else {
               return var6x;
            }
         }
      );
   }
}
