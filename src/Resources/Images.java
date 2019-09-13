package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
    public static BufferedImage[] Back;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static ImageIcon icon;
    public static BufferedImage gameOver;
    public static BufferedImage[] Continue;
    public static BufferedImage[] gTitle;
    public static BufferedImage crt;
    public static BufferedImage score;
    public static BufferedImage dialog;
    public static BufferedImage[] Off;
    public static BufferedImage[] On;
    public static BufferedImage Sound;
    public static BufferedImage options;
    public static BufferedImage apple;
    public static BufferedImage rottenApple;
    public static BufferedImage goldenApple;
    public static BufferedImage playOne;
    public static BufferedImage playerOneScore;
    public static BufferedImage playerTwoScore;
    public static BufferedImage[] singlePlayer;
    public static BufferedImage[] versus;
    public static BufferedImage[] titleOptions;
    public static Image playerOne;
    public static Image playerOneWin;
    public static Image playerTwo;
    public static Image playerTwoWin;
    public static BufferedImage[] titleOverlay;
    public static Image titleAni;
    public static Image titleAni2;
    public static Image titleFix;
    public static Image noiseTv;

    public Images() {

        butstart = new BufferedImage[3];
        Continue = new BufferedImage[2];
        gTitle = new BufferedImage[2];
        Resume = new BufferedImage[2];
        Back = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Off = new BufferedImage[2];
        On = new BufferedImage[2];
        titleOverlay = new BufferedImage[2];
        singlePlayer = new BufferedImage[2];
        versus = new BufferedImage[2];
        titleOptions = new BufferedImage[2];

        try {

            //title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Sheets/Pause.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
            Back[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Back.png"));
            Back[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BackP.png"));
            Continue[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Continue.png"));
            Continue[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ContinueP.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitle.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/BTitleP.png"));
            gTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/GTitle.png"));
            gTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/GTitleP.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Options.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsP.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/NormBut.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/HoverBut.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/ClickedBut.png"));//clickbut
            Off[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/Off.png"));
            Off[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/OffP.png"));
            On[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/On.png"));
            On[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/OnP.png"));
            Sound= ImageIO.read(getClass().getResourceAsStream("/Buttons/Sound.png"));
            crt = ImageIO.read(getClass().getResourceAsStream("/Sheets/CRT.png"));
            score = ImageIO.read(getClass().getResourceAsStream("/Sheets/Score.png"));
            playerOneScore = ImageIO.read(getClass().getResourceAsStream("/Sheets/PlayerOne.png"));
            playerTwoScore = ImageIO.read(getClass().getResourceAsStream("/Sheets/PlayerTwo.png"));
            dialog = ImageIO.read(getClass().getResourceAsStream("/Sheets/what.png"));
            playOne = ImageIO.read(getClass().getResourceAsStream("/Assets/PlayerOne.gif"));
            options = ImageIO.read(getClass().getResourceAsStream("/Sheets/OptionsS.png"));
            titleOverlay[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/PressStart.png"));
            titleOverlay[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/PressStartP.png"));
            singlePlayer[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SinglePlayer.png"));
            singlePlayer[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/SinglePlayerP.png"));
            versus[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Versus.png"));
            versus[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/VersusP.png"));
            titleOptions[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/titleOptions.png"));
            titleOptions[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/titleOptionsP.png"));
            titleFix = ImageIO.read(getClass().getResourceAsStream("/Sheets/titleFix.png"));
            
            gameOver = ImageIO.read(getClass().getResourceAsStream("/Sheets/GameOver.png"));
            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));
            apple = ImageIO.read(getClass().getResourceAsStream("/Assets/Apple.png"));
            rottenApple = ImageIO.read(getClass().getResourceAsStream("/Assets/AppleR.png"));
            goldenApple = ImageIO.read(getClass().getResourceAsStream("/Assets/AppleG.png"));
            URL url = this.getClass().getResource("/Sheets/FullTrim.gif");
            titleAni = new ImageIcon(url).getImage();
            URL url1 = this.getClass().getResource("/Assets/PlayerOne.gif");
            playerOne = new ImageIcon(url1).getImage();
            URL url2 = this.getClass().getResource("/Assets/PlayerOneWin.gif");
            playerOneWin = new ImageIcon(url2).getImage();
            URL url3 = this.getClass().getResource("/Assets/PlayerTwo.gif");
            playerTwo = new ImageIcon(url3).getImage();
            URL url4 = this.getClass().getResource("/Assets/PlayerTwoWin.gif");
            playerTwoWin = new ImageIcon(url4).getImage();
            URL url5 = this.getClass().getResource("/Assets/noiseTV.gif");
            noiseTv = new ImageIcon(url5).getImage();
        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    

}
