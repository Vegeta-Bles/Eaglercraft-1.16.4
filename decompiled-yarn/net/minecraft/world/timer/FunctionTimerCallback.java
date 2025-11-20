package net.minecraft.world.timer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;

public class FunctionTimerCallback implements TimerCallback<MinecraftServer> {
   private final Identifier name;

   public FunctionTimerCallback(Identifier _snowman) {
      this.name = _snowman;
   }

   public void call(MinecraftServer _snowman, Timer<MinecraftServer> _snowman, long _snowman) {
      CommandFunctionManager _snowmanxxx = _snowman.getCommandFunctionManager();
      _snowmanxxx.getFunction(this.name).ifPresent(_snowmanxxxx -> _snowman.execute(_snowmanxxxx, _snowman.getTaggedFunctionSource()));
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionTimerCallback> {
      public Serializer() {
         super(new Identifier("function"), FunctionTimerCallback.class);
      }

      public void serialize(CompoundTag _snowman, FunctionTimerCallback _snowman) {
         _snowman.putString("Name", _snowman.name.toString());
      }

      public FunctionTimerCallback deserialize(CompoundTag _snowman) {
         Identifier _snowmanx = new Identifier(_snowman.getString("Name"));
         return new FunctionTimerCallback(_snowmanx);
      }
   }
}
