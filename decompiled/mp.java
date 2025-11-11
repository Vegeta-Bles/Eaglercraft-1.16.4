import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class mp {
   private static final Logger a = LogManager.getLogger();

   @Nullable
   public static GameProfile a(md var0) {
      String _snowman = null;
      UUID _snowmanx = null;
      if (_snowman.c("Name", 8)) {
         _snowman = _snowman.l("Name");
      }

      if (_snowman.b("Id")) {
         _snowmanx = _snowman.a("Id");
      }

      try {
         GameProfile _snowmanxx = new GameProfile(_snowmanx, _snowman);
         if (_snowman.c("Properties", 10)) {
            md _snowmanxxx = _snowman.p("Properties");

            for (String _snowmanxxxx : _snowmanxxx.d()) {
               mj _snowmanxxxxx = _snowmanxxx.d(_snowmanxxxx, 10);

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  md _snowmanxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxx);
                  String _snowmanxxxxxxxx = _snowmanxxxxxxx.l("Value");
                  if (_snowmanxxxxxxx.c("Signature", 8)) {
                     _snowmanxx.getProperties().put(_snowmanxxxx, new Property(_snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx.l("Signature")));
                  } else {
                     _snowmanxx.getProperties().put(_snowmanxxxx, new Property(_snowmanxxxx, _snowmanxxxxxxxx));
                  }
               }
            }
         }

         return _snowmanxx;
      } catch (Throwable var11) {
         return null;
      }
   }

   public static md a(md var0, GameProfile var1) {
      if (!aft.b(_snowman.getName())) {
         _snowman.a("Name", _snowman.getName());
      }

      if (_snowman.getId() != null) {
         _snowman.a("Id", _snowman.getId());
      }

      if (!_snowman.getProperties().isEmpty()) {
         md _snowman = new md();

         for (String _snowmanx : _snowman.getProperties().keySet()) {
            mj _snowmanxx = new mj();

            for (Property _snowmanxxx : _snowman.getProperties().get(_snowmanx)) {
               md _snowmanxxxx = new md();
               _snowmanxxxx.a("Value", _snowmanxxx.getValue());
               if (_snowmanxxx.hasSignature()) {
                  _snowmanxxxx.a("Signature", _snowmanxxx.getSignature());
               }

               _snowmanxx.add(_snowmanxxxx);
            }

            _snowman.a(_snowmanx, _snowmanxx);
         }

         _snowman.a("Properties", _snowman);
      }

      return _snowman;
   }

   @VisibleForTesting
   public static boolean a(@Nullable mt var0, @Nullable mt var1, boolean var2) {
      if (_snowman == _snowman) {
         return true;
      } else if (_snowman == null) {
         return true;
      } else if (_snowman == null) {
         return false;
      } else if (!_snowman.getClass().equals(_snowman.getClass())) {
         return false;
      } else if (_snowman instanceof md) {
         md _snowman = (md)_snowman;
         md _snowmanx = (md)_snowman;

         for (String _snowmanxx : _snowman.d()) {
            mt _snowmanxxx = _snowman.c(_snowmanxx);
            if (!a(_snowmanxxx, _snowmanx.c(_snowmanxx), _snowman)) {
               return false;
            }
         }

         return true;
      } else if (_snowman instanceof mj && _snowman) {
         mj _snowman = (mj)_snowman;
         mj _snowmanx = (mj)_snowman;
         if (_snowman.isEmpty()) {
            return _snowmanx.isEmpty();
         } else {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
               mt _snowmanxxxx = _snowman.k(_snowmanxxx);
               boolean _snowmanxxxxx = false;

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanx.size(); _snowmanxxxxxx++) {
                  if (a(_snowmanxxxx, _snowmanx.k(_snowmanxxxxxx), _snowman)) {
                     _snowmanxxxxx = true;
                     break;
                  }
               }

               if (!_snowmanxxxxx) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return _snowman.equals(_snowman);
      }
   }

   public static mh a(UUID var0) {
      return new mh(gq.a(_snowman));
   }

   public static UUID a(mt var0) {
      if (_snowman.b() != mh.a) {
         throw new IllegalArgumentException("Expected UUID-Tag to be of type " + mh.a.a() + ", but found " + _snowman.b().a() + ".");
      } else {
         int[] _snowman = ((mh)_snowman).g();
         if (_snowman.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + _snowman.length + ".");
         } else {
            return gq.a(_snowman);
         }
      }
   }

   public static fx b(md var0) {
      return new fx(_snowman.h("X"), _snowman.h("Y"), _snowman.h("Z"));
   }

   public static md a(fx var0) {
      md _snowman = new md();
      _snowman.b("X", _snowman.u());
      _snowman.b("Y", _snowman.v());
      _snowman.b("Z", _snowman.w());
      return _snowman;
   }

   public static ceh c(md var0) {
      if (!_snowman.c("Name", 8)) {
         return bup.a.n();
      } else {
         buo _snowman = gm.Q.a(new vk(_snowman.l("Name")));
         ceh _snowmanx = _snowman.n();
         if (_snowman.c("Properties", 10)) {
            md _snowmanxx = _snowman.p("Properties");
            cei<buo, ceh> _snowmanxxx = _snowman.m();

            for (String _snowmanxxxx : _snowmanxx.d()) {
               cfj<?> _snowmanxxxxx = _snowmanxxx.a(_snowmanxxxx);
               if (_snowmanxxxxx != null) {
                  _snowmanx = a(_snowmanx, _snowmanxxxxx, _snowmanxxxx, _snowmanxx, _snowman);
               }
            }
         }

         return _snowmanx;
      }
   }

   private static <S extends cej<?, S>, T extends Comparable<T>> S a(S var0, cfj<T> var1, String var2, md var3, md var4) {
      Optional<T> _snowman = _snowman.b(_snowman.l(_snowman));
      if (_snowman.isPresent()) {
         return _snowman.a(_snowman, _snowman.get());
      } else {
         a.warn("Unable to read property: {} with value: {} for blockstate: {}", _snowman, _snowman.l(_snowman), _snowman.toString());
         return _snowman;
      }
   }

   public static md a(ceh var0) {
      md _snowman = new md();
      _snowman.a("Name", gm.Q.b(_snowman.b()).toString());
      ImmutableMap<cfj<?>, Comparable<?>> _snowmanx = _snowman.s();
      if (!_snowmanx.isEmpty()) {
         md _snowmanxx = new md();
         UnmodifiableIterator var4 = _snowmanx.entrySet().iterator();

         while (var4.hasNext()) {
            Entry<cfj<?>, Comparable<?>> _snowmanxxx = (Entry<cfj<?>, Comparable<?>>)var4.next();
            cfj<?> _snowmanxxxx = _snowmanxxx.getKey();
            _snowmanxx.a(_snowmanxxxx.f(), a(_snowmanxxxx, _snowmanxxx.getValue()));
         }

         _snowman.a("Properties", _snowmanxx);
      }

      return _snowman;
   }

   private static <T extends Comparable<T>> String a(cfj<T> var0, Comparable<?> var1) {
      return _snowman.a((T)_snowman);
   }

   public static md a(DataFixer var0, aga var1, md var2, int var3) {
      return a(_snowman, _snowman, _snowman, _snowman, w.a().getWorldVersion());
   }

   public static md a(DataFixer var0, aga var1, md var2, int var3, int var4) {
      return (md)_snowman.update(_snowman.a(), new Dynamic(mo.a, _snowman), _snowman, _snowman).getValue();
   }
}
