package gg.cat.miwa.pignet;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import gg.cat.miwa.EventHandler;
import gg.cat.miwa.Miwa;
import gg.cat.miwa.parkour.Recording;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoGetSave {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Map<String, BlockPattern> blockPatterns = new HashMap<>();

    public AutoGetSave() {
        List<BlockPatternEntry> purple7BlockJumpNeoThing = List.of(
                new BlockPatternEntry(new Vec3d(3, 1, 6), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(3, 1, 14), Blocks.IRON_TRAPDOOR)
        );
        blockPatterns.put("Purple 7 Block Jump Neo Thing", new BlockPattern(purple7BlockJumpNeoThing, "purple_7_block_jump_neo_thing"));

        List<BlockPatternEntry> purple_super_hard_neo_to_ladder = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 4), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(0, -1, 7), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(0, -1, 16), Blocks.LADDER)
        );
        blockPatterns.put("purple_super_hard_neo_to_ladder", new BlockPattern(purple_super_hard_neo_to_ladder, "purple_super_hard_neo_to_ladder"));

        List<BlockPatternEntry> purple_long_ladder_neos = List.of(
                new BlockPatternEntry(new Vec3d(1, 0, 4), Blocks.LADDER),
                new BlockPatternEntry(new Vec3d(-2, 1, 7), Blocks.LADDER),
                new BlockPatternEntry(new Vec3d(-2, 3, 21), Blocks.LADDER)
        );
        blockPatterns.put("purple_long_ladder_neos", new BlockPattern(purple_long_ladder_neos, "purple_long_ladder_neos"));

        List<BlockPatternEntry> red_glass_pane_neo_jumps = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 3), Blocks.RED_STAINED_GLASS_PANE),
                new BlockPatternEntry(new Vec3d(0, -1, 7), Blocks.RED_STAINED_GLASS_PANE),
                new BlockPatternEntry(new Vec3d(0, -1, 17), Blocks.RED_STAINED_GLASS_PANE),
                new BlockPatternEntry(new Vec3d(0, 0, 19), Blocks.RED_STAINED_GLASS_PANE)
                );
        blockPatterns.put("red_glass_pane_neo_jumps", new BlockPattern(red_glass_pane_neo_jumps, "red_glass_pane_neo_jumps"));

        List<BlockPatternEntry> red_ladder_neos = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 6), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(0, 0, 8), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(-1, 0, 9), Blocks.LADDER),
                new BlockPatternEntry(new Vec3d(-1, 1, 13), Blocks.LADDER)
            );
        blockPatterns.put("red_ladder_neos", new BlockPattern(red_ladder_neos, "red_ladder_neos"));

        List<BlockPatternEntry> purple_long_to_amongus = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 5), Blocks.PURPLE_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, -1, 10), Blocks.PURPLE_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, 0, 15), Blocks.STONE_SLAB),
                new BlockPatternEntry(new Vec3d(0, -3, 19), Blocks.PURPLE_CONCRETE)
            );
        blockPatterns.put("purple_long_to_amongus", new BlockPattern(purple_long_to_amongus, "purple_long_to_amongus"));

        List<BlockPatternEntry> purple_pighead_neos = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 4), Blocks.DEEPSLATE_BRICK_WALL),
                new BlockPatternEntry(new Vec3d(0, 0, 5), Blocks.DEEPSLATE_BRICK_WALL),
                new BlockPatternEntry(new Vec3d(0, 0, 6), Blocks.SPRUCE_FENCE)
        );
        blockPatterns.put("purple_pighead_neos", new BlockPattern(purple_pighead_neos, "purple_pighead_neos"));

        List<BlockPatternEntry> purple_pink_head_neos = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 4), Blocks.CHAIN),
                new BlockPatternEntry(new Vec3d(0, 1, 4), Blocks.DEEPSLATE_BRICK_WALL),
            new BlockPatternEntry(new Vec3d(0, -1, 4), Blocks.DEEPSLATE_BRICK_WALL)
        );
        blockPatterns.put("purple_pink_head_neos", new BlockPattern(purple_pink_head_neos, "purple_pink_head_neos"));

        List<BlockPatternEntry> purple_blood_barrier_neo = List.of(
                new BlockPatternEntry(new Vec3d(-2, -1, 4), Blocks.BARRIER),
                new BlockPatternEntry(new Vec3d(-2, -1, 9), Blocks.BARRIER)
        );
        blockPatterns.put("purple_blood_barrier_neo", new BlockPattern(purple_blood_barrier_neo, "purple_blood_barrier_neo"));

        List<BlockPatternEntry> purple_cobblestonewall_neo = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 5), Blocks.LANTERN),
                new BlockPatternEntry(new Vec3d(0, -1, 8), Blocks.LANTERN),
                new BlockPatternEntry(new Vec3d(0, -1, 11), Blocks.PURPLE_STAINED_GLASS_PANE)
        );
        blockPatterns.put("purple_cobblestonewall_neo", new BlockPattern(purple_cobblestonewall_neo, "purple_cobblestonewall_neo"));

        List<BlockPatternEntry> purple_winged_neos = List.of(
                new BlockPatternEntry(new Vec3d(1, -1, 2), Blocks.PURPLE_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, -1, 8), Blocks.PURPLE_CONCRETE),
                new BlockPatternEntry(new Vec3d(1, 1, 11), Blocks.IRON_TRAPDOOR),
            new BlockPatternEntry(new Vec3d(1, 1, 16), Blocks.IRON_TRAPDOOR)
        );
        blockPatterns.put("purple_winged_neos", new BlockPattern(purple_winged_neos, "purple_winged_neos"));

        List<BlockPatternEntry> red_45_deg_jumps = List.of(
                new BlockPatternEntry(new Vec3d(0, -2, 5), Blocks.STONE_SLAB),
                new BlockPatternEntry(new Vec3d(-4, -2, 9), Blocks.STONE_SLAB),
                new BlockPatternEntry(new Vec3d(0, -2, 13), Blocks.STONE_SLAB)
        );
        blockPatterns.put("red_45_deg_jumps", new BlockPattern(red_45_deg_jumps, "red_45_deg_jumps"));

        List<BlockPatternEntry> red_trapdoor_and_corner_neo = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 5), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(0, -1, 13), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(-2, -1, 16), Blocks.STONE_SLAB)
        );
        blockPatterns.put("red_trapdoor_and_corner_neo", new BlockPattern(red_trapdoor_and_corner_neo, "red_trapdoor_and_corner_neo"));

        List<BlockPatternEntry> purple_double_double_neos = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 5), Blocks.IRON_TRAPDOOR),
                new BlockPatternEntry(new Vec3d(0, 2, 14), Blocks.STONE_SLAB)
        );
        blockPatterns.put("purple_double_double_neos", new BlockPattern(purple_double_double_neos, "purple_double_double_neos"));

        List<BlockPatternEntry> purple_slab_to_glass_neo = List.of(
                new BlockPatternEntry(new Vec3d(-1, -2, 6), Blocks.STONE_SLAB),
                new BlockPatternEntry(new Vec3d(-1, -3, 11), Blocks.PURPLE_STAINED_GLASS_PANE)
        );
        blockPatterns.put("purple_slab_to_glass_neo", new BlockPattern(purple_slab_to_glass_neo, "purple_slab_to_glass_neo"));

        List<BlockPatternEntry> purple_scaffolding_jump = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 5), Blocks.SCAFFOLDING),
                new BlockPatternEntry(new Vec3d(0, 0, 6), Blocks.SCAFFOLDING)
        );
        blockPatterns.put("purple_scaffolding_jump", new BlockPattern(purple_scaffolding_jump, "purple_scaffolding_jump"));

        List<BlockPatternEntry> red_quad_slimejump = List.of(
                new BlockPatternEntry(new Vec3d(1, 5, 10), Blocks.RED_CONCRETE),
                new BlockPatternEntry(new Vec3d(1, 5, 12), Blocks.IRON_TRAPDOOR)
        );
        blockPatterns.put("red_quad_slimejump", new BlockPattern(red_quad_slimejump, "red_quad_slimejump"));

        List<BlockPatternEntry> red_glass_fence_neo = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 6), Blocks.RED_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, 1, 7), Blocks.RED_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, -1, 8), Blocks.IRON_TRAPDOOR)
        );
        blockPatterns.put("red_glass_fence_neo", new BlockPattern(red_glass_fence_neo, "red_glass_fence_neo"));

        List<BlockPatternEntry> red_candle_jumps = List.of(
                new BlockPatternEntry(new Vec3d(-1, 0, 4), Blocks.RED_CANDLE),
                new BlockPatternEntry(new Vec3d(-2, 1, 7), Blocks.RED_CANDLE)
        );
        blockPatterns.put("red_candle_jumps", new BlockPattern(red_candle_jumps, "red_candle_jumps"));

        List<BlockPatternEntry> purple_lot_of_momentum_jump = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 3), Blocks.PURPLE_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, 0, 6), Blocks.PURPLE_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, 1, 9), Blocks.PURPLE_CONCRETE)
        );
        blockPatterns.put("purple_lot_of_momentum_jump", new BlockPattern(purple_lot_of_momentum_jump, "purple_lot_of_momentum_jump"));

        List<BlockPatternEntry> purple_water_jumps = List.of(
                new BlockPatternEntry(new Vec3d(0, 0, 4), Blocks.WATER)
        );
        blockPatterns.put("purple_water_jumps", new BlockPattern(purple_water_jumps, "purple_water_jumps"));

        List<BlockPatternEntry> red_neo_slab_jumps = List.of(
                new BlockPatternEntry(new Vec3d(0, -1, 4), Blocks.RED_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, 2, 4), Blocks.RED_CONCRETE),
                new BlockPatternEntry(new Vec3d(0, -1, 8), Blocks.RED_CONCRETE)
        );
        blockPatterns.put("red_neo_slab_jumps", new BlockPattern(red_neo_slab_jumps, "red_neo_slab_jumps"));

    }

    public void Find() {
        if (mc.player == null || mc.world == null) return;

        Vec3d playerPos = mc.player.getPos();

        double frontZ = playerPos.z + 2.5;

        BlockPos basePos = new BlockPos((int) playerPos.x - 1, (int) playerPos.y, (int) frontZ);
        BlockPos cornerOne = basePos.add(4, -3, 0);
        BlockPos cornerTwo = basePos.add(-4, 6, 24);

//        mc.world.setBlockState(basePos, Blocks.IRON_BLOCK.getDefaultState());

        findMatchingBlocks(basePos, cornerOne, cornerTwo);
//        List<MatchedBlock> matchedBlocks = findMatchingBlocks(basePos, cornerOne, cornerTwo);
//        matchedBlocks.forEach(System.out::println);
    }

    private List<MatchedBlock> findMatchingBlocks(BlockPos basePos, BlockPos cornerOne, BlockPos cornerTwo) {
        List<MatchedBlock> matchedBlocks = new ArrayList<>();

        for (int x = Math.min(cornerOne.getX(), cornerTwo.getX()); x <= Math.max(cornerOne.getX(), cornerTwo.getX()); x++) {
            for (int y = Math.min(cornerOne.getY(), cornerTwo.getY()); y <= Math.max(cornerOne.getY(), cornerTwo.getY()); y++) {
                for (int z = Math.min(cornerOne.getZ(), cornerTwo.getZ()); z <= Math.max(cornerOne.getZ(), cornerTwo.getZ()); z++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    Block block = mc.world.getBlockState(currentPos).getBlock();

                    Vec3d relativePos = new Vec3d(
                            currentPos.getX() - basePos.getX(),
                            currentPos.getY() - basePos.getY(),
                            currentPos.getZ() - basePos.getZ()
                    );

                    for (Map.Entry<String, BlockPattern> entry : blockPatterns.entrySet()) {
                        BlockPattern pattern = entry.getValue();

                        boolean isPatternMatched = pattern.patternEntries.stream().allMatch(patternEntry -> {
                            BlockPos relativeBlockPos = basePos.add((int) patternEntry.relativePosition.x, (int) patternEntry.relativePosition.y, (int) patternEntry.relativePosition.z);
                            return mc.world.getBlockState(relativeBlockPos).getBlock() == patternEntry.blockType;
                        });

                        // comment
//                        pattern.patternEntries.forEach(patternEntry -> {
//                            BlockPos relativeBlockPos = basePos.add((int) patternEntry.relativePosition.x, (int) patternEntry.relativePosition.y, (int) patternEntry.relativePosition.z);
//                            mc.world.setBlockState(relativeBlockPos, Blocks.GOLD_BLOCK.getDefaultState());
//                        });

                        if (isPatternMatched) {
//                            pattern.patternEntries.forEach(patternEntry -> {
//                                BlockPos relativeBlockPos = basePos.add((int) patternEntry.relativePosition.x, (int) patternEntry.relativePosition.y, (int) patternEntry.relativePosition.z);
//                                mc.world.setBlockState(relativeBlockPos, Blocks.GOLD_BLOCK.getDefaultState());
//                            });

                            mc.player.sendMessage(Text.of("Matched pattern: " + entry.getKey() + " | Save File: " + pattern.saveFile), false);
                            loadSaves(pattern.saveFile);
                            return matchedBlocks;
                        }
                    }
                }
            }
        }

        return matchedBlocks;
    }

    private void loadSaves(String saveName) {
        saveName = saveName + ".json";
        File saveDir = new File(mc.runDirectory, "miwa/saves");
        File foundSave = null;

        if (!saveDir.exists()) {
            return;
        }

        for (File file : saveDir.listFiles()) {
            if (file.getName().equals(saveName)) {
                foundSave = file;
                openSelectedSave(foundSave);
                break;
            }
        }
    }

    private void openSelectedSave(File saveFile) {
        if (saveFile == null) {
            mc.player.sendMessage(Text.of("No save was found."), false);
            return;
        }

        try (FileReader reader = new FileReader(saveFile)) {
            Type recordingType = new TypeToken<Recording>() {}.getType();
            Recording recording = new Gson().fromJson(reader, recordingType);

            Miwa.LOGGER.info(String.valueOf(recording));
            EventHandler.recordHistory.clear();
            EventHandler.recordHistory.add(recording);
            mc.player.sendMessage(Text.of("Loaded recording: " + saveFile), false);
        } catch (IOException e) {
            mc.player.sendMessage(Text.of("Failed to load recording: " + e.getMessage()), false);
        }
    }

    private record BlockPattern(List<BlockPatternEntry> patternEntries, String saveFile) {
    }

    private record BlockPatternEntry(Vec3d relativePosition, Block blockType) {
    }

    private record MatchedBlock(String name, Vec3d relativePosition, Block blockType) {

        @Override
        public String toString() {
            return "MatchedBlock{" +
                    "name='" + name + '\'' +
                    ", relativePosition=" + relativePosition +
                    ", blockType=" + blockType +
                    '}';
        }
    }
}
