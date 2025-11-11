public enum cfh implements afs {
   a("harp", adq.jv),
   b("basedrum", adq.jp),
   c("snare", adq.jy),
   d("hat", adq.jw),
   e("bass", adq.jq),
   f("flute", adq.jt),
   g("bell", adq.jr),
   h("guitar", adq.ju),
   i("chime", adq.js),
   j("xylophone", adq.jz),
   k("iron_xylophone", adq.jA),
   l("cow_bell", adq.jB),
   m("didgeridoo", adq.jC),
   n("bit", adq.jD),
   o("banjo", adq.jE),
   p("pling", adq.jx);

   private final String q;
   private final adp r;

   private cfh(String var3, adp var4) {
      this.q = _snowman;
      this.r = _snowman;
   }

   @Override
   public String a() {
      return this.q;
   }

   public adp b() {
      return this.r;
   }

   public static cfh a(ceh var0) {
      if (_snowman.a(bup.cG)) {
         return f;
      } else if (_snowman.a(bup.bE)) {
         return g;
      } else if (_snowman.a(aed.b)) {
         return h;
      } else if (_snowman.a(bup.gT)) {
         return i;
      } else if (_snowman.a(bup.iM)) {
         return j;
      } else if (_snowman.a(bup.bF)) {
         return k;
      } else if (_snowman.a(bup.cM)) {
         return l;
      } else if (_snowman.a(bup.cK)) {
         return m;
      } else if (_snowman.a(bup.en)) {
         return n;
      } else if (_snowman.a(bup.gA)) {
         return o;
      } else if (_snowman.a(bup.cS)) {
         return p;
      } else {
         cva _snowman = _snowman.c();
         if (_snowman == cva.I) {
            return b;
         } else if (_snowman == cva.v) {
            return c;
         } else if (_snowman == cva.F) {
            return d;
         } else {
            return _snowman != cva.y && _snowman != cva.z ? a : e;
         }
      }
   }
}
