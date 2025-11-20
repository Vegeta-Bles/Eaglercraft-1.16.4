package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ArmorItem extends Item implements Wearable {
   private static final UUID[] MODIFIERS = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior() {
      @Override
      protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
         return ArmorItem.dispenseArmor(pointer, stack) ? stack : super.dispenseSilently(pointer, stack);
      }
   };
   protected final EquipmentSlot slot;
   private final int protection;
   private final float toughness;
   protected final float knockbackResistance;
   protected final ArmorMaterial type;
   private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

   public static boolean dispenseArmor(BlockPointer pointer, ItemStack armor) {
      BlockPos _snowman = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
      List<LivingEntity> _snowmanx = pointer.getWorld()
         .getEntitiesByClass(LivingEntity.class, new Box(_snowman), EntityPredicates.EXCEPT_SPECTATOR.and(new EntityPredicates.Equipable(armor)));
      if (_snowmanx.isEmpty()) {
         return false;
      } else {
         LivingEntity _snowmanxx = _snowmanx.get(0);
         EquipmentSlot _snowmanxxx = MobEntity.getPreferredEquipmentSlot(armor);
         ItemStack _snowmanxxxx = armor.split(1);
         _snowmanxx.equipStack(_snowmanxxx, _snowmanxxxx);
         if (_snowmanxx instanceof MobEntity) {
            ((MobEntity)_snowmanxx).setEquipmentDropChance(_snowmanxxx, 2.0F);
            ((MobEntity)_snowmanxx).setPersistent();
         }

         return true;
      }
   }

   public ArmorItem(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
      super(settings.maxDamageIfAbsent(material.getDurability(slot)));
      this.type = material;
      this.slot = slot;
      this.protection = material.getProtectionAmount(slot);
      this.toughness = material.getToughness();
      this.knockbackResistance = material.getKnockbackResistance();
      DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
      Builder<EntityAttribute, EntityAttributeModifier> _snowman = ImmutableMultimap.builder();
      UUID _snowmanx = MODIFIERS[slot.getEntitySlotId()];
      _snowman.put(
         EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(_snowmanx, "Armor modifier", (double)this.protection, EntityAttributeModifier.Operation.ADDITION)
      );
      _snowman.put(
         EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
         new EntityAttributeModifier(_snowmanx, "Armor toughness", (double)this.toughness, EntityAttributeModifier.Operation.ADDITION)
      );
      if (material == ArmorMaterials.NETHERITE) {
         _snowman.put(
            EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
            new EntityAttributeModifier(_snowmanx, "Armor knockback resistance", (double)this.knockbackResistance, EntityAttributeModifier.Operation.ADDITION)
         );
      }

      this.attributeModifiers = _snowman.build();
   }

   public EquipmentSlot getSlotType() {
      return this.slot;
   }

   @Override
   public int getEnchantability() {
      return this.type.getEnchantability();
   }

   public ArmorMaterial getMaterial() {
      return this.type;
   }

   @Override
   public boolean canRepair(ItemStack stack, ItemStack ingredient) {
      return this.type.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      EquipmentSlot _snowmanx = MobEntity.getPreferredEquipmentSlot(_snowman);
      ItemStack _snowmanxx = user.getEquippedStack(_snowmanx);
      if (_snowmanxx.isEmpty()) {
         user.equipStack(_snowmanx, _snowman.copy());
         _snowman.setCount(0);
         return TypedActionResult.success(_snowman, world.isClient());
      } else {
         return TypedActionResult.fail(_snowman);
      }
   }

   @Override
   public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
      return slot == this.slot ? this.attributeModifiers : super.getAttributeModifiers(slot);
   }

   public int getProtection() {
      return this.protection;
   }

   public float method_26353() {
      return this.toughness;
   }
}
