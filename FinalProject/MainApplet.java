import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;

import javax.security.auth.callback.ChoiceCallback;
import javax.sound.sampled.Clip;

import controlP5.*;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class MainApplet extends PApplet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int width = 1000, height = 700;
	private ControlP5 cp5;
	String []rankname=new String[5];
	int []rankScore={0,0,0,0,0};
	int x=0;
	
	Button buttonA,buttonB,buttonC,buttonD,btOne,btTwo,btCharacter,btEnvironment,btAbout,btBackToMenu,btAddCharacter1,btAddCharacter2,btAddCharacter3,btAddCharacter4,btAddCharacter5;
	Button btmusic;
	JSONObject data;
	JSONArray problems,unknown;
	JSONObject problem = new JSONObject();
	String msg;
	String answer;
	Ani ani1,ani2;
	int mx=300;
	TimerTask task;
	int score,total_count;
	private int flag=0,chflag=0,preflag=0,flagflag=0;
	Timer timer=new Timer();
	Timer timer1=new Timer();
	Timer timer2=new Timer();
	int btx1,bty1,btx2,bty2,btx3,bty3,btx4,bty4;
	PFont font = createFont("標楷體",20);
	PImage character,character2,menu,characterPage,environmentPage,aboutPage,ch1,ch2,ch3,ch4,ch5,nowch;
	int locationx=300,locationy=50;
	ArrayList<PImage> monster;
	private int i=0,j=0,k=0;
	Random ran;
	private String username = new String();
	ControlFont f = new ControlFont(font,20);
	boolean unknown_flag = false;
	private String file = "src/resources/problems.json";
	Music music = new Music();
	PImage musicanimal1,musicanimal2,gamebackground,rankbackground;
	int musicanimal=0,menuani=0;
	int musicanimalx=300,menux=600;
	int nowCharacter =1; int chgamerecord=1; 
	int chlocationx=40;
	int second = 10;
	int count = 0;
	BlockImage puzzle;
	boolean puzzlegame=false;
	public void setup(){
		second = 10;
		ran=new Random();
		monster=new ArrayList<PImage>();
		character = loadImage("src/resources/img/character_1.png");
		menu = loadImage("src/resources/main menu.png");
		rankbackground = loadImage("src/resources/rank.png");
		characterPage = loadImage("src/resources/shop.png");
		environmentPage = loadImage("src/resources/setting.png");
		aboutPage = loadImage("src/resources/about.png");
		ch1 = loadImage("src/resources/img/character_1.png");
		ch2 = loadImage("src/resources/img/character_2.png");
		ch3 = loadImage("src/resources/img/character_3.png");
		ch4 = loadImage("src/resources/img/character_4.png");
		ch5 = loadImage("src/resources/img/character_5.png");
		nowch = ch1;
		gamebackground = loadImage("src/resources/gamebackground.jpg");
		musicanimal1 = loadImage("src/resources/Bing_bong_1.png");
		musicanimal2 = loadImage("src/resources/Bing_bong_2.png");
		for(j=1;j<=5;j++)
		{
			character2=loadImage("src/resources/img/monster_"+j+".png");
			monster.add(character2);
		}
		Ani.init(this);
		size(width,height);
		smooth();
		
		cp5 = new ControlP5(this);

		score = 0;
		loadData();
		
		btOne = cp5.addButton("btOne").setLabel("單人遊戲").setPosition(width/2, 220) .setSize(450, 70); 
		btTwo = cp5.addButton("btTwo").setLabel("遊戲排名").setPosition(width/2, 300) .setSize(450, 70); 
		btCharacter = cp5.addButton("btCharacter").setLabel("角色商店").setPosition(width/2, 380) .setSize(450, 70); 
		btEnvironment = cp5.addButton("btEnvironment").setLabel("環境設定").setPosition(width/2, 460) .setSize(450, 70); 
		btAbout = cp5.addButton("btAbout").setLabel("關於遊戲").setPosition(width/2, 540) .setSize(450, 70); 
		btmusic = cp5.addButton("btmusic").setLabel("音樂暫停").setPosition(330, 600) .setSize(400, 50);
		cp5.getController("btmusic")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btmusic.hide();
		
		btBackToMenu = cp5.addButton("btBackToMenu").setLabel("返回").setPosition(800, 600) .setSize(150, 50);
		cp5.getController("btBackToMenu")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btBackToMenu.hide();
		
		btAddCharacter1 = cp5.addButton("btAddCharacter1").setLabel("更改角色 1").setPosition(360, 360) .setSize(150, 50);
		cp5.getController("btAddCharacter1")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter1.hide();
		
		btAddCharacter2 = cp5.addButton("btAddCharacter2").setLabel("更改角色 2").setPosition(580, 360) .setSize(150, 50);
		cp5.getController("btAddCharacter2")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter2.hide();
		
		btAddCharacter3 = cp5.addButton("btAddCharacter3").setLabel("更改角色 3").setPosition(800, 360) .setSize(150, 50);
		cp5.getController("btAddCharacter3")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter3.hide();
		
		btAddCharacter4 = cp5.addButton("btAddCharacter4").setLabel("更改角色 4").setPosition(360, 600) .setSize(150, 50);
		cp5.getController("btAddCharacter4")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter4.hide();
		
		btAddCharacter5 = cp5.addButton("btAddCharacter5").setLabel("更改角色 5").setPosition(580, 600) .setSize(150, 50);
		cp5.getController("btAddCharacter5")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter5.hide();
		
		cp5.getController("btOne")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		
		cp5.getController("btTwo")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		cp5.getController("btCharacter")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		cp5.getController("btEnvironment")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		cp5.getController("btAbout")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		
		buttonA = cp5.addButton("buttonA").setLabel(problem.getString("choiceA")).setPosition(btx1, 420) .setSize(350, 50); 
		buttonB = cp5.addButton("buttonB").setLabel(problem.getString("choiceB")).setPosition(btx1, 480) .setSize(350, 50); 
		buttonC = cp5.addButton("buttonC").setLabel(problem.getString("choiceC")).setPosition(btx1, 540) .setSize(350, 50); 
		buttonD = cp5.addButton("buttonD").setLabel(problem.getString("choiceD")).setPosition(btx1, 600) .setSize(350, 50); 
		cp5.getController("buttonA")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		
		cp5.getController("buttonB")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		
		cp5.getController("buttonC")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		
		cp5.getController("buttonD")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;


		buttonA.hide();
		buttonB.hide();
		buttonC.hide();
		buttonD.hide();
	}
	public void addBackButton(){
		btOne.hide();
		btTwo.hide();
		btCharacter.hide();
		btEnvironment.hide();
		btAbout.hide();
		btBackToMenu.show();
		

	}
	public void chButton(){ //get in 1-P
		btOne.hide();
		btTwo.hide();
		btCharacter.hide();
		btEnvironment.hide();
		btAbout.hide();
		btx1=350;
		
		buttonA.show();
		buttonB.show();
		buttonC.show();
		buttonD.show();
		btBackToMenu.show();
	
	}
	public void btBackToMenu(){
		flag=0;
		btOne.show();
		btTwo.show();
		btCharacter.show();
		btEnvironment.show();
		btAbout.show();
		btmusic.hide();
		btBackToMenu.hide();
		btAddCharacter1.hide();
		btAddCharacter2.hide();
		btAddCharacter3.hide();
		btAddCharacter4.hide();
		btAddCharacter5.hide();

		buttonA.hide();
		buttonB.hide();
		buttonC.hide();
		buttonD.hide();
	}
	public void btAddCharacter1(){
		//BlockImage puzzle = new BlockImage("1");
		puzzle = new BlockImage("1");
		chgamerecord=1;
		puzzlegame=true;
	}
	public void btAddCharacter2(){
		//BlockImage puzzle = new BlockImage("2");
		puzzle = new BlockImage("2");
		chgamerecord=2;
		puzzlegame=true;
	}
	public void btAddCharacter3(){
		//BlockImage puzzle = new BlockImage("3");
		puzzle = new BlockImage("3");
		chgamerecord=3;
		puzzlegame=true;
	}
	public void btAddCharacter4(){
		//BlockImage puzzle = new BlockImage("4");
		puzzle = new BlockImage("4");
		chgamerecord=4;
		puzzlegame=true;
	}
	public void btAddCharacter5(){
		//BlockImage puzzle = new BlockImage("5");
		puzzle = new BlockImage("5");
		chgamerecord=5;
		puzzlegame=true;
	}
	
	public void btOne(){ //1-P
		second = 10;
		timer1.schedule(new TimerTask() {
			public void run() {
				btBackToMenu();
			}
		}, 11000);
		
		flag=1;
		chButton();
		btUpdate();	
		
		cp5.update();
		
		//draw();
	}	
	public void btTwo(){ //2-P
		flag=2;
		x=0;
		FileReader f_in = null;
		//read file
		try {
			f_in = new FileReader("src/resources/rank.txt");		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//x=0;
		//use bufferedreader to put information in HashMap (for known and unknown words)
		 try {  
			 BufferedReader b_in =new BufferedReader(f_in);

			 String line = new String();
		     while ((line = b_in.readLine()) != null) {
		    	String[] strs = new String[2];
		    	strs = line.trim().split("\\s+");
		    	if(x<5){
		    		rankScore[x]=Integer.valueOf(strs[1]);
		    		rankname[x]=strs[0];
		    		x++;
		    	}else{
		    		int min=0;
		    		for(int y=0;y<5;y++){
		    			if(rankScore[min]>rankScore[y]){
		    				min=y;
		    			}
		    		}
		    		rankScore[min]=Integer.valueOf(strs[1]);
		    		rankname [min]=strs[0];
		    	}
		     }
		     String tmp=new String();
		     int tmpx=0;
		     for(int m=0;m<x;m++){
		    	 for(int n=m;n<x;n++){
		    		 if(rankScore[m]<rankScore[n]){
		    			 tmpx=rankScore[m];
		    			 rankScore[m]=rankScore[n];
		    			 rankScore[n]=tmpx;
		    			 
		    			 tmp=rankname[m];
		    			 rankname[m]=rankname[n];
		    			 rankname[n]=tmp;
		    		 }
		    	 }
		     }
		     
		     for(int y=0;y<x;y++){
		    	 System.out.println((y+1)+". "+rankname [y]+" "+rankScore[y]);
		     }
		 }
		 catch (IOException e) {	
			System.out.println(e);
		 }
		
		
		
		addBackButton();
		cp5.update();
		//draw();
	}
	public void btCharacter(){ //Character
		flag=3;
		addBackButton();
		btAddCharacter1.show();
		btAddCharacter2.show();
		btAddCharacter3.show();
		btAddCharacter4.show();
		btAddCharacter5.show();

		cp5.update();
		
		//draw();
	}
	public void btEnvironment(){ //Environment
		flag=4;
		btmusic.show();
		addBackButton();
		cp5.update();
		
		//draw();
	}
	public void btAbout(){ //About
		flag=5;
		addBackButton();
		cp5.update();
		
		//draw();
	}
	public void btmusic(){
		if(music.sw==false)music.sw=true;
			else music.sw=false;
			//===================
			if(music.sw==true){
				music.clip.start();
				music.clip.loop(Clip.LOOP_CONTINUOUSLY);
				btmusic.setCaptionLabel("音樂暫停");
				//.setText("click to pause");
			}
			else {
				music.clip.stop();
				btmusic.setCaptionLabel("音樂開始");
				//b.setText("click to start");
			}
	}
	public void buttonA(){ //choose A
		checkAnswer("A");
		if(score!=0 && score%3==0)
		{
			unknown_flag = true;
			k++;
			problem = unknown.getJSONObject(k);
		}
		else
		{
			i++;
			problem = problems.getJSONObject(i);
		}
		
		btUpdate();
		cp5.update();
		
		
		//draw();
	}
	public void buttonB(){ //choose B
		checkAnswer("B");
		if(score!=0 && score%3==0)
		{
			unknown_flag = true;
			k++;
			problem = unknown.getJSONObject(k);
		}
		else
		{
			i++;
			problem = problems.getJSONObject(i);
		}
		btUpdate();
		cp5.update();
		
	}
	public void buttonC(){ //choose C
		checkAnswer("C");
		if(score!=0 && score%3==0)
		{
			unknown_flag = true;
			k++;
			problem = unknown.getJSONObject(k);
		}
		else
		{
			i++;
			problem = problems.getJSONObject(i);
		}
		btUpdate();
		cp5.update();
		
	}
	public void buttonD(){ //choose D
		checkAnswer("D");
		if(score!=0 && score%3==0)
		{
			unknown_flag = true;
			k++;
			problem = unknown.getJSONObject(k);
		}
		else
		{
			i++;
			problem = problems.getJSONObject(i);
		}
		btUpdate();
		cp5.update();
		
	}
	public void draw(){
		background(255);
		
		if(flag==0){ //menu mode
			image(menu,0,0,width,height);
			if(true){	
				menuani++;
				
				if(menuani<=30){
					menux+=1;					
				}else{
					menux-=1;
				}
			}
			
			image(ch1,10,menux,180,200);
			image(ch2,210,menux,180,200);
			image(ch3,410,menux,180,200);
			image(ch4,610,menux,180,200);
			image(ch5,810,menux,180,200);
			if(menuani==60) menuani=0;
			fill(200);
			rect(490, 210, 470, 410,10);
		}
		
		else if (flag==1){ //1-P mode
			image(gamebackground,0,0,width,height);
			
			textFont(font,20);
			fill(0);
			text(second,width-50,20);
			count++;
			System.out.println(count);
			if(count==18)
			{
				count = 0;
				second--;
			}
			image(monster.get(preflag),locationx,locationy,300,300);
			
			line(0, height/2, width, height/2);
			msg = problem.getString("question");
			text(msg,20, 400);
			text(score,width-50,height/2+20);
			buttonA.setPosition(btx1, 420);
			buttonB.setPosition(btx1, 480);
			buttonC.setPosition(btx1,540);
			buttonD.setPosition(btx1,600);
			//updatebtn();
			
			image(nowch,chlocationx,200,140,140);
			/*
			if(nowCharacter==1){
				image(ch1,chlocationx,420,300,300);
			}else if(nowCharacter==2){
				image(ch2,chlocationx,420,300,280);
			}else if(nowCharacter==3){
				image(ch3,chlocationx,420,300,280);
			}else if(nowCharacter==4){
				image(ch4,chlocationx,420,300,280);
			}else{
				image(ch5,chlocationx,420,300,300);
			}*/
		}
		
		else if(flag==2){ //2-P mode
			image(rankbackground,0,0,width,height);
			
			fill(255);
			
			for(int y=0;y<x;y++){
				textSize(32);
				text("第"+(y+1)+"名. "+rankname[y]+" "+rankScore[y],350, 250+(y*40));
			}
			
		}else if(flag==3){ //Character mode
			
			if(puzzlegame==true){
				
				
				if(chgamerecord==1 && puzzle.win==true){
					nowCharacter = 1;
					nowch=ch1;
					puzzle.win=false;
				}else if(chgamerecord==2 && puzzle.win==true){
					nowCharacter = 2;
					nowch=ch2;
					puzzle.win=false;
					//System.out.print("11");
				}else if(chgamerecord==3 && puzzle.win==true){
					nowCharacter = 3;
					nowch=ch3;
					puzzle.win=false;
				}else if(chgamerecord==4 && puzzle.win==true){
					nowCharacter = 4;
					nowch=ch4;
					puzzle.win=false;
				}else if(chgamerecord==5 && puzzle.win==true){
					nowCharacter = 5;
					nowch=ch5;
					puzzle.win=false;
				}		
			}
			
			
			image(characterPage,0,0,width,height);
			image(ch1,360,200,150,150);
			image(ch2,590,200,140,150);
			image(ch3,810,200,140,150);
			image(ch4,360,450,140,150);
			image(ch5,580,450,150,150);
			
			fill(200);
			rect(55, 210, 210, 260,10);
			fill(10);
			text("目前角色",100, 250);
			
			image(nowch,65,270,200,200);
			
			
		}else if(flag==4){ //Environment mode
			image(environmentPage,0,0,width,height);
			if(music.sw==true){	
				musicanimal++;
				
				if(musicanimal<=30){
					musicanimalx+=5;					
				}else{
					musicanimalx-=5;
				}
			}
			
			if(musicanimal<=30){
				image(musicanimal1,musicanimalx,200,400,400);
			}else{
				image(musicanimal2,musicanimalx,200,400,400);
			}
			
			if(musicanimal==60) musicanimal=0;
			
		}else if(flag==5){ //About mode
			image(aboutPage,0,0,width,height);

		}
	}
	public void setuser(String username){
		this.username=username;
		System.out.println(this.username);
	} 	
	public void checkAnswer(String ans){
		flagflag=0;
		if(unknown_flag)
		{
			unknown_flag = false;
			total_count = problem.getInt("total_count");
			if(total_count<5)
			{
				
				System.out.println("答對");
				for(int i=0;i<=5;i++)
				{
				Ani.to(this, (float)1, "locationx",400,Ani.ELASTIC_OUT);
			
				Ani.from(this,(float)1,"locationx",400,Ani.ELASTIC_OUT);
				//System.out.println("a");
				//Ani.to(this,(float)0.25,1,"locationx",290,Ani.LINEAR);
				}
				score++;
				flagflag=1;
				timer.schedule(new TimerTask() {
					public void run() {
						
						if(flagflag==1){
							chflag=ran.nextInt(5);
							preflag=chflag;
							
							
						}
					}
			}, 1002);
				timer.schedule(new TimerTask() {
					public void run() {
						
						flagflag=0;
							
						}
					
			}, 1002);
//				preflag=chflag;
			}
			else
			{
				answer = maxchoice();
				System.out.println(answer);
				if(ans.equals(answer))
				{
					System.out.println("答對");
					for(int i=0;i<=5;i++)
					{
					Ani.to(this, (float)1, "locationx",400,Ani.ELASTIC_OUT);
				
					Ani.from(this,(float)1,"locationx",400,Ani.ELASTIC_OUT);
					//System.out.println("a");
					//Ani.to(this,(float)0.25,1,"locationx",290,Ani.LINEAR);
					}
					score++;
					flagflag=1;
					timer.schedule(new TimerTask() {
						public void run() {
							
							if(flagflag==1){
								chflag=ran.nextInt(5);
								preflag=chflag;
								
								
							}
						}
				}, 1002);
					timer.schedule(new TimerTask() {
						public void run() {
							
							flagflag=0;
								
							}
						
				}, 1002);
				}
				else
				{	
					System.out.println("答錯");
					Ani.to(this, (float)1, "btx1",400,Ani.ELASTIC_OUT);
					Ani.from(this,(float)1,"btx1",400,Ani.ELASTIC_OUT);
				}
			}
			System.out.println("before: "+problem.getInt("total_count"));
			total_count++;
			problem.setInt("total_count",total_count);
			System.out.println("after: "+problem.getInt("total_count"));
			if(ans.equals("A"))
			{
				int count = problem.getInt("countA");
				problem.setInt("countA",++count);
			}
			else if(ans.equals("B"))
			{
				int count = problem.getInt("countB");
				problem.setInt("countB",++count);
			}
			else if(ans.equals("C"))
			{
				int count = problem.getInt("countC");
				problem.setInt("countC",++count);
			}
			else if(ans.equals("D"))
			{
				int count = problem.getInt("countD");
				problem.setInt("countD",++count);
			}
		
			//this.saveJSONObject(data, file);
		}
		else
		{
			answer = problem.getString("answer");
			System.out.println(answer);
			if(ans.equals(answer))
			{
				System.out.println("答對");
				for(int i=0;i<=5;i++)
				{
				Ani.to(this, (float)1, "locationx",400,Ani.ELASTIC_OUT);
			
				Ani.from(this,(float)1,"locationx",400,Ani.ELASTIC_OUT);
				//System.out.println("a");
				//Ani.to(this,(float)0.25,1,"locationx",290,Ani.LINEAR);
				}
				score++;
	
				flagflag=1;
				timer.schedule(new TimerTask() {
					public void run() {
						
						if(flagflag==1){
							chflag=ran.nextInt(5);
							preflag=chflag;
							
							
						}
					}
			}, 1002);
				timer.schedule(new TimerTask() {
					public void run() {
						
						flagflag=0;
							
						}
					
			}, 1002);
			}
			else
			{	
				System.out.println("答錯");
				Ani.to(this, (float)1, "btx1",400,Ani.ELASTIC_OUT);
				Ani.from(this,(float)1,"btx1",400,Ani.ELASTIC_OUT);
			}
		}
		
	}
	private String maxchoice()
	{
		int[] countchoice;
		countchoice = new int[4];
		countchoice[0] = problem.getInt("countA");
		countchoice[1] = problem.getInt("countB");
		countchoice[2] = problem.getInt("countC");
		countchoice[3] = problem.getInt("countD");
		Arrays.sort(countchoice);
		for(int index=0;index<4;index++)
			System.out.println(countchoice[index]+" ");
		if(countchoice[3]==problem.getInt("countA"))
			return "A";
		else if(countchoice[3]==problem.getInt("countB"))
			return "B";
		else if(countchoice[3]==problem.getInt("countC"))
			return "C";
		else
			return "D";
		
	}
	private void btUpdate(){ //update button index
			buttonA.setLabel(problem.getString("choiceA"));
			buttonB.setLabel(problem.getString("choiceB"));
			buttonC.setLabel(problem.getString("choiceC"));
			buttonD.setLabel(problem.getString("choiceD"));
		
	}
	private void loadData(){
		data = loadJSONObject(file);
		problems = data.getJSONArray("problems");
		unknown = data.getJSONArray("unknown");
		problem = problems.getJSONObject(0);
		msg = problem.getString("question");
		System.out.println("Number of nodes: " + problems.size());
	}
}
