package beat.music_identifier.mixin;

import beat.music_identifier.MusicTrackerAccessor;
import net.minecraft.client.sound.MusicTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MusicTracker.class)
public interface MusicTrackerMixin extends MusicTrackerAccessor {
    @Accessor("timeUntilNextSong")
    @Override
    void setTimeUntilNextSong(int i);
}
