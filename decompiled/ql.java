import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ql implements oj<om> {
   private ql.a a;
   private List<vk> b;
   private List<vk> c;
   private adu d;

   public ql() {
   }

   public ql(ql.a var1, Collection<vk> var2, Collection<vk> var3, adu var4) {
      this.a = _snowman;
      this.b = ImmutableList.copyOf(_snowman);
      this.c = ImmutableList.copyOf(_snowman);
      this.d = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a(ql.a.class);
      this.d = adu.a(_snowman);
      int _snowman = _snowman.i();
      this.b = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.b.add(_snowman.p());
      }

      if (this.a == ql.a.a) {
         _snowman = _snowman.i();
         this.c = Lists.newArrayList();

         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            this.c.add(_snowman.p());
         }
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      this.d.b(_snowman);
      _snowman.d(this.b.size());

      for (vk _snowman : this.b) {
         _snowman.a(_snowman);
      }

      if (this.a == ql.a.a) {
         _snowman.d(this.c.size());

         for (vk _snowman : this.c) {
            _snowman.a(_snowman);
         }
      }
   }

   public List<vk> b() {
      return this.b;
   }

   public List<vk> c() {
      return this.c;
   }

   public adu d() {
      return this.d;
   }

   public ql.a e() {
      return this.a;
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
