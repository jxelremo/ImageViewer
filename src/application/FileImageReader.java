package application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Image;
import view.ImageReader;

public class FileImageReader implements ImageReader{

    private final File[] files;
    private final String[] extensions = {".bmp",".jpg",".gif",".png"};
    
    public FileImageReader(String path) {
        this(new File(path));
    }

    public FileImageReader(File folders) {
        this.files = folders.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                for(String extension : extensions){
                    if(name.toLowerCase().endsWith(extension)){
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public Image read() {
        return imageAt(0);
    }

    private Image imageAt(int index) {
        return new Image() {

            @Override
            public Image prev() {
                return imageAt(index > 0 ? index-1 : files.length-1);
            }

            @Override
            public Image next() {
                return imageAt(index < files.length-1 ? index+1 : 0);
            }

            @Override
            public Object bitMap() {
                try {
                    return ImageIO.read(files[index]);
                } catch (IOException exception) {
                    return null;
                }
            }

            @Override
            public String name() {
                return files[index].getName();
            }
        };
    }
    
}
