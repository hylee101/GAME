import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;



public class Game_2 extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;
	public static final int SCALE = 2;
	public static Dimension GAME_DIM = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
	public static final String NAME = "Pixel Engine";
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB );
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	
	private boolean running = false;
	Random random = new Random();
	
	public void start(){
		running = true;
		new Thread(this).start();
	}
	
	public Game_2(){
		
	}
	
	public void run(){
		while(running){
			tick();
			render();
		}
	}
	
	public void stop(){
		running = false;
	}
	
	public void tick(){
		for(int i=0;i<pixels.length;i++){
			pixels[i] = random.nextInt()<<16;
		}
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		Game_2 game = new Game_2();
		game.setPreferredSize(GAME_DIM);
		game.setMaximumSize(GAME_DIM);
		game.setMinimumSize(GAME_DIM);
		
		JFrame frame = new JFrame(NAME);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
        frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.start();
		
	}

}
