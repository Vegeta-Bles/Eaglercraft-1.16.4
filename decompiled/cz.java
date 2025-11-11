import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class cz extends RuntimeException {
   private final nr a;

   public cz(nr var1) {
      super(_snowman.getString(), null, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES, CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES);
      this.a = _snowman;
   }

   public nr a() {
      return this.a;
   }
}
