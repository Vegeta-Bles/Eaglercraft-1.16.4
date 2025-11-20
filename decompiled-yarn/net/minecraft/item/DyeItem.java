package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;

public class DyeItem extends Item {
   private static final Map<DyeColor, DyeItem> DYES = Maps.newEnumMap(DyeColor.class);
   private final DyeColor color;

   public DyeItem(DyeColor color, Item.Settings settings) {
      super(settings);
      this.color = color;
      DYES.put(color, this);
   }

   @Override
   public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
      if (entity instanceof SheepEntity) {
         SheepEntity _snowman = (SheepEntity)entity;
         if (_snowman.isAlive() && !_snowman.isSheared() && _snowman.getColor() != this.color) {
            if (!user.world.isClient) {
               _snowman.setColor(this.color);
               stack.decrement(1);
            }

            return ActionResult.success(user.world.isClient);
         }
      }

      return ActionResult.PASS;
   }

   public DyeColor getColor() {
      return this.color;
   }

   public static DyeItem byColor(DyeColor color) {
      return DYES.get(color);
   }
}
