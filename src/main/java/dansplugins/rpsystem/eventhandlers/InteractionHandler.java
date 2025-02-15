package dansplugins.rpsystem.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import dansplugins.rpsystem.MedievalRoleplayEngine;
import dansplugins.rpsystem.data.EphemeralData;
import dansplugins.rpsystem.data.PersistentData;
import dansplugins.rpsystem.objects.RPCharacter;
import dansplugins.rpsystem.utils.Messenger;

/**
 * @author Daniel McCoy Stephenson
 */
public class InteractionHandler implements Listener {

    @EventHandler()
    public void handle(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {

            Player target = (Player) event.getRightClicked();
            RPCharacter card = PersistentData.getInstance().getCharacter(target.getUniqueId());

            Player player = event.getPlayer();

            if (card == null) {
                return;
            }

            if (!MedievalRoleplayEngine.getInstance().getConfig().getBoolean("rightClickToViewCard")) {
                return;
            }

            if (!EphemeralData.getInstance().getPlayersWithRightClickCooldown().contains(player.getUniqueId())) {
                EphemeralData.getInstance().getPlayersWithRightClickCooldown().add(player.getUniqueId());

                if (player.hasPermission("rp.card.show.others") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default") || !player.hasMetadata("NPC")) {
                    Messenger.getInstance().sendCardInfoToPlayer(card, player);

                    int seconds = 2;
                    MedievalRoleplayEngine.getInstance().getServer().getScheduler().runTaskLater(MedievalRoleplayEngine.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            EphemeralData.getInstance().getPlayersWithRightClickCooldown().remove(player.getUniqueId());

                        }
                    }, seconds * 20);
                }
            }
        }
    }
}