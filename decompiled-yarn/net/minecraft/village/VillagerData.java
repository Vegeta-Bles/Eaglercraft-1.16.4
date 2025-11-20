package net.minecraft.village;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;

public class VillagerData {
   private static final int[] LEVEL_BASE_EXPERIENCE = new int[]{0, 10, 70, 150, 250};
   public static final Codec<VillagerData> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Registry.VILLAGER_TYPE.fieldOf("type").orElseGet(() -> VillagerType.PLAINS).forGetter(_snowmanx -> _snowmanx.type),
               Registry.VILLAGER_PROFESSION.fieldOf("profession").orElseGet(() -> VillagerProfession.NONE).forGetter(_snowmanx -> _snowmanx.profession),
               Codec.INT.fieldOf("level").orElse(1).forGetter(_snowmanx -> _snowmanx.level)
            )
            .apply(_snowman, VillagerData::new)
   );
   private final VillagerType type;
   private final VillagerProfession profession;
   private final int level;

   public VillagerData(VillagerType _snowman, VillagerProfession _snowman, int _snowman) {
      this.type = _snowman;
      this.profession = _snowman;
      this.level = Math.max(1, _snowman);
   }

   public VillagerType getType() {
      return this.type;
   }

   public VillagerProfession getProfession() {
      return this.profession;
   }

   public int getLevel() {
      return this.level;
   }

   public VillagerData withType(VillagerType _snowman) {
      return new VillagerData(_snowman, this.profession, this.level);
   }

   public VillagerData withProfession(VillagerProfession _snowman) {
      return new VillagerData(this.type, _snowman, this.level);
   }

   public VillagerData withLevel(int level) {
      return new VillagerData(this.type, this.profession, level);
   }

   public static int getLowerLevelExperience(int level) {
      return canLevelUp(level) ? LEVEL_BASE_EXPERIENCE[level - 1] : 0;
   }

   public static int getUpperLevelExperience(int level) {
      return canLevelUp(level) ? LEVEL_BASE_EXPERIENCE[level] : 0;
   }

   public static boolean canLevelUp(int level) {
      return level >= 1 && level < 5;
   }
}
