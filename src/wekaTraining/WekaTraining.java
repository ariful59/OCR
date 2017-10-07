/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wekaTraining;

import java.io.FileReader;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

/**
 *
 * @author anando
 */
public class WekaTraining {
    private String path;

    public WekaTraining(String path) {
        this.path = path;
    }
    
    public void doTrain(){
        try{
            
            //Reading training arff or csv file
            FileReader trainreader = new FileReader(path);
            Instances train = new Instances(trainreader);
            train.setClassIndex(train.numAttributes() - 1);
            //Instance of NN
            MultilayerPerceptron mlp = new MultilayerPerceptron();
            mlp.setLearningRate(0.3);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(200);
            mlp.setHiddenLayers("10");
            mlp.buildClassifier(train);
 
            Evaluation eval = new Evaluation(train);
            eval.evaluateModel(mlp, train);
            eval.crossValidateModel(mlp, train, 5, new Random(1));
            System.out.println(eval.errorRate()); //Printing Training Mean root squared Error
            System.out.println(eval.toSummaryString()); //Summary of Training
            
 
 
            /// Print Confusion Matrix
            double conMat[][]= eval.confusionMatrix();
            int i,j;
            for (i=0;i<conMat.length;++i) {
                for (j=0;j<conMat[i].length;++j) {
                    System.out.print(conMat[i][j]+" ");
                }
                System.out.println("");
            }
            
            weka.core.SerializationHelper.write("Ariful02_59.model", mlp);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
