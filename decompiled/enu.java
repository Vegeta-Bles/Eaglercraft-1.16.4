import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class enu extends ack<enu.a> {
   public static final emq a = new emq("meta:missing_sound", 1.0F, 1.0F, 1, emq.a.a, false, false, 16);
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().registerTypeHierarchyAdapter(nr.class, new nr.a()).registerTypeAdapter(emr.class, new ems()).create();
   private static final TypeToken<Map<String, emr>> d = new TypeToken<Map<String, emr>>() {
   };
   private final Map<vk, env> e = Maps.newHashMap();
   private final enr f;

   public enu(ach var1, dkd var2) {
      this.f = new enr(this, _snowman, _snowman);
   }

   protected enu.a a(ach var1, anw var2) {
      enu.a _snowman = new enu.a();
      _snowman.a();

      for (String _snowmanx : _snowman.a()) {
         _snowman.a(_snowmanx);

         try {
            for (acg _snowmanxx : _snowman.c(new vk(_snowmanx, "sounds.json"))) {
               _snowman.a(_snowmanxx.d());

               try (
                  InputStream _snowmanxxx = _snowmanxx.b();
                  Reader _snowmanxxxx = new InputStreamReader(_snowmanxxx, StandardCharsets.UTF_8);
               ) {
                  _snowman.a("parse");
                  Map<String, emr> _snowmanxxxxx = afd.a(c, _snowmanxxxx, d);
                  _snowman.b("register");

                  for (Entry<String, emr> _snowmanxxxxxx : _snowmanxxxxx.entrySet()) {
                     _snowman.a(new vk(_snowmanx, _snowmanxxxxxx.getKey()), _snowmanxxxxxx.getValue(), _snowman);
                  }

                  _snowman.c();
               } catch (RuntimeException var45) {
                  b.warn("Invalid sounds.json in resourcepack: '{}'", _snowmanxx.d(), var45);
               }

               _snowman.c();
            }
         } catch (IOException var46) {
         }

         _snowman.c();
      }

      _snowman.b();
      return _snowman;
   }

   protected void a(enu.a var1, ach var2, anw var3) {
      _snowman.a(this.e, this.f);

      for (vk _snowman : this.e.keySet()) {
         env _snowmanx = this.e.get(_snowman);
         if (_snowmanx.c() instanceof of) {
            String _snowmanxx = ((of)_snowmanx.c()).i();
            if (!ekx.a(_snowmanxx)) {
               b.debug("Missing subtitle {} for event: {}", _snowmanxx, _snowman);
            }
         }
      }

      if (b.isDebugEnabled()) {
         for (vk _snowmanx : this.e.keySet()) {
            if (!gm.N.c(_snowmanx)) {
               b.debug("Not having sound event for: {}", _snowmanx);
            }
         }
      }

      this.f.a();
   }

   private static boolean b(emq var0, vk var1, ach var2) {
      vk _snowman = _snowman.b();
      if (!_snowman.b(_snowman)) {
         b.warn("File {} does not exist, cannot add it to event {}", _snowman, _snowman);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   public env a(vk var1) {
      return this.e.get(_snowman);
   }

   public Collection<vk> a() {
      return this.e.keySet();
   }

   public void a(emu var1) {
      this.f.a(_snowman);
   }

   public void a(emt var1) {
      this.f.c(_snowman);
   }

   public void a(emt var1, int var2) {
      this.f.a(_snowman, _snowman);
   }

   public void a(djk var1) {
      this.f.a(_snowman);
   }

   public void b() {
      this.f.d();
   }

   public void d() {
      this.f.c();
   }

   public void e() {
      this.f.b();
   }

   public void a(boolean var1) {
      this.f.a(_snowman);
   }

   public void f() {
      this.f.e();
   }

   public void a(adr var1, float var2) {
      if (_snowman == adr.a && _snowman <= 0.0F) {
         this.d();
      }

      this.f.a(_snowman, _snowman);
   }

   public void b(emt var1) {
      this.f.a(_snowman);
   }

   public boolean c(emt var1) {
      return this.f.b(_snowman);
   }

   public void a(ent var1) {
      this.f.a(_snowman);
   }

   public void b(ent var1) {
      this.f.b(_snowman);
   }

   public void a(@Nullable vk var1, @Nullable adr var2) {
      this.f.a(_snowman, _snowman);
   }

   public String g() {
      return this.f.f();
   }

   public static class a {
      private final Map<vk, env> a = Maps.newHashMap();

      protected a() {
      }

      private void a(vk var1, emr var2, ach var3) {
         env _snowman = this.a.get(_snowman);
         boolean _snowmanx = _snowman == null;
         if (_snowmanx || _snowman.b()) {
            if (!_snowmanx) {
               enu.b.debug("Replaced sound event location {}", _snowman);
            }

            _snowman = new env(_snowman, _snowman.c());
            this.a.put(_snowman, _snowman);
         }

         for (final emq _snowmanxx : _snowman.a()) {
            final vk _snowmanxxx = _snowmanxx.a();
            enw<emq> _snowmanxxxx;
            switch (_snowmanxx.g()) {
               case a:
                  if (!enu.b(_snowmanxx, _snowman, _snowman)) {
                     continue;
                  }

                  _snowmanxxxx = _snowmanxx;
                  break;
               case b:
                  _snowmanxxxx = new enw<emq>() {
                     @Override
                     public int e() {
                        env _snowman = a.this.a.get(_snowman);
                        return _snowman == null ? 0 : _snowman.e();
                     }

                     public emq a() {
                        env _snowman = a.this.a.get(_snowman);
                        if (_snowman == null) {
                           return enu.a;
                        } else {
                           emq _snowmanx = _snowman.a();
                           return new emq(_snowmanx.a().toString(), _snowmanx.c() * _snowman.c(), _snowmanx.d() * _snowman.d(), _snowman.e(), emq.a.a, _snowmanx.h() || _snowman.h(), _snowmanx.i(), _snowmanx.j());
                        }
                     }

                     @Override
                     public void a(enr var1) {
                        env _snowman = a.this.a.get(_snowman);
                        if (_snowman != null) {
                           _snowman.a(_snowman);
                        }
                     }
                  };
                  break;
               default:
                  throw new IllegalStateException("Unknown SoundEventRegistration type: " + _snowmanxx.g());
            }

            _snowman.a(_snowmanxxxx);
         }
      }

      public void a(Map<vk, env> var1, enr var2) {
         _snowman.clear();

         for (Entry<vk, env> _snowman : this.a.entrySet()) {
            _snowman.put(_snowman.getKey(), _snowman.getValue());
            _snowman.getValue().a(_snowman);
         }
      }
   }
}
