/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekaTraining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
public class ShowResult {
    
    private String dec(int a){
        if(a>9){
            int dig = a-10;
            return "B-"+dig;
        }
        else {
            return "E-"+a;
        }
    }
    
    public String showRes(String path) throws IOException, Exception{
        FileReader trainreader = new FileReader("inputPhoto.arff");
        Instances train = new Instances(trainreader);
        train.setClassIndex(train.numAttributes() - 1);
        
        MultilayerPerceptron mlp = (MultilayerPerceptron)weka.core.SerializationHelper.read(new FileInputStream(new File(path)));
        
        System.out.println(path);
        Evaluation eval = new Evaluation(train);
        eval.evaluateModel(mlp, train);
        //eval.crossValidateModel(mlp, train, 2, new Random(1));
        System.out.println(eval.errorRate()); //Printing Training Mean root squared Error
        System.out.println(eval.toSummaryString()); //Summary of Training
        System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " "
                + eval.recall(1));


        /// Print Confusion Matrix
        double conMat[][]= eval.confusionMatrix();
        int i,j;
        for (i=0;i<conMat.length;++i) {
            for (j=0;j<conMat[i].length;++j) {
                //System.out.print(conMat[i][j]+" ");
                if(conMat[i][j]>0){
                    //System.out.println("Input "+dec(i)+" Detected "+dec(j));
                    return "Input "+dec(i)+" Detected "+dec(j);
                }
            }
            //System.out.println("");
        }
        return null;
        
    }
}
