package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
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

      for (Advancement _snowman : toEarn) {
         this.toEarn.put(_snowman.getId(), _snowman.createTask());
      }

      this.toRemove = toRemove;
      this.toSetProgress = Maps.newHashMap(toSetProgress);
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onAdvancements(this);
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.clearCurrent = buf.readBoolean();
      this.toEarn = Maps.newHashMap();
      this.toRemove = Sets.newLinkedHashSet();
      this.toSetProgress = Maps.newHashMap();
      int _snowman = buf.readVarInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Identifier _snowmanxx = buf.readIdentifier();
         Advancement.Task _snowmanxxx = Advancement.Task.fromPacket(buf);
         this.toEarn.put(_snowmanxx, _snowmanxxx);
      }

      _snowman = buf.readVarInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Identifier _snowmanxx = buf.readIdentifier();
         this.toRemove.add(_snowmanxx);
      }

      _snowman = buf.readVarInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Identifier _snowmanxx = buf.readIdentifier();
         this.toSetProgress.put(_snowmanxx, AdvancementProgress.fromPacket(buf));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeBoolean(this.clearCurrent);
      buf.writeVarInt(this.toEarn.size());

      for (Entry<Identifier, Advancement.Task> _snowman : this.toEarn.entrySet()) {
         Identifier _snowmanx = _snowman.getKey();
         Advancement.Task _snowmanxx = _snowman.getValue();
         buf.writeIdentifier(_snowmanx);
         _snowmanxx.toPacket(buf);
      }

      buf.writeVarInt(this.toRemove.size());

      for (Identifier _snowman : this.toRemove) {
         buf.writeIdentifier(_snowman);
      }

      buf.writeVarInt(this.toSetProgress.size());

      for (Entry<Identifier, AdvancementProgress> _snowman : this.toSetProgress.entrySet()) {
         buf.writeIdentifier(_snowman.getKey());
         _snowman.getValue().toPacket(buf);
      }
   }

   public Map<Identifier, Advancement.Task> getAdvancementsToEarn() {
      return this.toEarn;
   }

   public Set<Identifier> getAdvancementIdsToRemove() {
      return this.toRemove;
   }

   public Map<Identifier, AdvancementProgress> getAdvancementsToProgress() {
      return this.toSetProgress;
   }

   public boolean shouldClearCurrent() {
      return this.clearCurrent;
   }
}
