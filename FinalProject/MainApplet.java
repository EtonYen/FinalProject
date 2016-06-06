import java.util.ArrayList;
import java.util.Random;

import javax.security.auth.callback.ChoiceCallback;

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
	Button buttonA,buttonB,buttonC,buttonD,btOne,btTwo,btCharacter,btEnvironment,btAbout,btBackToMenu,btAddCharacter1,btAddCharacter2,btAddCharacter3,btAddCharacter4,btAddCharacter5;
	JSONObject data;
	JSONArray problems;
	JSONObject problem;
	String msg;
	String answer;
	Ani ani1,ani2;
	int mx=300;
	int score;
	private int flag=0,chflag=0;
	int btx1,bty1,btx2,bty2,btx3,bty3,btx4,bty4;
	PFont font = createFont("標楷體",20);
	PImage character,character2,menu,characterPage,environmentPage,aboutPage,ch1,ch2,ch3,ch4,ch5;
	int locationx=300,locationy=50;
	ArrayList<PImage> monster;
	private int i=0,j=0;
	Random ran;
	ControlFont f = new ControlFont(font,20);
	private String file = "src/resources/problems.json";
	public void setup(){
		ran=new Random();

		monster=new ArrayList<PImage>();
		character = loadImage("src/resources/img/character_1.png");
		menu = loadImage("src/resources/main menu.png");
		characterPage = loadImage("src/resources/shop.png");
		environmentPage = loadImage("src/resources/setting.png");
		aboutPage = loadImage("src/resources/about.png");
		ch1 = loadImage("src/resources/img/character_1.png");
		ch2 = loadImage("src/resources/img/character_2.png");
		ch3 = loadImage("src/resources/img/character_3.png");
		ch4 = loadImage("src/resources/img/character_4.png");
		ch5 = loadImage("src/resources/img/character_5.png");

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
		problem = problems.getJSONObject(0);
		msg = problem.getString("question");
		btOne = cp5.addButton("btOne").setLabel("單人遊戲").setPosition(width/2, 360) .setSize(150, 50); 
		btTwo = cp5.addButton("btTwo").setLabel("連線對戰").setPosition(width/2, 420) .setSize(150, 50); 
		btCharacter = cp5.addButton("btCharacter").setLabel("角色商店").setPosition(width/2, 480) .setSize(150, 50); 
		btEnvironment = cp5.addButton("btEnvironment").setLabel("環境設定").setPosition(width/2, 540) .setSize(150, 50); 
		btAbout = cp5.addButton("btAbout").setLabel("關於遊戲").setPosition(width/2, 600) .setSize(150, 50); 
		
		btBackToMenu = cp5.addButton("btBackToMenu").setLabel("返回").setPosition(800, 600) .setSize(150, 50);
		cp5.getController("btBackToMenu")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btBackToMenu.hide();
		
		btAddCharacter1 = cp5.addButton("btAddCharacter1").setLabel("新增角色 1").setPosition(360, 360) .setSize(150, 50);
		cp5.getController("btAddCharacter1")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter1.hide();
		
		btAddCharacter2 = cp5.addButton("btAddCharacter2").setLabel("新增角色 2").setPosition(580, 360) .setSize(150, 50);
		cp5.getController("btAddCharacter2")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter2.hide();
		
		btAddCharacter3 = cp5.addButton("btAddCharacter3").setLabel("新增角色 3").setPosition(800, 360) .setSize(150, 50);
		cp5.getController("btAddCharacter3")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter3.hide();
		
		btAddCharacter4 = cp5.addButton("btAddCharacter4").setLabel("新增角色 4").setPosition(360, 600) .setSize(150, 50);
		cp5.getController("btAddCharacter4")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter4.hide();
		
		btAddCharacter5 = cp5.addButton("btAddCharacter5").setLabel("新增角色 5").setPosition(580, 600) .setSize(150, 50);
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
	
	}
	public void btBackToMenu(){
		flag=0;
		btOne.show();
		btTwo.show();
		btCharacter.show();
		btEnvironment.show();
		btAbout.show();
		
		btBackToMenu.hide();
		btAddCharacter1.hide();
		btAddCharacter2.hide();
		btAddCharacter3.hide();
		btAddCharacter4.hide();
		btAddCharacter5.hide();

	}
	public void btAddCharacter1(){
		BlockImage puzzle = new BlockImage("1");
	}
	public void btAddCharacter2(){
		BlockImage puzzle = new BlockImage("2");
	}
	public void btAddCharacter3(){
		BlockImage puzzle = new BlockImage("3");
	}
	public void btAddCharacter4(){
		BlockImage puzzle = new BlockImage("4");
	}
	public void btAddCharacter5(){
		BlockImage puzzle = new BlockImage("5");
	}
	
	public void btOne(){ //1-P
		flag=1;
		chButton();
		btUpdate();
		cp5.update();
		
		//draw();
	}
	
	public void btTwo(){ //2-P
		flag=2;
		
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
	
	public void buttonA(){ //choose A
		checkAnswer("A");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
		
		//draw();
	}
	public void buttonB(){ //choose B
		checkAnswer("B");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
	}
	public void buttonC(){ //choose C
		checkAnswer("C");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
	}
	public void buttonD(){ //choose D
		checkAnswer("D");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
	}
	public void draw(){
		background(255);
		if(flag==0){ //menu mode
			image(menu,0,0,width,height);
		}
		
		else if (flag==1){ //1-P mode
			textFont(font,20);
			fill(0);
			
			image(monster.get(chflag),locationx,locationy,300,300);
			
			line(0, height/2, width, height/2);
			msg = problem.getString("question");
			text(msg,20, 400);
			text(score,width-50,height/2+20);
			buttonA.setPosition(btx1, 420);
			buttonB.setPosition(btx1, 480);
			buttonC.setPosition(btx1,540);
			buttonD.setPosition(btx1,600);
			//updatebtn();
		}
		
		else if(flag==2){ //2-P mode
			
		}else if(flag==3){ //Character mode
			image(characterPage,0,0,width,height);
			image(ch1,360,200,150,150);
			image(ch2,590,200,140,150);
			image(ch3,810,200,140,150);
			image(ch4,360,450,140,150);
			image(ch5,580,450,150,150);

		}else if(flag==4){ //Environment mode
			image(environmentPage,0,0,width,height);
			
		}else if(flag==5){ //About mode
			image(aboutPage,0,0,width,height);

		}
	}

	public void checkAnswer(String ans){
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
			chflag=ran.nextInt(4);
		}
		else
		{	
			System.out.println("答錯");
			Ani.to(this, (float)1, "btx1",400,Ani.ELASTIC_OUT);
			Ani.from(this,(float)1,"btx1",400,Ani.ELASTIC_OUT);
		}
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
		System.out.println("Number of nodes: " + problems.size());
	}
}
