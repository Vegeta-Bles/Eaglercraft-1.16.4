import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.annotation.Nullable;

public class pd implements oj<om> {
   private RootCommandNode<dd> a;

   public pd() {
   }

   public pd(RootCommandNode<dd> var1) {
      this.a = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      pd.a[] _snowman = new pd.a[_snowman.i()];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = c(_snowman);
      }

      a(_snowman);
      this.a = (RootCommandNode<dd>)_snowman[_snowman.i()].e;
   }

   @Override
   public void b(nf var1) throws IOException {
      Object2IntMap<CommandNode<dd>> _snowman = a(this.a);
      CommandNode<dd>[] _snowmanx = a(_snowman);
      _snowman.d(_snowmanx.length);

      for (CommandNode<dd> _snowmanxx : _snowmanx) {
         a(_snowman, _snowmanxx, _snowman);
      }

      _snowman.d(_snowman.get(this.a));
   }

   private static void a(pd.a[] var0) {
      List<pd.a> _snowman = Lists.newArrayList(_snowman);

      while (!_snowman.isEmpty()) {
         boolean _snowmanx = _snowman.removeIf(var1x -> var1x.a(_snowman));
         if (!_snowmanx) {
            throw new IllegalStateException("Server sent an impossible command tree");
         }
      }
   }

   private static Object2IntMap<CommandNode<dd>> a(RootCommandNode<dd> var0) {
      Object2IntMap<CommandNode<dd>> _snowman = new Object2IntOpenHashMap();
      Queue<CommandNode<dd>> _snowmanx = Queues.newArrayDeque();
      _snowmanx.add(_snowman);

      CommandNode<dd> _snowmanxx;
      while ((_snowmanxx = _snowmanx.poll()) != null) {
         if (!_snowman.containsKey(_snowmanxx)) {
            int _snowmanxxx = _snowman.size();
            _snowman.put(_snowmanxx, _snowmanxxx);
            _snowmanx.addAll(_snowmanxx.getChildren());
            if (_snowmanxx.getRedirect() != null) {
               _snowmanx.add(_snowmanxx.getRedirect());
            }
         }
      }

      return _snowman;
   }

   private static CommandNode<dd>[] a(Object2IntMap<CommandNode<dd>> var0) {
      CommandNode<dd>[] _snowman = new CommandNode[_snowman.size()];
      ObjectIterator var2 = Object2IntMaps.fastIterable(_snowman).iterator();

      while (var2.hasNext()) {
         Entry<CommandNode<dd>> _snowmanx = (Entry<CommandNode<dd>>)var2.next();
         _snowman[_snowmanx.getIntValue()] = (CommandNode<dd>)_snowmanx.getKey();
      }

      return _snowman;
   }

   private static pd.a c(nf var0) {
      byte _snowman = _snowman.readByte();
      int[] _snowmanx = _snowman.b();
      int _snowmanxx = (_snowman & 8) != 0 ? _snowman.i() : 0;
      ArgumentBuilder<dd, ?> _snowmanxxx = a(_snowman, _snowman);
      return new pd.a(_snowmanxxx, _snowman, _snowmanxx, _snowmanx);
   }

   @Nullable
   private static ArgumentBuilder<dd, ?> a(nf var0, byte var1) {
      int _snowman = _snowman & 3;
      if (_snowman == 2) {
         String _snowmanx = _snowman.e(32767);
         ArgumentType<?> _snowmanxx = fk.a(_snowman);
         if (_snowmanxx == null) {
            return null;
         } else {
            RequiredArgumentBuilder<dd, ?> _snowmanxxx = RequiredArgumentBuilder.argument(_snowmanx, _snowmanxx);
            if ((_snowman & 16) != 0) {
               _snowmanxxx.suggests(fm.a(_snowman.p()));
            }

            return _snowmanxxx;
         }
      } else {
         return _snowman == 1 ? LiteralArgumentBuilder.literal(_snowman.e(32767)) : null;
      }
   }

   private static void a(nf var0, CommandNode<dd> var1, Map<CommandNode<dd>, Integer> var2) {
      byte _snowman = 0;
      if (_snowman.getRedirect() != null) {
         _snowman = (byte)(_snowman | 8);
      }

      if (_snowman.getCommand() != null) {
         _snowman = (byte)(_snowman | 4);
      }

      if (_snowman instanceof RootCommandNode) {
         _snowman = (byte)(_snowman | 0);
      } else if (_snowman instanceof ArgumentCommandNode) {
         _snowman = (byte)(_snowman | 2);
         if (((ArgumentCommandNode)_snowman).getCustomSuggestions() != null) {
            _snowman = (byte)(_snowman | 16);
         }
      } else {
         if (!(_snowman instanceof LiteralCommandNode)) {
            throw new UnsupportedOperationException("Unknown node type " + _snowman);
         }

         _snowman = (byte)(_snowman | 1);
      }

      _snowman.writeByte(_snowman);
      _snowman.d(_snowman.getChildren().size());

      for (CommandNode<dd> _snowmanx : _snowman.getChildren()) {
         _snowman.d(_snowman.get(_snowmanx));
      }

      if (_snowman.getRedirect() != null) {
         _snowman.d(_snowman.get(_snowman.getRedirect()));
      }

      if (_snowman instanceof ArgumentCommandNode) {
         ArgumentCommandNode<dd, ?> _snowmanx = (ArgumentCommandNode<dd, ?>)_snowman;
         _snowman.a(_snowmanx.getName());
         fk.a(_snowman, _snowmanx.getType());
         if (_snowmanx.getCustomSuggestions() != null) {
            _snowman.a(fm.a(_snowmanx.getCustomSuggestions()));
         }
      } else if (_snowman instanceof LiteralCommandNode) {
         _snowman.a(((LiteralCommandNode)_snowman).getLiteral());
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public RootCommandNode<dd> b() {
      return this.a;
   }

   static class a {
      @Nullable
      private final ArgumentBuilder<dd, ?> a;
      private final byte b;
      private final int c;
      private final int[] d;
      @Nullable
      private CommandNode<dd> e;

      private a(@Nullable ArgumentBuilder<dd, ?> var1, byte var2, int var3, int[] var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public boolean a(pd.a[] var1) {
         if (this.e == null) {
            if (this.a == null) {
               this.e = new RootCommandNode();
            } else {
               if ((this.b & 8) != 0) {
                  if (_snowman[this.c].e == null) {
                     return false;
                  }

                  this.a.redirect(_snowman[this.c].e);
               }

               if ((this.b & 4) != 0) {
                  this.a.executes(var0 -> 0);
               }

               this.e = this.a.build();
            }
         }

         for (int _snowman : this.d) {
            if (_snowman[_snowman].e == null) {
               return false;
            }
         }

         for (int _snowmanx : this.d) {
            CommandNode<dd> _snowmanxx = _snowman[_snowmanx].e;
            if (!(_snowmanxx instanceof RootCommandNode)) {
               this.e.addChild(_snowmanxx);
            }
         }

         return true;
      }
   }
}
