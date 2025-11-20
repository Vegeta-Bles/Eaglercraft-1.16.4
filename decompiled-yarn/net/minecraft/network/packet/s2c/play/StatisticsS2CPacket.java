package net.minecraft.network.packet.s2c.play;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.IOException;
import java.util.Map;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;
import net.minecraft.util.registry.Registry;

public class StatisticsS2CPacket implements Packet<ClientPlayPacketListener> {
   private Object2IntMap<Stat<?>> stats;

   public StatisticsS2CPacket() {
   }

   public StatisticsS2CPacket(Object2IntMap<Stat<?>> stats) {
      this.stats = stats;
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onStatistics(this);
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      int _snowman = buf.readVarInt();
      this.stats = new Object2IntOpenHashMap(_snowman);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.readStat(Registry.STAT_TYPE.get(buf.readVarInt()), buf);
      }
   }

   private <T> void readStat(StatType<T> type, PacketByteBuf buf) {
      int _snowman = buf.readVarInt();
      int _snowmanx = buf.readVarInt();
      this.stats.put(type.getOrCreateStat(type.getRegistry().get(_snowman)), _snowmanx);
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.stats.size());
      ObjectIterator var2 = this.stats.object2IntEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<Stat<?>> _snowman = (Entry<Stat<?>>)var2.next();
         Stat<?> _snowmanx = (Stat<?>)_snowman.getKey();
         buf.writeVarInt(Registry.STAT_TYPE.getRawId(_snowmanx.getType()));
         buf.writeVarInt(this.getStatId(_snowmanx));
         buf.writeVarInt(_snowman.getIntValue());
      }
   }

   private <T> int getStatId(Stat<T> stat) {
      return stat.getType().getRegistry().getRawId(stat.getValue());
   }

   public Map<Stat<?>, Integer> getStatMap() {
      return this.stats;
   }
}
