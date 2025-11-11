import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class lp {
   private final Collection<lf> a = Lists.newArrayList();
   @Nullable
   private Collection<lg> b = Lists.newArrayList();

   public lp() {
   }

   public lp(Collection<lf> var1) {
      this.a.addAll(_snowman);
   }

   public void a(lf var1) {
      this.a.add(_snowman);
      this.b.forEach(_snowman::a);
   }

   public void a(lg var1) {
      this.b.add(_snowman);
      this.a.forEach(var1x -> var1x.a(_snowman));
   }

   public void a(final Consumer<lf> var1) {
      this.a(new lg() {
         @Override
         public void a(lf var1x) {
         }

         @Override
         public void c(lf var1x) {
            _snowman.accept(_snowman);
         }
      });
   }

   public int a() {
      return (int)this.a.stream().filter(lf::i).filter(lf::q).count();
   }

   public int b() {
      return (int)this.a.stream().filter(lf::i).filter(lf::r).count();
   }

   public int c() {
      return (int)this.a.stream().filter(lf::k).count();
   }

   public boolean d() {
      return this.a() > 0;
   }

   public boolean e() {
      return this.b() > 0;
   }

   public int h() {
      return this.a.size();
   }

   public boolean i() {
      return this.c() == this.h();
   }

   public String j() {
      StringBuffer _snowman = new StringBuffer();
      _snowman.append('[');
      this.a.forEach(var1x -> {
         if (!var1x.j()) {
            _snowman.append(' ');
         } else if (var1x.h()) {
            _snowman.append('+');
         } else if (var1x.i()) {
            _snowman.append((char)(var1x.q() ? 'X' : 'x'));
         } else {
            _snowman.append('_');
         }
      });
      _snowman.append(']');
      return _snowman.toString();
   }

   @Override
   public String toString() {
      return this.j();
   }
}
