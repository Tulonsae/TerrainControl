package com.Khorn.TerrainControl.BiomeLayers.Layers;

import com.Khorn.TerrainControl.BiomeLayers.ArraysCache;
import com.Khorn.TerrainControl.LocalBiome;

public class LayerBiome extends Layer
{
    public LocalBiome[] biomes;
    public LocalBiome[] ice_biomes;


    public LayerBiome(long paramLong, Layer paramGenLayer)
    {
        super(paramLong);
        this.child = paramGenLayer;
    }

    public int[] GetBiomes(int cacheId, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        int[] arrayOfInt1 = this.child.GetBiomes(cacheId, paramInt1, paramInt2, paramInt3, paramInt4);

        int[] arrayOfInt2 = ArraysCache.GetArray(cacheId, paramInt3 * paramInt4);
        for (int i = 0; i < paramInt4; i++)
        {
            for (int j = 0; j < paramInt3; j++)
            {
                SetSeed(j + paramInt1, i + paramInt2);
                int currentPiece = arrayOfInt1[(j + i * paramInt3)];


                if ((currentPiece & BiomeBits) == 0)    // without biome
                {
                    if (this.biomes.length > 0 && (currentPiece & IceBit) == 0) // Normal Biome
                    {
                        LocalBiome biome = this.biomes[nextInt(this.biomes.length)];
                        if (biome != null)
                            currentPiece = currentPiece | biome.getId();
                    } else if (this.ice_biomes.length > 0 && (currentPiece & IceBit) != 0)
                    {
                        LocalBiome biome = this.ice_biomes[nextInt(this.ice_biomes.length)];
                        if (biome != null)
                            currentPiece = currentPiece | biome.getId();
                    }
                }

                arrayOfInt2[(j + i * paramInt3)] = currentPiece;


            }
        }

        return arrayOfInt2;
    }
}