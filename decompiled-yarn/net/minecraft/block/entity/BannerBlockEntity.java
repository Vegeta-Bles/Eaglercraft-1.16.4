package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Nameable;

public class BannerBlockEntity extends BlockEntity implements Nameable {
   @Nullable
   private Text customName;
   @Nullable
   private DyeColor baseColor = DyeColor.WHITE;
   @Nullable
   private ListTag patternListTag;
   private boolean patternListTagRead;
   @Nullable
   private List<Pair<BannerPattern, DyeColor>> patterns;

   public BannerBlockEntity() {
      super(BlockEntityType.BANNER);
   }

   public BannerBlockEntity(DyeColor baseColor) {
      this();
      this.baseColor = baseColor;
   }

   @Nullable
   public static ListTag getPatternListTag(ItemStack stack) {
      ListTag _snowman = null;
      CompoundTag _snowmanx = stack.getSubTag("BlockEntityTag");
      if (_snowmanx != null && _snowmanx.contains("Patterns", 9)) {
         _snowman = _snowmanx.getList("Patterns", 10).copy();
      }

      return _snowman;
   }

   public void readFrom(ItemStack stack, DyeColor baseColor) {
      this.patternListTag = getPatternListTag(stack);
      this.baseColor = baseColor;
      this.patterns = null;
      this.patternListTagRead = true;
      this.customName = stack.hasCustomName() ? stack.getName() : null;
   }

   @Override
   public Text getName() {
      return (Text)(this.customName != null ? this.customName : new TranslatableText("block.minecraft.banner"));
   }

   @Nullable
   @Override
   public Text getCustomName() {
      return this.customName;
   }

   public void setCustomName(Text customName) {
      this.customName = customName;
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      if (this.patternListTag != null) {
         tag.put("Patterns", this.patternListTag);
      }

      if (this.customName != null) {
         tag.putString("CustomName", Text.Serializer.toJson(this.customName));
      }

      return tag;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      if (tag.contains("CustomName", 8)) {
         this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
      }

      if (this.hasWorld()) {
         this.baseColor = ((AbstractBannerBlock)this.getCachedState().getBlock()).getColor();
      } else {
         this.baseColor = null;
      }

      this.patternListTag = tag.getList("Patterns", 10);
      this.patterns = null;
      this.patternListTagRead = true;
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 6, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   public static int getPatternCount(ItemStack stack) {
      CompoundTag _snowman = stack.getSubTag("BlockEntityTag");
      return _snowman != null && _snowman.contains("Patterns") ? _snowman.getList("Patterns", 10).size() : 0;
   }

   public List<Pair<BannerPattern, DyeColor>> getPatterns() {
      if (this.patterns == null && this.patternListTagRead) {
         this.patterns = method_24280(this.getColorForState(this::getCachedState), this.patternListTag);
      }

      return this.patterns;
   }

   public static List<Pair<BannerPattern, DyeColor>> method_24280(DyeColor _snowman, @Nullable ListTag _snowman) {
      List<Pair<BannerPattern, DyeColor>> _snowmanxx = Lists.newArrayList();
      _snowmanxx.add(Pair.of(BannerPattern.BASE, _snowman));
      if (_snowman != null) {
         for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
            CompoundTag _snowmanxxxx = _snowman.getCompound(_snowmanxxx);
            BannerPattern _snowmanxxxxx = BannerPattern.byId(_snowmanxxxx.getString("Pattern"));
            if (_snowmanxxxxx != null) {
               int _snowmanxxxxxx = _snowmanxxxx.getInt("Color");
               _snowmanxx.add(Pair.of(_snowmanxxxxx, DyeColor.byId(_snowmanxxxxxx)));
            }
         }
      }

      return _snowmanxx;
   }

   public static void loadFromItemStack(ItemStack stack) {
      CompoundTag _snowman = stack.getSubTag("BlockEntityTag");
      if (_snowman != null && _snowman.contains("Patterns", 9)) {
         ListTag _snowmanx = _snowman.getList("Patterns", 10);
         if (!_snowmanx.isEmpty()) {
            _snowmanx.remove(_snowmanx.size() - 1);
            if (_snowmanx.isEmpty()) {
               stack.removeSubTag("BlockEntityTag");
            }
         }
      }
   }

   public ItemStack getPickStack(BlockState state) {
      ItemStack _snowman = new ItemStack(BannerBlock.getForColor(this.getColorForState(() -> state)));
      if (this.patternListTag != null && !this.patternListTag.isEmpty()) {
         _snowman.getOrCreateSubTag("BlockEntityTag").put("Patterns", this.patternListTag.copy());
      }

      if (this.customName != null) {
         _snowman.setCustomName(this.customName);
      }

      return _snowman;
   }

   public DyeColor getColorForState(Supplier<BlockState> _snowman) {
      if (this.baseColor == null) {
         this.baseColor = ((AbstractBannerBlock)_snowman.get().getBlock()).getColor();
      }

      return this.baseColor;
   }
}
