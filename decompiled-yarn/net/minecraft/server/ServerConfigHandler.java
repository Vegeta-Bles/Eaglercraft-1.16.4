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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
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
      List<String> _snowman = Files.readLines(file, StandardCharsets.UTF_8);

      for (String _snowmanx : _snowman) {
         _snowmanx = _snowmanx.trim();
         if (!_snowmanx.startsWith("#") && _snowmanx.length() >= 1) {
            String[] _snowmanxx = _snowmanx.split("\\|");
            valueMap.put(_snowmanxx[0].toLowerCase(Locale.ROOT), _snowmanxx);
         }
      }

      return _snowman;
   }

   private static void lookupProfile(MinecraftServer server, Collection<String> bannedPlayers, ProfileLookupCallback callback) {
      String[] _snowman = bannedPlayers.stream().filter(_snowmanx -> !ChatUtil.isEmpty(_snowmanx)).toArray(String[]::new);
      if (server.isOnlineMode()) {
         server.getGameProfileRepo().findProfilesByNames(_snowman, Agent.MINECRAFT, callback);
      } else {
         for (String _snowmanx : _snowman) {
            UUID _snowmanxx = PlayerEntity.getUuidFromProfile(new GameProfile(null, _snowmanx));
            GameProfile _snowmanxxx = new GameProfile(_snowmanxx, _snowmanx);
            callback.onProfileLookupSucceeded(_snowmanxxx);
         }
      }
   }

   public static boolean convertBannedPlayers(MinecraftServer server) {
      final BannedPlayerList _snowman = new BannedPlayerList(PlayerManager.BANNED_PLAYERS_FILE);
      if (BANNED_PLAYERS_FILE.exists() && BANNED_PLAYERS_FILE.isFile()) {
         if (_snowman.getFile().exists()) {
            try {
               _snowman.load();
            } catch (IOException var6) {
               LOGGER.warn("Could not load existing file {}", _snowman.getFile().getName(), var6);
            }
         }

         try {
            final Map<String, String[]> _snowmanx = Maps.newHashMap();
            processSimpleListFile(BANNED_PLAYERS_FILE, _snowmanx);
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile profile) {
                  server.getUserCache().add(profile);
                  String[] _snowman = _snowman.get(profile.getName().toLowerCase(Locale.ROOT));
                  if (_snowman == null) {
                     ServerConfigHandler.LOGGER.warn("Could not convert user banlist entry for {}", profile.getName());
                     throw new ServerConfigHandler.ServerConfigException("Profile not in the conversionlist");
                  } else {
                     Date _snowmanx = _snowman.length > 1 ? ServerConfigHandler.parseDate(_snowman[1], null) : null;
                     String _snowmanxx = _snowman.length > 2 ? _snowman[2] : null;
                     Date _snowmanxxx = _snowman.length > 3 ? ServerConfigHandler.parseDate(_snowman[3], null) : null;
                     String _snowmanxxxx = _snowman.length > 4 ? _snowman[4] : null;
                     _snowman.add(new BannedPlayerEntry(profile, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx));
                  }
               }

               public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                  ServerConfigHandler.LOGGER.warn("Could not lookup user banlist entry for {}", profile.getName(), exception);
                  if (!(exception instanceof ProfileNotFoundException)) {
                     throw new ServerConfigHandler.ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                  }
               }
            };
            lookupProfile(server, _snowmanx.keySet(), _snowmanxx);
            _snowman.save();
            markFileConverted(BANNED_PLAYERS_FILE);
            return true;
         } catch (IOException var4) {
            LOGGER.warn("Could not read old user banlist to convert it!", var4);
            return false;
         } catch (ServerConfigHandler.ServerConfigException var5) {
            LOGGER.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean convertBannedIps(MinecraftServer server) {
      BannedIpList _snowman = new BannedIpList(PlayerManager.BANNED_IPS_FILE);
      if (BANNED_IPS_FILE.exists() && BANNED_IPS_FILE.isFile()) {
         if (_snowman.getFile().exists()) {
            try {
               _snowman.load();
            } catch (IOException var11) {
               LOGGER.warn("Could not load existing file {}", _snowman.getFile().getName(), var11);
            }
         }

         try {
            Map<String, String[]> _snowmanx = Maps.newHashMap();
            processSimpleListFile(BANNED_IPS_FILE, _snowmanx);

            for (String _snowmanxx : _snowmanx.keySet()) {
               String[] _snowmanxxx = _snowmanx.get(_snowmanxx);
               Date _snowmanxxxx = _snowmanxxx.length > 1 ? parseDate(_snowmanxxx[1], null) : null;
               String _snowmanxxxxx = _snowmanxxx.length > 2 ? _snowmanxxx[2] : null;
               Date _snowmanxxxxxx = _snowmanxxx.length > 3 ? parseDate(_snowmanxxx[3], null) : null;
               String _snowmanxxxxxxx = _snowmanxxx.length > 4 ? _snowmanxxx[4] : null;
               _snowman.add(new BannedIpEntry(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx));
            }

            _snowman.save();
            markFileConverted(BANNED_IPS_FILE);
            return true;
         } catch (IOException var10) {
            LOGGER.warn("Could not parse old ip banlist to convert it!", var10);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean convertOperators(MinecraftServer server) {
      final OperatorList _snowman = new OperatorList(PlayerManager.OPERATORS_FILE);
      if (OPERATORS_FILE.exists() && OPERATORS_FILE.isFile()) {
         if (_snowman.getFile().exists()) {
            try {
               _snowman.load();
            } catch (IOException var6) {
               LOGGER.warn("Could not load existing file {}", _snowman.getFile().getName(), var6);
            }
         }

         try {
            List<String> _snowmanx = Files.readLines(OPERATORS_FILE, StandardCharsets.UTF_8);
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile profile) {
                  server.getUserCache().add(profile);
                  _snowman.add(new OperatorEntry(profile, server.getOpPermissionLevel(), false));
               }

               public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                  ServerConfigHandler.LOGGER.warn("Could not lookup oplist entry for {}", profile.getName(), exception);
                  if (!(exception instanceof ProfileNotFoundException)) {
                     throw new ServerConfigHandler.ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                  }
               }
            };
            lookupProfile(server, _snowmanx, _snowmanxx);
            _snowman.save();
            markFileConverted(OPERATORS_FILE);
            return true;
         } catch (IOException var4) {
            LOGGER.warn("Could not read old oplist to convert it!", var4);
            return false;
         } catch (ServerConfigHandler.ServerConfigException var5) {
            LOGGER.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean convertWhitelist(MinecraftServer server) {
      final Whitelist _snowman = new Whitelist(PlayerManager.WHITELIST_FILE);
      if (WHITE_LIST_FILE.exists() && WHITE_LIST_FILE.isFile()) {
         if (_snowman.getFile().exists()) {
            try {
               _snowman.load();
            } catch (IOException var6) {
               LOGGER.warn("Could not load existing file {}", _snowman.getFile().getName(), var6);
            }
         }

         try {
            List<String> _snowmanx = Files.readLines(WHITE_LIST_FILE, StandardCharsets.UTF_8);
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile profile) {
                  server.getUserCache().add(profile);
                  _snowman.add(new WhitelistEntry(profile));
               }

               public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                  ServerConfigHandler.LOGGER.warn("Could not lookup user whitelist entry for {}", profile.getName(), exception);
                  if (!(exception instanceof ProfileNotFoundException)) {
                     throw new ServerConfigHandler.ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                  }
               }
            };
            lookupProfile(server, _snowmanx, _snowmanxx);
            _snowman.save();
            markFileConverted(WHITE_LIST_FILE);
            return true;
         } catch (IOException var4) {
            LOGGER.warn("Could not read old whitelist to convert it!", var4);
            return false;
         } catch (ServerConfigHandler.ServerConfigException var5) {
            LOGGER.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   public static UUID getPlayerUuidByName(MinecraftServer server, String name) {
      if (!ChatUtil.isEmpty(name) && name.length() <= 16) {
         GameProfile _snowman = server.getUserCache().findByName(name);
         if (_snowman != null && _snowman.getId() != null) {
            return _snowman.getId();
         } else if (!server.isSinglePlayer() && server.isOnlineMode()) {
            final List<GameProfile> _snowmanx = Lists.newArrayList();
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile profile) {
                  server.getUserCache().add(profile);
                  _snowman.add(profile);
               }

               public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                  ServerConfigHandler.LOGGER.warn("Could not lookup user whitelist entry for {}", profile.getName(), exception);
               }
            };
            lookupProfile(server, Lists.newArrayList(new String[]{name}), _snowmanxx);
            return !_snowmanx.isEmpty() && _snowmanx.get(0).getId() != null ? _snowmanx.get(0).getId() : null;
         } else {
            return PlayerEntity.getUuidFromProfile(new GameProfile(null, name));
         }
      } else {
         try {
            return UUID.fromString(name);
         } catch (IllegalArgumentException var5) {
            return null;
         }
      }
   }

   public static boolean convertPlayerFiles(MinecraftDedicatedServer minecraftServer) {
      final File _snowman = getLevelPlayersFolder(minecraftServer);
      final File _snowmanx = new File(_snowman.getParentFile(), "playerdata");
      final File _snowmanxx = new File(_snowman.getParentFile(), "unknownplayers");
      if (_snowman.exists() && _snowman.isDirectory()) {
         File[] _snowmanxxx = _snowman.listFiles();
         List<String> _snowmanxxxx = Lists.newArrayList();

         for (File _snowmanxxxxx : _snowmanxxx) {
            String _snowmanxxxxxx = _snowmanxxxxx.getName();
            if (_snowmanxxxxxx.toLowerCase(Locale.ROOT).endsWith(".dat")) {
               String _snowmanxxxxxxx = _snowmanxxxxxx.substring(0, _snowmanxxxxxx.length() - ".dat".length());
               if (!_snowmanxxxxxxx.isEmpty()) {
                  _snowmanxxxx.add(_snowmanxxxxxxx);
               }
            }
         }

         try {
            final String[] _snowmanxxxxxx = _snowmanxxxx.toArray(new String[_snowmanxxxx.size()]);
            ProfileLookupCallback _snowmanxxxxxxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile profile) {
                  minecraftServer.getUserCache().add(profile);
                  UUID _snowman = profile.getId();
                  if (_snowman == null) {
                     throw new ServerConfigHandler.ServerConfigException("Missing UUID for user profile " + profile.getName());
                  } else {
                     this.convertPlayerFile(_snowman, this.getPlayerFileName(profile), _snowman.toString());
                  }
               }

               public void onProfileLookupFailed(GameProfile profile, Exception exception) {
                  ServerConfigHandler.LOGGER.warn("Could not lookup user uuid for {}", profile.getName(), exception);
                  if (exception instanceof ProfileNotFoundException) {
                     String _snowman = this.getPlayerFileName(profile);
                     this.convertPlayerFile(_snowman, _snowman, _snowman);
                  } else {
                     throw new ServerConfigHandler.ServerConfigException("Could not request user " + profile.getName() + " from backend systems", exception);
                  }
               }

               private void convertPlayerFile(File playerDataFolder, String fileName, String uuid) {
                  File _snowman = new File(_snowman, fileName + ".dat");
                  File _snowmanx = new File(playerDataFolder, uuid + ".dat");
                  ServerConfigHandler.createDirectory(playerDataFolder);
                  if (!_snowman.renameTo(_snowmanx)) {
                     throw new ServerConfigHandler.ServerConfigException("Could not convert file for " + fileName);
                  }
               }

               private String getPlayerFileName(GameProfile profile) {
                  String _snowman = null;

                  for (String _snowmanx : _snowman) {
                     if (_snowmanx != null && _snowmanx.equalsIgnoreCase(profile.getName())) {
                        _snowman = _snowmanx;
                        break;
                     }
                  }

                  if (_snowman == null) {
                     throw new ServerConfigHandler.ServerConfigException("Could not find the filename for " + profile.getName() + " anymore");
                  } else {
                     return _snowman;
                  }
               }
            };
            lookupProfile(minecraftServer, Lists.newArrayList(_snowmanxxxxxx), _snowmanxxxxxxx);
            return true;
         } catch (ServerConfigHandler.ServerConfigException var12) {
            LOGGER.error("Conversion failed, please try again later", var12);
            return false;
         }
      } else {
         return true;
      }
   }

   private static void createDirectory(File directory) {
      if (directory.exists()) {
         if (!directory.isDirectory()) {
            throw new ServerConfigHandler.ServerConfigException("Can't create directory " + directory.getName() + " in world save directory.");
         }
      } else if (!directory.mkdirs()) {
         throw new ServerConfigHandler.ServerConfigException("Can't create directory " + directory.getName() + " in world save directory.");
      }
   }

   public static boolean checkSuccess(MinecraftServer server) {
      boolean _snowman = checkListConversionSuccess();
      return _snowman && checkPlayerConversionSuccess(server);
   }

   private static boolean checkListConversionSuccess() {
      boolean _snowman = false;
      if (BANNED_PLAYERS_FILE.exists() && BANNED_PLAYERS_FILE.isFile()) {
         _snowman = true;
      }

      boolean _snowmanx = false;
      if (BANNED_IPS_FILE.exists() && BANNED_IPS_FILE.isFile()) {
         _snowmanx = true;
      }

      boolean _snowmanxx = false;
      if (OPERATORS_FILE.exists() && OPERATORS_FILE.isFile()) {
         _snowmanxx = true;
      }

      boolean _snowmanxxx = false;
      if (WHITE_LIST_FILE.exists() && WHITE_LIST_FILE.isFile()) {
         _snowmanxxx = true;
      }

      if (!_snowman && !_snowmanx && !_snowmanxx && !_snowmanxxx) {
         return true;
      } else {
         LOGGER.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
         LOGGER.warn("** please remove the following files and restart the server:");
         if (_snowman) {
            LOGGER.warn("* {}", BANNED_PLAYERS_FILE.getName());
         }

         if (_snowmanx) {
            LOGGER.warn("* {}", BANNED_IPS_FILE.getName());
         }

         if (_snowmanxx) {
            LOGGER.warn("* {}", OPERATORS_FILE.getName());
         }

         if (_snowmanxxx) {
            LOGGER.warn("* {}", WHITE_LIST_FILE.getName());
         }

         return false;
      }
   }

   private static boolean checkPlayerConversionSuccess(MinecraftServer server) {
      File _snowman = getLevelPlayersFolder(server);
      if (!_snowman.exists() || !_snowman.isDirectory() || _snowman.list().length <= 0 && _snowman.delete()) {
         return true;
      } else {
         LOGGER.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
         LOGGER.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
         LOGGER.warn("** please restart the server and if the problem persists, remove the directory '{}'", _snowman.getPath());
         return false;
      }
   }

   private static File getLevelPlayersFolder(MinecraftServer server) {
      return server.getSavePath(WorldSavePath.PLAYERS).toFile();
   }

   private static void markFileConverted(File file) {
      File _snowman = new File(file.getName() + ".converted");
      file.renameTo(_snowman);
   }

   private static Date parseDate(String dateString, Date fallback) {
      Date _snowman;
      try {
         _snowman = BanEntry.DATE_FORMAT.parse(dateString);
      } catch (ParseException var4) {
         _snowman = fallback;
      }

      return _snowman;
   }

   static class ServerConfigException extends RuntimeException {
      private ServerConfigException(String title, Throwable other) {
         super(title, other);
      }

      private ServerConfigException(String title) {
         super(title);
      }
   }
}
