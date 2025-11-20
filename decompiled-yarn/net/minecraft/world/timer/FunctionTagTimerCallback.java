package net.minecraft.world.timer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class FunctionTagTimerCallback implements TimerCallback<MinecraftServer> {
   private final Identifier name;

   public FunctionTagTimerCallback(Identifier _snowman) {
      this.name = _snowman;
   }

   public void call(MinecraftServer _snowman, Timer<MinecraftServer> _snowman, long _snowman) {
      CommandFunctionManager _snowmanxxx = _snowman.getCommandFunctionManager();
      Tag<CommandFunction> _snowmanxxxx = _snowmanxxx.method_29462(this.name);

      for (CommandFunction _snowmanxxxxx : _snowmanxxxx.values()) {
         _snowmanxxx.execute(_snowmanxxxxx, _snowmanxxx.getTaggedFunctionSource());
      }
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionTagTimerCallback> {
      public Serializer() {
         super(new Identifier("function_tag"), FunctionTagTimerCallback.class);
      }

      public void serialize(CompoundTag _snowman, FunctionTagTimerCallback _snowman) {
         _snowman.putString("Name", _snowman.name.toString());
      }

      public FunctionTagTimerCallback deserialize(CompoundTag _snowman) {
         Identifier _snowmanx = new Identifier(_snowman.getString("Name"));
         return new FunctionTagTimerCallback(_snowmanx);
      }
   }
}
