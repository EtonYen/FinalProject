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

public class BlockImage extends Canvas {
        
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
        boolean winornot=false;
        //===================================
        private boolean odd=false;//initial 0
        private int temp = 0;
        private int k=0,l=0,m=0,n=0;
        static public boolean win = false;
        //===================================

        static private Frame frm;
        /**
         * 析構函數，內部調用initial方法。
         * 
         * @param bImage
         * @param overImage
         * @param cs
         * @param rs
         */
        public BlockImage(Image bImage, Image overImage, int cs, int rs) {
                init(bImage, overImage, cs, rs);
        }

        /**
         * 初始化拼圖参數。
         * 
         * @param bImage
         * @param overImage
         * @param cs
         * @param rs
         */
        public void init(Image bImage, Image overImage, int cs, int rs) {
                // 列數
                _CS = cs;
                // 行數
                _RS = rs;
                // 加載拼圖用圖像。
                _img = bImage;

                // 獲得實際窗體寬。
                _width = _img.getWidth(null);
                // 獲得實際窗體高。
                _height = _img.getHeight(null);
                // 獲得單塊圖像寬。
                _objWidth = _width / _CS;
                // 獲得單塊圖像高。
                _objHeight = _height / _RS;

                // 本程序直接使用back image上一塊圖形區域緩沖選擇項，所以實際背景圖像高=圖形高+額外圖塊高。
                backimage = new BufferedImage(_width, _height + _objHeight, 1);
                // 獲得生成的圖形
                later = backimage.getGraphics();
                // 再創建一塊圖像區域，作为圖像緩存用。
                screen = new BufferedImage(_width, _height, 1);
                // 獲得緩存的圖形
                bg = screen.getGraphics();
                // 獲得等同圖片總數的數組。
                _COUNT = _CS * _RS;
                blocks = new int[_COUNT];
                // 初始化为非點擊。
                isEvent = false;
                // 加載完成拼圖的顯示圖。
                _img2 = overImage;
                // 初始化圖塊参數。
                for (int i = 0; i < _COUNT; i++) {
                        blocks[i] = i;
                }
                // 載入MediaTracker，用以跟蹤圖像狀態。
                mt = new MediaTracker(this);
                // 加載被跟蹤的圖像。
                mt.addImage(_img, 0);
                mt.addImage(_img2,0);
                // 同步載入。
                try {
                        mt.waitForID(0);
                } catch (InterruptedException interruptedexception) {
                        return;
                }
                // 隨機生成圖像面板內容。
                rndPannel();

        }

        /**
         * 描繪窗體圖像。
         */
        public void paint(Graphics g) {
                // 檢查圖像載入。
                if (mt.checkID(0)) {
                        // 描繪底層背景。
                        bg.drawImage(backimage, 0, 0, null);
                        // 判斷是否觸發完成事件。
                        if (!isEvent) {
                                // 設置背景色。
                                bg.setColor(Color.black);
                                // 循環繪制小圖片於背景緩存中。
                                for (int i = 0; i < _CS; i++) {
                                        for (int j = 0; j < _RS; j++)
                                                bg.drawRect(i * _objWidth, j * _objHeight, _objWidth,_objHeight);
                                }

                        }
                        else{
                        	System.out.println("win!!!!!!!");
                        	win = true;
                        	//frm.dispose();
                        }
                }
                // 舉凡繪制圖像時，應遵循顯示圖像僅繪制一次的基本原則，一次性的將背景繪制到窗體。
                // 簡單來說，也就是采取[雙緩存]的方式，所有复雜操作皆在緩存區完成，也只有這样才能避免產生延遲閃爍。
                g.drawImage(screen, 0, 0, this);
                g.dispose();
        }

        /**
         * 變更圖像。
         */
        public void update(Graphics g) {
                paint(g);
        }

        /**
         * 鼠標點擊事件。
         */
        public boolean mouseDown(Event event, int i, int j) {
        	
        	if (isEvent)return true;//complete and don't do things
        	
        	//=============================================
    		//add the add to look its even step or odd step
    		if(odd == true) odd = false ;
    		else odd = true ;
    		//=============================================
    		//do things in different condition
    		//System.out.println("odd->"+odd);
    		
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
        public BlockImage(String s){
        	frm = new Frame("Puzzle Game");
            frm.setSize(500,530);
            frm.setResizable(false);
           
            // 加載圖像。
            Image backImage = null ;
            Image overImage = null ;
            try {
            	backImage = ImageIO.read(new File("src/resources/img/puzzle-"+s+".jpg"));
            } catch (IOException e) {
            }                
            frm.add(new BlockImage(backImage,overImage, 4, 4));
            //BlockImage -> use to divide the puzzle in x*y
            backImage = null;
            overImage = null;
            
            // 顯示窗體。
            frm.setVisible(true);
            frm.addWindowListener(new WindowAdapter() 
            { 
            	public void windowClosing (WindowEvent e) { //<--- 當按下關閉視窗時 
            		System.out.println("win->"+win);
            		frm.dispose(); //<--- 結束程式 
            	} 
            } 
            ); 

        }

}
