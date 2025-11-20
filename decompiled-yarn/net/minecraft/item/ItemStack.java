package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagManager;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemStack {
   public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               Registry.ITEM.fieldOf("id").forGetter(_snowmanx -> _snowmanx.item),
               Codec.INT.fieldOf("Count").forGetter(_snowmanx -> _snowmanx.count),
               CompoundTag.CODEC.optionalFieldOf("tag").forGetter(_snowmanx -> Optional.ofNullable(_snowmanx.tag))
            )
            .apply(_snowman, ItemStack::new)
   );
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ItemStack EMPTY = new ItemStack((Item)null);
   public static final DecimalFormat MODIFIER_FORMAT = Util.make(
      new DecimalFormat("#.##"), _snowman -> _snowman.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT))
   );
   private static final Style LORE_STYLE = Style.EMPTY.withColor(Formatting.DARK_PURPLE).withItalic(true);
   private int count;
   private int cooldown;
   @Deprecated
   private final Item item;
   private CompoundTag tag;
   private boolean empty;
   private Entity holder;
   private CachedBlockPosition lastDestroyPos;
   private boolean lastDestroyResult;
   private CachedBlockPosition lastPlaceOnPos;
   private boolean lastPlaceOnResult;

   public ItemStack(ItemConvertible item) {
      this(item, 1);
   }

   private ItemStack(ItemConvertible _snowman, int count, Optional<CompoundTag> _snowman) {
      this(_snowman, count);
      _snowman.ifPresent(this::setTag);
   }

   public ItemStack(ItemConvertible item, int count) {
      this.item = item == null ? null : item.asItem();
      this.count = count;
      if (this.item != null && this.item.isDamageable()) {
         this.setDamage(this.getDamage());
      }

      this.updateEmptyState();
   }

   private void updateEmptyState() {
      this.empty = false;
      this.empty = this.isEmpty();
   }

   private ItemStack(CompoundTag tag) {
      this.item = Registry.ITEM.get(new Identifier(tag.getString("id")));
      this.count = tag.getByte("Count");
      if (tag.contains("tag", 10)) {
         this.tag = tag.getCompound("tag");
         this.getItem().postProcessTag(tag);
      }

      if (this.getItem().isDamageable()) {
         this.setDamage(this.getDamage());
      }

      this.updateEmptyState();
   }

   public static ItemStack fromTag(CompoundTag tag) {
      try {
         return new ItemStack(tag);
      } catch (RuntimeException var2) {
         LOGGER.debug("Tried to load invalid item: {}", tag, var2);
         return EMPTY;
      }
   }

   public boolean isEmpty() {
      if (this == EMPTY) {
         return true;
      } else {
         return this.getItem() == null || this.getItem() == Items.AIR ? true : this.count <= 0;
      }
   }

   public ItemStack split(int amount) {
      int _snowman = Math.min(amount, this.count);
      ItemStack _snowmanx = this.copy();
      _snowmanx.setCount(_snowman);
      this.decrement(_snowman);
      return _snowmanx;
   }

   public Item getItem() {
      return this.empty ? Items.AIR : this.item;
   }

   public ActionResult useOnBlock(ItemUsageContext context) {
      PlayerEntity _snowman = context.getPlayer();
      BlockPos _snowmanx = context.getBlockPos();
      CachedBlockPosition _snowmanxx = new CachedBlockPosition(context.getWorld(), _snowmanx, false);
      if (_snowman != null && !_snowman.abilities.allowModifyWorld && !this.canPlaceOn(context.getWorld().getTagManager(), _snowmanxx)) {
         return ActionResult.PASS;
      } else {
         Item _snowmanxxx = this.getItem();
         ActionResult _snowmanxxxx = _snowmanxxx.useOnBlock(context);
         if (_snowman != null && _snowmanxxxx.isAccepted()) {
            _snowman.incrementStat(Stats.USED.getOrCreateStat(_snowmanxxx));
         }

         return _snowmanxxxx;
      }
   }

   public float getMiningSpeedMultiplier(BlockState state) {
      return this.getItem().getMiningSpeedMultiplier(this, state);
   }

   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      return this.getItem().use(world, user, hand);
   }

   public ItemStack finishUsing(World world, LivingEntity user) {
      return this.getItem().finishUsing(this, world, user);
   }

   public CompoundTag toTag(CompoundTag tag) {
      Identifier _snowman = Registry.ITEM.getId(this.getItem());
      tag.putString("id", _snowman == null ? "minecraft:air" : _snowman.toString());
      tag.putByte("Count", (byte)this.count);
      if (this.tag != null) {
         tag.put("tag", this.tag.copy());
      }

      return tag;
   }

   public int getMaxCount() {
      return this.getItem().getMaxCount();
   }

   public boolean isStackable() {
      return this.getMaxCount() > 1 && (!this.isDamageable() || !this.isDamaged());
   }

   public boolean isDamageable() {
      if (!this.empty && this.getItem().getMaxDamage() > 0) {
         CompoundTag _snowman = this.getTag();
         return _snowman == null || !_snowman.getBoolean("Unbreakable");
      } else {
         return false;
      }
   }

   public boolean isDamaged() {
      return this.isDamageable() && this.getDamage() > 0;
   }

   public int getDamage() {
      return this.tag == null ? 0 : this.tag.getInt("Damage");
   }

   public void setDamage(int damage) {
      this.getOrCreateTag().putInt("Damage", Math.max(0, damage));
   }

   public int getMaxDamage() {
      return this.getItem().getMaxDamage();
   }

   public boolean damage(int amount, Random random, @Nullable ServerPlayerEntity player) {
      if (!this.isDamageable()) {
         return false;
      } else {
         if (amount > 0) {
            int _snowman = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, this);
            int _snowmanx = 0;

            for (int _snowmanxx = 0; _snowman > 0 && _snowmanxx < amount; _snowmanxx++) {
               if (UnbreakingEnchantment.shouldPreventDamage(this, _snowman, random)) {
                  _snowmanx++;
               }
            }

            amount -= _snowmanx;
            if (amount <= 0) {
               return false;
            }
         }

         if (player != null && amount != 0) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(player, this, this.getDamage() + amount);
         }

         int _snowman = this.getDamage() + amount;
         this.setDamage(_snowman);
         return _snowman >= this.getMaxDamage();
      }
   }

   public <T extends LivingEntity> void damage(int amount, T entity, Consumer<T> breakCallback) {
      if (!entity.world.isClient && (!(entity instanceof PlayerEntity) || !((PlayerEntity)entity).abilities.creativeMode)) {
         if (this.isDamageable()) {
            if (this.damage(amount, entity.getRandom(), entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null)) {
               breakCallback.accept(entity);
               Item _snowman = this.getItem();
               this.decrement(1);
               if (entity instanceof PlayerEntity) {
                  ((PlayerEntity)entity).incrementStat(Stats.BROKEN.getOrCreateStat(_snowman));
               }

               this.setDamage(0);
            }
         }
      }
   }

   public void postHit(LivingEntity target, PlayerEntity attacker) {
      Item _snowman = this.getItem();
      if (_snowman.postHit(this, target, attacker)) {
         attacker.incrementStat(Stats.USED.getOrCreateStat(_snowman));
      }
   }

   public void postMine(World world, BlockState state, BlockPos pos, PlayerEntity miner) {
      Item _snowman = this.getItem();
      if (_snowman.postMine(this, world, state, pos, miner)) {
         miner.incrementStat(Stats.USED.getOrCreateStat(_snowman));
      }
   }

   public boolean isEffectiveOn(BlockState state) {
      return this.getItem().isEffectiveOn(state);
   }

   public ActionResult useOnEntity(PlayerEntity user, LivingEntity entity, Hand hand) {
      return this.getItem().useOnEntity(this, user, entity, hand);
   }

   public ItemStack copy() {
      if (this.isEmpty()) {
         return EMPTY;
      } else {
         ItemStack _snowman = new ItemStack(this.getItem(), this.count);
         _snowman.setCooldown(this.getCooldown());
         if (this.tag != null) {
            _snowman.tag = this.tag.copy();
         }

         return _snowman;
      }
   }

   public static boolean areTagsEqual(ItemStack left, ItemStack right) {
      if (left.isEmpty() && right.isEmpty()) {
         return true;
      } else if (left.isEmpty() || right.isEmpty()) {
         return false;
      } else {
         return left.tag == null && right.tag != null ? false : left.tag == null || left.tag.equals(right.tag);
      }
   }

   public static boolean areEqual(ItemStack left, ItemStack right) {
      if (left.isEmpty() && right.isEmpty()) {
         return true;
      } else {
         return !left.isEmpty() && !right.isEmpty() ? left.isEqual(right) : false;
      }
   }

   private boolean isEqual(ItemStack stack) {
      if (this.count != stack.count) {
         return false;
      } else if (this.getItem() != stack.getItem()) {
         return false;
      } else {
         return this.tag == null && stack.tag != null ? false : this.tag == null || this.tag.equals(stack.tag);
      }
   }

   public static boolean areItemsEqualIgnoreDamage(ItemStack left, ItemStack right) {
      if (left == right) {
         return true;
      } else {
         return !left.isEmpty() && !right.isEmpty() ? left.isItemEqualIgnoreDamage(right) : false;
      }
   }

   public static boolean areItemsEqual(ItemStack left, ItemStack right) {
      if (left == right) {
         return true;
      } else {
         return !left.isEmpty() && !right.isEmpty() ? left.isItemEqual(right) : false;
      }
   }

   public boolean isItemEqualIgnoreDamage(ItemStack stack) {
      return !stack.isEmpty() && this.getItem() == stack.getItem();
   }

   public boolean isItemEqual(ItemStack stack) {
      return !this.isDamageable() ? this.isItemEqualIgnoreDamage(stack) : !stack.isEmpty() && this.getItem() == stack.getItem();
   }

   public String getTranslationKey() {
      return this.getItem().getTranslationKey(this);
   }

   @Override
   public String toString() {
      return this.count + " " + this.getItem();
   }

   public void inventoryTick(World world, Entity entity, int slot, boolean selected) {
      if (this.cooldown > 0) {
         this.cooldown--;
      }

      if (this.getItem() != null) {
         this.getItem().inventoryTick(this, world, entity, slot, selected);
      }
   }

   public void onCraft(World world, PlayerEntity player, int amount) {
      player.increaseStat(Stats.CRAFTED.getOrCreateStat(this.getItem()), amount);
      this.getItem().onCraft(this, world, player);
   }

   public int getMaxUseTime() {
      return this.getItem().getMaxUseTime(this);
   }

   public UseAction getUseAction() {
      return this.getItem().getUseAction(this);
   }

   public void onStoppedUsing(World world, LivingEntity user, int remainingUseTicks) {
      this.getItem().onStoppedUsing(this, world, user, remainingUseTicks);
   }

   public boolean isUsedOnRelease() {
      return this.getItem().isUsedOnRelease(this);
   }

   public boolean hasTag() {
      return !this.empty && this.tag != null && !this.tag.isEmpty();
   }

   @Nullable
   public CompoundTag getTag() {
      return this.tag;
   }

   public CompoundTag getOrCreateTag() {
      if (this.tag == null) {
         this.setTag(new CompoundTag());
      }

      return this.tag;
   }

   public CompoundTag getOrCreateSubTag(String key) {
      if (this.tag != null && this.tag.contains(key, 10)) {
         return this.tag.getCompound(key);
      } else {
         CompoundTag _snowman = new CompoundTag();
         this.putSubTag(key, _snowman);
         return _snowman;
      }
   }

   @Nullable
   public CompoundTag getSubTag(String key) {
      return this.tag != null && this.tag.contains(key, 10) ? this.tag.getCompound(key) : null;
   }

   public void removeSubTag(String key) {
      if (this.tag != null && this.tag.contains(key)) {
         this.tag.remove(key);
         if (this.tag.isEmpty()) {
            this.tag = null;
         }
      }
   }

   public ListTag getEnchantments() {
      return this.tag != null ? this.tag.getList("Enchantments", 10) : new ListTag();
   }

   public void setTag(@Nullable CompoundTag tag) {
      this.tag = tag;
      if (this.getItem().isDamageable()) {
         this.setDamage(this.getDamage());
      }
   }

   public Text getName() {
      CompoundTag _snowman = this.getSubTag("display");
      if (_snowman != null && _snowman.contains("Name", 8)) {
         try {
            Text _snowmanx = Text.Serializer.fromJson(_snowman.getString("Name"));
            if (_snowmanx != null) {
               return _snowmanx;
            }

            _snowman.remove("Name");
         } catch (JsonParseException var3) {
            _snowman.remove("Name");
         }
      }

      return this.getItem().getName(this);
   }

   public ItemStack setCustomName(@Nullable Text name) {
      CompoundTag _snowman = this.getOrCreateSubTag("display");
      if (name != null) {
         _snowman.putString("Name", Text.Serializer.toJson(name));
      } else {
         _snowman.remove("Name");
      }

      return this;
   }

   public void removeCustomName() {
      CompoundTag _snowman = this.getSubTag("display");
      if (_snowman != null) {
         _snowman.remove("Name");
         if (_snowman.isEmpty()) {
            this.removeSubTag("display");
         }
      }

      if (this.tag != null && this.tag.isEmpty()) {
         this.tag = null;
      }
   }

   public boolean hasCustomName() {
      CompoundTag _snowman = this.getSubTag("display");
      return _snowman != null && _snowman.contains("Name", 8);
   }

   public List<Text> getTooltip(@Nullable PlayerEntity player, TooltipContext context) {
      List<Text> _snowman = Lists.newArrayList();
      MutableText _snowmanx = new LiteralText("").append(this.getName()).formatted(this.getRarity().formatting);
      if (this.hasCustomName()) {
         _snowmanx.formatted(Formatting.ITALIC);
      }

      _snowman.add(_snowmanx);
      if (!context.isAdvanced() && !this.hasCustomName() && this.getItem() == Items.FILLED_MAP) {
         _snowman.add(new LiteralText("#" + FilledMapItem.getMapId(this)).formatted(Formatting.GRAY));
      }

      int _snowmanxx = this.getHideFlags();
      if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.ADDITIONAL)) {
         this.getItem().appendTooltip(this, player == null ? null : player.world, _snowman, context);
      }

      if (this.hasTag()) {
         if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.ENCHANTMENTS)) {
            appendEnchantments(_snowman, this.getEnchantments());
         }

         if (this.tag.contains("display", 10)) {
            CompoundTag _snowmanxxx = this.tag.getCompound("display");
            if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.DYE) && _snowmanxxx.contains("color", 99)) {
               if (context.isAdvanced()) {
                  _snowman.add(new TranslatableText("item.color", String.format("#%06X", _snowmanxxx.getInt("color"))).formatted(Formatting.GRAY));
               } else {
                  _snowman.add(new TranslatableText("item.dyed").formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}));
               }
            }

            if (_snowmanxxx.getType("Lore") == 9) {
               ListTag _snowmanxxxx = _snowmanxxx.getList("Lore", 8);

               for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
                  String _snowmanxxxxxx = _snowmanxxxx.getString(_snowmanxxxxx);

                  try {
                     MutableText _snowmanxxxxxxx = Text.Serializer.fromJson(_snowmanxxxxxx);
                     if (_snowmanxxxxxxx != null) {
                        _snowman.add(Texts.setStyleIfAbsent(_snowmanxxxxxxx, LORE_STYLE));
                     }
                  } catch (JsonParseException var19) {
                     _snowmanxxx.remove("Lore");
                  }
               }
            }
         }
      }

      if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.MODIFIERS)) {
         for (EquipmentSlot _snowmanxxxx : EquipmentSlot.values()) {
            Multimap<EntityAttribute, EntityAttributeModifier> _snowmanxxxxx = this.getAttributeModifiers(_snowmanxxxx);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowman.add(LiteralText.EMPTY);
               _snowman.add(new TranslatableText("item.modifiers." + _snowmanxxxx.getName()).formatted(Formatting.GRAY));

               for (Entry<EntityAttribute, EntityAttributeModifier> _snowmanxxxxxx : _snowmanxxxxx.entries()) {
                  EntityAttributeModifier _snowmanxxxxxxx = _snowmanxxxxxx.getValue();
                  double _snowmanxxxxxxxx = _snowmanxxxxxxx.getValue();
                  boolean _snowmanxxxxxxxxx = false;
                  if (player != null) {
                     if (_snowmanxxxxxxx.getId() == Item.ATTACK_DAMAGE_MODIFIER_ID) {
                        _snowmanxxxxxxxx += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                        _snowmanxxxxxxxx += (double)EnchantmentHelper.getAttackDamage(this, EntityGroup.DEFAULT);
                        _snowmanxxxxxxxxx = true;
                     } else if (_snowmanxxxxxxx.getId() == Item.ATTACK_SPEED_MODIFIER_ID) {
                        _snowmanxxxxxxxx += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_SPEED);
                        _snowmanxxxxxxxxx = true;
                     }
                  }

                  double _snowmanxxxxxxxxxx;
                  if (_snowmanxxxxxxx.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_BASE
                     || _snowmanxxxxxxx.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * 100.0;
                  } else if (_snowmanxxxxxx.getKey().equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)) {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx * 10.0;
                  } else {
                     _snowmanxxxxxxxxxx = _snowmanxxxxxxxx;
                  }

                  if (_snowmanxxxxxxxxx) {
                     _snowman.add(
                        new LiteralText(" ")
                           .append(
                              new TranslatableText(
                                 "attribute.modifier.equals." + _snowmanxxxxxxx.getOperation().getId(),
                                 MODIFIER_FORMAT.format(_snowmanxxxxxxxxxx),
                                 new TranslatableText(_snowmanxxxxxx.getKey().getTranslationKey())
                              )
                           )
                           .formatted(Formatting.DARK_GREEN)
                     );
                  } else if (_snowmanxxxxxxxx > 0.0) {
                     _snowman.add(
                        new TranslatableText(
                              "attribute.modifier.plus." + _snowmanxxxxxxx.getOperation().getId(),
                              MODIFIER_FORMAT.format(_snowmanxxxxxxxxxx),
                              new TranslatableText(_snowmanxxxxxx.getKey().getTranslationKey())
                           )
                           .formatted(Formatting.BLUE)
                     );
                  } else if (_snowmanxxxxxxxx < 0.0) {
                     _snowmanxxxxxxxxxx *= -1.0;
                     _snowman.add(
                        new TranslatableText(
                              "attribute.modifier.take." + _snowmanxxxxxxx.getOperation().getId(),
                              MODIFIER_FORMAT.format(_snowmanxxxxxxxxxx),
                              new TranslatableText(_snowmanxxxxxx.getKey().getTranslationKey())
                           )
                           .formatted(Formatting.RED)
                     );
                  }
               }
            }
         }
      }

      if (this.hasTag()) {
         if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.UNBREAKABLE) && this.tag.getBoolean("Unbreakable")) {
            _snowman.add(new TranslatableText("item.unbreakable").formatted(Formatting.BLUE));
         }

         if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.CAN_DESTROY) && this.tag.contains("CanDestroy", 9)) {
            ListTag _snowmanxxxxx = this.tag.getList("CanDestroy", 8);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowman.add(LiteralText.EMPTY);
               _snowman.add(new TranslatableText("item.canBreak").formatted(Formatting.GRAY));

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  _snowman.addAll(parseBlockTag(_snowmanxxxxx.getString(_snowmanxxxxxx)));
               }
            }
         }

         if (isSectionHidden(_snowmanxx, ItemStack.TooltipSection.CAN_PLACE) && this.tag.contains("CanPlaceOn", 9)) {
            ListTag _snowmanxxxxx = this.tag.getList("CanPlaceOn", 8);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowman.add(LiteralText.EMPTY);
               _snowman.add(new TranslatableText("item.canPlace").formatted(Formatting.GRAY));

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  _snowman.addAll(parseBlockTag(_snowmanxxxxx.getString(_snowmanxxxxxx)));
               }
            }
         }
      }

      if (context.isAdvanced()) {
         if (this.isDamaged()) {
            _snowman.add(new TranslatableText("item.durability", this.getMaxDamage() - this.getDamage(), this.getMaxDamage()));
         }

         _snowman.add(new LiteralText(Registry.ITEM.getId(this.getItem()).toString()).formatted(Formatting.DARK_GRAY));
         if (this.hasTag()) {
            _snowman.add(new TranslatableText("item.nbt_tags", this.tag.getKeys().size()).formatted(Formatting.DARK_GRAY));
         }
      }

      return _snowman;
   }

   private static boolean isSectionHidden(int flags, ItemStack.TooltipSection _snowman) {
      return (flags & _snowman.getFlag()) == 0;
   }

   private int getHideFlags() {
      return this.hasTag() && this.tag.contains("HideFlags", 99) ? this.tag.getInt("HideFlags") : 0;
   }

   public void addHideFlag(ItemStack.TooltipSection _snowman) {
      CompoundTag _snowmanx = this.getOrCreateTag();
      _snowmanx.putInt("HideFlags", _snowmanx.getInt("HideFlags") | _snowman.getFlag());
   }

   public static void appendEnchantments(List<Text> tooltip, ListTag enchantments) {
      for (int _snowman = 0; _snowman < enchantments.size(); _snowman++) {
         CompoundTag _snowmanx = enchantments.getCompound(_snowman);
         Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(_snowmanx.getString("id"))).ifPresent(e -> tooltip.add(e.getName(_snowman.getInt("lvl"))));
      }
   }

   private static Collection<Text> parseBlockTag(String tag) {
      try {
         BlockArgumentParser _snowman = new BlockArgumentParser(new StringReader(tag), true).parse(true);
         BlockState _snowmanx = _snowman.getBlockState();
         Identifier _snowmanxx = _snowman.getTagId();
         boolean _snowmanxxx = _snowmanx != null;
         boolean _snowmanxxxx = _snowmanxx != null;
         if (_snowmanxxx || _snowmanxxxx) {
            if (_snowmanxxx) {
               return Lists.newArrayList(new Text[]{_snowmanx.getBlock().getName().formatted(Formatting.DARK_GRAY)});
            }

            Tag<Block> _snowmanxxxxx = BlockTags.getTagGroup().getTag(_snowmanxx);
            if (_snowmanxxxxx != null) {
               Collection<Block> _snowmanxxxxxx = _snowmanxxxxx.values();
               if (!_snowmanxxxxxx.isEmpty()) {
                  return _snowmanxxxxxx.stream().map(Block::getName).map(text -> text.formatted(Formatting.DARK_GRAY)).collect(Collectors.toList());
               }
            }
         }
      } catch (CommandSyntaxException var8) {
      }

      return Lists.newArrayList(new Text[]{new LiteralText("missingno").formatted(Formatting.DARK_GRAY)});
   }

   public boolean hasGlint() {
      return this.getItem().hasGlint(this);
   }

   public Rarity getRarity() {
      return this.getItem().getRarity(this);
   }

   public boolean isEnchantable() {
      return !this.getItem().isEnchantable(this) ? false : !this.hasEnchantments();
   }

   public void addEnchantment(Enchantment enchantment, int level) {
      this.getOrCreateTag();
      if (!this.tag.contains("Enchantments", 9)) {
         this.tag.put("Enchantments", new ListTag());
      }

      ListTag _snowman = this.tag.getList("Enchantments", 10);
      CompoundTag _snowmanx = new CompoundTag();
      _snowmanx.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(enchantment)));
      _snowmanx.putShort("lvl", (short)((byte)level));
      _snowman.add(_snowmanx);
   }

   public boolean hasEnchantments() {
      return this.tag != null && this.tag.contains("Enchantments", 9) ? !this.tag.getList("Enchantments", 10).isEmpty() : false;
   }

   public void putSubTag(String key, net.minecraft.nbt.Tag tag) {
      this.getOrCreateTag().put(key, tag);
   }

   public boolean isInFrame() {
      return this.holder instanceof ItemFrameEntity;
   }

   public void setHolder(@Nullable Entity holder) {
      this.holder = holder;
   }

   @Nullable
   public ItemFrameEntity getFrame() {
      return this.holder instanceof ItemFrameEntity ? (ItemFrameEntity)this.getHolder() : null;
   }

   @Nullable
   public Entity getHolder() {
      return !this.empty ? this.holder : null;
   }

   public int getRepairCost() {
      return this.hasTag() && this.tag.contains("RepairCost", 3) ? this.tag.getInt("RepairCost") : 0;
   }

   public void setRepairCost(int repairCost) {
      this.getOrCreateTag().putInt("RepairCost", repairCost);
   }

   public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot _snowman) {
      Multimap<EntityAttribute, EntityAttributeModifier> _snowmanx;
      if (this.hasTag() && this.tag.contains("AttributeModifiers", 9)) {
         _snowmanx = HashMultimap.create();
         ListTag _snowmanxx = this.tag.getList("AttributeModifiers", 10);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            CompoundTag _snowmanxxxx = _snowmanxx.getCompound(_snowmanxxx);
            if (!_snowmanxxxx.contains("Slot", 8) || _snowmanxxxx.getString("Slot").equals(_snowman.getName())) {
               Optional<EntityAttribute> _snowmanxxxxx = Registry.ATTRIBUTE.getOrEmpty(Identifier.tryParse(_snowmanxxxx.getString("AttributeName")));
               if (_snowmanxxxxx.isPresent()) {
                  EntityAttributeModifier _snowmanxxxxxx = EntityAttributeModifier.fromTag(_snowmanxxxx);
                  if (_snowmanxxxxxx != null && _snowmanxxxxxx.getId().getLeastSignificantBits() != 0L && _snowmanxxxxxx.getId().getMostSignificantBits() != 0L) {
                     _snowmanx.put(_snowmanxxxxx.get(), _snowmanxxxxxx);
                  }
               }
            }
         }
      } else {
         _snowmanx = this.getItem().getAttributeModifiers(_snowman);
      }

      return _snowmanx;
   }

   public void addAttributeModifier(EntityAttribute _snowman, EntityAttributeModifier modifier, @Nullable EquipmentSlot slot) {
      this.getOrCreateTag();
      if (!this.tag.contains("AttributeModifiers", 9)) {
         this.tag.put("AttributeModifiers", new ListTag());
      }

      ListTag _snowmanx = this.tag.getList("AttributeModifiers", 10);
      CompoundTag _snowmanxx = modifier.toTag();
      _snowmanxx.putString("AttributeName", Registry.ATTRIBUTE.getId(_snowman).toString());
      if (slot != null) {
         _snowmanxx.putString("Slot", slot.getName());
      }

      _snowmanx.add(_snowmanxx);
   }

   public Text toHoverableText() {
      MutableText _snowman = new LiteralText("").append(this.getName());
      if (this.hasCustomName()) {
         _snowman.formatted(Formatting.ITALIC);
      }

      MutableText _snowmanx = Texts.bracketed(_snowman);
      if (!this.empty) {
         _snowmanx.formatted(this.getRarity().formatting)
            .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(this))));
      }

      return _snowmanx;
   }

   private static boolean areBlocksEqual(CachedBlockPosition first, @Nullable CachedBlockPosition second) {
      if (second == null || first.getBlockState() != second.getBlockState()) {
         return false;
      } else if (first.getBlockEntity() == null && second.getBlockEntity() == null) {
         return true;
      } else {
         return first.getBlockEntity() != null && second.getBlockEntity() != null
            ? Objects.equals(first.getBlockEntity().toTag(new CompoundTag()), second.getBlockEntity().toTag(new CompoundTag()))
            : false;
      }
   }

   public boolean canDestroy(TagManager _snowman, CachedBlockPosition pos) {
      if (areBlocksEqual(pos, this.lastDestroyPos)) {
         return this.lastDestroyResult;
      } else {
         this.lastDestroyPos = pos;
         if (this.hasTag() && this.tag.contains("CanDestroy", 9)) {
            ListTag _snowmanx = this.tag.getList("CanDestroy", 8);

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               String _snowmanxxx = _snowmanx.getString(_snowmanxx);

               try {
                  Predicate<CachedBlockPosition> _snowmanxxxx = BlockPredicateArgumentType.blockPredicate().parse(new StringReader(_snowmanxxx)).create(_snowman);
                  if (_snowmanxxxx.test(pos)) {
                     this.lastDestroyResult = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.lastDestroyResult = false;
         return false;
      }
   }

   public boolean canPlaceOn(TagManager _snowman, CachedBlockPosition pos) {
      if (areBlocksEqual(pos, this.lastPlaceOnPos)) {
         return this.lastPlaceOnResult;
      } else {
         this.lastPlaceOnPos = pos;
         if (this.hasTag() && this.tag.contains("CanPlaceOn", 9)) {
            ListTag _snowmanx = this.tag.getList("CanPlaceOn", 8);

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               String _snowmanxxx = _snowmanx.getString(_snowmanxx);

               try {
                  Predicate<CachedBlockPosition> _snowmanxxxx = BlockPredicateArgumentType.blockPredicate().parse(new StringReader(_snowmanxxx)).create(_snowman);
                  if (_snowmanxxxx.test(pos)) {
                     this.lastPlaceOnResult = true;
                     return true;
                  }
               } catch (CommandSyntaxException var7) {
               }
            }
         }

         this.lastPlaceOnResult = false;
         return false;
      }
   }

   public int getCooldown() {
      return this.cooldown;
   }

   public void setCooldown(int cooldown) {
      this.cooldown = cooldown;
   }

   public int getCount() {
      return this.empty ? 0 : this.count;
   }

   public void setCount(int count) {
      this.count = count;
      this.updateEmptyState();
   }

   public void increment(int amount) {
      this.setCount(this.count + amount);
   }

   public void decrement(int amount) {
      this.increment(-amount);
   }

   public void usageTick(World world, LivingEntity user, int remainingUseTicks) {
      this.getItem().usageTick(world, user, this, remainingUseTicks);
   }

   public boolean isFood() {
      return this.getItem().isFood();
   }

   public SoundEvent getDrinkSound() {
      return this.getItem().getDrinkSound();
   }

   public SoundEvent getEatSound() {
      return this.getItem().getEatSound();
   }

   public static enum TooltipSection {
      ENCHANTMENTS,
      MODIFIERS,
      UNBREAKABLE,
      CAN_DESTROY,
      CAN_PLACE,
      ADDITIONAL,
      DYE;

      private int flag = 1 << this.ordinal();

      private TooltipSection() {
      }

      public int getFlag() {
         return this.flag;
      }
   }
}
