class Game
{
  public

    void drawGame()
  {
    moveCamera();
    loadTerrain();
    loadGameButtons();
    displayMiniMap();

    if (mousePressed && mouseButton == LEFT && pressedRoot == null)
      rootPtr.pressed();
    else if (mousePressed && mouseButton == RIGHT)
      rootPtr.cancel();
    else if (!mousePressed && pressedRoot != null)
    {   
      if (!terrain[getCellX(panX, true)][getCellY(panY, true)].get(0).getClass().equals(new Sky().getClass()))
        pressedRoot.addRoot();

      rootPtr.growSize();
      println();
      pressedRoot = null;
    }
  }

  protected

    ArrayList<Tile> terrain[][];
  int arrayWidth = 0;
  int arrayHeight = 0;
  int tempX1;
  int tempX2;
  int tempY1;
  int tempY2;
  int drawX;
  int drawY;
  boolean cameraMoving = false;
  int panX;
  int panY;
  float scale;
  float cameraSpeed = 1;
  Root rootPtr;
  Root pressedRoot = null;

  Game(int terrainWidth, int terrainHeight)
  {
    arrayWidth = terrainWidth;
    arrayHeight = terrainHeight;

    terrain = new ArrayList[constant(arrayWidth)][constant(arrayHeight)];

    for (int i = 0; i < arrayHeight; i++)
      for (int j = 0; j < arrayWidth; j++)
        terrain[i][j] = new ArrayList<Tile>();

    for (int i = 0; i < arrayHeight; i++)
      for (int j = 0; j < arrayWidth; j++)
        terrain[j][i].add(new Sky());

    rootPtr = new Root(0, 0, 60, 60, 1);
    rootPtr.thickness = 0;
    rootPtr.grownThickness = 0;
    rootPtr.grown = true;
  }

