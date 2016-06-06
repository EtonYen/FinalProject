import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class BlockImage2 extends Canvas {
        
        private static final long serialVersionUID = 1L;
        private Image _img,_img2,backimage;
        private Graphics bg;      
        private Graphics later = null;
        private int blocks[];
        private boolean isEvent;
        private MediaTracker mt;
        private int _width,_height;
        private int _RS,_CS;
        private Image screen = null;
        private int _objWidth,_objHeight;
        private int _COUNT;
        
        //===================================
        private boolean odd=false;//initial 0
        private int temp = 0;
        private int k=0,l=0,m=0,n=0;
        static private boolean win = false;
        //===================================

        static private Frame frm;
        public BlockImage2(Image bImage, Image overImage, int cs, int rs) {
                init(bImage, overImage, cs, rs);
        }

        /**
         * ���������
         * 
         * @param bImage
         * @param overImage
         * @param cs
         * @param rs
         */
        public void init(Image bImage, Image overImage, int cs, int rs) {
                // ��
                _CS = cs;
                // 銵
                _RS = rs;
                // ����������
                _img = bImage;

                // �敺祕����祝��
                _width = _img.getWidth(null);
                // �敺祕�������
                _height = _img.getHeight(null);
                // �敺憛��祝��
                _objWidth = _width / _CS;
                // �敺憛�����
                _objHeight = _height / _RS;

                // �蝔��雿輻back image銝�憛�耦����楨瘝�����隞亙祕��������=��耦擃�+憿������
                backimage = new BufferedImage(_width, _height + _objHeight, 1);
                // �敺����耦
                later = backimage.getGraphics();
                // ��撱箔�憛�������蛹���楨摮��
                screen = new BufferedImage(_width, _height, 1);
                // �敺楨摮��耦
                bg = screen.getGraphics();
                // �敺����蜇���蝯��
                _COUNT = _CS * _RS;
                blocks = new int[_COUNT];
                // ����蛹������
                isEvent = false;
                // ��������＊蝷箏���
                _img2 = overImage;
                // ���������
                for (int i = 0; i < _COUNT; i++) {
                        blocks[i] = i;
                }
                // 頛MediaTracker嚗隞亥�馱��������
                mt = new MediaTracker(this);
                // ���◤頝馱������
                mt.addImage(_img, 0);
                mt.addImage(_img2,0);
                // ��郊頛��
                try {
                        mt.waitForID(0);
                } catch (InterruptedException interruptedexception) {
                        return;
                }
                // �璈������摰嫘��
                rndPannel();

        }

        /**
         * ��鼓蝒�����
         */
        public void paint(Graphics g) {
                // 瑼Ｘ������
                if (mt.checkID(0)) {
                        // ��鼓摨惜����
                        bg.drawImage(backimage, 0, 0, null);
                        // ����閫貊摰��辣��
                        if (!isEvent) {
                                // 閮剔蔭�����
                                bg.setColor(Color.black);
                                // 敺芰蝜芸撠����蝺拙�葉��
                                for (int i = 0; i < _CS; i++) {
                                        for (int j = 0; j < _RS; j++)
                                                bg.drawRect(i * _objWidth, j * _objHeight, _objWidth,_objHeight);
                                }

                        }
                        else{
                        	System.out.println("win!!!!!!!");
                        	//frm.dispose();
                        }
                }
                // ��蝜芸������敺芷＊蝷箏���鼓�銝�甈∠�������甈⊥�抒���蝜芸�蝒���
                // 蝪∪靘牧嚗�停������楨摮��撘���������蝺拙��摰�������������辣������
                g.drawImage(screen, 0, 0, this);
                g.dispose();
        }

        /**
         * 霈�����
         */
        public void update(Graphics g) {
                paint(g);
        }

        /**
         * 曌����辣��
         */
        public boolean mouseDown(Event event, int i, int j) {
        	
        	if (isEvent)return true;//complete and don't do things
        	
        	//=============================================
    		//add the add to look its even step or odd step
    		if(odd == true) odd = false ;
    		else odd = true ;
    		//=============================================
    		//do things in different condition
    		System.out.println("odd->"+odd);
    		
    		if(odd==true){
    			//first click
    			k = i / _objWidth;
                l = j / _objHeight;
    		}
    		else{
    			//second click
    			m = i / _objWidth;
                n = j / _objHeight;
                //========================
                //change position
                copy(m, n, 0, _RS);
                copy(k, l, m, n);
                copy(0, _RS, k, l);
                
                //copy(m, n, k, l);
                temp = blocks[l * _CS + k];
                blocks[l * _CS + k] = blocks[n * _CS + m];
                blocks[n * _CS + m] = temp;
    		}
            //===============================
    		//check is win or not
            int j1;
            for (j1 = 0; j1 < _COUNT; j1++) {
                    if (blocks[j1] != j1) {
                            break;
                    }
            }
            if (j1 == _COUNT){
            	win = true;
            	isEvent = true;           	
            }
            repaint();
            return true;
        }

        public boolean mouseUp(Event event, int i, int j) {
                return true;
        }
        public boolean mouseDrag(Event event, int i, int j) {        		
                return true;
        }
        
        //copy is to redraw the puzzle -> change position
        void copy(int i, int j, int k, int l) {
                later.copyArea(i * _objWidth, j * _objHeight, _objWidth, _objHeight,
                                (k - i) * _objWidth, (l - j) * _objHeight);
        }

        public boolean isEvent() {
        		//if you are win
                return isEvent;
        }
        public void setEvent(boolean isEvent) {
                this.isEvent = isEvent;
        }

        //random create the puzzle
        void rndPannel() {
                later.drawImage(_img, 0, 0, this);
                for (int i = 0; i < (_COUNT * _CS); i++) {
                        int j = (int) ((double) _CS * Math.random());
                        int k = (int) ((double) _RS * Math.random());
                        int l = (int) ((double) _CS * Math.random());
                        int i1 = (int) ((double) _RS * Math.random());
                        copy(j, k, 0, _RS);
                        copy(l, i1, j, k);
                        copy(0, _RS, l, i1);
                        int j1 = blocks[k * _CS + j];
                        blocks[k * _CS + j] = blocks[i1 * _CS + l];
                        blocks[i1 * _CS + l] = j1;
                }

        }
        public BlockImage2(){
        	frm = new Frame("Puzzle Game");
            frm.setSize(500,530);
            frm.setResizable(false);
           
            // �������
            Image backImage = null ;
            Image overImage = null ;
            try {
            	backImage = ImageIO.read(new File("src/resources/img/puzzle-2.jpg"));
            } catch (IOException e) {
            }                
            frm.add(new BlockImage(backImage,overImage, 4,4));
            //BlockImage -> use to divide the puzzle in x*y
            backImage = null;
            overImage = null;
            
            // 憿舐內蝒���
            frm.setVisible(true);
            frm.addWindowListener(new WindowAdapter() 
            { 
            	public void windowClosing (WindowEvent e) { //<--- ���������� 
            		System.out.println("win->"+win);
            		frm.dispose(); //<--- 蝯���� 
            	} 
            } 
            ); 

        }
        
        /*
        public static void main(String[] args) {

                frm = new Frame("Puzzle Game");
                frm.setSize(630,530);
                frm.setResizable(false);
               
                // �������
                Image backImage = null ;
                Image overImage = null ;
                try {
                	backImage = ImageIO.read(new File("src/resources/img/puzzle-1.jpg"));
                } catch (IOException e) {
                }                
                frm.add(new BlockImage(backImage,overImage, 2, 2));
                //BlockImage -> use to divide the puzzle in x*y
                backImage = null;
                overImage = null;
                
                // 憿舐內蝒���
                frm.setVisible(true);
        }*/

}
