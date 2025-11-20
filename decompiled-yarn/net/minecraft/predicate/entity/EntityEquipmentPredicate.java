package net.minecraft.predicate.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.JsonHelper;
import net.minecraft.village.raid.Raid;

public class EntityEquipmentPredicate {
   public static final EntityEquipmentPredicate ANY = new EntityEquipmentPredicate(
      ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY
   );
   public static final EntityEquipmentPredicate OMINOUS_BANNER_ON_HEAD = new EntityEquipmentPredicate(
      ItemPredicate.Builder.create().item(Items.WHITE_BANNER).nbt(Raid.getOminousBanner().getTag()).build(),
      ItemPredicate.ANY,
      ItemPredicate.ANY,
      ItemPredicate.ANY,
      ItemPredicate.ANY,
      ItemPredicate.ANY
   );
   private final ItemPredicate head;
   private final ItemPredicate chest;
   private final ItemPredicate legs;
   private final ItemPredicate feet;
   private final ItemPredicate mainhand;
   private final ItemPredicate offhand;

   public EntityEquipmentPredicate(
      ItemPredicate head, ItemPredicate chest, ItemPredicate legs, ItemPredicate feet, ItemPredicate mainhand, ItemPredicate offhand
   ) {
      this.head = head;
      this.chest = chest;
      this.legs = legs;
      this.feet = feet;
      this.mainhand = mainhand;
      this.offhand = offhand;
   }

   public boolean test(@Nullable Entity entity) {
      if (this == ANY) {
         return true;
      } else if (!(entity instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity _snowman = (LivingEntity)entity;
         if (!this.head.test(_snowman.getEquippedStack(EquipmentSlot.HEAD))) {
            return false;
         } else if (!this.chest.test(_snowman.getEquippedStack(EquipmentSlot.CHEST))) {
            return false;
         } else if (!this.legs.test(_snowman.getEquippedStack(EquipmentSlot.LEGS))) {
            return false;
         } else if (!this.feet.test(_snowman.getEquippedStack(EquipmentSlot.FEET))) {
            return false;
         } else {
            return !this.mainhand.test(_snowman.getEquippedStack(EquipmentSlot.MAINHAND)) ? false : this.offhand.test(_snowman.getEquippedStack(EquipmentSlot.OFFHAND));
         }
      }
   }

   public static EntityEquipmentPredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "equipment");
         ItemPredicate _snowmanx = ItemPredicate.fromJson(_snowman.get("head"));
         ItemPredicate _snowmanxx = ItemPredicate.fromJson(_snowman.get("chest"));
         ItemPredicate _snowmanxxx = ItemPredicate.fromJson(_snowman.get("legs"));
         ItemPredicate _snowmanxxxx = ItemPredicate.fromJson(_snowman.get("feet"));
         ItemPredicate _snowmanxxxxx = ItemPredicate.fromJson(_snowman.get("mainhand"));
         ItemPredicate _snowmanxxxxxx = ItemPredicate.fromJson(_snowman.get("offhand"));
         return new EntityEquipmentPredicate(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("head", this.head.toJson());
         _snowman.add("chest", this.chest.toJson());
         _snowman.add("legs", this.legs.toJson());
         _snowman.add("feet", this.feet.toJson());
         _snowman.add("mainhand", this.mainhand.toJson());
         _snowman.add("offhand", this.offhand.toJson());
         return _snowman;
      }
   }

   public static class Builder {
      private ItemPredicate head = ItemPredicate.ANY;
      private ItemPredicate chest = ItemPredicate.ANY;
      private ItemPredicate legs = ItemPredicate.ANY;
      private ItemPredicate feet = ItemPredicate.ANY;
      private ItemPredicate mainhand = ItemPredicate.ANY;
      private ItemPredicate offhand = ItemPredicate.ANY;

      public Builder() {
      }

      public static EntityEquipmentPredicate.Builder create() {
         return new EntityEquipmentPredicate.Builder();
      }

      public EntityEquipmentPredicate.Builder head(ItemPredicate head) {
         this.head = head;
         return this;
      }

      public EntityEquipmentPredicate.Builder chest(ItemPredicate chest) {
         this.chest = chest;
         return this;
      }

      public EntityEquipmentPredicate.Builder legs(ItemPredicate legs) {
         this.legs = legs;
         return this;
      }

      public EntityEquipmentPredicate.Builder feet(ItemPredicate feet) {
         this.feet = feet;
         return this;
      }

      public EntityEquipmentPredicate build() {
         return new EntityEquipmentPredicate(this.head, this.chest, this.legs, this.feet, this.mainhand, this.offhand);
      }
   }
}
