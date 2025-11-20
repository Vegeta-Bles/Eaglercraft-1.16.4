package net.minecraft.client.gui.hud;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BossBarHud extends DrawableHelper {
   private static final Identifier BARS_TEXTURE = new Identifier("textures/gui/bars.png");
   private final MinecraftClient client;
   private final Map<UUID, ClientBossBar> bossBars = Maps.newLinkedHashMap();

   public BossBarHud(MinecraftClient client) {
      this.client = client;
   }

   public void render(MatrixStack matrices) {
      if (!this.bossBars.isEmpty()) {
         int _snowman = this.client.getWindow().getScaledWidth();
         int _snowmanx = 12;

         for (ClientBossBar _snowmanxx : this.bossBars.values()) {
            int _snowmanxxx = _snowman / 2 - 91;
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.client.getTextureManager().bindTexture(BARS_TEXTURE);
            this.renderBossBar(matrices, _snowmanxxx, _snowmanx, _snowmanxx);
            Text _snowmanxxxx = _snowmanxx.getName();
            int _snowmanxxxxx = this.client.textRenderer.getWidth(_snowmanxxxx);
            int _snowmanxxxxxx = _snowman / 2 - _snowmanxxxxx / 2;
            int _snowmanxxxxxxx = _snowmanx - 9;
            this.client.textRenderer.drawWithShadow(matrices, _snowmanxxxx, (float)_snowmanxxxxxx, (float)_snowmanxxxxxxx, 16777215);
            _snowmanx += 10 + 9;
            if (_snowmanx >= this.client.getWindow().getScaledHeight() / 3) {
               break;
            }
         }
      }
   }

   private void renderBossBar(MatrixStack matrices, int x, int y, BossBar bossBar) {
      this.drawTexture(matrices, x, y, 0, bossBar.getColor().ordinal() * 5 * 2, 182, 5);
      if (bossBar.getOverlay() != BossBar.Style.PROGRESS) {
         this.drawTexture(matrices, x, y, 0, 80 + (bossBar.getOverlay().ordinal() - 1) * 5 * 2, 182, 5);
      }

      int _snowman = (int)(bossBar.getPercent() * 183.0F);
      if (_snowman > 0) {
         this.drawTexture(matrices, x, y, 0, bossBar.getColor().ordinal() * 5 * 2 + 5, _snowman, 5);
         if (bossBar.getOverlay() != BossBar.Style.PROGRESS) {
            this.drawTexture(matrices, x, y, 0, 80 + (bossBar.getOverlay().ordinal() - 1) * 5 * 2 + 5, _snowman, 5);
         }
      }
   }

   public void handlePacket(BossBarS2CPacket packet) {
      if (packet.getType() == BossBarS2CPacket.Type.ADD) {
         this.bossBars.put(packet.getUuid(), new ClientBossBar(packet));
      } else if (packet.getType() == BossBarS2CPacket.Type.REMOVE) {
         this.bossBars.remove(packet.getUuid());
      } else {
         this.bossBars.get(packet.getUuid()).handlePacket(packet);
      }
   }

   public void clear() {
      this.bossBars.clear();
   }

   public boolean shouldPlayDragonMusic() {
      if (!this.bossBars.isEmpty()) {
         for (BossBar _snowman : this.bossBars.values()) {
            if (_snowman.hasDragonMusic()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean shouldDarkenSky() {
      if (!this.bossBars.isEmpty()) {
         for (BossBar _snowman : this.bossBars.values()) {
            if (_snowman.getDarkenSky()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean shouldThickenFog() {
      if (!this.bossBars.isEmpty()) {
         for (BossBar _snowman : this.bossBars.values()) {
            if (_snowman.getThickenFog()) {
               return true;
            }
         }
      }

      return false;
   }
}
