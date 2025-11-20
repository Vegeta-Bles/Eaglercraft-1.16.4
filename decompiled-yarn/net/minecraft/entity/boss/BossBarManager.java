package net.minecraft.entity.boss;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BossBarManager {
   private final Map<Identifier, CommandBossBar> commandBossBars = Maps.newHashMap();

   public BossBarManager() {
   }

   @Nullable
   public CommandBossBar get(Identifier id) {
      return this.commandBossBars.get(id);
   }

   public CommandBossBar add(Identifier id, Text displayName) {
      CommandBossBar _snowman = new CommandBossBar(id, displayName);
      this.commandBossBars.put(id, _snowman);
      return _snowman;
   }

   public void remove(CommandBossBar bossBar) {
      this.commandBossBars.remove(bossBar.getId());
   }

   public Collection<Identifier> getIds() {
      return this.commandBossBars.keySet();
   }

   public Collection<CommandBossBar> getAll() {
      return this.commandBossBars.values();
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();

      for (CommandBossBar _snowmanx : this.commandBossBars.values()) {
         _snowman.put(_snowmanx.getId().toString(), _snowmanx.toTag());
      }

      return _snowman;
   }

   public void fromTag(CompoundTag tag) {
      for (String _snowman : tag.getKeys()) {
         Identifier _snowmanx = new Identifier(_snowman);
         this.commandBossBars.put(_snowmanx, CommandBossBar.fromTag(tag.getCompound(_snowman), _snowmanx));
      }
   }

   public void onPlayerConnect(ServerPlayerEntity player) {
      for (CommandBossBar _snowman : this.commandBossBars.values()) {
         _snowman.onPlayerConnect(player);
      }
   }

   public void onPlayerDisconnect(ServerPlayerEntity player) {
      for (CommandBossBar _snowman : this.commandBossBars.values()) {
         _snowman.onPlayerDisconnect(player);
      }
   }
}
