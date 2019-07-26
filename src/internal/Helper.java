/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package internal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author shouv
 */
public class Helper {
    final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    final static String USER = "root";
    final static String PASS = "admin";
    @SuppressWarnings("empty-statement")
    public static String verifyUser(String username,String password,char type)
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="";
            if(type=='P')
        {
            sql="select Password from prison.prisoner where P_ID='"+username+"'";
        }
        else if(type=='W')
        {
            sql="select Password from prison.warden where W_ID='"+username+"'";
        }
        else if(type=='G')
        {
            sql="select Password from prison.guard where G_ID='"+username+"'";
        }
            
           ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           String p=rs.getString("Password");
          
            //System.err.println(p);
            //System.out.println(p);
            
            if(p.equals(password))
            {
               System.out.println("Correct Password");
            }    
            else 
            {
                username="N";
            }
                       stmt.close();
            conn.close();
            rs.close();
           
            System.out.println(username);
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return username;  
    }
    
    public static void scheduleGuard(String wid , String gid , String ba)
    {
        
        Connection conn = null;
        Statement stmt = null;
        try{
           
            Class.forName(JDBC_DRIVER);
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            stmt = conn.createStatement();
            String sql="update prison.guard set W_ID=\'"+wid+"\' , Scheduled_In=\'"+ba+"\' where G_ID=\'"+gid+"\'";
            System.out.println("sql update?");
            
            int t=stmt.executeUpdate(sql);
            
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage());
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage());
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }//end finally try
    }
    }
    
    
    public static String generatePID()
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
        String p="";
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="select count(*) as tot from prison.prisoner";
                    ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           p=""+(rs.getInt("tot")) ;
            System.out.println(p);
           while(p.length()<=3)
           {
               p="0"+p;
                System.out.println(p);
           }
           p="P"+p;
            System.out.println(p);
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return p;
    }
    
    
    
    
    public static String roomAlloted()
    {
        Connection conn = null;
        Connection conn1=null;
        Statement stmt = null;
        Statement stmt1 = null;
        //System.out.println("lol");
        String B="";
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            conn1 = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            stmt1= conn1.createStatement();
            String sql="select * from prison.block";
                    ResultSet rs = stmt.executeQuery(sql); 
            
            while(rs.next())
            {
                String sql1="select count(*) as tot from prison.prisoner where Block=\""+rs.getString("B_name")+"\"";
                ResultSet rs1=stmt1.executeQuery(sql1);
                rs1.next();
                if(rs.getInt("N_Rooms")-rs1.getInt("tot") != 0)
                {
                    B=rs.getString("B_name");
                    break;
                }
                rs1.close();
            }
            
            for(int i=1 ; i<=rs.getInt("N_Rooms") ;i++)
            {
                 String sql1="select count(*) as tot from prison.prisoner where Block=\""+B+"\" and Room_No="+Integer.toString(i);
                ResultSet rs1=stmt1.executeQuery(sql1);
                rs1.next();
                if(rs1.getInt("tot")==0)
                {
                    B=B+" ";
                    B=B+Integer.toString(i);
                    break;
                }
                rs1.close();
            }
           
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            stmt1.close();
            conn1.close();
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return B;
     }
    
    
    
    public static String generateLID()
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
        String p="";
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="select count(*) as tot from prison.leaves";
                    ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           p=""+(rs.getInt("tot")+1) ;
            System.out.println(p);
           while(p.length()<=3)
           {
               p="0"+p;
                System.out.println(p);
           }
           p="L"+p;
            System.out.println(p);
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return p;
    }
    
    public static String getWarden()
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
        String p="";
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="SELECT W_ID FROM prison.warden  ORDER BY RAND ( )  LIMIT 1  ;";
                    ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           p=rs.getString("W_ID");
       
          
          
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return p;
    }
    
    
    public static void updateLeave(String lid , int f)
    {
        
        Connection conn = null;
        Statement stmt = null;
        try{
           
            Class.forName(JDBC_DRIVER);
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            stmt = conn.createStatement();
            String sql="";
            if(f==1)
            sql="update prison.leaves set Approved_Status=\"approved\" where L_ID=\""+lid+"\"";
            else if(f==0)
             sql="update prison.leaves set Approved_Status=\"declined\" where L_ID=\""+lid+"\"";   
            System.out.println("sql update?");
            
            int t=stmt.executeUpdate(sql);
            
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage());
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage());
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }//end finally try
    }
    }
    
    
    public static Date getAdmitDate(String id)
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
        long millis=System.currentTimeMillis();
        java.sql.Date p=new java.sql.Date(millis);
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="SELECT Admit_Date FROM prison.prisoner  where P_ID=\""+id+"\"";
                    ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           p=rs.getDate("Admit_Date");
       
          
          
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return p;
    }
    
    
    
    public static int getSentence(String id)
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
       int p=0;
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="SELECT Sentence FROM prison.prisoner  where P_ID=\""+id+"\"";
                    ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           p=rs.getInt("Sentence");
       
          
          
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return p;
    }
    
    
    
    public static String generateFBID()
    {
        Connection conn = null;
        Statement stmt = null;
        //System.out.println("lol");
        String p="";
        try{
           
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting");
            
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connected");
            
            stmt = conn.createStatement();
            String sql="select count(*) as tot from prison.prisoner";
                ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           p=""+(rs.getInt("tot")+1) ;
            System.out.println(p);
           while(p.length()<=3)
           {
               p="0"+p;
                System.out.println(p);
           }
           p="FB"+p;
            System.out.println(p);
           
           
          
            //System.err.println(p);
            //System.out.println(p);
            
            
                       stmt.close();
            conn.close();
            rs.close();
           
         
            
           
        }catch(SQLException se){
            //Handle JDBC Errors
            System.out.println(se.getMessage()+" Error1");
        }catch(Exception e){
            //Handle errors for Class.forname
            System.out.println(e.getMessage()+" Error2");
        }finally{
        //finally block used to close resources
        try{
            if(stmt!=null)
            stmt.close();
        }catch(SQLException se2){
        }// nothing we can do
            try{
            if(conn!=null)
            conn.close();
        }catch(SQLException se){
            System.out.println(se.getMessage()+" Error3");}
    }//end try
        return p;
    }
}
