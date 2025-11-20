/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DataResult$PartialResult
 *  com.mojang.serialization.Decoder
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.Lifecycle
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util.dynamic;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.ForwardingDynamicOps;
import net.minecraft.util.dynamic.RegistryReadingOps;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryOps<T>
extends ForwardingDynamicOps<T> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final EntryLoader entryLoader;
    private final DynamicRegistryManager.Impl registryManager;
    private final Map<RegistryKey<? extends Registry<?>>, ValueHolder<?>> valueHolders;
    private final RegistryOps<JsonElement> entryOps;

    public static <T> RegistryOps<T> of(DynamicOps<T> delegate, ResourceManager resourceManager, DynamicRegistryManager.Impl impl) {
        return RegistryOps.of(delegate, EntryLoader.resourceBacked(resourceManager), impl);
    }

    public static <T> RegistryOps<T> of(DynamicOps<T> dynamicOps, EntryLoader entryLoader, DynamicRegistryManager.Impl impl) {
        RegistryOps<T> registryOps = new RegistryOps<T>(dynamicOps, entryLoader, impl, Maps.newIdentityHashMap());
        DynamicRegistryManager.load(impl, registryOps);
        return registryOps;
    }

    private RegistryOps(DynamicOps<T> delegate, EntryLoader entryLoader, DynamicRegistryManager.Impl impl, IdentityHashMap<RegistryKey<? extends Registry<?>>, ValueHolder<?>> identityHashMap) {
        super(delegate);
        this.entryLoader = entryLoader;
        this.registryManager = impl;
        this.valueHolders = identityHashMap;
        this.entryOps = delegate == JsonOps.INSTANCE ? this : new RegistryOps<T>(JsonOps.INSTANCE, entryLoader, impl, (IdentityHashMap<RegistryKey<Registry<?>>, ValueHolder<?>>)identityHashMap);
    }

    protected <E> DataResult<Pair<java.util.function.Supplier<E>, T>> decodeOrId(T t, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec, boolean allowInlineDefinitions) {
        Optional optional = this.registryManager.getOptional(registryKey);
        if (!optional.isPresent()) {
            return DataResult.error((String)("Unknown registry: " + registryKey));
        }
        MutableRegistry _snowman2 = optional.get();
        DataResult _snowman3 = Identifier.CODEC.decode(this.delegate, t);
        if (!_snowman3.result().isPresent()) {
            if (!allowInlineDefinitions) {
                return DataResult.error((String)"Inline definitions not allowed here");
            }
            return codec.decode((DynamicOps)this, t).map(pair -> pair.mapFirst(object -> () -> object));
        }
        Pair _snowman4 = (Pair)_snowman3.result().get();
        Identifier _snowman5 = (Identifier)_snowman4.getFirst();
        return this.readSupplier(registryKey, _snowman2, codec, _snowman5).map(supplier -> Pair.of((Object)supplier, (Object)_snowman4.getSecond()));
    }

    public <E> DataResult<SimpleRegistry<E>> loadToRegistry(SimpleRegistry<E> registry, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec) {
        Collection<Identifier> collection = this.entryLoader.getKnownEntryPaths(registryKey);
        DataResult _snowman2 = DataResult.success(registry, (Lifecycle)Lifecycle.stable());
        String _snowman3 = registryKey.getValue().getPath() + "/";
        for (Identifier identifier : collection) {
            String string = identifier.getPath();
            if (!string.endsWith(".json")) {
                LOGGER.warn("Skipping resource {} since it is not a json file", (Object)identifier);
                continue;
            }
            if (!string.startsWith(_snowman3)) {
                LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", (Object)identifier);
                continue;
            }
            _snowman = string.substring(_snowman3.length(), string.length() - ".json".length());
            Identifier _snowman4 = new Identifier(identifier.getNamespace(), _snowman);
            _snowman2 = _snowman2.flatMap(simpleRegistry -> this.readSupplier(registryKey, (MutableRegistry)simpleRegistry, codec, _snowman4).map(supplier -> simpleRegistry));
        }
        return _snowman2.setPartial(registry);
    }

    private <E> DataResult<java.util.function.Supplier<E>> readSupplier(RegistryKey<? extends Registry<E>> registryKey, MutableRegistry<E> mutableRegistry2, Codec<E> codec, Identifier elementId) {
        MutableRegistry mutableRegistry2;
        Pair _snowman7;
        RegistryKey registryKey2 = RegistryKey.of(registryKey, elementId);
        ValueHolder<E> _snowman2 = this.getValueHolder(registryKey);
        DataResult _snowman3 = (DataResult)((ValueHolder)_snowman2).values.get(registryKey2);
        if (_snowman3 != null) {
            return _snowman3;
        }
        Supplier _snowman4 = Suppliers.memoize(() -> {
            Object t = mutableRegistry2.get(registryKey2);
            if (t == null) {
                throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + registryKey2);
            }
            return t;
        });
        ((ValueHolder)_snowman2).values.put(registryKey2, DataResult.success((Object)_snowman4));
        DataResult _snowman5 = this.entryLoader.load((DynamicOps<JsonElement>)this.entryOps, registryKey, registryKey2, codec);
        Optional _snowman6 = _snowman5.result();
        if (_snowman6.isPresent()) {
            _snowman7 = (Pair)_snowman6.get();
            mutableRegistry2.replace((OptionalInt)_snowman7.getSecond(), registryKey2, _snowman7.getFirst(), _snowman5.lifecycle());
        }
        _snowman7 = !_snowman6.isPresent() && mutableRegistry2.get(registryKey2) != null ? DataResult.success(() -> mutableRegistry2.get(registryKey2), (Lifecycle)Lifecycle.stable()) : _snowman5.map(pair -> () -> mutableRegistry2.get(registryKey2));
        ((ValueHolder)_snowman2).values.put(registryKey2, _snowman7);
        return _snowman7;
    }

    private <E> ValueHolder<E> getValueHolder(RegistryKey<? extends Registry<E>> registryRef) {
        return this.valueHolders.computeIfAbsent(registryRef, registryKey -> new ValueHolder());
    }

    protected <E> DataResult<Registry<E>> method_31152(RegistryKey<? extends Registry<E>> registryKey) {
        return this.registryManager.getOptional(registryKey).map(mutableRegistry -> DataResult.success((Object)mutableRegistry, (Lifecycle)mutableRegistry.getLifecycle())).orElseGet(() -> DataResult.error((String)("Unknown registry: " + registryKey)));
    }

    public static interface EntryLoader {
        public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> var1);

        public <E> DataResult<Pair<E, OptionalInt>> load(DynamicOps<JsonElement> var1, RegistryKey<? extends Registry<E>> var2, RegistryKey<E> var3, Decoder<E> var4);

        public static EntryLoader resourceBacked(ResourceManager resourceManager) {
            return new EntryLoader(resourceManager){
                final /* synthetic */ ResourceManager field_26740;
                {
                    this.field_26740 = resourceManager;
                }

                public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> registryKey) {
                    return this.field_26740.findResources(registryKey.getValue().getPath(), string -> string.endsWith(".json"));
                }

                /*
                 * Exception decompiling
                 */
                public <E> DataResult<Pair<E, OptionalInt>> load(DynamicOps<JsonElement> _snowman, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> _snowman) {
                    /*
                     * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                     * 
                     * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
                     *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
                     *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
                     *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
                     *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
                     *     at org.benf.cfr.reader.entities.Method.dump(Method.java:598)
                     *     at org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperAnonymousInner.dumpWithArgs(ClassFileDumperAnonymousInner.java:87)
                     *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.ConstructorInvokationAnonymousInner.dumpInner(ConstructorInvokationAnonymousInner.java:82)
                     *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dumpWithOuterPrecedence(AbstractExpression.java:142)
                     *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractExpression.dump(AbstractExpression.java:98)
                     *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                     *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredReturn.dump(StructuredReturn.java:60)
                     *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:220)
                     *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.Block.dump(Block.java:564)
                     *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                     *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.dump(Op04StructuredStatement.java:220)
                     *     at org.benf.cfr.reader.entities.attributes.AttributeCode.dump(AttributeCode.java:135)
                     *     at org.benf.cfr.reader.state.TypeUsageCollectingDumper.dump(TypeUsageCollectingDumper.java:194)
                     *     at org.benf.cfr.reader.entities.Method.dump(Method.java:627)
                     *     at org.benf.cfr.reader.entities.classfilehelpers.AbstractClassFileDumper.dumpMethods(AbstractClassFileDumper.java:211)
                     *     at org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperInterface.dump(ClassFileDumperInterface.java:75)
                     *     at org.benf.cfr.reader.entities.ClassFile.dumpNamedInnerClasses(ClassFile.java:1161)
                     *     at org.benf.cfr.reader.entities.classfilehelpers.ClassFileDumperNormal.dump(ClassFileDumperNormal.java:71)
                     *     at org.benf.cfr.reader.entities.ClassFile.dump(ClassFile.java:1167)
                     *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:952)
                     *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
                     *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
                     *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
                     *     at org.benf.cfr.reader.Main.main(Main.java:54)
                     */
                    throw new IllegalStateException("Decompilation failed");
                }

                public String toString() {
                    return "ResourceAccess[" + this.field_26740 + "]";
                }

                private static /* synthetic */ Pair method_31157(Object object) {
                    return Pair.of((Object)object, (Object)OptionalInt.empty());
                }
            };
        }

        public static final class Impl
        implements EntryLoader {
            private final Map<RegistryKey<?>, JsonElement> values = Maps.newIdentityHashMap();
            private final Object2IntMap<RegistryKey<?>> entryToRawId = new Object2IntOpenCustomHashMap(Util.identityHashStrategy());
            private final Map<RegistryKey<?>, Lifecycle> entryToLifecycle = Maps.newIdentityHashMap();

            public <E> void add(DynamicRegistryManager.Impl impl, RegistryKey<E> registryKey, Encoder<E> encoder, int rawId, E e, Lifecycle lifecycle) {
                DataResult dataResult = encoder.encodeStart(RegistryReadingOps.of(JsonOps.INSTANCE, impl), e);
                Optional _snowman2 = dataResult.error();
                if (_snowman2.isPresent()) {
                    LOGGER.error("Error adding element: {}", (Object)((DataResult.PartialResult)_snowman2.get()).message());
                    return;
                }
                this.values.put(registryKey, (JsonElement)dataResult.result().get());
                this.entryToRawId.put(registryKey, rawId);
                this.entryToLifecycle.put(registryKey, lifecycle);
            }

            @Override
            public Collection<Identifier> getKnownEntryPaths(RegistryKey<? extends Registry<?>> registryKey) {
                return this.values.keySet().stream().filter(registryKey2 -> registryKey2.isOf(registryKey)).map(registryKey2 -> new Identifier(registryKey2.getValue().getNamespace(), registryKey.getValue().getPath() + "/" + registryKey2.getValue().getPath() + ".json")).collect(Collectors.toList());
            }

            @Override
            public <E> DataResult<Pair<E, OptionalInt>> load(DynamicOps<JsonElement> dynamicOps, RegistryKey<? extends Registry<E>> registryId, RegistryKey<E> entryId, Decoder<E> decoder) {
                JsonElement jsonElement = this.values.get(entryId);
                if (jsonElement == null) {
                    return DataResult.error((String)("Unknown element: " + entryId));
                }
                return decoder.parse(dynamicOps, (Object)jsonElement).setLifecycle(this.entryToLifecycle.get(entryId)).map(object -> Pair.of((Object)object, (Object)OptionalInt.of(this.entryToRawId.getInt((Object)entryId))));
            }
        }
    }

    static final class ValueHolder<E> {
        private final Map<RegistryKey<E>, DataResult<java.util.function.Supplier<E>>> values = Maps.newIdentityHashMap();

        private ValueHolder() {
        }
    }
}

