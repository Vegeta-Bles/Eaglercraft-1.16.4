package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.command.CommandSource;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class DatapackCommand {
   private static final DynamicCommandExceptionType UNKNOWN_DATAPACK_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.datapack.unknown", _snowman)
   );
   private static final DynamicCommandExceptionType ALREADY_ENABLED_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.datapack.enable.failed", _snowman)
   );
   private static final DynamicCommandExceptionType ALREADY_DISABLED_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.datapack.disable.failed", _snowman)
   );
   private static final SuggestionProvider<ServerCommandSource> ENABLED_CONTAINERS_SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> CommandSource.suggestMatching(
         ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getDataPackManager().getEnabledNames().stream().map(StringArgumentType::escapeIfRequired),
         _snowmanx
      );
   private static final SuggestionProvider<ServerCommandSource> DISABLED_CONTAINERS_SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> {
      ResourcePackManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getDataPackManager();
      Collection<String> _snowmanxxx = _snowmanxx.getEnabledNames();
      return CommandSource.suggestMatching(_snowmanxx.getNames().stream().filter(_snowmanxxxx -> !_snowman.contains(_snowmanxxxx)).map(StringArgumentType::escapeIfRequired), _snowmanx);
   };

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("datapack")
                     .requires(_snowman -> _snowman.hasPermissionLevel(2)))
                  .then(
                     CommandManager.literal("enable")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                             "name", StringArgumentType.string()
                                          )
                                          .suggests(DISABLED_CONTAINERS_SUGGESTION_PROVIDER)
                                          .executes(
                                             _snowman -> executeEnable(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   getPackContainer(_snowman, "name", true),
                                                   (_snowmanx, _snowmanxx) -> _snowmanxx.getInitialPosition().insert(_snowmanx, _snowmanxx, _snowmanxxx -> _snowmanxxx, false)
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("after")
                                             .then(
                                                CommandManager.argument("existing", StringArgumentType.string())
                                                   .suggests(ENABLED_CONTAINERS_SUGGESTION_PROVIDER)
                                                   .executes(
                                                      _snowman -> executeEnable(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            getPackContainer(_snowman, "name", true),
                                                            (_snowmanxxx, _snowmanxx) -> _snowmanxxx.add(_snowmanxxx.indexOf(getPackContainer(_snowman, "existing", false)) + 1, _snowmanxx)
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("before")
                                          .then(
                                             CommandManager.argument("existing", StringArgumentType.string())
                                                .suggests(ENABLED_CONTAINERS_SUGGESTION_PROVIDER)
                                                .executes(
                                                   _snowman -> executeEnable(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         getPackContainer(_snowman, "name", true),
                                                         (_snowmanxxx, _snowmanxx) -> _snowmanxxx.add(_snowmanxxx.indexOf(getPackContainer(_snowman, "existing", false)), _snowmanxx)
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("last")
                                       .executes(_snowman -> executeEnable((ServerCommandSource)_snowman.getSource(), getPackContainer(_snowman, "name", true), List::add))
                                 ))
                              .then(
                                 CommandManager.literal("first")
                                    .executes(
                                       _snowman -> executeEnable((ServerCommandSource)_snowman.getSource(), getPackContainer(_snowman, "name", true), (_snowmanx, _snowmanxx) -> _snowmanx.add(0, _snowmanxx))
                                    )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("disable")
                     .then(
                        CommandManager.argument("name", StringArgumentType.string())
                           .suggests(ENABLED_CONTAINERS_SUGGESTION_PROVIDER)
                           .executes(_snowman -> executeDisable((ServerCommandSource)_snowman.getSource(), getPackContainer(_snowman, "name", false)))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("list").executes(_snowman -> executeList((ServerCommandSource)_snowman.getSource())))
                     .then(CommandManager.literal("available").executes(_snowman -> executeListAvailable((ServerCommandSource)_snowman.getSource()))))
                  .then(CommandManager.literal("enabled").executes(_snowman -> executeListEnabled((ServerCommandSource)_snowman.getSource())))
            )
      );
   }

   private static int executeEnable(ServerCommandSource source, ResourcePackProfile container, DatapackCommand.PackAdder packAdder) throws CommandSyntaxException {
      ResourcePackManager _snowman = source.getMinecraftServer().getDataPackManager();
      List<ResourcePackProfile> _snowmanx = Lists.newArrayList(_snowman.getEnabledProfiles());
      packAdder.apply(_snowmanx, container);
      source.sendFeedback(new TranslatableText("commands.datapack.modify.enable", container.getInformationText(true)), true);
      ReloadCommand.method_29480(_snowmanx.stream().map(ResourcePackProfile::getName).collect(Collectors.toList()), source);
      return _snowmanx.size();
   }

   private static int executeDisable(ServerCommandSource source, ResourcePackProfile container) {
      ResourcePackManager _snowman = source.getMinecraftServer().getDataPackManager();
      List<ResourcePackProfile> _snowmanx = Lists.newArrayList(_snowman.getEnabledProfiles());
      _snowmanx.remove(container);
      source.sendFeedback(new TranslatableText("commands.datapack.modify.disable", container.getInformationText(true)), true);
      ReloadCommand.method_29480(_snowmanx.stream().map(ResourcePackProfile::getName).collect(Collectors.toList()), source);
      return _snowmanx.size();
   }

   private static int executeList(ServerCommandSource source) {
      return executeListEnabled(source) + executeListAvailable(source);
   }

   private static int executeListAvailable(ServerCommandSource source) {
      ResourcePackManager _snowman = source.getMinecraftServer().getDataPackManager();
      _snowman.scanPacks();
      Collection<? extends ResourcePackProfile> _snowmanx = _snowman.getEnabledProfiles();
      Collection<? extends ResourcePackProfile> _snowmanxx = _snowman.getProfiles();
      List<ResourcePackProfile> _snowmanxxx = _snowmanxx.stream().filter(_snowmanxxxx -> !_snowman.contains(_snowmanxxxx)).collect(Collectors.toList());
      if (_snowmanxxx.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.datapack.list.available.none"), false);
      } else {
         source.sendFeedback(
            new TranslatableText("commands.datapack.list.available.success", _snowmanxxx.size(), Texts.join(_snowmanxxx, _snowmanxxxx -> _snowmanxxxx.getInformationText(false))), false
         );
      }

      return _snowmanxxx.size();
   }

   private static int executeListEnabled(ServerCommandSource source) {
      ResourcePackManager _snowman = source.getMinecraftServer().getDataPackManager();
      _snowman.scanPacks();
      Collection<? extends ResourcePackProfile> _snowmanx = _snowman.getEnabledProfiles();
      if (_snowmanx.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.datapack.list.enabled.none"), false);
      } else {
         source.sendFeedback(
            new TranslatableText("commands.datapack.list.enabled.success", _snowmanx.size(), Texts.join(_snowmanx, _snowmanxx -> _snowmanxx.getInformationText(true))), false
         );
      }

      return _snowmanx.size();
   }

   private static ResourcePackProfile getPackContainer(CommandContext<ServerCommandSource> context, String name, boolean enable) throws CommandSyntaxException {
      String _snowman = StringArgumentType.getString(context, name);
      ResourcePackManager _snowmanx = ((ServerCommandSource)context.getSource()).getMinecraftServer().getDataPackManager();
      ResourcePackProfile _snowmanxx = _snowmanx.getProfile(_snowman);
      if (_snowmanxx == null) {
         throw UNKNOWN_DATAPACK_EXCEPTION.create(_snowman);
      } else {
         boolean _snowmanxxx = _snowmanx.getEnabledProfiles().contains(_snowmanxx);
         if (enable && _snowmanxxx) {
            throw ALREADY_ENABLED_EXCEPTION.create(_snowman);
         } else if (!enable && !_snowmanxxx) {
            throw ALREADY_DISABLED_EXCEPTION.create(_snowman);
         } else {
            return _snowmanxx;
         }
      }
   }

   interface PackAdder {
      void apply(List<ResourcePackProfile> var1, ResourcePackProfile var2) throws CommandSyntaxException;
   }
}
