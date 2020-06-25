
//package UI;
import java.awt.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class mainUI extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MainProgram mp;
	private mainUI mu = this;
	private MusicPlayer mplayer;

	private JTable music_table;
	private JTextField userID_textField;

	JSlider music_slider;
	
	
	
	//recommended music list example
	private String[] columnType = {"music list"};

    public Object[][] data = {
							           {""},
							           {""},
							           {""},
							           {""},
							           {""},
							           {""},
							           {""},
							           {""},
							           {""},
							           {""}
							    };
    
    //non-self modifying music list 
    DefaultTableModel model = new DefaultTableModel(data, columnType) {
		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int i, int c) {
            return false;
        }
    };
	
	public void setPointer(MainProgram mp, MusicPlayer mplayer){
		this.mp=mp;
		this.mplayer=mplayer;
	}
	
	public mainUI() {

		setSize(new Dimension(310, 560));
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		
		//Icon Image Path
		ImageIcon searchIcon = new ImageIcon("_icon/icons8-search-50.png");
		Image image = searchIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		searchIcon = new ImageIcon(newimg); 
		
		ImageIcon playIcon = new ImageIcon("_icon/icons8-play-50.png");
		ImageIcon pauseIcon = new ImageIcon("_icon/icons8-pause-50.png");
		ImageIcon prevIcon = new ImageIcon("_icon/icons8-skip-to-start-50.png");
		Image image1 = prevIcon.getImage(); // transform it 
		Image newimg1 = image1.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		prevIcon = new ImageIcon(newimg1); 
		
		ImageIcon nextIcon = new ImageIcon("_icon/icons8-end-50.png");
		Image image2 = nextIcon.getImage(); // transform it 
		Image newimg2 = image2.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		nextIcon = new ImageIcon(newimg2); 
		
		ImageIcon fileIcon = new ImageIcon("_icon/icons8-opened-folder-50.png");
		Image image3 = fileIcon.getImage(); // transform it 
		Image newimg3 = image3.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		fileIcon = new ImageIcon(newimg3); 

		ImageIcon albumIcon = new ImageIcon("_icon/icons8-music-50.png");
		
		getContentPane().setLayout(null);
		
		
		//twitter ID search 
		JPanel search_panel = new JPanel();
		search_panel.setBackground(Color.WHITE);
		search_panel.setBounds(0, 0, 295, 40);
		search_panel.setLayout(null);
		getContentPane().add(search_panel);
		
		userID_textField = new JTextField();
		userID_textField.setToolTipText("@twitterID");
		userID_textField.setBackground(Color.WHITE);
		userID_textField.setForeground(Color.BLACK);
		userID_textField.setFont(new Font("Arial", Font.BOLD, 20));
		userID_textField.setHorizontalAlignment(SwingConstants.RIGHT);
		userID_textField.setBounds(12, 5, 240, 30);
		userID_textField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(217,217,217)));
		search_panel.add(userID_textField);
		userID_textField.setColumns(10);
		
		JButton userSearch_Button = new JButton(searchIcon);
		userSearch_Button.setToolTipText("search");
		userSearch_Button.setForeground(Color.WHITE);
		userSearch_Button.setBackground(Color.WHITE);
		userSearch_Button.setBorderPainted(false);
		userSearch_Button.setBounds(254, 0, 40, 40);
		search_panel.add(userSearch_Button);

		
		//music player - album image, button
		JPanel music_panel = new JPanel();
		music_panel.setBorder(null);
		music_panel.setForeground(Color.BLACK);
		music_panel.setBackground(Color.WHITE);
		music_panel.setBounds(0, 40, 294, 166);
		getContentPane().add(music_panel);
		music_panel.setLayout(null);
		
		
		JButton prevButton = new JButton(prevIcon);
		prevButton.setForeground(Color.WHITE);
		prevButton.setBackground(Color.WHITE);
		prevButton.setBorderPainted(false);
		
		prevButton.setBounds(75, 120, 40, 40);
		music_panel.add(prevButton);
		
		JButton play_Button = new JButton(playIcon);
		play_Button.setToolTipText("play/puase button");
		play_Button.setBackground(Color.WHITE);
		play_Button.setForeground(Color.WHITE);
		play_Button.setBorderPainted(false);
		play_Button.setBounds(122, 116, 50, 50);
		music_panel.add(play_Button);
		
		JButton next_Button = new JButton(nextIcon);
		next_Button.setForeground(Color.WHITE);
		next_Button.setBackground(Color.WHITE);
		next_Button.setBorderPainted(false);
		next_Button.setBounds(180, 120, 40, 40);
		music_panel.add(next_Button);
		
		
		JLabel albumImage_Label = new JLabel(albumIcon, JLabel.CENTER);
		albumImage_Label.setForeground(Color.BLACK);
		albumImage_Label.setEnabled(false);
		albumImage_Label.setToolTipText("album image");
		albumImage_Label.setBackground(new Color(217,217,217));
		albumImage_Label.setBounds(101, 5, 90, 90);
		music_panel.add(albumImage_Label);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 205, 294, 30);
		getContentPane().add(panel);
		panel.setLayout(null);
        
		//music folder
		JLabel musicfolder_Label = new JLabel("ChooseMusicFolder...");
		musicfolder_Label.setToolTipText("music folder");
		musicfolder_Label.setBounds(0, 0, 262, 30);
		panel.add(musicfolder_Label);
		musicfolder_Label.setBackground(Color.WHITE);
		musicfolder_Label.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton folder_Button = new JButton(fileIcon);
		folder_Button.setToolTipText("chage folder");
		folder_Button.setBounds(264, 0, 30, 30);
		panel.add(folder_Button);
		folder_Button.setForeground(Color.WHITE);
		folder_Button.setBackground(Color.WHITE);
		folder_Button.setBorderPainted(false);
            
        //music table scroll
        JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 232, 298, 292);
		getContentPane().add(scrollPane);
		
        
        
        //music list 
		music_table = new JTable(model);
      	scrollPane.setViewportView(music_table);
      	music_table.setFont(new Font("굴림", Font.PLAIN, 12));
      	music_table.setBorder(new MatteBorder(1, 0, 1, 0, new Color(217, 217, 217)));
      	music_table.setBackground(Color.WHITE);
      	music_table.setRowHeight(25);
		  
		
		//slider design
		UIDefaults d = new UIDefaults();
		
		d.put("Slider:SliderThumb[Enabled].backgroundPainter", new Painter<JSlider>() {
            public void paint(Graphics2D g, JSlider c, int w, int h) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(1.5f));
                //g.setColor(Color.RED);
                //g.fillOval(1, 1, w-3, h-3);
                //g.setColor(Color.WHITE);
                //g.drawOval(1, 1, w-3, h-3);
            }
        });
		
		d.put("Slider:SliderTrack[Enabled].backgroundPainter", new Painter<JSlider>() {
	      public void paint(Graphics2D g, JSlider c, int w, int h) {
	        int arc         = 10;
	        int trackHeight = 4;
	        int trackWidth  = w - 2;
	        int fillTop     = 6;
	        int fillLeft    = 1;

	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g.setStroke(new BasicStroke(1.5f));
	        g.setColor(new Color(217,217,217));
	        g.fillRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);

	        int fillBottom = fillTop + trackHeight;
	        int fillRight  = xPositionForValue(
	            c.getValue(), c,
	            new Rectangle(fillLeft, fillTop, trackWidth, fillBottom - fillTop));

	        g.setColor(new Color(33,130,191));
	        g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);
	     
	        g.setColor(Color.WHITE);
	        g.drawRoundRect(fillLeft, fillTop, trackWidth, trackHeight, arc, arc);
	      }
	      
	      protected int xPositionForValue(int value, JSlider slider, Rectangle trackRect) {
	          int min = slider.getMinimum();
	          int max = slider.getMaximum();
	          int trackLength = trackRect.width;
	          double valueRange = (double) max - (double) min;
	          double pixelsPerValue = (double) trackLength / valueRange;
	          int trackLeft = trackRect.x;
	          int trackRight = trackRect.x + (trackRect.width - 1);
	          int xPosition;

	          xPosition = trackLeft;
	          xPosition += Math.round(pixelsPerValue * ((double) value - min));

	          xPosition = Math.max(trackLeft, xPosition);
	          xPosition = Math.min(trackRight, xPosition);

	          return xPosition;
	        }
	      
	      });
		
        try {
            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
              if ("Nimbus".equals(laf.getName())) {
                UIManager.setLookAndFeel(laf.getClassName());
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        
       //music slider
        music_slider = new JSlider(0, 100, 0);
		music_slider.setBorder(null);
		music_slider.setBounds(0, 100, 295, 15);
		music_panel.add(music_slider);
		music_slider.putClientProperty("Nimbus.Overrides", d);

		setVisible(true);
		
		
       /*************** ACTION LISTENER ****************/ 
        
		folder_Button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				try{			
					mp.setDirectory(mu,musicfolder_Label);
					mp.loadMusicFiles();
					updateList(mp.music_num, mplayer.clip, musicfolder_Label, play_Button, playIcon);	/**/
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
					ex.printStackTrace();
				}
			}
		});

		userSearch_Button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				try{
					if (mp.directory==null){
						JOptionPane.showMessageDialog(null, "select music folder first!!!...");
						return;
					}
					mp.setTwitId(userID_textField);
					mp.getCurrentMood();
					mp.loadMusicFiles();
					updateList(mp.music_num, mplayer.clip, musicfolder_Label, play_Button, playIcon); /**/
				}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
		
      //play + pause button	
      		play_Button.addActionListener(new ActionListener() {
      			@Override
      			public void actionPerformed(ActionEvent e) {
      				JButton play_Button = (JButton)e.getSource();	
                    mplayer.playMusic(play_Button, pauseIcon, playIcon);
      			}
      		});
        
      		prevButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mplayer.prevMusic(play_Button, pauseIcon);
                }
                
            });
        
      		next_Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mplayer.nextMusic(play_Button, pauseIcon);
                }
                
            });
      		
		
    		//music slier updater for user
		music_slider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
		          int value = music_slider.getValue() - mplayer.clip.getFramePosition()*100/mplayer.clip.getFrameLength();
		          if(value != 0) {
		        	  mplayer.clip.stop();
		        	  mplayer.clip.setFramePosition(music_slider.getValue()*mplayer.clip.getFrameLength()/100); //set clip
		        	  mplayer.lastPoint = mplayer.clip.getFramePosition();
		        	  mplayer.clip.start();
	        		}
		      	}

		      });
		
	}
	
	
	public void updateList(int music_num, Clip clip,JLabel musicfolder_Label,JButton play_Button, Icon playIcon){ /**/
		// reset music_table 
		if (music_table.getRowCount() < music_num) { // 수 맞춰서 늘리고 , 초기화
			model.setNumRows(music_num);	
		} else if (music_table.getRowCount() > music_num && music_table.getRowCount() > 10) { // 수 맞춰서 줄이고, 초기화
			model.setNumRows(music_num);	
		} else if (music_table.getRowCount() <= 10){ //10개로 줄이고, 초기화
			model.setNumRows(10);
		} 		
		
		for(int i=0 ; i<model.getRowCount(); i++) // music list reset
			model.setValueAt("", i, 0);
		 
		//get music list
		if(music_num > 0){
		   for(int i=0; i < music_num; i++){ //all file
				model.setValueAt(mp.musicNameList.get(i), i, 0); // jtable update
		   }
		}
		   
		//first music setting
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.flush();
			clip.close();
		}
		//clip.setMicrosecondPosition(0);	
		clip = null;
		mplayer.music_now = 0;
		mplayer.currentMusic = new File(mp.musicFileList.get(mplayer.music_now));
		play_Button.setIcon(playIcon);
	}
}
