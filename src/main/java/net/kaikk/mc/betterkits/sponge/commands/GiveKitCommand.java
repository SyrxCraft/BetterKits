package net.kaikk.mc.betterkits.sponge.commands;

import net.kaikk.mc.betterkits.sponge.BetterKits;
import net.kaikk.mc.betterkits.sponge.Kit;
import net.kaikk.mc.betterkits.sponge.Messages;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

public class GiveKitCommand implements CommandExecutor {

    private BetterKits instance;

    public GiveKitCommand(BetterKits instance){
        this.instance = instance;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        User user = args.<User>getOne("user").get();
        String kitName = args.<String>getOne("kit").get();
        Kit kit;

        if(kitName != null && (kit = instance.getKit(kitName)) != null){

            if(user != null && user.getPlayer().isPresent() && user.isOnline()){

                kit.give(user.getPlayer().get());
                src.sendMessage(Text.of("\u00a7aThe player " + user.getName() + " has received the kit " + kit.getName() + "."));
                user.getPlayer().get().sendMessage(Messages.get("YouReceivedKit","name",kitName));
                return CommandResult.success();

            }else {
                src.sendMessage(Messages.get("PlayerNotOnline"));
                return CommandResult.empty();
            }

        }else {
            src.sendMessages(Messages.get("KitNotFound"));
            return CommandResult.empty();
        }

    }
}
