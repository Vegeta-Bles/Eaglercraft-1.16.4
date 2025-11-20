package net.minecraft.block;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

public class PlayerSkullBlock extends SkullBlock {
   protected PlayerSkullBlock(AbstractBlock.Settings _snowman) {
      super(SkullBlock.Type.PLAYER, _snowman);
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
      super.onPlaced(world, pos, state, placer, itemStack);
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof SkullBlockEntity) {
         SkullBlockEntity _snowmanx = (SkullBlockEntity)_snowman;
         GameProfile _snowmanxx = null;
         if (itemStack.hasTag()) {
            CompoundTag _snowmanxxx = itemStack.getTag();
            if (_snowmanxxx.contains("SkullOwner", 10)) {
               _snowmanxx = NbtHelper.toGameProfile(_snowmanxxx.getCompound("SkullOwner"));
            } else if (_snowmanxxx.contains("SkullOwner", 8) && !StringUtils.isBlank(_snowmanxxx.getString("SkullOwner"))) {
               _snowmanxx = new GameProfile(null, _snowmanxxx.getString("SkullOwner"));
            }
         }

         _snowmanx.setOwnerAndType(_snowmanxx);
      }
   }
}
