package sk.westland.world.commands.discord;


public class MessageCommand {//implements ICommand {
/*
    @Override
    @Command(command = "msg", "" = "For admins")
    public void onCommand(User user, String command, String[] args, String arg, MessageReceivedEvent event) {
        //System.out.println("textChannel: " + event.getTextChannel() + " CMD: " + command);
        String channelName = args[0];
        if(args == null || args.length < 1) {
            errorMessage(event.getTextChannel(), "!" + command + " <channel> <text>");
            channelName = event.getTextChannel().getName();
        }

        List<TextChannel> channels = event.getJDA().getTextChannelsByName(channelName.toLowerCase(), true);
        if(channels == null || channels.isEmpty()) {
            errorMessage(event.getMessage().getTextChannel(), "Neznámy channel");
            return;
        }

        TextChannel channel = channels.get(0);
        channel.sendMessage(buildOffsetMessage(args, 1)).queue();

    }

    public void errorMessage(TextChannel textChannel, String message) {
        errorMessage(textChannel, message, 5);
    }

    public void errorMessage(TextChannel textChannel, String message, int seconds) {
        textChannel.sendMessage("[ERROR]" + message).queue((text) -> {
            text.delete().queueAfter(seconds, TimeUnit.SECONDS);
        });
    }

    public String buildOffsetMessage(String[] string, int offset) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = offset; i < string.length; i++) {
            stringBuilder.append(string[i] + " ");
        }
        return stringBuilder.toString();
    }
*/
}
