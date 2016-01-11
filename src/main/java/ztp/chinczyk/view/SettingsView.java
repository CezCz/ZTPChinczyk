package ztp.chinczyk.view;

import ztp.chinczyk.presenter.SettingsPresenter;
import ztp.chinczyk.view.interfaces.SettingsViewInterface;
import ztp.chinczyk.view.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class SettingsView extends JPanel implements SettingsViewInterface{

    private static class Factory extends ViewFactory{

        @Override
        protected View create() {
            return new SettingsView();
        }
    }

    static {
        ViewFactory.addFactory("SettingsView", new Factory());
    }

    SettingsPresenter presenter;

    JButton acceptButton;
    JButton cancelButton;
    JTextField portTextField;


    public SettingsView(){
        super();
        this.setLayout(new GridLayout(2,2));
        acceptButton = new JButton("Zatwierdź");
        cancelButton = new JButton("Anuluj");
        portTextField = new JTextField("Port hosta");

        this.add(portTextField);
        this.add(new JLabel());
        this.add(acceptButton);
        this.add(cancelButton);
    }

    @Override
    public void registerPresenter(SettingsPresenter p) {
        presenter = p;

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> settings = new HashMap<String, String>();
                try{
                    Integer.parseInt(portTextField.getText());
                    settings.put("port", portTextField.getText());
                    p.onAccept(settings);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(SettingsView.this,"Port powinien być liczbą całkowitą!");
                }

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.onCancel();
            }
        });

    }
}
