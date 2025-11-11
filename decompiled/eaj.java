import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class eaj implements AutoCloseable {
   private final deg a;
   private final ach b;
   private final String c;
   private final List<eak> d = Lists.newArrayList();
   private final Map<String, deg> e = Maps.newHashMap();
   private final List<deg> f = Lists.newArrayList();
   private b g;
   private int h;
   private int i;
   private float j;
   private float k;

   public eaj(ekd var1, ach var2, deg var3, vk var4) throws IOException, JsonSyntaxException {
      this.b = _snowman;
      this.a = _snowman;
      this.j = 0.0F;
      this.k = 0.0F;
      this.h = _snowman.c;
      this.i = _snowman.d;
      this.c = _snowman.toString();
      this.b();
      this.a(_snowman, _snowman);
   }

   private void a(ekd var1, vk var2) throws IOException, JsonSyntaxException {
      acg _snowman = null;

      try {
         _snowman = this.b.a(_snowman);
         JsonObject _snowmanx = afd.a(new InputStreamReader(_snowman.b(), StandardCharsets.UTF_8));
         if (afd.d(_snowmanx, "targets")) {
            JsonArray _snowmanxx = _snowmanx.getAsJsonArray("targets");
            int _snowmanxxx = 0;

            for (JsonElement _snowmanxxxx : _snowmanxx) {
               try {
                  this.a(_snowmanxxxx);
               } catch (Exception var17) {
                  vn _snowmanxxxxx = vn.a(var17);
                  _snowmanxxxxx.a("targets[" + _snowmanxxx + "]");
                  throw _snowmanxxxxx;
               }

               _snowmanxxx++;
            }
         }

         if (afd.d(_snowmanx, "passes")) {
            JsonArray _snowmanxx = _snowmanx.getAsJsonArray("passes");
            int _snowmanxxx = 0;

            for (JsonElement _snowmanxxxx : _snowmanxx) {
               try {
                  this.a(_snowman, _snowmanxxxx);
               } catch (Exception var16) {
                  vn _snowmanxxxxx = vn.a(var16);
                  _snowmanxxxxx.a("passes[" + _snowmanxxx + "]");
                  throw _snowmanxxxxx;
               }

               _snowmanxxx++;
            }
         }
      } catch (Exception var18) {
         String _snowmanxx;
         if (_snowman != null) {
            _snowmanxx = " (" + _snowman.d() + ")";
         } else {
            _snowmanxx = "";
         }

         vn _snowmanxxx = vn.a(var18);
         _snowmanxxx.b(_snowman.a() + _snowmanxx);
         throw _snowmanxxx;
      } finally {
         IOUtils.closeQuietly(_snowman);
      }
   }

   private void a(JsonElement var1) throws vn {
      if (afd.a(_snowman)) {
         this.a(_snowman.getAsString(), this.h, this.i);
      } else {
         JsonObject _snowman = afd.m(_snowman, "target");
         String _snowmanx = afd.h(_snowman, "name");
         int _snowmanxx = afd.a(_snowman, "width", this.h);
         int _snowmanxxx = afd.a(_snowman, "height", this.i);
         if (this.e.containsKey(_snowmanx)) {
            throw new vn(_snowmanx + " is already defined");
         }

         this.a(_snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   private void a(ekd var1, JsonElement var2) throws IOException {
      JsonObject _snowman = afd.m(_snowman, "pass");
      String _snowmanx = afd.h(_snowman, "name");
      String _snowmanxx = afd.h(_snowman, "intarget");
      String _snowmanxxx = afd.h(_snowman, "outtarget");
      deg _snowmanxxxx = this.b(_snowmanxx);
      deg _snowmanxxxxx = this.b(_snowmanxxx);
      if (_snowmanxxxx == null) {
         throw new vn("Input target '" + _snowmanxx + "' does not exist");
      } else if (_snowmanxxxxx == null) {
         throw new vn("Output target '" + _snowmanxxx + "' does not exist");
      } else {
         eak _snowmanxxxxxx = this.a(_snowmanx, _snowmanxxxx, _snowmanxxxxx);
         JsonArray _snowmanxxxxxxx = afd.a(_snowman, "auxtargets", null);
         if (_snowmanxxxxxxx != null) {
            int _snowmanxxxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxxxx : _snowmanxxxxxxx) {
               try {
                  JsonObject _snowmanxxxxxxxxxx = afd.m(_snowmanxxxxxxxxx, "auxtarget");
                  String _snowmanxxxxxxxxxxx = afd.h(_snowmanxxxxxxxxxx, "name");
                  String _snowmanxxxxxxxxxxxx = afd.h(_snowmanxxxxxxxxxx, "id");
                  boolean _snowmanxxxxxxxxxxxxx;
                  String _snowmanxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxx.endsWith(":depth")) {
                     _snowmanxxxxxxxxxxxxx = true;
                     _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.substring(0, _snowmanxxxxxxxxxxxx.lastIndexOf(58));
                  } else {
                     _snowmanxxxxxxxxxxxxx = false;
                     _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
                  }

                  deg _snowmanxxxxxxxxxxxxxxx = this.b(_snowmanxxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxx == null) {
                     if (_snowmanxxxxxxxxxxxxx) {
                        throw new vn("Render target '" + _snowmanxxxxxxxxxxxxxx + "' can't be used as depth buffer");
                     }

                     vk _snowmanxxxxxxxxxxxxxxxx = new vk("textures/effect/" + _snowmanxxxxxxxxxxxxxx + ".png");
                     acg _snowmanxxxxxxxxxxxxxxxxx = null;

                     try {
                        _snowmanxxxxxxxxxxxxxxxxx = this.b.a(_snowmanxxxxxxxxxxxxxxxx);
                     } catch (FileNotFoundException var31) {
                        throw new vn("Render target or texture '" + _snowmanxxxxxxxxxxxxxx + "' does not exist");
                     } finally {
                        IOUtils.closeQuietly(_snowmanxxxxxxxxxxxxxxxxx);
                     }

                     _snowman.a(_snowmanxxxxxxxxxxxxxxxx);
                     ejq var22 = _snowman.b(_snowmanxxxxxxxxxxxxxxxx);
                     int var23 = afd.n(_snowmanxxxxxxxxxx, "width");
                     int var24 = afd.n(_snowmanxxxxxxxxxx, "height");
                     boolean _snowmanxxxxxxxxxxxxxxxxxx = afd.j(_snowmanxxxxxxxxxx, "bilinear");
                     if (_snowmanxxxxxxxxxxxxxxxxxx) {
                        RenderSystem.texParameter(3553, 10241, 9729);
                        RenderSystem.texParameter(3553, 10240, 9729);
                     } else {
                        RenderSystem.texParameter(3553, 10241, 9728);
                        RenderSystem.texParameter(3553, 10240, 9728);
                     }

                     _snowmanxxxxxx.a(_snowmanxxxxxxxxxxx, var22::b, var23, var24);
                  } else if (_snowmanxxxxxxxxxxxxx) {
                     _snowmanxxxxxx.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx::g, _snowmanxxxxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxxxxx.b);
                  } else {
                     _snowmanxxxxxx.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx::f, _snowmanxxxxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxxxxx.b);
                  }
               } catch (Exception var33) {
                  vn _snowmanxxxxxxxxxxxxxxx = vn.a(var33);
                  _snowmanxxxxxxxxxxxxxxx.a("auxtargets[" + _snowmanxxxxxxxx + "]");
                  throw _snowmanxxxxxxxxxxxxxxx;
               }

               _snowmanxxxxxxxx++;
            }
         }

         JsonArray _snowmanxxxxxxxx = afd.a(_snowman, "uniforms", null);
         if (_snowmanxxxxxxxx != null) {
            int _snowmanxxxxxxxxx = 0;

            for (JsonElement _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxxx) {
               try {
                  this.b(_snowmanxxxxxxxxxxxxxxx);
               } catch (Exception var30) {
                  vn _snowmanxxxxxxxxxxxxxxxx = vn.a(var30);
                  _snowmanxxxxxxxxxxxxxxxx.a("uniforms[" + _snowmanxxxxxxxxx + "]");
                  throw _snowmanxxxxxxxxxxxxxxxx;
               }

               _snowmanxxxxxxxxx++;
            }
         }
      }
   }

   private void b(JsonElement var1) throws vn {
      JsonObject _snowman = afd.m(_snowman, "uniform");
      String _snowmanx = afd.h(_snowman, "name");
      dfg _snowmanxx = this.d.get(this.d.size() - 1).b().a(_snowmanx);
      if (_snowmanxx == null) {
         throw new vn("Uniform '" + _snowmanx + "' does not exist");
      } else {
         float[] _snowmanxxx = new float[4];
         int _snowmanxxxx = 0;

         for (JsonElement _snowmanxxxxx : afd.u(_snowman, "values")) {
            try {
               _snowmanxxx[_snowmanxxxx] = afd.e(_snowmanxxxxx, "value");
            } catch (Exception var12) {
               vn _snowmanxxxxxx = vn.a(var12);
               _snowmanxxxxxx.a("values[" + _snowmanxxxx + "]");
               throw _snowmanxxxxxx;
            }

            _snowmanxxxx++;
         }

         switch (_snowmanxxxx) {
            case 0:
            default:
               break;
            case 1:
               _snowmanxx.a(_snowmanxxx[0]);
               break;
            case 2:
               _snowmanxx.a(_snowmanxxx[0], _snowmanxxx[1]);
               break;
            case 3:
               _snowmanxx.a(_snowmanxxx[0], _snowmanxxx[1], _snowmanxxx[2]);
               break;
            case 4:
               _snowmanxx.a(_snowmanxxx[0], _snowmanxxx[1], _snowmanxxx[2], _snowmanxxx[3]);
         }
      }
   }

   public deg a(String var1) {
      return this.e.get(_snowman);
   }

   public void a(String var1, int var2, int var3) {
      deg _snowman = new deg(_snowman, _snowman, true, djz.a);
      _snowman.a(0.0F, 0.0F, 0.0F, 0.0F);
      this.e.put(_snowman, _snowman);
      if (_snowman == this.h && _snowman == this.i) {
         this.f.add(_snowman);
      }
   }

   @Override
   public void close() {
      for (deg _snowman : this.e.values()) {
         _snowman.a();
      }

      for (eak _snowman : this.d) {
         _snowman.close();
      }

      this.d.clear();
   }

   public eak a(String var1, deg var2, deg var3) throws IOException {
      eak _snowman = new eak(this.b, _snowman, _snowman, _snowman);
      this.d.add(this.d.size(), _snowman);
      return _snowman;
   }

   private void b() {
      this.g = b.a((float)this.a.a, (float)this.a.b, 0.1F, 1000.0F);
   }

   public void a(int var1, int var2) {
      this.h = this.a.a;
      this.i = this.a.b;
      this.b();

      for (eak _snowman : this.d) {
         _snowman.a(this.g);
      }

      for (deg _snowman : this.f) {
         _snowman.a(_snowman, _snowman, djz.a);
      }
   }

   public void a(float var1) {
      if (_snowman < this.k) {
         this.j = this.j + (1.0F - this.k);
         this.j += _snowman;
      } else {
         this.j = this.j + (_snowman - this.k);
      }

      this.k = _snowman;

      while (this.j > 20.0F) {
         this.j -= 20.0F;
      }

      for (eak _snowman : this.d) {
         _snowman.a(this.j / 20.0F);
      }
   }

   public final String a() {
      return this.c;
   }

   private deg b(String var1) {
      if (_snowman == null) {
         return null;
      } else {
         return _snowman.equals("minecraft:main") ? this.a : this.e.get(_snowman);
      }
   }
}
