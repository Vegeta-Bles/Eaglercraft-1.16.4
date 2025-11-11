import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bsl<T> implements bso<T> {
   protected final Predicate<T> a;
   private final Function<T, vk> b;
   private final Set<bsp<T>> c = Sets.newHashSet();
   private final TreeSet<bsp<T>> d = Sets.newTreeSet(bsp.a());
   private final aag e;
   private final Queue<bsp<T>> f = Queues.newArrayDeque();
   private final List<bsp<T>> g = Lists.newArrayList();
   private final Consumer<bsp<T>> h;

   public bsl(aag var1, Predicate<T> var2, Function<T, vk> var3, Consumer<bsp<T>> var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.e = _snowman;
      this.h = _snowman;
   }

   public void b() {
      int _snowman = this.d.size();
      if (_snowman != this.c.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      } else {
         if (_snowman > 65536) {
            _snowman = 65536;
         }

         aae _snowmanx = this.e.i();
         Iterator<bsp<T>> _snowmanxx = this.d.iterator();
         this.e.Z().a("cleaning");

         while (_snowman > 0 && _snowmanxx.hasNext()) {
            bsp<T> _snowmanxxx = _snowmanxx.next();
            if (_snowmanxxx.b > this.e.T()) {
               break;
            }

            if (_snowmanx.a(_snowmanxxx.a)) {
               _snowmanxx.remove();
               this.c.remove(_snowmanxxx);
               this.f.add(_snowmanxxx);
               _snowman--;
            }
         }

         this.e.Z().b("ticking");

         bsp<T> _snowmanxxxx;
         while ((_snowmanxxxx = this.f.poll()) != null) {
            if (_snowmanx.a(_snowmanxxxx.a)) {
               try {
                  this.g.add(_snowmanxxxx);
                  this.h.accept(_snowmanxxxx);
               } catch (Throwable var8) {
                  l _snowmanxxxxx = l.a(var8, "Exception while ticking");
                  m _snowmanxxxxxx = _snowmanxxxxx.a("Block being ticked");
                  m.a(_snowmanxxxxxx, _snowmanxxxx.a, null);
                  throw new u(_snowmanxxxxx);
               }
            } else {
               this.a(_snowmanxxxx.a, _snowmanxxxx.b(), 0);
            }
         }

         this.e.Z().c();
         this.g.clear();
         this.f.clear();
      }
   }

   @Override
   public boolean b(fx var1, T var2) {
      return this.f.contains(new bsp(_snowman, _snowman));
   }

   public List<bsp<T>> a(brd var1, boolean var2, boolean var3) {
      int _snowman = (_snowman.b << 4) - 2;
      int _snowmanx = _snowman + 16 + 2;
      int _snowmanxx = (_snowman.c << 4) - 2;
      int _snowmanxxx = _snowmanxx + 16 + 2;
      return this.a(new cra(_snowman, 0, _snowmanxx, _snowmanx, 256, _snowmanxxx), _snowman, _snowman);
   }

   public List<bsp<T>> a(cra var1, boolean var2, boolean var3) {
      List<bsp<T>> _snowman = this.a(null, this.d, _snowman, _snowman);
      if (_snowman && _snowman != null) {
         this.c.removeAll(_snowman);
      }

      _snowman = this.a(_snowman, this.f, _snowman, _snowman);
      if (!_snowman) {
         _snowman = this.a(_snowman, this.g, _snowman, _snowman);
      }

      return _snowman == null ? Collections.emptyList() : _snowman;
   }

   @Nullable
   private List<bsp<T>> a(@Nullable List<bsp<T>> var1, Collection<bsp<T>> var2, cra var3, boolean var4) {
      Iterator<bsp<T>> _snowman = _snowman.iterator();

      while (_snowman.hasNext()) {
         bsp<T> _snowmanx = _snowman.next();
         fx _snowmanxx = _snowmanx.a;
         if (_snowmanxx.u() >= _snowman.a && _snowmanxx.u() < _snowman.d && _snowmanxx.w() >= _snowman.c && _snowmanxx.w() < _snowman.f) {
            if (_snowman) {
               _snowman.remove();
            }

            if (_snowman == null) {
               _snowman = Lists.newArrayList();
            }

            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   public void a(cra var1, fx var2) {
      for (bsp<T> _snowman : this.a(_snowman, false, false)) {
         if (_snowman.b(_snowman.a)) {
            fx _snowmanx = _snowman.a.a(_snowman);
            T _snowmanxx = _snowman.b();
            this.a(new bsp<>(_snowmanx, _snowmanxx, _snowman.b, _snowman.c));
         }
      }
   }

   public mj a(brd var1) {
      List<bsp<T>> _snowman = this.a(_snowman, false, true);
      return a(this.b, _snowman, this.e.T());
   }

   private static <T> mj a(Function<T, vk> var0, Iterable<bsp<T>> var1, long var2) {
      mj _snowman = new mj();

      for (bsp<T> _snowmanx : _snowman) {
         md _snowmanxx = new md();
         _snowmanxx.a("i", _snowman.apply(_snowmanx.b()).toString());
         _snowmanxx.b("x", _snowmanx.a.u());
         _snowmanxx.b("y", _snowmanx.a.v());
         _snowmanxx.b("z", _snowmanx.a.w());
         _snowmanxx.b("t", (int)(_snowmanx.b - _snowman));
         _snowmanxx.b("p", _snowmanx.c.a());
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   @Override
   public boolean a(fx var1, T var2) {
      return this.c.contains(new bsp(_snowman, _snowman));
   }

   @Override
   public void a(fx var1, T var2, int var3, bsq var4) {
      if (!this.a.test(_snowman)) {
         this.a(new bsp<>(_snowman, _snowman, (long)_snowman + this.e.T(), _snowman));
      }
   }

   private void a(bsp<T> var1) {
      if (!this.c.contains(_snowman)) {
         this.c.add(_snowman);
         this.d.add(_snowman);
      }
   }

   public int a() {
      return this.c.size();
   }
}