  void makeRandomTerrain(int[] randomTerrain, int terrainHeight, int heightVariation, int left, int right, int middle, Tile drawTile)
  {
    int terrainDifference;

    if (left == 0 && right == 0 && middle == 0)
    {
      randomTerrain[0] = int(random(terrainHeight - (heightVariation / 2), terrainHeight + (heightVariation / 2)));
      randomTerrain[floor(arrayWidth/2)] = int(random(terrainHeight - (heightVariation / 2), terrainHeight + (heightVariation / 2)));
      randomTerrain[arrayWidth - 1] = int(random(terrainHeight - (heightVariation / 2), terrainHeight + (heightVariation / 2)));

      terrainDifference = terrainHeight - randomTerrain[floor(arrayWidth/2)];
    } else
    {
      randomTerrain[0] = left;
      randomTerrain[floor(arrayWidth/2)] = right;
      randomTerrain[arrayWidth - 1] = middle;

      terrainDifference = terrainHeight - randomTerrain[floor(arrayWidth/2)];
    }

    generateRandomTerrain(randomTerrain, 0, arrayWidth - 1, floor(arrayWidth/2));

    for (int i = 0; i < arrayWidth; i++)
      randomTerrain[i] += terrainDifference;

    for (int i = terrainHeight - heightVariation; i < arrayHeight; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (i >= randomTerrain[j])
        {
          terrain[j][i].remove(0);

          if (drawTile.getClass().equals(new ParentRock().getClass()))
            terrain[j][i].add(new ParentRock());
          else if (drawTile.getClass().equals(new SubSoil().getClass()))
            terrain[j][i].add(new SubSoil());
          else if (drawTile.getClass().equals(new Bedrock().getClass()))
            terrain[j][i].add(new Bedrock());
        }
  };

  void generateRandomTerrain(int[] randomTerrain, float leftPosition, float rightPosition, float middlePosition)
  {
    int margin;
    float targetBlock = 0;

    if (round(middlePosition) - 1 > round(leftPosition))
    {
      targetBlock = (float(floor(middlePosition)) + float(round(leftPosition))) / float(2);

      if (round(leftPosition) + 2 > floor(middlePosition))
        margin = 1;
      else
        margin = 0;

      if (randomTerrain[round(leftPosition)] < randomTerrain[floor(middlePosition)])
      {
        randomTerrain[floor(targetBlock)] = int(random(randomTerrain[round(leftPosition)] + margin, randomTerrain[floor(middlePosition)] - margin));
        randomTerrain[ceil(targetBlock)] = int(random(randomTerrain[round(leftPosition)] + margin, randomTerrain[floor(middlePosition)] - margin));
      } else if (randomTerrain[round(leftPosition)] > randomTerrain[floor(middlePosition)])
      {
        randomTerrain[floor(targetBlock)] = int(random(randomTerrain[floor(middlePosition)] + margin, randomTerrain[round(leftPosition)] - margin));
        randomTerrain[ceil(targetBlock)] = int(random(randomTerrain[floor(middlePosition)] + margin, randomTerrain[round(leftPosition)] - margin));
      } else
      {
        randomTerrain[floor(targetBlock)] = randomTerrain[floor(middlePosition)];
        randomTerrain[ceil(targetBlock)] = randomTerrain[floor(middlePosition)];
      }

      if (floor((float(round(leftPosition)) + float(floor(middlePosition))) / float(2)) - 1 > round(leftPosition))
        generateRandomTerrain(randomTerrain, leftPosition, middlePosition, (float(ceil(leftPosition)) + float(floor(middlePosition))) / float(2));
    } 

    if (round(middlePosition) + 1 < floor(rightPosition))
    {
      targetBlock = (float(round(middlePosition)) + float(floor(rightPosition))) / float(2);

      if (round(middlePosition) + 2 > floor(rightPosition))
        margin = 1;
      else
        margin = 0;

      if (randomTerrain[round(middlePosition)] < randomTerrain[floor(rightPosition)])
      {
        randomTerrain[floor(targetBlock)] = int(random(randomTerrain[round(middlePosition)] + margin, randomTerrain[floor(rightPosition) - margin]));
        randomTerrain[ceil(targetBlock)] = int(random(randomTerrain[round(middlePosition)] + margin, randomTerrain[floor(rightPosition)] - margin));
      } else if (randomTerrain[round(middlePosition)] > randomTerrain[floor(rightPosition)])
      {
        randomTerrain[floor(targetBlock)] = int(random(randomTerrain[floor(rightPosition)] + margin, randomTerrain[round(middlePosition)] - margin));
        randomTerrain[ceil(targetBlock)] = int(random(randomTerrain[floor(rightPosition)] + margin, randomTerrain[round(middlePosition)] - margin));
      } else
      {
        randomTerrain[floor(targetBlock)] = randomTerrain[round(middlePosition)];
        randomTerrain[ceil(targetBlock)] = randomTerrain[round(middlePosition)];
      }

      if (ceil(targetBlock) + 1 < floor(rightPosition))
        generateRandomTerrain(randomTerrain, middlePosition, rightPosition, (float(ceil(middlePosition)) + float(floor(rightPosition))) / float(2));
    }
  }

  void moveCamera()
  {
    int distanceX = round(((mouseX - width/2)/-100) * cameraSpeed  / scale);
    int distanceY = round(((mouseY - height/2)/-100) * cameraSpeed  / scale);

    if (mouseY <= 5 || mouseY >= height - 5 || mouseX <= 5 || mouseX >= width-5)
      cameraMoving = true;

    if ((sq(mouseX - width/2)/sq(width/2 - 50)) + (sq(mouseY - height/2)/sq(height/2 - 50)) > 1 && cameraMoving)
    {
      panX += distanceX;
      panY += distanceY;

      if (cameraSpeed < 5)
        cameraSpeed += 0.25;

      cameraMoving = true;
    } else 
    {
      cameraSpeed = 1;
      cameraMoving = false;
    }
    if (panX/2 < arrayWidth*-50 + floor(width/scale/2))
      panX = (arrayWidth*-50 + ceil(width/scale/2)) * 2;
    else if (panX/2 > arrayWidth*50 - floor(width/scale/2))
      panX = (arrayWidth*50 - ceil(width/scale/2)) * 2;

    if (panY/2 < arrayHeight*-50 + ceil(height/scale/2))
      panY = (arrayHeight*-50 + ceil(height/scale/2)) * 2;
    else if (panY/2 > arrayHeight*50 - floor(height/scale/2))
      panY = (arrayHeight*50 - floor(height/scale/2)) * 2;
  }

  void zoom(MouseEvent event)
  {
    if (scale >= 2 && event.getAmount() > 0)
      return;

    scale += event.getAmount()/50;

    if (scale < width/float(arrayWidth)/100)
      scale = width/float(arrayWidth)/100;

    if (scale < height/float(arrayWidth)/100)
      scale = height/float(arrayWidth)/100;
  }

  void displayMiniMap()
  {
    stroke(0);
    fill(#969696, 50);
    rect(arrayWidth, arrayHeight, arrayWidth*2, arrayHeight*2);

    noStroke();
    fill(#FAC832, 80);
    rect(arrayWidth - panX/100, arrayHeight - panY/100, round(width/scale/50), round(height/50/scale));
    stroke(5);
  }

  void loadGameButtons()
  {
    if (cameraMoving)
    {
      stroke(0);
      fill(#505050, 20);
      ellipse(width/2, height/2, width/2 - 50, height/2 - 50);
    }
  }

  void loadGrass()
  {
    for (int i = floor (arrayHeight/2) - 10; i < floor(arrayHeight/2) + 10; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (terrain[j][i].get(0).getClass().equals(new TopSoil().getClass()) &&  terrain[j][i - 1].get(0).getClass().equals(new Sky().getClass()))
          terrain[j][i].add(new Grass(1));
  }

  void loadTerrain()
  { 
    background(#82C8C8);
    tempX1 = getCellX(getPoint(int(panX + width / scale)), false);
    tempX2 = getCellX(getPoint(int(panX - width / scale)), false);
    tempY1 = getCellY(getPoint(int(panY + height / scale)), false);
    tempY2 = getCellY(getPoint(int(panY - height / scale)), false);
    drawX = arrayWidth * -50 + 50 + int(tempX1 * 100);
    drawY = arrayHeight * -50 + 50 + int(tempY1 * 100);
    pushMatrix();

    if (tempX2 + 1 > arrayWidth)
      tempX2 = arrayWidth;
    if (tempY2 + 1 > arrayHeight)
      tempY2 = arrayHeight;

    translate(width/2, height/2);
    scale(scale);
    translate(panX/2, panY/2);

    for (int i = tempY1; i < tempY2 + 1; i++)
    {
      for (int j = tempX1; j < tempX2 + 1; j++)
      {
        terrain[j][i].get(0).drawTile(drawX, drawY);

        for (int k = 1; k < 5; k++)
          if (terrain[j][i].size() == k)
            terrain[j][i].get(k - 1).drawTile(drawX, drawY);

        drawX += 100;
      }
      drawX = arrayWidth * -50 + 50 + int(tempX1 * 100);
      drawY += 100;
    }

    rootPtr.drawRoot();

    fill(250, 200, 50, 80);
    rect(arrayWidth * -50 + 50 + getCellX(panX, true) * 100, arrayHeight * -50 + 50 + getCellY(panY, true) * 100, 100, 100);

    popMatrix();
  }

  class Root
  {
    private

      int X1;
    int X2;
    int Y1;
    int Y2;

    float X = 0;
    float Y = 0;

    float Length;
    int thickness = 2;
    int grownThickness = 5;
    int growthSize = thickness;
    int size = 0;

    float growthSpeed;
    boolean grown = false;

    ArrayList<Root> roots = new ArrayList<Root>();

    public

      Root(int x1, int x2, int y1, int y2, float GrowthSpeed)
    {
      X1 = x1;
      X2 = x2;
      Y1 = y1;
      Y2 = y2;

      X = x1;
      Y = y1;

      growthSpeed = GrowthSpeed;

      Length = sqrt(sq(X2 - X1) + sq(Y2 - Y1));
    }

    void addRoot()
    {
      roots.add(new Root(X2, -int(getPoint(panX) - (mouseX - width/2) / scale), Y2, -int(getPoint(panY) - (mouseY - height/2) / scale), 1));
      size++;
    }

    void cancel()
    {
      if (sqrt(sq(getPoint(panX) + X2 - (mouseX - width/2) / scale) + sq(getPoint(panY) + Y2 - (mouseY - height/2) / scale)) <= 10)
      {
        if (!grown)
        {
          X2 = round(X);
          Y2 = round(Y);
          grown = true;
        }
        return;
      } else
      {
        for (int i = 0; i < size; i++)
          roots.get(i).cancel();
      }
    }

    int getHeight()
    {
      int Height = 0;
      int tempHeight;

      for (int i = 0; i < size; i++)
      {
        tempHeight = roots.get(i).getHeight();

        if (tempHeight > Height)
          Height = tempHeight;
      }

      return Height + 1;
    }

    void growSize()
    {
      if (thickness != 0)
        grownThickness = growthSize * getHeight();

      for (int i = 0; i < size; i++)
        roots.get(i).growSize();
    }

    void drawRoot()
    {    
      if (thickness != grownThickness)
        thickness += growthSpeed;

      strokeWeight(thickness);
      stroke(#644B32);

      if (grown)
        line(X1, Y1, X2, Y2);
      else
      {
        X -= growthSpeed * (X1 - X2)/Length;
        Y -= growthSpeed * (Y1 - Y2)/Length;

        if (abs(X - X1) >= abs(X2 - X1) && abs(Y - Y1) >= abs(Y2 - Y1))
        {
          X = X2;
          Y = Y2;

          grown = true;
        }

        line(X1, Y1, X, Y);
      }

      strokeWeight(1);
      noStroke();

      for (int i = 0; i < size; i++)
        roots.get(i).drawRoot();

      if (grown)
        fill(#32C800, 90);
      else
        fill(#FA0000, 90);

      ellipse(X2, Y2, 10, 10);
    }

    void pressed()
    { 
      if (sqrt(sq(X2 + getPoint(panX) - (mouseX - width/2) / scale) + sq(Y2 + getPoint(panY) - (mouseY - height/2) / scale)) <= 10)
      {
        if (grown)
          pressedRoot = this;

        return;
      } else
        for (int i = 0; i < size; i++)
          roots.get(i).pressed();
    }
  }

  int getCellX(int X, boolean mouse)
  {
    if (mouse)
      return ceil(arrayWidth/2 - ((getPoint(X) - (mouseX - width/2)/scale) * scale)/(100 * scale) - 0.5);

    int i = ceil(arrayWidth/2 - (X * scale)/(100 * scale) - 0.5);

    if (i < 0)
      return 0;

    if (i > arrayWidth)
      return arrayWidth;

    return i;
  }

  int getCellY(int Y, boolean mouse)
  {
    if (mouse)
      return ceil(arrayHeight/2 - ((getPoint(Y) - (mouseY - height/2)/scale) * scale)/(100 * scale) - 0.5);

    int i = ceil(arrayHeight/2 - (Y * scale)/(100 * scale) - 0.5);

    if (i < 0)
      return 0;

    if (i > arrayHeight)
      return arrayWidth;

    return i;
  }

  int getPoint(int point)
  {
    return int(point/2);
  }
}

void mouseWheel(MouseEvent event)
{
  if (inGame)
  {
    displayGameScreen.zoom(event);
  }
}
