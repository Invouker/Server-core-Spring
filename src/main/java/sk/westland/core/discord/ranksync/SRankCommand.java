package sk.westland.core.discord.ranksync;


import dev.alangomes.springspigot.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "ranksync")
public class SRankCommand implements Runnable {

    @Autowired
    private Context context;

//    @CommandLine.Parameters(index = "0", defaultValue = "false")
  //  private boolean join;


    @Override
    public void run() {

    }
}
