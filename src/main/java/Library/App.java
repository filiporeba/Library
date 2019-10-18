package Library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Menu menu = context.getBean(Menu.class);


        while(true) {
            menu.displayFunction();
        }
    }
}
