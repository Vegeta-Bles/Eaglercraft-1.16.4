import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vt {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = new GsonBuilder()
      .registerTypeAdapter(aa.class, new aa.a())
      .registerTypeAdapter(vk.class, new vk.a())
      .setPrettyPrinting()
      .create();
   private static final TypeToken<Map<vk, aa>> c = new TypeToken<Map<vk, aa>>() {
   };
   private final DataFixer d;
   private final acu e;
   private final File f;
   private final Map<y, aa> g = Maps.newLinkedHashMap();
   private final Set<y> h = Sets.newLinkedHashSet();
   private final Set<y> i = Sets.newLinkedHashSet();
   private final Set<y> j = Sets.newLinkedHashSet();
   private aah k;
   @Nullable
   private y l;
   private boolean m = true;

   public vt(DataFixer var1, acu var2, vv var3, File var4, aah var5) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.k = _snowman;
      this.d(_snowman);
   }

   public void a(aah var1) {
      this.k = _snowman;
   }

   public void a() {
      for (af<?> _snowman : ac.a()) {
         _snowman.a(this);
      }
   }

   public void a(vv var1) {
      this.a();
      this.g.clear();
      this.h.clear();
      this.i.clear();
      this.j.clear();
      this.m = true;
      this.l = null;
      this.d(_snowman);
   }

   private void b(vv var1) {
      for (y _snowman : _snowman.a()) {
         this.c(_snowman);
      }
   }

   private void c() {
      List<y> _snowman = Lists.newArrayList();

      for (Entry<y, aa> _snowmanx : this.g.entrySet()) {
         if (_snowmanx.getValue().a()) {
            _snowman.add(_snowmanx.getKey());
            this.j.add(_snowmanx.getKey());
         }
      }

      for (y _snowmanxx : _snowman) {
         this.e(_snowmanxx);
      }
   }

   private void c(vv var1) {
      for (y _snowman : _snowman.a()) {
         if (_snowman.f().isEmpty()) {
            this.a(_snowman, "");
            _snowman.d().a(this.k);
         }
      }
   }

   private void d(vv var1) {
      if (this.f.isFile()) {
         try {
            JsonReader _snowman = new JsonReader(new StringReader(Files.toString(this.f, StandardCharsets.UTF_8)));
            Throwable var3 = null;

            try {
               _snowman.setLenient(false);
               Dynamic<JsonElement> _snowmanx = new Dynamic(JsonOps.INSTANCE, Streams.parse(_snowman));
               if (!_snowmanx.get("DataVersion").asNumber().result().isPresent()) {
                  _snowmanx = _snowmanx.set("DataVersion", _snowmanx.createInt(1343));
               }

               _snowmanx = this.d.update(aga.i.a(), _snowmanx, _snowmanx.get("DataVersion").asInt(0), w.a().getWorldVersion());
               _snowmanx = _snowmanx.remove("DataVersion");
               Map<vk, aa> _snowmanxx = (Map<vk, aa>)b.getAdapter(c).fromJsonTree((JsonElement)_snowmanx.getValue());
               if (_snowmanxx == null) {
                  throw new JsonParseException("Found null for advancements");
               }

               Stream<Entry<vk, aa>> _snowmanxxx = _snowmanxx.entrySet().stream().sorted(Comparator.comparing(Entry::getValue));

               for (Entry<vk, aa> _snowmanxxxx : _snowmanxxx.collect(Collectors.toList())) {
                  y _snowmanxxxxx = _snowman.a(_snowmanxxxx.getKey());
                  if (_snowmanxxxxx == null) {
                     a.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", _snowmanxxxx.getKey(), this.f);
                  } else {
                     this.a(_snowmanxxxxx, _snowmanxxxx.getValue());
                  }
               }
            } catch (Throwable var19) {
               var3 = var19;
               throw var19;
            } finally {
               if (_snowman != null) {
                  if (var3 != null) {
                     try {
                        _snowman.close();
                     } catch (Throwable var18) {
                        var3.addSuppressed(var18);
                     }
                  } else {
                     _snowman.close();
                  }
               }
            }
         } catch (JsonParseException var21) {
            a.error("Couldn't parse player advancements in {}", this.f, var21);
         } catch (IOException var22) {
            a.error("Couldn't access player advancements in {}", this.f, var22);
         }
      }

      this.c(_snowman);
      this.c();
      this.b(_snowman);
   }

   public void b() {
      Map<vk, aa> _snowman = Maps.newHashMap();

      for (Entry<y, aa> _snowmanx : this.g.entrySet()) {
         aa _snowmanxx = _snowmanx.getValue();
         if (_snowmanxx.b()) {
            _snowman.put(_snowmanx.getKey().h(), _snowmanxx);
         }
      }

      if (this.f.getParentFile() != null) {
         this.f.getParentFile().mkdirs();
      }

      JsonElement _snowmanxx = b.toJsonTree(_snowman);
      _snowmanxx.getAsJsonObject().addProperty("DataVersion", w.a().getWorldVersion());

      try (
         OutputStream _snowmanxxx = new FileOutputStream(this.f);
         Writer _snowmanxxxx = new OutputStreamWriter(_snowmanxxx, Charsets.UTF_8.newEncoder());
      ) {
         b.toJson(_snowmanxx, _snowmanxxxx);
      } catch (IOException var35) {
         a.error("Couldn't save player advancements to {}", this.f, var35);
      }
   }

   public boolean a(y var1, String var2) {
      boolean _snowman = false;
      aa _snowmanx = this.b(_snowman);
      boolean _snowmanxx = _snowmanx.a();
      if (_snowmanx.a(_snowman)) {
         this.d(_snowman);
         this.j.add(_snowman);
         _snowman = true;
         if (!_snowmanxx && _snowmanx.a()) {
            _snowman.d().a(this.k);
            if (_snowman.c() != null && _snowman.c().i() && this.k.l.V().b(brt.w)) {
               this.e.a(new of("chat.type.advancement." + _snowman.c().e().a(), this.k.d(), _snowman.j()), no.b, x.b);
            }
         }
      }

      if (_snowmanx.a()) {
         this.e(_snowman);
      }

      return _snowman;
   }

   public boolean b(y var1, String var2) {
      boolean _snowman = false;
      aa _snowmanx = this.b(_snowman);
      if (_snowmanx.b(_snowman)) {
         this.c(_snowman);
         this.j.add(_snowman);
         _snowman = true;
      }

      if (!_snowmanx.b()) {
         this.e(_snowman);
      }

      return _snowman;
   }

   private void c(y var1) {
      aa _snowman = this.b(_snowman);
      if (!_snowman.a()) {
         for (Entry<String, ad> _snowmanx : _snowman.f().entrySet()) {
            ae _snowmanxx = _snowman.c(_snowmanx.getKey());
            if (_snowmanxx != null && !_snowmanxx.a()) {
               ag _snowmanxxx = _snowmanx.getValue().a();
               if (_snowmanxxx != null) {
                  af<ag> _snowmanxxxx = ac.a(_snowmanxxx.a());
                  if (_snowmanxxxx != null) {
                     _snowmanxxxx.a(this, new af.a<>(_snowmanxxx, _snowman, _snowmanx.getKey()));
                  }
               }
            }
         }
      }
   }

   private void d(y var1) {
      aa _snowman = this.b(_snowman);

      for (Entry<String, ad> _snowmanx : _snowman.f().entrySet()) {
         ae _snowmanxx = _snowman.c(_snowmanx.getKey());
         if (_snowmanxx != null && (_snowmanxx.a() || _snowman.a())) {
            ag _snowmanxxx = _snowmanx.getValue().a();
            if (_snowmanxxx != null) {
               af<ag> _snowmanxxxx = ac.a(_snowmanxxx.a());
               if (_snowmanxxxx != null) {
                  _snowmanxxxx.b(this, new af.a<>(_snowmanxxx, _snowman, _snowmanx.getKey()));
               }
            }
         }
      }
   }

   public void b(aah var1) {
      if (this.m || !this.i.isEmpty() || !this.j.isEmpty()) {
         Map<vk, aa> _snowman = Maps.newHashMap();
         Set<y> _snowmanx = Sets.newLinkedHashSet();
         Set<vk> _snowmanxx = Sets.newLinkedHashSet();

         for (y _snowmanxxx : this.j) {
            if (this.h.contains(_snowmanxxx)) {
               _snowman.put(_snowmanxxx.h(), this.g.get(_snowmanxxx));
            }
         }

         for (y _snowmanxxxx : this.i) {
            if (this.h.contains(_snowmanxxxx)) {
               _snowmanx.add(_snowmanxxxx);
            } else {
               _snowmanxx.add(_snowmanxxxx.h());
            }
         }

         if (this.m || !_snowman.isEmpty() || !_snowmanx.isEmpty() || !_snowmanxx.isEmpty()) {
            _snowman.b.a(new rt(this.m, _snowmanx, _snowmanxx, _snowman));
            this.i.clear();
            this.j.clear();
         }
      }

      this.m = false;
   }

   public void a(@Nullable y var1) {
      y _snowman = this.l;
      if (_snowman != null && _snowman.b() == null && _snowman.c() != null) {
         this.l = _snowman;
      } else {
         this.l = null;
      }

      if (_snowman != this.l) {
         this.k.b.a(new qs(this.l == null ? null : this.l.h()));
      }
   }

   public aa b(y var1) {
      aa _snowman = this.g.get(_snowman);
      if (_snowman == null) {
         _snowman = new aa();
         this.a(_snowman, _snowman);
      }

      return _snowman;
   }

   private void a(y var1, aa var2) {
      _snowman.a(_snowman.f(), _snowman.i());
      this.g.put(_snowman, _snowman);
   }

   private void e(y var1) {
      boolean _snowman = this.f(_snowman);
      boolean _snowmanx = this.h.contains(_snowman);
      if (_snowman && !_snowmanx) {
         this.h.add(_snowman);
         this.i.add(_snowman);
         if (this.g.containsKey(_snowman)) {
            this.j.add(_snowman);
         }
      } else if (!_snowman && _snowmanx) {
         this.h.remove(_snowman);
         this.i.add(_snowman);
      }

      if (_snowman != _snowmanx && _snowman.b() != null) {
         this.e(_snowman.b());
      }

      for (y _snowmanxx : _snowman.e()) {
         this.e(_snowmanxx);
      }
   }

   private boolean f(y var1) {
      for (int _snowman = 0; _snowman != null && _snowman <= 2; _snowman++) {
         if (_snowman == 0 && this.g(_snowman)) {
            return true;
         }

         if (_snowman.c() == null) {
            return false;
         }

         aa _snowmanx = this.b(_snowman);
         if (_snowmanx.a()) {
            return true;
         }

         if (_snowman.c().j()) {
            return false;
         }

         _snowman = _snowman.b();
      }

      return false;
   }

   private boolean g(y var1) {
      aa _snowman = this.b(_snowman);
      if (_snowman.a()) {
         return true;
      } else {
         for (y _snowmanx : _snowman.e()) {
            if (this.g(_snowmanx)) {
               return true;
            }
         }

         return false;
      }
   }
}
