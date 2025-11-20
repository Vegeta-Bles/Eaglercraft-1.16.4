package net.minecraft.server.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.server.dedicated.command.BanCommand;
import net.minecraft.server.dedicated.command.BanIpCommand;
import net.minecraft.server.dedicated.command.BanListCommand;
import net.minecraft.server.dedicated.command.DeOpCommand;
import net.minecraft.server.dedicated.command.OpCommand;
import net.minecraft.server.dedicated.command.PardonCommand;
import net.minecraft.server.dedicated.command.PardonIpCommand;
import net.minecraft.server.dedicated.command.SaveAllCommand;
import net.minecraft.server.dedicated.command.SaveOffCommand;
import net.minecraft.server.dedicated.command.SaveOnCommand;
import net.minecraft.server.dedicated.command.SetIdleTimeoutCommand;
import net.minecraft.server.dedicated.command.StopCommand;
import net.minecraft.server.dedicated.command.WhitelistCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher();

   public CommandManager(CommandManager.RegistrationEnvironment environment) {
      AdvancementCommand.register(this.dispatcher);
      AttributeCommand.register(this.dispatcher);
      ExecuteCommand.register(this.dispatcher);
      BossBarCommand.register(this.dispatcher);
      ClearCommand.register(this.dispatcher);
      CloneCommand.register(this.dispatcher);
      DataCommand.register(this.dispatcher);
      DatapackCommand.register(this.dispatcher);
      DebugCommand.register(this.dispatcher);
      DefaultGameModeCommand.register(this.dispatcher);
      DifficultyCommand.register(this.dispatcher);
      EffectCommand.register(this.dispatcher);
      MeCommand.register(this.dispatcher);
      EnchantCommand.register(this.dispatcher);
      ExperienceCommand.register(this.dispatcher);
      FillCommand.register(this.dispatcher);
      ForceLoadCommand.register(this.dispatcher);
      FunctionCommand.register(this.dispatcher);
      GameModeCommand.register(this.dispatcher);
      GameRuleCommand.register(this.dispatcher);
      GiveCommand.register(this.dispatcher);
      HelpCommand.register(this.dispatcher);
      KickCommand.register(this.dispatcher);
      KillCommand.register(this.dispatcher);
      ListCommand.register(this.dispatcher);
      LocateCommand.register(this.dispatcher);
      LocateBiomeCommand.register(this.dispatcher);
      LootCommand.register(this.dispatcher);
      MessageCommand.register(this.dispatcher);
      ParticleCommand.register(this.dispatcher);
      PlaySoundCommand.register(this.dispatcher);
      ReloadCommand.register(this.dispatcher);
      RecipeCommand.register(this.dispatcher);
      ReplaceItemCommand.register(this.dispatcher);
      SayCommand.register(this.dispatcher);
      ScheduleCommand.register(this.dispatcher);
      ScoreboardCommand.register(this.dispatcher);
      SeedCommand.register(this.dispatcher, environment != CommandManager.RegistrationEnvironment.INTEGRATED);
      SetBlockCommand.register(this.dispatcher);
      SpawnPointCommand.register(this.dispatcher);
      SetWorldSpawnCommand.register(this.dispatcher);
      SpectateCommand.register(this.dispatcher);
      SpreadPlayersCommand.register(this.dispatcher);
      StopSoundCommand.register(this.dispatcher);
      SummonCommand.register(this.dispatcher);
      TagCommand.register(this.dispatcher);
      TeamCommand.register(this.dispatcher);
      TeamMsgCommand.register(this.dispatcher);
      TeleportCommand.register(this.dispatcher);
      TellRawCommand.register(this.dispatcher);
      TimeCommand.register(this.dispatcher);
      TitleCommand.register(this.dispatcher);
      TriggerCommand.register(this.dispatcher);
      WeatherCommand.register(this.dispatcher);
      WorldBorderCommand.register(this.dispatcher);
      if (SharedConstants.isDevelopment) {
         TestCommand.register(this.dispatcher);
      }

      if (environment.dedicated) {
         BanIpCommand.register(this.dispatcher);
         BanListCommand.register(this.dispatcher);
         BanCommand.register(this.dispatcher);
         DeOpCommand.register(this.dispatcher);
         OpCommand.register(this.dispatcher);
         PardonCommand.register(this.dispatcher);
         PardonIpCommand.register(this.dispatcher);
         SaveAllCommand.register(this.dispatcher);
         SaveOffCommand.register(this.dispatcher);
         SaveOnCommand.register(this.dispatcher);
         SetIdleTimeoutCommand.register(this.dispatcher);
         StopCommand.register(this.dispatcher);
         WhitelistCommand.register(this.dispatcher);
      }

      if (environment.integrated) {
         PublishCommand.register(this.dispatcher);
      }

      this.dispatcher
         .findAmbiguities(
            (_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> LOGGER.warn(
                  "Ambiguity between arguments {} and {} with inputs: {}", this.dispatcher.getPath(_snowmanx), this.dispatcher.getPath(_snowmanxx), _snowmanxxx
               )
         );
      this.dispatcher.setConsumer((_snowman, _snowmanx, _snowmanxx) -> ((ServerCommandSource)_snowman.getSource()).onCommandComplete(_snowman, _snowmanx, _snowmanxx));
   }

   public int execute(ServerCommandSource commandSource, String command) {
      StringReader _snowman = new StringReader(command);
      if (_snowman.canRead() && _snowman.peek() == '/') {
         _snowman.skip();
      }

      commandSource.getMinecraftServer().getProfiler().push(command);

      byte var20;
      try {
         return this.dispatcher.execute(_snowman, commandSource);
      } catch (CommandException var13) {
         commandSource.sendError(var13.getTextMessage());
         return 0;
      } catch (CommandSyntaxException var14) {
         commandSource.sendError(Texts.toText(var14.getRawMessage()));
         if (var14.getInput() != null && var14.getCursor() >= 0) {
            int _snowmanx = Math.min(var14.getInput().length(), var14.getCursor());
            MutableText _snowmanxx = new LiteralText("")
               .formatted(Formatting.GRAY)
               .styled(_snowmanxxx -> _snowmanxxx.withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)));
            if (_snowmanx > 10) {
               _snowmanxx.append("...");
            }

            _snowmanxx.append(var14.getInput().substring(Math.max(0, _snowmanx - 10), _snowmanx));
            if (_snowmanx < var14.getInput().length()) {
               Text _snowmanxxx = new LiteralText(var14.getInput().substring(_snowmanx)).formatted(new Formatting[]{Formatting.RED, Formatting.UNDERLINE});
               _snowmanxx.append(_snowmanxxx);
            }

            _snowmanxx.append(new TranslatableText("command.context.here").formatted(new Formatting[]{Formatting.RED, Formatting.ITALIC}));
            commandSource.sendError(_snowmanxx);
         }

         return 0;
      } catch (Exception var15) {
         MutableText _snowmanxxx = new LiteralText(var15.getMessage() == null ? var15.getClass().getName() : var15.getMessage());
         if (LOGGER.isDebugEnabled()) {
            LOGGER.error("Command exception: {}", command, var15);
            StackTraceElement[] _snowmanxxxx = var15.getStackTrace();

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < Math.min(_snowmanxxxx.length, 3); _snowmanxxxxx++) {
               _snowmanxxx.append("\n\n")
                  .append(_snowmanxxxx[_snowmanxxxxx].getMethodName())
                  .append("\n ")
                  .append(_snowmanxxxx[_snowmanxxxxx].getFileName())
                  .append(":")
                  .append(String.valueOf(_snowmanxxxx[_snowmanxxxxx].getLineNumber()));
            }
         }

         commandSource.sendError(new TranslatableText("command.failed").styled(_snowmanxxxx -> _snowmanxxxx.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, _snowman))));
         if (SharedConstants.isDevelopment) {
            commandSource.sendError(new LiteralText(Util.getInnermostMessage(var15)));
            LOGGER.error("'" + command + "' threw an exception", var15);
         }

         var20 = 0;
      } finally {
         commandSource.getMinecraftServer().getProfiler().pop();
      }

      return var20;
   }

   public void sendCommandTree(ServerPlayerEntity player) {
      Map<CommandNode<ServerCommandSource>, CommandNode<CommandSource>> _snowman = Maps.newHashMap();
      RootCommandNode<CommandSource> _snowmanx = new RootCommandNode();
      _snowman.put(this.dispatcher.getRoot(), _snowmanx);
      this.makeTreeForSource(this.dispatcher.getRoot(), _snowmanx, player.getCommandSource(), _snowman);
      player.networkHandler.sendPacket(new CommandTreeS2CPacket(_snowmanx));
   }

   private void makeTreeForSource(
      CommandNode<ServerCommandSource> tree,
      CommandNode<CommandSource> result,
      ServerCommandSource source,
      Map<CommandNode<ServerCommandSource>, CommandNode<CommandSource>> resultNodes
   ) {
      for (CommandNode<ServerCommandSource> _snowman : tree.getChildren()) {
         if (_snowman.canUse(source)) {
            ArgumentBuilder<CommandSource, ?> _snowmanx = _snowman.createBuilder();
            _snowmanx.requires(_snowmanxx -> true);
            if (_snowmanx.getCommand() != null) {
               _snowmanx.executes(_snowmanxx -> 0);
            }

            if (_snowmanx instanceof RequiredArgumentBuilder) {
               RequiredArgumentBuilder<CommandSource, ?> _snowmanxx = (RequiredArgumentBuilder<CommandSource, ?>)_snowmanx;
               if (_snowmanxx.getSuggestionsProvider() != null) {
                  _snowmanxx.suggests(SuggestionProviders.getLocalProvider(_snowmanxx.getSuggestionsProvider()));
               }
            }

            if (_snowmanx.getRedirect() != null) {
               _snowmanx.redirect(resultNodes.get(_snowmanx.getRedirect()));
            }

            CommandNode<CommandSource> _snowmanxx = _snowmanx.build();
            resultNodes.put(_snowman, _snowmanxx);
            result.addChild(_snowmanxx);
            if (!_snowman.getChildren().isEmpty()) {
               this.makeTreeForSource(_snowman, _snowmanxx, source, resultNodes);
            }
         }
      }
   }

   public static LiteralArgumentBuilder<ServerCommandSource> literal(String literal) {
      return LiteralArgumentBuilder.literal(literal);
   }

   public static <T> RequiredArgumentBuilder<ServerCommandSource, T> argument(String name, ArgumentType<T> type) {
      return RequiredArgumentBuilder.argument(name, type);
   }

   public static Predicate<String> getCommandValidator(CommandManager.CommandParser parser) {
      return _snowmanx -> {
         try {
            parser.parse(new StringReader(_snowmanx));
            return true;
         } catch (CommandSyntaxException var3) {
            return false;
         }
      };
   }

   public CommandDispatcher<ServerCommandSource> getDispatcher() {
      return this.dispatcher;
   }

   @Nullable
   public static <S> CommandSyntaxException getException(ParseResults<S> parse) {
      if (!parse.getReader().canRead()) {
         return null;
      } else if (parse.getExceptions().size() == 1) {
         return (CommandSyntaxException)parse.getExceptions().values().iterator().next();
      } else {
         return parse.getContext().getRange().isEmpty()
            ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(parse.getReader())
            : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(parse.getReader());
      }
   }

   public static void checkMissing() {
      RootCommandNode<ServerCommandSource> _snowman = new CommandManager(CommandManager.RegistrationEnvironment.ALL).getDispatcher().getRoot();
      Set<ArgumentType<?>> _snowmanx = ArgumentTypes.getAllArgumentTypes(_snowman);
      Set<ArgumentType<?>> _snowmanxx = _snowmanx.stream().filter(_snowmanxxx -> !ArgumentTypes.hasClass((ArgumentType<?>)_snowmanxxx)).collect(Collectors.toSet());
      if (!_snowmanxx.isEmpty()) {
         LOGGER.warn("Missing type registration for following arguments:\n {}", _snowmanxx.stream().map(_snowmanxxx -> "\t" + _snowmanxxx).collect(Collectors.joining(",\n")));
         throw new IllegalStateException("Unregistered argument types");
      }
   }

   @FunctionalInterface
   public interface CommandParser {
      void parse(StringReader var1) throws CommandSyntaxException;
   }

   public static enum RegistrationEnvironment {
      ALL(true, true),
      DEDICATED(false, true),
      INTEGRATED(true, false);

      private final boolean integrated;
      private final boolean dedicated;

      private RegistrationEnvironment(boolean integrated, boolean dedicated) {
         this.integrated = integrated;
         this.dedicated = dedicated;
      }
   }
}
