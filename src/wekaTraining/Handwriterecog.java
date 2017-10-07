/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekaTraining;

/**
 *
 * @author Rumi
 */
public class Handwriterecog {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ImageConverter img = new ImageConverter("Digits");
        WekaTraining train = new WekaTraining("dataset.arff");
        try{
            //img.interateDir();
            System.out.println("Entering Training seasson");
           //train.doTrain();
            
            img.inputImageConv("C:\\Users\\mdnur\\Documents\\NetBeansProjects\\wekaTrainng\\Digits/E0_2.jpg");
            //System.out.println("Starting");
            String str= new ShowResult().showRes("Ariful_59.model");
            System.out.println(str);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
 