package net.minecraft.entity.boss;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CommandBossBar extends ServerBossBar {
   private final Identifier id;
   private final Set<UUID> playerUuids = Sets.newHashSet();
   private int value;
   private int maxValue = 100;

   public CommandBossBar(Identifier id, Text displayName) {
      super(displayName, BossBar.Color.WHITE, BossBar.Style.PROGRESS);
      this.id = id;
      this.setPercent(0.0F);
   }

   public Identifier getId() {
      return this.id;
   }

   @Override
   public void addPlayer(ServerPlayerEntity player) {
      super.addPlayer(player);
      this.playerUuids.add(player.getUuid());
   }

   public void addPlayer(UUID uuid) {
      this.playerUuids.add(uuid);
   }

   @Override
   public void removePlayer(ServerPlayerEntity player) {
      super.removePlayer(player);
      this.playerUuids.remove(player.getUuid());
   }

   @Override
   public void clearPlayers() {
      super.clearPlayers();
      this.playerUuids.clear();
   }

   public int getValue() {
      return this.value;
   }

   public int getMaxValue() {
      return this.maxValue;
   }

   public void setValue(int value) {
      this.value = value;
      this.setPercent(MathHelper.clamp((float)value / (float)this.maxValue, 0.0F, 1.0F));
   }

   public void setMaxValue(int maxValue) {
      this.maxValue = maxValue;
      this.setPercent(MathHelper.clamp((float)this.value / (float)maxValue, 0.0F, 1.0F));
   }

   public final Text toHoverableText() {
      return Texts.bracketed(this.getName())
         .styled(
            style -> style.withColor(this.getColor().getTextFormat())
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText(this.getId().toString())))
                  .withInsertion(this.getId().toString())
         );
   }

   public boolean addPlayers(Collection<ServerPlayerEntity> players) {
      Set<UUID> _snowman = Sets.newHashSet();
      Set<ServerPlayerEntity> _snowmanx = Sets.newHashSet();

      for (UUID _snowmanxx : this.playerUuids) {
         boolean _snowmanxxx = false;

         for (ServerPlayerEntity _snowmanxxxx : players) {
            if (_snowmanxxxx.getUuid().equals(_snowmanxx)) {
               _snowmanxxx = true;
               break;
            }
         }

         if (!_snowmanxxx) {
            _snowman.add(_snowmanxx);
         }
      }

      for (ServerPlayerEntity _snowmanxx : players) {
         boolean _snowmanxxx = false;

         for (UUID _snowmanxxxxx : this.playerUuids) {
            if (_snowmanxx.getUuid().equals(_snowmanxxxxx)) {
               _snowmanxxx = true;
               break;
            }
         }

         if (!_snowmanxxx) {
            _snowmanx.add(_snowmanxx);
         }
      }

      for (UUID _snowmanxx : _snowman) {
         for (ServerPlayerEntity _snowmanxxx : this.getPlayers()) {
            if (_snowmanxxx.getUuid().equals(_snowmanxx)) {
               this.removePlayer(_snowmanxxx);
               break;
            }
         }

         this.playerUuids.remove(_snowmanxx);
      }

      for (ServerPlayerEntity _snowmanxx : _snowmanx) {
         this.addPlayer(_snowmanxx);
      }

      return !_snowman.isEmpty() || !_snowmanx.isEmpty();
   }

   public CompoundTag toTag() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("Name", Text.Serializer.toJson(this.name));
      _snowman.putBoolean("Visible", this.isVisible());
      _snowman.putInt("Value", this.value);
      _snowman.putInt("Max", this.maxValue);
      _snowman.putString("Color", this.getColor().getName());
      _snowman.putString("Overlay", this.getOverlay().getName());
      _snowman.putBoolean("DarkenScreen", this.getDarkenSky());
      _snowman.putBoolean("PlayBossMusic", this.hasDragonMusic());
      _snowman.putBoolean("CreateWorldFog", this.getThickenFog());
      ListTag _snowmanx = new ListTag();

      for (UUID _snowmanxx : this.playerUuids) {
         _snowmanx.add(NbtHelper.fromUuid(_snowmanxx));
      }

      _snowman.put("Players", _snowmanx);
      return _snowman;
   }

   public static CommandBossBar fromTag(CompoundTag tag, Identifier id) {
      CommandBossBar _snowman = new CommandBossBar(id, Text.Serializer.fromJson(tag.getString("Name")));
      _snowman.setVisible(tag.getBoolean("Visible"));
      _snowman.setValue(tag.getInt("Value"));
      _snowman.setMaxValue(tag.getInt("Max"));
      _snowman.setColor(BossBar.Color.byName(tag.getString("Color")));
      _snowman.setOverlay(BossBar.Style.byName(tag.getString("Overlay")));
      _snowman.setDarkenSky(tag.getBoolean("DarkenScreen"));
      _snowman.setDragonMusic(tag.getBoolean("PlayBossMusic"));
      _snowman.setThickenFog(tag.getBoolean("CreateWorldFog"));
      ListTag _snowmanx = tag.getList("Players", 11);

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         _snowman.addPlayer(NbtHelper.toUuid(_snowmanx.get(_snowmanxx)));
      }

      return _snowman;
   }

   public void onPlayerConnect(ServerPlayerEntity player) {
      if (this.playerUuids.contains(player.getUuid())) {
         this.addPlayer(player);
      }
   }

   public void onPlayerDisconnect(ServerPlayerEntity player) {
      super.removePlayer(player);
   }
}
