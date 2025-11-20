package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Map.Entry;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.StructureFeature;

public class LocateCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.locate.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("locate")
         .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2));

      for (Entry<String, StructureFeature<?>> _snowmanx : StructureFeature.STRUCTURES.entrySet()) {
         _snowman = (LiteralArgumentBuilder<ServerCommandSource>)_snowman.then(
            CommandManager.literal(_snowmanx.getKey()).executes(_snowmanxx -> execute((ServerCommandSource)_snowmanxx.getSource(), _snowman.getValue()))
         );
      }

      dispatcher.register(_snowman);
   }

   private static int execute(ServerCommandSource source, StructureFeature<?> _snowman) throws CommandSyntaxException {
      BlockPos _snowmanx = new BlockPos(source.getPosition());
      BlockPos _snowmanxx = source.getWorld().locateStructure(_snowman, _snowmanx, 100, false);
      if (_snowmanxx == null) {
         throw FAILED_EXCEPTION.create();
      } else {
         return sendCoordinates(source, _snowman.getName(), _snowmanx, _snowmanxx, "commands.locate.success");
      }
   }

   public static int sendCoordinates(ServerCommandSource source, String structure, BlockPos sourcePos, BlockPos structurePos, String successMessage) {
      int _snowman = MathHelper.floor(getDistance(sourcePos.getX(), sourcePos.getZ(), structurePos.getX(), structurePos.getZ()));
      Text _snowmanx = Texts.bracketed(new TranslatableText("chat.coordinates", structurePos.getX(), "~", structurePos.getZ()))
         .styled(
            _snowmanxx -> _snowmanxx.withColor(Formatting.GREEN)
                  .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + structurePos.getX() + " ~ " + structurePos.getZ()))
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.coordinates.tooltip")))
         );
      source.sendFeedback(new TranslatableText(successMessage, structure, _snowmanx, _snowman), false);
      return _snowman;
   }

   private static float getDistance(int x1, int y1, int x2, int y2) {
      int _snowman = x2 - x1;
      int _snowmanx = y2 - y1;
      return MathHelper.sqrt((float)(_snowman * _snowman + _snowmanx * _snowmanx));
   }
}
