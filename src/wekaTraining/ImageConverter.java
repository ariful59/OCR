/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekaTraining;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

/**
 *
 * @author Rumi
 */
public class ImageConverter {

    private String pathName = "";
    private PrintWriter pr = null;

    public ImageConverter(String path) {
        this.pathName = path;
        try {
            pr = new PrintWriter("dataset.arff", "UTF-8");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String findClass(String str) {
        int level = -1;
        if (str.charAt(0) == 'B') {
            level = 10;
        } else if (str.charAt(0) == 'E') {
            level = 0;
        }

        level += (str.charAt(1) - '0');
        return String.valueOf(level);
    }

    private void makeAttr() {
        pr.println("@relation myimg");
        //System.out.println("@relation myimg");
        for (int i = 0; i < 10000; i++) {
            pr.println("@attribute pixel" + i + " {0,1}");
            //System.out.println("@attribute pixel" + i + " {0,1}");
        }

        pr.print("@attribute class {");
        for (int i = 0; i < 19; i++) {
            pr.print(" " + i + ",");
        }
        pr.println("19}");
        pr.println("@data");
    }

    public void interateDir() throws IOException {
        File[] files = new File(pathName).listFiles();
        makeAttr();
        int count = 0;
        for (File f : files) {
            //System.out.println(f.getAbsolutePath());
            count++;
            File imgFile = new File(f.getAbsolutePath());
            BufferedImage img = ImageIO.read(imgFile);

            //System.out.println(f.getName()+" "+level);
            int len = f.getName().indexOf('.');
            String text = f.getName().substring(len + 1);
           // System.out.println(text);
            if (text.equals("db")) {
                continue;
            }
            if ((img.getHeight() < 100 || img.getWidth() < 100)) {
                System.out.println(f.getName() + " is not in proper size.");
                continue;
            }

            String level = findClass(f.getName());
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int color = img.getRGB(i, j);
                    if (color >= 0xFF888888) {
                        pr.print("1,");
                    } else {
                        pr.print("0,");
                    }
                }
            }
            pr.println(level);
        }
        pr.flush();
        pr.close();
    }

    public void inputImageConv(String path) {
        try {
            pr = new PrintWriter("inputPhoto.arff", "UTF-8");
            makeAttr();
            File imgFile = new File(path);
            BufferedImage img = ImageIO.read(imgFile);

            if (img.getHeight() < 100 || img.getWidth() < 100) {
                System.out.println(imgFile.getName() + " is not in proper size.");
                return;
            }

            String level = findClass(imgFile.getName());

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int color = img.getRGB(i, j);
                    if (color >= 0xFF888888) {
                        pr.print("1,");
                    } else {
                        pr.print("0,");
                    }
                }
            }

            pr.println(level);
            pr.flush();
            pr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
