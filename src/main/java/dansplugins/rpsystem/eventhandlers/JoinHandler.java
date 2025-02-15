package dansplugins.rpsystem.eventhandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dansplugins.rpsystem.data.PersistentData;
import dansplugins.rpsystem.objects.RPCharacter;

/**
 * @author Daniel McCoy Stephenson
 */
public class JoinHandler implements Listener {

    @EventHandler()
    public void handle(PlayerJoinEvent event) {
        if (!PersistentData.getInstance().hasCharacter(event.getPlayer().getUniqueId())) {
            RPCharacter newCharacter = new RPCharacter(event.getPlayer().getUniqueId());
            PersistentData.getInstance().getCharacters().add(newCharacter);
        }
    }
}