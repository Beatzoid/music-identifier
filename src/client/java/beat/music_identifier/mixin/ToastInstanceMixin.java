package beat.music_identifier.mixin;

import beat.music_identifier.MusicIdentifierConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.toast.Toast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Toast.Visibility.class)
public class ToastInstanceMixin {

    @Inject(method = "playSound", cancellable = true, at = @At("HEAD"))
    void silenceWhooshSound(SoundManager soundManager, CallbackInfo ci) {
        MusicIdentifierConfig config = AutoConfig.getConfigHolder(MusicIdentifierConfig.class).getConfig();

        // TODO: Fix this so that it checks if the Toast is an instance of MusicIdentifierToast
        // TODO: and only checks the config and mutes the sound if it is
        if (config.silenceWhoosh)
            ci.cancel();
    }
}
