package net.minecraft.world.level.storage;

import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;
import net.minecraft.world.level.LevelInfo;
import org.apache.commons.lang3.StringUtils;

public class LevelSummary implements Comparable<LevelSummary> {
   private final LevelInfo levelInfo;
   private final SaveVersionInfo field_25023;
   private final String name;
   private final boolean requiresConversion;
   private final boolean locked;
   private final File file;
   @Nullable
   private Text field_24191;

   public LevelSummary(LevelInfo _snowman, SaveVersionInfo _snowman, String _snowman, boolean _snowman, boolean _snowman, File _snowman) {
      this.levelInfo = _snowman;
      this.field_25023 = _snowman;
      this.name = _snowman;
      this.locked = _snowman;
      this.file = _snowman;
      this.requiresConversion = _snowman;
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return StringUtils.isEmpty(this.levelInfo.getLevelName()) ? this.name : this.levelInfo.getLevelName();
   }

   public File getFile() {
      return this.file;
   }

   public boolean requiresConversion() {
      return this.requiresConversion;
   }

   public long getLastPlayed() {
      return this.field_25023.getLastPlayed();
   }

   public int compareTo(LevelSummary _snowman) {
      if (this.field_25023.getLastPlayed() < _snowman.field_25023.getLastPlayed()) {
         return 1;
      } else {
         return this.field_25023.getLastPlayed() > _snowman.field_25023.getLastPlayed() ? -1 : this.name.compareTo(_snowman.name);
      }
   }

   public GameMode getGameMode() {
      return this.levelInfo.getGameMode();
   }

   public boolean isHardcore() {
      return this.levelInfo.isHardcore();
   }

   public boolean hasCheats() {
      return this.levelInfo.areCommandsAllowed();
   }

   public MutableText getVersion() {
      return (MutableText)(ChatUtil.isEmpty(this.field_25023.getVersionName())
         ? new TranslatableText("selectWorld.versionUnknown")
         : new LiteralText(this.field_25023.getVersionName()));
   }

   public SaveVersionInfo method_29586() {
      return this.field_25023;
   }

   public boolean isDifferentVersion() {
      return this.isFutureLevel() || !SharedConstants.getGameVersion().isStable() && !this.field_25023.isStable() || this.isOutdatedLevel();
   }

   public boolean isFutureLevel() {
      return this.field_25023.getVersionId() > SharedConstants.getGameVersion().getWorldVersion();
   }

   public boolean isOutdatedLevel() {
      return this.field_25023.getVersionId() < SharedConstants.getGameVersion().getWorldVersion();
   }

   public boolean isLocked() {
      return this.locked;
   }

   public Text method_27429() {
      if (this.field_24191 == null) {
         this.field_24191 = this.method_27430();
      }

      return this.field_24191;
   }

   private Text method_27430() {
      if (this.isLocked()) {
         return new TranslatableText("selectWorld.locked").formatted(Formatting.RED);
      } else if (this.requiresConversion()) {
         return new TranslatableText("selectWorld.conversion");
      } else {
         MutableText _snowman = (MutableText)(this.isHardcore()
            ? new LiteralText("").append(new TranslatableText("gameMode.hardcore").formatted(Formatting.DARK_RED))
            : new TranslatableText("gameMode." + this.getGameMode().getName()));
         if (this.hasCheats()) {
            _snowman.append(", ").append(new TranslatableText("selectWorld.cheats"));
         }

         MutableText _snowmanx = this.getVersion();
         MutableText _snowmanxx = new LiteralText(", ").append(new TranslatableText("selectWorld.version")).append(" ");
         if (this.isDifferentVersion()) {
            _snowmanxx.append(_snowmanx.formatted(this.isFutureLevel() ? Formatting.RED : Formatting.ITALIC));
         } else {
            _snowmanxx.append(_snowmanx);
         }

         _snowman.append(_snowmanxx);
         return _snowman;
      }
   }
}
