package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class LocateBiomeCommand {
   public static final DynamicCommandExceptionType INVALID_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.locatebiome.invalid", _snowman)
   );
   private static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.locatebiome.notFound", _snowman)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("locatebiome").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("biome", IdentifierArgumentType.identifier())
                  .suggests(SuggestionProviders.ALL_BIOMES)
                  .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), (Identifier)_snowman.getArgument("biome", Identifier.class)))
            )
      );
   }

   private static int execute(ServerCommandSource source, Identifier _snowman) throws CommandSyntaxException {
      Biome _snowmanx = source.getMinecraftServer().getRegistryManager().get(Registry.BIOME_KEY).getOrEmpty(_snowman).orElseThrow(() -> INVALID_EXCEPTION.create(_snowman));
      BlockPos _snowmanxx = new BlockPos(source.getPosition());
      BlockPos _snowmanxxx = source.getWorld().locateBiome(_snowmanx, _snowmanxx, 6400, 8);
      String _snowmanxxxx = _snowman.toString();
      if (_snowmanxxx == null) {
         throw NOT_FOUND_EXCEPTION.create(_snowmanxxxx);
      } else {
         return LocateCommand.sendCoordinates(source, _snowmanxxxx, _snowmanxx, _snowmanxxx, "commands.locatebiome.success");
      }
   }
}
