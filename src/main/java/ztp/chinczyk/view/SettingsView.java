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
    JTextField playerCountField;

    public SettingsView(){
        super();
        this.setLayout(new GridLayout(2,2));
        acceptButton = new JButton("Zatwierdź");
        cancelButton = new JButton("Anuluj");
        portTextField = new JTextField();
        portTextField.setToolTipText("Port hosta");
        playerCountField = new JTextField();
        playerCountField.setToolTipText("Liczba graczy");

        this.add(portTextField);
        this.add(playerCountField);
        this.add(acceptButton);
        this.add(cancelButton);
    }

    @Override
    public void registerPresenter(SettingsPresenter p) {
        presenter = p;

        portTextField.setText(Integer.toString(presenter.getCurrentSettings().getHostPort()));
        playerCountField.setText(Integer.toString(presenter.getCurrentSettings().getPlayerCount()));

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> settings = new HashMap<String, String>();
                try{
                    Integer.parseInt(portTextField.getText());
                    settings.put("port", portTextField.getText());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(SettingsView.this,"Port powinien być liczbą całkowitą!");
                }
                finally {
                    try {
                        int playerCount = Integer.parseInt(playerCountField.getText());
                        if(playerCount < 2 || playerCount > 4){
                            throw new RuntimeException("Wrong player count");
                        }
                        settings.put("playerCount", playerCountField.getText());
                        p.onAccept(settings);
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(SettingsView.this, "Liczba graczy powinna być liczbą całkowitą z zakresu 2-4!");
                    }
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
