import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 360;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	public static Dimension GAME_DIM = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);
	public static String NAME = "Pixel Engine";
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public SpriteSheetLoader loader;
	private Screen screen;
	
	private boolean running = false;
	Random random = new Random();
	
	public void start(){
		running = true;
		new Thread(this).start();
	}
	
	public Game(){		
	}

	public void init(){
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(Game.class.getResourceAsStream("tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loader = new SpriteSheetLoader(sheet);
		screen = new Screen(WIDTH, HEIGHT,loader);
	}
	
	public void run() {
		init();
		while(running){
			tick();
			render();
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop(){
		running = false;
	}
	
	public void tick(){
		screen.render(0,0,0,16,16);
		
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		
		for(int i=0;i<screen.pixels.length;i++){
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
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
