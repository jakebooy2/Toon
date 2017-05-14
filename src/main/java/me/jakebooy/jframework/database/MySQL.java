package me.jakebooy.jframework.database;

import me.jakebooy.jframework.JFramework;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySQL {

    private final JFramework framework;

    private final String host;
    private final String username;
    private final String password;
    private final String database;
    private final int port;

    private Connection connection;

    public MySQL(JFramework framework){
        this.framework = framework;
        host = framework.getProperties().getString("mysql_host");
        username = framework.getProperties().getString("mysql_username");
        password = framework.getProperties().getString("mysql_password");
        database = framework.getProperties().getString("mysql_database");
        port = framework.getProperties().getInt("mysql_port");
    }

    public void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }catch(SQLException e){
            System.out.println("Unable to connect to MySQL: " + e.getMessage());
        }
    }

    public void disconnect(){
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }catch(Exception e){
            System.out.println("Error disconnecting MySQL: " + e.getMessage());
        }
    }

    /**
     * create a table
     * @param sql the statement to execute
     */
    public void createTable(String sql){
        Statement statement;
        try{
            statement = connection.createStatement();
            statement.execute(sql);
        }catch(SQLException e){
            System.out.println("MySQL > Unable to create table: " + e.getMessage());
        }
    }

    /**
     * execute a statement
     * @param sql the statement to execute
     * @return the result of the execution
     */
    public boolean execute(String sql){
        PreparedStatement prep = null;
        ResultSet rs = null;
        try{
            prep = connection.prepareStatement(sql);
            rs = prep.executeQuery();
            if(rs.next()) return true;
        }catch(SQLException e){
            //System.out.println("MySQL > Unable to execute query: " + e.getMessage());
        }finally{
            try{
                if(prep!=null)prep.close();
                if(rs!=null)rs.close();
            }catch(SQLException e){
                System.out.println("(execute) Error closing: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * find a database entry
     * @param haystack the sql to run
     * @param needles list of strings to search for - the order of this list is the order the statement will be prepared
     * @return return the result of the search
     */
    public List<HashMap<String, Object>> find(String haystack, Object... needles){
        PreparedStatement prep = null;
        List<HashMap<String, Object>> results = new ArrayList<>();
        ResultSet rs = null;
        try{
            prep = connection.prepareStatement(haystack);
            for(int i = 0; i < needles.length; i++){
                prep.setObject(i+1, needles[i]);
            }
            rs = prep.executeQuery();
            while(rs.next()){
                HashMap<String, Object> result = new HashMap<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    result.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                results.add(result);
            }
        }catch(SQLException e){
            System.out.println("MySQL > Unable to execute query: " + e.getMessage());
        }finally{
            try{
                if(rs!=null)rs.close();
                if(prep!=null)prep.close();
            }catch(SQLException e){
                System.out.println("(find) Error closing: " + e.getMessage());
            }
        }
        return results;
    }

    /**
     * insert a row into a table
     * @param sql the sql to execute
     * @param values the values to add to the row
     */
    public boolean add(String sql, Object... values){
        PreparedStatement prep = null;
        try{
            prep = connection.prepareStatement(sql);
            for(int i = 0; i < values.length; i++){
                prep.setObject(i+1, values[i]);
            }
            prep.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println("MySQL > Unable to execute query: " + e.getMessage());
            return false;
        }finally{
            try {
                if(prep!=null)prep.close();
            }catch(SQLException e){
                System.out.println("(add) Error closing: " + e.getMessage());
            }
        }
    }


    /**
     * remove a row from a table
     * @param sql the sql to execute
     * @param values the values to find the row to remove
     */
    public boolean remove(String sql, Object... values){
        PreparedStatement prep = null;
        try{
            prep = connection.prepareStatement(sql);
            for(int i = 0; i < values.length; i++){
                prep.setObject(i+1, values[i]);
            }
            prep.executeUpdate();
            return true;
        }catch(Exception e){
            System.out.println("MySQL > Unable to execute query: " + e.getMessage());
            return false;
        }finally{
            try {
                if(prep!=null)prep.close();
            }catch(SQLException e){
                System.out.println("(remove) Error closing: " + e.getMessage());
            }
        }
    }

    /**
     * update a columns row
     * @param sql sql to execute
     * @param needle row value to find in specified column
     * @param haystack row value to update in specified column
     */
    public boolean update(String sql, Object needle, Object haystack){
        PreparedStatement prep = null;
        try{
            prep = connection.prepareStatement(sql);
            prep.setObject(1, needle);
            prep.setObject(2, haystack);
            prep.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("MySQL > Unable to execute query: " + e.getMessage());
            return false;
        }finally{
            try{
                if(prep!=null)prep.close();
            }catch(SQLException e){
                System.out.println("(update) Error closing: " + e.getMessage());
            }
        }
    }

    /**
     * return a tables rows
     * @param table name of the table
     * @return returns a list of hashmaps containing columns
     */
    public List<HashMap<String, Object>> get(String table){
        List<HashMap<String, Object>> results = new ArrayList<>();
        PreparedStatement prep = null;
        ResultSet rs = null;
        try{
            prep = connection.prepareStatement("SELECT * FROM " + table);
            rs = prep.executeQuery();
            while(rs.next()){
                HashMap<String, Object> result = new HashMap<>();
                for(int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++){
                    result.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                results.add(result);
            }
        }catch(SQLException e){
            System.out.println("MySQL > Unable to execute query: " + e.getMessage());
        }finally{
            try{
                if(prep!=null)prep.close();
                if(rs!=null)rs.close();
            }catch(SQLException e){
                System.out.println("(get) Error closing: " + e.getMessage());
            }
        }
        return results;
    }



}
