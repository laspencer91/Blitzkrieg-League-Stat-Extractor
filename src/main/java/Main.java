import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static String NAME_CLASS_STRING = "data-name";
    private static String KILLS_CLASS_STRING = "data-kills";
    private static String DEATHS_CLASS_STRING = "data-deaths";
    private static String ASSISTS_CLASS_STRING = "data-assists";


    public static void main(String[] args) throws IOException
    {
        Document doc = Jsoup.connect("http://blitzkrieg.theplays.co.uk/match/the-maintenance-men-vs-negative-impulse-nacl-week-1").get();
        System.out.println(doc.title());

        List<Player> players = new ArrayList();

        // Get all lineups from single doc
        Elements lineups = doc.getElementsByClass("lineup");

        // Process All Lineups. Loop through each player on the page
        for (Element player : lineups)
        {
            String playerKey = player.getElementsByClass(NAME_CLASS_STRING).select("a").attr("abs:href");
            String playerName = player.getElementsByClass(NAME_CLASS_STRING).text();
            Integer kills = Integer.parseInt(player.getElementsByClass(KILLS_CLASS_STRING).text());
            Integer deaths = Integer.parseInt(player.getElementsByClass(DEATHS_CLASS_STRING).text());
            Integer assists = Integer.parseInt(player.getElementsByClass(ASSISTS_CLASS_STRING).text());

            String team = "Team Not Found";
            if (player.parent().parent().parent().parent().hasClass("sp-template"))
                team = player.parent().parent().parent().parent().getElementsByClass("sp-table-caption").text();

            players.add(new Player(playerKey, playerName, kills, deaths, assists, team));
        }

        for (Player player : players) {
            System.out.println(player);
        }
    }
}
