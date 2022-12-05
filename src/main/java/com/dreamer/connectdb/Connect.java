package com.dreamer.connectdb;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
//import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;

@Named(value = "connect")
@SessionScoped
//@ApplicationScoped
public class Connect implements Serializable {
    private String textuser;
    public String getTextuser() {
        return textuser;
    }

    public void setTextuser(String textuser) throws UnsupportedEncodingException {
        //this.textuser = URLEncoder.encode(textuser,"UTF-8");
        this.textuser=textuser;
    }
    public Connect() { }
    
    public Connection Conn() throws ClassNotFoundException, IOException{
        Connection conn=null;
        //FacesContext.getCurrentInstance().getExternalContext().setResponseCharacterEncoding("UTF-8");
        try{
            Class.forName("org.postgresql.Driver");
            conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/links","postgres","123qwe$$");
            
            if(conn!=null)
            {
                try{
                    try (PreparedStatement prepareStat = conn.prepareStatement("INSERT INTO users (login) VALUES(?)")) {
                        prepareStat.setString(1,textuser);
                        prepareStat.execute();
                        FacesContext.getCurrentInstance().getExternalContext().redirect("result.xhtml");
                    }
                    catch(SQLException e){
                        Logger.getLogger("Ошибка создания sql-запроса",e.toString());
                    }
                    conn.close();
                }
                catch(SQLException e){
                    Logger.getLogger("Ошибка выполнения sql-запроса",e.toString());
                }
            }
        }
        catch(SQLException e){
            Logger.getLogger("Ошибка подключения к БД",e.toString());
        }
        return conn;
    }
}
