package beat.music_identifier;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

@Config(name = "music-identifier")
public class MusicIdentifierConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public Style musicStyle = Style.Toast;

    @ConfigEntry.Gui.Tooltip
    public boolean silenceWhoosh = false;

    public enum Style implements SelectionListEntry.Translatable {
        Hotbar {
            @Override
            public @NotNull String getKey() {
                return "music-identifier.config.style.hotbar";
            }
        },
        Toast {
            @Override
            public @NotNull String getKey() {
                return "music-identifier.config.style.toast";
            }
        },
        Disabled {
            @Override
            public @NotNull String getKey() {
                return "music-identifier.config.style.disabled";
            }
        }
    }
}
