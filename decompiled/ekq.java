import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class ekq implements abj {
   private static final Map<String, Pair<cez, vk>> d = x.a(Maps.newHashMap(), var0 -> {
      var0.put("textures/entity/chest/normal_left.png", new Pair(cez.b, new vk("textures/entity/chest/normal_double.png")));
      var0.put("textures/entity/chest/normal_right.png", new Pair(cez.c, new vk("textures/entity/chest/normal_double.png")));
      var0.put("textures/entity/chest/normal.png", new Pair(cez.a, new vk("textures/entity/chest/normal.png")));
      var0.put("textures/entity/chest/trapped_left.png", new Pair(cez.b, new vk("textures/entity/chest/trapped_double.png")));
      var0.put("textures/entity/chest/trapped_right.png", new Pair(cez.c, new vk("textures/entity/chest/trapped_double.png")));
      var0.put("textures/entity/chest/trapped.png", new Pair(cez.a, new vk("textures/entity/chest/trapped.png")));
      var0.put("textures/entity/chest/christmas_left.png", new Pair(cez.b, new vk("textures/entity/chest/christmas_double.png")));
      var0.put("textures/entity/chest/christmas_right.png", new Pair(cez.c, new vk("textures/entity/chest/christmas_double.png")));
      var0.put("textures/entity/chest/christmas.png", new Pair(cez.a, new vk("textures/entity/chest/christmas.png")));
      var0.put("textures/entity/chest/ender.png", new Pair(cez.a, new vk("textures/entity/chest/ender.png")));
   });
   private static final List<String> e = Lists.newArrayList(
      new String[]{
         "base",
         "border",
         "bricks",
         "circle",
         "creeper",
         "cross",
         "curly_border",
         "diagonal_left",
         "diagonal_right",
         "diagonal_up_left",
         "diagonal_up_right",
         "flower",
         "globe",
         "gradient",
         "gradient_up",
         "half_horizontal",
         "half_horizontal_bottom",
         "half_vertical",
         "half_vertical_right",
         "mojang",
         "rhombus",
         "skull",
         "small_stripes",
         "square_bottom_left",
         "square_bottom_right",
         "square_top_left",
         "square_top_right",
         "straight_cross",
         "stripe_bottom",
         "stripe_center",
         "stripe_downleft",
         "stripe_downright",
         "stripe_left",
         "stripe_middle",
         "stripe_right",
         "stripe_top",
         "triangle_bottom",
         "triangle_top",
         "triangles_bottom",
         "triangles_top"
      }
   );
   private static final Set<String> f = e.stream().map(var0 -> "textures/entity/shield/" + var0 + ".png").collect(Collectors.toSet());
   private static final Set<String> g = e.stream().map(var0 -> "textures/entity/banner/" + var0 + ".png").collect(Collectors.toSet());
   public static final vk a = new vk("textures/entity/shield_base.png");
   public static final vk b = new vk("textures/entity/banner_base.png");
   public static final vk c = new vk("textures/entity/iron_golem.png");
   private final abj h;

   public ekq(abj var1) {
      this.h = _snowman;
   }

   @Override
   public InputStream b(String var1) throws IOException {
      return this.h.b(_snowman);
   }

   @Override
   public boolean b(abk var1, vk var2) {
      if (!"minecraft".equals(_snowman.b())) {
         return this.h.b(_snowman, _snowman);
      } else {
         String _snowman = _snowman.a();
         if ("textures/misc/enchanted_item_glint.png".equals(_snowman)) {
            return false;
         } else if ("textures/entity/iron_golem/iron_golem.png".equals(_snowman)) {
            return this.h.b(_snowman, c);
         } else if ("textures/entity/conduit/wind.png".equals(_snowman) || "textures/entity/conduit/wind_vertical.png".equals(_snowman)) {
            return false;
         } else if (f.contains(_snowman)) {
            return this.h.b(_snowman, a) && this.h.b(_snowman, _snowman);
         } else if (!g.contains(_snowman)) {
            Pair<cez, vk> _snowmanx = d.get(_snowman);
            return _snowmanx != null && this.h.b(_snowman, (vk)_snowmanx.getSecond()) ? true : this.h.b(_snowman, _snowman);
         } else {
            return this.h.b(_snowman, b) && this.h.b(_snowman, _snowman);
         }
      }
   }

   @Override
   public InputStream a(abk var1, vk var2) throws IOException {
      if (!"minecraft".equals(_snowman.b())) {
         return this.h.a(_snowman, _snowman);
      } else {
         String _snowman = _snowman.a();
         if ("textures/entity/iron_golem/iron_golem.png".equals(_snowman)) {
            return this.h.a(_snowman, c);
         } else {
            if (f.contains(_snowman)) {
               InputStream _snowmanx = a(this.h.a(_snowman, a), this.h.a(_snowman, _snowman), 64, 2, 2, 12, 22);
               if (_snowmanx != null) {
                  return _snowmanx;
               }
            } else if (g.contains(_snowman)) {
               InputStream _snowmanx = a(this.h.a(_snowman, b), this.h.a(_snowman, _snowman), 64, 0, 0, 42, 41);
               if (_snowmanx != null) {
                  return _snowmanx;
               }
            } else {
               if ("textures/entity/enderdragon/dragon.png".equals(_snowman) || "textures/entity/enderdragon/dragon_exploding.png".equals(_snowman)) {
                  ByteArrayInputStream var23;
                  try (det _snowmanx = det.a(this.h.a(_snowman, _snowman))) {
                     int _snowmanxx = _snowmanx.a() / 256;

                     for (int _snowmanxxx = 88 * _snowmanxx; _snowmanxxx < 200 * _snowmanxx; _snowmanxxx++) {
                        for (int _snowmanxxxx = 56 * _snowmanxx; _snowmanxxxx < 112 * _snowmanxx; _snowmanxxxx++) {
                           _snowmanx.a(_snowmanxxxx, _snowmanxxx, 0);
                        }
                     }

                     var23 = new ByteArrayInputStream(_snowmanx.e());
                  }

                  return var23;
               }

               if ("textures/entity/conduit/closed_eye.png".equals(_snowman) || "textures/entity/conduit/open_eye.png".equals(_snowman)) {
                  return a(this.h.a(_snowman, _snowman));
               }

               Pair<cez, vk> _snowmanx = d.get(_snowman);
               if (_snowmanx != null) {
                  cez _snowmanxx = (cez)_snowmanx.getFirst();
                  InputStream _snowmanxxx = this.h.a(_snowman, (vk)_snowmanx.getSecond());
                  if (_snowmanxx == cez.a) {
                     return d(_snowmanxxx);
                  }

                  if (_snowmanxx == cez.b) {
                     return b(_snowmanxxx);
                  }

                  if (_snowmanxx == cez.c) {
                     return c(_snowmanxxx);
                  }
               }
            }

            return this.h.a(_snowman, _snowman);
         }
      }
   }

   @Nullable
   public static InputStream a(InputStream var0, InputStream var1, int var2, int var3, int var4, int var5, int var6) throws IOException {
      ByteArrayInputStream var71;
      try (
         det _snowman = det.a(_snowman);
         det _snowmanx = det.a(_snowman);
      ) {
         int _snowmanxx = _snowman.a();
         int _snowmanxxx = _snowman.b();
         if (_snowmanxx != _snowmanx.a() || _snowmanxxx != _snowmanx.b()) {
            return null;
         }

         try (det _snowmanxxxx = new det(_snowmanxx, _snowmanxxx, true)) {
            int _snowmanxxxxx = _snowmanxx / _snowman;

            for (int _snowmanxxxxxx = _snowman * _snowmanxxxxx; _snowmanxxxxxx < _snowman * _snowmanxxxxx; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = _snowman * _snowmanxxxxx; _snowmanxxxxxxx < _snowman * _snowmanxxxxx; _snowmanxxxxxxx++) {
                  int _snowmanxxxxxxxx = det.b(_snowmanx.a(_snowmanxxxxxxx, _snowmanxxxxxx));
                  int _snowmanxxxxxxxxx = _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxx);
                  _snowmanxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxx, det.a(_snowmanxxxxxxxx, det.d(_snowmanxxxxxxxxx), det.c(_snowmanxxxxxxxxx), det.b(_snowmanxxxxxxxxx)));
               }
            }

            var71 = new ByteArrayInputStream(_snowmanxxxx.e());
         }
      }

      return var71;
   }

   public static InputStream a(InputStream var0) throws IOException {
      ByteArrayInputStream var7;
      try (det _snowman = det.a(_snowman)) {
         int _snowmanx = _snowman.a();
         int _snowmanxx = _snowman.b();

         try (det _snowmanxxx = new det(2 * _snowmanx, 2 * _snowmanxx, true)) {
            a(_snowman, _snowmanxxx, 0, 0, 0, 0, _snowmanx, _snowmanxx, 1, false, false);
            var7 = new ByteArrayInputStream(_snowmanxxx.e());
         }
      }

      return var7;
   }

   public static InputStream b(InputStream var0) throws IOException {
      ByteArrayInputStream var8;
      try (det _snowman = det.a(_snowman)) {
         int _snowmanx = _snowman.a();
         int _snowmanxx = _snowman.b();

         try (det _snowmanxxx = new det(_snowmanx / 2, _snowmanxx, true)) {
            int _snowmanxxxx = _snowmanxx / 64;
            a(_snowman, _snowmanxxx, 29, 0, 29, 0, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 59, 0, 14, 0, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 29, 14, 43, 14, 15, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 44, 14, 29, 14, 14, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 58, 14, 14, 14, 15, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 29, 19, 29, 19, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 59, 19, 14, 19, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 29, 33, 43, 33, 15, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 44, 33, 29, 33, 14, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 58, 33, 14, 33, 15, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 2, 0, 2, 0, 1, 1, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 4, 0, 1, 0, 1, 1, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 2, 1, 3, 1, 1, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 3, 1, 2, 1, 1, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 4, 1, 1, 1, 1, 4, _snowmanxxxx, true, true);
            var8 = new ByteArrayInputStream(_snowmanxxx.e());
         }
      }

      return var8;
   }

   public static InputStream c(InputStream var0) throws IOException {
      ByteArrayInputStream var8;
      try (det _snowman = det.a(_snowman)) {
         int _snowmanx = _snowman.a();
         int _snowmanxx = _snowman.b();

         try (det _snowmanxxx = new det(_snowmanx / 2, _snowmanxx, true)) {
            int _snowmanxxxx = _snowmanxx / 64;
            a(_snowman, _snowmanxxx, 14, 0, 29, 0, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 44, 0, 14, 0, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 0, 14, 0, 14, 14, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 14, 14, 43, 14, 15, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 73, 14, 14, 14, 15, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 14, 19, 29, 19, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 44, 19, 14, 19, 15, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 0, 33, 0, 33, 14, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 14, 33, 43, 33, 15, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 73, 33, 14, 33, 15, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 1, 0, 2, 0, 1, 1, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 3, 0, 1, 0, 1, 1, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 0, 1, 0, 1, 1, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 1, 1, 3, 1, 1, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 5, 1, 1, 1, 1, 4, _snowmanxxxx, true, true);
            var8 = new ByteArrayInputStream(_snowmanxxx.e());
         }
      }

      return var8;
   }

   public static InputStream d(InputStream var0) throws IOException {
      ByteArrayInputStream var8;
      try (det _snowman = det.a(_snowman)) {
         int _snowmanx = _snowman.a();
         int _snowmanxx = _snowman.b();

         try (det _snowmanxxx = new det(_snowmanx, _snowmanxx, true)) {
            int _snowmanxxxx = _snowmanxx / 64;
            a(_snowman, _snowmanxxx, 14, 0, 28, 0, 14, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 28, 0, 14, 0, 14, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 0, 14, 0, 14, 14, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 14, 14, 42, 14, 14, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 28, 14, 28, 14, 14, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 42, 14, 14, 14, 14, 5, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 14, 19, 28, 19, 14, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 28, 19, 14, 19, 14, 14, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 0, 33, 0, 33, 14, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 14, 33, 42, 33, 14, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 28, 33, 28, 33, 14, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 42, 33, 14, 33, 14, 10, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 1, 0, 3, 0, 2, 1, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 3, 0, 1, 0, 2, 1, _snowmanxxxx, false, true);
            a(_snowman, _snowmanxxx, 0, 1, 0, 1, 1, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 1, 1, 4, 1, 2, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 3, 1, 3, 1, 1, 4, _snowmanxxxx, true, true);
            a(_snowman, _snowmanxxx, 4, 1, 1, 1, 2, 4, _snowmanxxxx, true, true);
            var8 = new ByteArrayInputStream(_snowmanxxx.e());
         }
      }

      return var8;
   }

   @Override
   public Collection<vk> a(abk var1, String var2, String var3, int var4, Predicate<String> var5) {
      return this.h.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public Set<String> a(abk var1) {
      return this.h.a(_snowman);
   }

   @Nullable
   @Override
   public <T> T a(abn<T> var1) throws IOException {
      return this.h.a(_snowman);
   }

   @Override
   public String a() {
      return this.h.a();
   }

   @Override
   public void close() {
      this.h.close();
   }

   private static void a(det var0, det var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;
      _snowman *= _snowman;

      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            _snowman.a(_snowman + _snowmanx, _snowman + _snowman, _snowman.a(_snowman + (_snowman ? _snowman - 1 - _snowmanx : _snowmanx), _snowman + (_snowman ? _snowman - 1 - _snowman : _snowman)));
         }
      }
   }
}
