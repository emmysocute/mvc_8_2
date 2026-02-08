package mvc.controller;

import mvc.model.Model;
import mvc.view.Graduation_Readiness_Check;
import mvc.view.Viewstudent;
import mvc.view.Evaluation_Results;
import mvc.view.menu;


public class Controller {
    private Model models;
    private Viewstudent viewstu;
    private Evaluation_Results EvaView;
    private Graduation_Readiness_Check checkView;
    private menu showmenu ;
    public Controller() {
        this.models = new Model();
        this.viewstu = new Viewstudent();
        this.EvaView = new Evaluation_Results();
        this.checkView = new Graduation_Readiness_Check();
        this.showmenu = new menu();
    }

    public void start() {
        int state;
        int in;
        while (true) {
            showmenu.showmenu();
            state  = showmenu.getInt();
            
            switch (state) {
                case 1:
                    viewstu.showstudent();
                    models.showName();
                    break;
                 case 2:
                    checkView.showresult();
                    models.showresult();
                    in = showmenu.getInt();
                    while (in != 0) {
                    }
                    break;
                case 3:
                    
                    models.showresult();
                    in = showmenu.getInt();
                    while (in != 0) {
                    }
                    viewstu.showstudent();
                    models.showName();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
        
        
    }
}
