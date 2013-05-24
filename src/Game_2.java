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
	
	public static final int HEIGHT = 120;
	public static final int WIDTH = 120;
	public static final int SCALE = 3;

	public static final String NAME = "Pixel Engine";
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	Random random = new Random();
	
	private boolean running = false;
	
	public void start(){
		running = true;
		new Thread(this).start();
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
			pixels[i] = random.nextInt();
		}
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		Game_2 game = new Game_2();
		game.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		JFrame frame = new JFrame(NAME);
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		
		game.start();
		
	}

}
