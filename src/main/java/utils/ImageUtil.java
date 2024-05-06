package utils;

import logger_decorator.*;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.InputStream;

/**
 * Utility class for asynchronously loading images in the application.
 * This method improves application responsiveness by performing image loading
 * in the background, thus preventing gui from freezing.
 */
public class ImageUtil {

    private static final String DEFAULT_IMAGE_PATH = "/images/icons/empty_image.png";
    private static final Logger logger = new TimeLogger(new FileLogger());

    /**
     * Loads image from specified path asynchronously and sets it to the given ImageView.
     * If the image at specified path cannot be found or fails to load, used default image.
     *
     * @param imagePath relative path to image resource
     * @param imageView ImageView to which image will be set once loaded
     */
    public static void loadImageAsynchronously(String imagePath, ImageView imageView) {
        Task<Image> loadImageTask = new Task<>() {
            @Override
            protected Image call() {
                try (InputStream is = ImageUtil.class.getResourceAsStream(imagePath)) {
                    if (is != null) {
                        return new Image(is);
                    } else {
                        logger.log("Image not found: " + imagePath, LoggerLevel.ERROR);
                        System.err.println("Image not found: " + imagePath);

                        // Default image instead
                        return new Image(ImageUtil.class.getResourceAsStream(DEFAULT_IMAGE_PATH));
                    }
                } catch (Exception e) {
                    logger.log("Failed to load image: " + imagePath, LoggerLevel.ERROR);
                    System.err.println("Failed to load image: " + imagePath);
                    return new Image(ImageUtil.class.getResourceAsStream(DEFAULT_IMAGE_PATH));
                }
            }
        };

        loadImageTask.setOnSucceeded(e -> imageView.setImage(loadImageTask.getValue()));
        loadImageTask.setOnFailed(e -> imageView.setImage(new Image(ImageUtil.class.getResourceAsStream(DEFAULT_IMAGE_PATH))));

        new Thread(loadImageTask).start();
    }
}
