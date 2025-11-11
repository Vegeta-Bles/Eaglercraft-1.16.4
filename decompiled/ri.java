import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;

public class ri implements oj<om> {
   private String a = "";
   private nr b;
   private nr c;
   private nr d;
   private String e;
   private String f;
   private k g;
   private final Collection<String> h;
   private int i;
   private int j;

   public ri() {
      this.b = oe.d;
      this.c = oe.d;
      this.d = oe.d;
      this.e = ddp.b.a.e;
      this.f = ddp.a.a.e;
      this.g = k.v;
      this.h = Lists.newArrayList();
   }

   public ri(ddl var1, int var2) {
      this.b = oe.d;
      this.c = oe.d;
      this.d = oe.d;
      this.e = ddp.b.a.e;
      this.f = ddp.a.a.e;
      this.g = k.v;
      this.h = Lists.newArrayList();
      this.a = _snowman.b();
      this.i = _snowman;
      if (_snowman == 0 || _snowman == 2) {
         this.b = _snowman.c();
         this.j = _snowman.m();
         this.e = _snowman.j().e;
         this.f = _snowman.l().e;
         this.g = _snowman.n();
         this.c = _snowman.e();
         this.d = _snowman.f();
      }

      if (_snowman == 0) {
         this.h.addAll(_snowman.g());
      }
   }

   public ri(ddl var1, Collection<String> var2, int var3) {
      this.b = oe.d;
      this.c = oe.d;
      this.d = oe.d;
      this.e = ddp.b.a.e;
      this.f = ddp.a.a.e;
      this.g = k.v;
      this.h = Lists.newArrayList();
      if (_snowman != 3 && _snowman != 4) {
         throw new IllegalArgumentException("Method must be join or leave for player constructor");
      } else if (_snowman != null && !_snowman.isEmpty()) {
         this.i = _snowman;
         this.a = _snowman.b();
         this.h.addAll(_snowman);
      } else {
         throw new IllegalArgumentException("Players cannot be null/empty");
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e(16);
      this.i = _snowman.readByte();
      if (this.i == 0 || this.i == 2) {
         this.b = _snowman.h();
         this.j = _snowman.readByte();
         this.e = _snowman.e(40);
         this.f = _snowman.e(40);
         this.g = _snowman.a(k.class);
         this.c = _snowman.h();
         this.d = _snowman.h();
      }

      if (this.i == 0 || this.i == 3 || this.i == 4) {
         int _snowman = _snowman.i();

         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            this.h.add(_snowman.e(40));
         }
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeByte(this.i);
      if (this.i == 0 || this.i == 2) {
         _snowman.a(this.b);
         _snowman.writeByte(this.j);
         _snowman.a(this.e);
         _snowman.a(this.f);
         _snowman.a(this.g);
         _snowman.a(this.c);
         _snowman.a(this.d);
      }

      if (this.i == 0 || this.i == 3 || this.i == 4) {
         _snowman.d(this.h.size());

         for (String _snowman : this.h) {
            _snowman.a(_snowman);
         }
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public String b() {
      return this.a;
   }

   public nr c() {
      return this.b;
   }

   public Collection<String> d() {
      return this.h;
   }

   public int e() {
      return this.i;
   }

   public int f() {
      return this.j;
   }

   public k g() {
      return this.g;
   }

   public String h() {
      return this.e;
   }

   public String i() {
      return this.f;
   }

   public nr j() {
      return this.c;
   }

   public nr k() {
      return this.d;
   }
}
