package net.goodcraft.comandos;

public class CoinsCmd/* extends Comando */ {
//
//    public CoinsCmd() {
//        super("coins", Rank.DEVELOPER);
//    }
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        if (args.length != 3) {
//            Message.ERROR.send(sender, "Use /coins <ADD/REMOVE> <UUID> <quantidade>");
//            return false;
//        }
//        if (!args[0].equalsIgnoreCase("ADD") && !args[0].equalsIgnoreCase("REMOVE")) {
//            Message.ERROR.send(sender, "Use /coins <ADD/REMOVE> <UUID> <quantidade>");
//            return false;
//        }
//
//        try {
//            UUID id = UUID.fromString(args[1]);
//
//            if (!SQLStatus.exists(id)) {
//                Message.ERROR.send(sender, "Este jogador nunca entrou no servidor ou não existe.");
//                return false;
//            }
//
//            int value = Integer.valueOf(args[2]);
//            String s = (value > 1 ? "s" : "");
//
//            if (args[0].equalsIgnoreCase("ADD")) {
//                SQLStatus.addCoins(id, value);
//                Message.INFO.send(sender, args[2] + " GoodCoin" + s + " adicionada" + s + " a conta " + id);
//                return false;
//            }
//
//            SQLStatus.removeCoins(id, value);
//            Message.INFO.send(sender, args[2] + " GoodCoin" + s + " removida" + s + " da conta " + id);
//            return false;
//
//        } catch (Exception e) {
//            Message.ERROR.send(sender, "Use /coins <ADD/REMOVE> <UUID> <quantidade>");
//        }
//
//        return false;
//    }

}
