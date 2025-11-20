/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.SaveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReloadCommand {
    private static final Logger field_25343 = LogManager.getLogger();

    public static void method_29480(Collection<String> collection, ServerCommandSource serverCommandSource) {
        serverCommandSource.getMinecraftServer().reloadResources(collection).exceptionally(throwable -> {
            field_25343.warn("Failed to execute reload", throwable);
            serverCommandSource.sendError(new TranslatableText("commands.reload.failure"));
            return null;
        });
    }

    private static Collection<String> method_29478(ResourcePackManager resourcePackManager, SaveProperties saveProperties, Collection<String> collection) {
        resourcePackManager.scanPacks();
        ArrayList arrayList = Lists.newArrayList(collection);
        List<String> _snowman2 = saveProperties.getDataPackSettings().getDisabled();
        for (String string : resourcePackManager.getNames()) {
            if (_snowman2.contains(string) || arrayList.contains(string)) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("reload").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).executes(commandContext -> {
            ServerCommandSource serverCommandSource = (ServerCommandSource)commandContext.getSource();
            MinecraftServer _snowman2 = serverCommandSource.getMinecraftServer();
            ResourcePackManager _snowman3 = _snowman2.getDataPackManager();
            SaveProperties _snowman4 = _snowman2.getSaveProperties();
            Collection<String> _snowman5 = _snowman3.getEnabledNames();
            Collection<String> _snowman6 = ReloadCommand.method_29478(_snowman3, _snowman4, _snowman5);
            serverCommandSource.sendFeedback(new TranslatableText("commands.reload.success"), true);
            ReloadCommand.method_29480(_snowman6, serverCommandSource);
            return 0;
        }));
    }
}

