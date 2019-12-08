package ca.aodong;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Input {
    private static ImageData[] loadedImages;
    private static int numFrames;
    private static boolean[] isLoaded;

    public void loadInput(String s){
        //Assume that the DICOM file has 5 frames
        numFrames = 5;
        try {
            loadedImages = new ImageData[numFrames];
            for(int j=0;j<numFrames;j++){
                String name = s + "frame" + (j+1) + ".bmp";
                File inputFile = new File(name);
                BufferedImage img = ImageIO.read(inputFile);
                int x = img.getWidth();
                int y = img.getHeight();
                int[] pixelValue = new int[x * y];
                for (int n = 0; n < y; n++) {
                    for (int m = 0; m < x; m++) {
                        pixelValue[n * x + m] = img.getRGB(m, n) & 0xFF;
                    }
                }
                loadedImages[j] = new ImageData(x,y,pixelValue);
            }
            verifyInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verifyInput(){
        isLoaded = new boolean[numFrames];
        for(int i=0;i<numFrames;i++){
            isLoaded[i] = ImageVerify.verify1File(loadedImages[i]);
        }
    }

    public static ImageData[] loadedImages() {
        return loadedImages;
    }

    public static int numFrames() {
        return numFrames;
    }

    public static boolean[] isLoaded() {
        return isLoaded;
    }
}
