/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
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
import net.minecraft.world.level.storage.SaveVersionInfo;
import org.apache.commons.lang3.StringUtils;

public class LevelSummary
implements Comparable<LevelSummary> {
    private final LevelInfo levelInfo;
    private final SaveVersionInfo field_25023;
    private final String name;
    private final boolean requiresConversion;
    private final boolean locked;
    private final File file;
    @Nullable
    private Text field_24191;

    public LevelSummary(LevelInfo levelInfo, SaveVersionInfo saveVersionInfo, String string, boolean bl, boolean bl2, File file) {
        this.levelInfo = levelInfo;
        this.field_25023 = saveVersionInfo;
        this.name = string;
        this.locked = bl2;
        this.file = file;
        this.requiresConversion = bl;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return StringUtils.isEmpty((CharSequence)this.levelInfo.getLevelName()) ? this.name : this.levelInfo.getLevelName();
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

    @Override
    public int compareTo(LevelSummary levelSummary) {
        if (this.field_25023.getLastPlayed() < levelSummary.field_25023.getLastPlayed()) {
            return 1;
        }
        if (this.field_25023.getLastPlayed() > levelSummary.field_25023.getLastPlayed()) {
            return -1;
        }
        return this.name.compareTo(levelSummary.name);
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
        if (ChatUtil.isEmpty(this.field_25023.getVersionName())) {
            return new TranslatableText("selectWorld.versionUnknown");
        }
        return new LiteralText(this.field_25023.getVersionName());
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
        MutableText mutableText;
        if (this.isLocked()) {
            return new TranslatableText("selectWorld.locked").formatted(Formatting.RED);
        }
        if (this.requiresConversion()) {
            return new TranslatableText("selectWorld.conversion");
        }
        MutableText mutableText2 = mutableText = this.isHardcore() ? new LiteralText("").append(new TranslatableText("gameMode.hardcore").formatted(Formatting.DARK_RED)) : new TranslatableText("gameMode." + this.getGameMode().getName());
        if (this.hasCheats()) {
            mutableText.append(", ").append(new TranslatableText("selectWorld.cheats"));
        }
        _snowman = this.getVersion();
        _snowman = new LiteralText(", ").append(new TranslatableText("selectWorld.version")).append(" ");
        if (this.isDifferentVersion()) {
            _snowman.append(_snowman.formatted(this.isFutureLevel() ? Formatting.RED : Formatting.ITALIC));
        } else {
            _snowman.append(_snowman);
        }
        mutableText.append(_snowman);
        return mutableText;
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((LevelSummary)object);
    }
}

