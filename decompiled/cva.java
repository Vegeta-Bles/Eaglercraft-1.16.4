public final class cva {
   public static final cva a = new cva.a(cvb.b).c().i().b().e().h();
   public static final cva b = new cva.a(cvb.b).c().i().b().e().h();
   public static final cva c = new cva.a(cvb.b).c().i().b().g().h();
   public static final cva d = new cva.a(cvb.e).c().i().b().d().h();
   public static final cva e = new cva.a(cvb.i).c().i().b().f().h();
   public static final cva f = new cva.a(cvb.n).c().i().b().f().h();
   public static final cva g = new cva.a(cvb.i).c().i().b().f().e().d().h();
   public static final cva h = new cva.a(cvb.i).c().i().b().f().e().h();
   public static final cva i = new cva.a(cvb.n).c().i().b().f().e().h();
   public static final cva j = new cva.a(cvb.n).c().i().b().f().e().a().h();
   public static final cva k = new cva.a(cvb.n).c().i().b().f().e().a().h();
   public static final cva l = new cva.a(cvb.f).c().i().b().f().e().a().h();
   public static final cva m = new cva.a(cvb.j).c().i().b().f().e().h();
   public static final cva n = new cva.a(cvb.b).c().i().b().f().e().h();
   public static final cva o = new cva.a(cvb.b).c().i().b().f().h();
   public static final cva p = new cva.a(cvb.e).c().i().f().h();
   public static final cva q = new cva.a(cvb.b).h();
   public static final cva r = new cva.a(cvb.k).h();
   public static final cva s = new cva.a(cvb.l).h();
   public static final cva t = new cva.a(cvb.c).h();
   public static final cva u = new cva.a(cvb.g).h();
   public static final cva v = new cva.a(cvb.d).h();
   public static final cva w = new cva.a(cvb.t).h();
   public static final cva x = new cva.a(cvb.z).h();
   public static final cva y = new cva.a(cvb.o).d().h();
   public static final cva z = new cva.a(cvb.o).h();
   public static final cva A = new cva.a(cvb.o).d().f().c().h();
   public static final cva B = new cva.a(cvb.o).d().f().h();
   public static final cva C = new cva.a(cvb.e).d().h();
   public static final cva D = new cva.a(cvb.f).d().i().h();
   public static final cva E = new cva.a(cvb.i).d().i().f().h();
   public static final cva F = new cva.a(cvb.b).i().h();
   public static final cva G = new cva.a(cvb.g).i().h();
   public static final cva H = new cva.a(cvb.i).i().f().h();
   public static final cva I = new cva.a(cvb.m).h();
   public static final cva J = new cva.a(cvb.h).h();
   public static final cva K = new cva.a(cvb.j).h();
   public static final cva L = new cva.a(cvb.h).g().h();
   public static final cva M = new cva.a(cvb.b).g().h();
   public static final cva N = new cva.a(cvb.m).g().h();
   public static final cva O = new cva.a(cvb.i).f().h();
   public static final cva P = new cva.a(cvb.i).f().h();
   public static final cva Q = new cva.a(cvb.i).f().h();
   public static final cva R = new cva.a(cvb.b).f().h();
   private final cvb S;
   private final cvc T;
   private final boolean U;
   private final boolean V;
   private final boolean W;
   private final boolean X;
   private final boolean Y;
   private final boolean Z;

   public cva(cvb var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6, boolean var7, cvc var8) {
      this.S = _snowman;
      this.W = _snowman;
      this.Z = _snowman;
      this.U = _snowman;
      this.X = _snowman;
      this.V = _snowman;
      this.Y = _snowman;
      this.T = _snowman;
   }

   public boolean a() {
      return this.W;
   }

   public boolean b() {
      return this.Z;
   }

   public boolean c() {
      return this.U;
   }

   public boolean d() {
      return this.V;
   }

   public boolean e() {
      return this.Y;
   }

   public boolean f() {
      return this.X;
   }

   public cvc g() {
      return this.T;
   }

   public cvb h() {
      return this.S;
   }

   public static class a {
      private cvc a;
      private boolean b;
      private boolean c;
      private boolean d;
      private boolean e;
      private boolean f;
      private final cvb g;
      private boolean h;

      public a(cvb var1) {
         this.a = cvc.a;
         this.b = true;
         this.f = true;
         this.h = true;
         this.g = _snowman;
      }

      public cva.a a() {
         this.d = true;
         return this;
      }

      public cva.a b() {
         this.f = false;
         return this;
      }

      public cva.a c() {
         this.b = false;
         return this;
      }

      private cva.a i() {
         this.h = false;
         return this;
      }

      protected cva.a d() {
         this.c = true;
         return this;
      }

      public cva.a e() {
         this.e = true;
         return this;
      }

      protected cva.a f() {
         this.a = cvc.b;
         return this;
      }

      protected cva.a g() {
         this.a = cvc.c;
         return this;
      }

      public cva h() {
         return new cva(this.g, this.d, this.f, this.b, this.h, this.c, this.e, this.a);
      }
   }
}
