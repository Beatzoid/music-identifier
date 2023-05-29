package beat.music_identifier;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class MusicHandler implements SoundInstanceListener {
    @Override
    public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
        MusicIdentifierConfig config = AutoConfig.getConfigHolder(MusicIdentifierConfig.class).getConfig();
        Screen screen = MinecraftClient.getInstance().currentScreen;

        if (!(screen instanceof GameMenuScreen) && sound.getCategory() == SoundCategory.MUSIC) {
            Text name = Util.getSoundName(sound);
            System.out.println(sound);
            if (name == null) return;

            Util.setNowPlaying(name);

            if (config.musicStyle == MusicIdentifierConfig.Style.Hotbar)
                MinecraftClient.getInstance().inGameHud.setRecordPlayingOverlay(name);
            else if (config.musicStyle == MusicIdentifierConfig.Style.Toast) {
                ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
                toastManager.clear();
                toastManager.add(new MusicIdentifierToast(name));
            }
        }
    }
}
