import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

public class adt {
   protected final Set<vk> a = Sets.newHashSet();
   protected final Set<vk> b = Sets.newHashSet();
   private final adu c = new adu();

   public adt() {
   }

   public void a(adt var1) {
      this.a.clear();
      this.b.clear();
      this.c.a(_snowman.c);
      this.a.addAll(_snowman.a);
      this.b.addAll(_snowman.b);
   }

   public void a(boq<?> var1) {
      if (!_snowman.af_()) {
         this.a(_snowman.f());
      }
   }

   protected void a(vk var1) {
      this.a.add(_snowman);
   }

   public boolean b(@Nullable boq<?> var1) {
      return _snowman == null ? false : this.a.contains(_snowman.f());
   }

   public boolean b(vk var1) {
      return this.a.contains(_snowman);
   }

   public void c(boq<?> var1) {
      this.c(_snowman.f());
   }

   protected void c(vk var1) {
      this.a.remove(_snowman);
      this.b.remove(_snowman);
   }

   public boolean d(boq<?> var1) {
      return this.b.contains(_snowman.f());
   }

   public void e(boq<?> var1) {
      this.b.remove(_snowman.f());
   }

   public void f(boq<?> var1) {
      this.d(_snowman.f());
   }

   protected void d(vk var1) {
      this.b.add(_snowman);
   }

   public boolean a(bjk var1) {
      return this.c.a(_snowman);
   }

   public void a(bjk var1, boolean var2) {
      this.c.a(_snowman, _snowman);
   }

   public boolean a(bjj<?> var1) {
      return this.b(_snowman.m());
   }

   public boolean b(bjk var1) {
      return this.c.b(_snowman);
   }

   public void b(bjk var1, boolean var2) {
      this.c.b(_snowman, _snowman);
   }

   public void a(adu var1) {
      this.c.a(_snowman);
   }

   public adu a() {
      return this.c.a();
   }

   public void a(bjk var1, boolean var2, boolean var3) {
      this.c.a(_snowman, _snowman);
      this.c.b(_snowman, _snowman);
   }
}
