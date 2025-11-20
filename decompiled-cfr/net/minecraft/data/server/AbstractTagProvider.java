/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.tag.SetTag;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractTagProvider<T>
implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final DataGenerator root;
    protected final Registry<T> registry;
    private final Map<Identifier, Tag.Builder> tagBuilders = Maps.newLinkedHashMap();

    protected AbstractTagProvider(DataGenerator root, Registry<T> registry) {
        this.root = root;
        this.registry = registry;
    }

    protected abstract void configure();

    @Override
    public void run(DataCache cache) {
        this.tagBuilders.clear();
        this.configure();
        SetTag setTag = SetTag.empty();
        Function<Identifier, Tag> _snowman2 = identifier -> this.tagBuilders.containsKey(identifier) ? setTag : null;
        Function<Identifier, Object> _snowman3 = identifier -> this.registry.getOrEmpty((Identifier)identifier).orElse(null);
        this.tagBuilders.forEach((identifier, builder) -> {
            List list = builder.streamUnresolvedEntries(_snowman2, _snowman3).collect(Collectors.toList());
            if (!list.isEmpty()) {
                throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", identifier, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
            }
            JsonObject _snowman2 = builder.toJson();
            Path _snowman3 = this.getOutput((Identifier)identifier);
            try {
                DataCache dataCache2;
                String string = GSON.toJson((JsonElement)_snowman2);
                _snowman = SHA1.hashUnencodedChars((CharSequence)string).toString();
                if (!Objects.equals(cache.getOldSha1(_snowman3), _snowman) || !Files.exists(_snowman3, new LinkOption[0])) {
                    Files.createDirectories(_snowman3.getParent(), new FileAttribute[0]);
                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(_snowman3, new OpenOption[0]);){
                        bufferedWriter.write(string);
                    }
                }
                cache.updateSha1(_snowman3, _snowman);
            }
            catch (IOException iOException) {
                LOGGER.error("Couldn't save tags to {}", (Object)_snowman3, (Object)iOException);
            }
        });
    }

    protected abstract Path getOutput(Identifier var1);

    protected ObjectBuilder<T> getOrCreateTagBuilder(Tag.Identified<T> identified) {
        Tag.Builder builder = this.method_27169(identified);
        return new ObjectBuilder(builder, this.registry, "vanilla");
    }

    protected Tag.Builder method_27169(Tag.Identified<T> identified) {
        return this.tagBuilders.computeIfAbsent(identified.getId(), identifier -> new Tag.Builder());
    }

    public static class ObjectBuilder<T> {
        private final Tag.Builder field_23960;
        private final Registry<T> field_23961;
        private final String field_23962;

        private ObjectBuilder(Tag.Builder builder, Registry<T> registry, String string) {
            this.field_23960 = builder;
            this.field_23961 = registry;
            this.field_23962 = string;
        }

        public ObjectBuilder<T> add(T element) {
            this.field_23960.add(this.field_23961.getId(element), this.field_23962);
            return this;
        }

        public ObjectBuilder<T> addTag(Tag.Identified<T> identifiedTag) {
            this.field_23960.addTag(identifiedTag.getId(), this.field_23962);
            return this;
        }

        @SafeVarargs
        public final ObjectBuilder<T> add(T ... TArray) {
            Stream.of(TArray).map(this.field_23961::getId).forEach(identifier -> this.field_23960.add((Identifier)identifier, this.field_23962));
            return this;
        }
    }
}

