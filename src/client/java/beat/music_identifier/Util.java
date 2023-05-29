package beat.music_identifier;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.Text;
import net.minecraft.client.resource.language.I18n;

public class Util {
    public static Text nowPlaying;

    public static boolean showingToast = false;

    public static void setNowPlaying(Text _nowPlaying) {
        nowPlaying = _nowPlaying;
    }

    public static Text getNowPlaying() {
        return nowPlaying;
    }

    public static Text getSoundName(SoundInstance instance) {
        // Gets the sound name and then removes the .ogg at the end
        String soundLocation = instance.getSound().getLocation().toString().split("\\.")[0];
        // If its music or it is listed in the language file
        if (soundLocation.startsWith("minecraft:sounds/music") && I18n.hasTranslation("music_identifier.sound." + soundLocation)) {
            // Return the text of the translated song name
            return Text.of(I18n.translate("music_identifier.sound." + soundLocation));
        }

        // Song isn't music or isn't translated
        return null;
    }

//    public static MusicDiscItem getDiscFromSound(SoundInstance instance) {
//        for (SoundEvent event : RecordItemAccessor.getDiscs().keySet()) {
//            if (event.getLocation().equals(instance.getLocation())) {
//                return MusicDiscItem.bySound(event);
//            }
//        }
//        return null;
//    }
}
