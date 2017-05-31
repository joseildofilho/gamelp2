package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by root on 15/05/17.
 */
public class RankingDAOImpl implements RankingDAO {

    private static final String RANKING_FILE = "ranking.txt";
    private File file;
    //todo não foi finalizada ainda
    public RankingDAOImpl() {
        this.file = new File(RANKING_FILE);
        if (!this.file.exists()) try {
            this.file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addResult(String result) {
        try {
            new PrintStream(this.file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPosition(String id) {
        return null;
    }
}

class Ranking {
    private ConcurrentSkipListSet<String> ranking;

    public Ranking() {
        this.ranking = new ConcurrentSkipListSet<>();
    }

    public void addResult(String s) {
        this.ranking.add(s);
    }

    public String getRanking() {
        Iterator i = this.ranking.descendingIterator();
        StringBuilder sb = new StringBuilder();
        while (i.hasNext()) {
            sb.append(i.next());
        }
        return sb.toString();
    }

}
