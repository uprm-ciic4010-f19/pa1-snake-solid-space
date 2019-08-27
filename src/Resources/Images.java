package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage[] Resume;
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

    public Images() {

        butstart = new BufferedImage[3];
        Continue = new BufferedImage[2];
        gTitle = new BufferedImage[2];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Off = new BufferedImage[2];
        On = new BufferedImage[2];

        try {

            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Title.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Sheets/Pause.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/Resume.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeP.png"));
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
            crt = ImageIO.read(getClass().getResourceAsStream("/Sheets/CRT2.png"));
            score = ImageIO.read(getClass().getResourceAsStream("/Sheets/score.png"));
            dialog = ImageIO.read(getClass().getResourceAsStream("/Sheets/what.png"));
            options = ImageIO.read(getClass().getResourceAsStream("/Sheets/OptionsS.png"));
            
            gameOver = ImageIO.read(getClass().getResourceAsStream("/Sheets/GameOver.png"));
            //gameOver = new ImageIcon("/Sheets/GameOver.gif").getImage();
            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/icon.png")));
            apple = ImageIO.read(getClass().getResourceAsStream("/Assets/Apple.png"));
            rottenApple = ImageIO.read(getClass().getResourceAsStream("/Assets/AppleR.png"));


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
