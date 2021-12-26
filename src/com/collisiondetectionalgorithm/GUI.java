package com.collisiondetectionalgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GUI implements ActionListener {


    public static JLabel FPSCounter;
    public static JLabel TargetFPS;
    public static JLabel TickDisplay;
    public static JLabel TargetTick;

    public static JLabel EntityCount;

    public static JTextField FPStext;
    public static JTextField Ticktext;

    public GUI(){

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayout(4,0));

        JCheckBox b1 = new JCheckBox("Toggle KD Partitioning");
        b1.setSelected(true);
        b1.setActionCommand("kdtoggle");
        b1.setToolTipText("Allows you to see the perfomance difference between brute force and partitioning");

        JCheckBox b2 = new JCheckBox("Hide/Show KD area boundries");
        b2.setSelected(true);
        b2.setActionCommand("areatoggle");

        JButton b3 = new JButton("Create 10 Entities");
        b3.setActionCommand("makeentity");


        JButton b4 = new JButton("Apply Targets");
        b4.setActionCommand("apply");
        b4.setMnemonic(KeyEvent.VK_ENTER);


        EntityCount = new JLabel();

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        FPSCounter = new JLabel("FPS:");
        TickDisplay = new JLabel("Tick Processing Time:");

        TargetFPS = new JLabel("Target FPS:");
        TargetTick = new JLabel("Target MS Per Tick:");

        UpdateReadings();

        FPStext = new JTextField("60");
        Ticktext = new JTextField("150");

        JPanel FPSPanel = new JPanel();

        FPSPanel.add(FPSCounter);
        FPSPanel.add(TargetFPS);
        FPSPanel.add(FPStext);
        rootPanel.add(FPSPanel);


        JPanel TickPanel = new JPanel();



        TickPanel.add(TickDisplay);
        TickPanel.add(TargetTick);
        TickPanel.add(Ticktext);
        rootPanel.add(TickPanel);

        rootPanel.add(b4);

        JPanel controlPanel = new JPanel();

        controlPanel.add(b1);
        controlPanel.add(b2);
        controlPanel.add(b3);
        controlPanel.add(EntityCount);

        rootPanel.add(controlPanel);

        rootPanel.setPreferredSize( new Dimension(500,300));


        JFrame frame = new JFrame("Controls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(rootPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);


    }

    private static void UpdateReadings(){

        TargetFPS.setText("Target FPS: "+Renderer.targetFps);
        TargetTick.setText("Target MS Per Tick: "+Ticker.MSperTick);
        EntityCount.setText("Entity Count: "+EntityManager.Entities.size());

    }



    @Override
    public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()){

        case "kdtoggle":
            Area.DisableKDPartitioning =  !Area.DisableKDPartitioning;
            break;
        case "areatoggle":
            Area.HideAreas =  !Area.HideAreas;
            break;
        case "makeentity":
            for (int i = 0; i < 10; i++) {
                EntityManager.MakeRandomEntity();

            }
            UpdateReadings();
            break;

        case "apply":
            int tickrate = Integer.parseInt(Ticktext.getText());
            if (tickrate > 0){
                Ticker.SetTickRate(tickrate);
            }
            int fps = Integer.parseInt(FPStext.getText());
            if (fps > 0){
                Renderer.SetFPS(fps);
            }
            UpdateReadings();

            break;


    }
    }
}

