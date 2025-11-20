/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.scoreboard;

import java.util.Collection;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.PersistentState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardState
extends PersistentState {
    private static final Logger LOGGER = LogManager.getLogger();
    private Scoreboard scoreboard;
    private CompoundTag tag;

    public ScoreboardState() {
        super("scoreboard");
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        if (this.tag != null) {
            this.fromTag(this.tag);
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        if (this.scoreboard == null) {
            this.tag = tag;
            return;
        }
        this.deserializeObjectives(tag.getList("Objectives", 10));
        this.scoreboard.fromTag(tag.getList("PlayerScores", 10));
        if (tag.contains("DisplaySlots", 10)) {
            this.deserializeDisplaySlots(tag.getCompound("DisplaySlots"));
        }
        if (tag.contains("Teams", 9)) {
            this.deserializeTeams(tag.getList("Teams", 10));
        }
    }

    protected void deserializeTeams(ListTag listTag) {
        for (int i = 0; i < listTag.size(); ++i) {
            AbstractTeam.CollisionRule collisionRule;
            AbstractTeam.VisibilityRule visibilityRule;
            AbstractTeam.VisibilityRule visibilityRule2;
            MutableText mutableText;
            MutableText mutableText2;
            CompoundTag compoundTag = listTag.getCompound(i);
            String _snowman2 = compoundTag.getString("Name");
            if (_snowman2.length() > 16) {
                _snowman2 = _snowman2.substring(0, 16);
            }
            Team _snowman3 = this.scoreboard.addTeam(_snowman2);
            MutableText _snowman4 = Text.Serializer.fromJson(compoundTag.getString("DisplayName"));
            if (_snowman4 != null) {
                _snowman3.setDisplayName(_snowman4);
            }
            if (compoundTag.contains("TeamColor", 8)) {
                _snowman3.setColor(Formatting.byName(compoundTag.getString("TeamColor")));
            }
            if (compoundTag.contains("AllowFriendlyFire", 99)) {
                _snowman3.setFriendlyFireAllowed(compoundTag.getBoolean("AllowFriendlyFire"));
            }
            if (compoundTag.contains("SeeFriendlyInvisibles", 99)) {
                _snowman3.setShowFriendlyInvisibles(compoundTag.getBoolean("SeeFriendlyInvisibles"));
            }
            if (compoundTag.contains("MemberNamePrefix", 8) && (mutableText2 = Text.Serializer.fromJson(compoundTag.getString("MemberNamePrefix"))) != null) {
                _snowman3.setPrefix(mutableText2);
            }
            if (compoundTag.contains("MemberNameSuffix", 8) && (mutableText = Text.Serializer.fromJson(compoundTag.getString("MemberNameSuffix"))) != null) {
                _snowman3.setSuffix(mutableText);
            }
            if (compoundTag.contains("NameTagVisibility", 8) && (visibilityRule2 = AbstractTeam.VisibilityRule.getRule(compoundTag.getString("NameTagVisibility"))) != null) {
                _snowman3.setNameTagVisibilityRule(visibilityRule2);
            }
            if (compoundTag.contains("DeathMessageVisibility", 8) && (visibilityRule = AbstractTeam.VisibilityRule.getRule(compoundTag.getString("DeathMessageVisibility"))) != null) {
                _snowman3.setDeathMessageVisibilityRule(visibilityRule);
            }
            if (compoundTag.contains("CollisionRule", 8) && (collisionRule = AbstractTeam.CollisionRule.getRule(compoundTag.getString("CollisionRule"))) != null) {
                _snowman3.setCollisionRule(collisionRule);
            }
            this.deserializeTeamPlayers(_snowman3, compoundTag.getList("Players", 8));
        }
    }

    protected void deserializeTeamPlayers(Team team, ListTag listTag) {
        for (int i = 0; i < listTag.size(); ++i) {
            this.scoreboard.addPlayerToTeam(listTag.getString(i), team);
        }
    }

    protected void deserializeDisplaySlots(CompoundTag compoundTag) {
        for (int i = 0; i < 19; ++i) {
            if (!compoundTag.contains("slot_" + i, 8)) continue;
            String string = compoundTag.getString("slot_" + i);
            ScoreboardObjective _snowman2 = this.scoreboard.getNullableObjective(string);
            this.scoreboard.setObjectiveSlot(i, _snowman2);
        }
    }

    protected void deserializeObjectives(ListTag listTag) {
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            ScoreboardCriterion.createStatCriterion(compoundTag.getString("CriteriaName")).ifPresent(scoreboardCriterion -> {
                String string = compoundTag.getString("Name");
                if (string.length() > 16) {
                    string = string.substring(0, 16);
                }
                MutableText _snowman2 = Text.Serializer.fromJson(compoundTag.getString("DisplayName"));
                ScoreboardCriterion.RenderType _snowman3 = ScoreboardCriterion.RenderType.getType(compoundTag.getString("RenderType"));
                this.scoreboard.addObjective(string, (ScoreboardCriterion)scoreboardCriterion, _snowman2, _snowman3);
            });
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if (this.scoreboard == null) {
            LOGGER.warn("Tried to save scoreboard without having a scoreboard...");
            return tag;
        }
        tag.put("Objectives", this.serializeObjectives());
        tag.put("PlayerScores", this.scoreboard.toTag());
        tag.put("Teams", this.serializeTeams());
        this.serializeSlots(tag);
        return tag;
    }

    protected ListTag serializeTeams() {
        ListTag listTag = new ListTag();
        Collection<Team> _snowman2 = this.scoreboard.getTeams();
        for (Team team : _snowman2) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("Name", team.getName());
            compoundTag.putString("DisplayName", Text.Serializer.toJson(team.getDisplayName()));
            if (team.getColor().getColorIndex() >= 0) {
                compoundTag.putString("TeamColor", team.getColor().getName());
            }
            compoundTag.putBoolean("AllowFriendlyFire", team.isFriendlyFireAllowed());
            compoundTag.putBoolean("SeeFriendlyInvisibles", team.shouldShowFriendlyInvisibles());
            compoundTag.putString("MemberNamePrefix", Text.Serializer.toJson(team.getPrefix()));
            compoundTag.putString("MemberNameSuffix", Text.Serializer.toJson(team.getSuffix()));
            compoundTag.putString("NameTagVisibility", team.getNameTagVisibilityRule().name);
            compoundTag.putString("DeathMessageVisibility", team.getDeathMessageVisibilityRule().name);
            compoundTag.putString("CollisionRule", team.getCollisionRule().name);
            ListTag _snowman3 = new ListTag();
            for (String string : team.getPlayerList()) {
                _snowman3.add(StringTag.of(string));
            }
            compoundTag.put("Players", _snowman3);
            listTag.add(compoundTag);
        }
        return listTag;
    }

    protected void serializeSlots(CompoundTag compoundTag) {
        _snowman = new CompoundTag();
        boolean _snowman2 = false;
        for (int i = 0; i < 19; ++i) {
            ScoreboardObjective scoreboardObjective = this.scoreboard.getObjectiveForSlot(i);
            if (scoreboardObjective == null) continue;
            _snowman.putString("slot_" + i, scoreboardObjective.getName());
            _snowman2 = true;
        }
        if (_snowman2) {
            compoundTag.put("DisplaySlots", _snowman);
        }
    }

    protected ListTag serializeObjectives() {
        ListTag listTag = new ListTag();
        Collection<ScoreboardObjective> _snowman2 = this.scoreboard.getObjectives();
        for (ScoreboardObjective scoreboardObjective : _snowman2) {
            if (scoreboardObjective.getCriterion() == null) continue;
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("Name", scoreboardObjective.getName());
            compoundTag.putString("CriteriaName", scoreboardObjective.getCriterion().getName());
            compoundTag.putString("DisplayName", Text.Serializer.toJson(scoreboardObjective.getDisplayName()));
            compoundTag.putString("RenderType", scoreboardObjective.getRenderType().getName());
            listTag.add(compoundTag);
        }
        return listTag;
    }
}

