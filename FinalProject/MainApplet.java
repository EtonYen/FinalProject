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
	Button buttonA,buttonB,buttonC,buttonD,btOne,btTwo,btCharacter,btEnvironment,btAbout;
	JSONObject data;
	JSONArray problems;
	JSONObject problem;
	String msg;
	String answer;
	Ani ani;
	int score;
	int flag=0;
	PFont font = createFont("標楷體",20);
	PImage character,character2,menu;
	int locationx=300,locationy=50;
	ArrayList<PImage> monster;
	private int i=0,j=0;
	ControlFont f = new ControlFont(font,20);
	private String file = "src/resources/problems.json";
	public void setup(){
		
		monster=new ArrayList<PImage>();
		character = loadImage("src/resources/img/character_1.png");
		menu = loadImage("src/resources/menu.png");
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
	
	public void chButton(){
		btOne.remove();
		btTwo.remove();
		btCharacter.remove();
		btEnvironment.remove();
		btAbout.remove();
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
	
	public void btOne(){
		flag=1;
		chButton();
		btUpdate();
		cp5.update();
		
		//draw();
	}
	
	public void buttonA(){
		checkAnswer("A");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		Ani.to(this, (float)0.5, "locationx",350,Ani.LINEAR);
		
		//draw();
	}
	public void buttonB(){
		checkAnswer("B");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
	}
	public void buttonC(){
		checkAnswer("C");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
	}
	public void buttonD(){
		checkAnswer("D");
		i++;
		problem = problems.getJSONObject(i);
		btUpdate();
		cp5.update();
		
	}
	public void draw(){
		background(255);
		if(flag==0){
			image(menu,0,0,width,height);
		}
		
		else if (flag==1){
		textFont(font,20);
		fill(0);
		image(monster.get(0),locationx,locationy,300,300);
		
		line(0, height/2, width, height/2);
		msg = problem.getString("question");
		text(msg,20, 400);
		text(score,width-50,height/2+20);
		}
	
	}
	public void checkAnswer(String ans){
		answer = problem.getString("answer");
		System.out.println(answer);
		if(ans.equals(answer))
		{
			System.out.println("答對");
			score++;
			
		}
		else
			System.out.println("答錯");
		
	}
	private void btUpdate(){
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
