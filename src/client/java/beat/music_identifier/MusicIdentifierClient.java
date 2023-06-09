package beat.music_identifier;

import beat.music_identifier.mixin.MusicTrackerMixin;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class MusicIdentifierClient implements ClientModInitializer {
	public static String KEYBINDING_CATEGORY = "music-identifier.key.categories.music-identifier";

	@Override
	public void onInitializeClient() {
		AutoConfig.register(MusicIdentifierConfig.class, Toml4jConfigSerializer::new);

		KeyBinding startMusicKeybind = new KeyBinding(
				"music-identifier.key.reset_music_key_binding",
				GLFW.GLFW_KEY_Y,
				KEYBINDING_CATEGORY
		);

		KeyBinding displayPlayingSongKeyBind = new KeyBinding(
				"music-identifier.key.display_song",
				GLFW.GLFW_KEY_G,
				KEYBINDING_CATEGORY
		);

		KeyBindingHelper.registerKeyBinding(startMusicKeybind);
		KeyBindingHelper.registerKeyBinding(displayPlayingSongKeyBind);

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (startMusicKeybind.isPressed()) {
				MusicTracker musicTracker = client.getMusicTracker();
				MusicTrackerMixin musicTrackerMixin = (MusicTrackerMixin) musicTracker;

				if (client.options.sneakKey.isPressed()) {
					client.getMusicTracker().stop();
					musicTrackerMixin.setTimeUntilNextSong(Integer.MAX_VALUE);
					Util.nowPlaying = null;
				} else {
					musicTrackerMixin.setTimeUntilNextSong(0);
				}
			}

			if (displayPlayingSongKeyBind.isPressed()) {
				Text name;

				if (Util.nowPlaying == null) {
					name = Text.of("nothing");
				} else {
					name = Util.getNowPlaying();
				}

				MusicIdentifierConfig config = AutoConfig.getConfigHolder(MusicIdentifierConfig.class).getConfig();

				if (config.musicStyle == MusicIdentifierConfig.Style.Hotbar)
					MinecraftClient.getInstance().inGameHud.setRecordPlayingOverlay(name);
				else if (config.musicStyle == MusicIdentifierConfig.Style.Toast) {
					ToastManager toastManager = MinecraftClient.getInstance().getToastManager();
					toastManager.clear();
					toastManager.add(new MusicIdentifierToast(name));
				}
			}
		});
	}
}