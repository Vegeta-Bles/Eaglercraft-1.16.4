import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class drh {
   private final abw a;
   private final List<abu> b;
   private final List<abu> c;
   private final Function<abu, vk> d;
   private final Runnable e;
   private final Consumer<abw> f;

   public drh(Runnable var1, Function<abu, vk> var2, abw var3, Consumer<abw> var4) {
      this.e = _snowman;
      this.d = _snowman;
      this.a = _snowman;
      this.b = Lists.newArrayList(_snowman.e());
      Collections.reverse(this.b);
      this.c = Lists.newArrayList(_snowman.c());
      this.c.removeAll(this.b);
      this.f = _snowman;
   }

   public Stream<drh.a> a() {
      return this.c.stream().map(var1 -> new drh.d(var1));
   }

   public Stream<drh.a> b() {
      return this.b.stream().map(var1 -> new drh.c(var1));
   }

   public void c() {
      this.a.a(Lists.reverse(this.b).stream().map(abu::e).collect(ImmutableList.toImmutableList()));
      this.f.accept(this.a);
   }

   public void d() {
      this.a.a();
      this.b.retainAll(this.a.c());
      this.c.clear();
      this.c.addAll(this.a.c());
      this.c.removeAll(this.b);
   }

   public interface a {
      vk a();

      abv b();

      nr c();

      nr d();

      abx e();

      default nr f() {
         return this.e().decorate(this.d());
      }

      boolean g();

      boolean h();

      void i();

      void j();

      void k();

      void l();

      boolean m();

      default boolean n() {
         return !this.m();
      }

      default boolean o() {
         return this.m() && !this.h();
      }

      boolean p();

      boolean q();
   }

   abstract class b implements drh.a {
      private final abu b;

      public b(abu var2) {
         this.b = _snowman;
      }

      protected abstract List<abu> r();

      protected abstract List<abu> s();

      @Override
      public vk a() {
         return drh.this.d.apply(this.b);
      }

      @Override
      public abv b() {
         return this.b.c();
      }

      @Override
      public nr c() {
         return this.b.a();
      }

      @Override
      public nr d() {
         return this.b.b();
      }

      @Override
      public abx e() {
         return this.b.i();
      }

      @Override
      public boolean g() {
         return this.b.g();
      }

      @Override
      public boolean h() {
         return this.b.f();
      }

      protected void t() {
         this.r().remove(this.b);
         this.b.h().a(this.s(), this.b, Function.identity(), true);
         drh.this.e.run();
      }

      protected void a(int var1) {
         List<abu> _snowman = this.r();
         int _snowmanx = _snowman.indexOf(this.b);
         _snowman.remove(_snowmanx);
         _snowman.add(_snowmanx + _snowman, this.b);
         drh.this.e.run();
      }

      @Override
      public boolean p() {
         List<abu> _snowman = this.r();
         int _snowmanx = _snowman.indexOf(this.b);
         return _snowmanx > 0 && !_snowman.get(_snowmanx - 1).g();
      }

      @Override
      public void k() {
         this.a(-1);
      }

      @Override
      public boolean q() {
         List<abu> _snowman = this.r();
         int _snowmanx = _snowman.indexOf(this.b);
         return _snowmanx >= 0 && _snowmanx < _snowman.size() - 1 && !_snowman.get(_snowmanx + 1).g();
      }

      @Override
      public void l() {
         this.a(1);
      }
   }

   class c extends drh.b {
      public c(abu var2) {
         super(_snowman);
      }

      @Override
      protected List<abu> r() {
         return drh.this.b;
      }

      @Override
      protected List<abu> s() {
         return drh.this.c;
      }

      @Override
      public boolean m() {
         return true;
      }

      @Override
      public void i() {
      }

      @Override
      public void j() {
         this.t();
      }
   }

   class d extends drh.b {
      public d(abu var2) {
         super(_snowman);
      }

      @Override
      protected List<abu> r() {
         return drh.this.c;
      }

      @Override
      protected List<abu> s() {
         return drh.this.b;
      }

      @Override
      public boolean m() {
         return false;
      }

      @Override
      public void i() {
         this.t();
      }

      @Override
      public void j() {
      }
   }
}
