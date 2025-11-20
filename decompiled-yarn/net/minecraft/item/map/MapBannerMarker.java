package net.minecraft.item.map;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MapBannerMarker {
   private final BlockPos pos;
   private final DyeColor color;
   @Nullable
   private final Text name;

   public MapBannerMarker(BlockPos pos, DyeColor dyeColor, @Nullable Text name) {
      this.pos = pos;
      this.color = dyeColor;
      this.name = name;
   }

   public static MapBannerMarker fromNbt(CompoundTag tag) {
      BlockPos _snowman = NbtHelper.toBlockPos(tag.getCompound("Pos"));
      DyeColor _snowmanx = DyeColor.byName(tag.getString("Color"), DyeColor.WHITE);
      Text _snowmanxx = tag.contains("Name") ? Text.Serializer.fromJson(tag.getString("Name")) : null;
      return new MapBannerMarker(_snowman, _snowmanx, _snowmanxx);
   }

   @Nullable
   public static MapBannerMarker fromWorldBlock(BlockView blockView, BlockPos blockPos) {
      BlockEntity _snowman = blockView.getBlockEntity(blockPos);
      if (_snowman instanceof BannerBlockEntity) {
         BannerBlockEntity _snowmanx = (BannerBlockEntity)_snowman;
         DyeColor _snowmanxx = _snowmanx.getColorForState(() -> blockView.getBlockState(blockPos));
         Text _snowmanxxx = _snowmanx.hasCustomName() ? _snowmanx.getCustomName() : null;
         return new MapBannerMarker(blockPos, _snowmanxx, _snowmanxxx);
      } else {
         return null;
      }
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public MapIcon.Type getIconType() {
      switch (this.color) {
         case WHITE:
            return MapIcon.Type.BANNER_WHITE;
         case ORANGE:
            return MapIcon.Type.BANNER_ORANGE;
         case MAGENTA:
            return MapIcon.Type.BANNER_MAGENTA;
         case LIGHT_BLUE:
            return MapIcon.Type.BANNER_LIGHT_BLUE;
         case YELLOW:
            return MapIcon.Type.BANNER_YELLOW;
         case LIME:
            return MapIcon.Type.BANNER_LIME;
         case PINK:
            return MapIcon.Type.BANNER_PINK;
         case GRAY:
            return MapIcon.Type.BANNER_GRAY;
         case LIGHT_GRAY:
            return MapIcon.Type.BANNER_LIGHT_GRAY;
         case CYAN:
            return MapIcon.Type.BANNER_CYAN;
         case PURPLE:
            return MapIcon.Type.BANNER_PURPLE;
         case BLUE:
            return MapIcon.Type.BANNER_BLUE;
         case BROWN:
            return MapIcon.Type.BANNER_BROWN;
         case GREEN:
            return MapIcon.Type.BANNER_GREEN;
         case RED:
            return MapIcon.Type.BANNER_RED;
         case BLACK:
         default:
            return MapIcon.Type.BANNER_BLACK;
      }
   }

   @Nullable
   public Text getName() {
      return this.name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MapBannerMarker _snowman = (MapBannerMarker)o;
         return Objects.equals(this.pos, _snowman.pos) && this.color == _snowman.color && Objects.equals(this.name, _snowman.name);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.pos, this.color, this.name);
   }

   public CompoundTag getNbt() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.put("Pos", NbtHelper.fromBlockPos(this.pos));
      _snowman.putString("Color", this.color.getName());
      if (this.name != null) {
         _snowman.putString("Name", Text.Serializer.toJson(this.name));
      }

      return _snowman;
   }

   public String getKey() {
      return "banner-" + this.pos.getX() + "," + this.pos.getY() + "," + this.pos.getZ();
   }
}
