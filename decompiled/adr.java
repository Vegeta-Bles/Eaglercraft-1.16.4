import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum adr {
   a("master"),
   b("music"),
   c("record"),
   d("weather"),
   e("block"),
   f("hostile"),
   g("neutral"),
   h("player"),
   i("ambient"),
   j("voice");

   private static final Map<String, adr> k = Arrays.stream(values()).collect(Collectors.toMap(adr::a, Function.identity()));
   private final String l;

   private adr(String var3) {
      this.l = _snowman;
   }

   public String a() {
      return this.l;
   }
}
