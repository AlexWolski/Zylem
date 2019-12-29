class Tile
{
private
  int texture;

protected

  Tile()
  {
  }
  
  Tile(int Texture)
  {
    texture = Texture;
  }
  
  void drawTile(int X, int Y)
  {
    image(textureObjects[texture], X, Y);
  }

  void action()
  {
  }
}

class Sky extends Tile
{
  Sky() 
  {
  }

  void drawTile(int X, int Y)
  {
  }
}

class Grass extends Tile
{
  int position;

  Grass(int p_position) 
  {
    position = p_position;
  }

  void drawTile(int X, int Y)
  {
    if (position == 1)
      image(textureImages[0], X, Y - 55, 100, 16);
  }
}

class TopSoil extends Tile
{
  TopSoil() 
  {
    super(3);
  }
}

class SubSoil extends Tile
{
  SubSoil() 
  {
    super(4);
  }
}

class ParentRock extends Tile
{
  ParentRock() {
    super(5);
  }
}

class LooseBedrock extends Tile
{
  LooseBedrock() {
    super(6);
  }
}

class Bedrock extends Tile
{
  Bedrock() {
    super(7);
  }
}

