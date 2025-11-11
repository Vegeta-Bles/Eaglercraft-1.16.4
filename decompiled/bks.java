import javax.annotation.Nullable;

public abstract class bks {
   public static final bks[] a = new bks[12];
   public static final bks b = (new bks(0, "buildingBlocks") {
      @Override
      public bmb e() {
         return new bmb(bup.bG);
      }
   }).b("building_blocks");
   public static final bks c = new bks(1, "decorations") {
      @Override
      public bmb e() {
         return new bmb(bup.gX);
      }
   };
   public static final bks d = new bks(2, "redstone") {
      @Override
      public bmb e() {
         return new bmb(bmd.lP);
      }
   };
   public static final bks e = new bks(3, "transportation") {
      @Override
      public bmb e() {
         return new bmb(bup.aN);
      }
   };
   public static final bks f = new bks(6, "misc") {
      @Override
      public bmb e() {
         return new bmb(bmd.lM);
      }
   };
   public static final bks g = (new bks(5, "search") {
      @Override
      public bmb e() {
         return new bmb(bmd.mh);
      }
   }).a("item_search.png");
   public static final bks h = new bks(7, "food") {
      @Override
      public bmb e() {
         return new bmb(bmd.kb);
      }
   };
   public static final bks i = (new bks(8, "tools") {
      @Override
      public bmb e() {
         return new bmb(bmd.kD);
      }
   }).a(new bpt[]{bpt.n, bpt.g, bpt.h, bpt.j});
   public static final bks j = (new bks(9, "combat") {
      @Override
      public bmb e() {
         return new bmb(bmd.kv);
      }
   }).a(new bpt[]{bpt.n, bpt.a, bpt.b, bpt.e, bpt.c, bpt.d, bpt.k, bpt.f, bpt.l, bpt.j, bpt.i, bpt.m});
   public static final bks k = new bks(10, "brewing") {
      @Override
      public bmb e() {
         return bnv.a(new bmb(bmd.nv), bnw.b);
      }
   };
   public static final bks l = f;
   public static final bks m = new bks(4, "hotbar") {
      @Override
      public bmb e() {
         return new bmb(bup.bI);
      }

      @Override
      public void a(gj<bmb> var1) {
         throw new RuntimeException("Implement exception client-side.");
      }

      @Override
      public boolean m() {
         return true;
      }
   };
   public static final bks n = (new bks(11, "inventory") {
      @Override
      public bmb e() {
         return new bmb(bup.bR);
      }
   }).a("inventory.png").j().h();
   private final int o;
   private final String p;
   private final nr q;
   private String r;
   private String s = "items.png";
   private boolean t = true;
   private boolean u = true;
   private bpt[] v = new bpt[0];
   private bmb w;

   public bks(int var1, String var2) {
      this.o = _snowman;
      this.p = _snowman;
      this.q = new of("itemGroup." + _snowman);
      this.w = bmb.b;
      a[_snowman] = this;
   }

   public int a() {
      return this.o;
   }

   public String b() {
      return this.r == null ? this.p : this.r;
   }

   public nr c() {
      return this.q;
   }

   public bmb d() {
      if (this.w.a()) {
         this.w = this.e();
      }

      return this.w;
   }

   public abstract bmb e();

   public String f() {
      return this.s;
   }

   public bks a(String var1) {
      this.s = _snowman;
      return this;
   }

   public bks b(String var1) {
      this.r = _snowman;
      return this;
   }

   public boolean g() {
      return this.u;
   }

   public bks h() {
      this.u = false;
      return this;
   }

   public boolean i() {
      return this.t;
   }

   public bks j() {
      this.t = false;
      return this;
   }

   public int k() {
      return this.o % 6;
   }

   public boolean l() {
      return this.o < 6;
   }

   public boolean m() {
      return this.k() == 5;
   }

   public bpt[] n() {
      return this.v;
   }

   public bks a(bpt... var1) {
      this.v = _snowman;
      return this;
   }

   public boolean a(@Nullable bpt var1) {
      if (_snowman != null) {
         for (bpt _snowman : this.v) {
            if (_snowman == _snowman) {
               return true;
            }
         }
      }

      return false;
   }

   public void a(gj<bmb> var1) {
      for (blx _snowman : gm.T) {
         _snowman.a(this, _snowman);
      }
   }
}
