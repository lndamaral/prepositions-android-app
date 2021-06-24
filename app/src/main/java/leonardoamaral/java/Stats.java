package leonardoamaral.java;

/**
 * Created by leonardoamaral on 10/5/15.
 */
public class Stats {

    private static Stats instance = null;
    static int cAnswer = 0;
    static int wAnswer = 0;
    static int sAnswer = 0;

    private Stats() {

    }

    public int getTotalOfCorrect(){
        return cAnswer;
    }

    public int getTotalOfWrong(){
        return wAnswer;
    }

    public int getTotalOfSkipped(){
        return sAnswer;
    }

    public static Stats getInstance(){
        if(instance == null) {
            instance = new Stats();
        }
        return instance;
    }

    public static void correct(){
        cAnswer++;
    }

    public static void wrong(){
        wAnswer++;
    }

    public static void skip(){
        sAnswer++;
    }

    public static void clearStats(){
        cAnswer = 0;
        wAnswer = 0;
        sAnswer = 0;
    }

    public int getTotalOfAll(){
        return cAnswer + wAnswer + sAnswer;
    }
}
