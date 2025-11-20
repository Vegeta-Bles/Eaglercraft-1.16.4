/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.nbt.AbstractListTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.nbt.Tag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NbtPathArgumentType
implements ArgumentType<NbtPath> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
    public static final SimpleCommandExceptionType INVALID_PATH_NODE_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("arguments.nbtpath.node.invalid"));
    public static final DynamicCommandExceptionType NOTHING_FOUND_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("arguments.nbtpath.nothing_found", object));

    public static NbtPathArgumentType nbtPath() {
        return new NbtPathArgumentType();
    }

    public static NbtPath getNbtPath(CommandContext<ServerCommandSource> context, String name) {
        return (NbtPath)context.getArgument(name, NbtPath.class);
    }

    public NbtPath parse(StringReader stringReader2) throws CommandSyntaxException {
        StringReader stringReader2;
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = stringReader2.getCursor();
        Object2IntOpenHashMap _snowman3 = new Object2IntOpenHashMap();
        boolean _snowman4 = true;
        while (stringReader2.canRead() && stringReader2.peek() != ' ') {
            PathNode pathNode = NbtPathArgumentType.parseNode(stringReader2, _snowman4);
            arrayList.add(pathNode);
            _snowman3.put((Object)pathNode, stringReader2.getCursor() - _snowman2);
            _snowman4 = false;
            if (!stringReader2.canRead() || (_snowman = stringReader2.peek()) == ' ' || _snowman == '[' || _snowman == '{') continue;
            stringReader2.expect('.');
        }
        return new NbtPath(stringReader2.getString().substring(_snowman2, stringReader2.getCursor()), arrayList.toArray(new PathNode[0]), (Object2IntMap<PathNode>)_snowman3);
    }

    private static PathNode parseNode(StringReader reader, boolean root) throws CommandSyntaxException {
        switch (reader.peek()) {
            case '{': {
                if (!root) {
                    throw INVALID_PATH_NODE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
                }
                CompoundTag compoundTag = new StringNbtReader(reader).parseCompoundTag();
                return new FilteredRootNode(compoundTag);
            }
            case '[': {
                reader.skip();
                char c = reader.peek();
                if (c == '{') {
                    CompoundTag compoundTag = new StringNbtReader(reader).parseCompoundTag();
                    reader.expect(']');
                    return new FilteredListElementNode(compoundTag);
                }
                if (c == ']') {
                    reader.skip();
                    return AllListElementNode.INSTANCE;
                }
                int _snowman2 = reader.readInt();
                reader.expect(']');
                return new IndexedListElementNode(_snowman2);
            }
            case '\"': {
                String _snowman3 = reader.readString();
                return NbtPathArgumentType.readCompoundChildNode(reader, _snowman3);
            }
        }
        String string = NbtPathArgumentType.readName(reader);
        return NbtPathArgumentType.readCompoundChildNode(reader, string);
    }

    private static PathNode readCompoundChildNode(StringReader reader, String name) throws CommandSyntaxException {
        if (reader.canRead() && reader.peek() == '{') {
            CompoundTag compoundTag = new StringNbtReader(reader).parseCompoundTag();
            return new FilteredNamedNode(name, compoundTag);
        }
        return new NamedNode(name);
    }

    private static String readName(StringReader reader) throws CommandSyntaxException {
        int n = reader.getCursor();
        while (reader.canRead() && NbtPathArgumentType.isNameCharacter(reader.peek())) {
            reader.skip();
        }
        if (reader.getCursor() == n) {
            throw INVALID_PATH_NODE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
        }
        return reader.getString().substring(n, reader.getCursor());
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private static boolean isNameCharacter(char c) {
        return c != ' ' && c != '\"' && c != '[' && c != ']' && c != '.' && c != '{' && c != '}';
    }

    private static Predicate<Tag> getPredicate(CompoundTag filter) {
        return tag -> NbtHelper.matches(filter, tag, true);
    }

    public /* synthetic */ Object parse(StringReader reader) throws CommandSyntaxException {
        return this.parse(reader);
    }

    static class FilteredRootNode
    implements PathNode {
        private final Predicate<Tag> matcher;

        public FilteredRootNode(CompoundTag filter) {
            this.matcher = NbtPathArgumentType.getPredicate(filter);
        }

        @Override
        public void get(Tag current, List<Tag> results) {
            if (current instanceof CompoundTag && this.matcher.test(current)) {
                results.add(current);
            }
        }

        @Override
        public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
            this.get(current, results);
        }

        @Override
        public Tag init() {
            return new CompoundTag();
        }

        @Override
        public int set(Tag current, Supplier<Tag> source) {
            return 0;
        }

        @Override
        public int clear(Tag current) {
            return 0;
        }
    }

    static class FilteredNamedNode
    implements PathNode {
        private final String name;
        private final CompoundTag filter;
        private final Predicate<Tag> predicate;

        public FilteredNamedNode(String name, CompoundTag filter) {
            this.name = name;
            this.filter = filter;
            this.predicate = NbtPathArgumentType.getPredicate(filter);
        }

        @Override
        public void get(Tag current, List<Tag> results) {
            Tag tag;
            if (current instanceof CompoundTag && this.predicate.test(tag = ((CompoundTag)current).get(this.name))) {
                results.add(tag);
            }
        }

        @Override
        public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
            if (current instanceof CompoundTag) {
                CompoundTag compoundTag = (CompoundTag)current;
                Tag _snowman2 = compoundTag.get(this.name);
                if (_snowman2 == null) {
                    _snowman2 = this.filter.copy();
                    compoundTag.put(this.name, _snowman2);
                    results.add(_snowman2);
                } else if (this.predicate.test(_snowman2)) {
                    results.add(_snowman2);
                }
            }
        }

        @Override
        public Tag init() {
            return new CompoundTag();
        }

        @Override
        public int set(Tag current, Supplier<Tag> source) {
            Tag tag;
            if (current instanceof CompoundTag && this.predicate.test(tag = (_snowman = (CompoundTag)current).get(this.name)) && !(_snowman = source.get()).equals(tag)) {
                _snowman.put(this.name, _snowman);
                return 1;
            }
            return 0;
        }

        @Override
        public int clear(Tag current) {
            Tag tag;
            if (current instanceof CompoundTag && this.predicate.test(tag = (_snowman = (CompoundTag)current).get(this.name))) {
                _snowman.remove(this.name);
                return 1;
            }
            return 0;
        }
    }

    static class AllListElementNode
    implements PathNode {
        public static final AllListElementNode INSTANCE = new AllListElementNode();

        private AllListElementNode() {
        }

        @Override
        public void get(Tag current, List<Tag> results) {
            if (current instanceof AbstractListTag) {
                results.addAll((AbstractListTag)current);
            }
        }

        @Override
        public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
            if (current instanceof AbstractListTag) {
                AbstractListTag abstractListTag = (AbstractListTag)current;
                if (abstractListTag.isEmpty()) {
                    Tag tag = source.get();
                    if (abstractListTag.addTag(0, tag)) {
                        results.add(tag);
                    }
                } else {
                    results.addAll(abstractListTag);
                }
            }
        }

        @Override
        public Tag init() {
            return new ListTag();
        }

        @Override
        public int set(Tag current, Supplier<Tag> source) {
            if (current instanceof AbstractListTag) {
                AbstractListTag abstractListTag = (AbstractListTag)current;
                int _snowman2 = abstractListTag.size();
                if (_snowman2 == 0) {
                    abstractListTag.addTag(0, source.get());
                    return 1;
                }
                Tag _snowman3 = source.get();
                int _snowman4 = _snowman2 - (int)abstractListTag.stream().filter(_snowman3::equals).count();
                if (_snowman4 == 0) {
                    return 0;
                }
                abstractListTag.clear();
                if (!abstractListTag.addTag(0, _snowman3)) {
                    return 0;
                }
                for (int i = 1; i < _snowman2; ++i) {
                    abstractListTag.addTag(i, source.get());
                }
                return _snowman4;
            }
            return 0;
        }

        @Override
        public int clear(Tag current) {
            int n;
            if (current instanceof AbstractListTag && (n = (_snowman = (AbstractListTag)current).size()) > 0) {
                _snowman.clear();
                return n;
            }
            return 0;
        }
    }

    static class FilteredListElementNode
    implements PathNode {
        private final CompoundTag filter;
        private final Predicate<Tag> predicate;

        public FilteredListElementNode(CompoundTag filter) {
            this.filter = filter;
            this.predicate = NbtPathArgumentType.getPredicate(filter);
        }

        @Override
        public void get(Tag current, List<Tag> results) {
            if (current instanceof ListTag) {
                ListTag listTag = (ListTag)current;
                listTag.stream().filter(this.predicate).forEach(results::add);
            }
        }

        @Override
        public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
            MutableBoolean mutableBoolean = new MutableBoolean();
            if (current instanceof ListTag) {
                ListTag listTag = (ListTag)current;
                listTag.stream().filter(this.predicate).forEach(tag -> {
                    results.add((Tag)tag);
                    mutableBoolean.setTrue();
                });
                if (mutableBoolean.isFalse()) {
                    CompoundTag compoundTag = this.filter.copy();
                    listTag.add(compoundTag);
                    results.add(compoundTag);
                }
            }
        }

        @Override
        public Tag init() {
            return new ListTag();
        }

        @Override
        public int set(Tag current, Supplier<Tag> source) {
            int n = 0;
            if (current instanceof ListTag) {
                ListTag listTag = (ListTag)current;
                int _snowman2 = listTag.size();
                if (_snowman2 == 0) {
                    listTag.add(source.get());
                    ++n;
                } else {
                    for (int i = 0; i < _snowman2; ++i) {
                        Tag tag = listTag.get(i);
                        if (!this.predicate.test(tag) || (_snowman = source.get()).equals(tag) || !listTag.setTag(i, _snowman)) continue;
                        ++n;
                    }
                }
            }
            return n;
        }

        @Override
        public int clear(Tag current) {
            int n = 0;
            if (current instanceof ListTag) {
                ListTag listTag = (ListTag)current;
                for (int i = listTag.size() - 1; i >= 0; --i) {
                    if (!this.predicate.test(listTag.get(i))) continue;
                    listTag.remove(i);
                    ++n;
                }
            }
            return n;
        }
    }

    static class IndexedListElementNode
    implements PathNode {
        private final int index;

        public IndexedListElementNode(int index) {
            this.index = index;
        }

        @Override
        public void get(Tag current, List<Tag> results) {
            if (current instanceof AbstractListTag) {
                AbstractListTag abstractListTag = (AbstractListTag)current;
                int _snowman2 = abstractListTag.size();
                int n = _snowman = this.index < 0 ? _snowman2 + this.index : this.index;
                if (0 <= _snowman && _snowman < _snowman2) {
                    results.add((Tag)abstractListTag.get(_snowman));
                }
            }
        }

        @Override
        public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
            this.get(current, results);
        }

        @Override
        public Tag init() {
            return new ListTag();
        }

        @Override
        public int set(Tag current, Supplier<Tag> source) {
            if (current instanceof AbstractListTag) {
                AbstractListTag abstractListTag = (AbstractListTag)current;
                int _snowman2 = abstractListTag.size();
                int n = _snowman = this.index < 0 ? _snowman2 + this.index : this.index;
                if (0 <= _snowman && _snowman < _snowman2) {
                    Tag tag = (Tag)abstractListTag.get(_snowman);
                    _snowman = source.get();
                    if (!_snowman.equals(tag) && abstractListTag.setTag(_snowman, _snowman)) {
                        return 1;
                    }
                }
            }
            return 0;
        }

        @Override
        public int clear(Tag current) {
            if (current instanceof AbstractListTag) {
                AbstractListTag abstractListTag = (AbstractListTag)current;
                int _snowman2 = abstractListTag.size();
                int n = _snowman = this.index < 0 ? _snowman2 + this.index : this.index;
                if (0 <= _snowman && _snowman < _snowman2) {
                    abstractListTag.remove(_snowman);
                    return 1;
                }
            }
            return 0;
        }
    }

    static class NamedNode
    implements PathNode {
        private final String name;

        public NamedNode(String name) {
            this.name = name;
        }

        @Override
        public void get(Tag current, List<Tag> results) {
            Tag tag;
            if (current instanceof CompoundTag && (tag = ((CompoundTag)current).get(this.name)) != null) {
                results.add(tag);
            }
        }

        @Override
        public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
            if (current instanceof CompoundTag) {
                Tag tag;
                CompoundTag compoundTag = (CompoundTag)current;
                if (compoundTag.contains(this.name)) {
                    tag = compoundTag.get(this.name);
                } else {
                    tag = source.get();
                    compoundTag.put(this.name, tag);
                }
                results.add(tag);
            }
        }

        @Override
        public Tag init() {
            return new CompoundTag();
        }

        @Override
        public int set(Tag current, Supplier<Tag> source) {
            if (current instanceof CompoundTag) {
                CompoundTag compoundTag = (CompoundTag)current;
                Tag _snowman2 = source.get();
                if (!_snowman2.equals(_snowman = compoundTag.put(this.name, _snowman2))) {
                    return 1;
                }
            }
            return 0;
        }

        @Override
        public int clear(Tag current) {
            CompoundTag compoundTag;
            if (current instanceof CompoundTag && (compoundTag = (CompoundTag)current).contains(this.name)) {
                compoundTag.remove(this.name);
                return 1;
            }
            return 0;
        }
    }

    static interface PathNode {
        public void get(Tag var1, List<Tag> var2);

        public void getOrInit(Tag var1, Supplier<Tag> var2, List<Tag> var3);

        public Tag init();

        public int set(Tag var1, Supplier<Tag> var2);

        public int clear(Tag var1);

        default public List<Tag> get(List<Tag> tags) {
            return this.process(tags, this::get);
        }

        default public List<Tag> getOrInit(List<Tag> tags, Supplier<Tag> supplier) {
            return this.process(tags, (current, results) -> this.getOrInit((Tag)current, supplier, (List<Tag>)results));
        }

        default public List<Tag> process(List<Tag> tags, BiConsumer<Tag, List<Tag>> action) {
            ArrayList arrayList = Lists.newArrayList();
            for (Tag tag : tags) {
                action.accept(tag, arrayList);
            }
            return arrayList;
        }
    }

    public static class NbtPath {
        private final String string;
        private final Object2IntMap<PathNode> nodeEndIndices;
        private final PathNode[] nodes;

        public NbtPath(String string, PathNode[] nodes, Object2IntMap<PathNode> nodeEndIndices) {
            this.string = string;
            this.nodes = nodes;
            this.nodeEndIndices = nodeEndIndices;
        }

        public List<Tag> get(Tag tag) throws CommandSyntaxException {
            List<Tag> list = Collections.singletonList(tag);
            for (PathNode pathNode : this.nodes) {
                if (!(list = pathNode.get(list)).isEmpty()) continue;
                throw this.createNothingFoundException(pathNode);
            }
            return list;
        }

        public int count(Tag tag) {
            List<Tag> list = Collections.singletonList(tag);
            for (PathNode pathNode : this.nodes) {
                if (!(list = pathNode.get(list)).isEmpty()) continue;
                return 0;
            }
            return list.size();
        }

        private List<Tag> getTerminals(Tag start) throws CommandSyntaxException {
            List<Tag> list = Collections.singletonList(start);
            for (int i = 0; i < this.nodes.length - 1; ++i) {
                PathNode pathNode = this.nodes[i];
                int _snowman2 = i + 1;
                if (!(list = pathNode.getOrInit(list, this.nodes[_snowman2]::init)).isEmpty()) continue;
                throw this.createNothingFoundException(pathNode);
            }
            return list;
        }

        public List<Tag> getOrInit(Tag tag, Supplier<Tag> source) throws CommandSyntaxException {
            List<Tag> list = this.getTerminals(tag);
            PathNode _snowman2 = this.nodes[this.nodes.length - 1];
            return _snowman2.getOrInit(list, source);
        }

        private static int forEach(List<Tag> tags, Function<Tag, Integer> operation) {
            return tags.stream().map(operation).reduce(0, (n, n2) -> n + n2);
        }

        public int put(Tag tag2, Supplier<Tag> source) throws CommandSyntaxException {
            List<Tag> list = this.getTerminals(tag2);
            PathNode _snowman2 = this.nodes[this.nodes.length - 1];
            return NbtPath.forEach(list, tag -> _snowman2.set((Tag)tag, source));
        }

        public int remove(Tag tag) {
            List<Tag> list = Collections.singletonList(tag);
            for (int i = 0; i < this.nodes.length - 1; ++i) {
                list = this.nodes[i].get(list);
            }
            PathNode pathNode = this.nodes[this.nodes.length - 1];
            return NbtPath.forEach(list, pathNode::clear);
        }

        private CommandSyntaxException createNothingFoundException(PathNode node) {
            int n = this.nodeEndIndices.getInt((Object)node);
            return NOTHING_FOUND_EXCEPTION.create((Object)this.string.substring(0, n));
        }

        public String toString() {
            return this.string;
        }
    }
}

