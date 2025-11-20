package net.minecraft.client.gui.hud;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

public class PlayerListHud extends DrawableHelper {
   private static final Ordering<PlayerListEntry> ENTRY_ORDERING = Ordering.from(new PlayerListHud.EntryOrderComparator());
   private final MinecraftClient client;
   private final InGameHud inGameHud;
   private Text footer;
   private Text header;
   private long showTime;
   private boolean visible;

   public PlayerListHud(MinecraftClient client, InGameHud inGameHud) {
      this.client = client;
      this.inGameHud = inGameHud;
   }

   public Text getPlayerName(PlayerListEntry _snowman) {
      return _snowman.getDisplayName() != null
         ? this.method_27538(_snowman, _snowman.getDisplayName().shallowCopy())
         : this.method_27538(_snowman, Team.modifyText(_snowman.getScoreboardTeam(), new LiteralText(_snowman.getProfile().getName())));
   }

   private Text method_27538(PlayerListEntry _snowman, MutableText _snowman) {
      return _snowman.getGameMode() == GameMode.SPECTATOR ? _snowman.formatted(Formatting.ITALIC) : _snowman;
   }

   public void tick(boolean visible) {
      if (visible && !this.visible) {
         this.showTime = Util.getMeasuringTimeMs();
      }

      this.visible = visible;
   }

