import java.util.ArrayList;

import javax.security.auth.callback.ChoiceCallback;

import controlP5.*;
import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class MainApplet extends PApplet{
	private final static int width = 1000, height = 700;
	private ControlP5 cp5;
	Button buttonA,buttonB,buttonC,buttonD,btOne,btTwo,btCharacter,btEnvironment,btAbout,btBackToMenu,btAddCharacter;
	JSONObject data;
	JSONArray problems;
	JSONObject problem;
	String msg;
	String answer;
	Ani ani1,ani2;
	int mx=300;
	int score;
	private int flag=0;
	PFont font = createFont("標楷體",20);
	PImage character,character2,menu,characterPage,environmentPage,aboutPage;
	int locationx=300,locationy=50;
	ArrayList<PImage> monster;
	private int i=0,j=0;
	ControlFont f = new ControlFont(font,20);
	private String file = "src/resources/problems.json";
	public void setup(){
		
		monster=new ArrayList<PImage>();
		character = loadImage("src/resources/img/character_1.png");
		menu = loadImage("src/resources/main menu.png");
		characterPage = loadImage("src/resources/shop.png");
		environmentPage = loadImage("src/resources/setting.png");
		aboutPage = loadImage("src/resources/setting.png");
		
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
		
		btAddCharacter = cp5.addButton("btAddCharacter").setLabel("新增角色").setPosition(800, 500) .setSize(150, 50);
		cp5.getController("btAddCharacter")
	     .getCaptionLabel()
	     .setFont(f)
	     .toUpperCase(false)
	     .setSize(24)
	     ;
		btAddCharacter.hide();
		
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
		
		buttonA = cp5.addButton("buttonA").setLabel(problem.getString("choiceA")).setPosition(350, 420) .setSize(150, 50); 
		buttonB = cp5.addButton("buttonB").setLabel(problem.getString("choiceB")).setPosition(350, 480) .setSize(150, 50); 
		buttonC = cp5.addButton("buttonC").setLabel(problem.getString("choiceC")).setPosition(350, 540) .setSize(150, 50); 
		buttonD = cp5.addButton("buttonD").setLabel(problem.getString("choiceD")).setPosition(350, 600) .setSize(150, 50); 
	
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
		btAddCharacter.hide();
	}
	public void btAddCharacter(){
		BlockImage puzzle = new BlockImage();
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
		btAddCharacter.show();
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
			image(monster.get(0),locationx,locationy,300,300);
			
			line(0, height/2, width, height/2);
			msg = problem.getString("question");
			text(msg,20, 400);
			text(score,width-50,height/2+20);
		}
		
		else if(flag==2){ //2-P mode
			
		}else if(flag==3){ //Character mode
			image(characterPage,0,0,width,height);
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
			
		}
		else
			System.out.println("答錯");
		
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
