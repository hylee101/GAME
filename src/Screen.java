
public class Screen {
	private SpriteSheetLoader loader;
	private int w,h;
	int xOffset = 0, yOffset = 0;
	public int[] pixels;
	
	public Screen(int w, int h, SpriteSheetLoader newloader){
		this.loader = newloader;
		this.w = w;
		this.h = h;
		
		pixels = new int[w*h];
	}
	
	public void render(int xPos, int yPos, int tile, int width, int height){
		loader.grabTile(tile, width, height);
		
		xPos -= xOffset;
		yPos -= yOffset;
		
		for(int y =0;y<height;y++){
			if(yPos + y < 0||yPos + y >=h)continue;
			for(int x =0; x < width; x++){
				if(xPos + x < 0||xPos + x >=w)continue;
				
				int col = loader.pixels[x+(y*height)];
				if(col !=-65281) pixels[(x+xPos)+(y+yPos)*w] = col;
			}
		}
	}
}