   public void render(MatrixStack _snowman, int _snowman, Scoreboard _snowman, @Nullable ScoreboardObjective _snowman) {
      ClientPlayNetworkHandler _snowmanxxxx = this.client.player.networkHandler;
      List<PlayerListEntry> _snowmanxxxxx = ENTRY_ORDERING.sortedCopy(_snowmanxxxx.getPlayerList());
      int _snowmanxxxxxx = 0;
      int _snowmanxxxxxxx = 0;

      for (PlayerListEntry _snowmanxxxxxxxx : _snowmanxxxxx) {
         int _snowmanxxxxxxxxx = this.client.textRenderer.getWidth(this.getPlayerName(_snowmanxxxxxxxx));
         _snowmanxxxxxx = Math.max(_snowmanxxxxxx, _snowmanxxxxxxxxx);
         if (_snowman != null && _snowman.getRenderType() != ScoreboardCriterion.RenderType.HEARTS) {
            _snowmanxxxxxxxxx = this.client.textRenderer.getWidth(" " + _snowman.getPlayerScore(_snowmanxxxxxxxx.getProfile().getName(), _snowman).getScore());
            _snowmanxxxxxxx = Math.max(_snowmanxxxxxxx, _snowmanxxxxxxxxx);
         }
      }

      _snowmanxxxxx = _snowmanxxxxx.subList(0, Math.min(_snowmanxxxxx.size(), 80));
      int _snowmanxxxxxxxxx = _snowmanxxxxx.size();
      int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx;

      int _snowmanxxxxxxxxxxx;
      for (_snowmanxxxxxxxxxxx = 1; _snowmanxxxxxxxxxx > 20; _snowmanxxxxxxxxxx = (_snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx - 1) / _snowmanxxxxxxxxxxx) {
         _snowmanxxxxxxxxxxx++;
      }

      boolean _snowmanxxxxxxxxxxxx = this.client.isInSingleplayer() || this.client.getNetworkHandler().getConnection().isEncrypted();
      int _snowmanxxxxxxxxxxxxx;
      if (_snowman != null) {
         if (_snowman.getRenderType() == ScoreboardCriterion.RenderType.HEARTS) {
            _snowmanxxxxxxxxxxxxx = 90;
         } else {
            _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx;
         }
      } else {
         _snowmanxxxxxxxxxxxxx = 0;
      }

      int _snowmanxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxx * ((_snowmanxxxxxxxxxxxx ? 9 : 0) + _snowmanxxxxxx + _snowmanxxxxxxxxxxxxx + 13), _snowman - 50) / _snowmanxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxx = _snowman / 2 - (_snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx + (_snowmanxxxxxxxxxxx - 1) * 5) / 2;
      int _snowmanxxxxxxxxxxxxxxxx = 10;
      int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx + (_snowmanxxxxxxxxxxx - 1) * 5;
      List<OrderedText> _snowmanxxxxxxxxxxxxxxxxxx = null;
      if (this.header != null) {
         _snowmanxxxxxxxxxxxxxxxxxx = this.client.textRenderer.wrapLines(this.header, _snowman - 50);

         for (OrderedText _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxxx, this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxxxxx));
         }
      }

      List<OrderedText> _snowmanxxxxxxxxxxxxxxxxxxx = null;
      if (this.footer != null) {
         _snowmanxxxxxxxxxxxxxxxxxxx = this.client.textRenderer.wrapLines(this.footer, _snowman - 50);

         for (OrderedText _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxxx, this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxxxxxx));
         }
      }

      if (_snowmanxxxxxxxxxxxxxxxxxx != null) {
         fill(
            _snowman,
            _snowman / 2 - _snowmanxxxxxxxxxxxxxxxxx / 2 - 1,
            _snowmanxxxxxxxxxxxxxxxx - 1,
            _snowman / 2 + _snowmanxxxxxxxxxxxxxxxxx / 2 + 1,
            _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx.size() * 9,
            Integer.MIN_VALUE
         );

         for (OrderedText _snowmanxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxx) {
            int _snowmanxxxxxxxxxxxxxxxxxxxxx = this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxxxxxx);
            this.client.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, (float)(_snowman / 2 - _snowmanxxxxxxxxxxxxxxxxxxxxx / 2), (float)_snowmanxxxxxxxxxxxxxxxx, -1);
            _snowmanxxxxxxxxxxxxxxxx += 9;
         }

         _snowmanxxxxxxxxxxxxxxxx++;
      }

      fill(
         _snowman,
         _snowman / 2 - _snowmanxxxxxxxxxxxxxxxxx / 2 - 1,
         _snowmanxxxxxxxxxxxxxxxx - 1,
         _snowman / 2 + _snowmanxxxxxxxxxxxxxxxxx / 2 + 1,
         _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx * 9,
         Integer.MIN_VALUE
      );
      int _snowmanxxxxxxxxxxxxxxxxxxxx = this.client.options.getTextBackgroundColor(553648127);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx % _snowmanxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxx * 5;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxx * 9;
         fill(
            _snowman,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 8,
            _snowmanxxxxxxxxxxxxxxxxxxxx
         );
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         if (_snowmanxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxx.size()) {
            PlayerListEntry _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx.get(_snowmanxxxxxxxxxxxxxxxxxxxxx);
            GameProfile _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getProfile();
            if (_snowmanxxxxxxxxxxxx) {
               PlayerEntity _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.client.world.getPlayerByUuid(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getId());
               boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null
                  && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isPartVisible(PlayerModelPart.CAPE)
                  && ("Dinnerbone".equals(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getName()) || "Grumm".equals(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getName()));
               this.client.getTextureManager().bindTexture(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getSkinTexture());
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx ? 8 : 0);
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 * (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx ? -1 : 1);
               DrawableHelper.drawTexture(
                  _snowman,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                  8,
                  8,
                  8.0F,
                  (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  8,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  64,
                  64
               );
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isPartVisible(PlayerModelPart.HAT)) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx ? 8 : 0);
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 * (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx ? -1 : 1);
                  DrawableHelper.drawTexture(
                     _snowman,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                     8,
                     8,
                     40.0F,
                     (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     8,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     64,
                     64
                  );
               }

               _snowmanxxxxxxxxxxxxxxxxxxxxxxxx += 9;
            }

            this.client
               .textRenderer
               .drawWithShadow(
                  _snowman,
                  this.getPlayerName(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx),
                  (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                  (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getGameMode() == GameMode.SPECTATOR ? -1862270977 : -1
               );
            if (_snowman != null && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.getGameMode() != GameMode.SPECTATOR) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxx + 1;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 5) {
                  this.renderScoreboardObjective(
                     _snowman,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getName(),
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowman
                  );
               }
            }

            this.renderLatencyIcon(
               _snowman, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx - (_snowmanxxxxxxxxxxxx ? 9 : 0), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
         }
      }

      if (_snowmanxxxxxxxxxxxxxxxxxxx != null) {
         _snowmanxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxx * 9 + 1;
         fill(
            _snowman,
            _snowman / 2 - _snowmanxxxxxxxxxxxxxxxxx / 2 - 1,
            _snowmanxxxxxxxxxxxxxxxx - 1,
            _snowman / 2 + _snowmanxxxxxxxxxxxxxxxxx / 2 + 1,
            _snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx.size() * 9,
            Integer.MIN_VALUE
         );

         for (OrderedText _snowmanxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxx) {
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxxxxxxxxx);
            this.client.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxx, (float)(_snowman / 2 - _snowmanxxxxxxxxxxxxxxxxxxxxxxx / 2), (float)_snowmanxxxxxxxxxxxxxxxx, -1);
            _snowmanxxxxxxxxxxxxxxxx += 9;
         }
      }
   }

   protected void renderLatencyIcon(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, PlayerListEntry _snowman) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
      int _snowmanxxxxx = 0;
      int _snowmanxxxxxx;
      if (_snowman.getLatency() < 0) {
         _snowmanxxxxxx = 5;
      } else if (_snowman.getLatency() < 150) {
         _snowmanxxxxxx = 0;
      } else if (_snowman.getLatency() < 300) {
         _snowmanxxxxxx = 1;
      } else if (_snowman.getLatency() < 600) {
         _snowmanxxxxxx = 2;
      } else if (_snowman.getLatency() < 1000) {
         _snowmanxxxxxx = 3;
      } else {
         _snowmanxxxxxx = 4;
      }

      this.setZOffset(this.getZOffset() + 100);
      this.drawTexture(_snowman, _snowman + _snowman - 11, _snowman, 0, 176 + _snowmanxxxxxx * 8, 10, 8);
      this.setZOffset(this.getZOffset() - 100);
   }

   private void renderScoreboardObjective(ScoreboardObjective _snowman, int _snowman, String _snowman, int _snowman, int _snowman, PlayerListEntry _snowman, MatrixStack _snowman) {
      int _snowmanxxxxxxx = _snowman.getScoreboard().getPlayerScore(_snowman, _snowman).getScore();
      if (_snowman.getRenderType() == ScoreboardCriterion.RenderType.HEARTS) {
         this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
         long _snowmanxxxxxxxx = Util.getMeasuringTimeMs();
         if (this.showTime == _snowman.method_2976()) {
            if (_snowmanxxxxxxx < _snowman.method_2973()) {
               _snowman.method_2978(_snowmanxxxxxxxx);
               _snowman.method_2975((long)(this.inGameHud.getTicks() + 20));
            } else if (_snowmanxxxxxxx > _snowman.method_2973()) {
               _snowman.method_2978(_snowmanxxxxxxxx);
               _snowman.method_2975((long)(this.inGameHud.getTicks() + 10));
            }
         }

         if (_snowmanxxxxxxxx - _snowman.method_2974() > 1000L || this.showTime != _snowman.method_2976()) {
            _snowman.method_2972(_snowmanxxxxxxx);
            _snowman.method_2965(_snowmanxxxxxxx);
            _snowman.method_2978(_snowmanxxxxxxxx);
         }

         _snowman.method_2964(this.showTime);
         _snowman.method_2972(_snowmanxxxxxxx);
         int _snowmanxxxxxxxxx = MathHelper.ceil((float)Math.max(_snowmanxxxxxxx, _snowman.method_2960()) / 2.0F);
         int _snowmanxxxxxxxxxx = Math.max(MathHelper.ceil((float)(_snowmanxxxxxxx / 2)), Math.max(MathHelper.ceil((float)(_snowman.method_2960() / 2)), 10));
         boolean _snowmanxxxxxxxxxxx = _snowman.method_2961() > (long)this.inGameHud.getTicks() && (_snowman.method_2961() - (long)this.inGameHud.getTicks()) / 3L % 2L == 1L;
         if (_snowmanxxxxxxxxx > 0) {
            int _snowmanxxxxxxxxxxxx = MathHelper.floor(Math.min((float)(_snowman - _snowman - 4) / (float)_snowmanxxxxxxxxxx, 9.0F));
            if (_snowmanxxxxxxxxxxxx > 3) {
               for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                  this.drawTexture(_snowman, _snowman + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxx ? 25 : 16, 0, 9, 9);
               }

               for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
                  this.drawTexture(_snowman, _snowman + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxx ? 25 : 16, 0, 9, 9);
                  if (_snowmanxxxxxxxxxxx) {
                     if (_snowmanxxxxxxxxxxxxx * 2 + 1 < _snowman.method_2960()) {
                        this.drawTexture(_snowman, _snowman + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx, _snowman, 70, 0, 9, 9);
                     }

                     if (_snowmanxxxxxxxxxxxxx * 2 + 1 == _snowman.method_2960()) {
                        this.drawTexture(_snowman, _snowman + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx, _snowman, 79, 0, 9, 9);
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxx * 2 + 1 < _snowmanxxxxxxx) {
                     this.drawTexture(_snowman, _snowman + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxx >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (_snowmanxxxxxxxxxxxxx * 2 + 1 == _snowmanxxxxxxx) {
                     this.drawTexture(_snowman, _snowman + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxxxx >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float _snowmanxxxxxxxxxxxxx = MathHelper.clamp((float)_snowmanxxxxxxx / 20.0F, 0.0F, 1.0F);
               int _snowmanxxxxxxxxxxxxxx = (int)((1.0F - _snowmanxxxxxxxxxxxxx) * 255.0F) << 16 | (int)(_snowmanxxxxxxxxxxxxx * 255.0F) << 8;
               String _snowmanxxxxxxxxxxxxxxx = "" + (float)_snowmanxxxxxxx / 2.0F;
               if (_snowman - this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxx + "hp") >= _snowman) {
                  _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx + "hp";
               }

               this.client
                  .textRenderer
                  .drawWithShadow(
                     _snowman, _snowmanxxxxxxxxxxxxxxx, (float)((_snowman + _snowman) / 2 - this.client.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxx) / 2), (float)_snowman, _snowmanxxxxxxxxxxxxxx
                  );
            }
         }
      } else {
         String _snowmanxxxxxxxxx = Formatting.YELLOW + "" + _snowmanxxxxxxx;
         this.client.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxxxx, (float)(_snowman - this.client.textRenderer.getWidth(_snowmanxxxxxxxxx)), (float)_snowman, 16777215);
      }
   }

   public void setFooter(@Nullable Text footer) {
      this.footer = footer;
   }

   public void setHeader(@Nullable Text header) {
      this.header = header;
   }

   public void clear() {
      this.header = null;
      this.footer = null;
   }

   static class EntryOrderComparator implements Comparator<PlayerListEntry> {
      private EntryOrderComparator() {
      }

      public int compare(PlayerListEntry _snowman, PlayerListEntry _snowman) {
         Team _snowmanxx = _snowman.getScoreboardTeam();
         Team _snowmanxxx = _snowman.getScoreboardTeam();
         return ComparisonChain.start()
            .compareTrueFirst(_snowman.getGameMode() != GameMode.SPECTATOR, _snowman.getGameMode() != GameMode.SPECTATOR)
            .compare(_snowmanxx != null ? _snowmanxx.getName() : "", _snowmanxxx != null ? _snowmanxxx.getName() : "")
            .compare(_snowman.getProfile().getName(), _snowman.getProfile().getName(), String::compareToIgnoreCase)
            .result();
      }
   }
}
