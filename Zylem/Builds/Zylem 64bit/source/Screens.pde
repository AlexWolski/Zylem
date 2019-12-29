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

  void drawScreen()
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

  void action()
  {
  }
}

class MovingScreen extends Screen
{
  MovingScreen(Image Images[], button Buttons[], int ImageCount, int ButtonCount)
  { 
    super(Images, Buttons, ImageCount, ButtonCount);
  }

  void action() 
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

  void action()
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

          images[0].x = round(width*0.75);
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
        scroll.image(images[0].Image, width*0.75 + width/10 + scrollX, width/10, images[0].Width, images[0].Height);
      else
        scroll.image(images[2].Image, -width/4 + width/10 + scrollX, width/10, images[0].Width, images[0].Height);

      scroll.endDraw();

      image(scroll, width/2, height/2);

      scrollX += width/50 * direction;
    }
  }
}

void mouseReleased()
{
  if (pressedButton != null && !inGame)
    pressedButton.action();
    
  pressedButton = null;
}
