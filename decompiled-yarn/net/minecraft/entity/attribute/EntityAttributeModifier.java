package net.minecraft.entity.attribute;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAttributeModifier {
   private static final Logger LOGGER = LogManager.getLogger();
   private final double value;
   private final EntityAttributeModifier.Operation operation;
   private final Supplier<String> nameGetter;
   private final UUID uuid;

   public EntityAttributeModifier(String name, double value, EntityAttributeModifier.Operation operation) {
      this(MathHelper.randomUuid(ThreadLocalRandom.current()), () -> name, value, operation);
   }

   public EntityAttributeModifier(UUID uuid, String name, double value, EntityAttributeModifier.Operation operation) {
      this(uuid, () -> name, value, operation);
   }

   public EntityAttributeModifier(UUID uuid, Supplier<String> nameGetter, double value, EntityAttributeModifier.Operation operation) {
      this.uuid = uuid;
      this.nameGetter = nameGetter;
      this.value = value;
      this.operation = operation;
   }

   public UUID getId() {
      return this.uuid;
   }

   public String getName() {
      return this.nameGetter.get();
   }

   public EntityAttributeModifier.Operation getOperation() {
      return this.operation;
   }

   public double getValue() {
      return this.value;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         EntityAttributeModifier _snowman = (EntityAttributeModifier)o;
         return Objects.equals(this.uuid, _snowman.uuid);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.uuid.hashCode();
   }

   @Override
   public String toString() {
      return "AttributeModifier{amount=" + this.value + ", operation=" + this.operation + ", name='" + this.nameGetter.get() + '\'' + ", id=" + this.uuid + '}';
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("Name", this.getName());
      _snowman.putDouble("Amount", this.value);
      _snowman.putInt("Operation", this.operation.getId());
      _snowman.putUuid("UUID", this.uuid);
      return _snowman;
   }

   @Nullable
   public static EntityAttributeModifier fromTag(CompoundTag tag) {
      try {
         UUID _snowman = tag.getUuid("UUID");
         EntityAttributeModifier.Operation _snowmanx = EntityAttributeModifier.Operation.fromId(tag.getInt("Operation"));
         return new EntityAttributeModifier(_snowman, tag.getString("Name"), tag.getDouble("Amount"), _snowmanx);
      } catch (Exception var3) {
         LOGGER.warn("Unable to create attribute: {}", var3.getMessage());
         return null;
      }
   }

   public static enum Operation {
      ADDITION(0),
      MULTIPLY_BASE(1),
      MULTIPLY_TOTAL(2);

      private static final EntityAttributeModifier.Operation[] VALUES = new EntityAttributeModifier.Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
      private final int id;

      private Operation(int id) {
         this.id = id;
      }

      public int getId() {
         return this.id;
      }

      public static EntityAttributeModifier.Operation fromId(int id) {
         if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
         } else {
            throw new IllegalArgumentException("No operation with value " + id);
         }
      }
   }
}
