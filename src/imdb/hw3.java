package imdb;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class hw3 {
	
	static PreparedStatement ps=null;
	static PreparedStatement ps1=null;
	static Connection con=null;
	static ResultSet rs=null;
	

	private JFrame frame;
	private JComboBox<String> andorcomboBox;
	JComboBox<String> tagweightcomboBox;
	private JTextField fromdate_tf;
	private JTextField dateto_tf;
	private JList<String> genreList;
	private JList<String> countryList;
	private JList<String> actorList;
	private JList<String> directorList;
	private JList<String> tagList;
	private JList<String> movieList;
	private JList<String> userList;
	
	
	private String startyear;
	private String stopyear;
	private String tagvalue;
	private String tagweight="";
	 String countrymidquery = "";
	 String countryfinalquery = "";
     String actorquery = "";
     String directorquery = "";
     String tagacquery = "";
     String tagdirquery = "";
     String tagquery = "";
     String actormidquery = "";
     String directormidquery = "";
     String tagacmidquery="";
     String tagdirmidquery="";
     String tagmidquery="";
     String tagweightmidquery="";
     String moviequery="";
     String moviemidquery="";
     String userquery="";
	
	DefaultListModel push_genreList=new DefaultListModel();
	DefaultListModel push_countryList=new DefaultListModel();
	DefaultListModel push_directorList=new DefaultListModel();
	DefaultListModel push_actorList=new DefaultListModel();
	DefaultListModel push_tagList=new DefaultListModel();
	DefaultListModel push_movieList=new DefaultListModel();
	DefaultListModel push_userList=new DefaultListModel();
	ArrayList<String> arraylist=new ArrayList();
	ArrayList<String> selcountrylist=new ArrayList();
	ArrayList<String> selactorlist=new ArrayList();
	ArrayList<String> seldirectorlist=new ArrayList();
	ArrayList<String> seltaglist=new ArrayList();
	ArrayList<String> temptaglist=new ArrayList();
	ArrayList<String> selmovielist=new ArrayList();
	private JTextField tftagvalue;
	private JTextArea querytextArea;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hw3 window = new hw3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public hw3() {
		initialize();
		run1();
	}

	private void run1() { 
		try { 
			con = openConnection(); 
			populate_genre();
		} catch (SQLException e) { 
			System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
		} catch (ClassNotFoundException e) { 
			System.err.println("Cannot find the database driver"); 
		} finally { 
			// Never forget to close database connection 
			//closeConnection(con); 
		} 
	} 

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
	

	// to populate the genre panel
	private void populate_genre()
	{ String genrequery="SELECT DISTINCT G.genre FROM movie_genres G";
		try{
			
			ResultSet rsg=null;
			ps=con.prepareStatement(genrequery);
			rsg=ps.executeQuery(genrequery);
			
			while(rsg.next())
			{
				if(!push_genreList.contains(rsg.getString("genre")))
				{
					push_genreList.addElement(rsg.getString("genre"));
				}
			}
			
			ps.close();
			rsg.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		genreList.setModel(push_genreList);
		
		MouseListener ml=new MouseAdapter()
				{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()==1)
				{
					arraylist=(ArrayList<String>) genreList.getSelectedValuesList();
				}
				
				System.out.println(arraylist.size());
			}
				};
				
				genreList.addMouseListener(ml);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 730, 749);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		JPanel movie_attributepanel = new JPanel();
		movie_attributepanel.setBackground(new Color(0, 139, 139));
		movie_attributepanel.setBounds(6, 6, 533, 26);
		frame.getContentPane().add(movie_attributepanel);

		JLabel lblNewLabel = new JLabel("Movie Attritbutes");
		lblNewLabel.setForeground(new Color(0, 0, 139));
		lblNewLabel.setBackground(Color.BLACK);
		movie_attributepanel.add(lblNewLabel);

		JPanel movie_resultsh_panel = new JPanel();
		movie_resultsh_panel.setBackground(new Color(0, 139, 139));
		movie_resultsh_panel.setBounds(540, 6, 184, 26);
		frame.getContentPane().add(movie_resultsh_panel);

		JLabel lblNewLabel_1 = new JLabel("    Movie Results");
		movie_resultsh_panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(0, 0, 139));
		
		JScrollPane genrescrollPane = new JScrollPane();
		genrescrollPane.setBounds(6, 58, 127, 139);
		frame.getContentPane().add(genrescrollPane);

		genreList=new JList<>();
		JPanel genrepanel = new JPanel();
		genrescrollPane.setViewportView(genrepanel);
		genrepanel.setBackground(new Color(0, 139, 139));
		genrepanel.setForeground(new Color(0, 128, 128));
		genrepanel.add(genreList);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(135, 206, 250));
		panel_4.setBounds(6, 197, 127, 26);
		frame.getContentPane().add(panel_4);

		JLabel lblNewLabel_3 = new JLabel("Movie Year");
		panel_4.add(lblNewLabel_3);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(16, 223, -11, 10);
		frame.getContentPane().add(panel_5);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(0, 139, 139));
		panel_6.setBounds(6, 223, 127, 108);
		frame.getContentPane().add(panel_6);

		JLabel lblNewLabel_7 = new JLabel("From");
		panel_6.add(lblNewLabel_7);

		fromdate_tf = new JTextField();
		panel_6.add(fromdate_tf);
		fromdate_tf.setColumns(10);
		fromdate_tf.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  startyear=fromdate_tf.getText();
		        }
		      });
		
		JLabel lblNewLabel_8 = new JLabel("To");
		panel_6.add(lblNewLabel_8);

		dateto_tf = new JTextField();
		panel_6.add(dateto_tf);
		dateto_tf.setColumns(10);
		dateto_tf.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  stopyear=dateto_tf.getText();
		        }
		      });
		
		JPanel search_panel = new JPanel();
		search_panel.setBackground(new Color(135, 206, 250));
		search_panel.setBounds(6, 393, 533, 44);
		frame.getContentPane().add(search_panel);
		search_panel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Search Between Attribute Values");
		lblNewLabel_4.setBounds(47, 11, 158, 14);
		search_panel.add(lblNewLabel_4);

		andorcomboBox = new JComboBox<String>();
		andorcomboBox.setBounds(216, 8, 195, 20);
		search_panel.add(andorcomboBox);
		andorcomboBox.setMaximumRowCount(3);
		andorcomboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select AND, OR between attributes", "AND", "OR" }));
		andorcomboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxAndOrActionPerformed(evt);
            }

			private void ComboBoxAndOrActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				
			}
        });


		JPanel countriesh_panel = new JPanel();
		countriesh_panel.setBackground(new Color(135, 206, 250));
		countriesh_panel.setBounds(134, 34, 100, 26);
		frame.getContentPane().add(countriesh_panel);

		JLabel lblCountries = new JLabel("Countries");
		countriesh_panel.add(lblCountries);

		JPanel genreh_panel = new JPanel();
		genreh_panel.setBackground(new Color(135, 206, 250));
		genreh_panel.setBounds(6, 34, 127, 26);
		frame.getContentPane().add(genreh_panel);

		JLabel lblNewLabel_2 = new JLabel("Genres");
		genreh_panel.add(lblNewLabel_2);
		
		JScrollPane countryscrollPane = new JScrollPane();
		countryscrollPane.setBounds(134, 58, 100, 273);
		frame.getContentPane().add(countryscrollPane);

		JPanel countries_panel = new JPanel();
		countryscrollPane.setViewportView(countries_panel);
		countries_panel.setBackground(new Color(0, 139, 139));
		countryList=new JList<>();
		countries_panel.add(countryList);

		JPanel casth_panel = new JPanel();
		casth_panel.setBackground(new Color(135, 206, 250));
		casth_panel.setBounds(236, 34, 127, 26);
		frame.getContentPane().add(casth_panel);

		JLabel lblCast = new JLabel("Cast");
		casth_panel.add(lblCast);

		JPanel cast_panel = new JPanel();
		cast_panel.setBackground(new Color(135, 206, 250));
		cast_panel.setBounds(236, 58, 127, 26);
		frame.getContentPane().add(cast_panel);

		JLabel lblActoractresses = new JLabel("Actor/Actresses");
		cast_panel.add(lblActoractresses);

		JPanel tagh_panel = new JPanel();
		tagh_panel.setBackground(new Color(135, 206, 250));
		tagh_panel.setBounds(364, 34, 175, 26);
		frame.getContentPane().add(tagh_panel);

		JLabel lblTag = new JLabel("Tag id's and Values");
		tagh_panel.add(lblTag);
		
		JScrollPane tagsscrollPane = new JScrollPane();
		tagsscrollPane.setBounds(364, 58, 175, 159);
		frame.getContentPane().add(tagsscrollPane);

		JPanel tags_panel = new JPanel();
		tagsscrollPane.setViewportView(tags_panel);
		tags_panel.setBackground(new Color(0, 139, 139));
		tagList=new JList<>();
		tags_panel.add(tagList);
		
		JScrollPane moviescrollPane = new JScrollPane();
		moviescrollPane.setBounds(540, 34, 174, 297);
		frame.getContentPane().add(moviescrollPane);

		JPanel movieresults_panel = new JPanel();
		moviescrollPane.setViewportView(movieresults_panel);
		movieresults_panel.setBackground(new Color(135, 206, 250));
		movieList=new JList<>();
		movieresults_panel.add(movieList);

		JPanel query_panel = new JPanel();
		query_panel.setBackground(new Color(0, 139, 139));
		query_panel.setBounds(6, 448, 533, 133);
		frame.getContentPane().add(query_panel);
		query_panel.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("Query here");
		lblNewLabel_5.setBounds(10, 10, 55, 14);
		query_panel.add(lblNewLabel_5);
	    
	    JScrollPane tascrollPane = new JScrollPane();
	    tascrollPane.setBounds(85, 5, 438, 117);
	    query_panel.add(tascrollPane);
		
	    querytextArea = new JTextArea();
	    tascrollPane.setViewportView(querytextArea);

		JPanel userresultsh_panel = new JPanel();
		userresultsh_panel.setBackground(new Color(0, 139, 139));
		userresultsh_panel.setBounds(540, 334, 184, 44);
		frame.getContentPane().add(userresultsh_panel);

		JLabel lblNewLabel_6 = new JLabel("User Results");
		lblNewLabel_6.setForeground(new Color(0, 0, 139));
		lblNewLabel_6.setBackground(new Color(0, 0, 139));
		userresultsh_panel.add(lblNewLabel_6);
		
		JScrollPane userscrollPane = new JScrollPane();
		userscrollPane.setBounds(540, 379, 184, 202);
		frame.getContentPane().add(userscrollPane);

		JPanel userresults_panel = new JPanel();
		userscrollPane.setViewportView(userresults_panel);
		userresults_panel.setBackground(new Color(135, 206, 250));
		userList=new JList<>();
		userresults_panel.add(userList);
		

		JButton bmovie = new JButton("Execute Movie Query ");
		bmovie.setForeground(new Color(0, 139, 139));
		bmovie.setBackground(new Color(255, 255, 255));
		bmovie.setBounds(49, 591, 149, 61);
		frame.getContentPane().add(bmovie);
		bmovie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BMovieActionPerformed(evt);
            }
		});

		JButton buser = new JButton("Execute User Query");
		buser.setForeground(new Color(0, 139, 139));
		buser.setBounds(305, 599, 143, 45);
		frame.getContentPane().add(buser);
		buser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BUserActionPerformed(evt);
            }
		});
		
		JButton bcountry = new JButton("Get Country");
		bcountry.setBounds(6, 342, 116, 23);
		frame.getContentPane().add(bcountry);
		bcountry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BAddCountryActionPerformed(evt);
            }
		});

		JScrollPane actorsscrollPane = new JScrollPane();
		actorsscrollPane.setBounds(236, 84, 127, 133);
		frame.getContentPane().add(actorsscrollPane);
		
		JPanel actorspanel = new JPanel();
		actorspanel.setBackground(new Color(0, 139, 139));
		actorsscrollPane.setViewportView(actorspanel);
		actorList=new JList<>();
		actorspanel.add(actorList);
		//actorspanel.setLayout(null);
		
		JPanel dhpanel = new JPanel();
		dhpanel.setBackground(new Color(135, 206, 250));
		dhpanel.setBounds(236, 220, 127, 26);
		frame.getContentPane().add(dhpanel);
		
		JLabel lblDirectors = new JLabel("Directors");
		dhpanel.add(lblDirectors);
		
		JScrollPane directorscrollPane = new JScrollPane();
		directorscrollPane.setBounds(236, 246, 127, 84);
		frame.getContentPane().add(directorscrollPane);
		
		JPanel directorspanel = new JPanel();
		directorspanel.setBackground(new Color(0, 128, 128));
		directorscrollPane.setViewportView(directorspanel);
		directorList=new JList<>();
		directorspanel.add(directorList);
		//directorspanel.setLayout(null);
		
		JButton bactor = new JButton("Get Actors");
		bactor.setBounds(134, 342, 100, 23);
		frame.getContentPane().add(bactor);
		bactor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BAddActorActionPerformed(evt);
            }
		});

		JButton btag = new JButton("Get Tags");
		btag.setBounds(374, 342, 89, 23);
		frame.getContentPane().add(btag);
		btag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BAddTagActionPerformed(evt);
            }
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 139, 139));
		panel.setBounds(364, 220, 175, 111);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblTagWeight = new JLabel("Tag Weight");
		lblTagWeight.setBounds(7, 34, 55, 14);
		panel.add(lblTagWeight);
		
		tagweightcomboBox = new JComboBox<String>();
		tagweightcomboBox.setBounds(72, 31, 93, 20);
		panel.add(tagweightcomboBox);
		tagweightcomboBox.setMaximumRowCount(4);
		tagweightcomboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select >, < or =", ">", "<" ,"="}));
		tagweightcomboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxTagActionPerformed(evt);
            }

			private void ComboBoxTagActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				
			}
        });
		
		JLabel lblValue = new JLabel("Value");
		lblValue.setBounds(7, 73, 46, 14);
		panel.add(lblValue);
		
		tftagvalue = new JTextField();
		tftagvalue.setBounds(55, 70, 86, 20);
		panel.add(tftagvalue);
		tftagvalue.setColumns(10);
		tftagvalue.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  tagvalue=tftagvalue.getText();
		        }
		      });
		
		JButton bdirector = new JButton("Get Directors");
		bdirector.setBounds(246, 342, 117, 23);
		frame.getContentPane().add(bdirector);
		bdirector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BAddDirectorActionPerformed(evt);
            }
		});

	}
	protected void BUserActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(arraylist.size()!=0)
        {         
			
			if(selmovielist.size() == 0)
			{
			int start = 0;
			int end = movieList.getModel().getSize()-1;
			movieList.setSelectionInterval(start, end);
			selmovielist = (ArrayList<String>) movieList.getSelectedValuesList(); 
			}
            
            String BetweenAttribute = "";
            ResultSet rsu=null;
            
        
            if(andorcomboBox.getSelectedIndex()==0 || andorcomboBox.getSelectedIndex()==1)
            {
               BetweenAttribute = "INTERSECT";
            }
            else
            {
                if( andorcomboBox.getSelectedIndex()==2)
                {
                  BetweenAttribute = "UNION";
                }
            }
            //Genre Within attributes
          
            
            int s=0;
            for(s=0;s<selmovielist.size()-1;s++)
            {
            	userquery += "SELECT DISTINCT u.userid\nFROM user_taggedmovies u\nWHERE u.movieid='"+selmovielist.get(s).split("\\s")[0]+"'\n"+BetweenAttribute+"\n";
            	 	
            }
            
            userquery += "SELECT DISTINCT u.userid\nFROM user_taggedmovies u\nWHERE u.movieid='"+selmovielist.get(s).split("\\s")[0]+"'";
            
          querytextArea.setText(userquery);
            
            try
                    {
                    ps = con.prepareStatement(userquery);
                    rsu = ps.executeQuery(userquery);
                    
                    while(rsu.next())
                    {
                        
                            if(!push_userList.contains(rsu.getString("USERID")))
                            {
                            	push_userList.addElement(rsu.getString("USERID"));
                            	
                            }
                     }

                    ps.close();
                    rsu.close();

               userList.setModel(push_userList);
                
                    
                
                    //ps.close();
                    
              
                
            }catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
        }
		
		
	}

	protected void BAddDirectorActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
		if(arraylist.size()!=0)
        {         
           
			if(selactorlist.size() == 0)
			{
			int start = 0;
			int end = actorList.getModel().getSize()-1;
			actorList.setSelectionInterval(start, end);
			selactorlist = (ArrayList<String>) actorList.getSelectedValuesList(); 
			}
			
            String BetweenAttribute = "";
            ResultSet rsd=null;
            
        
            if(andorcomboBox.getSelectedIndex()==0 || andorcomboBox.getSelectedIndex()==1)
            {
               BetweenAttribute = "INTERSECT";
            }
            else
            {
                if( andorcomboBox.getSelectedIndex()==2)
                {
                  BetweenAttribute = "UNION";
                }
            }
            //Genre Within attributes
            int p=0;
            for(p=0;p<selcountrylist.size()-1;p++)
            {
                	actormidquery += "SELECT DISTINCT ac.movieid\nFROM movie_actors ac,movie_countries c\nWHERE ac.movieid=c.movieid AND c.country='"+selcountrylist.get(p)+"' AND ac.movieid IN ("+countrymidquery+")\n"+BetweenAttribute+"\n";

            }    
            actormidquery += "SELECT DISTINCT ac.movieid\nFROM movie_actors ac,movie_countries c\nWHERE ac.movieid=c.movieid AND c.country='"+selcountrylist.get(p)+"' AND ac.movieid IN ("+countrymidquery+")";
            int z=0;
            for(z=0;z<selactorlist.size()-1;z++)
            {
                    directorquery += "SELECT DISTINCT md.directorname\nFROM movie_directors md,movie_actors ac\nWHERE md.movieid=ac.movieid AND ac.actorname='"+selactorlist.get(z)+"' AND md.movieid IN ("+actormidquery+")\n"+BetweenAttribute+"\n";
            }    
           
            directorquery += "SELECT DISTINCT md.directorname\nFROM movie_directors md,movie_actors ac \nWHERE md.movieid=ac.movieid AND ac.actorname='"+selactorlist.get(z)+"' AND md.movieid IN ("+actormidquery+")";
                    //System.out.println(actorquery);
                    //System.out.println(directorquery);
            try
                    {
                    
                    ps = con.prepareStatement(directorquery);
                    rsd = ps.executeQuery(directorquery);
                    
                    while(rsd.next())
                    {
                        
                            if(!push_directorList.contains(rsd.getString("DIRECTORNAME")))
                            {
                            	push_directorList.addElement(rsd.getString("DIRECTORNAME"));
                            }
                     }
                    
                    ps.close();
                    rsd.close();

                directorList.setModel(push_directorList);

                    MouseListener mouseListener2 = new MouseAdapter() 
                    {
                        public void mouseClicked(MouseEvent e) 
            	    {
                           if (e.getClickCount() == 1)
                           {
                        	   seldirectorlist = (ArrayList<String>) directorList.getSelectedValuesList();
                           }
                        }
                    };
                    directorList.addMouseListener(mouseListener2);
                
            }catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
        }
		
	}

	protected void BMovieActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(arraylist.size()!=0)
        {         
            
			if(seltaglist.size() == 0)
			{
			int start = 0;
			int end = tagList.getModel().getSize()-1;
			tagList.setSelectionInterval(start, end);
			seltaglist = (ArrayList<String>) tagList.getSelectedValuesList(); 
			}
			
			
            String BetweenAttribute = "";
           
            ResultSet rsm=null;
            
        
            if(andorcomboBox.getSelectedIndex()==0 || andorcomboBox.getSelectedIndex()==1)
            {
               BetweenAttribute = "INTERSECT";
            }
            else
            {
                if( andorcomboBox.getSelectedIndex()==2)
                {
                  BetweenAttribute = "UNION";
                }
            }
            
            if(tagweightcomboBox.getSelectedIndex()==0 || tagweightcomboBox.getSelectedIndex()==1)
            {
            	tagweight = ">";
            }
            else if( tagweightcomboBox.getSelectedIndex()==2)
            {  
            	tagweight = "<";   
            }
            else if( tagweightcomboBox.getSelectedIndex()==3)
            {  
            	tagweight = "=";   
            }
            
            //Genre Within attributes
         
            int y=0;
            for(y=0;y<seldirectorlist.size()-1;y++)
            {
            	tagmidquery += "SELECT DISTINCT mt.movieid\nFROM tags ta,movie_tags mt,movie_directors md\nWHERE md.movieid=mt.movieid AND mt.tagid=ta.tagid and md.directorname='"+seldirectorlist.get(y)+"' AND  mt.movieid IN ("+directormidquery+")\n"+BetweenAttribute+"\n";
            	 	
            }
            tagmidquery += "SELECT DISTINCT mt.movieid\nFROM tags ta,movie_tags mt,movie_directors md\nWHERE md.movieid=mt.movieid AND mt.tagid=ta.tagid and md.directorname='"+seldirectorlist.get(y)+"' AND  mt.movieid IN ("+directormidquery+")";
        	
            
            
            for(int i=0;i<seltaglist.size();i++)
            { String str;
              String[] l;
                    str=seltaglist.get(i);
                    l=str.split("\\s");
                    temptaglist.add(l[0]);
            }
            
            
            int x=0;
            for(x=0;x<seltaglist.size()-1;x++)
            {
            moviequery += "SELECT DISTINCT m.movieid,m.title,g.genre,m.myear,c.country,m.avgaudrating,m.audnorating\nFROM tags ta,movie_tags mt,movies m, movie_genres g,movie_countries c\nWHERE m.movieid=mt.movieid AND m.movieid=g.movieid AND m.movieid=c.movieid AND mt.tagid=ta.tagid AND ta.tagid='"+seltaglist.get(x).split("\\s")[0]+"' AND mt.movieid IN ("+tagmidquery+")\nINTERSECT\nSELECT DISTINCT mo.movieid,mo.title,mg.genre,mo.myear,mc.country,mo.avgaudrating,mo.audnorating\nFROM movies mo,tags tta,movie_tags mta, movie_genres mg,movie_countries mc WHERE mo.movieid=mta.movieid AND mo.movieid=mg.movieid AND mo.movieid=mc.movieid AND tta.tagid=mta.tagid AND mta.tagweight "+tagweight+"'"+tagvalue+"'\n"+BetweenAttribute+"\n";
            	 	
            }
            moviequery += "SELECT DISTINCT m.movieid,m.title,g.genre,m.myear,c.country,m.avgaudrating,m.audnorating\nFROM tags ta,movie_tags mt,movies m, movie_genres g,movie_countries c\nWHERE m.movieid=mt.movieid AND m.movieid=g.movieid AND m.movieid=c.movieid AND mt.tagid=ta.tagid AND ta.tagid='"+seltaglist.get(x).split("\\s")[0]+"' AND mt.movieid IN ("+tagmidquery+")\nINTERSECT\nSELECT DISTINCT mo.movieid,mo.title,mg.genre,mo.myear,mc.country,mo.avgaudrating,mo.audnorating\nFROM movies mo,tags tta,movie_tags mta, movie_genres mg,movie_countries mc WHERE mo.movieid=mta.movieid AND mo.movieid=mg.movieid AND mo.movieid=mc.movieid AND tta.tagid=mta.tagid AND mta.tagweight "+tagweight+"' "+tagvalue+"'";
            
            //System.out.println(moviequery);
            
            querytextArea.setText(moviequery);
            
            try
                    {
                    ps = con.prepareStatement(moviequery);
                    rsm = ps.executeQuery(moviequery);
                    
                    while(rsm.next())
                    {
                        
                            if(!push_movieList.contains(rsm.getString("MOVIEID")))
                            {
                            	push_movieList.addElement(rsm.getString("MOVIEID")+" "+rsm.getString("TITLE")+" "+rsm.getString("GENRE")+" "+rsm.getString("MYEAR")+" "+rsm.getString("COUNTRY")+" "+rsm.getString("AVGAUDRATING")+" "+rsm.getString("AUDNORATING"));
                            }
                     }

                    ps.close();
                    rsm.close();

                movieList.setModel(push_movieList);
               
                    MouseListener mouseListener = new MouseAdapter() 
                    {
                        public void mouseClicked(MouseEvent e) 
            	    {
                           if (e.getClickCount() == 1)
                           {
                        	   selmovielist = (ArrayList<String>) movieList.getSelectedValuesList();
                           }
                        }
                    };
                    movieList.addMouseListener(mouseListener);
                
                
            }catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
        }
		
		
	}

	protected void BAddTagActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(arraylist.size()!=0)
        {         
			
			if(seldirectorlist.size() == 0)
			{
			int start = 0;
			int end = directorList.getModel().getSize()-1;
			directorList.setSelectionInterval(start, end);
			seldirectorlist = (ArrayList<String>) directorList.getSelectedValuesList(); 
			}
            
            String BetweenAttribute = "";
            ResultSet rst=null;
            
        
            if(andorcomboBox.getSelectedIndex()==0 || andorcomboBox.getSelectedIndex()==1)
            {
               BetweenAttribute = "INTERSECT";
            }
            else
            {
                if( andorcomboBox.getSelectedIndex()==2)
                {
                  BetweenAttribute = "UNION";
                }
            }
            //Genre Within attributes
          
            int v=0;
            for(v=0;v<selactorlist.size()-1;v++)
            {
                	
                    directormidquery += "SELECT DISTINCT md.movieid\nFROM movie_directors md,movie_actors ac\nWHERE md.movieid=ac.movieid AND ac.actorname='"+selactorlist.get(v)+"' AND md.movieid IN ("+actormidquery+")\n"+BetweenAttribute+"\n";
            }    

            directormidquery += "SELECT DISTINCT md.movieid\nFROM movie_directors md,movie_actors ac\nWHERE md.movieid=ac.movieid AND ac.actorname='"+selactorlist.get(v)+"' AND md.movieid IN ("+actormidquery+")";
                    //System.out.println(actorquery);
                    //System.out.println(directorquery);
                    
            
            int u=0;
            for(u=0;u<seldirectorlist.size()-1;u++)
            {
            	tagquery += "SELECT DISTINCT ta.tagid,ta.tagvalue\nFROM tags ta,movie_tags mt,movie_directors md\nWHERE md.movieid=mt.movieid AND mt.tagid=ta.tagid and md.directorname='"+seldirectorlist.get(u)+"' AND mt.movieid IN ("+directormidquery+")\n"+BetweenAttribute+"\n";
            	 	
            }
            tagquery += "SELECT DISTINCT ta.tagid,ta.tagvalue\nFROM tags ta,movie_tags mt,movie_directors md\nWHERE md.movieid=mt.movieid AND mt.tagid=ta.tagid and md.directorname='"+seldirectorlist.get(u)+"' AND mt.movieid IN ("+directormidquery+")";   
                   
            
            
           // System.out.println(tagquery);
            
            try
                    {
                    ps = con.prepareStatement(tagquery);
                    rst = ps.executeQuery(tagquery);
                    
                    while(rst.next())
                    {
                        
                            if(!push_tagList.contains(rst.getString("TAGID")))
                            {
                            	push_tagList.addElement(rst.getString("TAGID")+" "+rst.getString("TAGVALUE"));
                            	
                            }
                     }

                    ps.close();
                    rst.close();

                tagList.setModel(push_tagList);
                
                    
                
                    //ps.close();
                    
                    
                    MouseListener mouseListener1 = new MouseAdapter() 
                    {
                        public void mouseClicked(MouseEvent e) 
            	    {
                           if (e.getClickCount() == 1)
                           {
                        	   seltaglist = (ArrayList<String>) tagList.getSelectedValuesList();
                           }
                        }
                    };
                    tagList.addMouseListener(mouseListener1);
                
                
            }catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
        }
		
		
	}

	protected void BAddActorActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(arraylist.size()!=0)
        {         
			
			if(selcountrylist.size() == 0)
			{
			int start = 0;
			int end = countryList.getModel().getSize()-1;
			countryList.setSelectionInterval(start, end);
			selcountrylist = (ArrayList<String>) countryList.getSelectedValuesList(); 
			}
            String BetweenAttribute = "";
            ResultSet rsa=null;
            
        
            if(andorcomboBox.getSelectedIndex()==0 || andorcomboBox.getSelectedIndex()==1)
            {
               BetweenAttribute = "INTERSECT";
            }
            else
            {
                if( andorcomboBox.getSelectedIndex()==2)
                {
                  BetweenAttribute = "UNION";
                }
            }
            //Genre Within attributes
            int k=0;
            for(k=0;k<arraylist.size()-1;k++)
            {
            	countrymidquery += "SELECT DISTINCT c.movieid\nFROM movie_genres g, movies m, movie_countries c\nWHERE m.movieid=g.movieid AND c.movieid=m.movieid AND m.myear >='"+ startyear+"' AND m.myear<='"+stopyear+"' AND g.genre = '"+arraylist.get(k)+"'\n"+BetweenAttribute+"\n";
            }
            
            countrymidquery += "SELECT DISTINCT c.movieid\nFROM movie_genres g, movies m, movie_countries c\nWHERE m.movieid=g.movieid AND c.movieid=m.movieid AND m.myear >='"+ startyear+"' AND m.myear<='"+stopyear+"' AND g.genre = '"+arraylist.get(k)+"'";
            //System.out.println(countrymidquery);
            int j=0;
            for(j=0;j<selcountrylist.size()-1;j++)
            {
                	actorquery += "SELECT DISTINCT ac.actorname\nFROM movie_actors ac,movie_countries c\nWHERE ac.movieid=c.movieid AND c.country='"+selcountrylist.get(j)+"' AND ac.movieid IN ("+countrymidquery+")\n"+BetweenAttribute+"\n";
                    
            }    
            actorquery += "SELECT DISTINCT ac.actorname\nFROM movie_actors ac,movie_countries c\nWHERE ac.movieid=c.movieid AND c.country='"+selcountrylist.get(j)+"' AND ac.movieid IN ("+countrymidquery+")";
           
                    //System.out.println(actorquery);
                    //System.out.println(directorquery);
                    
                    try
                    {
                    ps = con.prepareStatement(actorquery);
                    rsa = ps.executeQuery(actorquery);
                    
                    while(rsa.next())
                    {
                        
                            if(!push_actorList.contains(rsa.getString("ACTORNAME")))
                            {
                            	push_actorList.addElement(rsa.getString("ACTORNAME"));
                            }
                     }

                    ps.close();
                    rsa.close();

                actorList.setModel(push_actorList);

                    MouseListener mouseListener1 = new MouseAdapter() 
                    {
                        public void mouseClicked(MouseEvent e) 
            	    {
                           if (e.getClickCount() == 1)
                           {
                        	   selactorlist = (ArrayList<String>) actorList.getSelectedValuesList();
                           }
                        }
                    };
                    actorList.addMouseListener(mouseListener1);

            }catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
        }
    }//GEN-LAST:event_BAddCountryActionPerformed

	private void BAddCountryActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
		if(arraylist.size()!=0)
        {         
            String countryquery = "";
            String BetweenAttribute = "";
        
            if(andorcomboBox.getSelectedIndex()==0 || andorcomboBox.getSelectedIndex()==1)
            {
               BetweenAttribute = "INTERSECT";
            }
            else
            {
                if(andorcomboBox.getSelectedIndex()==2)
                {
                  BetweenAttribute = "UNION";
                }
            }
            //Genre Within attributes
            int i=0;
            for(i=0;i<arraylist.size()-1;i++)
            {
            	countryquery += "SELECT DISTINCT c.movieid\nFROM movie_genres g, movies m, movie_countries c\nWHERE m.movieid=g.movieid AND c.movieid=m.movieid AND m.myear >='"+ startyear+"' AND m.myear<='"+stopyear+"' AND g.genre = '"+arraylist.get(i)+"'\n"+BetweenAttribute+"\n";
            }
            
            countryquery += "SELECT DISTINCT c.movieid\nFROM movie_genres g, movies m, movie_countries c\nWHERE m.movieid=g.movieid AND c.movieid=m.movieid AND m.myear >='"+ startyear+"' AND m.myear<='"+stopyear+"' AND g.genre = '"+arraylist.get(i)+"'";
            //System.out.println(countryquery);
            ResultSet rsc = null;
            
            	countryfinalquery += "SELECT DISTINCT c.country\nFROM movie_countries c\nWHERE c.movieid IN ("+countryquery+")";
            
            
            try
            {
                ps = con.prepareStatement(countryfinalquery);
                rsc = ps.executeQuery(countryfinalquery);
               
                
                while(rsc.next())
                {
                    
                        if(!push_countryList.contains(rsc.getString("COUNTRY")))
                        {
                            push_countryList.addElement(rsc.getString("COUNTRY"));
                        }
                    }
                    ps.close();
                    rsc.close();
                
            }catch(Exception ex)
    		{
    			ex.printStackTrace();
    		}
                countryList.setModel(push_countryList);
               
        MouseListener mouseListener = new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
	    {
               if (e.getClickCount() == 1)
               {
            	   selcountrylist = (ArrayList<String>) countryList.getSelectedValuesList();
               }
            }
        };
        countryList.addMouseListener(mouseListener);
            
            
          
        }
    }//GEN-LAST:event_BAddCountryActionPerformed
}
