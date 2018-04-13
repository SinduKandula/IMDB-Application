package imdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*; 

public class populate { 
	public static void main(String args[]) { 
		populate example = new populate(); 
		example.run(); 
	}     
	private void run() { 
		Connection con = null; 
		try { 
			con = openConnection();       
			populateMovie_Actors(con); 
			populateMovie_Countries(con);
			populateMovie_Directors(con);
			populateMovie_Genres(con);
			populateMovie_Tags(con);
            populateTags(con);
			//populateUser_RatedMovies_timestamp(con);
			//populateUser_RatedMovies(con);
			//populateUser_TaggedMovies_timestamp(con);
			populateUser_TaggedMovies(con);
			populate_movies(con);
			//showTableContent(con); 
		} catch (SQLException e) { 
			System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
		} catch (ClassNotFoundException e) { 
			System.err.println("Cannot find the database driver"); 
		} finally { 
			// Never forget to close database connection 
			closeConnection(con); 
		} 
	} 
	/** 
34        * Using prepared statement that is faster than regular statement. 
35        * @param con 
36        * @throws SQLException 
37        */ 
	private void populateMovie_Actors(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM movie_actors"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movie_actors VALUES(?,?,?,?)"); 
		try{
			File file=new File("DatFiles/movie_actors.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}

				count++;

				str=l.split("\\s");
				for(int j=3;j<str.length-1;j++)
				{
					str[2]+=" "+str[j]+" ";
				}
				str[3]=str[str.length-1];

				   stmt1.setString(1,str[0]); 
		           stmt1.setString(2,str[1]); 
		           stmt1.setString(3,str[2]); 
		           stmt1.setString(4,str[3]); 
		           
		           stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 
	
	
	private void populateMovie_Countries(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM movie_countries"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movie_countries VALUES(?,?)"); 
		try{
			File file=new File("DatFiles/movie_countries.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\s");
				if(str.length>1)
				{
				for(int j=2;j<str.length;j++)
						{
							str[1]+=" "+str[j]+" ";
						}
				   stmt1.setString(1,str[0]); 
		           stmt1.setString(2,str[1]);  
				}
				   
		           stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 
	
	private void populateMovie_Directors(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM movie_directors"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movie_directors VALUES(?,?,?)"); 
		try{
			File file=new File("DatFiles/movie_directors.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\s");
				for(int j=3;j<str.length;j++)
				{
					str[2]+=" "+str[j]+" ";
				}
				
				
				 stmt1.setString(1,str[0]); 
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[2]); 
		           
					
				   
		           stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 
	
	
	private void populateMovie_Genres(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM movie_genres"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movie_genres VALUES(?,?)"); 
		try{
			File file=new File("DatFiles/movie_genres.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\s");
				
				 stmt1.setString(1,str[0]); 
		         stmt1.setString(2,str[1]);
		         
		         stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 

	private void populateMovie_Tags(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM movie_tags"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movie_tags VALUES(?,?,?)"); 
		try{
			File file=new File("DatFiles/movie_tags.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\s");
				
				 stmt1.setString(1,str[0]); 
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[2]);
		         
		         stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 
	
	private void populateTags(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM tags"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO tags VALUES(?,?)"); 
		try{
			File file=new File("DatFiles/tags.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\s");
				for(int j=2;j<str.length;j++)
				{
					str[1]+=" "+str[j]+" ";
				}
				
				 stmt1.setString(1,str[0]); 
		         stmt1.setString(2,str[1]);
		        
		           stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 
	
//	private void populateUser_RatedMovies_timestamp(Connection con) throws SQLException { 
//		Statement stmt = con.createStatement(); 
//		System.out.println("Deleting previous tuples ..."); 
//		stmt.executeUpdate("DELETE FROM user_ratedmovie_timestamps"); 
//		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO user_ratedmovie_timestamps VALUES(?,?,?,?)"); 
//		try{
//			File file=new File("DatFiles/user_ratedmovies-timestamps.dat");
//			FileReader f=new FileReader(file);
//			BufferedReader br=new BufferedReader(f);
//
//			String l;
//			String[] str={};
//			int count=0;
//            boolean firstline=true;
//			while((l=br.readLine())!=null)
//			{
//				if(firstline==true)
//				{
//					firstline=false;
//					continue;
//				}
//				count++;
//
//				str=l.split("\\s");
//				
//				 stmt1.setString(1,str[0]); 
//		         stmt1.setString(2,str[1]);
//		         stmt1.setString(3,str[2]);
//		         stmt1.setString(4,str[3]);
//		         
//		         stmt1.executeUpdate(); 
//				
//			}
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		stmt.close(); 
//		stmt1.close(); 
//	} 
	
//	private void populateUser_RatedMovies(Connection con) throws SQLException { 
//		Statement stmt = con.createStatement(); 
//		System.out.println("Deleting previous tuples ..."); 
//		stmt.executeUpdate("DELETE FROM user_ratedmovies"); 
//		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO user_ratedmovies VALUES(?,?,?,?,?,?,?,?,?)"); 
//		try{
//			File file=new File("DatFiles/user_ratedmovies.dat");
//			FileReader f=new FileReader(file);
//			BufferedReader br=new BufferedReader(f);
//
//			String l;
//			String[] str={};
//			int count=0;
//            boolean firstline=true;
//			while((l=br.readLine())!=null)
//			{
//				if(firstline==true)
//				{
//					firstline=false;
//					continue;
//				}
//				count++;
//
//				str=l.split("\\s");
//				
//				 stmt1.setString(1,str[0]); 
//		         stmt1.setString(2,str[1]);
//		         stmt1.setString(3,str[2]);
//		         stmt1.setString(4,str[3]);
//		         stmt1.setString(5,str[4]);
//		         stmt1.setString(6,str[5]);
//		         stmt1.setString(7,str[6]);
//		         stmt1.setString(8,str[7]);
//		         stmt1.setString(9,str[8]);
//		         
//		         stmt1.executeUpdate(); 
//				
//			}
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		stmt.close(); 
//		stmt1.close(); 
//	} 

//	private void populateUser_TaggedMovies_timestamp(Connection con) throws SQLException { 
//		Statement stmt = con.createStatement(); 
//		System.out.println("Deleting previous tuples ..."); 
//		stmt.executeUpdate("DELETE FROM user_taggedmovie_timestamps"); 
//		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO user_taggedmovie_timestamps VALUES(?,?,?,?)"); 
//		try{
//			File file=new File("DatFiles/user_taggedmovies-timestamps.dat");
//			FileReader f=new FileReader(file);
//			BufferedReader br=new BufferedReader(f);
//
//			String l;
//			String[] str={};
//			int count=0;
//            boolean firstline=true;
//			while((l=br.readLine())!=null)
//			{
//				if(firstline==true)
//				{
//					firstline=false;
//					continue;
//				}
//				count++;
//
//				str=l.split("\\s");
//				
//				 stmt1.setString(1,str[0]); 
//		         stmt1.setString(2,str[1]);
//		         stmt1.setString(3,str[2]);
//		         stmt1.setString(4,str[3]);
//		         
//		         stmt1.executeUpdate(); 
//				
//			}
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//		stmt.close(); 
//		stmt1.close(); 
//	} 
	
	private void populateUser_TaggedMovies(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM user_taggedmovies"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO user_taggedmovies VALUES(?,?,?)"); 
		try{
			File file=new File("DatFiles/user_taggedmovies.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\s");
				
				 stmt1.setString(1,str[0]); 
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[2]);
//		         stmt1.setString(4,str[3]);
//		         stmt1.setString(5,str[4]);
//		         stmt1.setString(6,str[5]);
//		         stmt1.setString(7,str[6]);
//		         stmt1.setString(8,str[7]);
//		         stmt1.setString(9,str[8]);
		         
		         stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 

	
	private void populate_movies(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		System.out.println("Deleting previous tuples ..."); 
		stmt.executeUpdate("DELETE FROM movies"); 
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movies VALUES(?,?,?,?,?)"); 
		try{
			File file=new File("DatFiles/movies.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l;
			String[] str={};
			int count=0;
            boolean firstline=true;
			while((l=br.readLine())!=null)
			{
				if(firstline==true)
				{
					firstline=false;
					continue;
				}
				count++;

				str=l.split("\\t");
				
				 stmt1.setString(1,str[0]); 
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[5]);
		         stmt1.setString(4,str[17]);
		         stmt1.setString(5,str[18]);
		         
		         stmt1.executeUpdate(); 
				
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close(); 
		stmt1.close(); 
	} 
	
	/** 
54        * This method will first clean up the table and then 
55        * populate it with new data. 
56        * @param con 
57        * @throws java.sql.SQLException 
58        */ 
	private void publishData(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 


		System.out.println("Inserting Data ..."); 
		stmt.executeUpdate("insert into info values ('Clinton, Bill', '9876543210', 400000, 'Ex. White House')"); 
		stmt.executeUpdate("insert into info values ('Doll, Bob', '1234567890', 100000, 'House')"); 
		stmt.executeUpdate("insert into info values ('Bush, George', '6543210987', 450000, 'White House')"); 
		stmt.executeUpdate("insert into info values ('Gore, Al', '3210987654', 200000, 'donno')"); 
		stmt.executeUpdate("insert into info values ('Bing, Chandler', '4321056789', 750000, 'Friends')"); 
		stmt.close(); 
	}     
	private void showTableContent(Connection con) throws SQLException { 
		Statement stmt = con.createStatement(); 
		ResultSet result = stmt.executeQuery("SELECT * FROM movie_actors"); 

		/* 
           We use ResultSetMetaData.getColumnCount() to know the number columns 
           that are contained. 
		 */ 
		ResultSetMetaData meta = result.getMetaData(); 
		for (int col = 1; col <= meta.getColumnCount(); col++) { 
			System.out.println("Column" + col + ": " + meta.getColumnName(col) + 
					"\t, Type: " + meta.getColumnTypeName(col)); 
		} 

		/* 
89           Every time we call ResultSet.next(), its internal cursor will advance 
90           one tuple. By calling this method continuously, we can iterate through 
91           the whole ResultSet. 
92           */ 
		while (result.next()) { 
			for (int col = 1; col <= meta.getColumnCount(); col++) { 
				System.out.print("\"" + result.getString(col) + "\","); 
			} 
			System.out.println(); 
		} 

		/* 
101          It is always a good practice to close a statement as soon as we 
102          no longer use it. 
103          */ 
		stmt.close(); 
	}    
	/** 
108       * 
109       * @return a database connection 
110       * @throws java.sql.SQLException when there is an error when trying to connect database 
111       * @throws ClassNotFoundException when the database driver is not found. 
112       */ 
	private Connection openConnection() throws SQLException, ClassNotFoundException { 
		// Load the Oracle database driver 
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 

		/* 
118          Here is the information needed when connecting to a database 
119          server. These values are now hard-coded in the program. In 
120          general, they should be stored in some configuration file and 
121          read at run time. 
122          */ 
		String host = "localhost"; 
		String port = "1521"; 
		String dbName = "sindu"; 
		String userName = "scott"; 
		String password = "tiger"; 

		// Construct the JDBC URL 
		String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName; 
		return DriverManager.getConnection(dbURL, userName, password); 
	} 
	/** 
135       * Close the database connection 
136       * @param con 
137       */ 
	private void closeConnection(Connection con) { 
		try { 
			con.close(); 
		} catch (SQLException e) { 
			System.err.println("Cannot close connection: " + e.getMessage()); 
		}       } 
}
