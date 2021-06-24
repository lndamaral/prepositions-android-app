package leonardoamaral.java;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {

    private List<String> PREPOSITIONS;

    public Utils(){

        PREPOSITIONS = new ArrayList<>();
        PREPOSITIONS.add(" OF ");
        PREPOSITIONS.add(" IN ");
        PREPOSITIONS.add(" FOR ");
        PREPOSITIONS.add(" WITH ");
        PREPOSITIONS.add(" ON ");
        PREPOSITIONS.add(" AT ");
        PREPOSITIONS.add(" FROM ");
        PREPOSITIONS.add(" BY ");
        PREPOSITIONS.add(" ABOUT ");
        PREPOSITIONS.add(" INTO ");
        PREPOSITIONS.add(" THROUGH ");
        PREPOSITIONS.add(" AFTER ");
        PREPOSITIONS.add(" OVER ");
        PREPOSITIONS.add(" BETWEEN ");
        PREPOSITIONS.add(" OUT ");
        PREPOSITIONS.add(" AGAINST ");
        PREPOSITIONS.add(" WITHOUT ");
        PREPOSITIONS.add(" BEFORE ");
        PREPOSITIONS.add(" UNDER ");
        PREPOSITIONS.add(" AROUND ");
        PREPOSITIONS.add(" AMONG ");

    }


    public List<String> mixAnswers (String correctedAnswer){

        List<String> mix = new ArrayList<>();
        mix.add(correctedAnswer);
        String p;

        do{
            p = PREPOSITIONS.get(randomNumberUntil(PREPOSITIONS.size()));
        }while (mix.get(0).equals(p));

        mix.add(p);

        do{
            p = PREPOSITIONS.get(randomNumberUntil(PREPOSITIONS.size()));
        }while (mix.get(1).equals(p) || mix.get(0).equals(p));

        mix.add(p);

        do{
            p = PREPOSITIONS.get(randomNumberUntil(PREPOSITIONS.size()));
        }while (mix.get(2).equals(p) || mix.get(1).equals(p) || mix.get(0).equals(p));

        mix.add(p);

        Collections.sort(mix);

        return mix;
    }

    public String[] maskSentence(List<String> sentences){
        String sortedSentence = sentences.get(randomNumberUntil(sentences.size()));
        String identifiedPreposition = null;
        String newSentence = null;

        for (String p: PREPOSITIONS) {
            if (sortedSentence.toUpperCase().contains(p)){
                identifiedPreposition = p;
                newSentence = sortedSentence.toUpperCase().replace(p, " _____ ");
                break;
            }
        }
        return new String[] {newSentence,identifiedPreposition};
    }

    private int randomNumberUntil(int max){

        if (max > 0){
            max = max - 1;
        }

        return new Random().nextInt((max - 0) + 1) + 0;
    }

    public List<String> getLinesWithPrepositions(List<String> lines){

        List<String> linesWithPrepositions = new ArrayList<>();

        for (String line: lines) {
            for (String p : PREPOSITIONS) {
                if (line.toUpperCase().contains(p)){
                    linesWithPrepositions.add(line.replace("<i>","").replace("- ", "").replace("</i >", ""));
                    break;
                }
            }
        }
        return linesWithPrepositions;
    }

    public List<String> readFiles(){

        List<String> paths = getPathOfFiles();
        List<String> lines = new ArrayList<>();
        String line;

        try {
            for (String path: paths) {

                FileReader inputFile = new FileReader(path);
                BufferedReader bufferReader = new BufferedReader(inputFile);

               while ((line = bufferReader.readLine()) != null) {
                    lines.add(line);
                }
            }

        }catch (Exception e){
            e.getMessage();
        }

        return lines;
    }

    public List<String> getPathOfFiles(){

        File folder = new File("/prepositions/data_source");

        String patha = new File("/data_source").getAbsolutePath();
        File[] fList = folder.listFiles();


        File[] files = folder.listFiles();
        List<String> listOfFiles = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String path = files[i].getAbsolutePath();
                if (path.toUpperCase().contains(".SRT")) {
                    listOfFiles.add(path);
                }
            }
        }

        return listOfFiles;
    }

    public List<String> getAllLines(AssetManager am, List<String> srtFiles){

        InputStream input;
        String str;
        List<String> allLines = new ArrayList<>();
        StringBuffer buf = new StringBuffer();

        for (String file: srtFiles) {
            try{
                input = am.open(file);

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                if (input!=null) {
                    while ((str = reader.readLine()) != null) {
                        allLines.add(str);
                    }
                }

            }catch (Exception e){

            }
        }

        return allLines;
    }

    public List<String> getSrtFiles(AssetManager am) {
        List<String> srtFiles = new ArrayList<>();

        try {
            String[] files = am.list("");

            for (int i = 0; i < files.length; i++){
                if(files[i].toUpperCase().contains(".SRT")){
                    srtFiles.add(files[i]);
                }
            }

        }catch (Exception e){

        }

        return srtFiles;
    }
}
