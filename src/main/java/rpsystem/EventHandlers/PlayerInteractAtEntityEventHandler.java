package rpsystem.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rpsystem.MedievalRoleplayEngine;
import rpsystem.Objects.CharacterCard;

public class PlayerInteractAtEntityEventHandler {

    public void handle(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof Player) {

            Player target = (Player) event.getRightClicked();
            CharacterCard card = MedievalRoleplayEngine.getInstance().utilities.getCard(target.getUniqueId());

            Player player = event.getPlayer();

            if (!MedievalRoleplayEngine.getInstance().playersWithRightClickCooldown.contains(player.getUniqueId())) {
                MedievalRoleplayEngine.getInstance().playersWithRightClickCooldown.add(player.getUniqueId());

                if (player.hasPermission("rp.card.show.others") || player.hasPermission("rp.card.*") || player.hasPermission("rp.default")) {
                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "\n == " + "Character Card of " + Bukkit.getOfflinePlayer(card.getPlayerUUID()).getName() + " == ");
                    player.sendMessage(ChatColor.AQUA + "Name: " + card.getName());
                    player.sendMessage(ChatColor.AQUA + "Race: " + card.getRace());
                    player.sendMessage(ChatColor.AQUA + "Subculture: " + card.getSubculture());
                    player.sendMessage(ChatColor.AQUA + "Age: " + card.getAge());
                    player.sendMessage(ChatColor.AQUA + "Gender: " + card.getGender());
                    player.sendMessage(ChatColor.AQUA + "Religion: " + card.getReligion());

                    int seconds = 2;
                    MedievalRoleplayEngine.getInstance().getServer().getScheduler().runTaskLater(MedievalRoleplayEngine.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            MedievalRoleplayEngine.getInstance().playersWithRightClickCooldown.remove(player.getUniqueId());

                        }
                    }, seconds * 20);
                }

            }

        }
    }

}
