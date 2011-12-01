package net.sourceforge.greenvine.dbextractor.impl;

public class Connection {

    private String jdbcDriver;
    private String jdbcUrl;
    private String username;
    private String password;

    public String getJdbcDriver() {
        return this.jdbcDriver;
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setJdbcDriver(final String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public void setJdbcUrl(final String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
