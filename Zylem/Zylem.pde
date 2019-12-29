import java.awt.*;
import java.awt.datatransfer.*;

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

void setup()
{
  smooth(8);
  fullScreen();

  background(0);
  imageMode(CENTER);
  rectMode(CENTER);
  ellipseMode(RADIUS);

  strokeJoin(ROUND);
  strokeCap(ROUND);
}

void initialize()
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
  imageArray[7] = new Image(loadImage("Logos/Weed.png"), round(width * 0.75), height/2, width/5, width/5);

  textureImages[0] = loadImage("Textures/Grass.png");
  textureImages[1] = loadImage("Textures/Grass Left.png");
  textureImages[2] = loadImage("Textures/Grass Right.png");
  textureImages[3] = loadImage("Textures/Top Soil.png");
  textureImages[4] = loadImage("Textures/Subsoil.png");
  textureImages[5] = loadImage("Textures/Parent Rock.png");
  textureImages[6] = loadImage("Textures/Loose Bedrock.png");
  textureImages[7] = loadImage("Textures/Bedrock.png");

  stationaryButtonArray[0] = new Start(width/2, round(height/1.8), round(width/6.4), height/16, #0F9614, #0FF014, "Start");
  stationaryButtonArray[1] = new Settings(width/2, round(height/1.5), round(width/6.4), height/16, #0F9614, #0FF014, "Settings");
  stationaryButtonArray[2] = new Exit(width/2, height - round(height/32), round(width/6.4), height/16, #0F9614, #0FF014, "Exit Game");
  stationaryButtonArray[3] = new Back(int(width/12.8), height/32, round(width/6.4), height/16, #0F9614, #0FF014, "Back");

  stationaryButtonArray[4] = new Confirm(width/2, height - round(height/32), round(width/6.4), height/16, #0F9614, #0FF014, "Confirm");
  stationaryButtonArray[5] = new Left(width/32, height/2, width/16, round(height/6.4), #0F9614, #0FF014, "<");
  stationaryButtonArray[6] = new Right(width - width/32, height/2, width/16, round(height/6.4), #0F9614, #0FF014, ">");

  stationaryButtonArray[7] = new Difficulty(width/2, height/2, round(width/6.4), height/16, #0082FF, #000000, "Easy");
  stationaryButtonArray[8] = new Easy(width/2 - round(width/19.2), height/2 + height/32 + height/96, round(width/19.2), height/48, #00FFFF, #00FFFF, " ");
  stationaryButtonArray[9] = new Normal(width/2, height/2 + height/32 + height/96, round(width/19.2), height/48, #1E82E6, #1EFFFF, " ");
  stationaryButtonArray[10] = new Hard(width/2 + round(width/19.2), height/2 + height/32 + height/96, round(width/19.2), height/48, #1E82E6, #1EFFFF, " ");
  stationaryButtonArray[11] = new button(width/2, round(height/1.5), round(width/6.4), height/16, #1E82E6, #1EFFFF, "Fullscreen");
  stationaryButtonArray[12] = new Copy(round(width * 0.75), round(height/5), round(width/6.4), height/16, #1E82E6, #1EFFFF, "Copy Seed");
  stationaryButtonArray[13] = new MainMenu(round(width * 0.75), height - round(height/32), round(width/6.4), height/16, #1E82E6, #1EFFFF, "Main Menu");
  stationaryButtonArray[14] = new Settings(round(width * 0.75), round(height/2.5), round(width/6.4), height/16, #1E82E6, #1EFFFF, "Settings");
  
  stationaryButtonArray[15] = new BackToGameMenu(int(width/12.8), height/32, round(width/6.4), height/16, #1E82E6, #1EFFFF, "Back");
  stationaryButtonArray[16] = new smoothZoom(width/2, round(height/1.2), round(width/6.4), height/16, #1E82E6, #1EFFFF, "Smooth Zoom");
  
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

  seed = int(random(0, 100000));
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

void loading()
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

  void display(float xpos, float ypos, int Width, int Height) 
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

  int getWidth() 
  {
    return images[0].width;
  }
}

final int constant(int integer)
{
  final int constant = integer;

  return constant;
}

void draw()
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