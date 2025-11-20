package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
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

public class NbtPathArgumentType implements ArgumentType<NbtPathArgumentType.NbtPath> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
   public static final SimpleCommandExceptionType INVALID_PATH_NODE_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("arguments.nbtpath.node.invalid")
   );
   public static final DynamicCommandExceptionType NOTHING_FOUND_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("arguments.nbtpath.nothing_found", _snowman)
   );

   public NbtPathArgumentType() {
   }

   public static NbtPathArgumentType nbtPath() {
      return new NbtPathArgumentType();
   }

   public static NbtPathArgumentType.NbtPath getNbtPath(CommandContext<ServerCommandSource> context, String name) {
      return (NbtPathArgumentType.NbtPath)context.getArgument(name, NbtPathArgumentType.NbtPath.class);
   }

   public NbtPathArgumentType.NbtPath parse(StringReader _snowman) throws CommandSyntaxException {
      List<NbtPathArgumentType.PathNode> _snowmanx = Lists.newArrayList();
      int _snowmanxx = _snowman.getCursor();
      Object2IntMap<NbtPathArgumentType.PathNode> _snowmanxxx = new Object2IntOpenHashMap();
      boolean _snowmanxxxx = true;

      while (_snowman.canRead() && _snowman.peek() != ' ') {
         NbtPathArgumentType.PathNode _snowmanxxxxx = parseNode(_snowman, _snowmanxxxx);
         _snowmanx.add(_snowmanxxxxx);
         _snowmanxxx.put(_snowmanxxxxx, _snowman.getCursor() - _snowmanxx);
         _snowmanxxxx = false;
         if (_snowman.canRead()) {
            char _snowmanxxxxxx = _snowman.peek();
            if (_snowmanxxxxxx != ' ' && _snowmanxxxxxx != '[' && _snowmanxxxxxx != '{') {
               _snowman.expect('.');
            }
         }
      }

      return new NbtPathArgumentType.NbtPath(_snowman.getString().substring(_snowmanxx, _snowman.getCursor()), _snowmanx.toArray(new NbtPathArgumentType.PathNode[0]), _snowmanxxx);
   }

   private static NbtPathArgumentType.PathNode parseNode(StringReader reader, boolean root) throws CommandSyntaxException {
      switch (reader.peek()) {
         case '"': {
            String _snowman = reader.readString();
            return readCompoundChildNode(reader, _snowman);
         }
         case '[': {
            reader.skip();
            int _snowman = reader.peek();
            if (_snowman == 123) {
               CompoundTag _snowmanx = new StringNbtReader(reader).parseCompoundTag();
               reader.expect(']');
               return new NbtPathArgumentType.FilteredListElementNode(_snowmanx);
            } else {
               if (_snowman == 93) {
                  reader.skip();
                  return NbtPathArgumentType.AllListElementNode.INSTANCE;
               }

               int _snowmanx = reader.readInt();
               reader.expect(']');
               return new NbtPathArgumentType.IndexedListElementNode(_snowmanx);
            }
         }
         case '{': {
            if (!root) {
               throw INVALID_PATH_NODE_EXCEPTION.createWithContext(reader);
            }

            CompoundTag _snowman = new StringNbtReader(reader).parseCompoundTag();
            return new NbtPathArgumentType.FilteredRootNode(_snowman);
         }
         default: {
            String _snowman = readName(reader);
            return readCompoundChildNode(reader, _snowman);
         }
      }
   }

   private static NbtPathArgumentType.PathNode readCompoundChildNode(StringReader reader, String name) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() == '{') {
         CompoundTag _snowman = new StringNbtReader(reader).parseCompoundTag();
         return new NbtPathArgumentType.FilteredNamedNode(name, _snowman);
      } else {
         return new NbtPathArgumentType.NamedNode(name);
      }
   }

   private static String readName(StringReader reader) throws CommandSyntaxException {
      int _snowman = reader.getCursor();

      while (reader.canRead() && isNameCharacter(reader.peek())) {
         reader.skip();
      }

      if (reader.getCursor() == _snowman) {
         throw INVALID_PATH_NODE_EXCEPTION.createWithContext(reader);
      } else {
         return reader.getString().substring(_snowman, reader.getCursor());
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   private static boolean isNameCharacter(char c) {
      return c != ' ' && c != '"' && c != '[' && c != ']' && c != '.' && c != '{' && c != '}';
   }

   private static Predicate<Tag> getPredicate(CompoundTag filter) {
      return _snowmanx -> NbtHelper.matches(filter, _snowmanx, true);
   }

   static class AllListElementNode implements NbtPathArgumentType.PathNode {
      public static final NbtPathArgumentType.AllListElementNode INSTANCE = new NbtPathArgumentType.AllListElementNode();

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
            AbstractListTag<?> _snowman = (AbstractListTag<?>)current;
            if (_snowman.isEmpty()) {
               Tag _snowmanx = source.get();
               if (_snowman.addTag(0, _snowmanx)) {
                  results.add(_snowmanx);
               }
            } else {
               results.addAll((Collection<? extends Tag>)_snowman);
            }
         }
      }

      @Override
      public Tag init() {
         return new ListTag();
      }

      @Override
      public int set(Tag current, Supplier<Tag> source) {
         if (!(current instanceof AbstractListTag)) {
            return 0;
         } else {
            AbstractListTag<?> _snowman = (AbstractListTag<?>)current;
            int _snowmanx = _snowman.size();
            if (_snowmanx == 0) {
               _snowman.addTag(0, source.get());
               return 1;
            } else {
               Tag _snowmanxx = source.get();
               int _snowmanxxx = _snowmanx - (int)_snowman.stream().filter(_snowmanxx::equals).count();
               if (_snowmanxxx == 0) {
                  return 0;
               } else {
                  _snowman.clear();
                  if (!_snowman.addTag(0, _snowmanxx)) {
                     return 0;
                  } else {
                     for (int _snowmanxxxx = 1; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
                        _snowman.addTag(_snowmanxxxx, source.get());
                     }

                     return _snowmanxxx;
                  }
               }
            }
         }
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof AbstractListTag) {
            AbstractListTag<?> _snowman = (AbstractListTag<?>)current;
            int _snowmanx = _snowman.size();
            if (_snowmanx > 0) {
               _snowman.clear();
               return _snowmanx;
            }
         }

         return 0;
      }
   }

   static class FilteredListElementNode implements NbtPathArgumentType.PathNode {
      private final CompoundTag filter;
      private final Predicate<Tag> predicate;

      public FilteredListElementNode(CompoundTag filter) {
         this.filter = filter;
         this.predicate = NbtPathArgumentType.getPredicate(filter);
      }

      @Override
      public void get(Tag current, List<Tag> results) {
         if (current instanceof ListTag) {
            ListTag _snowman = (ListTag)current;
            _snowman.stream().filter(this.predicate).forEach(results::add);
         }
      }

      @Override
      public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
         MutableBoolean _snowman = new MutableBoolean();
         if (current instanceof ListTag) {
            ListTag _snowmanx = (ListTag)current;
            _snowmanx.stream().filter(this.predicate).forEach(_snowmanxx -> {
               results.add(_snowmanxx);
               _snowman.setTrue();
            });
            if (_snowman.isFalse()) {
               CompoundTag _snowmanxx = this.filter.copy();
               _snowmanx.add(_snowmanxx);
               results.add(_snowmanxx);
            }
         }
      }

      @Override
      public Tag init() {
         return new ListTag();
      }

      @Override
      public int set(Tag current, Supplier<Tag> source) {
         int _snowman = 0;
         if (current instanceof ListTag) {
            ListTag _snowmanx = (ListTag)current;
            int _snowmanxx = _snowmanx.size();
            if (_snowmanxx == 0) {
               _snowmanx.add(source.get());
               _snowman++;
            } else {
               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
                  Tag _snowmanxxxx = _snowmanx.get(_snowmanxxx);
                  if (this.predicate.test(_snowmanxxxx)) {
                     Tag _snowmanxxxxx = source.get();
                     if (!_snowmanxxxxx.equals(_snowmanxxxx) && _snowmanx.setTag(_snowmanxxx, _snowmanxxxxx)) {
                        _snowman++;
                     }
                  }
               }
            }
         }

         return _snowman;
      }

      @Override
      public int clear(Tag current) {
         int _snowman = 0;
         if (current instanceof ListTag) {
            ListTag _snowmanx = (ListTag)current;

            for (int _snowmanxx = _snowmanx.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
               if (this.predicate.test(_snowmanx.get(_snowmanxx))) {
                  _snowmanx.remove(_snowmanxx);
                  _snowman++;
               }
            }
         }

         return _snowman;
      }
   }

   static class FilteredNamedNode implements NbtPathArgumentType.PathNode {
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
         if (current instanceof CompoundTag) {
            Tag _snowman = ((CompoundTag)current).get(this.name);
            if (this.predicate.test(_snowman)) {
               results.add(_snowman);
            }
         }
      }

      @Override
      public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
         if (current instanceof CompoundTag) {
            CompoundTag _snowman = (CompoundTag)current;
            Tag _snowmanx = _snowman.get(this.name);
            if (_snowmanx == null) {
               Tag var6 = this.filter.copy();
               _snowman.put(this.name, var6);
               results.add(var6);
            } else if (this.predicate.test(_snowmanx)) {
               results.add(_snowmanx);
            }
         }
      }

      @Override
      public Tag init() {
         return new CompoundTag();
      }

      @Override
      public int set(Tag current, Supplier<Tag> source) {
         if (current instanceof CompoundTag) {
            CompoundTag _snowman = (CompoundTag)current;
            Tag _snowmanx = _snowman.get(this.name);
            if (this.predicate.test(_snowmanx)) {
               Tag _snowmanxx = source.get();
               if (!_snowmanxx.equals(_snowmanx)) {
                  _snowman.put(this.name, _snowmanxx);
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof CompoundTag) {
            CompoundTag _snowman = (CompoundTag)current;
            Tag _snowmanx = _snowman.get(this.name);
            if (this.predicate.test(_snowmanx)) {
               _snowman.remove(this.name);
               return 1;
            }
         }

         return 0;
      }
   }

   static class FilteredRootNode implements NbtPathArgumentType.PathNode {
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

   static class IndexedListElementNode implements NbtPathArgumentType.PathNode {
      private final int index;

      public IndexedListElementNode(int index) {
         this.index = index;
      }

      @Override
      public void get(Tag current, List<Tag> results) {
         if (current instanceof AbstractListTag) {
            AbstractListTag<?> _snowman = (AbstractListTag<?>)current;
            int _snowmanx = _snowman.size();
            int _snowmanxx = this.index < 0 ? _snowmanx + this.index : this.index;
            if (0 <= _snowmanxx && _snowmanxx < _snowmanx) {
               results.add(_snowman.get(_snowmanxx));
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
            AbstractListTag<?> _snowman = (AbstractListTag<?>)current;
            int _snowmanx = _snowman.size();
            int _snowmanxx = this.index < 0 ? _snowmanx + this.index : this.index;
            if (0 <= _snowmanxx && _snowmanxx < _snowmanx) {
               Tag _snowmanxxx = _snowman.get(_snowmanxx);
               Tag _snowmanxxxx = source.get();
               if (!_snowmanxxxx.equals(_snowmanxxx) && _snowman.setTag(_snowmanxx, _snowmanxxxx)) {
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof AbstractListTag) {
            AbstractListTag<?> _snowman = (AbstractListTag<?>)current;
            int _snowmanx = _snowman.size();
            int _snowmanxx = this.index < 0 ? _snowmanx + this.index : this.index;
            if (0 <= _snowmanxx && _snowmanxx < _snowmanx) {
               _snowman.remove(_snowmanxx);
               return 1;
            }
         }

         return 0;
      }
   }

   static class NamedNode implements NbtPathArgumentType.PathNode {
      private final String name;

      public NamedNode(String name) {
         this.name = name;
      }

      @Override
      public void get(Tag current, List<Tag> results) {
         if (current instanceof CompoundTag) {
            Tag _snowman = ((CompoundTag)current).get(this.name);
            if (_snowman != null) {
               results.add(_snowman);
            }
         }
      }

      @Override
      public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
         if (current instanceof CompoundTag) {
            CompoundTag _snowman = (CompoundTag)current;
            Tag _snowmanx;
            if (_snowman.contains(this.name)) {
               _snowmanx = _snowman.get(this.name);
            } else {
               _snowmanx = source.get();
               _snowman.put(this.name, _snowmanx);
            }

            results.add(_snowmanx);
         }
      }

      @Override
      public Tag init() {
         return new CompoundTag();
      }

      @Override
      public int set(Tag current, Supplier<Tag> source) {
         if (current instanceof CompoundTag) {
            CompoundTag _snowman = (CompoundTag)current;
            Tag _snowmanx = source.get();
            Tag _snowmanxx = _snowman.put(this.name, _snowmanx);
            if (!_snowmanx.equals(_snowmanxx)) {
               return 1;
            }
         }

         return 0;
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof CompoundTag) {
            CompoundTag _snowman = (CompoundTag)current;
            if (_snowman.contains(this.name)) {
               _snowman.remove(this.name);
               return 1;
            }
         }

         return 0;
      }
   }

   public static class NbtPath {
      private final String string;
      private final Object2IntMap<NbtPathArgumentType.PathNode> nodeEndIndices;
      private final NbtPathArgumentType.PathNode[] nodes;

      public NbtPath(String string, NbtPathArgumentType.PathNode[] nodes, Object2IntMap<NbtPathArgumentType.PathNode> nodeEndIndices) {
         this.string = string;
         this.nodes = nodes;
         this.nodeEndIndices = nodeEndIndices;
      }

      public List<Tag> get(Tag tag) throws CommandSyntaxException {
         List<Tag> _snowman = Collections.singletonList(tag);

         for (NbtPathArgumentType.PathNode _snowmanx : this.nodes) {
            _snowman = _snowmanx.get(_snowman);
            if (_snowman.isEmpty()) {
               throw this.createNothingFoundException(_snowmanx);
            }
         }

         return _snowman;
      }

      public int count(Tag tag) {
         List<Tag> _snowman = Collections.singletonList(tag);

         for (NbtPathArgumentType.PathNode _snowmanx : this.nodes) {
            _snowman = _snowmanx.get(_snowman);
            if (_snowman.isEmpty()) {
               return 0;
            }
         }

         return _snowman.size();
      }

      private List<Tag> getTerminals(Tag start) throws CommandSyntaxException {
         List<Tag> _snowman = Collections.singletonList(start);

         for (int _snowmanx = 0; _snowmanx < this.nodes.length - 1; _snowmanx++) {
            NbtPathArgumentType.PathNode _snowmanxx = this.nodes[_snowmanx];
            int _snowmanxxx = _snowmanx + 1;
            _snowman = _snowmanxx.getOrInit(_snowman, this.nodes[_snowmanxxx]::init);
            if (_snowman.isEmpty()) {
               throw this.createNothingFoundException(_snowmanxx);
            }
         }

         return _snowman;
      }

      public List<Tag> getOrInit(Tag tag, Supplier<Tag> source) throws CommandSyntaxException {
         List<Tag> _snowman = this.getTerminals(tag);
         NbtPathArgumentType.PathNode _snowmanx = this.nodes[this.nodes.length - 1];
         return _snowmanx.getOrInit(_snowman, source);
      }

      private static int forEach(List<Tag> tags, Function<Tag, Integer> operation) {
         return tags.stream().map(operation).reduce(0, (_snowman, _snowmanx) -> _snowman + _snowmanx);
      }

      public int put(Tag tag, Supplier<Tag> source) throws CommandSyntaxException {
         List<Tag> _snowman = this.getTerminals(tag);
         NbtPathArgumentType.PathNode _snowmanx = this.nodes[this.nodes.length - 1];
         return forEach(_snowman, _snowmanxx -> _snowman.set(_snowmanxx, source));
      }

      public int remove(Tag tag) {
         List<Tag> _snowman = Collections.singletonList(tag);

         for (int _snowmanx = 0; _snowmanx < this.nodes.length - 1; _snowmanx++) {
            _snowman = this.nodes[_snowmanx].get(_snowman);
         }

         NbtPathArgumentType.PathNode _snowmanx = this.nodes[this.nodes.length - 1];
         return forEach(_snowman, _snowmanx::clear);
      }

      private CommandSyntaxException createNothingFoundException(NbtPathArgumentType.PathNode node) {
         int _snowman = this.nodeEndIndices.getInt(node);
         return NbtPathArgumentType.NOTHING_FOUND_EXCEPTION.create(this.string.substring(0, _snowman));
      }

      @Override
      public String toString() {
         return this.string;
      }
   }

   interface PathNode {
      void get(Tag current, List<Tag> results);

      void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results);

      Tag init();

      int set(Tag current, Supplier<Tag> source);

      int clear(Tag current);

      default List<Tag> get(List<Tag> tags) {
         return this.process(tags, this::get);
      }

      default List<Tag> getOrInit(List<Tag> tags, Supplier<Tag> supplier) {
         return this.process(tags, (current, results) -> this.getOrInit(current, supplier, results));
      }

      default List<Tag> process(List<Tag> tags, BiConsumer<Tag, List<Tag>> action) {
         List<Tag> _snowman = Lists.newArrayList();

         for (Tag _snowmanx : tags) {
            action.accept(_snowmanx, _snowman);
         }

         return _snowman;
      }
   }
}
