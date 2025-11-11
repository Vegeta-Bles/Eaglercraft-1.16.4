public class nq {
   public static final nr a = new of("options.on");
   public static final nr b = new of("options.off");
   public static final nr c = new of("gui.done");
   public static final nr d = new of("gui.cancel");
   public static final nr e = new of("gui.yes");
   public static final nr f = new of("gui.no");
   public static final nr g = new of("gui.proceed");
   public static final nr h = new of("gui.back");
   public static final nr i = new of("connect.failed");

   public static nr a(boolean var0) {
      return _snowman ? a : b;
   }

   public static nx a(nr var0, boolean var1) {
      return new of(_snowman ? "options.on.composed" : "options.off.composed", _snowman);
   }
}
