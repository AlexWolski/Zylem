class button
{
  private

    int X;
  int Y;
  int Width;
  int Height;
  color color1;
  color color2;
  String Text = " ";
  PFont f = createFont("arial", 33, true); 

  public

    button()
  {
  }

  button(int p_X, int p_Y, int p_Width, int p_Height, color Color1, color Color2, String p_Text)
  {
    X = p_X;
    Y = p_Y;
    Width = p_Width;
    Height = p_Height;
    color1 = Color1;
    color2 = Color2;
    Text = p_Text;
  }

  void drawButton()
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

  void pressButton()
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

  boolean MousePressed()
  {
    if (mouseButton == LEFT && mouseX >= X - Width/2 && mouseX <= X + Width/2 && mouseY >= Y - Height/2 && mouseY <= Y + Height/2)
      return true;

    return false;
  }

  void action() {
  }
}

class Back extends button
{
  Back(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Back()
  {
  }

  void action()
  {
    if (!scrolling)
      displayScreen = homeScreen;
  }
}

class BackToGameMenu extends button
{
  BackToGameMenu(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  BackToGameMenu()
  {
  }

  void action()
  {
    if (!scrolling)
      displayScreen = gameMenuScreen;
  }
}

class smoothZoom extends button
{
  smoothZoom(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  smoothZoom()
  {
  }

  void action()
  {
    smoothZoom = true;
  }
}

class Start extends button
{
  Start(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Start()
  {
  }

  void action()
  {
    displayScreen = plantSelectScreen;
  }
}


class Settings extends button
{
  Settings(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Settings()
  {
  }

  void action()
  {
    if (displayScreen == homeScreen)
      displayScreen = settingsScreen;
    else
      displayScreen = inGameSettingsScreen;
  }
}

class Exit extends button
{
  Exit(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Exit()
  {
  }

  void action()
  {
    exit();
  }
}

class Difficulty extends button
{
  Difficulty(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Difficulty()
  {
  }

  void pressButton()
  {
  }
}

class Easy extends button
{
  Easy(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Easy()
  {
  }

  void action()
  {    
    stationaryButtonArray[8].color1 = #00FFFF;
    stationaryButtonArray[9].color1 = #1E82E6;
    stationaryButtonArray[10].color1 = #1E82E6;

    stationaryButtonArray[7].Text = "Easy";
    Difficulty = 0;
  }
}

class Normal extends button
{
  Normal(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Normal()
  {
  }

  void action()
  {
    stationaryButtonArray[8].color1 = #1E82E6;
    stationaryButtonArray[9].color1 = #00FFFF;
    stationaryButtonArray[10].color1 = #1E82E6;

    stationaryButtonArray[7].Text = "Normal";
    Difficulty = 1;
  }
}

class Hard extends button
{
  Hard(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Hard()
  {
  }

  void action()
  {
    stationaryButtonArray[8].color1 = #1E82E6;
    stationaryButtonArray[9].color1 = #1E82E6;
    stationaryButtonArray[10].color1 = #00FFFF;

    stationaryButtonArray[7].Text = "Hard";
    Difficulty = 2;
  }
}

class Copy extends button
{
  Copy(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Copy()
  {
  }

  void action()
  {
    StringSelection copySeed = new StringSelection(Integer.toString(seed));
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(copySeed, copySeed);

    stationaryButtonArray[12].Text = "Seed Copied";
  }
}

class MainMenu extends button
{
  MainMenu(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  MainMenu()
  {
  }

  void action()
  {
    displayScreen = homeScreen;
  }
}

class Confirm extends button
{
  Confirm(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Confirm()
  {
  }

  void action()
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
      displayGameScreen.scale = 1.0;
    }
  }
}

class Left extends button
{
  Left(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Left()
  {
  }

  void action()
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
  Right(int p_X, int p_Y, int p_Width, int p_Height, color color1, color color2, String p_Text)
  {
    super(p_X, p_Y, p_Width, p_Height, color1, color2, p_Text);
  }

  Right()
  {
  }

  void action()
  {
    if (scrolling == false)
    {
      scrolling = true;
      direction = -1;
    }
  }
}

void keyPressed() 
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