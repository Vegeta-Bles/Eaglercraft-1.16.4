package net.minecraft.scoreboard;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public abstract class AbstractTeam {
   public AbstractTeam() {
   }

   public boolean isEqual(@Nullable AbstractTeam arg) {
      return arg == null ? false : this == arg;
   }

   public abstract String getName();

   public abstract MutableText modifyText(Text arg);

   @Environment(EnvType.CLIENT)
   public abstract boolean shouldShowFriendlyInvisibles();

   public abstract boolean isFriendlyFireAllowed();

   @Environment(EnvType.CLIENT)
   public abstract AbstractTeam.VisibilityRule getNameTagVisibilityRule();

   @Environment(EnvType.CLIENT)
   public abstract Formatting getColor();

   public abstract Collection<String> getPlayerList();

   public abstract AbstractTeam.VisibilityRule getDeathMessageVisibilityRule();

   public abstract AbstractTeam.CollisionRule getCollisionRule();

   public static enum CollisionRule {
      ALWAYS("always", 0),
      NEVER("never", 1),
      PUSH_OTHER_TEAMS("pushOtherTeams", 2),
      PUSH_OWN_TEAM("pushOwnTeam", 3);

      private static final Map<String, AbstractTeam.CollisionRule> COLLISION_RULES = Arrays.stream(values())
         .collect(Collectors.toMap(arg -> arg.name, arg -> (AbstractTeam.CollisionRule)arg));
      public final String name;
      public final int value;

      @Nullable
      public static AbstractTeam.CollisionRule getRule(String name) {
         return COLLISION_RULES.get(name);
      }

      private CollisionRule(String name, int value) {
         this.name = name;
         this.value = value;
      }

      public Text getTranslationKey() {
         return new TranslatableText("team.collision." + this.name);
      }
   }

   public static enum VisibilityRule {
      ALWAYS("always", 0),
      NEVER("never", 1),
      HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
      HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

      private static final Map<String, AbstractTeam.VisibilityRule> VISIBILITY_RULES = Arrays.stream(values())
         .collect(Collectors.toMap(arg -> arg.name, arg -> (AbstractTeam.VisibilityRule)arg));
      public final String name;
      public final int value;

      @Nullable
      public static AbstractTeam.VisibilityRule getRule(String name) {
         return VISIBILITY_RULES.get(name);
      }

      private VisibilityRule(String name, int value) {
         this.name = name;
         this.value = value;
      }

      public Text getTranslationKey() {
         return new TranslatableText("team.visibility." + this.name);
      }
   }
}
