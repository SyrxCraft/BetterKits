package net.kaikk.mc.betterkits.sponge.commands;

import net.kaikk.mc.betterkits.sponge.BetterKits;
import net.kaikk.mc.betterkits.sponge.Kit;
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

        //TODO: Adicionar mensagens no Messages.yml

        User user = args.<User>getOne("user").get();
        String kitName = args.<String>getOne("kit").get();
        Kit kit;

        if(kitName != null && (kit = instance.getKit(kitName)) != null){

            if(user != null && user.getPlayer().isPresent() && user.isOnline()){

                kit.give(user.getPlayer().get());
                src.sendMessage(Text.of("\u00a7aThe player " + user.getName() + " has received the kit " + kit.getName() + "."));
                user.getPlayer().get().sendMessage(Text.of("\u00a7aYou has received the kit " + kit.getName() + " from " + src.getName() + "."));
                return CommandResult.success();

            }else {
                src.sendMessage(Text.of("\u00a7cThis user doesn't exist or not is online."));
                return CommandResult.empty();
            }

        }else {
            src.sendMessage(Text.of("\u00a7cThis kit doesn't exist."));
            return CommandResult.empty();
        }

    }
}
