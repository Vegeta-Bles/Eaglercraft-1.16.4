/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 */
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.command.CommandSource;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ReloadCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class DatapackCommand {
    private static final DynamicCommandExceptionType UNKNOWN_DATAPACK_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.datapack.unknown", object));
    private static final DynamicCommandExceptionType ALREADY_ENABLED_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.datapack.enable.failed", object));
    private static final DynamicCommandExceptionType ALREADY_DISABLED_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("commands.datapack.disable.failed", object));
    private static final SuggestionProvider<ServerCommandSource> ENABLED_CONTAINERS_SUGGESTION_PROVIDER = (commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getDataPackManager().getEnabledNames().stream().map(StringArgumentType::escapeIfRequired), suggestionsBuilder);
    private static final SuggestionProvider<ServerCommandSource> DISABLED_CONTAINERS_SUGGESTION_PROVIDER = (commandContext, suggestionsBuilder) -> {
        ResourcePackManager resourcePackManager = ((ServerCommandSource)commandContext.getSource()).getMinecraftServer().getDataPackManager();
        Collection<String> _snowman2 = resourcePackManager.getEnabledNames();
        return CommandSource.suggestMatching(resourcePackManager.getNames().stream().filter(string -> !_snowman2.contains(string)).map(StringArgumentType::escapeIfRequired), suggestionsBuilder);
    };

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("datapack").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.literal("enable").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("name", StringArgumentType.string()).suggests(DISABLED_CONTAINERS_SUGGESTION_PROVIDER).executes(commandContext -> DatapackCommand.executeEnable((ServerCommandSource)commandContext.getSource(), DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "name", true), (list, resourcePackProfile2) -> resourcePackProfile2.getInitialPosition().insert(list, resourcePackProfile2, resourcePackProfile -> resourcePackProfile, false)))).then(CommandManager.literal("after").then(CommandManager.argument("existing", StringArgumentType.string()).suggests(ENABLED_CONTAINERS_SUGGESTION_PROVIDER).executes(commandContext -> DatapackCommand.executeEnable((ServerCommandSource)commandContext.getSource(), DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "name", true), (list, resourcePackProfile) -> list.add(list.indexOf(DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "existing", false)) + 1, resourcePackProfile)))))).then(CommandManager.literal("before").then(CommandManager.argument("existing", StringArgumentType.string()).suggests(ENABLED_CONTAINERS_SUGGESTION_PROVIDER).executes(commandContext -> DatapackCommand.executeEnable((ServerCommandSource)commandContext.getSource(), DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "name", true), (list, resourcePackProfile) -> list.add(list.indexOf(DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "existing", false)), resourcePackProfile)))))).then(CommandManager.literal("last").executes(commandContext -> DatapackCommand.executeEnable((ServerCommandSource)commandContext.getSource(), DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "name", true), List::add)))).then(CommandManager.literal("first").executes(commandContext -> DatapackCommand.executeEnable((ServerCommandSource)commandContext.getSource(), DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "name", true), (list, resourcePackProfile) -> list.add(0, resourcePackProfile))))))).then(CommandManager.literal("disable").then(CommandManager.argument("name", StringArgumentType.string()).suggests(ENABLED_CONTAINERS_SUGGESTION_PROVIDER).executes(commandContext -> DatapackCommand.executeDisable((ServerCommandSource)commandContext.getSource(), DatapackCommand.getPackContainer((CommandContext<ServerCommandSource>)commandContext, "name", false)))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("list").executes(commandContext -> DatapackCommand.executeList((ServerCommandSource)commandContext.getSource()))).then(CommandManager.literal("available").executes(commandContext -> DatapackCommand.executeListAvailable((ServerCommandSource)commandContext.getSource())))).then(CommandManager.literal("enabled").executes(commandContext -> DatapackCommand.executeListEnabled((ServerCommandSource)commandContext.getSource())))));
    }

    private static int executeEnable(ServerCommandSource source, ResourcePackProfile container, PackAdder packAdder) throws CommandSyntaxException {
        ResourcePackManager resourcePackManager = source.getMinecraftServer().getDataPackManager();
        ArrayList _snowman2 = Lists.newArrayList(resourcePackManager.getEnabledProfiles());
        packAdder.apply(_snowman2, container);
        source.sendFeedback(new TranslatableText("commands.datapack.modify.enable", container.getInformationText(true)), true);
        ReloadCommand.method_29480(_snowman2.stream().map(ResourcePackProfile::getName).collect(Collectors.toList()), source);
        return _snowman2.size();
    }

    private static int executeDisable(ServerCommandSource source, ResourcePackProfile container) {
        ResourcePackManager resourcePackManager = source.getMinecraftServer().getDataPackManager();
        ArrayList _snowman2 = Lists.newArrayList(resourcePackManager.getEnabledProfiles());
        _snowman2.remove(container);
        source.sendFeedback(new TranslatableText("commands.datapack.modify.disable", container.getInformationText(true)), true);
        ReloadCommand.method_29480(_snowman2.stream().map(ResourcePackProfile::getName).collect(Collectors.toList()), source);
        return _snowman2.size();
    }

    private static int executeList(ServerCommandSource source) {
        return DatapackCommand.executeListEnabled(source) + DatapackCommand.executeListAvailable(source);
    }

    private static int executeListAvailable(ServerCommandSource source) {
        ResourcePackManager resourcePackManager = source.getMinecraftServer().getDataPackManager();
        resourcePackManager.scanPacks();
        Collection<ResourcePackProfile> _snowman2 = resourcePackManager.getEnabledProfiles();
        Collection<ResourcePackProfile> _snowman3 = resourcePackManager.getProfiles();
        List _snowman4 = _snowman3.stream().filter(resourcePackProfile -> !_snowman2.contains(resourcePackProfile)).collect(Collectors.toList());
        if (_snowman4.isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.datapack.list.available.none"), false);
        } else {
            source.sendFeedback(new TranslatableText("commands.datapack.list.available.success", _snowman4.size(), Texts.join(_snowman4, resourcePackProfile -> resourcePackProfile.getInformationText(false))), false);
        }
        return _snowman4.size();
    }

    private static int executeListEnabled(ServerCommandSource source) {
        ResourcePackManager resourcePackManager = source.getMinecraftServer().getDataPackManager();
        resourcePackManager.scanPacks();
        Collection<ResourcePackProfile> _snowman2 = resourcePackManager.getEnabledProfiles();
        if (_snowman2.isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.datapack.list.enabled.none"), false);
        } else {
            source.sendFeedback(new TranslatableText("commands.datapack.list.enabled.success", _snowman2.size(), Texts.join(_snowman2, resourcePackProfile -> resourcePackProfile.getInformationText(true))), false);
        }
        return _snowman2.size();
    }

    private static ResourcePackProfile getPackContainer(CommandContext<ServerCommandSource> context, String name, boolean enable) throws CommandSyntaxException {
        String string = StringArgumentType.getString(context, (String)name);
        ResourcePackManager _snowman2 = ((ServerCommandSource)context.getSource()).getMinecraftServer().getDataPackManager();
        ResourcePackProfile _snowman3 = _snowman2.getProfile(string);
        if (_snowman3 == null) {
            throw UNKNOWN_DATAPACK_EXCEPTION.create((Object)string);
        }
        boolean _snowman4 = _snowman2.getEnabledProfiles().contains(_snowman3);
        if (enable && _snowman4) {
            throw ALREADY_ENABLED_EXCEPTION.create((Object)string);
        }
        if (!enable && !_snowman4) {
            throw ALREADY_DISABLED_EXCEPTION.create((Object)string);
        }
        return _snowman3;
    }

    static interface PackAdder {
        public void apply(List<ResourcePackProfile> var1, ResourcePackProfile var2) throws CommandSyntaxException;
    }
}

