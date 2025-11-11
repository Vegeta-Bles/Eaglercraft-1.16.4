import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class aad extends aok {
   private final Set<aah> h = Sets.newHashSet();
   private final Set<aah> i = Collections.unmodifiableSet(this.h);
   private boolean j = true;

   public aad(nr var1, aok.a var2, aok.b var3) {
      super(afm.a(), _snowman, _snowman, _snowman);
   }

   @Override
   public void a(float var1) {
      if (_snowman != this.b) {
         super.a(_snowman);
         this.a(oz.a.c);
      }
   }

   @Override
   public void a(aok.a var1) {
      if (_snowman != this.c) {
         super.a(_snowman);
         this.a(oz.a.e);
      }
   }

   @Override
   public void a(aok.b var1) {
      if (_snowman != this.d) {
         super.a(_snowman);
         this.a(oz.a.e);
      }
   }

   @Override
   public aok a(boolean var1) {
      if (_snowman != this.e) {
         super.a(_snowman);
         this.a(oz.a.f);
      }

      return this;
   }

   @Override
   public aok b(boolean var1) {
      if (_snowman != this.f) {
         super.b(_snowman);
         this.a(oz.a.f);
      }

      return this;
   }

   @Override
   public aok c(boolean var1) {
      if (_snowman != this.g) {
         super.c(_snowman);
         this.a(oz.a.f);
      }

      return this;
   }

   @Override
   public void a(nr var1) {
      if (!Objects.equal(_snowman, this.a)) {
         super.a(_snowman);
         this.a(oz.a.d);
      }
   }

   private void a(oz.a var1) {
      if (this.j) {
         oz _snowman = new oz(_snowman, this);

         for (aah _snowmanx : this.h) {
            _snowmanx.b.a(_snowman);
         }
      }
   }

   public void a(aah var1) {
      if (this.h.add(_snowman) && this.j) {
         _snowman.b.a(new oz(oz.a.a, this));
      }
   }

   public void b(aah var1) {
      if (this.h.remove(_snowman) && this.j) {
         _snowman.b.a(new oz(oz.a.b, this));
      }
   }

   public void b() {
      if (!this.h.isEmpty()) {
         for (aah _snowman : Lists.newArrayList(this.h)) {
            this.b(_snowman);
         }
      }
   }

   public boolean g() {
      return this.j;
   }

   public void d(boolean var1) {
      if (_snowman != this.j) {
         this.j = _snowman;

         for (aah _snowman : this.h) {
            _snowman.b.a(new oz(_snowman ? oz.a.a : oz.a.b, this));
         }
      }
   }

   public Collection<aah> h() {
      return this.i;
   }
}
