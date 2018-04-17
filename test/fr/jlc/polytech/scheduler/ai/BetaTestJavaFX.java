package fr.jlc.polytech.scheduler.ai;

import fr.jlc.polytech.scheduler.core.*;
import fr.jlc.polytech.scheduler.core.timeline.EventBuilder;
import fr.jlc.polytech.scheduler.core.timeline.TimelineController;
import fr.jlc.polytech.scheduler.io.FileGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class BetaTestJavaFX extends Application {
    private Box box;

    @Override
    @Test
    public void start(Stage primaryStage) throws Exception {
        BorderPane bp_main = new BorderPane();
        setup();
        Beta beta = new Beta();

        //box = Generator.generateBox();
        System.out.println(FileGenerator.generateContent(box));
        beta.manage(box);

        TimelineController timeline = new TimelineController();

        timeline.setTimeline(beta.getTimeline());
        bp_main.setCenter(timeline.getView());


        primaryStage.setTitle("TimelineBetaTest");
        primaryStage.setScene(new Scene(bp_main));
        primaryStage.show();

        timeline.update();
    }

    @Test
    public static void main(String[] args) {
        launch(args);
    }

    public void setup() {
        box = new Box();
        box.getClusters().add(new Cluster(
                new Machine(Type.CPU, new Capacity(40, 'G')),
                new Machine(Type.CPU, new Capacity(10, 'G')),
                new Machine(Type.CPU, new Capacity(8, 'G')),

                new Machine(Type.GPU, new Capacity(25, 'T')),
                new Machine(Type.GPU, new Capacity(11, 'T')),

                new Machine(Type.IO, new Capacity(2, 'G')),
                new Machine(Type.IO, new Capacity(2, 'G')),
                new Machine(Type.IO, new Capacity(5, 'G'))
        ));

        Task t11 = new Task(Type.CPU, new Capacity(400, 'G'));
        Task t12 = new Task(Type.CPU, new Capacity(500, 'G'));
        Task t13 = new Task(Type.GPU, new Capacity(100, 'G'), t11, t12);
        Task t14 = new Task(Type.IO, new Capacity(2, 'G'), t13);
        Task t15 = new Task(Type.IO, new Capacity(20, 'G'), t13);
        Task t16 = new Task(Type.IO, new Capacity(30, 'G'), t13);
        Task t17 = new Task(Type.CPU, new Capacity(150, 'G'), t13);
        Task t18 = new Task(Type.CPU, new Capacity(160, 'G'), t13);
        Task t19 = new Task(Type.CPU, new Capacity(170, 'G'), t13,t17);
        Task t110 = new Task(Type.CPU, new Capacity(180, 'G'), t17);
        Task t111 = new Task(Type.CPU, new Capacity(210, 'G'), t14);
        Task t112 = new Task(Type.CPU, new Capacity(300, 'G'), t15,t14,t13);
        Task t113 = new Task(Type.CPU, new Capacity(200, 'G'), t13,t12);
        Task t114 = new Task(Type.IO, new Capacity(10, 'G'), t15);
        Task t115 = new Task(Type.IO, new Capacity(20, 'G'), t17,t112,t113);
        Task t116 = new Task(Type.IO, new Capacity(50, 'G'), t11,t115);

        Job job1 = new Job(
                t11,
                t12,
                t13,
                t14,
                t15,
                t16,
                t17,
                t18,
                t19,
                t110,
                t111,
                t112,
                t113,
                t114,
                t115,
                t116
        );

        Task t21 = new Task(Type.GPU, new Capacity(200, 'G'));
        Task t22 = new Task(Type.CPU, new Capacity(1, 'T'), t21);
        Task t23 = new Task(Type.IO, new Capacity(7, 'G'), t22);
        Task t24 = new Task(Type.IO, new Capacity(5, 'G'), t21);

        Job job2 = new Job(
                t21,
                t22,
                t23,
                t24
        );

        box.addJobs(job1, job2);
    }

}
