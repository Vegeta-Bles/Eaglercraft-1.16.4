package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Scoreboard {
   private final Map<String, ScoreboardObjective> objectives = Maps.newHashMap();
   private final Map<ScoreboardCriterion, List<ScoreboardObjective>> objectivesByCriterion = Maps.newHashMap();
   private final Map<String, Map<ScoreboardObjective, ScoreboardPlayerScore>> playerObjectives = Maps.newHashMap();
   private final ScoreboardObjective[] objectiveSlots = new ScoreboardObjective[19];
   private final Map<String, Team> teams = Maps.newHashMap();
   private final Map<String, Team> teamsByPlayer = Maps.newHashMap();
   private static String[] displaySlotNames;

   public Scoreboard() {
   }

   public boolean containsObjective(String name) {
      return this.objectives.containsKey(name);
   }

   public ScoreboardObjective getObjective(String name) {
      return this.objectives.get(name);
   }

   @Nullable
   public ScoreboardObjective getNullableObjective(@Nullable String name) {
      return this.objectives.get(name);
   }

   public ScoreboardObjective addObjective(String name, ScoreboardCriterion criterion, Text displayName, ScoreboardCriterion.RenderType renderType) {
      if (name.length() > 16) {
         throw new IllegalArgumentException("The objective name '" + name + "' is too long!");
      } else if (this.objectives.containsKey(name)) {
         throw new IllegalArgumentException("An objective with the name '" + name + "' already exists!");
      } else {
         ScoreboardObjective _snowman = new ScoreboardObjective(this, name, criterion, displayName, renderType);
         this.objectivesByCriterion.computeIfAbsent(criterion, criterionx -> Lists.newArrayList()).add(_snowman);
         this.objectives.put(name, _snowman);
         this.updateObjective(_snowman);
         return _snowman;
      }
   }

   public final void forEachScore(ScoreboardCriterion criterion, String player, Consumer<ScoreboardPlayerScore> action) {
      this.objectivesByCriterion.getOrDefault(criterion, Collections.emptyList()).forEach(objective -> action.accept(this.getPlayerScore(player, objective)));
   }

   public boolean playerHasObjective(String playerName, ScoreboardObjective objective) {
      Map<ScoreboardObjective, ScoreboardPlayerScore> _snowman = this.playerObjectives.get(playerName);
      if (_snowman == null) {
         return false;
      } else {
         ScoreboardPlayerScore _snowmanx = _snowman.get(objective);
         return _snowmanx != null;
      }
   }

   public ScoreboardPlayerScore getPlayerScore(String player, ScoreboardObjective objective) {
      if (player.length() > 40) {
         throw new IllegalArgumentException("The player name '" + player + "' is too long!");
      } else {
         Map<ScoreboardObjective, ScoreboardPlayerScore> _snowman = this.playerObjectives.computeIfAbsent(player, _snowmanx -> Maps.newHashMap());
         return _snowman.computeIfAbsent(objective, objectivex -> {
            ScoreboardPlayerScore _snowmanx = new ScoreboardPlayerScore(this, objectivex, player);
            _snowmanx.setScore(0);
            return _snowmanx;
         });
      }
   }

   public Collection<ScoreboardPlayerScore> getAllPlayerScores(ScoreboardObjective objective) {
      List<ScoreboardPlayerScore> _snowman = Lists.newArrayList();

      for (Map<ScoreboardObjective, ScoreboardPlayerScore> _snowmanx : this.playerObjectives.values()) {
         ScoreboardPlayerScore _snowmanxx = _snowmanx.get(objective);
         if (_snowmanxx != null) {
            _snowman.add(_snowmanxx);
         }
      }

      _snowman.sort(ScoreboardPlayerScore.COMPARATOR);
      return _snowman;
   }

   public Collection<ScoreboardObjective> getObjectives() {
      return this.objectives.values();
   }

   public Collection<String> getObjectiveNames() {
      return this.objectives.keySet();
   }

   public Collection<String> getKnownPlayers() {
      return Lists.newArrayList(this.playerObjectives.keySet());
   }

   public void resetPlayerScore(String playerName, @Nullable ScoreboardObjective objective) {
      if (objective == null) {
         Map<ScoreboardObjective, ScoreboardPlayerScore> _snowman = this.playerObjectives.remove(playerName);
         if (_snowman != null) {
            this.updatePlayerScore(playerName);
         }
      } else {
         Map<ScoreboardObjective, ScoreboardPlayerScore> _snowman = this.playerObjectives.get(playerName);
         if (_snowman != null) {
            ScoreboardPlayerScore _snowmanx = _snowman.remove(objective);
            if (_snowman.size() < 1) {
               Map<ScoreboardObjective, ScoreboardPlayerScore> _snowmanxx = this.playerObjectives.remove(playerName);
               if (_snowmanxx != null) {
                  this.updatePlayerScore(playerName);
               }
            } else if (_snowmanx != null) {
               this.updatePlayerScore(playerName, objective);
            }
         }
      }
   }

   public Map<ScoreboardObjective, ScoreboardPlayerScore> getPlayerObjectives(String _snowman) {
      Map<ScoreboardObjective, ScoreboardPlayerScore> _snowmanx = this.playerObjectives.get(_snowman);
      if (_snowmanx == null) {
         _snowmanx = Maps.newHashMap();
      }

      return _snowmanx;
   }

   public void removeObjective(ScoreboardObjective objective) {
      this.objectives.remove(objective.getName());

      for (int _snowman = 0; _snowman < 19; _snowman++) {
         if (this.getObjectiveForSlot(_snowman) == objective) {
            this.setObjectiveSlot(_snowman, null);
         }
      }

      List<ScoreboardObjective> _snowmanx = this.objectivesByCriterion.get(objective.getCriterion());
      if (_snowmanx != null) {
         _snowmanx.remove(objective);
      }

      for (Map<ScoreboardObjective, ScoreboardPlayerScore> _snowmanxx : this.playerObjectives.values()) {
         _snowmanxx.remove(objective);
      }

      this.updateRemovedObjective(objective);
   }

   public void setObjectiveSlot(int slot, @Nullable ScoreboardObjective objective) {
      this.objectiveSlots[slot] = objective;
   }

   @Nullable
   public ScoreboardObjective getObjectiveForSlot(int _snowman) {
      return this.objectiveSlots[_snowman];
   }

   public Team getTeam(String _snowman) {
      return this.teams.get(_snowman);
   }

   public Team addTeam(String _snowman) {
      if (_snowman.length() > 16) {
         throw new IllegalArgumentException("The team name '" + _snowman + "' is too long!");
      } else {
         Team _snowmanx = this.getTeam(_snowman);
         if (_snowmanx != null) {
            throw new IllegalArgumentException("A team with the name '" + _snowman + "' already exists!");
         } else {
            _snowmanx = new Team(this, _snowman);
            this.teams.put(_snowman, _snowmanx);
            this.updateScoreboardTeamAndPlayers(_snowmanx);
            return _snowmanx;
         }
      }
   }

   public void removeTeam(Team _snowman) {
      this.teams.remove(_snowman.getName());

      for (String _snowmanx : _snowman.getPlayerList()) {
         this.teamsByPlayer.remove(_snowmanx);
      }

      this.updateRemovedTeam(_snowman);
   }

   public boolean addPlayerToTeam(String playerName, Team _snowman) {
      if (playerName.length() > 40) {
         throw new IllegalArgumentException("The player name '" + playerName + "' is too long!");
      } else {
         if (this.getPlayerTeam(playerName) != null) {
            this.clearPlayerTeam(playerName);
         }

         this.teamsByPlayer.put(playerName, _snowman);
         return _snowman.getPlayerList().add(playerName);
      }
   }

   public boolean clearPlayerTeam(String _snowman) {
      Team _snowmanx = this.getPlayerTeam(_snowman);
      if (_snowmanx != null) {
         this.removePlayerFromTeam(_snowman, _snowmanx);
         return true;
      } else {
         return false;
      }
   }

   public void removePlayerFromTeam(String playerName, Team _snowman) {
      if (this.getPlayerTeam(playerName) != _snowman) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + _snowman.getName() + "'.");
      } else {
         this.teamsByPlayer.remove(playerName);
         _snowman.getPlayerList().remove(playerName);
      }
   }

   public Collection<String> getTeamNames() {
      return this.teams.keySet();
   }

   public Collection<Team> getTeams() {
      return this.teams.values();
   }

   @Nullable
   public Team getPlayerTeam(String _snowman) {
      return this.teamsByPlayer.get(_snowman);
   }

   public void updateObjective(ScoreboardObjective objective) {
   }

   public void updateExistingObjective(ScoreboardObjective objective) {
   }

   public void updateRemovedObjective(ScoreboardObjective objective) {
   }

   public void updateScore(ScoreboardPlayerScore score) {
   }

   public void updatePlayerScore(String playerName) {
   }

   public void updatePlayerScore(String playerName, ScoreboardObjective objective) {
   }

   public void updateScoreboardTeamAndPlayers(Team _snowman) {
   }

   public void updateScoreboardTeam(Team _snowman) {
   }

   public void updateRemovedTeam(Team _snowman) {
   }

   public static String getDisplaySlotName(int slotId) {
      switch (slotId) {
         case 0:
            return "list";
         case 1:
            return "sidebar";
         case 2:
            return "belowName";
         default:
            if (slotId >= 3 && slotId <= 18) {
               Formatting _snowman = Formatting.byColorIndex(slotId - 3);
               if (_snowman != null && _snowman != Formatting.RESET) {
                  return "sidebar.team." + _snowman.getName();
               }
            }

            return null;
      }
   }

   public static int getDisplaySlotId(String slotName) {
      if ("list".equalsIgnoreCase(slotName)) {
         return 0;
      } else if ("sidebar".equalsIgnoreCase(slotName)) {
         return 1;
      } else if ("belowName".equalsIgnoreCase(slotName)) {
         return 2;
      } else {
         if (slotName.startsWith("sidebar.team.")) {
            String _snowman = slotName.substring("sidebar.team.".length());
            Formatting _snowmanx = Formatting.byName(_snowman);
            if (_snowmanx != null && _snowmanx.getColorIndex() >= 0) {
               return _snowmanx.getColorIndex() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] getDisplaySlotNames() {
      if (displaySlotNames == null) {
         displaySlotNames = new String[19];

         for (int _snowman = 0; _snowman < 19; _snowman++) {
            displaySlotNames[_snowman] = getDisplaySlotName(_snowman);
         }
      }

      return displaySlotNames;
   }

   public void resetEntityScore(Entity _snowman) {
      if (_snowman != null && !(_snowman instanceof PlayerEntity) && !_snowman.isAlive()) {
         String _snowmanx = _snowman.getUuidAsString();
         this.resetPlayerScore(_snowmanx, null);
         this.clearPlayerTeam(_snowmanx);
      }
   }

   protected ListTag toTag() {
      ListTag _snowman = new ListTag();
      this.playerObjectives.values().stream().map(Map::values).forEach(_snowmanx -> _snowmanx.stream().filter(score -> score.getObjective() != null).forEach(score -> {
            CompoundTag _snowmanx = new CompoundTag();
            _snowmanx.putString("Name", score.getPlayerName());
            _snowmanx.putString("Objective", score.getObjective().getName());
            _snowmanx.putInt("Score", score.getScore());
            _snowmanx.putBoolean("Locked", score.isLocked());
            _snowman.add(_snowmanx);
         }));
      return _snowman;
   }

   protected void fromTag(ListTag _snowman) {
      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
         ScoreboardObjective _snowmanxxx = this.getObjective(_snowmanxx.getString("Objective"));
         String _snowmanxxxx = _snowmanxx.getString("Name");
         if (_snowmanxxxx.length() > 40) {
            _snowmanxxxx = _snowmanxxxx.substring(0, 40);
         }

         ScoreboardPlayerScore _snowmanxxxxx = this.getPlayerScore(_snowmanxxxx, _snowmanxxx);
         _snowmanxxxxx.setScore(_snowmanxx.getInt("Score"));
         if (_snowmanxx.contains("Locked")) {
            _snowmanxxxxx.setLocked(_snowmanxx.getBoolean("Locked"));
         }
      }
   }
}
