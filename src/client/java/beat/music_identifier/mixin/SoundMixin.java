package beat.music_identifier.mixin;

import beat.music_identifier.MusicHandler;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class SoundMixin {
    @Shadow @Final private SoundManager soundManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void registerSoundInstanceListener(CallbackInfo ci) {
        soundManager.registerListener(new MusicHandler());
    }
}
