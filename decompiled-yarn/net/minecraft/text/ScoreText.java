package net.minecraft.text;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

public class ScoreText extends BaseText implements ParsableText {
   private final String name;
   @Nullable
   private final EntitySelector selector;
   private final String objective;

   @Nullable
   private static EntitySelector parseEntitySelector(String name) {
      try {
         return new EntitySelectorReader(new StringReader(name)).read();
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public ScoreText(String name, String objective) {
      this(name, parseEntitySelector(name), objective);
   }

   private ScoreText(String name, @Nullable EntitySelector selector, String objective) {
      this.name = name;
      this.selector = selector;
      this.objective = objective;
   }

   public String getName() {
      return this.name;
   }

   public String getObjective() {
      return this.objective;
   }

   private String getPlayerName(ServerCommandSource source) throws CommandSyntaxException {
      if (this.selector != null) {
         List<? extends Entity> _snowman = this.selector.getEntities(source);
         if (!_snowman.isEmpty()) {
            if (_snowman.size() != 1) {
               throw EntityArgumentType.TOO_MANY_ENTITIES_EXCEPTION.create();
            }

            return _snowman.get(0).getEntityName();
         }
      }

      return this.name;
   }

   private String getScore(String playerName, ServerCommandSource source) {
      MinecraftServer _snowman = source.getMinecraftServer();
      if (_snowman != null) {
         Scoreboard _snowmanx = _snowman.getScoreboard();
         ScoreboardObjective _snowmanxx = _snowmanx.getNullableObjective(this.objective);
         if (_snowmanx.playerHasObjective(playerName, _snowmanxx)) {
            ScoreboardPlayerScore _snowmanxxx = _snowmanx.getPlayerScore(playerName, _snowmanxx);
            return Integer.toString(_snowmanxxx.getScore());
         }
      }

      return "";
   }

   public ScoreText copy() {
      return new ScoreText(this.name, this.selector, this.objective);
   }

   @Override
   public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
      if (source == null) {
         return new LiteralText("");
      } else {
         String _snowman = this.getPlayerName(source);
         String _snowmanx = sender != null && _snowman.equals("*") ? sender.getEntityName() : _snowman;
         return new LiteralText(this.getScore(_snowmanx, source));
      }
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof ScoreText)) {
         return false;
      } else {
         ScoreText _snowmanx = (ScoreText)_snowman;
         return this.name.equals(_snowmanx.name) && this.objective.equals(_snowmanx.objective) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='"
         + this.name
         + '\''
         + "objective='"
         + this.objective
         + '\''
         + ", siblings="
         + this.siblings
         + ", style="
         + this.getStyle()
         + '}';
   }
}
