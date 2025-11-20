package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.SaveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReloadCommand {
   private static final Logger field_25343 = LogManager.getLogger();

   public static void method_29480(Collection<String> _snowman, ServerCommandSource _snowman) {
      _snowman.getMinecraftServer().reloadResources(_snowman).exceptionally(_snowmanxxx -> {
         field_25343.warn("Failed to execute reload", _snowmanxxx);
         _snowman.sendError(new TranslatableText("commands.reload.failure"));
         return null;
      });
   }

   private static Collection<String> method_29478(ResourcePackManager _snowman, SaveProperties _snowman, Collection<String> _snowman) {
      _snowman.scanPacks();
      Collection<String> _snowmanxxx = Lists.newArrayList(_snowman);
      Collection<String> _snowmanxxxx = _snowman.getDataPackSettings().getDisabled();

      for (String _snowmanxxxxx : _snowman.getNames()) {
         if (!_snowmanxxxx.contains(_snowmanxxxxx) && !_snowmanxxx.contains(_snowmanxxxxx)) {
            _snowmanxxx.add(_snowmanxxxxx);
         }
      }

      return _snowmanxxx;
   }

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("reload").requires(_snowman -> _snowman.hasPermissionLevel(2))).executes(_snowman -> {
            ServerCommandSource _snowmanx = (ServerCommandSource)_snowman.getSource();
            MinecraftServer _snowmanxx = _snowmanx.getMinecraftServer();
            ResourcePackManager _snowmanxxx = _snowmanxx.getDataPackManager();
            SaveProperties _snowmanxxxx = _snowmanxx.getSaveProperties();
            Collection<String> _snowmanxxxxx = _snowmanxxx.getEnabledNames();
            Collection<String> _snowmanxxxxxx = method_29478(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
            _snowmanx.sendFeedback(new TranslatableText("commands.reload.success"), true);
            method_29480(_snowmanxxxxxx, _snowmanx);
            return 0;
         })
      );
   }
}
