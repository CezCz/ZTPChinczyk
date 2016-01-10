package ztp.chinczyk.view;

import ztp.chinczyk.presenter.HelpPresenter;
import ztp.chinczyk.view.interfaces.HelpViewInterface;
import ztp.chinczyk.view.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HelpView extends JPanel implements HelpViewInterface{
    HelpPresenter presenter;
    JEditorPane helpEditorPane;
    JScrollPane helpEditorScrollPane;
    JButton exitButton;

    public HelpView(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        helpEditorPane = new JEditorPane();
        helpEditorPane.setEditable(false);
        helpEditorScrollPane = new JScrollPane(helpEditorPane);
        helpEditorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setSize(300,500);
        File helpFile = new File("res/helpFile.html");
        try {
            helpEditorPane.setPage(helpFile.toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
        exitButton = new JButton("Wyjdź");
        this.add(helpEditorScrollPane);
        this.add(exitButton);
    }

    private static class Factory extends ViewFactory{

        @Override
        protected View create() {
            return new HelpView();
        }
    }

    static {
        ViewFactory.addFactory("HelpView" , new Factory());
    }

    @Override
    public void registerPresenter(HelpPresenter p) {
        this.presenter = p;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.onExit();
            }
        });
    }
}
