package sample.Entity;

public class DBUser {

    private String username;
    private String password;
    private String port;
    private String sid;
    private String hostname;

    public DBUser() {
    }

    public DBUser(String username, String password, String port, String sid, String hostname) {
        this.username = username;
        this.password = password;
        this.port = port;
        this.sid = sid;
        this.hostname = hostname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
