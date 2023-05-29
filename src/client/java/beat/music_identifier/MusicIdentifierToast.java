package beat.music_identifier;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack   ;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;

public class MusicIdentifierToast implements Toast {
    private final Text description;
    private final ItemStack itemStack;

    private boolean justUpdated;
    private long startTime;

    public MusicIdentifierToast(Text description) {
        this(description, new ItemStack(Items.MUSIC_DISC_CAT));
    }

    public MusicIdentifierToast(Text description, ItemStack itemStack) {
        this.description = description;
        this.itemStack = itemStack;
    }

    @Override
    public Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        if (this.justUpdated) {
            this.startTime = startTime;
            this.justUpdated = false;
        }

        List<OrderedText> lines = manager.getClient().textRenderer.wrapLines(this.description, this.getWidth() - 40);

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (lines.size() == 1)
            DrawableHelper.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        else {
            int height = Math.max(0, lines.size() - 1) * 2;
            int m = Math.abs(Math.min(4, height - 28));
            // Draw the top border
            this.renderPart(matrices, manager, this.getWidth(), 0, 0, 28);

            // Draw background
            for (int n = 28; n < height - m; n += 10) {
                this.renderPart(matrices, manager, this.getWidth(), 16 /* middle */, n, Math.min(16, height - n - m));
            }

            // Draw the bottom border
            this.renderPart(matrices, manager, this.getWidth(), 32 - m, height * 8, m);
        }

        // Light Gray Text
        manager.getClient().textRenderer.draw(matrices, I18n.translate("music_identifier.toast.now_playing_title"), 28.0f, 6.0f, 0xff838383);

        if (lines.size() == 1) {
            manager.getClient().textRenderer.draw(matrices, lines.get(0), 28, 18, 0xffffffff);
        } else {
            for (int j = 0; j < lines.size(); ++j) {
                manager.getClient().textRenderer.draw(matrices, lines.get(j), 28, 10+(7+j*12), 0xffffffff);
            }
        }

        matrices.push();
        manager.getClient().getItemRenderer().renderInGui(matrices, itemStack, this.getWidth() / 22, this.getHeight() / 4);
        matrices.pop();

        return startTime - this.startTime >= 5000.0 ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void renderPart(MatrixStack matrices, ToastManager manager, int i, int vOffset, int y, int vHeight) {
        int uWidth = vOffset == 0 ? 20 : 5;
        int n = Math.min(60, i - uWidth);
        DrawableHelper.drawTexture(matrices, 0, y, 0, vOffset, uWidth, vHeight);

        for (int o = uWidth; o < i - n; o += 64) {
            DrawableHelper.drawTexture(matrices, o, y, 32, vOffset, Math.min(64, i - o - n), vHeight);
        }

        DrawableHelper.drawTexture(matrices, i - n, y, 160 - n, vOffset, n, vHeight);
    }
}
