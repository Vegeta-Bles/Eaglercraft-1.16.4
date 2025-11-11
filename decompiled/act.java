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
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class act {
   private static final Logger e = LogManager.getLogger();
   public static final File a = new File("banned-ips.txt");
   public static final File b = new File("banned-players.txt");
   public static final File c = new File("ops.txt");
   public static final File d = new File("white-list.txt");

   static List<String> a(File var0, Map<String, String[]> var1) throws IOException {
      List<String> _snowman = Files.readLines(_snowman, StandardCharsets.UTF_8);

      for (String _snowmanx : _snowman) {
         _snowmanx = _snowmanx.trim();
         if (!_snowmanx.startsWith("#") && _snowmanx.length() >= 1) {
            String[] _snowmanxx = _snowmanx.split("\\|");
            _snowman.put(_snowmanxx[0].toLowerCase(Locale.ROOT), _snowmanxx);
         }
      }

      return _snowman;
   }

   private static void a(MinecraftServer var0, Collection<String> var1, ProfileLookupCallback var2) {
      String[] _snowman = _snowman.stream().filter(var0x -> !aft.b(var0x)).toArray(String[]::new);
      if (_snowman.V()) {
         _snowman.aq().findProfilesByNames(_snowman, Agent.MINECRAFT, _snowman);
      } else {
         for (String _snowmanx : _snowman) {
            UUID _snowmanxx = bfw.a(new GameProfile(null, _snowmanx));
            GameProfile _snowmanxxx = new GameProfile(_snowmanxx, _snowmanx);
            _snowman.onProfileLookupSucceeded(_snowmanxxx);
         }
      }
   }

   public static boolean a(final MinecraftServer var0) {
      final acz _snowman = new acz(acu.b);
      if (b.exists() && b.isFile()) {
         if (_snowman.b().exists()) {
            try {
               _snowman.f();
            } catch (IOException var6) {
               e.warn("Could not load existing file {}", _snowman.b().getName(), var6);
            }
         }

         try {
            final Map<String, String[]> _snowmanx = Maps.newHashMap();
            a(b, _snowmanx);
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  _snowman.ar().a(_snowman);
                  String[] _snowman = _snowman.get(_snowman.getName().toLowerCase(Locale.ROOT));
                  if (_snowman == null) {
                     act.e.warn("Could not convert user banlist entry for {}", _snowman.getName());
                     throw new act.a("Profile not in the conversionlist");
                  } else {
                     Date _snowmanx = _snowman.length > 1 ? act.b(_snowman[1], null) : null;
                     String _snowmanxx = _snowman.length > 2 ? _snowman[2] : null;
                     Date _snowmanxxx = _snowman.length > 3 ? act.b(_snowman[3], null) : null;
                     String _snowmanxxxx = _snowman.length > 4 ? _snowman[4] : null;
                     _snowman.a(new ada(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx));
                  }
               }

               public void onProfileLookupFailed(GameProfile var1x, Exception var2x) {
                  act.e.warn("Could not lookup user banlist entry for {}", _snowman.getName(), _snowman);
                  if (!(_snowman instanceof ProfileNotFoundException)) {
                     throw new act.a("Could not request user " + _snowman.getName() + " from backend systems", _snowman);
                  }
               }
            };
            a(_snowman, _snowmanx.keySet(), _snowmanxx);
            _snowman.e();
            c(b);
            return true;
         } catch (IOException var4) {
            e.warn("Could not read old user banlist to convert it!", var4);
            return false;
         } catch (act.a var5) {
            e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean b(MinecraftServer var0) {
      acr _snowman = new acr(acu.c);
      if (a.exists() && a.isFile()) {
         if (_snowman.b().exists()) {
            try {
               _snowman.f();
            } catch (IOException var11) {
               e.warn("Could not load existing file {}", _snowman.b().getName(), var11);
            }
         }

         try {
            Map<String, String[]> _snowmanx = Maps.newHashMap();
            a(a, _snowmanx);

            for (String _snowmanxx : _snowmanx.keySet()) {
               String[] _snowmanxxx = _snowmanx.get(_snowmanxx);
               Date _snowmanxxxx = _snowmanxxx.length > 1 ? b(_snowmanxxx[1], null) : null;
               String _snowmanxxxxx = _snowmanxxx.length > 2 ? _snowmanxxx[2] : null;
               Date _snowmanxxxxxx = _snowmanxxx.length > 3 ? b(_snowmanxxx[3], null) : null;
               String _snowmanxxxxxxx = _snowmanxxx.length > 4 ? _snowmanxxx[4] : null;
               _snowman.a(new acs(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx));
            }

            _snowman.e();
            c(a);
            return true;
         } catch (IOException var10) {
            e.warn("Could not parse old ip banlist to convert it!", var10);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean c(final MinecraftServer var0) {
      final acv _snowman = new acv(acu.d);
      if (c.exists() && c.isFile()) {
         if (_snowman.b().exists()) {
            try {
               _snowman.f();
            } catch (IOException var6) {
               e.warn("Could not load existing file {}", _snowman.b().getName(), var6);
            }
         }

         try {
            List<String> _snowmanx = Files.readLines(c, StandardCharsets.UTF_8);
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  _snowman.ar().a(_snowman);
                  _snowman.a(new acw(_snowman, _snowman.g(), false));
               }

               public void onProfileLookupFailed(GameProfile var1x, Exception var2) {
                  act.e.warn("Could not lookup oplist entry for {}", _snowman.getName(), _snowman);
                  if (!(_snowman instanceof ProfileNotFoundException)) {
                     throw new act.a("Could not request user " + _snowman.getName() + " from backend systems", _snowman);
                  }
               }
            };
            a(_snowman, _snowmanx, _snowmanxx);
            _snowman.e();
            c(c);
            return true;
         } catch (IOException var4) {
            e.warn("Could not read old oplist to convert it!", var4);
            return false;
         } catch (act.a var5) {
            e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   public static boolean d(final MinecraftServer var0) {
      final adb _snowman = new adb(acu.e);
      if (d.exists() && d.isFile()) {
         if (_snowman.b().exists()) {
            try {
               _snowman.f();
            } catch (IOException var6) {
               e.warn("Could not load existing file {}", _snowman.b().getName(), var6);
            }
         }

         try {
            List<String> _snowmanx = Files.readLines(d, StandardCharsets.UTF_8);
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  _snowman.ar().a(_snowman);
                  _snowman.a(new adc(_snowman));
               }

               public void onProfileLookupFailed(GameProfile var1x, Exception var2) {
                  act.e.warn("Could not lookup user whitelist entry for {}", _snowman.getName(), _snowman);
                  if (!(_snowman instanceof ProfileNotFoundException)) {
                     throw new act.a("Could not request user " + _snowman.getName() + " from backend systems", _snowman);
                  }
               }
            };
            a(_snowman, _snowmanx, _snowmanxx);
            _snowman.e();
            c(d);
            return true;
         } catch (IOException var4) {
            e.warn("Could not read old whitelist to convert it!", var4);
            return false;
         } catch (act.a var5) {
            e.error("Conversion failed, please try again later", var5);
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   public static UUID a(final MinecraftServer var0, String var1) {
      if (!aft.b(_snowman) && _snowman.length() <= 16) {
         GameProfile _snowman = _snowman.ar().a(_snowman);
         if (_snowman != null && _snowman.getId() != null) {
            return _snowman.getId();
         } else if (!_snowman.O() && _snowman.V()) {
            final List<GameProfile> _snowmanx = Lists.newArrayList();
            ProfileLookupCallback _snowmanxx = new ProfileLookupCallback() {
               public void onProfileLookupSucceeded(GameProfile var1) {
                  _snowman.ar().a(_snowman);
                  _snowman.add(_snowman);
               }

               public void onProfileLookupFailed(GameProfile var1, Exception var2) {
                  act.e.warn("Could not lookup user whitelist entry for {}", _snowman.getName(), _snowman);
               }
            };
            a(_snowman, Lists.newArrayList(new String[]{_snowman}), _snowmanxx);
            return !_snowmanx.isEmpty() && _snowmanx.get(0).getId() != null ? _snowmanx.get(0).getId() : null;
         } else {
            return bfw.a(new GameProfile(null, _snowman));
         }
      } else {
         try {
            return UUID.fromString(_snowman);
         } catch (IllegalArgumentException var5) {
            return null;
         }
      }
   }

   public static boolean a(final zg var0) {
      final File _snowman = g(_snowman);
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
               public void onProfileLookupSucceeded(GameProfile var1x) {
                  _snowman.ar().a(_snowman);
                  UUID _snowman = _snowman.getId();
                  if (_snowman == null) {
                     throw new act.a("Missing UUID for user profile " + _snowman.getName());
                  } else {
                     this.a(_snowman, this.a(_snowman), _snowman.toString());
                  }
               }

               public void onProfileLookupFailed(GameProfile var1x, Exception var2x) {
                  act.e.warn("Could not lookup user uuid for {}", _snowman.getName(), _snowman);
                  if (_snowman instanceof ProfileNotFoundException) {
                     String _snowman = this.a(_snowman);
                     this.a(_snowman, _snowman, _snowman);
                  } else {
                     throw new act.a("Could not request user " + _snowman.getName() + " from backend systems", _snowman);
                  }
               }

               private void a(File var1x, String var2x, String var3x) {
                  File _snowman = new File(_snowman, _snowman + ".dat");
                  File _snowmanx = new File(_snowman, _snowman + ".dat");
                  act.b(_snowman);
                  if (!_snowman.renameTo(_snowmanx)) {
                     throw new act.a("Could not convert file for " + _snowman);
                  }
               }

               private String a(GameProfile var1x) {
                  String _snowman = null;

                  for (String _snowmanx : _snowman) {
                     if (_snowmanx != null && _snowmanx.equalsIgnoreCase(_snowman.getName())) {
                        _snowman = _snowmanx;
                        break;
                     }
                  }

                  if (_snowman == null) {
                     throw new act.a("Could not find the filename for " + _snowman.getName() + " anymore");
                  } else {
                     return _snowman;
                  }
               }
            };
            a(_snowman, Lists.newArrayList(_snowmanxxxxxx), _snowmanxxxxxxx);
            return true;
         } catch (act.a var12) {
            e.error("Conversion failed, please try again later", var12);
            return false;
         }
      } else {
         return true;
      }
   }

   private static void b(File var0) {
      if (_snowman.exists()) {
         if (!_snowman.isDirectory()) {
            throw new act.a("Can't create directory " + _snowman.getName() + " in world save directory.");
         }
      } else if (!_snowman.mkdirs()) {
         throw new act.a("Can't create directory " + _snowman.getName() + " in world save directory.");
      }
   }

   public static boolean e(MinecraftServer var0) {
      boolean _snowman = b();
      return _snowman && f(_snowman);
   }

   private static boolean b() {
      boolean _snowman = false;
      if (b.exists() && b.isFile()) {
         _snowman = true;
      }

      boolean _snowmanx = false;
      if (a.exists() && a.isFile()) {
         _snowmanx = true;
      }

      boolean _snowmanxx = false;
      if (c.exists() && c.isFile()) {
         _snowmanxx = true;
      }

      boolean _snowmanxxx = false;
      if (d.exists() && d.isFile()) {
         _snowmanxxx = true;
      }

      if (!_snowman && !_snowmanx && !_snowmanxx && !_snowmanxxx) {
         return true;
      } else {
         e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
         e.warn("** please remove the following files and restart the server:");
         if (_snowman) {
            e.warn("* {}", b.getName());
         }

         if (_snowmanx) {
            e.warn("* {}", a.getName());
         }

         if (_snowmanxx) {
            e.warn("* {}", c.getName());
         }

         if (_snowmanxxx) {
            e.warn("* {}", d.getName());
         }

         return false;
      }
   }

   private static boolean f(MinecraftServer var0) {
      File _snowman = g(_snowman);
      if (!_snowman.exists() || !_snowman.isDirectory() || _snowman.list().length <= 0 && _snowman.delete()) {
         return true;
      } else {
         e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
         e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
         e.warn("** please restart the server and if_ the problem persists, remove the directory '{}'", _snowman.getPath());
         return false;
      }
   }

   private static File g(MinecraftServer var0) {
      return _snowman.a(cye.d).toFile();
   }

   private static void c(File var0) {
      File _snowman = new File(_snowman.getName() + ".converted");
      _snowman.renameTo(_snowman);
   }

   private static Date b(String var0, Date var1) {
      Date _snowman;
      try {
         _snowman = acp.a.parse(_snowman);
      } catch (ParseException var4) {
         _snowman = _snowman;
      }

      return _snowman;
   }

   static class a extends RuntimeException {
      private a(String var1, Throwable var2) {
         super(_snowman, _snowman);
      }

      private a(String var1) {
         super(_snowman);
      }
   }
}
