/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Multimap
 *  com.google.gson.JsonParseException
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
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
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
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
    public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Registry.ITEM.fieldOf("id").forGetter(itemStack -> itemStack.item), (App)Codec.INT.fieldOf("Count").forGetter(itemStack -> itemStack.count), (App)CompoundTag.CODEC.optionalFieldOf("tag").forGetter(itemStack -> Optional.ofNullable(itemStack.tag))).apply((Applicative)instance, ItemStack::new));
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ItemStack EMPTY = new ItemStack((ItemConvertible)null);
    public static final DecimalFormat MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
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

    private ItemStack(ItemConvertible itemConvertible, int count, Optional<CompoundTag> optional) {
        this(itemConvertible, count);
        optional.ifPresent(this::setTag);
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
        }
        catch (RuntimeException runtimeException) {
            LOGGER.debug("Tried to load invalid item: {}", (Object)tag, (Object)runtimeException);
            return EMPTY;
        }
    }

    public boolean isEmpty() {
        if (this == EMPTY) {
            return true;
        }
        if (this.getItem() == null || this.getItem() == Items.AIR) {
            return true;
        }
        return this.count <= 0;
    }

    public ItemStack split(int amount) {
        int n = Math.min(amount, this.count);
        ItemStack _snowman2 = this.copy();
        _snowman2.setCount(n);
        this.decrement(n);
        return _snowman2;
    }

    public Item getItem() {
        return this.empty ? Items.AIR : this.item;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        BlockPos _snowman2 = context.getBlockPos();
        CachedBlockPosition _snowman3 = new CachedBlockPosition(context.getWorld(), _snowman2, false);
        if (playerEntity != null && !playerEntity.abilities.allowModifyWorld && !this.canPlaceOn(context.getWorld().getTagManager(), _snowman3)) {
            return ActionResult.PASS;
        }
        Item _snowman4 = this.getItem();
        ActionResult _snowman5 = _snowman4.useOnBlock(context);
        if (playerEntity != null && _snowman5.isAccepted()) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(_snowman4));
        }
        return _snowman5;
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
        Identifier identifier = Registry.ITEM.getId(this.getItem());
        tag.putString("id", identifier == null ? "minecraft:air" : identifier.toString());
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
        if (this.empty || this.getItem().getMaxDamage() <= 0) {
            return false;
        }
        CompoundTag compoundTag = this.getTag();
        return compoundTag == null || !compoundTag.getBoolean("Unbreakable");
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
        int n;
        if (!this.isDamageable()) {
            return false;
        }
        if (amount > 0) {
            n = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, this);
            _snowman = 0;
            for (_snowman = 0; n > 0 && _snowman < amount; ++_snowman) {
                if (!UnbreakingEnchantment.shouldPreventDamage(this, n, random)) continue;
                ++_snowman;
            }
            if ((amount -= _snowman) <= 0) {
                return false;
            }
        }
        if (player != null && amount != 0) {
            Criteria.ITEM_DURABILITY_CHANGED.trigger(player, this, this.getDamage() + amount);
        }
        n = this.getDamage() + amount;
        this.setDamage(n);
        return n >= this.getMaxDamage();
    }

    public <T extends LivingEntity> void damage(int amount, T entity, Consumer<T> breakCallback) {
        if (entity.world.isClient || entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.creativeMode) {
            return;
        }
        if (!this.isDamageable()) {
            return;
        }
        if (this.damage(amount, entity.getRandom(), entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null)) {
            breakCallback.accept(entity);
            Item item = this.getItem();
            this.decrement(1);
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity)entity).incrementStat(Stats.BROKEN.getOrCreateStat(item));
            }
            this.setDamage(0);
        }
    }

    public void postHit(LivingEntity target, PlayerEntity attacker) {
        Item item = this.getItem();
        if (item.postHit(this, target, attacker)) {
            attacker.incrementStat(Stats.USED.getOrCreateStat(item));
        }
    }

    public void postMine(World world, BlockState state, BlockPos pos, PlayerEntity miner) {
        Item item = this.getItem();
        if (item.postMine(this, world, state, pos, miner)) {
            miner.incrementStat(Stats.USED.getOrCreateStat(item));
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
        }
        ItemStack itemStack = new ItemStack(this.getItem(), this.count);
        itemStack.setCooldown(this.getCooldown());
        if (this.tag != null) {
            itemStack.tag = this.tag.copy();
        }
        return itemStack;
    }

    public static boolean areTagsEqual(ItemStack left, ItemStack right) {
        if (left.isEmpty() && right.isEmpty()) {
            return true;
        }
        if (left.isEmpty() || right.isEmpty()) {
            return false;
        }
        if (left.tag == null && right.tag != null) {
            return false;
        }
        return left.tag == null || left.tag.equals(right.tag);
    }

    public static boolean areEqual(ItemStack left, ItemStack right) {
        if (left.isEmpty() && right.isEmpty()) {
            return true;
        }
        if (left.isEmpty() || right.isEmpty()) {
            return false;
        }
        return left.isEqual(right);
    }

    private boolean isEqual(ItemStack stack) {
        if (this.count != stack.count) {
            return false;
        }
        if (this.getItem() != stack.getItem()) {
            return false;
        }
        if (this.tag == null && stack.tag != null) {
            return false;
        }
        return this.tag == null || this.tag.equals(stack.tag);
    }

    public static boolean areItemsEqualIgnoreDamage(ItemStack left, ItemStack right) {
        if (left == right) {
            return true;
        }
        if (!left.isEmpty() && !right.isEmpty()) {
            return left.isItemEqualIgnoreDamage(right);
        }
        return false;
    }

    public static boolean areItemsEqual(ItemStack left, ItemStack right) {
        if (left == right) {
            return true;
        }
        if (!left.isEmpty() && !right.isEmpty()) {
            return left.isItemEqual(right);
        }
        return false;
    }

    public boolean isItemEqualIgnoreDamage(ItemStack stack) {
        return !stack.isEmpty() && this.getItem() == stack.getItem();
    }

    public boolean isItemEqual(ItemStack stack) {
        if (this.isDamageable()) {
            return !stack.isEmpty() && this.getItem() == stack.getItem();
        }
        return this.isItemEqualIgnoreDamage(stack);
    }

    public String getTranslationKey() {
        return this.getItem().getTranslationKey(this);
    }

    public String toString() {
        return this.count + " " + this.getItem();
    }

    public void inventoryTick(World world, Entity entity, int slot, boolean selected) {
        if (this.cooldown > 0) {
            --this.cooldown;
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
        if (this.tag == null || !this.tag.contains(key, 10)) {
            CompoundTag compoundTag = new CompoundTag();
            this.putSubTag(key, compoundTag);
            return compoundTag;
        }
        return this.tag.getCompound(key);
    }

    @Nullable
    public CompoundTag getSubTag(String key) {
        if (this.tag == null || !this.tag.contains(key, 10)) {
            return null;
        }
        return this.tag.getCompound(key);
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
        if (this.tag != null) {
            return this.tag.getList("Enchantments", 10);
        }
        return new ListTag();
    }

    public void setTag(@Nullable CompoundTag tag) {
        this.tag = tag;
        if (this.getItem().isDamageable()) {
            this.setDamage(this.getDamage());
        }
    }

    public Text getName() {
        CompoundTag compoundTag = this.getSubTag("display");
        if (compoundTag != null && compoundTag.contains("Name", 8)) {
            try {
                MutableText mutableText = Text.Serializer.fromJson(compoundTag.getString("Name"));
                if (mutableText != null) {
                    return mutableText;
                }
                compoundTag.remove("Name");
            }
            catch (JsonParseException jsonParseException) {
                compoundTag.remove("Name");
            }
        }
        return this.getItem().getName(this);
    }

    public ItemStack setCustomName(@Nullable Text name) {
        CompoundTag compoundTag = this.getOrCreateSubTag("display");
        if (name != null) {
            compoundTag.putString("Name", Text.Serializer.toJson(name));
        } else {
            compoundTag.remove("Name");
        }
        return this;
    }

    public void removeCustomName() {
        CompoundTag compoundTag = this.getSubTag("display");
        if (compoundTag != null) {
            compoundTag.remove("Name");
            if (compoundTag.isEmpty()) {
                this.removeSubTag("display");
            }
        }
        if (this.tag != null && this.tag.isEmpty()) {
            this.tag = null;
        }
    }

    public boolean hasCustomName() {
        CompoundTag compoundTag = this.getSubTag("display");
        return compoundTag != null && compoundTag.contains("Name", 8);
    }

    public List<Text> getTooltip(@Nullable PlayerEntity player, TooltipContext context) {
        Object object;
        ArrayList arrayList = Lists.newArrayList();
        MutableText _snowman2 = new LiteralText("").append(this.getName()).formatted(this.getRarity().formatting);
        if (this.hasCustomName()) {
            _snowman2.formatted(Formatting.ITALIC);
        }
        arrayList.add(_snowman2);
        if (!context.isAdvanced() && !this.hasCustomName() && this.getItem() == Items.FILLED_MAP) {
            arrayList.add(new LiteralText("#" + FilledMapItem.getMapId(this)).formatted(Formatting.GRAY));
        }
        if (ItemStack.isSectionHidden(n = this.getHideFlags(), TooltipSection.ADDITIONAL)) {
            this.getItem().appendTooltip(this, player == null ? null : player.world, arrayList, context);
        }
        if (this.hasTag()) {
            if (ItemStack.isSectionHidden(n, TooltipSection.ENCHANTMENTS)) {
                ItemStack.appendEnchantments(arrayList, this.getEnchantments());
            }
            if (this.tag.contains("display", 10)) {
                Object object2 = this.tag.getCompound("display");
                if (ItemStack.isSectionHidden(n, TooltipSection.DYE) && ((CompoundTag)object2).contains("color", 99)) {
                    if (context.isAdvanced()) {
                        arrayList.add(new TranslatableText("item.color", String.format("#%06X", ((CompoundTag)object2).getInt("color"))).formatted(Formatting.GRAY));
                    } else {
                        arrayList.add(new TranslatableText("item.dyed").formatted(Formatting.GRAY, Formatting.ITALIC));
                    }
                }
                if (((CompoundTag)object2).getType("Lore") == 9) {
                    ListTag listTag = ((CompoundTag)object2).getList("Lore", 8);
                    for (int i = 0; i < listTag.size(); ++i) {
                        object = listTag.getString(i);
                        try {
                            _snowman = Text.Serializer.fromJson((String)object);
                            if (_snowman == null) continue;
                            arrayList.add(Texts.setStyleIfAbsent(_snowman, LORE_STYLE));
                            continue;
                        }
                        catch (JsonParseException _snowman3) {
                            ((CompoundTag)object2).remove("Lore");
                        }
                    }
                }
            }
        }
        if (ItemStack.isSectionHidden(n, TooltipSection.MODIFIERS)) {
            object2 = EquipmentSlot.values();
            int listTag = ((EquipmentSlot[])object2).length;
            for (int i = 0; i < listTag; ++i) {
                object = object2[i];
                _snowman = this.getAttributeModifiers((EquipmentSlot)((Object)object));
                if (_snowman.isEmpty()) continue;
                arrayList.add(LiteralText.EMPTY);
                arrayList.add(new TranslatableText("item.modifiers." + object.getName()).formatted(Formatting.GRAY));
                for (Map.Entry entry : _snowman.entries()) {
                    EntityAttributeModifier entityAttributeModifier = (EntityAttributeModifier)entry.getValue();
                    double _snowman4 = entityAttributeModifier.getValue();
                    boolean _snowman5 = false;
                    if (player != null) {
                        if (entityAttributeModifier.getId() == Item.ATTACK_DAMAGE_MODIFIER_ID) {
                            _snowman4 += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                            _snowman4 += (double)EnchantmentHelper.getAttackDamage(this, EntityGroup.DEFAULT);
                            _snowman5 = true;
                        } else if (entityAttributeModifier.getId() == Item.ATTACK_SPEED_MODIFIER_ID) {
                            _snowman4 += player.getAttributeBaseValue(EntityAttributes.GENERIC_ATTACK_SPEED);
                            _snowman5 = true;
                        }
                    }
                    double _snowman6 = entityAttributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_BASE || entityAttributeModifier.getOperation() == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? _snowman4 * 100.0 : (((EntityAttribute)entry.getKey()).equals(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? _snowman4 * 10.0 : _snowman4);
                    if (_snowman5) {
                        arrayList.add(new LiteralText(" ").append(new TranslatableText("attribute.modifier.equals." + entityAttributeModifier.getOperation().getId(), MODIFIER_FORMAT.format(_snowman6), new TranslatableText(((EntityAttribute)entry.getKey()).getTranslationKey()))).formatted(Formatting.DARK_GREEN));
                        continue;
                    }
                    if (_snowman4 > 0.0) {
                        arrayList.add(new TranslatableText("attribute.modifier.plus." + entityAttributeModifier.getOperation().getId(), MODIFIER_FORMAT.format(_snowman6), new TranslatableText(((EntityAttribute)entry.getKey()).getTranslationKey())).formatted(Formatting.BLUE));
                        continue;
                    }
                    if (!(_snowman4 < 0.0)) continue;
                    arrayList.add(new TranslatableText("attribute.modifier.take." + entityAttributeModifier.getOperation().getId(), MODIFIER_FORMAT.format(_snowman6 *= -1.0), new TranslatableText(((EntityAttribute)entry.getKey()).getTranslationKey())).formatted(Formatting.RED));
                }
            }
        }
        if (this.hasTag()) {
            int n;
            if (ItemStack.isSectionHidden(n, TooltipSection.UNBREAKABLE) && this.tag.getBoolean("Unbreakable")) {
                arrayList.add(new TranslatableText("item.unbreakable").formatted(Formatting.BLUE));
            }
            if (ItemStack.isSectionHidden(n, TooltipSection.CAN_DESTROY) && this.tag.contains("CanDestroy", 9) && !((ListTag)(object2 = this.tag.getList("CanDestroy", 8))).isEmpty()) {
                arrayList.add(LiteralText.EMPTY);
                arrayList.add(new TranslatableText("item.canBreak").formatted(Formatting.GRAY));
                for (int i = 0; i < ((ListTag)object2).size(); ++i) {
                    arrayList.addAll(ItemStack.parseBlockTag(((ListTag)object2).getString(i)));
                }
            }
            if (ItemStack.isSectionHidden(n, TooltipSection.CAN_PLACE) && this.tag.contains("CanPlaceOn", 9) && !((ListTag)(object2 = this.tag.getList("CanPlaceOn", 8))).isEmpty()) {
                arrayList.add(LiteralText.EMPTY);
                arrayList.add(new TranslatableText("item.canPlace").formatted(Formatting.GRAY));
                for (_snowman = 0; _snowman < ((ListTag)object2).size(); ++_snowman) {
                    arrayList.addAll(ItemStack.parseBlockTag(((ListTag)object2).getString(_snowman)));
                }
            }
        }
        if (context.isAdvanced()) {
            if (this.isDamaged()) {
                arrayList.add(new TranslatableText("item.durability", this.getMaxDamage() - this.getDamage(), this.getMaxDamage()));
            }
            arrayList.add(new LiteralText(Registry.ITEM.getId(this.getItem()).toString()).formatted(Formatting.DARK_GRAY));
            if (this.hasTag()) {
                arrayList.add(new TranslatableText("item.nbt_tags", this.tag.getKeys().size()).formatted(Formatting.DARK_GRAY));
            }
        }
        return arrayList;
    }

    private static boolean isSectionHidden(int flags, TooltipSection tooltipSection) {
        return (flags & tooltipSection.getFlag()) == 0;
    }

    private int getHideFlags() {
        if (this.hasTag() && this.tag.contains("HideFlags", 99)) {
            return this.tag.getInt("HideFlags");
        }
        return 0;
    }

    public void addHideFlag(TooltipSection tooltipSection) {
        CompoundTag compoundTag = this.getOrCreateTag();
        compoundTag.putInt("HideFlags", compoundTag.getInt("HideFlags") | tooltipSection.getFlag());
    }

    public static void appendEnchantments(List<Text> tooltip, ListTag enchantments) {
        for (int i = 0; i < enchantments.size(); ++i) {
            CompoundTag compoundTag = enchantments.getCompound(i);
            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).ifPresent(e -> tooltip.add(e.getName(compoundTag.getInt("lvl"))));
        }
    }

    private static Collection<Text> parseBlockTag(String tag) {
        try {
            BlockArgumentParser blockArgumentParser = new BlockArgumentParser(new StringReader(tag), true).parse(true);
            BlockState _snowman2 = blockArgumentParser.getBlockState();
            Identifier _snowman3 = blockArgumentParser.getTagId();
            boolean _snowman4 = _snowman2 != null;
            boolean bl = _snowman = _snowman3 != null;
            if (_snowman4 || _snowman) {
                if (_snowman4) {
                    return Lists.newArrayList((Object[])new Text[]{_snowman2.getBlock().getName().formatted(Formatting.DARK_GRAY)});
                }
                net.minecraft.tag.Tag<Block> tag2 = BlockTags.getTagGroup().getTag(_snowman3);
                if (tag2 != null && !(_snowman = tag2.values()).isEmpty()) {
                    return _snowman.stream().map(Block::getName).map(text -> text.formatted(Formatting.DARK_GRAY)).collect(Collectors.toList());
                }
            }
        }
        catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return Lists.newArrayList((Object[])new Text[]{new LiteralText("missingno").formatted(Formatting.DARK_GRAY)});
    }

    public boolean hasGlint() {
        return this.getItem().hasGlint(this);
    }

    public Rarity getRarity() {
        return this.getItem().getRarity(this);
    }

    public boolean isEnchantable() {
        if (!this.getItem().isEnchantable(this)) {
            return false;
        }
        return !this.hasEnchantments();
    }

    public void addEnchantment(Enchantment enchantment, int level) {
        this.getOrCreateTag();
        if (!this.tag.contains("Enchantments", 9)) {
            this.tag.put("Enchantments", new ListTag());
        }
        ListTag listTag = this.tag.getList("Enchantments", 10);
        CompoundTag _snowman2 = new CompoundTag();
        _snowman2.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(enchantment)));
        _snowman2.putShort("lvl", (byte)level);
        listTag.add(_snowman2);
    }

    public boolean hasEnchantments() {
        if (this.tag != null && this.tag.contains("Enchantments", 9)) {
            return !this.tag.getList("Enchantments", 10).isEmpty();
        }
        return false;
    }

    public void putSubTag(String key, Tag tag) {
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
        if (this.hasTag() && this.tag.contains("RepairCost", 3)) {
            return this.tag.getInt("RepairCost");
        }
        return 0;
    }

    public void setRepairCost(int repairCost) {
        this.getOrCreateTag().putInt("RepairCost", repairCost);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot2) {
        HashMultimap _snowman3;
        if (this.hasTag() && this.tag.contains("AttributeModifiers", 9)) {
            _snowman3 = HashMultimap.create();
            ListTag _snowman2 = this.tag.getList("AttributeModifiers", 10);
            for (int i = 0; i < _snowman2.size(); ++i) {
                CompoundTag compoundTag = _snowman2.getCompound(i);
                if (compoundTag.contains("Slot", 8) && !compoundTag.getString("Slot").equals(equipmentSlot2.getName()) || !(_snowman = Registry.ATTRIBUTE.getOrEmpty(Identifier.tryParse(compoundTag.getString("AttributeName")))).isPresent() || (_snowman = EntityAttributeModifier.fromTag(compoundTag)) == null || _snowman.getId().getLeastSignificantBits() == 0L || _snowman.getId().getMostSignificantBits() == 0L) continue;
                _snowman3.put((Object)_snowman.get(), (Object)_snowman);
            }
        } else {
            EquipmentSlot equipmentSlot2;
            _snowman3 = this.getItem().getAttributeModifiers(equipmentSlot2);
        }
        return _snowman3;
    }

    public void addAttributeModifier(EntityAttribute entityAttribute, EntityAttributeModifier modifier, @Nullable EquipmentSlot slot) {
        this.getOrCreateTag();
        if (!this.tag.contains("AttributeModifiers", 9)) {
            this.tag.put("AttributeModifiers", new ListTag());
        }
        ListTag listTag = this.tag.getList("AttributeModifiers", 10);
        CompoundTag _snowman2 = modifier.toTag();
        _snowman2.putString("AttributeName", Registry.ATTRIBUTE.getId(entityAttribute).toString());
        if (slot != null) {
            _snowman2.putString("Slot", slot.getName());
        }
        listTag.add(_snowman2);
    }

    public Text toHoverableText() {
        MutableText mutableText = new LiteralText("").append(this.getName());
        if (this.hasCustomName()) {
            mutableText.formatted(Formatting.ITALIC);
        }
        _snowman = Texts.bracketed(mutableText);
        if (!this.empty) {
            _snowman.formatted(this.getRarity().formatting).styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(this))));
        }
        return _snowman;
    }

    private static boolean areBlocksEqual(CachedBlockPosition first, @Nullable CachedBlockPosition second) {
        if (second == null || first.getBlockState() != second.getBlockState()) {
            return false;
        }
        if (first.getBlockEntity() == null && second.getBlockEntity() == null) {
            return true;
        }
        if (first.getBlockEntity() == null || second.getBlockEntity() == null) {
            return false;
        }
        return Objects.equals(first.getBlockEntity().toTag(new CompoundTag()), second.getBlockEntity().toTag(new CompoundTag()));
    }

    public boolean canDestroy(TagManager tagManager, CachedBlockPosition pos) {
        if (ItemStack.areBlocksEqual(pos, this.lastDestroyPos)) {
            return this.lastDestroyResult;
        }
        this.lastDestroyPos = pos;
        if (this.hasTag() && this.tag.contains("CanDestroy", 9)) {
            ListTag listTag = this.tag.getList("CanDestroy", 8);
            for (int i = 0; i < listTag.size(); ++i) {
                String string = listTag.getString(i);
                try {
                    Predicate<CachedBlockPosition> predicate = BlockPredicateArgumentType.blockPredicate().parse(new StringReader(string)).create(tagManager);
                    if (predicate.test(pos)) {
                        this.lastDestroyResult = true;
                        return true;
                    }
                    continue;
                }
                catch (CommandSyntaxException commandSyntaxException) {
                    // empty catch block
                }
            }
        }
        this.lastDestroyResult = false;
        return false;
    }

    public boolean canPlaceOn(TagManager tagManager, CachedBlockPosition pos) {
        if (ItemStack.areBlocksEqual(pos, this.lastPlaceOnPos)) {
            return this.lastPlaceOnResult;
        }
        this.lastPlaceOnPos = pos;
        if (this.hasTag() && this.tag.contains("CanPlaceOn", 9)) {
            ListTag listTag = this.tag.getList("CanPlaceOn", 8);
            for (int i = 0; i < listTag.size(); ++i) {
                String string = listTag.getString(i);
                try {
                    Predicate<CachedBlockPosition> predicate = BlockPredicateArgumentType.blockPredicate().parse(new StringReader(string)).create(tagManager);
                    if (predicate.test(pos)) {
                        this.lastPlaceOnResult = true;
                        return true;
                    }
                    continue;
                }
                catch (CommandSyntaxException commandSyntaxException) {
                    // empty catch block
                }
            }
        }
        this.lastPlaceOnResult = false;
        return false;
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

        public int getFlag() {
            return this.flag;
        }
    }
}

