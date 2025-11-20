/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.resource;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlDebugInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VideoWarningManager
extends SinglePreparationResourceReloadListener<WarningPatternLoader> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier GPU_WARNLIST_ID = new Identifier("gpu_warnlist.json");
    private ImmutableMap<String, String> warnings = ImmutableMap.of();
    private boolean warningScheduled;
    private boolean warned;
    private boolean cancelledAfterWarning;

    public boolean hasWarning() {
        return !this.warnings.isEmpty();
    }

    public boolean canWarn() {
        return this.hasWarning() && !this.warned;
    }

    public void scheduleWarning() {
        this.warningScheduled = true;
    }

    public void acceptAfterWarnings() {
        this.warned = true;
    }

    public void cancelAfterWarnings() {
        this.warned = true;
        this.cancelledAfterWarning = true;
    }

    public boolean shouldWarn() {
        return this.warningScheduled && !this.warned;
    }

    public boolean hasCancelledAfterWarning() {
        return this.cancelledAfterWarning;
    }

    public void reset() {
        this.warningScheduled = false;
        this.warned = false;
        this.cancelledAfterWarning = false;
    }

    @Nullable
    public String getRendererWarning() {
        return (String)this.warnings.get((Object)"renderer");
    }

    @Nullable
    public String getVersionWarning() {
        return (String)this.warnings.get((Object)"version");
    }

    @Nullable
    public String getVendorWarning() {
        return (String)this.warnings.get((Object)"vendor");
    }

    @Nullable
    public String method_30920() {
        StringBuilder stringBuilder = new StringBuilder();
        this.warnings.forEach((string, string2) -> stringBuilder.append((String)string).append(": ").append((String)string2));
        return stringBuilder.length() == 0 ? null : stringBuilder.toString();
    }

    @Override
    protected WarningPatternLoader prepare(ResourceManager resourceManager, Profiler profiler) {
        ArrayList arrayList = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        profiler.startTick();
        JsonObject _snowman2 = VideoWarningManager.loadWarnlist(resourceManager, profiler);
        if (_snowman2 != null) {
            profiler.push("compile_regex");
            VideoWarningManager.compilePatterns(_snowman2.getAsJsonArray("renderer"), arrayList);
            VideoWarningManager.compilePatterns(_snowman2.getAsJsonArray("version"), _snowman);
            VideoWarningManager.compilePatterns(_snowman2.getAsJsonArray("vendor"), _snowman);
            profiler.pop();
        }
        profiler.endTick();
        return new WarningPatternLoader(arrayList, _snowman, _snowman);
    }

    @Override
    protected void apply(WarningPatternLoader warningPatternLoader, ResourceManager resourceManager, Profiler profiler) {
        this.warnings = warningPatternLoader.buildWarnings();
    }

    private static void compilePatterns(JsonArray array, List<Pattern> patterns) {
        array.forEach(jsonElement -> patterns.add(Pattern.compile(jsonElement.getAsString(), 2)));
    }

    @Nullable
    private static JsonObject loadWarnlist(ResourceManager resourceManager, Profiler profiler) {
        profiler.push("parse_json");
        JsonObject jsonObject = null;
        try (Resource resource = resourceManager.getResource(GPU_WARNLIST_ID);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));){
            jsonObject = new JsonParser().parse((Reader)bufferedReader).getAsJsonObject();
        }
        catch (JsonSyntaxException | IOException throwable) {
            LOGGER.warn("Failed to load GPU warnlist");
        }
        profiler.pop();
        return jsonObject;
    }

    @Override
    protected /* synthetic */ Object prepare(ResourceManager manager, Profiler profiler) {
        return this.prepare(manager, profiler);
    }

    public static final class WarningPatternLoader {
        private final List<Pattern> rendererPatterns;
        private final List<Pattern> versionPatterns;
        private final List<Pattern> vendorPatterns;

        private WarningPatternLoader(List<Pattern> rendererPatterns, List<Pattern> versionPatterns, List<Pattern> vendorPatterns) {
            this.rendererPatterns = rendererPatterns;
            this.versionPatterns = versionPatterns;
            this.vendorPatterns = vendorPatterns;
        }

        private static String buildWarning(List<Pattern> warningPattern, String info) {
            ArrayList arrayList = Lists.newArrayList();
            for (Pattern pattern : warningPattern) {
                Matcher matcher = pattern.matcher(info);
                while (matcher.find()) {
                    arrayList.add(matcher.group());
                }
            }
            return String.join((CharSequence)", ", arrayList);
        }

        private ImmutableMap<String, String> buildWarnings() {
            ImmutableMap.Builder builder = new ImmutableMap.Builder();
            String _snowman2 = WarningPatternLoader.buildWarning(this.rendererPatterns, GlDebugInfo.getRenderer());
            if (!_snowman2.isEmpty()) {
                builder.put((Object)"renderer", (Object)_snowman2);
            }
            if (!(_snowman = WarningPatternLoader.buildWarning(this.versionPatterns, GlDebugInfo.getVersion())).isEmpty()) {
                builder.put((Object)"version", (Object)_snowman);
            }
            if (!(_snowman = WarningPatternLoader.buildWarning(this.vendorPatterns, GlDebugInfo.getVendor())).isEmpty()) {
                builder.put((Object)"vendor", (Object)_snowman);
            }
            return builder.build();
        }
    }
}

