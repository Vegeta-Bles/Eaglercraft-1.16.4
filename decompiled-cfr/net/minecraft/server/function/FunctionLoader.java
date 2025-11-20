/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.datafixers.util.Pair
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadListener;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FunctionLoader
implements ResourceReloadListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PATH_PREFIX_LENGTH = "functions/".length();
    private static final int PATH_SUFFIX_LENGTH = ".mcfunction".length();
    private volatile Map<Identifier, CommandFunction> functions = ImmutableMap.of();
    private final TagGroupLoader<CommandFunction> tagLoader = new TagGroupLoader(this::get, "tags/functions", "function");
    private volatile TagGroup<CommandFunction> tags = TagGroup.createEmpty();
    private final int level;
    private final CommandDispatcher<ServerCommandSource> commandDispatcher;

    public Optional<CommandFunction> get(Identifier id) {
        return Optional.ofNullable(this.functions.get(id));
    }

    public Map<Identifier, CommandFunction> getFunctions() {
        return this.functions;
    }

    public TagGroup<CommandFunction> getTags() {
        return this.tags;
    }

    public Tag<CommandFunction> getOrCreateTag(Identifier id) {
        return this.tags.getTagOrEmpty(id);
    }

    public FunctionLoader(int level, CommandDispatcher<ServerCommandSource> commandDispatcher) {
        this.level = level;
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public CompletableFuture<Void> reload(ResourceReloadListener.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        CompletableFuture<Map<Identifier, Tag.Builder>> completableFuture = this.tagLoader.prepareReload(manager, prepareExecutor);
        CompletionStage _snowman2 = CompletableFuture.supplyAsync(() -> manager.findResources("functions", string -> string.endsWith(".mcfunction")), prepareExecutor).thenCompose(collection -> {
            HashMap hashMap = Maps.newHashMap();
            ServerCommandSource _snowman2 = new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ZERO, Vec2f.ZERO, null, this.level, "", LiteralText.EMPTY, null, null);
            for (Identifier identifier : collection) {
                String string = identifier.getPath();
                Identifier _snowman3 = new Identifier(identifier.getNamespace(), string.substring(PATH_PREFIX_LENGTH, string.length() - PATH_SUFFIX_LENGTH));
                hashMap.put(_snowman3, CompletableFuture.supplyAsync(() -> {
                    List<String> list = FunctionLoader.readLines(manager, identifier);
                    return CommandFunction.create(_snowman3, this.commandDispatcher, _snowman2, list);
                }, prepareExecutor));
            }
            CompletableFuture[] completableFutureArray = hashMap.values().toArray(new CompletableFuture[0]);
            return CompletableFuture.allOf(completableFutureArray).handle((void_, throwable) -> hashMap);
        });
        return ((CompletableFuture)((CompletableFuture)completableFuture.thenCombine(_snowman2, Pair::of)).thenCompose(synchronizer::whenPrepared)).thenAcceptAsync(pair -> {
            Map map = (Map)pair.getSecond();
            ImmutableMap.Builder _snowman2 = ImmutableMap.builder();
            map.forEach((identifier, completableFuture) -> ((CompletableFuture)completableFuture.handle((commandFunction, throwable) -> {
                if (throwable != null) {
                    LOGGER.error("Failed to load function {}", identifier, throwable);
                } else {
                    _snowman2.put(identifier, commandFunction);
                }
                return null;
            })).join());
            this.functions = _snowman2.build();
            this.tags = this.tagLoader.applyReload((Map)pair.getFirst());
        }, applyExecutor);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<String> readLines(ResourceManager resourceManager, Identifier id) {
        try (Resource resource = resourceManager.getResource(id);){
            List list = IOUtils.readLines((InputStream)resource.getInputStream(), (Charset)StandardCharsets.UTF_8);
            return list;
        }
        catch (IOException iOException) {
            throw new CompletionException(iOException);
        }
    }
}

