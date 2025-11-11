import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class uv {
   private static final Logger a = LogManager.getLogger();
   private static final Map<Class<? extends aqa>, Integer> b = Maps.newHashMap();
   private final aqa c;
   private final Map<Integer, uv.a<?>> d = Maps.newHashMap();
   private final ReadWriteLock e = new ReentrantReadWriteLock();
   private boolean f = true;
   private boolean g;

   public uv(aqa var1) {
      this.c = _snowman;
   }

   public static <T> us<T> a(Class<? extends aqa> var0, ut<T> var1) {
      if (a.isDebugEnabled()) {
         try {
            Class<?> _snowman = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!_snowman.equals(_snowman)) {
               a.debug("defineId called for: {} from {}", _snowman, _snowman, new RuntimeException());
            }
         } catch (ClassNotFoundException var5) {
         }
      }

      int _snowman;
      if (b.containsKey(_snowman)) {
         _snowman = b.get(_snowman) + 1;
      } else {
         int _snowmanx = 0;
         Class<?> _snowmanxx = _snowman;

         while (_snowmanxx != aqa.class) {
            _snowmanxx = _snowmanxx.getSuperclass();
            if (b.containsKey(_snowmanxx)) {
               _snowmanx = b.get(_snowmanxx) + 1;
               break;
            }
         }

         _snowman = _snowmanx;
      }

      if (_snowman > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + _snowman + "! (Max is " + 254 + ")");
      } else {
         b.put(_snowman, _snowman);
         return _snowman.a(_snowman);
      }
   }

   public <T> void a(us<T> var1, T var2) {
      int _snowman = _snowman.a();
      if (_snowman > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + _snowman + "! (Max is " + 254 + ")");
      } else if (this.d.containsKey(_snowman)) {
         throw new IllegalArgumentException("Duplicate id value for " + _snowman + "!");
      } else if (uu.b(_snowman.b()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + _snowman.b() + " for " + _snowman + "!");
      } else {
         this.c(_snowman, _snowman);
      }
   }

   private <T> void c(us<T> var1, T var2) {
      uv.a<T> _snowman = new uv.a<>(_snowman, _snowman);
      this.e.writeLock().lock();
      this.d.put(_snowman.a(), _snowman);
      this.f = false;
      this.e.writeLock().unlock();
   }

   private <T> uv.a<T> b(us<T> var1) {
      this.e.readLock().lock();

      uv.a<T> _snowman;
      try {
         _snowman = (uv.a<T>)this.d.get(_snowman.a());
      } catch (Throwable var9) {
         l _snowmanx = l.a(var9, "Getting synched entity data");
         m _snowmanxx = _snowmanx.a("Synched entity data");
         _snowmanxx.a("Data ID", _snowman);
         throw new u(_snowmanx);
      } finally {
         this.e.readLock().unlock();
      }

      return _snowman;
   }

   public <T> T a(us<T> var1) {
      return this.b(_snowman).b();
   }

   public <T> void b(us<T> var1, T var2) {
      uv.a<T> _snowman = this.b(_snowman);
      if (ObjectUtils.notEqual(_snowman, _snowman.b())) {
         _snowman.a(_snowman);
         this.c.a(_snowman);
         _snowman.a(true);
         this.g = true;
      }
   }

   public boolean a() {
      return this.g;
   }

   public static void a(List<uv.a<?>> var0, nf var1) throws IOException {
      if (_snowman != null) {
         int _snowman = 0;

         for (int _snowmanx = _snowman.size(); _snowman < _snowmanx; _snowman++) {
            a(_snowman, _snowman.get(_snowman));
         }
      }

      _snowman.writeByte(255);
   }

   @Nullable
   public List<uv.a<?>> b() {
      List<uv.a<?>> _snowman = null;
      if (this.g) {
         this.e.readLock().lock();

         for (uv.a<?> _snowmanx : this.d.values()) {
            if (_snowmanx.c()) {
               _snowmanx.a(false);
               if (_snowman == null) {
                  _snowman = Lists.newArrayList();
               }

               _snowman.add(_snowmanx.d());
            }
         }

         this.e.readLock().unlock();
      }

      this.g = false;
      return _snowman;
   }

   @Nullable
   public List<uv.a<?>> c() {
      List<uv.a<?>> _snowman = null;
      this.e.readLock().lock();

      for (uv.a<?> _snowmanx : this.d.values()) {
         if (_snowman == null) {
            _snowman = Lists.newArrayList();
         }

         _snowman.add(_snowmanx.d());
      }

      this.e.readLock().unlock();
      return _snowman;
   }

   private static <T> void a(nf var0, uv.a<T> var1) throws IOException {
      us<T> _snowman = _snowman.a();
      int _snowmanx = uu.b(_snowman.b());
      if (_snowmanx < 0) {
         throw new EncoderException("Unknown serializer type " + _snowman.b());
      } else {
         _snowman.writeByte(_snowman.a());
         _snowman.d(_snowmanx);
         _snowman.b().a(_snowman, _snowman.b());
      }
   }

   @Nullable
   public static List<uv.a<?>> a(nf var0) throws IOException {
      List<uv.a<?>> _snowman = null;

      int _snowmanx;
      while ((_snowmanx = _snowman.readUnsignedByte()) != 255) {
         if (_snowman == null) {
            _snowman = Lists.newArrayList();
         }

         int _snowmanxx = _snowman.i();
         ut<?> _snowmanxxx = uu.a(_snowmanxx);
         if (_snowmanxxx == null) {
            throw new DecoderException("Unknown serializer type " + _snowmanxx);
         }

         _snowman.add(a(_snowman, _snowmanx, _snowmanxxx));
      }

      return _snowman;
   }

   private static <T> uv.a<T> a(nf var0, int var1, ut<T> var2) {
      return new uv.a<>(_snowman.a(_snowman), _snowman.a(_snowman));
   }

   public void a(List<uv.a<?>> var1) {
      this.e.writeLock().lock();

      for (uv.a<?> _snowman : _snowman) {
         uv.a<?> _snowmanx = this.d.get(_snowman.a().a());
         if (_snowmanx != null) {
            this.a(_snowmanx, _snowman);
            this.c.a(_snowman.a());
         }
      }

      this.e.writeLock().unlock();
      this.g = true;
   }

   private <T> void a(uv.a<T> var1, uv.a<?> var2) {
      if (!Objects.equals(_snowman.a.b(), _snowman.a.b())) {
         throw new IllegalStateException(
            String.format(
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)", _snowman.a.a(), this.c, _snowman.b, _snowman.b.getClass(), _snowman.b, _snowman.b.getClass()
            )
         );
      } else {
         _snowman.a((T)_snowman.b());
      }
   }

   public boolean d() {
      return this.f;
   }

   public void e() {
      this.g = false;
      this.e.readLock().lock();

      for (uv.a<?> _snowman : this.d.values()) {
         _snowman.a(false);
      }

      this.e.readLock().unlock();
   }

   public static class a<T> {
      private final us<T> a;
      private T b;
      private boolean c;

      public a(us<T> var1, T var2) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = true;
      }

      public us<T> a() {
         return this.a;
      }

      public void a(T var1) {
         this.b = _snowman;
      }

      public T b() {
         return this.b;
      }

      public boolean c() {
         return this.c;
      }

      public void a(boolean var1) {
         this.c = _snowman;
      }

      public uv.a<T> d() {
         return new uv.a<>(this.a, this.a.b().a(this.b));
      }
   }
}
