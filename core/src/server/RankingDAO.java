package server;

/**
 * Created by root on 15/05/17.
 */
public interface RankingDAO {

    void addResult(String result);

    String getPosition(String id);
}
