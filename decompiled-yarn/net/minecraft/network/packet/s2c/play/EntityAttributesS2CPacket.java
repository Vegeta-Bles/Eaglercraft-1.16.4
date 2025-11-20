package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityAttributesS2CPacket implements Packet<ClientPlayPacketListener> {
   private int entityId;
   private final List<EntityAttributesS2CPacket.Entry> entries = Lists.newArrayList();

   public EntityAttributesS2CPacket() {
   }

   public EntityAttributesS2CPacket(int entityId, Collection<EntityAttributeInstance> attributes) {
      this.entityId = entityId;

      for (EntityAttributeInstance _snowman : attributes) {
         this.entries.add(new EntityAttributesS2CPacket.Entry(_snowman.getAttribute(), _snowman.getBaseValue(), _snowman.getModifiers()));
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.entityId = buf.readVarInt();
      int _snowman = buf.readInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Identifier _snowmanxx = buf.readIdentifier();
         EntityAttribute _snowmanxxx = Registry.ATTRIBUTE.get(_snowmanxx);
         double _snowmanxxxx = buf.readDouble();
         List<EntityAttributeModifier> _snowmanxxxxx = Lists.newArrayList();
         int _snowmanxxxxxx = buf.readVarInt();

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
            UUID _snowmanxxxxxxxx = buf.readUuid();
            _snowmanxxxxx.add(
               new EntityAttributeModifier(
                  _snowmanxxxxxxxx, "Unknown synced attribute modifier", buf.readDouble(), EntityAttributeModifier.Operation.fromId(buf.readByte())
               )
            );
         }

         this.entries.add(new EntityAttributesS2CPacket.Entry(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.entityId);
      buf.writeInt(this.entries.size());

      for (EntityAttributesS2CPacket.Entry _snowman : this.entries) {
         buf.writeIdentifier(Registry.ATTRIBUTE.getId(_snowman.getId()));
         buf.writeDouble(_snowman.getBaseValue());
         buf.writeVarInt(_snowman.getModifiers().size());

         for (EntityAttributeModifier _snowmanx : _snowman.getModifiers()) {
            buf.writeUuid(_snowmanx.getId());
            buf.writeDouble(_snowmanx.getValue());
            buf.writeByte(_snowmanx.getOperation().getId());
         }
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onEntityAttributes(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public List<EntityAttributesS2CPacket.Entry> getEntries() {
      return this.entries;
   }

   public class Entry {
      private final EntityAttribute id;
      private final double baseValue;
      private final Collection<EntityAttributeModifier> modifiers;

      public Entry(EntityAttribute _snowman, double _snowman, Collection<EntityAttributeModifier> _snowman) {
         this.id = _snowman;
         this.baseValue = _snowman;
         this.modifiers = _snowman;
      }

      public EntityAttribute getId() {
         return this.id;
      }

      public double getBaseValue() {
         return this.baseValue;
      }

      public Collection<EntityAttributeModifier> getModifiers() {
         return this.modifiers;
      }
   }
}
