package cn.lasagna.www.util;

/**
 * Created by walkerlala on 16-10-23.
 */

import cn.lasagna.www.classifier.knn.KNN;
//import org.jcp.xml.dsig.internal.dom.DOMUtils;

import java.io.*;
import java.util.DoubleSummaryStatistics;
import java.util.Properties;

public class Configuration {

    //forbit instantiating
    private Configuration() {

    }

    public static final String sourceDBUrl;
    public static final String sourceDBUser;
    public static final String sourceDBPasswd;
    public static final String sourceDBName;

    public static final String trainingSetDBUrl;
    public static final String trainingSetDBUser;
    public static final String trainingSetDBPasswd;
    public static final String trainingSetDBName;
    
    public static final String tfidfDBUrl;
    public static final String tfidfDBUser;
    public static final String tfidfDBPasswd;
    public static final String tfidfDBName;

    public static final String targetDBUrl;
    public static final String targetDBUser;
    public static final String targetDBPasswd;
    public static final String targetDBName;

    public static final String classifier;
    public static final int K;
    public static final double KNNKeywordsWeight;
    public static final double KNNTitleWeight;
    public static final double KNNDescriptionWeight;
    
    public static final int numOfTag;
    public static final String[] tags;

    //instantiate
    static {
        Properties prop = new Properties();
        try {
            InputStreamReader inputStream = new InputStreamReader(
                    new FileInputStream("conf/Configuration.properties"), "UTF-8");
            prop.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sourceDBUrl = prop.getProperty("sourceDBUrl");
        sourceDBUser = prop.getProperty("sourceDBUser");
        sourceDBPasswd = prop.getProperty("sourceDBPasswd");
        sourceDBName = prop.getProperty("sourceDBName");

        trainingSetDBUrl = prop.getProperty("trainingDBUrl");
        trainingSetDBUser = prop.getProperty("trainingDBUser");
        trainingSetDBPasswd = prop.getProperty("trainingDBPasswd");
        trainingSetDBName = prop.getProperty("trainingDBName");
        
        tfidfDBUrl = prop.getProperty("tfidfDBUrl");
        tfidfDBUser = prop.getProperty("tfidfDBUser");
        tfidfDBPasswd = prop.getProperty("tfidfDBPasswd");
        tfidfDBName = prop.getProperty("tfidfDBName");

        targetDBUrl = prop.getProperty("targetDBUrl");
        targetDBUser = prop.getProperty("targetDBUser");
        targetDBPasswd = prop.getProperty("targetDBPasswd");
        targetDBName = prop.getProperty("targetDBName");

        classifier = prop.getProperty("classifier");
        K = Integer.valueOf(prop.getProperty("K"));
        KNNKeywordsWeight = Double.valueOf(prop.getProperty("KNNKeywordsWeight"));
        KNNTitleWeight = Double.valueOf(prop.getProperty("KNNTitleWeight"));
        KNNDescriptionWeight = Double.valueOf(prop.getProperty("KNNDescriptionWeight"));
        numOfTag = Integer.valueOf(prop.getProperty("numOfTag"));
        tags = prop.getProperty("tags").split(";");
        if(tags.length != numOfTag){
            new Exception("ERROR Configuration: numOfTag NOT equal tags count").printStackTrace();
        }else{
            for(int i=0;i<numOfTag;i++){
                tags[i] = tags[i].split(":")[0];
            }
        }
    }

}
