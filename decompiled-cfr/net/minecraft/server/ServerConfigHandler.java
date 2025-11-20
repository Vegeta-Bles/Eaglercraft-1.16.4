/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.mojang.authlib.Agent
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.ProfileLookupCallback
 *  com.mojang.authlib.yggdrasil.ProfileNotFoundException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.BanEntry;
import net.minecraft.server.BannedIpEntry;
import net.minecraft.server.BannedIpList;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.OperatorList;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.Whitelist;
import net.minecraft.server.WhitelistEntry;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.WorldSavePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerConfigHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final File BANNED_IPS_FILE = new File("banned-ips.txt");
    public static final File BANNED_PLAYERS_FILE = new File("banned-players.txt");
    public static final File OPERATORS_FILE = new File("ops.txt");
    public static final File WHITE_LIST_FILE = new File("white-list.txt");

    static List<String> processSimpleListFile(File file, Map<String, String[]> valueMap) throws IOException {
        List list = Files.readLines((File)file, (Charset)StandardCharsets.UTF_8);
        for (String string : list) {
            if ((string = string.trim()).startsWith("#") || string.length() < 1) continue;
            String[] stringArray = string.split("\\|");
            valueMap.put(stringArray[0].toLowerCase(Locale.ROOT), stringArray);
        }
        return list;
    }

    private static void lookupProfile(MinecraftServer server, Collection<String> bannedPlayers, ProfileLookupCallback callback) {
        String[] stringArray = (String[])bannedPlayers.stream().filter(string -> !ChatUtil.isEmpty(string)).toArray(String[]::new);
        if (server.isOnlineMode()) {
            server.getGameProfileRepo().findProfilesByNames(stringArray, Agent.MINECRAFT, callback);
        } else {
            for (String string2 : stringArray) {
                UUID uUID = PlayerEntity.getUuidFromProfile(new GameProfile(null, string2));
                GameProfile _snowman2 = new GameProfile(uUID, string2);
                callback.onProfileLookupSucceeded(_snowman2);
            }
        }
    }

    public static boolean convertBannedPlayers(final MinecraftServer server) {
        final BannedPlayerList bannedPlayerList = new BannedPlayerList(PlayerManager.BANNED_PLAYERS_FILE);
        if (BANNED_PLAYERS_FILE.exists() && BANNED_PLAYERS_FILE.isFile()) {
            if (bannedPlayerList.getFile().exists()) {
                try {
                    bannedPlayerList.load();
                }
                catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)bannedPlayerList.getFile().getName(), (Object)iOException);
                }
            }
            try {
                final HashMap hashMap = Maps.newHashMap();
                ServerConfigHandler.processSimpleListFile(BANNED_PLAYERS_FILE, hashMap);
                ProfileLookupCallback _snowman2 = new ProfileLookupCallback(){

                    public void onProfileLookupSucceeded(GameProfile profile) {
                        server.getUserCache().add(profile);
                        String[] stringArray = (String[])hashMap.get(profile.getName().toLowerCase(Locale.ROOT));
                        if (stringArray == null) {
                            LOGGER.warn("Could not convert user banlist entry for {}", (Object)profile.getName());
                            throw new ServerConfigException("Profile not in the conversionlist");
                        }
                        Date _snowman2 = stringArray.length > 1 ? ServerConfigHandler.parseDate(stringArray[1], null) : null;
                        String _snowman3 = stringArray.length > 2 ? stringArray[2] : null;
                        Date _snowman4 = stringArray.length > 3 ? ServerConfigHandler.parseDate(stringArray[3], null) : null;
                        String _snowman5 = stringArray.length > 4 ? stringArray[4] : null;
                        bannedPlayerList.add(new BannedPlayerEntry(profile, _snowman2, _snowman3, _snowman4, _snowman5));
                    }

                    public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                        LOGGER.warn("Could not lookup user banlist entry for {}", (Object)profile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                        }
                    }
                };
                ServerConfigHandler.lookupProfile(server, hashMap.keySet(), _snowman2);
                bannedPlayerList.save();
                ServerConfigHandler.markFileConverted(BANNED_PLAYERS_FILE);
            }
            catch (IOException iOException) {
                LOGGER.warn("Could not read old user banlist to convert it!", (Throwable)iOException);
                return false;
            }
            catch (ServerConfigException serverConfigException) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)serverConfigException);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean convertBannedIps(MinecraftServer server) {
        BannedIpList bannedIpList = new BannedIpList(PlayerManager.BANNED_IPS_FILE);
        if (BANNED_IPS_FILE.exists() && BANNED_IPS_FILE.isFile()) {
            if (bannedIpList.getFile().exists()) {
                try {
                    bannedIpList.load();
                }
                catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)bannedIpList.getFile().getName(), (Object)iOException);
                }
            }
            try {
                HashMap hashMap = Maps.newHashMap();
                ServerConfigHandler.processSimpleListFile(BANNED_IPS_FILE, hashMap);
                for (String string : hashMap.keySet()) {
                    String[] stringArray = (String[])hashMap.get(string);
                    Date _snowman2 = stringArray.length > 1 ? ServerConfigHandler.parseDate(stringArray[1], null) : null;
                    String _snowman3 = stringArray.length > 2 ? stringArray[2] : null;
                    Date _snowman4 = stringArray.length > 3 ? ServerConfigHandler.parseDate(stringArray[3], null) : null;
                    String _snowman5 = stringArray.length > 4 ? stringArray[4] : null;
                    bannedIpList.add(new BannedIpEntry(string, _snowman2, _snowman3, _snowman4, _snowman5));
                }
                bannedIpList.save();
                ServerConfigHandler.markFileConverted(BANNED_IPS_FILE);
            }
            catch (IOException iOException) {
                LOGGER.warn("Could not parse old ip banlist to convert it!", (Throwable)iOException);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean convertOperators(final MinecraftServer server) {
        final OperatorList operatorList = new OperatorList(PlayerManager.OPERATORS_FILE);
        if (OPERATORS_FILE.exists() && OPERATORS_FILE.isFile()) {
            if (operatorList.getFile().exists()) {
                try {
                    operatorList.load();
                }
                catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)operatorList.getFile().getName(), (Object)iOException);
                }
            }
            try {
                List list = Files.readLines((File)OPERATORS_FILE, (Charset)StandardCharsets.UTF_8);
                ProfileLookupCallback _snowman2 = new ProfileLookupCallback(){

                    public void onProfileLookupSucceeded(GameProfile profile) {
                        server.getUserCache().add(profile);
                        operatorList.add(new OperatorEntry(profile, server.getOpPermissionLevel(), false));
                    }

                    public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                        LOGGER.warn("Could not lookup oplist entry for {}", (Object)profile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                        }
                    }
                };
                ServerConfigHandler.lookupProfile(server, list, _snowman2);
                operatorList.save();
                ServerConfigHandler.markFileConverted(OPERATORS_FILE);
            }
            catch (IOException iOException) {
                LOGGER.warn("Could not read old oplist to convert it!", (Throwable)iOException);
                return false;
            }
            catch (ServerConfigException serverConfigException) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)serverConfigException);
                return false;
            }
            return true;
        }
        return true;
    }

    public static boolean convertWhitelist(final MinecraftServer server) {
        final Whitelist whitelist = new Whitelist(PlayerManager.WHITELIST_FILE);
        if (WHITE_LIST_FILE.exists() && WHITE_LIST_FILE.isFile()) {
            if (whitelist.getFile().exists()) {
                try {
                    whitelist.load();
                }
                catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)whitelist.getFile().getName(), (Object)iOException);
                }
            }
            try {
                List list = Files.readLines((File)WHITE_LIST_FILE, (Charset)StandardCharsets.UTF_8);
                ProfileLookupCallback _snowman2 = new ProfileLookupCallback(){

                    public void onProfileLookupSucceeded(GameProfile profile) {
                        server.getUserCache().add(profile);
                        whitelist.add(new WhitelistEntry(profile));
                    }

                    public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                        LOGGER.warn("Could not lookup user whitelist entry for {}", (Object)profile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                        }
                    }
                };
                ServerConfigHandler.lookupProfile(server, list, _snowman2);
                whitelist.save();
                ServerConfigHandler.markFileConverted(WHITE_LIST_FILE);
            }
            catch (IOException iOException) {
                LOGGER.warn("Could not read old whitelist to convert it!", (Throwable)iOException);
                return false;
            }
            catch (ServerConfigException serverConfigException) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)serverConfigException);
                return false;
            }
            return true;
        }
        return true;
    }

    @Nullable
    public static UUID getPlayerUuidByName(final MinecraftServer server, String name) {
        if (ChatUtil.isEmpty(name) || name.length() > 16) {
            try {
                return UUID.fromString(name);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
        GameProfile gameProfile = server.getUserCache().findByName(name);
        if (gameProfile != null && gameProfile.getId() != null) {
            return gameProfile.getId();
        }
        if (server.isSinglePlayer() || !server.isOnlineMode()) {
            return PlayerEntity.getUuidFromProfile(new GameProfile(null, name));
        }
        final ArrayList _snowman2 = Lists.newArrayList();
        ProfileLookupCallback _snowman3 = new ProfileLookupCallback(){

            public void onProfileLookupSucceeded(GameProfile profile) {
                server.getUserCache().add(profile);
                _snowman2.add(profile);
            }

            public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                LOGGER.warn("Could not lookup user whitelist entry for {}", (Object)profile.getName(), (Object)exception);
            }
        };
        ServerConfigHandler.lookupProfile(server, Lists.newArrayList((Object[])new String[]{name}), _snowman3);
        if (!_snowman2.isEmpty() && ((GameProfile)_snowman2.get(0)).getId() != null) {
            return ((GameProfile)_snowman2.get(0)).getId();
        }
        return null;
    }

    public static boolean convertPlayerFiles(final MinecraftDedicatedServer minecraftServer) {
        final File file = ServerConfigHandler.getLevelPlayersFolder(minecraftServer);
        _snowman = new File(file.getParentFile(), "playerdata");
        _snowman = new File(file.getParentFile(), "unknownplayers");
        if (!file.exists() || !file.isDirectory()) {
            return true;
        }
        File[] _snowman2 = file.listFiles();
        ArrayList _snowman3 = Lists.newArrayList();
        for (File file2 : _snowman2) {
            String string = file2.getName();
            if (!string.toLowerCase(Locale.ROOT).endsWith(".dat") || (_snowman = string.substring(0, string.length() - ".dat".length())).isEmpty()) continue;
            _snowman3.add(_snowman);
        }
        try {
            Object[] objectArray = _snowman3.toArray(new String[_snowman3.size()]);
            ProfileLookupCallback _snowman4 = new ProfileLookupCallback((String[])objectArray){
                final /* synthetic */ String[] field_14340;
                {
                    this.field_14340 = stringArray;
                }

                public void onProfileLookupSucceeded(GameProfile profile) {
                    minecraftServer.getUserCache().add(profile);
                    UUID uUID = profile.getId();
                    if (uUID == null) {
                        throw new ServerConfigException("Missing UUID for user profile " + profile.getName());
                    }
                    this.convertPlayerFile(_snowman, this.getPlayerFileName(profile), uUID.toString());
                }

                public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                    LOGGER.warn("Could not lookup user uuid for {}", (Object)profile.getName(), (Object)exception);
                    if (!(exception instanceof ProfileNotFoundException)) {
                        throw new ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                    }
                    String string = this.getPlayerFileName(profile);
                    this.convertPlayerFile(_snowman, string, string);
                }

                private void convertPlayerFile(File playerDataFolder, String fileName, String uuid) {
                    File file2 = new File(file, fileName + ".dat");
                    _snowman = new File(playerDataFolder, uuid + ".dat");
                    ServerConfigHandler.createDirectory(playerDataFolder);
                    if (!file2.renameTo(_snowman)) {
                        throw new ServerConfigException("Could not convert file for " + fileName);
                    }
                }

                private String getPlayerFileName(GameProfile profile) {
                    String string = null;
                    for (String string2 : this.field_14340) {
                        if (string2 == null || !string2.equalsIgnoreCase(profile.getName())) continue;
                        string = string2;
                        break;
                    }
                    if (string == null) {
                        throw new ServerConfigException("Could not find the filename for " + profile.getName() + " anymore");
                    }
                    return string;
                }
            };
            ServerConfigHandler.lookupProfile(minecraftServer, Lists.newArrayList((Object[])objectArray), _snowman4);
        }
        catch (ServerConfigException serverConfigException) {
            LOGGER.error("Conversion failed, please try again later", (Throwable)serverConfigException);
            return false;
        }
        return true;
    }

    private static void createDirectory(File directory) {
        if (directory.exists()) {
            if (directory.isDirectory()) {
                return;
            }
            throw new ServerConfigException("Can't create directory " + directory.getName() + " in world save directory.");
        }
        if (!directory.mkdirs()) {
            throw new ServerConfigException("Can't create directory " + directory.getName() + " in world save directory.");
        }
    }

    public static boolean checkSuccess(MinecraftServer server) {
        boolean bl = ServerConfigHandler.checkListConversionSuccess();
        bl = bl && ServerConfigHandler.checkPlayerConversionSuccess(server);
        return bl;
    }

    private static boolean checkListConversionSuccess() {
        boolean bl = false;
        if (BANNED_PLAYERS_FILE.exists() && BANNED_PLAYERS_FILE.isFile()) {
            bl = true;
        }
        _snowman = false;
        if (BANNED_IPS_FILE.exists() && BANNED_IPS_FILE.isFile()) {
            _snowman = true;
        }
        _snowman = false;
        if (OPERATORS_FILE.exists() && OPERATORS_FILE.isFile()) {
            _snowman = true;
        }
        _snowman = false;
        if (WHITE_LIST_FILE.exists() && WHITE_LIST_FILE.isFile()) {
            _snowman = true;
        }
        if (bl || _snowman || _snowman || _snowman) {
            LOGGER.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
            LOGGER.warn("** please remove the following files and restart the server:");
            if (bl) {
                LOGGER.warn("* {}", (Object)BANNED_PLAYERS_FILE.getName());
            }
            if (_snowman) {
                LOGGER.warn("* {}", (Object)BANNED_IPS_FILE.getName());
            }
            if (_snowman) {
                LOGGER.warn("* {}", (Object)OPERATORS_FILE.getName());
            }
            if (_snowman) {
                LOGGER.warn("* {}", (Object)WHITE_LIST_FILE.getName());
            }
            return false;
        }
        return true;
    }

    private static boolean checkPlayerConversionSuccess(MinecraftServer server) {
        File file = ServerConfigHandler.getLevelPlayersFolder(server);
        if (file.exists() && file.isDirectory() && (file.list().length > 0 || !file.delete())) {
            LOGGER.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
            LOGGER.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
            LOGGER.warn("** please restart the server and if the problem persists, remove the directory '{}'", (Object)file.getPath());
            return false;
        }
        return true;
    }

    private static File getLevelPlayersFolder(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.PLAYERS).toFile();
    }

    private static void markFileConverted(File file) {
        File file2 = new File(file.getName() + ".converted");
        file.renameTo(file2);
    }

    private static Date parseDate(String dateString, Date fallback) {
        Date date;
        try {
            date = BanEntry.DATE_FORMAT.parse(dateString);
        }
        catch (ParseException parseException) {
            date = fallback;
        }
        return date;
    }

    static class ServerConfigException
    extends RuntimeException {
        private ServerConfigException(String title, Throwable other) {
            super(title, other);
        }

        private ServerConfigException(String title) {
            super(title);
        }
    }
}

