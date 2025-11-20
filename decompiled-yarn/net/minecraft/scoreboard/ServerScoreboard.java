package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer server;
   private final Set<ScoreboardObjective> objectives = Sets.newHashSet();
   private Runnable[] updateListeners = new Runnable[0];

   public ServerScoreboard(MinecraftServer server) {
      this.server = server;
   }

   @Override
   public void updateScore(ScoreboardPlayerScore score) {
      super.updateScore(score);
      if (this.objectives.contains(score.getObjective())) {
         this.server
            .getPlayerManager()
            .sendToAll(
               new ScoreboardPlayerUpdateS2CPacket(ServerScoreboard.UpdateMode.CHANGE, score.getObjective().getName(), score.getPlayerName(), score.getScore())
            );
      }

      this.runUpdateListeners();
   }

   @Override
   public void updatePlayerScore(String playerName) {
      super.updatePlayerScore(playerName);
      this.server.getPlayerManager().sendToAll(new ScoreboardPlayerUpdateS2CPacket(ServerScoreboard.UpdateMode.REMOVE, null, playerName, 0));
      this.runUpdateListeners();
   }

   @Override
   public void updatePlayerScore(String playerName, ScoreboardObjective objective) {
      super.updatePlayerScore(playerName, objective);
      if (this.objectives.contains(objective)) {
         this.server.getPlayerManager().sendToAll(new ScoreboardPlayerUpdateS2CPacket(ServerScoreboard.UpdateMode.REMOVE, objective.getName(), playerName, 0));
      }

      this.runUpdateListeners();
   }

   @Override
   public void setObjectiveSlot(int slot, @Nullable ScoreboardObjective objective) {
      ScoreboardObjective _snowman = this.getObjectiveForSlot(slot);
      super.setObjectiveSlot(slot, objective);
      if (_snowman != objective && _snowman != null) {
         if (this.getSlot(_snowman) > 0) {
            this.server.getPlayerManager().sendToAll(new ScoreboardDisplayS2CPacket(slot, objective));
         } else {
            this.removeScoreboardObjective(_snowman);
         }
      }

      if (objective != null) {
         if (this.objectives.contains(objective)) {
            this.server.getPlayerManager().sendToAll(new ScoreboardDisplayS2CPacket(slot, objective));
         } else {
            this.addScoreboardObjective(objective);
         }
      }

      this.runUpdateListeners();
   }

   @Override
   public boolean addPlayerToTeam(String playerName, Team _snowman) {
      if (super.addPlayerToTeam(playerName, _snowman)) {
         this.server.getPlayerManager().sendToAll(new TeamS2CPacket(_snowman, Arrays.asList(playerName), 3));
         this.runUpdateListeners();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void removePlayerFromTeam(String playerName, Team _snowman) {
      super.removePlayerFromTeam(playerName, _snowman);
      this.server.getPlayerManager().sendToAll(new TeamS2CPacket(_snowman, Arrays.asList(playerName), 4));
      this.runUpdateListeners();
   }

   @Override
   public void updateObjective(ScoreboardObjective objective) {
      super.updateObjective(objective);
      this.runUpdateListeners();
   }

   @Override
   public void updateExistingObjective(ScoreboardObjective objective) {
      super.updateExistingObjective(objective);
      if (this.objectives.contains(objective)) {
         this.server.getPlayerManager().sendToAll(new ScoreboardObjectiveUpdateS2CPacket(objective, 2));
      }

      this.runUpdateListeners();
   }

   @Override
   public void updateRemovedObjective(ScoreboardObjective objective) {
      super.updateRemovedObjective(objective);
      if (this.objectives.contains(objective)) {
         this.removeScoreboardObjective(objective);
      }

      this.runUpdateListeners();
   }

   @Override
   public void updateScoreboardTeamAndPlayers(Team _snowman) {
      super.updateScoreboardTeamAndPlayers(_snowman);
      this.server.getPlayerManager().sendToAll(new TeamS2CPacket(_snowman, 0));
      this.runUpdateListeners();
   }

   @Override
   public void updateScoreboardTeam(Team _snowman) {
      super.updateScoreboardTeam(_snowman);
      this.server.getPlayerManager().sendToAll(new TeamS2CPacket(_snowman, 2));
      this.runUpdateListeners();
   }

   @Override
   public void updateRemovedTeam(Team _snowman) {
      super.updateRemovedTeam(_snowman);
      this.server.getPlayerManager().sendToAll(new TeamS2CPacket(_snowman, 1));
      this.runUpdateListeners();
   }

   public void addUpdateListener(Runnable listener) {
      this.updateListeners = Arrays.copyOf(this.updateListeners, this.updateListeners.length + 1);
      this.updateListeners[this.updateListeners.length - 1] = listener;
   }

   protected void runUpdateListeners() {
      for (Runnable _snowman : this.updateListeners) {
         _snowman.run();
      }
   }

   public List<Packet<?>> createChangePackets(ScoreboardObjective objective) {
      List<Packet<?>> _snowman = Lists.newArrayList();
      _snowman.add(new ScoreboardObjectiveUpdateS2CPacket(objective, 0));

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (this.getObjectiveForSlot(_snowmanx) == objective) {
            _snowman.add(new ScoreboardDisplayS2CPacket(_snowmanx, objective));
         }
      }

      for (ScoreboardPlayerScore _snowmanxx : this.getAllPlayerScores(objective)) {
         _snowman.add(new ScoreboardPlayerUpdateS2CPacket(ServerScoreboard.UpdateMode.CHANGE, _snowmanxx.getObjective().getName(), _snowmanxx.getPlayerName(), _snowmanxx.getScore()));
      }

      return _snowman;
   }

   public void addScoreboardObjective(ScoreboardObjective objective) {
      List<Packet<?>> _snowman = this.createChangePackets(objective);

      for (ServerPlayerEntity _snowmanx : this.server.getPlayerManager().getPlayerList()) {
         for (Packet<?> _snowmanxx : _snowman) {
            _snowmanx.networkHandler.sendPacket(_snowmanxx);
         }
      }

      this.objectives.add(objective);
   }

   public List<Packet<?>> createRemovePackets(ScoreboardObjective objective) {
      List<Packet<?>> _snowman = Lists.newArrayList();
      _snowman.add(new ScoreboardObjectiveUpdateS2CPacket(objective, 1));

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (this.getObjectiveForSlot(_snowmanx) == objective) {
            _snowman.add(new ScoreboardDisplayS2CPacket(_snowmanx, objective));
         }
      }

      return _snowman;
   }

   public void removeScoreboardObjective(ScoreboardObjective objective) {
      List<Packet<?>> _snowman = this.createRemovePackets(objective);

      for (ServerPlayerEntity _snowmanx : this.server.getPlayerManager().getPlayerList()) {
         for (Packet<?> _snowmanxx : _snowman) {
            _snowmanx.networkHandler.sendPacket(_snowmanxx);
         }
      }

      this.objectives.remove(objective);
   }

   public int getSlot(ScoreboardObjective objective) {
      int _snowman = 0;

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (this.getObjectiveForSlot(_snowmanx) == objective) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public static enum UpdateMode {
      CHANGE,
      REMOVE;

      private UpdateMode() {
      }
   }
}
