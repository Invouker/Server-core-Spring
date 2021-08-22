package sk.xpress.worldgen;

import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WestChunkGenerator extends ChunkGenerator {

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);

        if(chunkX == 0 && chunkZ == 0) {
            int spawnPosY = 70;
            chunk.setBlock(0, spawnPosY -2, 0, Material.BEDROCK);
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setSpawnLocation(new Location(world, .5, spawnPosY, .5));
        }

        return chunk;
    }

}
