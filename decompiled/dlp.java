import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.DataFixUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.ClientBrandRetriever;

public class dlp extends dkw {
   private static final Map<chn.a, String> a = x.a(new EnumMap<>(chn.a.class), var0 -> {
      var0.put(chn.a.a, "SW");
      var0.put(chn.a.b, "S");
      var0.put(chn.a.c, "OW");
      var0.put(chn.a.d, "O");
      var0.put(chn.a.e, "M");
      var0.put(chn.a.f, "ML");
   });
   private final djz b;
   private final dku c;
   private dcl d;
   private dcl e;
   @Nullable
   private brd i;
   @Nullable
   private cgh j;
   @Nullable
   private CompletableFuture<cgh> k;

   public dlp(djz var1) {
      this.b = _snowman;
      this.c = _snowman.g;
   }

   public void a() {
      this.k = null;
      this.j = null;
   }

   public void a(dfm var1) {
      this.b.au().a("debug");
      RenderSystem.pushMatrix();
      aqa _snowman = this.b.aa();
      this.d = _snowman.a(20.0, 0.0F, false);
      this.e = _snowman.a(20.0, 0.0F, true);
      this.b(_snowman);
      this.c(_snowman);
      RenderSystem.popMatrix();
      if (this.b.k.aL) {
         int _snowmanx = this.b.aD().o();
         this.a(_snowman, this.b.ag(), 0, _snowmanx / 2, true);
         eng _snowmanxx = this.b.H();
         if (_snowmanxx != null) {
            this.a(_snowman, _snowmanxx.aP(), _snowmanx - Math.min(_snowmanx / 2, 240), _snowmanx / 2, false);
         }
      }

      this.b.au().c();
   }

