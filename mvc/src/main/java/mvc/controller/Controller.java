package mvc.controller;

import mvc.model.Eva_graduation;
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
    private menu showmenu;
    private Eva_graduation Eva_gradua;

    public Controller() {
        this.models = new Model();
        this.viewstu = new Viewstudent();
        this.EvaView = new Evaluation_Results();
        this.checkView = new Graduation_Readiness_Check();
        this.showmenu = new menu();
        this.Eva_gradua = new Eva_graduation();
    }

    public void start() {
        int state;
        int in;
        while (true) {
            showmenu.showmenu();
            state = showmenu.getInt();

            switch (state) {
                case 1:
                    viewstu.showstudent();
                    models.showName();
                    in = showmenu.getInt();
                    while (in != 0) {
                    in = showmenu.getInt();
                    }
                    break;
                case 3:
                    checkView.showresult();
                    models.showresult();
                    in = showmenu.getInt();
                    while (in != 0) {
                    in = showmenu.getInt();
                    }
                    break;
                case 2:
                    checkView.fillstudentid();
                    in = showmenu.getInt();
                    Eva_gradua.evaluateGraduation(in + "");
                    checkView.endfill();
                    models.showresult();
                    in = showmenu.getInt();
                    while (in != 0) {
                    in = showmenu.getInt();
                    }
                    viewstu.showstudent();
                    models.showName();
                    in = showmenu.getInt();
                     while (in != 0) {
                    in = showmenu.getInt();
                    }
                    break;
                case 4:
                    try {
                        models.setNewData();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }

    }
}
