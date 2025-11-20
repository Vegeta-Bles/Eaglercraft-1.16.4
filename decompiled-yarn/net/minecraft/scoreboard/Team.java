package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;

public class Team extends AbstractTeam {
   private final Scoreboard scoreboard;
   private final String name;
   private final Set<String> playerList = Sets.newHashSet();
   private Text displayName;
   private Text prefix = LiteralText.EMPTY;
   private Text suffix = LiteralText.EMPTY;
   private boolean friendlyFire = true;
   private boolean showFriendlyInvisibles = true;
   private AbstractTeam.VisibilityRule nameTagVisibilityRule = AbstractTeam.VisibilityRule.ALWAYS;
   private AbstractTeam.VisibilityRule deathMessageVisibilityRule = AbstractTeam.VisibilityRule.ALWAYS;
   private Formatting color = Formatting.RESET;
   private AbstractTeam.CollisionRule collisionRule = AbstractTeam.CollisionRule.ALWAYS;
   private final Style field_24195;

   public Team(Scoreboard scoreboard, String name) {
      this.scoreboard = scoreboard;
      this.name = name;
      this.displayName = new LiteralText(name);
      this.field_24195 = Style.EMPTY.withInsertion(name).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText(name)));
   }

   @Override
   public String getName() {
      return this.name;
   }

   public Text getDisplayName() {
      return this.displayName;
   }

   public MutableText getFormattedName() {
      MutableText _snowman = Texts.bracketed(this.displayName.shallowCopy().fillStyle(this.field_24195));
      Formatting _snowmanx = this.getColor();
      if (_snowmanx != Formatting.RESET) {
         _snowman.formatted(_snowmanx);
      }

      return _snowman;
   }

   public void setDisplayName(Text _snowman) {
      if (_snowman == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.displayName = _snowman;
         this.scoreboard.updateScoreboardTeam(this);
      }
   }

   public void setPrefix(@Nullable Text _snowman) {
      this.prefix = _snowman == null ? LiteralText.EMPTY : _snowman;
      this.scoreboard.updateScoreboardTeam(this);
   }

   public Text getPrefix() {
      return this.prefix;
   }

   public void setSuffix(@Nullable Text _snowman) {
      this.suffix = _snowman == null ? LiteralText.EMPTY : _snowman;
      this.scoreboard.updateScoreboardTeam(this);
   }

   public Text getSuffix() {
      return this.suffix;
   }

   @Override
   public Collection<String> getPlayerList() {
      return this.playerList;
   }

   @Override
   public MutableText modifyText(Text _snowman) {
      MutableText _snowmanx = new LiteralText("").append(this.prefix).append(_snowman).append(this.suffix);
      Formatting _snowmanxx = this.getColor();
      if (_snowmanxx != Formatting.RESET) {
         _snowmanx.formatted(_snowmanxx);
      }

      return _snowmanx;
   }

   public static MutableText modifyText(@Nullable AbstractTeam _snowman, Text _snowman) {
      return _snowman == null ? _snowman.shallowCopy() : _snowman.modifyText(_snowman);
   }

   @Override
   public boolean isFriendlyFireAllowed() {
      return this.friendlyFire;
   }

   public void setFriendlyFireAllowed(boolean friendlyFire) {
      this.friendlyFire = friendlyFire;
      this.scoreboard.updateScoreboardTeam(this);
   }

   @Override
   public boolean shouldShowFriendlyInvisibles() {
      return this.showFriendlyInvisibles;
   }

   public void setShowFriendlyInvisibles(boolean showFriendlyInvisible) {
      this.showFriendlyInvisibles = showFriendlyInvisible;
      this.scoreboard.updateScoreboardTeam(this);
   }

   @Override
   public AbstractTeam.VisibilityRule getNameTagVisibilityRule() {
      return this.nameTagVisibilityRule;
   }

   @Override
   public AbstractTeam.VisibilityRule getDeathMessageVisibilityRule() {
      return this.deathMessageVisibilityRule;
   }

   public void setNameTagVisibilityRule(AbstractTeam.VisibilityRule _snowman) {
      this.nameTagVisibilityRule = _snowman;
      this.scoreboard.updateScoreboardTeam(this);
   }

   public void setDeathMessageVisibilityRule(AbstractTeam.VisibilityRule _snowman) {
      this.deathMessageVisibilityRule = _snowman;
      this.scoreboard.updateScoreboardTeam(this);
   }

   @Override
   public AbstractTeam.CollisionRule getCollisionRule() {
      return this.collisionRule;
   }

   public void setCollisionRule(AbstractTeam.CollisionRule _snowman) {
      this.collisionRule = _snowman;
      this.scoreboard.updateScoreboardTeam(this);
   }

   public int getFriendlyFlagsBitwise() {
      int _snowman = 0;
      if (this.isFriendlyFireAllowed()) {
         _snowman |= 1;
      }

      if (this.shouldShowFriendlyInvisibles()) {
         _snowman |= 2;
      }

      return _snowman;
   }

   public void setFriendlyFlagsBitwise(int _snowman) {
      this.setFriendlyFireAllowed((_snowman & 1) > 0);
      this.setShowFriendlyInvisibles((_snowman & 2) > 0);
   }

   public void setColor(Formatting color) {
      this.color = color;
      this.scoreboard.updateScoreboardTeam(this);
   }

   @Override
   public Formatting getColor() {
      return this.color;
   }
}
