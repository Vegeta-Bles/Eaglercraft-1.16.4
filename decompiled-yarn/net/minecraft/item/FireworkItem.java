package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireworkItem extends Item {
   public FireworkItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      if (!_snowman.isClient) {
         ItemStack _snowmanx = context.getStack();
         Vec3d _snowmanxx = context.getHitPos();
         Direction _snowmanxxx = context.getSide();
         FireworkRocketEntity _snowmanxxxx = new FireworkRocketEntity(
            _snowman,
            context.getPlayer(),
            _snowmanxx.x + (double)_snowmanxxx.getOffsetX() * 0.15,
            _snowmanxx.y + (double)_snowmanxxx.getOffsetY() * 0.15,
            _snowmanxx.z + (double)_snowmanxxx.getOffsetZ() * 0.15,
            _snowmanx
         );
         _snowman.spawnEntity(_snowmanxxxx);
         _snowmanx.decrement(1);
      }

      return ActionResult.success(_snowman.isClient);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      if (user.isFallFlying()) {
         ItemStack _snowman = user.getStackInHand(hand);
         if (!world.isClient) {
            world.spawnEntity(new FireworkRocketEntity(world, _snowman, user));
            if (!user.abilities.creativeMode) {
               _snowman.decrement(1);
            }
         }

         return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
      } else {
         return TypedActionResult.pass(user.getStackInHand(hand));
      }
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      CompoundTag _snowman = stack.getSubTag("Fireworks");
      if (_snowman != null) {
         if (_snowman.contains("Flight", 99)) {
            tooltip.add(
               new TranslatableText("item.minecraft.firework_rocket.flight").append(" ").append(String.valueOf(_snowman.getByte("Flight"))).formatted(Formatting.GRAY)
            );
         }

         ListTag _snowmanx = _snowman.getList("Explosions", 10);
         if (!_snowmanx.isEmpty()) {
            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               CompoundTag _snowmanxxx = _snowmanx.getCompound(_snowmanxx);
               List<Text> _snowmanxxxx = Lists.newArrayList();
               FireworkChargeItem.appendFireworkTooltip(_snowmanxxx, _snowmanxxxx);
               if (!_snowmanxxxx.isEmpty()) {
                  for (int _snowmanxxxxx = 1; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
                     _snowmanxxxx.set(_snowmanxxxxx, new LiteralText("  ").append(_snowmanxxxx.get(_snowmanxxxxx)).formatted(Formatting.GRAY));
                  }

                  tooltip.addAll(_snowmanxxxx);
               }
            }
         }
      }
   }

   public static enum Type {
      SMALL_BALL(0, "small_ball"),
      LARGE_BALL(1, "large_ball"),
      STAR(2, "star"),
      CREEPER(3, "creeper"),
      BURST(4, "burst");

      private static final FireworkItem.Type[] TYPES = Arrays.stream(values())
         .sorted(Comparator.comparingInt(type -> type.id))
         .toArray(FireworkItem.Type[]::new);
      private final int id;
      private final String name;

      private Type(int id, String name) {
         this.id = id;
         this.name = name;
      }

      public int getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      public static FireworkItem.Type byId(int id) {
         return id >= 0 && id < TYPES.length ? TYPES[id] : SMALL_BALL;
      }
   }
}
