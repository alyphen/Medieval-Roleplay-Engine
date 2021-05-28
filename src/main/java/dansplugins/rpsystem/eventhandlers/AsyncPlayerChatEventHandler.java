package dansplugins.rpsystem.eventhandlers;

import dansplugins.rpsystem.utils.ColorChecker;
import dansplugins.rpsystem.MedievalRoleplayEngine;
import dansplugins.rpsystem.Messenger;
import dansplugins.rpsystem.data.EphemeralData;
import dansplugins.rpsystem.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.UUID;

public class AsyncPlayerChatEventHandler implements Listener {

    @EventHandler()
    public void handle(AsyncPlayerChatEvent event) {
        int localChatRadius = MedievalRoleplayEngine.getInstance().getConfig().getInt("localChatRadius");
        String localChatColor = MedievalRoleplayEngine.getInstance().getConfig().getString("localChatColor");
        if (EphemeralData.getInstance().getPlayersSpeakingInLocalChat().contains(event.getPlayer().getUniqueId())) {
            // get color and character name
            ChatColor color = ColorChecker.getInstance().getColorByName(localChatColor);
            String characterName = PersistentData.getInstance().getCard(event.getPlayer().getUniqueId()).getName();

            // prepare message to send
            String messageToSend;
            if (!event.getMessage().contains("*")) {
                messageToSend = color + "" + String.format("%s: \"%s\"", characterName, event.getMessage());
            }
            else {
                // TODO: implement emote-while-chatting feature
                messageToSend = "";
            }

            Messenger.getInstance().sendRPMessageToPlayersWithinDistance(event.getPlayer(), messageToSend, localChatRadius);
            event.setCancelled(true);
            return;
        }

        // global chat
        /*
        ArrayList<UUID> playersWhoHaveLeftGlobalChat = EphemeralData.getInstance().getPlayersWhoHaveHiddenGlobalChat();
        if (playersWhoHaveLeftGlobalChat.size() != 0) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!playersWhoHaveLeftGlobalChat.contains(onlinePlayer.getUniqueId())) {
                    event.setFormat(event.getFormat());
                    onlinePlayer.sendMessage(event.getMessage()); // TODO: fix this
                }
            }
            event.setCancelled(true);
            return;
        }
        */

    }

}
