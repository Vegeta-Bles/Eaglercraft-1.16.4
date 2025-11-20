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
      object -> new TranslatableText("arguments.nbtpath.nothing_found", object)
   );

   public NbtPathArgumentType() {
   }

   public static NbtPathArgumentType nbtPath() {
      return new NbtPathArgumentType();
   }

   public static NbtPathArgumentType.NbtPath getNbtPath(CommandContext<ServerCommandSource> context, String name) {
      return (NbtPathArgumentType.NbtPath)context.getArgument(name, NbtPathArgumentType.NbtPath.class);
   }

   public NbtPathArgumentType.NbtPath parse(StringReader stringReader) throws CommandSyntaxException {
      List<NbtPathArgumentType.PathNode> list = Lists.newArrayList();
      int i = stringReader.getCursor();
      Object2IntMap<NbtPathArgumentType.PathNode> object2IntMap = new Object2IntOpenHashMap();
      boolean bl = true;

      while (stringReader.canRead() && stringReader.peek() != ' ') {
         NbtPathArgumentType.PathNode lv = parseNode(stringReader, bl);
         list.add(lv);
         object2IntMap.put(lv, stringReader.getCursor() - i);
         bl = false;
         if (stringReader.canRead()) {
            char c = stringReader.peek();
            if (c != ' ' && c != '[' && c != '{') {
               stringReader.expect('.');
            }
         }
      }

      return new NbtPathArgumentType.NbtPath(
         stringReader.getString().substring(i, stringReader.getCursor()), list.toArray(new NbtPathArgumentType.PathNode[0]), object2IntMap
      );
   }

   private static NbtPathArgumentType.PathNode parseNode(StringReader reader, boolean root) throws CommandSyntaxException {
      switch (reader.peek()) {
         case '"':
            String string = reader.readString();
            return readCompoundChildNode(reader, string);
         case '[':
            reader.skip();
            int i = reader.peek();
            if (i == 123) {
               CompoundTag lv2 = new StringNbtReader(reader).parseCompoundTag();
               reader.expect(']');
               return new NbtPathArgumentType.FilteredListElementNode(lv2);
            } else {
               if (i == 93) {
                  reader.skip();
                  return NbtPathArgumentType.AllListElementNode.INSTANCE;
               }

               int j = reader.readInt();
               reader.expect(']');
               return new NbtPathArgumentType.IndexedListElementNode(j);
            }
         case '{':
            if (!root) {
               throw INVALID_PATH_NODE_EXCEPTION.createWithContext(reader);
            }

            CompoundTag lv = new StringNbtReader(reader).parseCompoundTag();
            return new NbtPathArgumentType.FilteredRootNode(lv);
         default:
            String string2 = readName(reader);
            return readCompoundChildNode(reader, string2);
      }
   }

   private static NbtPathArgumentType.PathNode readCompoundChildNode(StringReader reader, String name) throws CommandSyntaxException {
      if (reader.canRead() && reader.peek() == '{') {
         CompoundTag lv = new StringNbtReader(reader).parseCompoundTag();
         return new NbtPathArgumentType.FilteredNamedNode(name, lv);
      } else {
         return new NbtPathArgumentType.NamedNode(name);
      }
   }

   private static String readName(StringReader reader) throws CommandSyntaxException {
      int i = reader.getCursor();

      while (reader.canRead() && isNameCharacter(reader.peek())) {
         reader.skip();
      }

      if (reader.getCursor() == i) {
         throw INVALID_PATH_NODE_EXCEPTION.createWithContext(reader);
      } else {
         return reader.getString().substring(i, reader.getCursor());
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   private static boolean isNameCharacter(char c) {
      return c != ' ' && c != '"' && c != '[' && c != ']' && c != '.' && c != '{' && c != '}';
   }

   private static Predicate<Tag> getPredicate(CompoundTag filter) {
      return arg2 -> NbtHelper.matches(filter, arg2, true);
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
            AbstractListTag<?> lv = (AbstractListTag<?>)current;
            if (lv.isEmpty()) {
               Tag lv2 = source.get();
               if (lv.addTag(0, lv2)) {
                  results.add(lv2);
               }
            } else {
               results.addAll((Collection<? extends Tag>)lv);
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
            AbstractListTag<?> lv = (AbstractListTag<?>)current;
            int i = lv.size();
            if (i == 0) {
               lv.addTag(0, source.get());
               return 1;
            } else {
               Tag lv2 = source.get();
               int j = i - (int)lv.stream().filter(lv2::equals).count();
               if (j == 0) {
                  return 0;
               } else {
                  lv.clear();
                  if (!lv.addTag(0, lv2)) {
                     return 0;
                  } else {
                     for (int k = 1; k < i; k++) {
                        lv.addTag(k, source.get());
                     }

                     return j;
                  }
               }
            }
         }
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof AbstractListTag) {
            AbstractListTag<?> lv = (AbstractListTag<?>)current;
            int i = lv.size();
            if (i > 0) {
               lv.clear();
               return i;
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
            ListTag lv = (ListTag)current;
            lv.stream().filter(this.predicate).forEach(results::add);
         }
      }

      @Override
      public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
         MutableBoolean mutableBoolean = new MutableBoolean();
         if (current instanceof ListTag) {
            ListTag lv = (ListTag)current;
            lv.stream().filter(this.predicate).forEach(arg -> {
               results.add(arg);
               mutableBoolean.setTrue();
            });
            if (mutableBoolean.isFalse()) {
               CompoundTag lv2 = this.filter.copy();
               lv.add(lv2);
               results.add(lv2);
            }
         }
      }

      @Override
      public Tag init() {
         return new ListTag();
      }

      @Override
      public int set(Tag current, Supplier<Tag> source) {
         int i = 0;
         if (current instanceof ListTag) {
            ListTag lv = (ListTag)current;
            int j = lv.size();
            if (j == 0) {
               lv.add(source.get());
               i++;
            } else {
               for (int k = 0; k < j; k++) {
                  Tag lv2 = lv.get(k);
                  if (this.predicate.test(lv2)) {
                     Tag lv3 = source.get();
                     if (!lv3.equals(lv2) && lv.setTag(k, lv3)) {
                        i++;
                     }
                  }
               }
            }
         }

         return i;
      }

      @Override
      public int clear(Tag current) {
         int i = 0;
         if (current instanceof ListTag) {
            ListTag lv = (ListTag)current;

            for (int j = lv.size() - 1; j >= 0; j--) {
               if (this.predicate.test(lv.get(j))) {
                  lv.remove(j);
                  i++;
               }
            }
         }

         return i;
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
            Tag lv = ((CompoundTag)current).get(this.name);
            if (this.predicate.test(lv)) {
               results.add(lv);
            }
         }
      }

      @Override
      public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
         if (current instanceof CompoundTag) {
            CompoundTag lv = (CompoundTag)current;
            Tag lv2 = lv.get(this.name);
            if (lv2 == null) {
               Tag var6 = this.filter.copy();
               lv.put(this.name, var6);
               results.add(var6);
            } else if (this.predicate.test(lv2)) {
               results.add(lv2);
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
            CompoundTag lv = (CompoundTag)current;
            Tag lv2 = lv.get(this.name);
            if (this.predicate.test(lv2)) {
               Tag lv3 = source.get();
               if (!lv3.equals(lv2)) {
                  lv.put(this.name, lv3);
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof CompoundTag) {
            CompoundTag lv = (CompoundTag)current;
            Tag lv2 = lv.get(this.name);
            if (this.predicate.test(lv2)) {
               lv.remove(this.name);
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
            AbstractListTag<?> lv = (AbstractListTag<?>)current;
            int i = lv.size();
            int j = this.index < 0 ? i + this.index : this.index;
            if (0 <= j && j < i) {
               results.add(lv.get(j));
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
            AbstractListTag<?> lv = (AbstractListTag<?>)current;
            int i = lv.size();
            int j = this.index < 0 ? i + this.index : this.index;
            if (0 <= j && j < i) {
               Tag lv2 = lv.get(j);
               Tag lv3 = source.get();
               if (!lv3.equals(lv2) && lv.setTag(j, lv3)) {
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof AbstractListTag) {
            AbstractListTag<?> lv = (AbstractListTag<?>)current;
            int i = lv.size();
            int j = this.index < 0 ? i + this.index : this.index;
            if (0 <= j && j < i) {
               lv.remove(j);
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
            Tag lv = ((CompoundTag)current).get(this.name);
            if (lv != null) {
               results.add(lv);
            }
         }
      }

      @Override
      public void getOrInit(Tag current, Supplier<Tag> source, List<Tag> results) {
         if (current instanceof CompoundTag) {
            CompoundTag lv = (CompoundTag)current;
            Tag lv2;
            if (lv.contains(this.name)) {
               lv2 = lv.get(this.name);
            } else {
               lv2 = source.get();
               lv.put(this.name, lv2);
            }

            results.add(lv2);
         }
      }

      @Override
      public Tag init() {
         return new CompoundTag();
      }

      @Override
      public int set(Tag current, Supplier<Tag> source) {
         if (current instanceof CompoundTag) {
            CompoundTag lv = (CompoundTag)current;
            Tag lv2 = source.get();
            Tag lv3 = lv.put(this.name, lv2);
            if (!lv2.equals(lv3)) {
               return 1;
            }
         }

         return 0;
      }

      @Override
      public int clear(Tag current) {
         if (current instanceof CompoundTag) {
            CompoundTag lv = (CompoundTag)current;
            if (lv.contains(this.name)) {
               lv.remove(this.name);
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
         List<Tag> list = Collections.singletonList(tag);

         for (NbtPathArgumentType.PathNode lv : this.nodes) {
            list = lv.get(list);
            if (list.isEmpty()) {
               throw this.createNothingFoundException(lv);
            }
         }

         return list;
      }

      public int count(Tag tag) {
         List<Tag> list = Collections.singletonList(tag);

         for (NbtPathArgumentType.PathNode lv : this.nodes) {
            list = lv.get(list);
            if (list.isEmpty()) {
               return 0;
            }
         }

         return list.size();
      }

      private List<Tag> getTerminals(Tag start) throws CommandSyntaxException {
         List<Tag> list = Collections.singletonList(start);

         for (int i = 0; i < this.nodes.length - 1; i++) {
            NbtPathArgumentType.PathNode lv = this.nodes[i];
            int j = i + 1;
            list = lv.getOrInit(list, this.nodes[j]::init);
            if (list.isEmpty()) {
               throw this.createNothingFoundException(lv);
            }
         }

         return list;
      }

      public List<Tag> getOrInit(Tag tag, Supplier<Tag> source) throws CommandSyntaxException {
         List<Tag> list = this.getTerminals(tag);
         NbtPathArgumentType.PathNode lv = this.nodes[this.nodes.length - 1];
         return lv.getOrInit(list, source);
      }

      private static int forEach(List<Tag> tags, Function<Tag, Integer> operation) {
         return tags.stream().map(operation).reduce(0, (integer, integer2) -> integer + integer2);
      }

      public int put(Tag tag, Supplier<Tag> source) throws CommandSyntaxException {
         List<Tag> list = this.getTerminals(tag);
         NbtPathArgumentType.PathNode lv = this.nodes[this.nodes.length - 1];
         return forEach(list, arg2 -> lv.set(arg2, source));
      }

      public int remove(Tag tag) {
         List<Tag> list = Collections.singletonList(tag);

         for (int i = 0; i < this.nodes.length - 1; i++) {
            list = this.nodes[i].get(list);
         }

         NbtPathArgumentType.PathNode lv = this.nodes[this.nodes.length - 1];
         return forEach(list, lv::clear);
      }

      private CommandSyntaxException createNothingFoundException(NbtPathArgumentType.PathNode node) {
         int i = this.nodeEndIndices.getInt(node);
         return NbtPathArgumentType.NOTHING_FOUND_EXCEPTION.create(this.string.substring(0, i));
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
         List<Tag> list2 = Lists.newArrayList();

         for (Tag lv : tags) {
            action.accept(lv, list2);
         }

         return list2;
      }
   }
}
