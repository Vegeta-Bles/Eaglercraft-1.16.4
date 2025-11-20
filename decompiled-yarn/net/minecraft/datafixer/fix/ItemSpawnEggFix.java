package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemSpawnEggFix extends DataFix {
   private static final String[] DAMAGE_TO_ENTITY_IDS = (String[])DataFixUtils.make(new String[256], _snowman -> {
      _snowman[1] = "Item";
      _snowman[2] = "XPOrb";
      _snowman[7] = "ThrownEgg";
      _snowman[8] = "LeashKnot";
      _snowman[9] = "Painting";
      _snowman[10] = "Arrow";
      _snowman[11] = "Snowball";
      _snowman[12] = "Fireball";
      _snowman[13] = "SmallFireball";
      _snowman[14] = "ThrownEnderpearl";
      _snowman[15] = "EyeOfEnderSignal";
      _snowman[16] = "ThrownPotion";
      _snowman[17] = "ThrownExpBottle";
      _snowman[18] = "ItemFrame";
      _snowman[19] = "WitherSkull";
      _snowman[20] = "PrimedTnt";
      _snowman[21] = "FallingSand";
      _snowman[22] = "FireworksRocketEntity";
      _snowman[23] = "TippedArrow";
      _snowman[24] = "SpectralArrow";
      _snowman[25] = "ShulkerBullet";
      _snowman[26] = "DragonFireball";
      _snowman[30] = "ArmorStand";
      _snowman[41] = "Boat";
      _snowman[42] = "MinecartRideable";
      _snowman[43] = "MinecartChest";
      _snowman[44] = "MinecartFurnace";
      _snowman[45] = "MinecartTNT";
      _snowman[46] = "MinecartHopper";
      _snowman[47] = "MinecartSpawner";
      _snowman[40] = "MinecartCommandBlock";
      _snowman[48] = "Mob";
      _snowman[49] = "Monster";
      _snowman[50] = "Creeper";
      _snowman[51] = "Skeleton";
      _snowman[52] = "Spider";
      _snowman[53] = "Giant";
      _snowman[54] = "Zombie";
      _snowman[55] = "Slime";
      _snowman[56] = "Ghast";
      _snowman[57] = "PigZombie";
      _snowman[58] = "Enderman";
      _snowman[59] = "CaveSpider";
      _snowman[60] = "Silverfish";
      _snowman[61] = "Blaze";
      _snowman[62] = "LavaSlime";
      _snowman[63] = "EnderDragon";
      _snowman[64] = "WitherBoss";
      _snowman[65] = "Bat";
      _snowman[66] = "Witch";
      _snowman[67] = "Endermite";
      _snowman[68] = "Guardian";
      _snowman[69] = "Shulker";
      _snowman[90] = "Pig";
      _snowman[91] = "Sheep";
      _snowman[92] = "Cow";
      _snowman[93] = "Chicken";
      _snowman[94] = "Squid";
      _snowman[95] = "Wolf";
      _snowman[96] = "MushroomCow";
      _snowman[97] = "SnowMan";
      _snowman[98] = "Ozelot";
      _snowman[99] = "VillagerGolem";
      _snowman[100] = "EntityHorse";
      _snowman[101] = "Rabbit";
      _snowman[120] = "Villager";
      _snowman[200] = "EnderCrystal";
   });

   public ItemSpawnEggFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      Type<?> _snowmanx = _snowman.getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanxx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<String> _snowmanxxx = DSL.fieldFinder("id", DSL.string());
      OpticFinder<?> _snowmanxxxx = _snowmanx.findField("tag");
      OpticFinder<?> _snowmanxxxxx = _snowmanxxxx.type().findField("EntityTag");
      OpticFinder<?> _snowmanxxxxxx = DSL.typeFinder(_snowman.getTypeRaw(TypeReferences.ENTITY));
      Type<?> _snowmanxxxxxxx = this.getOutputSchema().getTypeRaw(TypeReferences.ENTITY);
      return this.fixTypeEverywhereTyped(
         "ItemSpawnEggFix",
         _snowmanx,
         _snowmanxxxxxxxx -> {
            Optional<Pair<String, String>> _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getOptional(_snowman);
            if (_snowmanxxxxxxxxx.isPresent() && Objects.equals(_snowmanxxxxxxxxx.get().getSecond(), "minecraft:spawn_egg")) {
               Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxxxxxxxx.get(DSL.remainderFinder());
               short _snowmanxx = _snowmanx.get("Damage").asShort((short)0);
               Optional<? extends Typed<?>> _snowmanxxx = _snowmanxxxxxxxx.getOptionalTyped(_snowman);
               Optional<? extends Typed<?>> _snowmanxxxx = _snowmanxxx.flatMap(_snowmanxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxx.getOptionalTyped(_snowman));
               Optional<? extends Typed<?>> _snowmanxxxxx = _snowmanxxxx.flatMap(_snowmanxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxx.getOptionalTyped(_snowman));
               Optional<String> _snowmanxxxxxxx = _snowmanxxxxx.flatMap(_snowmanxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxx.getOptional(_snowman));
               Typed<?> _snowmanxxxxxxxx = _snowmanxxxxxxxx;
               String _snowmanxxxxxxxxx = DAMAGE_TO_ENTITY_IDS[_snowmanxx & 255];
               if (_snowmanxxxxxxxxx != null && (!_snowmanxxxxxxx.isPresent() || !Objects.equals(_snowmanxxxxxxx.get(), _snowmanxxxxxxxxx))) {
                  Typed<?> _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getOrCreateTyped(_snowman);
                  Typed<?> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getOrCreateTyped(_snowman);
                  Typed<?> _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getOrCreateTyped(_snowman);
                  Typed<?> _snowmanxxxxxxxxxxxxx = (Typed<?>)((Pair)_snowmanxxxxxxxxxxxx.write()
                        .flatMap(_snowmanxxxxxxxxxxxxxx -> _snowman.readTyped(_snowmanxxxxxxxxxxxxxx.set("id", _snowman.createString(_snowman))))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not parse new entity")))
                     .getFirst();
                  _snowmanxxxxxxxx = _snowmanxxxxxxxx.set(_snowman, _snowmanxxxxxxxxxx.set(_snowman, _snowmanxxxxxxxxxxx.set(_snowman, _snowmanxxxxxxxxxxxxx)));
               }

               if (_snowmanxx != 0) {
                  _snowmanx = _snowmanx.set("Damage", _snowmanx.createShort((short)0));
                  _snowmanxxxxxxxx = _snowmanxxxxxxxx.set(DSL.remainderFinder(), _snowmanx);
               }

               return _snowmanxxxxxxxx;
            } else {
               return _snowmanxxxxxxxx;
            }
         }
      );
   }
}
