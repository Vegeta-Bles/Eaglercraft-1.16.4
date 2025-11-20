/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSaveHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final File playerDataDir;
    protected final DataFixer dataFixer;

    public WorldSaveHandler(LevelStorage.Session session, DataFixer dataFixer) {
        this.dataFixer = dataFixer;
        this.playerDataDir = session.getDirectory(WorldSavePath.PLAYERDATA).toFile();
        this.playerDataDir.mkdirs();
    }

    public void savePlayerData(PlayerEntity playerEntity) {
        try {
            CompoundTag compoundTag = playerEntity.toTag(new CompoundTag());
            File _snowman2 = File.createTempFile(playerEntity.getUuidAsString() + "-", ".dat", this.playerDataDir);
            NbtIo.writeCompressed(compoundTag, _snowman2);
            File _snowman3 = new File(this.playerDataDir, playerEntity.getUuidAsString() + ".dat");
            File _snowman4 = new File(this.playerDataDir, playerEntity.getUuidAsString() + ".dat_old");
            Util.backupAndReplace(_snowman3, _snowman2, _snowman4);
        }
        catch (Exception exception) {
            LOGGER.warn("Failed to save player data for {}", (Object)playerEntity.getName().getString());
        }
    }

    @Nullable
    public CompoundTag loadPlayerData(PlayerEntity playerEntity) {
        CompoundTag compoundTag = null;
        try {
            File file = new File(this.playerDataDir, playerEntity.getUuidAsString() + ".dat");
            if (file.exists() && file.isFile()) {
                compoundTag = NbtIo.readCompressed(file);
            }
        }
        catch (Exception exception) {
            LOGGER.warn("Failed to load player data for {}", (Object)playerEntity.getName().getString());
        }
        if (compoundTag != null) {
            int n = compoundTag.contains("DataVersion", 3) ? compoundTag.getInt("DataVersion") : -1;
            playerEntity.fromTag(NbtHelper.update(this.dataFixer, DataFixTypes.PLAYER, compoundTag, n));
        }
        return compoundTag;
    }

    public String[] getSavedPlayerIds() {
        String[] stringArray = this.playerDataDir.list();
        if (stringArray == null) {
            stringArray = new String[]{};
        }
        for (int i = 0; i < stringArray.length; ++i) {
            if (!stringArray[i].endsWith(".dat")) continue;
            stringArray[i] = stringArray[i].substring(0, stringArray[i].length() - 4);
        }
        return stringArray;
    }
}

