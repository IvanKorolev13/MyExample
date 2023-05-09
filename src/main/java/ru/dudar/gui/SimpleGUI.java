package ru.dudar.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGUI extends JFrame {
    private JButton button = new JButton("Press");
    private JTextField input = new JTextField("", 10);
    private JLabel label = new JLabel("Input");
    private JRadioButton radio1 = new JRadioButton("M");
    private JRadioButton radio2 = new JRadioButton("W");
    private JCheckBox check = new JCheckBox("Check", false);

    public SimpleGUI() {
        super("Title"); //название окна/ формочки
        this.setBounds(200, 100, 300,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 5,2,2));

        container.add(label);
        container.add(input);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        container.add(radio1);
        radio1.setSelected(true);
        container.add(radio2);

        container.add(check);

        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String massage = "";
            massage += "Button was pressed\n";
            massage += "Text is " + input.getText() + "\n";
            massage += (radio1.isSelected() ? "Men" : "Women") + " is selected\n";
            massage += "Checkbox is " + (check.isSelected() ? "checked" : "unchecked") + "\n";
            //вывод новым окном это сообщение
            JOptionPane.showMessageDialog(null, massage, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
