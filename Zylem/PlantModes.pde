class Tree extends Game
{
  Tree()
  {
    super(101, 101);

    int[] randomTerrain = new int[arrayWidth];

    makeRandomTerrain(randomTerrain, floor(arrayWidth/2) + 1, 10, 0, 0, 0, new SubSoil());
    makeRandomTerrain(randomTerrain, floor(arrayWidth * 0.55), 10, randomTerrain[0], randomTerrain[floor(arrayWidth/2)], randomTerrain[arrayWidth - 1], new ParentRock());
    makeRandomTerrain(randomTerrain, floor(arrayWidth * 0.70), 6, 0, 0, 0, new Bedrock());

    for (int i = floor (arrayHeight/2) - 10; i < floor(arrayHeight/2) + 10; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (terrain[j][i].get(0).getClass().equals(new SubSoil().getClass()) &&  terrain[j][i - 1].get(0).getClass().equals(new Sky().getClass()))
        {
          terrain[j][i].remove(0);
          terrain[j][i].add(new TopSoil());
        }

    for (int i = floor (arrayHeight * 0.55) - 20; i < floor(arrayHeight * 0.55) + 20; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (terrain[j][i].get(0).getClass().equals(new Bedrock().getClass()) &&  terrain[j][i - 1].get(0).getClass().equals(new ParentRock().getClass()))
        {
          terrain[j][i].remove(0);
          terrain[j][i].add(new LooseBedrock());
        }

    loadGrass();
    loadTerrain();
    inGame = true;
  }
}

class Weed extends Game
{
  Weed()
  {
    super(51, 51);

    int[] randomTerrain = new int[arrayWidth];

    makeRandomTerrain(randomTerrain, floor(arrayWidth/2), 10, 0, 0, 0, new SubSoil());

    for (int i = floor (arrayHeight/2) - 10; i < floor(arrayHeight/2) + 10; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (terrain[j][i].get(0).getClass().equals(new SubSoil().getClass()) &&  terrain[j][i - 1].get(0).getClass().equals(new Sky().getClass()))
        {
          terrain[j][i].remove(0);
          terrain[j][i].add(new TopSoil());
        }

    loadGrass();
    loadTerrain();
    inGame = true;
  }
}

class Moss extends Game
{
  Moss()
  {
    super(51, 51);
    int[] randomTerrain = new int[arrayWidth];

    loadGrass();
    loadTerrain();
    inGame = true;
  }
}