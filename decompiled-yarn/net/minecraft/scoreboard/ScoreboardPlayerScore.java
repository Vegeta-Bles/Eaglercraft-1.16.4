package net.minecraft.scoreboard;

import java.util.Comparator;
import javax.annotation.Nullable;

public class ScoreboardPlayerScore {
   public static final Comparator<ScoreboardPlayerScore> COMPARATOR = (_snowman, _snowmanx) -> {
      if (_snowman.getScore() > _snowmanx.getScore()) {
         return 1;
      } else {
         return _snowman.getScore() < _snowmanx.getScore() ? -1 : _snowmanx.getPlayerName().compareToIgnoreCase(_snowman.getPlayerName());
      }
   };
   private final Scoreboard scoreboard;
   @Nullable
   private final ScoreboardObjective objective;
   private final String playerName;
   private int score;
   private boolean locked;
   private boolean forceUpdate;

   public ScoreboardPlayerScore(Scoreboard scoreboard, ScoreboardObjective objective, String playerName) {
      this.scoreboard = scoreboard;
      this.objective = objective;
      this.playerName = playerName;
      this.locked = true;
      this.forceUpdate = true;
   }

   public void incrementScore(int _snowman) {
      if (this.objective.getCriterion().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.setScore(this.getScore() + _snowman);
      }
   }

   public void incrementScore() {
      this.incrementScore(1);
   }

   public int getScore() {
      return this.score;
   }

   public void clearScore() {
      this.setScore(0);
   }

   public void setScore(int score) {
      int _snowman = this.score;
      this.score = score;
      if (_snowman != score || this.forceUpdate) {
         this.forceUpdate = false;
         this.getScoreboard().updateScore(this);
      }
   }

   @Nullable
   public ScoreboardObjective getObjective() {
      return this.objective;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean locked) {
      this.locked = locked;
   }
}
