package inn.config.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
   //    String URL = "jdbc:mysql://104.238.222.166:3306/kwegyira_inn_register";
//1244@inn2023 //jdbc:mysql://127.0.0.1:3308/inn_register
//
//   protected String URL = "jdbc:mysql://" +SERVER_NAME+"/"+DATABASE_NAME;

   protected String SERVER_NAME = "localhost";
   protected String PORT = "3308";
   protected String DATABASE_NAME = "inn_register";
   protected String USERNAME = "register";
   protected String PASSWORD = "1244656800";
   protected Connection CONNECTOR() throws SQLException {
      String URL = "jdbc:mysql://" +SERVER_NAME+":"+PORT + "/"+DATABASE_NAME;
      return DriverManager.getConnection(URL, USERNAME, PASSWORD);
   }

}//END OF CLASS
