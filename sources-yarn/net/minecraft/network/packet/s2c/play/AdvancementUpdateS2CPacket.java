package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.Identifier;

public class AdvancementUpdateS2CPacket implements Packet<ClientPlayPacketListener> {
   private boolean clearCurrent;
   private Map<Identifier, Advancement.Task> toEarn;
   private Set<Identifier> toRemove;
   private Map<Identifier, AdvancementProgress> toSetProgress;

   public AdvancementUpdateS2CPacket() {
   }

   public AdvancementUpdateS2CPacket(
      boolean clearCurrent, Collection<Advancement> toEarn, Set<Identifier> toRemove, Map<Identifier, AdvancementProgress> toSetProgress
   ) {
      this.clearCurrent = clearCurrent;
      this.toEarn = Maps.newHashMap();

      for (Advancement lv : toEarn) {
         this.toEarn.put(lv.getId(), lv.createTask());
      }

      this.toRemove = toRemove;
      this.toSetProgress = Maps.newHashMap(toSetProgress);
   }

   public void apply(ClientPlayPacketListener arg) {
      arg.onAdvancements(this);
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.clearCurrent = buf.readBoolean();
      this.toEarn = Maps.newHashMap();
      this.toRemove = Sets.newLinkedHashSet();
      this.toSetProgress = Maps.newHashMap();
      int i = buf.readVarInt();

      for (int j = 0; j < i; j++) {
         Identifier lv = buf.readIdentifier();
         Advancement.Task lv2 = Advancement.Task.fromPacket(buf);
         this.toEarn.put(lv, lv2);
      }

      i = buf.readVarInt();

      for (int k = 0; k < i; k++) {
         Identifier lv3 = buf.readIdentifier();
         this.toRemove.add(lv3);
      }

      i = buf.readVarInt();

      for (int l = 0; l < i; l++) {
         Identifier lv4 = buf.readIdentifier();
         this.toSetProgress.put(lv4, AdvancementProgress.fromPacket(buf));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeBoolean(this.clearCurrent);
      buf.writeVarInt(this.toEarn.size());

      for (Entry<Identifier, Advancement.Task> entry : this.toEarn.entrySet()) {
         Identifier lv = entry.getKey();
         Advancement.Task lv2 = entry.getValue();
         buf.writeIdentifier(lv);
         lv2.toPacket(buf);
      }

      buf.writeVarInt(this.toRemove.size());

      for (Identifier lv3 : this.toRemove) {
         buf.writeIdentifier(lv3);
      }

      buf.writeVarInt(this.toSetProgress.size());

      for (Entry<Identifier, AdvancementProgress> entry2 : this.toSetProgress.entrySet()) {
         buf.writeIdentifier(entry2.getKey());
         entry2.getValue().toPacket(buf);
      }
   }

   @Environment(EnvType.CLIENT)
   public Map<Identifier, Advancement.Task> getAdvancementsToEarn() {
      return this.toEarn;
   }

   @Environment(EnvType.CLIENT)
   public Set<Identifier> getAdvancementIdsToRemove() {
      return this.toRemove;
   }

   @Environment(EnvType.CLIENT)
   public Map<Identifier, AdvancementProgress> getAdvancementsToProgress() {
      return this.toSetProgress;
   }

   @Environment(EnvType.CLIENT)
   public boolean shouldClearCurrent() {
      return this.clearCurrent;
   }
}
