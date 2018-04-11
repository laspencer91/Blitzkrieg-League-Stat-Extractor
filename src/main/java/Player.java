/**
 * Object Model For A Single Player
 */
public class Player
{
    private String profileLink;
    private String name;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private String team;

    public Player(String playerKey, String name, Integer kills, Integer deaths, Integer assists, String team) {
        this.profileLink = playerKey;
        this.name = name;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.team = team;
    }

    public String getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(String profileLink) {
        this.profileLink = profileLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public String toString() {
        return profileLink + "\n" + name + "\n" + kills + "\n" + deaths + "\n" + assists + "\n" + team;
    }
}
