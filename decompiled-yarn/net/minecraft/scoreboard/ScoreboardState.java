package net.minecraft.scoreboard;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardState extends PersistentState {
   private static final Logger LOGGER = LogManager.getLogger();
   private Scoreboard scoreboard;
   private CompoundTag tag;

   public ScoreboardState() {
      super("scoreboard");
   }

   public void setScoreboard(Scoreboard _snowman) {
      this.scoreboard = _snowman;
      if (this.tag != null) {
         this.fromTag(this.tag);
      }
   }

   @Override
   public void fromTag(CompoundTag tag) {
      if (this.scoreboard == null) {
         this.tag = tag;
      } else {
         this.deserializeObjectives(tag.getList("Objectives", 10));
         this.scoreboard.fromTag(tag.getList("PlayerScores", 10));
         if (tag.contains("DisplaySlots", 10)) {
            this.deserializeDisplaySlots(tag.getCompound("DisplaySlots"));
         }

         if (tag.contains("Teams", 9)) {
            this.deserializeTeams(tag.getList("Teams", 10));
         }
      }
   }

   protected void deserializeTeams(ListTag _snowman) {
      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
         String _snowmanxxx = _snowmanxx.getString("Name");
         if (_snowmanxxx.length() > 16) {
            _snowmanxxx = _snowmanxxx.substring(0, 16);
         }

         Team _snowmanxxxx = this.scoreboard.addTeam(_snowmanxxx);
         Text _snowmanxxxxx = Text.Serializer.fromJson(_snowmanxx.getString("DisplayName"));
         if (_snowmanxxxxx != null) {
            _snowmanxxxx.setDisplayName(_snowmanxxxxx);
         }

         if (_snowmanxx.contains("TeamColor", 8)) {
            _snowmanxxxx.setColor(Formatting.byName(_snowmanxx.getString("TeamColor")));
         }

         if (_snowmanxx.contains("AllowFriendlyFire", 99)) {
            _snowmanxxxx.setFriendlyFireAllowed(_snowmanxx.getBoolean("AllowFriendlyFire"));
         }

         if (_snowmanxx.contains("SeeFriendlyInvisibles", 99)) {
            _snowmanxxxx.setShowFriendlyInvisibles(_snowmanxx.getBoolean("SeeFriendlyInvisibles"));
         }

         if (_snowmanxx.contains("MemberNamePrefix", 8)) {
            Text _snowmanxxxxxx = Text.Serializer.fromJson(_snowmanxx.getString("MemberNamePrefix"));
            if (_snowmanxxxxxx != null) {
               _snowmanxxxx.setPrefix(_snowmanxxxxxx);
            }
         }

         if (_snowmanxx.contains("MemberNameSuffix", 8)) {
            Text _snowmanxxxxxx = Text.Serializer.fromJson(_snowmanxx.getString("MemberNameSuffix"));
            if (_snowmanxxxxxx != null) {
               _snowmanxxxx.setSuffix(_snowmanxxxxxx);
            }
         }

         if (_snowmanxx.contains("NameTagVisibility", 8)) {
            AbstractTeam.VisibilityRule _snowmanxxxxxx = AbstractTeam.VisibilityRule.getRule(_snowmanxx.getString("NameTagVisibility"));
            if (_snowmanxxxxxx != null) {
               _snowmanxxxx.setNameTagVisibilityRule(_snowmanxxxxxx);
            }
         }

         if (_snowmanxx.contains("DeathMessageVisibility", 8)) {
            AbstractTeam.VisibilityRule _snowmanxxxxxx = AbstractTeam.VisibilityRule.getRule(_snowmanxx.getString("DeathMessageVisibility"));
            if (_snowmanxxxxxx != null) {
               _snowmanxxxx.setDeathMessageVisibilityRule(_snowmanxxxxxx);
            }
         }

         if (_snowmanxx.contains("CollisionRule", 8)) {
            AbstractTeam.CollisionRule _snowmanxxxxxx = AbstractTeam.CollisionRule.getRule(_snowmanxx.getString("CollisionRule"));
            if (_snowmanxxxxxx != null) {
               _snowmanxxxx.setCollisionRule(_snowmanxxxxxx);
            }
         }

         this.deserializeTeamPlayers(_snowmanxxxx, _snowmanxx.getList("Players", 8));
      }
   }

   protected void deserializeTeamPlayers(Team team, ListTag _snowman) {
      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         this.scoreboard.addPlayerToTeam(_snowman.getString(_snowmanx), team);
      }
   }

   protected void deserializeDisplaySlots(CompoundTag _snowman) {
      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         if (_snowman.contains("slot_" + _snowmanx, 8)) {
            String _snowmanxx = _snowman.getString("slot_" + _snowmanx);
            ScoreboardObjective _snowmanxxx = this.scoreboard.getNullableObjective(_snowmanxx);
            this.scoreboard.setObjectiveSlot(_snowmanx, _snowmanxxx);
         }
      }
   }

   protected void deserializeObjectives(ListTag _snowman) {
      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
         ScoreboardCriterion.createStatCriterion(_snowmanxx.getString("CriteriaName")).ifPresent(_snowmanxxxx -> {
            String _snowmanxx = _snowman.getString("Name");
            if (_snowmanxx.length() > 16) {
               _snowmanxx = _snowmanxx.substring(0, 16);
            }

            Text _snowmanxxxx = Text.Serializer.fromJson(_snowman.getString("DisplayName"));
            ScoreboardCriterion.RenderType _snowmanxxxxx = ScoreboardCriterion.RenderType.getType(_snowman.getString("RenderType"));
            this.scoreboard.addObjective(_snowmanxx, _snowmanxxxx, _snowmanxxxx, _snowmanxxxxx);
         });
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      if (this.scoreboard == null) {
         LOGGER.warn("Tried to save scoreboard without having a scoreboard...");
         return tag;
      } else {
         tag.put("Objectives", this.serializeObjectives());
         tag.put("PlayerScores", this.scoreboard.toTag());
         tag.put("Teams", this.serializeTeams());
         this.serializeSlots(tag);
         return tag;
      }
   }

   protected ListTag serializeTeams() {
      ListTag _snowman = new ListTag();

      for (Team _snowmanx : this.scoreboard.getTeams()) {
         CompoundTag _snowmanxx = new CompoundTag();
         _snowmanxx.putString("Name", _snowmanx.getName());
         _snowmanxx.putString("DisplayName", Text.Serializer.toJson(_snowmanx.getDisplayName()));
         if (_snowmanx.getColor().getColorIndex() >= 0) {
            _snowmanxx.putString("TeamColor", _snowmanx.getColor().getName());
         }

         _snowmanxx.putBoolean("AllowFriendlyFire", _snowmanx.isFriendlyFireAllowed());
         _snowmanxx.putBoolean("SeeFriendlyInvisibles", _snowmanx.shouldShowFriendlyInvisibles());
         _snowmanxx.putString("MemberNamePrefix", Text.Serializer.toJson(_snowmanx.getPrefix()));
         _snowmanxx.putString("MemberNameSuffix", Text.Serializer.toJson(_snowmanx.getSuffix()));
         _snowmanxx.putString("NameTagVisibility", _snowmanx.getNameTagVisibilityRule().name);
         _snowmanxx.putString("DeathMessageVisibility", _snowmanx.getDeathMessageVisibilityRule().name);
         _snowmanxx.putString("CollisionRule", _snowmanx.getCollisionRule().name);
         ListTag _snowmanxxx = new ListTag();

         for (String _snowmanxxxx : _snowmanx.getPlayerList()) {
            _snowmanxxx.add(StringTag.of(_snowmanxxxx));
         }

         _snowmanxx.put("Players", _snowmanxxx);
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   protected void serializeSlots(CompoundTag _snowman) {
      CompoundTag _snowmanx = new CompoundTag();
      boolean _snowmanxx = false;

      for (int _snowmanxxx = 0; _snowmanxxx < 19; _snowmanxxx++) {
         ScoreboardObjective _snowmanxxxx = this.scoreboard.getObjectiveForSlot(_snowmanxxx);
         if (_snowmanxxxx != null) {
            _snowmanx.putString("slot_" + _snowmanxxx, _snowmanxxxx.getName());
            _snowmanxx = true;
         }
      }

      if (_snowmanxx) {
         _snowman.put("DisplaySlots", _snowmanx);
      }
   }

   protected ListTag serializeObjectives() {
      ListTag _snowman = new ListTag();

      for (ScoreboardObjective _snowmanx : this.scoreboard.getObjectives()) {
         if (_snowmanx.getCriterion() != null) {
            CompoundTag _snowmanxx = new CompoundTag();
            _snowmanxx.putString("Name", _snowmanx.getName());
            _snowmanxx.putString("CriteriaName", _snowmanx.getCriterion().getName());
            _snowmanxx.putString("DisplayName", Text.Serializer.toJson(_snowmanx.getDisplayName()));
            _snowmanxx.putString("RenderType", _snowmanx.getRenderType().getName());
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }
}
