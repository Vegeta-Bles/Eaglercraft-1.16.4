import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dzw implements dfd, AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private static final dfb b = new dfb();
   private static dzw c;
   private static int d = -1;
   private final Map<String, IntSupplier> e = Maps.newHashMap();
   private final List<String> f = Lists.newArrayList();
   private final List<Integer> g = Lists.newArrayList();
   private final List<dfg> h = Lists.newArrayList();
   private final List<Integer> i = Lists.newArrayList();
   private final Map<String, dfg> j = Maps.newHashMap();
   private final int k;
   private final String l;
   private boolean m;
   private final dfc n;
   private final List<Integer> o;
   private final List<String> p;
   private final dfe q;
   private final dfe r;

   public dzw(ach var1, String var2) throws IOException {
      vk _snowman = new vk("shaders/program/" + _snowman + ".json");
      this.l = _snowman;
      acg _snowmanx = null;

      try {
         _snowmanx = _snowman.a(_snowman);
         JsonObject _snowmanxx = afd.a(new InputStreamReader(_snowmanx.b(), StandardCharsets.UTF_8));
         String _snowmanxxx = afd.h(_snowmanxx, "vertex");
         String _snowmanxxxx = afd.h(_snowmanxx, "fragment");
         JsonArray _snowmanxxxxx = afd.a(_snowmanxx, "samplers", null);
         if (_snowmanxxxxx != null) {
            int _snowmanxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxx : _snowmanxxxxx) {
               try {
                  this.a(_snowmanxxxxxxx);
               } catch (Exception var24) {
                  vn _snowmanxxxxxxxx = vn.a(var24);
                  _snowmanxxxxxxxx.a("samplers[" + _snowmanxxxxxx + "]");
                  throw _snowmanxxxxxxxx;
               }

               _snowmanxxxxxx++;
            }
         }

         JsonArray _snowmanxxxxxx = afd.a(_snowmanxx, "attributes", null);
         if (_snowmanxxxxxx != null) {
            int _snowmanxxxxxxx = 0;
            this.o = Lists.newArrayListWithCapacity(_snowmanxxxxxx.size());
            this.p = Lists.newArrayListWithCapacity(_snowmanxxxxxx.size());

            for (JsonElement _snowmanxxxxxxxx : _snowmanxxxxxx) {
               try {
                  this.p.add(afd.a(_snowmanxxxxxxxx, "attribute"));
               } catch (Exception var23) {
                  vn _snowmanxxxxxxxxx = vn.a(var23);
                  _snowmanxxxxxxxxx.a("attributes[" + _snowmanxxxxxxx + "]");
                  throw _snowmanxxxxxxxxx;
               }

               _snowmanxxxxxxx++;
            }
         } else {
            this.o = null;
            this.p = null;
         }

         JsonArray _snowmanxxxxxxx = afd.a(_snowmanxx, "uniforms", null);
         if (_snowmanxxxxxxx != null) {
            int _snowmanxxxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
               try {
                  this.b(_snowmanxxxxxxxxx);
               } catch (Exception var22) {
                  vn _snowmanxxxxxxxxxx = vn.a(var22);
                  _snowmanxxxxxxxxxx.a("uniforms[" + _snowmanxxxxxxxx + "]");
                  throw _snowmanxxxxxxxxxx;
               }

               _snowmanxxxxxxxx++;
            }
         }

         this.n = a(afd.a(_snowmanxx, "blend", null));
         this.q = a(_snowman, dfe.a.a, _snowmanxxx);
         this.r = a(_snowman, dfe.a.b, _snowmanxxxx);
         this.k = dff.a();
         dff.b(this);
         this.h();
         if (this.p != null) {
            for (String _snowmanxxxxxxxx : this.p) {
               int _snowmanxxxxxxxxx = dfg.b(this.k, _snowmanxxxxxxxx);
               this.o.add(_snowmanxxxxxxxxx);
            }
         }
      } catch (Exception var25) {
         String _snowmanxxxxxxxx;
         if (_snowmanx != null) {
            _snowmanxxxxxxxx = " (" + _snowmanx.d() + ")";
         } else {
            _snowmanxxxxxxxx = "";
         }

         vn _snowmanxxxxxxxxx = vn.a(var25);
         _snowmanxxxxxxxxx.b(_snowman.a() + _snowmanxxxxxxxx);
         throw _snowmanxxxxxxxxx;
      } finally {
         IOUtils.closeQuietly(_snowmanx);
      }

      this.b();
   }

   public static dfe a(ach var0, dfe.a var1, String var2) throws IOException {
      dfe _snowman = _snowman.c().get(_snowman);
      if (_snowman == null) {
         vk _snowmanx = new vk("shaders/program/" + _snowman + _snowman.b());
         acg _snowmanxx = _snowman.a(_snowmanx);

         try {
            _snowman = dfe.a(_snowman, _snowman, _snowmanxx.b(), _snowmanxx.d());
         } finally {
            IOUtils.closeQuietly(_snowmanxx);
         }
      }

      return _snowman;
   }

   public static dfc a(JsonObject var0) {
      if (_snowman == null) {
         return new dfc();
      } else {
         int _snowman = 32774;
         int _snowmanx = 1;
         int _snowmanxx = 0;
         int _snowmanxxx = 1;
         int _snowmanxxxx = 0;
         boolean _snowmanxxxxx = true;
         boolean _snowmanxxxxxx = false;
         if (afd.a(_snowman, "func")) {
            _snowman = dfc.a(_snowman.get("func").getAsString());
            if (_snowman != 32774) {
               _snowmanxxxxx = false;
            }
         }

         if (afd.a(_snowman, "srcrgb")) {
            _snowmanx = dfc.b(_snowman.get("srcrgb").getAsString());
            if (_snowmanx != 1) {
               _snowmanxxxxx = false;
            }
         }

         if (afd.a(_snowman, "dstrgb")) {
            _snowmanxx = dfc.b(_snowman.get("dstrgb").getAsString());
            if (_snowmanxx != 0) {
               _snowmanxxxxx = false;
            }
         }

         if (afd.a(_snowman, "srcalpha")) {
            _snowmanxxx = dfc.b(_snowman.get("srcalpha").getAsString());
            if (_snowmanxxx != 1) {
               _snowmanxxxxx = false;
            }

            _snowmanxxxxxx = true;
         }

         if (afd.a(_snowman, "dstalpha")) {
            _snowmanxxxx = dfc.b(_snowman.get("dstalpha").getAsString());
            if (_snowmanxxxx != 0) {
               _snowmanxxxxx = false;
            }

            _snowmanxxxxxx = true;
         }

         if (_snowmanxxxxx) {
            return new dfc();
         } else {
            return _snowmanxxxxxx ? new dfc(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman) : new dfc(_snowmanx, _snowmanxx, _snowman);
         }
      }
   }

   @Override
   public void close() {
      for (dfg _snowman : this.h) {
         _snowman.close();
      }

      dff.a(this);
   }

   public void e() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      dff.a(0);
      d = -1;
      c = null;

      for (int _snowman = 0; _snowman < this.g.size(); _snowman++) {
         if (this.e.get(this.f.get(_snowman)) != null) {
            dem.q(33984 + _snowman);
            dem.L();
            dem.s(0);
         }
      }
   }

   public void f() {
      RenderSystem.assertThread(RenderSystem::isOnGameThread);
      this.m = false;
      c = this;
      this.n.a();
      if (this.k != d) {
         dff.a(this.k);
         d = this.k;
      }

      for (int _snowman = 0; _snowman < this.g.size(); _snowman++) {
         String _snowmanx = this.f.get(_snowman);
         IntSupplier _snowmanxx = this.e.get(_snowmanx);
         if (_snowmanxx != null) {
            RenderSystem.activeTexture(33984 + _snowman);
            RenderSystem.enableTexture();
            int _snowmanxxx = _snowmanxx.getAsInt();
            if (_snowmanxxx != -1) {
               RenderSystem.bindTexture(_snowmanxxx);
               dfg.a(this.g.get(_snowman), _snowman);
            }
         }
      }

      for (dfg _snowmanx : this.h) {
         _snowmanx.b();
      }
   }

   @Override
   public void b() {
      this.m = true;
   }

   @Nullable
   public dfg a(String var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return this.j.get(_snowman);
   }

   public dfb b(String var1) {
      RenderSystem.assertThread(RenderSystem::isOnGameThread);
      dfg _snowman = this.a(_snowman);
      return (dfb)(_snowman == null ? b : _snowman);
   }

   private void h() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      IntList _snowman = new IntArrayList();

      for (int _snowmanx = 0; _snowmanx < this.f.size(); _snowmanx++) {
         String _snowmanxx = this.f.get(_snowmanx);
         int _snowmanxxx = dfg.a(this.k, _snowmanxx);
         if (_snowmanxxx == -1) {
            a.warn("Shader {} could not find sampler named {} in the specified shader program.", this.l, _snowmanxx);
            this.e.remove(_snowmanxx);
            _snowman.add(_snowmanx);
         } else {
            this.g.add(_snowmanxxx);
         }
      }

      for (int _snowmanxx = _snowman.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
         this.f.remove(_snowman.getInt(_snowmanxx));
      }

      for (dfg _snowmanxx : this.h) {
         String _snowmanxxx = _snowmanxx.a();
         int _snowmanxxxx = dfg.a(this.k, _snowmanxxx);
         if (_snowmanxxxx == -1) {
            a.warn("Could not find uniform named {} in the specified shader program.", _snowmanxxx);
         } else {
            this.i.add(_snowmanxxxx);
            _snowmanxx.a(_snowmanxxxx);
            this.j.put(_snowmanxxx, _snowmanxx);
         }
      }
   }

   private void a(JsonElement var1) {
      JsonObject _snowman = afd.m(_snowman, "sampler");
      String _snowmanx = afd.h(_snowman, "name");
      if (!afd.a(_snowman, "file")) {
         this.e.put(_snowmanx, null);
         this.f.add(_snowmanx);
      } else {
         this.f.add(_snowmanx);
      }
   }

   public void a(String var1, IntSupplier var2) {
      if (this.e.containsKey(_snowman)) {
         this.e.remove(_snowman);
      }

      this.e.put(_snowman, _snowman);
      this.b();
   }

   private void b(JsonElement var1) throws vn {
      JsonObject _snowman = afd.m(_snowman, "uniform");
      String _snowmanx = afd.h(_snowman, "name");
      int _snowmanxx = dfg.a(afd.h(_snowman, "type"));
      int _snowmanxxx = afd.n(_snowman, "count");
      float[] _snowmanxxxx = new float[Math.max(_snowmanxxx, 16)];
      JsonArray _snowmanxxxxx = afd.u(_snowman, "values");
      if (_snowmanxxxxx.size() != _snowmanxxx && _snowmanxxxxx.size() > 1) {
         throw new vn("Invalid amount of values specified (expected " + _snowmanxxx + ", found " + _snowmanxxxxx.size() + ")");
      } else {
         int _snowmanxxxxxx = 0;

         for (JsonElement _snowmanxxxxxxx : _snowmanxxxxx) {
            try {
               _snowmanxxxx[_snowmanxxxxxx] = afd.e(_snowmanxxxxxxx, "value");
            } catch (Exception var13) {
               vn _snowmanxxxxxxxx = vn.a(var13);
               _snowmanxxxxxxxx.a("values[" + _snowmanxxxxxx + "]");
               throw _snowmanxxxxxxxx;
            }

            _snowmanxxxxxx++;
         }

         if (_snowmanxxx > 1 && _snowmanxxxxx.size() == 1) {
            while (_snowmanxxxxxx < _snowmanxxx) {
               _snowmanxxxx[_snowmanxxxxxx] = _snowmanxxxx[0];
               _snowmanxxxxxx++;
            }
         }

         int _snowmanxxxxxxx = _snowmanxxx > 1 && _snowmanxxx <= 4 && _snowmanxx < 8 ? _snowmanxxx - 1 : 0;
         dfg _snowmanxxxxxxxx = new dfg(_snowmanx, _snowmanxx + _snowmanxxxxxxx, _snowmanxxx, this);
         if (_snowmanxx <= 3) {
            _snowmanxxxxxxxx.a((int)_snowmanxxxx[0], (int)_snowmanxxxx[1], (int)_snowmanxxxx[2], (int)_snowmanxxxx[3]);
         } else if (_snowmanxx <= 7) {
            _snowmanxxxxxxxx.b(_snowmanxxxx[0], _snowmanxxxx[1], _snowmanxxxx[2], _snowmanxxxx[3]);
         } else {
            _snowmanxxxxxxxx.a(_snowmanxxxx);
         }

         this.h.add(_snowmanxxxxxxxx);
      }
   }

   @Override
   public dfe c() {
      return this.q;
   }

   @Override
   public dfe d() {
      return this.r;
   }

   @Override
   public int a() {
      return this.k;
   }
}
