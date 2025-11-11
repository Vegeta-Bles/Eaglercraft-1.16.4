import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ekd implements acc, eke, AutoCloseable {
   private static final Logger b = LogManager.getLogger();
   public static final vk a = new vk("");
   private final Map<vk, ejq> c = Maps.newHashMap();
   private final Set<eke> d = Sets.newHashSet();
   private final Map<String, Integer> e = Maps.newHashMap();
   private final ach f;

   public ekd(ach var1) {
      this.f = _snowman;
   }

   public void a(vk var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.d(_snowman));
      } else {
         this.d(_snowman);
      }
   }

   private void d(vk var1) {
      ejq _snowman = this.c.get(_snowman);
      if (_snowman == null) {
         _snowman = new ejy(_snowman);
         this.a(_snowman, _snowman);
      }

      _snowman.d();
   }

   public void a(vk var1, ejq var2) {
      _snowman = this.c(_snowman, _snowman);
      ejq _snowman = this.c.put(_snowman, _snowman);
      if (_snowman != _snowman) {
         if (_snowman != null && _snowman != ejv.c()) {
            this.d.remove(_snowman);
            this.b(_snowman, _snowman);
         }

         if (_snowman instanceof eke) {
            this.d.add((eke)_snowman);
         }
      }
   }

   private void b(vk var1, ejq var2) {
      if (_snowman != ejv.c()) {
         try {
            _snowman.close();
         } catch (Exception var4) {
            b.warn("Failed to close texture {}", _snowman, var4);
         }
      }

      _snowman.c();
   }

   private ejq c(vk var1, ejq var2) {
      try {
         _snowman.a(this.f);
         return _snowman;
      } catch (IOException var6) {
         if (_snowman != a) {
            b.warn("Failed to load texture: {}", _snowman, var6);
         }

         return ejv.c();
      } catch (Throwable var7) {
         l _snowman = l.a(var7, "Registering texture");
         m _snowmanx = _snowman.a("Resource location being registered");
         _snowmanx.a("Resource location", _snowman);
         _snowmanx.a("Texture object class", () -> _snowman.getClass().getName());
         throw new u(_snowman);
      }
   }

   @Nullable
   public ejq b(vk var1) {
      return this.c.get(_snowman);
   }

   public vk a(String var1, ejs var2) {
      Integer _snowman = this.e.get(_snowman);
      if (_snowman == null) {
         _snowman = 1;
      } else {
         _snowman = _snowman + 1;
      }

      this.e.put(_snowman, _snowman);
      vk _snowmanx = new vk(String.format("dynamic/%s_%d", _snowman, _snowman));
      this.a(_snowmanx, _snowman);
      return _snowmanx;
   }

   public CompletableFuture<Void> a(vk var1, Executor var2) {
      if (!this.c.containsKey(_snowman)) {
         ejx _snowman = new ejx(this.f, _snowman, _snowman);
         this.c.put(_snowman, _snowman);
         return _snowman.a().thenRunAsync(() -> this.a(_snowman, (ejq)_snowman), ekd::a);
      } else {
         return CompletableFuture.completedFuture(null);
      }
   }

   private static void a(Runnable var0) {
      djz.C().execute(() -> RenderSystem.recordRenderCall(_snowman::run));
   }

   @Override
   public void e() {
      for (eke _snowman : this.d) {
         _snowman.e();
      }
   }

   public void c(vk var1) {
      ejq _snowman = this.b(_snowman);
      if (_snowman != null) {
         dex.a(_snowman.b());
      }
   }

   @Override
   public void close() {
      this.c.forEach(this::b);
      this.c.clear();
      this.d.clear();
      this.e.clear();
   }

   @Override
   public CompletableFuture<Void> a(acc.a var1, ach var2, anw var3, anw var4, Executor var5, Executor var6) {
      return CompletableFuture.allOf(doy.a(this, _snowman), this.a(dlh.i, _snowman)).thenCompose(_snowman::a).thenAcceptAsync(var3x -> {
         ejv.c();
         dfw.a(this.f);
         Iterator<Entry<vk, ejq>> _snowman = this.c.entrySet().iterator();

         while (_snowman.hasNext()) {
            Entry<vk, ejq> _snowmanx = _snowman.next();
            vk _snowmanxx = _snowmanx.getKey();
            ejq _snowmanxxx = _snowmanx.getValue();
            if (_snowmanxxx == ejv.c() && !_snowmanxx.equals(ejv.a())) {
               _snowman.remove();
            } else {
               _snowmanxxx.a(this, _snowman, _snowmanxx, _snowman);
            }
         }
      }, var0 -> RenderSystem.recordRenderCall(var0::run));
   }
}
