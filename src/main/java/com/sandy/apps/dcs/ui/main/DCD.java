
package com.sandy.apps.dcs.ui.main ;

import javax.swing.* ;

import com.sandy.apps.dcs.common.* ;
import com.sandy.apps.dcs.ui.* ;

import java.awt.* ;
import java.awt.event.* ;

public class DCD {

    public static void main(String[] args) {

        DCDUtility utility = null ;
        Dimension screenSize = null ;
        DCDFrame frame = null ;
        DCDCanvas canvas = null ;
        DCDController controller = null ;
        DCDToolBar toolbar = null ;
        JScrollPane scrollPane = null ;
        JTabbedPane tabbedPane = new JTabbedPane() ;
        JTextArea textArea = new JTextArea(50, 50) ;
        JTextArea logArea = new JTextArea(50, 50) ;
        JTextArea addedInformationArea = new JTextArea(50, 50) ;
        int width = 0 ;
        int height = 0 ;

        screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
        utility = DCDUtility.getInstance() ;
        canvas = new DCDCanvas() ;
        frame = new DCDFrame() ;
        scrollPane = new JScrollPane(canvas) ;
        width = (int) screenSize.getWidth() ;
        height = (int) screenSize.getHeight() ;

        textArea.setBackground(utility.getBackgroundColor()) ;
        logArea.setBackground(utility.getBackgroundColor()) ;
        addedInformationArea.setLineWrap(true) ;
        addedInformationArea.setWrapStyleWord(true) ;
        textArea.setLineWrap(true) ;
        textArea.setWrapStyleWord(true) ;

        utility.setDescriptionArea(textArea) ;
        utility.setMessageArea(logArea) ;
        utility.setAddedInformationArea(addedInformationArea) ;

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                // TODO => Ask for confirmation.
                System.exit(0) ;
            }
        }) ;

        frame.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize
                .getHeight()) ;
        scrollPane.setPreferredSize(new Dimension((int) (4 * width / 5),
                (int) height - 50)) ;
        canvas.setPreferredSize(new Dimension(utility.getCanvasWidth(), utility
                .getCanvasHeight())) ;

        utility.setCanvas(canvas) ;
        utility.setFrame(frame) ;

        controller = new DCDController(canvas) ;
        utility.setController(controller) ;

        canvas.setController(controller) ;

        toolbar = new DCDToolBar() ;
        utility.setToolBar(toolbar) ;

        // Prepare the center panel which will contain the canvas, the toolbars
        // and the text area.
        JPanel centerPanel = new JPanel() ;
        JPanel cLeftPanel = new JPanel() ;
        JPanel cRightPanel = new JPanel() ;

        cLeftPanel.setLayout(new BoxLayout(cLeftPanel, BoxLayout.Y_AXIS)) ;
        toolbar.setPreferredSize(new Dimension(50, 175)) ;
        cLeftPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
        cLeftPanel.add(toolbar) ;
        cLeftPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;

        // descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
        // messageLabel.setHorizontalTextPosition(JLabel.LEFT);
        // cLeftPanel.add(descriptionLabel);
        cLeftPanel.add(new JScrollPane(textArea)) ;
        cLeftPanel.add(Box.createRigidArea(new Dimension(0, 5))) ;
        // cLeftPanel.add(messageLabel);
        tabbedPane.addTab("More Info", null, new JScrollPane(
                addedInformationArea), "Added information for IC's") ;
        tabbedPane.addTab("Logs", null, new JScrollPane(logArea),
                "Log messages") ;

        cLeftPanel.add(tabbedPane) ;
        cLeftPanel.setPreferredSize(new Dimension(width / 5, height - 50)) ;

        cRightPanel.setLayout(new BorderLayout()) ;
        cRightPanel.add(scrollPane) ;

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS)) ;
        centerPanel.add(cLeftPanel) ;
        centerPanel.add(cRightPanel) ;

        frame.getContentPane().add(centerPanel, "Center") ;
        frame.setJMenuBar(new DCDMenuBar(controller)) ;

        frame.setVisible(true) ;
    }
}