package modulo4_administracion.controllers;

import core.Controller;
import modulo4_administracion.views.AdminPanelView;


public class AdminController extends Controller {

    private AdminPanelView adminPanelView;

    @Override
    public void run() {
        adminPanelView = new AdminPanelView(this);
        addView("AdminPanelView", adminPanelView);
        mainFrame.setVisible(true);
        loadView("AdminPanelView");
    }
}