   protected void b(dfm var1) {
      List<String> _snowman = this.b();
      _snowman.add("");
      boolean _snowmanx = this.b.H() != null;
      _snowman.add("Debug: Pie [shift]: " + (this.b.k.aK ? "visible" : "hidden") + (_snowmanx ? " FPS + TPS" : " FPS") + " [alt]: " + (this.b.k.aL ? "visible" : "hidden"));
      _snowman.add("For help: press F3 + Q");

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         String _snowmanxxx = _snowman.get(_snowmanxx);
         if (!Strings.isNullOrEmpty(_snowmanxxx)) {
            int _snowmanxxxx = 9;
            int _snowmanxxxxx = this.c.b(_snowmanxxx);
            int _snowmanxxxxxx = 2;
            int _snowmanxxxxxxx = 2 + _snowmanxxxx * _snowmanxx;
            a(_snowman, 1, _snowmanxxxxxxx - 1, 2 + _snowmanxxxxx + 1, _snowmanxxxxxxx + _snowmanxxxx - 1, -1873784752);
            this.c.b(_snowman, _snowmanxxx, 2.0F, (float)_snowmanxxxxxxx, 14737632);
         }
      }
   }

   protected void c(dfm var1) {
      List<String> _snowman = this.c();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         String _snowmanxx = _snowman.get(_snowmanx);
         if (!Strings.isNullOrEmpty(_snowmanxx)) {
            int _snowmanxxx = 9;
            int _snowmanxxxx = this.c.b(_snowmanxx);
            int _snowmanxxxxx = this.b.aD().o() - 2 - _snowmanxxxx;
            int _snowmanxxxxxx = 2 + _snowmanxxx * _snowmanx;
            a(_snowman, _snowmanxxxxx - 1, _snowmanxxxxxx - 1, _snowmanxxxxx + _snowmanxxxx + 1, _snowmanxxxxxx + _snowmanxxx - 1, -1873784752);
            this.c.b(_snowman, _snowmanxx, (float)_snowmanxxxxx, (float)_snowmanxxxxxx, 14737632);
         }
      }
   }

   protected List<String> b() {
      eng _snowman = this.b.H();
      nd _snowmanx = this.b.w().a();
      float _snowmanxx = _snowmanx.o();
      float _snowmanxxx = _snowmanx.n();
      String _snowmanxxxx;
      if (_snowman != null) {
         _snowmanxxxx = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", _snowman.aO(), _snowmanxx, _snowmanxxx);
      } else {
         _snowmanxxxx = String.format("\"%s\" server, %.0f tx, %.0f rx", this.b.s.B(), _snowmanxx, _snowmanxxx);
      }

      fx _snowmanxxxxx = this.b.aa().cB();
      if (this.b.am()) {
         return Lists.newArrayList(
            new String[]{
               "Minecraft " + w.a().getName() + " (" + this.b.g() + "/" + ClientBrandRetriever.getClientModName() + ")",
               this.b.A,
               _snowmanxxxx,
               this.b.e.g(),
               this.b.e.i(),
               "P: " + this.b.f.d() + ". T: " + this.b.r.j(),
               this.b.r.P(),
               "",
               String.format("Chunk-relative: %d %d %d", _snowmanxxxxx.u() & 15, _snowmanxxxxx.v() & 15, _snowmanxxxxx.w() & 15)
            }
         );
      } else {
         aqa _snowmanxxxxxx = this.b.aa();
         gc _snowmanxxxxxxx = _snowmanxxxxxx.bZ();
         String _snowmanxxxxxxxx;
         switch (_snowmanxxxxxxx) {
            case c:
               _snowmanxxxxxxxx = "Towards negative Z";
               break;
            case d:
               _snowmanxxxxxxxx = "Towards positive Z";
               break;
            case e:
               _snowmanxxxxxxxx = "Towards negative X";
               break;
            case f:
               _snowmanxxxxxxxx = "Towards positive X";
               break;
            default:
               _snowmanxxxxxxxx = "Invalid";
         }

         brd _snowmanxxxxxx = new brd(_snowmanxxxxx);
         if (!Objects.equals(this.i, _snowmanxxxxxx)) {
            this.i = _snowmanxxxxxx;
            this.a();
         }

         brx _snowmanxxxxxxx = this.f();
         LongSet _snowmanxxxxxxxx = (LongSet)(_snowmanxxxxxxx instanceof aag ? ((aag)_snowmanxxxxxxx).w() : LongSets.EMPTY_SET);
         List<String> _snowmanxxxxxxxxx = Lists.newArrayList(
            new String[]{
               "Minecraft "
                  + w.a().getName()
                  + " ("
                  + this.b.g()
                  + "/"
                  + ClientBrandRetriever.getClientModName()
                  + ("release".equalsIgnoreCase(this.b.h()) ? "" : "/" + this.b.h())
                  + ")",
               this.b.A,
               _snowmanxxxx,
               this.b.e.g(),
               this.b.e.i(),
               "P: " + this.b.f.d() + ". T: " + this.b.r.j(),
               this.b.r.P()
            }
         );
         String _snowmanxxxxxxxxxx = this.e();
         if (_snowmanxxxxxxxxxx != null) {
            _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxx);
         }

         _snowmanxxxxxxxxx.add(this.b.r.Y().a() + " FC: " + _snowmanxxxxxxxx.size());
         _snowmanxxxxxxxxx.add("");
         _snowmanxxxxxxxxx.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", this.b.aa().cD(), this.b.aa().cE(), this.b.aa().cH()));
         _snowmanxxxxxxxxx.add(String.format("Block: %d %d %d", _snowmanxxxxx.u(), _snowmanxxxxx.v(), _snowmanxxxxx.w()));
         _snowmanxxxxxxxxx.add(
            String.format("Chunk: %d %d %d in %d %d %d", _snowmanxxxxx.u() & 15, _snowmanxxxxx.v() & 15, _snowmanxxxxx.w() & 15, _snowmanxxxxx.u() >> 4, _snowmanxxxxx.v() >> 4, _snowmanxxxxx.w() >> 4)
         );
         _snowmanxxxxxxxxx.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", _snowmanxxxxxxx, _snowmanxxxxxxxx, afm.g(_snowmanxxxxxx.p), afm.g(_snowmanxxxxxx.q)));
         if (this.b.r != null) {
            if (this.b.r.C(_snowmanxxxxx)) {
               cgh _snowmanxxxxxxxxxxx = this.h();
               if (_snowmanxxxxxxxxxxx.t()) {
                  _snowmanxxxxxxxxx.add("Waiting for chunk...");
               } else {
                  int _snowmanxxxxxxxxxxxx = this.b.r.n().l().b(_snowmanxxxxx, 0);
                  int _snowmanxxxxxxxxxxxxx = this.b.r.a(bsf.a, _snowmanxxxxx);
                  int _snowmanxxxxxxxxxxxxxx = this.b.r.a(bsf.b, _snowmanxxxxx);
                  _snowmanxxxxxxxxx.add("Client Light: " + _snowmanxxxxxxxxxxxx + " (" + _snowmanxxxxxxxxxxxxx + " sky, " + _snowmanxxxxxxxxxxxxxx + " block)");
                  cgh _snowmanxxxxxxxxxxxxxxx = this.g();
                  if (_snowmanxxxxxxxxxxxxxxx != null) {
                     cuo _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.H().l();
                     _snowmanxxxxxxxxx.add("Server Light: (" + _snowmanxxxxxxxxxxxxxxxx.a(bsf.a).b(_snowmanxxxxx) + " sky, " + _snowmanxxxxxxxxxxxxxxxx.a(bsf.b).b(_snowmanxxxxx) + " block)");
                  } else {
                     _snowmanxxxxxxxxx.add("Server Light: (?? sky, ?? block)");
                  }

                  StringBuilder _snowmanxxxxxxxxxxxxxxxx = new StringBuilder("CH");

                  for (chn.a _snowmanxxxxxxxxxxxxxxxxx : chn.a.values()) {
                     if (_snowmanxxxxxxxxxxxxxxxxx.c()) {
                        _snowmanxxxxxxxxxxxxxxxx.append(" ")
                           .append(a.get(_snowmanxxxxxxxxxxxxxxxxx))
                           .append(": ")
                           .append(_snowmanxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxx.u(), _snowmanxxxxx.w()));
                     }
                  }

                  _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxx.toString());
                  _snowmanxxxxxxxxxxxxxxxx.setLength(0);
                  _snowmanxxxxxxxxxxxxxxxx.append("SH");

                  for (chn.a _snowmanxxxxxxxxxxxxxxxxxx : chn.a.values()) {
                     if (_snowmanxxxxxxxxxxxxxxxxxx.d()) {
                        _snowmanxxxxxxxxxxxxxxxx.append(" ").append(a.get(_snowmanxxxxxxxxxxxxxxxxxx)).append(": ");
                        if (_snowmanxxxxxxxxxxxxxxx != null) {
                           _snowmanxxxxxxxxxxxxxxxx.append(_snowmanxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxx.u(), _snowmanxxxxx.w()));
                        } else {
                           _snowmanxxxxxxxxxxxxxxxx.append("??");
                        }
                     }
                  }

                  _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxx.toString());
                  if (_snowmanxxxxx.v() >= 0 && _snowmanxxxxx.v() < 256) {
                     _snowmanxxxxxxxxx.add("Biome: " + this.b.r.r().b(gm.ay).b(this.b.r.v(_snowmanxxxxx)));
                     long _snowmanxxxxxxxxxxxxxxxxxxx = 0L;
                     float _snowmanxxxxxxxxxxxxxxxxxxxx = 0.0F;
                     if (_snowmanxxxxxxxxxxxxxxx != null) {
                        _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.af();
                        _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.q();
                     }

                     aos _snowmanxxxxxxxxxxxxxxxxxxxxx = new aos(_snowmanxxxxxxx.ad(), _snowmanxxxxxxx.U(), _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxx.add(
                        String.format(
                           Locale.ROOT,
                           "Local Difficulty: %.2f // %.2f (Day %d)",
                           _snowmanxxxxxxxxxxxxxxxxxxxxx.b(),
                           _snowmanxxxxxxxxxxxxxxxxxxxxx.d(),
                           this.b.r.U() / 24000L
                        )
                     );
                  }
               }
            } else {
               _snowmanxxxxxxxxx.add("Outside of world...");
            }
         } else {
            _snowmanxxxxxxxxx.add("Outside of world...");
         }

         aag _snowmanxxxxxxxxxxx = this.d();
         if (_snowmanxxxxxxxxxxx != null) {
            bsg.d _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.i().k();
            if (_snowmanxxxxxxxxxxxxxxxx != null) {
               Object2IntMap<aqo> _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.b();
               int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.a();
               _snowmanxxxxxxxxx.add(
                  "SC: "
                     + _snowmanxxxxxxxxxxxxxxxxxxxx
                     + ", "
                     + Stream.of(aqo.values())
                        .map(var1x -> Character.toUpperCase(var1x.b().charAt(0)) + ": " + _snowman.getInt(var1x))
                        .collect(Collectors.joining(", "))
               );
            } else {
               _snowmanxxxxxxxxx.add("SC: N/A");
            }
         }

         eaj _snowmanxxxxxxxxxxxxxxxx = this.b.h.f();
         if (_snowmanxxxxxxxxxxxxxxxx != null) {
            _snowmanxxxxxxxxx.add("Shader: " + _snowmanxxxxxxxxxxxxxxxx.a());
         }

         _snowmanxxxxxxxxx.add(this.b.W().g() + String.format(" (Mood %d%%)", Math.round(this.b.s.w() * 100.0F)));
         return _snowmanxxxxxxxxx;
      }
   }

   @Nullable
   private aag d() {
      eng _snowman = this.b.H();
      return _snowman != null ? _snowman.a(this.b.r.Y()) : null;
   }

   @Nullable
   private String e() {
      aag _snowman = this.d();
      return _snowman != null ? _snowman.P() : null;
   }

   private brx f() {
      return (brx)DataFixUtils.orElse(Optional.ofNullable(this.b.H()).flatMap(var1 -> Optional.ofNullable(var1.a(this.b.r.Y()))), this.b.r);
   }

   @Nullable
   private cgh g() {
      if (this.k == null) {
         aag _snowman = this.d();
         if (_snowman != null) {
            this.k = _snowman.i().b(this.i.b, this.i.c, cga.m, false).thenApply(var0 -> (cgh)var0.map(var0x -> (cgh)var0x, var0x -> null));
         }

         if (this.k == null) {
            this.k = CompletableFuture.completedFuture(this.h());
         }
      }

      return this.k.getNow(null);
   }

   private cgh h() {
      if (this.j == null) {
         this.j = this.b.r.d(this.i.b, this.i.c);
      }

      return this.j;
   }

   protected List<String> c() {
      long _snowman = Runtime.getRuntime().maxMemory();
      long _snowmanx = Runtime.getRuntime().totalMemory();
      long _snowmanxx = Runtime.getRuntime().freeMemory();
      long _snowmanxxx = _snowmanx - _snowmanxx;
      List<String> _snowmanxxxx = Lists.newArrayList(
         new String[]{
            String.format("Java: %s %dbit", System.getProperty("java.version"), this.b.S() ? 64 : 32),
            String.format("Mem: % 2d%% %03d/%03dMB", _snowmanxxx * 100L / _snowman, a(_snowmanxxx), a(_snowman)),
            String.format("Allocated: % 2d%% %03dMB", _snowmanx * 100L / _snowman, a(_snowmanx)),
            "",
            String.format("CPU: %s", den.b()),
            "",
            String.format("Display: %dx%d (%s)", djz.C().aD().k(), djz.C().aD().l(), den.a()),
            den.c(),
            den.d()
         }
      );
      if (this.b.am()) {
         return _snowmanxxxx;
      } else {
         if (this.d.c() == dcl.a.b) {
            fx _snowmanxxxxx = ((dcj)this.d).a();
            ceh _snowmanxxxxxx = this.b.r.d_(_snowmanxxxxx);
            _snowmanxxxx.add("");
            _snowmanxxxx.add(k.t + "Targeted Block: " + _snowmanxxxxx.u() + ", " + _snowmanxxxxx.v() + ", " + _snowmanxxxxx.w());
            _snowmanxxxx.add(String.valueOf(gm.Q.b(_snowmanxxxxxx.b())));
            UnmodifiableIterator var12 = _snowmanxxxxxx.s().entrySet().iterator();

            while (var12.hasNext()) {
               Entry<cfj<?>, Comparable<?>> _snowmanxxxxxxx = (Entry<cfj<?>, Comparable<?>>)var12.next();
               _snowmanxxxx.add(this.a(_snowmanxxxxxxx));
            }

            for (vk _snowmanxxxxxxx : this.b.w().k().a().a(_snowmanxxxxxx.b())) {
               _snowmanxxxx.add("#" + _snowmanxxxxxxx);
            }
         }

         if (this.e.c() == dcl.a.b) {
            fx _snowmanxxxxx = ((dcj)this.e).a();
            cux _snowmanxxxxxx = this.b.r.b(_snowmanxxxxx);
            _snowmanxxxx.add("");
            _snowmanxxxx.add(k.t + "Targeted Fluid: " + _snowmanxxxxx.u() + ", " + _snowmanxxxxx.v() + ", " + _snowmanxxxxx.w());
            _snowmanxxxx.add(String.valueOf(gm.O.b(_snowmanxxxxxx.a())));
            UnmodifiableIterator var18 = _snowmanxxxxxx.s().entrySet().iterator();

            while (var18.hasNext()) {
               Entry<cfj<?>, Comparable<?>> _snowmanxxxxxxx = (Entry<cfj<?>, Comparable<?>>)var18.next();
               _snowmanxxxx.add(this.a(_snowmanxxxxxxx));
            }

            for (vk _snowmanxxxxxxx : this.b.w().k().c().a(_snowmanxxxxxx.a())) {
               _snowmanxxxx.add("#" + _snowmanxxxxxxx);
            }
         }

         aqa _snowmanxxxxx = this.b.u;
         if (_snowmanxxxxx != null) {
            _snowmanxxxx.add("");
            _snowmanxxxx.add(k.t + "Targeted Entity");
            _snowmanxxxx.add(String.valueOf(gm.S.b(_snowmanxxxxx.X())));
         }

         return _snowmanxxxx;
      }
   }

   private String a(Entry<cfj<?>, Comparable<?>> var1) {
      cfj<?> _snowman = _snowman.getKey();
      Comparable<?> _snowmanx = _snowman.getValue();
      String _snowmanxx = x.a(_snowman, _snowmanx);
      if (Boolean.TRUE.equals(_snowmanx)) {
         _snowmanxx = k.k + _snowmanxx;
      } else if (Boolean.FALSE.equals(_snowmanx)) {
         _snowmanxx = k.m + _snowmanxx;
      }

      return _snowman.f() + ": " + _snowmanxx;
   }

   private void a(dfm var1, afc var2, int var3, int var4, boolean var5) {
      RenderSystem.disableDepthTest();
      int _snowman = _snowman.a();
      int _snowmanx = _snowman.b();
      long[] _snowmanxx = _snowman.c();
      int _snowmanxxx = _snowman;
      int _snowmanxxxx = Math.max(0, _snowmanxx.length - _snowman);
      int _snowmanxxxxx = _snowmanxx.length - _snowmanxxxx;
      int var9 = _snowman.b(_snowman + _snowmanxxxx);
      long _snowmanxxxxxx = 0L;
      int _snowmanxxxxxxx = Integer.MAX_VALUE;
      int _snowmanxxxxxxxx = Integer.MIN_VALUE;

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxx++) {
         int _snowmanxxxxxxxxxx = (int)(_snowmanxx[_snowman.b(var9 + _snowmanxxxxxxxxx)] / 1000000L);
         _snowmanxxxxxxx = Math.min(_snowmanxxxxxxx, _snowmanxxxxxxxxxx);
         _snowmanxxxxxxxx = Math.max(_snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
         _snowmanxxxxxx += (long)_snowmanxxxxxxxxxx;
      }

      int _snowmanxxxxxxxxx = this.b.aD().p();
      a(_snowman, _snowman, _snowmanxxxxxxxxx - 60, _snowman + _snowmanxxxxx, _snowmanxxxxxxxxx, -1873784752);
      dfh _snowmanxxxxxxxxxx = dfo.a().c();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      _snowmanxxxxxxxxxx.a(7, dfk.l);

      for (b _snowmanxxxxxxxxxxx = f.a().c(); var9 != _snowmanx; var9 = _snowman.b(var9 + 1)) {
         int _snowmanxxxxxxxxxxxx = _snowman.a(_snowmanxx[var9], _snowman ? 30 : 60, _snowman ? 60 : 20);
         int _snowmanxxxxxxxxxxxxx = _snowman ? 100 : 60;
         int _snowmanxxxxxxxxxxxxxx = this.a(afm.a(_snowmanxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxxxx), 0, _snowmanxxxxxxxxxxxxx / 2, _snowmanxxxxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 24 & 0xFF;
         int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 16 & 0xFF;
         int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx >> 8 & 0xFF;
         int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx & 0xFF;
         _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, (float)(_snowmanxxx + 1), (float)_snowmanxxxxxxxxx, 0.0F)
            .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .d();
         _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, (float)(_snowmanxxx + 1), (float)(_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx + 1), 0.0F)
            .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .d();
         _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, (float)_snowmanxxx, (float)(_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxx + 1), 0.0F)
            .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            .d();
         _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, (float)_snowmanxxx, (float)_snowmanxxxxxxxxx, 0.0F).a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx).d();
         _snowmanxxx++;
      }

      _snowmanxxxxxxxxxx.c();
      dfi.a(_snowmanxxxxxxxxxx);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (_snowman) {
         a(_snowman, _snowman + 1, _snowmanxxxxxxxxx - 30 + 1, _snowman + 14, _snowmanxxxxxxxxx - 30 + 10, -1873784752);
         this.c.b(_snowman, "60 FPS", (float)(_snowman + 2), (float)(_snowmanxxxxxxxxx - 30 + 2), 14737632);
         this.a(_snowman, _snowman, _snowman + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 30, -1);
         a(_snowman, _snowman + 1, _snowmanxxxxxxxxx - 60 + 1, _snowman + 14, _snowmanxxxxxxxxx - 60 + 10, -1873784752);
         this.c.b(_snowman, "30 FPS", (float)(_snowman + 2), (float)(_snowmanxxxxxxxxx - 60 + 2), 14737632);
         this.a(_snowman, _snowman, _snowman + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 60, -1);
      } else {
         a(_snowman, _snowman + 1, _snowmanxxxxxxxxx - 60 + 1, _snowman + 14, _snowmanxxxxxxxxx - 60 + 10, -1873784752);
         this.c.b(_snowman, "20 TPS", (float)(_snowman + 2), (float)(_snowmanxxxxxxxxx - 60 + 2), 14737632);
         this.a(_snowman, _snowman, _snowman + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 60, -1);
      }

      this.a(_snowman, _snowman, _snowman + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 1, -1);
      this.b(_snowman, _snowman, _snowmanxxxxxxxxx - 60, _snowmanxxxxxxxxx, -1);
      this.b(_snowman, _snowman + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 60, _snowmanxxxxxxxxx, -1);
      if (_snowman && this.b.k.d > 0 && this.b.k.d <= 250) {
         this.a(_snowman, _snowman, _snowman + _snowmanxxxxx - 1, _snowmanxxxxxxxxx - 1 - (int)(1800.0 / (double)this.b.k.d), -16711681);
      }

      String _snowmanxxxxxxxxxxx = _snowmanxxxxxxx + " ms min";
      String _snowmanxxxxxxxxxxxx = _snowmanxxxxxx / (long)_snowmanxxxxx + " ms avg";
      String _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx + " ms max";
      this.c.a(_snowman, _snowmanxxxxxxxxxxx, (float)(_snowman + 2), (float)(_snowmanxxxxxxxxx - 60 - 9), 14737632);
      this.c.a(_snowman, _snowmanxxxxxxxxxxxx, (float)(_snowman + _snowmanxxxxx / 2 - this.c.b(_snowmanxxxxxxxxxxxx) / 2), (float)(_snowmanxxxxxxxxx - 60 - 9), 14737632);
      this.c.a(_snowman, _snowmanxxxxxxxxxxxxx, (float)(_snowman + _snowmanxxxxx - this.c.b(_snowmanxxxxxxxxxxxxx)), (float)(_snowmanxxxxxxxxx - 60 - 9), 14737632);
      RenderSystem.enableDepthTest();
   }

   private int a(int var1, int var2, int var3, int var4) {
      return _snowman < _snowman ? this.a(-16711936, -256, (float)_snowman / (float)_snowman) : this.a(-256, -65536, (float)(_snowman - _snowman) / (float)(_snowman - _snowman));
   }

   private int a(int var1, int var2, float var3) {
      int _snowman = _snowman >> 24 & 0xFF;
      int _snowmanx = _snowman >> 16 & 0xFF;
      int _snowmanxx = _snowman >> 8 & 0xFF;
      int _snowmanxxx = _snowman & 0xFF;
      int _snowmanxxxx = _snowman >> 24 & 0xFF;
      int _snowmanxxxxx = _snowman >> 16 & 0xFF;
      int _snowmanxxxxxx = _snowman >> 8 & 0xFF;
      int _snowmanxxxxxxx = _snowman & 0xFF;
      int _snowmanxxxxxxxx = afm.a((int)afm.g(_snowman, (float)_snowman, (float)_snowmanxxxx), 0, 255);
      int _snowmanxxxxxxxxx = afm.a((int)afm.g(_snowman, (float)_snowmanx, (float)_snowmanxxxxx), 0, 255);
      int _snowmanxxxxxxxxxx = afm.a((int)afm.g(_snowman, (float)_snowmanxx, (float)_snowmanxxxxxx), 0, 255);
      int _snowmanxxxxxxxxxxx = afm.a((int)afm.g(_snowman, (float)_snowmanxxx, (float)_snowmanxxxxxxx), 0, 255);
      return _snowmanxxxxxxxx << 24 | _snowmanxxxxxxxxx << 16 | _snowmanxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxx;
   }

   private static long a(long var0) {
      return _snowman / 1024L / 1024L;
   }
}
