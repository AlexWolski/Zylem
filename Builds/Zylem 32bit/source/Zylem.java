import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.*; 
import java.awt.datatransfer.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Zylem extends PApplet {




final int stationaryButtonArraySize = 17;
final int imageArraySize = 8;
final int animatedImageArraySize = 1;
final int textureArraySize = 8;

PImage textureImages[] = new PImage[textureArraySize];
PGraphics textureObjects[] = new PGraphics[textureArraySize];
button stationaryButtonArray[] = new button[stationaryButtonArraySize];
Image imageArray[] = new Image[imageArraySize];
button pressedButton = null;

Screen displayScreen = null;
MovingScreen homeScreen;
Screen settingsScreen;
Screen plantSelectScreen;
Screen gameMenuScreen;
Screen inGameSettingsScreen;

Game displayGameScreen = null;

Animation xylem, phloem, loading;
PImage x1, p1;
boolean animationIsDone = true;
boolean scrolling = false;
int direction;
int frame = 0;
boolean loadingIsDone = false;
boolean inGame = false;

PGraphics scroll;
int scrollX = 0;
int textSize;

int Difficulty = 1; 
int seed;

boolean smoothZoom = false;

public void setup()
{
  
  

  background(0);
  imageMode(CENTER);
  rectMode(CENTER);
  ellipseMode(RADIUS);

  strokeJoin(ROUND);
  strokeCap(ROUND);
}

public void initialize()
{
  xylem = new Animation("Xylem Gif/x", 66, 5);
  phloem = new Animation("Phloem Gif/p", 80, 5);
  x1 = loadImage("Xylem Gif/x66.png");
  p1 = loadImage("Phloem Gif/p80.png");

  imageArray[0] = new Image(loadImage("Logos/Title.png"), width/2, height/4, width/2, height/2);
  imageArray[3] = new Image(loadImage("Logos/Gear.png"), width/2, height/8, width/10, width/10);
  imageArray[4] = new Image(loadImage("Logos/Background.PNG"), width/2, height/2, width, height);
  imageArray[5] = new Image(loadImage("Logos/Tree.png"), width/4, height/2, width/5, width/5);
  imageArray[6] = new Image(loadImage("Logos/Moss.png"), width/2, height/2, width/5, width/5);
  imageArray[7] = new Image(loadImage("Logos/Weed.png"), round(width * 0.75f), height/2, width/5, width/5);

  textureImages[0] = loadImage("Textures/Grass.png");
  textureImages[1] = loadImage("Textures/Grass Left.png");
  textureImages[2] = loadImage("Textures/Grass Right.png");
  textureImages[3] = loadImage("Textures/Top Soil.png");
  textureImages[4] = loadImage("Textures/Subsoil.png");
  textureImages[5] = loadImage("Textures/Parent Rock.png");
  textureImages[6] = loadImage("Textures/Loose Bedrock.png");
  textureImages[7] = loadImage("Textures/Bedrock.png");

  stationaryButtonArray[0] = new Start(width/2, round(height/1.8f), round(width/6.4f), height/16, 0xff0F9614, 0xff0FF014, "Start");
  stationaryButtonArray[1] = new Settings(width/2, round(height/1.5f), round(width/6.4f), height/16, 0xff0F9614, 0xff0FF014, "Settings");
  stationaryButtonArray[2] = new Exit(width/2, height - round(height/32), round(width/6.4f), height/16, 0xff0F9614, 0xff0FF014, "Exit Game");
  stationaryButtonArray[3] = new Back(PApplet.parseInt(width/12.8f), height/32, round(width/6.4f), height/16, 0xff0F9614, 0xff0FF014, "Back");

  stationaryButtonArray[4] = new Confirm(width/2, height - round(height/32), round(width/6.4f), height/16, 0xff0F9614, 0xff0FF014, "Confirm");
  stationaryButtonArray[5] = new Left(width/32, height/2, width/16, round(height/6.4f), 0xff0F9614, 0xff0FF014, "<");
  stationaryButtonArray[6] = new Right(width - width/32, height/2, width/16, round(height/6.4f), 0xff0F9614, 0xff0FF014, ">");

  stationaryButtonArray[7] = new Difficulty(width/2, height/2, round(width/6.4f), height/16, 0xff0082FF, 0xff000000, "Easy");
  stationaryButtonArray[8] = new Easy(width/2 - round(width/19.2f), height/2 + height/32 + height/96, round(width/19.2f), height/48, 0xff00FFFF, 0xff00FFFF, " ");
  stationaryButtonArray[9] = new Normal(width/2, height/2 + height/32 + height/96, round(width/19.2f), height/48, 0xff1E82E6, 0xff1EFFFF, " ");
  stationaryButtonArray[10] = new Hard(width/2 + round(width/19.2f), height/2 + height/32 + height/96, round(width/19.2f), height/48, 0xff1E82E6, 0xff1EFFFF, " ");
  stationaryButtonArray[11] = new button(width/2, round(height/1.5f), round(width/6.4f), height/16, 0xff1E82E6, 0xff1EFFFF, "Fullscreen");
  stationaryButtonArray[12] = new Copy(round(width * 0.75f), round(height/5), round(width/6.4f), height/16, 0xff1E82E6, 0xff1EFFFF, "Copy Seed");
  stationaryButtonArray[13] = new MainMenu(round(width * 0.75f), height - round(height/32), round(width/6.4f), height/16, 0xff1E82E6, 0xff1EFFFF, "Main Menu");
  stationaryButtonArray[14] = new Settings(round(width * 0.75f), round(height/2.5f), round(width/6.4f), height/16, 0xff1E82E6, 0xff1EFFFF, "Settings");
  
  stationaryButtonArray[15] = new BackToGameMenu(PApplet.parseInt(width/12.8f), height/32, round(width/6.4f), height/16, 0xff1E82E6, 0xff1EFFFF, "Back");
  stationaryButtonArray[16] = new smoothZoom(width/2, round(height/1.2f), round(width/6.4f), height/16, 0xff1E82E6, 0xff1EFFFF, "Smooth Zoom");
  
  textureObjects[0] = createGraphics(100, 100);
  textureObjects[1] = createGraphics(100, 100);
  textureObjects[2] = createGraphics(100, 100);
  textureObjects[3] = createGraphics(100, 100);
  textureObjects[4] = createGraphics(100, 100);
  textureObjects[5] = createGraphics(100, 100);
  textureObjects[6] = createGraphics(100, 100);
  textureObjects[7] = createGraphics(100, 100);

  for (int i = 0; i < textureArraySize; i++)
  {
    textureObjects[i].beginDraw();
    textureObjects[i].imageMode(CORNER);
    textureObjects[i].image(textureImages[i], 0, 0, 100, 100);
    textureObjects[i].endDraw();
  }

  seed = PApplet.parseInt(random(0, 100000));
  randomSeed(seed);

  //
  Image Images[] = new Image[1];
  Images[0] = imageArray[0];
  button Buttons[] = new button[3];

  for (int i = 0; i < 3; i++)
    Buttons[i] = stationaryButtonArray[i];

  homeScreen = new MovingScreen(Images, Buttons, 1, 3);
  //
  Images = new Image[1];
  Images[0] = imageArray[3];
  Buttons = new button[6];
  Buttons[0] = stationaryButtonArray[3];

  for (int i = 1; i < 6; i++)
    Buttons[i] = stationaryButtonArray[i + 6];

  settingsScreen = new Screen(Images, Buttons, 1, 6);
  //
  Images = new Image[3];
  Images[0] = imageArray[5];
  Images[1] = imageArray[6];
  Images[2] = imageArray[7];

  Buttons = new button[4];

  for (int i = 0; i < 4; i++)
    Buttons[i] = stationaryButtonArray[i + 3];

  plantSelectScreen = new SelectionScreen(Images, Buttons, 3, 4);
  //
  Images = new Image[2];
  Images[0] = imageArray[3];
  Images[1] = imageArray[4];

  Buttons = new button[3];
  Buttons[0] = stationaryButtonArray[12]; 
  Buttons[1] = stationaryButtonArray[13];
  Buttons[2] = stationaryButtonArray[14];

  gameMenuScreen = new Screen(Images, Buttons, 2, 3);
  //
  Images = new Image[1];
  Images[0] = imageArray[3];
  Buttons = new button[7];
  Buttons[0] = stationaryButtonArray[15];

  for (int i = 1; i < 6; i++)
    Buttons[i] = stationaryButtonArray[i + 6];
  
  Buttons[6] = stationaryButtonArray[16];
  
  inGameSettingsScreen = new Screen(Images, Buttons, 1, 7);
  //
  
  if (width > height)
    textSize = height;
  else
    textSize = width;

  displayScreen = homeScreen;

  scroll = createGraphics(width/2 + width/5, width/5);

  loadingIsDone = true;
}

public void loading()
{
    fill(255);
    rect(width/2, height/2, width/4, height/4); 
    loading.display(width/2, height/2, width/4, height/4);
}

class Animation 
{
  PImage[] images;
  int imageCount;
  int animationSpeed;
  int ellapsedTime;
  int frame;

  Animation(String imagePrefix, int count, int speed)
  {
    imageCount = count;
    images = new PImage[imageCount];
    animationSpeed = speed;
    frame = 0;
    ellapsedTime = -1;

    for (int i = 0; i < imageCount; i++) 
    {
      String filename = imagePrefix + nf(i + 1, 1) + ".png";
      images[i] = loadImage(filename);
    }
  }

  public void display(float xpos, float ypos, int Width, int Height) 
  {
    if(ellapsedTime == -1)
      ellapsedTime = millis();
      
    if(millis() - ellapsedTime >= animationSpeed)
    {
      frame = (frame + 1) % imageCount;
      ellapsedTime = -1;
    }

    image(images[frame], xpos, ypos, Width, Height);
  }

  public int getWidth() 
  {
    return images[0].width;
  }
}

public final int constant(int integer)
{
  final int constant = integer;

  return constant;
}

public void draw()
{
  if (loadingIsDone)
  {
    if (!inGame)
    {
      displayScreen.drawScreen();
      displayScreen.action();
    } else if (inGame)
    {
      displayGameScreen.drawGame();
    }
  } else
  {
    if (frameCount <= 1)
    {
      loading = new Animation("Loading Gif/L", 33, 40);
      thread("initialize");
    }

    loading();
  }
}
class button
{
  private

    int X;
  int Y;
  int Width;
  int Height;
  int color1;
  int color2;
  String Text = " ";
  PFont f = createFont("arial", 33, true); 

  public

    button()
  {
  }

  button(int p_X, int p_Y, int p_Width, int p_Height, int Color1, int Color2, String p_Text)
  {
    X = p_X;
    Y = p_Y;
    Width = p_Width;
    Height = p_Height;
    color1 = Color1;
    color2 = Color2;
    Text = p_Text;
  }

  public void drawButton()
  {
    rectMode(CENTER);
    fill(color1);
    rect(X, Y, Width, Height);

    if (Text != " ")
    {
      textAlign(CENTER, CENTER);
      textFont(f, round(textSize/25));
      fill(255);
      textLeading(25);
      text(Text, X, Y);
    }
  }

  public void pressButton()
  {
    rectMode(CENTER);
    fill(color2);

    rect(X, Y, Width, Height);

    if (Text != " ")
    {
      textAlign(CENTER, CENTER);
      textFont(f, round(textSize/30));
      fill(255);
      textLeading(25);

      text(Text, X, Y);
    }
  }

  public boolean MousePressed()
  {
    if (mouseButton == LEFT && mouseX >= X - Width/2 && mouseX <= X + Width/2 && mouseY >= Y - Height/2 && mouseY <= Y + Height/2)
      return true;

    return false;
  }

  public void action() {
  }
}

class Back extends button
{
  Back(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Back()
  {
  }

  public void action()
  {
    if (!scrolling)
      displayScreen = homeScreen;
  }
}

class BackToGameMenu extends button
{
  BackToGameMenu(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  BackToGameMenu()
  {
  }

  public void action()
  {
    if (!scrolling)
      displayScreen = gameMenuScreen;
  }
}

class smoothZoom extends button
{
  smoothZoom(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  smoothZoom()
  {
  }

  public void action()
  {
    smoothZoom = true;
  }
}

class Start extends button
{
  Start(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Start()
  {
  }

  public void action()
  {
    displayScreen = plantSelectScreen;
  }
}


class Settings extends button
{
  Settings(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Settings()
  {
  }

  public void action()
  {
    if (displayScreen == homeScreen)
      displayScreen = settingsScreen;
    else
      displayScreen = inGameSettingsScreen;
  }
}

class Exit extends button
{
  Exit(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Exit()
  {
  }

  public void action()
  {
    exit();
  }
}

class Difficulty extends button
{
  Difficulty(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Difficulty()
  {
  }

  public void pressButton()
  {
  }
}

class Easy extends button
{
  Easy(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Easy()
  {
  }

  public void action()
  {    
    stationaryButtonArray[8].color1 = 0xff00FFFF;
    stationaryButtonArray[9].color1 = 0xff1E82E6;
    stationaryButtonArray[10].color1 = 0xff1E82E6;

    stationaryButtonArray[7].Text = "Easy";
    Difficulty = 0;
  }
}

class Normal extends button
{
  Normal(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Normal()
  {
  }

  public void action()
  {
    stationaryButtonArray[8].color1 = 0xff1E82E6;
    stationaryButtonArray[9].color1 = 0xff00FFFF;
    stationaryButtonArray[10].color1 = 0xff1E82E6;

    stationaryButtonArray[7].Text = "Normal";
    Difficulty = 1;
  }
}

class Hard extends button
{
  Hard(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Hard()
  {
  }

  public void action()
  {
    stationaryButtonArray[8].color1 = 0xff1E82E6;
    stationaryButtonArray[9].color1 = 0xff1E82E6;
    stationaryButtonArray[10].color1 = 0xff00FFFF;

    stationaryButtonArray[7].Text = "Hard";
    Difficulty = 2;
  }
}

class Copy extends button
{
  Copy(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Copy()
  {
  }

  public void action()
  {
    StringSelection copySeed = new StringSelection(Integer.toString(seed));
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(copySeed, copySeed);

    stationaryButtonArray[12].Text = "Seed Copied";
  }
}

class MainMenu extends button
{
  MainMenu(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  MainMenu()
  {
  }

  public void action()
  {
    displayScreen = homeScreen;
  }
}

class Confirm extends button
{
  Confirm(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Confirm()
  {
  }

  public void action()
  {
    if (!scrolling)
    {
      Image image = displayScreen.images[1];

      if (image == imageArray[5])
        displayGameScreen = new Tree();
      else if (image == imageArray[6])
        displayGameScreen = new Moss();
      else if (image == imageArray[7])
        displayGameScreen = new Weed();

      displayGameScreen.panX = 0;
      displayGameScreen.panY = 0;
      displayGameScreen.scale = 1.0f;
    }
  }
}

class Left extends button
{
  Left(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Left()
  {
  }

  public void action()
  {
    if (scrolling == false)
    {
      scrolling = true;
      direction = 1;
    }
  }
}

class Right extends button
{
  Right(int p_X, int p_Y, int p_Width, int p_Height, int color1, int color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Right()
  {
  }

  public void action()
  {
    if (scrolling == false)
    {
      scrolling = true;
      direction = -1;
    }
  }
}

public void keyPressed() 
{
  if (inGame)
  {
    if (key == 27)
    {
      inGame = false;
      displayScreen = gameMenuScreen;
    }
  } else if (displayScreen == homeScreen)
  {
    if (key == ' ' || key == ENTER)
    {
      new Start().action();
    }
  } else if (displayScreen == plantSelectScreen)
  {
    if (keyCode == LEFT)
      new Left().action();
    if (keyCode == RIGHT)
      new Right().action();
    if (key == ' ' || key == ENTER)
    {
      new Confirm().action();
    }
  } else if (displayScreen == gameMenuScreen)
  {
    if (key == 27) 
    {
      inGame = true;
    }
  } 
  if (displayScreen == plantSelectScreen || displayScreen == settingsScreen)
  {
    if (key == 27) 
      new Back().action();
  } 
  if(displayScreen == inGameSettingsScreen)
  {
    if(key == 27)
      displayScreen = gameMenuScreen;
  }
  if (key == ESC)
    key = 0;
}
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

  public void makeRandomTerrain(int[] randomTerrain, int terrainHeight, int heightVariation, int left, int right, int middle, Tile drawTile)
  {
    int terrainDifference;

    if (left == 0 && right == 0 && middle == 0)
    {
      randomTerrain[0] = PApplet.parseInt(random(terrainHeight - (heightVariation / 2), terrainHeight + (heightVariation / 2)));
      randomTerrain[floor(arrayWidth/2)] = PApplet.parseInt(random(terrainHeight - (heightVariation / 2), terrainHeight + (heightVariation / 2)));
      randomTerrain[arrayWidth - 1] = PApplet.parseInt(random(terrainHeight - (heightVariation / 2), terrainHeight + (heightVariation / 2)));

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

  public void generateRandomTerrain(int[] randomTerrain, float leftPosition, float rightPosition, float middlePosition)
  {
    int margin;
    float targetBlock = 0;

    if (round(middlePosition) - 1 > round(leftPosition))
    {
      targetBlock = (PApplet.parseFloat(floor(middlePosition)) + PApplet.parseFloat(round(leftPosition))) / PApplet.parseFloat(2);

      if (round(leftPosition) + 2 > floor(middlePosition))
        margin = 1;
      else
        margin = 0;

      if (randomTerrain[round(leftPosition)] < randomTerrain[floor(middlePosition)])
      {
        randomTerrain[floor(targetBlock)] = PApplet.parseInt(random(randomTerrain[round(leftPosition)] + margin, randomTerrain[floor(middlePosition)] - margin));
        randomTerrain[ceil(targetBlock)] = PApplet.parseInt(random(randomTerrain[round(leftPosition)] + margin, randomTerrain[floor(middlePosition)] - margin));
      } else if (randomTerrain[round(leftPosition)] > randomTerrain[floor(middlePosition)])
      {
        randomTerrain[floor(targetBlock)] = PApplet.parseInt(random(randomTerrain[floor(middlePosition)] + margin, randomTerrain[round(leftPosition)] - margin));
        randomTerrain[ceil(targetBlock)] = PApplet.parseInt(random(randomTerrain[floor(middlePosition)] + margin, randomTerrain[round(leftPosition)] - margin));
      } else
      {
        randomTerrain[floor(targetBlock)] = randomTerrain[floor(middlePosition)];
        randomTerrain[ceil(targetBlock)] = randomTerrain[floor(middlePosition)];
      }

      if (floor((PApplet.parseFloat(round(leftPosition)) + PApplet.parseFloat(floor(middlePosition))) / PApplet.parseFloat(2)) - 1 > round(leftPosition))
        generateRandomTerrain(randomTerrain, leftPosition, middlePosition, (PApplet.parseFloat(ceil(leftPosition)) + PApplet.parseFloat(floor(middlePosition))) / PApplet.parseFloat(2));
    } 

    if (round(middlePosition) + 1 < floor(rightPosition))
    {
      targetBlock = (PApplet.parseFloat(round(middlePosition)) + PApplet.parseFloat(floor(rightPosition))) / PApplet.parseFloat(2);

      if (round(middlePosition) + 2 > floor(rightPosition))
        margin = 1;
      else
        margin = 0;

      if (randomTerrain[round(middlePosition)] < randomTerrain[floor(rightPosition)])
      {
        randomTerrain[floor(targetBlock)] = PApplet.parseInt(random(randomTerrain[round(middlePosition)] + margin, randomTerrain[floor(rightPosition) - margin]));
        randomTerrain[ceil(targetBlock)] = PApplet.parseInt(random(randomTerrain[round(middlePosition)] + margin, randomTerrain[floor(rightPosition)] - margin));
      } else if (randomTerrain[round(middlePosition)] > randomTerrain[floor(rightPosition)])
      {
        randomTerrain[floor(targetBlock)] = PApplet.parseInt(random(randomTerrain[floor(rightPosition)] + margin, randomTerrain[round(middlePosition)] - margin));
        randomTerrain[ceil(targetBlock)] = PApplet.parseInt(random(randomTerrain[floor(rightPosition)] + margin, randomTerrain[round(middlePosition)] - margin));
      } else
      {
        randomTerrain[floor(targetBlock)] = randomTerrain[round(middlePosition)];
        randomTerrain[ceil(targetBlock)] = randomTerrain[round(middlePosition)];
      }

      if (ceil(targetBlock) + 1 < floor(rightPosition))
        generateRandomTerrain(randomTerrain, middlePosition, rightPosition, (PApplet.parseFloat(ceil(middlePosition)) + PApplet.parseFloat(floor(rightPosition))) / PApplet.parseFloat(2));
    }
  }

  public void moveCamera()
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
        cameraSpeed += 0.25f;

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

  public void zoom(MouseEvent event)
  {
    if (scale >= 2 && event.getAmount() > 0)
      return;

    scale += event.getAmount()/50;

    if (scale < width/PApplet.parseFloat(arrayWidth)/100)
      scale = width/PApplet.parseFloat(arrayWidth)/100;

    if (scale < height/PApplet.parseFloat(arrayWidth)/100)
      scale = height/PApplet.parseFloat(arrayWidth)/100;
  }

  public void displayMiniMap()
  {
    stroke(0);
    fill(0xff969696, 50);
    rect(arrayWidth, arrayHeight, arrayWidth*2, arrayHeight*2);

    noStroke();
    fill(0xffFAC832, 80);
    rect(arrayWidth - panX/100, arrayHeight - panY/100, round(width/scale/50), round(height/50/scale));
    stroke(5);
  }

  public void loadGameButtons()
  {
    if (cameraMoving)
    {
      stroke(0);
      fill(0xff505050, 20);
      ellipse(width/2, height/2, width/2 - 50, height/2 - 50);
    }
  }

  public void loadGrass()
  {
    for (int i = floor (arrayHeight/2) - 10; i < floor(arrayHeight/2) + 10; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (terrain[j][i].get(0).getClass().equals(new TopSoil().getClass()) &&  terrain[j][i - 1].get(0).getClass().equals(new Sky().getClass()))
          terrain[j][i].add(new Grass(1));
  }

  public void loadTerrain()
  { 
    background(0xff82C8C8);
    tempX1 = getCellX(getPoint(PApplet.parseInt(panX + width / scale)), false);
    tempX2 = getCellX(getPoint(PApplet.parseInt(panX - width / scale)), false);
    tempY1 = getCellY(getPoint(PApplet.parseInt(panY + height / scale)), false);
    tempY2 = getCellY(getPoint(PApplet.parseInt(panY - height / scale)), false);
    drawX = arrayWidth * -50 + 50 + PApplet.parseInt(tempX1 * 100);
    drawY = arrayHeight * -50 + 50 + PApplet.parseInt(tempY1 * 100);
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
      drawX = arrayWidth * -50 + 50 + PApplet.parseInt(tempX1 * 100);
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

    public void addRoot()
    {
      roots.add(new Root(X2, -PApplet.parseInt(getPoint(panX) - (mouseX - width/2) / scale), Y2, -PApplet.parseInt(getPoint(panY) - (mouseY - height/2) / scale), 1));
      size++;
    }

    public void cancel()
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

    public int getHeight()
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

    public void growSize()
    {
      if (thickness != 0)
        grownThickness = growthSize * getHeight();

      for (int i = 0; i < size; i++)
        roots.get(i).growSize();
    }

    public void drawRoot()
    {    
      if (thickness != grownThickness)
        thickness += growthSpeed;

      strokeWeight(thickness);
      stroke(0xff644B32);

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
        fill(0xff32C800, 90);
      else
        fill(0xffFA0000, 90);

      ellipse(X2, Y2, 10, 10);
    }

    public void pressed()
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

  public int getCellX(int X, boolean mouse)
  {
    if (mouse)
      return ceil(arrayWidth/2 - ((getPoint(X) - (mouseX - width/2)/scale) * scale)/(100 * scale) - 0.5f);

    int i = ceil(arrayWidth/2 - (X * scale)/(100 * scale) - 0.5f);

    if (i < 0)
      return 0;

    if (i > arrayWidth)
      return arrayWidth;

    return i;
  }

  public int getCellY(int Y, boolean mouse)
  {
    if (mouse)
      return ceil(arrayHeight/2 - ((getPoint(Y) - (mouseY - height/2)/scale) * scale)/(100 * scale) - 0.5f);

    int i = ceil(arrayHeight/2 - (Y * scale)/(100 * scale) - 0.5f);

    if (i < 0)
      return 0;

    if (i > arrayHeight)
      return arrayWidth;

    return i;
  }

  public int getPoint(int point)
  {
    return PApplet.parseInt(point/2);
  }
}

public void mouseWheel(MouseEvent event)
{
  if (inGame)
  {
    displayGameScreen.zoom(event);
  }
}
class Tree extends Game
{
  Tree()
  {
    super(101, 101);

    int[] randomTerrain = new int[arrayWidth];

    makeRandomTerrain(randomTerrain, floor(arrayWidth/2) + 1, 10, 0, 0, 0, new SubSoil());
    makeRandomTerrain(randomTerrain, floor(arrayWidth * 0.55f), 10, randomTerrain[0], randomTerrain[floor(arrayWidth/2)], randomTerrain[arrayWidth - 1], new ParentRock());
    makeRandomTerrain(randomTerrain, floor(arrayWidth * 0.70f), 6, 0, 0, 0, new Bedrock());

    for (int i = floor (arrayHeight/2) - 10; i < floor(arrayHeight/2) + 10; i++)
      for (int j = 0; j < arrayWidth; j++)
        if (terrain[j][i].get(0).getClass().equals(new SubSoil().getClass()) &&  terrain[j][i - 1].get(0).getClass().equals(new Sky().getClass()))
        {
          terrain[j][i].remove(0);
          terrain[j][i].add(new TopSoil());
        }

    for (int i = floor (arrayHeight * 0.55f) - 20; i < floor(arrayHeight * 0.55f) + 20; i++)
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
class Image
{
  Image(PImage p_Image, int X, int Y, int p_Width, int p_Height)
  {
    Image = p_Image;
    x = X;
    y = Y;
    Width = p_Width;
    Height = p_Height;
  }

  private

    PImage Image;
  int x;
  int y;
  int Width;
  int Height;
}

class Screen
{
  protected

    Image images[];
  button buttons[];
  int imageCount;
  int buttonCount;

  public

    Screen(Image Images[], button Buttons[], int ImageCount, int ButtonCount) 
  { 
    images = Images;
    buttons = Buttons;
    imageCount = ImageCount;
    buttonCount = ButtonCount;
  }

  public void drawScreen()
  {
    imageMode(CENTER);
    image(imageArray[4].Image, imageArray[4].x, imageArray[4].y, imageArray[4].Width, imageArray[4].Height);

    for (int i = 0; i < imageCount; i++)
      image(images[i].Image, images[i].x, images[i].y, images[i].Width, images[i].Height);

    for (int i = 0; i < buttonCount; i++)
      buttons[i].drawButton();

    if (mousePressed)
    {
      for (int i = 0; i < buttonCount; i++)
        if (buttons[i].MousePressed())
        {
          buttons[i].pressButton();
          pressedButton = buttons[i];
        }
    }
  }

  public void action()
  {
  }
}

class MovingScreen extends Screen
{
  MovingScreen(Image Images[], button Buttons[], int ImageCount, int ButtonCount)
  { 
    super(Images, Buttons, ImageCount, ButtonCount);
  }

  public void action() 
  { 
    if (animationIsDone)
    {
      xylem.display(width/12, height/2, width/6, height);
      image(p1, width - width/12, height/2, width/6, height);

      if (xylem.frame == 0)
        animationIsDone = false;
    }
    if (!animationIsDone)
    {
      phloem.display(width - width/12, height/2, width/6, height);
      image(x1, width/12, height/2, width/6, height);

      if (phloem.frame == 79)
        animationIsDone = true;
    }
  }
}

class SelectionScreen extends Screen
{
  SelectionScreen(Image Images[], button Buttons[], int ImageCount, int ButtonCount)
  { 
    super(Images, Buttons, ImageCount, ButtonCount);
  }

  public void action()
  {      
    if (scrolling)
    {
      if (scrollX == 0)
        imageCount = 0;

      if (scrollX <= -width/4 || scrollX >= width/4)
      {
        imageCount = 3;

        if (direction == -1)
        {
          for (int i = imageCount - 1; i > 0; i--)
          {
            images[i].x = images[i - 1].x;
            images[i].y = images[i - 1].y;
          }

          images[0].x = round(width*0.75f);
          images[0].y = height/2;

          Image tempImage = images[0];

          for (int i = 0; i < imageCount - 1; i++)
            images[i] = images[i + 1];

          images[imageCount - 1] = tempImage;
        } else
        {
          for (int i = 0; i < imageCount - 1; i++)
          {
            images[i].x = images[i + 1].x;
            images[i].y = images[i + 1].y;
          }

          images[2].x = width/4;
          images[2].y = height/2;

          Image tempImage = images[2];

          for (int i = imageCount - 1; i > 0; i--)
            images[i] = images[i - 1];

          images[0] = tempImage;
        }

        for (int i = 0; i < imageCount; i++)
          image(images[i].Image, images[i].x, images[i].y, images[i].Width, images[i].Height);

        scrolling = false;
        scrollX = 0;

        return;
      }

      scroll.beginDraw();
      scroll.clear();
      scroll.imageMode(CENTER);

      for (int i = 0; i < 3; i++)
        scroll.image(images[i].Image, images[i].x + width/10 - width/4 + scrollX, width/10, images[i].Width, images[i].Height);

      if (direction == -1)
        scroll.image(images[0].Image, width*0.75f + width/10 + scrollX, width/10, images[0].Width, images[0].Height);
      else
        scroll.image(images[2].Image, -width/4 + width/10 + scrollX, width/10, images[0].Width, images[0].Height);

      scroll.endDraw();

      image(scroll, width/2, height/2);

      scrollX += width/50 * direction;
    }
  }
}

public void mouseReleased()
{
  if (pressedButton != null && !inGame)
    pressedButton.action();
    
  pressedButton = null;
}
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
  
  public void drawTile(int X, int Y)
  {
    image(textureObjects[texture], X, Y);
  }

  public void action()
  {
  }
}

class Sky extends Tile
{
  Sky() 
  {
  }

  public void drawTile(int X, int Y)
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

  public void drawTile(int X, int Y)
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
  public void settings() {  fullScreen();  smooth(8); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Zylem" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
