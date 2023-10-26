package net.goodcraft.hardcoregames.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class MinerListener {
    private HashMap<BlockFace, Byte> faces = new HashMap<>();
    public static HashSet<Integer> list = new HashSet<>();

    static List<Block> unchecked = new LinkedList<>();
    static List<Block> checked = new LinkedList<>();
    static List<Block> confirmed = new LinkedList<>();

    @SuppressWarnings("deprecation")
    public MinerListener() {
        this.faces.put(BlockFace.SOUTH, (byte) 1);
        this.faces.put(BlockFace.WEST, (byte) 2);
        this.faces.put(BlockFace.NORTH, (byte) 4);
        this.faces.put(BlockFace.EAST, (byte) 8);
        for (int b = 8; b < 12; b++) {
            list.add(Material.IRON_ORE.getId());
        }
        list.add(Material.GOLD_ORE.getId());
        list.add(Material.COAL_ORE.getId());
        list.add(Material.REDSTONE_ORE.getId());
    }

    public static class MinerConnect {
        static List<Block> unchecked = new LinkedList<>();
        static List<Block> checked = new LinkedList<>();
        static List<Block> confirmed = new LinkedList<>();

        @SuppressWarnings("deprecation")
        public static List<Block> getConnectedBlocks(Block block) {
            BlockFace bf = null;

            unchecked.clear();
            checked.clear();
            confirmed.clear();
            unchecked.add(block);

            while (unchecked.size() > 0) {
                if (!isChecked(unchecked.get(0))) {
                    for (int i = 0; i < 6; i++) {
                        if (i == 0)
                            bf = BlockFace.DOWN;
                        else if (i == 1)
                            bf = BlockFace.EAST;
                        else if (i == 2)
                            bf = BlockFace.NORTH;
                        else if (i == 3)
                            bf = BlockFace.SOUTH;
                        else if (i == 4)
                            bf = BlockFace.UP;
                        else if (i == 5) {
                            bf = BlockFace.WEST;
                        }
                        if ((MinerListener.list.contains((unchecked.get(0))
                                .getRelative(bf).getTypeId()))
                                && (!isChecked((unchecked.get(0))
                                .getRelative(bf)))) {
                            unchecked.add((unchecked.get(0))
                                    .getRelative(bf));
                        }
                    }
                    checked.add(unchecked.get(0));
                }
                unchecked.remove(0);
            }
            for (int i = 0; i < checked.size(); i++) {
                if (!((Block) checked.get(i)).getType().equals(Material.AIR)) {
                    confirmed.add(checked.get(i));
                }
            }
            return confirmed;
        }

        public static boolean isChecked(Block block) {
            for (int i = 0; i < checked.size(); i++) {
                if ((checked.get(i)).equals(block)) {
                    return true;
                }
            }
            return false;
        }
    }
}