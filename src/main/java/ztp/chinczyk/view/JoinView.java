package ztp.chinczyk.view;

import ztp.chinczyk.presenter.JoinPresenter;
import ztp.chinczyk.view.interfaces.JoinViewInterface;
import ztp.chinczyk.view.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinView extends JPanel implements JoinViewInterface {

    private static class Factory extends ViewFactory{

        @Override
        protected View create() {
            return new JoinView();
        }
    }

    static {
        ViewFactory.addFactory("JoinView", new Factory());
    }

    JoinPresenter joinPresenter;
    JButton cancelButton;
    JButton okButton;
    JTextField ipTextField;
    JTextField portTextField;

    private JoinView(){
        super();
        this.setLayout(new GridLayout(2,2));
        portTextField = new JTextField("Port");
        cancelButton = new JButton("Anuluj");
        ipTextField = new JTextField("Adres IP");
        okButton = new JButton("Dołącz");

        this.add(ipTextField);
        this.add(portTextField);
        this.add(cancelButton);
        this.add(okButton);
    }

    @Override
    public void registerPresenter(JoinPresenter p) {
        this.joinPresenter = p;

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.onCancel();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.onConnect(ipTextField.getText(), portTextField.getText());
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
